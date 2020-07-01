package com.google.android.gms.internal.firebase_ml;

final class zztk extends zzti {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzbkv;
    private int zzbkw;
    private int zzbkx;
    private int zzbky;

    private zztk(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzbky = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i2 + i;
        this.pos = i;
        this.zzbkx = this.pos;
        this.zzbkv = z;
    }

    public final int zzco(int i) throws zzuo {
        if (i >= 0) {
            i += zzqd();
            int i2 = this.zzbky;
            if (i <= i2) {
                this.zzbky = i;
                this.limit += this.zzbkw;
                i = this.limit;
                int i3 = i - this.zzbkx;
                int i4 = this.zzbky;
                if (i3 > i4) {
                    this.zzbkw = i3 - i4;
                    this.limit = i - this.zzbkw;
                } else {
                    this.zzbkw = 0;
                }
                return i2;
            }
            throw zzuo.zzrm();
        }
        throw zzuo.zzrn();
    }

    public final int zzqd() {
        return this.pos - this.zzbkx;
    }
}
