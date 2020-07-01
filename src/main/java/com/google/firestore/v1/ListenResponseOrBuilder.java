package com.google.firestore.v1;

import com.google.firestore.v1.ListenResponse.ResponseTypeCase;
import com.google.protobuf.MessageLiteOrBuilder;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface ListenResponseOrBuilder extends MessageLiteOrBuilder {
    DocumentChange getDocumentChange();

    DocumentDelete getDocumentDelete();

    DocumentRemove getDocumentRemove();

    ExistenceFilter getFilter();

    ResponseTypeCase getResponseTypeCase();

    TargetChange getTargetChange();
}
