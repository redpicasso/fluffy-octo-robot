package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.Comparator;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
public abstract class ForwardingSortedSet<E> extends ForwardingSet<E> implements SortedSet<E> {
    protected abstract SortedSet<E> delegate();

    protected ForwardingSortedSet() {
    }

    public Comparator<? super E> comparator() {
        return delegate().comparator();
    }

    public E first() {
        return delegate().first();
    }

    public SortedSet<E> headSet(E e) {
        return delegate().headSet(e);
    }

    public E last() {
        return delegate().last();
    }

    public SortedSet<E> subSet(E e, E e2) {
        return delegate().subSet(e, e2);
    }

    public SortedSet<E> tailSet(E e) {
        return delegate().tailSet(e);
    }

    private int unsafeCompare(@NullableDecl Object obj, @NullableDecl Object obj2) {
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
    protected boolean standardContains(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r3) {
        /*
        r2 = this;
        r0 = 0;
        r1 = r2.tailSet(r3);	 Catch:{ ClassCastException -> 0x0010, ClassCastException -> 0x0010, ClassCastException -> 0x0010 }
        r1 = r1.first();	 Catch:{ ClassCastException -> 0x0010, ClassCastException -> 0x0010, ClassCastException -> 0x0010 }
        r3 = r2.unsafeCompare(r1, r3);	 Catch:{ ClassCastException -> 0x0010, ClassCastException -> 0x0010, ClassCastException -> 0x0010 }
        if (r3 != 0) goto L_0x0010;
    L_0x000f:
        r0 = 1;
    L_0x0010:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ForwardingSortedSet.standardContains(java.lang.Object):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x001e A:{RETURN, ExcHandler: java.lang.ClassCastException (unused java.lang.ClassCastException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Missing block: B:9:0x001e, code:
            return false;
     */
    @com.google.common.annotations.Beta
    protected boolean standardRemove(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r4) {
        /*
        r3 = this;
        r0 = 0;
        r1 = r3.tailSet(r4);	 Catch:{ ClassCastException -> 0x001e, ClassCastException -> 0x001e }
        r1 = r1.iterator();	 Catch:{ ClassCastException -> 0x001e, ClassCastException -> 0x001e }
        r2 = r1.hasNext();	 Catch:{ ClassCastException -> 0x001e, ClassCastException -> 0x001e }
        if (r2 == 0) goto L_0x001e;
    L_0x000f:
        r2 = r1.next();	 Catch:{ ClassCastException -> 0x001e, ClassCastException -> 0x001e }
        r4 = r3.unsafeCompare(r2, r4);	 Catch:{ ClassCastException -> 0x001e, ClassCastException -> 0x001e }
        if (r4 != 0) goto L_0x001e;
    L_0x0019:
        r1.remove();	 Catch:{ ClassCastException -> 0x001e, ClassCastException -> 0x001e }
        r4 = 1;
        return r4;
    L_0x001e:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ForwardingSortedSet.standardRemove(java.lang.Object):boolean");
    }

    @Beta
    protected SortedSet<E> standardSubSet(E e, E e2) {
        return tailSet(e).headSet(e2);
    }
}
