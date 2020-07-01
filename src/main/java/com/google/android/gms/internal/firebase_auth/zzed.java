package com.google.android.gms.internal.firebase_auth;

import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp.zza;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzed implements zzfd<zza> {
    @Nullable
    private final String zzhy;
    private String zzrd;
    private String zzre = "http://localhost";

    public zzed(String str, @Nullable String str2) {
        this.zzrd = Preconditions.checkNotEmpty(str);
        this.zzhy = str2;
    }

    public final /* synthetic */ zzjc zzeq() {
        zzhs.zza zzb = zza.zzd().zza(this.zzrd).zzb(this.zzre);
        String str = this.zzhy;
        if (str != null) {
            zzb.zzc(str);
        }
        return (zza) ((zzhs) zzb.zzih());
    }
}
