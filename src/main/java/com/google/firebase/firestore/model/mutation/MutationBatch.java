package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.util.Assert;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class MutationBatch {
    public static final int UNKNOWN = -1;
    private final List<Mutation> baseMutations;
    private final int batchId;
    private final Timestamp localWriteTime;
    private final List<Mutation> mutations;

    public MutationBatch(int i, Timestamp timestamp, List<Mutation> list, List<Mutation> list2) {
        Assert.hardAssert(list2.isEmpty() ^ 1, "Cannot create an empty mutation batch", new Object[0]);
        this.batchId = i;
        this.localWriteTime = timestamp;
        this.baseMutations = list;
        this.mutations = list2;
    }

    @Nullable
    public MaybeDocument applyToRemoteDocument(DocumentKey documentKey, @Nullable MaybeDocument maybeDocument, MutationBatchResult mutationBatchResult) {
        int i = 0;
        if (maybeDocument != null) {
            Assert.hardAssert(maybeDocument.getKey().equals(documentKey), "applyToRemoteDocument: key %s doesn't match maybeDoc key %s", documentKey, maybeDocument.getKey());
        }
        int size = this.mutations.size();
        List mutationResults = mutationBatchResult.getMutationResults();
        Assert.hardAssert(mutationResults.size() == size, "Mismatch between mutations length (%d) and results length (%d)", Integer.valueOf(size), Integer.valueOf(mutationResults.size()));
        while (i < size) {
            Mutation mutation = (Mutation) this.mutations.get(i);
            if (mutation.getKey().equals(documentKey)) {
                maybeDocument = mutation.applyToRemoteDocument(maybeDocument, (MutationResult) mutationResults.get(i));
            }
            i++;
        }
        return maybeDocument;
    }

    @Nullable
    public MaybeDocument applyToLocalView(DocumentKey documentKey, @Nullable MaybeDocument maybeDocument) {
        Mutation mutation;
        int i = 0;
        if (maybeDocument != null) {
            Assert.hardAssert(maybeDocument.getKey().equals(documentKey), "applyToRemoteDocument: key %s doesn't match maybeDoc key %s", documentKey, maybeDocument.getKey());
        }
        MaybeDocument maybeDocument2 = maybeDocument;
        for (int i2 = 0; i2 < this.baseMutations.size(); i2++) {
            mutation = (Mutation) this.baseMutations.get(i2);
            if (mutation.getKey().equals(documentKey)) {
                maybeDocument2 = mutation.applyToLocalView(maybeDocument2, maybeDocument2, this.localWriteTime);
            }
        }
        maybeDocument = maybeDocument2;
        while (i < this.mutations.size()) {
            mutation = (Mutation) this.mutations.get(i);
            if (mutation.getKey().equals(documentKey)) {
                maybeDocument = mutation.applyToLocalView(maybeDocument, maybeDocument2, this.localWriteTime);
            }
            i++;
        }
        return maybeDocument;
    }

    public ImmutableSortedMap<DocumentKey, MaybeDocument> applyToLocalDocumentSet(ImmutableSortedMap<DocumentKey, MaybeDocument> immutableSortedMap) {
        for (DocumentKey documentKey : getKeys()) {
            MaybeDocument applyToLocalView = applyToLocalView(documentKey, (MaybeDocument) immutableSortedMap.get(documentKey));
            if (applyToLocalView != null) {
                immutableSortedMap = immutableSortedMap.insert(applyToLocalView.getKey(), applyToLocalView);
            }
        }
        return immutableSortedMap;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MutationBatch mutationBatch = (MutationBatch) obj;
        if (!(this.batchId == mutationBatch.batchId && this.localWriteTime.equals(mutationBatch.localWriteTime) && this.baseMutations.equals(mutationBatch.baseMutations) && this.mutations.equals(mutationBatch.mutations))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((((this.batchId * 31) + this.localWriteTime.hashCode()) * 31) + this.baseMutations.hashCode()) * 31) + this.mutations.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MutationBatch(batchId=");
        stringBuilder.append(this.batchId);
        stringBuilder.append(", localWriteTime=");
        stringBuilder.append(this.localWriteTime);
        stringBuilder.append(", baseMutations=");
        stringBuilder.append(this.baseMutations);
        stringBuilder.append(", mutations=");
        stringBuilder.append(this.mutations);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public Set<DocumentKey> getKeys() {
        Set hashSet = new HashSet();
        for (Mutation key : this.mutations) {
            hashSet.add(key.getKey());
        }
        return hashSet;
    }

    public int getBatchId() {
        return this.batchId;
    }

    public Timestamp getLocalWriteTime() {
        return this.localWriteTime;
    }

    public List<Mutation> getMutations() {
        return this.mutations;
    }

    public List<Mutation> getBaseMutations() {
        return this.baseMutations;
    }
}
