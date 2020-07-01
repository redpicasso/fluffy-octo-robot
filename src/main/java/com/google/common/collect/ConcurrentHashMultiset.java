package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E> implements Serializable {
    private static final long serialVersionUID = 1;
    private final transient ConcurrentMap<E, AtomicInteger> countMap;

    private static class FieldSettersHolder {
        static final FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");

        private FieldSettersHolder() {
        }
    }

    private class EntrySet extends EntrySet {
        private EntrySet() {
            super();
        }

        /* synthetic */ EntrySet(ConcurrentHashMultiset concurrentHashMultiset, AnonymousClass1 anonymousClass1) {
            this();
        }

        ConcurrentHashMultiset<E> multiset() {
            return ConcurrentHashMultiset.this;
        }

        public Object[] toArray() {
            return snapshot().toArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return snapshot().toArray(tArr);
        }

        private List<Entry<E>> snapshot() {
            Object newArrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(size());
            Iterators.addAll(newArrayListWithExpectedSize, iterator());
            return newArrayListWithExpectedSize;
        }
    }

    public static <E> ConcurrentHashMultiset<E> create() {
        return new ConcurrentHashMultiset(new ConcurrentHashMap());
    }

    public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> iterable) {
        Object create = create();
        Iterables.addAll(create, iterable);
        return create;
    }

    @Beta
    public static <E> ConcurrentHashMultiset<E> create(ConcurrentMap<E, AtomicInteger> concurrentMap) {
        return new ConcurrentHashMultiset(concurrentMap);
    }

    @VisibleForTesting
    ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> concurrentMap) {
        Preconditions.checkArgument(concurrentMap.isEmpty(), "the backing map (%s) must be empty", (Object) concurrentMap);
        this.countMap = concurrentMap;
    }

    public int count(@NullableDecl Object obj) {
        AtomicInteger atomicInteger = (AtomicInteger) Maps.safeGet(this.countMap, obj);
        if (atomicInteger == null) {
            return 0;
        }
        return atomicInteger.get();
    }

    public int size() {
        long j = 0;
        for (AtomicInteger atomicInteger : this.countMap.values()) {
            j += (long) atomicInteger.get();
        }
        return Ints.saturatedCast(j);
    }

    public Object[] toArray() {
        return snapshot().toArray();
    }

    public <T> T[] toArray(T[] tArr) {
        return snapshot().toArray(tArr);
    }

    private List<E> snapshot() {
        List<E> newArrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(size());
        for (Entry entry : entrySet()) {
            Object element = entry.getElement();
            for (int count = entry.getCount(); count > 0; count--) {
                newArrayListWithExpectedSize.add(element);
            }
        }
        return newArrayListWithExpectedSize;
    }

    /* JADX WARNING: Missing block: B:18:0x005a, code:
            r2 = new java.util.concurrent.atomic.AtomicInteger(r6);
     */
    /* JADX WARNING: Missing block: B:19:0x0065, code:
            if (r4.countMap.putIfAbsent(r5, r2) == null) goto L_0x006f;
     */
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    public int add(E r5, int r6) {
        /*
        r4 = this;
        com.google.common.base.Preconditions.checkNotNull(r5);
        if (r6 != 0) goto L_0x000a;
    L_0x0005:
        r5 = r4.count(r5);
        return r5;
    L_0x000a:
        r0 = "occurences";
        com.google.common.collect.CollectPreconditions.checkPositive(r6, r0);
    L_0x000f:
        r0 = r4.countMap;
        r0 = com.google.common.collect.Maps.safeGet(r0, r5);
        r0 = (java.util.concurrent.atomic.AtomicInteger) r0;
        r1 = 0;
        if (r0 != 0) goto L_0x002a;
    L_0x001a:
        r0 = r4.countMap;
        r2 = new java.util.concurrent.atomic.AtomicInteger;
        r2.<init>(r6);
        r0 = r0.putIfAbsent(r5, r2);
        r0 = (java.util.concurrent.atomic.AtomicInteger) r0;
        if (r0 != 0) goto L_0x002a;
    L_0x0029:
        return r1;
    L_0x002a:
        r2 = r0.get();
        if (r2 == 0) goto L_0x005a;
    L_0x0030:
        r3 = com.google.common.math.IntMath.checkedAdd(r2, r6);	 Catch:{ ArithmeticException -> 0x003b }
        r3 = r0.compareAndSet(r2, r3);	 Catch:{ ArithmeticException -> 0x003b }
        if (r3 == 0) goto L_0x002a;
    L_0x003a:
        return r2;
    L_0x003b:
        r5 = new java.lang.IllegalArgumentException;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "Overflow adding ";
        r0.append(r1);
        r0.append(r6);
        r6 = " occurrences to a count of ";
        r0.append(r6);
        r0.append(r2);
        r6 = r0.toString();
        r5.<init>(r6);
        throw r5;
    L_0x005a:
        r2 = new java.util.concurrent.atomic.AtomicInteger;
        r2.<init>(r6);
        r3 = r4.countMap;
        r3 = r3.putIfAbsent(r5, r2);
        if (r3 == 0) goto L_0x006f;
    L_0x0067:
        r3 = r4.countMap;
        r0 = r3.replace(r5, r0, r2);
        if (r0 == 0) goto L_0x000f;
    L_0x006f:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ConcurrentHashMultiset.add(java.lang.Object, int):int");
    }

    @CanIgnoreReturnValue
    public int remove(@NullableDecl Object obj, int i) {
        if (i == 0) {
            return count(obj);
        }
        CollectPreconditions.checkPositive(i, "occurences");
        AtomicInteger atomicInteger = (AtomicInteger) Maps.safeGet(this.countMap, obj);
        if (atomicInteger == null) {
            return 0;
        }
        int i2;
        int max;
        do {
            i2 = atomicInteger.get();
            if (i2 == 0) {
                return 0;
            }
            max = Math.max(0, i2 - i);
        } while (!atomicInteger.compareAndSet(i2, max));
        if (max == 0) {
            this.countMap.remove(obj, atomicInteger);
        }
        return i2;
    }

    @CanIgnoreReturnValue
    public boolean removeExactly(@NullableDecl Object obj, int i) {
        if (i == 0) {
            return true;
        }
        CollectPreconditions.checkPositive(i, "occurences");
        AtomicInteger atomicInteger = (AtomicInteger) Maps.safeGet(this.countMap, obj);
        if (atomicInteger == null) {
            return false;
        }
        int i2;
        int i3;
        do {
            i3 = atomicInteger.get();
            if (i3 < i) {
                return false;
            }
            i2 = i3 - i;
        } while (!atomicInteger.compareAndSet(i3, i2));
        if (i2 == 0) {
            this.countMap.remove(obj, atomicInteger);
        }
        return true;
    }

    /* JADX WARNING: Missing block: B:10:0x002c, code:
            if (r6 != 0) goto L_0x002f;
     */
    /* JADX WARNING: Missing block: B:11:0x002e, code:
            return 0;
     */
    /* JADX WARNING: Missing block: B:12:0x002f, code:
            r2 = new java.util.concurrent.atomic.AtomicInteger(r6);
     */
    /* JADX WARNING: Missing block: B:13:0x003a, code:
            if (r4.countMap.putIfAbsent(r5, r2) == null) goto L_0x0044;
     */
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    public int setCount(E r5, int r6) {
        /*
        r4 = this;
        com.google.common.base.Preconditions.checkNotNull(r5);
        r0 = "count";
        com.google.common.collect.CollectPreconditions.checkNonnegative(r6, r0);
    L_0x0008:
        r0 = r4.countMap;
        r0 = com.google.common.collect.Maps.safeGet(r0, r5);
        r0 = (java.util.concurrent.atomic.AtomicInteger) r0;
        r1 = 0;
        if (r0 != 0) goto L_0x0026;
    L_0x0013:
        if (r6 != 0) goto L_0x0016;
    L_0x0015:
        return r1;
    L_0x0016:
        r0 = r4.countMap;
        r2 = new java.util.concurrent.atomic.AtomicInteger;
        r2.<init>(r6);
        r0 = r0.putIfAbsent(r5, r2);
        r0 = (java.util.concurrent.atomic.AtomicInteger) r0;
        if (r0 != 0) goto L_0x0026;
    L_0x0025:
        return r1;
    L_0x0026:
        r2 = r0.get();
        if (r2 != 0) goto L_0x0045;
    L_0x002c:
        if (r6 != 0) goto L_0x002f;
    L_0x002e:
        return r1;
    L_0x002f:
        r2 = new java.util.concurrent.atomic.AtomicInteger;
        r2.<init>(r6);
        r3 = r4.countMap;
        r3 = r3.putIfAbsent(r5, r2);
        if (r3 == 0) goto L_0x0044;
    L_0x003c:
        r3 = r4.countMap;
        r0 = r3.replace(r5, r0, r2);
        if (r0 == 0) goto L_0x0008;
    L_0x0044:
        return r1;
    L_0x0045:
        r3 = r0.compareAndSet(r2, r6);
        if (r3 == 0) goto L_0x0026;
    L_0x004b:
        if (r6 != 0) goto L_0x0052;
    L_0x004d:
        r6 = r4.countMap;
        r6.remove(r5, r0);
    L_0x0052:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ConcurrentHashMultiset.setCount(java.lang.Object, int):int");
    }

    @CanIgnoreReturnValue
    public boolean setCount(E e, int i, int i2) {
        Preconditions.checkNotNull(e);
        CollectPreconditions.checkNonnegative(i, "oldCount");
        CollectPreconditions.checkNonnegative(i2, "newCount");
        AtomicInteger atomicInteger = (AtomicInteger) Maps.safeGet(this.countMap, e);
        boolean z = false;
        if (atomicInteger != null) {
            int i3 = atomicInteger.get();
            if (i3 == i) {
                if (i3 == 0) {
                    if (i2 == 0) {
                        this.countMap.remove(e, atomicInteger);
                        return true;
                    }
                    AtomicInteger atomicInteger2 = new AtomicInteger(i2);
                    if (this.countMap.putIfAbsent(e, atomicInteger2) == null || this.countMap.replace(e, atomicInteger, atomicInteger2)) {
                        z = true;
                    }
                    return z;
                } else if (atomicInteger.compareAndSet(i3, i2)) {
                    if (i2 == 0) {
                        this.countMap.remove(e, atomicInteger);
                    }
                    return true;
                }
            }
            return false;
        } else if (i != 0) {
            return false;
        } else {
            if (i2 == 0) {
                return true;
            }
            if (this.countMap.putIfAbsent(e, new AtomicInteger(i2)) == null) {
                z = true;
            }
            return z;
        }
    }

    Set<E> createElementSet() {
        final Set keySet = this.countMap.keySet();
        return new ForwardingSet<E>() {
            protected Set<E> delegate() {
                return keySet;
            }

            public boolean contains(@NullableDecl Object obj) {
                return obj != null && Collections2.safeContains(keySet, obj);
            }

            public boolean containsAll(Collection<?> collection) {
                return standardContainsAll(collection);
            }

            public boolean remove(Object obj) {
                return obj != null && Collections2.safeRemove(keySet, obj);
            }

            public boolean removeAll(Collection<?> collection) {
                return standardRemoveAll(collection);
            }
        };
    }

    Iterator<E> elementIterator() {
        throw new AssertionError("should never be called");
    }

    @Deprecated
    public Set<Entry<E>> createEntrySet() {
        return new EntrySet(this, null);
    }

    int distinctElements() {
        return this.countMap.size();
    }

    public boolean isEmpty() {
        return this.countMap.isEmpty();
    }

    Iterator<Entry<E>> entryIterator() {
        final Iterator anonymousClass2 = new AbstractIterator<Entry<E>>() {
            private final Iterator<Map.Entry<E, AtomicInteger>> mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();

            protected Entry<E> computeNext() {
                while (this.mapEntries.hasNext()) {
                    Map.Entry entry = (Map.Entry) this.mapEntries.next();
                    int i = ((AtomicInteger) entry.getValue()).get();
                    if (i != 0) {
                        return Multisets.immutableEntry(entry.getKey(), i);
                    }
                }
                return (Entry) endOfData();
            }
        };
        return new ForwardingIterator<Entry<E>>() {
            @NullableDecl
            private Entry<E> last;

            protected Iterator<Entry<E>> delegate() {
                return anonymousClass2;
            }

            public Entry<E> next() {
                this.last = (Entry) super.next();
                return this.last;
            }

            public void remove() {
                CollectPreconditions.checkRemove(this.last != null);
                ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
                this.last = null;
            }
        };
    }

    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    public void clear() {
        this.countMap.clear();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.countMap);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set((Object) this, (ConcurrentMap) objectInputStream.readObject());
    }
}
