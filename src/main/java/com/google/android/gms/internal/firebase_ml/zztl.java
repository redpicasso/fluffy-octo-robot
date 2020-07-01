package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class zztl extends zzsv {
    private static final Logger logger = Logger.getLogger(zztl.class.getName());
    private static final boolean zzbkz = zzxc.zztj();
    zztn zzbla = this;

    public static class zzb extends IOException {
        zzb() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zzb(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        zzb(String str, Throwable th) {
            str = String.valueOf(str);
            String str2 = "CodedOutputStream was writing to a flat byte array and ran out of space.: ";
            super(str.length() != 0 ? str2.concat(str) : new String(str2), th);
        }
    }

    static class zza extends zztl {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zza(byte[] bArr, int i, int i2) {
            super();
            if (bArr != null) {
                int i3 = i2 + 0;
                if (((i2 | 0) | (bArr.length - i3)) >= 0) {
                    this.buffer = bArr;
                    this.offset = 0;
                    this.position = 0;
                    this.limit = i3;
                    return;
                }
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(0), Integer.valueOf(i2)}));
            }
            throw new NullPointerException("buffer");
        }

        public final void zzg(int i, int i2) throws IOException {
            zzcr((i << 3) | i2);
        }

        public final void zzh(int i, int i2) throws IOException {
            zzg(i, 0);
            zzcq(i2);
        }

        public final void zzi(int i, int i2) throws IOException {
            zzg(i, 0);
            zzcr(i2);
        }

        public final void zzk(int i, int i2) throws IOException {
            zzg(i, 5);
            zzct(i2);
        }

        public final void zza(int i, long j) throws IOException {
            zzg(i, 0);
            zzq(j);
        }

        public final void zzc(int i, long j) throws IOException {
            zzg(i, 1);
            zzs(j);
        }

        public final void zzb(int i, boolean z) throws IOException {
            zzg(i, 0);
            zzd((byte) z);
        }

        public final void zzb(int i, String str) throws IOException {
            zzg(i, 2);
            zzco(str);
        }

        public final void zza(int i, zzsw zzsw) throws IOException {
            zzg(i, 2);
            zza(zzsw);
        }

        public final void zza(zzsw zzsw) throws IOException {
            zzcr(zzsw.size());
            zzsw.zza(this);
        }

        public final void zze(byte[] bArr, int i, int i2) throws IOException {
            zzcr(i2);
            write(bArr, 0, i2);
        }

        final void zza(int i, zzvo zzvo, zzwe zzwe) throws IOException {
            zzg(i, 2);
            zzsn zzsn = (zzsn) zzvo;
            int zzpq = zzsn.zzpq();
            if (zzpq == -1) {
                zzpq = zzwe.zzaa(zzsn);
                zzsn.zzch(zzpq);
            }
            zzcr(zzpq);
            zzwe.zza(zzvo, this.zzbla);
        }

        public final void zza(int i, zzvo zzvo) throws IOException {
            zzg(1, 3);
            zzi(2, i);
            zzg(3, 2);
            zzb(zzvo);
            zzg(1, 4);
        }

        public final void zzb(int i, zzsw zzsw) throws IOException {
            zzg(1, 3);
            zzi(2, i);
            zza(3, zzsw);
            zzg(1, 4);
        }

        public final void zzb(zzvo zzvo) throws IOException {
            zzcr(zzvo.zzqy());
            zzvo.zzb(this);
        }

        public final void zzd(byte b) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (Throwable e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        public final void zzcq(int i) throws IOException {
            if (i >= 0) {
                zzcr(i);
            } else {
                zzq((long) i);
            }
        }

        public final void zzcr(int i) throws IOException {
            byte[] bArr;
            int i2;
            if (!zztl.zzbkz || zzqe() < 10) {
                while ((i & -128) != 0) {
                    bArr = this.buffer;
                    i2 = this.position;
                    this.position = i2 + 1;
                    bArr[i2] = (byte) ((i & 127) | 128);
                    i >>>= 7;
                }
                try {
                    bArr = this.buffer;
                    i2 = this.position;
                    this.position = i2 + 1;
                    bArr[i2] = (byte) i;
                    return;
                } catch (Throwable e) {
                    throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
                }
            }
            while ((i & -128) != 0) {
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                zzxc.zza(bArr, (long) i2, (byte) ((i & 127) | 128));
                i >>>= 7;
            }
            bArr = this.buffer;
            i2 = this.position;
            this.position = i2 + 1;
            zzxc.zza(bArr, (long) i2, (byte) i);
        }

        public final void zzct(int i) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) i;
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) (i >> 8);
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) (i >> 16);
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) (i >>> 24);
            } catch (Throwable e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        public final void zzq(long j) throws IOException {
            byte[] bArr;
            int i;
            int i2;
            if (!zztl.zzbkz || zzqe() < 10) {
                while ((j & -128) != 0) {
                    bArr = this.buffer;
                    i = this.position;
                    this.position = i + 1;
                    bArr[i] = (byte) ((((int) j) & 127) | 128);
                    j >>>= 7;
                }
                try {
                    bArr = this.buffer;
                    i2 = this.position;
                    this.position = i2 + 1;
                    bArr[i2] = (byte) ((int) j);
                    return;
                } catch (Throwable e) {
                    throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
                }
            }
            while ((j & -128) != 0) {
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                zzxc.zza(bArr, (long) i, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            bArr = this.buffer;
            i2 = this.position;
            this.position = i2 + 1;
            zzxc.zza(bArr, (long) i2, (byte) ((int) j));
        }

        public final void zzs(long j) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) j);
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 8));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 16));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 24));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 32));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 40));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 48));
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) (j >> 56));
            } catch (Throwable e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        private final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (Throwable e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)}), e);
            }
        }

        public final void zzb(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        public final void zzco(String str) throws IOException {
            int i = this.position;
            try {
                int zzcw = zztl.zzcw(str.length() * 3);
                int zzcw2 = zztl.zzcw(str.length());
                if (zzcw2 == zzcw) {
                    this.position = i + zzcw2;
                    zzcw = zzxe.zza(str, this.buffer, this.position, zzqe());
                    this.position = i;
                    zzcr((zzcw - i) - zzcw2);
                    this.position = zzcw;
                    return;
                }
                zzcr(zzxe.zzb(str));
                this.position = zzxe.zza(str, this.buffer, this.position, zzqe());
            } catch (zzxi e) {
                this.position = i;
                zza(str, e);
            } catch (Throwable e2) {
                throw new zzb(e2);
            }
        }

        public final int zzqe() {
            return this.limit - this.position;
        }
    }

    public static int zzai(boolean z) {
        return 1;
    }

    public static int zzcw(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (i & -268435456) == 0 ? 4 : 5;
    }

    public static int zzcy(int i) {
        return 4;
    }

    public static int zzcz(int i) {
        return 4;
    }

    public static int zzd(double d) {
        return 8;
    }

    private static int zzdc(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static zztl zzg(byte[] bArr) {
        return new zza(bArr, 0, bArr.length);
    }

    public static int zzu(float f) {
        return 4;
    }

    public static int zzu(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        int i;
        if ((-34359738368L & j) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if ((-2097152 & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        if ((j & -16384) != 0) {
            i++;
        }
        return i;
    }

    public static int zzw(long j) {
        return 8;
    }

    public static int zzx(long j) {
        return 8;
    }

    private static long zzy(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzsw zzsw) throws IOException;

    public abstract void zza(int i, zzvo zzvo) throws IOException;

    abstract void zza(int i, zzvo zzvo, zzwe zzwe) throws IOException;

    public abstract void zza(zzsw zzsw) throws IOException;

    public abstract void zzb(int i, zzsw zzsw) throws IOException;

    public abstract void zzb(int i, String str) throws IOException;

    public abstract void zzb(int i, boolean z) throws IOException;

    public abstract void zzb(zzvo zzvo) throws IOException;

    public abstract void zzc(int i, long j) throws IOException;

    public abstract void zzco(String str) throws IOException;

    public abstract void zzcq(int i) throws IOException;

    public abstract void zzcr(int i) throws IOException;

    public abstract void zzct(int i) throws IOException;

    public abstract void zzd(byte b) throws IOException;

    abstract void zze(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zzg(int i, int i2) throws IOException;

    public abstract void zzh(int i, int i2) throws IOException;

    public abstract void zzi(int i, int i2) throws IOException;

    public abstract void zzk(int i, int i2) throws IOException;

    public abstract void zzq(long j) throws IOException;

    public abstract int zzqe();

    public abstract void zzs(long j) throws IOException;

    private zztl() {
    }

    public final void zzj(int i, int i2) throws IOException {
        zzi(i, zzdc(i2));
    }

    public final void zzb(int i, long j) throws IOException {
        zza(i, zzy(j));
    }

    public final void zza(int i, float f) throws IOException {
        zzk(i, Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zzcs(int i) throws IOException {
        zzcr(zzdc(i));
    }

    public final void zzr(long j) throws IOException {
        zzq(zzy(j));
    }

    public final void zzt(float f) throws IOException {
        zzct(Float.floatToRawIntBits(f));
    }

    public final void zzc(double d) throws IOException {
        zzs(Double.doubleToRawLongBits(d));
    }

    public final void zzah(boolean z) throws IOException {
        zzd((byte) z);
    }

    public static int zzl(int i, int i2) {
        return zzcu(i) + zzcv(i2);
    }

    public static int zzm(int i, int i2) {
        return zzcu(i) + zzcw(i2);
    }

    public static int zzn(int i, int i2) {
        return zzcu(i) + zzcw(zzdc(i2));
    }

    public static int zzo(int i, int i2) {
        return zzcu(i) + 4;
    }

    public static int zzp(int i, int i2) {
        return zzcu(i) + 4;
    }

    public static int zzd(int i, long j) {
        return zzcu(i) + zzu(j);
    }

    public static int zze(int i, long j) {
        return zzcu(i) + zzu(j);
    }

    public static int zzf(int i, long j) {
        return zzcu(i) + zzu(zzy(j));
    }

    public static int zzg(int i, long j) {
        return zzcu(i) + 8;
    }

    public static int zzh(int i, long j) {
        return zzcu(i) + 8;
    }

    public static int zzb(int i, float f) {
        return zzcu(i) + 4;
    }

    public static int zzb(int i, double d) {
        return zzcu(i) + 8;
    }

    public static int zzc(int i, boolean z) {
        return zzcu(i) + 1;
    }

    public static int zzq(int i, int i2) {
        return zzcu(i) + zzcv(i2);
    }

    public static int zzc(int i, String str) {
        return zzcu(i) + zzcp(str);
    }

    public static int zzc(int i, zzsw zzsw) {
        i = zzcu(i);
        int size = zzsw.size();
        return i + (zzcw(size) + size);
    }

    public static int zza(int i, zzuv zzuv) {
        i = zzcu(i);
        int zzqy = zzuv.zzqy();
        return i + (zzcw(zzqy) + zzqy);
    }

    static int zzb(int i, zzvo zzvo, zzwe zzwe) {
        return zzcu(i) + zza(zzvo, zzwe);
    }

    public static int zzb(int i, zzvo zzvo) {
        return ((zzcu(1) << 1) + zzm(2, i)) + (zzcu(3) + zzc(zzvo));
    }

    public static int zzd(int i, zzsw zzsw) {
        return ((zzcu(1) << 1) + zzm(2, i)) + zzc(3, zzsw);
    }

    public static int zzb(int i, zzuv zzuv) {
        return ((zzcu(1) << 1) + zzm(2, i)) + zza(3, zzuv);
    }

    public static int zzcu(int i) {
        return zzcw(i << 3);
    }

    public static int zzcv(int i) {
        return i >= 0 ? zzcw(i) : 10;
    }

    public static int zzcx(int i) {
        return zzcw(zzdc(i));
    }

    public static int zzt(long j) {
        return zzu(j);
    }

    public static int zzv(long j) {
        return zzu(zzy(j));
    }

    public static int zzda(int i) {
        return zzcv(i);
    }

    public static int zzcp(String str) {
        int zzb;
        try {
            zzb = zzxe.zzb(str);
        } catch (zzxi unused) {
            zzb = str.getBytes(zzug.UTF_8).length;
        }
        return zzcw(zzb) + zzb;
    }

    public static int zza(zzuv zzuv) {
        int zzqy = zzuv.zzqy();
        return zzcw(zzqy) + zzqy;
    }

    public static int zzb(zzsw zzsw) {
        int size = zzsw.size();
        return zzcw(size) + size;
    }

    public static int zzh(byte[] bArr) {
        int length = bArr.length;
        return zzcw(length) + length;
    }

    public static int zzc(zzvo zzvo) {
        int zzqy = zzvo.zzqy();
        return zzcw(zzqy) + zzqy;
    }

    static int zza(zzvo zzvo, zzwe zzwe) {
        zzsn zzsn = (zzsn) zzvo;
        int zzpq = zzsn.zzpq();
        if (zzpq == -1) {
            zzpq = zzwe.zzaa(zzsn);
            zzsn.zzch(zzpq);
        }
        return zzcw(zzpq) + zzpq;
    }

    static int zzdb(int i) {
        return zzcw(i) + i;
    }

    public final void zzqf() {
        if (zzqe() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    final void zza(String str, zzxi zzxi) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", zzxi);
        byte[] bytes = str.getBytes(zzug.UTF_8);
        try {
            zzcr(bytes.length);
            zzb(bytes, 0, bytes.length);
        } catch (Throwable e) {
            throw new zzb(e);
        } catch (zzb e2) {
            throw e2;
        }
    }

    @Deprecated
    static int zzc(int i, zzvo zzvo, zzwe zzwe) {
        i = zzcu(i) << 1;
        zzsn zzsn = (zzsn) zzvo;
        int zzpq = zzsn.zzpq();
        if (zzpq == -1) {
            zzpq = zzwe.zzaa(zzsn);
            zzsn.zzch(zzpq);
        }
        return i + zzpq;
    }

    @Deprecated
    public static int zzd(zzvo zzvo) {
        return zzvo.zzqy();
    }

    @Deprecated
    public static int zzdd(int i) {
        return zzcw(i);
    }

    /* synthetic */ zztl(zztm zztm) {
        this();
    }
}
