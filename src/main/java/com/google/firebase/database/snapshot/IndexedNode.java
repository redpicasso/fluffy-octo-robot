package com.google.firebase.database.snapshot;

import com.google.android.gms.common.internal.Objects;
import com.google.firebase.database.collection.ImmutableSortedSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class IndexedNode implements Iterable<NamedNode> {
    private static final ImmutableSortedSet<NamedNode> FALLBACK_INDEX = new ImmutableSortedSet(Collections.emptyList(), null);
    private final Index index;
    private ImmutableSortedSet<NamedNode> indexed;
    private final Node node;

    private IndexedNode(Node node, Index index) {
        this.index = index;
        this.node = node;
        this.indexed = null;
    }

    private IndexedNode(Node node, Index index, ImmutableSortedSet<NamedNode> immutableSortedSet) {
        this.index = index;
        this.node = node;
        this.indexed = immutableSortedSet;
    }

    private void ensureIndexed() {
        if (this.indexed != null) {
            return;
        }
        if (this.index.equals(KeyIndex.getInstance())) {
            this.indexed = FALLBACK_INDEX;
            return;
        }
        List arrayList = new ArrayList();
        Object obj = null;
        for (NamedNode namedNode : this.node) {
            obj = (obj != null || this.index.isDefinedOn(namedNode.getNode())) ? 1 : null;
            arrayList.add(new NamedNode(namedNode.getName(), namedNode.getNode()));
        }
        if (obj != null) {
            this.indexed = new ImmutableSortedSet(arrayList, this.index);
        } else {
            this.indexed = FALLBACK_INDEX;
        }
    }

    public static IndexedNode from(Node node) {
        return new IndexedNode(node, PriorityIndex.getInstance());
    }

    public static IndexedNode from(Node node, Index index) {
        return new IndexedNode(node, index);
    }

    public boolean hasIndex(Index index) {
        return this.index == index;
    }

    public Node getNode() {
        return this.node;
    }

    public Iterator<NamedNode> iterator() {
        ensureIndexed();
        if (Objects.equal(this.indexed, FALLBACK_INDEX)) {
            return this.node.iterator();
        }
        return this.indexed.iterator();
    }

    public Iterator<NamedNode> reverseIterator() {
        ensureIndexed();
        if (Objects.equal(this.indexed, FALLBACK_INDEX)) {
            return this.node.reverseIterator();
        }
        return this.indexed.reverseIterator();
    }

    public IndexedNode updateChild(ChildKey childKey, Node node) {
        Node updateImmediateChild = this.node.updateImmediateChild(childKey, node);
        if (Objects.equal(this.indexed, FALLBACK_INDEX) && !this.index.isDefinedOn(node)) {
            return new IndexedNode(updateImmediateChild, this.index, FALLBACK_INDEX);
        }
        ImmutableSortedSet immutableSortedSet = this.indexed;
        if (immutableSortedSet == null || Objects.equal(immutableSortedSet, FALLBACK_INDEX)) {
            return new IndexedNode(updateImmediateChild, this.index, null);
        }
        immutableSortedSet = this.indexed.remove(new NamedNode(childKey, this.node.getImmediateChild(childKey)));
        if (!node.isEmpty()) {
            immutableSortedSet = immutableSortedSet.insert(new NamedNode(childKey, node));
        }
        return new IndexedNode(updateImmediateChild, this.index, immutableSortedSet);
    }

    public IndexedNode updatePriority(Node node) {
        return new IndexedNode(this.node.updatePriority(node), this.index, this.indexed);
    }

    public NamedNode getFirstChild() {
        if (!(this.node instanceof ChildrenNode)) {
            return null;
        }
        ensureIndexed();
        if (!Objects.equal(this.indexed, FALLBACK_INDEX)) {
            return (NamedNode) this.indexed.getMinEntry();
        }
        ChildKey firstChildKey = ((ChildrenNode) this.node).getFirstChildKey();
        return new NamedNode(firstChildKey, this.node.getImmediateChild(firstChildKey));
    }

    public NamedNode getLastChild() {
        if (!(this.node instanceof ChildrenNode)) {
            return null;
        }
        ensureIndexed();
        if (!Objects.equal(this.indexed, FALLBACK_INDEX)) {
            return (NamedNode) this.indexed.getMaxEntry();
        }
        ChildKey lastChildKey = ((ChildrenNode) this.node).getLastChildKey();
        return new NamedNode(lastChildKey, this.node.getImmediateChild(lastChildKey));
    }

    public ChildKey getPredecessorChildName(ChildKey childKey, Node node, Index index) {
        if (this.index.equals(KeyIndex.getInstance()) || this.index.equals(index)) {
            ensureIndexed();
            if (Objects.equal(this.indexed, FALLBACK_INDEX)) {
                return this.node.getPredecessorChildKey(childKey);
            }
            NamedNode namedNode = (NamedNode) this.indexed.getPredecessorEntry(new NamedNode(childKey, node));
            return namedNode != null ? namedNode.getName() : null;
        }
        throw new IllegalArgumentException("Index not available in IndexedNode!");
    }
}
