package com.google.android.gms.internal.firebase_auth;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class zzkq {
    private static final Logger logger = Logger.getLogger(zzkq.class.getName());
    private static final Unsafe zzacw = zzkt();
    private static final boolean zzaer = zzk(Long.TYPE);
    private static final boolean zzaes = zzk(Integer.TYPE);
    private static final zzd zzaet;
    private static final boolean zzaeu = zzkv();
    private static final long zzaev = ((long) zzi(byte[].class));
    private static final long zzaew = ((long) zzi(boolean[].class));
    private static final long zzaex = ((long) zzj(boolean[].class));
    private static final long zzaey = ((long) zzi(int[].class));
    private static final long zzaez = ((long) zzj(int[].class));
    private static final long zzafa = ((long) zzi(long[].class));
    private static final long zzafb = ((long) zzj(long[].class));
    private static final long zzafc = ((long) zzi(float[].class));
    private static final long zzafd = ((long) zzj(float[].class));
    private static final long zzafe = ((long) zzi(double[].class));
    private static final long zzaff = ((long) zzj(double[].class));
    private static final long zzafg = ((long) zzi(Object[].class));
    private static final long zzafh = ((long) zzj(Object[].class));
    private static final long zzafi;
    private static final int zzafj = ((int) (zzaev & 7));
    static final boolean zzafk = (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN);
    private static final Class<?> zzvt = zzge.zzgb();
    private static final boolean zzww = zzku();

    static abstract class zzd {
        Unsafe zzafn;

        zzd(Unsafe unsafe) {
            this.zzafn = unsafe;
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
            return this.zzafn.getInt(obj, j);
        }

        public final void zzb(Object obj, long j, int i) {
            this.zzafn.putInt(obj, j, i);
        }

        public final long zzl(Object obj, long j) {
            return this.zzafn.getLong(obj, j);
        }

        public final void zza(Object obj, long j, long j2) {
            this.zzafn.putLong(obj, j, j2);
        }
    }

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            if (zzkq.zzafk) {
                return zzkq.zzq(obj, j);
            }
            return zzkq.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzkq.zzafk) {
                zzkq.zza(obj, j, b);
            } else {
                zzkq.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzkq.zzafk) {
                return zzkq.zzs(obj, j);
            }
            return zzkq.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzkq.zzafk) {
                zzkq.zzb(obj, j, z);
            } else {
                zzkq.zzc(obj, j, z);
            }
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
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
            return this.zzafn.getByte(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            this.zzafn.putByte(obj, j, b);
        }

        public final boolean zzm(Object obj, long j) {
            return this.zzafn.getBoolean(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            this.zzafn.putBoolean(obj, j, z);
        }

        public final float zzn(Object obj, long j) {
            return this.zzafn.getFloat(obj, j);
        }

        public final void zza(Object obj, long j, float f) {
            this.zzafn.putFloat(obj, j, f);
        }

        public final double zzo(Object obj, long j) {
            return this.zzafn.getDouble(obj, j);
        }

        public final void zza(Object obj, long j, double d) {
            this.zzafn.putDouble(obj, j, d);
        }
    }

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            if (zzkq.zzafk) {
                return zzkq.zzq(obj, j);
            }
            return zzkq.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzkq.zzafk) {
                zzkq.zza(obj, j, b);
            } else {
                zzkq.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzkq.zzafk) {
                return zzkq.zzs(obj, j);
            }
            return zzkq.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzkq.zzafk) {
                zzkq.zzb(obj, j, z);
            } else {
                zzkq.zzc(obj, j, z);
            }
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }
    }

    private zzkq() {
    }

    static boolean zzkr() {
        return zzww;
    }

    static boolean zzks() {
        return zzaeu;
    }

    static <T> T zzh(Class<T> cls) {
        try {
            return zzacw.allocateInstance(cls);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    private static int zzi(Class<?> cls) {
        return zzww ? zzaet.zzafn.arrayBaseOffset(cls) : -1;
    }

    private static int zzj(Class<?> cls) {
        return zzww ? zzaet.zzafn.arrayIndexScale(cls) : -1;
    }

    static int zzk(Object obj, long j) {
        return zzaet.zzk(obj, j);
    }

    static void zzb(Object obj, long j, int i) {
        zzaet.zzb(obj, j, i);
    }

    static long zzl(Object obj, long j) {
        return zzaet.zzl(obj, j);
    }

    static void zza(Object obj, long j, long j2) {
        zzaet.zza(obj, j, j2);
    }

    static boolean zzm(Object obj, long j) {
        return zzaet.zzm(obj, j);
    }

    static void zza(Object obj, long j, boolean z) {
        zzaet.zza(obj, j, z);
    }

    static float zzn(Object obj, long j) {
        return zzaet.zzn(obj, j);
    }

    static void zza(Object obj, long j, float f) {
        zzaet.zza(obj, j, f);
    }

    static double zzo(Object obj, long j) {
        return zzaet.zzo(obj, j);
    }

    static void zza(Object obj, long j, double d) {
        zzaet.zza(obj, j, d);
    }

    static Object zzp(Object obj, long j) {
        return zzaet.zzafn.getObject(obj, j);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzaet.zzafn.putObject(obj, j, obj2);
    }

    static byte zza(byte[] bArr, long j) {
        return zzaet.zzy(bArr, zzaev + j);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzaet.zze(bArr, zzaev + j, b);
    }

    static Unsafe zzkt() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzks());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzku() {
        Unsafe unsafe = zzacw;
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
            if (zzge.zzga()) {
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

    private static boolean zzkv() {
        String str = "copyMemory";
        String str2 = "getLong";
        Unsafe unsafe = zzacw;
        if (unsafe == null) {
            return false;
        }
        try {
            Class cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod(str2, new Class[]{Object.class, Long.TYPE});
            if (zzkw() == null) {
                return false;
            }
            if (zzge.zzga()) {
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

    private static boolean zzk(Class<?> cls) {
        if (!zzge.zzga()) {
            return false;
        }
        try {
            Class cls2 = zzvt;
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

    private static Field zzkw() {
        Field zzb;
        if (zzge.zzga()) {
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
        zzb(obj, j2, i | (zzk(obj, j2) & (~(255 << i))));
    }

    private static void zzb(Object obj, long j, byte b) {
        long j2 = -4 & j;
        int i = (((int) j) & 3) << 3;
        zzb(obj, j2, ((255 & b) << i) | (zzk(obj, j2) & (~(255 << i))));
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

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00fc  */
    static {
        /*
        r0 = com.google.android.gms.internal.firebase_auth.zzkq.class;
        r0 = r0.getName();
        r0 = java.util.logging.Logger.getLogger(r0);
        logger = r0;
        r0 = zzkt();
        zzacw = r0;
        r0 = com.google.android.gms.internal.firebase_auth.zzge.zzgb();
        zzvt = r0;
        r0 = java.lang.Long.TYPE;
        r0 = zzk(r0);
        zzaer = r0;
        r0 = java.lang.Integer.TYPE;
        r0 = zzk(r0);
        zzaes = r0;
        r0 = zzacw;
        r1 = 0;
        if (r0 != 0) goto L_0x002e;
    L_0x002d:
        goto L_0x0053;
    L_0x002e:
        r0 = com.google.android.gms.internal.firebase_auth.zzge.zzga();
        if (r0 == 0) goto L_0x004c;
    L_0x0034:
        r0 = zzaer;
        if (r0 == 0) goto L_0x0040;
    L_0x0038:
        r1 = new com.google.android.gms.internal.firebase_auth.zzkq$zzc;
        r0 = zzacw;
        r1.<init>(r0);
        goto L_0x0053;
    L_0x0040:
        r0 = zzaes;
        if (r0 == 0) goto L_0x0053;
    L_0x0044:
        r1 = new com.google.android.gms.internal.firebase_auth.zzkq$zza;
        r0 = zzacw;
        r1.<init>(r0);
        goto L_0x0053;
    L_0x004c:
        r1 = new com.google.android.gms.internal.firebase_auth.zzkq$zzb;
        r0 = zzacw;
        r1.<init>(r0);
    L_0x0053:
        zzaet = r1;
        r0 = zzkv();
        zzaeu = r0;
        r0 = zzku();
        zzww = r0;
        r0 = byte[].class;
        r0 = zzi(r0);
        r0 = (long) r0;
        zzaev = r0;
        r0 = boolean[].class;
        r0 = zzi(r0);
        r0 = (long) r0;
        zzaew = r0;
        r0 = boolean[].class;
        r0 = zzj(r0);
        r0 = (long) r0;
        zzaex = r0;
        r0 = int[].class;
        r0 = zzi(r0);
        r0 = (long) r0;
        zzaey = r0;
        r0 = int[].class;
        r0 = zzj(r0);
        r0 = (long) r0;
        zzaez = r0;
        r0 = long[].class;
        r0 = zzi(r0);
        r0 = (long) r0;
        zzafa = r0;
        r0 = long[].class;
        r0 = zzj(r0);
        r0 = (long) r0;
        zzafb = r0;
        r0 = float[].class;
        r0 = zzi(r0);
        r0 = (long) r0;
        zzafc = r0;
        r0 = float[].class;
        r0 = zzj(r0);
        r0 = (long) r0;
        zzafd = r0;
        r0 = double[].class;
        r0 = zzi(r0);
        r0 = (long) r0;
        zzafe = r0;
        r0 = double[].class;
        r0 = zzj(r0);
        r0 = (long) r0;
        zzaff = r0;
        r0 = java.lang.Object[].class;
        r0 = zzi(r0);
        r0 = (long) r0;
        zzafg = r0;
        r0 = java.lang.Object[].class;
        r0 = zzj(r0);
        r0 = (long) r0;
        zzafh = r0;
        r0 = zzkw();
        if (r0 == 0) goto L_0x00e8;
    L_0x00dc:
        r1 = zzaet;
        if (r1 != 0) goto L_0x00e1;
    L_0x00e0:
        goto L_0x00e8;
    L_0x00e1:
        r1 = r1.zzafn;
        r0 = r1.objectFieldOffset(r0);
        goto L_0x00ea;
    L_0x00e8:
        r0 = -1;
    L_0x00ea:
        zzafi = r0;
        r0 = zzaev;
        r2 = 7;
        r0 = r0 & r2;
        r1 = (int) r0;
        zzafj = r1;
        r0 = java.nio.ByteOrder.nativeOrder();
        r1 = java.nio.ByteOrder.BIG_ENDIAN;
        if (r0 != r1) goto L_0x00fe;
    L_0x00fc:
        r0 = 1;
        goto L_0x00ff;
    L_0x00fe:
        r0 = 0;
    L_0x00ff:
        zzafk = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzkq.<clinit>():void");
    }
}
