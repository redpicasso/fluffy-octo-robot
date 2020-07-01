package com.google.android.gms.internal.firebase_auth;

import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class zzbc<E> extends zzav<E> implements Set<E> {
    @NullableDecl
    private transient zzay<E> zzhd;

    zzbc() {
    }

    public boolean equals(@NullableDecl Object obj) {
        return obj == this ? true : zzbh.zza(this, obj);
    }

    public int hashCode() {
        return zzbh.zza(this);
    }

    public zzay<E> zzcd() {
        zzay<E> zzay = this.zzhd;
        if (zzay != null) {
            return zzay;
        }
        zzay = zzci();
        this.zzhd = zzay;
        return zzay;
    }

    zzay<E> zzci() {
        return zzay.zza(toArray());
    }
}
