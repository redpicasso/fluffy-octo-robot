package com.squareup.okhttp;

import androidx.core.app.NotificationCompat;
import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.RequestException;
import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.http.StreamAllocation;
import java.io.IOException;
import java.net.ProtocolException;

public class Call {
    volatile boolean canceled;
    private final OkHttpClient client;
    HttpEngine engine;
    private boolean executed;
    Request originalRequest;

    class ApplicationInterceptorChain implements Chain {
        private final boolean forWebSocket;
        private final int index;
        private final Request request;

        public Connection connection() {
            return null;
        }

        ApplicationInterceptorChain(int i, Request request, boolean z) {
            this.index = i;
            this.request = request;
            this.forWebSocket = z;
        }

        public Request request() {
            return this.request;
        }

        public Response proceed(Request request) throws IOException {
            if (this.index >= Call.this.client.interceptors().size()) {
                return Call.this.getResponse(request, this.forWebSocket);
            }
            Chain applicationInterceptorChain = new ApplicationInterceptorChain(this.index + 1, request, this.forWebSocket);
            Interceptor interceptor = (Interceptor) Call.this.client.interceptors().get(this.index);
            Response intercept = interceptor.intercept(applicationInterceptorChain);
            if (intercept != null) {
                return intercept;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("application interceptor ");
            stringBuilder.append(interceptor);
            stringBuilder.append(" returned null");
            throw new NullPointerException(stringBuilder.toString());
        }
    }

    final class AsyncCall extends NamedRunnable {
        private final boolean forWebSocket;
        private final Callback responseCallback;

        private AsyncCall(Callback callback, boolean z) {
            super("OkHttp %s", r3.originalRequest.urlString());
            this.responseCallback = callback;
            this.forWebSocket = z;
        }

        String host() {
            return Call.this.originalRequest.httpUrl().host();
        }

        Request request() {
            return Call.this.originalRequest;
        }

        Object tag() {
            return Call.this.originalRequest.tag();
        }

        void cancel() {
            Call.this.cancel();
        }

