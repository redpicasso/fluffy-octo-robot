package com.google.android.gms.internal.firebase_ml;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzvc extends zzsq<Long> implements zzun<Long>, zzwa, RandomAccess {
    private static final zzvc zzbqh;
    private int size;
    private long[] zzbqi;

    zzvc() {
        this(new long[10], 0);
    }

    private zzvc(long[] jArr, int i) {
        this.zzbqi = jArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzpu();
        if (i2 >= i) {
            Object obj = this.zzbqi;
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
        if (!(obj instanceof zzvc)) {
            return super.equals(obj);
        }
        zzvc zzvc = (zzvc) obj;
        if (this.size != zzvc.size) {
            return false;
        }
        long[] jArr = zzvc.zzbqi;
        for (int i = 0; i < this.size; i++) {
            if (this.zzbqi[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzug.zzz(this.zzbqi[i2]);
        }
        return i;
    }

    public final long getLong(int i) {
        zzci(i);
        return this.zzbqi[i];
    }

    public final int size() {
        return this.size;
    }

    public final void zzaa(long j) {
        zzk(this.size, j);
    }

    private final void zzk(int i, long j) {
        zzpu();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                Object obj = this.zzbqi;
                if (i2 < obj.length) {
                    System.arraycopy(obj, i, obj, i + 1, i2 - i);
                } else {
                    Object obj2 = new long[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(this.zzbqi, i, obj2, i + 1, this.size - i);
                    this.zzbqi = obj2;
                }
                this.zzbqi[i] = j;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzcj(i));
    }

    public final boolean addAll(Collection<? extends Long> collection) {
        zzpu();
        zzug.checkNotNull(collection);
        if (!(collection instanceof zzvc)) {
            return super.addAll(collection);
        }
        zzvc zzvc = (zzvc) collection;
        int i = zzvc.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            i2 += i;
            long[] jArr = this.zzbqi;
            if (i2 > jArr.length) {
                this.zzbqi = Arrays.copyOf(jArr, i2);
            }
            System.arraycopy(zzvc.zzbqi, 0, this.zzbqi, this.size, zzvc.size);
            this.size = i2;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzpu();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Long.valueOf(this.zzbqi[i]))) {
                obj = this.zzbqi;
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
        long longValue = ((Long) obj).longValue();
        zzpu();
        zzci(i);
        long[] jArr = this.zzbqi;
        long j = jArr[i];
        jArr[i] = longValue;
        return Long.valueOf(j);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzk(i, ((Long) obj).longValue());
    }

    public final /* synthetic */ zzun zzck(int i) {
        if (i >= this.size) {
            return new zzvc(Arrays.copyOf(this.zzbqi, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        return Long.valueOf(getLong(i));
    }

    static {
        zzsq zzvc = new zzvc(new long[0], 0);
        zzbqh = zzvc;
        zzvc.zzpt();
    }
}
