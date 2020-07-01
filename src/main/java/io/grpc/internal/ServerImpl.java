package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.BinaryLog;
import io.grpc.CompressorRegistry;
import io.grpc.Context;
import io.grpc.Context.CancellableContext;
import io.grpc.Context.CancellationListener;
import io.grpc.Contexts;
import io.grpc.Decompressor;
import io.grpc.DecompressorRegistry;
import io.grpc.HandlerRegistry;
import io.grpc.InternalChannelz;
import io.grpc.InternalChannelz.ServerStats;
import io.grpc.InternalChannelz.ServerStats.Builder;
import io.grpc.InternalInstrumented;
import io.grpc.InternalLogId;
import io.grpc.InternalServerInterceptors;
import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServerTransportFilter;
import io.grpc.Status;
import io.grpc.internal.StreamListener.MessageProducer;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

public final class ServerImpl extends Server implements InternalInstrumented<ServerStats> {
    private static final ServerStreamListener NOOP_LISTENER = new NoopListener();
    private static final Logger log = Logger.getLogger(ServerImpl.class.getName());
    private final BinaryLog binlog;
    private final InternalChannelz channelz;
    private final CompressorRegistry compressorRegistry;
    private final DecompressorRegistry decompressorRegistry;
    private Executor executor;
    private final ObjectPool<? extends Executor> executorPool;
    private final HandlerRegistry fallbackRegistry;
    private final long handshakeTimeoutMillis;
    private final ServerInterceptor[] interceptors;
    private final Object lock = new Object();
    private final InternalLogId logId = InternalLogId.allocate(getClass().getName());
    private final HandlerRegistry registry;
    private final Context rootContext;
    private final CallTracer serverCallTracer;
    @GuardedBy("lock")
    private boolean serverShutdownCallbackInvoked;
    @GuardedBy("lock")
    private boolean shutdown;
    @GuardedBy("lock")
    private Status shutdownNowStatus;
    @GuardedBy("lock")
    private boolean started;
    @GuardedBy("lock")
    private boolean terminated;
    private final List<ServerTransportFilter> transportFilters;
    private final InternalServer transportServer;
    @GuardedBy("lock")
    private boolean transportServerTerminated;
    @GuardedBy("lock")
    private final Collection<ServerTransport> transports = new HashSet();

    @VisibleForTesting
    static final class ContextCloser implements Runnable {
        private final Throwable cause;
        private final CancellableContext context;

        ContextCloser(CancellableContext cancellableContext, Throwable th) {
            this.context = cancellableContext;
            this.cause = th;
        }

        public void run() {
            this.context.cancel(this.cause);
        }
    }

    private final class ServerListenerImpl implements ServerListener {
        private ServerListenerImpl() {
        }

        public ServerTransportListener transportCreated(ServerTransport serverTransport) {
            synchronized (ServerImpl.this.lock) {
                ServerImpl.this.transports.add(serverTransport);
            }
            ServerTransportListener serverTransportListenerImpl = new ServerTransportListenerImpl(serverTransport);
            serverTransportListenerImpl.init();
            return serverTransportListenerImpl;
        }

        public void serverShutdown() {
            ArrayList arrayList;
            Status access$400;
            synchronized (ServerImpl.this.lock) {
                arrayList = new ArrayList(ServerImpl.this.transports);
                access$400 = ServerImpl.this.shutdownNowStatus;
                ServerImpl.this.serverShutdownCallbackInvoked = true;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ServerTransport serverTransport = (ServerTransport) it.next();
                if (access$400 == null) {
                    serverTransport.shutdown();
                } else {
                    serverTransport.shutdownNow(access$400);
                }
            }
            synchronized (ServerImpl.this.lock) {
                ServerImpl.this.transportServerTerminated = true;
                ServerImpl.this.checkForTermination();
            }
        }
    }

    private final class ServerTransportListenerImpl implements ServerTransportListener {
        private Attributes attributes;
        private Future<?> handshakeTimeoutFuture;
        private final ServerTransport transport;

