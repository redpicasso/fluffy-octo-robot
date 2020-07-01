package com.google.firebase.database.collection;

import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder.KeyTranslator;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
public class ArraySortedMap<K, V> extends ImmutableSortedMap<K, V> {
    private final Comparator<K> comparator;
    private final K[] keys;
    private final V[] values;

    public static <A, B, C> ArraySortedMap<A, C> buildFrom(List<A> list, Map<B, C> map, KeyTranslator<A, B> keyTranslator, Comparator<A> comparator) {
        Collections.sort(list, comparator);
        int size = list.size();
        Object[] objArr = new Object[size];
        Object[] objArr2 = new Object[size];
        int i = 0;
        for (Object next : list) {
            objArr[i] = next;
            objArr2[i] = map.get(keyTranslator.translate(next));
            i++;
        }
        return new ArraySortedMap(comparator, objArr, objArr2);
    }

    public static <K, V> ArraySortedMap<K, V> fromMap(Map<K, V> map, Comparator<K> comparator) {
        return buildFrom(new ArrayList(map.keySet()), map, Builder.identityTranslator(), comparator);
    }

    public ArraySortedMap(Comparator<K> comparator) {
        this.keys = new Object[0];
        this.values = new Object[0];
        this.comparator = comparator;
    }

    private ArraySortedMap(Comparator<K> comparator, K[] kArr, V[] vArr) {
        this.keys = kArr;
        this.values = vArr;
        this.comparator = comparator;
    }

    public boolean containsKey(K k) {
        return findKey(k) != -1;
    }

    public V get(K k) {
        int findKey = findKey(k);
        return findKey != -1 ? this.values[findKey] : null;
    }

    public ImmutableSortedMap<K, V> remove(K k) {
        int findKey = findKey(k);
        if (findKey == -1) {
            return this;
        }
        return new ArraySortedMap(this.comparator, removeFromArray(this.keys, findKey), removeFromArray(this.values, findKey));
    }

    public ImmutableSortedMap<K, V> insert(K k, V v) {
        int findKey = findKey(k);
        if (findKey == -1) {
            Object[] objArr = this.keys;
            if (objArr.length > 25) {
                Map hashMap = new HashMap(objArr.length + 1);
                findKey = 0;
                while (true) {
                    Object[] objArr2 = this.keys;
                    if (findKey < objArr2.length) {
                        hashMap.put(objArr2[findKey], this.values[findKey]);
                        findKey++;
                    } else {
                        hashMap.put(k, v);
                        return RBTreeSortedMap.fromMap(hashMap, this.comparator);
                    }
                }
            }
            findKey = findKeyOrInsertPosition(k);
            return new ArraySortedMap(this.comparator, addToArray(this.keys, findKey, k), addToArray(this.values, findKey, v));
        } else if (this.keys[findKey] == k && this.values[findKey] == v) {
            return this;
        } else {
            return new ArraySortedMap(this.comparator, replaceInArray(this.keys, findKey, k), replaceInArray(this.values, findKey, v));
        }
    }

    public K getMinKey() {
        Object[] objArr = this.keys;
        return objArr.length > 0 ? objArr[0] : null;
    }

    public K getMaxKey() {
        Object[] objArr = this.keys;
        return objArr.length > 0 ? objArr[objArr.length - 1] : null;
    }

    public int size() {
        return this.keys.length;
    }

    public boolean isEmpty() {
        return this.keys.length == 0;
    }

    public void inOrderTraversal(NodeVisitor<K, V> nodeVisitor) {
        int i = 0;
        while (true) {
            Object[] objArr = this.keys;
            if (i < objArr.length) {
                nodeVisitor.visitEntry(objArr[i], this.values[i]);
                i++;
            } else {
                return;
            }
        }
    }

    private Iterator<Entry<K, V>> iterator(final int i, final boolean z) {
        return new Iterator<Entry<K, V>>() {
            int currentPos = i;

            public boolean hasNext() {
                if (z) {
                    if (this.currentPos >= 0) {
                        return true;
                    }
                } else if (this.currentPos < ArraySortedMap.this.keys.length) {
                    return true;
                }
                return false;
            }

            public Entry<K, V> next() {
                Object obj = ArraySortedMap.this.keys[this.currentPos];
                Object[] access$100 = ArraySortedMap.this.values;
                int i = this.currentPos;
                Object obj2 = access$100[i];
                this.currentPos = z ? i - 1 : i + 1;
                return new SimpleImmutableEntry(obj, obj2);
            }

            public void remove() {
                throw new UnsupportedOperationException("Can't remove elements from ImmutableSortedMap");
            }
        };
    }

    public Iterator<Entry<K, V>> iterator() {
        return iterator(0, false);
    }

    public Iterator<Entry<K, V>> iteratorFrom(K k) {
        return iterator(findKeyOrInsertPosition(k), false);
    }

    public Iterator<Entry<K, V>> reverseIteratorFrom(K k) {
        int findKeyOrInsertPosition = findKeyOrInsertPosition(k);
        Object[] objArr = this.keys;
        if (findKeyOrInsertPosition >= objArr.length || this.comparator.compare(objArr[findKeyOrInsertPosition], k) != 0) {
            return iterator(findKeyOrInsertPosition - 1, true);
        }
        return iterator(findKeyOrInsertPosition, true);
    }

    public Iterator<Entry<K, V>> reverseIterator() {
        return iterator(this.keys.length - 1, true);
    }

    public K getPredecessorKey(K k) {
        int findKey = findKey(k);
        if (findKey != -1) {
            return findKey > 0 ? this.keys[findKey - 1] : null;
        } else {
            throw new IllegalArgumentException("Can't find predecessor of nonexistent key");
        }
    }

    public K getSuccessorKey(K k) {
        int findKey = findKey(k);
        if (findKey != -1) {
            Object[] objArr = this.keys;
            return findKey < objArr.length + -1 ? objArr[findKey + 1] : null;
        } else {
            throw new IllegalArgumentException("Can't find successor of nonexistent key");
        }
    }

    public int indexOf(K k) {
        return findKey(k);
    }

    public Comparator<K> getComparator() {
        return this.comparator;
    }

    private static <T> T[] removeFromArray(T[] tArr, int i) {
        int length = tArr.length - 1;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, i);
        System.arraycopy(tArr, i + 1, obj, i, length - i);
        return obj;
    }

    private static <T> T[] addToArray(T[] tArr, int i, T t) {
        int length = tArr.length + 1;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, i);
        obj[i] = t;
        System.arraycopy(tArr, i, obj, i + 1, (length - i) - 1);
        return obj;
    }

    private static <T> T[] replaceInArray(T[] tArr, int i, T t) {
        int length = tArr.length;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, length);
        obj[i] = t;
        return obj;
    }

    private int findKeyOrInsertPosition(K k) {
        int i = 0;
        while (true) {
            Object[] objArr = this.keys;
            if (i >= objArr.length || this.comparator.compare(objArr[i], k) >= 0) {
                return i;
            }
            i++;
        }
        return i;
    }

    private int findKey(K k) {
        int i = 0;
        for (Object compare : this.keys) {
            if (this.comparator.compare(k, compare) == 0) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
