package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzfy.zzb;
import com.google.android.gms.internal.vision.zzfy.zzg;

public final class zzbo extends zzfy<zzbo, zza> implements zzhh {
    private static volatile zzhq<zzbo> zzbf;
    private static final zzbo zzhs = new zzbo();
    private int zzbg;
    private int zzhm;
    private int zzhn;
    private int zzho;
    private int zzhp;
    private boolean zzhq;
    private long zzhr;

    public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zzbo, zza> implements zzhh {
        private zza() {
            super(zzbo.zzhs);
        }

        public final zza zzi(int i) {
            zzfc();
            ((zzbo) this.zzwn).setWidth(i);
            return this;
        }

        public final zza zzj(int i) {
            zzfc();
            ((zzbo) this.zzwn).setHeight(i);
            return this;
        }

        public final zza zzb(zzbl zzbl) {
            zzfc();
            ((zzbo) this.zzwn).zza(zzbl);
            return this;
        }

        public final zza zzb(zzbq zzbq) {
            zzfc();
            ((zzbo) this.zzwn).zza(zzbq);
            return this;
        }

        public final zza zzc(long j) {
            zzfc();
            ((zzbo) this.zzwn).zzb(j);
            return this;
        }

        /* synthetic */ zza(zzbp zzbp) {
            this();
        }
    }

    private zzbo() {
    }

    private final void setWidth(int i) {
        this.zzbg |= 1;
        this.zzhm = i;
    }

    private final void setHeight(int i) {
        this.zzbg |= 2;
        this.zzhn = i;
    }

    private final void zza(zzbl zzbl) {
        if (zzbl != null) {
            this.zzbg |= 4;
            this.zzho = zzbl.zzr();
            return;
        }
        throw new NullPointerException();
    }

    private final void zza(zzbq zzbq) {
        if (zzbq != null) {
            this.zzbg |= 8;
            this.zzhp = zzbq.zzr();
            return;
        }
        throw new NullPointerException();
    }

    private final void zzb(long j) {
        this.zzbg |= 32;
        this.zzhr = j;
    }

    public static zza zzai() {
        return (zza) ((com.google.android.gms.internal.vision.zzfy.zza) zzhs.zza(zzg.zzxb, null, null));
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        switch (zzbp.zzbc[i - 1]) {
            case 1:
                return new zzbo();
            case 2:
                return new zza();
            case 3:
                return zzfy.zza(zzhs, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\f\u0002\u0004\f\u0003\u0005\u0007\u0004\u0006\u0002\u0005", new Object[]{"zzbg", "zzhm", "zzhn", "zzho", zzbl.zzah(), "zzhp", zzbq.zzah(), "zzhq", "zzhr"});
            case 4:
                return zzhs;
            case 5:
                Object obj3 = zzbf;
                if (obj3 == null) {
                    synchronized (zzbo.class) {
                        obj3 = zzbf;
                        if (obj3 == null) {
                            obj3 = new zzb(zzhs);
                            zzbf = obj3;
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
        zzfy.zza(zzbo.class, zzhs);
    }
}
