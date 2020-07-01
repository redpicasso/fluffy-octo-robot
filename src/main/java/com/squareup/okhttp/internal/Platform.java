package com.squareup.okhttp.internal;

import android.util.Log;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.tls.AndroidTrustRootIndex;
import com.squareup.okhttp.internal.tls.RealTrustRootIndex;
import com.squareup.okhttp.internal.tls.TrustRootIndex;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okio.Buffer;

public class Platform {
    private static final Platform PLATFORM = findPlatform();

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

    private static class Android extends Platform {
        private static final int MAX_LOG_LENGTH = 4000;
        private final OptionalMethod<Socket> getAlpnSelectedProtocol;
        private final OptionalMethod<Socket> setAlpnProtocols;
        private final OptionalMethod<Socket> setHostname;
        private final OptionalMethod<Socket> setUseSessionTickets;
        private final Class<?> sslParametersClass;
        private final Method trafficStatsTagSocket;
        private final Method trafficStatsUntagSocket;

        public Android(Class<?> cls, OptionalMethod<Socket> optionalMethod, OptionalMethod<Socket> optionalMethod2, Method method, Method method2, OptionalMethod<Socket> optionalMethod3, OptionalMethod<Socket> optionalMethod4) {
            this.sslParametersClass = cls;
            this.setUseSessionTickets = optionalMethod;
            this.setHostname = optionalMethod2;
            this.trafficStatsTagSocket = method;
            this.trafficStatsUntagSocket = method2;
            this.getAlpnSelectedProtocol = optionalMethod3;
            this.setAlpnProtocols = optionalMethod4;
        }

        public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) throws IOException {
            try {
                socket.connect(inetSocketAddress, i);
            } catch (Throwable e) {
                if (Util.isAndroidGetsocknameError(e)) {
                    throw new IOException(e);
                }
                throw e;
            } catch (Throwable e2) {
                IOException iOException = new IOException("Exception in connect");
                iOException.initCause(e2);
                throw iOException;
            }
        }

