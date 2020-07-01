package com.google.android.gms.internal.measurement;

final class zzej {
    private static final Class<?> zzaeq = zztl();

    private static Class<?> zztl() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzel zztm() {
        if (zzaeq != null) {
            try {
                return zzdu("getEmptyRegistry");
            } catch (Exception unused) {
                return zzel.zzaev;
            }
        }
    }

    static zzel zztn() {
        if (zzaeq != null) {
            zzel zzdu;
            try {
                zzdu = zzdu("loadGeneratedRegistry");
            } catch (Exception unused) {
                zzdu = null;
            }
            if (zzdu == null) {
                zzdu = zzel.zztn();
            }
            return zzdu == null ? zztm() : zzdu;
        }
    }

    private static final zzel zzdu(String str) throws Exception {
        return (zzel) zzaeq.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }
}
