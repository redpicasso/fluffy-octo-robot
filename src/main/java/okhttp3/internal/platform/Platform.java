package okhttp3.internal.platform;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.tls.BasicCertificateChainCleaner;
import okhttp3.internal.tls.BasicTrustRootIndex;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;
import okio.Buffer;

public class Platform {
    public static final int INFO = 4;
    private static final Platform PLATFORM = findPlatform();
    public static final int WARN = 5;
    private static final Logger logger = Logger.getLogger(OkHttpClient.class.getName());

    public void afterHandshake(SSLSocket sSLSocket) {
    }

    public void configureSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
    }

    public void configureTlsExtensions(SSLSocket sSLSocket, @Nullable String str, List<Protocol> list) {
    }

    public String getPrefix() {
        return "OkHttp";
    }

    @Nullable
    public String getSelectedProtocol(SSLSocket sSLSocket) {
        return null;
    }

    public boolean isCleartextTrafficPermitted(String str) {
        return true;
    }

    public static Platform get() {
        return PLATFORM;
    }

    @Nullable
    protected X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
        try {
            Object readFieldOrNull = readFieldOrNull(sSLSocketFactory, Class.forName("sun.security.ssl.SSLContextImpl"), "context");
            if (readFieldOrNull == null) {
                return null;
            }
            return (X509TrustManager) readFieldOrNull(readFieldOrNull, X509TrustManager.class, "trustManager");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) throws IOException {
        socket.connect(inetSocketAddress, i);
    }

    public void log(int i, String str, @Nullable Throwable th) {
        logger.log(i == 5 ? Level.WARNING : Level.INFO, str, th);
    }

    public Object getStackTraceForCloseable(String str) {
        return logger.isLoggable(Level.FINE) ? new Throwable(str) : null;
    }

    public void logCloseableLeak(String str, Object obj) {
        if (obj == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(" To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);");
            str = stringBuilder.toString();
        }
        log(5, str, (Throwable) obj);
    }

    public static List<String> alpnProtocolNames(List<Protocol> list) {
        List<String> arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Protocol protocol = (Protocol) list.get(i);
            if (protocol != Protocol.HTTP_1_0) {
                arrayList.add(protocol.toString());
            }
        }
        return arrayList;
    }

    public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager x509TrustManager) {
        return new BasicCertificateChainCleaner(buildTrustRootIndex(x509TrustManager));
    }

    public CertificateChainCleaner buildCertificateChainCleaner(SSLSocketFactory sSLSocketFactory) {
        X509TrustManager trustManager = trustManager(sSLSocketFactory);
        if (trustManager != null) {
            return buildCertificateChainCleaner(trustManager);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to extract the trust manager on ");
        stringBuilder.append(get());
        stringBuilder.append(", sslSocketFactory is ");
        stringBuilder.append(sSLSocketFactory.getClass());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public static boolean isConscryptPreferred() {
        if ("conscrypt".equals(System.getProperty("okhttp.platform"))) {
            return true;
        }
        return "Conscrypt".equals(Security.getProviders()[0].getName());
    }

    private static Platform findPlatform() {
        Platform buildIfSupported = AndroidPlatform.buildIfSupported();
        if (buildIfSupported != null) {
            return buildIfSupported;
        }
        if (isConscryptPreferred()) {
            buildIfSupported = ConscryptPlatform.buildIfSupported();
            if (buildIfSupported != null) {
                return buildIfSupported;
            }
        }
        buildIfSupported = Jdk9Platform.buildIfSupported();
        if (buildIfSupported != null) {
            return buildIfSupported;
        }
        buildIfSupported = JdkWithJettyBootPlatform.buildIfSupported();
        if (buildIfSupported != null) {
            return buildIfSupported;
        }
        return new Platform();
    }

    static byte[] concatLengthPrefixed(List<Protocol> list) {
        Buffer buffer = new Buffer();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Protocol protocol = (Protocol) list.get(i);
            if (protocol != Protocol.HTTP_1_0) {
                buffer.writeByte(protocol.toString().length());
                buffer.writeUtf8(protocol.toString());
            }
        }
        return buffer.readByteArray();
    }

    @Nullable
    static <T> T readFieldOrNull(Object obj, Class<T> cls, String str) {
        Class cls2 = obj.getClass();
        while (cls2 != Object.class) {
            try {
                Field declaredField = cls2.getDeclaredField(str);
                declaredField.setAccessible(true);
                Object obj2 = declaredField.get(obj);
                if (obj2 == null || !cls.isInstance(obj2)) {
                    return null;
                }
                return cls.cast(obj2);
            } catch (NoSuchFieldException unused) {
                cls2 = cls2.getSuperclass();
            } catch (IllegalAccessException unused2) {
                throw new AssertionError();
            }
        }
        String str2 = "delegate";
        if (!str.equals(str2)) {
            obj = readFieldOrNull(obj, Object.class, str2);
            if (obj != null) {
                return readFieldOrNull(obj, cls, str);
            }
        }
        return null;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:7:0x001b, code:
            return javax.net.ssl.SSLContext.getInstance("TLS");
     */
    public javax.net.ssl.SSLContext getSSLContext() {
        /*
        r3 = this;
        r0 = "java.specification.version";
        r0 = java.lang.System.getProperty(r0);
        r1 = "1.7";
        r0 = r1.equals(r0);
        if (r0 == 0) goto L_0x0015;
    L_0x000e:
        r0 = "TLSv1.2";
        r0 = javax.net.ssl.SSLContext.getInstance(r0);	 Catch:{ NoSuchAlgorithmException -> 0x0015 }
        return r0;
    L_0x0015:
        r0 = "TLS";
        r0 = javax.net.ssl.SSLContext.getInstance(r0);	 Catch:{ NoSuchAlgorithmException -> 0x001c }
        return r0;
    L_0x001c:
        r0 = move-exception;
        r1 = new java.lang.IllegalStateException;
        r2 = "No TLS provider";
        r1.<init>(r2, r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.platform.Platform.getSSLContext():javax.net.ssl.SSLContext");
    }

    public TrustRootIndex buildTrustRootIndex(X509TrustManager x509TrustManager) {
        return new BasicTrustRootIndex(x509TrustManager.getAcceptedIssuers());
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
