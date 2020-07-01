package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    static final RegularImmutableSortedSet<Comparable> NATURAL_EMPTY_SET = new RegularImmutableSortedSet(ImmutableList.of(), Ordering.natural());
    @VisibleForTesting
    final transient ImmutableList<E> elements;

    RegularImmutableSortedSet(ImmutableList<E> immutableList, Comparator<? super E> comparator) {
        super(comparator);
        this.elements = immutableList;
    }

    public UnmodifiableIterator<E> iterator() {
        return this.elements.iterator();
    }

    @GwtIncompatible
    public UnmodifiableIterator<E> descendingIterator() {
        return this.elements.reverse().iterator();
    }

    public int size() {
        return this.elements.size();
    }

    public boolean contains(@NullableDecl Object obj) {
        boolean z = false;
        if (obj != null) {
            try {
                if (unsafeBinarySearch(obj) >= 0) {
                    z = true;
                }
            } catch (ClassCastException unused) {
                return z;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0056 A:{RETURN, ExcHandler: java.lang.NullPointerException (unused java.lang.NullPointerException), Splitter: B:11:0x0034} */
    /* JADX WARNING: Missing block: B:25:0x0056, code:
            return false;
     */
    public boolean containsAll(java.util.Collection<?> r7) {
        /*
        r6 = this;
        r0 = r7 instanceof com.google.common.collect.Multiset;
        if (r0 == 0) goto L_0x000a;
    L_0x0004:
        r7 = (com.google.common.collect.Multiset) r7;
        r7 = r7.elementSet();
    L_0x000a:
        r0 = r6.comparator();
        r0 = com.google.common.collect.SortedIterables.hasSameComparator(r0, r7);
        if (r0 == 0) goto L_0x0057;
    L_0x0014:
        r0 = r7.size();
        r1 = 1;
        if (r0 > r1) goto L_0x001c;
    L_0x001b:
        goto L_0x0057;
    L_0x001c:
        r0 = r6.iterator();
        r7 = r7.iterator();
        r2 = r0.hasNext();
        r3 = 0;
        if (r2 != 0) goto L_0x002c;
    L_0x002b:
        return r3;
    L_0x002c:
        r2 = r7.next();
        r4 = r0.next();
    L_0x0034:
        r5 = r6.unsafeCompare(r4, r2);	 Catch:{ NullPointerException -> 0x0056, NullPointerException -> 0x0056 }
        if (r5 >= 0) goto L_0x0046;
    L_0x003a:
        r4 = r0.hasNext();	 Catch:{ NullPointerException -> 0x0056, NullPointerException -> 0x0056 }
        if (r4 != 0) goto L_0x0041;
    L_0x0040:
        return r3;
    L_0x0041:
        r4 = r0.next();	 Catch:{ NullPointerException -> 0x0056, NullPointerException -> 0x0056 }
        goto L_0x0034;
    L_0x0046:
        if (r5 != 0) goto L_0x0054;
    L_0x0048:
        r2 = r7.hasNext();	 Catch:{ NullPointerException -> 0x0056, NullPointerException -> 0x0056 }
        if (r2 != 0) goto L_0x004f;
    L_0x004e:
        return r1;
    L_0x004f:
        r2 = r7.next();	 Catch:{ NullPointerException -> 0x0056, NullPointerException -> 0x0056 }
        goto L_0x0034;
    L_0x0054:
        if (r5 <= 0) goto L_0x0034;
    L_0x0056:
        return r3;
    L_0x0057:
        r7 = super.containsAll(r7);
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableSortedSet.containsAll(java.util.Collection):boolean");
    }

    private int unsafeBinarySearch(Object obj) throws ClassCastException {
        return Collections.binarySearch(this.elements, obj, unsafeComparator());
    }

    boolean isPartialView() {
        return this.elements.isPartialView();
    }

    int copyIntoArray(Object[] objArr, int i) {
        return this.elements.copyIntoArray(objArr, i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0046 A:{RETURN, ExcHandler: java.lang.ClassCastException (unused java.lang.ClassCastException), Splitter: B:15:0x002a} */
    /* JADX WARNING: Missing block: B:25:0x0046, code:
            return false;
     */
    public boolean equals(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r6) {
        /*
        r5 = this;
        r0 = 1;
        if (r6 != r5) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = r6 instanceof java.util.Set;
        r2 = 0;
        if (r1 != 0) goto L_0x000a;
    L_0x0009:
        return r2;
    L_0x000a:
        r6 = (java.util.Set) r6;
        r1 = r5.size();
        r3 = r6.size();
        if (r1 == r3) goto L_0x0017;
    L_0x0016:
        return r2;
    L_0x0017:
        r1 = r5.isEmpty();
        if (r1 == 0) goto L_0x001e;
    L_0x001d:
        return r0;
    L_0x001e:
        r1 = r5.comparator;
        r1 = com.google.common.collect.SortedIterables.hasSameComparator(r1, r6);
        if (r1 == 0) goto L_0x0047;
    L_0x0026:
        r6 = r6.iterator();
        r1 = r5.iterator();	 Catch:{ ClassCastException -> 0x0046, ClassCastException -> 0x0046 }
    L_0x002e:
        r3 = r1.hasNext();	 Catch:{ ClassCastException -> 0x0046, ClassCastException -> 0x0046 }
        if (r3 == 0) goto L_0x0045;
    L_0x0034:
        r3 = r1.next();	 Catch:{ ClassCastException -> 0x0046, ClassCastException -> 0x0046 }
        r4 = r6.next();	 Catch:{ ClassCastException -> 0x0046, ClassCastException -> 0x0046 }
        if (r4 == 0) goto L_0x0044;
    L_0x003e:
        r3 = r5.unsafeCompare(r3, r4);	 Catch:{ ClassCastException -> 0x0046, ClassCastException -> 0x0046 }
        if (r3 == 0) goto L_0x002e;
    L_0x0044:
        return r2;
    L_0x0045:
        return r0;
    L_0x0046:
        return r2;
    L_0x0047:
        r6 = r5.containsAll(r6);
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableSortedSet.equals(java.lang.Object):boolean");
    }

    public E first() {
        if (!isEmpty()) {
            return this.elements.get(0);
        }
        throw new NoSuchElementException();
    }

    public E last() {
        if (!isEmpty()) {
            return this.elements.get(size() - 1);
        }
        throw new NoSuchElementException();
    }

    public E lower(E e) {
        int headIndex = headIndex(e, false) - 1;
        if (headIndex == -1) {
            return null;
        }
        return this.elements.get(headIndex);
    }

    public E floor(E e) {
        int headIndex = headIndex(e, true) - 1;
        if (headIndex == -1) {
            return null;
        }
        return this.elements.get(headIndex);
    }

    public E ceiling(E e) {
        int tailIndex = tailIndex(e, true);
        return tailIndex == size() ? null : this.elements.get(tailIndex);
    }

    public E higher(E e) {
        int tailIndex = tailIndex(e, false);
        return tailIndex == size() ? null : this.elements.get(tailIndex);
    }

    ImmutableSortedSet<E> headSetImpl(E e, boolean z) {
        return getSubSet(0, headIndex(e, z));
    }

    int headIndex(E e, boolean z) {
        int binarySearch = Collections.binarySearch(this.elements, Preconditions.checkNotNull(e), comparator());
        if (binarySearch < 0) {
            return ~binarySearch;
        }
        if (z) {
            binarySearch++;
        }
        return binarySearch;
    }

    ImmutableSortedSet<E> subSetImpl(E e, boolean z, E e2, boolean z2) {
        return tailSetImpl(e, z).headSetImpl(e2, z2);
    }

    ImmutableSortedSet<E> tailSetImpl(E e, boolean z) {
        return getSubSet(tailIndex(e, z), size());
    }

    int tailIndex(E e, boolean z) {
        int binarySearch = Collections.binarySearch(this.elements, Preconditions.checkNotNull(e), comparator());
        if (binarySearch < 0) {
            return ~binarySearch;
        }
        if (!z) {
            binarySearch++;
        }
        return binarySearch;
    }

    Comparator<Object> unsafeComparator() {
        return this.comparator;
    }

    RegularImmutableSortedSet<E> getSubSet(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        if (i < i2) {
            return new RegularImmutableSortedSet(this.elements.subList(i, i2), this.comparator);
        }
        return ImmutableSortedSet.emptySet(this.comparator);
    }

    int indexOf(@NullableDecl Object obj) {
        if (obj == null) {
            return -1;
        }
        try {
            int binarySearch = Collections.binarySearch(this.elements, obj, unsafeComparator());
            if (binarySearch < 0) {
                binarySearch = -1;
            }
            return binarySearch;
        } catch (ClassCastException unused) {
            return -1;
        }
    }

    public ImmutableList<E> asList() {
        return this.elements;
    }

    ImmutableSortedSet<E> createDescendingSet() {
        Comparator reverseOrder = Collections.reverseOrder(this.comparator);
        if (isEmpty()) {
            return ImmutableSortedSet.emptySet(reverseOrder);
        }
        return new RegularImmutableSortedSet(this.elements.reverse(), reverseOrder);
    }
}
