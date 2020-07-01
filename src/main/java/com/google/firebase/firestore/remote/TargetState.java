package com.google.firebase.firestore.remote;

import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.DocumentViewChange.Type;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.util.Assert;
import com.google.protobuf.ByteString;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final class TargetState {
    private boolean current = false;
    private final Map<DocumentKey, Type> documentChanges = new HashMap();
    private boolean hasChanges = true;
    private int outstandingResponses = 0;
    private ByteString resumeToken = ByteString.EMPTY;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firebase.firestore.remote.TargetState$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type = new int[Type.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type[com.google.firebase.firestore.core.DocumentViewChange.Type.REMOVED.ordinal()] = 3;
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
            r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.REMOVED;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.remote.TargetState.1.<clinit>():void");
        }
    }

    TargetState() {
    }

    boolean isCurrent() {
        return this.current;
    }

    boolean isPending() {
        return this.outstandingResponses != 0;
    }

    boolean hasChanges() {
        return this.hasChanges;
    }

    void updateResumeToken(ByteString byteString) {
        if (!byteString.isEmpty()) {
            this.hasChanges = true;
            this.resumeToken = byteString;
        }
    }

    TargetChange toTargetChange() {
        ImmutableSortedSet emptyKeySet = DocumentKey.emptyKeySet();
        ImmutableSortedSet emptyKeySet2 = DocumentKey.emptyKeySet();
        ImmutableSortedSet emptyKeySet3 = DocumentKey.emptyKeySet();
        ImmutableSortedSet immutableSortedSet = emptyKeySet;
        ImmutableSortedSet immutableSortedSet2 = emptyKeySet2;
        ImmutableSortedSet immutableSortedSet3 = emptyKeySet3;
        for (Entry entry : this.documentChanges.entrySet()) {
            DocumentKey documentKey = (DocumentKey) entry.getKey();
            int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type[((Type) entry.getValue()).ordinal()];
            if (i == 1) {
                immutableSortedSet = immutableSortedSet.insert(documentKey);
            } else if (i == 2) {
                immutableSortedSet2 = immutableSortedSet2.insert(documentKey);
            } else if (i == 3) {
                immutableSortedSet3 = immutableSortedSet3.insert(documentKey);
            } else {
                throw Assert.fail("Encountered invalid change type: %s", r0);
            }
        }
        return new TargetChange(this.resumeToken, this.current, immutableSortedSet, immutableSortedSet2, immutableSortedSet3);
    }

    void clearChanges() {
        this.hasChanges = false;
        this.documentChanges.clear();
    }

    void addDocumentChange(DocumentKey documentKey, Type type) {
        this.hasChanges = true;
        this.documentChanges.put(documentKey, type);
    }

    void removeDocumentChange(DocumentKey documentKey) {
        this.hasChanges = true;
        this.documentChanges.remove(documentKey);
    }

    void recordPendingTargetRequest() {
        this.outstandingResponses++;
    }

    void recordTargetResponse() {
        this.outstandingResponses--;
    }

    void markCurrent() {
        this.hasChanges = true;
        this.current = true;
    }
}
