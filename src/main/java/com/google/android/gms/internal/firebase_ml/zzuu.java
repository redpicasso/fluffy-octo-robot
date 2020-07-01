package com.google.android.gms.internal.firebase_ml;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzuu<K> implements Iterator<Entry<K, Object>> {
    private Iterator<Entry<K, Object>> zzbpx;

    public zzuu(Iterator<Entry<K, Object>> it) {
        this.zzbpx = it;
    }

    public final boolean hasNext() {
        return this.zzbpx.hasNext();
    }

    public final void remove() {
        this.zzbpx.remove();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zzbpx.next();
        return entry.getValue() instanceof zzur ? new zzut(entry) : entry;
    }
}
