package com.google.firebase.firestore.core;

import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.DocumentViewChange.Type;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.DocumentSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class ViewSnapshot {
    private final List<DocumentViewChange> changes;
    private final boolean didSyncStateChange;
    private final DocumentSet documents;
    private boolean excludesMetadataChanges;
    private final boolean isFromCache;
    private final ImmutableSortedSet<DocumentKey> mutatedKeys;
    private final DocumentSet oldDocuments;
    private final Query query;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum SyncState {
        NONE,
        LOCAL,
        SYNCED
    }

    public ViewSnapshot(Query query, DocumentSet documentSet, DocumentSet documentSet2, List<DocumentViewChange> list, boolean z, ImmutableSortedSet<DocumentKey> immutableSortedSet, boolean z2, boolean z3) {
        this.query = query;
        this.documents = documentSet;
        this.oldDocuments = documentSet2;
        this.changes = list;
        this.isFromCache = z;
        this.mutatedKeys = immutableSortedSet;
        this.didSyncStateChange = z2;
        this.excludesMetadataChanges = z3;
    }

    public static ViewSnapshot fromInitialDocuments(Query query, DocumentSet documentSet, ImmutableSortedSet<DocumentKey> immutableSortedSet, boolean z, boolean z2) {
        List arrayList = new ArrayList();
        Iterator it = documentSet.iterator();
        while (it.hasNext()) {
            arrayList.add(DocumentViewChange.create(Type.ADDED, (Document) it.next()));
        }
        return new ViewSnapshot(query, documentSet, DocumentSet.emptySet(query.comparator()), arrayList, z, immutableSortedSet, true, z2);
    }

    public Query getQuery() {
        return this.query;
    }

    public DocumentSet getDocuments() {
        return this.documents;
    }

    public DocumentSet getOldDocuments() {
        return this.oldDocuments;
    }

    public List<DocumentViewChange> getChanges() {
        return this.changes;
    }

    public boolean isFromCache() {
        return this.isFromCache;
    }

    public boolean hasPendingWrites() {
        return this.mutatedKeys.isEmpty() ^ 1;
    }

    public ImmutableSortedSet<DocumentKey> getMutatedKeys() {
        return this.mutatedKeys;
    }

    public boolean didSyncStateChange() {
        return this.didSyncStateChange;
    }

    public boolean excludesMetadataChanges() {
        return this.excludesMetadataChanges;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ViewSnapshot)) {
            return false;
        }
        ViewSnapshot viewSnapshot = (ViewSnapshot) obj;
        if (this.isFromCache == viewSnapshot.isFromCache && this.didSyncStateChange == viewSnapshot.didSyncStateChange && this.excludesMetadataChanges == viewSnapshot.excludesMetadataChanges && this.query.equals(viewSnapshot.query) && this.mutatedKeys.equals(viewSnapshot.mutatedKeys) && this.documents.equals(viewSnapshot.documents) && this.oldDocuments.equals(viewSnapshot.oldDocuments)) {
            return this.changes.equals(viewSnapshot.changes);
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((this.query.hashCode() * 31) + this.documents.hashCode()) * 31) + this.oldDocuments.hashCode()) * 31) + this.changes.hashCode()) * 31) + this.mutatedKeys.hashCode()) * 31) + this.isFromCache) * 31) + this.didSyncStateChange) * 31) + this.excludesMetadataChanges;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewSnapshot(");
        stringBuilder.append(this.query);
        String str = ", ";
        stringBuilder.append(str);
        stringBuilder.append(this.documents);
        stringBuilder.append(str);
        stringBuilder.append(this.oldDocuments);
        stringBuilder.append(str);
        stringBuilder.append(this.changes);
        stringBuilder.append(", isFromCache=");
        stringBuilder.append(this.isFromCache);
        stringBuilder.append(", mutatedKeys=");
        stringBuilder.append(this.mutatedKeys.size());
        stringBuilder.append(", didSyncStateChange=");
        stringBuilder.append(this.didSyncStateChange);
        stringBuilder.append(", excludesMetadataChanges=");
        stringBuilder.append(this.excludesMetadataChanges);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
