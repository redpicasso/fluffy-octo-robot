package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zziq extends zzgb<Long> implements zzhz<Long>, zzjl, RandomAccess {
    private static final zziq zzach;
    private int size;
    private long[] zzaci;

    zziq() {
        this(new long[10], 0);
    }

    private zziq(long[] jArr, int i) {
        this.zzaci = jArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzfz();
        if (i2 >= i) {
            Object obj = this.zzaci;
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
        if (!(obj instanceof zziq)) {
            return super.equals(obj);
        }
        zziq zziq = (zziq) obj;
        if (this.size != zziq.size) {
            return false;
        }
        long[] jArr = zziq.zzaci;
        for (int i = 0; i < this.size; i++) {
            if (this.zzaci[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzht.zzk(this.zzaci[i2]);
        }
        return i;
    }

    public final long getLong(int i) {
        zzm(i);
        return this.zzaci[i];
    }

    public final int size() {
        return this.size;
    }

    public final void zzl(long j) {
        zzk(this.size, j);
    }

    private final void zzk(int i, long j) {
        zzfz();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                Object obj = this.zzaci;
                if (i2 < obj.length) {
                    System.arraycopy(obj, i, obj, i + 1, i2 - i);
                } else {
                    Object obj2 = new long[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(this.zzaci, i, obj2, i + 1, this.size - i);
                    this.zzaci = obj2;
                }
                this.zzaci[i] = j;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzn(i));
    }

    public final boolean addAll(Collection<? extends Long> collection) {
        zzfz();
        zzht.checkNotNull(collection);
        if (!(collection instanceof zziq)) {
            return super.addAll(collection);
        }
        zziq zziq = (zziq) collection;
        int i = zziq.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            i2 += i;
            long[] jArr = this.zzaci;
            if (i2 > jArr.length) {
                this.zzaci = Arrays.copyOf(jArr, i2);
            }
            System.arraycopy(zziq.zzaci, 0, this.zzaci, this.size, zziq.size);
            this.size = i2;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzfz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Long.valueOf(this.zzaci[i]))) {
                obj = this.zzaci;
                System.arraycopy(obj, i + 1, obj, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzm(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzn(i));
        }
    }

    private final String zzn(int i) {
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
        zzfz();
        zzm(i);
        long[] jArr = this.zzaci;
        long j = jArr[i];
        jArr[i] = longValue;
        return Long.valueOf(j);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzk(i, ((Long) obj).longValue());
    }

    public final /* synthetic */ zzhz zzo(int i) {
        if (i >= this.size) {
            return new zziq(Arrays.copyOf(this.zzaci, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        return Long.valueOf(getLong(i));
    }

    static {
        zzgb zziq = new zziq(new long[0], 0);
        zzach = zziq;
        zziq.zzfy();
    }
}
