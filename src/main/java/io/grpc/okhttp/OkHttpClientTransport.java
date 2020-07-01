package io.grpc.okhttp;

import android.support.v4.media.session.PlaybackStateCompat;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs.CastExtraArgs;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.net.HttpHeaders;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.logging.type.LogSeverity;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.HttpUrl.Builder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.http.StatusLine;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.Grpc;
import io.grpc.InternalChannelz.Security;
import io.grpc.InternalChannelz.SocketOptions;
import io.grpc.InternalChannelz.SocketStats;
import io.grpc.InternalChannelz.Tls;
import io.grpc.InternalLogId;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.MethodDescriptor.MethodType;
import io.grpc.SecurityLevel;
import io.grpc.Status;
import io.grpc.Status.Code;
import io.grpc.StatusException;
import io.grpc.internal.ClientStreamListener.RpcProgress;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.GrpcAttributes;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.GrpcUtil.Http2Error;
import io.grpc.internal.Http2Ping;
import io.grpc.internal.KeepAliveManager;
import io.grpc.internal.KeepAliveManager.ClientKeepAlivePinger;
import io.grpc.internal.ManagedClientTransport.Listener;
import io.grpc.internal.ProxyParameters;
import io.grpc.internal.SerializingExecutor;
import io.grpc.internal.SharedResourceHolder;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.TransportTracer;
import io.grpc.internal.TransportTracer.FlowControlReader;
import io.grpc.internal.TransportTracer.FlowControlWindows;
import io.grpc.okhttp.internal.ConnectionSpec;
import io.grpc.okhttp.internal.framed.ErrorCode;
import io.grpc.okhttp.internal.framed.FrameReader;
import io.grpc.okhttp.internal.framed.FrameReader.Handler;
import io.grpc.okhttp.internal.framed.FrameWriter;
import io.grpc.okhttp.internal.framed.Header;
import io.grpc.okhttp.internal.framed.HeadersMode;
import io.grpc.okhttp.internal.framed.Http2;
import io.grpc.okhttp.internal.framed.Settings;
import io.grpc.okhttp.internal.framed.Variant;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;
import okio.Timeout;

class OkHttpClientTransport implements ConnectionClientTransport, TransportExceptionHandler {
    private static final OkHttpClientStream[] EMPTY_STREAM_ARRAY = new OkHttpClientStream[0];
    private static final Map<ErrorCode, Status> ERROR_CODE_TO_STATUS = buildErrorCodeToStatusMap();
    private static final Logger log = Logger.getLogger(OkHttpClientTransport.class.getName());
    private final InetSocketAddress address;
    private Attributes attributes;
    private ClientFrameHandler clientFrameHandler;
    SettableFuture<Void> connectedFuture;
    Runnable connectingCallback;
    private final ConnectionSpec connectionSpec;
    private int connectionUnacknowledgedBytesRead;
    private final String defaultAuthority;
    private boolean enableKeepAlive;
    private final Executor executor;
    private AsyncFrameWriter frameWriter;
    @GuardedBy("lock")
    private boolean goAwaySent;
    @GuardedBy("lock")
    private Status goAwayStatus;
    private HostnameVerifier hostnameVerifier;
    @GuardedBy("lock")
    private boolean inUse;
    private KeepAliveManager keepAliveManager;
    private long keepAliveTimeNanos;
    private long keepAliveTimeoutNanos;
    private boolean keepAliveWithoutCalls;
    private Listener listener;
    private final Object lock;
    private final InternalLogId logId;
    @GuardedBy("lock")
    private int maxConcurrentStreams;
    private final int maxMessageSize;
    @GuardedBy("lock")
    private int nextStreamId;
    private OutboundFlowController outboundFlow;
    @GuardedBy("lock")
    private LinkedList<OkHttpClientStream> pendingStreams;
    @GuardedBy("lock")
    private Http2Ping ping;
    @Nullable
    @VisibleForTesting
    final ProxyParameters proxy;
    private final Random random;
    private ScheduledExecutorService scheduler;
    @GuardedBy("lock")
    private Security securityInfo;
    private final SerializingExecutor serializingExecutor;
    private Socket socket;
    private SSLSocketFactory sslSocketFactory;
    @GuardedBy("lock")
    private boolean stopped;
    private final Supplier<Stopwatch> stopwatchFactory;
    @GuardedBy("lock")
    private final Map<Integer, OkHttpClientStream> streams;
    private FrameReader testFrameReader;
    private FrameWriter testFrameWriter;
    private final Runnable tooManyPingsRunnable;
    @GuardedBy("lock")
    private final TransportTracer transportTracer;
    private final String userAgent;

    @VisibleForTesting
    class ClientFrameHandler implements Handler, Runnable {
        boolean firstSettings = true;
        FrameReader frameReader;

        public void ackSettings() {
        }

        public void alternateService(int i, String str, ByteString byteString, String str2, int i2, long j) {
        }

        public void priority(int i, int i2, int i3, boolean z) {
        }

        ClientFrameHandler(FrameReader frameReader) {
            this.frameReader = frameReader;
        }

        public void run() {
            String str = "Exception closing frame reader";
            String name = Thread.currentThread().getName();
            if (!GrpcUtil.IS_RESTRICTED_APPENGINE) {
                Thread.currentThread().setName("OkHttpClientTransport");
            }
            while (this.frameReader.nextFrame(this)) {
                try {
                    if (OkHttpClientTransport.this.keepAliveManager != null) {
                        OkHttpClientTransport.this.keepAliveManager.onDataReceived();
                    }
                } catch (Throwable e) {
                    try {
                        this.frameReader.close();
                    } catch (Throwable th) {
                        OkHttpClientTransport.log.log(Level.INFO, str, th);
                    }
                    OkHttpClientTransport.this.listener.transportTerminated();
                    if (!GrpcUtil.IS_RESTRICTED_APPENGINE) {
                        Thread.currentThread().setName(name);
                    }
                    throw e;
                }
            }
            OkHttpClientTransport.this.startGoAway(0, ErrorCode.INTERNAL_ERROR, Status.UNAVAILABLE.withDescription("End of stream or IOException"));
            try {
                this.frameReader.close();
            } catch (Throwable e2) {
                OkHttpClientTransport.log.log(Level.INFO, str, e2);
            }
            OkHttpClientTransport.this.listener.transportTerminated();
            if (GrpcUtil.IS_RESTRICTED_APPENGINE) {
                return;
            }
            Thread.currentThread().setName(name);
        }

