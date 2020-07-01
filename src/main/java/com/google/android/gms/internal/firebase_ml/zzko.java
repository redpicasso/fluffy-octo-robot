package com.google.android.gms.internal.firebase_ml;

import java.io.Serializable;

public abstract class zzko<T> implements Serializable {
    public static <T> zzko<T> zzig() {
        return zzjy.zzaas;
    }

    public abstract T get();

    public abstract boolean isPresent();

    public static <T> zzko<T> zzk(T t) {
        return new zzkt(zzks.checkNotNull(t));
    }

    zzko() {
    }
}
