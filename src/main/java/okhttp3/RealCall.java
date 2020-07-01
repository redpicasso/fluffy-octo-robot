package okhttp3;

import androidx.core.app.NotificationCompat;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;
import okio.AsyncTimeout;
import okio.Timeout;

final class RealCall implements Call {
    final OkHttpClient client;
    @Nullable
    private EventListener eventListener;
    private boolean executed;
    final boolean forWebSocket;
    final Request originalRequest;
    final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;
    final AsyncTimeout timeout = new AsyncTimeout() {
        protected void timedOut() {
            RealCall.this.cancel();
        }
    };

    final class AsyncCall extends NamedRunnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Callback responseCallback;

        static {
            Class cls = RealCall.class;
        }

        AsyncCall(Callback callback) {
            super("OkHttp %s", r3.redactedUrl());
            this.responseCallback = callback;
        }

        String host() {
            return RealCall.this.originalRequest.url().host();
        }

        Request request() {
            return RealCall.this.originalRequest;
        }

        RealCall get() {
            return RealCall.this;
        }

        void executeOn(ExecutorService executorService) {
            try {
                executorService.execute(this);
            } catch (Throwable e) {
                IOException interruptedIOException = new InterruptedIOException("executor rejected");
                interruptedIOException.initCause(e);
                RealCall.this.eventListener.callFailed(RealCall.this, interruptedIOException);
                this.responseCallback.onFailure(RealCall.this, interruptedIOException);
            } finally {
                RealCall.this.client.dispatcher().finished(this);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x006a A:{Catch:{ all -> 0x003d }} */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x004a A:{Catch:{ all -> 0x003d }} */
        protected void execute() {
            /*
            r5 = this;
            r0 = okhttp3.RealCall.this;
            r0 = r0.timeout;
            r0.enter();
            r0 = 1;
            r1 = 0;
            r2 = okhttp3.RealCall.this;	 Catch:{ IOException -> 0x003f }
            r2 = r2.getResponseWithInterceptorChain();	 Catch:{ IOException -> 0x003f }
            r3 = okhttp3.RealCall.this;	 Catch:{ IOException -> 0x003f }
            r3 = r3.retryAndFollowUpInterceptor;	 Catch:{ IOException -> 0x003f }
            r1 = r3.isCanceled();	 Catch:{ IOException -> 0x003f }
            if (r1 == 0) goto L_0x0028;
        L_0x0019:
            r1 = r5.responseCallback;	 Catch:{ IOException -> 0x003b }
            r2 = okhttp3.RealCall.this;	 Catch:{ IOException -> 0x003b }
            r3 = new java.io.IOException;	 Catch:{ IOException -> 0x003b }
            r4 = "Canceled";
            r3.<init>(r4);	 Catch:{ IOException -> 0x003b }
            r1.onFailure(r2, r3);	 Catch:{ IOException -> 0x003b }
            goto L_0x002f;
        L_0x0028:
            r1 = r5.responseCallback;	 Catch:{ IOException -> 0x003b }
            r3 = okhttp3.RealCall.this;	 Catch:{ IOException -> 0x003b }
            r1.onResponse(r3, r2);	 Catch:{ IOException -> 0x003b }
        L_0x002f:
            r0 = okhttp3.RealCall.this;
            r0 = r0.client;
            r0 = r0.dispatcher();
            r0.finished(r5);
            goto L_0x007d;
        L_0x003b:
            r1 = move-exception;
            goto L_0x0042;
        L_0x003d:
            r0 = move-exception;
            goto L_0x007e;
        L_0x003f:
            r0 = move-exception;
            r1 = r0;
            r0 = 0;
        L_0x0042:
            r2 = okhttp3.RealCall.this;	 Catch:{ all -> 0x003d }
            r1 = r2.timeoutExit(r1);	 Catch:{ all -> 0x003d }
            if (r0 == 0) goto L_0x006a;
        L_0x004a:
            r0 = okhttp3.internal.platform.Platform.get();	 Catch:{ all -> 0x003d }
            r2 = 4;
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x003d }
            r3.<init>();	 Catch:{ all -> 0x003d }
            r4 = "Callback failure for ";
            r3.append(r4);	 Catch:{ all -> 0x003d }
            r4 = okhttp3.RealCall.this;	 Catch:{ all -> 0x003d }
            r4 = r4.toLoggableString();	 Catch:{ all -> 0x003d }
            r3.append(r4);	 Catch:{ all -> 0x003d }
            r3 = r3.toString();	 Catch:{ all -> 0x003d }
            r0.log(r2, r3, r1);	 Catch:{ all -> 0x003d }
            goto L_0x002f;
        L_0x006a:
            r0 = okhttp3.RealCall.this;	 Catch:{ all -> 0x003d }
            r0 = r0.eventListener;	 Catch:{ all -> 0x003d }
            r2 = okhttp3.RealCall.this;	 Catch:{ all -> 0x003d }
            r0.callFailed(r2, r1);	 Catch:{ all -> 0x003d }
            r0 = r5.responseCallback;	 Catch:{ all -> 0x003d }
            r2 = okhttp3.RealCall.this;	 Catch:{ all -> 0x003d }
            r0.onFailure(r2, r1);	 Catch:{ all -> 0x003d }
            goto L_0x002f;
        L_0x007d:
            return;
        L_0x007e:
            r1 = okhttp3.RealCall.this;
            r1 = r1.client;
            r1 = r1.dispatcher();
            r1.finished(r5);
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.RealCall.AsyncCall.execute():void");
        }
    }

    private RealCall(OkHttpClient okHttpClient, Request request, boolean z) {
        this.client = okHttpClient;
        this.originalRequest = request;
        this.forWebSocket = z;
        this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(okHttpClient, z);
        this.timeout.timeout((long) okHttpClient.callTimeoutMillis(), TimeUnit.MILLISECONDS);
    }

    static RealCall newRealCall(OkHttpClient okHttpClient, Request request, boolean z) {
        RealCall realCall = new RealCall(okHttpClient, request, z);
        realCall.eventListener = okHttpClient.eventListenerFactory().create(realCall);
        return realCall;
    }

    public Request request() {
        return this.originalRequest;
    }

    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        captureCallStackTrace();
        this.timeout.enter();
        this.eventListener.callStart(this);
        try {
            this.client.dispatcher().executed(this);
            Response responseWithInterceptorChain = getResponseWithInterceptorChain();
            if (responseWithInterceptorChain != null) {
                this.client.dispatcher().finished(this);
                return responseWithInterceptorChain;
            }
            throw new IOException("Canceled");
        } catch (IOException iOException) {
            IOException iOException2 = timeoutExit(iOException2);
            this.eventListener.callFailed(this, iOException2);
            throw iOException2;
        } catch (Throwable th) {
            this.client.dispatcher().finished(this);
        }
    }

    @Nullable
    IOException timeoutExit(@Nullable IOException iOException) {
        if (!this.timeout.exit()) {
            return iOException;
        }
        IOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    private void captureCallStackTrace() {
        this.retryAndFollowUpInterceptor.setCallStackTrace(Platform.get().getStackTraceForCloseable("response.body().close()"));
    }

    public void enqueue(Callback callback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        captureCallStackTrace();
        this.eventListener.callStart(this);
        this.client.dispatcher().enqueue(new AsyncCall(callback));
    }

    public void cancel() {
        this.retryAndFollowUpInterceptor.cancel();
    }

    public Timeout timeout() {
        return this.timeout;
    }

    public synchronized boolean isExecuted() {
        return this.executed;
    }

    public boolean isCanceled() {
        return this.retryAndFollowUpInterceptor.isCanceled();
    }

    public RealCall clone() {
        return newRealCall(this.client, this.originalRequest, this.forWebSocket);
    }

    StreamAllocation streamAllocation() {
        return this.retryAndFollowUpInterceptor.streamAllocation();
    }

    String toLoggableString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(isCanceled() ? "canceled " : "");
        stringBuilder.append(this.forWebSocket ? "web socket" : NotificationCompat.CATEGORY_CALL);
        stringBuilder.append(" to ");
        stringBuilder.append(redactedUrl());
        return stringBuilder.toString();
    }

    String redactedUrl() {
        return this.originalRequest.url().redact();
    }

    Response getResponseWithInterceptorChain() throws IOException {
        List arrayList = new ArrayList();
        arrayList.addAll(this.client.interceptors());
        arrayList.add(this.retryAndFollowUpInterceptor);
        arrayList.add(new BridgeInterceptor(this.client.cookieJar()));
        arrayList.add(new CacheInterceptor(this.client.internalCache()));
        arrayList.add(new ConnectInterceptor(this.client));
        if (!this.forWebSocket) {
            arrayList.addAll(this.client.networkInterceptors());
        }
        arrayList.add(new CallServerInterceptor(this.forWebSocket));
        return new RealInterceptorChain(arrayList, null, null, null, 0, this.originalRequest, this, this.eventListener, this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis()).proceed(this.originalRequest);
    }
}
