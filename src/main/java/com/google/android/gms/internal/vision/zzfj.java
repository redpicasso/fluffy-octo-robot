package com.google.android.gms.internal.vision;

final class zzfj {
    private static final Class<?> zzte = zzee();

    private static Class<?> zzee() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzfk zzef() {
        if (zzte != null) {
            try {
                return zzp("newInstance");
            } catch (Exception unused) {
                return new zzfk();
            }
        }
    }

    public static zzfk zzeg() {
        if (zzte != null) {
            try {
                return zzp("getEmptyRegistry");
            } catch (Exception unused) {
                return zzfk.zzti;
            }
        }
    }

    static zzfk zzeh() {
        if (zzte != null) {
            zzfk zzp;
            try {
                zzp = zzp("loadGeneratedRegistry");
            } catch (Exception unused) {
                zzp = null;
            }
            if (zzp == null) {
                zzp = zzfk.zzeh();
            }
            return zzp == null ? zzeg() : zzp;
        }
    }

    private static final zzfk zzp(String str) throws Exception {
        return (zzfk) zzte.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }
}
