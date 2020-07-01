package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.internal.firebase_ml.zzue.zzf;

final class zzud implements zzvn {
    private static final zzud zzbog = new zzud();

    private zzud() {
    }

    public static zzud zzqw() {
        return zzbog;
    }

    public final boolean zzg(Class<?> cls) {
        return zzue.class.isAssignableFrom(cls);
    }

    public final zzvm zzh(Class<?> cls) {
        String valueOf;
        if (zzue.class.isAssignableFrom(cls)) {
            try {
                return (zzvm) zzue.zzi(cls.asSubclass(zzue.class)).zza(zzf.zzboq, null, null);
            } catch (Throwable e) {
                String str = "Unable to get message info for ";
                valueOf = String.valueOf(cls.getName());
                throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), e);
            }
        }
        String str2 = "Unsupported message type: ";
        valueOf = String.valueOf(cls.getName());
        throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
    }
}
