package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzfy.zzb;

public final class zzkf extends zzfy<zzkf, zza> implements zzhh {
    private static final zzkf zzahj = new zzkf();
    private static volatile zzhq<zzkf> zzbf;
    private zzge<zzjx> zzahi = zzfy.zzey();
    private byte zzkc = (byte) 2;

    public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zzkf, zza> implements zzhh {
        private zza() {
            super(zzkf.zzahj);
        }

        /* synthetic */ zza(zzkg zzkg) {
            this();
        }
    }

    private zzkf() {
    }

    public final int zzip() {
        return this.zzahi.size();
    }

    public final zzjx zzcc(int i) {
        return (zzjx) this.zzahi.get(i);
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        int i2 = 0;
        switch (zzkg.zzbc[i - 1]) {
            case 1:
                return new zzkf();
            case 2:
                return new zza();
            case 3:
                return zzfy.zza(zzahj, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001Л", new Object[]{"zzahi", zzjx.class});
            case 4:
                return zzahj;
            case 5:
                Object obj3 = zzbf;
                if (obj3 == null) {
                    synchronized (zzkf.class) {
                        obj3 = zzbf;
                        if (obj3 == null) {
                            obj3 = new zzb(zzahj);
                            zzbf = obj3;
                        }
                    }
                }
                return obj3;
            case 6:
                return Byte.valueOf(this.zzkc);
            case 7:
                if (obj != null) {
                    i2 = 1;
                }
                this.zzkc = (byte) i2;
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public static zzkf zziq() {
        return zzahj;
    }

    static {
        zzfy.zza(zzkf.class, zzahj);
    }
}
