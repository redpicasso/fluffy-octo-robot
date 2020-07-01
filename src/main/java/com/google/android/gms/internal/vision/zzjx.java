package com.google.android.gms.internal.vision;

import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.drew.metadata.exif.makernotes.OlympusRawInfoMakernoteDirectory;
import com.facebook.imageutils.JfifUtil;
import com.google.logging.type.LogSeverity;
import java.util.List;

public final class zzjx extends com.google.android.gms.internal.vision.zzfy.zzd<zzjx, zzc> implements zzhh {
    private static final zzjx zzael = new zzjx();
    private static volatile zzhq<zzjx> zzbf;
    private zzb zzaeb;
    private zzge<zze> zzaec = zzfy.zzey();
    private float zzaed;
    private float zzaee;
    private float zzaef;
    private zzge<zzd> zzaeg = zzfy.zzey();
    private zzge<zza> zzaeh = zzfy.zzey();
    private long zzaei;
    private long zzaej;
    private float zzaek;
    private int zzbg;
    private float zzih;
    private byte zzkc = (byte) 2;

    public static final class zza extends zzfy<zza, zza> implements zzhh {
        private static final zza zzaen = new zza();
        private static volatile zzhq<zza> zzbf;
        private float zzaem;
        private int zzbg;
        private float zzih;
        private int zzjd;
        private String zzmq = "";

        public enum zzb implements zzgb {
            TYPE_UNKNOWN(0),
            FREE_FORM(1),
            FEMALE(2),
            MALE(3),
            AGE(5),
            NON_HUMAN(6),
            GLASSES(7),
            DARK_GLASSES(8),
            HEADWEAR(9),
            EYES_VISIBLE(10),
            MOUTH_OPEN(11),
            FACIAL_HAIR(12),
            LONG_HAIR(13),
            FRONTAL_GAZE(14),
            SMILING(15);
            
            private static final zzgc<zzb> zzdv = null;
            private final int value;

            public final int zzr() {
                return this.value;
            }

            public static zzb zzbz(int i) {
                switch (i) {
                    case 0:
                        return TYPE_UNKNOWN;
                    case 1:
                        return FREE_FORM;
                    case 2:
                        return FEMALE;
                    case 3:
                        return MALE;
                    case 5:
                        return AGE;
                    case 6:
                        return NON_HUMAN;
                    case 7:
                        return GLASSES;
                    case 8:
                        return DARK_GLASSES;
                    case 9:
                        return HEADWEAR;
                    case 10:
                        return EYES_VISIBLE;
                    case 11:
                        return MOUTH_OPEN;
                    case 12:
                        return FACIAL_HAIR;
                    case 13:
                        return LONG_HAIR;
                    case 14:
                        return FRONTAL_GAZE;
                    case 15:
                        return SMILING;
                    default:
                        return null;
                }
            }

            public static zzgd zzah() {
                return zzka.zzhl;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzdv = new zzjz();
            }
        }

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zza, zza> implements zzhh {
            private zza() {
                super(zza.zzaen);
            }

            /* synthetic */ zza(zzjy zzjy) {
                this();
            }
        }

        private zza() {
        }

        public final String getName() {
            return this.zzmq;
        }

        public final float getConfidence() {
            return this.zzih;
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzjy.zzbc[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zzaen, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\f\u0000\u0002\b\u0001\u0003\u0001\u0002\u0004\u0001\u0003", new Object[]{"zzbg", "zzjd", zzb.zzah(), "zzmq", "zzih", "zzaem"});
                case 4:
                    return zzaen;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zza.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzaen);
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
            zzfy.zza(zza.class, zzaen);
        }
    }

    public static final class zzb extends zzfy<zzb, zza> implements zzhh {
        private static final zzb zzafi = new zzb();
        private static volatile zzhq<zzb> zzbf;
        private float zzafe;
        private float zzaff;
        private float zzafg;
        private float zzafh;
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zzb, zza> implements zzhh {
            private zza() {
                super(zzb.zzafi);
            }

            /* synthetic */ zza(zzjy zzjy) {
                this();
            }
        }

