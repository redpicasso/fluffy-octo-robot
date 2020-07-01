package com.google.android.gms.internal.firebase_ml;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

class zzwq extends AbstractSet<Entry<K, V>> {
    private final /* synthetic */ zzwh zzbsf;

    private zzwq(zzwh zzwh) {
        this.zzbsf = zzwh;
    }

    public Iterator<Entry<K, V>> iterator() {
        return new zzwp(this.zzbsf, null);
    }

    public int size() {
        return this.zzbsf.size();
    }

    public boolean contains(Object obj) {
        Entry entry = (Entry) obj;
        Object obj2 = this.zzbsf.get(entry.getKey());
        obj = entry.getValue();
        return obj2 == obj || (obj2 != null && obj2.equals(obj));
    }

    public boolean remove(Object obj) {
        Entry entry = (Entry) obj;
        if (!contains(entry)) {
            return false;
        }
        this.zzbsf.remove(entry.getKey());
        return true;
    }

    public void clear() {
        this.zzbsf.clear();
    }

    public /* synthetic */ boolean add(Object obj) {
        Entry entry = (Entry) obj;
        if (contains(entry)) {
            return false;
        }
        this.zzbsf.put((Comparable) entry.getKey(), entry.getValue());
        return true;
    }

    /* synthetic */ zzwq(zzwh zzwh, zzwi zzwi) {
        this(zzwh);
    }
}
