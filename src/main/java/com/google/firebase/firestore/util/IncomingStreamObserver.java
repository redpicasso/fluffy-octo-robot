package com.google.firebase.firestore.util;

import io.grpc.Metadata;
import io.grpc.Status;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface IncomingStreamObserver<RespT> {
    void onClose(Status status);

    void onHeaders(Metadata metadata);

    void onNext(RespT respT);

    void onReady();
}
