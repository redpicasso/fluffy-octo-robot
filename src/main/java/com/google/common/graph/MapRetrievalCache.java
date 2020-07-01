package com.google.common.graph;

import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class MapRetrievalCache<K, V> extends MapIteratorCache<K, V> {
    @NullableDecl
    private transient CacheEntry<K, V> cacheEntry1;
    @NullableDecl
    private transient CacheEntry<K, V> cacheEntry2;

    private static final class CacheEntry<K, V> {
        final K key;
        final V value;

        CacheEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }

    MapRetrievalCache(Map<K, V> map) {
        super(map);
    }

    public V get(@NullableDecl Object obj) {
        V ifCached = getIfCached(obj);
        if (ifCached != null) {
            return ifCached;
        }
        ifCached = getWithoutCaching(obj);
        if (ifCached != null) {
            addToCache(obj, ifCached);
        }
        return ifCached;
    }

    protected V getIfCached(@NullableDecl Object obj) {
        V ifCached = super.getIfCached(obj);
        if (ifCached != null) {
            return ifCached;
        }
        CacheEntry cacheEntry = this.cacheEntry1;
        if (cacheEntry != null && cacheEntry.key == obj) {
            return cacheEntry.value;
        }
        cacheEntry = this.cacheEntry2;
        if (cacheEntry == null || cacheEntry.key != obj) {
            return null;
        }
        addToCache(cacheEntry);
        return cacheEntry.value;
    }

    protected void clearCache() {
        super.clearCache();
        this.cacheEntry1 = null;
        this.cacheEntry2 = null;
    }

    private void addToCache(K k, V v) {
        addToCache(new CacheEntry(k, v));
    }

    private void addToCache(CacheEntry<K, V> cacheEntry) {
        this.cacheEntry2 = this.cacheEntry1;
        this.cacheEntry1 = cacheEntry;
    }
}
