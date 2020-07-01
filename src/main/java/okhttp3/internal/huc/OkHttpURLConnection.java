package okhttp3.internal.huc;

import io.grpc.internal.GrpcUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketPermission;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.Permission;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Internal;
import okhttp3.internal.JavaNetHeaders;
import okhttp3.internal.URLFilter;
import okhttp3.internal.Version;
import okhttp3.internal.http.HttpDate;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.platform.Platform;
import okio.Buffer;

public final class OkHttpURLConnection extends HttpURLConnection implements Callback {
    private static final Set<String> METHODS = new LinkedHashSet(Arrays.asList(new String[]{"OPTIONS", "GET", "HEAD", GrpcUtil.HTTP_METHOD, "PUT", "DELETE", "TRACE", "PATCH"}));
    public static final String RESPONSE_SOURCE;
    public static final String SELECTED_PROTOCOL;
    Call call;
    private Throwable callFailure;
    OkHttpClient client;
    boolean connectPending;
    private boolean executed;
    private long fixedContentLength;
    Handshake handshake;
    private final Object lock;
    private final NetworkInterceptor networkInterceptor;
    Response networkResponse;
    Proxy proxy;
    private Builder requestHeaders;
    private Response response;
    private Headers responseHeaders;
    URLFilter urlFilter;