        public void data(boolean z, int i, BufferedSource bufferedSource, int i2) throws IOException {
            OkHttpClientStream stream = OkHttpClientTransport.this.getStream(i);
            if (stream != null) {
                long j = (long) i2;
                bufferedSource.require(j);
                Buffer buffer = new Buffer();
                buffer.write(bufferedSource.buffer(), j);
                synchronized (OkHttpClientTransport.this.lock) {
                    stream.transportState().transportDataReceived(buffer, z);
                }
            } else if (OkHttpClientTransport.this.mayHaveCreatedStream(i)) {
                OkHttpClientTransport.this.frameWriter.rstStream(i, ErrorCode.INVALID_STREAM);
                bufferedSource.skip((long) i2);
            } else {
                OkHttpClientTransport okHttpClientTransport = OkHttpClientTransport.this;
                ErrorCode errorCode = ErrorCode.PROTOCOL_ERROR;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Received data for unknown stream: ");
                stringBuilder.append(i);
                okHttpClientTransport.onError(errorCode, stringBuilder.toString());
                return;
            }
            OkHttpClientTransport.access$2312(OkHttpClientTransport.this, i2);
            if (OkHttpClientTransport.this.connectionUnacknowledgedBytesRead >= 32767) {
                OkHttpClientTransport.this.frameWriter.windowUpdate(0, (long) OkHttpClientTransport.this.connectionUnacknowledgedBytesRead);
                OkHttpClientTransport.this.connectionUnacknowledgedBytesRead = 0;
            }
        }

        public void headers(boolean z, boolean z2, int i, int i2, List<Header> list, HeadersMode headersMode) {
            Object obj;
            synchronized (OkHttpClientTransport.this.lock) {
                OkHttpClientStream okHttpClientStream = (OkHttpClientStream) OkHttpClientTransport.this.streams.get(Integer.valueOf(i));
                if (okHttpClientStream != null) {
                    okHttpClientStream.transportState().transportHeadersReceived(list, z2);
                } else if (OkHttpClientTransport.this.mayHaveCreatedStream(i)) {
                    OkHttpClientTransport.this.frameWriter.rstStream(i, ErrorCode.INVALID_STREAM);
                } else {
                    obj = 1;
                }
                obj = null;
            }
            if (obj != null) {
                OkHttpClientTransport okHttpClientTransport = OkHttpClientTransport.this;
                ErrorCode errorCode = ErrorCode.PROTOCOL_ERROR;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Received header for unknown stream: ");
                stringBuilder.append(i);
                okHttpClientTransport.onError(errorCode, stringBuilder.toString());
            }
        }

        public void rstStream(int i, ErrorCode errorCode) {
            Status augmentDescription = OkHttpClientTransport.toGrpcStatus(errorCode).augmentDescription("Rst Stream");
            boolean z = augmentDescription.getCode() == Code.CANCELLED || augmentDescription.getCode() == Code.DEADLINE_EXCEEDED;
            OkHttpClientTransport.this.finishStream(i, augmentDescription, errorCode == ErrorCode.REFUSED_STREAM ? RpcProgress.REFUSED : RpcProgress.PROCESSED, z, null, null);
        }

        public void settings(boolean z, Settings settings) {
            synchronized (OkHttpClientTransport.this.lock) {
                boolean initialOutboundWindowSize;
                if (OkHttpSettingsUtil.isSet(settings, 4)) {
                    OkHttpClientTransport.this.maxConcurrentStreams = OkHttpSettingsUtil.get(settings, 4);
                }
                if (OkHttpSettingsUtil.isSet(settings, 7)) {
                    initialOutboundWindowSize = OkHttpClientTransport.this.outboundFlow.initialOutboundWindowSize(OkHttpSettingsUtil.get(settings, 7));
                } else {
                    initialOutboundWindowSize = false;
                }
                if (this.firstSettings) {
                    OkHttpClientTransport.this.listener.transportReady();
                    this.firstSettings = false;
                }
                OkHttpClientTransport.this.frameWriter.ackSettings(settings);
                if (initialOutboundWindowSize) {
                    OkHttpClientTransport.this.outboundFlow.writeStreams();
                }
                OkHttpClientTransport.this.startPendingStreams();
            }
        }

        public void ping(boolean z, int i, int i2) {
            if (z) {
                Http2Ping access$2500;
                long j = (((long) i) << 32) | (((long) i2) & 4294967295L);
                synchronized (OkHttpClientTransport.this.lock) {
                    if (OkHttpClientTransport.this.ping == null) {
                        OkHttpClientTransport.log.warning("Received unexpected ping ack. No ping outstanding");
                    } else if (OkHttpClientTransport.this.ping.payload() == j) {
                        access$2500 = OkHttpClientTransport.this.ping;
                        OkHttpClientTransport.this.ping = null;
                    } else {
                        OkHttpClientTransport.log.log(Level.WARNING, String.format("Received unexpected ping ack. Expecting %d, got %d", new Object[]{Long.valueOf(OkHttpClientTransport.this.ping.payload()), Long.valueOf(j)}));
                    }
                    access$2500 = null;
                }
                if (access$2500 != null) {
                    access$2500.complete();
                    return;
                }
                return;
            }
            OkHttpClientTransport.this.frameWriter.ping(true, i, i2);
        }

