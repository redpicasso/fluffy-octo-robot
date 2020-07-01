package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.Node.HashVersion;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class LongNode extends LeafNode<LongNode> {
    private final long value;

    public LongNode(Long l, Node node) {
        super(node);
        this.value = l.longValue();
    }

    public Object getValue() {
        return Long.valueOf(this.value);
    }

    public String getHashRepresentation(HashVersion hashVersion) {
        String priorityHash = getPriorityHash(hashVersion);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(priorityHash);
        stringBuilder.append("number:");
        priorityHash = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(priorityHash);
        stringBuilder.append(Utilities.doubleToHashString((double) this.value));
        return stringBuilder.toString();
    }

    public LongNode updatePriority(Node node) {
        return new LongNode(Long.valueOf(this.value), node);
    }

    protected LeafType getLeafType() {
        return LeafType.Number;
    }

    protected int compareLeafValues(LongNode longNode) {
        return Utilities.compareLongs(this.value, longNode.value);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof LongNode)) {
            return false;
        }
        LongNode longNode = (LongNode) obj;
        if (this.value == longNode.value && this.priority.equals(longNode.priority)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        long j = this.value;
        return ((int) (j ^ (j >>> 32))) + this.priority.hashCode();
    }
}
