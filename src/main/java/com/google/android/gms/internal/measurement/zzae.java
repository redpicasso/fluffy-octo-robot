package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

final class zzae extends zzb {
    private final /* synthetic */ zzz zzaa;

    zzae(zzz zzz) {
        this.zzaa = zzz;
        super(zzz);
    }

    final void zzf() throws RemoteException {
        this.zzaa.zzar.resetAnalyticsData(this.timestamp);
    }
}
