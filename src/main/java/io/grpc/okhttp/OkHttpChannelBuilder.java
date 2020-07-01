package io.grpc.okhttp;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.squareup.okhttp.CipherSuite;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.ConnectionSpec.Builder;
import com.squareup.okhttp.TlsVersion;
import io.grpc.Attributes;
import io.grpc.ExperimentalApi;
import io.grpc.Internal;
import io.grpc.NameResolver;
import io.grpc.internal.AbstractManagedChannelImplBuilder;
import io.grpc.internal.AtomicBackoff;
import io.grpc.internal.AtomicBackoff.State;
import io.grpc.internal.ClientTransportFactory;
import io.grpc.internal.ClientTransportFactory.ClientTransportOptions;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.KeepAliveManager;
import io.grpc.internal.SharedResourceHolder;
import io.grpc.internal.SharedResourceHolder.Resource;
import io.grpc.internal.TransportTracer.Factory;
import io.grpc.okhttp.internal.Platform;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

@ExperimentalApi("https://github.com/grpc/grpc-java/issues/1785")
public class OkHttpChannelBuilder extends AbstractManagedChannelImplBuilder<OkHttpChannelBuilder> {
    private static final long AS_LARGE_AS_INFINITE = TimeUnit.DAYS.toNanos(1000);
    @Deprecated
    public static final ConnectionSpec DEFAULT_CONNECTION_SPEC = new Builder(ConnectionSpec.MODERN_TLS).cipherSuites(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384).tlsVersions(TlsVersion.TLS_1_2).supportsTlsExtensions(true).build();
    @VisibleForTesting
    static final io.grpc.okhttp.internal.ConnectionSpec INTERNAL_DEFAULT_CONNECTION_SPEC = new io.grpc.okhttp.internal.ConnectionSpec.Builder(io.grpc.okhttp.internal.ConnectionSpec.MODERN_TLS).cipherSuites(io.grpc.okhttp.internal.CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, io.grpc.okhttp.internal.CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, io.grpc.okhttp.internal.CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, io.grpc.okhttp.internal.CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, io.grpc.okhttp.internal.CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, io.grpc.okhttp.internal.CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, io.grpc.okhttp.internal.CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, io.grpc.okhttp.internal.CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384).tlsVersions(io.grpc.okhttp.internal.TlsVersion.TLS_1_2).supportsTlsExtensions(true).build();
    private static final Resource<ExecutorService> SHARED_EXECUTOR = new Resource<ExecutorService>() {
        public ExecutorService create() {
            return Executors.newCachedThreadPool(GrpcUtil.getThreadFactory("grpc-okhttp-%d", true));
        }

        public void close(ExecutorService executorService) {
            executorService.shutdown();
        }
    };
    private io.grpc.okhttp.internal.ConnectionSpec connectionSpec;
    private HostnameVerifier hostnameVerifier;
    private long keepAliveTimeNanos;
    private long keepAliveTimeoutNanos;
    private boolean keepAliveWithoutCalls;
    private NegotiationType negotiationType;
    private ScheduledExecutorService scheduledExecutorService;
    private SSLSocketFactory sslSocketFactory;
    private Executor transportExecutor;

