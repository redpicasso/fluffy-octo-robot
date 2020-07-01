package com.google.android.gms.internal.firebase_ml;

import java.util.Map.Entry;

final class zzlo<K, V> extends zzll<Entry<K, V>> {
    private final transient int size;
    private final transient Object[] zzadd;
    private final transient zzlj<K, V> zzadk;
    private final transient int zzadl = 0;

    zzlo(zzlj<K, V> zzlj, Object[] objArr, int i, int i2) {
        this.zzadk = zzlj;
        this.zzadd = objArr;
        this.size = i2;
    }

    final boolean zzio() {
        return true;
    }

    public final zzlt<Entry<K, V>> zzij() {
        return (zzlt) zzin().iterator();
    }

    final int zza(Object[] objArr, int i) {
        return zzin().zza(objArr, i);
    }

    final zzlg<Entry<K, V>> zziu() {
        return new zzlp(this);
    }

    public final boolean contains(Object obj) {
        if (obj instanceof Entry) {
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            obj = entry.getValue();
            if (obj != null && obj.equals(this.zzadk.get(key))) {
                return true;
            }
        }
        return false;
    }

    public final int size() {
        return this.size;
    }
}