        private zzb() {
        }

        public final float zzig() {
            return this.zzafe;
        }

        public final float zzih() {
            return this.zzaff;
        }

        public final float zzii() {
            return this.zzafg;
        }

        public final float zzij() {
            return this.zzafh;
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzjy.zzbc[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zzafi, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0001\u0000\u0002\u0001\u0001\u0003\u0001\u0002\u0004\u0001\u0003", new Object[]{"zzbg", "zzafe", "zzaff", "zzafg", "zzafh"});
                case 4:
                    return zzafi;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zzb.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzafi);
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

        public static zzb zzik() {
            return zzafi;
        }

        static {
            zzfy.zza(zzb.class, zzafi);
        }
    }

    public static final class zzd extends zzfy<zzd, zza> implements zzhh {
        private static final zzd zzafm = new zzd();
        private static volatile zzhq<zzd> zzbf;
        private int zzafj;
        private zzeo zzafk = zzeo.zzrx;
        private String zzafl = "";
        private int zzbg;

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zzd, zza> implements zzhh {
            private zza() {
                super(zzd.zzafm);
            }

            /* synthetic */ zza(zzjy zzjy) {
                this();
            }
        }

        private zzd() {
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzjy.zzbc[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zzafm, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0000\u0002\n\u0001\u0003\b\u0002", new Object[]{"zzbg", "zzafj", "zzafk", "zzafl"});
                case 4:
                    return zzafm;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zzd.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzafm);
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
            zzfy.zza(zzd.class, zzafm);
        }
    }

    public static final class zze extends zzfy<zze, zza> implements zzhh {
        private static final zze zzafo = new zze();
        private static volatile zzhq<zze> zzbf;
        private int zzafn;
        private int zzbg;
        private float zzih;
        private int zzjd = 15000;
        private float zzjg;
        private float zzjh;
        private float zzji;

        public enum zzb implements zzgb {
            LEFT_EYE(0),
            RIGHT_EYE(1),
            LEFT_OF_LEFT_EYEBROW(2),
            RIGHT_OF_LEFT_EYEBROW(3),
            LEFT_OF_RIGHT_EYEBROW(4),
            RIGHT_OF_RIGHT_EYEBROW(5),
            MIDPOINT_BETWEEN_EYES(6),
            NOSE_TIP(9),
            UPPER_LIP(10),
            LOWER_LIP(11),
            MOUTH_LEFT(12),
            MOUTH_RIGHT(13),
            MOUTH_CENTER(45),
            NOSE_BOTTOM_RIGHT(43),
            NOSE_BOTTOM_LEFT(44),
            NOSE_BOTTOM_CENTER(LogSeverity.INFO_VALUE),
            LEFT_EYE_TOP_BOUNDARY(220),
            LEFT_EYE_RIGHT_CORNER(221),
            LEFT_EYE_BOTTOM_BOUNDARY(222),
            LEFT_EYE_LEFT_CORNER(223),
            RIGHT_EYE_TOP_BOUNDARY(CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY),
            RIGHT_EYE_RIGHT_CORNER(JfifUtil.MARKER_APP1),
            RIGHT_EYE_BOTTOM_BOUNDARY(226),
            RIGHT_EYE_LEFT_CORNER(227),
            LEFT_EYEBROW_UPPER_MIDPOINT(300),
            RIGHT_EYEBROW_UPPER_MIDPOINT(302),
            LEFT_EAR_TRAGION(240),
            RIGHT_EAR_TRAGION(241),
            LEFT_EYE_PUPIL(OlympusRawInfoMakernoteDirectory.TagWbRbLevelsDaylightFluor),
            RIGHT_EYE_PUPIL(305),
            FOREHEAD_GLABELLA(312),
            CHIN_GNATHION(314),
            CHIN_LEFT_GONION(ExifDirectoryBase.TAG_ARTIST),
            CHIN_RIGHT_GONION(316),
            LEFT_CHEEK_CENTER(238),
            RIGHT_CHEEK_CENTER(239),
            LEFT_EAR_TOP(242),
            RIGHT_EAR_TOP(243),
            LANDMARK_UNKNOWN(15000);
            
