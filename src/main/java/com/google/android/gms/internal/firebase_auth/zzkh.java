package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzc;

public final class zzkh extends zzhs<zzkh, zza> implements zzje {
    private static final zzkh zzaej = new zzkh();
    private static volatile zzjm<zzkh> zzs;
    private long zzaeh;
    private int zzaei;

    public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzkh, zza> implements zzje {
        private zza() {
            super(zzkh.zzaej);
        }

        /* synthetic */ zza(zzkj zzkj) {
            this();
        }
    }

    private zzkh() {
    }

    public final long getSeconds() {
        return this.zzaeh;
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        switch (zzkj.zzt[i - 1]) {
            case 1:
                return new zzkh();
            case 2:
                return new zza();
            case 3:
                return zzhs.zza(zzaej, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0002\u0004", new Object[]{"zzaeh", "zzaei"});
            case 4:
                return zzaej;
            case 5:
                Object obj3 = zzs;
                if (obj3 == null) {
                    synchronized (zzkh.class) {
                        obj3 = zzs;
                        if (obj3 == null) {
                            obj3 = new zzc(zzaej);
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

    public static zzkh zzkl() {
        return zzaej;
    }

    static {
        zzhs.zza(zzkh.class, zzaej);
    }
}
