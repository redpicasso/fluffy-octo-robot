package com.google.android.gms.internal.firebase_ml;

public final class zzmd {

    public static final class zza extends zzue<zza, zza> implements zzvq {
        private static final zza zzadz = new zza();
        private static volatile zzvz<zza> zzbm;
        private zzs zzadw;
        private zzb zzadx;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zza, zza> implements zzvq {
            private zza() {
                super(zza.zzadz);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zza() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzadz, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzadx", "zzady"});
                case 4:
                    return zzadz;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zza.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzadz);
                                zzbm = obj3;
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
            zzue.zza(zza.class, zzadz);
        }
    }

    public static final class zzaa extends zzue<zzaa, zza> implements zzvq {
        private static final zzaa zzaoh = new zzaa();
        private static volatile zzvz<zzaa> zzbm;
        private zzs zzadw;
        private zzun<zzc> zzaod = zzue.zzrb();
        private int zzaoe;
        private int zzaof;
        private int zzaog;
        private int zzbg;

        public enum zzb implements zzuh {
            NO_ERROR(0),
            STATUS_SENSITIVE_TOPIC(1),
            STATUS_QUALITY_THRESHOLDED(2),
            STATUS_INTERNAL_ERROR(3),
            STATUS_NOT_SUPPORTED_LANGUAGE(101);
            
            private static final zzui<zzb> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzb zzbi(int i) {
                if (i == 0) {
                    return NO_ERROR;
                }
                if (i == 1) {
                    return STATUS_SENSITIVE_TOPIC;
                }
                if (i == 2) {
                    return STATUS_QUALITY_THRESHOLDED;
                }
                if (i != 3) {
                    return i != 101 ? null : STATUS_NOT_SUPPORTED_LANGUAGE;
                } else {
                    return STATUS_INTERNAL_ERROR;
                }
            }

