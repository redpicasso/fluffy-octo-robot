package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.api.internal.GoogleApiManager.zaa;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zad<A extends ApiMethodImpl<? extends Result, AnyClient>> extends zac {
    private final A zacp;

    public zad(int i, A a) {
        super(i);
        this.zacp = a;
    }

    public final void zac(zaa<?> zaa) throws DeadObjectException {
        try {
            this.zacp.run(zaa.zaad());
        } catch (RuntimeException e) {
            zaa(e);
        }
    }

    public final void zaa(@NonNull Status status) {
        this.zacp.setFailedResult(status);
    }

    public final void zaa(@NonNull RuntimeException runtimeException) {
        String simpleName = runtimeException.getClass().getSimpleName();
        String localizedMessage = runtimeException.getLocalizedMessage();
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(simpleName).length() + 2) + String.valueOf(localizedMessage).length());
        stringBuilder.append(simpleName);
        stringBuilder.append(": ");
        stringBuilder.append(localizedMessage);
        this.zacp.setFailedResult(new Status(10, stringBuilder.toString()));
    }

    public final void zaa(@NonNull zaz zaz, boolean z) {
        zaz.zaa(this.zacp, z);
    }
}
