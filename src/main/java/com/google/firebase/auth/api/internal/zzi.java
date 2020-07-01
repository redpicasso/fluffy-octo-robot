package com.google.firebase.auth.api.internal;

import androidx.annotation.Nullable;
import com.google.android.gms.internal.firebase_auth.zzec;
import com.google.firebase.auth.internal.zzt;

final class zzi implements zzez<zzec> {
    private final /* synthetic */ zzdm zzla;

    zzi(zzb zzb, zzdm zzdm) {
        this.zzla = zzdm;
    }

    public final void zzbv(@Nullable String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzla.zza((zzec) obj);
    }
}
