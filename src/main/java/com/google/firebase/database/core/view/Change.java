package com.google.firebase.database.core.view;

import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Change {
    private final ChildKey childKey;
    private final EventType eventType;
    private final IndexedNode indexedNode;
    private final IndexedNode oldIndexedNode;
    private final ChildKey prevName;

    private Change(EventType eventType, IndexedNode indexedNode, ChildKey childKey, ChildKey childKey2, IndexedNode indexedNode2) {
        this.eventType = eventType;
        this.indexedNode = indexedNode;
        this.childKey = childKey;
        this.prevName = childKey2;
        this.oldIndexedNode = indexedNode2;
    }

    public static Change valueChange(IndexedNode indexedNode) {
        return new Change(EventType.VALUE, indexedNode, null, null, null);
    }

    public static Change childAddedChange(ChildKey childKey, Node node) {
        return childAddedChange(childKey, IndexedNode.from(node));
    }

    public static Change childAddedChange(ChildKey childKey, IndexedNode indexedNode) {
        return new Change(EventType.CHILD_ADDED, indexedNode, childKey, null, null);
    }

    public static Change childRemovedChange(ChildKey childKey, Node node) {
        return childRemovedChange(childKey, IndexedNode.from(node));
    }

    public static Change childRemovedChange(ChildKey childKey, IndexedNode indexedNode) {
        return new Change(EventType.CHILD_REMOVED, indexedNode, childKey, null, null);
    }

    public static Change childChangedChange(ChildKey childKey, Node node, Node node2) {
        return childChangedChange(childKey, IndexedNode.from(node), IndexedNode.from(node2));
    }

    public static Change childChangedChange(ChildKey childKey, IndexedNode indexedNode, IndexedNode indexedNode2) {
        return new Change(EventType.CHILD_CHANGED, indexedNode, childKey, null, indexedNode2);
    }

    public static Change childMovedChange(ChildKey childKey, Node node) {
        return childMovedChange(childKey, IndexedNode.from(node));
    }

    public static Change childMovedChange(ChildKey childKey, IndexedNode indexedNode) {
        return new Change(EventType.CHILD_MOVED, indexedNode, childKey, null, null);
    }

    public Change changeWithPrevName(ChildKey childKey) {
        return new Change(this.eventType, this.indexedNode, this.childKey, childKey, this.oldIndexedNode);
    }

    public ChildKey getChildKey() {
        return this.childKey;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public IndexedNode getIndexedNode() {
        return this.indexedNode;
    }

    public ChildKey getPrevName() {
        return this.prevName;
    }

    public IndexedNode getOldIndexedNode() {
        return this.oldIndexedNode;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Change: ");
        stringBuilder.append(this.eventType);
        stringBuilder.append(" ");
        stringBuilder.append(this.childKey);
        return stringBuilder.toString();
    }
}
