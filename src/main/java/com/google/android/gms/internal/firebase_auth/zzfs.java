package com.google.android.gms.internal.firebase_auth;

import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzhs.zza;
import com.google.android.gms.internal.firebase_auth.zzp.zzt;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzfs implements zzfd<zzt> {
    @Nullable
    private final String zzhy;
    private String zzif;
    private String zzig;
    private boolean zzsj = true;

    public zzfs(String str, String str2, @Nullable String str3) {
        this.zzif = Preconditions.checkNotEmpty(str);
        this.zzig = Preconditions.checkNotEmpty(str2);
        this.zzhy = str3;
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzm = zzt.zzbh().zzbm(this.zzif).zzbn(this.zzig).zzm(this.zzsj);
        String str = this.zzhy;
        if (str != null) {
            zzm.zzbo(str);
        }
        return (zzt) ((zzhs) zzm.zzih());
    }
}
