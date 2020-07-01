package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.Node.HashVersion;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public abstract class LeafNode<T extends LeafNode> implements Node {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private String lazyHash;
    protected final Node priority;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    /* renamed from: com.google.firebase.database.snapshot.LeafNode$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion = new int[HashVersion.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = com.google.firebase.database.snapshot.Node.HashVersion.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion = r0;
            r0 = $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.database.snapshot.Node.HashVersion.V1;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.database.snapshot.Node.HashVersion.V2;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.snapshot.LeafNode.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    protected enum LeafType {
        DeferredValue,
        Boolean,
        Number,
        String
    }

    protected abstract int compareLeafValues(T t);

    public abstract boolean equals(Object obj);

    public int getChildCount() {
        return 0;
    }

    protected abstract LeafType getLeafType();

    public ChildKey getPredecessorChildKey(ChildKey childKey) {
        return null;
    }

    public ChildKey getSuccessorChildKey(ChildKey childKey) {
        return null;
    }

    public boolean hasChild(ChildKey childKey) {
        return false;
    }

    public abstract int hashCode();

    public boolean isEmpty() {
        return false;
    }

    public boolean isLeafNode() {
        return true;
    }

    LeafNode(Node node) {
        this.priority = node;
    }

    public Node getPriority() {
        return this.priority;
    }

    public Node getChild(Path path) {
        if (path.isEmpty()) {
            return this;
        }
        if (path.getFront().isPriorityChildName()) {
            return this.priority;
        }
        return EmptyNode.Empty();
    }

    public Node updateChild(Path path, Node node) {
        ChildKey front = path.getFront();
        if (front == null) {
            return node;
        }
        if (!node.isEmpty() || front.isPriorityChildName()) {
            return updateImmediateChild(front, EmptyNode.Empty().updateChild(path.popFront(), node));
        }
        return this;
    }

    public Node getImmediateChild(ChildKey childKey) {
        if (childKey.isPriorityChildName()) {
            return this.priority;
        }
        return EmptyNode.Empty();
    }

    public Object getValue(boolean z) {
        if (!z || this.priority.isEmpty()) {
            return getValue();
        }
        Map hashMap = new HashMap();
        hashMap.put(".value", getValue());
        hashMap.put(".priority", this.priority.getValue());
        return hashMap;
    }

    public Node updateImmediateChild(ChildKey childKey, Node node) {
        if (childKey.isPriorityChildName()) {
            return updatePriority(node);
        }
        if (node.isEmpty()) {
            return this;
        }
        return EmptyNode.Empty().updateImmediateChild(childKey, node).updatePriority(this.priority);
    }

    public String getHash() {
        if (this.lazyHash == null) {
            this.lazyHash = Utilities.sha1HexDigest(getHashRepresentation(HashVersion.V1));
        }
        return this.lazyHash;
    }

    protected String getPriorityHash(HashVersion hashVersion) {
        int i = AnonymousClass1.$SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion[hashVersion.ordinal()];
        if (i != 1 && i != 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown hash version: ");
            stringBuilder.append(hashVersion);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (this.priority.isEmpty()) {
            return "";
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("priority:");
            stringBuilder2.append(this.priority.getHashRepresentation(hashVersion));
            stringBuilder2.append(":");
            return stringBuilder2.toString();
        }
    }

    public Iterator<NamedNode> iterator() {
        return Collections.emptyList().iterator();
    }

    public Iterator<NamedNode> reverseIterator() {
        return Collections.emptyList().iterator();
    }

    private static int compareLongDoubleNodes(LongNode longNode, DoubleNode doubleNode) {
        return Double.valueOf((double) ((Long) longNode.getValue()).longValue()).compareTo((Double) doubleNode.getValue());
    }

    public int compareTo(Node node) {
        if (node.isEmpty()) {
            return 1;
        }
        if (node instanceof ChildrenNode) {
            return -1;
        }
        if ((this instanceof LongNode) && (node instanceof DoubleNode)) {
            return compareLongDoubleNodes((LongNode) this, (DoubleNode) node);
        }
        if ((this instanceof DoubleNode) && (node instanceof LongNode)) {
            return compareLongDoubleNodes((LongNode) node, (DoubleNode) this) * -1;
        }
        return leafCompare((LeafNode) node);
    }

    protected int leafCompare(LeafNode<?> leafNode) {
        LeafType leafType = getLeafType();
        Enum leafType2 = leafNode.getLeafType();
        if (leafType.equals(leafType2)) {
            return compareLeafValues(leafNode);
        }
        return leafType.compareTo(leafType2);
    }

    public String toString() {
        String obj = getValue(true).toString();
        if (obj.length() <= 100) {
            return obj;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(obj.substring(0, 100));
        stringBuilder.append("...");
        return stringBuilder.toString();
    }
}
