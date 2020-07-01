package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzc;

public final class zzl extends zzhs<zzl, zza> implements zzje {
    private static final zzl zzr = new zzl();
    private static volatile zzjm<zzl> zzs;
    private int zzo;
    private String zzp;
    private String zzq;

    public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzl, zza> implements zzje {
        private zza() {
            super(zzl.zzr);
        }

        /* synthetic */ zza(zzn zzn) {
            this();
        }
    }

    private zzl() {
        String str = "";
        this.zzp = str;
        this.zzq = str;
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        switch (zzn.zzt[i - 1]) {
            case 1:
                return new zzl();
            case 2:
                return new zza();
            case 3:
                return zzhs.zza(zzr, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001", new Object[]{"zzo", "zzp", "zzq"});
            case 4:
                return zzr;
            case 5:
                Object obj3 = zzs;
                if (obj3 == null) {
                    synchronized (zzl.class) {
                        obj3 = zzs;
                        if (obj3 == null) {
                            obj3 = new zzc(zzr);
                            zzs = obj3;
                        }
                    }
                }
                return obj3;
            case 6:
                return Byte.valueOf((byte) 1);
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static {
        zzhs.zza(zzl.class, zzr);
    }
}
