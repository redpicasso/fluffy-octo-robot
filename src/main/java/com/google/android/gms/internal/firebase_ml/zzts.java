package com.google.android.gms.internal.firebase_ml;

final class zzts {
    private final int number;
    private final Object object;

    zzts(Object obj, int i) {
        this.object = obj;
        this.number = i;
    }

    public final int hashCode() {
        return (System.identityHashCode(this.object) * 65535) + this.number;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzts)) {
            return false;
        }
        zzts zzts = (zzts) obj;
        if (this.object == zzts.object && this.number == zzts.number) {
            return true;
        }
        return false;
    }
}
