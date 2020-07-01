package com.google.android.gms.internal.firebase_ml;

import java.lang.reflect.Type;

public enum zztz {
    DOUBLE(0, zzub.SCALAR, zzuq.DOUBLE),
    FLOAT(1, zzub.SCALAR, zzuq.FLOAT),
    INT64(2, zzub.SCALAR, zzuq.LONG),
    UINT64(3, zzub.SCALAR, zzuq.LONG),
    INT32(4, zzub.SCALAR, zzuq.INT),
    FIXED64(5, zzub.SCALAR, zzuq.LONG),
    FIXED32(6, zzub.SCALAR, zzuq.INT),
    BOOL(7, zzub.SCALAR, zzuq.BOOLEAN),
    STRING(8, zzub.SCALAR, zzuq.STRING),
    MESSAGE(9, zzub.SCALAR, zzuq.MESSAGE),
    BYTES(10, zzub.SCALAR, zzuq.BYTE_STRING),
    UINT32(11, zzub.SCALAR, zzuq.INT),
    ENUM(12, zzub.SCALAR, zzuq.ENUM),
    SFIXED32(13, zzub.SCALAR, zzuq.INT),
    SFIXED64(14, zzub.SCALAR, zzuq.LONG),
    SINT32(15, zzub.SCALAR, zzuq.INT),
    SINT64(16, zzub.SCALAR, zzuq.LONG),
    GROUP(17, zzub.SCALAR, zzuq.MESSAGE),
    DOUBLE_LIST(18, zzub.VECTOR, zzuq.DOUBLE),
    FLOAT_LIST(19, zzub.VECTOR, zzuq.FLOAT),
    INT64_LIST(20, zzub.VECTOR, zzuq.LONG),
    UINT64_LIST(21, zzub.VECTOR, zzuq.LONG),
    INT32_LIST(22, zzub.VECTOR, zzuq.INT),
    FIXED64_LIST(23, zzub.VECTOR, zzuq.LONG),
    FIXED32_LIST(24, zzub.VECTOR, zzuq.INT),
    BOOL_LIST(25, zzub.VECTOR, zzuq.BOOLEAN),
    STRING_LIST(26, zzub.VECTOR, zzuq.STRING),
    MESSAGE_LIST(27, zzub.VECTOR, zzuq.MESSAGE),
    BYTES_LIST(28, zzub.VECTOR, zzuq.BYTE_STRING),
    UINT32_LIST(29, zzub.VECTOR, zzuq.INT),
    ENUM_LIST(30, zzub.VECTOR, zzuq.ENUM),
    SFIXED32_LIST(31, zzub.VECTOR, zzuq.INT),
    SFIXED64_LIST(32, zzub.VECTOR, zzuq.LONG),
    SINT32_LIST(33, zzub.VECTOR, zzuq.INT),
    SINT64_LIST(34, zzub.VECTOR, zzuq.LONG),
    DOUBLE_LIST_PACKED(35, zzub.PACKED_VECTOR, zzuq.DOUBLE),
    FLOAT_LIST_PACKED(36, zzub.PACKED_VECTOR, zzuq.FLOAT),
    INT64_LIST_PACKED(37, zzub.PACKED_VECTOR, zzuq.LONG),
    UINT64_LIST_PACKED(38, zzub.PACKED_VECTOR, zzuq.LONG),
    INT32_LIST_PACKED(39, zzub.PACKED_VECTOR, zzuq.INT),
    FIXED64_LIST_PACKED(40, zzub.PACKED_VECTOR, zzuq.LONG),
    FIXED32_LIST_PACKED(41, zzub.PACKED_VECTOR, zzuq.INT),
    BOOL_LIST_PACKED(42, zzub.PACKED_VECTOR, zzuq.BOOLEAN),
    UINT32_LIST_PACKED(43, zzub.PACKED_VECTOR, zzuq.INT),
    ENUM_LIST_PACKED(44, zzub.PACKED_VECTOR, zzuq.ENUM),
    SFIXED32_LIST_PACKED(45, zzub.PACKED_VECTOR, zzuq.INT),
    SFIXED64_LIST_PACKED(46, zzub.PACKED_VECTOR, zzuq.LONG),
    SINT32_LIST_PACKED(47, zzub.PACKED_VECTOR, zzuq.INT),
    SINT64_LIST_PACKED(48, zzub.PACKED_VECTOR, zzuq.LONG),
    GROUP_LIST(49, zzub.VECTOR, zzuq.MESSAGE),
    MAP(50, zzub.MAP, zzuq.VOID);
    
    private static final zztz[] zzbnt = null;
    private static final Type[] zzbnu = null;
    private final int id;
    private final zzuq zzbnp;
    private final zzub zzbnq;
    private final Class<?> zzbnr;
    private final boolean zzbns;

    private zztz(int i, zzub zzub, zzuq zzuq) {
        this.id = i;
        this.zzbnq = zzub;
        this.zzbnp = zzuq;
        int i2 = zzua.zzbnw[zzub.ordinal()];
        if (i2 == 1) {
            this.zzbnr = zzuq.zzrs();
        } else if (i2 != 2) {
            this.zzbnr = null;
        } else {
            this.zzbnr = zzuq.zzrs();
        }
        boolean z = false;
        if (zzub == zzub.SCALAR) {
            int i3 = zzua.zzbnx[zzuq.ordinal()];
            if (!(i3 == 1 || i3 == 2 || i3 == 3)) {
                z = true;
            }
        }
        this.zzbns = z;
    }

    public final int id() {
        return this.id;
    }

    static {
        zzbnu = new Type[0];
        zztz[] values = values();
        zzbnt = new zztz[values.length];
        int length = values.length;
        int i;
        while (i < length) {
            zztz zztz = values[i];
            zzbnt[zztz.id] = zztz;
            i++;
        }
    }
}
