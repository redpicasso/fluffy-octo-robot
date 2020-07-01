package com.google.firebase.database.core.view;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.WriteTreeRef;
import com.google.firebase.database.core.operation.AckUserWrite;
import com.google.firebase.database.core.operation.Merge;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.core.operation.Operation.OperationType;
import com.google.firebase.database.core.operation.Overwrite;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.view.filter.ChildChangeAccumulator;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.core.view.filter.NodeFilter.CompleteChildSource;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.KeyIndex;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ViewProcessor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static CompleteChildSource NO_COMPLETE_SOURCE = new CompleteChildSource() {
        public NamedNode getChildAfterChild(Index index, NamedNode namedNode, boolean z) {
            return null;
        }

        public Node getCompleteChild(ChildKey childKey) {
            return null;
        }
    };
    private final NodeFilter filter;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    /* renamed from: com.google.firebase.database.core.view.ViewProcessor$2 */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$database$core$operation$Operation$OperationType = new int[OperationType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:8:?, code:
            $SwitchMap$com$google$firebase$database$core$operation$Operation$OperationType[com.google.firebase.database.core.operation.Operation.OperationType.ListenComplete.ordinal()] = 4;
     */
        static {
            /*
            r0 = com.google.firebase.database.core.operation.Operation.OperationType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$database$core$operation$Operation$OperationType = r0;
            r0 = $SwitchMap$com$google$firebase$database$core$operation$Operation$OperationType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.database.core.operation.Operation.OperationType.Overwrite;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$database$core$operation$Operation$OperationType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.database.core.operation.Operation.OperationType.Merge;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$database$core$operation$Operation$OperationType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.database.core.operation.Operation.OperationType.AckUserWrite;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$google$firebase$database$core$operation$Operation$OperationType;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.google.firebase.database.core.operation.Operation.OperationType.ListenComplete;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.view.ViewProcessor.2.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public static class ProcessorResult {
        public final List<Change> changes;
        public final ViewCache viewCache;

        public ProcessorResult(ViewCache viewCache, List<Change> list) {
            this.viewCache = viewCache;
            this.changes = list;
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class WriteTreeCompleteChildSource implements CompleteChildSource {
        private final Node optCompleteServerCache;
        private final ViewCache viewCache;
        private final WriteTreeRef writes;

        public WriteTreeCompleteChildSource(WriteTreeRef writeTreeRef, ViewCache viewCache, Node node) {
            this.writes = writeTreeRef;
            this.viewCache = viewCache;
            this.optCompleteServerCache = node;
        }

        public Node getCompleteChild(ChildKey childKey) {
            CacheNode eventCache = this.viewCache.getEventCache();
            if (eventCache.isCompleteForChild(childKey)) {
                return eventCache.getNode().getImmediateChild(childKey);
            }
            CacheNode cacheNode;
            Node node = this.optCompleteServerCache;
            if (node != null) {
                cacheNode = new CacheNode(IndexedNode.from(node, KeyIndex.getInstance()), true, false);
            } else {
                cacheNode = this.viewCache.getServerCache();
            }
            return this.writes.calcCompleteChild(childKey, cacheNode);
        }

        public NamedNode getChildAfterChild(Index index, NamedNode namedNode, boolean z) {
            Node node = this.optCompleteServerCache;
            if (node == null) {
                node = this.viewCache.getCompleteServerSnap();
            }
            return this.writes.calcNextNodeAfterPost(node, namedNode, z, index);
        }
    }

    public ViewProcessor(NodeFilter nodeFilter) {
        this.filter = nodeFilter;
    }

    public ProcessorResult applyOperation(ViewCache viewCache, Operation operation, WriteTreeRef writeTreeRef, Node node) {
        ViewCache applyUserOverwrite;
        ChildChangeAccumulator childChangeAccumulator = new ChildChangeAccumulator();
        int i = AnonymousClass2.$SwitchMap$com$google$firebase$database$core$operation$Operation$OperationType[operation.getType().ordinal()];
        boolean z;
        if (i == 1) {
            Overwrite overwrite = (Overwrite) operation;
            if (overwrite.getSource().isFromUser()) {
                applyUserOverwrite = applyUserOverwrite(viewCache, overwrite.getPath(), overwrite.getSnapshot(), writeTreeRef, node, childChangeAccumulator);
            } else {
                z = overwrite.getSource().isTagged() || (viewCache.getServerCache().isFiltered() && !overwrite.getPath().isEmpty());
                applyUserOverwrite = applyServerOverwrite(viewCache, overwrite.getPath(), overwrite.getSnapshot(), writeTreeRef, node, z, childChangeAccumulator);
            }
        } else if (i == 2) {
            Merge merge = (Merge) operation;
            if (merge.getSource().isFromUser()) {
                applyUserOverwrite = applyUserMerge(viewCache, merge.getPath(), merge.getChildren(), writeTreeRef, node, childChangeAccumulator);
            } else {
                z = merge.getSource().isTagged() || viewCache.getServerCache().isFiltered();
                applyUserOverwrite = applyServerMerge(viewCache, merge.getPath(), merge.getChildren(), writeTreeRef, node, z, childChangeAccumulator);
            }
        } else if (i == 3) {
            AckUserWrite ackUserWrite = (AckUserWrite) operation;
            if (ackUserWrite.isRevert()) {
                applyUserOverwrite = revertUserWrite(viewCache, ackUserWrite.getPath(), writeTreeRef, node, childChangeAccumulator);
            } else {
                applyUserOverwrite = ackUserWrite(viewCache, ackUserWrite.getPath(), ackUserWrite.getAffectedTree(), writeTreeRef, node, childChangeAccumulator);
            }
        } else if (i == 4) {
            applyUserOverwrite = listenComplete(viewCache, operation.getPath(), writeTreeRef, node, childChangeAccumulator);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown operation: ");
            stringBuilder.append(operation.getType());
            throw new AssertionError(stringBuilder.toString());
        }
        List arrayList = new ArrayList(childChangeAccumulator.getChanges());
        maybeAddValueEvent(viewCache, applyUserOverwrite, arrayList);
        return new ProcessorResult(applyUserOverwrite, arrayList);
    }

    private void maybeAddValueEvent(ViewCache viewCache, ViewCache viewCache2, List<Change> list) {
        CacheNode eventCache = viewCache2.getEventCache();
        if (eventCache.isFullyInitialized()) {
            Object obj = (eventCache.getNode().isLeafNode() || eventCache.getNode().isEmpty()) ? 1 : null;
            if (!list.isEmpty() || !viewCache.getEventCache().isFullyInitialized() || ((obj != null && !eventCache.getNode().equals(viewCache.getCompleteEventSnap())) || !eventCache.getNode().getPriority().equals(viewCache.getCompleteEventSnap().getPriority()))) {
                list.add(Change.valueChange(eventCache.getIndexedNode()));
            }
        }
    }

    private ViewCache generateEventCacheAfterServerEvent(ViewCache viewCache, Path path, WriteTreeRef writeTreeRef, CompleteChildSource completeChildSource, ChildChangeAccumulator childChangeAccumulator) {
        CacheNode eventCache = viewCache.getEventCache();
        if (writeTreeRef.shadowingWrite(path) != null) {
            return viewCache;
        }
        IndexedNode updateFullNode;
        Node calcCompleteEventChildren;
        if (path.isEmpty()) {
            if (viewCache.getServerCache().isFiltered()) {
                Node completeServerSnap = viewCache.getCompleteServerSnap();
                if (!(completeServerSnap instanceof ChildrenNode)) {
                    completeServerSnap = EmptyNode.Empty();
                }
                calcCompleteEventChildren = writeTreeRef.calcCompleteEventChildren(completeServerSnap);
            } else {
                calcCompleteEventChildren = writeTreeRef.calcCompleteEventCache(viewCache.getCompleteServerSnap());
            }
            updateFullNode = this.filter.updateFullNode(viewCache.getEventCache().getIndexedNode(), IndexedNode.from(calcCompleteEventChildren, this.filter.getIndex()), childChangeAccumulator);
        } else {
            ChildKey front = path.getFront();
            if (front.isPriorityChildName()) {
                calcCompleteEventChildren = writeTreeRef.calcEventCacheAfterServerOverwrite(path, eventCache.getNode(), viewCache.getServerCache().getNode());
                if (calcCompleteEventChildren != null) {
                    updateFullNode = this.filter.updatePriority(eventCache.getIndexedNode(), calcCompleteEventChildren);
                } else {
                    updateFullNode = eventCache.getIndexedNode();
                }
            } else {
                Path popFront = path.popFront();
                if (eventCache.isCompleteForChild(front)) {
                    calcCompleteEventChildren = writeTreeRef.calcEventCacheAfterServerOverwrite(path, eventCache.getNode(), viewCache.getServerCache().getNode());
                    if (calcCompleteEventChildren != null) {
                        calcCompleteEventChildren = eventCache.getNode().getImmediateChild(front).updateChild(popFront, calcCompleteEventChildren);
                    } else {
                        calcCompleteEventChildren = eventCache.getNode().getImmediateChild(front);
                    }
                } else {
                    calcCompleteEventChildren = writeTreeRef.calcCompleteChild(front, viewCache.getServerCache());
                }
                Node node = calcCompleteEventChildren;
                if (node != null) {
                    updateFullNode = this.filter.updateChild(eventCache.getIndexedNode(), front, node, popFront, completeChildSource, childChangeAccumulator);
                } else {
                    updateFullNode = eventCache.getIndexedNode();
                }
            }
        }
        boolean z = eventCache.isFullyInitialized() || path.isEmpty();
        return viewCache.updateEventSnap(updateFullNode, z, this.filter.filtersNodes());
    }

    private ViewCache applyServerOverwrite(ViewCache viewCache, Path path, Node node, WriteTreeRef writeTreeRef, Node node2, boolean z, ChildChangeAccumulator childChangeAccumulator) {
        IndexedNode updateFullNode;
        ViewCache updateServerSnap;
        WriteTreeRef writeTreeRef2;
        ViewCache viewCache2 = viewCache;
        Node node3 = node;
        CacheNode serverCache = viewCache.getServerCache();
        NodeFilter indexedFilter = z ? this.filter : this.filter.getIndexedFilter();
        boolean z2 = true;
        if (path.isEmpty()) {
            updateFullNode = indexedFilter.updateFullNode(serverCache.getIndexedNode(), IndexedNode.from(node3, indexedFilter.getIndex()), null);
        } else if (!indexedFilter.filtersNodes() || serverCache.isFiltered()) {
            ChildKey front = path.getFront();
            if (!serverCache.isCompleteForPath(path) && path.size() > 1) {
                return viewCache2;
            }
            Path popFront = path.popFront();
            Node updateChild = serverCache.getNode().getImmediateChild(front).updateChild(popFront, node3);
            if (front.isPriorityChildName()) {
                updateFullNode = indexedFilter.updatePriority(serverCache.getIndexedNode(), updateChild);
            } else {
                updateFullNode = indexedFilter.updateChild(serverCache.getIndexedNode(), front, updateChild, popFront, NO_COMPLETE_SOURCE, null);
            }
            if (!(serverCache.isFullyInitialized() || path.isEmpty())) {
                z2 = false;
            }
            updateServerSnap = viewCache.updateServerSnap(updateFullNode, z2, indexedFilter.filtersNodes());
            writeTreeRef2 = writeTreeRef;
            return generateEventCacheAfterServerEvent(updateServerSnap, path, writeTreeRef2, new WriteTreeCompleteChildSource(writeTreeRef2, updateServerSnap, node2), childChangeAccumulator);
        } else {
            ChildKey front2 = path.getFront();
            updateFullNode = indexedFilter.updateFullNode(serverCache.getIndexedNode(), serverCache.getIndexedNode().updateChild(front2, serverCache.getNode().getImmediateChild(front2).updateChild(path.popFront(), node3)), null);
        }
        Path path2 = path;
        z2 = false;
        updateServerSnap = viewCache.updateServerSnap(updateFullNode, z2, indexedFilter.filtersNodes());
        writeTreeRef2 = writeTreeRef;
        return generateEventCacheAfterServerEvent(updateServerSnap, path, writeTreeRef2, new WriteTreeCompleteChildSource(writeTreeRef2, updateServerSnap, node2), childChangeAccumulator);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x009d  */
    private com.google.firebase.database.core.view.ViewCache applyUserOverwrite(com.google.firebase.database.core.view.ViewCache r9, com.google.firebase.database.core.Path r10, com.google.firebase.database.snapshot.Node r11, com.google.firebase.database.core.WriteTreeRef r12, com.google.firebase.database.snapshot.Node r13, com.google.firebase.database.core.view.filter.ChildChangeAccumulator r14) {
        /*
        r8 = this;
        r0 = r9.getEventCache();
        r6 = new com.google.firebase.database.core.view.ViewProcessor$WriteTreeCompleteChildSource;
        r6.<init>(r12, r9, r13);
        r12 = r10.isEmpty();
        if (r12 == 0) goto L_0x0034;
    L_0x000f:
        r10 = r8.filter;
        r10 = r10.getIndex();
        r10 = com.google.firebase.database.snapshot.IndexedNode.from(r11, r10);
        r11 = r8.filter;
        r12 = r9.getEventCache();
        r12 = r12.getIndexedNode();
        r10 = r11.updateFullNode(r12, r10, r14);
        r11 = 1;
        r12 = r8.filter;
        r12 = r12.filtersNodes();
        r9 = r9.updateEventSnap(r10, r11, r12);
        goto L_0x00b6;
    L_0x0034:
        r3 = r10.getFront();
        r12 = r3.isPriorityChildName();
        if (r12 == 0) goto L_0x0059;
    L_0x003e:
        r10 = r8.filter;
        r12 = r9.getEventCache();
        r12 = r12.getIndexedNode();
        r10 = r10.updatePriority(r12, r11);
        r11 = r0.isFullyInitialized();
        r12 = r0.isFiltered();
        r9 = r9.updateEventSnap(r10, r11, r12);
        goto L_0x00b6;
    L_0x0059:
        r5 = r10.popFront();
        r10 = r0.getNode();
        r10 = r10.getImmediateChild(r3);
        r12 = r5.isEmpty();
        if (r12 == 0) goto L_0x006d;
    L_0x006b:
        r4 = r11;
        goto L_0x0097;
    L_0x006d:
        r12 = r6.getCompleteChild(r3);
        if (r12 == 0) goto L_0x0092;
    L_0x0073:
        r13 = r5.getBack();
        r13 = r13.isPriorityChildName();
        if (r13 == 0) goto L_0x008d;
    L_0x007d:
        r13 = r5.getParent();
        r13 = r12.getChild(r13);
        r13 = r13.isEmpty();
        if (r13 == 0) goto L_0x008d;
    L_0x008b:
        r4 = r12;
        goto L_0x0097;
    L_0x008d:
        r11 = r12.updateChild(r5, r11);
        goto L_0x006b;
    L_0x0092:
        r11 = com.google.firebase.database.snapshot.EmptyNode.Empty();
        goto L_0x006b;
    L_0x0097:
        r10 = r10.equals(r4);
        if (r10 != 0) goto L_0x00b6;
    L_0x009d:
        r1 = r8.filter;
        r2 = r0.getIndexedNode();
        r7 = r14;
        r10 = r1.updateChild(r2, r3, r4, r5, r6, r7);
        r11 = r0.isFullyInitialized();
        r12 = r8.filter;
        r12 = r12.filtersNodes();
        r9 = r9.updateEventSnap(r10, r11, r12);
    L_0x00b6:
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.view.ViewProcessor.applyUserOverwrite(com.google.firebase.database.core.view.ViewCache, com.google.firebase.database.core.Path, com.google.firebase.database.snapshot.Node, com.google.firebase.database.core.WriteTreeRef, com.google.firebase.database.snapshot.Node, com.google.firebase.database.core.view.filter.ChildChangeAccumulator):com.google.firebase.database.core.view.ViewCache");
    }

    private static boolean cacheHasChild(ViewCache viewCache, ChildKey childKey) {
        return viewCache.getEventCache().isCompleteForChild(childKey);
    }

    private ViewCache applyUserMerge(ViewCache viewCache, Path path, CompoundWrite compoundWrite, WriteTreeRef writeTreeRef, Node node, ChildChangeAccumulator childChangeAccumulator) {
        Entry entry;
        ViewCache viewCache2 = viewCache;
        Path path2 = path;
        Iterator it = compoundWrite.iterator();
        ViewCache viewCache3 = viewCache2;
        while (it.hasNext()) {
            entry = (Entry) it.next();
            Path child = path.child((Path) entry.getKey());
            if (cacheHasChild(viewCache, child.getFront())) {
                viewCache3 = applyUserOverwrite(viewCache3, child, (Node) entry.getValue(), writeTreeRef, node, childChangeAccumulator);
            }
        }
        it = compoundWrite.iterator();
        ViewCache viewCache4 = viewCache3;
        while (it.hasNext()) {
            entry = (Entry) it.next();
            Path child2 = path.child((Path) entry.getKey());
            if (!cacheHasChild(viewCache, child2.getFront())) {
                viewCache4 = applyUserOverwrite(viewCache4, child2, (Node) entry.getValue(), writeTreeRef, node, childChangeAccumulator);
            }
        }
        return viewCache4;
    }

    private ViewCache applyServerMerge(ViewCache viewCache, Path path, CompoundWrite compoundWrite, WriteTreeRef writeTreeRef, Node node, boolean z, ChildChangeAccumulator childChangeAccumulator) {
        if (viewCache.getServerCache().getNode().isEmpty() && !viewCache.getServerCache().isFullyInitialized()) {
            return viewCache;
        }
        CompoundWrite compoundWrite2;
        if (path.isEmpty()) {
            compoundWrite2 = compoundWrite;
        } else {
            compoundWrite2 = CompoundWrite.emptyWrite().addWrites(path, compoundWrite);
        }
        Node node2 = viewCache.getServerCache().getNode();
        Map childCompoundWrites = compoundWrite2.childCompoundWrites();
        ViewCache viewCache2 = viewCache;
        for (Entry entry : childCompoundWrites.entrySet()) {
            ChildKey childKey = (ChildKey) entry.getKey();
            if (node2.hasChild(childKey)) {
                viewCache2 = applyServerOverwrite(viewCache2, new Path(childKey), ((CompoundWrite) entry.getValue()).apply(node2.getImmediateChild(childKey)), writeTreeRef, node, z, childChangeAccumulator);
            }
        }
        ViewCache viewCache3 = viewCache2;
        for (Entry entry2 : childCompoundWrites.entrySet()) {
            ChildKey childKey2 = (ChildKey) entry2.getKey();
            Object obj = (viewCache.getServerCache().isCompleteForChild(childKey2) || ((CompoundWrite) entry2.getValue()).rootWrite() != null) ? null : 1;
            if (!node2.hasChild(childKey2) && obj == null) {
                viewCache3 = applyServerOverwrite(viewCache3, new Path(childKey2), ((CompoundWrite) entry2.getValue()).apply(node2.getImmediateChild(childKey2)), writeTreeRef, node, z, childChangeAccumulator);
            }
        }
        return viewCache3;
    }

    private ViewCache ackUserWrite(ViewCache viewCache, Path path, ImmutableTree<Boolean> immutableTree, WriteTreeRef writeTreeRef, Node node, ChildChangeAccumulator childChangeAccumulator) {
        if (writeTreeRef.shadowingWrite(path) != null) {
            return viewCache;
        }
        boolean isFiltered = viewCache.getServerCache().isFiltered();
        CacheNode serverCache = viewCache.getServerCache();
        CompoundWrite compoundWrite;
        if (immutableTree.getValue() == null) {
            CompoundWrite emptyWrite = CompoundWrite.emptyWrite();
            Iterator it = immutableTree.iterator();
            compoundWrite = emptyWrite;
            while (it.hasNext()) {
                Path path2 = (Path) ((Entry) it.next()).getKey();
                Path child = path.child(path2);
                if (serverCache.isCompleteForPath(child)) {
                    compoundWrite = compoundWrite.addWrite(path2, serverCache.getNode().getChild(child));
                }
            }
            return applyServerMerge(viewCache, path, compoundWrite, writeTreeRef, node, isFiltered, childChangeAccumulator);
        } else if ((path.isEmpty() && serverCache.isFullyInitialized()) || serverCache.isCompleteForPath(path)) {
            return applyServerOverwrite(viewCache, path, serverCache.getNode().getChild(path), writeTreeRef, node, isFiltered, childChangeAccumulator);
        } else {
            if (path.isEmpty()) {
                CompoundWrite emptyWrite2 = CompoundWrite.emptyWrite();
                compoundWrite = emptyWrite2;
                for (NamedNode namedNode : serverCache.getNode()) {
                    compoundWrite = compoundWrite.addWrite(namedNode.getName(), namedNode.getNode());
                }
                viewCache = applyServerMerge(viewCache, path, compoundWrite, writeTreeRef, node, isFiltered, childChangeAccumulator);
            }
            return viewCache;
        }
    }

    public ViewCache revertUserWrite(ViewCache viewCache, Path path, WriteTreeRef writeTreeRef, Node node, ChildChangeAccumulator childChangeAccumulator) {
        if (writeTreeRef.shadowingWrite(path) != null) {
            return viewCache;
        }
        CompleteChildSource writeTreeCompleteChildSource = new WriteTreeCompleteChildSource(writeTreeRef, viewCache, node);
        IndexedNode indexedNode = viewCache.getEventCache().getIndexedNode();
        Node calcCompleteEventCache;
        if (path.isEmpty() || path.getFront().isPriorityChildName()) {
            if (viewCache.getServerCache().isFullyInitialized()) {
                calcCompleteEventCache = writeTreeRef.calcCompleteEventCache(viewCache.getCompleteServerSnap());
            } else {
                calcCompleteEventCache = writeTreeRef.calcCompleteEventChildren(viewCache.getServerCache().getNode());
            }
            indexedNode = this.filter.updateFullNode(indexedNode, IndexedNode.from(calcCompleteEventCache, this.filter.getIndex()), childChangeAccumulator);
        } else {
            ChildKey front = path.getFront();
            node = writeTreeRef.calcCompleteChild(front, viewCache.getServerCache());
            if (node == null && viewCache.getServerCache().isCompleteForChild(front)) {
                node = indexedNode.getNode().getImmediateChild(front);
            }
            Node node2 = node;
            if (node2 != null) {
                indexedNode = this.filter.updateChild(indexedNode, front, node2, path.popFront(), writeTreeCompleteChildSource, childChangeAccumulator);
            } else if (node2 == null && viewCache.getEventCache().getNode().hasChild(front)) {
                indexedNode = this.filter.updateChild(indexedNode, front, EmptyNode.Empty(), path.popFront(), writeTreeCompleteChildSource, childChangeAccumulator);
            }
            if (indexedNode.getNode().isEmpty() && viewCache.getServerCache().isFullyInitialized()) {
                calcCompleteEventCache = writeTreeRef.calcCompleteEventCache(viewCache.getCompleteServerSnap());
                if (calcCompleteEventCache.isLeafNode()) {
                    indexedNode = this.filter.updateFullNode(indexedNode, IndexedNode.from(calcCompleteEventCache, this.filter.getIndex()), childChangeAccumulator);
                }
            }
        }
        boolean z = viewCache.getServerCache().isFullyInitialized() || writeTreeRef.shadowingWrite(Path.getEmptyPath()) != null;
        return viewCache.updateEventSnap(indexedNode, z, this.filter.filtersNodes());
    }

    private ViewCache listenComplete(ViewCache viewCache, Path path, WriteTreeRef writeTreeRef, Node node, ChildChangeAccumulator childChangeAccumulator) {
        CacheNode serverCache = viewCache.getServerCache();
        IndexedNode indexedNode = serverCache.getIndexedNode();
        boolean z = serverCache.isFullyInitialized() || path.isEmpty();
        return generateEventCacheAfterServerEvent(viewCache.updateServerSnap(indexedNode, z, serverCache.isFiltered()), path, writeTreeRef, NO_COMPLETE_SOURCE, childChangeAccumulator);
    }
}
