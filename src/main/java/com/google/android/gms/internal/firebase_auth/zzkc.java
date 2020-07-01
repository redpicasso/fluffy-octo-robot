package com.google.android.gms.internal.firebase_auth;

import java.util.Map.Entry;

final class zzkc implements Comparable<zzkc>, Entry<K, V> {
    private V value;
    private final /* synthetic */ zzjt zzaeb;
    private final K zzaef;

    zzkc(zzjt zzjt, Entry<K, V> entry) {
        this(zzjt, (Comparable) entry.getKey(), entry.getValue());
    }

    zzkc(zzjt zzjt, K k, V v) {
        this.zzaeb = zzjt;
        this.zzaef = k;
        this.value = v;
    }

    public final V getValue() {
        return this.value;
    }

    public final V setValue(V v) {
        this.zzaeb.zzkb();
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
        return equals(this.zzaef, entry.getKey()) && equals(this.value, entry.getValue());
    }

    public final int hashCode() {
        Comparable comparable = this.zzaef;
        int i = 0;
        int hashCode = comparable == null ? 0 : comparable.hashCode();
        Object obj = this.value;
        if (obj != null) {
            i = obj.hashCode();
        }
        return hashCode ^ i;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzaef);
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
        return this.zzaef;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        return ((Comparable) getKey()).compareTo((Comparable) ((zzkc) obj).getKey());
    }
}
