package com.google.android.gms.internal.firebase_ml;

import java.util.concurrent.Callable;

final class zznr implements Callable<TResult> {
    private final /* synthetic */ Callable val$callable;
    private final /* synthetic */ zznw zzaph;
    private final /* synthetic */ zznq zzapi;

    zznr(zznq zznq, zznw zznw, Callable callable) {
        this.zzapi = zznq;
        this.zzaph = zznw;
        this.val$callable = callable;
    }

    public final TResult call() throws Exception {
        this.zzapi.zzapg.zzf(this.zzaph);
        return this.val$callable.call();
    }
}