        /* renamed from: io.grpc.internal.ServerImpl$ServerTransportListenerImpl$1StreamCreated */
        final class AnonymousClass1StreamCreated extends ContextRunnable {
            final /* synthetic */ CancellableContext val$context;
            final /* synthetic */ Metadata val$headers;
            final /* synthetic */ JumpToApplicationThreadServerStreamListener val$jumpListener;
            final /* synthetic */ String val$methodName;
            final /* synthetic */ StatsTraceContext val$statsTraceCtx;
            final /* synthetic */ ServerStream val$stream;

            AnonymousClass1StreamCreated(CancellableContext cancellableContext, String str, ServerStream serverStream, Metadata metadata, StatsTraceContext statsTraceContext, JumpToApplicationThreadServerStreamListener jumpToApplicationThreadServerStreamListener) {
                this.val$context = cancellableContext;
                this.val$methodName = str;
                this.val$stream = serverStream;
                this.val$headers = metadata;
                this.val$statsTraceCtx = statsTraceContext;
                this.val$jumpListener = jumpToApplicationThreadServerStreamListener;
                super(cancellableContext);
            }

            public void runInContext() {
                ServerStreamListener access$1500 = ServerImpl.NOOP_LISTENER;
                try {
                    ServerMethodDefinition lookupMethod = ServerImpl.this.registry.lookupMethod(this.val$methodName);
                    if (lookupMethod == null) {
                        lookupMethod = ServerImpl.this.fallbackRegistry.lookupMethod(this.val$methodName, this.val$stream.getAuthority());
                    }
                    ServerMethodDefinition serverMethodDefinition = lookupMethod;
                    if (serverMethodDefinition == null) {
                        Status status = Status.UNIMPLEMENTED;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Method not found: ");
                        stringBuilder.append(this.val$methodName);
                        this.val$stream.close(status.withDescription(stringBuilder.toString()), new Metadata());
                        this.val$context.cancel(null);
                        this.val$jumpListener.setListener(access$1500);
                        return;
                    }
                    access$1500 = ServerTransportListenerImpl.this.startCall(this.val$stream, this.val$methodName, serverMethodDefinition, this.val$headers, this.val$context, this.val$statsTraceCtx);
                    this.val$jumpListener.setListener(access$1500);
                } catch (Throwable e) {
                    this.val$stream.close(Status.fromThrowable(e), new Metadata());
                    this.val$context.cancel(null);
                    throw e;
                } catch (Throwable e2) {
                    this.val$stream.close(Status.fromThrowable(e2), new Metadata());
                    this.val$context.cancel(null);
                    throw e2;
                } catch (Throwable th) {
                    this.val$jumpListener.setListener(access$1500);
                }
            }
        }

        ServerTransportListenerImpl(ServerTransport serverTransport) {
            this.transport = serverTransport;
        }

