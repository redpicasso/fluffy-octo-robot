package com.google.firebase.database.core.view.filter;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.filter.NodeFilter.CompleteChildSource;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Iterator;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class LimitedFilter implements NodeFilter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Index index;
    private final int limit;
    private final RangedFilter rangedFilter;
    private final boolean reverse;

    public boolean filtersNodes() {
        return true;
    }

    public IndexedNode updatePriority(IndexedNode indexedNode, Node node) {
        return indexedNode;
    }

    public LimitedFilter(QueryParams queryParams) {
        this.rangedFilter = new RangedFilter(queryParams);
        this.index = queryParams.getIndex();
        this.limit = queryParams.getLimit();
        this.reverse = queryParams.isViewFromLeft() ^ 1;
    }

    public IndexedNode updateChild(IndexedNode indexedNode, ChildKey childKey, Node node, Path path, CompleteChildSource completeChildSource, ChildChangeAccumulator childChangeAccumulator) {
        if (!this.rangedFilter.matches(new NamedNode(childKey, node))) {
            node = EmptyNode.Empty();
        }
        Node node2 = node;
        if (indexedNode.getNode().getImmediateChild(childKey).equals(node2)) {
            return indexedNode;
        }
        if (indexedNode.getNode().getChildCount() < this.limit) {
            return this.rangedFilter.getIndexedFilter().updateChild(indexedNode, childKey, node2, path, completeChildSource, childChangeAccumulator);
        }
        return fullLimitUpdateChild(indexedNode, childKey, node2, completeChildSource, childChangeAccumulator);
    }

    private IndexedNode fullLimitUpdateChild(IndexedNode indexedNode, ChildKey childKey, Node node, CompleteChildSource completeChildSource, ChildChangeAccumulator childChangeAccumulator) {
        NamedNode namedNode = new NamedNode(childKey, node);
        NamedNode firstChild = this.reverse ? indexedNode.getFirstChild() : indexedNode.getLastChild();
        boolean matches = this.rangedFilter.matches(namedNode);
        if (indexedNode.getNode().hasChild(childKey)) {
            int i;
            Node immediateChild = indexedNode.getNode().getImmediateChild(childKey);
            firstChild = completeChildSource.getChildAfterChild(this.index, firstChild, this.reverse);
            while (firstChild != null && (firstChild.getName().equals(childKey) || indexedNode.getNode().hasChild(firstChild.getName()))) {
                firstChild = completeChildSource.getChildAfterChild(this.index, firstChild, this.reverse);
            }
            Object obj = 1;
            if (firstChild == null) {
                i = 1;
            } else {
                i = this.index.compare(firstChild, namedNode, this.reverse);
            }
            Object obj2 = (!matches || node.isEmpty() || i < 0) ? null : 1;
            if (obj2 != null) {
                if (childChangeAccumulator != null) {
                    childChangeAccumulator.trackChildChange(Change.childChangedChange(childKey, node, immediateChild));
                }
                return indexedNode.updateChild(childKey, node);
            }
            if (childChangeAccumulator != null) {
                childChangeAccumulator.trackChildChange(Change.childRemovedChange(childKey, immediateChild));
            }
            indexedNode = indexedNode.updateChild(childKey, EmptyNode.Empty());
            if (firstChild == null || !this.rangedFilter.matches(firstChild)) {
                obj = null;
            }
            if (obj != null) {
                if (childChangeAccumulator != null) {
                    childChangeAccumulator.trackChildChange(Change.childAddedChange(firstChild.getName(), firstChild.getNode()));
                }
                indexedNode = indexedNode.updateChild(firstChild.getName(), firstChild.getNode());
            }
            return indexedNode;
        } else if (node.isEmpty()) {
            return indexedNode;
        } else {
            if (matches && this.index.compare(firstChild, namedNode, this.reverse) >= 0) {
                if (childChangeAccumulator != null) {
                    childChangeAccumulator.trackChildChange(Change.childRemovedChange(firstChild.getName(), firstChild.getNode()));
                    childChangeAccumulator.trackChildChange(Change.childAddedChange(childKey, node));
                }
                indexedNode = indexedNode.updateChild(childKey, node).updateChild(firstChild.getName(), EmptyNode.Empty());
            }
            return indexedNode;
        }
    }

    public IndexedNode updateFullNode(IndexedNode indexedNode, IndexedNode indexedNode2, ChildChangeAccumulator childChangeAccumulator) {
        IndexedNode from;
        if (indexedNode2.getNode().isLeafNode() || indexedNode2.getNode().isEmpty()) {
            from = IndexedNode.from(EmptyNode.Empty(), this.index);
        } else {
            Iterator reverseIterator;
            Object startPost;
            int i;
            from = indexedNode2.updatePriority(PriorityUtilities.NullPriority());
            Object endPost;
            if (this.reverse) {
                reverseIterator = indexedNode2.reverseIterator();
                endPost = this.rangedFilter.getEndPost();
                startPost = this.rangedFilter.getStartPost();
                i = -1;
            } else {
                reverseIterator = indexedNode2.iterator();
                endPost = this.rangedFilter.getStartPost();
                startPost = this.rangedFilter.getEndPost();
                i = 1;
            }
            Object obj = null;
            int i2 = 0;
            while (reverseIterator.hasNext()) {
                NamedNode namedNode = (NamedNode) reverseIterator.next();
                if (obj == null && this.index.compare(endPost, namedNode) * i <= 0) {
                    obj = 1;
                }
                Object obj2 = (obj == null || i2 >= this.limit || this.index.compare(namedNode, startPost) * i > 0) ? null : 1;
                if (obj2 != null) {
                    i2++;
                } else {
                    from = from.updateChild(namedNode.getName(), EmptyNode.Empty());
                }
            }
        }
        return this.rangedFilter.getIndexedFilter().updateFullNode(indexedNode, from, childChangeAccumulator);
    }

    public NodeFilter getIndexedFilter() {
        return this.rangedFilter.getIndexedFilter();
    }

    public Index getIndex() {
        return this.index;
    }
}
