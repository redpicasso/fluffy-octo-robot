package com.google.android.gms.internal.firebase_auth;

import java.util.List;

public final class zzp {

    public static final class zza extends zzhs<zza, zza> implements zzje {
        private static final zza zzak = new zza();
        private static volatile zzjm<zza> zzs;
        private String zzaa;
        private String zzab;
        private String zzac;
        private String zzad;
        private String zzae;
        private String zzaf;
        private String zzag;
        private zzhz<zzl> zzah = zzhs.zzim();
        private String zzai;
        private long zzaj;
        private int zzo;
        private String zzu;
        private String zzv;
        private String zzw;
        private String zzx;
        private String zzy;
        private String zzz;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zza, zza> implements zzje {
            private zza() {
                super(zza.zzak);
            }

            public final zza zza(String str) {
                zzid();
                ((zza) this.zzaah).zzd(str);
                return this;
            }

            public final zza zzb(String str) {
                zzid();
                ((zza) this.zzaah).zze(str);
                return this;
            }

            public final zza zzc(String str) {
                zzid();
                ((zza) this.zzaah).zzf(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zza() {
            String str = "";
            this.zzu = str;
            this.zzv = str;
            this.zzw = str;
            this.zzx = str;
            this.zzy = str;
            this.zzz = str;
            this.zzaa = str;
            this.zzab = str;
            this.zzac = str;
            this.zzad = str;
            this.zzae = str;
            this.zzaf = str;
            this.zzag = str;
            this.zzai = str;
        }

        private final void zzd(String str) {
            if (str != null) {
                this.zzo |= 1;
                this.zzu = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zze(String str) {
            if (str != null) {
                this.zzo |= 2;
                this.zzv = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzf(String str) {
            if (str != null) {
                this.zzo |= 8192;
                this.zzai = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzd() {
            return (zza) zzak.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzak, "\u0001\u0010\u0000\u0001\u0001\u0010\u0010\u0000\u0001\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\b\u0004\u0006\b\u0005\u0007\b\u0006\b\b\u0007\t\b\b\n\b\t\u000b\b\n\f\b\u000b\r\b\f\u000e\u001b\u000f\b\r\u0010\u0003\u000e", new Object[]{"zzo", "zzu", "zzv", "zzw", "zzx", "zzy", "zzz", "zzaa", "zzab", "zzac", "zzad", "zzae", "zzaf", "zzag", "zzah", zzl.class, "zzai", "zzaj"});
                case 4:
                    return zzak;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zza.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzak);
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
            zzhs.zza(zza.class, zzak);
        }
    }

    public static final class zzb extends zzhs<zzb, zza> implements zzje {
        private static final zzb zzat = new zzb();
        private static volatile zzjm<zzb> zzs;
        private String zzaf;
        private String zzal;
        private String zzam;
        private zzhz<String> zzan;
        private boolean zzao;
        private boolean zzap;
        private boolean zzaq;
        private zzhz<String> zzar;
        private byte zzas = (byte) 2;
        private int zzo;
        private String zzx;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzb, zza> implements zzje {
            private zza() {
                super(zzb.zzat);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzb() {
            String str = "";
            this.zzal = str;
            this.zzam = str;
            this.zzan = zzhs.zzim();
            this.zzx = str;
            this.zzaf = str;
            this.zzar = zzhs.zzim();
        }

        public final String zzf() {
            return this.zzam;
        }

        public final List<String> zzg() {
            return this.zzan;
        }

        public final int zzh() {
            return this.zzan.size();
        }

        public final boolean zzi() {
            return this.zzao;
        }

        public final String getProviderId() {
            return this.zzx;
        }

        public final boolean zzj() {
            return this.zzap;
        }

        public final List<String> zzk() {
            return this.zzar;
        }

        public final int zzl() {
            return this.zzar.size();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzat, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0002\u0001\u0001Ԉ\u0000\u0002\b\u0001\u0003\u001a\u0004\u0007\u0002\u0005\b\u0003\u0006\u0007\u0004\u0007\u0007\u0005\b\b\u0006\t\u001a", new Object[]{"zzo", "zzal", "zzam", "zzan", "zzao", "zzx", "zzap", "zzaq", "zzaf", "zzar"});
                case 4:
                    return zzat;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzb.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzat);
                                zzs = obj3;
                            }
                        }
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzas);
                case 7:
                    if (obj != null) {
                        i2 = 1;
                    }
                    this.zzas = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzjm<zzb> zzm() {
            return (zzjm) zzat.zza(com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaat, null, null);
        }

        static {
            zzhs.zza(zzb.class, zzat);
        }
    }

    public static final class zzc extends zzhs<zzc, zza> implements zzje {
        private static final zzc zzax = new zzc();
        private static volatile zzjm<zzc> zzs;
        private String zzau;
        private long zzav;
        private String zzaw;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzc, zza> implements zzje {
            private zza() {
                super(zzc.zzax);
            }

            public final zza zzg(String str) {
                zzid();
                ((zzc) this.zzaah).zzh(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzc() {
            String str = "";
            this.zzau = str;
            this.zzaw = str;
        }

        private final void zzh(String str) {
            if (str != null) {
                this.zzo |= 4;
                this.zzaw = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzo() {
            return (zza) zzax.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzax, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\b\u0000\u0002\u0002\u0001\u0003\b\u0002", new Object[]{"zzo", "zzau", "zzav", "zzaw"});
                case 4:
                    return zzax;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzc.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzax);
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
            zzhs.zza(zzc.class, zzax);
        }
    }

    public static final class zzd extends zzhs<zzd, zza> implements zzje {
        private static final zzd zzba = new zzd();
        private static volatile zzjm<zzd> zzs;
        private String zzai;
        private long zzaj;
        private String zzaw;
        private String zzay;
        private String zzaz;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzd, zza> implements zzje {
            private zza() {
                super(zzd.zzba);
            }

            public final zza zzi(String str) {
                zzid();
                ((zzd) this.zzaah).zzm(str);
                return this;
            }

            public final zza zzj(String str) {
                zzid();
                ((zzd) this.zzaah).zzn(str);
                return this;
            }

            public final zza zzk(String str) {
                zzid();
                ((zzd) this.zzaah).zzh(str);
                return this;
            }

            public final zza zzl(String str) {
                zzid();
                ((zzd) this.zzaah).zzf(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzd() {
            String str = "";
            this.zzay = str;
            this.zzaz = str;
            this.zzaw = str;
            this.zzai = str;
        }

        private final void zzm(String str) {
            if (str != null) {
                this.zzo |= 1;
                this.zzay = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzn(String str) {
            if (str != null) {
                this.zzo |= 2;
                this.zzaz = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzh(String str) {
            if (str != null) {
                this.zzo |= 4;
                this.zzaw = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzf(String str) {
            if (str != null) {
                this.zzo |= 8;
                this.zzai = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzq() {
            return (zza) zzba.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzba, "\u0001\u0005\u0000\u0001\u0001\u0007\u0005\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0006\b\u0003\u0007\u0003\u0004", new Object[]{"zzo", "zzay", "zzaz", "zzaw", "zzai", "zzaj"});
                case 4:
                    return zzba;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzd.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzba);
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
            zzhs.zza(zzd.class, zzba);
        }
    }

    public static final class zze extends zzhs<zze, zza> implements zzje {
        private static final zze zzbe = new zze();
        private static volatile zzjm<zze> zzs;
        private String zzal;
        private byte zzas = (byte) 2;
        private String zzau;
        private String zzaw;
        private String zzaz;
        private String zzbb;
        private long zzbc;
        private boolean zzbd;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zze, zza> implements zzje {
            private zza() {
                super(zze.zzbe);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zze() {
            String str = "";
            this.zzal = str;
            this.zzaw = str;
            this.zzaz = str;
            this.zzbb = str;
            this.zzau = str;
        }

        public final String getIdToken() {
            return this.zzaw;
        }

        public final String getEmail() {
            return this.zzaz;
        }

        public final String zzs() {
            return this.zzbb;
        }

        public final long zzt() {
            return this.zzbc;
        }

        public final String getLocalId() {
            return this.zzau;
        }

        public final boolean zzu() {
            return this.zzbd;
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzbe, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0001\u0001Ԉ\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\u0002\u0004\u0006\b\u0005\u0007\u0007\u0006", new Object[]{"zzo", "zzal", "zzaw", "zzaz", "zzbb", "zzbc", "zzau", "zzbd"});
                case 4:
                    return zzbe;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zze.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzbe);
                                zzs = obj3;
                            }
                        }
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzas);
                case 7:
                    if (obj != null) {
                        i2 = 1;
                    }
                    this.zzas = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzjm<zze> zzm() {
            return (zzjm) zzbe.zza(com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaat, null, null);
        }

        static {
            zzhs.zza(zze.class, zzbe);
        }
    }

    public static final class zzf extends zzhs<zzf, zza> implements zzje {
        private static final zzf zzbh = new zzf();
        private static volatile zzjm<zzf> zzs;
        private long zzav;
        private String zzaw = "";
        private zzhz<String> zzbf = zzhs.zzim();
        private zzhz<String> zzbg = zzhs.zzim();
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzf, zza> implements zzje {
            private zza() {
                super(zzf.zzbh);
            }

            public final zza zzo(String str) {
                zzid();
                ((zzf) this.zzaah).zzh(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzf() {
        }

        private final void zzh(String str) {
            if (str != null) {
                this.zzo |= 1;
                this.zzaw = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzw() {
            return (zza) zzbh.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzbh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0002\u0000\u0001\b\u0000\u0002\u001a\u0003\u001a\u0004\u0002\u0001", new Object[]{"zzo", "zzaw", "zzbf", "zzbg", "zzav"});
                case 4:
                    return zzbh;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzf.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzbh);
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
            zzhs.zza(zzf.class, zzbh);
        }
    }

    public static final class zzg extends zzhs<zzg, zza> implements zzje {
        private static final zzg zzbj = new zzg();
        private static volatile zzjm<zzg> zzs;
        private String zzal = "";
        private byte zzas = (byte) 2;
        private zzhz<zzz> zzbi = zzhs.zzim();
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzg, zza> implements zzje {
            private zza() {
                super(zzg.zzbj);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzg() {
        }

        public final int zzy() {
            return this.zzbi.size();
        }

        public final zzz zzb(int i) {
            return (zzz) this.zzbi.get(i);
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzbj, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0001\u0001Ԉ\u0000\u0002\u001b", new Object[]{"zzo", "zzal", "zzbi", zzz.class});
                case 4:
                    return zzbj;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzg.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzbj);
                                zzs = obj3;
                            }
                        }
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzas);
                case 7:
                    if (obj != null) {
                        i2 = 1;
                    }
                    this.zzas = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzjm<zzg> zzm() {
            return (zzjm) zzbj.zza(com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaat, null, null);
        }

        static {
            zzhs.zza(zzg.class, zzbj);
        }
    }

    public static final class zzh extends zzhs<zzh, zza> implements zzje {
        private static final zzh zzby = new zzh();
        private static volatile zzjm<zzh> zzs;
        private String zzai;
        private long zzaj;
        private String zzaw;
        private String zzaz;
        private int zzbk;
        private String zzbl;
        private String zzbm;
        private String zzbn;
        private String zzbo;
        private String zzbp;
        private String zzbq;
        private String zzbr;
        private String zzbs;
        private boolean zzbt;
        private String zzbu;
        private boolean zzbv;
        private String zzbw;
        private boolean zzbx;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzh, zza> implements zzje {
            private zza() {
                super(zzh.zzby);
            }

            public final zza zza(zzfw zzfw) {
                zzid();
                ((zzh) this.zzaah).zzb(zzfw);
                return this;
            }

            public final zza zzp(String str) {
                zzid();
                ((zzh) this.zzaah).zzn(str);
                return this;
            }

            public final zza zzq(String str) {
                zzid();
                ((zzh) this.zzaah).zzz(str);
                return this;
            }

            public final zza zzr(String str) {
                zzid();
                ((zzh) this.zzaah).zzh(str);
                return this;
            }

            public final zza zzs(String str) {
                zzid();
                ((zzh) this.zzaah).zzaa(str);
                return this;
            }

            public final zza zzt(String str) {
                zzid();
                ((zzh) this.zzaah).zzab(str);
                return this;
            }

            public final zza zzu(String str) {
                zzid();
                ((zzh) this.zzaah).zzac(str);
                return this;
            }

            public final zza zzv(String str) {
                zzid();
                ((zzh) this.zzaah).zzad(str);
                return this;
            }

            public final zza zza(boolean z) {
                zzid();
                ((zzh) this.zzaah).zzc(z);
                return this;
            }

            public final zza zzw(String str) {
                zzid();
                ((zzh) this.zzaah).zzae(str);
                return this;
            }

            public final zza zzb(boolean z) {
                zzid();
                ((zzh) this.zzaah).zzd(z);
                return this;
            }

            public final zza zzx(String str) {
                zzid();
                ((zzh) this.zzaah).zzf(str);
                return this;
            }

            public final zza zzy(String str) {
                zzid();
                ((zzh) this.zzaah).zzaf(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzh() {
            String str = "";
            this.zzaz = str;
            this.zzbl = str;
            this.zzbm = str;
            this.zzbn = str;
            this.zzbo = str;
            this.zzaw = str;
            this.zzbp = str;
            this.zzbq = str;
            this.zzbr = str;
            this.zzbs = str;
            this.zzbu = str;
            this.zzai = str;
            this.zzbw = str;
        }

        private final void zzb(zzfw zzfw) {
            if (zzfw != null) {
                this.zzo |= 1;
                this.zzbk = zzfw.zzbq();
                return;
            }
            throw new NullPointerException();
        }

        private final void zzn(String str) {
            if (str != null) {
                this.zzo |= 2;
                this.zzaz = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzz(String str) {
            if (str != null) {
                this.zzo |= 32;
                this.zzbo = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzh(String str) {
            if (str != null) {
                this.zzo |= 64;
                this.zzaw = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzaa(String str) {
            if (str != null) {
                this.zzo |= 128;
                this.zzbp = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzab(String str) {
            if (str != null) {
                this.zzo |= 256;
                this.zzbq = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzac(String str) {
            if (str != null) {
                this.zzo |= 512;
                this.zzbr = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzad(String str) {
            if (str != null) {
                this.zzo |= 1024;
                this.zzbs = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzc(boolean z) {
            this.zzo |= 2048;
            this.zzbt = z;
        }

        private final void zzae(String str) {
            if (str != null) {
                this.zzo |= 4096;
                this.zzbu = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzd(boolean z) {
            this.zzo |= 8192;
            this.zzbv = z;
        }

        private final void zzf(String str) {
            if (str != null) {
                this.zzo |= 16384;
                this.zzai = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzaf(String str) {
            if (str != null) {
                this.zzo |= 65536;
                this.zzbw = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzaa() {
            return (zza) zzby.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzh();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzby, "\u0001\u0012\u0000\u0001\u0001\u0013\u0012\u0000\u0000\u0000\u0001\f\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\b\u0004\u0006\b\u0005\u0007\b\u0006\b\b\u0007\t\b\b\n\b\t\u000b\b\n\f\u0007\u000b\r\b\f\u000e\u0007\r\u000f\b\u000e\u0010\u0003\u000f\u0012\b\u0010\u0013\u0007\u0011", new Object[]{"zzo", "zzbk", zzfw.zzbr(), "zzaz", "zzbl", "zzbm", "zzbn", "zzbo", "zzaw", "zzbp", "zzbq", "zzbr", "zzbs", "zzbt", "zzbu", "zzbv", "zzai", "zzaj", "zzbw", "zzbx"});
                case 4:
                    return zzby;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzh.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzby);
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
            zzhs.zza(zzh.class, zzby);
        }
    }

    public static final class zzi extends zzhs<zzi, zza> implements zzje {
        private static final zzi zzcb = new zzi();
        private static volatile zzjm<zzi> zzs;
        private String zzai;
        private long zzaj;
        private String zzay;
        private String zzaz;
        private String zzbz;
        private String zzca;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzi, zza> implements zzje {
            private zza() {
                super(zzi.zzcb);
            }

            public final zza zzag(String str) {
                zzid();
                ((zzi) this.zzaah).zzm(str);
                return this;
            }

            public final zza zzah(String str) {
                zzid();
                ((zzi) this.zzaah).zzaj(str);
                return this;
            }

            public final zza zzai(String str) {
                zzid();
                ((zzi) this.zzaah).zzf(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzi() {
            String str = "";
            this.zzay = str;
            this.zzbz = str;
            this.zzca = str;
            this.zzaz = str;
            this.zzai = str;
        }

        private final void zzm(String str) {
            if (str != null) {
                this.zzo |= 1;
                this.zzay = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzaj(String str) {
            if (str != null) {
                this.zzo |= 2;
                this.zzbz = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzf(String str) {
            if (str != null) {
                this.zzo |= 16;
                this.zzai = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzac() {
            return (zza) zzcb.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzi();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzcb, "\u0001\u0006\u0000\u0001\u0001\u0007\u0006\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0006\b\u0004\u0007\u0003\u0005", new Object[]{"zzo", "zzay", "zzbz", "zzca", "zzaz", "zzai", "zzaj"});
                case 4:
                    return zzcb;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzi.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzcb);
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
            zzhs.zza(zzi.class, zzcb);
        }
    }

    public static final class zzj extends zzhs<zzj, zza> implements zzje {
        private static final zzj zzcd = new zzj();
        private static volatile zzjm<zzj> zzs;
        private String zzal;
        private byte zzas = (byte) 2;
        private String zzaz;
        private String zzbo;
        private int zzcc;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzj, zza> implements zzje {
            private zza() {
                super(zzj.zzcd);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzj() {
            String str = "";
            this.zzal = str;
            this.zzaz = str;
            this.zzbo = str;
        }

        public final String getEmail() {
            return this.zzaz;
        }

        public final String zzae() {
            return this.zzbo;
        }

        public final zzfw zzaf() {
            zzfw zzk = zzfw.zzk(this.zzcc);
            return zzk == null ? zzfw.OOB_REQ_TYPE_UNSPECIFIED : zzk;
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzj();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzcd, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0001\u0001Ԉ\u0000\u0002\b\u0001\u0003\b\u0002\u0004\f\u0003", new Object[]{"zzo", "zzal", "zzaz", "zzbo", "zzcc", zzfw.zzbr()});
                case 4:
                    return zzcd;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzj.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzcd);
                                zzs = obj3;
                            }
                        }
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzas);
                case 7:
                    if (obj != null) {
                        i2 = 1;
                    }
                    this.zzas = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzjm<zzj> zzm() {
            return (zzjm) zzcd.zza(com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaat, null, null);
        }

        static {
            zzhs.zza(zzj.class, zzcd);
        }
    }

    public static final class zzk extends zzhs<zzk, zza> implements zzje {
        private static final zzk zzci = new zzk();
        private static volatile zzjm<zzk> zzs;
        private String zzai;
        private long zzaj;
        private String zzce;
        private String zzcf;
        private String zzcg;
        private String zzch;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzk, zza> implements zzje {
            private zza() {
                super(zzk.zzci);
            }

            public final zza zzak(String str) {
                zzid();
                ((zzk) this.zzaah).zzam(str);
                return this;
            }

            public final zza zzal(String str) {
                zzid();
                ((zzk) this.zzaah).zzf(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzk() {
            String str = "";
            this.zzce = str;
            this.zzcf = str;
            this.zzcg = str;
            this.zzch = str;
            this.zzai = str;
        }

        private final void zzam(String str) {
            if (str != null) {
                this.zzo |= 1;
                this.zzce = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzf(String str) {
            if (str != null) {
                this.zzo |= 16;
                this.zzai = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzah() {
            return (zza) zzci.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzk();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzci, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\b\u0004\u0006\u0003\u0005", new Object[]{"zzo", "zzce", "zzcf", "zzcg", "zzch", "zzai", "zzaj"});
                case 4:
                    return zzci;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzk.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzci);
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
            zzhs.zza(zzk.class, zzci);
        }
    }

    public static final class zzl extends zzhs<zzl, zza> implements zzje {
        private static final zzia<Integer, zzv> zzcv = new zzq();
        private static final zzl zzda = new zzl();
        private static volatile zzjm<zzl> zzs;
        private String zzai;
        private long zzaj;
        private String zzau;
        private long zzav;
        private String zzaw;
        private String zzay;
        private String zzaz;
        private String zzcj;
        private String zzck;
        private zzhz<String> zzcl = zzhs.zzim();
        private boolean zzcm;
        private boolean zzcn;
        private String zzco;
        private String zzcp;
        private zzkh zzcq;
        private boolean zzcr;
        private String zzcs;
        private String zzct;
        private zzhx zzcu;
        private boolean zzcw;
        private zzhz<String> zzcx;
        private long zzcy;
        private long zzcz;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzl, zza> implements zzje {
            private zza() {
                super(zzl.zzda);
            }

            public final zza zzap(String str) {
                zzid();
                ((zzl) this.zzaah).zzh(str);
                return this;
            }

            public final zza zzaq(String str) {
                zzid();
                ((zzl) this.zzaah).zzan(str);
                return this;
            }

            public final zza zzar(String str) {
                zzid();
                ((zzl) this.zzaah).zzn(str);
                return this;
            }

            public final zza zzas(String str) {
                zzid();
                ((zzl) this.zzaah).setPassword(str);
                return this;
            }

            public final zza zzat(String str) {
                zzid();
                ((zzl) this.zzaah).zzm(str);
                return this;
            }

            public final zza zzau(String str) {
                zzid();
                ((zzl) this.zzaah).zzao(str);
                return this;
            }

            public final zza zzc(Iterable<? extends zzv> iterable) {
                zzid();
                ((zzl) this.zzaah).zza(iterable);
                return this;
            }

            public final zza zzf(boolean z) {
                zzid();
                ((zzl) this.zzaah).zze(z);
                return this;
            }

            public final zza zzd(Iterable<String> iterable) {
                zzid();
                ((zzl) this.zzaah).zzb(iterable);
                return this;
            }

            public final zza zzav(String str) {
                zzid();
                ((zzl) this.zzaah).zzf(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzl() {
            String str = "";
            this.zzaw = str;
            this.zzau = str;
            this.zzcj = str;
            this.zzaz = str;
            this.zzck = str;
            this.zzay = str;
            this.zzco = str;
            this.zzcp = str;
            this.zzcs = str;
            this.zzct = str;
            this.zzcu = zzhs.zzil();
            this.zzcx = zzhs.zzim();
            this.zzai = str;
        }

        private final void zzh(String str) {
            if (str != null) {
                this.zzo |= 1;
                this.zzaw = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzan(String str) {
            if (str != null) {
                this.zzo |= 4;
                this.zzcj = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzn(String str) {
            if (str != null) {
                this.zzo |= 8;
                this.zzaz = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void setPassword(String str) {
            if (str != null) {
                this.zzo |= 16;
                this.zzck = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzm(String str) {
            if (str != null) {
                this.zzo |= 32;
                this.zzay = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzao(String str) {
            if (str != null) {
                this.zzo |= 16384;
                this.zzct = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zza(Iterable<? extends zzv> iterable) {
            if (!this.zzcu.zzfx()) {
                zzhx zzhx = this.zzcu;
                int size = zzhx.size();
                this.zzcu = zzhx.zzav(size == 0 ? 10 : size << 1);
            }
            for (zzv zzbq : iterable) {
                this.zzcu.zzaw(zzbq.zzbq());
            }
        }

        private final void zze(boolean z) {
            this.zzo |= 32768;
            this.zzcw = z;
        }

        private final void zzb(Iterable<String> iterable) {
            if (!this.zzcx.zzfx()) {
                zzhz zzhz = this.zzcx;
                int size = zzhz.size();
                this.zzcx = zzhz.zzo(size == 0 ? 10 : size << 1);
            }
            zzfx.zza(iterable, this.zzcx);
        }

        private final void zzf(String str) {
            if (str != null) {
                this.zzo |= 262144;
                this.zzai = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzaj() {
            return (zza) zzda.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzl();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzda, "\u0001\u0017\u0000\u0001\u0002\u001a\u0017\u0000\u0003\u0000\u0002\b\u0000\u0003\b\u0001\u0004\b\u0002\u0005\b\u0003\u0006\b\u0004\u0007\u001a\b\b\u0005\t\u0007\u0006\n\u0007\u0007\u000b\b\b\f\b\t\r\t\n\u000e\u0007\u000b\u000f\b\f\u0010\u0002\r\u0011\b\u000e\u0012\u001e\u0013\u0007\u000f\u0014\u001a\u0015\u0002\u0010\u0016\u0002\u0011\u0019\b\u0012\u001a\u0003\u0013", new Object[]{"zzo", "zzaw", "zzau", "zzcj", "zzaz", "zzck", "zzcl", "zzay", "zzcm", "zzcn", "zzco", "zzcp", "zzcq", "zzcr", "zzcs", "zzav", "zzct", "zzcu", zzv.zzbr(), "zzcw", "zzcx", "zzcy", "zzcz", "zzai", "zzaj"});
                case 4:
                    return zzda;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzl.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzda);
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
            zzhs.zza(zzl.class, zzda);
        }
    }

    public static final class zzm extends zzhs<zzm, zza> implements zzje {
        private static final zzm zzdd = new zzm();
        private static volatile zzjm<zzm> zzs;
        private String zzal;
        private byte zzas = (byte) 2;
        private String zzau;
        private String zzaw;
        private String zzaz;
        private String zzbb;
        private long zzbc;
        private String zzbo;
        private String zzcj;
        private zzhz<String> zzcl;
        private boolean zzcm;
        private String zzct;
        private zzhz<zzu> zzdb;
        private String zzdc;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzm, zza> implements zzje {
            private zza() {
                super(zzm.zzdd);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzm() {
            String str = "";
            this.zzal = str;
            this.zzau = str;
            this.zzaz = str;
            this.zzcj = str;
            this.zzcl = zzhs.zzim();
            this.zzaw = str;
            this.zzdb = zzhs.zzim();
            this.zzbo = str;
            this.zzct = str;
            this.zzbb = str;
            this.zzdc = str;
        }

        @Deprecated
        public final String getEmail() {
            return this.zzaz;
        }

        @Deprecated
        public final String getDisplayName() {
            return this.zzcj;
        }

        public final String getIdToken() {
            return this.zzaw;
        }

        @Deprecated
        public final List<zzu> zzal() {
            return this.zzdb;
        }

        @Deprecated
        public final String zzam() {
            return this.zzct;
        }

        public final String zzs() {
            return this.zzbb;
        }

        public final long zzt() {
            return this.zzbc;
        }

        public final String zzan() {
            return this.zzdc;
        }

        public final boolean zzao() {
            return this.zzcm;
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzm();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzdd, "\u0001\r\u0000\u0001\u0001\r\r\u0000\u0002\u0001\u0001Ԉ\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\u001a\u0006\b\u0004\u0007\u001b\b\b\u0005\t\b\u0006\n\b\u0007\u000b\u0002\b\f\b\t\r\u0007\n", new Object[]{"zzo", "zzal", "zzau", "zzaz", "zzcj", "zzcl", "zzaw", "zzdb", zzu.class, "zzbo", "zzct", "zzbb", "zzbc", "zzdc", "zzcm"});
                case 4:
                    return zzdd;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzm.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzdd);
                                zzs = obj3;
                            }
                        }
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzas);
                case 7:
                    if (obj != null) {
                        i2 = 1;
                    }
                    this.zzas = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzjm<zzm> zzm() {
            return (zzjm) zzdd.zza(com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaat, null, null);
        }

        static {
            zzhs.zza(zzm.class, zzdd);
        }
    }

    public static final class zzn extends zzhs<zzn, zza> implements zzje {
        private static final zzn zzdf = new zzn();
        private static volatile zzjm<zzn> zzs;
        private String zzai;
        private long zzaj;
        private String zzaw;
        private String zzaz;
        private String zzcj;
        private String zzck;
        private boolean zzcm;
        private String zzco;
        private String zzcp;
        private String zzcs;
        private String zzct;
        private boolean zzde;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzn, zza> implements zzje {
            private zza() {
                super(zzn.zzdf);
            }

            public final zza zzaw(String str) {
                zzid();
                ((zzn) this.zzaah).zzn(str);
                return this;
            }

            public final zza zzax(String str) {
                zzid();
                ((zzn) this.zzaah).setPassword(str);
                return this;
            }

            public final zza zzay(String str) {
                zzid();
                ((zzn) this.zzaah).zzf(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzn() {
            String str = "";
            this.zzaz = str;
            this.zzck = str;
            this.zzcj = str;
            this.zzco = str;
            this.zzcp = str;
            this.zzcs = str;
            this.zzaw = str;
            this.zzct = str;
            this.zzai = str;
        }

        private final void zzn(String str) {
            if (str != null) {
                this.zzo |= 1;
                this.zzaz = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void setPassword(String str) {
            if (str != null) {
                this.zzo |= 2;
                this.zzck = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzf(String str) {
            if (str != null) {
                this.zzo |= 1024;
                this.zzai = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzaq() {
            return (zza) zzdf.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzn();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzdf, "\u0001\f\u0000\u0001\u0001\u000e\f\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\b\u0004\u0006\b\u0005\u0007\b\u0006\b\u0007\u0007\t\b\b\n\u0007\t\r\b\n\u000e\u0003\u000b", new Object[]{"zzo", "zzaz", "zzck", "zzcj", "zzco", "zzcp", "zzcs", "zzaw", "zzcm", "zzct", "zzde", "zzai", "zzaj"});
                case 4:
                    return zzdf;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzn.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzdf);
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
            zzhs.zza(zzn.class, zzdf);
        }
    }

    public static final class zzo extends zzhs<zzo, zza> implements zzje {
        private static final zzo zzdg = new zzo();
        private static volatile zzjm<zzo> zzs;
        private String zzal;
        private byte zzas = (byte) 2;
        private String zzau;
        private String zzaw;
        private String zzaz;
        private String zzbb;
        private long zzbc;
        private String zzcj;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzo, zza> implements zzje {
            private zza() {
                super(zzo.zzdg);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzo() {
            String str = "";
            this.zzal = str;
            this.zzaw = str;
            this.zzcj = str;
            this.zzaz = str;
            this.zzbb = str;
            this.zzau = str;
        }

        public final String getIdToken() {
            return this.zzaw;
        }

        public final String getDisplayName() {
            return this.zzcj;
        }

        public final String getEmail() {
            return this.zzaz;
        }

        public final String zzs() {
            return this.zzbb;
        }

        public final long zzt() {
            return this.zzbc;
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzo();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzdg, "\u0001\u0007\u0000\u0001\u0001\b\u0007\u0000\u0000\u0001\u0001Ԉ\u0000\u0002\b\u0001\u0004\b\u0002\u0005\b\u0003\u0006\b\u0004\u0007\u0002\u0005\b\b\u0006", new Object[]{"zzo", "zzal", "zzaw", "zzcj", "zzaz", "zzbb", "zzbc", "zzau"});
                case 4:
                    return zzdg;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzo.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzdg);
                                zzs = obj3;
                            }
                        }
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzas);
                case 7:
                    if (obj != null) {
                        i2 = 1;
                    }
                    this.zzas = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzjm<zzo> zzm() {
            return (zzjm) zzdg.zza(com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaat, null, null);
        }

        static {
            zzhs.zza(zzo.class, zzdg);
        }
    }

    public static final class zzp extends zzhs<zzp, zza> implements zzje {
        private static final zzp zzdo = new zzp();
        private static volatile zzjm<zzp> zzs;
        private String zzaf;
        private String zzai;
        private long zzaj;
        private long zzav;
        private String zzaw;
        private String zzcs;
        private boolean zzcw;
        private String zzdh;
        private String zzdi;
        private String zzdj;
        private boolean zzdk;
        private boolean zzdl;
        private boolean zzdm = true;
        private String zzdn;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzp, zza> implements zzje {
            private zza() {
                super(zzp.zzdo);
            }

            public final zza zzbd(String str) {
                zzid();
                ((zzp) this.zzaah).zzaz(str);
                return this;
            }

            public final zza zzbe(String str) {
                zzid();
                ((zzp) this.zzaah).zzba(str);
                return this;
            }

            public final zza zzbf(String str) {
                zzid();
                ((zzp) this.zzaah).zzbb(str);
                return this;
            }

            public final zza zzbg(String str) {
                zzid();
                ((zzp) this.zzaah).zzh(str);
                return this;
            }

            public final zza zzi(boolean z) {
                zzid();
                ((zzp) this.zzaah).zze(z);
                return this;
            }

            public final zza zzj(boolean z) {
                zzid();
                ((zzp) this.zzaah).zzg(z);
                return this;
            }

            public final zza zzk(boolean z) {
                zzid();
                ((zzp) this.zzaah).zzh(z);
                return this;
            }

            public final zza zzbh(String str) {
                zzid();
                ((zzp) this.zzaah).zzf(str);
                return this;
            }

            public final zza zzbi(String str) {
                zzid();
                ((zzp) this.zzaah).zzbc(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzp() {
            String str = "";
            this.zzdh = str;
            this.zzdi = str;
            this.zzdj = str;
            this.zzaf = str;
            this.zzcs = str;
            this.zzaw = str;
            this.zzai = str;
            this.zzdn = str;
        }

        private final void zzaz(String str) {
            if (str != null) {
                this.zzo |= 1;
                this.zzdh = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzba(String str) {
            if (str != null) {
                this.zzo |= 2;
                this.zzdi = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzbb(String str) {
            if (str != null) {
                this.zzo |= 16;
                this.zzaf = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzh(String str) {
            if (str != null) {
                this.zzo |= 128;
                this.zzaw = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zze(boolean z) {
            this.zzo |= 256;
            this.zzcw = z;
        }

        private final void zzg(boolean z) {
            this.zzo |= 512;
            this.zzdl = z;
        }

        private final void zzh(boolean z) {
            this.zzo |= 1024;
            this.zzdm = z;
        }

        private final void zzf(String str) {
            if (str != null) {
                this.zzo |= 2048;
                this.zzai = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zzbc(String str) {
            if (str != null) {
                this.zzo |= 8192;
                this.zzdn = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzat() {
            return (zza) zzdo.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzp();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzdo, "\u0001\u000e\u0000\u0001\u0001\u000f\u000e\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004\u0007\u0003\u0005\b\u0004\u0006\b\u0005\u0007\u0002\u0006\b\b\u0007\t\u0007\b\n\u0007\t\u000b\u0007\n\r\b\u000b\u000e\u0003\f\u000f\b\r", new Object[]{"zzo", "zzdh", "zzdi", "zzdj", "zzdk", "zzaf", "zzcs", "zzav", "zzaw", "zzcw", "zzdl", "zzdm", "zzai", "zzaj", "zzdn"});
                case 4:
                    return zzdo;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzp.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzdo);
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
            zzhs.zza(zzp.class, zzdo);
        }
    }

    public static final class zzq extends zzhs<zzq, zza> implements zzje {
        private static final zzq zzet = new zzq();
        private static volatile zzjm<zzq> zzs;
        private String zzab;
        private String zzai;
        private String zzau;
        private String zzaw;
        private String zzaz;
        private String zzbb;
        private long zzbc;
        private boolean zzbd;
        private String zzcj;
        private boolean zzcm;
        private String zzct;
        private String zzdn;
        private int zzdp;
        private String zzdq;
        private String zzdr;
        private String zzds;
        private String zzdt;
        private String zzdu;
        private String zzdv;
        private String zzdw;
        private String zzdx;
        private String zzdy;
        private String zzdz;
        private String zzea;
        private boolean zzeb;
        private String zzec;
        private zzhz<String> zzed = zzhs.zzim();
        private boolean zzee;
        private String zzef;
        private String zzeg;
        private String zzeh;
        private String zzei;
        private long zzej;
        private String zzek;
        private boolean zzel;
        private String zzem;
        private String zzen;
        private String zzeo;
        private String zzep;
        private String zzeq;
        private String zzer;
        private zzhz<zzr> zzes;
        private int zzo;
        private String zzx;
        private String zzz;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzq, zza> implements zzje {
            private zza() {
                super(zzq.zzet);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzq() {
            String str = "";
            this.zzdq = str;
            this.zzx = str;
            this.zzaz = str;
            this.zzdr = str;
            this.zzds = str;
            this.zzdt = str;
            this.zzdu = str;
            this.zzdv = str;
            this.zzdw = str;
            this.zzct = str;
            this.zzdx = str;
            this.zzdy = str;
            this.zzz = str;
            this.zzdz = str;
            this.zzea = str;
            this.zzau = str;
            this.zzcj = str;
            this.zzaw = str;
            this.zzec = str;
            this.zzab = str;
            this.zzef = str;
            this.zzeg = str;
            this.zzeh = str;
            this.zzei = str;
            this.zzek = str;
            this.zzem = str;
            this.zzbb = str;
            this.zzen = str;
            this.zzeo = str;
            this.zzep = str;
            this.zzeq = str;
            this.zzdn = str;
            this.zzai = str;
            this.zzer = str;
            this.zzes = zzhs.zzim();
        }

        public final String getProviderId() {
            return this.zzx;
        }

        public final String getEmail() {
            return this.zzaz;
        }

        public final String zzam() {
            return this.zzct;
        }

        public final String getLocalId() {
            return this.zzau;
        }

        public final String getDisplayName() {
            return this.zzcj;
        }

        public final String getIdToken() {
            return this.zzaw;
        }

        public final boolean zzav() {
            return this.zzee;
        }

        public final String zzaw() {
            return this.zzeh;
        }

        public final boolean zzax() {
            return this.zzel;
        }

        public final String zzs() {
            return this.zzbb;
        }

        public final long zzt() {
            return this.zzbc;
        }

        public final String zzay() {
            return this.zzen;
        }

        public final String getRawUserInfo() {
            return this.zzep;
        }

        public final String getErrorMessage() {
            return this.zzeq;
        }

        public final boolean zzu() {
            return this.zzbd;
        }

        public final String zzaz() {
            return this.zzdn;
        }

        public final String zzba() {
            return this.zzai;
        }

        public final String zzbb() {
            return this.zzer;
        }

        public final List<zzr> zzbc() {
            return this.zzes;
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzq();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzet, "\u0001+\u0000\u0002\u0001-+\u0000\u0002\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004\u0007\u0003\u0005\b\u0004\u0006\b\u0005\u0007\b\u0006\b\b\u0007\t\b\b\n\b\t\u000b\b\n\f\b\u000b\r\b\f\u000e\b\r\u000f\b\u000e\u0010\b\u000f\u0011\b\u0010\u0012\u0007\u0011\u0013\b\u0012\u0014\b\u0013\u0015\b\u0014\u0017\b\u0015\u0018\u001a\u0019\u0007\u0016\u001a\b\u0017\u001b\b\u0018\u001c\b\u0019\u001d\b\u001a\u001e\u0002\u001b\u001f\b\u001c \u0007\u001d!\b\u001e\"\b\u001f#\u0002 $\b!%\b\"&\b#'\b$(\u0007%*\b&+\b',\b(-\u001b", new Object[]{"zzo", "zzdp", "zzdq", "zzx", "zzaz", "zzcm", "zzdr", "zzds", "zzdt", "zzdu", "zzdv", "zzdw", "zzct", "zzdx", "zzdy", "zzz", "zzdz", "zzea", "zzau", "zzeb", "zzcj", "zzaw", "zzec", "zzab", "zzed", "zzee", "zzef", "zzeg", "zzeh", "zzei", "zzej", "zzek", "zzel", "zzem", "zzbb", "zzbc", "zzen", "zzeo", "zzep", "zzeq", "zzbd", "zzdn", "zzai", "zzer", "zzes", zzr.class});
                case 4:
                    return zzet;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzq.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzet);
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

        public static zzjm<zzq> zzm() {
            return (zzjm) zzet.zza(com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaat, null, null);
        }

        static {
            zzhs.zza(zzq.class, zzet);
        }
    }

    public static final class zzr extends zzhs<zzr, zza> implements zzje {
        private static final zzr zzev = new zzr();
        private static volatile zzjm<zzr> zzs;
        private String zzai;
        private long zzav;
        private String zzcs;
        private boolean zzcw;
        private String zzeu;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzr, zza> implements zzje {
            private zza() {
                super(zzr.zzev);
            }

            public final zza zzbk(String str) {
                zzid();
                ((zzr) this.zzaah).zzbj(str);
                return this;
            }

            public final zza zzl(boolean z) {
                zzid();
                ((zzr) this.zzaah).zze(true);
                return this;
            }

            public final zza zzbl(String str) {
                zzid();
                ((zzr) this.zzaah).zzf(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzr() {
            String str = "";
            this.zzeu = str;
            this.zzcs = str;
            this.zzai = str;
        }

        private final void zzbj(String str) {
            if (str != null) {
                this.zzo |= 1;
                this.zzeu = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zze(boolean z) {
            this.zzo |= 4;
            this.zzcw = z;
        }

        private final void zzf(String str) {
            if (str != null) {
                this.zzo |= 16;
                this.zzai = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzbe() {
            return (zza) zzev.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzr();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzev, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\u0007\u0002\u0004\u0002\u0003\u0005\b\u0004", new Object[]{"zzo", "zzeu", "zzcs", "zzcw", "zzav", "zzai"});
                case 4:
                    return zzev;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzr.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzev);
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
            zzhs.zza(zzr.class, zzev);
        }
    }

    public static final class zzs extends zzhs<zzs, zza> implements zzje {
        private static final zzs zzew = new zzs();
        private static volatile zzjm<zzs> zzs;
        private String zzal;
        private byte zzas = (byte) 2;
        private String zzaw;
        private String zzbb;
        private long zzbc;
        private boolean zzbd;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzs, zza> implements zzje {
            private zza() {
                super(zzs.zzew);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzs() {
            String str = "";
            this.zzal = str;
            this.zzaw = str;
            this.zzbb = str;
        }

        public final String getIdToken() {
            return this.zzaw;
        }

        public final String zzs() {
            return this.zzbb;
        }

        public final long zzt() {
            return this.zzbc;
        }

        public final boolean zzu() {
            return this.zzbd;
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzs();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzew, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0001\u0001Ԉ\u0000\u0002\b\u0001\u0003\b\u0002\u0004\u0002\u0003\u0005\u0007\u0004", new Object[]{"zzo", "zzal", "zzaw", "zzbb", "zzbc", "zzbd"});
                case 4:
                    return zzew;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzs.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzew);
                                zzs = obj3;
                            }
                        }
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzas);
                case 7:
                    if (obj != null) {
                        i2 = 1;
                    }
                    this.zzas = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzjm<zzs> zzm() {
            return (zzjm) zzew.zza(com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaat, null, null);
        }

        static {
            zzhs.zza(zzs.class, zzew);
        }
    }

    public static final class zzt extends zzhs<zzt, zza> implements zzje {
        private static final zzt zzey = new zzt();
        private static volatile zzjm<zzt> zzs;
        private String zzai;
        private long zzaj;
        private long zzav;
        private String zzaw;
        private String zzaz;
        private String zzck;
        private String zzco;
        private String zzcp;
        private String zzcs;
        private boolean zzcw;
        private String zzdj;
        private String zzex;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzt, zza> implements zzje {
            private zza() {
                super(zzt.zzey);
            }

            public final zza zzbm(String str) {
                zzid();
                ((zzt) this.zzaah).zzn(str);
                return this;
            }

            public final zza zzbn(String str) {
                zzid();
                ((zzt) this.zzaah).setPassword(str);
                return this;
            }

            public final zza zzm(boolean z) {
                zzid();
                ((zzt) this.zzaah).zze(z);
                return this;
            }

            public final zza zzbo(String str) {
                zzid();
                ((zzt) this.zzaah).zzf(str);
                return this;
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzt() {
            String str = "";
            this.zzaz = str;
            this.zzck = str;
            this.zzdj = str;
            this.zzco = str;
            this.zzcp = str;
            this.zzex = str;
            this.zzcs = str;
            this.zzaw = str;
            this.zzai = str;
        }

        private final void zzn(String str) {
            if (str != null) {
                this.zzo |= 1;
                this.zzaz = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void setPassword(String str) {
            if (str != null) {
                this.zzo |= 2;
                this.zzck = str;
                return;
            }
            throw new NullPointerException();
        }

        private final void zze(boolean z) {
            this.zzo |= 512;
            this.zzcw = z;
        }

        private final void zzf(String str) {
            if (str != null) {
                this.zzo |= 1024;
                this.zzai = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzbh() {
            return (zza) zzey.zzij();
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzt();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzey, "\u0001\f\u0000\u0001\u0001\f\f\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\b\u0004\u0006\b\u0005\u0007\b\u0006\b\u0002\u0007\t\b\b\n\u0007\t\u000b\b\n\f\u0003\u000b", new Object[]{"zzo", "zzaz", "zzck", "zzdj", "zzco", "zzcp", "zzex", "zzcs", "zzav", "zzaw", "zzcw", "zzai", "zzaj"});
                case 4:
                    return zzey;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzt.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzey);
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
            zzhs.zza(zzt.class, zzey);
        }
    }

    public static final class zzu extends zzhs<zzu, zza> implements zzje {
        private static final zzu zzez = new zzu();
        private static volatile zzjm<zzu> zzs;
        private String zzal;
        private boolean zzao;
        private byte zzas = (byte) 2;
        private String zzau;
        private String zzaw;
        private String zzaz;
        private String zzbb;
        private long zzbc;
        private String zzcj;
        private String zzct;
        private String zzeh;
        private long zzej;
        private String zzek;
        private String zzer;
        private zzhz<zzr> zzes;
        private int zzo;

        public static final class zza extends com.google.android.gms.internal.firebase_auth.zzhs.zza<zzu, zza> implements zzje {
            private zza() {
                super(zzu.zzez);
            }

            /* synthetic */ zza(zzo zzo) {
                this();
            }
        }

        private zzu() {
            String str = "";
            this.zzal = str;
            this.zzau = str;
            this.zzaz = str;
            this.zzcj = str;
            this.zzaw = str;
            this.zzct = str;
            this.zzeh = str;
            this.zzek = str;
            this.zzbb = str;
            this.zzer = str;
            this.zzes = zzhs.zzim();
        }

        public final String getLocalId() {
            return this.zzau;
        }

        public final String getEmail() {
            return this.zzaz;
        }

        public final String getDisplayName() {
            return this.zzcj;
        }

        public final String getIdToken() {
            return this.zzaw;
        }

        public final String zzam() {
            return this.zzct;
        }

        public final String zzs() {
            return this.zzbb;
        }

        public final long zzt() {
            return this.zzbc;
        }

        public final String zzbb() {
            return this.zzer;
        }

        public final List<zzr> zzbc() {
            return this.zzes;
        }

        protected final Object zza(int i, Object obj, Object obj2) {
            int i2 = 0;
            switch (zzo.zzt[i - 1]) {
                case 1:
                    return new zzu();
                case 2:
                    return new zza();
                case 3:
                    return zzhs.zza(zzez, "\u0001\u000e\u0000\u0001\u0001\u000f\u000e\u0000\u0001\u0001\u0001Ԉ\u0000\u0002\b\u0001\u0003\b\u0002\u0004\b\u0003\u0005\b\u0004\u0006\u0007\u0005\u0007\b\u0006\b\b\u0007\t\u0002\b\n\b\t\u000b\b\n\f\u0002\u000b\u000e\b\f\u000f\u001b", new Object[]{"zzo", "zzal", "zzau", "zzaz", "zzcj", "zzaw", "zzao", "zzct", "zzeh", "zzej", "zzek", "zzbb", "zzbc", "zzer", "zzes", zzr.class});
                case 4:
                    return zzez;
                case 5:
                    Object obj3 = zzs;
                    if (obj3 == null) {
                        synchronized (zzu.class) {
                            obj3 = zzs;
                            if (obj3 == null) {
                                obj3 = new com.google.android.gms.internal.firebase_auth.zzhs.zzc(zzez);
                                zzs = obj3;
                            }
                        }
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzas);
                case 7:
                    if (obj != null) {
                        i2 = 1;
                    }
                    this.zzas = (byte) i2;
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static zzjm<zzu> zzm() {
            return (zzjm) zzez.zza(com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaat, null, null);
        }

        static {
            zzhs.zza(zzu.class, zzez);
        }
    }
}
