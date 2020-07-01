package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

final class zzai extends zzb {
    private final /* synthetic */ zzz zzaa;
    private final /* synthetic */ String zzbb;

    zzai(zzz zzz, String str) {
        this.zzaa = zzz;
        this.zzbb = str;
        super(zzz);
    }

    final void zzf() throws RemoteException {
        this.zzaa.zzar.endAdUnitExposure(this.zzbb, this.zzbt);
    }
}
