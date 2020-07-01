package io.grpc;

import java.util.Arrays;

final class PersistentHashArrayMappedTrie<K, V> {
    private final Node<K, V> root;

    interface Node<K, V> {
        V get(K k, int i, int i2);

        Node<K, V> put(K k, V v, int i, int i2);

        int size();
    }

    static final class CollisionLeaf<K, V> implements Node<K, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final K[] keys;
        private final V[] values;

        CollisionLeaf(K k, V v, K k2, V v2) {
            this(new Object[]{k, k2}, new Object[]{v, v2});
        }

        private CollisionLeaf(K[] kArr, V[] vArr) {
            this.keys = kArr;
            this.values = vArr;
        }

        public int size() {
            return this.values.length;
        }

        public V get(K k, int i, int i2) {
            i = 0;
            while (true) {
                Object[] objArr = this.keys;
                if (i >= objArr.length) {
                    return null;
                }
                if (objArr[i] == k) {
                    return this.values[i];
                }
                i++;
            }
        }

        public Node<K, V> put(K k, V v, int i, int i2) {
            int hashCode = this.keys[0].hashCode();
            if (hashCode != i) {
                return CompressedIndex.combine(new Leaf(k, v), i, this, hashCode, i2);
            }
            i = indexOfKey(k);
            Object[] objArr;
            Object[] copyOf;
            if (i != -1) {
                objArr = this.keys;
                objArr = Arrays.copyOf(objArr, objArr.length);
                copyOf = Arrays.copyOf(this.values, this.keys.length);
                objArr[i] = k;
                copyOf[i] = v;
                return new CollisionLeaf(objArr, copyOf);
            }
            Object[] objArr2 = this.keys;
            objArr2 = Arrays.copyOf(objArr2, objArr2.length + 1);
            objArr = Arrays.copyOf(this.values, this.keys.length + 1);
            copyOf = this.keys;
            objArr2[copyOf.length] = k;
            objArr[copyOf.length] = v;
            return new CollisionLeaf(objArr2, objArr);
        }

        private int indexOfKey(K k) {
            int i = 0;
            while (true) {
                Object[] objArr = this.keys;
                if (i >= objArr.length) {
                    return -1;
                }
                if (objArr[i] == k) {
                    return i;
                }
                i++;
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CollisionLeaf(");
            for (int i = 0; i < this.values.length; i++) {
                stringBuilder.append("(key=");
                stringBuilder.append(this.keys[i]);
                stringBuilder.append(" value=");
                stringBuilder.append(this.values[i]);
                stringBuilder.append(") ");
            }
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static final class CompressedIndex<K, V> implements Node<K, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int BITS = 5;
        private static final int BITS_MASK = 31;
        final int bitmap;
        private final int size;
        final Node<K, V>[] values;

        private static int uncompressedIndex(int i, int i2) {
            return (i >>> i2) & 31;
        }

        static {
            Class cls = PersistentHashArrayMappedTrie.class;
        }

        private CompressedIndex(int i, Node<K, V>[] nodeArr, int i2) {
            this.bitmap = i;
            this.values = nodeArr;
            this.size = i2;
        }

        public int size() {
            return this.size;
        }

        public V get(K k, int i, int i2) {
            int indexBit = indexBit(i, i2);
            if ((this.bitmap & indexBit) == 0) {
                return null;
            }
            return this.values[compressedIndex(indexBit)].get(k, i, i2 + 5);
        }

        public Node<K, V> put(K k, V v, int i, int i2) {
            int indexBit = indexBit(i, i2);
            int compressedIndex = compressedIndex(indexBit);
            int i3 = this.bitmap;
            if ((i3 & indexBit) == 0) {
                i = i3 | indexBit;
                Object obj = this.values;
                Object obj2 = new Node[(obj.length + 1)];
                System.arraycopy(obj, 0, obj2, 0, compressedIndex);
                obj2[compressedIndex] = new Leaf(k, v);
                Object obj3 = this.values;
                System.arraycopy(obj3, compressedIndex, obj2, compressedIndex + 1, obj3.length - compressedIndex);
                return new CompressedIndex(i, obj2, size() + 1);
            }
            Node[] nodeArr = this.values;
            nodeArr = (Node[]) Arrays.copyOf(nodeArr, nodeArr.length);
            nodeArr[compressedIndex] = this.values[compressedIndex].put(k, v, i, i2 + 5);
            return new CompressedIndex(this.bitmap, nodeArr, (size() + nodeArr[compressedIndex].size()) - this.values[compressedIndex].size());
        }

        static <K, V> Node<K, V> combine(Node<K, V> node, int i, Node<K, V> node2, int i2, int i3) {
            int indexBit = indexBit(i, i3);
            int indexBit2 = indexBit(i2, i3);
            if (indexBit == indexBit2) {
                return new CompressedIndex(indexBit, new Node[]{combine(node, i, node2, i2, i3 + 5)}, combine(node, i, node2, i2, i3 + 5).size());
            }
            Node node22;
            Node node3;
            if (uncompressedIndex(i, i3) > uncompressedIndex(i2, i3)) {
                Node<K, V> node4 = node22;
                node22 = node3;
                node3 = node4;
            }
            return new CompressedIndex(indexBit | indexBit2, new Node[]{node3, node22}, node3.size() + node22.size());
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CompressedIndex(");
            Object[] objArr = new Object[1];
            int i = 0;
            objArr[0] = Integer.toBinaryString(this.bitmap);
            stringBuilder.append(String.format("bitmap=%s ", objArr));
            Node[] nodeArr = this.values;
            int length = nodeArr.length;
            while (i < length) {
                stringBuilder.append(nodeArr[i]);
                stringBuilder.append(" ");
                i++;
            }
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        private int compressedIndex(int i) {
            return Integer.bitCount((i - 1) & this.bitmap);
        }

        private static int indexBit(int i, int i2) {
            return 1 << uncompressedIndex(i, i2);
        }
    }

    static final class Leaf<K, V> implements Node<K, V> {
        private final K key;
        private final V value;

        public int size() {
            return 1;
        }

        public Leaf(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public V get(K k, int i, int i2) {
            return this.key == k ? this.value : null;
        }

        public Node<K, V> put(K k, V v, int i, int i2) {
            int hashCode = this.key.hashCode();
            if (hashCode != i) {
                return CompressedIndex.combine(new Leaf(k, v), i, this, hashCode, i2);
            }
            K k2 = this.key;
            if (k2 == k) {
                return new Leaf(k, v);
            }
            return new CollisionLeaf(k2, this.value, k, v);
        }

        public String toString() {
            return String.format("Leaf(key=%s value=%s)", new Object[]{this.key, this.value});
        }
    }

    PersistentHashArrayMappedTrie() {
        this(null);
    }

    private PersistentHashArrayMappedTrie(Node<K, V> node) {
        this.root = node;
    }

    public int size() {
        Node node = this.root;
        if (node == null) {
            return 0;
        }
        return node.size();
    }

    public V get(K k) {
        Node node = this.root;
        if (node == null) {
            return null;
        }
        return node.get(k, k.hashCode(), 0);
    }

    public PersistentHashArrayMappedTrie<K, V> put(K k, V v) {
        Node node = this.root;
        if (node == null) {
            return new PersistentHashArrayMappedTrie(new Leaf(k, v));
        }
        return new PersistentHashArrayMappedTrie(node.put(k, v, k.hashCode(), 0));
    }
}
