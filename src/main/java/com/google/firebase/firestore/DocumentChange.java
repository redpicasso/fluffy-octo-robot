package com.google.firebase.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.firestore.core.DocumentViewChange;
import com.google.firebase.firestore.core.ViewSnapshot;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentSet;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class DocumentChange {
    private final QueryDocumentSnapshot document;
    private final int newIndex;
    private final int oldIndex;
    private final Type type;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firebase.firestore.DocumentChange$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type = new int[com.google.firebase.firestore.core.DocumentViewChange.Type.values().length];

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
            r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.METADATA;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.MODIFIED;	 Catch:{ NoSuchFieldError -> 0x002a }
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
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.DocumentChange.1.<clinit>():void");
        }
    }

    @PublicApi
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Type {
        ADDED,
        MODIFIED,
        REMOVED
    }

    @VisibleForTesting
    DocumentChange(QueryDocumentSnapshot queryDocumentSnapshot, Type type, int i, int i2) {
        this.type = type;
        this.document = queryDocumentSnapshot;
        this.oldIndex = i;
        this.newIndex = i2;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof DocumentChange)) {
            return false;
        }
        DocumentChange documentChange = (DocumentChange) obj;
        if (this.type.equals(documentChange.type) && this.document.equals(documentChange.document) && this.oldIndex == documentChange.oldIndex && this.newIndex == documentChange.newIndex) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((this.type.hashCode() * 31) + this.document.hashCode()) * 31) + this.oldIndex) * 31) + this.newIndex;
    }

    @PublicApi
    @NonNull
    public Type getType() {
        return this.type;
    }

    @PublicApi
    @NonNull
    public QueryDocumentSnapshot getDocument() {
        return this.document;
    }

    @PublicApi
    public int getOldIndex() {
        return this.oldIndex;
    }

    @PublicApi
    public int getNewIndex() {
        return this.newIndex;
    }

    static List<DocumentChange> changesFromSnapshot(FirebaseFirestore firebaseFirestore, MetadataChanges metadataChanges, ViewSnapshot viewSnapshot) {
        List<DocumentChange> arrayList = new ArrayList();
        Document document;
        QueryDocumentSnapshot fromDocument;
        int i;
        if (viewSnapshot.getOldDocuments().isEmpty()) {
            Object obj = null;
            int i2 = 0;
            for (DocumentViewChange documentViewChange : viewSnapshot.getChanges()) {
                document = documentViewChange.getDocument();
                fromDocument = QueryDocumentSnapshot.fromDocument(firebaseFirestore, document, viewSnapshot.isFromCache(), viewSnapshot.getMutatedKeys().contains(document.getKey()));
                Assert.hardAssert(documentViewChange.getType() == com.google.firebase.firestore.core.DocumentViewChange.Type.ADDED, "Invalid added event for first snapshot", new Object[0]);
                boolean z = obj == null || viewSnapshot.getQuery().comparator().compare(obj, document) < 0;
                Assert.hardAssert(z, "Got added events in wrong order", new Object[0]);
                i = i2 + 1;
                arrayList.add(new DocumentChange(fromDocument, Type.ADDED, -1, i2));
                Document obj2 = document;
                i2 = i;
            }
        } else {
            DocumentSet oldDocuments = viewSnapshot.getOldDocuments();
            for (DocumentViewChange documentViewChange2 : viewSnapshot.getChanges()) {
                if (metadataChanges != MetadataChanges.EXCLUDE || documentViewChange2.getType() != com.google.firebase.firestore.core.DocumentViewChange.Type.METADATA) {
                    int indexOf;
                    document = documentViewChange2.getDocument();
                    fromDocument = QueryDocumentSnapshot.fromDocument(firebaseFirestore, document, viewSnapshot.isFromCache(), viewSnapshot.getMutatedKeys().contains(document.getKey()));
                    Type type = getType(documentViewChange2);
                    String str = "Index for document not found";
                    if (type != Type.ADDED) {
                        i = oldDocuments.indexOf(document.getKey());
                        Assert.hardAssert(i >= 0, str, new Object[0]);
                        oldDocuments = oldDocuments.remove(document.getKey());
                    } else {
                        i = -1;
                    }
                    if (type != Type.REMOVED) {
                        oldDocuments = oldDocuments.add(document);
                        indexOf = oldDocuments.indexOf(document.getKey());
                        Assert.hardAssert(indexOf >= 0, str, new Object[0]);
                    } else {
                        indexOf = -1;
                    }
                    arrayList.add(new DocumentChange(fromDocument, type, i, indexOf));
                }
            }
        }
        return arrayList;
    }

    private static Type getType(DocumentViewChange documentViewChange) {
        int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type[documentViewChange.getType().ordinal()];
        if (i == 1) {
            return Type.ADDED;
        }
        if (i == 2 || i == 3) {
            return Type.MODIFIED;
        }
        if (i == 4) {
            return Type.REMOVED;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown view change type: ");
        stringBuilder.append(documentViewChange.getType());
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
