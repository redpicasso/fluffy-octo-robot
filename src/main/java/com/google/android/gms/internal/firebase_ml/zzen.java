package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;

final class zzen implements zzfn {
    private final /* synthetic */ zzfn zztf;
    private final /* synthetic */ zzfh zztg;
    private final /* synthetic */ zzem zzth;

    zzen(zzem zzem, zzfn zzfn, zzfh zzfh) {
        this.zzth = zzem;
        this.zztf = zzfn;
        this.zztg = zzfh;
    }

    public final void zzb(zzfk zzfk) throws IOException {
        zzfn zzfn = this.zztf;
        if (zzfn != null) {
            zzfn.zzb(zzfk);
        }
        if (!zzfk.zzfk() && this.zztg.zzfi()) {
            throw this.zzth.zza(zzfk);
        }
    }
}
