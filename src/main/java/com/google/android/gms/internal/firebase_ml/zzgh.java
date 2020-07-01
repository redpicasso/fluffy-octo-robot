package com.google.android.gms.internal.firebase_ml;

import java.util.Collection;
import java.util.HashSet;

public final class zzgh {
    final zzge zzwf;
    Collection<String> zzwi = new HashSet();

    public zzgh(zzge zzge) {
        this.zzwf = (zzge) zzks.checkNotNull(zzge);
    }

    public final zzgg zzgf() {
        return new zzgg(this);
    }

    public final zzgh zza(Collection<String> collection) {
        this.zzwi = collection;
        return this;
    }
}
