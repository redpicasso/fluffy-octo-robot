package com.google.firebase.firestore;

import androidx.annotation.NonNull;
import com.google.common.base.Preconditions;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior;
import com.google.firebase.firestore.core.ViewSnapshot;
import com.google.firebase.firestore.model.Document;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class QuerySnapshot implements Iterable<QueryDocumentSnapshot> {
    private List<DocumentChange> cachedChanges;
    private MetadataChanges cachedChangesMetadataState;
    private final FirebaseFirestore firestore;
    private final SnapshotMetadata metadata;
    private final Query originalQuery;
    private final ViewSnapshot snapshot;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private class QuerySnapshotIterator implements Iterator<QueryDocumentSnapshot> {
        private final Iterator<Document> it;

        QuerySnapshotIterator(Iterator<Document> it) {
            this.it = it;
        }

        public boolean hasNext() {
            return this.it.hasNext();
        }

        public QueryDocumentSnapshot next() {
            return QuerySnapshot.this.convertDocument((Document) this.it.next());
        }

        public void remove() {
            throw new UnsupportedOperationException("QuerySnapshot does not support remove().");
        }
    }

    QuerySnapshot(Query query, ViewSnapshot viewSnapshot, FirebaseFirestore firebaseFirestore) {
        this.originalQuery = (Query) Preconditions.checkNotNull(query);
        this.snapshot = (ViewSnapshot) Preconditions.checkNotNull(viewSnapshot);
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firebaseFirestore);
        this.metadata = new SnapshotMetadata(viewSnapshot.hasPendingWrites(), viewSnapshot.isFromCache());
    }

    @PublicApi
    @NonNull
    public Query getQuery() {
        return this.originalQuery;
    }

    @PublicApi
    @NonNull
    public SnapshotMetadata getMetadata() {
        return this.metadata;
    }

    @PublicApi
    @NonNull
    public List<DocumentChange> getDocumentChanges() {
        return getDocumentChanges(MetadataChanges.EXCLUDE);
    }

    @PublicApi
    @NonNull
    public List<DocumentChange> getDocumentChanges(@NonNull MetadataChanges metadataChanges) {
        if (MetadataChanges.INCLUDE.equals(metadataChanges) && this.snapshot.excludesMetadataChanges()) {
            throw new IllegalArgumentException("To include metadata changes with your document changes, you must also pass MetadataChanges.INCLUDE to addSnapshotListener().");
        }
        if (this.cachedChanges == null || this.cachedChangesMetadataState != metadataChanges) {
            this.cachedChanges = Collections.unmodifiableList(DocumentChange.changesFromSnapshot(this.firestore, metadataChanges, this.snapshot));
            this.cachedChangesMetadataState = metadataChanges;
        }
        return this.cachedChanges;
    }

    @PublicApi
    @NonNull
    public List<DocumentSnapshot> getDocuments() {
        List<DocumentSnapshot> arrayList = new ArrayList(this.snapshot.getDocuments().size());
        Iterator it = this.snapshot.getDocuments().iterator();
        while (it.hasNext()) {
            arrayList.add(convertDocument((Document) it.next()));
        }
        return arrayList;
    }

    @PublicApi
    public boolean isEmpty() {
        return this.snapshot.getDocuments().isEmpty();
    }

    @PublicApi
    public int size() {
        return this.snapshot.getDocuments().size();
    }

    @PublicApi
    @NonNull
    public Iterator<QueryDocumentSnapshot> iterator() {
        return new QuerySnapshotIterator(this.snapshot.getDocuments().iterator());
    }

    @PublicApi
    @NonNull
    public <T> List<T> toObjects(@NonNull Class<T> cls) {
        return toObjects(cls, ServerTimestampBehavior.DEFAULT);
    }

    @PublicApi
    @NonNull
    public <T> List<T> toObjects(@NonNull Class<T> cls, @NonNull ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(cls, "Provided POJO type must not be null.");
        List<T> arrayList = new ArrayList();
        Iterator it = iterator();
        while (it.hasNext()) {
            arrayList.add(((DocumentSnapshot) it.next()).toObject(cls, serverTimestampBehavior));
        }
        return arrayList;
    }

    private QueryDocumentSnapshot convertDocument(Document document) {
        return QueryDocumentSnapshot.fromDocument(this.firestore, document, this.snapshot.isFromCache(), this.snapshot.getMutatedKeys().contains(document.getKey()));
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QuerySnapshot)) {
            return false;
        }
        QuerySnapshot querySnapshot = (QuerySnapshot) obj;
        if (!(this.firestore.equals(querySnapshot.firestore) && this.originalQuery.equals(querySnapshot.originalQuery) && this.snapshot.equals(querySnapshot.snapshot) && this.metadata.equals(querySnapshot.metadata))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((((this.firestore.hashCode() * 31) + this.originalQuery.hashCode()) * 31) + this.snapshot.hashCode()) * 31) + this.metadata.hashCode();
    }
}
