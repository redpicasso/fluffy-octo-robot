package com.google.firebase.database.core.utilities;

import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.LongNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.StringNode;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class NodeSizeEstimator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int LEAF_PRIORITY_OVERHEAD = 24;

    private static long estimateLeafNodeSize(LeafNode<?> leafNode) {
        long j = 8;
        if (!((leafNode instanceof DoubleNode) || (leafNode instanceof LongNode))) {
            if (leafNode instanceof BooleanNode) {
                j = 4;
            } else if (leafNode instanceof StringNode) {
                j = ((long) ((String) leafNode.getValue()).length()) + 2;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown leaf node type: ");
                stringBuilder.append(leafNode.getClass());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        if (leafNode.getPriority().isEmpty()) {
            return j;
        }
        return (j + 24) + estimateLeafNodeSize((LeafNode) leafNode.getPriority());
    }

    public static long estimateSerializedNodeSize(Node node) {
        if (node.isEmpty()) {
            return 4;
        }
        if (node.isLeafNode()) {
            return estimateLeafNodeSize((LeafNode) node);
        }
        long j = 1;
        for (NamedNode namedNode : node) {
            j = ((j + ((long) namedNode.getName().asString().length())) + 4) + estimateSerializedNodeSize(namedNode.getNode());
        }
        if (!node.getPriority().isEmpty()) {
            j = (j + 12) + estimateLeafNodeSize((LeafNode) node.getPriority());
        }
        return j;
    }

    public static int nodeCount(Node node) {
        int i = 0;
        if (node.isEmpty()) {
            return 0;
        }
        if (node.isLeafNode()) {
            return 1;
        }
        for (NamedNode node2 : node) {
            i += nodeCount(node2.getNode());
        }
        return i;
    }
}
