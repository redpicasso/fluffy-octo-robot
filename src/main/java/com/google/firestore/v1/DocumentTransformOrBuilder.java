package com.google.firestore.v1;

import com.google.firestore.v1.DocumentTransform.FieldTransform;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface DocumentTransformOrBuilder extends MessageLiteOrBuilder {
    String getDocument();

    ByteString getDocumentBytes();

    FieldTransform getFieldTransforms(int i);

    int getFieldTransformsCount();

    List<FieldTransform> getFieldTransformsList();
}