        public void init() {
            if (ServerImpl.this.handshakeTimeoutMillis != Long.MAX_VALUE) {
                this.handshakeTimeoutFuture = this.transport.getScheduledExecutorService().schedule(new Runnable() {
                    public void run() {
                        ServerTransportListenerImpl.this.transport.shutdownNow(Status.CANCELLED.withDescription("Handshake timeout exceeded"));
                    }
                }, ServerImpl.this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                this.handshakeTimeoutFuture = new FutureTask(new Runnable() {
                    public void run() {
                    }
                }, null);
            }
            ServerImpl.this.channelz.addServerSocket(ServerImpl.this, this.transport);
        }

        public Attributes transportReady(Attributes attributes) {
            this.handshakeTimeoutFuture.cancel(false);
            this.handshakeTimeoutFuture = null;
            for (Object obj : ServerImpl.this.transportFilters) {
                attributes = (Attributes) Preconditions.checkNotNull(obj.transportReady(attributes), "Filter %s returned null", obj);
            }
            this.attributes = attributes;
            return attributes;
        }

        public void transportTerminated() {
            Future future = this.handshakeTimeoutFuture;
            if (future != null) {
                future.cancel(false);
                this.handshakeTimeoutFuture = null;
            }
            for (ServerTransportFilter transportTerminated : ServerImpl.this.transportFilters) {
                transportTerminated.transportTerminated(this.attributes);
            }
            ServerImpl.this.transportClosed(this.transport);
        }

        public void streamCreated(ServerStream serverStream, String str, Metadata metadata) {
            Executor serializeReentrantCallsDirectExecutor;
            if (metadata.containsKey(GrpcUtil.MESSAGE_ENCODING_KEY)) {
                Decompressor lookupDecompressor = ServerImpl.this.decompressorRegistry.lookupDecompressor((String) metadata.get(GrpcUtil.MESSAGE_ENCODING_KEY));
                if (lookupDecompressor == null) {
                    serverStream.close(Status.UNIMPLEMENTED.withDescription(String.format("Can't find decompressor for %s", new Object[]{r0})), new Metadata());
                    return;
                }
                serverStream.setDecompressor(lookupDecompressor);
            }
            StatsTraceContext statsTraceContext = (StatsTraceContext) Preconditions.checkNotNull(serverStream.statsTraceContext(), "statsTraceCtx not present from stream");
            CancellableContext createContext = createContext(serverStream, metadata, statsTraceContext);
            if (ServerImpl.this.executor == MoreExecutors.directExecutor()) {
                serializeReentrantCallsDirectExecutor = new SerializeReentrantCallsDirectExecutor();
            } else {
                serializeReentrantCallsDirectExecutor = new SerializingExecutor(ServerImpl.this.executor);
            }
            Object jumpToApplicationThreadServerStreamListener = new JumpToApplicationThreadServerStreamListener(serializeReentrantCallsDirectExecutor, ServerImpl.this.executor, serverStream, createContext);
            serverStream.setListener(jumpToApplicationThreadServerStreamListener);
            serializeReentrantCallsDirectExecutor.execute(new AnonymousClass1StreamCreated(createContext, str, serverStream, metadata, statsTraceContext, jumpToApplicationThreadServerStreamListener));
        }

        private CancellableContext createContext(final ServerStream serverStream, Metadata metadata, StatsTraceContext statsTraceContext) {
            Long l = (Long) metadata.get(GrpcUtil.TIMEOUT_KEY);
            Context serverFilterContext = statsTraceContext.serverFilterContext(ServerImpl.this.rootContext);
            if (l == null) {
                return serverFilterContext.withCancellation();
            }
            CancellableContext withDeadlineAfter = serverFilterContext.withDeadlineAfter(l.longValue(), TimeUnit.NANOSECONDS, this.transport.getScheduledExecutorService());
            withDeadlineAfter.addListener(new CancellationListener() {
                public void cancelled(Context context) {
                    Status statusFromCancelled = Contexts.statusFromCancelled(context);
                    if (Status.DEADLINE_EXCEEDED.getCode().equals(statusFromCancelled.getCode())) {
                        serverStream.cancel(statusFromCancelled);
                    }
                }
            }, MoreExecutors.directExecutor());
            return withDeadlineAfter;
        }

        private <ReqT, RespT> ServerStreamListener startCall(ServerStream serverStream, String str, ServerMethodDefinition<ReqT, RespT> serverMethodDefinition, Metadata metadata, CancellableContext cancellableContext, StatsTraceContext statsTraceContext) {
            statsTraceContext.serverCallStarted(new ServerCallInfoImpl(serverMethodDefinition.getMethodDescriptor(), serverStream.getAttributes(), serverStream.getAuthority()));
            ServerCallHandler serverCallHandler = serverMethodDefinition.getServerCallHandler();
            for (ServerInterceptor interceptCallHandler : ServerImpl.this.interceptors) {
                serverCallHandler = InternalServerInterceptors.interceptCallHandler(interceptCallHandler, serverCallHandler);
            }
            ServerMethodDefinition withServerCallHandler = serverMethodDefinition.withServerCallHandler(serverCallHandler);
            if (ServerImpl.this.binlog != null) {
                withServerCallHandler = ServerImpl.this.binlog.wrapMethodDefinition(withServerCallHandler);
            }
            return startWrappedCall(str, withServerCallHandler, serverStream, metadata, cancellableContext);
        }

        private <WReqT, WRespT> ServerStreamListener startWrappedCall(String str, ServerMethodDefinition<WReqT, WRespT> serverMethodDefinition, ServerStream serverStream, Metadata metadata, CancellableContext cancellableContext) {
            ServerCall serverCallImpl = new ServerCallImpl(serverStream, serverMethodDefinition.getMethodDescriptor(), metadata, cancellableContext, ServerImpl.this.decompressorRegistry, ServerImpl.this.compressorRegistry, ServerImpl.this.serverCallTracer);
            Listener startCall = serverMethodDefinition.getServerCallHandler().startCall(serverCallImpl, metadata);
            if (startCall != null) {
                return serverCallImpl.newServerStreamListener(startCall);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("startCall() returned a null listener for method ");
            stringBuilder.append(str);
            throw new NullPointerException(stringBuilder.toString());
        }
    }

    @VisibleForTesting
    static final class JumpToApplicationThreadServerStreamListener implements ServerStreamListener {
        private final Executor callExecutor;
        private final Executor cancelExecutor;
        private final CancellableContext context;
        private ServerStreamListener listener;
        private final ServerStream stream;

        public JumpToApplicationThreadServerStreamListener(Executor executor, Executor executor2, ServerStream serverStream, CancellableContext cancellableContext) {
            this.callExecutor = executor;
            this.cancelExecutor = executor2;
            this.stream = serverStream;
            this.context = cancellableContext;
        }

        private ServerStreamListener getListener() {
            ServerStreamListener serverStreamListener = this.listener;
            if (serverStreamListener != null) {
                return serverStreamListener;
            }
            throw new IllegalStateException("listener unset");
        }

        @VisibleForTesting
        void setListener(ServerStreamListener serverStreamListener) {
            Preconditions.checkNotNull(serverStreamListener, "listener must not be null");
            Preconditions.checkState(this.listener == null, "Listener already set");
            this.listener = serverStreamListener;
        }

        private void internalClose() {
            this.stream.close(Status.UNKNOWN, new Metadata());
        }

        public void messagesAvailable(final MessageProducer messageProducer) {
            this.callExecutor.execute(new ContextRunnable() {
                public void runInContext() {
                    try {
                        JumpToApplicationThreadServerStreamListener.this.getListener().messagesAvailable(messageProducer);
                    } catch (RuntimeException e) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e;
                    } catch (Error e2) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e2;
                    }
                }
            });
        }

