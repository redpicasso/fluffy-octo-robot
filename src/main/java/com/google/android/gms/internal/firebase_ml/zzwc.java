package com.google.android.gms.internal.firebase_ml;

import java.util.ArrayList;
import java.util.List;

final class zzwc<E> extends zzsq<E> {
    private static final zzwc<Object> zzbrt;
    private final List<E> zzbqd;

    public static <E> zzwc<E> zzsp() {
        return zzbrt;
    }

    zzwc() {
        this(new ArrayList(10));
    }

    private zzwc(List<E> list) {
        this.zzbqd = list;
    }

    public final void add(int i, E e) {
        zzpu();
        this.zzbqd.add(i, e);
        this.modCount++;
    }

    public final E get(int i) {
        return this.zzbqd.get(i);
    }

    public final E remove(int i) {
        zzpu();
        E remove = this.zzbqd.remove(i);
        this.modCount++;
        return remove;
    }

    public final E set(int i, E e) {
        zzpu();
        E e2 = this.zzbqd.set(i, e);
        this.modCount++;
        return e2;
    }

    public final int size() {
        return this.zzbqd.size();
    }

    public final /* synthetic */ zzun zzck(int i) {
        if (i >= size()) {
            List arrayList = new ArrayList(i);
            arrayList.addAll(this.zzbqd);
            return new zzwc(arrayList);
        }
        throw new IllegalArgumentException();
    }

    static {
        zzsq zzwc = new zzwc(new ArrayList(0));
        zzbrt = zzwc;
        zzwc.zzpt();
    }
}
