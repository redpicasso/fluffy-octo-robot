package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

final class zzak extends zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ zzl zzat;

    zzak(zzz zzz, zzl zzl) {
        this.zzaa = zzz;
        this.zzat = zzl;
        super(zzz);
    }

    final void zzf() throws RemoteException {
        this.zzaa.zzar.getGmpAppId(this.zzat);
    }

    protected final void zzk() {
        this.zzat.zzb(null);
    }
}
