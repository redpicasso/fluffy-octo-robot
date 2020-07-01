package io.grpc.inprocess;

import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.Compressor;
import io.grpc.Deadline;
import io.grpc.Decompressor;
import io.grpc.DecompressorRegistry;
import io.grpc.Grpc;
import io.grpc.InternalChannelz.SocketStats;
import io.grpc.InternalLogId;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.SecurityLevel;
import io.grpc.ServerStreamTracer.Factory;
import io.grpc.Status;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.ClientTransport.PingCallback;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.GrpcAttributes;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.ManagedClientTransport.Listener;
import io.grpc.internal.NoopClientStream;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.ServerStream;
import io.grpc.internal.ServerStreamListener;
import io.grpc.internal.ServerTransport;
import io.grpc.internal.ServerTransportListener;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.StreamListener.MessageProducer;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
final class InProcessTransport implements ServerTransport, ConnectionClientTransport {
    private static final Logger log = Logger.getLogger(InProcessTransport.class.getName());
    private final Attributes attributes = Attributes.newBuilder().set(GrpcAttributes.ATTR_SECURITY_LEVEL, SecurityLevel.PRIVACY_AND_INTEGRITY).build();
    private final String authority;
    private Listener clientTransportListener;
    private final InternalLogId logId = InternalLogId.allocate(getClass().getName());
    private final String name;
    private ScheduledExecutorService serverScheduler;
    private ObjectPool<ScheduledExecutorService> serverSchedulerPool;
    private Attributes serverStreamAttributes;
    @GuardedBy("this")
    private List<Factory> serverStreamTracerFactories;
    private ServerTransportListener serverTransportListener;
    @GuardedBy("this")
    private boolean shutdown;
    @GuardedBy("this")
    private Status shutdownStatus;
    @GuardedBy("this")
    private Set<InProcessStream> streams = new HashSet();
    @GuardedBy("this")
    private boolean terminated;
    private final String userAgent;

    private class InProcessStream {
        private volatile String authority;
        private final InProcessClientStream clientStream;
        private final Metadata headers;
        private final MethodDescriptor<?, ?> method;
        private final InProcessServerStream serverStream;

        private class InProcessClientStream implements ClientStream {
            @GuardedBy("this")
            private boolean closed;
            @GuardedBy("this")
            private int outboundSeqNo;
            @GuardedBy("this")
            private boolean serverNotifyHalfClose;
            @GuardedBy("this")
            private ArrayDeque<MessageProducer> serverReceiveQueue = new ArrayDeque();
            @GuardedBy("this")
            private int serverRequested;
            @GuardedBy("this")
            private ServerStreamListener serverStreamListener;
            final StatsTraceContext statsTraceCtx;

            public void flush() {
            }

            public void setCompressor(Compressor compressor) {
            }

            public void setDecompressorRegistry(DecompressorRegistry decompressorRegistry) {
            }

            public void setFullStreamDecompression(boolean z) {
            }

            public void setMaxInboundMessageSize(int i) {
            }

            public void setMaxOutboundMessageSize(int i) {
            }

            public void setMessageCompression(boolean z) {
            }

            InProcessClientStream(CallOptions callOptions, Metadata metadata) {
                this.statsTraceCtx = StatsTraceContext.newClientContext(callOptions, metadata);
            }

            private synchronized void setListener(ServerStreamListener serverStreamListener) {
                this.serverStreamListener = serverStreamListener;
            }

            public void request(int i) {
                if (InProcessStream.this.serverStream.clientRequested(i)) {
                    synchronized (this) {
                        if (!this.closed) {
                            this.serverStreamListener.onReady();
                        }
                    }
                }
            }

