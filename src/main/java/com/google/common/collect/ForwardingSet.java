package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
public abstract class ForwardingSet<E> extends ForwardingCollection<E> implements Set<E> {
    protected abstract Set<E> delegate();

    protected ForwardingSet() {
    }

    public boolean equals(@NullableDecl Object obj) {
        return obj == this || delegate().equals(obj);
    }

    public int hashCode() {
        return delegate().hashCode();
    }

    protected boolean standardRemoveAll(Collection<?> collection) {
        return Sets.removeAllImpl((Set) this, (Collection) Preconditions.checkNotNull(collection));
    }

    protected boolean standardEquals(@NullableDecl Object obj) {
        return Sets.equalsImpl(this, obj);
    }

    protected int standardHashCode() {
        return Sets.hashCodeImpl(this);
    }
}
