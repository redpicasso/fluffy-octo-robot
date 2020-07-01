package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class zzha extends zzgg {
    private static final Logger logger = Logger.getLogger(zzha.class.getName());
    private static final boolean zzww = zzkq.zzkr();
    zzhc zzwx = this;

    public static class zza extends IOException {
        zza() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zza(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        zza(String str, Throwable th) {
            str = String.valueOf(str);
            String str2 = "CodedOutputStream was writing to a flat byte array and ran out of space.: ";
            super(str.length() != 0 ? str2.concat(str) : new String(str2), th);
        }
    }

    static class zzb extends zzha {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zzb(byte[] bArr, int i, int i2) {
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

        public final void zze(int i, int i2) throws IOException {
            zzah((i << 3) | i2);
        }

        public final void zzf(int i, int i2) throws IOException {
            zze(i, 0);
            zzag(i2);
        }

        public final void zzg(int i, int i2) throws IOException {
            zze(i, 0);
            zzah(i2);
        }

        public final void zzi(int i, int i2) throws IOException {
            zze(i, 5);
            zzaj(i2);
        }

        public final void zza(int i, long j) throws IOException {
            zze(i, 0);
            zzb(j);
        }

        public final void zzc(int i, long j) throws IOException {
            zze(i, 1);
            zzd(j);
        }

        public final void zzc(int i, boolean z) throws IOException {
            zze(i, 0);
            zzc((byte) z);
        }

        public final void zza(int i, String str) throws IOException {
            zze(i, 2);
            zzdi(str);
        }

        public final void zza(int i, zzgf zzgf) throws IOException {
            zze(i, 2);
            zza(zzgf);
        }

        public final void zza(zzgf zzgf) throws IOException {
            zzah(zzgf.size());
            zzgf.zza((zzgg) this);
        }

        public final void zzd(byte[] bArr, int i, int i2) throws IOException {
            zzah(i2);
            write(bArr, 0, i2);
        }

        final void zza(int i, zzjc zzjc, zzjs zzjs) throws IOException {
            zze(i, 2);
            zzfx zzfx = (zzfx) zzjc;
            int zzfu = zzfx.zzfu();
            if (zzfu == -1) {
                zzfu = zzjs.zzq(zzfx);
                zzfx.zzl(zzfu);
            }
            zzah(zzfu);
            zzjs.zza(zzjc, this.zzwx);
        }

        public final void zza(int i, zzjc zzjc) throws IOException {
            zze(1, 3);
            zzg(2, i);
            zze(3, 2);
            zzc(zzjc);
            zze(1, 4);
        }

        public final void zzb(int i, zzgf zzgf) throws IOException {
            zze(1, 3);
            zzg(2, i);
            zza(3, zzgf);
            zze(1, 4);
        }

        public final void zzc(zzjc zzjc) throws IOException {
            zzah(zzjc.zzik());
            zzjc.zzb(this);
        }

        public final void zzc(byte b) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (Throwable e) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        public final void zzag(int i) throws IOException {
            if (i >= 0) {
                zzah(i);
            } else {
                zzb((long) i);
            }
        }

        public final void zzah(int i) throws IOException {
            byte[] bArr;
            int i2;
            if (!zzha.zzww || zzge.zzga() || zzhi() < 5) {
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
                } catch (Throwable e) {
                    throw new zza(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
                }
            } else if ((i & -128) == 0) {
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                zzkq.zza(bArr, (long) i2, (byte) i);
            } else {
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                zzkq.zza(bArr, (long) i2, (byte) (i | 128));
                i >>>= 7;
                if ((i & -128) == 0) {
                    bArr = this.buffer;
                    i2 = this.position;
                    this.position = i2 + 1;
                    zzkq.zza(bArr, (long) i2, (byte) i);
                    return;
                }
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                zzkq.zza(bArr, (long) i2, (byte) (i | 128));
                i >>>= 7;
                if ((i & -128) == 0) {
                    bArr = this.buffer;
                    i2 = this.position;
                    this.position = i2 + 1;
                    zzkq.zza(bArr, (long) i2, (byte) i);
                    return;
                }
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                zzkq.zza(bArr, (long) i2, (byte) (i | 128));
                i >>>= 7;
                if ((i & -128) == 0) {
                    bArr = this.buffer;
                    i2 = this.position;
                    this.position = i2 + 1;
                    zzkq.zza(bArr, (long) i2, (byte) i);
                    return;
                }
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                zzkq.zza(bArr, (long) i2, (byte) (i | 128));
                i >>>= 7;
                bArr = this.buffer;
                i2 = this.position;
                this.position = i2 + 1;
                zzkq.zza(bArr, (long) i2, (byte) i);
            }
        }

        public final void zzaj(int i) throws IOException {
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
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        public final void zzb(long j) throws IOException {
            byte[] bArr;
            int i;
            int i2;
            if (!zzha.zzww || zzhi() < 10) {
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
                    throw new zza(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
                }
            }
            while ((j & -128) != 0) {
                bArr = this.buffer;
                i = this.position;
                this.position = i + 1;
                zzkq.zza(bArr, (long) i, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            bArr = this.buffer;
            i2 = this.position;
            this.position = i2 + 1;
            zzkq.zza(bArr, (long) i2, (byte) ((int) j));
        }

        public final void zzd(long j) throws IOException {
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
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        private final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (Throwable e) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)}), e);
            }
        }

        public final void zzb(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        public final void zzdi(String str) throws IOException {
            int i = this.position;
            try {
                int zzam = zzha.zzam(str.length() * 3);
                int zzam2 = zzha.zzam(str.length());
                if (zzam2 == zzam) {
                    this.position = i + zzam2;
                    zzam = zzkt.zza(str, this.buffer, this.position, zzhi());
                    this.position = i;
                    zzah((zzam - i) - zzam2);
                    this.position = zzam;
                    return;
                }
                zzah(zzkt.zzb(str));
                this.position = zzkt.zza(str, this.buffer, this.position, zzhi());
            } catch (zzkw e) {
                this.position = i;
                zza(str, e);
            } catch (Throwable e2) {
                throw new zza(e2);
            }
        }

        public final int zzhi() {
            return this.limit - this.position;
        }
    }

    public static int zzam(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (i & -268435456) == 0 ? 4 : 5;
    }

    public static int zzao(int i) {
        return 4;
    }

    public static int zzap(int i) {
        return 4;
    }

    private static int zzar(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static int zzb(double d) {
        return 8;
    }

    public static int zzb(float f) {
        return 4;
    }

    public static zzha zzc(byte[] bArr) {
        return new zzb(bArr, 0, bArr.length);
    }

    public static int zzf(long j) {
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

    public static int zzh(long j) {
        return 8;
    }

    public static int zzi(long j) {
        return 8;
    }

    private static long zzj(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public static int zzu(boolean z) {
        return 1;
    }

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzgf zzgf) throws IOException;

    public abstract void zza(int i, zzjc zzjc) throws IOException;

    abstract void zza(int i, zzjc zzjc, zzjs zzjs) throws IOException;

    public abstract void zza(int i, String str) throws IOException;

    public abstract void zza(zzgf zzgf) throws IOException;

    public abstract void zzag(int i) throws IOException;

    public abstract void zzah(int i) throws IOException;

    public abstract void zzaj(int i) throws IOException;

    public abstract void zzb(int i, zzgf zzgf) throws IOException;

    public abstract void zzb(long j) throws IOException;

    public abstract void zzc(byte b) throws IOException;

    public abstract void zzc(int i, long j) throws IOException;

    public abstract void zzc(int i, boolean z) throws IOException;

    public abstract void zzc(zzjc zzjc) throws IOException;

    public abstract void zzd(long j) throws IOException;

    abstract void zzd(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zzdi(String str) throws IOException;

    public abstract void zze(int i, int i2) throws IOException;

    public abstract void zzf(int i, int i2) throws IOException;

    public abstract void zzg(int i, int i2) throws IOException;

    public abstract int zzhi();

    public abstract void zzi(int i, int i2) throws IOException;

    private zzha() {
    }

    public final void zzh(int i, int i2) throws IOException {
        zzg(i, zzar(i2));
    }

    public final void zzb(int i, long j) throws IOException {
        zza(i, zzj(j));
    }

    public final void zza(int i, float f) throws IOException {
        zzi(i, Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zzai(int i) throws IOException {
        zzah(zzar(i));
    }

    public final void zzc(long j) throws IOException {
        zzb(zzj(j));
    }

    public final void zza(float f) throws IOException {
        zzaj(Float.floatToRawIntBits(f));
    }

    public final void zza(double d) throws IOException {
        zzd(Double.doubleToRawLongBits(d));
    }

    public final void zzt(boolean z) throws IOException {
        zzc((byte) z);
    }

    public static int zzj(int i, int i2) {
        return zzak(i) + zzal(i2);
    }

    public static int zzk(int i, int i2) {
        return zzak(i) + zzam(i2);
    }

    public static int zzl(int i, int i2) {
        return zzak(i) + zzam(zzar(i2));
    }

    public static int zzm(int i, int i2) {
        return zzak(i) + 4;
    }

    public static int zzn(int i, int i2) {
        return zzak(i) + 4;
    }

    public static int zzd(int i, long j) {
        return zzak(i) + zzf(j);
    }

    public static int zze(int i, long j) {
        return zzak(i) + zzf(j);
    }

    public static int zzf(int i, long j) {
        return zzak(i) + zzf(zzj(j));
    }

    public static int zzg(int i, long j) {
        return zzak(i) + 8;
    }

    public static int zzh(int i, long j) {
        return zzak(i) + 8;
    }

    public static int zzb(int i, float f) {
        return zzak(i) + 4;
    }

    public static int zzb(int i, double d) {
        return zzak(i) + 8;
    }

    public static int zzd(int i, boolean z) {
        return zzak(i) + 1;
    }

    public static int zzo(int i, int i2) {
        return zzak(i) + zzal(i2);
    }

    public static int zzb(int i, String str) {
        return zzak(i) + zzdj(str);
    }

    public static int zzc(int i, zzgf zzgf) {
        i = zzak(i);
        int size = zzgf.size();
        return i + (zzam(size) + size);
    }

    public static int zza(int i, zzih zzih) {
        i = zzak(i);
        int zzik = zzih.zzik();
        return i + (zzam(zzik) + zzik);
    }

    static int zzb(int i, zzjc zzjc, zzjs zzjs) {
        return zzak(i) + zza(zzjc, zzjs);
    }

    public static int zzb(int i, zzjc zzjc) {
        return ((zzak(1) << 1) + zzk(2, i)) + (zzak(3) + zzd(zzjc));
    }

    public static int zzd(int i, zzgf zzgf) {
        return ((zzak(1) << 1) + zzk(2, i)) + zzc(3, zzgf);
    }

    public static int zzb(int i, zzih zzih) {
        return ((zzak(1) << 1) + zzk(2, i)) + zza(3, zzih);
    }

    public static int zzak(int i) {
        return zzam(i << 3);
    }

    public static int zzal(int i) {
        return i >= 0 ? zzam(i) : 10;
    }

    public static int zzan(int i) {
        return zzam(zzar(i));
    }

    public static int zze(long j) {
        return zzf(j);
    }

    public static int zzg(long j) {
        return zzf(zzj(j));
    }

    public static int zzaq(int i) {
        return zzal(i);
    }

    public static int zzdj(String str) {
        int zzb;
        try {
            zzb = zzkt.zzb(str);
        } catch (zzkw unused) {
            zzb = str.getBytes(zzht.UTF_8).length;
        }
        return zzam(zzb) + zzb;
    }

    public static int zza(zzih zzih) {
        int zzik = zzih.zzik();
        return zzam(zzik) + zzik;
    }

    public static int zzb(zzgf zzgf) {
        int size = zzgf.size();
        return zzam(size) + size;
    }

    public static int zzd(byte[] bArr) {
        int length = bArr.length;
        return zzam(length) + length;
    }

    public static int zzd(zzjc zzjc) {
        int zzik = zzjc.zzik();
        return zzam(zzik) + zzik;
    }

    static int zza(zzjc zzjc, zzjs zzjs) {
        zzfx zzfx = (zzfx) zzjc;
        int zzfu = zzfx.zzfu();
        if (zzfu == -1) {
            zzfu = zzjs.zzq(zzfx);
            zzfx.zzl(zzfu);
        }
        return zzam(zzfu) + zzfu;
    }

    public final void zzhj() {
        if (zzhi() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    final void zza(String str, zzkw zzkw) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", zzkw);
        byte[] bytes = str.getBytes(zzht.UTF_8);
        try {
            zzah(bytes.length);
            zzb(bytes, 0, bytes.length);
        } catch (Throwable e) {
            throw new zza(e);
        } catch (zza e2) {
            throw e2;
        }
    }

    @Deprecated
    static int zzc(int i, zzjc zzjc, zzjs zzjs) {
        i = zzak(i) << 1;
        zzfx zzfx = (zzfx) zzjc;
        int zzfu = zzfx.zzfu();
        if (zzfu == -1) {
            zzfu = zzjs.zzq(zzfx);
            zzfx.zzl(zzfu);
        }
        return i + zzfu;
    }

    @Deprecated
    public static int zze(zzjc zzjc) {
        return zzjc.zzik();
    }

    @Deprecated
    public static int zzas(int i) {
        return zzam(i);
    }

    /* synthetic */ zzha(zzgz zzgz) {
        this();
    }
}
