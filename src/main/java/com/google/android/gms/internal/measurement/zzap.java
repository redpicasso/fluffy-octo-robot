package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

final class zzap extends zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ zzl zzat;

    zzap(zzz zzz, zzl zzl) {
        this.zzaa = zzz;
        this.zzat = zzl;
        super(zzz);
    }

    final void zzf() throws RemoteException {
        this.zzaa.zzar.getCurrentScreenName(this.zzat);
    }

    protected final void zzk() {
        this.zzat.zzb(null);
    }
}
