package com.google.firebase.auth.api.internal;

import androidx.annotation.Nullable;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfq;
import com.google.firebase.auth.internal.zzt;

final class zzn implements zzez<zzfq> {
    private final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;

    zzn(zzb zzb, zzdm zzdm) {
        this.zzle = zzb;
        this.zzla = zzdm;
    }

    public final void zzbv(@Nullable String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzfq zzfq = (zzfq) obj;
        this.zzle.zza(new zzes(zzfq.zzs(), zzfq.getIdToken(), Long.valueOf(zzfq.zzt()), "Bearer"), null, null, Boolean.valueOf(zzfq.isNewUser()), null, this.zzla, this);
    }
}