    /* renamed from: io.grpc.okhttp.OkHttpChannelBuilder$2 */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$io$grpc$okhttp$NegotiationType = new int[NegotiationType.values().length];
        static final /* synthetic */ int[] $SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType = new int[NegotiationType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:10:?, code:
            $SwitchMap$io$grpc$okhttp$NegotiationType[io.grpc.okhttp.NegotiationType.PLAINTEXT.ordinal()] = 2;
     */
        static {
            /*
            r0 = io.grpc.okhttp.OkHttpChannelBuilder.NegotiationType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType = r0;
            r0 = 1;
            r1 = $SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = io.grpc.okhttp.OkHttpChannelBuilder.NegotiationType.PLAINTEXT;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = 2;
            r2 = $SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = io.grpc.okhttp.OkHttpChannelBuilder.NegotiationType.TLS;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r2 = io.grpc.okhttp.NegotiationType.values();
            r2 = r2.length;
            r2 = new int[r2];
            $SwitchMap$io$grpc$okhttp$NegotiationType = r2;
            r2 = $SwitchMap$io$grpc$okhttp$NegotiationType;	 Catch:{ NoSuchFieldError -> 0x0032 }
            r3 = io.grpc.okhttp.NegotiationType.TLS;	 Catch:{ NoSuchFieldError -> 0x0032 }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x0032 }
            r2[r3] = r0;	 Catch:{ NoSuchFieldError -> 0x0032 }
        L_0x0032:
            r0 = $SwitchMap$io$grpc$okhttp$NegotiationType;	 Catch:{ NoSuchFieldError -> 0x003c }
            r2 = io.grpc.okhttp.NegotiationType.PLAINTEXT;	 Catch:{ NoSuchFieldError -> 0x003c }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x003c }
            r0[r2] = r1;	 Catch:{ NoSuchFieldError -> 0x003c }
        L_0x003c:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.OkHttpChannelBuilder.2.<clinit>():void");
        }
    }

    private enum NegotiationType {
        TLS,
        PLAINTEXT
    }

    @Internal
    static final class OkHttpTransportFactory implements ClientTransportFactory {
        private boolean closed;
        private final io.grpc.okhttp.internal.ConnectionSpec connectionSpec;
        private final boolean enableKeepAlive;
        private final Executor executor;
        @Nullable
        private final HostnameVerifier hostnameVerifier;
        private final AtomicBackoff keepAliveTimeNanos;
        private final long keepAliveTimeoutNanos;
        private final boolean keepAliveWithoutCalls;
        private final int maxMessageSize;
        @Nullable
        private final SSLSocketFactory socketFactory;
        private final ScheduledExecutorService timeoutService;
        private final Factory transportTracerFactory;
        private final boolean usingSharedExecutor;
        private final boolean usingSharedScheduler;

        /* synthetic */ OkHttpTransportFactory(Executor executor, ScheduledExecutorService scheduledExecutorService, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier, io.grpc.okhttp.internal.ConnectionSpec connectionSpec, int i, boolean z, long j, long j2, boolean z2, Factory factory, AnonymousClass1 anonymousClass1) {
            this(executor, scheduledExecutorService, sSLSocketFactory, hostnameVerifier, connectionSpec, i, z, j, j2, z2, factory);
        }

        private OkHttpTransportFactory(Executor executor, @Nullable ScheduledExecutorService scheduledExecutorService, @Nullable SSLSocketFactory sSLSocketFactory, @Nullable HostnameVerifier hostnameVerifier, io.grpc.okhttp.internal.ConnectionSpec connectionSpec, int i, boolean z, long j, long j2, boolean z2, Factory factory) {
            Executor executor2 = executor;
            boolean z3 = true;
            this.usingSharedScheduler = scheduledExecutorService == null;
            this.timeoutService = this.usingSharedScheduler ? (ScheduledExecutorService) SharedResourceHolder.get(GrpcUtil.TIMER_SERVICE) : scheduledExecutorService;
            this.socketFactory = sSLSocketFactory;
            this.hostnameVerifier = hostnameVerifier;
            this.connectionSpec = connectionSpec;
            this.maxMessageSize = i;
            this.enableKeepAlive = z;
            this.keepAliveTimeNanos = new AtomicBackoff("keepalive time nanos", j);
            this.keepAliveTimeoutNanos = j2;
            this.keepAliveWithoutCalls = z2;
            if (executor2 != null) {
                z3 = false;
            }
            this.usingSharedExecutor = z3;
            this.transportTracerFactory = (Factory) Preconditions.checkNotNull(factory, "transportTracerFactory");
            if (this.usingSharedExecutor) {
                this.executor = (Executor) SharedResourceHolder.get(OkHttpChannelBuilder.SHARED_EXECUTOR);
            } else {
                this.executor = executor2;
            }
        }

        public ConnectionClientTransport newClientTransport(SocketAddress socketAddress, ClientTransportOptions clientTransportOptions) {
            if (this.closed) {
                throw new IllegalStateException("The transport factory is closed.");
            }
            final State state = this.keepAliveTimeNanos.getState();
            ConnectionClientTransport okHttpClientTransport = new OkHttpClientTransport((InetSocketAddress) socketAddress, clientTransportOptions.getAuthority(), clientTransportOptions.getUserAgent(), this.executor, this.socketFactory, this.hostnameVerifier, this.connectionSpec, this.maxMessageSize, clientTransportOptions.getProxyParameters(), new Runnable() {
                public void run() {
                    state.backoff();
                }
            }, this.transportTracerFactory.create());
            if (this.enableKeepAlive) {
                okHttpClientTransport.enableKeepAlive(true, state.get(), this.keepAliveTimeoutNanos, this.keepAliveWithoutCalls);
            }
            return okHttpClientTransport;
        }

        public ScheduledExecutorService getScheduledExecutorService() {
            return this.timeoutService;
        }

        public void close() {
            if (!this.closed) {
                this.closed = true;
                if (this.usingSharedScheduler) {
                    SharedResourceHolder.release(GrpcUtil.TIMER_SERVICE, this.timeoutService);
                }
                if (this.usingSharedExecutor) {
                    SharedResourceHolder.release(OkHttpChannelBuilder.SHARED_EXECUTOR, (ExecutorService) this.executor);
                }
            }
        }
    }

    public static OkHttpChannelBuilder forAddress(String str, int i) {
        return new OkHttpChannelBuilder(str, i);
    }

    public static OkHttpChannelBuilder forTarget(String str) {
        return new OkHttpChannelBuilder(str);
    }

    protected OkHttpChannelBuilder(String str, int i) {
        this(GrpcUtil.authorityFromHostAndPort(str, i));
    }

    private OkHttpChannelBuilder(String str) {
        super(str);
        this.connectionSpec = INTERNAL_DEFAULT_CONNECTION_SPEC;
        this.negotiationType = NegotiationType.TLS;
        this.keepAliveTimeNanos = Long.MAX_VALUE;
        this.keepAliveTimeoutNanos = GrpcUtil.DEFAULT_KEEPALIVE_TIMEOUT_NANOS;
    }

    @VisibleForTesting
    final OkHttpChannelBuilder setTransportTracerFactory(Factory factory) {
        this.transportTracerFactory = factory;
        return this;
    }

    public final OkHttpChannelBuilder transportExecutor(@Nullable Executor executor) {
        this.transportExecutor = executor;
        return this;
    }

    @Deprecated
    public final OkHttpChannelBuilder negotiationType(NegotiationType negotiationType) {
        Preconditions.checkNotNull(negotiationType, "type");
        int i = AnonymousClass2.$SwitchMap$io$grpc$okhttp$NegotiationType[negotiationType.ordinal()];
        if (i == 1) {
            this.negotiationType = NegotiationType.TLS;
        } else if (i == 2) {
            this.negotiationType = NegotiationType.PLAINTEXT;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown negotiation type: ");
            stringBuilder.append(negotiationType);
            throw new AssertionError(stringBuilder.toString());
        }
        return this;
    }

    @Deprecated
    public final OkHttpChannelBuilder enableKeepAlive(boolean z) {
        if (z) {
            return keepAliveTime(GrpcUtil.DEFAULT_KEEPALIVE_TIME_NANOS, TimeUnit.NANOSECONDS);
        }
        return keepAliveTime(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    @Deprecated
    public final OkHttpChannelBuilder enableKeepAlive(boolean z, long j, TimeUnit timeUnit, long j2, TimeUnit timeUnit2) {
        if (z) {
            return keepAliveTime(j, timeUnit).keepAliveTimeout(j2, timeUnit2);
        }
        return keepAliveTime(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    public OkHttpChannelBuilder keepAliveTime(long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j > 0, "keepalive time must be positive");
        this.keepAliveTimeNanos = timeUnit.toNanos(j);
        this.keepAliveTimeNanos = KeepAliveManager.clampKeepAliveTimeInNanos(this.keepAliveTimeNanos);
        if (this.keepAliveTimeNanos >= AS_LARGE_AS_INFINITE) {
            this.keepAliveTimeNanos = Long.MAX_VALUE;
        }
        return this;
    }

    public OkHttpChannelBuilder keepAliveTimeout(long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j > 0, "keepalive timeout must be positive");
        this.keepAliveTimeoutNanos = timeUnit.toNanos(j);
        this.keepAliveTimeoutNanos = KeepAliveManager.clampKeepAliveTimeoutInNanos(this.keepAliveTimeoutNanos);
        return this;
    }

    public OkHttpChannelBuilder keepAliveWithoutCalls(boolean z) {
        this.keepAliveWithoutCalls = z;
        return this;
    }

    public final OkHttpChannelBuilder sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sslSocketFactory = sSLSocketFactory;
        this.negotiationType = NegotiationType.TLS;
        return this;
    }

    public final OkHttpChannelBuilder hostnameVerifier(@Nullable HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    public final OkHttpChannelBuilder connectionSpec(ConnectionSpec connectionSpec) {
        Preconditions.checkArgument(connectionSpec.isTls(), "plaintext ConnectionSpec is not accepted");
        this.connectionSpec = Utils.convertSpec(connectionSpec);
        return this;
    }

    @Deprecated
    public final OkHttpChannelBuilder usePlaintext(boolean z) {
        if (z) {
            negotiationType(NegotiationType.PLAINTEXT);
            return this;
        }
        throw new IllegalArgumentException("Plaintext negotiation not currently supported");
    }

    public final OkHttpChannelBuilder usePlaintext() {
        this.negotiationType = NegotiationType.PLAINTEXT;
        return this;
    }

    public final OkHttpChannelBuilder useTransportSecurity() {
        this.negotiationType = NegotiationType.TLS;
        return this;
    }

    public final OkHttpChannelBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = (ScheduledExecutorService) Preconditions.checkNotNull(scheduledExecutorService, "scheduledExecutorService");
        return this;
    }

    @Internal
    protected final ClientTransportFactory buildTransportFactory() {
        return new OkHttpTransportFactory(this.transportExecutor, this.scheduledExecutorService, createSocketFactory(), this.hostnameVerifier, this.connectionSpec, maxInboundMessageSize(), this.keepAliveTimeNanos != Long.MAX_VALUE, this.keepAliveTimeNanos, this.keepAliveTimeoutNanos, this.keepAliveWithoutCalls, this.transportTracerFactory, null);
    }

    protected Attributes getNameResolverParams() {
        int i = AnonymousClass2.$SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType[this.negotiationType.ordinal()];
        if (i == 1) {
            i = 80;
        } else if (i == 2) {
            i = GrpcUtil.DEFAULT_PORT_SSL;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.negotiationType);
            stringBuilder.append(" not handled");
            throw new AssertionError(stringBuilder.toString());
        }
        return Attributes.newBuilder().set(NameResolver.Factory.PARAMS_DEFAULT_PORT, Integer.valueOf(i)).build();
    }

    @Nullable
    @VisibleForTesting
    SSLSocketFactory createSocketFactory() {
        int i = AnonymousClass2.$SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType[this.negotiationType.ordinal()];
        if (i == 1) {
            return null;
        }
        if (i == 2) {
            try {
                if (this.sslSocketFactory == null) {
                    SSLContext instance;
                    if (GrpcUtil.IS_RESTRICTED_APPENGINE) {
                        instance = SSLContext.getInstance("TLS", Platform.get().getProvider());
                        TrustManagerFactory instance2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                        instance2.init((KeyStore) null);
                        instance.init(null, instance2.getTrustManagers(), SecureRandom.getInstance("SHA1PRNG", Platform.get().getProvider()));
                    } else {
                        instance = SSLContext.getInstance("Default", Platform.get().getProvider());
                    }
                    this.sslSocketFactory = instance.getSocketFactory();
                }
                return this.sslSocketFactory;
            } catch (Throwable e) {
                throw new RuntimeException("TLS Provider failure", e);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown negotiation type: ");
        stringBuilder.append(this.negotiationType);
        throw new RuntimeException(stringBuilder.toString());
    }
}
