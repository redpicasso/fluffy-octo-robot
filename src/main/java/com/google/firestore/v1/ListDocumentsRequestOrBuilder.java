package com.google.firestore.v1;

import com.google.firestore.v1.ListDocumentsRequest.ConsistencySelectorCase;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Timestamp;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface ListDocumentsRequestOrBuilder extends MessageLiteOrBuilder {
    String getCollectionId();

    ByteString getCollectionIdBytes();

    ConsistencySelectorCase getConsistencySelectorCase();

    DocumentMask getMask();

    String getOrderBy();

    ByteString getOrderByBytes();

    int getPageSize();

    String getPageToken();

    ByteString getPageTokenBytes();

    String getParent();

    ByteString getParentBytes();

    Timestamp getReadTime();

    boolean getShowMissing();

    ByteString getTransaction();

    boolean hasMask();
}