            /* JADX WARNING: Missing block: B:31:0x0054, code:
            return r1;
     */
            private synchronized boolean serverRequested(int r5) {
                /*
                r4 = this;
                monitor-enter(r4);
                r0 = r4.closed;	 Catch:{ all -> 0x0055 }
                r1 = 0;
                if (r0 == 0) goto L_0x0008;
            L_0x0006:
                monitor-exit(r4);
                return r1;
            L_0x0008:
                r0 = r4.serverRequested;	 Catch:{ all -> 0x0055 }
                r2 = 1;
                if (r0 <= 0) goto L_0x000f;
            L_0x000d:
                r0 = 1;
                goto L_0x0010;
            L_0x000f:
                r0 = 0;
            L_0x0010:
                r3 = r4.serverRequested;	 Catch:{ all -> 0x0055 }
                r3 = r3 + r5;
                r4.serverRequested = r3;	 Catch:{ all -> 0x0055 }
            L_0x0015:
                r5 = r4.serverRequested;	 Catch:{ all -> 0x0055 }
                if (r5 <= 0) goto L_0x0034;
            L_0x0019:
                r5 = r4.serverReceiveQueue;	 Catch:{ all -> 0x0055 }
                r5 = r5.isEmpty();	 Catch:{ all -> 0x0055 }
                if (r5 != 0) goto L_0x0034;
            L_0x0021:
                r5 = r4.serverRequested;	 Catch:{ all -> 0x0055 }
                r5 = r5 - r2;
                r4.serverRequested = r5;	 Catch:{ all -> 0x0055 }
                r5 = r4.serverStreamListener;	 Catch:{ all -> 0x0055 }
                r3 = r4.serverReceiveQueue;	 Catch:{ all -> 0x0055 }
                r3 = r3.poll();	 Catch:{ all -> 0x0055 }
                r3 = (io.grpc.internal.StreamListener.MessageProducer) r3;	 Catch:{ all -> 0x0055 }
                r5.messagesAvailable(r3);	 Catch:{ all -> 0x0055 }
                goto L_0x0015;
            L_0x0034:
                r5 = r4.serverReceiveQueue;	 Catch:{ all -> 0x0055 }
                r5 = r5.isEmpty();	 Catch:{ all -> 0x0055 }
                if (r5 == 0) goto L_0x0047;
            L_0x003c:
                r5 = r4.serverNotifyHalfClose;	 Catch:{ all -> 0x0055 }
                if (r5 == 0) goto L_0x0047;
            L_0x0040:
                r4.serverNotifyHalfClose = r1;	 Catch:{ all -> 0x0055 }
                r5 = r4.serverStreamListener;	 Catch:{ all -> 0x0055 }
                r5.halfClosed();	 Catch:{ all -> 0x0055 }
            L_0x0047:
                r5 = r4.serverRequested;	 Catch:{ all -> 0x0055 }
                if (r5 <= 0) goto L_0x004d;
            L_0x004b:
                r5 = 1;
                goto L_0x004e;
            L_0x004d:
                r5 = 0;
            L_0x004e:
                if (r0 != 0) goto L_0x0053;
            L_0x0050:
                if (r5 == 0) goto L_0x0053;
            L_0x0052:
                r1 = 1;
            L_0x0053:
                monitor-exit(r4);
                return r1;
            L_0x0055:
                r5 = move-exception;
                monitor-exit(r4);
                throw r5;
                */
                throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.InProcessStream.InProcessClientStream.serverRequested(int):boolean");
            }

            private void serverClosed(Status status, Status status2) {
                internalCancel(status, status2);
            }

            /* JADX WARNING: Missing block: B:12:0x0059, code:
            return;
     */
            public synchronized void writeMessage(java.io.InputStream r9) {
                /*
                r8 = this;
                monitor-enter(r8);
                r0 = r8.closed;	 Catch:{ all -> 0x005a }
                if (r0 == 0) goto L_0x0007;
            L_0x0005:
                monitor-exit(r8);
                return;
            L_0x0007:
                r0 = r8.statsTraceCtx;	 Catch:{ all -> 0x005a }
                r1 = r8.outboundSeqNo;	 Catch:{ all -> 0x005a }
                r0.outboundMessage(r1);	 Catch:{ all -> 0x005a }
                r2 = r8.statsTraceCtx;	 Catch:{ all -> 0x005a }
                r3 = r8.outboundSeqNo;	 Catch:{ all -> 0x005a }
                r4 = -1;
                r6 = -1;
                r2.outboundMessageSent(r3, r4, r6);	 Catch:{ all -> 0x005a }
                r0 = io.grpc.inprocess.InProcessTransport.InProcessStream.this;	 Catch:{ all -> 0x005a }
                r0 = r0.serverStream;	 Catch:{ all -> 0x005a }
                r0 = r0.statsTraceCtx;	 Catch:{ all -> 0x005a }
                r1 = r8.outboundSeqNo;	 Catch:{ all -> 0x005a }
                r0.inboundMessage(r1);	 Catch:{ all -> 0x005a }
                r0 = io.grpc.inprocess.InProcessTransport.InProcessStream.this;	 Catch:{ all -> 0x005a }
                r0 = r0.serverStream;	 Catch:{ all -> 0x005a }
                r1 = r0.statsTraceCtx;	 Catch:{ all -> 0x005a }
                r2 = r8.outboundSeqNo;	 Catch:{ all -> 0x005a }
                r3 = -1;
                r5 = -1;
                r1.inboundMessageRead(r2, r3, r5);	 Catch:{ all -> 0x005a }
                r0 = r8.outboundSeqNo;	 Catch:{ all -> 0x005a }
                r0 = r0 + 1;
                r8.outboundSeqNo = r0;	 Catch:{ all -> 0x005a }
                r0 = new io.grpc.inprocess.InProcessTransport$SingleMessageProducer;	 Catch:{ all -> 0x005a }
                r1 = 0;
                r0.<init>(r9, r1);	 Catch:{ all -> 0x005a }
                r9 = r8.serverRequested;	 Catch:{ all -> 0x005a }
                if (r9 <= 0) goto L_0x0053;
            L_0x0047:
                r9 = r8.serverRequested;	 Catch:{ all -> 0x005a }
                r9 = r9 + -1;
                r8.serverRequested = r9;	 Catch:{ all -> 0x005a }
                r9 = r8.serverStreamListener;	 Catch:{ all -> 0x005a }
                r9.messagesAvailable(r0);	 Catch:{ all -> 0x005a }
                goto L_0x0058;
            L_0x0053:
                r9 = r8.serverReceiveQueue;	 Catch:{ all -> 0x005a }
                r9.add(r0);	 Catch:{ all -> 0x005a }
            L_0x0058:
                monitor-exit(r8);
                return;
            L_0x005a:
                r9 = move-exception;
                monitor-exit(r8);
                throw r9;
                */
                throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.InProcessStream.InProcessClientStream.writeMessage(java.io.InputStream):void");
            }