    static final class UnexpectedException extends IOException {
        static final Interceptor INTERCEPTOR = new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Throwable e;
                try {
                    return chain.proceed(chain.request());
                } catch (Error e2) {
                    e = e2;
                    throw new UnexpectedException(e);
                } catch (RuntimeException e3) {
                    e = e3;
                    throw new UnexpectedException(e);
                }
            }
        };

        UnexpectedException(Throwable th) {
            super(th);
        }
    }

    final class NetworkInterceptor implements Interceptor {
        private boolean proceed;

        NetworkInterceptor() {
        }

        public void proceed() {
            synchronized (OkHttpURLConnection.this.lock) {
                this.proceed = true;
                OkHttpURLConnection.this.lock.notifyAll();
            }
        }

        /* JADX WARNING: Missing block: B:27:?, code:
            java.lang.Thread.currentThread().interrupt();
     */
        /* JADX WARNING: Missing block: B:28:0x009c, code:
            throw new java.io.InterruptedIOException();
     */
        public okhttp3.Response intercept(okhttp3.Interceptor.Chain r5) throws java.io.IOException {
            /*
            r4 = this;
            r0 = r5.request();
            r1 = okhttp3.internal.huc.OkHttpURLConnection.this;
            r1 = r1.urlFilter;
            if (r1 == 0) goto L_0x0019;
        L_0x000a:
            r1 = okhttp3.internal.huc.OkHttpURLConnection.this;
            r1 = r1.urlFilter;
            r2 = r0.url();
            r2 = r2.url();
            r1.checkURLPermitted(r2);
        L_0x0019:
            r1 = okhttp3.internal.huc.OkHttpURLConnection.this;
            r1 = r1.lock;
            monitor-enter(r1);
            r2 = okhttp3.internal.huc.OkHttpURLConnection.this;	 Catch:{ all -> 0x009d }
            r3 = 0;
            r2.connectPending = r3;	 Catch:{ all -> 0x009d }
            r2 = okhttp3.internal.huc.OkHttpURLConnection.this;	 Catch:{ all -> 0x009d }
            r3 = r5.connection();	 Catch:{ all -> 0x009d }
            r3 = r3.route();	 Catch:{ all -> 0x009d }
            r3 = r3.proxy();	 Catch:{ all -> 0x009d }
            r2.proxy = r3;	 Catch:{ all -> 0x009d }
            r2 = okhttp3.internal.huc.OkHttpURLConnection.this;	 Catch:{ all -> 0x009d }
            r3 = r5.connection();	 Catch:{ all -> 0x009d }
            r3 = r3.handshake();	 Catch:{ all -> 0x009d }
            r2.handshake = r3;	 Catch:{ all -> 0x009d }
            r2 = okhttp3.internal.huc.OkHttpURLConnection.this;	 Catch:{ all -> 0x009d }
            r2 = r2.lock;	 Catch:{ all -> 0x009d }
            r2.notifyAll();	 Catch:{ all -> 0x009d }
        L_0x004a:
            r2 = r4.proceed;	 Catch:{ InterruptedException -> 0x0090 }
            if (r2 != 0) goto L_0x0058;
        L_0x004e:
            r2 = okhttp3.internal.huc.OkHttpURLConnection.this;	 Catch:{ InterruptedException -> 0x0090 }
            r2 = r2.lock;	 Catch:{ InterruptedException -> 0x0090 }
            r2.wait();	 Catch:{ InterruptedException -> 0x0090 }
            goto L_0x004a;
        L_0x0058:
            monitor-exit(r1);	 Catch:{ all -> 0x009d }
            r1 = r0.body();
            r1 = r1 instanceof okhttp3.internal.huc.OutputStreamRequestBody;
            if (r1 == 0) goto L_0x006b;
        L_0x0061:
            r1 = r0.body();
            r1 = (okhttp3.internal.huc.OutputStreamRequestBody) r1;
            r0 = r1.prepareToSendRequest(r0);
        L_0x006b:
            r5 = r5.proceed(r0);
            r0 = okhttp3.internal.huc.OkHttpURLConnection.this;
            r0 = r0.lock;
            monitor-enter(r0);
            r1 = okhttp3.internal.huc.OkHttpURLConnection.this;	 Catch:{ all -> 0x008d }
            r1.networkResponse = r5;	 Catch:{ all -> 0x008d }
            r1 = okhttp3.internal.huc.OkHttpURLConnection.this;	 Catch:{ all -> 0x008d }
            r2 = r5.request();	 Catch:{ all -> 0x008d }
            r2 = r2.url();	 Catch:{ all -> 0x008d }
            r2 = r2.url();	 Catch:{ all -> 0x008d }
            r1.url = r2;	 Catch:{ all -> 0x008d }
            monitor-exit(r0);	 Catch:{ all -> 0x008d }
            return r5;
        L_0x008d:
            r5 = move-exception;
            monitor-exit(r0);	 Catch:{ all -> 0x008d }
            throw r5;
        L_0x0090:
            r5 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x009d }
            r5.interrupt();	 Catch:{ all -> 0x009d }
            r5 = new java.io.InterruptedIOException;	 Catch:{ all -> 0x009d }
            r5.<init>();	 Catch:{ all -> 0x009d }
            throw r5;	 Catch:{ all -> 0x009d }
        L_0x009d:
            r5 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x009d }
            throw r5;
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.huc.OkHttpURLConnection.NetworkInterceptor.intercept(okhttp3.Interceptor$Chain):okhttp3.Response");
        }
    }

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Platform.get().getPrefix());
        stringBuilder.append("-Selected-Protocol");
        SELECTED_PROTOCOL = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(Platform.get().getPrefix());
        stringBuilder.append("-Response-Source");
        RESPONSE_SOURCE = stringBuilder.toString();
    }

    public OkHttpURLConnection(URL url, OkHttpClient okHttpClient) {
        super(url);
        this.networkInterceptor = new NetworkInterceptor();
        this.requestHeaders = new Builder();
        this.fixedContentLength = -1;
        this.lock = new Object();
        this.connectPending = true;
        this.client = okHttpClient;
    }

    public OkHttpURLConnection(URL url, OkHttpClient okHttpClient, URLFilter uRLFilter) {
        this(url, okHttpClient);
        this.urlFilter = uRLFilter;
    }

    /* JADX WARNING: Missing block: B:23:?, code:
            java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Missing block: B:24:0x003f, code:
            throw new java.io.InterruptedIOException();
     */
    public void connect() throws java.io.IOException {
        /*
        r2 = this;
        r0 = r2.executed;
        if (r0 == 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r0 = r2.buildCall();
        r1 = 1;
        r2.executed = r1;
        r0.enqueue(r2);
        r0 = r2.lock;
        monitor-enter(r0);
    L_0x0012:
        r1 = r2.connectPending;	 Catch:{ InterruptedException -> 0x0033 }
        if (r1 == 0) goto L_0x0024;
    L_0x0016:
        r1 = r2.response;	 Catch:{ InterruptedException -> 0x0033 }
        if (r1 != 0) goto L_0x0024;
    L_0x001a:
        r1 = r2.callFailure;	 Catch:{ InterruptedException -> 0x0033 }
        if (r1 != 0) goto L_0x0024;
    L_0x001e:
        r1 = r2.lock;	 Catch:{ InterruptedException -> 0x0033 }
        r1.wait();	 Catch:{ InterruptedException -> 0x0033 }
        goto L_0x0012;
    L_0x0024:
        r1 = r2.callFailure;	 Catch:{ InterruptedException -> 0x0033 }
        if (r1 != 0) goto L_0x002a;
    L_0x0028:
        monitor-exit(r0);	 Catch:{ all -> 0x0031 }
        return;
    L_0x002a:
        r1 = r2.callFailure;	 Catch:{ InterruptedException -> 0x0033 }
        r1 = propagate(r1);	 Catch:{ InterruptedException -> 0x0033 }
        throw r1;	 Catch:{ InterruptedException -> 0x0033 }
    L_0x0031:
        r1 = move-exception;
        goto L_0x0040;
    L_0x0033:
        r1 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0031 }
        r1.interrupt();	 Catch:{ all -> 0x0031 }
        r1 = new java.io.InterruptedIOException;	 Catch:{ all -> 0x0031 }
        r1.<init>();	 Catch:{ all -> 0x0031 }
        throw r1;	 Catch:{ all -> 0x0031 }
    L_0x0040:
        monitor-exit(r0);	 Catch:{ all -> 0x0031 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.huc.OkHttpURLConnection.connect():void");
    }

    public void disconnect() {
        if (this.call != null) {
            this.networkInterceptor.proceed();
            this.call.cancel();
        }
    }

    public InputStream getErrorStream() {
        try {
            Response response = getResponse(true);
            if (HttpHeaders.hasBody(response) && response.code() >= 400) {
                return response.body().byteStream();
            }
        } catch (IOException unused) {
            return null;
        }
    }

    private Headers getHeaders() throws IOException {
        if (this.responseHeaders == null) {
            Response response = getResponse(true);
            this.responseHeaders = response.headers().newBuilder().add(SELECTED_PROTOCOL, response.protocol().toString()).add(RESPONSE_SOURCE, responseSourceHeader(response)).build();
        }
        return this.responseHeaders;
    }

    private static String responseSourceHeader(Response response) {
        StringBuilder stringBuilder;
        if (response.networkResponse() == null) {
            if (response.cacheResponse() == null) {
                return "NONE";
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("CACHE ");
            stringBuilder.append(response.code());
            return stringBuilder.toString();
        } else if (response.cacheResponse() == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("NETWORK ");
            stringBuilder.append(response.code());
            return stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("CONDITIONAL_CACHE ");
            stringBuilder.append(response.networkResponse().code());
            return stringBuilder.toString();
        }
    }

    public String getHeaderField(int i) {
        try {
            Headers headers = getHeaders();
            if (i >= 0 && i < headers.size()) {
                return headers.value(i);
            }
        } catch (IOException unused) {
            return null;
        }
    }

    public String getHeaderField(String str) {
        if (str == null) {
            try {
                str = StatusLine.get(getResponse(true)).toString();
            } catch (IOException unused) {
                return null;
            }
        }
        str = getHeaders().get(str);
        return str;
    }

    public String getHeaderFieldKey(int i) {
        try {
            Headers headers = getHeaders();
            if (i >= 0 && i < headers.size()) {
                return headers.name(i);
            }
        } catch (IOException unused) {
            return null;
        }
    }

    public Map<String, List<String>> getHeaderFields() {
        try {
            return JavaNetHeaders.toMultimap(getHeaders(), StatusLine.get(getResponse(true)).toString());
        } catch (IOException unused) {
            return Collections.emptyMap();
        }
    }

    public Map<String, List<String>> getRequestProperties() {
        if (!this.connected) {
            return JavaNetHeaders.toMultimap(this.requestHeaders.build(), null);
        }
        throw new IllegalStateException("Cannot access request header fields after connection is set");
    }

    public InputStream getInputStream() throws IOException {
        if (this.doInput) {
            Response response = getResponse(false);
            if (response.code() < 400) {
                return response.body().byteStream();
            }
            throw new FileNotFoundException(this.url.toString());
        }
        throw new ProtocolException("This protocol does not support input");
    }

    public OutputStream getOutputStream() throws IOException {
        OutputStreamRequestBody outputStreamRequestBody = (OutputStreamRequestBody) buildCall().request().body();
        if (outputStreamRequestBody != null) {
            if (outputStreamRequestBody instanceof StreamedRequestBody) {
                connect();
                this.networkInterceptor.proceed();
            }
            if (!outputStreamRequestBody.isClosed()) {
                return outputStreamRequestBody.outputStream();
            }
            throw new ProtocolException("cannot write request body after response has been read");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("method does not support a request body: ");
        stringBuilder.append(this.method);
        throw new ProtocolException(stringBuilder.toString());
    }

    public Permission getPermission() throws IOException {
        int port;
        URL url = getURL();
        String host = url.getHost();
        if (url.getPort() != -1) {
            port = url.getPort();
        } else {
            port = HttpUrl.defaultPort(url.getProtocol());
        }
        if (usingProxy()) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) this.client.proxy().address();
            host = inetSocketAddress.getHostName();
            port = inetSocketAddress.getPort();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(host);
        stringBuilder.append(":");
        stringBuilder.append(port);
        return new SocketPermission(stringBuilder.toString(), "connect, resolve");
    }

    public String getRequestProperty(String str) {
        return str == null ? null : this.requestHeaders.get(str);
    }

    public void setConnectTimeout(int i) {
        this.client = this.client.newBuilder().connectTimeout((long) i, TimeUnit.MILLISECONDS).build();
    }

    public void setInstanceFollowRedirects(boolean z) {
        this.client = this.client.newBuilder().followRedirects(z).build();
    }

    public boolean getInstanceFollowRedirects() {
        return this.client.followRedirects();
    }

    public int getConnectTimeout() {
        return this.client.connectTimeoutMillis();
    }

    public void setReadTimeout(int i) {
        this.client = this.client.newBuilder().readTimeout((long) i, TimeUnit.MILLISECONDS).build();
    }

    public int getReadTimeout() {
        return this.client.readTimeoutMillis();
    }

    private Call buildCall() throws IOException {
        Call call = this.call;
        if (call != null) {
            return call;
        }
        RequestBody streamedRequestBody;
        boolean z = true;
        this.connected = true;
        if (this.doOutput) {
            if (this.method.equals("GET")) {
                this.method = GrpcUtil.HTTP_METHOD;
            } else if (!HttpMethod.permitsRequestBody(this.method)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.method);
                stringBuilder.append(" does not support writing");
                throw new ProtocolException(stringBuilder.toString());
            }
        }
        Builder builder = this.requestHeaders;
        String str = com.google.common.net.HttpHeaders.USER_AGENT;
        if (builder.get(str) == null) {
            this.requestHeaders.add(str, defaultUserAgent());
        }
        if (HttpMethod.permitsRequestBody(this.method)) {
            builder = this.requestHeaders;
            String str2 = com.google.common.net.HttpHeaders.CONTENT_TYPE;
            if (builder.get(str2) == null) {
                this.requestHeaders.add(str2, "application/x-www-form-urlencoded");
            }
            long j = -1;
            if (this.fixedContentLength == -1 && this.chunkLength <= 0) {
                z = false;
            }
            String str3 = this.requestHeaders.get(com.google.common.net.HttpHeaders.CONTENT_LENGTH);
            long j2 = this.fixedContentLength;
            if (j2 != -1) {
                j = j2;
            } else if (str3 != null) {
                j = Long.parseLong(str3);
            }
            if (z) {
                streamedRequestBody = new StreamedRequestBody(j);
            } else {
                streamedRequestBody = new BufferedRequestBody(j);
            }
            streamedRequestBody.timeout().timeout((long) this.client.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
        } else {
            streamedRequestBody = null;
        }
        try {
            Request build = new Request.Builder().url(HttpUrl.get(getURL().toString())).headers(this.requestHeaders.build()).method(this.method, streamedRequestBody).build();
            URLFilter uRLFilter = this.urlFilter;
            if (uRLFilter != null) {
                uRLFilter.checkURLPermitted(build.url().url());
            }
            OkHttpClient.Builder newBuilder = this.client.newBuilder();
            newBuilder.interceptors().clear();
            newBuilder.interceptors().add(UnexpectedException.INTERCEPTOR);
            newBuilder.networkInterceptors().clear();
            newBuilder.networkInterceptors().add(this.networkInterceptor);
            newBuilder.dispatcher(new Dispatcher(this.client.dispatcher().executorService()));
            if (!getUseCaches()) {
                newBuilder.cache(null);
            }
            call = newBuilder.build().newCall(build);
            this.call = call;
            return call;
        } catch (Throwable e) {
            if (Internal.instance.isInvalidHttpUrlHost(e)) {
                UnknownHostException unknownHostException = new UnknownHostException();
                unknownHostException.initCause(e);
                throw unknownHostException;
            }
            MalformedURLException malformedURLException = new MalformedURLException();
            malformedURLException.initCause(e);
            throw malformedURLException;
        }
    }

    private String defaultUserAgent() {
        String property = System.getProperty("http.agent");
        return property != null ? toHumanReadableAscii(property) : Version.userAgent();
    }

    private static String toHumanReadableAscii(String str) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            int codePointAt = str.codePointAt(i);
            if (codePointAt <= 31 || codePointAt >= 127) {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, 0, i);
                buffer.writeUtf8CodePoint(63);
                while (true) {
                    i += Character.charCount(codePointAt);
                    if (i >= length) {
                        return buffer.readUtf8();
                    }
                    codePointAt = str.codePointAt(i);
                    int i2 = (codePointAt <= 31 || codePointAt >= 127) ? 63 : codePointAt;
                    buffer.writeUtf8CodePoint(i2);
                }
            } else {
                i += Character.charCount(codePointAt);
            }
        }
        return str;
    }

    /* JADX WARNING: Missing block: B:19:0x0021, code:
            r3 = buildCall();
            r2.networkInterceptor.proceed();
            r0 = (okhttp3.internal.huc.OutputStreamRequestBody) r3.request().body();
     */
    /* JADX WARNING: Missing block: B:20:0x0034, code:
            if (r0 == null) goto L_0x003d;
     */
    /* JADX WARNING: Missing block: B:21:0x0036, code:
            r0.outputStream().close();
     */
    /* JADX WARNING: Missing block: B:23:0x003f, code:
            if (r2.executed == false) goto L_0x0065;
     */
    /* JADX WARNING: Missing block: B:24:0x0041, code:
            r0 = r2.lock;
     */
    /* JADX WARNING: Missing block: B:25:0x0043, code:
            monitor-enter(r0);
     */
    /* JADX WARNING: Missing block: B:28:0x0046, code:
            if (r2.response != null) goto L_0x0052;
     */
    /* JADX WARNING: Missing block: B:30:0x004a, code:
            if (r2.callFailure != null) goto L_0x0052;
     */
    /* JADX WARNING: Missing block: B:31:0x004c, code:
            r2.lock.wait();
     */
    /* JADX WARNING: Missing block: B:34:?, code:
            monitor-exit(r0);
     */
    /* JADX WARNING: Missing block: B:36:0x0056, code:
            java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Missing block: B:37:0x0062, code:
            throw new java.io.InterruptedIOException();
     */
    /* JADX WARNING: Missing block: B:40:0x0065, code:
            r2.executed = true;
     */
    /* JADX WARNING: Missing block: B:42:?, code:
            onResponse(r3, r3.execute());
     */
    /* JADX WARNING: Missing block: B:43:0x0070, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:44:0x0071, code:
            onFailure(r3, r0);
     */
    private okhttp3.Response getResponse(boolean r3) throws java.io.IOException {
        /*
        r2 = this;
        r0 = r2.lock;
        monitor-enter(r0);
        r1 = r2.response;	 Catch:{ all -> 0x0094 }
        if (r1 == 0) goto L_0x000b;
    L_0x0007:
        r3 = r2.response;	 Catch:{ all -> 0x0094 }
        monitor-exit(r0);	 Catch:{ all -> 0x0094 }
        return r3;
    L_0x000b:
        r1 = r2.callFailure;	 Catch:{ all -> 0x0094 }
        if (r1 == 0) goto L_0x0020;
    L_0x000f:
        if (r3 == 0) goto L_0x0019;
    L_0x0011:
        r3 = r2.networkResponse;	 Catch:{ all -> 0x0094 }
        if (r3 == 0) goto L_0x0019;
    L_0x0015:
        r3 = r2.networkResponse;	 Catch:{ all -> 0x0094 }
        monitor-exit(r0);	 Catch:{ all -> 0x0094 }
        return r3;
    L_0x0019:
        r3 = r2.callFailure;	 Catch:{ all -> 0x0094 }
        r3 = propagate(r3);	 Catch:{ all -> 0x0094 }
        throw r3;	 Catch:{ all -> 0x0094 }
    L_0x0020:
        monitor-exit(r0);	 Catch:{ all -> 0x0094 }
        r3 = r2.buildCall();
        r0 = r2.networkInterceptor;
        r0.proceed();
        r0 = r3.request();
        r0 = r0.body();
        r0 = (okhttp3.internal.huc.OutputStreamRequestBody) r0;
        if (r0 == 0) goto L_0x003d;
    L_0x0036:
        r0 = r0.outputStream();
        r0.close();
    L_0x003d:
        r0 = r2.executed;
        if (r0 == 0) goto L_0x0065;
    L_0x0041:
        r0 = r2.lock;
        monitor-enter(r0);
    L_0x0044:
        r3 = r2.response;	 Catch:{ InterruptedException -> 0x0056 }
        if (r3 != 0) goto L_0x0052;
    L_0x0048:
        r3 = r2.callFailure;	 Catch:{ InterruptedException -> 0x0056 }
        if (r3 != 0) goto L_0x0052;
    L_0x004c:
        r3 = r2.lock;	 Catch:{ InterruptedException -> 0x0056 }
        r3.wait();	 Catch:{ InterruptedException -> 0x0056 }
        goto L_0x0044;
    L_0x0052:
        monitor-exit(r0);	 Catch:{ all -> 0x0054 }
        goto L_0x0074;
    L_0x0054:
        r3 = move-exception;
        goto L_0x0063;
    L_0x0056:
        r3 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0054 }
        r3.interrupt();	 Catch:{ all -> 0x0054 }
        r3 = new java.io.InterruptedIOException;	 Catch:{ all -> 0x0054 }
        r3.<init>();	 Catch:{ all -> 0x0054 }
        throw r3;	 Catch:{ all -> 0x0054 }
    L_0x0063:
        monitor-exit(r0);	 Catch:{ all -> 0x0054 }
        throw r3;
    L_0x0065:
        r0 = 1;
        r2.executed = r0;
        r0 = r3.execute();	 Catch:{ IOException -> 0x0070 }
        r2.onResponse(r3, r0);	 Catch:{ IOException -> 0x0070 }
        goto L_0x0074;
    L_0x0070:
        r0 = move-exception;
        r2.onFailure(r3, r0);
    L_0x0074:
        r3 = r2.lock;
        monitor-enter(r3);
        r0 = r2.callFailure;	 Catch:{ all -> 0x0091 }
        if (r0 != 0) goto L_0x008a;
    L_0x007b:
        r0 = r2.response;	 Catch:{ all -> 0x0091 }
        if (r0 == 0) goto L_0x0083;
    L_0x007f:
        r0 = r2.response;	 Catch:{ all -> 0x0091 }
        monitor-exit(r3);	 Catch:{ all -> 0x0091 }
        return r0;
    L_0x0083:
        monitor-exit(r3);	 Catch:{ all -> 0x0091 }
        r3 = new java.lang.AssertionError;
        r3.<init>();
        throw r3;
    L_0x008a:
        r0 = r2.callFailure;	 Catch:{ all -> 0x0091 }
        r0 = propagate(r0);	 Catch:{ all -> 0x0091 }
        throw r0;	 Catch:{ all -> 0x0091 }
    L_0x0091:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0091 }
        throw r0;
    L_0x0094:
        r3 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0094 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.huc.OkHttpURLConnection.getResponse(boolean):okhttp3.Response");
    }

    public boolean usingProxy() {
        boolean z = true;
        if (this.proxy != null) {
            return true;
        }
        Proxy proxy = this.client.proxy();
        if (proxy == null || proxy.type() == Type.DIRECT) {
            z = false;
        }
        return z;
    }

    public String getResponseMessage() throws IOException {
        return getResponse(true).message();
    }

    public int getResponseCode() throws IOException {
        return getResponse(true).code();
    }

    public void setRequestProperty(String str, String str2) {
        if (this.connected) {
            throw new IllegalStateException("Cannot set request property after connection is made");
        } else if (str == null) {
            throw new NullPointerException("field == null");
        } else if (str2 == null) {
            Platform platform = Platform.get();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignoring header ");
            stringBuilder.append(str);
            stringBuilder.append(" because its value was null.");
            platform.log(5, stringBuilder.toString(), null);
        } else {
            this.requestHeaders.set(str, str2);
        }
    }

    public void setIfModifiedSince(long j) {
        super.setIfModifiedSince(j);
        j = this.ifModifiedSince;
        String str = com.google.common.net.HttpHeaders.IF_MODIFIED_SINCE;
        if (j != 0) {
            this.requestHeaders.set(str, HttpDate.format(new Date(this.ifModifiedSince)));
        } else {
            this.requestHeaders.removeAll(str);
        }
    }

    public void addRequestProperty(String str, String str2) {
        if (this.connected) {
            throw new IllegalStateException("Cannot add request property after connection is made");
        } else if (str == null) {
            throw new NullPointerException("field == null");
        } else if (str2 == null) {
            Platform platform = Platform.get();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignoring header ");
            stringBuilder.append(str);
            stringBuilder.append(" because its value was null.");
            platform.log(5, stringBuilder.toString(), null);
        } else {
            this.requestHeaders.add(str, str2);
        }
    }

    public void setRequestMethod(String str) throws ProtocolException {
        if (METHODS.contains(str)) {
            this.method = str;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected one of ");
        stringBuilder.append(METHODS);
        stringBuilder.append(" but was ");
        stringBuilder.append(str);
        throw new ProtocolException(stringBuilder.toString());
    }

    public void setFixedLengthStreamingMode(int i) {
        setFixedLengthStreamingMode((long) i);
    }

    public void setFixedLengthStreamingMode(long j) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        } else if (this.chunkLength > 0) {
            throw new IllegalStateException("Already in chunked mode");
        } else if (j >= 0) {
            this.fixedContentLength = j;
            this.fixedContentLength = (int) Math.min(j, 2147483647L);
        } else {
            throw new IllegalArgumentException("contentLength < 0");
        }
    }

    public void onFailure(Call call, IOException iOException) {
        synchronized (this.lock) {
            Throwable iOException2;
            if (iOException2 instanceof UnexpectedException) {
                iOException2 = iOException2.getCause();
            }
            this.callFailure = iOException2;
            this.lock.notifyAll();
        }
    }

    public void onResponse(Call call, Response response) {
        synchronized (this.lock) {
            this.response = response;
            this.handshake = response.handshake();
            this.url = response.request().url().url();
            this.lock.notifyAll();
        }
    }

    private static IOException propagate(Throwable th) throws IOException {
        if (th instanceof IOException) {
            throw ((IOException) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        } else if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else {
            throw new AssertionError();
        }
    }
}
