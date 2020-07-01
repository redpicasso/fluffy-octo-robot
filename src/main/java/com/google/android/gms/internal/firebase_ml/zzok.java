package com.google.android.gms.internal.firebase_ml;

public enum zzok {
    BASE,
    AUTOML,
    CUSTOM,
    TRANSLATE;

    public static zzok zzbx(String str) {
        return (zzok) Enum.valueOf(zzok.class, str);
    }
}
