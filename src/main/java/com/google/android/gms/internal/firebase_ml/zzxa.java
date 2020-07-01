package com.google.android.gms.internal.firebase_ml;

import java.util.ListIterator;

final class zzxa implements ListIterator<String> {
    private ListIterator<String> zzbsp = this.zzbsr.zzbso.listIterator(this.zzbsq);
    private final /* synthetic */ int zzbsq;
    private final /* synthetic */ zzwz zzbsr;

    zzxa(zzwz zzwz, int i) {
        this.zzbsr = zzwz;
        this.zzbsq = i;
    }

    public final boolean hasNext() {
        return this.zzbsp.hasNext();
    }

    public final boolean hasPrevious() {
        return this.zzbsp.hasPrevious();
    }

    public final int nextIndex() {
        return this.zzbsp.nextIndex();
    }

    public final int previousIndex() {
        return this.zzbsp.previousIndex();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ void set(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object previous() {
        return (String) this.zzbsp.previous();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzbsp.next();
    }
}
