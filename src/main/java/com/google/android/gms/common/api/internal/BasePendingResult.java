package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.StatusListener;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.base.zar;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@KeepName
@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public abstract class BasePendingResult<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> zado = new zao();
    @KeepName
    private zaa mResultGuardian;
    private Status mStatus;
    private R zacl;
    private final Object zadp;
    private final CallbackHandler<R> zadq;
    private final WeakReference<GoogleApiClient> zadr;
    private final CountDownLatch zads;
    private final ArrayList<StatusListener> zadt;
    private ResultCallback<? super R> zadu;
    private final AtomicReference<zacq> zadv;
    private volatile boolean zadw;
    private boolean zadx;
    private boolean zady;
    private ICancelToken zadz;
    private volatile zack<R> zaea;
    private boolean zaeb;

    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    private final class zaa {
        private zaa() {
        }

        protected final void finalize() throws Throwable {
            BasePendingResult.zab(BasePendingResult.this.zacl);
            super.finalize();
        }

        /* synthetic */ zaa(BasePendingResult basePendingResult, zao zao) {
            this();
        }
    }

    @VisibleForTesting
    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public static class CallbackHandler<R extends Result> extends zar {
        public CallbackHandler() {
            this(Looper.getMainLooper());
        }

        public CallbackHandler(Looper looper) {
            super(looper);
        }

        public final void zaa(ResultCallback<? super R> resultCallback, R r) {
            sendMessage(obtainMessage(1, new Pair(BasePendingResult.zaa((ResultCallback) resultCallback), r)));
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Pair pair = (Pair) message.obj;
                ResultCallback resultCallback = (ResultCallback) pair.first;
                Result result = (Result) pair.second;
                try {
                    resultCallback.onResult(result);
                } catch (RuntimeException e) {
                    BasePendingResult.zab(result);
                    throw e;
                }
            } else if (i != 2) {
                int i2 = message.what;
                StringBuilder stringBuilder = new StringBuilder(45);
                stringBuilder.append("Don't know how to handle message: ");
                stringBuilder.append(i2);
                Log.wtf("BasePendingResult", stringBuilder.toString(), new Exception());
            } else {
                ((BasePendingResult) message.obj).zab(Status.RESULT_TIMEOUT);
            }
        }
    }

    @Deprecated
    BasePendingResult() {
        this.zadp = new Object();
        this.zads = new CountDownLatch(1);
        this.zadt = new ArrayList();
        this.zadv = new AtomicReference();
        this.zaeb = false;
        this.zadq = new CallbackHandler(Looper.getMainLooper());
        this.zadr = new WeakReference(null);
    }

    private static <R extends Result> ResultCallback<R> zaa(ResultCallback<R> resultCallback) {
        return resultCallback;
    }

    @NonNull
    @KeepForSdk
    protected abstract R createFailedResult(Status status);

    public final Integer zal() {
        return null;
    }

    @KeepForSdk
    protected BasePendingResult(GoogleApiClient googleApiClient) {
        this.zadp = new Object();
        this.zads = new CountDownLatch(1);
        this.zadt = new ArrayList();
        this.zadv = new AtomicReference();
        this.zaeb = false;
        this.zadq = new CallbackHandler(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
        this.zadr = new WeakReference(googleApiClient);
    }

    @KeepForSdk
    @Deprecated
    protected BasePendingResult(Looper looper) {
        this.zadp = new Object();
        this.zads = new CountDownLatch(1);
        this.zadt = new ArrayList();
        this.zadv = new AtomicReference();
        this.zaeb = false;
        this.zadq = new CallbackHandler(looper);
        this.zadr = new WeakReference(null);
    }

    @KeepForSdk
    @VisibleForTesting
    protected BasePendingResult(@NonNull CallbackHandler<R> callbackHandler) {
        this.zadp = new Object();
        this.zads = new CountDownLatch(1);
        this.zadt = new ArrayList();
        this.zadv = new AtomicReference();
        this.zaeb = false;
        this.zadq = (CallbackHandler) Preconditions.checkNotNull(callbackHandler, "CallbackHandler must not be null");
        this.zadr = new WeakReference(null);
    }

    @KeepForSdk
    public final boolean isReady() {
        return this.zads.getCount() == 0;
    }

    public final R await() {
        Preconditions.checkNotMainThread("await must not be called on the UI thread");
        boolean z = true;
        Preconditions.checkState(this.zadw ^ true, "Result has already been consumed");
        if (this.zaea != null) {
            z = false;
        }
        Preconditions.checkState(z, "Cannot await if then() has been called.");
        try {
            this.zads.await();
        } catch (InterruptedException unused) {
            zab(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(isReady(), "Result is not ready.");
        return get();
    }

    public final R await(long j, TimeUnit timeUnit) {
        if (j > 0) {
            Preconditions.checkNotMainThread("await must not be called on the UI thread when time is greater than zero.");
        }
        boolean z = true;
        Preconditions.checkState(this.zadw ^ true, "Result has already been consumed.");
        if (this.zaea != null) {
            z = false;
        }
        Preconditions.checkState(z, "Cannot await if then() has been called.");
        try {
            if (!this.zads.await(j, timeUnit)) {
                zab(Status.RESULT_TIMEOUT);
            }
        } catch (InterruptedException unused) {
            zab(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(isReady(), "Result is not ready.");
        return get();
    }

    /* JADX WARNING: Missing block: B:24:0x003e, code:
            return;
     */
    @com.google.android.gms.common.annotation.KeepForSdk
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r6) {
        /*
        r5 = this;
        r0 = r5.zadp;
        monitor-enter(r0);
        if (r6 != 0) goto L_0x000a;
    L_0x0005:
        r6 = 0;
        r5.zadu = r6;	 Catch:{ all -> 0x003f }
        monitor-exit(r0);	 Catch:{ all -> 0x003f }
        return;
    L_0x000a:
        r1 = r5.zadw;	 Catch:{ all -> 0x003f }
        r2 = 1;
        r3 = 0;
        if (r1 != 0) goto L_0x0012;
    L_0x0010:
        r1 = 1;
        goto L_0x0013;
    L_0x0012:
        r1 = 0;
    L_0x0013:
        r4 = "Result has already been consumed.";
        com.google.android.gms.common.internal.Preconditions.checkState(r1, r4);	 Catch:{ all -> 0x003f }
        r1 = r5.zaea;	 Catch:{ all -> 0x003f }
        if (r1 != 0) goto L_0x001d;
    L_0x001c:
        goto L_0x001e;
    L_0x001d:
        r2 = 0;
    L_0x001e:
        r1 = "Cannot set callbacks if then() has been called.";
        com.google.android.gms.common.internal.Preconditions.checkState(r2, r1);	 Catch:{ all -> 0x003f }
        r1 = r5.isCanceled();	 Catch:{ all -> 0x003f }
        if (r1 == 0) goto L_0x002b;
    L_0x0029:
        monitor-exit(r0);	 Catch:{ all -> 0x003f }
        return;
    L_0x002b:
        r1 = r5.isReady();	 Catch:{ all -> 0x003f }
        if (r1 == 0) goto L_0x003b;
    L_0x0031:
        r1 = r5.zadq;	 Catch:{ all -> 0x003f }
        r2 = r5.get();	 Catch:{ all -> 0x003f }
        r1.zaa(r6, r2);	 Catch:{ all -> 0x003f }
        goto L_0x003d;
    L_0x003b:
        r5.zadu = r6;	 Catch:{ all -> 0x003f }
    L_0x003d:
        monitor-exit(r0);	 Catch:{ all -> 0x003f }
        return;
    L_0x003f:
        r6 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x003f }
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.setResultCallback(com.google.android.gms.common.api.ResultCallback):void");
    }

    /* JADX WARNING: Missing block: B:24:0x004c, code:
            return;
     */
    @com.google.android.gms.common.annotation.KeepForSdk
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r6, long r7, java.util.concurrent.TimeUnit r9) {
        /*
        r5 = this;
        r0 = r5.zadp;
        monitor-enter(r0);
        if (r6 != 0) goto L_0x000a;
    L_0x0005:
        r6 = 0;
        r5.zadu = r6;	 Catch:{ all -> 0x004d }
        monitor-exit(r0);	 Catch:{ all -> 0x004d }
        return;
    L_0x000a:
        r1 = r5.zadw;	 Catch:{ all -> 0x004d }
        r2 = 1;
        r3 = 0;
        if (r1 != 0) goto L_0x0012;
    L_0x0010:
        r1 = 1;
        goto L_0x0013;
    L_0x0012:
        r1 = 0;
    L_0x0013:
        r4 = "Result has already been consumed.";
        com.google.android.gms.common.internal.Preconditions.checkState(r1, r4);	 Catch:{ all -> 0x004d }
        r1 = r5.zaea;	 Catch:{ all -> 0x004d }
        if (r1 != 0) goto L_0x001d;
    L_0x001c:
        goto L_0x001e;
    L_0x001d:
        r2 = 0;
    L_0x001e:
        r1 = "Cannot set callbacks if then() has been called.";
        com.google.android.gms.common.internal.Preconditions.checkState(r2, r1);	 Catch:{ all -> 0x004d }
        r1 = r5.isCanceled();	 Catch:{ all -> 0x004d }
        if (r1 == 0) goto L_0x002b;
    L_0x0029:
        monitor-exit(r0);	 Catch:{ all -> 0x004d }
        return;
    L_0x002b:
        r1 = r5.isReady();	 Catch:{ all -> 0x004d }
        if (r1 == 0) goto L_0x003b;
    L_0x0031:
        r7 = r5.zadq;	 Catch:{ all -> 0x004d }
        r8 = r5.get();	 Catch:{ all -> 0x004d }
        r7.zaa(r6, r8);	 Catch:{ all -> 0x004d }
        goto L_0x004b;
    L_0x003b:
        r5.zadu = r6;	 Catch:{ all -> 0x004d }
        r6 = r5.zadq;	 Catch:{ all -> 0x004d }
        r7 = r9.toMillis(r7);	 Catch:{ all -> 0x004d }
        r9 = 2;
        r9 = r6.obtainMessage(r9, r5);	 Catch:{ all -> 0x004d }
        r6.sendMessageDelayed(r9, r7);	 Catch:{ all -> 0x004d }
    L_0x004b:
        monitor-exit(r0);	 Catch:{ all -> 0x004d }
        return;
    L_0x004d:
        r6 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x004d }
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.setResultCallback(com.google.android.gms.common.api.ResultCallback, long, java.util.concurrent.TimeUnit):void");
    }

    public final void addStatusListener(StatusListener statusListener) {
        Preconditions.checkArgument(statusListener != null, "Callback cannot be null.");
        synchronized (this.zadp) {
            if (isReady()) {
                statusListener.onComplete(this.mStatus);
            } else {
                this.zadt.add(statusListener);
            }
        }
    }

    @KeepForSdk
    public void cancel() {
        synchronized (this.zadp) {
            if (this.zadx || this.zadw) {
            } else {
                if (this.zadz != null) {
                    this.zadz.cancel();
                }
                try {
                } catch (RemoteException unused) {
                    zab(this.zacl);
                    this.zadx = true;
                    zaa(createFailedResult(Status.RESULT_CANCELED));
                }
            }
        }
    }

    public final boolean zaq() {
        boolean isCanceled;
        synchronized (this.zadp) {
            if (((GoogleApiClient) this.zadr.get()) == null || !this.zaeb) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.zadp) {
            z = this.zadx;
        }
        return z;
    }

    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> then;
        Preconditions.checkState(this.zadw ^ true, "Result has already been consumed.");
        synchronized (this.zadp) {
            boolean z = false;
            Preconditions.checkState(this.zaea == null, "Cannot call then() twice.");
            Preconditions.checkState(this.zadu == null, "Cannot call then() if callbacks are set.");
            if (!this.zadx) {
                z = true;
            }
            Preconditions.checkState(z, "Cannot call then() if result was canceled.");
            this.zaeb = true;
            this.zaea = new zack(this.zadr);
            then = this.zaea.then(resultTransform);
            if (isReady()) {
                this.zadq.zaa(this.zaea, get());
            } else {
                this.zadu = this.zaea;
            }
        }
        return then;
    }

    @KeepForSdk
    public final void setResult(R r) {
        synchronized (this.zadp) {
            if (this.zady || this.zadx) {
                zab((Result) r);
                return;
            }
            isReady();
            boolean z = true;
            Preconditions.checkState(!isReady(), "Results have already been set");
            if (this.zadw) {
                z = false;
            }
            Preconditions.checkState(z, "Result has already been consumed");
            zaa((Result) r);
        }
    }

    public final void zab(Status status) {
        synchronized (this.zadp) {
            if (!isReady()) {
                setResult(createFailedResult(status));
                this.zady = true;
            }
        }
    }

    public final void zaa(zacq zacq) {
        this.zadv.set(zacq);
    }

    @KeepForSdk
    protected final void setCancelToken(ICancelToken iCancelToken) {
        synchronized (this.zadp) {
            this.zadz = iCancelToken;
        }
    }

    public final void zar() {
        boolean z = this.zaeb || ((Boolean) zado.get()).booleanValue();
        this.zaeb = z;
    }

    private final R get() {
        R r;
        synchronized (this.zadp) {
            Preconditions.checkState(!this.zadw, "Result has already been consumed.");
            Preconditions.checkState(isReady(), "Result is not ready.");
            r = this.zacl;
            this.zacl = null;
            this.zadu = null;
            this.zadw = true;
        }
        zacq zacq = (zacq) this.zadv.getAndSet(null);
        if (zacq != null) {
            zacq.zab(this);
        }
        return r;
    }

    private final void zaa(R r) {
        this.zacl = r;
        this.zadz = null;
        this.zads.countDown();
        this.mStatus = this.zacl.getStatus();
        if (this.zadx) {
            this.zadu = null;
        } else if (this.zadu != null) {
            this.zadq.removeMessages(2);
            this.zadq.zaa(this.zadu, get());
        } else if (this.zacl instanceof Releasable) {
            this.mResultGuardian = new zaa(this, null);
        }
        ArrayList arrayList = this.zadt;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((StatusListener) obj).onComplete(this.mStatus);
        }
        this.zadt.clear();
    }

    public static void zab(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (Throwable e) {
                String valueOf = String.valueOf(result);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 18);
                stringBuilder.append("Unable to release ");
                stringBuilder.append(valueOf);
                Log.w("BasePendingResult", stringBuilder.toString(), e);
            }
        }
    }
}
