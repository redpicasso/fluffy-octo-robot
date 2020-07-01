package com.google.android.gms.internal.firebase_ml;

final class zzlr extends zzlg<Object> {
    private final transient int offset;
    private final transient int size;
    private final transient Object[] zzadd;

    zzlr(Object[] objArr, int i, int i2) {
        this.zzadd = objArr;
        this.offset = i;
        this.size = i2;
    }

    final boolean zzio() {
        return true;
    }

    public final Object get(int i) {
        zzks.zzb(i, this.size);
        return this.zzadd[(i * 2) + this.offset];
    }

    public final int size() {
        return this.size;
    }
}
