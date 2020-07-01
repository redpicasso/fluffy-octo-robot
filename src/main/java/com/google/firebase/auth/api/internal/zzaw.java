package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzaw implements RemoteCall {
    private final zzax zzmu;

    zzaw(zzax zzax) {
        this.zzmu = zzax;
    }

    public final void accept(Object obj, Object obj2) {
        zzen zzen = this.zzmu;
        zzdp zzdp = (zzdp) obj;
        zzen.zzpu = new zzeu(zzen, (TaskCompletionSource) obj2);
        if (zzen.zzqh) {
            zzdp.zzeb().zzc(zzen.zzmv.getEmail(), zzen.zzmv.getPassword(), zzen.zzpq);
        } else {
            zzdp.zzeb().zza(zzen.zzmv, zzen.zzpq);
        }
    }
}
