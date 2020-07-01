package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzc;

public final class zzr extends zzhs<zzr, zza> implements zzje {
    private static final zzr zzfg = new zzr();
    private static volatile zzjm<zzr> zzs;
    private String zzcj;
    private int zzfa = 0;
    private Object zzfb;
    private int zzfc = 0;
    private Object zzfd;
    private String zzfe;
    private zzkh zzff;
    private int zzo;

    public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzr, zza> implements zzje {
        private zza() {
            super(zzr.zzfg);
        }

        /* synthetic */ zza(zzs zzs) {
            this();
        }
    }

    private zzr() {
        String str = "";
        this.zzfe = str;
        this.zzcj = str;
    }

    public final String zzbk() {
        return this.zzfa == 1 ? (String) this.zzfb : "";
    }

    public final String zzbl() {
        return this.zzfe;
    }

    public final String getDisplayName() {
        return this.zzcj;
    }

    public final zzkh zzbm() {
        zzkh zzkh = this.zzff;
        return zzkh == null ? zzkh.zzkl() : zzkh;
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        switch (zzs.zzt[i - 1]) {
            case 1:
                return new zzr();
            case 2:
                return new zza();
            case 3:
                return zzhs.zza(zzfg, "\u0001\u0005\u0002\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001;\u0000\u0002\b\u0001\u0003\b\u0002\u0004\t\u0003\u0005;\u0001", new Object[]{"zzfb", "zzfa", "zzfd", "zzfc", "zzo", "zzfe", "zzcj", "zzff"});
            case 4:
                return zzfg;
            case 5:
                Object obj3 = zzs;
                if (obj3 == null) {
                    synchronized (zzr.class) {
                        obj3 = zzs;
                        if (obj3 == null) {
                            obj3 = new zzc(zzfg);
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
        zzhs.zza(zzr.class, zzfg);
    }
}
