package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzeh extends zzdj<Double> implements zzff<Double>, zzgu, RandomAccess {
    private static final zzeh zzaeo;
    private int size;
    private double[] zzaep;

    zzeh() {
        this(new double[10], 0);
    }

    private zzeh(double[] dArr, int i) {
        this.zzaep = dArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzrz();
        if (i2 >= i) {
            Object obj = this.zzaep;
            System.arraycopy(obj, i2, obj, i, this.size - i2);
            this.size -= i2 - i;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzeh)) {
            return super.equals(obj);
        }
        zzeh zzeh = (zzeh) obj;
        if (this.size != zzeh.size) {
            return false;
        }
        double[] dArr = zzeh.zzaep;
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(this.zzaep[i]) != Double.doubleToLongBits(dArr[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzez.zzbx(Double.doubleToLongBits(this.zzaep[i2]));
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void zzf(double d) {
        zzc(this.size, d);
    }

    private final void zzc(int i, double d) {
        zzrz();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                Object obj = this.zzaep;
                if (i2 < obj.length) {
                    System.arraycopy(obj, i, obj, i + 1, i2 - i);
                } else {
                    Object obj2 = new double[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(this.zzaep, i, obj2, i + 1, this.size - i);
                    this.zzaep = obj2;
                }
                this.zzaep[i] = d;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzao(i));
    }

    public final boolean addAll(Collection<? extends Double> collection) {
        zzrz();
        zzez.checkNotNull(collection);
        if (!(collection instanceof zzeh)) {
            return super.addAll(collection);
        }
        zzeh zzeh = (zzeh) collection;
        int i = zzeh.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            i2 += i;
            double[] dArr = this.zzaep;
            if (i2 > dArr.length) {
                this.zzaep = Arrays.copyOf(dArr, i2);
            }
            System.arraycopy(zzeh.zzaep, 0, this.zzaep, this.size, zzeh.size);
            this.size = i2;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzrz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zzaep[i]))) {
                obj = this.zzaep;
                System.arraycopy(obj, i + 1, obj, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzan(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzao(i));
        }
    }

    private final String zzao(int i) {
        int i2 = this.size;
        StringBuilder stringBuilder = new StringBuilder(35);
        stringBuilder.append("Index:");
        stringBuilder.append(i);
        stringBuilder.append(", Size:");
        stringBuilder.append(i2);
        return stringBuilder.toString();
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        double doubleValue = ((Double) obj).doubleValue();
        zzrz();
        zzan(i);
        double[] dArr = this.zzaep;
        double d = dArr[i];
        dArr[i] = doubleValue;
        return Double.valueOf(d);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Double) obj).doubleValue());
    }

    public final /* synthetic */ zzff zzap(int i) {
        if (i >= this.size) {
            return new zzeh(Arrays.copyOf(this.zzaep, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzan(i);
        return Double.valueOf(this.zzaep[i]);
    }

    static {
        zzdj zzeh = new zzeh(new double[0], 0);
        zzaeo = zzeh;
        zzeh.zzry();
    }
}
