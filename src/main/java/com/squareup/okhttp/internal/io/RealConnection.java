package com.squareup.okhttp.internal.io;

import com.facebook.react.uimanager.ViewProps;
import com.google.common.net.HttpHeaders;
import com.google.logging.type.LogSeverity;
import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.ConnectionSpecSelector;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.Version;
import com.squareup.okhttp.internal.framed.FramedConnection;
import com.squareup.okhttp.internal.framed.FramedConnection.Builder;
import com.squareup.okhttp.internal.http.Http1xStream;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.http.StreamAllocation;
import com.squareup.okhttp.internal.tls.TrustRootIndex;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public final class RealConnection implements Connection {
    private static SSLSocketFactory lastSslSocketFactory;
    private static TrustRootIndex lastTrustRootIndex;
    public final List<Reference<StreamAllocation>> allocations = new ArrayList();
    public volatile FramedConnection framedConnection;
    private Handshake handshake;
    public long idleAtNanos = Long.MAX_VALUE;
    public boolean noNewStreams;
    private Protocol protocol;
    private Socket rawSocket;
    private final Route route;
    public BufferedSink sink;
    public Socket socket;
    public BufferedSource source;
    public int streamCount;

    public RealConnection(Route route) {
        this.route = route;
    }

    public void connect(int i, int i2, int i3, List<ConnectionSpec> list, boolean z) throws RouteException {
        if (this.protocol == null) {
            ConnectionSpecSelector connectionSpecSelector = new ConnectionSpecSelector(list);
            Proxy proxy = this.route.getProxy();
            Address address = this.route.getAddress();
            if (this.route.getAddress().getSslSocketFactory() != null || list.contains(ConnectionSpec.CLEARTEXT)) {
                RouteException routeException = null;
                while (this.protocol == null) {
                    try {
                        Socket createSocket = (proxy.type() == Type.DIRECT || proxy.type() == Type.HTTP) ? address.getSocketFactory().createSocket() : new Socket(proxy);
                        this.rawSocket = createSocket;
                        connectSocket(i, i2, i3, connectionSpecSelector);
                    } catch (IOException e) {
                        Util.closeQuietly(this.socket);
                        Util.closeQuietly(this.rawSocket);
                        this.socket = null;
                        this.rawSocket = null;
                        this.source = null;
                        this.sink = null;
                        this.handshake = null;
                        this.protocol = null;
                        if (routeException == null) {
                            routeException = new RouteException(e);
                        } else {
                            routeException.addConnectException(e);
                        }
                        if (!z || !connectionSpecSelector.connectionFailed(e)) {
                            throw routeException;
                        }
                    }
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CLEARTEXT communication not supported: ");
            stringBuilder.append(list);
            throw new RouteException(new UnknownServiceException(stringBuilder.toString()));
        }
        throw new IllegalStateException("already connected");
    }

    private void connectSocket(int i, int i2, int i3, ConnectionSpecSelector connectionSpecSelector) throws IOException {
        this.rawSocket.setSoTimeout(i2);
        try {
            Platform.get().connectSocket(this.rawSocket, this.route.getSocketAddress(), i);
            this.source = Okio.buffer(Okio.source(this.rawSocket));
            this.sink = Okio.buffer(Okio.sink(this.rawSocket));
            if (this.route.getAddress().getSslSocketFactory() != null) {
                connectTls(i2, i3, connectionSpecSelector);
            } else {
                this.protocol = Protocol.HTTP_1_1;
                this.socket = this.rawSocket;
            }
            if (this.protocol == Protocol.SPDY_3 || this.protocol == Protocol.HTTP_2) {
                this.socket.setSoTimeout(0);
                FramedConnection build = new Builder(true).socket(this.socket, this.route.getAddress().url().host(), this.source, this.sink).protocol(this.protocol).build();
                build.sendConnectionPreface();
                this.framedConnection = build;
            }
        } catch (ConnectException unused) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to connect to ");
            stringBuilder.append(this.route.getSocketAddress());
            throw new ConnectException(stringBuilder.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x012c A:{Catch:{ all -> 0x011c }} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0126 A:{Catch:{ all -> 0x011c }} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x012f  */
    private void connectTls(int r6, int r7, com.squareup.okhttp.internal.ConnectionSpecSelector r8) throws java.io.IOException {
        /*
        r5 = this;
        r0 = r5.route;
        r0 = r0.requiresTunnel();
        if (r0 == 0) goto L_0x000b;
    L_0x0008:
        r5.createTunnel(r6, r7);
    L_0x000b:
        r6 = r5.route;
        r6 = r6.getAddress();
        r7 = r6.getSslSocketFactory();
        r0 = 0;
        r1 = r5.rawSocket;	 Catch:{ AssertionError -> 0x011f }
        r2 = r6.getUriHost();	 Catch:{ AssertionError -> 0x011f }
        r3 = r6.getUriPort();	 Catch:{ AssertionError -> 0x011f }
        r4 = 1;
        r7 = r7.createSocket(r1, r2, r3, r4);	 Catch:{ AssertionError -> 0x011f }
        r7 = (javax.net.ssl.SSLSocket) r7;	 Catch:{ AssertionError -> 0x011f }
        r8 = r8.configureSecureSocket(r7);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r1 = r8.supportsTlsExtensions();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        if (r1 == 0) goto L_0x0040;
    L_0x0031:
        r1 = com.squareup.okhttp.internal.Platform.get();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r2 = r6.getUriHost();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r3 = r6.getProtocols();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r1.configureTlsExtensions(r7, r2, r3);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
    L_0x0040:
        r7.startHandshake();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r1 = r7.getSession();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r1 = com.squareup.okhttp.Handshake.get(r1);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r2 = r6.getHostnameVerifier();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r3 = r6.getUriHost();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r4 = r7.getSession();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r2 = r2.verify(r3, r4);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        if (r2 == 0) goto L_0x00c4;
    L_0x005d:
        r2 = r6.getCertificatePinner();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r3 = com.squareup.okhttp.CertificatePinner.DEFAULT;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        if (r2 == r3) goto L_0x0085;
    L_0x0065:
        r2 = r6.getSslSocketFactory();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r2 = trustRootIndex(r2);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r3 = new com.squareup.okhttp.internal.tls.CertificateChainCleaner;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r3.<init>(r2);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r2 = r1.peerCertificates();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r2 = r3.clean(r2);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r3 = r6.getCertificatePinner();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = r6.getUriHost();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r3.check(r6, r2);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
    L_0x0085:
        r6 = r8.supportsTlsExtensions();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        if (r6 == 0) goto L_0x0093;
    L_0x008b:
        r6 = com.squareup.okhttp.internal.Platform.get();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r0 = r6.getSelectedProtocol(r7);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
    L_0x0093:
        r5.socket = r7;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = r5.socket;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = okio.Okio.source(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = okio.Okio.buffer(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r5.source = r6;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = r5.socket;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = okio.Okio.sink(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = okio.Okio.buffer(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r5.sink = r6;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r5.handshake = r1;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        if (r0 == 0) goto L_0x00b6;
    L_0x00b1:
        r6 = com.squareup.okhttp.Protocol.get(r0);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        goto L_0x00b8;
    L_0x00b6:
        r6 = com.squareup.okhttp.Protocol.HTTP_1_1;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
    L_0x00b8:
        r5.protocol = r6;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        if (r7 == 0) goto L_0x00c3;
    L_0x00bc:
        r6 = com.squareup.okhttp.internal.Platform.get();
        r6.afterHandshake(r7);
    L_0x00c3:
        return;
    L_0x00c4:
        r8 = r1.peerCertificates();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r0 = 0;
        r8 = r8.get(r0);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r8 = (java.security.cert.X509Certificate) r8;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r0 = new javax.net.ssl.SSLPeerUnverifiedException;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r1 = new java.lang.StringBuilder;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r1.<init>();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r2 = "Hostname ";
        r1.append(r2);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = r6.getUriHost();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r1.append(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = " not verified:";
        r1.append(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = "\n    certificate: ";
        r1.append(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = com.squareup.okhttp.CertificatePinner.pin(r8);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r1.append(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = "\n    DN: ";
        r1.append(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = r8.getSubjectDN();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = r6.getName();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r1.append(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = "\n    subjectAltNames: ";
        r1.append(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = com.squareup.okhttp.internal.tls.OkHostnameVerifier.allSubjectAltNames(r8);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r1.append(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r6 = r1.toString();	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        r0.<init>(r6);	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
        throw r0;	 Catch:{ AssertionError -> 0x0119, all -> 0x0117 }
    L_0x0117:
        r6 = move-exception;
        goto L_0x012d;
    L_0x0119:
        r6 = move-exception;
        r0 = r7;
        goto L_0x0120;
    L_0x011c:
        r6 = move-exception;
        r7 = r0;
        goto L_0x012d;
    L_0x011f:
        r6 = move-exception;
    L_0x0120:
        r7 = com.squareup.okhttp.internal.Util.isAndroidGetsocknameError(r6);	 Catch:{ all -> 0x011c }
        if (r7 == 0) goto L_0x012c;
    L_0x0126:
        r7 = new java.io.IOException;	 Catch:{ all -> 0x011c }
        r7.<init>(r6);	 Catch:{ all -> 0x011c }
        throw r7;	 Catch:{ all -> 0x011c }
    L_0x012c:
        throw r6;	 Catch:{ all -> 0x011c }
    L_0x012d:
        if (r7 == 0) goto L_0x0136;
    L_0x012f:
        r8 = com.squareup.okhttp.internal.Platform.get();
        r8.afterHandshake(r7);
    L_0x0136:
        com.squareup.okhttp.internal.Util.closeQuietly(r7);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.io.RealConnection.connectTls(int, int, com.squareup.okhttp.internal.ConnectionSpecSelector):void");
    }

    private static synchronized TrustRootIndex trustRootIndex(SSLSocketFactory sSLSocketFactory) {
        TrustRootIndex trustRootIndex;
        synchronized (RealConnection.class) {
            if (sSLSocketFactory != lastSslSocketFactory) {
                lastTrustRootIndex = Platform.get().trustRootIndex(Platform.get().trustManager(sSLSocketFactory));
                lastSslSocketFactory = sSLSocketFactory;
            }
            trustRootIndex = lastTrustRootIndex;
        }
        return trustRootIndex;
    }

    private void createTunnel(int i, int i2) throws IOException {
        Request createTunnelRequest = createTunnelRequest();
        HttpUrl httpUrl = createTunnelRequest.httpUrl();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CONNECT ");
        stringBuilder.append(httpUrl.host());
        stringBuilder.append(":");
        stringBuilder.append(httpUrl.port());
        stringBuilder.append(" HTTP/1.1");
        String stringBuilder2 = stringBuilder.toString();
        while (true) {
            Http1xStream http1xStream = new Http1xStream(null, this.source, this.sink);
            this.source.timeout().timeout((long) i, TimeUnit.MILLISECONDS);
            this.sink.timeout().timeout((long) i2, TimeUnit.MILLISECONDS);
            http1xStream.writeRequest(createTunnelRequest.headers(), stringBuilder2);
            http1xStream.finishRequest();
            Response build = http1xStream.readResponse().request(createTunnelRequest).build();
            long contentLength = OkHeaders.contentLength(build);
            if (contentLength == -1) {
                contentLength = 0;
            }
            Source newFixedLengthSource = http1xStream.newFixedLengthSource(contentLength);
            Util.skipAll(newFixedLengthSource, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            newFixedLengthSource.close();
            int code = build.code();
            if (code != LogSeverity.INFO_VALUE) {
                if (code == 407) {
                    createTunnelRequest = OkHeaders.processAuthHeader(this.route.getAddress().getAuthenticator(), build, this.route.getProxy());
                    if (createTunnelRequest == null) {
                        throw new IOException("Failed to authenticate with proxy");
                    }
                } else {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Unexpected response code for CONNECT: ");
                    stringBuilder3.append(build.code());
                    throw new IOException(stringBuilder3.toString());
                }
            } else if (!this.source.buffer().exhausted() || !this.sink.buffer().exhausted()) {
                throw new IOException("TLS tunnel buffered too many bytes!");
            } else {
                return;
            }
        }
    }

    private Request createTunnelRequest() throws IOException {
        return new Request.Builder().url(this.route.getAddress().url()).header(HttpHeaders.HOST, Util.hostHeader(this.route.getAddress().url())).header("Proxy-Connection", "Keep-Alive").header(HttpHeaders.USER_AGENT, Version.userAgent()).build();
    }

    boolean isConnected() {
        return this.protocol != null;
    }

    public Route getRoute() {
        return this.route;
    }

    public void cancel() {
        Util.closeQuietly(this.rawSocket);
    }

    public Socket getSocket() {
        return this.socket;
    }

    public int allocationLimit() {
        FramedConnection framedConnection = this.framedConnection;
        return framedConnection != null ? framedConnection.maxConcurrentStreams() : 1;
    }

    public boolean isHealthy(boolean z) {
        if (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown()) {
            return false;
        }
        if (this.framedConnection == null && z) {
            int soTimeout;
            try {
                soTimeout = this.socket.getSoTimeout();
                this.socket.setSoTimeout(1);
                if (this.source.exhausted()) {
                    this.socket.setSoTimeout(soTimeout);
                    return false;
                }
                this.socket.setSoTimeout(soTimeout);
                return true;
            } catch (SocketTimeoutException unused) {
                return true;
            } catch (IOException unused2) {
                return false;
            } catch (Throwable th) {
                this.socket.setSoTimeout(soTimeout);
            }
        }
        return true;
    }

    public Handshake getHandshake() {
        return this.handshake;
    }

    public boolean isMultiplexed() {
        return this.framedConnection != null;
    }

    public Protocol getProtocol() {
        Protocol protocol = this.protocol;
        return protocol != null ? protocol : Protocol.HTTP_1_1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Connection{");
        stringBuilder.append(this.route.getAddress().url().host());
        stringBuilder.append(":");
        stringBuilder.append(this.route.getAddress().url().port());
        stringBuilder.append(", proxy=");
        stringBuilder.append(this.route.getProxy());
        stringBuilder.append(" hostAddress=");
        stringBuilder.append(this.route.getSocketAddress());
        stringBuilder.append(" cipherSuite=");
        Handshake handshake = this.handshake;
        stringBuilder.append(handshake != null ? handshake.cipherSuite() : ViewProps.NONE);
        stringBuilder.append(" protocol=");
        stringBuilder.append(this.protocol);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
