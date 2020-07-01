package com.google.firebase.database.collection;

import com.google.firebase.database.collection.LLRBNode.Color;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import com.google.firebase.database.collection.LLRBNode.ShortCircuitingNodeVisitor;
import java.util.Comparator;

/* compiled from: com.google.firebase:firebase-database-collection@@16.0.1 */
public abstract class LLRBValueNode<K, V> implements LLRBNode<K, V> {
    private final K key;
    private LLRBNode<K, V> left;
    private final LLRBNode<K, V> right;
    private final V value;

    protected abstract LLRBValueNode<K, V> copy(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2);

    protected abstract Color getColor();

    public boolean isEmpty() {
        return false;
    }

    private static Color oppositeColor(LLRBNode lLRBNode) {
        return lLRBNode.isRed() ? Color.BLACK : Color.RED;
    }

    LLRBValueNode(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        LLRBNode lLRBNode3;
        LLRBNode lLRBNode22;
        this.key = k;
        this.value = v;
        if (lLRBNode3 == null) {
            lLRBNode3 = LLRBEmptyNode.getInstance();
        }
        this.left = lLRBNode3;
        if (lLRBNode22 == null) {
            lLRBNode22 = LLRBEmptyNode.getInstance();
        }
        this.right = lLRBNode22;
    }

    public LLRBNode<K, V> getLeft() {
        return this.left;
    }

