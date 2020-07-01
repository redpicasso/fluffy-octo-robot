package com.google.android.gms.internal.firebase_ml;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public final class zzwz extends AbstractList<String> implements zzux, RandomAccess {
    private final zzux zzbso;

    public zzwz(zzux zzux) {
        this.zzbso = zzux;
    }

    public final zzux zzrw() {
        return this;
    }

    public final Object getRaw(int i) {
        return this.zzbso.getRaw(i);
    }

    public final int size() {
        return this.zzbso.size();
    }

    public final void zzc(zzsw zzsw) {
        throw new UnsupportedOperationException();
    }

    public final ListIterator<String> listIterator(int i) {
        return new zzxa(this, i);
    }

    public final Iterator<String> iterator() {
        return new zzxb(this);
    }

    public final List<?> zzrv() {
        return this.zzbso.zzrv();
    }

    public final /* synthetic */ Object get(int i) {
        return (String) this.zzbso.get(i);
    }
}
