package io.grpc.internal;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.logging.type.LogSeverity;
import io.grpc.CallOptions;
import io.grpc.ClientStreamTracer.Factory;
import io.grpc.InternalChannelz.SocketStats;
import io.grpc.InternalLogId;
import io.grpc.InternalMetadata;
import io.grpc.InternalMetadata.TrustedAsciiMarshaller;
import io.grpc.LoadBalancer.PickResult;
import io.grpc.LoadBalancer.Subchannel;
import io.grpc.Metadata;
import io.grpc.Metadata.AsciiMarshaller;
import io.grpc.Metadata.Key;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.Status.Code;
import io.grpc.internal.ClientStreamListener.RpcProgress;
import io.grpc.internal.ClientTransport.PingCallback;
import io.grpc.internal.SharedResourceHolder.Resource;
import io.grpc.internal.StreamListener.MessageProducer;
import java.io.InputStream;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class GrpcUtil {
    public static final Splitter ACCEPT_ENCODING_SPLITTER = Splitter.on(',').trimResults();
    public static final String CONTENT_ACCEPT_ENCODING = "accept-encoding";
    public static final Key<byte[]> CONTENT_ACCEPT_ENCODING_KEY = InternalMetadata.keyOf(CONTENT_ACCEPT_ENCODING, new AcceptEncodingMarshaller());
    public static final String CONTENT_ENCODING = "content-encoding";
    public static final Key<String> CONTENT_ENCODING_KEY = Key.of(CONTENT_ENCODING, Metadata.ASCII_STRING_MARSHALLER);
    public static final String CONTENT_TYPE_GRPC = "application/grpc";
    public static final Key<String> CONTENT_TYPE_KEY = Key.of("content-type", Metadata.ASCII_STRING_MARSHALLER);
    public static final long DEFAULT_KEEPALIVE_TIMEOUT_NANOS = TimeUnit.SECONDS.toNanos(20);
    public static final long DEFAULT_KEEPALIVE_TIME_NANOS = TimeUnit.MINUTES.toNanos(1);
    public static final int DEFAULT_MAX_HEADER_LIST_SIZE = 8192;
    public static final int DEFAULT_MAX_MESSAGE_SIZE = 4194304;
    public static final int DEFAULT_PORT_PLAINTEXT = 80;
    public static final int DEFAULT_PORT_SSL = 443;
    public static final ProxyDetector DEFAULT_PROXY_DETECTOR = new ProxyDetectorImpl();
    public static final long DEFAULT_SERVER_KEEPALIVE_TIMEOUT_NANOS = TimeUnit.SECONDS.toNanos(20);
    public static final long DEFAULT_SERVER_KEEPALIVE_TIME_NANOS = TimeUnit.HOURS.toNanos(2);
    public static final String HTTP_METHOD = "POST";
    private static final String IMPLEMENTATION_VERSION = "1.16.1";
    public static final boolean IS_RESTRICTED_APPENGINE;
    public static final long KEEPALIVE_TIME_NANOS_DISABLED = Long.MAX_VALUE;
    public static final String MESSAGE_ACCEPT_ENCODING = "grpc-accept-encoding";
    public static final Key<byte[]> MESSAGE_ACCEPT_ENCODING_KEY = InternalMetadata.keyOf(MESSAGE_ACCEPT_ENCODING, new AcceptEncodingMarshaller());
    public static final String MESSAGE_ENCODING = "grpc-encoding";
    public static final Key<String> MESSAGE_ENCODING_KEY = Key.of(MESSAGE_ENCODING, Metadata.ASCII_STRING_MARSHALLER);
    public static final ProxyDetector NOOP_PROXY_DETECTOR = new ProxyDetector() {
        @Nullable
        public ProxyParameters proxyFor(SocketAddress socketAddress) {
            return null;
        }
    };
    public static final long SERVER_KEEPALIVE_TIME_NANOS_DISABLED = Long.MAX_VALUE;
    public static final Resource<ExecutorService> SHARED_CHANNEL_EXECUTOR = new Resource<ExecutorService>() {
        private static final String NAME = "grpc-default-executor";

        public String toString() {
            return NAME;
        }

        public ExecutorService create() {
            return Executors.newCachedThreadPool(GrpcUtil.getThreadFactory("grpc-default-executor-%d", true));
        }

        public void close(ExecutorService executorService) {
            executorService.shutdown();
        }
    };
    public static final Supplier<Stopwatch> STOPWATCH_SUPPLIER = new Supplier<Stopwatch>() {
        public Stopwatch get() {
            return Stopwatch.createUnstarted();
        }
    };
    public static final Key<String> TE_HEADER = Key.of("te", Metadata.ASCII_STRING_MARSHALLER);
    public static final String TE_TRAILERS = "trailers";
    public static final String TIMEOUT = "grpc-timeout";
    public static final Key<Long> TIMEOUT_KEY = Key.of(TIMEOUT, new TimeoutMarshaller());
    public static final Resource<ScheduledExecutorService> TIMER_SERVICE = new Resource<ScheduledExecutorService>() {
        public ScheduledExecutorService create() {
            ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1, GrpcUtil.getThreadFactory("grpc-timer-%d", true));
            try {
                newScheduledThreadPool.getClass().getMethod("setRemoveOnCancelPolicy", new Class[]{Boolean.TYPE}).invoke(newScheduledThreadPool, new Object[]{Boolean.valueOf(true)});
            } catch (NoSuchMethodException unused) {
                return newScheduledThreadPool;
            } catch (RuntimeException e) {
                throw e;
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            }
        }

        public void close(ScheduledExecutorService scheduledExecutorService) {
            scheduledExecutorService.shutdown();
        }
    };
    public static final Key<String> USER_AGENT_KEY = Key.of("user-agent", Metadata.ASCII_STRING_MARSHALLER);
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    private static final Logger log = Logger.getLogger(GrpcUtil.class.getName());

    public enum Http2Error {
        NO_ERROR(0, Status.UNAVAILABLE),
        PROTOCOL_ERROR(1, Status.INTERNAL),
        INTERNAL_ERROR(2, Status.INTERNAL),
        FLOW_CONTROL_ERROR(3, Status.INTERNAL),
        SETTINGS_TIMEOUT(4, Status.INTERNAL),
        STREAM_CLOSED(5, Status.INTERNAL),
        FRAME_SIZE_ERROR(6, Status.INTERNAL),
        REFUSED_STREAM(7, Status.UNAVAILABLE),
        CANCEL(8, Status.CANCELLED),
        COMPRESSION_ERROR(9, Status.INTERNAL),
        CONNECT_ERROR(10, Status.INTERNAL),
        ENHANCE_YOUR_CALM(11, Status.RESOURCE_EXHAUSTED.withDescription("Bandwidth exhausted")),
        INADEQUATE_SECURITY(12, Status.PERMISSION_DENIED.withDescription("Permission denied as protocol is not secure enough to call")),
        HTTP_1_1_REQUIRED(13, Status.UNKNOWN);
        
        private static final Http2Error[] codeMap = null;
        private final int code;
        private final Status status;

        static {
            codeMap = buildHttp2CodeMap();
        }

        private static Http2Error[] buildHttp2CodeMap() {
            Http2Error[] values = values();
            Http2Error[] http2ErrorArr = new Http2Error[(((int) values[values.length - 1].code()) + 1)];
            for (Http2Error http2Error : values) {
                http2ErrorArr[(int) http2Error.code()] = http2Error;
            }
            return http2ErrorArr;
        }

        private Http2Error(int i, Status status) {
            this.code = i;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HTTP/2 error code: ");
            stringBuilder.append(name());
            this.status = status.augmentDescription(stringBuilder.toString());
        }

        public long code() {
            return (long) this.code;
        }

        public Status status() {
            return this.status;
        }

        public static Http2Error forCode(long j) {
            Http2Error[] http2ErrorArr = codeMap;
            return (j >= ((long) http2ErrorArr.length) || j < 0) ? null : http2ErrorArr[(int) j];
        }

        public static Status statusForCode(long j) {
            Http2Error forCode = forCode(j);
            if (forCode != null) {
                return forCode.status();
            }
            Status fromCodeValue = Status.fromCodeValue(INTERNAL_ERROR.status().getCode().value());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unrecognized HTTP/2 error code: ");
            stringBuilder.append(j);
            return fromCodeValue.withDescription(stringBuilder.toString());
        }
    }

    @VisibleForTesting
    static class TimeoutMarshaller implements AsciiMarshaller<Long> {
        TimeoutMarshaller() {
        }

        public String toAsciiString(Long l) {
            TimeUnit timeUnit = TimeUnit.NANOSECONDS;
            StringBuilder stringBuilder;
            if (l.longValue() < 0) {
                throw new IllegalArgumentException("Timeout too small");
            } else if (l.longValue() < 100000000) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(l);
                stringBuilder2.append("n");
                return stringBuilder2.toString();
            } else if (l.longValue() < 100000000000L) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(timeUnit.toMicros(l.longValue()));
                stringBuilder.append("u");
                return stringBuilder.toString();
            } else if (l.longValue() < 100000000000000L) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(timeUnit.toMillis(l.longValue()));
                stringBuilder.append("m");
                return stringBuilder.toString();
            } else if (l.longValue() < 100000000000000000L) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(timeUnit.toSeconds(l.longValue()));
                stringBuilder.append(ExifInterface.LATITUDE_SOUTH);
                return stringBuilder.toString();
            } else if (l.longValue() < 6000000000000000000L) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(timeUnit.toMinutes(l.longValue()));
                stringBuilder.append("M");
                return stringBuilder.toString();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(timeUnit.toHours(l.longValue()));
                stringBuilder.append("H");
                return stringBuilder.toString();
            }
        }

        public Long parseAsciiString(String str) {
            Preconditions.checkArgument(str.length() > 0, "empty timeout");
            Preconditions.checkArgument(str.length() <= 9, "bad timeout format");
            long parseLong = Long.parseLong(str.substring(0, str.length() - 1));
            char charAt = str.charAt(str.length() - 1);
            if (charAt == 'H') {
                return Long.valueOf(TimeUnit.HOURS.toNanos(parseLong));
            }
            if (charAt == 'M') {
                return Long.valueOf(TimeUnit.MINUTES.toNanos(parseLong));
            }
            if (charAt == 'S') {
                return Long.valueOf(TimeUnit.SECONDS.toNanos(parseLong));
            }
            if (charAt == 'u') {
                return Long.valueOf(TimeUnit.MICROSECONDS.toNanos(parseLong));
            }
            if (charAt == 'm') {
                return Long.valueOf(TimeUnit.MILLISECONDS.toNanos(parseLong));
            }
            if (charAt == 'n') {
                return Long.valueOf(parseLong);
            }
            throw new IllegalArgumentException(String.format("Invalid timeout unit: %s", new Object[]{Character.valueOf(charAt)}));
        }
    }

    private static final class AcceptEncodingMarshaller implements TrustedAsciiMarshaller<byte[]> {
        public byte[] parseAsciiString(byte[] bArr) {
            return bArr;
        }

        public byte[] toAsciiString(byte[] bArr) {
            return bArr;
        }

        private AcceptEncodingMarshaller() {
        }

        /* synthetic */ AcceptEncodingMarshaller(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    static {
        boolean z;
        if (System.getProperty("com.google.appengine.runtime.environment") != null) {
            if ("1.7".equals(System.getProperty("java.specification.version"))) {
                z = true;
                IS_RESTRICTED_APPENGINE = z;
            }
        }
        z = false;
        IS_RESTRICTED_APPENGINE = z;
    }

    public static ProxyDetector getDefaultProxyDetector() {
        if (IS_RESTRICTED_APPENGINE) {
            return NOOP_PROXY_DETECTOR;
        }
        return DEFAULT_PROXY_DETECTOR;
    }

    public static Status httpStatusToGrpcStatus(int i) {
        Status toStatus = httpStatusToGrpcCode(i).toStatus();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP status code ");
        stringBuilder.append(i);
        return toStatus.withDescription(stringBuilder.toString());
    }

    private static Code httpStatusToGrpcCode(int i) {
        if (i >= 100 && i < LogSeverity.INFO_VALUE) {
            return Code.INTERNAL;
        }
        if (i != 400) {
            if (i == 401) {
                return Code.UNAUTHENTICATED;
            }
            if (i == 403) {
                return Code.PERMISSION_DENIED;
            }
            if (i == 404) {
                return Code.UNIMPLEMENTED;
            }
            if (i != 429) {
                if (i != 431) {
                    switch (i) {
                        case 502:
                        case 503:
                        case 504:
                            break;
                        default:
                            return Code.UNKNOWN;
                    }
                }
            }
            return Code.UNAVAILABLE;
        }
        return Code.INTERNAL;
    }

    public static boolean isGrpcContentType(String str) {
        boolean z = false;
        if (str == null || 16 > str.length()) {
            return false;
        }
        str = str.toLowerCase();
        if (!str.startsWith(CONTENT_TYPE_GRPC)) {
            return false;
        }
        if (str.length() == 16) {
            return true;
        }
        char charAt = str.charAt(16);
        if (charAt == '+' || charAt == ';') {
            z = true;
        }
        return z;
    }

    public static String getGrpcUserAgent(String str, @Nullable String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        if (str2 != null) {
            stringBuilder.append(str2);
            stringBuilder.append(' ');
        }
        stringBuilder.append("grpc-java-");
        stringBuilder.append(str);
        stringBuilder.append('/');
        stringBuilder.append(IMPLEMENTATION_VERSION);
        return stringBuilder.toString();
    }

    public static URI authorityToUri(String str) {
        Preconditions.checkNotNull(str, "authority");
        try {
            return new URI(null, str, null, null, null);
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid authority: ");
            stringBuilder.append(str);
            throw new IllegalArgumentException(stringBuilder.toString(), e);
        }
    }

    public static String checkAuthority(String str) {
        URI authorityToUri = authorityToUri(str);
        boolean z = true;
        Preconditions.checkArgument(authorityToUri.getHost() != null, "No host in authority '%s'", (Object) str);
        if (authorityToUri.getUserInfo() != null) {
            z = false;
        }
        Preconditions.checkArgument(z, "Userinfo must not be present on authority: '%s'", (Object) str);
        return str;
    }

    public static String authorityFromHostAndPort(String str, int i) {
        try {
            str = new URI(null, null, str, i, null, null, null).getAuthority();
            return str;
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid host or port: ");
            stringBuilder.append(str);
            stringBuilder.append(" ");
            stringBuilder.append(i);
            throw new IllegalArgumentException(stringBuilder.toString(), e);
        }
    }

    public static ThreadFactory getThreadFactory(String str, boolean z) {
        if (IS_RESTRICTED_APPENGINE) {
            return MoreExecutors.platformThreadFactory();
        }
        return new ThreadFactoryBuilder().setDaemon(z).setNameFormat(str).build();
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x0014 A:{ExcHandler: java.lang.NoSuchMethodException (unused java.lang.NoSuchMethodException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x0014 A:{ExcHandler: java.lang.NoSuchMethodException (unused java.lang.NoSuchMethodException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Missing block: B:4:0x0018, code:
            return r4.getHostName();
     */
    public static java.lang.String getHost(java.net.InetSocketAddress r4) {
        /*
        r0 = java.net.InetSocketAddress.class;
        r1 = "getHostString";
        r2 = 0;
        r3 = new java.lang.Class[r2];	 Catch:{ NoSuchMethodException -> 0x0014, NoSuchMethodException -> 0x0014, NoSuchMethodException -> 0x0014 }
        r0 = r0.getMethod(r1, r3);	 Catch:{ NoSuchMethodException -> 0x0014, NoSuchMethodException -> 0x0014, NoSuchMethodException -> 0x0014 }
        r1 = new java.lang.Object[r2];	 Catch:{ NoSuchMethodException -> 0x0014, NoSuchMethodException -> 0x0014, NoSuchMethodException -> 0x0014 }
        r0 = r0.invoke(r4, r1);	 Catch:{ NoSuchMethodException -> 0x0014, NoSuchMethodException -> 0x0014, NoSuchMethodException -> 0x0014 }
        r0 = (java.lang.String) r0;	 Catch:{ NoSuchMethodException -> 0x0014, NoSuchMethodException -> 0x0014, NoSuchMethodException -> 0x0014 }
        return r0;
    L_0x0014:
        r4 = r4.getHostName();
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.GrpcUtil.getHost(java.net.InetSocketAddress):java.lang.String");
    }

    @Nullable
    static ClientTransport getTransportFromPickResult(PickResult pickResult, boolean z) {
        Subchannel subchannel = pickResult.getSubchannel();
        final ClientTransport obtainActiveTransport = subchannel != null ? ((AbstractSubchannel) subchannel).obtainActiveTransport() : null;
        if (obtainActiveTransport != null) {
            final Factory streamTracerFactory = pickResult.getStreamTracerFactory();
            if (streamTracerFactory == null) {
                return obtainActiveTransport;
            }
            return new ClientTransport() {
                public ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions) {
                    return obtainActiveTransport.newStream(methodDescriptor, metadata, callOptions.withStreamTracerFactory(streamTracerFactory));
                }

                public void ping(PingCallback pingCallback, Executor executor) {
                    obtainActiveTransport.ping(pingCallback, executor);
                }

                public InternalLogId getLogId() {
                    return obtainActiveTransport.getLogId();
                }

                public ListenableFuture<SocketStats> getStats() {
                    return obtainActiveTransport.getStats();
                }
            };
        }
        if (!pickResult.getStatus().isOk()) {
            if (pickResult.isDrop()) {
                return new FailingClientTransport(pickResult.getStatus(), RpcProgress.DROPPED);
            }
            if (!z) {
                return new FailingClientTransport(pickResult.getStatus(), RpcProgress.PROCESSED);
            }
        }
        return null;
    }

    static void closeQuietly(MessageProducer messageProducer) {
        while (true) {
            InputStream next = messageProducer.next();
            if (next != null) {
                closeQuietly(next);
            } else {
                return;
            }
        }
    }

    public static void closeQuietly(@Nullable InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Throwable e) {
                log.log(Level.WARNING, "exception caught in closeQuietly", e);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x000c A:{RETURN, ExcHandler: java.lang.NullPointerException (unused java.lang.NullPointerException), Splitter: B:3:0x0007} */
    /* JADX WARNING: Missing block: B:6:0x000c, code:
            return false;
     */
    static <T> boolean iterableContains(java.lang.Iterable<T> r2, T r3) {
        /*
        r0 = r2 instanceof java.util.Collection;
        r1 = 0;
        if (r0 == 0) goto L_0x000d;
    L_0x0005:
        r2 = (java.util.Collection) r2;
        r2 = r2.contains(r3);	 Catch:{ NullPointerException -> 0x000c, NullPointerException -> 0x000c }
        return r2;
    L_0x000c:
        return r1;
    L_0x000d:
        r2 = r2.iterator();
    L_0x0011:
        r0 = r2.hasNext();
        if (r0 == 0) goto L_0x0023;
    L_0x0017:
        r0 = r2.next();
        r0 = com.google.common.base.Objects.equal(r0, r3);
        if (r0 == 0) goto L_0x0011;
    L_0x0021:
        r2 = 1;
        return r2;
    L_0x0023:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.internal.GrpcUtil.iterableContains(java.lang.Iterable, java.lang.Object):boolean");
    }

    private GrpcUtil() {
    }
}
