package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;

public abstract class zzsw implements Serializable, Iterable<Byte> {
    public static final zzsw zzbkl = new zztg(zzug.zzbpe);
    private static final zztc zzbkm = (zzsr.zzpv() ? new zzth() : new zzta());
    private static final Comparator<zzsw> zzbkn = new zzsy();
    private int zzadu = 0;

    zzsw() {
    }

    private static int zzb(byte b) {
        return b & 255;
    }

    public abstract boolean equals(Object obj);

    public abstract int size();

    abstract void zza(zzsv zzsv) throws IOException;

    protected abstract int zzb(int i, int i2, int i3);

    protected abstract String zzb(Charset charset);

    public abstract byte zzcl(int i);

    abstract byte zzcm(int i);

    public abstract zzsw zzf(int i, int i2);

    public abstract boolean zzpy();

    public static zzsw zzc(byte[] bArr, int i, int i2) {
        zzc(i, i + i2, bArr.length);
        return new zztg(zzbkm.zzd(bArr, i, i2));
    }

    public static zzsw zzcn(String str) {
        return new zztg(str.getBytes(zzug.UTF_8));
    }

    public final String zzpx() {
        return size() == 0 ? "" : zzb(zzug.UTF_8);
    }

    public final int hashCode() {
        int i = this.zzadu;
        if (i == 0) {
            i = size();
            i = zzb(i, 0, i);
            if (i == 0) {
                i = 1;
            }
            this.zzadu = i;
        }
        return i;
    }

    static zzte zzcn(int i) {
        return new zzte(i, null);
    }

    protected final int zzpz() {
        return this.zzadu;
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
        return new zzsx(this);
    }
}
