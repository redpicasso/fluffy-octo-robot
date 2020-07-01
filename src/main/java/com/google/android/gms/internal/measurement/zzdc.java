package com.google.android.gms.internal.measurement;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class zzdc<T> implements zzdb<T> {
    @NullableDecl
    private T value;
    private volatile zzdb<T> zzabs;
    private volatile boolean zzdh;

    zzdc(zzdb<T> zzdb) {
        this.zzabs = (zzdb) zzcz.checkNotNull(zzdb);
    }

    public final T get() {
        if (!this.zzdh) {
            synchronized (this) {
                if (!this.zzdh) {
                    T t = this.zzabs.get();
                    this.value = t;
                    this.zzdh = true;
                    this.zzabs = null;
                    return t;
                }
            }
        }
        return this.value;
    }

    public final String toString() {
        String valueOf;
        StringBuilder stringBuilder;
        Object obj = this.zzabs;
        if (obj == null) {
            valueOf = String.valueOf(this.value);
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 25);
            stringBuilder.append("<supplier that returned ");
            stringBuilder.append(valueOf);
            stringBuilder.append(">");
            obj = stringBuilder.toString();
        }
        valueOf = String.valueOf(obj);
        stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 19);
        stringBuilder.append("Suppliers.memoize(");
        stringBuilder.append(valueOf);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
