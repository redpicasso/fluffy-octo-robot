package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.database.snapshot.Node;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Transaction {

    @PublicApi
    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface Handler {
        @PublicApi
        @NonNull
        Result doTransaction(@NonNull MutableData mutableData);

        @PublicApi
        void onComplete(@Nullable DatabaseError databaseError, boolean z, @Nullable DataSnapshot dataSnapshot);
    }

    @PublicApi
    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public static class Result {
        private Node data;
        private boolean success;

        private Result(boolean z, Node node) {
            this.success = z;
            this.data = node;
        }

        @PublicApi
        public boolean isSuccess() {
            return this.success;
        }

        @RestrictTo({Scope.LIBRARY_GROUP})
        public Node getNode() {
            return this.data;
        }
    }

    @PublicApi
    @NonNull
    public static Result abort() {
        return new Result(false, null);
    }

    @PublicApi
    @NonNull
    public static Result success(@NonNull MutableData mutableData) {
        return new Result(true, mutableData.getNode());
    }
}