        public void goAway(int i, ErrorCode errorCode, ByteString byteString) {
            if (errorCode == ErrorCode.ENHANCE_YOUR_CALM) {
                String utf8 = byteString.utf8();
                OkHttpClientTransport.log.log(Level.WARNING, String.format("%s: Received GOAWAY with ENHANCE_YOUR_CALM. Debug data: %s", new Object[]{this, utf8}));
                if ("too_many_pings".equals(utf8)) {
                    OkHttpClientTransport.this.tooManyPingsRunnable.run();
                }
            }
            Status augmentDescription = Http2Error.statusForCode((long) errorCode.httpCode).augmentDescription("Received Goaway");
            if (byteString.size() > 0) {
                augmentDescription = augmentDescription.augmentDescription(byteString.utf8());
            }
            OkHttpClientTransport.this.startGoAway(i, null, augmentDescription);
        }

        public void pushPromise(int i, int i2, List<Header> list) throws IOException {
            OkHttpClientTransport.this.frameWriter.rstStream(i, ErrorCode.PROTOCOL_ERROR);
        }

        /* JADX WARNING: Missing block: B:21:0x0063, code:
            if (r0 == null) goto L_0x007d;
     */
        /* JADX WARNING: Missing block: B:22:0x0065, code:
            r9 = r7.this$0;
            r10 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR;
            r0 = new java.lang.StringBuilder();
            r0.append("Received window_update for unknown stream: ");
            r0.append(r8);
            io.grpc.okhttp.OkHttpClientTransport.access$2200(r9, r10, r0.toString());
     */
        /* JADX WARNING: Missing block: B:23:0x007d, code:
            return;
     */
        public void windowUpdate(int r8, long r9) {
            /*
            r7 = this;
            r0 = 0;
            r2 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1));
            if (r2 != 0) goto L_0x0025;
        L_0x0006:
            r9 = "Received 0 flow control window increment.";
            if (r8 != 0) goto L_0x0012;
        L_0x000a:
            r8 = io.grpc.okhttp.OkHttpClientTransport.this;
            r10 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR;
            r8.onError(r10, r9);
            goto L_0x0024;
        L_0x0012:
            r0 = io.grpc.okhttp.OkHttpClientTransport.this;
            r10 = io.grpc.Status.INTERNAL;
            r2 = r10.withDescription(r9);
            r3 = io.grpc.internal.ClientStreamListener.RpcProgress.PROCESSED;
            r4 = 0;
            r5 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR;
            r6 = 0;
            r1 = r8;
            r0.finishStream(r1, r2, r3, r4, r5, r6);
        L_0x0024:
            return;
        L_0x0025:
            r0 = 0;
            r1 = io.grpc.okhttp.OkHttpClientTransport.this;
            r1 = r1.lock;
            monitor-enter(r1);
            if (r8 != 0) goto L_0x003c;
        L_0x002f:
            r8 = io.grpc.okhttp.OkHttpClientTransport.this;	 Catch:{ all -> 0x007e }
            r8 = r8.outboundFlow;	 Catch:{ all -> 0x007e }
            r0 = 0;
            r10 = (int) r9;	 Catch:{ all -> 0x007e }
            r8.windowUpdate(r0, r10);	 Catch:{ all -> 0x007e }
            monitor-exit(r1);	 Catch:{ all -> 0x007e }
            return;
        L_0x003c:
            r2 = io.grpc.okhttp.OkHttpClientTransport.this;	 Catch:{ all -> 0x007e }
            r2 = r2.streams;	 Catch:{ all -> 0x007e }
            r3 = java.lang.Integer.valueOf(r8);	 Catch:{ all -> 0x007e }
            r2 = r2.get(r3);	 Catch:{ all -> 0x007e }
            r2 = (io.grpc.okhttp.OkHttpClientStream) r2;	 Catch:{ all -> 0x007e }
            if (r2 == 0) goto L_0x0059;
        L_0x004e:
            r3 = io.grpc.okhttp.OkHttpClientTransport.this;	 Catch:{ all -> 0x007e }
            r3 = r3.outboundFlow;	 Catch:{ all -> 0x007e }
            r10 = (int) r9;	 Catch:{ all -> 0x007e }
            r3.windowUpdate(r2, r10);	 Catch:{ all -> 0x007e }
            goto L_0x0062;
        L_0x0059:
            r9 = io.grpc.okhttp.OkHttpClientTransport.this;	 Catch:{ all -> 0x007e }
            r9 = r9.mayHaveCreatedStream(r8);	 Catch:{ all -> 0x007e }
            if (r9 != 0) goto L_0x0062;
        L_0x0061:
            r0 = 1;
        L_0x0062:
            monitor-exit(r1);	 Catch:{ all -> 0x007e }
            if (r0 == 0) goto L_0x007d;
        L_0x0065:
            r9 = io.grpc.okhttp.OkHttpClientTransport.this;
            r10 = io.grpc.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR;
            r0 = new java.lang.StringBuilder;
            r0.<init>();
            r1 = "Received window_update for unknown stream: ";
            r0.append(r1);
            r0.append(r8);
            r8 = r0.toString();
            r9.onError(r10, r8);
        L_0x007d:
            return;
        L_0x007e:
            r8 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x007e }
            throw r8;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpClientTransport.ClientFrameHandler.windowUpdate(int, long):void");
        }
    }

    static /* synthetic */ int access$2312(OkHttpClientTransport okHttpClientTransport, int i) {
        int i2 = okHttpClientTransport.connectionUnacknowledgedBytesRead + i;
        okHttpClientTransport.connectionUnacknowledgedBytesRead = i2;
        return i2;
    }

    private static Map<ErrorCode, Status> buildErrorCodeToStatusMap() {
        Map enumMap = new EnumMap(ErrorCode.class);
        enumMap.put(ErrorCode.NO_ERROR, Status.INTERNAL.withDescription("No error: A GRPC status of OK should have been sent"));
        enumMap.put(ErrorCode.PROTOCOL_ERROR, Status.INTERNAL.withDescription("Protocol error"));
        enumMap.put(ErrorCode.INTERNAL_ERROR, Status.INTERNAL.withDescription("Internal error"));
        enumMap.put(ErrorCode.FLOW_CONTROL_ERROR, Status.INTERNAL.withDescription("Flow control error"));
        enumMap.put(ErrorCode.STREAM_CLOSED, Status.INTERNAL.withDescription("Stream closed"));
        enumMap.put(ErrorCode.FRAME_TOO_LARGE, Status.INTERNAL.withDescription("Frame too large"));
        enumMap.put(ErrorCode.REFUSED_STREAM, Status.UNAVAILABLE.withDescription("Refused stream"));
        enumMap.put(ErrorCode.CANCEL, Status.CANCELLED.withDescription("Cancelled"));
        enumMap.put(ErrorCode.COMPRESSION_ERROR, Status.INTERNAL.withDescription("Compression error"));
        enumMap.put(ErrorCode.CONNECT_ERROR, Status.INTERNAL.withDescription("Connect error"));
        enumMap.put(ErrorCode.ENHANCE_YOUR_CALM, Status.RESOURCE_EXHAUSTED.withDescription("Enhance your calm"));
        enumMap.put(ErrorCode.INADEQUATE_SECURITY, Status.PERMISSION_DENIED.withDescription("Inadequate security"));
        return Collections.unmodifiableMap(enumMap);
    }

    OkHttpClientTransport(InetSocketAddress inetSocketAddress, String str, @Nullable String str2, Executor executor, @Nullable SSLSocketFactory sSLSocketFactory, @Nullable HostnameVerifier hostnameVerifier, ConnectionSpec connectionSpec, int i, @Nullable ProxyParameters proxyParameters, Runnable runnable, TransportTracer transportTracer) {
        this.random = new Random();
        this.lock = new Object();
        this.logId = InternalLogId.allocate(getClass().getName());
        this.streams = new HashMap();
        this.attributes = Attributes.EMPTY;
        this.maxConcurrentStreams = 0;
        this.pendingStreams = new LinkedList();
        this.address = (InetSocketAddress) Preconditions.checkNotNull(inetSocketAddress, "address");
        this.defaultAuthority = str;
        this.maxMessageSize = i;
        this.executor = (Executor) Preconditions.checkNotNull(executor, "executor");
        this.serializingExecutor = new SerializingExecutor(executor);
        this.nextStreamId = 3;
        this.sslSocketFactory = sSLSocketFactory;
        this.hostnameVerifier = hostnameVerifier;
        this.connectionSpec = (ConnectionSpec) Preconditions.checkNotNull(connectionSpec, "connectionSpec");
        this.stopwatchFactory = GrpcUtil.STOPWATCH_SUPPLIER;
        this.userAgent = GrpcUtil.getGrpcUserAgent("okhttp", str2);
        this.proxy = proxyParameters;
        this.tooManyPingsRunnable = (Runnable) Preconditions.checkNotNull(runnable, "tooManyPingsRunnable");
        this.transportTracer = (TransportTracer) Preconditions.checkNotNull(transportTracer);
        initTransportTracer();
    }

    @VisibleForTesting
    OkHttpClientTransport(String str, Executor executor, FrameReader frameReader, FrameWriter frameWriter, int i, Socket socket, Supplier<Stopwatch> supplier, @Nullable Runnable runnable, SettableFuture<Void> settableFuture, int i2, Runnable runnable2, TransportTracer transportTracer) {
        this.random = new Random();
        this.lock = new Object();
        this.logId = InternalLogId.allocate(getClass().getName());
        this.streams = new HashMap();
        this.attributes = Attributes.EMPTY;
        this.maxConcurrentStreams = 0;
        this.pendingStreams = new LinkedList();
        this.address = null;
        this.maxMessageSize = i2;
        this.defaultAuthority = "notarealauthority:80";
        this.userAgent = GrpcUtil.getGrpcUserAgent("okhttp", str);
        this.executor = (Executor) Preconditions.checkNotNull(executor, "executor");
        this.serializingExecutor = new SerializingExecutor(executor);
        this.testFrameReader = (FrameReader) Preconditions.checkNotNull(frameReader, "frameReader");
        this.testFrameWriter = (FrameWriter) Preconditions.checkNotNull(frameWriter, "testFrameWriter");
        this.socket = (Socket) Preconditions.checkNotNull(socket, "socket");
        this.nextStreamId = i;
        this.stopwatchFactory = supplier;
        this.connectionSpec = null;
        this.connectingCallback = runnable;
        this.connectedFuture = (SettableFuture) Preconditions.checkNotNull(settableFuture, "connectedFuture");
        this.proxy = null;
        this.tooManyPingsRunnable = (Runnable) Preconditions.checkNotNull(runnable2, "tooManyPingsRunnable");
        this.transportTracer = (TransportTracer) Preconditions.checkNotNull(transportTracer, "transportTracer");
        initTransportTracer();
    }

    private void initTransportTracer() {
        synchronized (this.lock) {
            this.transportTracer.setFlowControlWindowReader(new FlowControlReader() {
                public FlowControlWindows read() {
                    FlowControlWindows flowControlWindows;
                    synchronized (OkHttpClientTransport.this.lock) {
                        flowControlWindows = new FlowControlWindows(-1, OkHttpClientTransport.this.outboundFlow == null ? -1 : (long) OkHttpClientTransport.this.outboundFlow.windowUpdate(null, 0));
                    }
                    return flowControlWindows;
                }
            });
        }
    }

    void enableKeepAlive(boolean z, long j, long j2, boolean z2) {
        this.enableKeepAlive = z;
        this.keepAliveTimeNanos = j;
        this.keepAliveTimeoutNanos = j2;
        this.keepAliveWithoutCalls = z2;
    }

    private boolean isForTest() {
        return this.address == null;
    }

    /* JADX WARNING: Missing block: B:17:0x0045, code:
            if (r1 == null) goto L_0x0052;
     */
    /* JADX WARNING: Missing block: B:18:0x0047, code:
            r9.frameWriter.ping(false, (int) (r3 >>> 32), (int) r3);
     */
    /* JADX WARNING: Missing block: B:19:0x0052, code:
            r6.addCallback(r10, r11);
     */
    /* JADX WARNING: Missing block: B:20:0x0055, code:
            return;
     */
    public void ping(io.grpc.internal.ClientTransport.PingCallback r10, java.util.concurrent.Executor r11) {
        /*
        r9 = this;
        r0 = r9.frameWriter;
        r1 = 1;
        r2 = 0;
        if (r0 == 0) goto L_0x0008;
    L_0x0006:
        r0 = 1;
        goto L_0x0009;
    L_0x0008:
        r0 = 0;
    L_0x0009:
        com.google.common.base.Preconditions.checkState(r0);
        r3 = 0;
        r0 = r9.lock;
        monitor-enter(r0);
        r5 = r9.stopped;	 Catch:{ all -> 0x0056 }
        if (r5 == 0) goto L_0x001e;
    L_0x0015:
        r1 = r9.getPingFailure();	 Catch:{ all -> 0x0056 }
        io.grpc.internal.Http2Ping.notifyFailed(r10, r11, r1);	 Catch:{ all -> 0x0056 }
        monitor-exit(r0);	 Catch:{ all -> 0x0056 }
        return;
    L_0x001e:
        r5 = r9.ping;	 Catch:{ all -> 0x0056 }
        if (r5 == 0) goto L_0x0027;
    L_0x0022:
        r1 = r9.ping;	 Catch:{ all -> 0x0056 }
        r6 = r1;
        r1 = 0;
        goto L_0x0044;
    L_0x0027:
        r3 = r9.random;	 Catch:{ all -> 0x0056 }
        r3 = r3.nextLong();	 Catch:{ all -> 0x0056 }
        r5 = r9.stopwatchFactory;	 Catch:{ all -> 0x0056 }
        r5 = r5.get();	 Catch:{ all -> 0x0056 }
        r5 = (com.google.common.base.Stopwatch) r5;	 Catch:{ all -> 0x0056 }
        r5.start();	 Catch:{ all -> 0x0056 }
        r6 = new io.grpc.internal.Http2Ping;	 Catch:{ all -> 0x0056 }
        r6.<init>(r3, r5);	 Catch:{ all -> 0x0056 }
        r9.ping = r6;	 Catch:{ all -> 0x0056 }
        r5 = r9.transportTracer;	 Catch:{ all -> 0x0056 }
        r5.reportKeepAliveSent();	 Catch:{ all -> 0x0056 }
    L_0x0044:
        monitor-exit(r0);	 Catch:{ all -> 0x0056 }
        if (r1 == 0) goto L_0x0052;
    L_0x0047:
        r0 = r9.frameWriter;
        r1 = 32;
        r7 = r3 >>> r1;
        r1 = (int) r7;
        r4 = (int) r3;
        r0.ping(r2, r1, r4);
    L_0x0052:
        r6.addCallback(r10, r11);
        return;
    L_0x0056:
        r10 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0056 }
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpClientTransport.ping(io.grpc.internal.ClientTransport$PingCallback, java.util.concurrent.Executor):void");
    }

    public OkHttpClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions) {
        Metadata metadata2 = metadata;
        MethodDescriptor<?, ?> methodDescriptor2 = methodDescriptor;
        Preconditions.checkNotNull(methodDescriptor, Param.METHOD);
        Preconditions.checkNotNull(metadata2, "headers");
        return new OkHttpClientStream(methodDescriptor2, metadata2, this.frameWriter, this, this.outboundFlow, this.lock, this.maxMessageSize, this.defaultAuthority, this.userAgent, StatsTraceContext.newClientContext(callOptions, metadata2), this.transportTracer);
    }

    @GuardedBy("lock")
    void streamReadyToStart(OkHttpClientStream okHttpClientStream) {
        if (this.goAwayStatus != null) {
            okHttpClientStream.transportState().transportReportStatus(this.goAwayStatus, RpcProgress.REFUSED, true, new Metadata());
        } else if (this.streams.size() >= this.maxConcurrentStreams) {
            this.pendingStreams.add(okHttpClientStream);
            setInUse();
        } else {
            startStream(okHttpClientStream);
        }
    }

    @GuardedBy("lock")
    private void startStream(OkHttpClientStream okHttpClientStream) {
        Preconditions.checkState(okHttpClientStream.id() == -1, "StreamId already assigned");
        this.streams.put(Integer.valueOf(this.nextStreamId), okHttpClientStream);
        setInUse();
        okHttpClientStream.transportState().start(this.nextStreamId);
        if (!(okHttpClientStream.getType() == MethodType.UNARY || okHttpClientStream.getType() == MethodType.SERVER_STREAMING) || okHttpClientStream.useGet()) {
            this.frameWriter.flush();
        }
        int i = this.nextStreamId;
        if (i >= 2147483645) {
            this.nextStreamId = Integer.MAX_VALUE;
            startGoAway(Integer.MAX_VALUE, ErrorCode.NO_ERROR, Status.UNAVAILABLE.withDescription("Stream ids exhausted"));
            return;
        }
        this.nextStreamId = i + 2;
    }

    @GuardedBy("lock")
    private boolean startPendingStreams() {
        boolean z = false;
        while (!this.pendingStreams.isEmpty() && this.streams.size() < this.maxConcurrentStreams) {
            startStream((OkHttpClientStream) this.pendingStreams.poll());
            z = true;
        }
        return z;
    }

    @GuardedBy("lock")
    void removePendingStream(OkHttpClientStream okHttpClientStream) {
        this.pendingStreams.remove(okHttpClientStream);
        maybeClearInUse();
    }

    public Runnable start(Listener listener) {
        this.listener = (Listener) Preconditions.checkNotNull(listener, CastExtraArgs.LISTENER);
        if (this.enableKeepAlive) {
            this.scheduler = (ScheduledExecutorService) SharedResourceHolder.get(GrpcUtil.TIMER_SERVICE);
            this.keepAliveManager = new KeepAliveManager(new ClientKeepAlivePinger(this), this.scheduler, this.keepAliveTimeNanos, this.keepAliveTimeoutNanos, this.keepAliveWithoutCalls);
            this.keepAliveManager.onTransportStarted();
        }
        this.frameWriter = new AsyncFrameWriter(this, this.serializingExecutor);
        this.outboundFlow = new OutboundFlowController(this, this.frameWriter);
        this.serializingExecutor.execute(new Runnable() {
            public void run() {
                BufferedSink buffer;
                SSLSession sSLSession = null;
                if (OkHttpClientTransport.this.isForTest()) {
                    if (OkHttpClientTransport.this.connectingCallback != null) {
                        OkHttpClientTransport.this.connectingCallback.run();
                    }
                    OkHttpClientTransport okHttpClientTransport = OkHttpClientTransport.this;
                    okHttpClientTransport.clientFrameHandler = new ClientFrameHandler(okHttpClientTransport.testFrameReader);
                    OkHttpClientTransport.this.executor.execute(OkHttpClientTransport.this.clientFrameHandler);
                    synchronized (OkHttpClientTransport.this.lock) {
                        OkHttpClientTransport.this.maxConcurrentStreams = Integer.MAX_VALUE;
                        OkHttpClientTransport.this.startPendingStreams();
                    }
                    OkHttpClientTransport.this.frameWriter.becomeConnected(OkHttpClientTransport.this.testFrameWriter, OkHttpClientTransport.this.socket);
                    OkHttpClientTransport.this.connectedFuture.set(null);
                    return;
                }
                Socket socket;
                BufferedSource buffer2 = Okio.buffer(new Source() {
                    public void close() {
                    }

                    public long read(Buffer buffer, long j) {
                        return -1;
                    }

                    public Timeout timeout() {
                        return Timeout.NONE;
                    }
                });
                Variant http2 = new Http2();
                try {
                    Socket socket2;
                    if (OkHttpClientTransport.this.proxy == null) {
                        socket2 = new Socket(OkHttpClientTransport.this.address.getAddress(), OkHttpClientTransport.this.address.getPort());
                    } else {
                        socket2 = OkHttpClientTransport.this.createHttpProxySocket(OkHttpClientTransport.this.address, OkHttpClientTransport.this.proxy.proxyAddress, OkHttpClientTransport.this.proxy.username, OkHttpClientTransport.this.proxy.password);
                    }
                    socket = socket2;
                    if (OkHttpClientTransport.this.sslSocketFactory != null) {
                        socket = OkHttpTlsUpgrader.upgrade(OkHttpClientTransport.this.sslSocketFactory, OkHttpClientTransport.this.hostnameVerifier, socket, OkHttpClientTransport.this.getOverridenHost(), OkHttpClientTransport.this.getOverridenPort(), OkHttpClientTransport.this.connectionSpec);
                        sSLSession = socket.getSession();
                    }
                    socket.setTcpNoDelay(true);
                    buffer2 = Okio.buffer(Okio.source(socket));
                    buffer = Okio.buffer(Okio.sink(socket));
                    OkHttpClientTransport.this.attributes = Attributes.newBuilder().set(Grpc.TRANSPORT_ATTR_REMOTE_ADDR, socket.getRemoteSocketAddress()).set(Grpc.TRANSPORT_ATTR_LOCAL_ADDR, socket.getLocalSocketAddress()).set(Grpc.TRANSPORT_ATTR_SSL_SESSION, sSLSession).set(GrpcAttributes.ATTR_SECURITY_LEVEL, sSLSession == null ? SecurityLevel.NONE : SecurityLevel.PRIVACY_AND_INTEGRITY).build();
                } catch (StatusException e) {
                    OkHttpClientTransport.this.startGoAway(0, ErrorCode.INTERNAL_ERROR, e.getStatus());
                    return;
                } catch (Throwable e2) {
                    OkHttpClientTransport.this.onException(e2);
                    return;
                } finally {
                    OkHttpClientTransport okHttpClientTransport2 = OkHttpClientTransport.this;
                    buffer = new ClientFrameHandler(http2.newReader(buffer2, true));
                    okHttpClientTransport2.clientFrameHandler = buffer;
                    Executor access$500 = OkHttpClientTransport.this.executor;
                    sSLSession = OkHttpClientTransport.this.clientFrameHandler;
                    access$500.execute(sSLSession);
                    return;
                }
                synchronized (OkHttpClientTransport.this.lock) {
                    OkHttpClientTransport.this.socket = (Socket) Preconditions.checkNotNull(socket, "socket");
                    OkHttpClientTransport.this.maxConcurrentStreams = Integer.MAX_VALUE;
                    OkHttpClientTransport.this.startPendingStreams();
                    if (sSLSession != null) {
                        OkHttpClientTransport.this.securityInfo = new Security(new Tls(sSLSession));
                    }
                }
                FrameWriter newWriter = http2.newWriter(buffer, true);
                OkHttpClientTransport.this.frameWriter.becomeConnected(newWriter, OkHttpClientTransport.this.socket);
                try {
                    newWriter.connectionPreface();
                    newWriter.settings(new Settings());
                } catch (Throwable e3) {
                    OkHttpClientTransport.this.onException(e3);
                }
            }
        });
        return null;
    }

    private Socket createHttpProxySocket(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, String str, String str2) throws IOException, StatusException {
        String str3 = "\r\n";
        StatusLine parse;
        Buffer buffer;
        try {
            Socket socket;
            if (inetSocketAddress2.getAddress() != null) {
                socket = new Socket(inetSocketAddress2.getAddress(), inetSocketAddress2.getPort());
            } else {
                socket = new Socket(inetSocketAddress2.getHostName(), inetSocketAddress2.getPort());
            }
            socket.setTcpNoDelay(true);
            Source source = Okio.source(socket);
            BufferedSink buffer2 = Okio.buffer(Okio.sink(socket));
            Request createHttpProxyRequest = createHttpProxyRequest(inetSocketAddress, str, str2);
            HttpUrl httpUrl = createHttpProxyRequest.httpUrl();
            buffer2.writeUtf8(String.format("CONNECT %s:%d HTTP/1.1", new Object[]{httpUrl.host(), Integer.valueOf(httpUrl.port())})).writeUtf8(str3);
            int size = createHttpProxyRequest.headers().size();
            for (int i = 0; i < size; i++) {
                buffer2.writeUtf8(createHttpProxyRequest.headers().name(i)).writeUtf8(": ").writeUtf8(createHttpProxyRequest.headers().value(i)).writeUtf8(str3);
            }
            buffer2.writeUtf8(str3);
            buffer2.flush();
            parse = StatusLine.parse(readUtf8LineStrictUnbuffered(source));
            while (!readUtf8LineStrictUnbuffered(source).equals("")) {
            }
            if (parse.code >= LogSeverity.INFO_VALUE && parse.code < 300) {
                return socket;
            }
            buffer = new Buffer();
            try {
                socket.shutdownOutput();
                source.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            } catch (IOException e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to read body: ");
                stringBuilder.append(e.toString());
                buffer.writeUtf8(stringBuilder.toString());
            }
            socket.close();
        } catch (IOException unused) {
            throw Status.UNAVAILABLE.withDescription(String.format("Response returned from proxy was not successful (expected 2xx, got %d %s). Response body:\n%s", new Object[]{Integer.valueOf(parse.code), parse.message, buffer.readUtf8()})).asException();
        } catch (Throwable e2) {
            throw Status.UNAVAILABLE.withDescription("Failed trying to connect with proxy").withCause(e2).asException();
        }
    }

    private Request createHttpProxyRequest(InetSocketAddress inetSocketAddress, String str, String str2) {
        HttpUrl build = new Builder().scheme(UriUtil.HTTPS_SCHEME).host(inetSocketAddress.getHostName()).port(inetSocketAddress.getPort()).build();
        Request.Builder url = new Request.Builder().url(build);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(build.host());
        stringBuilder.append(":");
        stringBuilder.append(build.port());
        Request.Builder header = url.header(HttpHeaders.HOST, stringBuilder.toString()).header(HttpHeaders.USER_AGENT, this.userAgent);
        if (!(str == null || str2 == null)) {
            header.header(HttpHeaders.PROXY_AUTHORIZATION, Credentials.basic(str, str2));
        }
        return header.build();
    }

    private static String readUtf8LineStrictUnbuffered(Source source) throws IOException {
        Buffer buffer = new Buffer();
        while (source.read(buffer, 1) != -1) {
            if (buffer.getByte(buffer.size() - 1) == (byte) 10) {
                return buffer.readUtf8LineStrict();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\n not found: ");
        stringBuilder.append(buffer.readByteString().hex());
        throw new EOFException(stringBuilder.toString());
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("logId", this.logId.getId()).add("address", this.address).toString();
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    @VisibleForTesting
    String getOverridenHost() {
        URI authorityToUri = GrpcUtil.authorityToUri(this.defaultAuthority);
        if (authorityToUri.getHost() != null) {
            return authorityToUri.getHost();
        }
        return this.defaultAuthority;
    }

    @VisibleForTesting
    int getOverridenPort() {
        URI authorityToUri = GrpcUtil.authorityToUri(this.defaultAuthority);
        if (authorityToUri.getPort() != -1) {
            return authorityToUri.getPort();
        }
        return this.address.getPort();
    }

    public void shutdown(Status status) {
        synchronized (this.lock) {
            if (this.goAwayStatus != null) {
                return;
            }
            this.goAwayStatus = status;
            this.listener.transportShutdown(this.goAwayStatus);
            stopIfNecessary();
        }
    }

    public void shutdownNow(Status status) {
        shutdown(status);
        synchronized (this.lock) {
            Iterator it = this.streams.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                it.remove();
                ((OkHttpClientStream) entry.getValue()).transportState().transportReportStatus(status, false, new Metadata());
            }
            it = this.pendingStreams.iterator();
            while (it.hasNext()) {
                ((OkHttpClientStream) it.next()).transportState().transportReportStatus(status, true, new Metadata());
            }
            this.pendingStreams.clear();
            maybeClearInUse();
            stopIfNecessary();
        }
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    OkHttpClientStream[] getActiveStreams() {
        OkHttpClientStream[] okHttpClientStreamArr;
        synchronized (this.lock) {
            okHttpClientStreamArr = (OkHttpClientStream[]) this.streams.values().toArray(EMPTY_STREAM_ARRAY);
        }
        return okHttpClientStreamArr;
    }

    @VisibleForTesting
    ClientFrameHandler getHandler() {
        return this.clientFrameHandler;
    }

    @VisibleForTesting
    int getPendingStreamSize() {
        int size;
        synchronized (this.lock) {
            size = this.pendingStreams.size();
        }
        return size;
    }

    public void onException(Throwable th) {
        Preconditions.checkNotNull(th, "failureCause");
        startGoAway(0, ErrorCode.INTERNAL_ERROR, Status.UNAVAILABLE.withCause(th));
    }

    private void onError(ErrorCode errorCode, String str) {
        startGoAway(0, errorCode, toGrpcStatus(errorCode).augmentDescription(str));
    }

    private void startGoAway(int i, ErrorCode errorCode, Status status) {
        synchronized (this.lock) {
            if (this.goAwayStatus == null) {
                this.goAwayStatus = status;
                this.listener.transportShutdown(status);
            }
            if (!(errorCode == null || this.goAwaySent)) {
                this.goAwaySent = true;
                this.frameWriter.goAway(0, errorCode, new byte[0]);
            }
            Iterator it = this.streams.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                if (((Integer) entry.getKey()).intValue() > i) {
                    it.remove();
                    ((OkHttpClientStream) entry.getValue()).transportState().transportReportStatus(status, RpcProgress.REFUSED, false, new Metadata());
                }
            }
            Iterator it2 = this.pendingStreams.iterator();
            while (it2.hasNext()) {
                ((OkHttpClientStream) it2.next()).transportState().transportReportStatus(status, RpcProgress.REFUSED, true, new Metadata());
            }
            this.pendingStreams.clear();
            maybeClearInUse();
            stopIfNecessary();
        }
    }

    void finishStream(int i, @Nullable Status status, RpcProgress rpcProgress, boolean z, @Nullable ErrorCode errorCode, @Nullable Metadata metadata) {
        synchronized (this.lock) {
            OkHttpClientStream okHttpClientStream = (OkHttpClientStream) this.streams.remove(Integer.valueOf(i));
            if (okHttpClientStream != null) {
                if (errorCode != null) {
                    this.frameWriter.rstStream(i, ErrorCode.CANCEL);
                }
                if (status != null) {
                    TransportState transportState = okHttpClientStream.transportState();
                    if (metadata == null) {
                        metadata = new Metadata();
                    }
                    transportState.transportReportStatus(status, rpcProgress, z, metadata);
                }
                if (!startPendingStreams()) {
                    stopIfNecessary();
                    maybeClearInUse();
                }
            }
        }
    }

    @GuardedBy("lock")
    private void stopIfNecessary() {
        if (this.goAwayStatus != null && this.streams.isEmpty() && this.pendingStreams.isEmpty() && !this.stopped) {
            this.stopped = true;
            KeepAliveManager keepAliveManager = this.keepAliveManager;
            if (keepAliveManager != null) {
                keepAliveManager.onTransportTermination();
                this.scheduler = (ScheduledExecutorService) SharedResourceHolder.release(GrpcUtil.TIMER_SERVICE, this.scheduler);
            }
            Http2Ping http2Ping = this.ping;
            if (http2Ping != null) {
                http2Ping.failed(getPingFailure());
                this.ping = null;
            }
            if (!this.goAwaySent) {
                this.goAwaySent = true;
                this.frameWriter.goAway(0, ErrorCode.NO_ERROR, new byte[0]);
            }
            this.frameWriter.close();
        }
    }

    @GuardedBy("lock")
    private void maybeClearInUse() {
        if (this.inUse && this.pendingStreams.isEmpty() && this.streams.isEmpty()) {
            this.inUse = false;
            this.listener.transportInUse(false);
            KeepAliveManager keepAliveManager = this.keepAliveManager;
            if (keepAliveManager != null) {
                keepAliveManager.onTransportIdle();
            }
        }
    }

    @GuardedBy("lock")
    private void setInUse() {
        if (!this.inUse) {
            this.inUse = true;
            this.listener.transportInUse(true);
            KeepAliveManager keepAliveManager = this.keepAliveManager;
            if (keepAliveManager != null) {
                keepAliveManager.onTransportActive();
            }
        }
    }

    private Throwable getPingFailure() {
        synchronized (this.lock) {
            Throwable asException;
            if (this.goAwayStatus != null) {
                asException = this.goAwayStatus.asException();
                return asException;
            }
            asException = Status.UNAVAILABLE.withDescription("Connection closed").asException();
            return asException;
        }
    }

    boolean mayHaveCreatedStream(int i) {
        boolean z;
        synchronized (this.lock) {
            z = true;
            if (i >= this.nextStreamId || (i & 1) != 1) {
                z = false;
            }
        }
        return z;
    }

    OkHttpClientStream getStream(int i) {
        OkHttpClientStream okHttpClientStream;
        synchronized (this.lock) {
            okHttpClientStream = (OkHttpClientStream) this.streams.get(Integer.valueOf(i));
        }
        return okHttpClientStream;
    }

    @VisibleForTesting
    static Status toGrpcStatus(ErrorCode errorCode) {
        Status status = (Status) ERROR_CODE_TO_STATUS.get(errorCode);
        if (status != null) {
            return status;
        }
        status = Status.UNKNOWN;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown http2 error code: ");
        stringBuilder.append(errorCode.httpCode);
        return status.withDescription(stringBuilder.toString());
    }

    public ListenableFuture<SocketStats> getStats() {
        ListenableFuture create = SettableFuture.create();
        synchronized (this.lock) {
            if (this.socket == null) {
                create.set(new SocketStats(this.transportTracer.getStats(), null, null, new SocketOptions.Builder().build(), null));
            } else {
                create.set(new SocketStats(this.transportTracer.getStats(), this.socket.getLocalSocketAddress(), this.socket.getRemoteSocketAddress(), Utils.getSocketOptions(this.socket), this.securityInfo));
            }
        }
        return create;
    }
}
