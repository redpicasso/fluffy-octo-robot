package com.google.android.gms.internal.vision;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzfh extends zzef<Double> implements zzge<Double>, zzhr, RandomAccess {
    private static final zzfh zztc;
    private int size;
    private double[] zztd;

    zzfh() {
        this(new double[10], 0);
    }

    private zzfh(double[] dArr, int i) {
        this.zztd = dArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzcj();
        if (i2 >= i) {
            Object obj = this.zztd;
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
        if (!(obj instanceof zzfh)) {
            return super.equals(obj);
        }
        zzfh zzfh = (zzfh) obj;
        if (this.size != zzfh.size) {
            return false;
        }
        double[] dArr = zzfh.zztd;
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(this.zztd[i]) != Double.doubleToLongBits(dArr[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzga.zzo(Double.doubleToLongBits(this.zztd[i2]));
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void zzc(double d) {
        zzc(this.size, d);
    }

    private final void zzc(int i, double d) {
        zzcj();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                Object obj = this.zztd;
                if (i2 < obj.length) {
                    System.arraycopy(obj, i, obj, i + 1, i2 - i);
                } else {
                    Object obj2 = new double[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(this.zztd, i, obj2, i + 1, this.size - i);
                    this.zztd = obj2;
                }
                this.zztd[i] = d;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzag(i));
    }

    public final boolean addAll(Collection<? extends Double> collection) {
        zzcj();
        zzga.checkNotNull(collection);
        if (!(collection instanceof zzfh)) {
            return super.addAll(collection);
        }
        zzfh zzfh = (zzfh) collection;
        int i = zzfh.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            i2 += i;
            double[] dArr = this.zztd;
            if (i2 > dArr.length) {
                this.zztd = Arrays.copyOf(dArr, i2);
            }
            System.arraycopy(zzfh.zztd, 0, this.zztd, this.size, zzfh.size);
            this.size = i2;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzcj();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zztd[i]))) {
                obj = this.zztd;
                System.arraycopy(obj, i + 1, obj, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzaf(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzag(i));
        }
    }

    private final String zzag(int i) {
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
        zzcj();
        zzaf(i);
        double[] dArr = this.zztd;
        double d = dArr[i];
        dArr[i] = doubleValue;
        return Double.valueOf(d);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Double) obj).doubleValue());
    }

    public final /* synthetic */ zzge zzah(int i) {
        if (i >= this.size) {
            return new zzfh(Arrays.copyOf(this.zztd, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzaf(i);
        return Double.valueOf(this.zztd[i]);
    }

    static {
        zzef zzfh = new zzfh();
        zztc = zzfh;
        zzfh.zzci();
    }
}
