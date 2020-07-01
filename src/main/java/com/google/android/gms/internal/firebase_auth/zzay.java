package com.google.android.gms.internal.firebase_auth;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class zzay<E> extends zzav<E> implements List<E>, RandomAccess {
    private static final zzbj<Object> zzgv = new zzax(zzbb.zzhb, 0);

    public static <E> zzay<E> zzce() {
        return zzbb.zzhb;
    }

    public final zzay<E> zzcd() {
        return this;
    }

    public static <E> zzay<E> zza(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        r1 = new Object[8];
        int i = 0;
        r1[0] = e;
        r1[1] = e2;
        r1[2] = e3;
        r1[3] = e4;
        r1[4] = e5;
        r1[5] = e6;
        r1[6] = e7;
        r1[7] = e8;
        while (i < 8) {
            if (r1[i] != null) {
                i++;
            } else {
                StringBuilder stringBuilder = new StringBuilder(20);
                stringBuilder.append("at index ");
                stringBuilder.append(i);
                throw new NullPointerException(stringBuilder.toString());
            }
        }
        return new zzbb(r1, 8);
    }

    static <E> zzay<E> zza(Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return zzbb.zzhb;
        }
        return new zzbb(objArr, length);
    }

    zzay() {
    }

    public final zzbk<E> zzbz() {
        return (zzbj) listIterator();
    }

    public int indexOf(@NullableDecl Object obj) {
        if (obj == null) {
            return -1;
        }
        if (this instanceof RandomAccess) {
            int size = size();
            int i = 0;
            if (obj == null) {
                while (i < size) {
                    if (get(i) == null) {
                        return i;
                    }
                    i++;
                }
            } else {
                while (i < size) {
                    if (obj.equals(get(i))) {
                        return i;
                    }
                    i++;
                }
            }
            return -1;
        }
        ListIterator listIterator = listIterator();
        while (listIterator.hasNext()) {
            if (zzak.equal(obj, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    public int lastIndexOf(@NullableDecl Object obj) {
        if (obj == null) {
            return -1;
        }
        if (this instanceof RandomAccess) {
            if (obj == null) {
                for (int size = size() - 1; size >= 0; size--) {
                    if (get(size) == null) {
                        return size;
                    }
                }
            } else {
                for (int size2 = size() - 1; size2 >= 0; size2--) {
                    if (obj.equals(get(size2))) {
                        return size2;
                    }
                }
            }
            return -1;
        }
        ListIterator listIterator = listIterator(size());
        while (listIterator.hasPrevious()) {
            if (zzak.equal(obj, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    public boolean contains(@NullableDecl Object obj) {
        return indexOf(obj) >= 0;
    }

    /* renamed from: zzc */
    public zzay<E> subList(int i, int i2) {
        zzaj.zza(i, i2, size());
        i2 -= i;
        if (i2 == size()) {
            return this;
        }
        if (i2 == 0) {
            return zzbb.zzhb;
        }
        return new zzba(this, i, i2);
    }

    @Deprecated
    public final boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final E remove(int i) {
        throw new UnsupportedOperationException();
    }

    int zza(Object[] objArr, int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i + i2] = get(i2);
        }
        return i + size;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == zzaj.checkNotNull(this)) {
            return true;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            int size = size();
            if (size == list.size()) {
                int i;
                if ((this instanceof RandomAccess) && (list instanceof RandomAccess)) {
                    i = 0;
                    while (i < size) {
                        if (zzak.equal(get(i), list.get(i))) {
                            i++;
                        }
                    }
                    return true;
                }
                zzay zzay = this;
                i = zzay.size();
                Iterator it = list.iterator();
                int i2 = 0;
                while (i2 < i) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object obj2 = zzay.get(i2);
                    i2++;
                    if (!zzak.equal(obj2, it.next())) {
                        break;
                    }
                }
                if (!it.hasNext()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        int size = size();
        int i = 1;
        for (int i2 = 0; i2 < size; i2++) {
            i = ~(~((i * 31) + get(i2).hashCode()));
        }
        return i;
    }

    public /* synthetic */ ListIterator listIterator(int i) {
        zzaj.zzb(i, size());
        if (isEmpty()) {
            return zzgv;
        }
        return new zzax(this, i);
    }

    public /* synthetic */ ListIterator listIterator() {
        return (zzbj) listIterator(0);
    }
}
