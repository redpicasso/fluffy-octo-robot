package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzgd extends zzgb<Boolean> implements zzhz<Boolean>, zzjl, RandomAccess {
    private static final zzgd zzvr;
    private int size;
    private boolean[] zzvs;

    zzgd() {
        this(new boolean[10], 0);
    }

    private zzgd(boolean[] zArr, int i) {
        this.zzvs = zArr;
        this.size = i;
    }

    protected final void removeRange(int i, int i2) {
        zzfz();
        if (i2 >= i) {
            Object obj = this.zzvs;
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
        if (!(obj instanceof zzgd)) {
            return super.equals(obj);
        }
        zzgd zzgd = (zzgd) obj;
        if (this.size != zzgd.size) {
            return false;
        }
        boolean[] zArr = zzgd.zzvs;
        for (int i = 0; i < this.size; i++) {
            if (this.zzvs[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzht.zzv(this.zzvs[i2]);
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
        zzfz();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                Object obj = this.zzvs;
                if (i2 < obj.length) {
                    System.arraycopy(obj, i, obj, i + 1, i2 - i);
                } else {
                    Object obj2 = new boolean[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(obj, 0, obj2, 0, i);
                    System.arraycopy(this.zzvs, i, obj2, i + 1, this.size - i);
                    this.zzvs = obj2;
                }
                this.zzvs[i] = z;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzn(i));
    }

    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzfz();
        zzht.checkNotNull(collection);
        if (!(collection instanceof zzgd)) {
            return super.addAll(collection);
        }
        zzgd zzgd = (zzgd) collection;
        int i = zzgd.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            i2 += i;
            boolean[] zArr = this.zzvs;
            if (i2 > zArr.length) {
                this.zzvs = Arrays.copyOf(zArr, i2);
            }
            System.arraycopy(zzgd.zzvs, 0, this.zzvs, this.size, zzgd.size);
            this.size = i2;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzfz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Boolean.valueOf(this.zzvs[i]))) {
                obj = this.zzvs;
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
        boolean booleanValue = ((Boolean) obj).booleanValue();
        zzfz();
        zzm(i);
        boolean[] zArr = this.zzvs;
        boolean z = zArr[i];
        zArr[i] = booleanValue;
        return Boolean.valueOf(z);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Boolean) obj).booleanValue());
    }

    public final /* synthetic */ zzhz zzo(int i) {
        if (i >= this.size) {
            return new zzgd(Arrays.copyOf(this.zzvs, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzm(i);
        return Boolean.valueOf(this.zzvs[i]);
    }

    static {
        zzgb zzgd = new zzgd(new boolean[0], 0);
        zzvr = zzgd;
        zzgd.zzfy();
    }
}
