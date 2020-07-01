package com.google.android.gms.internal.firebase_ml;

final class zzvf implements zzvn {
    private zzvn[] zzbql;

    zzvf(zzvn... zzvnArr) {
        this.zzbql = zzvnArr;
    }

    public final boolean zzg(Class<?> cls) {
        for (zzvn zzg : this.zzbql) {
            if (zzg.zzg(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzvm zzh(Class<?> cls) {
        for (zzvn zzvn : this.zzbql) {
            if (zzvn.zzg(cls)) {
                return zzvn.zzh(cls);
            }
        }
        String str = "No factory is available for message type: ";
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }
}
