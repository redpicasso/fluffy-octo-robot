package io.grpc.okhttp.internal;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import okio.Buffer;

public class Platform {
    private static final String[] ANDROID_SECURITY_PROVIDERS = new String[]{"com.google.android.gms.org.conscrypt.OpenSSLProvider", "org.conscrypt.OpenSSLProvider", "com.android.org.conscrypt.OpenSSLProvider", "org.apache.harmony.xnet.provider.jsse.OpenSSLProvider", "com.google.android.libraries.stitch.sslguard.SslGuardProvider"};
    private static final Platform PLATFORM = findPlatform();
    public static final Logger logger = Logger.getLogger(Platform.class.getName());
    private final Provider sslProvider;

    private static class JettyNegoProvider implements InvocationHandler {
        private final List<String> protocols;
        private String selected;
        private boolean unsupported;

        public JettyNegoProvider(List<String> list) {
            this.protocols = list;
        }

        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            String name = method.getName();
            Class returnType = method.getReturnType();
            if (objArr == null) {
                objArr = Util.EMPTY_STRING_ARRAY;
            }
            if (name.equals("supports") && Boolean.TYPE == returnType) {
                return Boolean.valueOf(true);
            }
            if (name.equals("unsupported") && Void.TYPE == returnType) {
                this.unsupported = true;
                return null;
            } else if (name.equals("protocols") && objArr.length == 0) {
                return this.protocols;
            } else {
                if ((name.equals("selectProtocol") || name.equals("select")) && String.class == returnType && objArr.length == 1 && (objArr[0] instanceof List)) {
                    List list = (List) objArr[0];
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (this.protocols.contains(list.get(i))) {
                            name = (String) list.get(i);
                            this.selected = name;
                            return name;
                        }
                    }
                    name = (String) this.protocols.get(0);
                    this.selected = name;
                    return name;
                } else if ((!name.equals("protocolSelected") && !name.equals("selected")) || objArr.length != 1) {
                    return method.invoke(this, objArr);
                } else {
                    this.selected = (String) objArr[0];
                    return null;
                }
            }
        }
    }

    public enum TlsExtensionType {
        ALPN_AND_NPN,
        NPN,
        NONE
    }

    private static class Android extends Platform {
        private final OptionalMethod<Socket> getAlpnSelectedProtocol;
        private final OptionalMethod<Socket> setAlpnProtocols;
        private final OptionalMethod<Socket> setHostname;
        private final OptionalMethod<Socket> setUseSessionTickets;
        private final TlsExtensionType tlsExtensionType;
        private final Method trafficStatsTagSocket;
        private final Method trafficStatsUntagSocket;

        public Android(OptionalMethod<Socket> optionalMethod, OptionalMethod<Socket> optionalMethod2, Method method, Method method2, OptionalMethod<Socket> optionalMethod3, OptionalMethod<Socket> optionalMethod4, Provider provider, TlsExtensionType tlsExtensionType) {
            super(provider);
            this.setUseSessionTickets = optionalMethod;
            this.setHostname = optionalMethod2;
            this.trafficStatsTagSocket = method;
            this.trafficStatsUntagSocket = method2;
            this.getAlpnSelectedProtocol = optionalMethod3;
            this.setAlpnProtocols = optionalMethod4;
            this.tlsExtensionType = tlsExtensionType;
        }

        public TlsExtensionType getTlsExtensionType() {
            return this.tlsExtensionType;
        }

        public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) throws IOException {
            try {
                socket.connect(inetSocketAddress, i);
            } catch (Throwable e) {
                IOException iOException = new IOException("Exception in connect");
                iOException.initCause(e);
                throw iOException;
            }
        }

        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
            if (str != null) {
                this.setUseSessionTickets.invokeOptionalWithoutCheckedException(sSLSocket, Boolean.valueOf(true));
                this.setHostname.invokeOptionalWithoutCheckedException(sSLSocket, str);
            }
            if (this.setAlpnProtocols.isSupported(sSLSocket)) {
                this.setAlpnProtocols.invokeWithoutCheckedException(sSLSocket, Platform.concatLengthPrefixed(list));
            }
        }

        public String getSelectedProtocol(SSLSocket sSLSocket) {
            String str = null;
            if (!this.getAlpnSelectedProtocol.isSupported(sSLSocket)) {
                return null;
            }
            byte[] bArr = (byte[]) this.getAlpnSelectedProtocol.invokeWithoutCheckedException(sSLSocket, new Object[0]);
            if (bArr != null) {
                str = new String(bArr, Util.UTF_8);
            }
            return str;
        }

        public void tagSocket(Socket socket) throws SocketException {
            Method method = this.trafficStatsTagSocket;
            if (method != null) {
                try {
                    method.invoke(null, new Object[]{socket});
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2.getCause());
                }
            }
        }

        public void untagSocket(Socket socket) throws SocketException {
            Method method = this.trafficStatsUntagSocket;
            if (method != null) {
                try {
                    method.invoke(null, new Object[]{socket});
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2.getCause());
                }
            }
        }
    }

    private static class JdkAlpnPlatform extends Platform {
        private final Method getApplicationProtocol;
        private final Method setApplicationProtocols;

        /* synthetic */ JdkAlpnPlatform(Provider provider, Method method, Method method2, AnonymousClass1 anonymousClass1) {
            this(provider, method, method2);
        }

        private JdkAlpnPlatform(Provider provider, Method method, Method method2) {
            super(provider);
            this.setApplicationProtocols = method;
            this.getApplicationProtocol = method2;
        }

        public TlsExtensionType getTlsExtensionType() {
            return TlsExtensionType.ALPN_AND_NPN;
        }

        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
            SSLParameters sSLParameters = sSLSocket.getSSLParameters();
            List arrayList = new ArrayList(list.size());
            for (Protocol protocol : list) {
                if (protocol != Protocol.HTTP_1_0) {
                    arrayList.add(protocol.toString());
                }
            }
            try {
                this.setApplicationProtocols.invoke(sSLParameters, new Object[]{arrayList.toArray(new String[arrayList.size()])});
                sSLSocket.setSSLParameters(sSLParameters);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            }
        }

        public String getSelectedProtocol(SSLSocket sSLSocket) {
            try {
                return (String) this.getApplicationProtocol.invoke(sSLSocket, new Object[0]);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    private static class JdkWithJettyBootPlatform extends Platform {
        private final Class<?> clientProviderClass;
        private final Method getMethod;
        private final Method putMethod;
        private final Method removeMethod;
        private final Class<?> serverProviderClass;

        public JdkWithJettyBootPlatform(Method method, Method method2, Method method3, Class<?> cls, Class<?> cls2, Provider provider) {
            super(provider);
            this.putMethod = method;
            this.getMethod = method2;
            this.removeMethod = method3;
            this.clientProviderClass = cls;
            this.serverProviderClass = cls2;
        }

        public TlsExtensionType getTlsExtensionType() {
            return TlsExtensionType.ALPN_AND_NPN;
        }

        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
            List arrayList = new ArrayList(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Protocol protocol = (Protocol) list.get(i);
                if (protocol != Protocol.HTTP_1_0) {
                    arrayList.add(protocol.toString());
                }
            }
            try {
                Object newProxyInstance = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(arrayList));
                this.putMethod.invoke(null, new Object[]{sSLSocket, newProxyInstance});
            } catch (InvocationTargetException e) {
                throw new AssertionError(e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        public void afterHandshake(SSLSocket sSLSocket) {
            try {
                this.removeMethod.invoke(null, new Object[]{sSLSocket});
            } catch (IllegalAccessException unused) {
                throw new AssertionError();
            } catch (InvocationTargetException unused2) {
            }
        }

        public String getSelectedProtocol(SSLSocket sSLSocket) {
            try {
                Object[] objArr = new Object[]{sSLSocket};
                String str = null;
                JettyNegoProvider jettyNegoProvider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, objArr));
                if (jettyNegoProvider.unsupported || jettyNegoProvider.selected != null) {
                    if (!jettyNegoProvider.unsupported) {
                        str = jettyNegoProvider.selected;
                    }
                    return str;
                }
                logger.log(Level.INFO, "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?");
                return null;
            } catch (InvocationTargetException unused) {
                throw new AssertionError();
            } catch (IllegalAccessException unused2) {
                throw new AssertionError();
            }
        }
    }

    public void afterHandshake(SSLSocket sSLSocket) {
    }

    public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
    }

    public String getPrefix() {
        return "OkHttp";
    }

    public String getSelectedProtocol(SSLSocket sSLSocket) {
        return null;
    }

    public void tagSocket(Socket socket) throws SocketException {
    }

    public void untagSocket(Socket socket) throws SocketException {
    }

    public static Platform get() {
        return PLATFORM;
    }

    public Platform(Provider provider) {
        this.sslProvider = provider;
    }

    public void logW(String str) {
        System.out.println(str);
    }

    public Provider getProvider() {
        return this.sslProvider;
    }

    public TlsExtensionType getTlsExtensionType() {
        return TlsExtensionType.NONE;
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) throws IOException {
        socket.connect(inetSocketAddress, i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:? A:{Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }, ExcHandler: java.security.NoSuchAlgorithmException (unused java.security.NoSuchAlgorithmException), Splitter: B:38:0x00f6} */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A:{Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }, ExcHandler: java.security.NoSuchAlgorithmException (unused java.security.NoSuchAlgorithmException), Splitter: B:38:0x00f6} */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A:{Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }, ExcHandler: java.security.NoSuchAlgorithmException (unused java.security.NoSuchAlgorithmException), Splitter: B:38:0x00f6} */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A:{Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }, ExcHandler: java.security.NoSuchAlgorithmException (unused java.security.NoSuchAlgorithmException), Splitter: B:38:0x00f6} */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0065 A:{ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:7:0x0045} */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0066 A:{PHI: r9 , ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:9:0x0057} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x016a A:{ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:38:0x00f6} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:12:0x0065, code:
            r9 = null;
     */
    /* JADX WARNING: Missing block: B:13:0x0066, code:
            r5 = null;
     */
    /* JADX WARNING: Missing block: B:39:?, code:
            r1 = "org.eclipse.jetty.alpn.ALPN";
            r4 = java.lang.Class.forName(r1);
            r5 = new java.lang.StringBuilder();
            r5.append(r1);
            r5.append("$Provider");
            r5 = java.lang.Class.forName(r5.toString());
            r6 = new java.lang.StringBuilder();
            r6.append(r1);
            r6.append("$ClientProvider");
            r8 = java.lang.Class.forName(r6.toString());
            r6 = new java.lang.StringBuilder();
            r6.append(r1);
            r6.append("$ServerProvider");
     */
    /* JADX WARNING: Missing block: B:40:0x0169, code:
            return new io.grpc.okhttp.internal.Platform.JdkWithJettyBootPlatform(r4.getMethod("put", new java.lang.Class[]{javax.net.ssl.SSLSocket.class, r5}), r4.getMethod("get", new java.lang.Class[]{javax.net.ssl.SSLSocket.class}), r4.getMethod("remove", new java.lang.Class[]{javax.net.ssl.SSLSocket.class}), r8, java.lang.Class.forName(r6.toString()), r3);
     */
    /* JADX WARNING: Missing block: B:42:0x016f, code:
            return new io.grpc.okhttp.internal.Platform(r3);
     */
    private static io.grpc.okhttp.internal.Platform findPlatform() {
        /*
        r0 = io.grpc.internal.GrpcUtil.IS_RESTRICTED_APPENGINE;
        if (r0 == 0) goto L_0x0009;
    L_0x0004:
        r0 = getAppEngineProvider();
        goto L_0x000d;
    L_0x0009:
        r0 = getAndroidSecurityProvider();
    L_0x000d:
        r8 = r0;
        r0 = 1;
        r1 = 0;
        r2 = 0;
        if (r8 == 0) goto L_0x00b5;
    L_0x0013:
        r3 = new io.grpc.okhttp.internal.OptionalMethod;
        r4 = new java.lang.Class[r0];
        r5 = java.lang.Boolean.TYPE;
        r4[r2] = r5;
        r5 = "setUseSessionTickets";
        r3.<init>(r1, r5, r4);
        r4 = new io.grpc.okhttp.internal.OptionalMethod;
        r5 = new java.lang.Class[r0];
        r6 = java.lang.String.class;
        r5[r2] = r6;
        r6 = "setHostname";
        r4.<init>(r1, r6, r5);
        r6 = new io.grpc.okhttp.internal.OptionalMethod;
        r5 = byte[].class;
        r7 = new java.lang.Class[r2];
        r9 = "getAlpnSelectedProtocol";
        r6.<init>(r5, r9, r7);
        r7 = new io.grpc.okhttp.internal.OptionalMethod;
        r5 = new java.lang.Class[r0];
        r9 = byte[].class;
        r5[r2] = r9;
        r9 = "setAlpnProtocols";
        r7.<init>(r1, r9, r5);
        r5 = "android.net.TrafficStats";
        r5 = java.lang.Class.forName(r5);	 Catch:{ ClassNotFoundException -> 0x0065, ClassNotFoundException -> 0x0065 }
        r9 = "tagSocket";
        r10 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x0065, ClassNotFoundException -> 0x0065 }
        r11 = java.net.Socket.class;
        r10[r2] = r11;	 Catch:{ ClassNotFoundException -> 0x0065, ClassNotFoundException -> 0x0065 }
        r9 = r5.getMethod(r9, r10);	 Catch:{ ClassNotFoundException -> 0x0065, ClassNotFoundException -> 0x0065 }
        r10 = "untagSocket";
        r0 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x0066, ClassNotFoundException -> 0x0066 }
        r11 = java.net.Socket.class;
        r0[r2] = r11;	 Catch:{ ClassNotFoundException -> 0x0066, ClassNotFoundException -> 0x0066 }
        r0 = r5.getMethod(r10, r0);	 Catch:{ ClassNotFoundException -> 0x0066, ClassNotFoundException -> 0x0066 }
        r5 = r0;
        goto L_0x0067;
    L_0x0065:
        r9 = r1;
    L_0x0066:
        r5 = r1;
    L_0x0067:
        r0 = io.grpc.internal.GrpcUtil.IS_RESTRICTED_APPENGINE;
        if (r0 == 0) goto L_0x006e;
    L_0x006b:
        r0 = io.grpc.okhttp.internal.Platform.TlsExtensionType.ALPN_AND_NPN;
        goto L_0x00aa;
    L_0x006e:
        r0 = r8.getName();
        r1 = "GmsCore_OpenSSL";
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x00a8;
    L_0x007a:
        r0 = r8.getName();
        r1 = "Conscrypt";
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x00a8;
    L_0x0086:
        r0 = r8.getName();
        r1 = "Ssl_Guard";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0093;
    L_0x0092:
        goto L_0x00a8;
    L_0x0093:
        r0 = isAtLeastAndroid5();
        if (r0 == 0) goto L_0x009c;
    L_0x0099:
        r0 = io.grpc.okhttp.internal.Platform.TlsExtensionType.ALPN_AND_NPN;
        goto L_0x00aa;
    L_0x009c:
        r0 = isAtLeastAndroid41();
        if (r0 == 0) goto L_0x00a5;
    L_0x00a2:
        r0 = io.grpc.okhttp.internal.Platform.TlsExtensionType.NPN;
        goto L_0x00aa;
    L_0x00a5:
        r0 = io.grpc.okhttp.internal.Platform.TlsExtensionType.NONE;
        goto L_0x00aa;
    L_0x00a8:
        r0 = io.grpc.okhttp.internal.Platform.TlsExtensionType.ALPN_AND_NPN;
    L_0x00aa:
        r10 = new io.grpc.okhttp.internal.Platform$Android;
        r1 = r10;
        r2 = r3;
        r3 = r4;
        r4 = r9;
        r9 = r0;
        r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9);
        return r10;
    L_0x00b5:
        r3 = javax.net.ssl.SSLContext.getDefault();	 Catch:{ NoSuchAlgorithmException -> 0x0170 }
        r3 = r3.getProvider();	 Catch:{ NoSuchAlgorithmException -> 0x0170 }
        r4 = "TLS";
        r4 = javax.net.ssl.SSLContext.getInstance(r4, r3);	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r4.init(r1, r1, r1);	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r4 = r4.createSSLEngine();	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r5 = new io.grpc.okhttp.internal.Platform$1;	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r5.<init>();	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r5 = java.security.AccessController.doPrivileged(r5);	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r5 = (java.lang.reflect.Method) r5;	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r6 = new java.lang.Object[r2];	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r5.invoke(r4, r6);	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r4 = new io.grpc.okhttp.internal.Platform$2;	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r4.<init>();	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r4 = java.security.AccessController.doPrivileged(r4);	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r4 = (java.lang.reflect.Method) r4;	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r5 = new io.grpc.okhttp.internal.Platform$3;	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r5.<init>();	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r5 = java.security.AccessController.doPrivileged(r5);	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r5 = (java.lang.reflect.Method) r5;	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r6 = new io.grpc.okhttp.internal.Platform$JdkAlpnPlatform;	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        r6.<init>(r3, r4, r5, r1);	 Catch:{ NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6, NoSuchAlgorithmException -> 0x00f6 }
        return r6;
    L_0x00f6:
        r1 = "org.eclipse.jetty.alpn.ALPN";
        r4 = java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r5 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r5.<init>();	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r5.append(r1);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r6 = "$Provider";
        r5.append(r6);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r5 = r5.toString();	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r5 = java.lang.Class.forName(r5);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r6 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r6.<init>();	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r6.append(r1);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r7 = "$ClientProvider";
        r6.append(r7);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r6 = r6.toString();	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r8 = java.lang.Class.forName(r6);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r6 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r6.<init>();	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r6.append(r1);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r1 = "$ServerProvider";
        r6.append(r1);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r1 = r6.toString();	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r9 = java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r1 = "put";
        r6 = 2;
        r6 = new java.lang.Class[r6];	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r7 = javax.net.ssl.SSLSocket.class;
        r6[r2] = r7;	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r6[r0] = r5;	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r5 = r4.getMethod(r1, r6);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r1 = "get";
        r6 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r7 = javax.net.ssl.SSLSocket.class;
        r6[r2] = r7;	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r6 = r4.getMethod(r1, r6);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r1 = "remove";
        r0 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r7 = javax.net.ssl.SSLSocket.class;
        r0[r2] = r7;	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r7 = r4.getMethod(r1, r0);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r0 = new io.grpc.okhttp.internal.Platform$JdkWithJettyBootPlatform;	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        r4 = r0;
        r10 = r3;
        r4.<init>(r5, r6, r7, r8, r9, r10);	 Catch:{ ClassNotFoundException -> 0x016a, ClassNotFoundException -> 0x016a }
        return r0;
    L_0x016a:
        r0 = new io.grpc.okhttp.internal.Platform;
        r0.<init>(r3);
        return r0;
    L_0x0170:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r1.<init>(r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.okhttp.internal.Platform.findPlatform():io.grpc.okhttp.internal.Platform");
    }

    private static boolean isAtLeastAndroid5() {
        try {
            Platform.class.getClassLoader().loadClass("android.net.Network");
            return true;
        } catch (Throwable e) {
            logger.log(Level.FINE, "Can't find class", e);
            return false;
        }
    }

    private static boolean isAtLeastAndroid41() {
        try {
            Platform.class.getClassLoader().loadClass("android.app.ActivityOptions");
            return true;
        } catch (Throwable e) {
            logger.log(Level.FINE, "Can't find class", e);
            return false;
        }
    }

    private static Provider getAppEngineProvider() {
        try {
            return (Provider) Class.forName("org.conscrypt.OpenSSLProvider").getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable th) {
            RuntimeException runtimeException = new RuntimeException("Unable to load conscrypt security provider", th);
        }
    }

    private static Provider getAndroidSecurityProvider() {
        for (Provider provider : Security.getProviders()) {
            for (String str : ANDROID_SECURITY_PROVIDERS) {
                if (str.equals(provider.getClass().getName())) {
                    logger.log(Level.FINE, "Found registered provider {0}", str);
                    return provider;
                }
            }
        }
        logger.log(Level.WARNING, "Unable to find Conscrypt");
        return null;
    }

    public static byte[] concatLengthPrefixed(List<Protocol> list) {
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
}
