package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.firebase_auth.zzbz;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzay implements RemoteCall {
    private final zzaz zzmw;

    zzay(zzaz zzaz) {
        this.zzmw = zzaz;
    }

    public final void accept(Object obj, Object obj2) {
        zzen zzen = this.zzmw;
        zzdp zzdp = (zzdp) obj;
        zzen.zzpu = new zzeu(zzen, (TaskCompletionSource) obj2);
        if (zzen.zzqh) {
            zzdp.zzeb().zzg(zzen.zzpr.zzcz(), zzen.zzpq);
        } else {
            zzdp.zzeb().zza(new zzbz(zzen.zzpr.zzcz()), zzen.zzpq);
        }
    }
}