    public LLRBNode<K, V> getRight() {
        return this.right;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public LLRBValueNode<K, V> copy(K k, V v, Color color, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        Object k2;
        Object v2;
        LLRBNode lLRBNode3;
        LLRBNode lLRBNode22;
        if (k2 == null) {
            k2 = this.key;
        }
        if (v2 == null) {
            v2 = this.value;
        }
        if (lLRBNode3 == null) {
            lLRBNode3 = this.left;
        }
        if (lLRBNode22 == null) {
            lLRBNode22 = this.right;
        }
        if (color == Color.RED) {
            return new LLRBRedValueNode(k2, v2, lLRBNode3, lLRBNode22);
        }
        return new LLRBBlackValueNode(k2, v2, lLRBNode3, lLRBNode22);
    }

    public LLRBNode<K, V> insert(K k, V v, Comparator<K> comparator) {
        LLRBValueNode copy;
        int compare = comparator.compare(k, this.key);
        if (compare < 0) {
            copy = copy(null, null, this.left.insert(k, v, comparator), null);
        } else if (compare == 0) {
            copy = copy(k, v, null, null);
        } else {
            copy = copy(null, null, null, this.right.insert(k, v, comparator));
        }
        return copy.fixUp();
    }

    public LLRBNode<K, V> remove(K k, Comparator<K> comparator) {
        LLRBValueNode copy;
        LLRBValueNode moveRedLeft;
        if (comparator.compare(k, this.key) < 0) {
            moveRedLeft = (this.left.isEmpty() || this.left.isRed() || ((LLRBValueNode) this.left).left.isRed()) ? this : moveRedLeft();
            copy = moveRedLeft.copy(null, null, moveRedLeft.left.remove(k, comparator), null);
        } else {
            moveRedLeft = this.left.isRed() ? rotateRight() : this;
            if (!(moveRedLeft.right.isEmpty() || moveRedLeft.right.isRed() || ((LLRBValueNode) moveRedLeft.right).left.isRed())) {
                moveRedLeft = moveRedLeft.moveRedRight();
            }
            if (comparator.compare(k, moveRedLeft.key) == 0) {
                if (moveRedLeft.right.isEmpty()) {
                    return LLRBEmptyNode.getInstance();
                }
                LLRBNode min = moveRedLeft.right.getMin();
                moveRedLeft = moveRedLeft.copy(min.getKey(), min.getValue(), null, ((LLRBValueNode) moveRedLeft.right).removeMin());
            }
            copy = moveRedLeft.copy(null, null, null, moveRedLeft.right.remove(k, comparator));
        }
        return copy.fixUp();
    }

    public LLRBNode<K, V> getMin() {
        if (this.left.isEmpty()) {
            return this;
        }
        return this.left.getMin();
    }

    public LLRBNode<K, V> getMax() {
        if (this.right.isEmpty()) {
            return this;
        }
        return this.right.getMax();
    }

    public void inOrderTraversal(NodeVisitor<K, V> nodeVisitor) {
        this.left.inOrderTraversal(nodeVisitor);
        nodeVisitor.visitEntry(this.key, this.value);
        this.right.inOrderTraversal(nodeVisitor);
    }

    public boolean shortCircuitingInOrderTraversal(ShortCircuitingNodeVisitor<K, V> shortCircuitingNodeVisitor) {
        return (this.left.shortCircuitingInOrderTraversal(shortCircuitingNodeVisitor) && shortCircuitingNodeVisitor.shouldContinue(this.key, this.value)) ? this.right.shortCircuitingInOrderTraversal(shortCircuitingNodeVisitor) : false;
    }

    public boolean shortCircuitingReverseOrderTraversal(ShortCircuitingNodeVisitor<K, V> shortCircuitingNodeVisitor) {
        return (this.right.shortCircuitingReverseOrderTraversal(shortCircuitingNodeVisitor) && shortCircuitingNodeVisitor.shouldContinue(this.key, this.value)) ? this.left.shortCircuitingReverseOrderTraversal(shortCircuitingNodeVisitor) : false;
    }

    void setLeft(LLRBNode<K, V> lLRBNode) {
        this.left = lLRBNode;
    }

    private LLRBNode<K, V> removeMin() {
        if (this.left.isEmpty()) {
            return LLRBEmptyNode.getInstance();
        }
        LLRBValueNode moveRedLeft = (getLeft().isRed() || getLeft().getLeft().isRed()) ? this : moveRedLeft();
        return moveRedLeft.copy(null, null, ((LLRBValueNode) moveRedLeft.left).removeMin(), null).fixUp();
    }

    private LLRBValueNode<K, V> moveRedLeft() {
        LLRBValueNode<K, V> colorFlip = colorFlip();
        return colorFlip.getRight().getLeft().isRed() ? colorFlip.copy(null, null, null, ((LLRBValueNode) colorFlip.getRight()).rotateRight()).rotateLeft().colorFlip() : colorFlip;
    }

    private LLRBValueNode<K, V> moveRedRight() {
        LLRBValueNode<K, V> colorFlip = colorFlip();
        return colorFlip.getLeft().getLeft().isRed() ? colorFlip.rotateRight().colorFlip() : colorFlip;
    }

    private LLRBValueNode<K, V> fixUp() {
        LLRBValueNode<K, V> rotateLeft = (!this.right.isRed() || this.left.isRed()) ? this : rotateLeft();
        if (rotateLeft.left.isRed() && ((LLRBValueNode) rotateLeft.left).left.isRed()) {
            rotateLeft = rotateLeft.rotateRight();
        }
        return (rotateLeft.left.isRed() && rotateLeft.right.isRed()) ? rotateLeft.colorFlip() : rotateLeft;
    }

    private LLRBValueNode<K, V> rotateLeft() {
        return (LLRBValueNode) this.right.copy(null, null, getColor(), copy(null, null, Color.RED, null, ((LLRBValueNode) this.right).left), null);
    }

    private LLRBValueNode<K, V> rotateRight() {
        return (LLRBValueNode) this.left.copy(null, null, getColor(), null, copy(null, null, Color.RED, ((LLRBValueNode) this.left).right, null));
    }

    private LLRBValueNode<K, V> colorFlip() {
        LLRBNode lLRBNode = this.left;
        LLRBNode copy = lLRBNode.copy(null, null, oppositeColor(lLRBNode), null, null);
        lLRBNode = this.right;
        return copy(null, null, oppositeColor(this), copy, lLRBNode.copy(null, null, oppositeColor(lLRBNode), null, null));
    }
}
