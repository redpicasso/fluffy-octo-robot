package com.google.android.gms.internal.firebase_ml;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzsu extends zzsq<Boolean> implements zzun<Boolean>, zzwa, RandomAccess {
    private static final zzsu zzbkj;
    private int size;
    private boolean[] zzbkk;

    zzsu() {
        this(new boolean[10], 0);
    }

    private zzsu(boolean[] zArr, int i) {
        this.zzbkk = zArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzpu();
        if (i2 >= i) {
            Object obj = this.zzbkk;
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
        if (!(obj instanceof zzsu)) {
            return super.equals(obj);
        }
        zzsu zzsu = (zzsu) obj;
        if (this.size != zzsu.size) {
            return false;
        }
        boolean[] zArr = zzsu.zzbkk;
        for (int i = 0; i < this.size; i++) {
            if (this.zzbkk[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzug.zzaj(this.zzbkk[i2]);
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void addBoolean(boolean z) {
        zza(this.size, z);
    }

    private final void zza(int i, boolean z) {
        zzpu();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                Object obj = this.zzbkk;
                if (i2 < obj.length) {
                    System.arraycopy(obj, i, obj, i + 1, i2 - i);
                } else {
                    Object obj2 = new boolean[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(this.zzbkk, i, obj2, i + 1, this.size - i);
                    this.zzbkk = obj2;
                }
                this.zzbkk[i] = z;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzcj(i));
    }

    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzpu();
        zzug.checkNotNull(collection);
        if (!(collection instanceof zzsu)) {
            return super.addAll(collection);
        }
        zzsu zzsu = (zzsu) collection;
        int i = zzsu.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            i2 += i;
            boolean[] zArr = this.zzbkk;
            if (i2 > zArr.length) {
                this.zzbkk = Arrays.copyOf(zArr, i2);
            }
            System.arraycopy(zzsu.zzbkk, 0, this.zzbkk, this.size, zzsu.size);
            this.size = i2;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzpu();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Boolean.valueOf(this.zzbkk[i]))) {
                obj = this.zzbkk;
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
        boolean booleanValue = ((Boolean) obj).booleanValue();
        zzpu();
        zzci(i);
        boolean[] zArr = this.zzbkk;
        boolean z = zArr[i];
        zArr[i] = booleanValue;
        return Boolean.valueOf(z);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Boolean) obj).booleanValue());
    }

    public final /* synthetic */ zzun zzck(int i) {
        if (i >= this.size) {
            return new zzsu(Arrays.copyOf(this.zzbkk, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzci(i);
        return Boolean.valueOf(this.zzbkk[i]);
    }

    static {
        zzsq zzsu = new zzsu(new boolean[0], 0);
        zzbkj = zzsu;
        zzsu.zzpt();
    }
}
