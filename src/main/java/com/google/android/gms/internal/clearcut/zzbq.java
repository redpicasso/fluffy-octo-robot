package com.google.android.gms.internal.clearcut;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzbq extends zzav<Double> implements zzcn<Double>, RandomAccess {
    private static final zzbq zzgj;
    private int size;
    private double[] zzgk;

    static {
        zzav zzbq = new zzbq();
        zzgj = zzbq;
        zzbq.zzv();
    }

    zzbq() {
        this(new double[10], 0);
    }

    private zzbq(double[] dArr, int i) {
        this.zzgk = dArr;
        this.size = i;
    }

    private final void zzc(int i, double d) {
        zzw();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                Object obj = this.zzgk;
                if (i2 < obj.length) {
                    System.arraycopy(obj, i, obj, i + 1, i2 - i);
                } else {
                    Object obj2 = new double[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(this.zzgk, i, obj2, i + 1, this.size - i);
                    this.zzgk = obj2;
                }
                this.zzgk[i] = d;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzh(i));
    }

    private final void zzg(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzh(i));
        }
    }

    private final String zzh(int i) {
        int i2 = this.size;
        StringBuilder stringBuilder = new StringBuilder(35);
        stringBuilder.append("Index:");
        stringBuilder.append(i);
        stringBuilder.append(", Size:");
        stringBuilder.append(i2);
        return stringBuilder.toString();
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Double) obj).doubleValue());
    }

    public final boolean addAll(Collection<? extends Double> collection) {
        zzw();
        zzci.checkNotNull(collection);
        if (!(collection instanceof zzbq)) {
            return super.addAll(collection);
        }
        zzbq zzbq = (zzbq) collection;
        int i = zzbq.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            i2 += i;
            double[] dArr = this.zzgk;
            if (i2 > dArr.length) {
                this.zzgk = Arrays.copyOf(dArr, i2);
            }
            System.arraycopy(zzbq.zzgk, 0, this.zzgk, this.size, zzbq.size);
            this.size = i2;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbq)) {
            return super.equals(obj);
        }
        zzbq zzbq = (zzbq) obj;
        if (this.size != zzbq.size) {
            return false;
        }
        double[] dArr = zzbq.zzgk;
        for (int i = 0; i < this.size; i++) {
            if (this.zzgk[i] != dArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object get(int i) {
        zzg(i);
        return Double.valueOf(this.zzgk[i]);
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzci.zzl(Double.doubleToLongBits(this.zzgk[i2]));
        }
        return i;
    }

    public final boolean remove(Object obj) {
        zzw();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zzgk[i]))) {
                obj = this.zzgk;
                System.arraycopy(obj, i + 1, obj, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    protected final void removeRange(int i, int i2) {
        zzw();
        if (i2 >= i) {
            Object obj = this.zzgk;
            System.arraycopy(obj, i2, obj, i, this.size - i2);
            this.size -= i2 - i;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        double doubleValue = ((Double) obj).doubleValue();
        zzw();
        zzg(i);
        double[] dArr = this.zzgk;
        double d = dArr[i];
        dArr[i] = doubleValue;
        return Double.valueOf(d);
    }

    public final int size() {
        return this.size;
    }

    public final void zzc(double d) {
        zzc(this.size, d);
    }

    public final /* synthetic */ zzcn zzi(int i) {
        if (i >= this.size) {
            return new zzbq(Arrays.copyOf(this.zzgk, i), this.size);
        }
        throw new IllegalArgumentException();
    }
}
