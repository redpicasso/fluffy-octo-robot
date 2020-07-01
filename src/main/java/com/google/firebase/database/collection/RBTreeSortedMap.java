package com.google.firebase.database.collection;

import com.google.firebase.database.collection.ImmutableSortedMap.Builder.KeyTranslator;
import com.google.firebase.database.collection.LLRBNode.Color;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
public class RBTreeSortedMap<K, V> extends ImmutableSortedMap<K, V> {
    private Comparator<K> comparator;
    private LLRBNode<K, V> root;

    /* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
    private static class Builder<A, B, C> {
        private final KeyTranslator<A, B> keyTranslator;
        private final List<A> keys;
        private LLRBValueNode<A, C> leaf;
        private LLRBValueNode<A, C> root;
        private final Map<B, C> values;

        /* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
        static class Base1_2 implements Iterable<BooleanChunk> {
            private final int length;
            private long value;

            public Base1_2(int i) {
                i++;
                this.length = (int) Math.floor(Math.log((double) i) / Math.log(2.0d));
                this.value = (((long) Math.pow(2.0d, (double) this.length)) - 1) & ((long) i);
            }

            public Iterator<BooleanChunk> iterator() {
                return new Iterator<BooleanChunk>() {
                    private int current = (Base1_2.this.length - 1);

                    public void remove() {
                    }

                    public boolean hasNext() {
                        return this.current >= 0;
                    }

                    public BooleanChunk next() {
                        long access$100 = Base1_2.this.value & ((long) (1 << this.current));
                        BooleanChunk booleanChunk = new BooleanChunk();
                        booleanChunk.isOne = access$100 == 0;
                        booleanChunk.chunkSize = (int) Math.pow(2.0d, (double) this.current);
                        this.current--;
                        return booleanChunk;
                    }
                };
            }
        }

        /* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
        static class BooleanChunk {
            public int chunkSize;
            public boolean isOne;

            BooleanChunk() {
            }
        }

        private Builder(List<A> list, Map<B, C> map, KeyTranslator<A, B> keyTranslator) {
            this.keys = list;
            this.values = map;
            this.keyTranslator = keyTranslator;
        }

        private C getValue(A a) {
            return this.values.get(this.keyTranslator.translate(a));
        }

        private LLRBNode<A, C> buildBalancedTree(int i, int i2) {
            if (i2 == 0) {
                return LLRBEmptyNode.getInstance();
            }
            if (i2 == 1) {
                Object obj = this.keys.get(i);
                return new LLRBBlackValueNode(obj, getValue(obj), null, null);
            }
            i2 /= 2;
            int i3 = i + i2;
            LLRBNode buildBalancedTree = buildBalancedTree(i, i2);
            LLRBNode buildBalancedTree2 = buildBalancedTree(i3 + 1, i2);
            Object obj2 = this.keys.get(i3);
            return new LLRBBlackValueNode(obj2, getValue(obj2), buildBalancedTree, buildBalancedTree2);
        }

        private void buildPennant(Color color, int i, int i2) {
            Object lLRBRedValueNode;
            LLRBNode buildBalancedTree = buildBalancedTree(i2 + 1, i - 1);
            Object obj = this.keys.get(i2);
            if (color == Color.RED) {
                lLRBRedValueNode = new LLRBRedValueNode(obj, getValue(obj), null, buildBalancedTree);
            } else {
                lLRBRedValueNode = new LLRBBlackValueNode(obj, getValue(obj), null, buildBalancedTree);
            }
            if (this.root == null) {
                this.root = lLRBRedValueNode;
                this.leaf = lLRBRedValueNode;
                return;
            }
            this.leaf.setLeft(lLRBRedValueNode);
            this.leaf = lLRBRedValueNode;
        }

        public static <A, B, C> RBTreeSortedMap<A, C> buildFrom(List<A> list, Map<B, C> map, KeyTranslator<A, B> keyTranslator, Comparator<A> comparator) {
            Builder builder = new Builder(list, map, keyTranslator);
            Collections.sort(list, comparator);
            Iterator it = new Base1_2(list.size()).iterator();
            int size = list.size();
            while (it.hasNext()) {
                BooleanChunk booleanChunk = (BooleanChunk) it.next();
                size -= booleanChunk.chunkSize;
                if (booleanChunk.isOne) {
                    builder.buildPennant(Color.BLACK, booleanChunk.chunkSize, size);
                } else {
                    builder.buildPennant(Color.BLACK, booleanChunk.chunkSize, size);
                    size -= booleanChunk.chunkSize;
                    builder.buildPennant(Color.RED, booleanChunk.chunkSize, size);
                }
            }
            LLRBNode lLRBNode = builder.root;
            if (lLRBNode == null) {
                lLRBNode = LLRBEmptyNode.getInstance();
            }
            return new RBTreeSortedMap(lLRBNode, comparator);
        }
    }

    RBTreeSortedMap(Comparator<K> comparator) {
        this.root = LLRBEmptyNode.getInstance();
        this.comparator = comparator;
    }

    private RBTreeSortedMap(LLRBNode<K, V> lLRBNode, Comparator<K> comparator) {
        this.root = lLRBNode;
        this.comparator = comparator;
    }

    LLRBNode<K, V> getRoot() {
        return this.root;
    }

    private LLRBNode<K, V> getNode(K k) {
        LLRBNode<K, V> lLRBNode = this.root;
        while (!lLRBNode.isEmpty()) {
            int compare = this.comparator.compare(k, lLRBNode.getKey());
            if (compare < 0) {
                lLRBNode = lLRBNode.getLeft();
            } else if (compare == 0) {
                return lLRBNode;
            } else {
                lLRBNode = lLRBNode.getRight();
            }
        }
        return null;
    }

    public boolean containsKey(K k) {
        return getNode(k) != null;
    }

    public V get(K k) {
        LLRBNode node = getNode(k);
        return node != null ? node.getValue() : null;
    }

    public ImmutableSortedMap<K, V> remove(K k) {
        if (containsKey(k)) {
            return new RBTreeSortedMap(this.root.remove(k, this.comparator).copy(null, null, Color.BLACK, null, null), this.comparator);
        }
        return this;
    }

    public ImmutableSortedMap<K, V> insert(K k, V v) {
        return new RBTreeSortedMap(this.root.insert(k, v, this.comparator).copy(null, null, Color.BLACK, null, null), this.comparator);
    }

    public K getMinKey() {
        return this.root.getMin().getKey();
    }

    public K getMaxKey() {
        return this.root.getMax().getKey();
    }

    public int size() {
        return this.root.size();
    }

    public boolean isEmpty() {
        return this.root.isEmpty();
    }

    public void inOrderTraversal(NodeVisitor<K, V> nodeVisitor) {
        this.root.inOrderTraversal(nodeVisitor);
    }

    public Iterator<Entry<K, V>> iterator() {
        return new ImmutableSortedMapIterator(this.root, null, this.comparator, false);
    }

    public Iterator<Entry<K, V>> iteratorFrom(K k) {
        return new ImmutableSortedMapIterator(this.root, k, this.comparator, false);
    }

    public Iterator<Entry<K, V>> reverseIteratorFrom(K k) {
        return new ImmutableSortedMapIterator(this.root, k, this.comparator, true);
    }

    public Iterator<Entry<K, V>> reverseIterator() {
        return new ImmutableSortedMapIterator(this.root, null, this.comparator, true);
    }

    public K getPredecessorKey(K k) {
        LLRBNode lLRBNode = this.root;
        LLRBNode lLRBNode2 = null;
        while (!lLRBNode.isEmpty()) {
            int compare = this.comparator.compare(k, lLRBNode.getKey());
            if (compare == 0) {
                if (!lLRBNode.getLeft().isEmpty()) {
                    LLRBNode left = lLRBNode.getLeft();
                    while (!left.getRight().isEmpty()) {
                        left = left.getRight();
                    }
                    return left.getKey();
                } else if (lLRBNode2 != null) {
                    return lLRBNode2.getKey();
                } else {
                    return null;
                }
            } else if (compare < 0) {
                lLRBNode = lLRBNode.getLeft();
            } else {
                lLRBNode2 = lLRBNode;
                lLRBNode = lLRBNode.getRight();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Couldn't find predecessor key of non-present key: ");
        stringBuilder.append(k);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public K getSuccessorKey(K k) {
        LLRBNode lLRBNode = this.root;
        LLRBNode lLRBNode2 = null;
        while (!lLRBNode.isEmpty()) {
            int compare = this.comparator.compare(lLRBNode.getKey(), k);
            if (compare == 0) {
                if (!lLRBNode.getRight().isEmpty()) {
                    LLRBNode right = lLRBNode.getRight();
                    while (!right.getLeft().isEmpty()) {
                        right = right.getLeft();
                    }
                    return right.getKey();
                } else if (lLRBNode2 != null) {
                    return lLRBNode2.getKey();
                } else {
                    return null;
                }
            } else if (compare < 0) {
                lLRBNode = lLRBNode.getRight();
            } else {
                lLRBNode2 = lLRBNode;
                lLRBNode = lLRBNode.getLeft();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Couldn't find successor key of non-present key: ");
        stringBuilder.append(k);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int indexOf(K k) {
        LLRBNode lLRBNode = this.root;
        int i = 0;
        while (!lLRBNode.isEmpty()) {
            int compare = this.comparator.compare(k, lLRBNode.getKey());
            if (compare == 0) {
                return i + lLRBNode.getLeft().size();
            }
            if (compare < 0) {
                lLRBNode = lLRBNode.getLeft();
            } else {
                i += lLRBNode.getLeft().size() + 1;
                lLRBNode = lLRBNode.getRight();
            }
        }
        return -1;
    }

    public Comparator<K> getComparator() {
        return this.comparator;
    }

    public static <A, B, C> RBTreeSortedMap<A, C> buildFrom(List<A> list, Map<B, C> map, KeyTranslator<A, B> keyTranslator, Comparator<A> comparator) {
        return Builder.buildFrom(list, map, keyTranslator, comparator);
    }

    public static <A, B> RBTreeSortedMap<A, B> fromMap(Map<A, B> map, Comparator<A> comparator) {
        return Builder.buildFrom(new ArrayList(map.keySet()), map, com.google.firebase.database.collection.ImmutableSortedMap.Builder.identityTranslator(), comparator);
    }
}