            private static final zzgc<zzb> zzdv = null;
            private final int value;

            public final int zzr() {
                return this.value;
            }

            public static zzb zzca(int i) {
                if (i == LogSeverity.INFO_VALUE) {
                    return NOSE_BOTTOM_CENTER;
                }
                if (i == 300) {
                    return LEFT_EYEBROW_UPPER_MIDPOINT;
                }
                if (i == 302) {
                    return RIGHT_EYEBROW_UPPER_MIDPOINT;
                }
                if (i == 312) {
                    return FOREHEAD_GLABELLA;
                }
                if (i == 15000) {
                    return LANDMARK_UNKNOWN;
                }
                if (i == OlympusRawInfoMakernoteDirectory.TagWbRbLevelsDaylightFluor) {
                    return LEFT_EYE_PUPIL;
                }
                if (i == 305) {
                    return RIGHT_EYE_PUPIL;
                }
                switch (i) {
                    case 0:
                        return LEFT_EYE;
                    case 1:
                        return RIGHT_EYE;
                    case 2:
                        return LEFT_OF_LEFT_EYEBROW;
                    case 3:
                        return RIGHT_OF_LEFT_EYEBROW;
                    case 4:
                        return LEFT_OF_RIGHT_EYEBROW;
                    case 5:
                        return RIGHT_OF_RIGHT_EYEBROW;
                    case 6:
                        return MIDPOINT_BETWEEN_EYES;
                    default:
                        switch (i) {
                            case 9:
                                return NOSE_TIP;
                            case 10:
                                return UPPER_LIP;
                            case 11:
                                return LOWER_LIP;
                            case 12:
                                return MOUTH_LEFT;
                            case 13:
                                return MOUTH_RIGHT;
                            default:
                                switch (i) {
                                    case 43:
                                        return NOSE_BOTTOM_RIGHT;
                                    case 44:
                                        return NOSE_BOTTOM_LEFT;
                                    case 45:
                                        return MOUTH_CENTER;
                                    default:
                                        switch (i) {
                                            case 220:
                                                return LEFT_EYE_TOP_BOUNDARY;
                                            case 221:
                                                return LEFT_EYE_RIGHT_CORNER;
                                            case 222:
                                                return LEFT_EYE_BOTTOM_BOUNDARY;
                                            case 223:
                                                return LEFT_EYE_LEFT_CORNER;
                                            case CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY /*224*/:
                                                return RIGHT_EYE_TOP_BOUNDARY;
                                            case JfifUtil.MARKER_APP1 /*225*/:
                                                return RIGHT_EYE_RIGHT_CORNER;
                                            case 226:
                                                return RIGHT_EYE_BOTTOM_BOUNDARY;
                                            case 227:
                                                return RIGHT_EYE_LEFT_CORNER;
                                            default:
                                                switch (i) {
                                                    case 238:
                                                        return LEFT_CHEEK_CENTER;
                                                    case 239:
                                                        return RIGHT_CHEEK_CENTER;
                                                    case 240:
                                                        return LEFT_EAR_TRAGION;
                                                    case 241:
                                                        return RIGHT_EAR_TRAGION;
                                                    case 242:
                                                        return LEFT_EAR_TOP;
                                                    case 243:
                                                        return RIGHT_EAR_TOP;
                                                    default:
                                                        switch (i) {
                                                            case 314:
                                                                return CHIN_GNATHION;
                                                            case ExifDirectoryBase.TAG_ARTIST /*315*/:
                                                                return CHIN_LEFT_GONION;
                                                            case 316:
                                                                return CHIN_RIGHT_GONION;
                                                            default:
                                                                return null;
                                                        }
                                                }
                                        }
                                }
                        }
                }
            }

            public static zzgd zzah() {
                return zzkc.zzhl;
            }

            private zzb(int i) {
                this.value = i;
            }

            static {
                zzdv = new zzkb();
            }
        }

        public enum zzc implements zzgb {
            VISIBILITY_UNKNOWN(0),
            VISIBLE(1),
            OCCLUDED_SELF(2),
            OCCLUDED_OTHER(3);
            
