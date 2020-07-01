package com.google.android.gms.internal.firebase_ml;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class zzjz<T> implements Iterator<T> {
    private int zzaat = zzkb.zzaax;
    @NullableDecl
    private T zzaau;

    protected zzjz() {
    }

    protected abstract T zzid();

    @NullableDecl
    protected final T zzie() {
        this.zzaat = zzkb.zzaay;
        return null;
    }

    public final boolean hasNext() {
        zzks.checkState(this.zzaat != zzkb.zzaaz);
        int i = zzka.zzaav[this.zzaat - 1];
        if (i == 1) {
            return true;
        }
        if (i != 2) {
            this.zzaat = zzkb.zzaaz;
            this.zzaau = zzid();
            if (this.zzaat != zzkb.zzaay) {
                this.zzaat = zzkb.zzaaw;
                return true;
            }
        }
        return false;
    }

    public final T next() {
        if (hasNext()) {
            this.zzaat = zzkb.zzaax;
            T t = this.zzaau;
            this.zzaau = null;
            return t;
        }
        throw new NoSuchElementException();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
