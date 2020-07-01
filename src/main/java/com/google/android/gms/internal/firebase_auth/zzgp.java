package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.nio.charset.Charset;

class zzgp extends zzgq {
    protected final byte[] zzwd;

    zzgp(byte[] bArr) {
        if (bArr != null) {
            this.zzwd = bArr;
            return;
        }
        throw new NullPointerException();
    }

    protected int zzgf() {
        return 0;
    }

    public byte zzp(int i) {
        return this.zzwd[i];
    }

    byte zzq(int i) {
        return this.zzwd[i];
    }

    public int size() {
        return this.zzwd.length;
    }

    public final zzgf zzd(int i, int i2) {
        i = zzgf.zzc(0, i2, size());
        if (i == 0) {
            return zzgf.zzvv;
        }
        return new zzgm(this.zzwd, zzgf(), i);
    }

    final void zza(zzgg zzgg) throws IOException {
        zzgg.zzb(this.zzwd, zzgf(), size());
    }

    protected final String zza(Charset charset) {
        return new String(this.zzwd, zzgf(), size(), charset);
    }

    public final boolean zzgd() {
        int zzgf = zzgf();
        return zzkt.zze(this.zzwd, zzgf, size() + zzgf);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgf) || size() != ((zzgf) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzgp)) {
            return obj.equals(this);
        }
        zzgp zzgp = (zzgp) obj;
        int zzge = zzge();
        int zzge2 = zzgp.zzge();
        if (zzge == 0 || zzge2 == 0 || zzge == zzge2) {
            return zza(zzgp, 0, size());
        }
        return false;
    }

    final boolean zza(zzgf zzgf, int i, int i2) {
        StringBuilder stringBuilder;
        int size;
        if (i2 > zzgf.size()) {
            i = size();
            stringBuilder = new StringBuilder(40);
            stringBuilder.append("Length too large: ");
            stringBuilder.append(i2);
            stringBuilder.append(i);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (i2 > zzgf.size()) {
            size = zzgf.size();
            stringBuilder = new StringBuilder(59);
            stringBuilder.append("Ran off end of other: 0, ");
            stringBuilder.append(i2);
            stringBuilder.append(", ");
            stringBuilder.append(size);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (!(zzgf instanceof zzgp)) {
            return zzgf.zzd(0, i2).equals(zzd(0, i2));
        } else {
            zzgp zzgp = (zzgp) zzgf;
            byte[] bArr = this.zzwd;
            byte[] bArr2 = zzgp.zzwd;
            int zzgf2 = zzgf() + i2;
            i2 = zzgf();
            size = zzgp.zzgf();
            while (i2 < zzgf2) {
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
        return zzht.zza(i, this.zzwd, zzgf(), i3);
    }
}
