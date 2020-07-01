package com.google.android.gms.internal.firebase_auth;

final class zzgm extends zzgp {
    private final int zzwa;
    private final int zzwb;

    zzgm(byte[] bArr, int i, int i2) {
        super(bArr);
        zzgf.zzc(i, i + i2, bArr.length);
        this.zzwa = i;
        this.zzwb = i2;
    }

    public final byte zzp(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzwd[this.zzwa + i];
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

    final byte zzq(int i) {
        return this.zzwd[this.zzwa + i];
    }

    public final int size() {
        return this.zzwb;
    }

    protected final int zzgf() {
        return this.zzwa;
    }
}
