package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzd;

final class zzhq implements zziz {
    private static final zzhq zzaae = new zzhq();

    private zzhq() {
    }

    public static zzhq zzib() {
        return zzaae;
    }

    public final boolean zza(Class<?> cls) {
        return zzhs.class.isAssignableFrom(cls);
    }

    public final zzja zzb(Class<?> cls) {
        String valueOf;
        if (zzhs.class.isAssignableFrom(cls)) {
            try {
                return (zzja) zzhs.zzd(cls.asSubclass(zzhs.class)).zza(zzd.zzaap, null, null);
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