            public static zzuj zzq() {
                return zznj.zzbs;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzni();
            }
        }

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzaa, zza> implements zzvq {
            private zza() {
                super(zzaa.zzaoh);
            }

            public final zza zzg(zza zza) {
                zzrf();
                ((zzaa) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zzb zzb) {
                zzrf();
                ((zzaa) this.zzbol).zza(zzb);
                return this;
            }

            public final zza zzbg(int i) {
                zzrf();
                ((zzaa) this.zzbol).zzbe(i);
                return this;
            }

            public final zza zzbh(int i) {
                zzrf();
                ((zzaa) this.zzbol).zzbf(i);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        public static final class zzc extends zzue<zzc, zza> implements zzvq {
            private static final zzc zzaoo = new zzc();
            private static volatile zzvz<zzc> zzbm;
            private float zzanw;
            private int zzbg;

            public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzc, zza> implements zzvq {
                private zza() {
                    super(zzc.zzaoo);
                }

                /* synthetic */ zza(zzme zzme) {
                    this();
                }
            }

            private zzc() {
            }

            protected final Object zza(int i, Object obj, Object obj2) {
                switch (zzme.zzbn[i - 1]) {
                    case 1:
                        return new zzc();
                    case 2:
                        return new zza();
                    case 3:
                        return zzue.zza(zzaoo, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0001\u0000", new Object[]{"zzbg", "zzanw"});
                    case 4:
                        return zzaoo;
                    case 5:
                        Object obj3 = zzbm;
                        if (obj3 == null) {
                            synchronized (zzc.class) {
                                obj3 = zzbm;
                                if (obj3 == null) {
                                    obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaoo);
                                    zzbm = obj3;
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
                zzue.zza(zzc.class, zzaoo);
            }
        }

        private zzaa() {
        }

        private final void zza(zza zza) {
            this.zzadw = (zzs) ((zzue) zza.zzrj());
            this.zzbg |= 1;
        }

        private final void zza(zzb zzb) {
            if (zzb != null) {
                this.zzbg |= 2;
                this.zzaoe = zzb.zzo();
                return;
            }
            throw new NullPointerException();
        }

        private final void zzbe(int i) {
            this.zzbg |= 4;
            this.zzaof = i;
        }

        private final void zzbf(int i) {
            this.zzbg |= 8;
            this.zzaog = i;
        }

        public static zza zzla() {
            return (zza) zzaoh.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzaa();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaoh, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001\t\u0000\u0002\u001b\u0003\f\u0001\u0004\u0004\u0002\u0005\u0004\u0003", new Object[]{"zzbg", "zzadw", "zzaod", zzc.class, "zzaoe", zzb.zzq(), "zzaof", "zzaog"});
                case 4:
                    return zzaoh;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzaa.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaoh);
                                zzbm = obj3;
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

        public static zzaa zzlb() {
            return zzaoh;
        }

        static {
            zzue.zza(zzaa.class, zzaoh);
        }
    }

    public static final class zzab extends zzue<zzab, zza> implements zzvq {
        private static final zzab zzaop = new zzab();
        private static volatile zzvz<zzab> zzbm;
        private zzs zzadw;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzab, zza> implements zzvq {
            private zza() {
                super(zzab.zzaop);
            }

            public final zza zzh(zza zza) {
                zzrf();
                ((zzab) this.zzbol).zza(zza);
                return this;
            }

            public final zza zze(zzr zzr) {
                zzrf();
                ((zzab) this.zzbol).zza(zzr);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzab() {
        }

        private final void zza(zza zza) {
            this.zzadw = (zzs) ((zzue) zza.zzrj());
            this.zzbg |= 1;
        }

        private final void zza(zzr zzr) {
            if (zzr != null) {
                this.zzady = zzr;
                this.zzbg |= 2;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzle() {
            return (zza) zzaop.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzab();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaop, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001", new Object[]{"zzbg", "zzadw", "zzady"});
                case 4:
                    return zzaop;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzab.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaop);
                                zzbm = obj3;
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

        public static zzab zzlf() {
            return zzaop;
        }

        static {
            zzue.zza(zzab.class, zzaop);
        }
    }

    public static final class zzac extends zzue<zzac, zza> implements zzvq {
        private static final zzac zzaoy = new zzac();
        private static volatile zzvz<zzac> zzbm;
        private String zzaoq;
        private String zzaor;
        private String zzaos;
        private String zzaot;
        private String zzaou;
        private String zzaov;
        private String zzaow;
        private zzun<String> zzaox = zzue.zzrb();
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzac, zza> implements zzvq {
            private zza() {
                super(zzac.zzaoy);
            }

            public final zza zzbq(String str) {
                zzrf();
                ((zzac) this.zzbol).zzbj(str);
                return this;
            }

            public final zza zzbr(String str) {
                zzrf();
                ((zzac) this.zzbol).zzbk(str);
                return this;
            }

            public final zza zzbs(String str) {
                zzrf();
                ((zzac) this.zzbol).zzbl(str);
                return this;
            }

            public final zza zzbt(String str) {
                zzrf();
                ((zzac) this.zzbol).zzbm(str);
                return this;
            }

            public final zza zzbu(String str) {
                zzrf();
                ((zzac) this.zzbol).zzbn(str);
                return this;
            }

            public final zza zzbv(String str) {
                zzrf();
                ((zzac) this.zzbol).zzbo(str);
                return this;
            }

            public final zza zzbw(String str) {
                zzrf();
                ((zzac) this.zzbol).zzbp(str);
                return this;
            }

            public final zza zzn(Iterable<String> iterable) {
                zzrf();
                ((zzac) this.zzbol).zzm(iterable);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzac() {
            String str = "";
            this.zzaoq = str;
            this.zzaor = str;
            this.zzaos = str;
            this.zzaot = str;
            this.zzaou = str;
            this.zzaov = str;
            this.zzaow = str;
        }

        private final void zzbj(String str) {
            if (str != null) {
                this.zzbg |= 1;
                this.zzaoq = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzbk(String str) {
            if (str != null) {
                this.zzbg |= 2;
                this.zzaor = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzbl(String str) {
            if (str != null) {
                this.zzbg |= 4;
                this.zzaos = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzbm(String str) {
            if (str != null) {
                this.zzbg |= 8;
                this.zzaot = str;
                return;
            }
            throw new NullPointerException();
        }

        public final String zzlh() {
            return this.zzaou;
        }

        private final void zzbn(String str) {
            if (str != null) {
                this.zzbg |= 16;
                this.zzaou = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzbo(String str) {
            if (str != null) {
                this.zzbg |= 32;
                this.zzaov = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzbp(String str) {
            if (str != null) {
                this.zzbg |= 64;
                this.zzaow = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzm(Iterable<String> iterable) {
            if (!this.zzaox.zzps()) {
                this.zzaox = zzue.zza(this.zzaox);
            }
            zzsn.zza(iterable, this.zzaox);
        }

        public static zza zzli() {
            return (zza) zzaoy.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzac();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaoy, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0001\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\b\u0004\u0006\b\u0005\u0007\b\u0006\b\u001a", new Object[]{"zzbg", "zzaoq", "zzaor", "zzaos", "zzaot", "zzaou", "zzaov", "zzaow", "zzaox"});
                case 4:
                    return zzaoy;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzac.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaoy);
                                zzbm = obj3;
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

        public static zzac zzlj() {
            return zzaoy;
        }

        static {
            zzue.zza(zzac.class, zzaoy);
        }
    }

    public static final class zzb extends zzue<zzb, zza> implements zzvq {
        private static final zzb zzaeb = new zzb();
        private static volatile zzvz<zzb> zzbm;
        private int zzaea;
        private int zzbg;
        private int zzqo;

        public enum zzb implements zzuh {
            UNKNOWN_MODEL_TYPE(0),
            STABLE_MODEL(1),
            LATEST_MODEL(2);
            
            private static final zzui<zzb> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzb zzam(int i) {
                if (i == 0) {
                    return UNKNOWN_MODEL_TYPE;
                }
                if (i != 1) {
                    return i != 2 ? null : LATEST_MODEL;
                } else {
                    return STABLE_MODEL;
                }
            }

            public static zzuj zzq() {
                return zzmg.zzbs;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzmf();
            }
        }

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzb, zza> implements zzvq {
            private zza() {
                super(zzb.zzaeb);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzb() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaeb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0000\u0002\f\u0001", new Object[]{"zzbg", "zzqo", "zzaea", zzb.zzq()});
                case 4:
                    return zzaeb;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzb.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaeb);
                                zzbm = obj3;
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
            zzue.zza(zzb.class, zzaeb);
        }
    }

    public static final class zzc extends zzue<zzc, zza> implements zzvq {
        private static final zzc zzaeg = new zzc();
        private static volatile zzvz<zzc> zzbm;
        private zzs zzadw;
        private zzb zzadx;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzc, zza> implements zzvq {
            private zza() {
                super(zzc.zzaeg);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzc() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaeg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzadx", "zzady"});
                case 4:
                    return zzaeg;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzc.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaeg);
                                zzbm = obj3;
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
            zzue.zza(zzc.class, zzaeg);
        }
    }

    public static final class zzd extends zzue<zzd, zza> implements zzvq {
        private static final zzd zzaeh = new zzd();
        private static volatile zzvz<zzd> zzbm;
        private zzs zzadw;
        private zzb zzadx;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzd, zza> implements zzvq {
            private zza() {
                super(zzd.zzaeh);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzd() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaeh, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzadx", "zzady"});
                case 4:
                    return zzaeh;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzd.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaeh);
                                zzbm = obj3;
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
            zzue.zza(zzd.class, zzaeh);
        }
    }

    public static final class zze extends zzue<zze, zza> implements zzvq {
        private static final zze zzaei = new zze();
        private static volatile zzvz<zze> zzbm;
        private zzs zzadw;
        private zzb zzadx;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zze, zza> implements zzvq {
            private zza() {
                super(zze.zzaei);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zze() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaei, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzadx", "zzady"});
                case 4:
                    return zzaei;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zze.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaei);
                                zzbm = obj3;
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
            zzue.zza(zze.class, zzaei);
        }
    }

    public static final class zzf extends zzue<zzf, zza> implements zzvq {
        private static final zzf zzaej = new zzf();
        private static volatile zzvz<zzf> zzbm;
        private zzs zzadw;
        private zzb zzadx;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzf, zza> implements zzvq {
            private zza() {
                super(zzf.zzaej);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzf() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaej, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzadx", "zzady"});
                case 4:
                    return zzaej;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzf.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaej);
                                zzbm = obj3;
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
            zzue.zza(zzf.class, zzaej);
        }
    }

    public static final class zzg extends zzue<zzg, zza> implements zzvq {
        private static final zzg zzaek = new zzg();
        private static volatile zzvz<zzg> zzbm;
        private zzs zzadw;
        private zzb zzadx;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzg, zza> implements zzvq {
            private zza() {
                super(zzg.zzaek);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzg() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaek, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzadx", "zzady"});
                case 4:
                    return zzaek;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzg.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaek);
                                zzbm = obj3;
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
            zzue.zza(zzg.class, zzaek);
        }
    }

    public static final class zzh extends zzue<zzh, zza> implements zzvq {
        private static final zzh zzael = new zzh();
        private static volatile zzvz<zzh> zzbm;
        private zzs zzadw;
        private zzb zzadx;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzh, zza> implements zzvq {
            private zza() {
                super(zzh.zzael);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzh() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzh();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzael, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzadx", "zzady"});
                case 4:
                    return zzael;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzh.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzael);
                                zzbm = obj3;
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
            zzue.zza(zzh.class, zzael);
        }
    }

    public static final class zzi extends zzue<zzi, zza> implements zzvq {
        private static final zzi zzaem = new zzi();
        private static volatile zzvz<zzi> zzbm;
        private zzs zzadw;
        private zzb zzadx;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzi, zza> implements zzvq {
            private zza() {
                super(zzi.zzaem);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzi() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzi();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaem, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzadx", "zzady"});
                case 4:
                    return zzaem;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzi.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaem);
                                zzbm = obj3;
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
            zzue.zza(zzi.class, zzaem);
        }
    }

    public static final class zzj extends zzue<zzj, zza> implements zzvq {
        private static final zzj zzaen = new zzj();
        private static volatile zzvz<zzj> zzbm;
        private zzs zzadw;
        private zzb zzadx;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzj, zza> implements zzvq {
            private zza() {
                super(zzj.zzaen);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzj() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzj();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaen, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzadx", "zzady"});
                case 4:
                    return zzaen;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzj.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaen);
                                zzbm = obj3;
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
            zzue.zza(zzj.class, zzaen);
        }
    }

    public static final class zzk extends zzue<zzk, zza> implements zzvq {
        private static final zzk zzaeo = new zzk();
        private static volatile zzvz<zzk> zzbm;
        private zzs zzadw;
        private zzb zzadx;
        private zzr zzady;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzk, zza> implements zzvq {
            private zza() {
                super(zzk.zzaeo);
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzk() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzk();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaeo, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzadx", "zzady"});
                case 4:
                    return zzaeo;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzk.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaeo);
                                zzbm = obj3;
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
            zzue.zza(zzk.class, zzaeo);
        }
    }

    public static final class zzl extends zzue<zzl, zza> implements zzvq {
        private static final zzl zzaes = new zzl();
        private static volatile zzvz<zzl> zzbm;
        private zzo zzaep;
        private long zzaeq;
        private int zzaer;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzl, zza> implements zzvq {
            private zza() {
                super(zzl.zzaes);
            }

            public final zza zzb(zzo zzo) {
                zzrf();
                ((zzl) this.zzbol).zza(zzo);
                return this;
            }

            public final zza zzi(long j) {
                zzrf();
                ((zzl) this.zzbol).zzh(j);
                return this;
            }

            public final zza zzb(zzmk zzmk) {
                zzrf();
                ((zzl) this.zzbol).zza(zzmk);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzl() {
        }

        private final void zza(zzo zzo) {
            if (zzo != null) {
                this.zzaep = zzo;
                this.zzbg |= 1;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzh(long j) {
            this.zzbg |= 2;
            this.zzaeq = j;
        }

        private final void zza(zzmk zzmk) {
            if (zzmk != null) {
                this.zzbg |= 4;
                this.zzaer = zzmk.zzo();
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzji() {
            return (zza) zzaes.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzl();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaes, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\u0003\u0001\u0003\f\u0002", new Object[]{"zzbg", "zzaep", "zzaeq", "zzaer", zzmk.zzq()});
                case 4:
                    return zzaes;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzl.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaes);
                                zzbm = obj3;
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
            zzue.zza(zzl.class, zzaes);
        }
    }

    public static final class zzm extends zzue<zzm, zza> implements zzvq {
        private static final zzm zzaew = new zzm();
        private static volatile zzvz<zzm> zzbm;
        private zzs zzadw;
        private zzo zzaep;
        private zzun<zzb> zzaet = zzue.zzrb();
        private zzun<zzb> zzaeu = zzue.zzrb();
        private long zzaev;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzm, zza> implements zzvq {
            private zza() {
                super(zzm.zzaew);
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzm) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzc(zzo zzo) {
                zzrf();
                ((zzm) this.zzbol).zza(zzo);
                return this;
            }

            public final zza zzc(Iterable<? extends zzb> iterable) {
                zzrf();
                ((zzm) this.zzbol).zza((Iterable) iterable);
                return this;
            }

            public final zza zzd(Iterable<? extends zzb> iterable) {
                zzrf();
                ((zzm) this.zzbol).zzb(iterable);
                return this;
            }

            public final zza zzk(long j) {
                zzrf();
                ((zzm) this.zzbol).zzj(j);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        public static final class zzb extends zzue<zzb, zza> implements zzvq {
            private static final zzb zzaez = new zzb();
            private static volatile zzvz<zzb> zzbm;
            private int zzaex;
            private zzul zzaey = zzue.zzqz();
            private int zzbg;

            public enum zzb implements zzuh {
                UNKNOWN_DATA_TYPE(0),
                TYPE_FLOAT32(1),
                TYPE_INT32(2),
                TYPE_BYTE(3),
                TYPE_LONG(4);
                
                private static final zzui<zzb> zzbe = null;
                private final int value;

                public final int zzo() {
                    return this.value;
                }

                public static zzb zzan(int i) {
                    if (i == 0) {
                        return UNKNOWN_DATA_TYPE;
                    }
                    if (i == 1) {
                        return TYPE_FLOAT32;
                    }
                    if (i == 2) {
                        return TYPE_INT32;
                    }
                    if (i != 3) {
                        return i != 4 ? null : TYPE_LONG;
                    } else {
                        return TYPE_BYTE;
                    }
                }

                public static zzuj zzq() {
                    return zzmi.zzbs;
                }

                private zzb(int i) {
                    this.value = i;
                }

                static {
                    zzbe = new zzmh();
                }
            }

            public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzb, zza> implements zzvq {
                private zza() {
                    super(zzb.zzaez);
                }

                public final zza zzb(zzb zzb) {
                    zzrf();
                    ((zzb) this.zzbol).zza(zzb);
                    return this;
                }

                public final zza zzf(Iterable<? extends Integer> iterable) {
                    zzrf();
                    ((zzb) this.zzbol).zze(iterable);
                    return this;
                }

                /* synthetic */ zza(zzme zzme) {
                    this();
                }
            }

            private zzb() {
            }

            private final void zza(zzb zzb) {
                if (zzb != null) {
                    this.zzbg |= 1;
                    this.zzaex = zzb.zzo();
                    return;
                }
                throw new NullPointerException();
            }

            private final void zze(Iterable<? extends Integer> iterable) {
                if (!this.zzaey.zzps()) {
                    this.zzaey = zzue.zza(this.zzaey);
                }
                zzsn.zza(iterable, this.zzaey);
            }

            public static zza zzjm() {
                return (zza) zzaez.zzqx();
            }

            protected final Object zza(int i, Object obj, Object obj2) {
                switch (zzme.zzbn[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza();
                    case 3:
                        return zzue.zza(zzaez, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\f\u0000\u0002\u0016", new Object[]{"zzbg", "zzaex", zzb.zzq(), "zzaey"});
                    case 4:
                        return zzaez;
                    case 5:
                        Object obj3 = zzbm;
                        if (obj3 == null) {
                            synchronized (zzb.class) {
                                obj3 = zzbm;
                                if (obj3 == null) {
                                    obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaez);
                                    zzbm = obj3;
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
                zzue.zza(zzb.class, zzaez);
            }
        }

        private zzm() {
        }

        private final void zza(zza zza) {
            this.zzadw = (zzs) ((zzue) zza.zzrj());
            this.zzbg |= 1;
        }

        private final void zza(zzo zzo) {
            if (zzo != null) {
                this.zzaep = zzo;
                this.zzbg |= 2;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(Iterable<? extends zzb> iterable) {
            if (!this.zzaet.zzps()) {
                this.zzaet = zzue.zza(this.zzaet);
            }
            zzsn.zza(iterable, this.zzaet);
        }

        private final void zzb(Iterable<? extends zzb> iterable) {
            if (!this.zzaeu.zzps()) {
                this.zzaeu = zzue.zza(this.zzaeu);
            }
            zzsn.zza(iterable, this.zzaeu);
        }

        private final void zzj(long j) {
            this.zzbg |= 4;
            this.zzaev = j;
        }

        public static zza zzjk() {
            return (zza) zzaew.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzm();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaew, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001\t\u0000\u0002\t\u0001\u0003\u001b\u0004\u001b\u0005\u0003\u0002", new Object[]{"zzbg", "zzadw", "zzaep", "zzaet", zzb.class, "zzaeu", zzb.class, "zzaev"});
                case 4:
                    return zzaew;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzm.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaew);
                                zzbm = obj3;
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
            zzue.zza(zzm.class, zzaew);
        }
    }

    public static final class zzn extends zzue<zzn, zza> implements zzvq {
        private static final zzum<Integer, zzmk> zzafj = new zzmj();
        private static final zzn zzafl = new zzn();
        private static volatile zzvz<zzn> zzbm;
        private long zzaev;
        private zzo zzafg;
        private zzo zzafh;
        private zzul zzafi = zzue.zzqz();
        private boolean zzafk;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzn, zza> implements zzvq {
            private zza() {
                super(zzn.zzafl);
            }

            public final zza zzf(zzo zzo) {
                zzrf();
                ((zzn) this.zzbol).zzd(zzo);
                return this;
            }

            public final zza zzg(zzo zzo) {
                zzrf();
                ((zzn) this.zzbol).zze(zzo);
                return this;
            }

            public final zza zzh(Iterable<? extends zzmk> iterable) {
                zzrf();
                ((zzn) this.zzbol).zzg(iterable);
                return this;
            }

            public final zza zzl(long j) {
                zzrf();
                ((zzn) this.zzbol).zzj(j);
                return this;
            }

            public final zza zzl(boolean z) {
                zzrf();
                ((zzn) this.zzbol).zzk(z);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzn() {
        }

        private final void zzd(zzo zzo) {
            if (zzo != null) {
                this.zzafg = zzo;
                this.zzbg |= 1;
                return;
            }
            throw new NullPointerException();
        }

        private final void zze(zzo zzo) {
            if (zzo != null) {
                this.zzafh = zzo;
                this.zzbg |= 2;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzg(Iterable<? extends zzmk> iterable) {
            if (!this.zzafi.zzps()) {
                this.zzafi = zzue.zza(this.zzafi);
            }
            for (zzmk zzo : iterable) {
                this.zzafi.zzdh(zzo.zzo());
            }
        }

        private final void zzj(long j) {
            this.zzbg |= 4;
            this.zzaev = j;
        }

        private final void zzk(boolean z) {
            this.zzbg |= 8;
            this.zzafk = z;
        }

        public static zza zzjo() {
            return (zza) zzafl.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzn();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzafl, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001\t\u0000\u0002\t\u0001\u0003\u001e\u0004\u0003\u0002\u0005\u0007\u0003", new Object[]{"zzbg", "zzafg", "zzafh", "zzafi", zzmk.zzq(), "zzaev", "zzafk"});
                case 4:
                    return zzafl;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzn.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzafl);
                                zzbm = obj3;
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
            zzue.zza(zzn.class, zzafl);
        }
    }

    public static final class zzo extends zzue<zzo, zza> implements zzvq {
        private static final zzo zzafq = new zzo();
        private static volatile zzvz<zzo> zzbm;
        private zzu zzafm;
        private zzb zzafn;
        private zzb zzafo;
        private boolean zzafp;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzo, zza> implements zzvq {
            private zza() {
                super(zzo.zzafq);
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzo) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzc(zzb zzb) {
                zzrf();
                ((zzo) this.zzbol).zza(zzb);
                return this;
            }

            public final zza zzd(zzb zzb) {
                zzrf();
                ((zzo) this.zzbol).zzb(zzb);
                return this;
            }

            public final zza zzn(boolean z) {
                zzrf();
                ((zzo) this.zzbol).zzm(z);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        public static final class zzb extends zzue<zzb, zza> implements zzvq {
            private static final zzb zzafv = new zzb();
            private static volatile zzvz<zzb> zzbm;
            private boolean zzafr;
            private boolean zzafs;
            private boolean zzaft;
            private boolean zzafu;
            private int zzbg;

            public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzb, zza> implements zzvq {
                private zza() {
                    super(zzb.zzafv);
                }

                public final zza zzr(boolean z) {
                    zzrf();
                    ((zzb) this.zzbol).zzo(z);
                    return this;
                }

                public final zza zzs(boolean z) {
                    zzrf();
                    ((zzb) this.zzbol).zzp(z);
                    return this;
                }

                public final zza zzt(boolean z) {
                    zzrf();
                    ((zzb) this.zzbol).zzq(z);
                    return this;
                }

                /* synthetic */ zza(zzme zzme) {
                    this();
                }
            }

            private zzb() {
            }

            private final void zzo(boolean z) {
                this.zzbg |= 1;
                this.zzafr = z;
            }

            private final void zzp(boolean z) {
                this.zzbg |= 2;
                this.zzafs = z;
            }

            private final void zzq(boolean z) {
                this.zzbg |= 4;
                this.zzaft = z;
            }

            public static zza zzjs() {
                return (zza) zzafv.zzqx();
            }

            protected final Object zza(int i, Object obj, Object obj2) {
                switch (zzme.zzbn[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza();
                    case 3:
                        return zzue.zza(zzafv, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0007\u0000\u0002\u0007\u0001\u0003\u0007\u0002\u0004\u0007\u0003", new Object[]{"zzbg", "zzafr", "zzafs", "zzaft", "zzafu"});
                    case 4:
                        return zzafv;
                    case 5:
                        Object obj3 = zzbm;
                        if (obj3 == null) {
                            synchronized (zzb.class) {
                                obj3 = zzbm;
                                if (obj3 == null) {
                                    obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzafv);
                                    zzbm = obj3;
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
                zzue.zza(zzb.class, zzafv);
            }
        }

        private zzo() {
        }

        private final void zza(zza zza) {
            this.zzafm = (zzu) ((zzue) zza.zzrj());
            this.zzbg |= 1;
        }

        private final void zza(zzb zzb) {
            if (zzb != null) {
                this.zzafn = zzb;
                this.zzbg |= 2;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzb(zzb zzb) {
            if (zzb != null) {
                this.zzafo = zzb;
                this.zzbg |= 4;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzm(boolean z) {
            this.zzbg |= 8;
            this.zzafp = z;
        }

        public static zza zzjq() {
            return (zza) zzafq.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzo();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzafq, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002\u0004\u0007\u0003", new Object[]{"zzbg", "zzafm", "zzafn", "zzafo", "zzafp"});
                case 4:
                    return zzafq;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzo.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzafq);
                                zzbm = obj3;
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
            zzue.zza(zzo.class, zzafq);
        }
    }

    public static final class zzp extends zzue<zzp, zza> implements zzvq {
        private static final zzp zzajh = new zzp();
        private static volatile zzvz<zzp> zzbm;
        private int zzajb;
        private int zzajc;
        private int zzajd;
        private int zzaje;
        private boolean zzajf;
        private float zzajg;
        private int zzbg;

        public enum zzb implements zzuh {
            UNKNOWN_CLASSIFICATIONS(0),
            NO_CLASSIFICATIONS(1),
            ALL_CLASSIFICATIONS(2);
            
            private static final zzui<zzb> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzb zzaq(int i) {
                if (i == 0) {
                    return UNKNOWN_CLASSIFICATIONS;
                }
                if (i != 1) {
                    return i != 2 ? null : ALL_CLASSIFICATIONS;
                } else {
                    return NO_CLASSIFICATIONS;
                }
            }

            public static zzuj zzq() {
                return zzmr.zzbs;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzmq();
            }
        }

        public enum zzc implements zzuh {
            UNKNOWN_CONTOURS(0),
            NO_CONTOURS(1),
            ALL_CONTOURS(2);
            
            private static final zzui<zzc> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzc zzar(int i) {
                if (i == 0) {
                    return UNKNOWN_CONTOURS;
                }
                if (i != 1) {
                    return i != 2 ? null : ALL_CONTOURS;
                } else {
                    return NO_CONTOURS;
                }
            }

            public static zzuj zzq() {
                return zzmt.zzbs;
            }

            private zzc(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzms();
            }
        }

        public enum zzd implements zzuh {
            UNKNOWN_LANDMARKS(0),
            NO_LANDMARKS(1),
            ALL_LANDMARKS(2);
            
            private static final zzui<zzd> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzd zzas(int i) {
                if (i == 0) {
                    return UNKNOWN_LANDMARKS;
                }
                if (i != 1) {
                    return i != 2 ? null : ALL_LANDMARKS;
                } else {
                    return NO_LANDMARKS;
                }
            }

            public static zzuj zzq() {
                return zzmv.zzbs;
            }

            private zzd(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzmu();
            }
        }

        public enum zze implements zzuh {
            UNKNOWN_PERFORMANCE(0),
            FAST(1),
            ACCURATE(2);
            
            private static final zzui<zze> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zze zzat(int i) {
                if (i == 0) {
                    return UNKNOWN_PERFORMANCE;
                }
                if (i != 1) {
                    return i != 2 ? null : ACCURATE;
                } else {
                    return FAST;
                }
            }

            public static zzuj zzq() {
                return zzmx.zzbs;
            }

            private zze(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzmw();
            }
        }

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzp, zza> implements zzvq {
            private zza() {
                super(zzp.zzajh);
            }

            public final zza zzb(zzd zzd) {
                zzrf();
                ((zzp) this.zzbol).zza(zzd);
                return this;
            }

            public final zza zzb(zzb zzb) {
                zzrf();
                ((zzp) this.zzbol).zza(zzb);
                return this;
            }

            public final zza zzb(zze zze) {
                zzrf();
                ((zzp) this.zzbol).zza(zze);
                return this;
            }

            public final zza zzb(zzc zzc) {
                zzrf();
                ((zzp) this.zzbol).zza(zzc);
                return this;
            }

            public final zza zzv(boolean z) {
                zzrf();
                ((zzp) this.zzbol).zzu(z);
                return this;
            }

            public final zza zzn(float f) {
                zzrf();
                ((zzp) this.zzbol).zzm(f);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzp() {
        }

        private final void zza(zzd zzd) {
            if (zzd != null) {
                this.zzbg |= 1;
                this.zzajb = zzd.zzo();
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zzb zzb) {
            if (zzb != null) {
                this.zzbg |= 2;
                this.zzajc = zzb.zzo();
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zze zze) {
            if (zze != null) {
                this.zzbg |= 4;
                this.zzajd = zze.zzo();
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zzc zzc) {
            if (zzc != null) {
                this.zzbg |= 8;
                this.zzaje = zzc.zzo();
                return;
            }
            throw new NullPointerException();
        }

        private final void zzu(boolean z) {
            this.zzbg |= 16;
            this.zzajf = z;
        }

        private final void zzm(float f) {
            this.zzbg |= 32;
            this.zzajg = f;
        }

        public static zza zzju() {
            return (zza) zzajh.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzp();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzajh, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\f\u0000\u0002\f\u0001\u0003\f\u0002\u0004\f\u0003\u0005\u0007\u0004\u0006\u0001\u0005", new Object[]{"zzbg", "zzajb", zzd.zzq(), "zzajc", zzb.zzq(), "zzajd", zze.zzq(), "zzaje", zzc.zzq(), "zzajf", "zzajg"});
                case 4:
                    return zzajh;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzp.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzajh);
                                zzbm = obj3;
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
            zzue.zza(zzp.class, zzajh);
        }
    }

    public static final class zzq extends zzue<zzq, zza> implements zzvq {
        private static final zzq zzakt = new zzq();
        private static volatile zzvz<zzq> zzbm;
        private zzac zzajy;
        private int zzajz;
        private zzl zzaka;
        private zzm zzakb;
        private zzn zzakc;
        private zzw zzakd;
        private zzab zzake;
        private zzv zzakf;
        private zzx zzakg;
        private zzaa zzakh;
        private zzz zzaki;
        private zzd zzakj;
        private zza zzakk;
        private zzc zzakl;
        private zzf zzakm;
        private zze zzakn;
        private zzg zzako;
        private zzh zzakp;
        private zzi zzakq;
        private zzj zzakr;
        private zzk zzaks;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzq, zza> implements zzvq {
            private zza() {
                super(zzq.zzakt);
            }

            public final zzac zzjw() {
                return ((zzq) this.zzbol).zzjw();
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzq) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zzmn zzmn) {
                zzrf();
                ((zzq) this.zzbol).zza(zzmn);
                return this;
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzq) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zzm zzm) {
                zzrf();
                ((zzq) this.zzbol).zza(zzm);
                return this;
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzq) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzq) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzq) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zzab zzab) {
                zzrf();
                ((zzq) this.zzbol).zza(zzab);
                return this;
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzq) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zzc zzc) {
                zzrf();
                ((zzq) this.zzbol).zza(zzc);
                return this;
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzq) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zzaa zzaa) {
                zzrf();
                ((zzq) this.zzbol).zza(zzaa);
                return this;
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzq) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zza zza) {
                zzrf();
                ((zzq) this.zzbol).zza(zza);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzq() {
        }

        public final zzac zzjw() {
            zzac zzac = this.zzajy;
            return zzac == null ? zzac.zzlj() : zzac;
        }

        private final void zza(zza zza) {
            this.zzajy = (zzac) ((zzue) zza.zzrj());
            this.zzbg |= 1;
        }

        private final void zza(zzmn zzmn) {
            if (zzmn != null) {
                this.zzbg |= 2;
                this.zzajz = zzmn.zzo();
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zza zza) {
            this.zzaka = (zzl) ((zzue) zza.zzrj());
            this.zzbg |= 4;
        }

        private final void zza(zzm zzm) {
            if (zzm != null) {
                this.zzakb = zzm;
                this.zzbg |= 8;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zza zza) {
            this.zzakb = (zzm) ((zzue) zza.zzrj());
            this.zzbg |= 8;
        }

        private final void zza(zza zza) {
            this.zzakc = (zzn) ((zzue) zza.zzrj());
            this.zzbg |= 16;
        }

        private final void zza(zza zza) {
            this.zzakd = (zzw) ((zzue) zza.zzrj());
            this.zzbg |= 32;
        }

        private final void zza(zzab zzab) {
            if (zzab != null) {
                this.zzake = zzab;
                this.zzbg |= 64;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zza zza) {
            this.zzake = (zzab) ((zzue) zza.zzrj());
            this.zzbg |= 64;
        }

        private final void zza(zzc zzc) {
            this.zzakf = (zzv) ((zzue) zzc.zzrj());
            this.zzbg |= 128;
        }

        private final void zza(zza zza) {
            this.zzakg = (zzx) ((zzue) zza.zzrj());
            this.zzbg |= 256;
        }

        private final void zza(zzaa zzaa) {
            if (zzaa != null) {
                this.zzakh = zzaa;
                this.zzbg |= 512;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zza zza) {
            this.zzakh = (zzaa) ((zzue) zza.zzrj());
            this.zzbg |= 512;
        }

        private final void zza(zza zza) {
            this.zzaki = (zzz) ((zzue) zza.zzrj());
            this.zzbg |= 1024;
        }

        public static zza zzjx() {
            return (zza) zzakt.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzq();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzakt, "\u0001\u0015\u0000\u0001\u0001\u0015\u0015\u0000\u0000\u0000\u0001\t\u0000\u0002\f\u0001\u0003\t\u0002\u0004\t\u0003\u0005\t\u0005\u0006\t\u0006\u0007\t\u0007\b\t\u000b\t\t\f\n\t\r\u000b\t\u000e\f\t\u000f\r\t\u0010\u000e\t\u0011\u000f\t\u0012\u0010\t\u0013\u0011\t\u0014\u0012\t\b\u0013\t\t\u0014\t\u0004\u0015\t\n", new Object[]{"zzbg", "zzajy", "zzajz", zzmn.zzq(), "zzaka", "zzakb", "zzakd", "zzake", "zzakf", "zzakj", "zzakk", "zzakl", "zzakm", "zzakn", "zzako", "zzakp", "zzakq", "zzakr", "zzaks", "zzakg", "zzakh", "zzakc", "zzaki"});
                case 4:
                    return zzakt;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzq.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzakt);
                                zzbm = obj3;
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
            zzue.zza(zzq.class, zzakt);
        }
    }

    public static final class zzr extends zzue<zzr, zza> implements zzvq {
        private static final zzr zzakx = new zzr();
        private static volatile zzvz<zzr> zzbm;
        private int zzaku;
        private int zzakv;
        private int zzakw;
        private int zzbg;

        public enum zzb implements zzuh {
            UNKNOWN_FORMAT(0),
            NV16(1),
            NV21(2),
            YV12(3),
            BITMAP(4),
            CM_SAMPLE_BUFFER_REF(5),
            UI_IMAGE(6);
            
            private static final zzui<zzb> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzb zzaw(int i) {
                switch (i) {
                    case 0:
                        return UNKNOWN_FORMAT;
                    case 1:
                        return NV16;
                    case 2:
                        return NV21;
                    case 3:
                        return YV12;
                    case 4:
                        return BITMAP;
                    case 5:
                        return CM_SAMPLE_BUFFER_REF;
                    case 6:
                        return UI_IMAGE;
                    default:
                        return null;
                }
            }

            public static zzuj zzq() {
                return zzmz.zzbs;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzmy();
            }
        }

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzr, zza> implements zzvq {
            private zza() {
                super(zzr.zzakx);
            }

            public final zza zzb(zzb zzb) {
                zzrf();
                ((zzr) this.zzbol).zza(zzb);
                return this;
            }

            public final zza zzav(int i) {
                zzrf();
                ((zzr) this.zzbol).zzau(i);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzr() {
        }

        private final void zza(zzb zzb) {
            if (zzb != null) {
                this.zzbg |= 1;
                this.zzaku = zzb.zzo();
                return;
            }
            throw new NullPointerException();
        }

        private final void zzau(int i) {
            this.zzbg |= 2;
            this.zzakv = i;
        }

        public static zza zzjz() {
            return (zza) zzakx.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzr();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzakx, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\f\u0000\u0002\u000b\u0001\u0003\u000b\u0002", new Object[]{"zzbg", "zzaku", zzb.zzq(), "zzakv", "zzakw"});
                case 4:
                    return zzakx;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzr.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzakx);
                                zzbm = obj3;
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
            zzue.zza(zzr.class, zzakx);
        }
    }

    public static final class zzs extends zzue<zzs, zza> implements zzvq {
        private static final zzs zzalk = new zzs();
        private static volatile zzvz<zzs> zzbm;
        private int zzaer;
        private boolean zzafk;
        private long zzalg;
        private boolean zzalh;
        private boolean zzali;
        private boolean zzalj;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzs, zza> implements zzvq {
            private zza() {
                super(zzs.zzalk);
            }

            public final zza zzn(long j) {
                zzrf();
                ((zzs) this.zzbol).zzm(j);
                return this;
            }

            public final zza zzc(zzmk zzmk) {
                zzrf();
                ((zzs) this.zzbol).zza(zzmk);
                return this;
            }

            public final zza zzz(boolean z) {
                zzrf();
                ((zzs) this.zzbol).zzw(z);
                return this;
            }

            public final zza zzaa(boolean z) {
                zzrf();
                ((zzs) this.zzbol).zzx(true);
                return this;
            }

            public final zza zzab(boolean z) {
                zzrf();
                ((zzs) this.zzbol).zzy(true);
                return this;
            }

            public final zza zzac(boolean z) {
                zzrf();
                ((zzs) this.zzbol).zzk(z);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzs() {
        }

        private final void zzm(long j) {
            this.zzbg |= 1;
            this.zzalg = j;
        }

        private final void zza(zzmk zzmk) {
            if (zzmk != null) {
                this.zzbg |= 2;
                this.zzaer = zzmk.zzo();
                return;
            }
            throw new NullPointerException();
        }

        private final void zzw(boolean z) {
            this.zzbg |= 4;
            this.zzalh = z;
        }

        private final void zzx(boolean z) {
            this.zzbg |= 8;
            this.zzali = z;
        }

        private final void zzy(boolean z) {
            this.zzbg |= 16;
            this.zzalj = z;
        }

        private final void zzk(boolean z) {
            this.zzbg |= 32;
            this.zzafk = z;
        }

        public static zza zzkb() {
            return (zza) zzalk.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzs();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzalk, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\u0003\u0000\u0002\f\u0001\u0003\u0007\u0002\u0004\u0007\u0003\u0005\u0007\u0004\u0006\u0007\u0005", new Object[]{"zzbg", "zzalg", "zzaer", zzmk.zzq(), "zzalh", "zzali", "zzalj", "zzafk"});
                case 4:
                    return zzalk;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzs.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzalk);
                                zzbm = obj3;
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
            zzue.zza(zzs.class, zzalk);
        }
    }

    public static final class zzt extends zzue<zzt, zza> implements zzvq {
        private static final zzt zzaln = new zzt();
        private static volatile zzvz<zzt> zzbm;
        private float zzall;
        private float zzalm;
        private int zzbg;
        private float zzfw;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzt, zza> implements zzvq {
            private zza() {
                super(zzt.zzaln);
            }

            public final zza zzp(float f) {
                zzrf();
                ((zzt) this.zzbol).zzo(f);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzt() {
        }

        private final void zzo(float f) {
            this.zzbg |= 4;
            this.zzfw = f;
        }

        public static zza zzkd() {
            return (zza) zzaln.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzt();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzaln, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0001\u0000\u0002\u0001\u0001\u0003\u0001\u0002", new Object[]{"zzbg", "zzall", "zzalm", "zzfw"});
                case 4:
                    return zzaln;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzt.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaln);
                                zzbm = obj3;
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

        public static zzt zzke() {
            return zzaln;
        }

        static {
            zzue.zza(zzt.class, zzaln);
        }
    }

    public static final class zzu extends zzue<zzu, zza> implements zzvq {
        private static final zzu zzalt = new zzu();
        private static volatile zzvz<zzu> zzbm;
        private String zzalo;
        private String zzalp;
        private int zzalq;
        private String zzalr;
        private String zzals;
        private int zzbg;

        public enum zzb implements zzuh {
            SOURCE_UNKNOWN(0),
            APP_ASSET(1),
            LOCAL(2),
            CLOUD(3),
            SDK_BUILT_IN(4);
            
            private static final zzui<zzb> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzb zzax(int i) {
                if (i == 0) {
                    return SOURCE_UNKNOWN;
                }
                if (i == 1) {
                    return APP_ASSET;
                }
                if (i == 2) {
                    return LOCAL;
                }
                if (i != 3) {
                    return i != 4 ? null : SDK_BUILT_IN;
                } else {
                    return CLOUD;
                }
            }

            public static zzuj zzq() {
                return zznb.zzbs;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzna();
            }
        }

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzu, zza> implements zzvq {
            private zza() {
                super(zzu.zzalt);
            }

            public final zza zzbe(String str) {
                zzrf();
                ((zzu) this.zzbol).setName(str);
                return this;
            }

            public final zza zzb(zzb zzb) {
                zzrf();
                ((zzu) this.zzbol).zza(zzb);
                return this;
            }

            public final zza zzbf(String str) {
                zzrf();
                ((zzu) this.zzbol).zzbc(str);
                return this;
            }

            public final zza zzbg(String str) {
                zzrf();
                ((zzu) this.zzbol).zzbd(str);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzu() {
            String str = "";
            this.zzalo = str;
            this.zzalp = str;
            this.zzalr = str;
            this.zzals = str;
        }

        private final void setName(String str) {
            if (str != null) {
                this.zzbg |= 1;
                this.zzalo = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zzb zzb) {
            if (zzb != null) {
                this.zzbg |= 4;
                this.zzalq = zzb.zzo();
                return;
            }
            throw new NullPointerException();
        }

        private final void zzbc(String str) {
            if (str != null) {
                this.zzbg |= 8;
                this.zzalr = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzbd(String str) {
            if (str != null) {
                this.zzbg |= 16;
                this.zzals = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzkg() {
            return (zza) zzalt.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzu();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzalt, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\f\u0002\u0004\b\u0003\u0005\b\u0004", new Object[]{"zzbg", "zzalo", "zzalp", "zzalq", zzb.zzq(), "zzalr", "zzals"});
                case 4:
                    return zzalt;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzu.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzalt);
                                zzbm = obj3;
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
            zzue.zza(zzu.class, zzalt);
        }
    }

    public static final class zzv extends zzue<zzv, zzc> implements zzvq {
        private static final zzum<Integer, zza> zzamc = new zznc();
        private static final zzum<Integer, zzb> zzame = new zznd();
        private static final zzv zzamf = new zzv();
        private static volatile zzvz<zzv> zzbm;
        private zzs zzadw;
        private zzr zzady;
        private com.google.android.gms.internal.firebase_ml.zzqs.zza zzama;
        private zzul zzamb = zzue.zzqz();
        private zzul zzamd = zzue.zzqz();
        private int zzbg;

        public enum zza implements zzuh {
            FORMAT_UNKNOWN(0),
            FORMAT_CODE_128(1),
            FORMAT_CODE_39(2),
            FORMAT_CODE_93(4),
            FORMAT_CODABAR(8),
            FORMAT_DATA_MATRIX(16),
            FORMAT_EAN_13(32),
            FORMAT_EAN_8(64),
            FORMAT_ITF(128),
            FORMAT_QR_CODE(256),
            FORMAT_UPC_A(512),
            FORMAT_UPC_E(1024),
            FORMAT_PDF417(2048),
            FORMAT_AZTEC(4096);
            
            private static final zzui<zza> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zza zzay(int i) {
                if (i == 0) {
                    return FORMAT_UNKNOWN;
                }
                if (i == 1) {
                    return FORMAT_CODE_128;
                }
                if (i == 2) {
                    return FORMAT_CODE_39;
                }
                switch (i) {
                    case 4:
                        return FORMAT_CODE_93;
                    case 8:
                        return FORMAT_CODABAR;
                    case 16:
                        return FORMAT_DATA_MATRIX;
                    case 32:
                        return FORMAT_EAN_13;
                    case 64:
                        return FORMAT_EAN_8;
                    case 128:
                        return FORMAT_ITF;
                    case 256:
                        return FORMAT_QR_CODE;
                    case 512:
                        return FORMAT_UPC_A;
                    case 1024:
                        return FORMAT_UPC_E;
                    case 2048:
                        return FORMAT_PDF417;
                    case 4096:
                        return FORMAT_AZTEC;
                    default:
                        return null;
                }
            }

            public static zzuj zzq() {
                return zznf.zzbs;
            }

            private zza(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzne();
            }
        }

        public enum zzb implements zzuh {
            TYPE_UNKNOWN(0),
            TYPE_CONTACT_INFO(1),
            TYPE_EMAIL(2),
            TYPE_ISBN(3),
            TYPE_PHONE(4),
            TYPE_PRODUCT(5),
            TYPE_SMS(6),
            TYPE_TEXT(7),
            TYPE_URL(8),
            TYPE_WIFI(9),
            TYPE_GEO(10),
            TYPE_CALENDAR_EVENT(11),
            TYPE_DRIVER_LICENSE(12);
            
            private static final zzui<zzb> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzb zzaz(int i) {
                switch (i) {
                    case 0:
                        return TYPE_UNKNOWN;
                    case 1:
                        return TYPE_CONTACT_INFO;
                    case 2:
                        return TYPE_EMAIL;
                    case 3:
                        return TYPE_ISBN;
                    case 4:
                        return TYPE_PHONE;
                    case 5:
                        return TYPE_PRODUCT;
                    case 6:
                        return TYPE_SMS;
                    case 7:
                        return TYPE_TEXT;
                    case 8:
                        return TYPE_URL;
                    case 9:
                        return TYPE_WIFI;
                    case 10:
                        return TYPE_GEO;
                    case 11:
                        return TYPE_CALENDAR_EVENT;
                    case 12:
                        return TYPE_DRIVER_LICENSE;
                    default:
                        return null;
                }
            }

            public static zzuj zzq() {
                return zznh.zzbs;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzng();
            }
        }

        public static final class zzc extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzv, zzc> implements zzvq {
            private zzc() {
                super(zzv.zzamf);
            }

            public final zzc zzc(zza zza) {
                zzrf();
                ((zzv) this.zzbol).zza(zza);
                return this;
            }

            public final zzc zzb(com.google.android.gms.internal.firebase_ml.zzqs.zza zza) {
                zzrf();
                ((zzv) this.zzbol).zza(zza);
                return this;
            }

            public final zzc zzk(Iterable<? extends zza> iterable) {
                zzrf();
                ((zzv) this.zzbol).zzi(iterable);
                return this;
            }

            public final zzc zzl(Iterable<? extends zzb> iterable) {
                zzrf();
                ((zzv) this.zzbol).zzj(iterable);
                return this;
            }

            public final zzc zzb(zzr zzr) {
                zzrf();
                ((zzv) this.zzbol).zza(zzr);
                return this;
            }

            /* synthetic */ zzc(zzme zzme) {
                this();
            }
        }

        private zzv() {
        }

        private final void zza(zza zza) {
            this.zzadw = (zzs) ((zzue) zza.zzrj());
            this.zzbg |= 1;
        }

        private final void zza(com.google.android.gms.internal.firebase_ml.zzqs.zza zza) {
            if (zza != null) {
                this.zzama = zza;
                this.zzbg |= 2;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzi(Iterable<? extends zza> iterable) {
            if (!this.zzamb.zzps()) {
                this.zzamb = zzue.zza(this.zzamb);
            }
            for (zza zzo : iterable) {
                this.zzamb.zzdh(zzo.zzo());
            }
        }

        private final void zzj(Iterable<? extends zzb> iterable) {
            if (!this.zzamd.zzps()) {
                this.zzamd = zzue.zza(this.zzamd);
            }
            for (zzb zzo : iterable) {
                this.zzamd.zzdh(zzo.zzo());
            }
        }

        private final void zza(zzr zzr) {
            if (zzr != null) {
                this.zzady = zzr;
                this.zzbg |= 4;
                return;
            }
            throw new NullPointerException();
        }

        public static zzc zzki() {
            return (zzc) zzamf.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzv();
                case 2:
                    return new zzc();
                case 3:
                    return zzue.zza(zzamf, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001\t\u0000\u0002\t\u0001\u0003\u001e\u0004\u001e\u0005\t\u0002", new Object[]{"zzbg", "zzadw", "zzama", "zzamb", zza.zzq(), "zzamd", zzb.zzq(), "zzady"});
                case 4:
                    return zzamf;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzv.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzamf);
                                zzbm = obj3;
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
            zzue.zza(zzv.class, zzamf);
        }
    }

    public static final class zzw extends zzue<zzw, zza> implements zzvq {
        private static final zzw zzann = new zzw();
        private static volatile zzvz<zzw> zzbm;
        private zzs zzadw;
        private zzr zzady;
        private com.google.android.gms.internal.firebase_ml.zzqs.zzb zzanj;
        private zzp zzank;
        private int zzanl;
        private int zzanm;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzw, zza> implements zzvq {
            private zza() {
                super(zzw.zzann);
            }

            public final zza zzd(zza zza) {
                zzrf();
                ((zzw) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzc(zzr zzr) {
                zzrf();
                ((zzw) this.zzbol).zza(zzr);
                return this;
            }

            public final zza zzb(zzp zzp) {
                zzrf();
                ((zzw) this.zzbol).zza(zzp);
                return this;
            }

            public final zza zzbc(int i) {
                zzrf();
                ((zzw) this.zzbol).zzba(i);
                return this;
            }

            public final zza zzbd(int i) {
                zzrf();
                ((zzw) this.zzbol).zzbb(i);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzw() {
        }

        private final void zza(zza zza) {
            this.zzadw = (zzs) ((zzue) zza.zzrj());
            this.zzbg |= 1;
        }

        private final void zza(zzr zzr) {
            if (zzr != null) {
                this.zzady = zzr;
                this.zzbg |= 4;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zzp zzp) {
            if (zzp != null) {
                this.zzank = zzp;
                this.zzbg |= 8;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzba(int i) {
            this.zzbg |= 16;
            this.zzanl = i;
        }

        private final void zzbb(int i) {
            this.zzbg |= 32;
            this.zzanm = i;
        }

        public static zza zzkk() {
            return (zza) zzann.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzw();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzann, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002\u0004\t\u0003\u0005\u000b\u0004\u0006\u000b\u0005", new Object[]{"zzbg", "zzadw", "zzanj", "zzady", "zzank", "zzanl", "zzanm"});
                case 4:
                    return zzann;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzw.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzann);
                                zzbm = obj3;
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
            zzue.zza(zzw.class, zzann);
        }
    }

    public static final class zzx extends zzue<zzx, zza> implements zzvq {
        private static final zzx zzanp = new zzx();
        private static volatile zzvz<zzx> zzbm;
        private zzs zzadw;
        private zzr zzady;
        private zzy zzano;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzx, zza> implements zzvq {
            private zza() {
                super(zzx.zzanp);
            }

            public final zza zze(zza zza) {
                zzrf();
                ((zzx) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zzy zzy) {
                zzrf();
                ((zzx) this.zzbol).zza(zzy);
                return this;
            }

            public final zza zzd(zzr zzr) {
                zzrf();
                ((zzx) this.zzbol).zza(zzr);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzx() {
        }

        private final void zza(zza zza) {
            this.zzadw = (zzs) ((zzue) zza.zzrj());
            this.zzbg |= 1;
        }

        private final void zza(zzy zzy) {
            if (zzy != null) {
                this.zzano = zzy;
                this.zzbg |= 2;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zzr zzr) {
            if (zzr != null) {
                this.zzady = zzr;
                this.zzbg |= 4;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzkm() {
            return (zza) zzanp.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzx();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzanp, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002", new Object[]{"zzbg", "zzadw", "zzano", "zzady"});
                case 4:
                    return zzanp;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzx.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzanp);
                                zzbm = obj3;
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
            zzue.zza(zzx.class, zzanp);
        }
    }

    public static final class zzy extends zzue<zzy, zza> implements zzvq {
        private static final zzy zzanr = new zzy();
        private static volatile zzvz<zzy> zzbm;
        private int zzanq;
        private int zzbg;
        private float zzfw;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzy, zza> implements zzvq {
            private zza() {
                super(zzy.zzanr);
            }

            public final zza zzq(float f) {
                zzrf();
                ((zzy) this.zzbol).zzo(f);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        private zzy() {
        }

        private final void zzo(float f) {
            this.zzbg |= 2;
            this.zzfw = f;
        }

        public static zza zzko() {
            return (zza) zzanr.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzy();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzanr, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u000b\u0000\u0002\u0001\u0001", new Object[]{"zzbg", "zzanq", "zzfw"});
                case 4:
                    return zzanr;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzy.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzanr);
                                zzbm = obj3;
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
            zzue.zza(zzy.class, zzanr);
        }
    }

    public static final class zzz extends zzue<zzz, zza> implements zzvq {
        private static final zzz zzanv = new zzz();
        private static volatile zzvz<zzz> zzbm;
        private zzs zzadw;
        private zzt zzans;
        private zzc zzant;
        private zzd zzanu;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzz, zza> implements zzvq {
            private zza() {
                super(zzz.zzanv);
            }

            public final zza zzf(zza zza) {
                zzrf();
                ((zzz) this.zzbol).zza(zza);
                return this;
            }

            public final zza zzb(zzt zzt) {
                zzrf();
                ((zzz) this.zzbol).zza(zzt);
                return this;
            }

            public final zza zzb(zzc zzc) {
                zzrf();
                ((zzz) this.zzbol).zza(zzc);
                return this;
            }

            public final zza zzb(zzd zzd) {
                zzrf();
                ((zzz) this.zzbol).zza(zzd);
                return this;
            }

            /* synthetic */ zza(zzme zzme) {
                this();
            }
        }

        public static final class zzb extends zzue<zzb, zza> implements zzvq {
            private static final zzb zzany = new zzb();
            private static volatile zzvz<zzb> zzbm;
            private float zzanw;
            private String zzanx = "";
            private int zzbg;

            public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzb, zza> implements zzvq {
                private zza() {
                    super(zzb.zzany);
                }

                public final zza zzs(float f) {
                    zzrf();
                    ((zzb) this.zzbol).zzr(f);
                    return this;
                }

                public final zza zzbi(String str) {
                    zzrf();
                    ((zzb) this.zzbol).zzbh(str);
                    return this;
                }

                /* synthetic */ zza(zzme zzme) {
                    this();
                }
            }

            private zzb() {
            }

            private final void zzr(float f) {
                this.zzbg |= 1;
                this.zzanw = f;
            }

            private final void zzbh(String str) {
                if (str != null) {
                    this.zzbg |= 2;
                    this.zzanx = str;
                    return;
                }
                throw new NullPointerException();
            }

            public static zza zzks() {
                return (zza) zzany.zzqx();
            }

            protected final Object zza(int i, Object obj, Object obj2) {
                switch (zzme.zzbn[i - 1]) {
                    case 1:
                        return new zzb();
                    case 2:
                        return new zza();
                    case 3:
                        return zzue.zza(zzany, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0001\u0000\u0002\b\u0001", new Object[]{"zzbg", "zzanw", "zzanx"});
                    case 4:
                        return zzany;
                    case 5:
                        Object obj3 = zzbm;
                        if (obj3 == null) {
                            synchronized (zzb.class) {
                                obj3 = zzbm;
                                if (obj3 == null) {
                                    obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzany);
                                    zzbm = obj3;
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
                zzue.zza(zzb.class, zzany);
            }
        }

        public static final class zzc extends zzue<zzc, zza> implements zzvq {
            private static final zzc zzaoa = new zzc();
            private static volatile zzvz<zzc> zzbm;
            private zzb zzanz;
            private int zzbg;

            public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzc, zza> implements zzvq {
                private zza() {
                    super(zzc.zzaoa);
                }

                public final zza zzb(zza zza) {
                    zzrf();
                    ((zzc) this.zzbol).zza(zza);
                    return this;
                }

                /* synthetic */ zza(zzme zzme) {
                    this();
                }
            }

            private zzc() {
            }

            private final void zza(zza zza) {
                this.zzanz = (zzb) ((zzue) zza.zzrj());
                this.zzbg |= 1;
            }

            public static zza zzku() {
                return (zza) zzaoa.zzqx();
            }

            protected final Object zza(int i, Object obj, Object obj2) {
                switch (zzme.zzbn[i - 1]) {
                    case 1:
                        return new zzc();
                    case 2:
                        return new zza();
                    case 3:
                        return zzue.zza(zzaoa, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t\u0000", new Object[]{"zzbg", "zzanz"});
                    case 4:
                        return zzaoa;
                    case 5:
                        Object obj3 = zzbm;
                        if (obj3 == null) {
                            synchronized (zzc.class) {
                                obj3 = zzbm;
                                if (obj3 == null) {
                                    obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaoa);
                                    zzbm = obj3;
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

            public static zzc zzkv() {
                return zzaoa;
            }

            static {
                zzue.zza(zzc.class, zzaoa);
            }
        }

        public static final class zzd extends zzue<zzd, zza> implements zzvq {
            private static final zzd zzaoc = new zzd();
            private static volatile zzvz<zzd> zzbm;
            private zzun<zzb> zzaob = zzue.zzrb();

            public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzd, zza> implements zzvq {
                private zza() {
                    super(zzd.zzaoc);
                }

                public final zza zzd(zza zza) {
                    zzrf();
                    ((zzd) this.zzbol).zzc(zza);
                    return this;
                }

                /* synthetic */ zza(zzme zzme) {
                    this();
                }
            }

            private zzd() {
            }

            private final void zzc(zza zza) {
                if (!this.zzaob.zzps()) {
                    this.zzaob = zzue.zza(this.zzaob);
                }
                this.zzaob.add((zzb) ((zzue) zza.zzrj()));
            }

            public static zza zzkx() {
                return (zza) zzaoc.zzqx();
            }

            protected final Object zza(int i, Object obj, Object obj2) {
                switch (zzme.zzbn[i - 1]) {
                    case 1:
                        return new zzd();
                    case 2:
                        return new zza();
                    case 3:
                        return zzue.zza(zzaoc, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzaob", zzb.class});
                    case 4:
                        return zzaoc;
                    case 5:
                        Object obj3 = zzbm;
                        if (obj3 == null) {
                            synchronized (zzd.class) {
                                obj3 = zzbm;
                                if (obj3 == null) {
                                    obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzaoc);
                                    zzbm = obj3;
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

            public static zzd zzky() {
                return zzaoc;
            }

            static {
                zzue.zza(zzd.class, zzaoc);
            }
        }

        private zzz() {
        }

        private final void zza(zza zza) {
            this.zzadw = (zzs) ((zzue) zza.zzrj());
            this.zzbg |= 1;
        }

        private final void zza(zzt zzt) {
            if (zzt != null) {
                this.zzans = zzt;
                this.zzbg |= 2;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zzc zzc) {
            if (zzc != null) {
                this.zzant = zzc;
                this.zzbg |= 4;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(zzd zzd) {
            if (zzd != null) {
                this.zzanu = zzd;
                this.zzbg |= 8;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzkq() {
            return (zza) zzanv.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzme.zzbn[i - 1]) {
                case 1:
                    return new zzz();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzanv, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002\u0004\t\u0003", new Object[]{"zzbg", "zzadw", "zzans", "zzant", "zzanu"});
                case 4:
                    return zzanv;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzz.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzanv);
                                zzbm = obj3;
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
            zzue.zza(zzz.class, zzanv);
        }
    }
}