        public X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
            String str = "sslParameters";
            Object readFieldOrNull = Platform.readFieldOrNull(sSLSocketFactory, this.sslParametersClass, str);
            if (readFieldOrNull == null) {
                try {
                    readFieldOrNull = Platform.readFieldOrNull(sSLSocketFactory, Class.forName("com.google.android.gms.org.conscrypt.SSLParametersImpl", false, sSLSocketFactory.getClass().getClassLoader()), str);
                } catch (ClassNotFoundException unused) {
                    return null;
                }
            }
            X509TrustManager x509TrustManager = (X509TrustManager) Platform.readFieldOrNull(readFieldOrNull, X509TrustManager.class, "x509TrustManager");
            if (x509TrustManager != null) {
                return x509TrustManager;
            }
            return (X509TrustManager) Platform.readFieldOrNull(readFieldOrNull, X509TrustManager.class, "trustManager");
        }

        public TrustRootIndex trustRootIndex(X509TrustManager x509TrustManager) {
            TrustRootIndex trustRootIndex = AndroidTrustRootIndex.get(x509TrustManager);
            if (trustRootIndex != null) {
                return trustRootIndex;
            }
            return super.trustRootIndex(x509TrustManager);
        }

        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
            if (str != null) {
                this.setUseSessionTickets.invokeOptionalWithoutCheckedException(sSLSocket, Boolean.valueOf(true));
                this.setHostname.invokeOptionalWithoutCheckedException(sSLSocket, str);
            }
            OptionalMethod optionalMethod = this.setAlpnProtocols;
            if (optionalMethod != null && optionalMethod.isSupported(sSLSocket)) {
                this.setAlpnProtocols.invokeWithoutCheckedException(sSLSocket, Platform.concatLengthPrefixed(list));
            }
        }

        public String getSelectedProtocol(SSLSocket sSLSocket) {
            OptionalMethod optionalMethod = this.getAlpnSelectedProtocol;
            String str = null;
            if (optionalMethod == null || !optionalMethod.isSupported(sSLSocket)) {
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

        public void log(String str) {
            int length = str.length();
            int i = 0;
            while (i < length) {
                int min;
                int indexOf = str.indexOf(10, i);
                if (indexOf == -1) {
                    indexOf = length;
                }
                while (true) {
                    min = Math.min(indexOf, i + MAX_LOG_LENGTH);
                    Log.d("OkHttp", str.substring(i, min));
                    if (min >= indexOf) {
                        break;
                    }
                    i = min;
                }
                i = min + 1;
            }
        }
    }

    private static class JdkPlatform extends Platform {
        private final Class<?> sslContextClass;

        public JdkPlatform(Class<?> cls) {
            this.sslContextClass = cls;
        }

        public X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
            Object readFieldOrNull = Platform.readFieldOrNull(sSLSocketFactory, this.sslContextClass, "context");
            if (readFieldOrNull == null) {
                return null;
            }
            return (X509TrustManager) Platform.readFieldOrNull(readFieldOrNull, X509TrustManager.class, "trustManager");
        }
    }

    private static class JdkWithJettyBootPlatform extends JdkPlatform {
        private final Class<?> clientProviderClass;
        private final Method getMethod;
        private final Method putMethod;
        private final Method removeMethod;
        private final Class<?> serverProviderClass;

        public JdkWithJettyBootPlatform(Class<?> cls, Method method, Method method2, Method method3, Class<?> cls2, Class<?> cls3) {
            super(cls);
            this.putMethod = method;
            this.getMethod = method2;
            this.removeMethod = method3;
            this.clientProviderClass = cls2;
            this.serverProviderClass = cls3;
        }

        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
            Object e;
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
            } catch (InvocationTargetException e2) {
                e = e2;
                throw new AssertionError(e);
            } catch (IllegalAccessException e3) {
                e = e3;
                throw new AssertionError(e);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:3:0x000d A:{ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:0:0x0000} */
        /* JADX WARNING: Missing block: B:4:0x0012, code:
            throw new java.lang.AssertionError();
     */
        public void afterHandshake(javax.net.ssl.SSLSocket r5) {
            /*
            r4 = this;
            r0 = r4.removeMethod;	 Catch:{ IllegalAccessException -> 0x000d, IllegalAccessException -> 0x000d }
            r1 = 0;
            r2 = 1;
            r2 = new java.lang.Object[r2];	 Catch:{ IllegalAccessException -> 0x000d, IllegalAccessException -> 0x000d }
            r3 = 0;
            r2[r3] = r5;	 Catch:{ IllegalAccessException -> 0x000d, IllegalAccessException -> 0x000d }
            r0.invoke(r1, r2);	 Catch:{ IllegalAccessException -> 0x000d, IllegalAccessException -> 0x000d }
            return;
        L_0x000d:
            r5 = new java.lang.AssertionError;
            r5.<init>();
            throw r5;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.Platform.JdkWithJettyBootPlatform.afterHandshake(javax.net.ssl.SSLSocket):void");
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0035 A:{ExcHandler: java.lang.reflect.InvocationTargetException (unused java.lang.reflect.InvocationTargetException), Splitter: B:0:0x0000} */
        /* JADX WARNING: Missing block: B:12:0x003a, code:
            throw new java.lang.AssertionError();
     */
        public java.lang.String getSelectedProtocol(javax.net.ssl.SSLSocket r4) {
            /*
            r3 = this;
            r0 = r3.getMethod;	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            r1 = 1;
            r1 = new java.lang.Object[r1];	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            r2 = 0;
            r1[r2] = r4;	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            r4 = 0;
            r0 = r0.invoke(r4, r1);	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            r0 = java.lang.reflect.Proxy.getInvocationHandler(r0);	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            r0 = (com.squareup.okhttp.internal.Platform.JettyNegoProvider) r0;	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            r1 = r0.unsupported;	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            if (r1 != 0) goto L_0x0029;
        L_0x0019:
            r1 = r0.selected;	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            if (r1 != 0) goto L_0x0029;
        L_0x001f:
            r0 = com.squareup.okhttp.internal.Internal.logger;	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            r1 = java.util.logging.Level.INFO;	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            r2 = "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?";
            r0.log(r1, r2);	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            return r4;
        L_0x0029:
            r1 = r0.unsupported;	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
            if (r1 == 0) goto L_0x0030;
        L_0x002f:
            goto L_0x0034;
        L_0x0030:
            r4 = r0.selected;	 Catch:{ InvocationTargetException -> 0x0035, InvocationTargetException -> 0x0035 }
        L_0x0034:
            return r4;
        L_0x0035:
            r4 = new java.lang.AssertionError;
            r4.<init>();
            throw r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.Platform.JdkWithJettyBootPlatform.getSelectedProtocol(javax.net.ssl.SSLSocket):java.lang.String");
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

    public X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
        return null;
    }

    public void untagSocket(Socket socket) throws SocketException {
    }

    public static Platform get() {
        return PLATFORM;
    }

    public void logW(String str) {
        System.out.println(str);
    }

    public TrustRootIndex trustRootIndex(X509TrustManager x509TrustManager) {
        return new RealTrustRootIndex(x509TrustManager.getAcceptedIssuers());
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) throws IOException {
        socket.connect(inetSocketAddress, i);
    }

    public void log(String str) {
        System.out.println(str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x006e A:{ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:7:0x002c} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x006b A:{ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:9:0x003e} */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0069 A:{ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:11:0x004a} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0071 A:{PHI: r3 r6 r7 r8 , ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:13:0x005a} */
    /* JADX WARNING: Removed duplicated region for block: B:29:? A:{Catch:{ ClassNotFoundException -> 0x00fc }, ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:28:0x00f6} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:5:?, code:
            r2 = java.lang.Class.forName("org.apache.harmony.xnet.provider.jsse.SSLParametersImpl");
     */
    /* JADX WARNING: Missing block: B:16:0x0069, code:
            r8 = null;
     */
    /* JADX WARNING: Missing block: B:17:0x006b, code:
            r3 = null;
            r8 = r3;
     */
    /* JADX WARNING: Missing block: B:18:0x006e, code:
            r3 = null;
            r7 = r3;
            r8 = r7;
     */
    /* JADX WARNING: Missing block: B:22:0x007b, code:
            return new com.squareup.okhttp.internal.Platform.Android(r4, r5, r2, r7, r3, r8, r6);
     */
    /* JADX WARNING: Missing block: B:24:?, code:
            r2 = java.lang.Class.forName("sun.security.ssl.SSLContextImpl");
     */
    /* JADX WARNING: Missing block: B:26:?, code:
            r3 = "org.eclipse.jetty.alpn.ALPN";
            r4 = java.lang.Class.forName(r3);
            r5 = new java.lang.StringBuilder();
            r5.append(r3);
            r5.append("$Provider");
            r5 = java.lang.Class.forName(r5.toString());
            r6 = new java.lang.StringBuilder();
            r6.append(r3);
            r6.append("$ClientProvider");
            r8 = java.lang.Class.forName(r6.toString());
            r6 = new java.lang.StringBuilder();
            r6.append(r3);
            r6.append("$ServerProvider");
            r9 = java.lang.Class.forName(r6.toString());
     */
    /* JADX WARNING: Missing block: B:27:0x00f5, code:
            return new com.squareup.okhttp.internal.Platform.JdkWithJettyBootPlatform(r2, r4.getMethod("put", new java.lang.Class[]{javax.net.ssl.SSLSocket.class, r5}), r4.getMethod("get", new java.lang.Class[]{javax.net.ssl.SSLSocket.class}), r4.getMethod("remove", new java.lang.Class[]{javax.net.ssl.SSLSocket.class}), r8, r9);
     */
    /* JADX WARNING: Missing block: B:30:0x00fb, code:
            return new com.squareup.okhttp.internal.Platform.JdkPlatform(r2);
     */
    /* JADX WARNING: Missing block: B:32:0x0101, code:
            return new com.squareup.okhttp.internal.Platform();
     */
    private static com.squareup.okhttp.internal.Platform findPlatform() {
        /*
        r0 = 1;
        r1 = 0;
        r2 = "com.android.org.conscrypt.SSLParametersImpl";
        r2 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x000a }
    L_0x0008:
        r4 = r2;
        goto L_0x0011;
    L_0x000a:
        r2 = "org.apache.harmony.xnet.provider.jsse.SSLParametersImpl";
        r2 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x007c }
        goto L_0x0008;
    L_0x0011:
        r5 = new com.squareup.okhttp.internal.OptionalMethod;	 Catch:{ ClassNotFoundException -> 0x007c }
        r2 = "setUseSessionTickets";
        r3 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x007c }
        r6 = java.lang.Boolean.TYPE;	 Catch:{ ClassNotFoundException -> 0x007c }
        r3[r1] = r6;	 Catch:{ ClassNotFoundException -> 0x007c }
        r6 = 0;
        r5.<init>(r6, r2, r3);	 Catch:{ ClassNotFoundException -> 0x007c }
        r2 = new com.squareup.okhttp.internal.OptionalMethod;	 Catch:{ ClassNotFoundException -> 0x007c }
        r3 = "setHostname";
        r7 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x007c }
        r8 = java.lang.String.class;
        r7[r1] = r8;	 Catch:{ ClassNotFoundException -> 0x007c }
        r2.<init>(r6, r3, r7);	 Catch:{ ClassNotFoundException -> 0x007c }
        r3 = "android.net.TrafficStats";
        r3 = java.lang.Class.forName(r3);	 Catch:{ ClassNotFoundException -> 0x006e, ClassNotFoundException -> 0x006e }
        r7 = "tagSocket";
        r8 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x006e, ClassNotFoundException -> 0x006e }
        r9 = java.net.Socket.class;
        r8[r1] = r9;	 Catch:{ ClassNotFoundException -> 0x006e, ClassNotFoundException -> 0x006e }
        r7 = r3.getMethod(r7, r8);	 Catch:{ ClassNotFoundException -> 0x006e, ClassNotFoundException -> 0x006e }
        r8 = "untagSocket";
        r9 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x006b, ClassNotFoundException -> 0x006b }
        r10 = java.net.Socket.class;
        r9[r1] = r10;	 Catch:{ ClassNotFoundException -> 0x006b, ClassNotFoundException -> 0x006b }
        r3 = r3.getMethod(r8, r9);	 Catch:{ ClassNotFoundException -> 0x006b, ClassNotFoundException -> 0x006b }
        r8 = "android.net.Network";
        java.lang.Class.forName(r8);	 Catch:{ ClassNotFoundException -> 0x0069, ClassNotFoundException -> 0x0069 }
        r8 = new com.squareup.okhttp.internal.OptionalMethod;	 Catch:{ ClassNotFoundException -> 0x0069, ClassNotFoundException -> 0x0069 }
        r9 = byte[].class;
        r10 = "getAlpnSelectedProtocol";
        r11 = new java.lang.Class[r1];	 Catch:{ ClassNotFoundException -> 0x0069, ClassNotFoundException -> 0x0069 }
        r8.<init>(r9, r10, r11);	 Catch:{ ClassNotFoundException -> 0x0069, ClassNotFoundException -> 0x0069 }
        r9 = new com.squareup.okhttp.internal.OptionalMethod;	 Catch:{ ClassNotFoundException -> 0x0071, ClassNotFoundException -> 0x0071 }
        r10 = "setAlpnProtocols";
        r11 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x0071, ClassNotFoundException -> 0x0071 }
        r12 = byte[].class;
        r11[r1] = r12;	 Catch:{ ClassNotFoundException -> 0x0071, ClassNotFoundException -> 0x0071 }
        r9.<init>(r6, r10, r11);	 Catch:{ ClassNotFoundException -> 0x0071, ClassNotFoundException -> 0x0071 }
        r6 = r9;
        goto L_0x0071;
    L_0x0069:
        r8 = r6;
        goto L_0x0071;
    L_0x006b:
        r3 = r6;
        r8 = r3;
        goto L_0x0071;
    L_0x006e:
        r3 = r6;
        r7 = r3;
        r8 = r7;
    L_0x0071:
        r10 = r6;
        r9 = r8;
        r8 = r3;
        r11 = new com.squareup.okhttp.internal.Platform$Android;	 Catch:{ ClassNotFoundException -> 0x007c }
        r3 = r11;
        r6 = r2;
        r3.<init>(r4, r5, r6, r7, r8, r9, r10);	 Catch:{ ClassNotFoundException -> 0x007c }
        return r11;
    L_0x007c:
        r2 = "sun.security.ssl.SSLContextImpl";
        r2 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x00fc }
        r3 = "org.eclipse.jetty.alpn.ALPN";
        r4 = java.lang.Class.forName(r3);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r5 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r5.<init>();	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r5.append(r3);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r6 = "$Provider";
        r5.append(r6);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r5 = r5.toString();	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r5 = java.lang.Class.forName(r5);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r6 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r6.<init>();	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r6.append(r3);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r7 = "$ClientProvider";
        r6.append(r7);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r6 = r6.toString();	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r8 = java.lang.Class.forName(r6);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r6 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r6.<init>();	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r6.append(r3);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r3 = "$ServerProvider";
        r6.append(r3);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r3 = r6.toString();	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r9 = java.lang.Class.forName(r3);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r3 = "put";
        r6 = 2;
        r6 = new java.lang.Class[r6];	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r7 = javax.net.ssl.SSLSocket.class;
        r6[r1] = r7;	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r6[r0] = r5;	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r5 = r4.getMethod(r3, r6);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r3 = "get";
        r6 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r7 = javax.net.ssl.SSLSocket.class;
        r6[r1] = r7;	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r6 = r4.getMethod(r3, r6);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r3 = "remove";
        r0 = new java.lang.Class[r0];	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r7 = javax.net.ssl.SSLSocket.class;
        r0[r1] = r7;	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r7 = r4.getMethod(r3, r0);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r0 = new com.squareup.okhttp.internal.Platform$JdkWithJettyBootPlatform;	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        r3 = r0;
        r4 = r2;
        r3.<init>(r4, r5, r6, r7, r8, r9);	 Catch:{ ClassNotFoundException -> 0x00f6, ClassNotFoundException -> 0x00f6 }
        return r0;
    L_0x00f6:
        r0 = new com.squareup.okhttp.internal.Platform$JdkPlatform;	 Catch:{ ClassNotFoundException -> 0x00fc }
        r0.<init>(r2);	 Catch:{ ClassNotFoundException -> 0x00fc }
        return r0;
    L_0x00fc:
        r0 = new com.squareup.okhttp.internal.Platform;
        r0.<init>();
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.Platform.findPlatform():com.squareup.okhttp.internal.Platform");
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
}