            private static final zzgc<zzc> zzdv = null;
            private final int value;

            public final int zzr() {
                return this.value;
            }

            public static zzc zzcb(int i) {
                if (i == 0) {
                    return VISIBILITY_UNKNOWN;
                }
                if (i == 1) {
                    return VISIBLE;
                }
                if (i != 2) {
                    return i != 3 ? null : OCCLUDED_OTHER;
                } else {
                    return OCCLUDED_SELF;
                }
            }

            public static zzgd zzah() {
                return zzke.zzhl;
            }

            private zzc(int i) {
                this.value = i;
            }

            static {
                zzdv = new zzkd();
            }
        }

        public static final class zza extends com.google.android.gms.internal.vision.zzfy.zza<zze, zza> implements zzhh {
            private zza() {
                super(zze.zzafo);
            }

            /* synthetic */ zza(zzjy zzjy) {
                this();
            }
        }

        private zze() {
        }

        public final float getX() {
            return this.zzjg;
        }

        public final float getY() {
            return this.zzjh;
        }

        public final zzb zzin() {
            zzb zzca = zzb.zzca(this.zzjd);
            return zzca == null ? zzb.LANDMARK_UNKNOWN : zzca;
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzjy.zzbc[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza();
                case 3:
                    return zzfy.zza(zzafo, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\u0001\u0000\u0002\u0001\u0001\u0003\u0001\u0002\u0004\f\u0003\u0005\f\u0004\u0006\u0001\u0005", new Object[]{"zzbg", "zzjg", "zzjh", "zzji", "zzjd", zzb.zzah(), "zzafn", zzc.zzah(), "zzih"});
                case 4:
                    return zzafo;
                case 5:
                    Object obj3 = zzbf;
                    if (obj3 == null) {
                        synchronized (zze.class) {
                            obj3 = zzbf;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzafo);
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
            zzfy.zza(zze.class, zzafo);
        }
    }

    public static final class zzc extends com.google.android.gms.internal.vision.zzfy.zzc<zzjx, zzc> implements zzhh {
        private zzc() {
            super(zzjx.zzael);
        }

        /* synthetic */ zzc(zzjy zzjy) {
            this();
        }
    }

    private zzjx() {
    }

    public final zzb zzhw() {
        zzb zzb = this.zzaeb;
        return zzb == null ? zzb.zzik() : zzb;
    }

    public final List<zze> zzhx() {
        return this.zzaec;
    }

    public final float zzhy() {
        return this.zzaed;
    }

    public final float zzhz() {
        return this.zzaee;
    }

    public final float zzia() {
        return this.zzaef;
    }

    public final List<zza> zzib() {
        return this.zzaeh;
    }

    public final long zzic() {
        return this.zzaei;
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        int i2 = 0;
        switch (zzjy.zzbc[i - 1]) {
            case 1:
                return new zzjx();
            case 2:
                return new zzc();
            case 3:
                return zzfy.zza(zzael, "\u0001\u000b\u0000\u0001\u0001\u000b\u000b\u0000\u0003\u0000\u0001\t\u0000\u0002\u001b\u0003\u0001\u0001\u0004\u0001\u0002\u0005\u0001\u0003\u0006\u0001\u0004\u0007\u001b\b\u001b\t\u0003\u0005\n\u0001\u0007\u000b\u0003\u0006", new Object[]{"zzbg", "zzaeb", "zzaec", zze.class, "zzih", "zzaed", "zzaee", "zzaef", "zzaeg", zzd.class, "zzaeh", zza.class, "zzaei", "zzaek", "zzaej"});
            case 4:
                return zzael;
            case 5:
                Object obj3 = zzbf;
                if (obj3 == null) {
                    synchronized (zzjx.class) {
                        obj3 = zzbf;
                        if (obj3 == null) {
                            obj3 = new com.google.android.gms.internal.vision.zzfy.zzb(zzael);
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

    public static zzjx zzid() {
        return zzael;
    }

    static {
        zzfy.zza(zzjx.class, zzael);
    }
}
