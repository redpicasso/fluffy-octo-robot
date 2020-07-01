package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zaf<ResultT> extends zab {
    private final TaskCompletionSource<ResultT> zacq;
    private final TaskApiCall<AnyClient, ResultT> zacr;
    private final StatusExceptionMapper zacs;

    public zaf(int i, TaskApiCall<AnyClient, ResultT> taskApiCall, TaskCompletionSource<ResultT> taskCompletionSource, StatusExceptionMapper statusExceptionMapper) {
        super(i);
        this.zacq = taskCompletionSource;
        this.zacr = taskApiCall;
        this.zacs = statusExceptionMapper;
    }

    public final void zac(zaa<?> zaa) throws DeadObjectException {
        try {
            this.zacr.doExecute(zaa.zaad(), this.zacq);
        } catch (DeadObjectException e) {
            throw e;
        } catch (RemoteException e2) {
            zaa(zac.zaa(e2));
        } catch (RuntimeException e3) {
            zaa(e3);
        }
    }

    public final void zaa(@NonNull Status status) {
        this.zacq.trySetException(this.zacs.getException(status));
    }

    public final void zaa(@NonNull RuntimeException runtimeException) {
        this.zacq.trySetException(runtimeException);
    }

    public final void zaa(@NonNull zaz zaz, boolean z) {
        zaz.zaa(this.zacq, z);
    }

    @Nullable
    public final Feature[] zaa(zaa<?> zaa) {
        return this.zacr.zabr();
    }

    public final boolean zab(zaa<?> zaa) {
        return this.zacr.shouldAutoResolveMissingFeatures();
    }
}
