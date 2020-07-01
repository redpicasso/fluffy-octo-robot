package com.google.android.gms.internal.firebase_ml;

import com.google.firebase.ml.common.FirebaseMLException;
import java.util.concurrent.Callable;

final class zzns implements Callable<T> {
    private final /* synthetic */ zznw zzaph;
    private final /* synthetic */ zznq zzapi;
    private final /* synthetic */ zznm zzapj;
    private final /* synthetic */ zznp zzapk;

    zzns(zznq zznq, zznw zznw, zznm zznm, zznp zznp) {
        this.zzapi = zznq;
        this.zzaph = zznw;
        this.zzapj = zznm;
        this.zzapk = zznp;
    }

    public final T call() throws FirebaseMLException {
        if (this.zzaph != null) {
            this.zzapi.zzapg.zzf(this.zzaph);
        }
        return this.zzapj.zza(this.zzapk);
    }
}
