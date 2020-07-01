package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class DeleteMutation extends Mutation {
    @Nullable
    public FieldMask getFieldMask() {
        return null;
    }

    public boolean isIdempotent() {
        return true;
    }

    public DeleteMutation(DocumentKey documentKey, Precondition precondition) {
        super(documentKey, precondition);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj == null || getClass() != obj.getClass()) ? false : hasSameKeyAndPrecondition((DeleteMutation) obj);
    }

    public int hashCode() {
        return keyAndPreconditionHashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DeleteMutation{");
        stringBuilder.append(keyAndPreconditionToString());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public MaybeDocument applyToRemoteDocument(@Nullable MaybeDocument maybeDocument, MutationResult mutationResult) {
        verifyKeyMatches(maybeDocument);
        Assert.hardAssert(mutationResult.getTransformResults() == null, "Transform results received by DeleteMutation.", new Object[0]);
        return new NoDocument(getKey(), mutationResult.getVersion(), true);
    }

    @Nullable
    public MaybeDocument applyToLocalView(@Nullable MaybeDocument maybeDocument, @Nullable MaybeDocument maybeDocument2, Timestamp timestamp) {
        verifyKeyMatches(maybeDocument);
        if (getPrecondition().isValidFor(maybeDocument)) {
            return new NoDocument(getKey(), SnapshotVersion.NONE, false);
        }
        return maybeDocument;
    }
}
