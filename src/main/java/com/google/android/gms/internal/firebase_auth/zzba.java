package com.google.android.gms.internal.firebase_auth;

final class zzba extends zzay<E> {
    private final transient int length;
    private final transient int offset;
    private final /* synthetic */ zzay zzha;

    zzba(zzay zzay, int i, int i2) {
        this.zzha = zzay;
        this.offset = i;
        this.length = i2;
    }

    public final int size() {
        return this.length;
    }

    final Object[] zzca() {
        return this.zzha.zzca();
    }

    final int zzcb() {
        return this.zzha.zzcb() + this.offset;
    }

    final int zzcc() {
        return (this.zzha.zzcb() + this.offset) + this.length;
    }

    public final E get(int i) {
        zzaj.zza(i, this.length);
        return this.zzha.get(i + this.offset);
    }

    public final zzay<E> zzc(int i, int i2) {
        zzaj.zza(i, i2, this.length);
        zzay zzay = this.zzha;
        int i3 = this.offset;
        return (zzay) zzay.subList(i + i3, i2 + i3);
    }
}
