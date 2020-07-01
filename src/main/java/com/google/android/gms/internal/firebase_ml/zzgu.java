package com.google.android.gms.internal.firebase_ml;

public abstract class zzgu {
    @Deprecated
    private final byte zzxq;
    protected final byte zzxr;
    private final int zzxs;
    private final int zzxt;
    protected final int zzxu;
    private final int zzxv;

    protected zzgu(int i, int i2, int i3, int i4) {
        this(3, 4, i3, i4, (byte) 61);
    }

    abstract void zza(byte[] bArr, int i, int i2, zzgv zzgv);

    protected abstract boolean zza(byte b);

    private zzgu(int i, int i2, int i3, int i4, byte b) {
        this.zzxq = (byte) 61;
        this.zzxs = 3;
        this.zzxt = 4;
        int i5 = 0;
        Object obj = (i3 <= 0 || i4 <= 0) ? null : 1;
        if (obj != null) {
            i5 = (i3 / 4) << 2;
        }
        this.zzxu = i5;
        this.zzxv = i4;
        this.zzxr = (byte) 61;
    }

    protected final byte[] zza(int i, zzgv zzgv) {
        if (zzgv.buffer != null && zzgv.buffer.length >= zzgv.pos + i) {
            return zzgv.buffer;
        }
        if (zzgv.buffer == null) {
            zzgv.buffer = new byte[8192];
            zzgv.pos = 0;
            zzgv.zzxx = 0;
        } else {
            Object obj = new byte[(zzgv.buffer.length << 1)];
            System.arraycopy(zzgv.buffer, 0, obj, 0, zzgv.buffer.length);
            zzgv.buffer = obj;
        }
        return zzgv.buffer;
    }

    public final long zzc(byte[] bArr) {
        int length = bArr.length;
        int i = this.zzxs;
        long j = ((long) (((length + i) - 1) / i)) * ((long) this.zzxt);
        length = this.zzxu;
        return length > 0 ? j + ((((((long) length) + j) - 1) / ((long) length)) * ((long) this.zzxv)) : j;
    }
}
