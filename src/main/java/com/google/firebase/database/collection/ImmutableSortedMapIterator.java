package com.google.firebase.database.collection;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

/* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
public class ImmutableSortedMapIterator<K, V> implements Iterator<Entry<K, V>> {
    private final boolean isReverse;
    private final ArrayDeque<LLRBValueNode<K, V>> nodeStack = new ArrayDeque();

    ImmutableSortedMapIterator(LLRBNode<K, V> lLRBNode, K k, Comparator<K> comparator, boolean z) {
        this.isReverse = z;
        LLRBNode lLRBNode2;
        while (!lLRBNode2.isEmpty()) {
            int compare = k != null ? z ? comparator.compare(k, lLRBNode2.getKey()) : comparator.compare(lLRBNode2.getKey(), k) : 1;
            if (compare < 0) {
                if (z) {
                    lLRBNode2 = lLRBNode2.getLeft();
                } else {
                    lLRBNode2 = lLRBNode2.getRight();
                }
            } else if (compare == 0) {
                this.nodeStack.push((LLRBValueNode) lLRBNode2);
                return;
            } else {
                this.nodeStack.push((LLRBValueNode) lLRBNode2);
                if (z) {
                    lLRBNode2 = lLRBNode2.getRight();
                } else {
                    lLRBNode2 = lLRBNode2.getLeft();
                }
            }
        }
    }

    public boolean hasNext() {
        return this.nodeStack.size() > 0;
    }

    public Entry<K, V> next() {
        try {
            LLRBValueNode lLRBValueNode = (LLRBValueNode) this.nodeStack.pop();
            Entry simpleEntry = new SimpleEntry(lLRBValueNode.getKey(), lLRBValueNode.getValue());
            LLRBNode left;
            if (this.isReverse) {
                for (left = lLRBValueNode.getLeft(); !left.isEmpty(); left = left.getRight()) {
                    this.nodeStack.push((LLRBValueNode) left);
                }
            } else {
                for (left = lLRBValueNode.getRight(); !left.isEmpty(); left = left.getLeft()) {
                    this.nodeStack.push((LLRBValueNode) left);
                }
            }
            return simpleEntry;
        } catch (EmptyStackException unused) {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("remove called on immutable collection");
    }
}
