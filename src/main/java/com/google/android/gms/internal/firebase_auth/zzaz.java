package com.google.android.gms.internal.firebase_auth;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class zzaz<K, V> implements Serializable, Map<K, V> {
    private static final Entry<?, ?>[] zzgw = new Entry[0];
    private transient zzbc<Entry<K, V>> zzgx;
    private transient zzbc<K> zzgy;
    private transient zzav<V> zzgz;

    public static <K, V> zzaz<K, V> zzb(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        zzat.zza(k, v);
        zzat.zza(k2, v2);
        zzat.zza(k3, v3);
        zzat.zza(k4, v4);
        return zzbe.zza(4, new Object[]{k, v, k2, v2, k3, v3, k4, v4});
    }

    public abstract V get(@NullableDecl Object obj);

    abstract zzbc<Entry<K, V>> zzcf();

    abstract zzbc<K> zzcg();

    abstract zzav<V> zzch();

    zzaz() {
    }

    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(@NullableDecl Object obj) {
        return get(obj) != null;
    }

    public boolean containsValue(@NullableDecl Object obj) {
        return ((zzav) values()).contains(obj);
    }

    public final V getOrDefault(@NullableDecl Object obj, @NullableDecl V v) {
        V v2 = get(obj);
        return v2 != null ? v2 : v;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        return entrySet().equals(((Map) obj).entrySet());
    }

    public int hashCode() {
        return zzbh.zza((zzbc) entrySet());
    }

    public String toString() {
        int size = size();
        if (size >= 0) {
            StringBuilder stringBuilder = new StringBuilder((int) Math.min(((long) size) << 3, 1073741824));
            stringBuilder.append('{');
            Object obj = 1;
            for (Entry entry : entrySet()) {
                if (obj == null) {
                    stringBuilder.append(", ");
                }
                obj = null;
                stringBuilder.append(entry.getKey());
                stringBuilder.append('=');
                stringBuilder.append(entry.getValue());
            }
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
        String str = "size";
        StringBuilder stringBuilder2 = new StringBuilder(str.length() + 40);
        stringBuilder2.append(str);
        stringBuilder2.append(" cannot be negative but was: ");
        stringBuilder2.append(size);
        throw new IllegalArgumentException(stringBuilder2.toString());
    }

    public /* synthetic */ Set entrySet() {
        Set set = this.zzgx;
        if (set != null) {
            return set;
        }
        set = zzcf();
        this.zzgx = set;
        return set;
    }

    public /* synthetic */ Collection values() {
        Collection collection = this.zzgz;
        if (collection != null) {
            return collection;
        }
        collection = zzch();
        this.zzgz = collection;
        return collection;
    }

    public /* synthetic */ Set keySet() {
        Set set = this.zzgy;
        if (set != null) {
            return set;
        }
        set = zzcg();
        this.zzgy = set;
        return set;
    }
}
