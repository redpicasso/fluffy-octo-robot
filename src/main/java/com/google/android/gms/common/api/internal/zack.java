package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zack<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
    private final Object zadp = new Object();
    private final WeakReference<GoogleApiClient> zadr;
    private ResultTransform<? super R, ? extends Result> zakr = null;
    private zack<? extends Result> zaks = null;
    private volatile ResultCallbacks<? super R> zakt = null;
    private PendingResult<R> zaku = null;
    private Status zakv = null;
    private final zacm zakw;
    private boolean zakx = false;

    public zack(WeakReference<GoogleApiClient> weakReference) {
        Preconditions.checkNotNull(weakReference, "GoogleApiClient reference must not be null");
        this.zadr = weakReference;
        GoogleApiClient googleApiClient = (GoogleApiClient) this.zadr.get();
        this.zakw = new zacm(this, googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
    }

    @NonNull
    public final <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult zack;
        synchronized (this.zadp) {
            boolean z = true;
            Preconditions.checkState(this.zakr == null, "Cannot call then() twice.");
            if (this.zakt != null) {
                z = false;
            }
            Preconditions.checkState(z, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zakr = resultTransform;
            zack = new zack(this.zadr);
            this.zaks = zack;
            zabs();
        }
        return zack;
    }

    public final void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        synchronized (this.zadp) {
            boolean z = true;
            Preconditions.checkState(this.zakt == null, "Cannot call andFinally() twice.");
            if (this.zakr != null) {
                z = false;
            }
            Preconditions.checkState(z, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zakt = resultCallbacks;
            zabs();
        }
    }

    public final void onResult(R r) {
        synchronized (this.zadp) {
            if (!r.getStatus().isSuccess()) {
                zad(r.getStatus());
                zab(r);
            } else if (this.zakr != null) {
                zacb.zaaz().submit(new zacn(this, r));
            } else if (zabu()) {
                this.zakt.onSuccess(r);
            }
        }
    }

    public final void zaa(PendingResult<?> pendingResult) {
        synchronized (this.zadp) {
            this.zaku = pendingResult;
            zabs();
        }
    }

    @GuardedBy("mSyncToken")
    private final void zabs() {
        if (this.zakr != null || this.zakt != null) {
            GoogleApiClient googleApiClient = (GoogleApiClient) this.zadr.get();
            if (!(this.zakx || this.zakr == null || googleApiClient == null)) {
                googleApiClient.zaa(this);
                this.zakx = true;
            }
            Status status = this.zakv;
            if (status != null) {
                zae(status);
                return;
            }
            PendingResult pendingResult = this.zaku;
            if (pendingResult != null) {
                pendingResult.setResultCallback(this);
            }
        }
    }

    private final void zad(Status status) {
        synchronized (this.zadp) {
            this.zakv = status;
            zae(this.zakv);
        }
    }

    private final void zae(Status status) {
        synchronized (this.zadp) {
            if (this.zakr != null) {
                status = this.zakr.onFailure(status);
                Preconditions.checkNotNull(status, "onFailure must not return null");
                this.zaks.zad(status);
            } else if (zabu()) {
                this.zakt.onFailure(status);
            }
        }
    }

    final void zabt() {
        this.zakt = null;
    }

    @GuardedBy("mSyncToken")
    private final boolean zabu() {
        return (this.zakt == null || ((GoogleApiClient) this.zadr.get()) == null) ? false : true;
    }

    private static void zab(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (Throwable e) {
                String valueOf = String.valueOf(result);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 18);
                stringBuilder.append("Unable to release ");
                stringBuilder.append(valueOf);
                Log.w("TransformedResultImpl", stringBuilder.toString(), e);
            }
        }
    }
}
