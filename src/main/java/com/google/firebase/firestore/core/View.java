package com.google.firebase.firestore.core;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.DocumentViewChange.Type;
import com.google.firebase.firestore.core.ViewSnapshot.SyncState;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.DocumentSet;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.remote.TargetChange;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class View {
    private boolean current;
    private DocumentSet documentSet;
    private ImmutableSortedSet<DocumentKey> limboDocuments;
    private ImmutableSortedSet<DocumentKey> mutatedKeys;
    private final Query query;
    private SyncState syncState = SyncState.NONE;
    private ImmutableSortedSet<DocumentKey> syncedDocuments;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firebase.firestore.core.View$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type = new int[Type.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:8:?, code:
            $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type[com.google.firebase.firestore.core.DocumentViewChange.Type.REMOVED.ordinal()] = 4;
     */
        static {
            /*
            r0 = com.google.firebase.firestore.core.DocumentViewChange.Type.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type = r0;
            r0 = $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.ADDED;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.MODIFIED;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.METADATA;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.REMOVED;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.core.View.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class DocumentChanges {
        final DocumentViewChangeSet changeSet;
        final DocumentSet documentSet;
        final ImmutableSortedSet<DocumentKey> mutatedKeys;
        private final boolean needsRefill;

        /* synthetic */ DocumentChanges(DocumentSet documentSet, DocumentViewChangeSet documentViewChangeSet, ImmutableSortedSet immutableSortedSet, boolean z, AnonymousClass1 anonymousClass1) {
            this(documentSet, documentViewChangeSet, immutableSortedSet, z);
        }

        private DocumentChanges(DocumentSet documentSet, DocumentViewChangeSet documentViewChangeSet, ImmutableSortedSet<DocumentKey> immutableSortedSet, boolean z) {
            this.documentSet = documentSet;
            this.changeSet = documentViewChangeSet;
            this.mutatedKeys = immutableSortedSet;
            this.needsRefill = z;
        }

        public boolean needsRefill() {
            return this.needsRefill;
        }
    }

    public View(Query query, ImmutableSortedSet<DocumentKey> immutableSortedSet) {
        this.query = query;
        this.documentSet = DocumentSet.emptySet(query.comparator());
        this.syncedDocuments = immutableSortedSet;
        this.limboDocuments = DocumentKey.emptyKeySet();
        this.mutatedKeys = DocumentKey.emptyKeySet();
    }

    public <D extends MaybeDocument> DocumentChanges computeDocChanges(ImmutableSortedMap<DocumentKey, D> immutableSortedMap) {
        return computeDocChanges(immutableSortedMap, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:88:0x013f A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x013f A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x013f A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0118  */
    /* JADX WARNING: Missing block: B:51:0x00e5, code:
            if (r0.query.comparator().compare(r11, r4) > 0) goto L_0x0112;
     */
    /* JADX WARNING: Missing block: B:61:0x0110, code:
            if (r4 != null) goto L_0x0112;
     */
    /* JADX WARNING: Missing block: B:62:0x0112, code:
            r8 = 1;
            r10 = true;
     */
    public <D extends com.google.firebase.firestore.model.MaybeDocument> com.google.firebase.firestore.core.View.DocumentChanges computeDocChanges(com.google.firebase.database.collection.ImmutableSortedMap<com.google.firebase.firestore.model.DocumentKey, D> r18, @javax.annotation.Nullable com.google.firebase.firestore.core.View.DocumentChanges r19) {
        /*
        r17 = this;
        r0 = r17;
        r1 = r19;
        if (r1 == 0) goto L_0x0009;
    L_0x0006:
        r2 = r1.changeSet;
        goto L_0x000e;
    L_0x0009:
        r2 = new com.google.firebase.firestore.core.DocumentViewChangeSet;
        r2.<init>();
    L_0x000e:
        r5 = r2;
        if (r1 == 0) goto L_0x0014;
    L_0x0011:
        r2 = r1.documentSet;
        goto L_0x0016;
    L_0x0014:
        r2 = r0.documentSet;
    L_0x0016:
        if (r1 == 0) goto L_0x001b;
    L_0x0018:
        r3 = r1.mutatedKeys;
        goto L_0x001d;
    L_0x001b:
        r3 = r0.mutatedKeys;
    L_0x001d:
        r4 = r0.query;
        r4 = r4.hasLimit();
        if (r4 == 0) goto L_0x0039;
    L_0x0025:
        r4 = r2.size();
        r7 = (long) r4;
        r4 = r0.query;
        r9 = r4.getLimit();
        r4 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1));
        if (r4 != 0) goto L_0x0039;
    L_0x0034:
        r4 = r2.getLastDocument();
        goto L_0x003a;
    L_0x0039:
        r4 = 0;
    L_0x003a:
        r7 = r18.iterator();
        r8 = 0;
        r9 = r3;
        r10 = 0;
        r3 = r2;
    L_0x0042:
        r11 = r7.hasNext();
        r12 = 1;
        if (r11 == 0) goto L_0x0142;
    L_0x0049:
        r11 = r7.next();
        r11 = (java.util.Map.Entry) r11;
        r13 = r11.getKey();
        r13 = (com.google.firebase.firestore.model.DocumentKey) r13;
        r14 = r2.getDocument(r13);
        r11 = r11.getValue();
        r11 = (com.google.firebase.firestore.model.MaybeDocument) r11;
        r15 = r11 instanceof com.google.firebase.firestore.model.Document;
        if (r15 == 0) goto L_0x0066;
    L_0x0063:
        r11 = (com.google.firebase.firestore.model.Document) r11;
        goto L_0x0067;
    L_0x0066:
        r11 = 0;
    L_0x0067:
        if (r11 == 0) goto L_0x008a;
    L_0x0069:
        r15 = r11.getKey();
        r15 = r13.equals(r15);
        r6 = 2;
        r6 = new java.lang.Object[r6];
        r6[r8] = r13;
        r16 = r11.getKey();
        r6[r12] = r16;
        r12 = "Mismatching key in doc change %s != %s";
        com.google.firebase.firestore.util.Assert.hardAssert(r15, r12, r6);
        r6 = r0.query;
        r6 = r6.matches(r11);
        if (r6 != 0) goto L_0x008a;
    L_0x0089:
        r11 = 0;
    L_0x008a:
        if (r14 == 0) goto L_0x009a;
    L_0x008c:
        r6 = r0.mutatedKeys;
        r12 = r14.getKey();
        r6 = r6.contains(r12);
        if (r6 == 0) goto L_0x009a;
    L_0x0098:
        r6 = 1;
        goto L_0x009b;
    L_0x009a:
        r6 = 0;
    L_0x009b:
        if (r11 == 0) goto L_0x00b7;
    L_0x009d:
        r12 = r11.hasLocalMutations();
        if (r12 != 0) goto L_0x00b5;
    L_0x00a3:
        r12 = r0.mutatedKeys;
        r15 = r11.getKey();
        r12 = r12.contains(r15);
        if (r12 == 0) goto L_0x00b7;
    L_0x00af:
        r12 = r11.hasCommittedMutations();
        if (r12 == 0) goto L_0x00b7;
    L_0x00b5:
        r12 = 1;
        goto L_0x00b8;
    L_0x00b7:
        r12 = 0;
    L_0x00b8:
        if (r14 == 0) goto L_0x00f4;
    L_0x00ba:
        if (r11 == 0) goto L_0x00f4;
    L_0x00bc:
        r15 = r14.getData();
        r8 = r11.getData();
        r8 = r15.equals(r8);
        if (r8 != 0) goto L_0x00e8;
    L_0x00ca:
        r6 = r0.shouldWaitForSyncedDocument(r14, r11);
        if (r6 != 0) goto L_0x0115;
    L_0x00d0:
        r6 = com.google.firebase.firestore.core.DocumentViewChange.Type.MODIFIED;
        r6 = com.google.firebase.firestore.core.DocumentViewChange.create(r6, r11);
        r5.addChange(r6);
        if (r4 == 0) goto L_0x0101;
    L_0x00db:
        r6 = r0.query;
        r6 = r6.comparator();
        r6 = r6.compare(r11, r4);
        if (r6 <= 0) goto L_0x0101;
    L_0x00e7:
        goto L_0x0112;
    L_0x00e8:
        if (r6 == r12) goto L_0x0115;
    L_0x00ea:
        r6 = com.google.firebase.firestore.core.DocumentViewChange.Type.METADATA;
        r6 = com.google.firebase.firestore.core.DocumentViewChange.create(r6, r11);
        r5.addChange(r6);
        goto L_0x0101;
    L_0x00f4:
        if (r14 != 0) goto L_0x0103;
    L_0x00f6:
        if (r11 == 0) goto L_0x0103;
    L_0x00f8:
        r6 = com.google.firebase.firestore.core.DocumentViewChange.Type.ADDED;
        r6 = com.google.firebase.firestore.core.DocumentViewChange.create(r6, r11);
        r5.addChange(r6);
    L_0x0101:
        r8 = 1;
        goto L_0x0116;
    L_0x0103:
        if (r14 == 0) goto L_0x0115;
    L_0x0105:
        if (r11 != 0) goto L_0x0115;
    L_0x0107:
        r6 = com.google.firebase.firestore.core.DocumentViewChange.Type.REMOVED;
        r6 = com.google.firebase.firestore.core.DocumentViewChange.create(r6, r14);
        r5.addChange(r6);
        if (r4 == 0) goto L_0x0101;
    L_0x0112:
        r8 = 1;
        r10 = 1;
        goto L_0x0116;
    L_0x0115:
        r8 = 0;
    L_0x0116:
        if (r8 == 0) goto L_0x013f;
    L_0x0118:
        if (r11 == 0) goto L_0x0136;
    L_0x011a:
        r3 = r3.add(r11);
        r6 = r11.hasLocalMutations();
        if (r6 == 0) goto L_0x012d;
    L_0x0124:
        r6 = r11.getKey();
        r6 = r9.insert(r6);
        goto L_0x013e;
    L_0x012d:
        r6 = r11.getKey();
        r6 = r9.remove(r6);
        goto L_0x013e;
    L_0x0136:
        r3 = r3.remove(r13);
        r6 = r9.remove(r13);
    L_0x013e:
        r9 = r6;
    L_0x013f:
        r8 = 0;
        goto L_0x0042;
    L_0x0142:
        r2 = r0.query;
        r2 = r2.hasLimit();
        if (r2 == 0) goto L_0x017c;
    L_0x014a:
        r2 = r3.size();
        r6 = (long) r2;
        r2 = r0.query;
        r11 = r2.getLimit();
    L_0x0155:
        r6 = r6 - r11;
        r11 = 0;
        r2 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1));
        if (r2 <= 0) goto L_0x017c;
    L_0x015c:
        r2 = r3.getLastDocument();
        r4 = r2.getKey();
        r3 = r3.remove(r4);
        r4 = r2.getKey();
        r9 = r9.remove(r4);
        r4 = com.google.firebase.firestore.core.DocumentViewChange.Type.REMOVED;
        r2 = com.google.firebase.firestore.core.DocumentViewChange.create(r4, r2);
        r5.addChange(r2);
        r11 = 1;
        goto L_0x0155;
    L_0x017c:
        r4 = r3;
        r6 = r9;
        if (r10 == 0) goto L_0x0185;
    L_0x0180:
        if (r1 != 0) goto L_0x0183;
    L_0x0182:
        goto L_0x0185;
    L_0x0183:
        r1 = 0;
        goto L_0x0186;
    L_0x0185:
        r1 = 1;
    L_0x0186:
        r2 = 0;
        r2 = new java.lang.Object[r2];
        r3 = "View was refilled using docs that themselves needed refilling.";
        com.google.firebase.firestore.util.Assert.hardAssert(r1, r3, r2);
        r1 = new com.google.firebase.firestore.core.View$DocumentChanges;
        r8 = 0;
        r3 = r1;
        r7 = r10;
        r3.<init>(r4, r5, r6, r7, r8);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.core.View.computeDocChanges(com.google.firebase.database.collection.ImmutableSortedMap, com.google.firebase.firestore.core.View$DocumentChanges):com.google.firebase.firestore.core.View$DocumentChanges");
    }

    private boolean shouldWaitForSyncedDocument(Document document, Document document2) {
        return document.hasLocalMutations() && document2.hasCommittedMutations() && !document2.hasLocalMutations();
    }

    public ViewChange applyChanges(DocumentChanges documentChanges) {
        return applyChanges(documentChanges, null);
    }

    public ViewChange applyChanges(DocumentChanges documentChanges, TargetChange targetChange) {
        ViewSnapshot viewSnapshot;
        DocumentChanges documentChanges2 = documentChanges;
        Assert.hardAssert(documentChanges.needsRefill ^ true, "Cannot apply changes that need a refill", new Object[0]);
        DocumentSet documentSet = this.documentSet;
        this.documentSet = documentChanges2.documentSet;
        this.mutatedKeys = documentChanges2.mutatedKeys;
        List changes = documentChanges2.changeSet.getChanges();
        Collections.sort(changes, View$$Lambda$1.lambdaFactory$(this));
        applyTargetChange(targetChange);
        List updateLimboDocuments = updateLimboDocuments();
        Object obj = (this.limboDocuments.size() == 0 && this.current) ? 1 : null;
        SyncState syncState = obj != null ? SyncState.SYNCED : SyncState.LOCAL;
        boolean z = syncState != this.syncState;
        this.syncState = syncState;
        if (changes.size() != 0 || z) {
            ViewSnapshot viewSnapshot2 = new ViewSnapshot(this.query, documentChanges2.documentSet, documentSet, changes, syncState == SyncState.LOCAL, documentChanges2.mutatedKeys, z, false);
        } else {
            viewSnapshot = null;
        }
        return new ViewChange(viewSnapshot, updateLimboDocuments);
    }

    static /* synthetic */ int lambda$applyChanges$0(View view, DocumentViewChange documentViewChange, DocumentViewChange documentViewChange2) {
        int compareIntegers = Util.compareIntegers(changeTypeOrder(documentViewChange), changeTypeOrder(documentViewChange2));
        documentViewChange.getType().compareTo(documentViewChange2.getType());
        if (compareIntegers != 0) {
            return compareIntegers;
        }
        return view.query.comparator().compare(documentViewChange.getDocument(), documentViewChange2.getDocument());
    }

    public ViewChange applyOnlineStateChange(OnlineState onlineState) {
        if (!this.current || onlineState != OnlineState.OFFLINE) {
            return new ViewChange(null, Collections.emptyList());
        }
        this.current = false;
        return applyChanges(new DocumentChanges(this.documentSet, new DocumentViewChangeSet(), this.mutatedKeys, false, null));
    }

    private void applyTargetChange(TargetChange targetChange) {
        if (targetChange != null) {
            Iterator it = targetChange.getAddedDocuments().iterator();
            while (it.hasNext()) {
                this.syncedDocuments = this.syncedDocuments.insert((DocumentKey) it.next());
            }
            it = targetChange.getModifiedDocuments().iterator();
            while (it.hasNext()) {
                Assert.hardAssert(this.syncedDocuments.contains((DocumentKey) it.next()), "Modified document %s not found in view.", r1);
            }
            it = targetChange.getRemovedDocuments().iterator();
            while (it.hasNext()) {
                this.syncedDocuments = this.syncedDocuments.remove((DocumentKey) it.next());
            }
            this.current = targetChange.isCurrent();
        }
    }

    private List<LimboDocumentChange> updateLimboDocuments() {
        if (!this.current) {
            return Collections.emptyList();
        }
        DocumentKey documentKey;
        ImmutableSortedSet immutableSortedSet = this.limboDocuments;
        this.limboDocuments = DocumentKey.emptyKeySet();
        Iterator it = this.documentSet.iterator();
        while (it.hasNext()) {
            Document document = (Document) it.next();
            if (shouldBeLimboDoc(document.getKey())) {
                this.limboDocuments = this.limboDocuments.insert(document.getKey());
            }
        }
        List<LimboDocumentChange> arrayList = new ArrayList(immutableSortedSet.size() + this.limboDocuments.size());
        Iterator it2 = immutableSortedSet.iterator();
        while (it2.hasNext()) {
            documentKey = (DocumentKey) it2.next();
            if (!this.limboDocuments.contains(documentKey)) {
                arrayList.add(new LimboDocumentChange(LimboDocumentChange.Type.REMOVED, documentKey));
            }
        }
        it2 = this.limboDocuments.iterator();
        while (it2.hasNext()) {
            documentKey = (DocumentKey) it2.next();
            if (!immutableSortedSet.contains(documentKey)) {
                arrayList.add(new LimboDocumentChange(LimboDocumentChange.Type.ADDED, documentKey));
            }
        }
        return arrayList;
    }

    private boolean shouldBeLimboDoc(DocumentKey documentKey) {
        if (this.syncedDocuments.contains(documentKey)) {
            return false;
        }
        Document document = this.documentSet.getDocument(documentKey);
        if (document == null || document.hasLocalMutations()) {
            return false;
        }
        return true;
    }

    ImmutableSortedSet<DocumentKey> getLimboDocuments() {
        return this.limboDocuments;
    }

    ImmutableSortedSet<DocumentKey> getSyncedDocuments() {
        return this.syncedDocuments;
    }

    private static int changeTypeOrder(DocumentViewChange documentViewChange) {
        int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type[documentViewChange.getType().ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (!(i == 2 || i == 3)) {
                if (i == 4) {
                    return 0;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown change type: ");
                stringBuilder.append(documentViewChange.getType());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        return i2;
    }
}
