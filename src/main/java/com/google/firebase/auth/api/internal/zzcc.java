package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.firebase_auth.zzcp;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzcc implements RemoteCall {
    private final zzcd zzns;

    zzcc(zzcd zzcd) {
        this.zzns = zzcd;
    }

    public final void accept(Object obj, Object obj2) {
        zzen zzen = this.zzns;
        zzdp zzdp = (zzdp) obj;
        zzen.zzpu = new zzeu(zzen, (TaskCompletionSource) obj2);
        if (zzen.zzqh) {
            zzdp.zzeb().zzf(zzen.zzpr.zzcz(), zzen.zzpq);
        } else {
            zzdp.zzeb().zza(new zzcp(zzen.zzpr.zzcz()), zzen.zzpq);
        }
    }
}
