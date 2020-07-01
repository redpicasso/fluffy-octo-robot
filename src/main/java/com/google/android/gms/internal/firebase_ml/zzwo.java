package com.google.android.gms.internal.firebase_ml;

import java.util.Map.Entry;

final class zzwo implements Comparable<zzwo>, Entry<K, V> {
    private V value;
    private final /* synthetic */ zzwh zzbsf;
    private final K zzbsi;

    zzwo(zzwh zzwh, Entry<K, V> entry) {
        this(zzwh, (Comparable) entry.getKey(), entry.getValue());
    }

    zzwo(zzwh zzwh, K k, V v) {
        this.zzbsf = zzwh;
        this.zzbsi = k;
        this.value = v;
    }

    public final V getValue() {
        return this.value;
    }

    public final V setValue(V v) {
        this.zzbsf.zzta();
        V v2 = this.value;
        this.value = v;
        return v2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) obj;
        return equals(this.zzbsi, entry.getKey()) && equals(this.value, entry.getValue());
    }

    public final int hashCode() {
        Comparable comparable = this.zzbsi;
        int i = 0;
        int hashCode = comparable == null ? 0 : comparable.hashCode();
        Object obj = this.value;
        if (obj != null) {
            i = obj.hashCode();
        }
        return hashCode ^ i;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzbsi);
        String valueOf2 = String.valueOf(this.value);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(valueOf2).length());
        stringBuilder.append(valueOf);
        stringBuilder.append("=");
        stringBuilder.append(valueOf2);
        return stringBuilder.toString();
    }

    private static boolean equals(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        } else {
            return obj.equals(obj2);
        }
    }

    public final /* synthetic */ Object getKey() {
        return this.zzbsi;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        return ((Comparable) getKey()).compareTo((Comparable) ((zzwo) obj).getKey());
    }
}
