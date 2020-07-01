package com.google.android.gms.internal.firebase_ml;

import java.util.Iterator;

final class zzxb implements Iterator<String> {
    private final /* synthetic */ zzwz zzbsr;
    private Iterator<String> zzbss = this.zzbsr.zzbso.iterator();

    zzxb(zzwz zzwz) {
        this.zzbsr = zzwz;
    }

    public final boolean hasNext() {
        return this.zzbss.hasNext();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zzbss.next();
    }
}
