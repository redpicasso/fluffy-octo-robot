package com.google.android.gms.internal.vision;

import java.util.List;

public final class zzca {
    public static final com.google.android.gms.internal.vision.zzfy.zzf<zzjx, List<zzb>> zziv = zzfy.zza(zzjx.zzid(), zzb.zzbb(), null, 202056002, zzjd.zzace, false, zzb.class);

    public static final class zza extends zzfy<zza, zza> implements zzhh {
        private static volatile zzhq<zza> zzbf;
        private static final zza zziy = new zza();
        private int zzbg;
        private zzg zziw;
        private zzg zzix;

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zza, zza> implements zzhh {
            private zza() {
                super(zza.zziy);
            }

            public final zza zzc(zzg zzg) {
                zzfc();
                ((zza) this.zzwn).zza(zzg);
                return this;
            }

            public final zza zzd(zzg zzg) {
                zzfc();
                ((zza) this.zzwn).zzb(zzg);
                return this;
            }

            /* synthetic */ zza(zzcb zzcb) {
                this();
            }
        }

        private zza() {
        }

        private final void zza(zzg zzg) {
            if (zzg != null) {
                this.zziw = zzg;
                this.zzbg |= 1;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzb(zzg zzg) {
            if (zzg != null) {
                this.zzix = zzg;
                this.zzbg |= 2;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzaw() {
            return (zza) ((com.google.android.gms.internal.vision.zzfy.zza) zziy.zza(com.google.android.gms.internal.vision.zzfy.zzg.zzxb, null, null));
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzcb.zzbc[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zziy, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001", new Object[]{"zzbg", "zziw", "zzix"});
                case 4:
                    return zziy;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zza.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zziy);
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
            zzfy.zza(zza.class, zziy);
        }
    }

    public static final class zzb extends zzfy<zzb, zza> implements zzhh {
        private static volatile zzhq<zzb> zzbf;
        private static final zzb zzjf = new zzb();
        private int zzbg;
        private int zzjd;
        private zzge<zzb> zzje = zzfy.zzey();

        public enum zzc implements zzgb {
            CONTOUR_UNKNOWN(0),
            FACE_OVAL(1),
            LEFT_EYEBROW_TOP(2),
            LEFT_EYEBROW_BOTTOM(3),
            RIGHT_EYEBROW_TOP(4),
            RIGHT_EYEBROW_BOTTOM(5),
            LEFT_EYE(6),
            RIGHT_EYE(7),
            UPPER_LIP_TOP(8),
            UPPER_LIP_BOTTOM(9),
            LOWER_LIP_TOP(10),
            LOWER_LIP_BOTTOM(11),
            NOSE_BRIDGE(12),
            NOSE_BOTTOM(13),
            LEFT_CHEEK_CENTER(14),
            RIGHT_CHEEK_CENTER(15);
            
            private static final zzgc<zzc> zzdv = null;
            private final int value;

            public final int zzr() {
                return this.value;
            }

            public static zzc zzp(int i) {
                switch (i) {
                    case 0:
                        return CONTOUR_UNKNOWN;
                    case 1:
                        return FACE_OVAL;
                    case 2:
                        return LEFT_EYEBROW_TOP;
                    case 3:
                        return LEFT_EYEBROW_BOTTOM;
                    case 4:
                        return RIGHT_EYEBROW_TOP;
                    case 5:
                        return RIGHT_EYEBROW_BOTTOM;
                    case 6:
                        return LEFT_EYE;
                    case 7:
                        return RIGHT_EYE;
                    case 8:
                        return UPPER_LIP_TOP;
                    case 9:
                        return UPPER_LIP_BOTTOM;
                    case 10:
                        return LOWER_LIP_TOP;
                    case 11:
                        return LOWER_LIP_BOTTOM;
                    case 12:
                        return NOSE_BRIDGE;
                    case 13:
                        return NOSE_BOTTOM;
                    case 14:
                        return LEFT_CHEEK_CENTER;
                    case 15:
                        return RIGHT_CHEEK_CENTER;
                    default:
                        return null;
                }
            }

            public static zzgd zzah() {
                return zzcg.zzhl;
            }

            private zzc(int i) {
                this.value = i;
            }

            static {
                zzdv = new zzcf();
            }
        }

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zzb, zza> implements zzhh {
            private zza() {
                super(zzb.zzjf);
            }

            /* synthetic */ zza(zzcb zzcb) {
                this();
            }
        }

        public static final class zzb extends zzfy<zzb, zza> implements zzhh {
            private static volatile zzhq<zzb> zzbf;
            private static final zzb zzjj = new zzb();
            private int zzbg;
            private float zzjg;
            private float zzjh;
            private float zzji;

            public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zzb, zza> implements zzhh {
                private zza() {
                    super(zzb.zzjj);
                }

                /* synthetic */ zza(zzcb zzcb) {
                    this();
                }
            }

            private zzb() {
            }

            public final float getX() {
                return this.zzjg;
            }

            public final float getY() {
                return this.zzjh;
            }

            protected final Object zza(int i, Object obj, Object obj2) {
                switch (zzcb.zzbc[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza();
                    case 3:
                        return zzfy.zza(zzjj, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0001\u0000\u0002\u0001\u0001\u0003\u0001\u0002", new Object[]{"zzbg", "zzjg", "zzjh", "zzji"});
                    case 4:
                        return zzjj;
                    case 5:
                        Object obj3 = zzbf;
                        if (obj3 == null) {
                            synchronized (zzb.class) {
                                obj3 = zzbf;
                                if (obj3 == null) {
                                    obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzjj);
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
                zzfy.zza(zzb.class, zzjj);
            }
        }

        private zzb() {
        }

        public final zzc zzay() {
            zzc zzp = zzc.zzp(this.zzjd);
            return zzp == null ? zzc.CONTOUR_UNKNOWN : zzp;
        }

        public final List<zzb> zzaz() {
            return this.zzje;
        }

        public final int zzba() {
            return this.zzje.size();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzcb.zzbc[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zzjf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\f\u0000\u0002\u001b", new Object[]{"zzbg", "zzjd", zzc.zzah(), "zzje", zzb.class});
                case 4:
                    return zzjf;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zzb.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzjf);
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

        public static zzb zzbb() {
            return zzjf;
        }

        static {
            zzfy.zza(zzb.class, zzjf);
        }
    }

    public static final class zzc extends zzfy<zzc, zza> implements zzhh {
        private static volatile zzhq<zzc> zzbf;
        private static final zzc zzkd = new zzc();
        private int zzbg;
        private zzkf zzkb;
        private byte zzkc = (byte) 2;

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zzc, zza> implements zzhh {
            private zza() {
                super(zzc.zzkd);
            }

            /* synthetic */ zza(zzcb zzcb) {
                this();
            }
        }

        private zzc() {
        }

        public final zzkf zzbe() {
            zzkf zzkf = this.zzkb;
            return zzkf == null ? zzkf.zziq() : zzkf;
        }

        public static zzc zza(byte[] bArr, zzfk zzfk) throws zzgf {
            return (zzc) zzfy.zzb(zzkd, bArr, zzfk);
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzcb.zzbc[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zzkd, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0001\u0001Љ\u0000", new Object[]{"zzbg", "zzkb"});
                case 4:
                    return zzkd;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zzc.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzkd);
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

        static {
            zzfy.zza(zzc.class, zzkd);
        }
    }

    public static final class zzd extends zzfy<zzd, zza> implements zzhh {
        private static volatile zzhq<zzd> zzbf;
        private static final zzd zzkq = new zzd();
        private int zzbg;
        private float zzke = 0.1f;
        private int zzkf = 1;
        private int zzkg = 1;
        private int zzkh = 1;
        private boolean zzki;
        private boolean zzkj;
        private float zzkk = 45.0f;
        private float zzkl = 0.5f;
        private boolean zzkm;
        private zze zzkn;
        private zza zzko;
        private zzf zzkp;

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zzd, zza> implements zzhh {
            private zza() {
                super(zzd.zzkq);
            }

            public final zza zze(float f) {
                zzfc();
                ((zzd) this.zzwn).zzd(f);
                return this;
            }

            public final zza zzb(zzch zzch) {
                zzfc();
                ((zzd) this.zzwn).zza(zzch);
                return this;
            }

            public final zza zzb(zzcc zzcc) {
                zzfc();
                ((zzd) this.zzwn).zza(zzcc);
                return this;
            }

            public final zza zzb(zzck zzck) {
                zzfc();
                ((zzd) this.zzwn).zza(zzck);
                return this;
            }

            public final zza zzd(boolean z) {
                zzfc();
                ((zzd) this.zzwn).zza(z);
                return this;
            }

            public final zza zze(boolean z) {
                zzfc();
                ((zzd) this.zzwn).zzb(z);
                return this;
            }

            public final zza zzf(boolean z) {
                zzfc();
                ((zzd) this.zzwn).zzc(true);
                return this;
            }

            public final zza zzb(zze zze) {
                zzfc();
                ((zzd) this.zzwn).zza(zze);
                return this;
            }

            public final zza zzb(zza zza) {
                zzfc();
                ((zzd) this.zzwn).zza(zza);
                return this;
            }

            public final zza zzb(zzf zzf) {
                zzfc();
                ((zzd) this.zzwn).zza(zzf);
                return this;
            }

            /* synthetic */ zza(zzcb zzcb) {
                this();
            }
        }

        private zzd() {
        }

        private final void zzd(float f) {
            this.zzbg |= 1;
            this.zzke = f;
        }

        public final zzch zzbg() {
            zzch zzq = zzch.zzq(this.zzkf);
            return zzq == null ? zzch.NO_LANDMARK : zzq;
        }

        private final void zza(zzch zzch) {
            if (zzch != null) {
                this.zzbg |= 2;
                this.zzkf = zzch.zzr();
                return;
            }
            throw new NullPointerException();
        }

        public final zzcc zzbh() {
            zzcc zzo = zzcc.zzo(this.zzkg);
            return zzo == null ? zzcc.NO_CLASSIFICATION : zzo;
        }

        private final void zza(zzcc zzcc) {
            if (zzcc != null) {
                this.zzbg |= 4;
                this.zzkg = zzcc.zzr();
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zzck zzck) {
            if (zzck != null) {
                this.zzbg |= 8;
                this.zzkh = zzck.zzr();
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(boolean z) {
            this.zzbg |= 16;
            this.zzki = z;
        }

        private final void zzb(boolean z) {
            this.zzbg |= 32;
            this.zzkj = z;
        }

        private final void zzc(boolean z) {
            this.zzbg |= 256;
            this.zzkm = z;
        }

        private final void zza(zze zze) {
            if (zze != null) {
                this.zzkn = zze;
                this.zzbg |= 512;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zza zza) {
            if (zza != null) {
                this.zzko = zza;
                this.zzbg |= 1024;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zzf zzf) {
            if (zzf != null) {
                this.zzkp = zzf;
                this.zzbg |= 2048;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzbi() {
            return (zza) ((com.google.android.gms.internal.vision.zzfy.zza) zzkq.zza(com.google.android.gms.internal.vision.zzfy.zzg.zzxb, null, null));
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzcb.zzbc[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zzkq, "\u0001\f\u0000\u0001\u0001\f\f\u0000\u0000\u0000\u0001\u0001\u0000\u0002\f\u0001\u0003\f\u0002\u0004\f\u0003\u0005\u0007\u0004\u0006\u0007\u0005\u0007\u0001\u0006\b\u0001\u0007\t\u0007\b\n\t\t\u000b\t\n\f\t\u000b", new Object[]{"zzbg", "zzke", "zzkf", zzch.zzah(), "zzkg", zzcc.zzah(), "zzkh", zzck.zzah(), "zzki", "zzkj", "zzkk", "zzkl", "zzkm", "zzkn", "zzko", "zzkp"});
                case 4:
                    return zzkq;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zzd.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzkq);
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
            zzfy.zza(zzd.class, zzkq);
        }
    }

    public static final class zze extends zzfy<zze, zza> implements zzhh {
        private static volatile zzhq<zze> zzbf;
        private static final zze zzku = new zze();
        private int zzbg;
        private zzg zzkr;
        private zzg zzks;
        private zzg zzkt;

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zze, zza> implements zzhh {
            private zza() {
                super(zze.zzku);
            }

            public final zza zzh(zzg zzg) {
                zzfc();
                ((zze) this.zzwn).zze(zzg);
                return this;
            }

            public final zza zzi(zzg zzg) {
                zzfc();
                ((zze) this.zzwn).zzf(zzg);
                return this;
            }

            public final zza zzj(zzg zzg) {
                zzfc();
                ((zze) this.zzwn).zzg(zzg);
                return this;
            }

            /* synthetic */ zza(zzcb zzcb) {
                this();
            }
        }

        private zze() {
        }

        private final void zze(zzg zzg) {
            if (zzg != null) {
                this.zzkr = zzg;
                this.zzbg |= 1;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzf(zzg zzg) {
            if (zzg != null) {
                this.zzks = zzg;
                this.zzbg |= 2;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzg(zzg zzg) {
            if (zzg != null) {
                this.zzkt = zzg;
                this.zzbg |= 4;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzbk() {
            return (zza) ((com.google.android.gms.internal.vision.zzfy.zza) zzku.zza(com.google.android.gms.internal.vision.zzfy.zzg.zzxb, null, null));
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzcb.zzbc[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zzku, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzkr", "zzks", "zzkt"});
                case 4:
                    return zzku;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zze.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzku);
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
            zzfy.zza(zze.class, zzku);
        }
    }

    public static final class zzf extends zzfy<zzf, zza> implements zzhh {
        private static volatile zzhq<zzf> zzbf;
        private static final zzf zzle = new zzf();
        private int zzbg;
        private zzg zzla;
        private zzg zzlb;
        private zzg zzlc;
        private zzg zzld;

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zzf, zza> implements zzhh {
            private zza() {
                super(zzf.zzle);
            }

            public final zza zzo(zzg zzg) {
                zzfc();
                ((zzf) this.zzwn).zzk(zzg);
                return this;
            }

            public final zza zzp(zzg zzg) {
                zzfc();
                ((zzf) this.zzwn).zzl(zzg);
                return this;
            }

            public final zza zzq(zzg zzg) {
                zzfc();
                ((zzf) this.zzwn).zzm(zzg);
                return this;
            }

            public final zza zzr(zzg zzg) {
                zzfc();
                ((zzf) this.zzwn).zzn(zzg);
                return this;
            }

            /* synthetic */ zza(zzcb zzcb) {
                this();
            }
        }

        private zzf() {
        }

        private final void zzk(zzg zzg) {
            if (zzg != null) {
                this.zzla = zzg;
                this.zzbg |= 1;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzl(zzg zzg) {
            if (zzg != null) {
                this.zzlb = zzg;
                this.zzbg |= 2;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzm(zzg zzg) {
            if (zzg != null) {
                this.zzlc = zzg;
                this.zzbg |= 4;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzn(zzg zzg) {
            if (zzg != null) {
                this.zzld = zzg;
                this.zzbg |= 8;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzbm() {
            return (zza) ((com.google.android.gms.internal.vision.zzfy.zza) zzle.zza(com.google.android.gms.internal.vision.zzfy.zzg.zzxb, null, null));
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzcb.zzbc[i - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zzle, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002\u0004\t\u0003", new Object[]{"zzbg", "zzla", "zzlb", "zzlc", "zzld"});
                case 4:
                    return zzle;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zzf.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzle);
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
            zzfy.zza(zzf.class, zzle);
        }
    }

    public static final class zzg extends zzfy<zzg, zza> implements zzhh {
        private static volatile zzhq<zzg> zzbf;
        private static final zzg zzlm = new zzg();
        private int zzbg;
        private zzeo zzlk = zzeo.zzrx;
        private String zzll = "";

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zzg, zza> implements zzhh {
            private zza() {
                super(zzg.zzlm);
            }

            public final zza zzj(String str) {
                zzfc();
                ((zzg) this.zzwn).zzi(str);
                return this;
            }

            /* synthetic */ zza(zzcb zzcb) {
                this();
            }
        }

        private zzg() {
        }

        private final void zzi(String str) {
            if (str != null) {
                this.zzbg |= 2;
                this.zzll = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzbo() {
            return (zza) ((com.google.android.gms.internal.vision.zzfy.zza) zzlm.zza(com.google.android.gms.internal.vision.zzfy.zzg.zzxb, null, null));
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzcb.zzbc[i - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zzlm, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\n\u0000\u0002\b\u0001", new Object[]{"zzbg", "zzlk", "zzll"});
                case 4:
                    return zzlm;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zzg.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzlm);
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
            zzfy.zza(zzg.class, zzlm);
        }
    }
}
