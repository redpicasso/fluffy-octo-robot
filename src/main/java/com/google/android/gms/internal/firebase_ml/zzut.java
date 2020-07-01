package com.google.android.gms.internal.firebase_ml;

import java.util.Map.Entry;

final class zzut<K> implements Entry<K, Object> {
    private Entry<K, zzur> zzbpw;

    private zzut(Entry<K, zzur> entry) {
        this.zzbpw = entry;
    }

    public final K getKey() {
        return this.zzbpw.getKey();
    }

    public final Object getValue() {
        if (((zzur) this.zzbpw.getValue()) == null) {
            return null;
        }
        return zzur.zzrt();
    }

    public final zzur zzru() {
        return (zzur) this.zzbpw.getValue();
    }

    public final Object setValue(Object obj) {
        if (obj instanceof zzvo) {
            return ((zzur) this.zzbpw.getValue()).zzi((zzvo) obj);
        }
        throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
    }
}
