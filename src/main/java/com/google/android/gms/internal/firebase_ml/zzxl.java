package com.google.android.gms.internal.firebase_ml;

public enum zzxl {
    DOUBLE(zzxq.DOUBLE, 1),
    FLOAT(zzxq.FLOAT, 5),
    INT64(zzxq.LONG, 0),
    UINT64(zzxq.LONG, 0),
    INT32(zzxq.INT, 0),
    FIXED64(zzxq.LONG, 1),
    FIXED32(zzxq.INT, 5),
    BOOL(zzxq.BOOLEAN, 0),
    STRING(zzxq.STRING, 2),
    GROUP(zzxq.MESSAGE, 3),
    MESSAGE(zzxq.MESSAGE, 2),
    BYTES(zzxq.BYTE_STRING, 2),
    UINT32(zzxq.INT, 0),
    ENUM(zzxq.ENUM, 0),
    SFIXED32(zzxq.INT, 5),
    SFIXED64(zzxq.LONG, 1),
    SINT32(zzxq.INT, 0),
    SINT64(zzxq.LONG, 0);
    
    private final zzxq zzbug;
    private final int zzbuh;

    private zzxl(zzxq zzxq, int i) {
        this.zzbug = zzxq;
        this.zzbuh = i;
    }

    public final zzxq zztp() {
        return this.zzbug;
    }

    public final int zztq() {
        return this.zzbuh;
    }
}
