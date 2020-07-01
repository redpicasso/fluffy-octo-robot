package com.google.android.gms.internal.firebase_ml;

public final class zzqs {

    public static final class zza extends zzue<zza, zza> implements zzvq {
        private static final zzum<Integer, zzsk> zzbbb = new zzqu();
        private static final zza zzbbc = new zza();
        private static volatile zzvz<zza> zzbm;
        private zzul zzbba = zzue.zzqz();

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zza, zza> implements zzvq {
            private zza() {
                super(zza.zzbbc);
            }

            public final zza zzp(Iterable<? extends zzsk> iterable) {
                zzrf();
                ((zza) this.zzbol).zzo(iterable);
                return this;
            }

            /* synthetic */ zza(zzqt zzqt) {
                this();
            }
        }

        private zza() {
        }

        private final void zzo(Iterable<? extends zzsk> iterable) {
            if (!this.zzbba.zzps()) {
                this.zzbba = zzue.zza(this.zzbba);
            }
            for (zzsk zzo : iterable) {
                this.zzbba.zzdh(zzo.zzo());
            }
        }

        public static zza zzof() {
            return (zza) zzbbc.zzqx();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzqt.zzbn[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzbbc, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001e", new Object[]{"zzbba", zzsk.zzq()});
                case 4:
                    return zzbbc;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zza.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzbbc);
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
            zzue.zza(zza.class, zzbbc);
        }
    }

    public static final class zzb extends zzue<zzb, zza> implements zzvq {
        private static final zzb zzbbh = new zzb();
        private static volatile zzvz<zzb> zzbm;
        private float zzajg;
        private int zzbbd;
        private int zzbbe;
        private boolean zzbbf;
        private boolean zzbbg;
        private int zzbg;
        private int zzgd;

        public enum zzb implements zzuh {
            CLASSIFICATION_UNKNOWN(0),
            CLASSIFICATION_NONE(1),
            CLASSIFICATION_ALL(2);
            
            private static final zzui<zzb> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzb zzbv(int i) {
                if (i == 0) {
                    return CLASSIFICATION_UNKNOWN;
                }
                if (i != 1) {
                    return i != 2 ? null : CLASSIFICATION_ALL;
                } else {
                    return CLASSIFICATION_NONE;
                }
            }

            public static zzuj zzq() {
                return zzqw.zzbs;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzqv();
            }
        }

        public enum zzc implements zzuh {
            LANDMARK_UNKNOWN(0),
            LANDMARK_NONE(1),
            LANDMARK_ALL(2),
            LANDMARK_CONTOUR(3);
            
            private static final zzui<zzc> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzc zzbw(int i) {
                if (i == 0) {
                    return LANDMARK_UNKNOWN;
                }
                if (i == 1) {
                    return LANDMARK_NONE;
                }
                if (i != 2) {
                    return i != 3 ? null : LANDMARK_CONTOUR;
                } else {
                    return LANDMARK_ALL;
                }
            }

            public static zzuj zzq() {
                return zzqy.zzbs;
            }

            private zzc(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzqx();
            }
        }

        public enum zzd implements zzuh {
            MODE_UNKNOWN(0),
            MODE_ACCURATE(1),
            MODE_FAST(2),
            MODE_SELFIE(3);
            
            private static final zzui<zzd> zzbe = null;
            private final int value;

            public final int zzo() {
                return this.value;
            }

            public static zzd zzbx(int i) {
                if (i == 0) {
                    return MODE_UNKNOWN;
                }
                if (i == 1) {
                    return MODE_ACCURATE;
                }
                if (i != 2) {
                    return i != 3 ? null : MODE_SELFIE;
                } else {
                    return MODE_FAST;
                }
            }

            public static zzuj zzq() {
                return zzra.zzbs;
            }

            private zzd(int i) {
                this.value = i;
            }

            static {
                zzbe = new zzqz();
            }
        }

        public static final class zza extends com.google.android.gms.internal.firebase_ml.zzue.zza<zzb, zza> implements zzvq {
            private zza() {
                super(zzb.zzbbh);
            }

            /* synthetic */ zza(zzqt zzqt) {
                this();
            }
        }

        private zzb() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzqt.zzbn[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza();
                case 3:
                    return zzue.zza(zzbbh, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\f\u0000\u0002\f\u0001\u0003\f\u0002\u0004\u0007\u0003\u0005\u0007\u0004\u0006\u0001\u0005", new Object[]{"zzbg", "zzgd", zzd.zzq(), "zzbbd", zzc.zzq(), "zzbbe", zzb.zzq(), "zzbbf", "zzbbg", "zzajg"});
                case 4:
                    return zzbbh;
                case 5:
                    Object obj3 = zzbm;
                    if (obj3 == null) {
                        synchronized (zzb.class) {
                            obj3 = zzbm;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_ml.zzue.zzb(zzbbh);
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
            zzue.zza(zzb.class, zzbbh);
        }
    }
}
