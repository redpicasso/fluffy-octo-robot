package com.google.android.gms.internal.firebase_ml;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class zzxc {
    private static final Logger logger = Logger.getLogger(zzxc.class.getName());
    private static final Class<?> zzbke = zzsr.zzpw();
    private static final boolean zzbkz = zztm();
    private static final Unsafe zzbqw = zztl();
    private static final boolean zzbst = zzp(Long.TYPE);
    private static final boolean zzbsu = zzp(Integer.TYPE);
    private static final zzd zzbsv;
    private static final boolean zzbsw = zztn();
    private static final long zzbsx = ((long) zzn(byte[].class));
    private static final long zzbsy = ((long) zzn(boolean[].class));
    private static final long zzbsz = ((long) zzo(boolean[].class));
    private static final long zzbta = ((long) zzn(int[].class));
    private static final long zzbtb = ((long) zzo(int[].class));
    private static final long zzbtc = ((long) zzn(long[].class));
    private static final long zzbtd = ((long) zzo(long[].class));
    private static final long zzbte = ((long) zzn(float[].class));
    private static final long zzbtf = ((long) zzo(float[].class));
    private static final long zzbtg = ((long) zzn(double[].class));
    private static final long zzbth = ((long) zzo(double[].class));
    private static final long zzbti = ((long) zzn(Object[].class));
    private static final long zzbtj = ((long) zzo(Object[].class));
    private static final long zzbtk;
    private static final boolean zzbtl = (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN);

    static abstract class zzd {
        Unsafe zzbtm;

        zzd(Unsafe unsafe) {
            this.zzbtm = unsafe;
        }

        public abstract void zza(Object obj, long j, double d);

        public abstract void zza(Object obj, long j, float f);

        public abstract void zza(Object obj, long j, boolean z);

        public abstract void zze(Object obj, long j, byte b);

        public abstract boolean zzm(Object obj, long j);

        public abstract float zzn(Object obj, long j);

        public abstract double zzo(Object obj, long j);

        public abstract byte zzy(Object obj, long j);

        public final int zzk(Object obj, long j) {
            return this.zzbtm.getInt(obj, j);
        }

        public final void zza(Object obj, long j, int i) {
            this.zzbtm.putInt(obj, j, i);
        }

        public final long zzl(Object obj, long j) {
            return this.zzbtm.getLong(obj, j);
        }

        public final void zza(Object obj, long j, long j2) {
            this.zzbtm.putLong(obj, j, j2);
        }
    }

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            if (zzxc.zzbtl) {
                return zzxc.zzq(obj, j);
            }
            return zzxc.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzxc.zzbtl) {
                zzxc.zza(obj, j, b);
            } else {
                zzxc.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzxc.zzbtl) {
                return zzxc.zzs(obj, j);
            }
            return zzxc.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzxc.zzbtl) {
                zzxc.zzb(obj, j, z);
            } else {
                zzxc.zzc(obj, j, z);
            }
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zza(obj, j, Float.floatToIntBits(f));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }
    }

    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            if (zzxc.zzbtl) {
                return zzxc.zzq(obj, j);
            }
            return zzxc.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzxc.zzbtl) {
                zzxc.zza(obj, j, b);
            } else {
                zzxc.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzxc.zzbtl) {
                return zzxc.zzs(obj, j);
            }
            return zzxc.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzxc.zzbtl) {
                zzxc.zzb(obj, j, z);
            } else {
                zzxc.zzc(obj, j, z);
            }
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zza(obj, j, Float.floatToIntBits(f));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }
    }

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            return this.zzbtm.getByte(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            this.zzbtm.putByte(obj, j, b);
        }

        public final boolean zzm(Object obj, long j) {
            return this.zzbtm.getBoolean(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            this.zzbtm.putBoolean(obj, j, z);
        }

        public final float zzn(Object obj, long j) {
            return this.zzbtm.getFloat(obj, j);
        }

        public final void zza(Object obj, long j, float f) {
            this.zzbtm.putFloat(obj, j, f);
        }

        public final double zzo(Object obj, long j) {
            return this.zzbtm.getDouble(obj, j);
        }

        public final void zza(Object obj, long j, double d) {
            this.zzbtm.putDouble(obj, j, d);
        }
    }

    private zzxc() {
    }

    static boolean zztj() {
        return zzbkz;
    }

    static boolean zztk() {
        return zzbsw;
    }

    static <T> T zzm(Class<T> cls) {
        try {
            return zzbqw.allocateInstance(cls);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    private static int zzn(Class<?> cls) {
        return zzbkz ? zzbsv.zzbtm.arrayBaseOffset(cls) : -1;
    }

    private static int zzo(Class<?> cls) {
        return zzbkz ? zzbsv.zzbtm.arrayIndexScale(cls) : -1;
    }

    static int zzk(Object obj, long j) {
        return zzbsv.zzk(obj, j);
    }

    static void zza(Object obj, long j, int i) {
        zzbsv.zza(obj, j, i);
    }

    static long zzl(Object obj, long j) {
        return zzbsv.zzl(obj, j);
    }

    static void zza(Object obj, long j, long j2) {
        zzbsv.zza(obj, j, j2);
    }

    static boolean zzm(Object obj, long j) {
        return zzbsv.zzm(obj, j);
    }

    static void zza(Object obj, long j, boolean z) {
        zzbsv.zza(obj, j, z);
    }

    static float zzn(Object obj, long j) {
        return zzbsv.zzn(obj, j);
    }

    static void zza(Object obj, long j, float f) {
        zzbsv.zza(obj, j, f);
    }

    static double zzo(Object obj, long j) {
        return zzbsv.zzo(obj, j);
    }

    static void zza(Object obj, long j, double d) {
        zzbsv.zza(obj, j, d);
    }

    static Object zzp(Object obj, long j) {
        return zzbsv.zzbtm.getObject(obj, j);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzbsv.zzbtm.putObject(obj, j, obj2);
    }

    static byte zza(byte[] bArr, long j) {
        return zzbsv.zzy(bArr, zzbsx + j);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzbsv.zze(bArr, zzbsx + j, b);
    }

    static Unsafe zztl() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzxd());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zztm() {
        Unsafe unsafe = zzbqw;
        if (unsafe == null) {
            return false;
        }
        try {
            Class cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("arrayBaseOffset", new Class[]{Class.class});
            cls.getMethod("arrayIndexScale", new Class[]{Class.class});
            cls.getMethod("getInt", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putInt", new Class[]{Object.class, Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putLong", new Class[]{Object.class, Long.TYPE, Long.TYPE});
            cls.getMethod("getObject", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putObject", new Class[]{Object.class, Long.TYPE, Object.class});
            if (zzsr.zzpv()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putByte", new Class[]{Object.class, Long.TYPE, Byte.TYPE});
            cls.getMethod("getBoolean", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putBoolean", new Class[]{Object.class, Long.TYPE, Boolean.TYPE});
            cls.getMethod("getFloat", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putFloat", new Class[]{Object.class, Long.TYPE, Float.TYPE});
            cls.getMethod("getDouble", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putDouble", new Class[]{Object.class, Long.TYPE, Double.TYPE});
            return true;
        } catch (Throwable th) {
            Logger logger = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 71);
            stringBuilder.append("platform method missing - proto runtime falling back to safer methods: ");
            stringBuilder.append(valueOf);
            logger.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", stringBuilder.toString());
            return false;
        }
    }

    private static boolean zztn() {
        String str = "copyMemory";
        String str2 = "getLong";
        Unsafe unsafe = zzbqw;
        if (unsafe == null) {
            return false;
        }
        try {
            Class cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod(str2, new Class[]{Object.class, Long.TYPE});
            if (zzto() == null) {
                return false;
            }
            if (zzsr.zzpv()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Long.TYPE});
            cls.getMethod("putByte", new Class[]{Long.TYPE, Byte.TYPE});
            cls.getMethod("getInt", new Class[]{Long.TYPE});
            cls.getMethod("putInt", new Class[]{Long.TYPE, Integer.TYPE});
            cls.getMethod(str2, new Class[]{Long.TYPE});
            cls.getMethod("putLong", new Class[]{Long.TYPE, Long.TYPE});
            cls.getMethod(str, new Class[]{Long.TYPE, Long.TYPE, Long.TYPE});
            cls.getMethod(str, new Class[]{Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE});
            return true;
        } catch (Throwable th) {
            Logger logger = logger;
            Level level = Level.WARNING;
            str = String.valueOf(th);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 71);
            stringBuilder.append("platform method missing - proto runtime falling back to safer methods: ");
            stringBuilder.append(str);
            logger.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", stringBuilder.toString());
            return false;
        }
    }

    private static boolean zzp(Class<?> cls) {
        if (!zzsr.zzpv()) {
            return false;
        }
        try {
            Class cls2 = zzbke;
            cls2.getMethod("peekLong", new Class[]{cls, Boolean.TYPE});
            cls2.getMethod("pokeLong", new Class[]{cls, Long.TYPE, Boolean.TYPE});
            cls2.getMethod("pokeInt", new Class[]{cls, Integer.TYPE, Boolean.TYPE});
            cls2.getMethod("peekInt", new Class[]{cls, Boolean.TYPE});
            cls2.getMethod("pokeByte", new Class[]{cls, Byte.TYPE});
            cls2.getMethod("peekByte", new Class[]{cls});
            cls2.getMethod("pokeByteArray", new Class[]{cls, byte[].class, Integer.TYPE, Integer.TYPE});
            cls2.getMethod("peekByteArray", new Class[]{cls, byte[].class, Integer.TYPE, Integer.TYPE});
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static Field zzto() {
        Field zzb;
        if (zzsr.zzpv()) {
            zzb = zzb(Buffer.class, "effectiveDirectAddress");
            if (zzb != null) {
                return zzb;
            }
        }
        zzb = zzb(Buffer.class, "address");
        return (zzb == null || zzb.getType() != Long.TYPE) ? null : zzb;
    }

    private static Field zzb(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static byte zzq(Object obj, long j) {
        return (byte) (zzk(obj, -4 & j) >>> ((int) (((~j) & 3) << 3)));
    }

    private static byte zzr(Object obj, long j) {
        return (byte) (zzk(obj, -4 & j) >>> ((int) ((j & 3) << 3)));
    }

    private static void zza(Object obj, long j, byte b) {
        long j2 = -4 & j;
        int i = ((~((int) j)) & 3) << 3;
        i = (255 & b) << i;
        zza(obj, j2, i | (zzk(obj, j2) & (~(255 << i))));
    }

    private static void zzb(Object obj, long j, byte b) {
        long j2 = -4 & j;
        int i = (((int) j) & 3) << 3;
        zza(obj, j2, ((255 & b) << i) | (zzk(obj, j2) & (~(255 << i))));
    }

    private static boolean zzs(Object obj, long j) {
        return zzq(obj, j) != (byte) 0;
    }

    private static boolean zzt(Object obj, long j) {
        return zzr(obj, j) != (byte) 0;
    }

    private static void zzb(Object obj, long j, boolean z) {
        zza(obj, j, (byte) z);
    }

    private static void zzc(Object obj, long j, boolean z) {
        zzb(obj, j, (byte) z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00f4  */
    static {
        /*
        r0 = com.google.android.gms.internal.firebase_ml.zzxc.class;
        r0 = r0.getName();
        r0 = java.util.logging.Logger.getLogger(r0);
        logger = r0;
        r0 = zztl();
        zzbqw = r0;
        r0 = com.google.android.gms.internal.firebase_ml.zzsr.zzpw();
        zzbke = r0;
        r0 = java.lang.Long.TYPE;
        r0 = zzp(r0);
        zzbst = r0;
        r0 = java.lang.Integer.TYPE;
        r0 = zzp(r0);
        zzbsu = r0;
        r0 = zzbqw;
        r1 = 0;
        if (r0 != 0) goto L_0x002e;
    L_0x002d:
        goto L_0x0053;
    L_0x002e:
        r0 = com.google.android.gms.internal.firebase_ml.zzsr.zzpv();
        if (r0 == 0) goto L_0x004c;
    L_0x0034:
        r0 = zzbst;
        if (r0 == 0) goto L_0x0040;
    L_0x0038:
        r1 = new com.google.android.gms.internal.firebase_ml.zzxc$zzb;
        r0 = zzbqw;
        r1.<init>(r0);
        goto L_0x0053;
    L_0x0040:
        r0 = zzbsu;
        if (r0 == 0) goto L_0x0053;
    L_0x0044:
        r1 = new com.google.android.gms.internal.firebase_ml.zzxc$zza;
        r0 = zzbqw;
        r1.<init>(r0);
        goto L_0x0053;
    L_0x004c:
        r1 = new com.google.android.gms.internal.firebase_ml.zzxc$zzc;
        r0 = zzbqw;
        r1.<init>(r0);
    L_0x0053:
        zzbsv = r1;
        r0 = zztn();
        zzbsw = r0;
        r0 = zztm();
        zzbkz = r0;
        r0 = byte[].class;
        r0 = zzn(r0);
        r0 = (long) r0;
        zzbsx = r0;
        r0 = boolean[].class;
        r0 = zzn(r0);
        r0 = (long) r0;
        zzbsy = r0;
        r0 = boolean[].class;
        r0 = zzo(r0);
        r0 = (long) r0;
        zzbsz = r0;
        r0 = int[].class;
        r0 = zzn(r0);
        r0 = (long) r0;
        zzbta = r0;
        r0 = int[].class;
        r0 = zzo(r0);
        r0 = (long) r0;
        zzbtb = r0;
        r0 = long[].class;
        r0 = zzn(r0);
        r0 = (long) r0;
        zzbtc = r0;
        r0 = long[].class;
        r0 = zzo(r0);
        r0 = (long) r0;
        zzbtd = r0;
        r0 = float[].class;
        r0 = zzn(r0);
        r0 = (long) r0;
        zzbte = r0;
        r0 = float[].class;
        r0 = zzo(r0);
        r0 = (long) r0;
        zzbtf = r0;
        r0 = double[].class;
        r0 = zzn(r0);
        r0 = (long) r0;
        zzbtg = r0;
        r0 = double[].class;
        r0 = zzo(r0);
        r0 = (long) r0;
        zzbth = r0;
        r0 = java.lang.Object[].class;
        r0 = zzn(r0);
        r0 = (long) r0;
        zzbti = r0;
        r0 = java.lang.Object[].class;
        r0 = zzo(r0);
        r0 = (long) r0;
        zzbtj = r0;
        r0 = zzto();
        if (r0 == 0) goto L_0x00e8;
    L_0x00dc:
        r1 = zzbsv;
        if (r1 != 0) goto L_0x00e1;
    L_0x00e0:
        goto L_0x00e8;
    L_0x00e1:
        r1 = r1.zzbtm;
        r0 = r1.objectFieldOffset(r0);
        goto L_0x00ea;
    L_0x00e8:
        r0 = -1;
    L_0x00ea:
        zzbtk = r0;
        r0 = java.nio.ByteOrder.nativeOrder();
        r1 = java.nio.ByteOrder.BIG_ENDIAN;
        if (r0 != r1) goto L_0x00f6;
    L_0x00f4:
        r0 = 1;
        goto L_0x00f7;
    L_0x00f6:
        r0 = 0;
    L_0x00f7:
        zzbtl = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzxc.<clinit>():void");
    }
}
