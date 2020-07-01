package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.firebase_auth.zzdr;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzcw implements RemoteCall {
    private final zzcx zzoi;

    zzcw(zzcx zzcx) {
        this.zzoi = zzcx;
    }

    public final void accept(Object obj, Object obj2) {
        zzen zzen = this.zzoi;
        zzdp zzdp = (zzdp) obj;
        zzen.zzpu = new zzeu(zzen, (TaskCompletionSource) obj2);
        if (zzen.zzqh) {
            zzdp.zzeb().zze(zzen.zzpr.zzcz(), zzen.zzpq);
        } else {
            zzdp.zzeb().zza(new zzdr(zzen.zzpr.zzcz()), zzen.zzpq);
        }
    }
}
