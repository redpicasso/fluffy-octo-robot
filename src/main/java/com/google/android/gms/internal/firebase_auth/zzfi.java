package com.google.android.gms.internal.firebase_auth;

import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzhs.zza;
import com.google.android.gms.internal.firebase_auth.zzp.zzn;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzfi implements zzfd<zzn> {
    @Nullable
    private String zzhy;
    private String zzif;
    private String zzig;
    @Nullable
    private String zzjv;

    public zzfi(@Nullable String str) {
        this.zzhy = str;
    }

    public zzfi(String str, String str2, @Nullable String str3, @Nullable String str4) {
        this.zzif = Preconditions.checkNotEmpty(str);
        this.zzig = Preconditions.checkNotEmpty(str2);
        this.zzjv = null;
        this.zzhy = str4;
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzaq = zzn.zzaq();
        String str = this.zzif;
        if (str != null) {
            zzaq.zzaw(str);
        }
        str = this.zzig;
        if (str != null) {
            zzaq.zzax(str);
        }
        str = this.zzhy;
        if (str != null) {
            zzaq.zzay(str);
        }
        return (zzn) ((zzhs) zzaq.zzih());
    }
}
