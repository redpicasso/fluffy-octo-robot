package com.google.android.gms.internal.firebase_ml;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class zzlj<K, V> implements Serializable, Map<K, V> {
    private static final Entry<?, ?>[] zzacz = new Entry[0];
    private transient zzll<Entry<K, V>> zzada;
    private transient zzll<K> zzadb;
    private transient zzlf<V> zzadc;

    zzlj() {
    }

    public abstract V get(@NullableDecl Object obj);

    abstract zzll<Entry<K, V>> zziq();

    abstract zzll<K> zzir();

    abstract zzlf<V> zzis();

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
        return ((zzlf) values()).contains(obj);
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
        return zzls.zzb((zzll) entrySet());
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
        Set set = this.zzada;
        if (set != null) {
            return set;
        }
        set = zziq();
        this.zzada = set;
        return set;
    }

    public /* synthetic */ Collection values() {
        Collection collection = this.zzadc;
        if (collection != null) {
            return collection;
        }
        collection = zzis();
        this.zzadc = collection;
        return collection;
    }

    public /* synthetic */ Set keySet() {
        Set set = this.zzadb;
        if (set != null) {
            return set;
        }
        set = zzir();
        this.zzadb = set;
        return set;
    }
}
