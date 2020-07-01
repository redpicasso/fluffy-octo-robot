package com.google.android.gms.internal.firebase_ml;

final class zzlh<E> extends zzlc<E> {
    private final zzlg<E> zzacx;

    zzlh(zzlg<E> zzlg, int i) {
        super(zzlg.size(), i);
        this.zzacx = zzlg;
    }

    protected final E get(int i) {
        return this.zzacx.get(i);
    }
}
