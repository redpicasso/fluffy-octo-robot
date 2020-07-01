package com.google.android.gms.internal.firebase_ml;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzuc extends zzsq<Float> implements zzun<Float>, zzwa, RandomAccess {
    private static final zzuc zzboe;
    private int size;
    private float[] zzbof;

    public static zzuc zzqv() {
        return zzboe;
    }

    zzuc() {
        this(new float[10], 0);
    }

    private zzuc(float[] fArr, int i) {
        this.zzbof = fArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzpu();
        if (i2 >= i) {
            Object obj = this.zzbof;
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
        if (!(obj instanceof zzuc)) {
            return super.equals(obj);
        }
        zzuc zzuc = (zzuc) obj;
        if (this.size != zzuc.size) {
            return false;
        }
        float[] fArr = zzuc.zzbof;
        for (int i = 0; i < this.size; i++) {
            if (Float.floatToIntBits(this.zzbof[i]) != Float.floatToIntBits(fArr[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + Float.floatToIntBits(this.zzbof[i2]);
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void zzv(float f) {
        zzc(this.size, f);
    }

    private final void zzc(int i, float f) {
        zzpu();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                Object obj = this.zzbof;
                if (i2 < obj.length) {
                    System.arraycopy(obj, i, obj, i + 1, i2 - i);
                } else {
                    Object obj2 = new float[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(this.zzbof, i, obj2, i + 1, this.size - i);
                    this.zzbof = obj2;
                }
                this.zzbof[i] = f;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzcj(i));
    }

    public final boolean addAll(Collection<? extends Float> collection) {
        zzpu();
        zzug.checkNotNull(collection);
        if (!(collection instanceof zzuc)) {
            return super.addAll(collection);
        }
        zzuc zzuc = (zzuc) collection;
        int i = zzuc.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            i2 += i;
            float[] fArr = this.zzbof;
            if (i2 > fArr.length) {
                this.zzbof = Arrays.copyOf(fArr, i2);
            }
            System.arraycopy(zzuc.zzbof, 0, this.zzbof, this.size, zzuc.size);
            this.size = i2;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzpu();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Float.valueOf(this.zzbof[i]))) {
                obj = this.zzbof;
                System.arraycopy(obj, i + 1, obj, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzci(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzcj(i));
        }
    }

    private final String zzcj(int i) {
        int i2 = this.size;
        StringBuilder stringBuilder = new StringBuilder(35);
        stringBuilder.append("Index:");
        stringBuilder.append(i);
        stringBuilder.append(", Size:");
        stringBuilder.append(i2);
        return stringBuilder.toString();
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        float floatValue = ((Float) obj).floatValue();
        zzpu();
        zzci(i);
        float[] fArr = this.zzbof;
        float f = fArr[i];
        fArr[i] = floatValue;
        return Float.valueOf(f);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Float) obj).floatValue());
    }

    public final /* synthetic */ zzun zzck(int i) {
        if (i >= this.size) {
            return new zzuc(Arrays.copyOf(this.zzbof, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzci(i);
        return Float.valueOf(this.zzbof[i]);
    }

    static {
        zzsq zzuc = new zzuc(new float[0], 0);
        zzboe = zzuc;
        zzuc.zzpt();
    }
}