            /* JADX WARNING: Missing block: B:12:0x000e, code:
            return r1;
     */
            public synchronized boolean isReady() {
                /*
                r2 = this;
                monitor-enter(r2);
                r0 = r2.closed;	 Catch:{ all -> 0x000f }
                r1 = 0;
                if (r0 == 0) goto L_0x0008;
            L_0x0006:
                monitor-exit(r2);
                return r1;
            L_0x0008:
                r0 = r2.serverRequested;	 Catch:{ all -> 0x000f }
                if (r0 <= 0) goto L_0x000d;
            L_0x000c:
                r1 = 1;
            L_0x000d:
                monitor-exit(r2);
                return r1;
            L_0x000f:
                r0 = move-exception;
                monitor-exit(r2);
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.InProcessStream.InProcessClientStream.isReady():boolean");
            }

            public void cancel(Status status) {
                Status access$1500 = InProcessTransport.stripCause(status);
                if (internalCancel(access$1500, access$1500)) {
                    InProcessStream.this.serverStream.clientCancelled(status);
                    InProcessStream.this.streamClosed();
                }
            }

            private synchronized boolean internalCancel(Status status, Status status2) {
                if (this.closed) {
                    return false;
                }
                this.closed = true;
                while (true) {
                    MessageProducer messageProducer = (MessageProducer) this.serverReceiveQueue.poll();
                    if (messageProducer != null) {
                        while (true) {
                            InputStream next = messageProducer.next();
                            if (next != null) {
                                try {
                                    next.close();
                                } catch (Throwable th) {
                                    InProcessTransport.log.log(Level.WARNING, "Exception closing stream", th);
                                }
                            }
                        }
                    } else {
                        InProcessStream.this.serverStream.statsTraceCtx.streamClosed(status2);
                        this.serverStreamListener.closed(status);
                        return true;
                    }
                }
            }

            /* JADX WARNING: Missing block: B:12:0x0019, code:
            return;
     */
            public synchronized void halfClose() {
                /*
                r1 = this;
                monitor-enter(r1);
                r0 = r1.closed;	 Catch:{ all -> 0x001a }
                if (r0 == 0) goto L_0x0007;
            L_0x0005:
                monitor-exit(r1);
                return;
            L_0x0007:
                r0 = r1.serverReceiveQueue;	 Catch:{ all -> 0x001a }
                r0 = r0.isEmpty();	 Catch:{ all -> 0x001a }
                if (r0 == 0) goto L_0x0015;
            L_0x000f:
                r0 = r1.serverStreamListener;	 Catch:{ all -> 0x001a }
                r0.halfClosed();	 Catch:{ all -> 0x001a }
                goto L_0x0018;
            L_0x0015:
                r0 = 1;
                r1.serverNotifyHalfClose = r0;	 Catch:{ all -> 0x001a }
            L_0x0018:
                monitor-exit(r1);
                return;
            L_0x001a:
                r0 = move-exception;
                monitor-exit(r1);
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.InProcessStream.InProcessClientStream.halfClose():void");
            }

            public void setAuthority(String str) {
                InProcessStream.this.authority = str;
            }

            public void start(ClientStreamListener clientStreamListener) {
                InProcessStream.this.serverStream.setListener(clientStreamListener);
                synchronized (InProcessTransport.this) {
                    this.statsTraceCtx.clientOutboundHeaders();
                    InProcessTransport.this.streams.add(InProcessStream.this);
                    if (InProcessTransport.this.streams.size() == 1) {
                        InProcessTransport.this.clientTransportListener.transportInUse(true);
                    }
                    InProcessTransport.this.serverTransportListener.streamCreated(InProcessStream.this.serverStream, InProcessStream.this.method.getFullMethodName(), InProcessStream.this.headers);
                }
            }

            public Attributes getAttributes() {
                return Attributes.EMPTY;
            }

            public void setDeadline(Deadline deadline) {
                InProcessStream.this.headers.discardAll(GrpcUtil.TIMEOUT_KEY);
                InProcessStream.this.headers.put(GrpcUtil.TIMEOUT_KEY, Long.valueOf(Math.max(0, deadline.timeRemaining(TimeUnit.NANOSECONDS))));
            }
        }

        private class InProcessServerStream implements ServerStream {
            @GuardedBy("this")
            private Status clientNotifyStatus;
            @GuardedBy("this")
            private Metadata clientNotifyTrailers;
            @GuardedBy("this")
            private ArrayDeque<MessageProducer> clientReceiveQueue = new ArrayDeque();
            @GuardedBy("this")
            private int clientRequested;
            @GuardedBy("this")
            private ClientStreamListener clientStreamListener;
            @GuardedBy("this")
            private boolean closed;
            @GuardedBy("this")
            private int outboundSeqNo;
            final StatsTraceContext statsTraceCtx;

            public void flush() {
            }

            public void setCompressor(Compressor compressor) {
            }

            public void setDecompressor(Decompressor decompressor) {
            }

            public void setMessageCompression(boolean z) {
            }

            InProcessServerStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata) {
                this.statsTraceCtx = StatsTraceContext.newServerContext(InProcessTransport.this.serverStreamTracerFactories, methodDescriptor.getFullMethodName(), metadata);
            }

            private synchronized void setListener(ClientStreamListener clientStreamListener) {
                this.clientStreamListener = clientStreamListener;
            }

            public void setListener(ServerStreamListener serverStreamListener) {
                InProcessStream.this.clientStream.setListener(serverStreamListener);
            }

            public void request(int i) {
                if (InProcessStream.this.clientStream.serverRequested(i)) {
                    synchronized (this) {
                        if (!this.closed) {
                            this.clientStreamListener.onReady();
                        }
                    }
                }
            }

            /* JADX WARNING: Missing block: B:36:0x006b, code:
            return r1;
     */
            private synchronized boolean clientRequested(int r6) {
                /*
                r5 = this;
                monitor-enter(r5);
                r0 = r5.closed;	 Catch:{ all -> 0x006c }
                r1 = 0;
                if (r0 == 0) goto L_0x0008;
            L_0x0006:
                monitor-exit(r5);
                return r1;
            L_0x0008:
                r0 = r5.clientRequested;	 Catch:{ all -> 0x006c }
                r2 = 1;
                if (r0 <= 0) goto L_0x000f;
            L_0x000d:
                r0 = 1;
                goto L_0x0010;
            L_0x000f:
                r0 = 0;
            L_0x0010:
                r3 = r5.clientRequested;	 Catch:{ all -> 0x006c }
                r3 = r3 + r6;
                r5.clientRequested = r3;	 Catch:{ all -> 0x006c }
            L_0x0015:
                r6 = r5.clientRequested;	 Catch:{ all -> 0x006c }
                if (r6 <= 0) goto L_0x0034;
            L_0x0019:
                r6 = r5.clientReceiveQueue;	 Catch:{ all -> 0x006c }
                r6 = r6.isEmpty();	 Catch:{ all -> 0x006c }
                if (r6 != 0) goto L_0x0034;
            L_0x0021:
                r6 = r5.clientRequested;	 Catch:{ all -> 0x006c }
                r6 = r6 - r2;
                r5.clientRequested = r6;	 Catch:{ all -> 0x006c }
                r6 = r5.clientStreamListener;	 Catch:{ all -> 0x006c }
                r3 = r5.clientReceiveQueue;	 Catch:{ all -> 0x006c }
                r3 = r3.poll();	 Catch:{ all -> 0x006c }
                r3 = (io.grpc.internal.StreamListener.MessageProducer) r3;	 Catch:{ all -> 0x006c }
                r6.messagesAvailable(r3);	 Catch:{ all -> 0x006c }
                goto L_0x0015;
            L_0x0034:
                r6 = r5.closed;	 Catch:{ all -> 0x006c }
                if (r6 == 0) goto L_0x003a;
            L_0x0038:
                monitor-exit(r5);
                return r1;
            L_0x003a:
                r6 = r5.clientReceiveQueue;	 Catch:{ all -> 0x006c }
                r6 = r6.isEmpty();	 Catch:{ all -> 0x006c }
                if (r6 == 0) goto L_0x005e;
            L_0x0042:
                r6 = r5.clientNotifyStatus;	 Catch:{ all -> 0x006c }
                if (r6 == 0) goto L_0x005e;
            L_0x0046:
                r5.closed = r2;	 Catch:{ all -> 0x006c }
                r6 = io.grpc.inprocess.InProcessTransport.InProcessStream.this;	 Catch:{ all -> 0x006c }
                r6 = r6.clientStream;	 Catch:{ all -> 0x006c }
                r6 = r6.statsTraceCtx;	 Catch:{ all -> 0x006c }
                r3 = r5.clientNotifyStatus;	 Catch:{ all -> 0x006c }
                r6.streamClosed(r3);	 Catch:{ all -> 0x006c }
                r6 = r5.clientStreamListener;	 Catch:{ all -> 0x006c }
                r3 = r5.clientNotifyStatus;	 Catch:{ all -> 0x006c }
                r4 = r5.clientNotifyTrailers;	 Catch:{ all -> 0x006c }
                r6.closed(r3, r4);	 Catch:{ all -> 0x006c }
            L_0x005e:
                r6 = r5.clientRequested;	 Catch:{ all -> 0x006c }
                if (r6 <= 0) goto L_0x0064;
            L_0x0062:
                r6 = 1;
                goto L_0x0065;
            L_0x0064:
                r6 = 0;
            L_0x0065:
                if (r0 != 0) goto L_0x006a;
            L_0x0067:
                if (r6 == 0) goto L_0x006a;
            L_0x0069:
                r1 = 1;
            L_0x006a:
                monitor-exit(r5);
                return r1;
            L_0x006c:
                r6 = move-exception;
                monitor-exit(r5);
                throw r6;
                */
                throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.InProcessStream.InProcessServerStream.clientRequested(int):boolean");
            }

            private void clientCancelled(Status status) {
                internalCancel(status);
            }

            /* JADX WARNING: Missing block: B:12:0x0059, code:
            return;
     */
            public synchronized void writeMessage(java.io.InputStream r9) {
                /*
                r8 = this;
                monitor-enter(r8);
                r0 = r8.closed;	 Catch:{ all -> 0x005a }
                if (r0 == 0) goto L_0x0007;
            L_0x0005:
                monitor-exit(r8);
                return;
            L_0x0007:
                r0 = r8.statsTraceCtx;	 Catch:{ all -> 0x005a }
                r1 = r8.outboundSeqNo;	 Catch:{ all -> 0x005a }
                r0.outboundMessage(r1);	 Catch:{ all -> 0x005a }
                r2 = r8.statsTraceCtx;	 Catch:{ all -> 0x005a }
                r3 = r8.outboundSeqNo;	 Catch:{ all -> 0x005a }
                r4 = -1;
                r6 = -1;
                r2.outboundMessageSent(r3, r4, r6);	 Catch:{ all -> 0x005a }
                r0 = io.grpc.inprocess.InProcessTransport.InProcessStream.this;	 Catch:{ all -> 0x005a }
                r0 = r0.clientStream;	 Catch:{ all -> 0x005a }
                r0 = r0.statsTraceCtx;	 Catch:{ all -> 0x005a }
                r1 = r8.outboundSeqNo;	 Catch:{ all -> 0x005a }
                r0.inboundMessage(r1);	 Catch:{ all -> 0x005a }
                r0 = io.grpc.inprocess.InProcessTransport.InProcessStream.this;	 Catch:{ all -> 0x005a }
                r0 = r0.clientStream;	 Catch:{ all -> 0x005a }
                r1 = r0.statsTraceCtx;	 Catch:{ all -> 0x005a }
                r2 = r8.outboundSeqNo;	 Catch:{ all -> 0x005a }
                r3 = -1;
                r5 = -1;
                r1.inboundMessageRead(r2, r3, r5);	 Catch:{ all -> 0x005a }
                r0 = r8.outboundSeqNo;	 Catch:{ all -> 0x005a }
                r0 = r0 + 1;
                r8.outboundSeqNo = r0;	 Catch:{ all -> 0x005a }
                r0 = new io.grpc.inprocess.InProcessTransport$SingleMessageProducer;	 Catch:{ all -> 0x005a }
                r1 = 0;
                r0.<init>(r9, r1);	 Catch:{ all -> 0x005a }
                r9 = r8.clientRequested;	 Catch:{ all -> 0x005a }
                if (r9 <= 0) goto L_0x0053;
            L_0x0047:
                r9 = r8.clientRequested;	 Catch:{ all -> 0x005a }
                r9 = r9 + -1;
                r8.clientRequested = r9;	 Catch:{ all -> 0x005a }
                r9 = r8.clientStreamListener;	 Catch:{ all -> 0x005a }
                r9.messagesAvailable(r0);	 Catch:{ all -> 0x005a }
                goto L_0x0058;
            L_0x0053:
                r9 = r8.clientReceiveQueue;	 Catch:{ all -> 0x005a }
                r9.add(r0);	 Catch:{ all -> 0x005a }
            L_0x0058:
                monitor-exit(r8);
                return;
            L_0x005a:
                r9 = move-exception;
                monitor-exit(r8);
                throw r9;
                */
                throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.InProcessStream.InProcessServerStream.writeMessage(java.io.InputStream):void");
            }

            /* JADX WARNING: Missing block: B:12:0x000e, code:
            return r1;
     */
            public synchronized boolean isReady() {
                /*
                r2 = this;
                monitor-enter(r2);
                r0 = r2.closed;	 Catch:{ all -> 0x000f }
                r1 = 0;
                if (r0 == 0) goto L_0x0008;
            L_0x0006:
                monitor-exit(r2);
                return r1;
            L_0x0008:
                r0 = r2.clientRequested;	 Catch:{ all -> 0x000f }
                if (r0 <= 0) goto L_0x000d;
            L_0x000c:
                r1 = 1;
            L_0x000d:
                monitor-exit(r2);
                return r1;
            L_0x000f:
                r0 = move-exception;
                monitor-exit(r2);
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.InProcessStream.InProcessServerStream.isReady():boolean");
            }

            public synchronized void writeHeaders(Metadata metadata) {
                if (!this.closed) {
                    InProcessStream.this.clientStream.statsTraceCtx.clientInboundHeaders();
                    this.clientStreamListener.headersRead(metadata);
                }
            }

            /* JADX WARNING: Missing block: B:12:0x0037, code:
            io.grpc.inprocess.InProcessTransport.InProcessStream.access$1600(r2.this$1);
     */
            /* JADX WARNING: Missing block: B:13:0x003c, code:
            return;
     */
            public void close(io.grpc.Status r3, io.grpc.Metadata r4) {
                /*
                r2 = this;
                r0 = io.grpc.inprocess.InProcessTransport.InProcessStream.this;
                r0 = r0.clientStream;
                r1 = io.grpc.Status.OK;
                r0.serverClosed(r1, r3);
                r3 = io.grpc.inprocess.InProcessTransport.stripCause(r3);
                monitor-enter(r2);
                r0 = r2.closed;	 Catch:{ all -> 0x003d }
                if (r0 == 0) goto L_0x0016;
            L_0x0014:
                monitor-exit(r2);	 Catch:{ all -> 0x003d }
                return;
            L_0x0016:
                r0 = r2.clientReceiveQueue;	 Catch:{ all -> 0x003d }
                r0 = r0.isEmpty();	 Catch:{ all -> 0x003d }
                if (r0 == 0) goto L_0x0032;
            L_0x001e:
                r0 = 1;
                r2.closed = r0;	 Catch:{ all -> 0x003d }
                r0 = io.grpc.inprocess.InProcessTransport.InProcessStream.this;	 Catch:{ all -> 0x003d }
                r0 = r0.clientStream;	 Catch:{ all -> 0x003d }
                r0 = r0.statsTraceCtx;	 Catch:{ all -> 0x003d }
                r0.streamClosed(r3);	 Catch:{ all -> 0x003d }
                r0 = r2.clientStreamListener;	 Catch:{ all -> 0x003d }
                r0.closed(r3, r4);	 Catch:{ all -> 0x003d }
                goto L_0x0036;
            L_0x0032:
                r2.clientNotifyStatus = r3;	 Catch:{ all -> 0x003d }
                r2.clientNotifyTrailers = r4;	 Catch:{ all -> 0x003d }
            L_0x0036:
                monitor-exit(r2);	 Catch:{ all -> 0x003d }
                r3 = io.grpc.inprocess.InProcessTransport.InProcessStream.this;
                r3.streamClosed();
                return;
            L_0x003d:
                r3 = move-exception;
                monitor-exit(r2);	 Catch:{ all -> 0x003d }
                throw r3;
                */
                throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.InProcessStream.InProcessServerStream.close(io.grpc.Status, io.grpc.Metadata):void");
            }

            public void cancel(Status status) {
                if (internalCancel(Status.CANCELLED.withDescription("server cancelled stream"))) {
                    InProcessStream.this.clientStream.serverClosed(status, status);
                    InProcessStream.this.streamClosed();
                }
            }

            private synchronized boolean internalCancel(Status status) {
                if (this.closed) {
                    return false;
                }
                this.closed = true;
                while (true) {
                    MessageProducer messageProducer = (MessageProducer) this.clientReceiveQueue.poll();
                    if (messageProducer != null) {
                        while (true) {
                            InputStream next = messageProducer.next();
                            if (next != null) {
                                try {
                                    next.close();
                                } catch (Throwable th) {
                                    InProcessTransport.log.log(Level.WARNING, "Exception closing stream", th);
                                }
                            }
                        }
                    } else {
                        InProcessStream.this.clientStream.statsTraceCtx.streamClosed(status);
                        this.clientStreamListener.closed(status, new Metadata());
                        return true;
                    }
                }
            }

            public Attributes getAttributes() {
                return InProcessTransport.this.serverStreamAttributes;
            }

            public String getAuthority() {
                return InProcessStream.this.authority;
            }

            public StatsTraceContext statsTraceContext() {
                return this.statsTraceCtx;
            }
        }

        /* synthetic */ InProcessStream(InProcessTransport inProcessTransport, MethodDescriptor methodDescriptor, Metadata metadata, CallOptions callOptions, String str, AnonymousClass1 anonymousClass1) {
            this(methodDescriptor, metadata, callOptions, str);
        }

        private InProcessStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions, String str) {
            this.method = (MethodDescriptor) Preconditions.checkNotNull(methodDescriptor, Param.METHOD);
            this.headers = (Metadata) Preconditions.checkNotNull(metadata, "headers");
            this.authority = str;
            this.clientStream = new InProcessClientStream(callOptions, metadata);
            this.serverStream = new InProcessServerStream(methodDescriptor, metadata);
        }

        private void streamClosed() {
            synchronized (InProcessTransport.this) {
                boolean remove = InProcessTransport.this.streams.remove(this);
                if (InProcessTransport.this.streams.isEmpty() && remove) {
                    InProcessTransport.this.clientTransportListener.transportInUse(false);
                    if (InProcessTransport.this.shutdown) {
                        InProcessTransport.this.notifyTerminated();
                    }
                }
            }
        }
    }

