package com.google.android.gms.internal.firebase_ml;

public enum zzuq {
    VOID(Void.class, Void.class, null),
    INT(Integer.TYPE, Integer.class, Integer.valueOf(0)),
    LONG(Long.TYPE, Long.class, Long.valueOf(0)),
    FLOAT(Float.TYPE, Float.class, Float.valueOf(0.0f)),
    DOUBLE(Double.TYPE, Double.class, Double.valueOf(0.0d)),
    BOOLEAN(Boolean.TYPE, Boolean.class, Boolean.valueOf(false)),
    STRING(String.class, String.class, ""),
    BYTE_STRING(zzsw.class, zzsw.class, zzsw.zzbkl),
    ENUM(Integer.TYPE, Integer.class, null),
    MESSAGE(Object.class, Object.class, null);
    
    private final Class<?> zzbps;
    private final Class<?> zzbpt;
    private final Object zzbpu;

    private zzuq(Class<?> cls, Class<?> cls2, Object obj) {
        this.zzbps = cls;
        this.zzbpt = cls2;
        this.zzbpu = obj;
    }

    public final Class<?> zzrs() {
        return this.zzbpt;
    }
}
