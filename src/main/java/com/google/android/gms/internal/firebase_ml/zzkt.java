package com.google.android.gms.internal.firebase_ml;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class zzkt<T> extends zzko<T> {
    private final T zzabi;

    zzkt(T t) {
        this.zzabi = t;
    }

    public final boolean isPresent() {
        return true;
    }

    public final T get() {
        return this.zzabi;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof zzkt)) {
            return false;
        }
        return this.zzabi.equals(((zzkt) obj).zzabi);
    }

    public final int hashCode() {
        return this.zzabi.hashCode() + 1502476572;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzabi);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 13);
        stringBuilder.append("Optional.of(");
        stringBuilder.append(valueOf);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
