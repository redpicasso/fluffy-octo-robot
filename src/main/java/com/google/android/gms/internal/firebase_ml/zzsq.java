package com.google.android.gms.internal.firebase_ml;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

abstract class zzsq<E> extends AbstractList<E> implements zzun<E> {
    private boolean zzbkd = true;

    zzsq() {
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        if (!(obj instanceof RandomAccess)) {
            return super.equals(obj);
        }
        List list = (List) obj;
        int size = size();
        if (size != list.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!get(i).equals(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < size(); i2++) {
            i = (i * 31) + get(i2).hashCode();
        }
        return i;
    }

    public boolean add(E e) {
        zzpu();
        return super.add(e);
    }

    public void add(int i, E e) {
        zzpu();
        super.add(i, e);
    }

    public boolean addAll(Collection<? extends E> collection) {
        zzpu();
        return super.addAll(collection);
    }

    public boolean addAll(int i, Collection<? extends E> collection) {
        zzpu();
        return super.addAll(i, collection);
    }

    public void clear() {
        zzpu();
        super.clear();
    }

    public boolean zzps() {
        return this.zzbkd;
    }

    public final void zzpt() {
        this.zzbkd = false;
    }

    public E remove(int i) {
        zzpu();
        return super.remove(i);
    }

    public boolean remove(Object obj) {
        zzpu();
        return super.remove(obj);
    }

    public boolean removeAll(Collection<?> collection) {
        zzpu();
        return super.removeAll(collection);
    }

    public boolean retainAll(Collection<?> collection) {
        zzpu();
        return super.retainAll(collection);
    }

    public E set(int i, E e) {
        zzpu();
        return super.set(i, e);
    }

    protected final void zzpu() {
        if (!this.zzbkd) {
            throw new UnsupportedOperationException();
        }
    }
}
