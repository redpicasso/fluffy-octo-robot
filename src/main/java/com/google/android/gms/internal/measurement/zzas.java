package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

final class zzas extends zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ zzl zzat;
    private final /* synthetic */ String zzx;

    zzas(zzz zzz, String str, zzl zzl) {
        this.zzaa = zzz;
        this.zzx = str;
        this.zzat = zzl;
        super(zzz);
    }

    final void zzf() throws RemoteException {
        this.zzaa.zzar.getMaxUserProperties(this.zzx, this.zzat);
    }

    protected final void zzk() {
        this.zzat.zzb(null);
    }
}
