package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class RangeMerge {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Path optExclusiveStart;
    private final Path optInclusiveEnd;
    private final Node snap;

    public RangeMerge(Path path, Path path2, Node node) {
        this.optExclusiveStart = path;
        this.optInclusiveEnd = path2;
        this.snap = node;
    }

    public RangeMerge(com.google.firebase.database.connection.RangeMerge rangeMerge) {
        List optExclusiveStart = rangeMerge.getOptExclusiveStart();
        Path path = null;
        this.optExclusiveStart = optExclusiveStart != null ? new Path(optExclusiveStart) : null;
        optExclusiveStart = rangeMerge.getOptInclusiveEnd();
        if (optExclusiveStart != null) {
            path = new Path(optExclusiveStart);
        }
        this.optInclusiveEnd = path;
        this.snap = NodeUtilities.NodeFromJSON(rangeMerge.getSnap());
    }

    public Node applyTo(Node node) {
        return updateRangeInNode(Path.getEmptyPath(), node, this.snap);
    }

    Path getStart() {
        return this.optExclusiveStart;
    }

    Path getEnd() {
        return this.optInclusiveEnd;
    }

    private Node updateRangeInNode(Path path, Node node, Node node2) {
        Path path2 = this.optExclusiveStart;
        int compareTo = path2 == null ? 1 : path.compareTo(path2);
        Path path3 = this.optInclusiveEnd;
        int compareTo2 = path3 == null ? -1 : path.compareTo(path3);
        Path path4 = this.optExclusiveStart;
        Object obj = null;
        Object obj2 = (path4 == null || !path.contains(path4)) ? null : 1;
        Path path5 = this.optInclusiveEnd;
        if (path5 != null && path.contains(path5)) {
            obj = 1;
        }
        if (compareTo > 0 && compareTo2 < 0 && obj == null) {
            return node2;
        }
        if (compareTo > 0 && obj != null && node2.isLeafNode()) {
            return node2;
        }
        if (compareTo > 0 && compareTo2 == 0) {
            return node.isLeafNode() ? EmptyNode.Empty() : node;
        } else {
            if (obj2 == null && obj == null) {
                return node;
            }
            Collection hashSet = new HashSet();
            for (NamedNode name : node) {
                hashSet.add(name.getName());
            }
            for (NamedNode name2 : node2) {
                hashSet.add(name2.getName());
            }
            List<ChildKey> arrayList = new ArrayList(hashSet.size() + 1);
            arrayList.addAll(hashSet);
            if (!(node2.getPriority().isEmpty() && node.getPriority().isEmpty())) {
                arrayList.add(ChildKey.getPriorityKey());
            }
            Node node3 = node;
            for (ChildKey childKey : arrayList) {
                Node immediateChild = node.getImmediateChild(childKey);
                Node updateRangeInNode = updateRangeInNode(path.child(childKey), node.getImmediateChild(childKey), node2.getImmediateChild(childKey));
                if (updateRangeInNode != immediateChild) {
                    node3 = node3.updateImmediateChild(childKey, updateRangeInNode);
                }
            }
            return node3;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RangeMerge{optExclusiveStart=");
        stringBuilder.append(this.optExclusiveStart);
        stringBuilder.append(", optInclusiveEnd=");
        stringBuilder.append(this.optInclusiveEnd);
        stringBuilder.append(", snap=");
        stringBuilder.append(this.snap);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