        Call get() {
            return Call.this;
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x005c A:{Catch:{ all -> 0x0036 }} */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x003d A:{SYNTHETIC, Splitter: B:13:0x003d} */
        protected void execute() {
            /*
            r5 = this;
            r0 = 1;
            r1 = 0;
            r2 = com.squareup.okhttp.Call.this;	 Catch:{ IOException -> 0x0038 }
            r3 = r5.forWebSocket;	 Catch:{ IOException -> 0x0038 }
            r2 = r2.getResponseWithInterceptorChain(r3);	 Catch:{ IOException -> 0x0038 }
            r3 = com.squareup.okhttp.Call.this;	 Catch:{ IOException -> 0x0038 }
            r1 = r3.canceled;	 Catch:{ IOException -> 0x0038 }
            if (r1 == 0) goto L_0x0021;
        L_0x0010:
            r1 = r5.responseCallback;	 Catch:{ IOException -> 0x0034 }
            r2 = com.squareup.okhttp.Call.this;	 Catch:{ IOException -> 0x0034 }
            r2 = r2.originalRequest;	 Catch:{ IOException -> 0x0034 }
            r3 = new java.io.IOException;	 Catch:{ IOException -> 0x0034 }
            r4 = "Canceled";
            r3.<init>(r4);	 Catch:{ IOException -> 0x0034 }
            r1.onFailure(r2, r3);	 Catch:{ IOException -> 0x0034 }
            goto L_0x0026;
        L_0x0021:
            r1 = r5.responseCallback;	 Catch:{ IOException -> 0x0034 }
            r1.onResponse(r2);	 Catch:{ IOException -> 0x0034 }
        L_0x0026:
            r0 = com.squareup.okhttp.Call.this;
            r0 = r0.client;
            r0 = r0.getDispatcher();
            r0.finished(r5);
            goto L_0x0075;
        L_0x0034:
            r1 = move-exception;
            goto L_0x003b;
        L_0x0036:
            r0 = move-exception;
            goto L_0x0076;
        L_0x0038:
            r0 = move-exception;
            r1 = r0;
            r0 = 0;
        L_0x003b:
            if (r0 == 0) goto L_0x005c;
        L_0x003d:
            r0 = com.squareup.okhttp.internal.Internal.logger;	 Catch:{ all -> 0x0036 }
            r2 = java.util.logging.Level.INFO;	 Catch:{ all -> 0x0036 }
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0036 }
            r3.<init>();	 Catch:{ all -> 0x0036 }
            r4 = "Callback failure for ";
            r3.append(r4);	 Catch:{ all -> 0x0036 }
            r4 = com.squareup.okhttp.Call.this;	 Catch:{ all -> 0x0036 }
            r4 = r4.toLoggableString();	 Catch:{ all -> 0x0036 }
            r3.append(r4);	 Catch:{ all -> 0x0036 }
            r3 = r3.toString();	 Catch:{ all -> 0x0036 }
            r0.log(r2, r3, r1);	 Catch:{ all -> 0x0036 }
            goto L_0x0026;
        L_0x005c:
            r0 = com.squareup.okhttp.Call.this;	 Catch:{ all -> 0x0036 }
            r0 = r0.engine;	 Catch:{ all -> 0x0036 }
            if (r0 != 0) goto L_0x0067;
        L_0x0062:
            r0 = com.squareup.okhttp.Call.this;	 Catch:{ all -> 0x0036 }
            r0 = r0.originalRequest;	 Catch:{ all -> 0x0036 }
            goto L_0x006f;
        L_0x0067:
            r0 = com.squareup.okhttp.Call.this;	 Catch:{ all -> 0x0036 }
            r0 = r0.engine;	 Catch:{ all -> 0x0036 }
            r0 = r0.getRequest();	 Catch:{ all -> 0x0036 }
        L_0x006f:
            r2 = r5.responseCallback;	 Catch:{ all -> 0x0036 }
            r2.onFailure(r0, r1);	 Catch:{ all -> 0x0036 }
            goto L_0x0026;
        L_0x0075:
            return;
        L_0x0076:
            r1 = com.squareup.okhttp.Call.this;
            r1 = r1.client;
            r1 = r1.getDispatcher();
            r1.finished(r5);
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.Call.AsyncCall.execute():void");
        }
    }

    protected Call(OkHttpClient okHttpClient, Request request) {
        this.client = okHttpClient.copyWithDefaults();
        this.originalRequest = request;
    }

    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        try {
            this.client.getDispatcher().executed(this);
            Response responseWithInterceptorChain = getResponseWithInterceptorChain(false);
            if (responseWithInterceptorChain != null) {
                return responseWithInterceptorChain;
            }
            throw new IOException("Canceled");
        } finally {
            this.client.getDispatcher().finished(this);
        }
    }

    Object tag() {
        return this.originalRequest.tag();
    }

    public void enqueue(Callback callback) {
        enqueue(callback, false);
    }

    void enqueue(Callback callback, boolean z) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        this.client.getDispatcher().enqueue(new AsyncCall(callback, z));
    }

    public void cancel() {
        this.canceled = true;
        HttpEngine httpEngine = this.engine;
        if (httpEngine != null) {
            httpEngine.cancel();
        }
    }

    public synchronized boolean isExecuted() {
        return this.executed;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    private String toLoggableString() {
        String str = this.canceled ? "canceled call" : NotificationCompat.CATEGORY_CALL;
        HttpUrl resolve = this.originalRequest.httpUrl().resolve("/...");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" to ");
        stringBuilder.append(resolve);
        return stringBuilder.toString();
    }

    private Response getResponseWithInterceptorChain(boolean z) throws IOException {
        return new ApplicationInterceptorChain(0, this.originalRequest, z).proceed(this.originalRequest);
    }

    Response getResponse(Request request, boolean z) throws IOException {
        Object obj;
        Throwable th;
        RequestBody body = request.body();
        if (body != null) {
            Builder newBuilder = request.newBuilder();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                newBuilder.header(HttpHeaders.CONTENT_TYPE, contentType.toString());
            }
            long contentLength = body.contentLength();
            String str = HttpHeaders.CONTENT_LENGTH;
            String str2 = HttpHeaders.TRANSFER_ENCODING;
            if (contentLength != -1) {
                newBuilder.header(str, Long.toString(contentLength));
                newBuilder.removeHeader(str2);
            } else {
                newBuilder.header(str2, "chunked");
                newBuilder.removeHeader(str);
            }
            request = newBuilder.build();
        }
        this.engine = new HttpEngine(this.client, request, false, false, z, null, null, null);
        int i = 0;
        while (!this.canceled) {
            obj = 1;
            try {
                this.engine.sendRequest();
                this.engine.readResponse();
                Response response = this.engine.getResponse();
                Request followUpRequest = this.engine.followUpRequest();
                if (followUpRequest == null) {
                    if (!z) {
                        this.engine.releaseStreamAllocation();
                    }
                    return response;
                }
                StreamAllocation close = this.engine.close();
                i++;
                if (i <= 20) {
                    StreamAllocation streamAllocation;
                    if (this.engine.sameConnection(followUpRequest.httpUrl())) {
                        streamAllocation = close;
                    } else {
                        close.release();
                        streamAllocation = null;
                    }
                    this.engine = new HttpEngine(this.client, followUpRequest, false, false, z, streamAllocation, null, response);
                } else {
                    close.release();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Too many follow-up requests: ");
                    stringBuilder.append(i);
                    throw new ProtocolException(stringBuilder.toString());
                }
            } catch (RequestException e) {
                throw e.getCause();
            } catch (RouteException e2) {
                HttpEngine recover = this.engine.recover(e2);
                if (recover != null) {
                    this.engine = recover;
                } else {
                    throw e2.getLastConnectException();
                }
            } catch (IOException e3) {
                HttpEngine recover2 = this.engine.recover(e3, null);
                if (recover2 != null) {
                    this.engine = recover2;
                } else {
                    throw e3;
                }
            } catch (Throwable th2) {
                th = th2;
                obj = null;
            }
        }
        this.engine.releaseStreamAllocation();
        throw new IOException("Canceled");
        if (obj != null) {
            this.engine.close().release();
        }
        throw th;
    }
}
