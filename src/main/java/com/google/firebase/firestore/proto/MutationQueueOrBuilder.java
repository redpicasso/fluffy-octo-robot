package com.google.firebase.firestore.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface MutationQueueOrBuilder extends MessageLiteOrBuilder {
    int getLastAcknowledgedBatchId();

    ByteString getLastStreamToken();
}
