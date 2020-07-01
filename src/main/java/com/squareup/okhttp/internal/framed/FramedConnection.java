package com.squareup.okhttp.internal.framed;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.framed.FrameReader.Handler;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public final class FramedConnection implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    private static final ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp FramedConnection", true));
    long bytesLeftInWriteWindow;
    final boolean client;
    private final Set<Integer> currentPushRequests;
    final FrameWriter frameWriter;
    private final String hostName;
    private long idleStartTimeNs;
    private int lastGoodStreamId;
    private final Listener listener;
    private int nextPingId;
    private int nextStreamId;
    Settings okHttpSettings;
    final Settings peerSettings;
    private Map<Integer, Ping> pings;
    final Protocol protocol;
    private final ExecutorService pushExecutor;
    private final PushObserver pushObserver;
    final Reader readerRunnable;
    private boolean receivedInitialPeerSettings;
    private boolean shutdown;
    final Socket socket;
    private final Map<Integer, FramedStream> streams;
    long unacknowledgedBytesRead;
    final Variant variant;

    public static class Builder {
        private boolean client;
        private String hostName;
        private Listener listener = Listener.REFUSE_INCOMING_STREAMS;
        private Protocol protocol = Protocol.SPDY_3;
        private PushObserver pushObserver = PushObserver.CANCEL;
        private BufferedSink sink;
        private Socket socket;
        private BufferedSource source;

        public Builder(boolean z) throws IOException {
            this.client = z;
        }

        public Builder socket(Socket socket) throws IOException {
            return socket(socket, ((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName(), Okio.buffer(Okio.source(socket)), Okio.buffer(Okio.sink(socket)));
        }

        public Builder socket(Socket socket, String str, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            this.socket = socket;
            this.hostName = str;
            this.source = bufferedSource;
            this.sink = bufferedSink;
            return this;
        }

        public Builder listener(Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.pushObserver = pushObserver;
            return this;
        }

        public FramedConnection build() throws IOException {
            return new FramedConnection(this, null);
        }
    }

    public static abstract class Listener {
        public static final Listener REFUSE_INCOMING_STREAMS = new Listener() {
            public void onStream(FramedStream framedStream) throws IOException {
                framedStream.close(ErrorCode.REFUSED_STREAM);
            }
        };

        public void onSettings(FramedConnection framedConnection) {
        }

        public abstract void onStream(FramedStream framedStream) throws IOException;
    }

    class Reader extends NamedRunnable implements Handler {
        final FrameReader frameReader;

        public void ackSettings() {
        }

        public void alternateService(int i, String str, ByteString byteString, String str2, int i2, long j) {
        }

        public void priority(int i, int i2, int i3, boolean z) {
        }

        /* synthetic */ Reader(FramedConnection framedConnection, FrameReader frameReader, AnonymousClass1 anonymousClass1) {
            this(frameReader);
        }

        private Reader(FrameReader frameReader) {
            super("OkHttp %s", r3.hostName);
            this.frameReader = frameReader;
        }

        /* JADX WARNING: Missing block: B:11:0x001f, code:
            r2 = move-exception;
     */
        /* JADX WARNING: Missing block: B:13:?, code:
            r0 = com.squareup.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR;
            r1 = com.squareup.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR;
     */
        /* JADX WARNING: Missing block: B:15:?, code:
            r2 = r4.this$0;
     */
        /* JADX WARNING: Missing block: B:20:?, code:
            com.squareup.okhttp.internal.framed.FramedConnection.access$1200(r4.this$0, r0, r1);
     */
        /* JADX WARNING: Missing block: B:21:0x0035, code:
            com.squareup.okhttp.internal.Util.closeQuietly(r4.frameReader);
     */
        /* JADX WARNING: Missing block: B:22:0x003a, code:
            throw r2;
     */
        protected void execute() {
            /*
            r4 = this;
            r0 = com.squareup.okhttp.internal.framed.ErrorCode.INTERNAL_ERROR;
            r1 = com.squareup.okhttp.internal.framed.ErrorCode.INTERNAL_ERROR;
            r2 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ IOException -> 0x0021 }
            r2 = r2.client;	 Catch:{ IOException -> 0x0021 }
            if (r2 != 0) goto L_0x000f;
        L_0x000a:
            r2 = r4.frameReader;	 Catch:{ IOException -> 0x0021 }
            r2.readConnectionPreface();	 Catch:{ IOException -> 0x0021 }
        L_0x000f:
            r2 = r4.frameReader;	 Catch:{ IOException -> 0x0021 }
            r2 = r2.nextFrame(r4);	 Catch:{ IOException -> 0x0021 }
            if (r2 == 0) goto L_0x0018;
        L_0x0017:
            goto L_0x000f;
        L_0x0018:
            r0 = com.squareup.okhttp.internal.framed.ErrorCode.NO_ERROR;	 Catch:{ IOException -> 0x0021 }
            r1 = com.squareup.okhttp.internal.framed.ErrorCode.CANCEL;	 Catch:{ IOException -> 0x0021 }
            r2 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ IOException -> 0x002a }
            goto L_0x0027;
        L_0x001f:
            r2 = move-exception;
            goto L_0x0030;
        L_0x0021:
            r0 = com.squareup.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR;	 Catch:{ all -> 0x001f }
            r1 = com.squareup.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR;	 Catch:{ all -> 0x001f }
            r2 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ IOException -> 0x002a }
        L_0x0027:
            r2.close(r0, r1);	 Catch:{ IOException -> 0x002a }
        L_0x002a:
            r0 = r4.frameReader;
            com.squareup.okhttp.internal.Util.closeQuietly(r0);
            return;
        L_0x0030:
            r3 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ IOException -> 0x0035 }
            r3.close(r0, r1);	 Catch:{ IOException -> 0x0035 }
        L_0x0035:
            r0 = r4.frameReader;
            com.squareup.okhttp.internal.Util.closeQuietly(r0);
            throw r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.FramedConnection.Reader.execute():void");
        }

        public void data(boolean z, int i, BufferedSource bufferedSource, int i2) throws IOException {
            if (FramedConnection.this.pushedStream(i)) {
                FramedConnection.this.pushDataLater(i, bufferedSource, i2, z);
                return;
            }
            FramedStream stream = FramedConnection.this.getStream(i);
            if (stream == null) {
                FramedConnection.this.writeSynResetLater(i, ErrorCode.INVALID_STREAM);
                bufferedSource.skip((long) i2);
                return;
            }
            stream.receiveData(bufferedSource, i2);
            if (z) {
                stream.receiveFin();
            }
        }

        /* JADX WARNING: Missing block: B:31:0x008f, code:
            if (r14.failIfStreamPresent() == false) goto L_0x009c;
     */
        /* JADX WARNING: Missing block: B:32:0x0091, code:
            r0.closeLater(com.squareup.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR);
            r8.this$0.removeStream(r11);
     */
        /* JADX WARNING: Missing block: B:33:0x009b, code:
            return;
     */
        /* JADX WARNING: Missing block: B:34:0x009c, code:
            r0.receiveHeaders(r13, r14);
     */
        /* JADX WARNING: Missing block: B:35:0x009f, code:
            if (r10 == false) goto L_0x00a4;
     */
        /* JADX WARNING: Missing block: B:36:0x00a1, code:
            r0.receiveFin();
     */
        /* JADX WARNING: Missing block: B:37:0x00a4, code:
            return;
     */
        public void headers(boolean r9, boolean r10, int r11, int r12, java.util.List<com.squareup.okhttp.internal.framed.Header> r13, com.squareup.okhttp.internal.framed.HeadersMode r14) {
            /*
            r8 = this;
            r12 = com.squareup.okhttp.internal.framed.FramedConnection.this;
            r12 = r12.pushedStream(r11);
            if (r12 == 0) goto L_0x000e;
        L_0x0008:
            r9 = com.squareup.okhttp.internal.framed.FramedConnection.this;
            r9.pushHeadersLater(r11, r13, r10);
            return;
        L_0x000e:
            r12 = com.squareup.okhttp.internal.framed.FramedConnection.this;
            monitor-enter(r12);
            r0 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ all -> 0x00a5 }
            r0 = r0.shutdown;	 Catch:{ all -> 0x00a5 }
            if (r0 == 0) goto L_0x001b;
        L_0x0019:
            monitor-exit(r12);	 Catch:{ all -> 0x00a5 }
            return;
        L_0x001b:
            r0 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ all -> 0x00a5 }
            r0 = r0.getStream(r11);	 Catch:{ all -> 0x00a5 }
            if (r0 != 0) goto L_0x008a;
        L_0x0023:
            r14 = r14.failIfStreamAbsent();	 Catch:{ all -> 0x00a5 }
            if (r14 == 0) goto L_0x0032;
        L_0x0029:
            r9 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ all -> 0x00a5 }
            r10 = com.squareup.okhttp.internal.framed.ErrorCode.INVALID_STREAM;	 Catch:{ all -> 0x00a5 }
            r9.writeSynResetLater(r11, r10);	 Catch:{ all -> 0x00a5 }
            monitor-exit(r12);	 Catch:{ all -> 0x00a5 }
            return;
        L_0x0032:
            r14 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ all -> 0x00a5 }
            r14 = r14.lastGoodStreamId;	 Catch:{ all -> 0x00a5 }
            if (r11 > r14) goto L_0x003c;
        L_0x003a:
            monitor-exit(r12);	 Catch:{ all -> 0x00a5 }
            return;
        L_0x003c:
            r14 = r11 % 2;
            r0 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ all -> 0x00a5 }
            r0 = r0.nextStreamId;	 Catch:{ all -> 0x00a5 }
            r1 = 2;
            r0 = r0 % r1;
            if (r14 != r0) goto L_0x004a;
        L_0x0048:
            monitor-exit(r12);	 Catch:{ all -> 0x00a5 }
            return;
        L_0x004a:
            r14 = new com.squareup.okhttp.internal.framed.FramedStream;	 Catch:{ all -> 0x00a5 }
            r4 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ all -> 0x00a5 }
            r2 = r14;
            r3 = r11;
            r5 = r9;
            r6 = r10;
            r7 = r13;
            r2.<init>(r3, r4, r5, r6, r7);	 Catch:{ all -> 0x00a5 }
            r9 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ all -> 0x00a5 }
            r9.lastGoodStreamId = r11;	 Catch:{ all -> 0x00a5 }
            r9 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ all -> 0x00a5 }
            r9 = r9.streams;	 Catch:{ all -> 0x00a5 }
            r10 = java.lang.Integer.valueOf(r11);	 Catch:{ all -> 0x00a5 }
            r9.put(r10, r14);	 Catch:{ all -> 0x00a5 }
            r9 = com.squareup.okhttp.internal.framed.FramedConnection.executor;	 Catch:{ all -> 0x00a5 }
            r10 = new com.squareup.okhttp.internal.framed.FramedConnection$Reader$1;	 Catch:{ all -> 0x00a5 }
            r13 = "OkHttp %s stream %d";
            r0 = new java.lang.Object[r1];	 Catch:{ all -> 0x00a5 }
            r1 = 0;
            r2 = com.squareup.okhttp.internal.framed.FramedConnection.this;	 Catch:{ all -> 0x00a5 }
            r2 = r2.hostName;	 Catch:{ all -> 0x00a5 }
            r0[r1] = r2;	 Catch:{ all -> 0x00a5 }
            r1 = 1;
            r11 = java.lang.Integer.valueOf(r11);	 Catch:{ all -> 0x00a5 }
            r0[r1] = r11;	 Catch:{ all -> 0x00a5 }
            r10.<init>(r13, r0, r14);	 Catch:{ all -> 0x00a5 }
            r9.execute(r10);	 Catch:{ all -> 0x00a5 }
            monitor-exit(r12);	 Catch:{ all -> 0x00a5 }
            return;
        L_0x008a:
            monitor-exit(r12);	 Catch:{ all -> 0x00a5 }
            r9 = r14.failIfStreamPresent();
            if (r9 == 0) goto L_0x009c;
        L_0x0091:
            r9 = com.squareup.okhttp.internal.framed.ErrorCode.PROTOCOL_ERROR;
            r0.closeLater(r9);
            r9 = com.squareup.okhttp.internal.framed.FramedConnection.this;
            r9.removeStream(r11);
            return;
        L_0x009c:
            r0.receiveHeaders(r13, r14);
            if (r10 == 0) goto L_0x00a4;
        L_0x00a1:
            r0.receiveFin();
        L_0x00a4:
            return;
        L_0x00a5:
            r9 = move-exception;
            monitor-exit(r12);	 Catch:{ all -> 0x00a5 }
            throw r9;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.FramedConnection.Reader.headers(boolean, boolean, int, int, java.util.List, com.squareup.okhttp.internal.framed.HeadersMode):void");
        }

        public void rstStream(int i, ErrorCode errorCode) {
            if (FramedConnection.this.pushedStream(i)) {
                FramedConnection.this.pushResetLater(i, errorCode);
                return;
            }
            FramedStream removeStream = FramedConnection.this.removeStream(i);
            if (removeStream != null) {
                removeStream.receiveRstStream(errorCode);
            }
        }

        public void settings(boolean z, Settings settings) {
            FramedStream[] framedStreamArr;
            long j;
            int i;
            synchronized (FramedConnection.this) {
                int initialWindowSize = FramedConnection.this.peerSettings.getInitialWindowSize(65536);
                if (z) {
                    FramedConnection.this.peerSettings.clear();
                }
                FramedConnection.this.peerSettings.merge(settings);
                if (FramedConnection.this.getProtocol() == Protocol.HTTP_2) {
                    ackSettingsLater(settings);
                }
                int initialWindowSize2 = FramedConnection.this.peerSettings.getInitialWindowSize(65536);
                framedStreamArr = null;
                if (initialWindowSize2 == -1 || initialWindowSize2 == initialWindowSize) {
                    j = 0;
                } else {
                    j = (long) (initialWindowSize2 - initialWindowSize);
                    if (!FramedConnection.this.receivedInitialPeerSettings) {
                        FramedConnection.this.addBytesToWriteWindow(j);
                        FramedConnection.this.receivedInitialPeerSettings = true;
                    }
                    if (!FramedConnection.this.streams.isEmpty()) {
                        framedStreamArr = (FramedStream[]) FramedConnection.this.streams.values().toArray(new FramedStream[FramedConnection.this.streams.size()]);
                    }
                }
                ExecutorService access$2100 = FramedConnection.executor;
                Object[] objArr = new Object[1];
                i = 0;
                objArr[0] = FramedConnection.this.hostName;
                access$2100.execute(new NamedRunnable("OkHttp %s settings", objArr) {
                    public void execute() {
                        FramedConnection.this.listener.onSettings(FramedConnection.this);
                    }
                });
            }
            if (framedStreamArr != null && j != 0) {
                int length = framedStreamArr.length;
                while (i < length) {
                    FramedStream framedStream = framedStreamArr[i];
                    synchronized (framedStream) {
                        framedStream.addBytesToWriteWindow(j);
                    }
                    i++;
                }
            }
        }

        private void ackSettingsLater(final Settings settings) {
            FramedConnection.executor.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{FramedConnection.this.hostName}) {
                public void execute() {
                    try {
                        FramedConnection.this.frameWriter.ackSettings(settings);
                    } catch (IOException unused) {
                    }
                }
            });
        }

        public void ping(boolean z, int i, int i2) {
            if (z) {
                Ping access$2400 = FramedConnection.this.removePing(i);
                if (access$2400 != null) {
                    access$2400.receive();
                    return;
                }
                return;
            }
            FramedConnection.this.writePingLater(true, i, i2, null);
        }

        public void goAway(int i, ErrorCode errorCode, ByteString byteString) {
            byteString.size();
            synchronized (FramedConnection.this) {
                FramedStream[] framedStreamArr = (FramedStream[]) FramedConnection.this.streams.values().toArray(new FramedStream[FramedConnection.this.streams.size()]);
                FramedConnection.this.shutdown = true;
            }
            for (FramedStream framedStream : framedStreamArr) {
                if (framedStream.getId() > i && framedStream.isLocallyInitiated()) {
                    framedStream.receiveRstStream(ErrorCode.REFUSED_STREAM);
                    FramedConnection.this.removeStream(framedStream.getId());
                }
            }
        }

        public void windowUpdate(int i, long j) {
            if (i == 0) {
                synchronized (FramedConnection.this) {
                    FramedConnection framedConnection = FramedConnection.this;
                    framedConnection.bytesLeftInWriteWindow += j;
                    FramedConnection.this.notifyAll();
                }
                return;
            }
            FramedStream stream = FramedConnection.this.getStream(i);
            if (stream != null) {
                synchronized (stream) {
                    stream.addBytesToWriteWindow(j);
                }
            }
        }

        public void pushPromise(int i, int i2, List<Header> list) {
            FramedConnection.this.pushRequestLater(i2, list);
        }
    }

    /* synthetic */ FramedConnection(Builder builder, AnonymousClass1 anonymousClass1) throws IOException {
        this(builder);
    }

    private FramedConnection(Builder builder) throws IOException {
        this.streams = new HashMap();
        this.idleStartTimeNs = System.nanoTime();
        this.unacknowledgedBytesRead = 0;
        this.okHttpSettings = new Settings();
        this.peerSettings = new Settings();
        this.receivedInitialPeerSettings = false;
        this.currentPushRequests = new LinkedHashSet();
        this.protocol = builder.protocol;
        this.pushObserver = builder.pushObserver;
        this.client = builder.client;
        this.listener = builder.listener;
        int i = 2;
        this.nextStreamId = builder.client ? 1 : 2;
        if (builder.client && this.protocol == Protocol.HTTP_2) {
            this.nextStreamId += 2;
        }
        if (builder.client) {
            i = 1;
        }
        this.nextPingId = i;
        if (builder.client) {
            this.okHttpSettings.set(7, 0, 16777216);
        }
        this.hostName = builder.hostName;
        if (this.protocol == Protocol.HTTP_2) {
            this.variant = new Http2();
            this.pushExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(String.format("OkHttp %s Push Observer", new Object[]{this.hostName}), true));
            this.peerSettings.set(7, 0, 65535);
            this.peerSettings.set(5, 0, 16384);
        } else if (this.protocol == Protocol.SPDY_3) {
            this.variant = new Spdy3();
            this.pushExecutor = null;
        } else {
            throw new AssertionError(this.protocol);
        }
        this.bytesLeftInWriteWindow = (long) this.peerSettings.getInitialWindowSize(65536);
        this.socket = builder.socket;
        this.frameWriter = this.variant.newWriter(builder.sink, this.client);
        this.readerRunnable = new Reader(this, this.variant.newReader(builder.source, this.client), null);
        new Thread(this.readerRunnable).start();
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public synchronized int openStreamCount() {
        return this.streams.size();
    }

    synchronized FramedStream getStream(int i) {
        return (FramedStream) this.streams.get(Integer.valueOf(i));
    }

    synchronized FramedStream removeStream(int i) {
        FramedStream framedStream;
        framedStream = (FramedStream) this.streams.remove(Integer.valueOf(i));
        if (framedStream != null && this.streams.isEmpty()) {
            setIdle(true);
        }
        notifyAll();
        return framedStream;
    }

    private synchronized void setIdle(boolean z) {
        this.idleStartTimeNs = z ? System.nanoTime() : Long.MAX_VALUE;
    }

    public synchronized boolean isIdle() {
        return this.idleStartTimeNs != Long.MAX_VALUE;
    }

    public synchronized int maxConcurrentStreams() {
        return this.peerSettings.getMaxConcurrentStreams(Integer.MAX_VALUE);
    }

    public synchronized long getIdleStartTimeNs() {
        return this.idleStartTimeNs;
    }

    public FramedStream pushStream(int i, List<Header> list, boolean z) throws IOException {
        if (this.client) {
            throw new IllegalStateException("Client cannot push requests.");
        } else if (this.protocol == Protocol.HTTP_2) {
            return newStream(i, list, z, false);
        } else {
            throw new IllegalStateException("protocol != HTTP_2");
        }
    }

    public FramedStream newStream(List<Header> list, boolean z, boolean z2) throws IOException {
        return newStream(0, list, z, z2);
    }

    private FramedStream newStream(int i, List<Header> list, boolean z, boolean z2) throws IOException {
        FramedStream framedStream;
        boolean z3 = z ^ 1;
        z2 ^= 1;
        synchronized (this.frameWriter) {
            int i2;
            synchronized (this) {
                if (this.shutdown) {
                    throw new IOException("shutdown");
                }
                i2 = this.nextStreamId;
                this.nextStreamId += 2;
                framedStream = new FramedStream(i2, this, z3, z2, list);
                if (framedStream.isOpen()) {
                    this.streams.put(Integer.valueOf(i2), framedStream);
                    setIdle(false);
                }
            }
            if (i == 0) {
                this.frameWriter.synStream(z3, z2, i2, i, list);
            } else if (this.client) {
                throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
            } else {
                this.frameWriter.pushPromise(i, i2, list);
            }
        }
        if (!z) {
            this.frameWriter.flush();
        }
        return framedStream;
    }

    void writeSynReply(int i, boolean z, List<Header> list) throws IOException {
        this.frameWriter.synReply(z, i, list);
    }

    /* JADX WARNING: Missing block: B:16:?, code:
            r3 = java.lang.Math.min((int) java.lang.Math.min(r12, r8.bytesLeftInWriteWindow), r8.frameWriter.maxDataLength());
            r6 = (long) r3;
            r8.bytesLeftInWriteWindow -= r6;
     */
    /* JADX WARNING: Missing block: B:28:0x005f, code:
            throw new java.io.InterruptedIOException();
     */
    public void writeData(int r9, boolean r10, okio.Buffer r11, long r12) throws java.io.IOException {
        /*
        r8 = this;
        r0 = 0;
        r1 = 0;
        r3 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1));
        if (r3 != 0) goto L_0x000d;
    L_0x0007:
        r12 = r8.frameWriter;
        r12.data(r10, r9, r11, r0);
        return;
    L_0x000d:
        r3 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1));
        if (r3 <= 0) goto L_0x0062;
    L_0x0011:
        monitor-enter(r8);
    L_0x0012:
        r3 = r8.bytesLeftInWriteWindow;	 Catch:{ InterruptedException -> 0x005a }
        r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1));
        if (r5 > 0) goto L_0x0030;
    L_0x0018:
        r3 = r8.streams;	 Catch:{ InterruptedException -> 0x005a }
        r4 = java.lang.Integer.valueOf(r9);	 Catch:{ InterruptedException -> 0x005a }
        r3 = r3.containsKey(r4);	 Catch:{ InterruptedException -> 0x005a }
        if (r3 == 0) goto L_0x0028;
    L_0x0024:
        r8.wait();	 Catch:{ InterruptedException -> 0x005a }
        goto L_0x0012;
    L_0x0028:
        r9 = new java.io.IOException;	 Catch:{ InterruptedException -> 0x005a }
        r10 = "stream closed";
        r9.<init>(r10);	 Catch:{ InterruptedException -> 0x005a }
        throw r9;	 Catch:{ InterruptedException -> 0x005a }
    L_0x0030:
        r3 = r8.bytesLeftInWriteWindow;	 Catch:{ all -> 0x0058 }
        r3 = java.lang.Math.min(r12, r3);	 Catch:{ all -> 0x0058 }
        r4 = (int) r3;	 Catch:{ all -> 0x0058 }
        r3 = r8.frameWriter;	 Catch:{ all -> 0x0058 }
        r3 = r3.maxDataLength();	 Catch:{ all -> 0x0058 }
        r3 = java.lang.Math.min(r4, r3);	 Catch:{ all -> 0x0058 }
        r4 = r8.bytesLeftInWriteWindow;	 Catch:{ all -> 0x0058 }
        r6 = (long) r3;	 Catch:{ all -> 0x0058 }
        r4 = r4 - r6;
        r8.bytesLeftInWriteWindow = r4;	 Catch:{ all -> 0x0058 }
        monitor-exit(r8);	 Catch:{ all -> 0x0058 }
        r12 = r12 - r6;
        r4 = r8.frameWriter;
        if (r10 == 0) goto L_0x0053;
    L_0x004d:
        r5 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1));
        if (r5 != 0) goto L_0x0053;
    L_0x0051:
        r5 = 1;
        goto L_0x0054;
    L_0x0053:
        r5 = 0;
    L_0x0054:
        r4.data(r5, r9, r11, r3);
        goto L_0x000d;
    L_0x0058:
        r9 = move-exception;
        goto L_0x0060;
    L_0x005a:
        r9 = new java.io.InterruptedIOException;	 Catch:{ all -> 0x0058 }
        r9.<init>();	 Catch:{ all -> 0x0058 }
        throw r9;	 Catch:{ all -> 0x0058 }
    L_0x0060:
        monitor-exit(r8);	 Catch:{ all -> 0x0058 }
        throw r9;
    L_0x0062:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.FramedConnection.writeData(int, boolean, okio.Buffer, long):void");
    }

    void addBytesToWriteWindow(long j) {
        this.bytesLeftInWriteWindow += j;
        if (j > 0) {
            notifyAll();
        }
    }

    void writeSynResetLater(int i, ErrorCode errorCode) {
        final int i2 = i;
        final ErrorCode errorCode2 = errorCode;
        executor.submit(new NamedRunnable("OkHttp %s stream %d", new Object[]{this.hostName, Integer.valueOf(i)}) {
            public void execute() {
                try {
                    FramedConnection.this.writeSynReset(i2, errorCode2);
                } catch (IOException unused) {
                }
            }
        });
    }

    void writeSynReset(int i, ErrorCode errorCode) throws IOException {
        this.frameWriter.rstStream(i, errorCode);
    }

    void writeWindowUpdateLater(int i, long j) {
        final int i2 = i;
        final long j2 = j;
        executor.execute(new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.hostName, Integer.valueOf(i)}) {
            public void execute() {
                try {
                    FramedConnection.this.frameWriter.windowUpdate(i2, j2);
                } catch (IOException unused) {
                }
            }
        });
    }

    public Ping ping() throws IOException {
        int i;
        Ping ping = new Ping();
        synchronized (this) {
            if (this.shutdown) {
                throw new IOException("shutdown");
            }
            i = this.nextPingId;
            this.nextPingId += 2;
            if (this.pings == null) {
                this.pings = new HashMap();
            }
            this.pings.put(Integer.valueOf(i), ping);
        }
        writePing(false, i, 1330343787, ping);
        return ping;
    }

    private void writePingLater(boolean z, int i, int i2, Ping ping) {
        final boolean z2 = z;
        final int i3 = i;
        final int i4 = i2;
        final Ping ping2 = ping;
        executor.execute(new NamedRunnable("OkHttp %s ping %08x%08x", new Object[]{this.hostName, Integer.valueOf(i), Integer.valueOf(i2)}) {
            public void execute() {
                try {
                    FramedConnection.this.writePing(z2, i3, i4, ping2);
                } catch (IOException unused) {
                }
            }
        });
    }

    private void writePing(boolean z, int i, int i2, Ping ping) throws IOException {
        synchronized (this.frameWriter) {
            if (ping != null) {
                ping.send();
            }
            this.frameWriter.ping(z, i, i2);
        }
    }

    private synchronized Ping removePing(int i) {
        return this.pings != null ? (Ping) this.pings.remove(Integer.valueOf(i)) : null;
    }

    public void flush() throws IOException {
        this.frameWriter.flush();
    }

    public void shutdown(ErrorCode errorCode) throws IOException {
        synchronized (this.frameWriter) {
            synchronized (this) {
                if (this.shutdown) {
                    return;
                }
                this.shutdown = true;
                int i = this.lastGoodStreamId;
                this.frameWriter.goAway(i, errorCode, Util.EMPTY_BYTE_ARRAY);
            }
        }
    }

    public void close() throws IOException {
        close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    private void close(ErrorCode errorCode, ErrorCode errorCode2) throws IOException {
        IOException e;
        int i;
        FramedStream[] framedStreamArr;
        Map map = null;
        try {
            shutdown(errorCode);
            e = null;
        } catch (IOException e2) {
            e = e2;
        }
        synchronized (this) {
            i = 0;
            if (this.streams.isEmpty()) {
                framedStreamArr = null;
            } else {
                framedStreamArr = (FramedStream[]) this.streams.values().toArray(new FramedStream[this.streams.size()]);
                this.streams.clear();
                setIdle(false);
            }
            if (this.pings != null) {
                Ping[] pingArr = (Ping[]) this.pings.values().toArray(new Ping[this.pings.size()]);
                this.pings = null;
                map = pingArr;
            }
        }
        if (framedStreamArr != null) {
            IOException iOException = e;
            for (FramedStream close : framedStreamArr) {
                try {
                    close.close(errorCode2);
                } catch (IOException e3) {
                    if (iOException != null) {
                        iOException = e3;
                    }
                }
            }
            e = iOException;
        }
        if (map != null) {
            int length = map.length;
            while (i < length) {
                map[i].cancel();
                i++;
            }
        }
        try {
            this.frameWriter.close();
        } catch (IOException e4) {
            if (e == null) {
                e = e4;
            }
        }
        try {
            this.socket.close();
        } catch (IOException e5) {
            e = e5;
        }
        if (e != null) {
            throw e;
        }
    }

    public void sendConnectionPreface() throws IOException {
        this.frameWriter.connectionPreface();
        this.frameWriter.settings(this.okHttpSettings);
        int initialWindowSize = this.okHttpSettings.getInitialWindowSize(65536);
        if (initialWindowSize != 65536) {
            this.frameWriter.windowUpdate(0, (long) (initialWindowSize - 65536));
        }
    }

    public void setSettings(Settings settings) throws IOException {
        synchronized (this.frameWriter) {
            synchronized (this) {
                if (this.shutdown) {
                    throw new IOException("shutdown");
                }
                this.okHttpSettings.merge(settings);
                this.frameWriter.settings(settings);
            }
        }
    }

    private boolean pushedStream(int i) {
        return this.protocol == Protocol.HTTP_2 && i != 0 && (i & 1) == 0;
    }

    private void pushRequestLater(int i, List<Header> list) {
        synchronized (this) {
            if (this.currentPushRequests.contains(Integer.valueOf(i))) {
                writeSynResetLater(i, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.currentPushRequests.add(Integer.valueOf(i));
            final int i2 = i;
            final List<Header> list2 = list;
            this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Request[%s]", new Object[]{this.hostName, Integer.valueOf(i)}) {
                public void execute() {
                    if (FramedConnection.this.pushObserver.onRequest(i2, list2)) {
                        try {
                            FramedConnection.this.frameWriter.rstStream(i2, ErrorCode.CANCEL);
                            synchronized (FramedConnection.this) {
                                FramedConnection.this.currentPushRequests.remove(Integer.valueOf(i2));
                            }
                        } catch (IOException unused) {
                        }
                    }
                }
            });
        }
    }

    private void pushHeadersLater(int i, List<Header> list, boolean z) {
        final int i2 = i;
        final List<Header> list2 = list;
        final boolean z2 = z;
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.hostName, Integer.valueOf(i)}) {
            public void execute() {
                boolean onHeaders = FramedConnection.this.pushObserver.onHeaders(i2, list2, z2);
                if (onHeaders) {
                    try {
                        FramedConnection.this.frameWriter.rstStream(i2, ErrorCode.CANCEL);
                    } catch (IOException unused) {
                    }
                }
                if (onHeaders || z2) {
                    synchronized (FramedConnection.this) {
                        FramedConnection.this.currentPushRequests.remove(Integer.valueOf(i2));
                    }
                }
            }
        });
    }

    private void pushDataLater(int i, BufferedSource bufferedSource, int i2, boolean z) throws IOException {
        final Buffer buffer = new Buffer();
        long j = (long) i2;
        bufferedSource.require(j);
        bufferedSource.read(buffer, j);
        if (buffer.size() == j) {
            final int i3 = i;
            final int i4 = i2;
            final boolean z2 = z;
            this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.hostName, Integer.valueOf(i)}) {
                public void execute() {
                    try {
                        boolean onData = FramedConnection.this.pushObserver.onData(i3, buffer, i4, z2);
                        if (onData) {
                            FramedConnection.this.frameWriter.rstStream(i3, ErrorCode.CANCEL);
                        }
                        if (onData || z2) {
                            synchronized (FramedConnection.this) {
                                FramedConnection.this.currentPushRequests.remove(Integer.valueOf(i3));
                            }
                        }
                    } catch (IOException unused) {
                    }
                }
            });
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(buffer.size());
        stringBuilder.append(" != ");
        stringBuilder.append(i2);
        throw new IOException(stringBuilder.toString());
    }

    private void pushResetLater(int i, ErrorCode errorCode) {
        final int i2 = i;
        final ErrorCode errorCode2 = errorCode;
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.hostName, Integer.valueOf(i)}) {
            public void execute() {
                FramedConnection.this.pushObserver.onReset(i2, errorCode2);
                synchronized (FramedConnection.this) {
                    FramedConnection.this.currentPushRequests.remove(Integer.valueOf(i2));
                }
            }
        });
    }
}
