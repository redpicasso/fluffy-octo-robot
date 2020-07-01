package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.SortedMap;

@GwtCompatible
public abstract class ForwardingSortedMap<K, V> extends ForwardingMap<K, V> implements SortedMap<K, V> {

    @Beta
    protected class StandardKeySet extends SortedKeySet<K, V> {
        public StandardKeySet() {
            super(ForwardingSortedMap.this);
        }
    }

    protected abstract SortedMap<K, V> delegate();

    protected ForwardingSortedMap() {
    }

    public Comparator<? super K> comparator() {
        return delegate().comparator();
    }

    public K firstKey() {
        return delegate().firstKey();
    }

    public SortedMap<K, V> headMap(K k) {
        return delegate().headMap(k);
    }

    public K lastKey() {
        return delegate().lastKey();
    }

    public SortedMap<K, V> subMap(K k, K k2) {
        return delegate().subMap(k, k2);
    }

    public SortedMap<K, V> tailMap(K k) {
        return delegate().tailMap(k);
    }

    private int unsafeCompare(Object obj, Object obj2) {
        Comparator comparator = comparator();
        if (comparator == null) {
            return ((Comparable) obj).compareTo(obj2);
        }
        return comparator.compare(obj, obj2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:5:0x0010 A:{RETURN, PHI: r0 , ExcHandler: java.lang.ClassCastException (unused java.lang.ClassCastException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x0010 A:{RETURN, PHI: r0 , ExcHandler: java.lang.ClassCastException (unused java.lang.ClassCastException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Missing block: B:5:0x0010, code:
            return r0;
     */
    @com.google.common.annotations.Beta
    protected boolean standardContainsKey(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r3) {
        /*
        r2 = this;
        r0 = 0;
        r1 = r2.tailMap(r3);	 Catch:{ ClassCastException -> 0x0010, ClassCastException -> 0x0010, ClassCastException -> 0x0010 }
        r1 = r1.firstKey();	 Catch:{ ClassCastException -> 0x0010, ClassCastException -> 0x0010, ClassCastException -> 0x0010 }
        r3 = r2.unsafeCompare(r1, r3);	 Catch:{ ClassCastException -> 0x0010, ClassCastException -> 0x0010, ClassCastException -> 0x0010 }
        if (r3 != 0) goto L_0x0010;
    L_0x000f:
        r0 = 1;
    L_0x0010:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ForwardingSortedMap.standardContainsKey(java.lang.Object):boolean");
    }

    @Beta
    protected SortedMap<K, V> standardSubMap(K k, K k2) {
        Preconditions.checkArgument(unsafeCompare(k, k2) <= 0, "fromKey must be <= toKey");
        return tailMap(k).headMap(k2);
    }
}
