package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.nio.charset.Charset;

class zztg extends zztf {
    protected final byte[] bytes;

    zztg(byte[] bArr) {
        if (bArr != null) {
            this.bytes = bArr;
            return;
        }
        throw new NullPointerException();
    }

    protected int zzqa() {
        return 0;
    }

    public byte zzcl(int i) {
        return this.bytes[i];
    }

    byte zzcm(int i) {
        return this.bytes[i];
    }

    public int size() {
        return this.bytes.length;
    }

    public final zzsw zzf(int i, int i2) {
        i = zzsw.zzc(0, i2, size());
        if (i == 0) {
            return zzsw.zzbkl;
        }
        return new zztb(this.bytes, zzqa(), i);
    }

    final void zza(zzsv zzsv) throws IOException {
        zzsv.zzb(this.bytes, zzqa(), size());
    }

    protected final String zzb(Charset charset) {
        return new String(this.bytes, zzqa(), size(), charset);
    }

    public final boolean zzpy() {
        int zzqa = zzqa();
        return zzxe.zzf(this.bytes, zzqa, size() + zzqa);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzsw) || size() != ((zzsw) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zztg)) {
            return obj.equals(this);
        }
        zztg zztg = (zztg) obj;
        int zzpz = zzpz();
        int zzpz2 = zztg.zzpz();
        if (zzpz == 0 || zzpz2 == 0 || zzpz == zzpz2) {
            return zza(zztg, 0, size());
        }
        return false;
    }

    final boolean zza(zzsw zzsw, int i, int i2) {
        StringBuilder stringBuilder;
        int size;
        if (i2 > zzsw.size()) {
            i = size();
            stringBuilder = new StringBuilder(40);
            stringBuilder.append("Length too large: ");
            stringBuilder.append(i2);
            stringBuilder.append(i);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (i2 > zzsw.size()) {
            size = zzsw.size();
            stringBuilder = new StringBuilder(59);
            stringBuilder.append("Ran off end of other: 0, ");
            stringBuilder.append(i2);
            stringBuilder.append(", ");
            stringBuilder.append(size);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (!(zzsw instanceof zztg)) {
            return zzsw.zzf(0, i2).equals(zzf(0, i2));
        } else {
            zztg zztg = (zztg) zzsw;
            byte[] bArr = this.bytes;
            byte[] bArr2 = zztg.bytes;
            int zzqa = zzqa() + i2;
            i2 = zzqa();
            size = zztg.zzqa();
            while (i2 < zzqa) {
                if (bArr[i2] != bArr2[size]) {
                    return false;
                }
                i2++;
                size++;
            }
            return true;
        }
    }

    protected final int zzb(int i, int i2, int i3) {
        return zzug.zza(i, this.bytes, zzqa(), i3);
    }
}
