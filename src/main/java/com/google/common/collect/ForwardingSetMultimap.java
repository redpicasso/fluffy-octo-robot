package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Map.Entry;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
public abstract class ForwardingSetMultimap<K, V> extends ForwardingMultimap<K, V> implements SetMultimap<K, V> {
    protected abstract SetMultimap<K, V> delegate();

    public Set<Entry<K, V>> entries() {
        return delegate().entries();
    }

    public Set<V> get(@NullableDecl K k) {
        return delegate().get(k);
    }

    @CanIgnoreReturnValue
    public Set<V> removeAll(@NullableDecl Object obj) {
        return delegate().removeAll(obj);
    }

    @CanIgnoreReturnValue
    public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return delegate().replaceValues(k, iterable);
    }
}
