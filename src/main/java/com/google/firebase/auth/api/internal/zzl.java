package com.google.firebase.auth.api.internal;

import androidx.annotation.Nullable;
import com.google.firebase.auth.internal.zzt;

final class zzl implements zzez<Object> {
    private final /* synthetic */ zzdm zzla;

    zzl(zzb zzb, zzdm zzdm) {
        this.zzla = zzdm;
    }

    public final void zzbv(@Nullable String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzla.zza(null);
    }
}
