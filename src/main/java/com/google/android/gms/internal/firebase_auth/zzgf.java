package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;

public abstract class zzgf implements Serializable, Iterable<Byte> {
    public static final zzgf zzvv = new zzgp(zzht.EMPTY_BYTE_ARRAY);
    private static final zzgl zzvw = (zzge.zzga() ? new zzgs() : new zzgj());
    private static final Comparator<zzgf> zzvy = new zzgh();
    private int zzvx = 0;

    zzgf() {
    }

    private static int zza(byte b) {
        return b & 255;
    }

    public abstract boolean equals(Object obj);

    public abstract int size();

    protected abstract String zza(Charset charset);

    abstract void zza(zzgg zzgg) throws IOException;

    protected abstract int zzb(int i, int i2, int i3);

    public abstract zzgf zzd(int i, int i2);

    public abstract boolean zzgd();

    public abstract byte zzp(int i);

    abstract byte zzq(int i);

    public static zzgf zza(byte[] bArr, int i, int i2) {
        zzc(i, i + i2, bArr.length);
        return new zzgp(zzvw.zzc(bArr, i, i2));
    }

    public static zzgf zza(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    static zzgf zzb(byte[] bArr) {
        return new zzgp(bArr);
    }

    public static zzgf zzdh(String str) {
        return new zzgp(str.getBytes(zzht.UTF_8));
    }

    public final String zzgc() {
        return size() == 0 ? "" : zza(zzht.UTF_8);
    }

    public final int hashCode() {
        int i = this.zzvx;
        if (i == 0) {
            i = size();
            i = zzb(i, 0, i);
            if (i == 0) {
                i = 1;
            }
            this.zzvx = i;
        }
        return i;
    }

    static zzgn zzr(int i) {
        return new zzgn(i, null);
    }

    protected final int zzge() {
        return this.zzvx;
    }

    static int zzc(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((((i | i2) | i4) | (i3 - i2)) >= 0) {
            return i4;
        }
        StringBuilder stringBuilder;
        if (i < 0) {
            StringBuilder stringBuilder2 = new StringBuilder(32);
            stringBuilder2.append("Beginning index: ");
            stringBuilder2.append(i);
            stringBuilder2.append(" < 0");
            throw new IndexOutOfBoundsException(stringBuilder2.toString());
        } else if (i2 < i) {
            stringBuilder = new StringBuilder(66);
            stringBuilder.append("Beginning index larger than ending index: ");
            stringBuilder.append(i);
            stringBuilder.append(", ");
            stringBuilder.append(i2);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else {
            stringBuilder = new StringBuilder(37);
            stringBuilder.append("End index: ");
            stringBuilder.append(i2);
            stringBuilder.append(" >= ");
            stringBuilder.append(i3);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }

    public /* synthetic */ Iterator iterator() {
        return new zzgi(this);
    }
}
