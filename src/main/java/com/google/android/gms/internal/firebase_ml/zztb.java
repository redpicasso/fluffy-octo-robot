package com.google.android.gms.internal.firebase_ml;

final class zztb extends zztg {
    private final int zzbkp;
    private final int zzbkq;

    zztb(byte[] bArr, int i, int i2) {
        super(bArr);
        zzsw.zzc(i, i + i2, bArr.length);
        this.zzbkp = i;
        this.zzbkq = i2;
    }

    public final byte zzcl(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.bytes[this.zzbkp + i];
        }
        if (i < 0) {
            StringBuilder stringBuilder = new StringBuilder(22);
            stringBuilder.append("Index < 0: ");
            stringBuilder.append(i);
            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder(40);
        stringBuilder2.append("Index > length: ");
        stringBuilder2.append(i);
        stringBuilder2.append(", ");
        stringBuilder2.append(size);
        throw new ArrayIndexOutOfBoundsException(stringBuilder2.toString());
    }

    final byte zzcm(int i) {
        return this.bytes[this.zzbkp + i];
    }

    public final int size() {
        return this.zzbkq;
    }

    protected final int zzqa() {
        return this.zzbkp;
    }
}
