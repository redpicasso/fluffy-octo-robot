package com.google.android.gms.internal.firebase_auth;

import java.util.Map.Entry;

final class zzbd<K, V> extends zzbc<Entry<K, V>> {
    private final transient int size;
    private final transient zzaz<K, V> zzhe;
    private final transient Object[] zzhf;
    private final transient int zzhg = 0;

    zzbd(zzaz<K, V> zzaz, Object[] objArr, int i, int i2) {
        this.zzhe = zzaz;
        this.zzhf = objArr;
        this.size = i2;
    }

    public final zzbk<Entry<K, V>> zzbz() {
        return (zzbk) zzcd().iterator();
    }

    final int zza(Object[] objArr, int i) {
        return zzcd().zza(objArr, i);
    }

    final zzay<Entry<K, V>> zzci() {
        return new zzbg(this);
    }

    public final boolean contains(Object obj) {
        if (obj instanceof Entry) {
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            obj = entry.getValue();
            if (obj != null && obj.equals(this.zzhe.get(key))) {
                return true;
            }
        }
        return false;
    }

    public final int size() {
        return this.size;
    }
}
