package com.google.android.gms.internal.firebase_ml;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzwk extends zzwq {
    private final /* synthetic */ zzwh zzbsf;

    private zzwk(zzwh zzwh) {
        this.zzbsf = zzwh;
        super(zzwh, null);
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new zzwj(this.zzbsf, null);
    }

    /* synthetic */ zzwk(zzwh zzwh, zzwi zzwi) {
        this(zzwh);
    }
}
