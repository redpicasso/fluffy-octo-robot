package com.google.firebase.database.core;

import com.google.firebase.database.core.utilities.Predicate;
import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class WriteTree {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Predicate<UserWriteRecord> DEFAULT_FILTER = new Predicate<UserWriteRecord>() {
        public boolean evaluate(UserWriteRecord userWriteRecord) {
            return userWriteRecord.isVisible();
        }
    };
    private List<UserWriteRecord> allWrites = new ArrayList();
    private Long lastWriteId = Long.valueOf(-1);
    private CompoundWrite visibleWrites = CompoundWrite.emptyWrite();

    public WriteTreeRef childWrites(Path path) {
        return new WriteTreeRef(path, this);
    }

    public void addOverwrite(Path path, Node node, Long l, boolean z) {
        this.allWrites.add(new UserWriteRecord(l.longValue(), path, node, z));
        if (z) {
            this.visibleWrites = this.visibleWrites.addWrite(path, node);
        }
        this.lastWriteId = l;
    }

    public void addMerge(Path path, CompoundWrite compoundWrite, Long l) {
        this.allWrites.add(new UserWriteRecord(l.longValue(), path, compoundWrite));
        this.visibleWrites = this.visibleWrites.addWrites(path, compoundWrite);
        this.lastWriteId = l;
    }

    public UserWriteRecord getWrite(long j) {
        for (UserWriteRecord userWriteRecord : this.allWrites) {
            if (userWriteRecord.getWriteId() == j) {
                return userWriteRecord;
            }
        }
        return null;
    }

    public List<UserWriteRecord> purgeAllWrites() {
        List arrayList = new ArrayList(this.allWrites);
        this.visibleWrites = CompoundWrite.emptyWrite();
        this.allWrites = new ArrayList();
        return arrayList;
    }

    public boolean removeWrite(long j) {
        int i = 0;
        for (UserWriteRecord userWriteRecord : this.allWrites) {
            if (userWriteRecord.getWriteId() == j) {
                break;
            }
            i++;
        }
        UserWriteRecord userWriteRecord2 = null;
        this.allWrites.remove(userWriteRecord2);
        boolean isVisible = userWriteRecord2.isVisible();
        int size = this.allWrites.size() - 1;
        Object obj = null;
        while (isVisible && size >= 0) {
            UserWriteRecord userWriteRecord3 = (UserWriteRecord) this.allWrites.get(size);
            if (userWriteRecord3.isVisible()) {
                if (size >= i && recordContainsPath(userWriteRecord3, userWriteRecord2.getPath())) {
                    isVisible = false;
                } else if (userWriteRecord2.getPath().contains(userWriteRecord3.getPath())) {
                    obj = 1;
                }
            }
            size--;
        }
        if (!isVisible) {
            return false;
        }
        if (obj != null) {
            resetTree();
            return true;
        }
        if (userWriteRecord2.isOverwrite()) {
            this.visibleWrites = this.visibleWrites.removeWrite(userWriteRecord2.getPath());
        } else {
            Iterator it = userWriteRecord2.getMerge().iterator();
            while (it.hasNext()) {
                this.visibleWrites = this.visibleWrites.removeWrite(userWriteRecord2.getPath().child((Path) ((Entry) it.next()).getKey()));
            }
        }
        return true;
    }

    public Node getCompleteWriteData(Path path) {
        return this.visibleWrites.getCompleteNode(path);
    }

    public Node calcCompleteEventCache(Path path, Node node) {
        return calcCompleteEventCache(path, node, new ArrayList());
    }

    public Node calcCompleteEventCache(Path path, Node node, List<Long> list) {
        return calcCompleteEventCache(path, node, list, false);
    }

    public Node calcCompleteEventCache(final Path path, Node node, final List<Long> list, final boolean z) {
        CompoundWrite layerTree;
        if (!list.isEmpty() || z) {
            CompoundWrite childCompoundWrite = this.visibleWrites.childCompoundWrite(path);
            if (!z && childCompoundWrite.isEmpty()) {
                return node;
            }
            if (!z && node == null && !childCompoundWrite.hasCompleteWrite(Path.getEmptyPath())) {
                return null;
            }
            layerTree = layerTree(this.allWrites, new Predicate<UserWriteRecord>() {
                public boolean evaluate(UserWriteRecord userWriteRecord) {
                    return (userWriteRecord.isVisible() || z) && !list.contains(Long.valueOf(userWriteRecord.getWriteId())) && (userWriteRecord.getPath().contains(path) || path.contains(userWriteRecord.getPath()));
                }
            }, path);
            if (node == null) {
                node = EmptyNode.Empty();
            }
            return layerTree.apply(node);
        }
        Node completeNode = this.visibleWrites.getCompleteNode(path);
        if (completeNode != null) {
            return completeNode;
        }
        layerTree = this.visibleWrites.childCompoundWrite(path);
        if (layerTree.isEmpty()) {
            return node;
        }
        if (node == null && !layerTree.hasCompleteWrite(Path.getEmptyPath())) {
            return null;
        }
        if (node == null) {
            node = EmptyNode.Empty();
        }
        return layerTree.apply(node);
    }

    public Node calcCompleteEventChildren(Path path, Node node) {
        Node Empty = EmptyNode.Empty();
        Node<NamedNode> completeNode = this.visibleWrites.getCompleteNode(path);
        if (completeNode != null) {
            if (!completeNode.isLeafNode()) {
                for (NamedNode namedNode : completeNode) {
                    Empty = Empty.updateImmediateChild(namedNode.getName(), namedNode.getNode());
                }
            }
            return Empty;
        }
        CompoundWrite childCompoundWrite = this.visibleWrites.childCompoundWrite(path);
        for (NamedNode namedNode2 : node) {
            Empty = Empty.updateImmediateChild(namedNode2.getName(), childCompoundWrite.childCompoundWrite(new Path(namedNode2.getName())).apply(namedNode2.getNode()));
        }
        for (NamedNode namedNode3 : childCompoundWrite.getCompleteChildren()) {
            Empty = Empty.updateImmediateChild(namedNode3.getName(), namedNode3.getNode());
        }
        return Empty;
    }

    public Node calcEventCacheAfterServerOverwrite(Path path, Path path2, Node node, Node node2) {
        path = path.child(path2);
        if (this.visibleWrites.hasCompleteWrite(path)) {
            return null;
        }
        CompoundWrite childCompoundWrite = this.visibleWrites.childCompoundWrite(path);
        if (childCompoundWrite.isEmpty()) {
            return node2.getChild(path2);
        }
        return childCompoundWrite.apply(node2.getChild(path2));
    }

    public Node calcCompleteChild(Path path, ChildKey childKey, CacheNode cacheNode) {
        path = path.child(childKey);
        Node completeNode = this.visibleWrites.getCompleteNode(path);
        if (completeNode != null) {
            return completeNode;
        }
        return cacheNode.isCompleteForChild(childKey) ? this.visibleWrites.childCompoundWrite(path).apply(cacheNode.getNode().getImmediateChild(childKey)) : null;
    }

    public Node shadowingWrite(Path path) {
        return this.visibleWrites.getCompleteNode(path);
    }

    public NamedNode calcNextNodeAfterPost(Path path, Node node, NamedNode namedNode, boolean z, Index index) {
        CompoundWrite childCompoundWrite = this.visibleWrites.childCompoundWrite(path);
        Node completeNode = childCompoundWrite.getCompleteNode(Path.getEmptyPath());
        NamedNode namedNode2 = null;
        if (completeNode == null) {
            if (node != null) {
                completeNode = childCompoundWrite.apply(node);
            }
            return namedNode2;
        }
        for (NamedNode namedNode3 : completeNode) {
            if (index.compare(namedNode3, namedNode, z) > 0 && (r1 == null || index.compare(namedNode3, r1, z) < 0)) {
                namedNode2 = namedNode3;
            }
        }
        return namedNode2;
    }

    private boolean recordContainsPath(UserWriteRecord userWriteRecord, Path path) {
        if (userWriteRecord.isOverwrite()) {
            return userWriteRecord.getPath().contains(path);
        }
        Iterator it = userWriteRecord.getMerge().iterator();
        while (it.hasNext()) {
            if (userWriteRecord.getPath().child((Path) ((Entry) it.next()).getKey()).contains(path)) {
                return true;
            }
        }
        return false;
    }

    private void resetTree() {
        this.visibleWrites = layerTree(this.allWrites, DEFAULT_FILTER, Path.getEmptyPath());
        if (this.allWrites.size() > 0) {
            List list = this.allWrites;
            this.lastWriteId = Long.valueOf(((UserWriteRecord) list.get(list.size() - 1)).getWriteId());
            return;
        }
        this.lastWriteId = Long.valueOf(-1);
    }

    private static CompoundWrite layerTree(List<UserWriteRecord> list, Predicate<UserWriteRecord> predicate, Path path) {
        CompoundWrite emptyWrite = CompoundWrite.emptyWrite();
        for (UserWriteRecord userWriteRecord : list) {
            if (predicate.evaluate(userWriteRecord)) {
                Path path2 = userWriteRecord.getPath();
                if (userWriteRecord.isOverwrite()) {
                    if (path.contains(path2)) {
                        emptyWrite = emptyWrite.addWrite(Path.getRelative(path, path2), userWriteRecord.getOverwrite());
                    } else if (path2.contains(path)) {
                        emptyWrite = emptyWrite.addWrite(Path.getEmptyPath(), userWriteRecord.getOverwrite().getChild(Path.getRelative(path2, path)));
                    }
                } else if (path.contains(path2)) {
                    emptyWrite = emptyWrite.addWrites(Path.getRelative(path, path2), userWriteRecord.getMerge());
                } else if (path2.contains(path)) {
                    path2 = Path.getRelative(path2, path);
                    if (path2.isEmpty()) {
                        emptyWrite = emptyWrite.addWrites(Path.getEmptyPath(), userWriteRecord.getMerge());
                    } else {
                        Node completeNode = userWriteRecord.getMerge().getCompleteNode(path2);
                        if (completeNode != null) {
                            emptyWrite = emptyWrite.addWrite(Path.getEmptyPath(), completeNode);
                        }
                    }
                }
            }
        }
        return emptyWrite;
    }
}
