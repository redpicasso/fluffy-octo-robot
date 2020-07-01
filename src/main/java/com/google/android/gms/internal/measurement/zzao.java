package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

final class zzao extends zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ zzl zzat;

    zzao(zzz zzz, zzl zzl) {
        this.zzaa = zzz;
        this.zzat = zzl;
        super(zzz);
    }

    final void zzf() throws RemoteException {
        this.zzaa.zzar.getCurrentScreenClass(this.zzat);
    }

    protected final void zzk() {
        this.zzat.zzb(null);
    }
}
