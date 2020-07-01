package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;

public class zzet<T> extends zzem<T> {
    private final Object zztl;

    protected zzet(zzer zzer, String str, String str2, Object obj, Class<T> cls) {
        String str3 = null;
        if (obj != null) {
            zzgb zzgb = new zzgb(zzer.zzes(), obj);
            if (!((zzgg) zzer.zzem()).zzge().isEmpty()) {
                str3 = "data";
            }
            str3 = zzgb.zzal(str3);
        }
        super(zzer, str, str2, str3, cls);
        this.zztl = obj;
    }

    /* renamed from: zzet */
    public zzer zzen() {
        return (zzer) super.zzen();
    }

    /* renamed from: zzd */
    public zzet<T> zzb(String str, Object obj) {
        return (zzet) super.zzb(str, obj);
    }

    public /* synthetic */ zzem zzc(String str, Object obj) {
        return (zzet) zzb(str, obj);
    }

    protected final /* synthetic */ IOException zza(zzfk zzfk) {
        return zzek.zza(((zzer) zzen()).zzes(), zzfk);
    }
}
