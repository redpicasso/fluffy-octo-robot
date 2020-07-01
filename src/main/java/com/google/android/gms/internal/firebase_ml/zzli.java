package com.google.android.gms.internal.firebase_ml;

final class zzli extends zzlg<E> {
    private final transient int length;
    private final transient int offset;
    private final /* synthetic */ zzlg zzacy;

    zzli(zzlg zzlg, int i, int i2) {
        this.zzacy = zzlg;
        this.offset = i;
        this.length = i2;
    }

    final boolean zzio() {
        return true;
    }

    public final int size() {
        return this.length;
    }

    final Object[] zzik() {
        return this.zzacy.zzik();
    }

    final int zzil() {
        return this.zzacy.zzil() + this.offset;
    }

    final int zzim() {
        return (this.zzacy.zzil() + this.offset) + this.length;
    }

    public final E get(int i) {
        zzks.zzb(i, this.length);
        return this.zzacy.get(i + this.offset);
    }

    public final zzlg<E> zzd(int i, int i2) {
        zzks.zza(i, i2, this.length);
        zzlg zzlg = this.zzacy;
        int i3 = this.offset;
        return (zzlg) zzlg.subList(i + i3, i2 + i3);
    }
}
