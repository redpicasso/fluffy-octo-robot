package com.google.firebase.firestore.remote;

import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import io.grpc.CallCredentials.RequestInfo;
import io.grpc.CallCredentials2;
import io.grpc.CallCredentials2.MetadataApplier;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.Status;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class FirestoreCallCredentials extends CallCredentials2 {
    private static final Key<String> AUTHORIZATION_HEADER = Key.of(HttpHeaders.AUTHORIZATION, Metadata.ASCII_STRING_MARSHALLER);
    private static final String LOG_TAG = "FirestoreCallCredentials";
    private final CredentialsProvider credentialsProvider;

    public void thisUsesUnstableApi() {
    }

    public FirestoreCallCredentials(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        this.credentialsProvider.getToken().addOnSuccessListener(executor, FirestoreCallCredentials$$Lambda$1.lambdaFactory$(metadataApplier)).addOnFailureListener(executor, FirestoreCallCredentials$$Lambda$2.lambdaFactory$(metadataApplier));
    }

    static /* synthetic */ void lambda$applyRequestMetadata$0(MetadataApplier metadataApplier, String str) {
        Logger.debug(LOG_TAG, "Successfully fetched token.", new Object[0]);
        Metadata metadata = new Metadata();
        if (str != null) {
            Key key = AUTHORIZATION_HEADER;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bearer ");
            stringBuilder.append(str);
            metadata.put(key, stringBuilder.toString());
        }
        metadataApplier.apply(metadata);
    }

    static /* synthetic */ void lambda$applyRequestMetadata$1(MetadataApplier metadataApplier, Exception exception) {
        boolean z = exception instanceof FirebaseApiNotAvailableException;
        String str = LOG_TAG;
        if (z) {
            Logger.debug(str, "Firebase Auth API not available, not using authentication.", new Object[0]);
            metadataApplier.apply(new Metadata());
        } else if (exception instanceof FirebaseNoSignedInUserException) {
            Logger.debug(str, "No user signed in, not using authentication.", new Object[0]);
            metadataApplier.apply(new Metadata());
        } else {
            Logger.warn(str, "Failed to get token: %s.", exception);
            metadataApplier.fail(Status.UNAUTHENTICATED.withCause(exception));
        }
    }
}
