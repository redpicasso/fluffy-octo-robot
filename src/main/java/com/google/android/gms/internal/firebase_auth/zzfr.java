package com.google.android.gms.internal.firebase_auth;

import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzhs.zza;
import com.google.android.gms.internal.firebase_auth.zzp.zzr;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzfr implements zzfd<zzr> {
    @Nullable
    private String zzhy;
    private String zzji;

    public zzfr(String str, @Nullable String str2) {
        this.zzji = Preconditions.checkNotEmpty(str);
        this.zzhy = str2;
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzl = zzr.zzbe().zzbk(this.zzji).zzl(true);
        String str = this.zzhy;
        if (str != null) {
            zzl.zzbl(str);
        }
        return (zzr) ((zzhs) zzl.zzih());
    }
}
