package com.google.firebase.database.collection;

import com.google.firebase.database.collection.LLRBNode.Color;

/* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
public class LLRBBlackValueNode<K, V> extends LLRBValueNode<K, V> {
    private int size = -1;

    public boolean isRed() {
        return false;
    }

    LLRBBlackValueNode(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        super(k, v, lLRBNode, lLRBNode2);
    }

    protected Color getColor() {
        return Color.BLACK;
    }

    public int size() {
        if (this.size == -1) {
            this.size = (getLeft().size() + 1) + getRight().size();
        }
        return this.size;
    }

    void setLeft(LLRBNode<K, V> lLRBNode) {
        if (this.size == -1) {
            super.setLeft(lLRBNode);
            return;
        }
        throw new IllegalStateException("Can't set left after using size");
    }

    protected LLRBValueNode<K, V> copy(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        Object k2;
        Object v2;
        LLRBNode lLRBNode3;
        LLRBNode lLRBNode22;
        if (k2 == null) {
            k2 = getKey();
        }
        if (v2 == null) {
            v2 = getValue();
        }
        if (lLRBNode3 == null) {
            lLRBNode3 = getLeft();
        }
        if (lLRBNode22 == null) {
            lLRBNode22 = getRight();
        }
        return new LLRBBlackValueNode(k2, v2, lLRBNode3, lLRBNode22);
    }
}
