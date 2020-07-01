package com.google.android.gms.internal.firebase_ml;

final class zzsr {
    private static final Class<?> zzbke = zzcm("libcore.io.Memory");
    private static final boolean zzbkf = (zzcm("org.robolectric.Robolectric") != null);

    static boolean zzpv() {
        return (zzbke == null || zzbkf) ? false : true;
    }

    static Class<?> zzpw() {
        return zzbke;
    }

    private static <T> Class<T> zzcm(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }
}
