package com.google.android.gms.internal.firebase_auth;

final class zzhd {
    private static final Class<?> zzxa = zzhm();

    private static Class<?> zzhm() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzhf zzhn() {
        if (zzxa != null) {
            try {
                return zzdk("getEmptyRegistry");
            } catch (Exception unused) {
                return zzhf.zzxf;
            }
        }
    }

    static zzhf zzho() {
        if (zzxa != null) {
            zzhf zzdk;
            try {
                zzdk = zzdk("loadGeneratedRegistry");
            } catch (Exception unused) {
                zzdk = null;
            }
            if (zzdk == null) {
                zzdk = zzhf.zzho();
            }
            return zzdk == null ? zzhn() : zzdk;
        }
    }

    private static final zzhf zzdk(String str) throws Exception {
        return (zzhf) zzxa.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }
}