    private static class SingleMessageProducer implements MessageProducer {
        private InputStream message;

        /* synthetic */ SingleMessageProducer(InputStream inputStream, AnonymousClass1 anonymousClass1) {
            this(inputStream);
        }

        private SingleMessageProducer(InputStream inputStream) {
            this.message = inputStream;
        }

        @Nullable
        public InputStream next() {
            InputStream inputStream = this.message;
            this.message = null;
            return inputStream;
        }
    }

    public InProcessTransport(String str, String str2, String str3) {
        this.name = str;
        this.authority = str2;
        this.userAgent = GrpcUtil.getGrpcUserAgent("inprocess", str3);
    }

    @CheckReturnValue
    public synchronized Runnable start(Listener listener) {
        this.clientTransportListener = listener;
        InProcessServer findServer = InProcessServer.findServer(this.name);
        if (findServer != null) {
            this.serverSchedulerPool = findServer.getScheduledExecutorServicePool();
            this.serverScheduler = (ScheduledExecutorService) this.serverSchedulerPool.getObject();
            this.serverStreamTracerFactories = findServer.getStreamTracerFactories();
            this.serverTransportListener = findServer.register(this);
        }
        if (this.serverTransportListener == null) {
            Status status = Status.UNAVAILABLE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find server: ");
            stringBuilder.append(this.name);
            this.shutdownStatus = status.withDescription(stringBuilder.toString());
            status = this.shutdownStatus;
            return new Runnable() {
                public void run() {
                    synchronized (InProcessTransport.this) {
                        InProcessTransport.this.notifyShutdown(status);
                        InProcessTransport.this.notifyTerminated();
                    }
                }
            };
        }
        return new Runnable() {
            public void run() {
                synchronized (InProcessTransport.this) {
                    InProcessTransport.this.serverStreamAttributes = InProcessTransport.this.serverTransportListener.transportReady(Attributes.newBuilder().set(Grpc.TRANSPORT_ATTR_REMOTE_ADDR, new InProcessSocketAddress(InProcessTransport.this.name)).set(Grpc.TRANSPORT_ATTR_LOCAL_ADDR, new InProcessSocketAddress(InProcessTransport.this.name)).build());
                    InProcessTransport.this.clientTransportListener.transportReady();
                }
            }
        };
    }