        public void halfClosed() {
            this.callExecutor.execute(new ContextRunnable() {
                {
                    Context access$2500 = JumpToApplicationThreadServerStreamListener.this.context;
                }

                public void runInContext() {
                    try {
                        JumpToApplicationThreadServerStreamListener.this.getListener().halfClosed();
                    } catch (RuntimeException e) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e;
                    } catch (Error e2) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e2;
                    }
                }
            });
        }

        public void closed(final Status status) {
            if (!status.isOk()) {
                this.cancelExecutor.execute(new ContextCloser(this.context, status.getCause()));
            }
            this.callExecutor.execute(new ContextRunnable() {
                public void runInContext() {
                    JumpToApplicationThreadServerStreamListener.this.getListener().closed(status);
                }
            });
        }

        public void onReady() {
            this.callExecutor.execute(new ContextRunnable() {
                {
                    Context access$2500 = JumpToApplicationThreadServerStreamListener.this.context;
                }

                public void runInContext() {
                    try {
                        JumpToApplicationThreadServerStreamListener.this.getListener().onReady();
                    } catch (RuntimeException e) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e;
                    } catch (Error e2) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e2;
                    }
                }
            });
        }
    }

    private static final class NoopListener implements ServerStreamListener {
        public void closed(Status status) {
        }

        public void halfClosed() {
        }

        public void onReady() {
        }

        private NoopListener() {
        }

        public void messagesAvailable(MessageProducer messageProducer) {
            while (true) {
                InputStream next = messageProducer.next();
                if (next != null) {
                    try {
                        next.close();
                    } catch (Throwable e) {
                        while (true) {
                            InputStream next2 = messageProducer.next();
                            if (next2 != null) {
                                try {
                                    next2.close();
                                } catch (Throwable e2) {
                                    ServerImpl.log.log(Level.WARNING, "Exception closing stream", e2);
                                }
                            } else {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                return;
            }
        }
    }

    ServerImpl(AbstractServerImplBuilder<?> abstractServerImplBuilder, InternalServer internalServer, Context context) {
        this.executorPool = (ObjectPool) Preconditions.checkNotNull(abstractServerImplBuilder.executorPool, "executorPool");
        this.registry = (HandlerRegistry) Preconditions.checkNotNull(abstractServerImplBuilder.registryBuilder.build(), "registryBuilder");
        this.fallbackRegistry = (HandlerRegistry) Preconditions.checkNotNull(abstractServerImplBuilder.fallbackRegistry, "fallbackRegistry");
        this.transportServer = (InternalServer) Preconditions.checkNotNull(internalServer, "transportServer");
        this.rootContext = ((Context) Preconditions.checkNotNull(context, "rootContext")).fork();
        this.decompressorRegistry = abstractServerImplBuilder.decompressorRegistry;
        this.compressorRegistry = abstractServerImplBuilder.compressorRegistry;
        this.transportFilters = Collections.unmodifiableList(new ArrayList(abstractServerImplBuilder.transportFilters));
        this.interceptors = (ServerInterceptor[]) abstractServerImplBuilder.interceptors.toArray(new ServerInterceptor[abstractServerImplBuilder.interceptors.size()]);
        this.handshakeTimeoutMillis = abstractServerImplBuilder.handshakeTimeoutMillis;
        this.binlog = abstractServerImplBuilder.binlog;
        this.channelz = abstractServerImplBuilder.channelz;
        this.serverCallTracer = abstractServerImplBuilder.callTracerFactory.create();
        this.channelz.addServer(this);
    }

    public ServerImpl start() throws IOException {
        synchronized (this.lock) {
            boolean z = false;
            Preconditions.checkState(!this.started, "Already started");
            if (!this.shutdown) {
                z = true;
            }
            Preconditions.checkState(z, "Shutting down");
            this.transportServer.start(new ServerListenerImpl());
            this.executor = (Executor) Preconditions.checkNotNull(this.executorPool.getObject(), "executor");
            this.started = true;
        }
        return this;
    }

    public int getPort() {
        int port;
        synchronized (this.lock) {
            Preconditions.checkState(this.started, "Not started");
            Preconditions.checkState(!this.terminated, "Already terminated");
            port = this.transportServer.getPort();
        }
        return port;
    }

    public List<ServerServiceDefinition> getServices() {
        Collection services = this.fallbackRegistry.getServices();
        if (services.isEmpty()) {
            return this.registry.getServices();
        }
        Collection services2 = this.registry.getServices();
        List arrayList = new ArrayList(services2.size() + services.size());
        arrayList.addAll(services2);
        arrayList.addAll(services);
        return Collections.unmodifiableList(arrayList);
    }

    public List<ServerServiceDefinition> getImmutableServices() {
        return this.registry.getServices();
    }

    public List<ServerServiceDefinition> getMutableServices() {
        return Collections.unmodifiableList(this.fallbackRegistry.getServices());
    }

    /* JADX WARNING: Missing block: B:11:0x0016, code:
            if (r2 == false) goto L_0x001d;
     */
    /* JADX WARNING: Missing block: B:12:0x0018, code:
            r3.transportServer.shutdown();
     */
    /* JADX WARNING: Missing block: B:13:0x001d, code:
            return r3;
     */
    public io.grpc.internal.ServerImpl shutdown() {
        /*
        r3 = this;
        r0 = r3.lock;
        monitor-enter(r0);
        r1 = r3.shutdown;	 Catch:{ all -> 0x001e }
        if (r1 == 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r0);	 Catch:{ all -> 0x001e }
        return r3;
    L_0x0009:
        r1 = 1;
        r3.shutdown = r1;	 Catch:{ all -> 0x001e }
        r2 = r3.started;	 Catch:{ all -> 0x001e }
        if (r2 != 0) goto L_0x0015;
    L_0x0010:
        r3.transportServerTerminated = r1;	 Catch:{ all -> 0x001e }
        r3.checkForTermination();	 Catch:{ all -> 0x001e }
    L_0x0015:
        monitor-exit(r0);	 Catch:{ all -> 0x001e }
        if (r2 == 0) goto L_0x001d;
    L_0x0018:
        r0 = r3.transportServer;
        r0.shutdown();
    L_0x001d:
        return r3;
    L_0x001e:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x001e }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.ServerImpl.shutdown():io.grpc.internal.ServerImpl");
    }

    /* JADX WARNING: Missing block: B:9:0x0020, code:
            if (r3 == false) goto L_0x0036;
     */
    /* JADX WARNING: Missing block: B:10:0x0022, code:
            r1 = r2.iterator();
     */
    /* JADX WARNING: Missing block: B:12:0x002a, code:
            if (r1.hasNext() == false) goto L_0x0036;
     */
    /* JADX WARNING: Missing block: B:13:0x002c, code:
            ((io.grpc.internal.ServerTransport) r1.next()).shutdownNow(r0);
     */
    /* JADX WARNING: Missing block: B:14:0x0036, code:
            return r4;
     */
    public io.grpc.internal.ServerImpl shutdownNow() {
        /*
        r4 = this;
        r4.shutdown();
        r0 = io.grpc.Status.UNAVAILABLE;
        r1 = "Server shutdownNow invoked";
        r0 = r0.withDescription(r1);
        r1 = r4.lock;
        monitor-enter(r1);
        r2 = r4.shutdownNowStatus;	 Catch:{ all -> 0x0037 }
        if (r2 == 0) goto L_0x0014;
    L_0x0012:
        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
        return r4;
    L_0x0014:
        r4.shutdownNowStatus = r0;	 Catch:{ all -> 0x0037 }
        r2 = new java.util.ArrayList;	 Catch:{ all -> 0x0037 }
        r3 = r4.transports;	 Catch:{ all -> 0x0037 }
        r2.<init>(r3);	 Catch:{ all -> 0x0037 }
        r3 = r4.serverShutdownCallbackInvoked;	 Catch:{ all -> 0x0037 }
        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
        if (r3 == 0) goto L_0x0036;
    L_0x0022:
        r1 = r2.iterator();
    L_0x0026:
        r2 = r1.hasNext();
        if (r2 == 0) goto L_0x0036;
    L_0x002c:
        r2 = r1.next();
        r2 = (io.grpc.internal.ServerTransport) r2;
        r2.shutdownNow(r0);
        goto L_0x0026;
    L_0x0036:
        return r4;
    L_0x0037:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.ServerImpl.shutdownNow():io.grpc.internal.ServerImpl");
    }

    public boolean isShutdown() {
        boolean z;
        synchronized (this.lock) {
            z = this.shutdown;
        }
        return z;
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        boolean z;
        synchronized (this.lock) {
            long nanoTime = System.nanoTime() + timeUnit.toNanos(j);
            while (!this.terminated) {
                j = nanoTime - System.nanoTime();
                if (j <= 0) {
                    break;
                }
                TimeUnit.NANOSECONDS.timedWait(this.lock, j);
            }
            z = this.terminated;
        }
        return z;
    }

    public void awaitTermination() throws InterruptedException {
        synchronized (this.lock) {
            while (!this.terminated) {
                this.lock.wait();
            }
        }
    }

    public boolean isTerminated() {
        boolean z;
        synchronized (this.lock) {
            z = this.terminated;
        }
        return z;
    }

    private void transportClosed(ServerTransport serverTransport) {
        synchronized (this.lock) {
            if (this.transports.remove(serverTransport)) {
                this.channelz.removeServerSocket(this, serverTransport);
                checkForTermination();
            } else {
                throw new AssertionError("Transport already removed");
            }
        }
    }

    private void checkForTermination() {
        synchronized (this.lock) {
            if (this.shutdown && this.transports.isEmpty() && this.transportServerTerminated) {
                if (this.terminated) {
                    throw new AssertionError("Server already terminated");
                }
                this.terminated = true;
                this.channelz.removeServer(this);
                if (this.executor != null) {
                    this.executor = (Executor) this.executorPool.returnObject(this.executor);
                }
                this.lock.notifyAll();
            }
        }
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    public ListenableFuture<ServerStats> getStats() {
        Builder listenSockets = new Builder().setListenSockets(this.transportServer.getListenSockets());
        this.serverCallTracer.updateBuilder(listenSockets);
        ListenableFuture create = SettableFuture.create();
        create.set(listenSockets.build());
        return create;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("logId", this.logId.getId()).add("transportServer", this.transportServer).toString();
    }
}
