package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection.Builder;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V>, Serializable {
    private static final int ABSENT = -1;
    private static final int ENDPOINT = -2;
    private transient Set<Entry<K, V>> entrySet;
    @NullableDecl
    private transient int firstInInsertionOrder;
    private transient int[] hashTableKToV;
    private transient int[] hashTableVToK;
    @RetainedWith
    @MonotonicNonNullDecl
    private transient BiMap<V, K> inverse;
    private transient Set<K> keySet;
    transient K[] keys;
    @NullableDecl
    private transient int lastInInsertionOrder;
    transient int modCount;
    private transient int[] nextInBucketKToV;
    private transient int[] nextInBucketVToK;
    private transient int[] nextInInsertionOrder;
    private transient int[] prevInInsertionOrder;
    transient int size;
    private transient Set<V> valueSet;
    transient V[] values;

    static abstract class View<K, V, T> extends AbstractSet<T> {
        final HashBiMap<K, V> biMap;

        abstract T forEntry(int i);

        View(HashBiMap<K, V> hashBiMap) {
            this.biMap = hashBiMap;
        }

        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private int expectedModCount = View.this.biMap.modCount;
                private int index = View.this.biMap.firstInInsertionOrder;
                private int indexToRemove = -1;
                private int remaining = View.this.biMap.size;

                private void checkForComodification() {
                    if (View.this.biMap.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }

                public boolean hasNext() {
                    checkForComodification();
                    return this.index != -2 && this.remaining > 0;
                }

                public T next() {
                    if (hasNext()) {
                        T forEntry = View.this.forEntry(this.index);
                        this.indexToRemove = this.index;
                        this.index = View.this.biMap.nextInInsertionOrder[this.index];
                        this.remaining--;
                        return forEntry;
                    }
                    throw new NoSuchElementException();
                }

                public void remove() {
                    checkForComodification();
                    CollectPreconditions.checkRemove(this.indexToRemove != -1);
                    View.this.biMap.removeEntry(this.indexToRemove);
                    if (this.index == View.this.biMap.size) {
                        this.index = this.indexToRemove;
                    }
                    this.indexToRemove = -1;
                    this.expectedModCount = View.this.biMap.modCount;
                }
            };
        }

        public int size() {
            return this.biMap.size;
        }

        public void clear() {
            this.biMap.clear();
        }
    }

    final class EntryForKey extends AbstractMapEntry<K, V> {
        int index;
        @NullableDecl
        final K key;

        EntryForKey(int i) {
            this.key = HashBiMap.this.keys[i];
            this.index = i;
        }

        void updateIndex() {
            int i = this.index;
            if (i == -1 || i > HashBiMap.this.size || !Objects.equal(HashBiMap.this.keys[this.index], this.key)) {
                this.index = HashBiMap.this.findEntryByKey(this.key);
            }
        }

        public K getKey() {
            return this.key;
        }

        @NullableDecl
        public V getValue() {
            updateIndex();
            return this.index == -1 ? null : HashBiMap.this.values[this.index];
        }

        public V setValue(V v) {
            updateIndex();
            if (this.index == -1) {
                return HashBiMap.this.put(this.key, v);
            }
            V v2 = HashBiMap.this.values[this.index];
            if (Objects.equal(v2, v)) {
                return v;
            }
            HashBiMap.this.replaceValueInEntry(this.index, v, false);
            return v2;
        }
    }

    static final class EntryForValue<K, V> extends AbstractMapEntry<V, K> {
        final HashBiMap<K, V> biMap;
        int index;
        final V value;

        EntryForValue(HashBiMap<K, V> hashBiMap, int i) {
            this.biMap = hashBiMap;
            this.value = hashBiMap.values[i];
            this.index = i;
        }

        private void updateIndex() {
            int i = this.index;
            if (i == -1 || i > this.biMap.size || !Objects.equal(this.value, this.biMap.values[this.index])) {
                this.index = this.biMap.findEntryByValue(this.value);
            }
        }

        public V getKey() {
            return this.value;
        }

        public K getValue() {
            updateIndex();
            return this.index == -1 ? null : this.biMap.keys[this.index];
        }

        public K setValue(K k) {
            updateIndex();
            if (this.index == -1) {
                return this.biMap.putInverse(this.value, k, false);
            }
            K k2 = this.biMap.keys[this.index];
            if (Objects.equal(k2, k)) {
                return k;
            }
            this.biMap.replaceKeyInEntry(this.index, k, false);
            return k2;
        }
    }

    final class EntrySet extends View<K, V, Entry<K, V>> {
        EntrySet() {
            super(HashBiMap.this);
        }

        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            obj = entry.getValue();
            int findEntryByKey = HashBiMap.this.findEntryByKey(key);
            if (findEntryByKey == -1 || !Objects.equal(obj, HashBiMap.this.values[findEntryByKey])) {
                return false;
            }
            return true;
        }

        @CanIgnoreReturnValue
        public boolean remove(@NullableDecl Object obj) {
            if (obj instanceof Entry) {
                Entry entry = (Entry) obj;
                Object key = entry.getKey();
                obj = entry.getValue();
                int smearedHash = Hashing.smearedHash(key);
                int findEntryByKey = HashBiMap.this.findEntryByKey(key, smearedHash);
                if (findEntryByKey != -1 && Objects.equal(obj, HashBiMap.this.values[findEntryByKey])) {
                    HashBiMap.this.removeEntryKeyHashKnown(findEntryByKey, smearedHash);
                    return true;
                }
            }
            return false;
        }

        Entry<K, V> forEntry(int i) {
            return new EntryForKey(i);
        }
    }

    static class Inverse<K, V> extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {
        private final HashBiMap<K, V> forward;
        private transient Set<Entry<V, K>> inverseEntrySet;

        Inverse(HashBiMap<K, V> hashBiMap) {
            this.forward = hashBiMap;
        }

        public int size() {
            return this.forward.size;
        }

        public boolean containsKey(@NullableDecl Object obj) {
            return this.forward.containsValue(obj);
        }

        @NullableDecl
        public K get(@NullableDecl Object obj) {
            return this.forward.getInverse(obj);
        }

        public boolean containsValue(@NullableDecl Object obj) {
            return this.forward.containsKey(obj);
        }

        @NullableDecl
        @CanIgnoreReturnValue
        public K put(@NullableDecl V v, @NullableDecl K k) {
            return this.forward.putInverse(v, k, false);
        }

        @NullableDecl
        @CanIgnoreReturnValue
        public K forcePut(@NullableDecl V v, @NullableDecl K k) {
            return this.forward.putInverse(v, k, true);
        }

        public BiMap<K, V> inverse() {
            return this.forward;
        }

        @NullableDecl
        @CanIgnoreReturnValue
        public K remove(@NullableDecl Object obj) {
            return this.forward.removeInverse(obj);
        }

        public void clear() {
            this.forward.clear();
        }

        public Set<V> keySet() {
            return this.forward.values();
        }

        public Set<K> values() {
            return this.forward.keySet();
        }

        public Set<Entry<V, K>> entrySet() {
            Set<Entry<V, K>> set = this.inverseEntrySet;
            if (set != null) {
                return set;
            }
            set = new InverseEntrySet(this.forward);
            this.inverseEntrySet = set;
            return set;
        }

        @GwtIncompatible("serialization")
        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.forward.inverse = this;
        }
    }

    static class InverseEntrySet<K, V> extends View<K, V, Entry<V, K>> {
        InverseEntrySet(HashBiMap<K, V> hashBiMap) {
            super(hashBiMap);
        }

        public boolean contains(@NullableDecl Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            obj = entry.getValue();
            int findEntryByValue = this.biMap.findEntryByValue(key);
            if (findEntryByValue == -1 || !Objects.equal(this.biMap.keys[findEntryByValue], obj)) {
                return false;
            }
            return true;
        }

        public boolean remove(Object obj) {
            if (obj instanceof Entry) {
                Entry entry = (Entry) obj;
                Object key = entry.getKey();
                obj = entry.getValue();
                int smearedHash = Hashing.smearedHash(key);
                int findEntryByValue = this.biMap.findEntryByValue(key, smearedHash);
                if (findEntryByValue != -1 && Objects.equal(this.biMap.keys[findEntryByValue], obj)) {
                    this.biMap.removeEntryValueHashKnown(findEntryByValue, smearedHash);
                    return true;
                }
            }
            return false;
        }

        Entry<V, K> forEntry(int i) {
            return new EntryForValue(this.biMap, i);
        }
    }

    final class KeySet extends View<K, V, K> {
        KeySet() {
            super(HashBiMap.this);
        }

        K forEntry(int i) {
            return HashBiMap.this.keys[i];
        }

        public boolean contains(@NullableDecl Object obj) {
            return HashBiMap.this.containsKey(obj);
        }

        public boolean remove(@NullableDecl Object obj) {
            int smearedHash = Hashing.smearedHash(obj);
            int findEntryByKey = HashBiMap.this.findEntryByKey(obj, smearedHash);
            if (findEntryByKey == -1) {
                return false;
            }
            HashBiMap.this.removeEntryKeyHashKnown(findEntryByKey, smearedHash);
            return true;
        }
    }

    final class ValueSet extends View<K, V, V> {
        ValueSet() {
            super(HashBiMap.this);
        }

        V forEntry(int i) {
            return HashBiMap.this.values[i];
        }

        public boolean contains(@NullableDecl Object obj) {
            return HashBiMap.this.containsValue(obj);
        }

        public boolean remove(@NullableDecl Object obj) {
            int smearedHash = Hashing.smearedHash(obj);
            int findEntryByValue = HashBiMap.this.findEntryByValue(obj, smearedHash);
            if (findEntryByValue == -1) {
                return false;
            }
            HashBiMap.this.removeEntryValueHashKnown(findEntryByValue, smearedHash);
            return true;
        }
    }

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    public static <K, V> HashBiMap<K, V> create(int i) {
        return new HashBiMap(i);
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> create = create(map.size());
        create.putAll(map);
        return create;
    }

    private HashBiMap(int i) {
        init(i);
    }

    void init(int i) {
        CollectPreconditions.checkNonnegative(i, "expectedSize");
        int closedTableSize = Hashing.closedTableSize(i, 1.0d);
        this.size = 0;
        this.keys = new Object[i];
        this.values = new Object[i];
        this.hashTableKToV = createFilledWithAbsent(closedTableSize);
        this.hashTableVToK = createFilledWithAbsent(closedTableSize);
        this.nextInBucketKToV = createFilledWithAbsent(i);
        this.nextInBucketVToK = createFilledWithAbsent(i);
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.prevInInsertionOrder = createFilledWithAbsent(i);
        this.nextInInsertionOrder = createFilledWithAbsent(i);
    }

    private static int[] createFilledWithAbsent(int i) {
        int[] iArr = new int[i];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private static int[] expandAndFillWithAbsent(int[] iArr, int i) {
        int length = iArr.length;
        iArr = Arrays.copyOf(iArr, i);
        Arrays.fill(iArr, length, i, -1);
        return iArr;
    }

    public int size() {
        return this.size;
    }

    private void ensureCapacity(int i) {
        int expandedCapacity;
        int[] iArr = this.nextInBucketKToV;
        if (iArr.length < i) {
            expandedCapacity = Builder.expandedCapacity(iArr.length, i);
            this.keys = Arrays.copyOf(this.keys, expandedCapacity);
            this.values = Arrays.copyOf(this.values, expandedCapacity);
            this.nextInBucketKToV = expandAndFillWithAbsent(this.nextInBucketKToV, expandedCapacity);
            this.nextInBucketVToK = expandAndFillWithAbsent(this.nextInBucketVToK, expandedCapacity);
            this.prevInInsertionOrder = expandAndFillWithAbsent(this.prevInInsertionOrder, expandedCapacity);
            this.nextInInsertionOrder = expandAndFillWithAbsent(this.nextInInsertionOrder, expandedCapacity);
        }
        if (this.hashTableKToV.length < i) {
            i = Hashing.closedTableSize(i, 1.0d);
            this.hashTableKToV = createFilledWithAbsent(i);
            this.hashTableVToK = createFilledWithAbsent(i);
            for (i = 0; i < this.size; i++) {
                expandedCapacity = bucket(Hashing.smearedHash(this.keys[i]));
                int[] iArr2 = this.nextInBucketKToV;
                int[] iArr3 = this.hashTableKToV;
                iArr2[i] = iArr3[expandedCapacity];
                iArr3[expandedCapacity] = i;
                expandedCapacity = bucket(Hashing.smearedHash(this.values[i]));
                iArr2 = this.nextInBucketVToK;
                iArr3 = this.hashTableVToK;
                iArr2[i] = iArr3[expandedCapacity];
                iArr3[expandedCapacity] = i;
            }
        }
    }

    private int bucket(int i) {
        return i & (this.hashTableKToV.length - 1);
    }

    int findEntryByKey(@NullableDecl Object obj) {
        return findEntryByKey(obj, Hashing.smearedHash(obj));
    }

    int findEntryByKey(@NullableDecl Object obj, int i) {
        return findEntry(obj, i, this.hashTableKToV, this.nextInBucketKToV, this.keys);
    }

    int findEntryByValue(@NullableDecl Object obj) {
        return findEntryByValue(obj, Hashing.smearedHash(obj));
    }

    int findEntryByValue(@NullableDecl Object obj, int i) {
        return findEntry(obj, i, this.hashTableVToK, this.nextInBucketVToK, this.values);
    }

    int findEntry(@NullableDecl Object obj, int i, int[] iArr, int[] iArr2, Object[] objArr) {
        i = iArr[bucket(i)];
        while (i != -1) {
            if (Objects.equal(objArr[i], obj)) {
                return i;
            }
            i = iArr2[i];
        }
        return -1;
    }

    public boolean containsKey(@NullableDecl Object obj) {
        return findEntryByKey(obj) != -1;
    }

    public boolean containsValue(@NullableDecl Object obj) {
        return findEntryByValue(obj) != -1;
    }

    @NullableDecl
    public V get(@NullableDecl Object obj) {
        int findEntryByKey = findEntryByKey(obj);
        if (findEntryByKey == -1) {
            return null;
        }
        return this.values[findEntryByKey];
    }

    @NullableDecl
    K getInverse(@NullableDecl Object obj) {
        int findEntryByValue = findEntryByValue(obj);
        if (findEntryByValue == -1) {
            return null;
        }
        return this.keys[findEntryByValue];
    }

    @CanIgnoreReturnValue
    public V put(@NullableDecl K k, @NullableDecl V v) {
        return put(k, v, false);
    }

    @NullableDecl
    V put(@NullableDecl K k, @NullableDecl V v, boolean z) {
        int smearedHash = Hashing.smearedHash(k);
        int findEntryByKey = findEntryByKey(k, smearedHash);
        if (findEntryByKey != -1) {
            V v2 = this.values[findEntryByKey];
            if (Objects.equal(v2, v)) {
                return v;
            }
            replaceValueInEntry(findEntryByKey, v, z);
            return v2;
        }
        findEntryByKey = Hashing.smearedHash(v);
        int findEntryByValue = findEntryByValue(v, findEntryByKey);
        if (!z) {
            Preconditions.checkArgument(findEntryByValue == -1, "Value already present: %s", (Object) v);
        } else if (findEntryByValue != -1) {
            removeEntryValueHashKnown(findEntryByValue, findEntryByKey);
        }
        ensureCapacity(this.size + 1);
        Object[] objArr = this.keys;
        int i = this.size;
        objArr[i] = k;
        this.values[i] = v;
        insertIntoTableKToV(i, smearedHash);
        insertIntoTableVToK(this.size, findEntryByKey);
        setSucceeds(this.lastInInsertionOrder, this.size);
        setSucceeds(this.size, -2);
        this.size++;
        this.modCount++;
        return null;
    }

    @NullableDecl
    @CanIgnoreReturnValue
    public V forcePut(@NullableDecl K k, @NullableDecl V v) {
        return put(k, v, true);
    }

    @NullableDecl
    K putInverse(@NullableDecl V v, @NullableDecl K k, boolean z) {
        int smearedHash = Hashing.smearedHash(v);
        int findEntryByValue = findEntryByValue(v, smearedHash);
        if (findEntryByValue != -1) {
            K k2 = this.keys[findEntryByValue];
            if (Objects.equal(k2, k)) {
                return k;
            }
            replaceKeyInEntry(findEntryByValue, k, z);
            return k2;
        }
        findEntryByValue = this.lastInInsertionOrder;
        int smearedHash2 = Hashing.smearedHash(k);
        int findEntryByKey = findEntryByKey(k, smearedHash2);
        if (!z) {
            Preconditions.checkArgument(findEntryByKey == -1, "Key already present: %s", (Object) k);
        } else if (findEntryByKey != -1) {
            findEntryByValue = this.prevInInsertionOrder[findEntryByKey];
            removeEntryKeyHashKnown(findEntryByKey, smearedHash2);
        }
        ensureCapacity(this.size + 1);
        Object[] objArr = this.keys;
        int i = this.size;
        objArr[i] = k;
        this.values[i] = v;
        insertIntoTableKToV(i, smearedHash2);
        insertIntoTableVToK(this.size, smearedHash);
        int i2 = findEntryByValue == -2 ? this.firstInInsertionOrder : this.nextInInsertionOrder[findEntryByValue];
        setSucceeds(findEntryByValue, this.size);
        setSucceeds(this.size, i2);
        this.size++;
        this.modCount++;
        return null;
    }

    private void setSucceeds(int i, int i2) {
        if (i == -2) {
            this.firstInInsertionOrder = i2;
        } else {
            this.nextInInsertionOrder[i] = i2;
        }
        if (i2 == -2) {
            this.lastInInsertionOrder = i;
        } else {
            this.prevInInsertionOrder[i2] = i;
        }
    }

    private void insertIntoTableKToV(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        i2 = bucket(i2);
        int[] iArr = this.nextInBucketKToV;
        int[] iArr2 = this.hashTableKToV;
        iArr[i] = iArr2[i2];
        iArr2[i2] = i;
    }

    private void insertIntoTableVToK(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        i2 = bucket(i2);
        int[] iArr = this.nextInBucketVToK;
        int[] iArr2 = this.hashTableVToK;
        iArr[i] = iArr2[i2];
        iArr2[i2] = i;
    }

    private void deleteFromTableKToV(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        i2 = bucket(i2);
        int[] iArr = this.hashTableKToV;
        if (iArr[i2] == i) {
            int[] iArr2 = this.nextInBucketKToV;
            iArr[i2] = iArr2[i];
            iArr2[i] = -1;
            return;
        }
        i2 = iArr[i2];
        int i3 = this.nextInBucketKToV[i2];
        while (true) {
            int i4 = i3;
            i3 = i2;
            i2 = i4;
            if (i2 == -1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected to find entry with key ");
                stringBuilder.append(this.keys[i]);
                throw new AssertionError(stringBuilder.toString());
            } else if (i2 == i) {
                int[] iArr3 = this.nextInBucketKToV;
                iArr3[i3] = iArr3[i];
                iArr3[i] = -1;
                return;
            } else {
                i3 = this.nextInBucketKToV[i2];
            }
        }
    }

    private void deleteFromTableVToK(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        i2 = bucket(i2);
        int[] iArr = this.hashTableVToK;
        if (iArr[i2] == i) {
            int[] iArr2 = this.nextInBucketVToK;
            iArr[i2] = iArr2[i];
            iArr2[i] = -1;
            return;
        }
        i2 = iArr[i2];
        int i3 = this.nextInBucketVToK[i2];
        while (true) {
            int i4 = i3;
            i3 = i2;
            i2 = i4;
            if (i2 == -1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected to find entry with value ");
                stringBuilder.append(this.values[i]);
                throw new AssertionError(stringBuilder.toString());
            } else if (i2 == i) {
                int[] iArr3 = this.nextInBucketVToK;
                iArr3[i3] = iArr3[i];
                iArr3[i] = -1;
                return;
            } else {
                i3 = this.nextInBucketVToK[i2];
            }
        }
    }

    private void replaceValueInEntry(int i, @NullableDecl V v, boolean z) {
        Preconditions.checkArgument(i != -1);
        int smearedHash = Hashing.smearedHash(v);
        int findEntryByValue = findEntryByValue(v, smearedHash);
        if (findEntryByValue != -1) {
            if (z) {
                removeEntryValueHashKnown(findEntryByValue, smearedHash);
                if (i == this.size) {
                    i = findEntryByValue;
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value already present in map: ");
                stringBuilder.append(v);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        deleteFromTableVToK(i, Hashing.smearedHash(this.values[i]));
        this.values[i] = v;
        insertIntoTableVToK(i, smearedHash);
    }

    private void replaceKeyInEntry(int i, @NullableDecl K k, boolean z) {
        int i2;
        int i3;
        Preconditions.checkArgument(i != -1);
        int smearedHash = Hashing.smearedHash(k);
        int findEntryByKey = findEntryByKey(k, smearedHash);
        int i4 = this.lastInInsertionOrder;
        if (findEntryByKey == -1) {
            i2 = i4;
            i3 = -2;
        } else if (z) {
            i2 = this.prevInInsertionOrder[findEntryByKey];
            i3 = this.nextInInsertionOrder[findEntryByKey];
            removeEntryKeyHashKnown(findEntryByKey, smearedHash);
            if (i == this.size) {
                i = findEntryByKey;
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Key already present in map: ");
            stringBuilder.append(k);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (i2 == i) {
            i2 = this.prevInInsertionOrder[i];
        } else if (i2 == this.size) {
            i2 = findEntryByKey;
        }
        if (i3 == i) {
            findEntryByKey = this.nextInInsertionOrder[i];
        } else if (i3 != this.size) {
            findEntryByKey = i3;
        }
        setSucceeds(this.prevInInsertionOrder[i], this.nextInInsertionOrder[i]);
        deleteFromTableKToV(i, Hashing.smearedHash(this.keys[i]));
        this.keys[i] = k;
        insertIntoTableKToV(i, Hashing.smearedHash(k));
        setSucceeds(i2, i);
        setSucceeds(i, findEntryByKey);
    }

    @NullableDecl
    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object obj) {
        int smearedHash = Hashing.smearedHash(obj);
        int findEntryByKey = findEntryByKey(obj, smearedHash);
        if (findEntryByKey == -1) {
            return null;
        }
        V v = this.values[findEntryByKey];
        removeEntryKeyHashKnown(findEntryByKey, smearedHash);
        return v;
    }

    @NullableDecl
    K removeInverse(@NullableDecl Object obj) {
        int smearedHash = Hashing.smearedHash(obj);
        int findEntryByValue = findEntryByValue(obj, smearedHash);
        if (findEntryByValue == -1) {
            return null;
        }
        K k = this.keys[findEntryByValue];
        removeEntryValueHashKnown(findEntryByValue, smearedHash);
        return k;
    }

    void removeEntry(int i) {
        removeEntryKeyHashKnown(i, Hashing.smearedHash(this.keys[i]));
    }

    private void removeEntry(int i, int i2, int i3) {
        Preconditions.checkArgument(i != -1);
        deleteFromTableKToV(i, i2);
        deleteFromTableVToK(i, i3);
        setSucceeds(this.prevInInsertionOrder[i], this.nextInInsertionOrder[i]);
        moveEntryToIndex(this.size - 1, i);
        Object[] objArr = this.keys;
        i2 = this.size;
        objArr[i2 - 1] = null;
        this.values[i2 - 1] = null;
        this.size = i2 - 1;
        this.modCount++;
    }

    void removeEntryKeyHashKnown(int i, int i2) {
        removeEntry(i, i2, Hashing.smearedHash(this.values[i]));
    }

    void removeEntryValueHashKnown(int i, int i2) {
        removeEntry(i, Hashing.smearedHash(this.keys[i]), i2);
    }

    private void moveEntryToIndex(int i, int i2) {
        if (i != i2) {
            int i3;
            int i4 = this.prevInInsertionOrder[i];
            int i5 = this.nextInInsertionOrder[i];
            setSucceeds(i4, i2);
            setSucceeds(i2, i5);
            Object[] objArr = this.keys;
            Object obj = objArr[i];
            Object[] objArr2 = this.values;
            Object obj2 = objArr2[i];
            objArr[i2] = obj;
            objArr2[i2] = obj2;
            i4 = bucket(Hashing.smearedHash(obj));
            int[] iArr = this.hashTableKToV;
            if (iArr[i4] == i) {
                iArr[i4] = i2;
            } else {
                i4 = iArr[i4];
                i5 = this.nextInBucketKToV[i4];
                while (true) {
                    i3 = i5;
                    i5 = i4;
                    i4 = i3;
                    if (i4 == i) {
                        break;
                    }
                    i5 = this.nextInBucketKToV[i4];
                }
                this.nextInBucketKToV[i5] = i2;
            }
            int[] iArr2 = this.nextInBucketKToV;
            iArr2[i2] = iArr2[i];
            iArr2[i] = -1;
            i4 = bucket(Hashing.smearedHash(obj2));
            int[] iArr3 = this.hashTableVToK;
            if (iArr3[i4] == i) {
                iArr3[i4] = i2;
            } else {
                i4 = iArr3[i4];
                int i6 = this.nextInBucketVToK[i4];
                while (true) {
                    i3 = i6;
                    i6 = i4;
                    i4 = i3;
                    if (i4 == i) {
                        break;
                    }
                    i6 = this.nextInBucketVToK[i4];
                }
                this.nextInBucketVToK[i6] = i2;
            }
            iArr2 = this.nextInBucketVToK;
            iArr2[i2] = iArr2[i];
            iArr2[i] = -1;
        }
    }

    public void clear() {
        Arrays.fill(this.keys, 0, this.size, null);
        Arrays.fill(this.values, 0, this.size, null);
        Arrays.fill(this.hashTableKToV, -1);
        Arrays.fill(this.hashTableVToK, -1);
        Arrays.fill(this.nextInBucketKToV, 0, this.size, -1);
        Arrays.fill(this.nextInBucketVToK, 0, this.size, -1);
        Arrays.fill(this.prevInInsertionOrder, 0, this.size, -1);
        Arrays.fill(this.nextInInsertionOrder, 0, this.size, -1);
        this.size = 0;
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.modCount++;
    }

    public Set<K> keySet() {
        Set<K> set = this.keySet;
        if (set != null) {
            return set;
        }
        set = new KeySet();
        this.keySet = set;
        return set;
    }

    public Set<V> values() {
        Set<V> set = this.valueSet;
        if (set != null) {
            return set;
        }
        set = new ValueSet();
        this.valueSet = set;
        return set;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = this.entrySet;
        if (set != null) {
            return set;
        }
        set = new EntrySet();
        this.entrySet = set;
        return set;
    }

    public BiMap<V, K> inverse() {
        BiMap<V, K> biMap = this.inverse;
        if (biMap != null) {
            return biMap;
        }
        biMap = new Inverse(this);
        this.inverse = biMap;
        return biMap;
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMap(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readCount = Serialization.readCount(objectInputStream);
        init(16);
        Serialization.populateMap(this, objectInputStream, readCount);
    }
}
