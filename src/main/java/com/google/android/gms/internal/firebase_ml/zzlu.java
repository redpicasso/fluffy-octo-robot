package com.google.android.gms.internal.firebase_ml;

import java.util.ListIterator;

public abstract class zzlu<E> extends zzlt<E> implements ListIterator<E> {
    protected zzlu() {
    }

    @Deprecated
    public final void add(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void set(E e) {
        throw new UnsupportedOperationException();
    }
}