    public synchronized ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions) {
        if (this.shutdownStatus != null) {
            final Status status = this.shutdownStatus;
            final StatsTraceContext newClientContext = StatsTraceContext.newClientContext(callOptions, metadata);
            return new NoopClientStream() {
                public void start(ClientStreamListener clientStreamListener) {
                    newClientContext.clientOutboundHeaders();
                    newClientContext.streamClosed(status);
                    clientStreamListener.closed(status, new Metadata());
                }
            };
        }
        metadata.put(GrpcUtil.USER_AGENT_KEY, this.userAgent);
        return new InProcessStream(this, methodDescriptor, metadata, callOptions, this.authority, null).clientStream;
    }

    public synchronized void ping(final PingCallback pingCallback, Executor executor) {
        if (this.terminated) {
            final Status status = this.shutdownStatus;
            executor.execute(new Runnable() {
                public void run() {
                    pingCallback.onFailure(status.asRuntimeException());
                }
            });
        } else {
            executor.execute(new Runnable() {
                public void run() {
                    pingCallback.onSuccess(0);
                }
            });
        }
    }

    /* JADX WARNING: Missing block: B:11:0x0018, code:
            return;
     */
    public synchronized void shutdown(io.grpc.Status r2) {
        /*
        r1 = this;
        monitor-enter(r1);
        r0 = r1.shutdown;	 Catch:{ all -> 0x0019 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r1);
        return;
    L_0x0007:
        r1.shutdownStatus = r2;	 Catch:{ all -> 0x0019 }
        r1.notifyShutdown(r2);	 Catch:{ all -> 0x0019 }
        r2 = r1.streams;	 Catch:{ all -> 0x0019 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x0019 }
        if (r2 == 0) goto L_0x0017;
    L_0x0014:
        r1.notifyTerminated();	 Catch:{ all -> 0x0019 }
    L_0x0017:
        monitor-exit(r1);
        return;
    L_0x0019:
        r2 = move-exception;
        monitor-exit(r1);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.shutdown(io.grpc.Status):void");
    }

    public synchronized void shutdown() {
        shutdown(Status.UNAVAILABLE.withDescription("InProcessTransport shutdown by the server-side"));
    }

    /* JADX WARNING: Missing block: B:9:0x0017, code:
            r0 = r0.iterator();
     */
    /* JADX WARNING: Missing block: B:11:0x001f, code:
            if (r0.hasNext() == false) goto L_0x002f;
     */
    /* JADX WARNING: Missing block: B:12:0x0021, code:
            io.grpc.inprocess.InProcessTransport.InProcessStream.access$700((io.grpc.inprocess.InProcessTransport.InProcessStream) r0.next()).cancel(r3);
     */
    /* JADX WARNING: Missing block: B:13:0x002f, code:
            return;
     */
    public void shutdownNow(io.grpc.Status r3) {
        /*
        r2 = this;
        r0 = "reason";
        com.google.common.base.Preconditions.checkNotNull(r3, r0);
        monitor-enter(r2);
        r2.shutdown(r3);	 Catch:{ all -> 0x0030 }
        r0 = r2.terminated;	 Catch:{ all -> 0x0030 }
        if (r0 == 0) goto L_0x000f;
    L_0x000d:
        monitor-exit(r2);	 Catch:{ all -> 0x0030 }
        return;
    L_0x000f:
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0030 }
        r1 = r2.streams;	 Catch:{ all -> 0x0030 }
        r0.<init>(r1);	 Catch:{ all -> 0x0030 }
        monitor-exit(r2);	 Catch:{ all -> 0x0030 }
        r0 = r0.iterator();
    L_0x001b:
        r1 = r0.hasNext();
        if (r1 == 0) goto L_0x002f;
    L_0x0021:
        r1 = r0.next();
        r1 = (io.grpc.inprocess.InProcessTransport.InProcessStream) r1;
        r1 = r1.clientStream;
        r1.cancel(r3);
        goto L_0x001b;
    L_0x002f:
        return;
    L_0x0030:
        r3 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0030 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.shutdownNow(io.grpc.Status):void");
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("logId", this.logId.getId()).add(ConditionalUserProperty.NAME, this.name).toString();
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.serverScheduler;
    }

    public ListenableFuture<SocketStats> getStats() {
        ListenableFuture create = SettableFuture.create();
        create.set(null);
        return create;
    }

    private synchronized void notifyShutdown(Status status) {
        if (!this.shutdown) {
            this.shutdown = true;
            this.clientTransportListener.transportShutdown(status);
        }
    }

    /* JADX WARNING: Missing block: B:15:0x0029, code:
            return;
     */
    private synchronized void notifyTerminated() {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.terminated;	 Catch:{ all -> 0x002a }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r2);
        return;
    L_0x0007:
        r0 = 1;
        r2.terminated = r0;	 Catch:{ all -> 0x002a }
        r0 = r2.serverScheduler;	 Catch:{ all -> 0x002a }
        if (r0 == 0) goto L_0x001a;
    L_0x000e:
        r0 = r2.serverSchedulerPool;	 Catch:{ all -> 0x002a }
        r1 = r2.serverScheduler;	 Catch:{ all -> 0x002a }
        r0 = r0.returnObject(r1);	 Catch:{ all -> 0x002a }
        r0 = (java.util.concurrent.ScheduledExecutorService) r0;	 Catch:{ all -> 0x002a }
        r2.serverScheduler = r0;	 Catch:{ all -> 0x002a }
    L_0x001a:
        r0 = r2.clientTransportListener;	 Catch:{ all -> 0x002a }
        r0.transportTerminated();	 Catch:{ all -> 0x002a }
        r0 = r2.serverTransportListener;	 Catch:{ all -> 0x002a }
        if (r0 == 0) goto L_0x0028;
    L_0x0023:
        r0 = r2.serverTransportListener;	 Catch:{ all -> 0x002a }
        r0.transportTerminated();	 Catch:{ all -> 0x002a }
    L_0x0028:
        monitor-exit(r2);
        return;
    L_0x002a:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.inprocess.InProcessTransport.notifyTerminated():void");
    }

    private static Status stripCause(Status status) {
        if (status == null) {
            return null;
        }
        return Status.fromCodeValue(status.getCode().value()).withDescription(status.getDescription());
    }
}
