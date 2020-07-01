package com.google.android.gms.internal.firebase_ml;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class zzlg<E> extends zzlf<E> implements List<E>, RandomAccess {
    private static final zzlu<Object> zzacw = new zzlh(zzlm.zzadg, 0);

    public static <E> zzlg<E> zzip() {
        return zzlm.zzadg;
    }

    public final zzlg<E> zzin() {
        return this;
    }

    public static <E> zzlg<E> zzb(Collection<? extends E> collection) {
        Object[] toArray;
        int length;
        if (collection instanceof zzlf) {
            zzlf zzin = ((zzlf) collection).zzin();
            if (!zzin.zzio()) {
                return zzin;
            }
            toArray = zzin.toArray();
            length = toArray.length;
            if (length == 0) {
                return zzlm.zzadg;
            }
            return new zzlm(toArray, length);
        }
        toArray = collection.toArray();
        length = toArray.length;
        int i = 0;
        while (i < length) {
            if (toArray[i] != null) {
                i++;
            } else {
                StringBuilder stringBuilder = new StringBuilder(20);
                stringBuilder.append("at index ");
                stringBuilder.append(i);
                throw new NullPointerException(stringBuilder.toString());
            }
        }
        length = toArray.length;
        if (length == 0) {
            return zzlm.zzadg;
        }
        return new zzlm(toArray, length);
    }

    static <E> zzlg<E> zza(Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return zzlm.zzadg;
        }
        return new zzlm(objArr, length);
    }

    zzlg() {
    }

    public final zzlt<E> zzij() {
        return (zzlu) listIterator();
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
            if (zzkn.equal(obj, listIterator.next())) {
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
            if (zzkn.equal(obj, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    public boolean contains(@NullableDecl Object obj) {
        return indexOf(obj) >= 0;
    }

    /* renamed from: zzd */
    public zzlg<E> subList(int i, int i2) {
        zzks.zza(i, i2, size());
        i2 -= i;
        if (i2 == size()) {
            return this;
        }
        if (i2 == 0) {
            return zzlm.zzadg;
        }
        return new zzli(this, i, i2);
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
        if (obj == zzks.checkNotNull(this)) {
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
                        if (zzkn.equal(get(i), list.get(i))) {
                            i++;
                        }
                    }
                    return true;
                }
                zzlg zzlg = this;
                i = zzlg.size();
                Iterator it = list.iterator();
                int i2 = 0;
                while (i2 < i) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object obj2 = zzlg.get(i2);
                    i2++;
                    if (!zzkn.equal(obj2, it.next())) {
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
        zzks.zzc(i, size());
        if (isEmpty()) {
            return zzacw;
        }
        return new zzlh(this, i);
    }

    public /* synthetic */ ListIterator listIterator() {
        return (zzlu) listIterator(0);
    }
}
