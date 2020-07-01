package com.google.firestore.v1;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.Internal.ProtobufList;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class DocumentTransform extends GeneratedMessageLite<DocumentTransform, Builder> implements DocumentTransformOrBuilder {
    private static final DocumentTransform DEFAULT_INSTANCE = new DocumentTransform();
    public static final int DOCUMENT_FIELD_NUMBER = 1;
    public static final int FIELD_TRANSFORMS_FIELD_NUMBER = 2;
    private static volatile Parser<DocumentTransform> PARSER;
    private int bitField0_;
    private String document_ = "";
    private ProtobufList<FieldTransform> fieldTransforms_ = GeneratedMessageLite.emptyProtobufList();

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface FieldTransformOrBuilder extends MessageLiteOrBuilder {
        ArrayValue getAppendMissingElements();

        String getFieldPath();

        ByteString getFieldPathBytes();

        Value getIncrement();

        Value getMaximum();

        Value getMinimum();

        ArrayValue getRemoveAllFromArray();

        ServerValue getSetToServerValue();

        int getSetToServerValueValue();

        TransformTypeCase getTransformTypeCase();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<DocumentTransform, Builder> implements DocumentTransformOrBuilder {
        private Builder() {
            super(DocumentTransform.DEFAULT_INSTANCE);
        }

        public String getDocument() {
            return ((DocumentTransform) this.instance).getDocument();
        }

        public ByteString getDocumentBytes() {
            return ((DocumentTransform) this.instance).getDocumentBytes();
        }

        public Builder setDocument(String str) {
            copyOnWrite();
            ((DocumentTransform) this.instance).setDocument(str);
            return this;
        }

        public Builder clearDocument() {
            copyOnWrite();
            ((DocumentTransform) this.instance).clearDocument();
            return this;
        }

        public Builder setDocumentBytes(ByteString byteString) {
            copyOnWrite();
            ((DocumentTransform) this.instance).setDocumentBytes(byteString);
            return this;
        }

        public List<FieldTransform> getFieldTransformsList() {
            return Collections.unmodifiableList(((DocumentTransform) this.instance).getFieldTransformsList());
        }

        public int getFieldTransformsCount() {
            return ((DocumentTransform) this.instance).getFieldTransformsCount();
        }

        public FieldTransform getFieldTransforms(int i) {
            return ((DocumentTransform) this.instance).getFieldTransforms(i);
        }

        public Builder setFieldTransforms(int i, FieldTransform fieldTransform) {
            copyOnWrite();
            ((DocumentTransform) this.instance).setFieldTransforms(i, fieldTransform);
            return this;
        }

        public Builder setFieldTransforms(int i, Builder builder) {
            copyOnWrite();
            ((DocumentTransform) this.instance).setFieldTransforms(i, builder);
            return this;
        }

        public Builder addFieldTransforms(FieldTransform fieldTransform) {
            copyOnWrite();
            ((DocumentTransform) this.instance).addFieldTransforms(fieldTransform);
            return this;
        }

        public Builder addFieldTransforms(int i, FieldTransform fieldTransform) {
            copyOnWrite();
            ((DocumentTransform) this.instance).addFieldTransforms(i, fieldTransform);
            return this;
        }

        public Builder addFieldTransforms(Builder builder) {
            copyOnWrite();
            ((DocumentTransform) this.instance).addFieldTransforms(builder);
            return this;
        }

        public Builder addFieldTransforms(int i, Builder builder) {
            copyOnWrite();
            ((DocumentTransform) this.instance).addFieldTransforms(i, builder);
            return this;
        }

        public Builder addAllFieldTransforms(Iterable<? extends FieldTransform> iterable) {
            copyOnWrite();
            ((DocumentTransform) this.instance).addAllFieldTransforms(iterable);
            return this;
        }

        public Builder clearFieldTransforms() {
            copyOnWrite();
            ((DocumentTransform) this.instance).clearFieldTransforms();
            return this;
        }

        public Builder removeFieldTransforms(int i) {
            copyOnWrite();
            ((DocumentTransform) this.instance).removeFieldTransforms(i);
            return this;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class FieldTransform extends GeneratedMessageLite<FieldTransform, Builder> implements FieldTransformOrBuilder {
        public static final int APPEND_MISSING_ELEMENTS_FIELD_NUMBER = 6;
        private static final FieldTransform DEFAULT_INSTANCE = new FieldTransform();
        public static final int FIELD_PATH_FIELD_NUMBER = 1;
        public static final int INCREMENT_FIELD_NUMBER = 3;
        public static final int MAXIMUM_FIELD_NUMBER = 4;
        public static final int MINIMUM_FIELD_NUMBER = 5;
        private static volatile Parser<FieldTransform> PARSER = null;
        public static final int REMOVE_ALL_FROM_ARRAY_FIELD_NUMBER = 7;
        public static final int SET_TO_SERVER_VALUE_FIELD_NUMBER = 2;
        private String fieldPath_ = "";
        private int transformTypeCase_ = 0;
        private Object transformType_;

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public enum ServerValue implements EnumLite {
            SERVER_VALUE_UNSPECIFIED(0),
            REQUEST_TIME(1),
            UNRECOGNIZED(-1);
            
            public static final int REQUEST_TIME_VALUE = 1;
            public static final int SERVER_VALUE_UNSPECIFIED_VALUE = 0;
            private static final EnumLiteMap<ServerValue> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<ServerValue>() {
                    public ServerValue findValueByNumber(int i) {
                        return ServerValue.forNumber(i);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static ServerValue valueOf(int i) {
                return forNumber(i);
            }

            public static ServerValue forNumber(int i) {
                if (i != 0) {
                    return i != 1 ? null : REQUEST_TIME;
                } else {
                    return SERVER_VALUE_UNSPECIFIED;
                }
            }

            public static EnumLiteMap<ServerValue> internalGetValueMap() {
                return internalValueMap;
            }

            private ServerValue(int i) {
                this.value = i;
            }
        }

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public enum TransformTypeCase implements EnumLite {
            SET_TO_SERVER_VALUE(2),
            INCREMENT(3),
            MAXIMUM(4),
            MINIMUM(5),
            APPEND_MISSING_ELEMENTS(6),
            REMOVE_ALL_FROM_ARRAY(7),
            TRANSFORMTYPE_NOT_SET(0);
            
            private final int value;

            private TransformTypeCase(int i) {
                this.value = i;
            }

            @Deprecated
            public static TransformTypeCase valueOf(int i) {
                return forNumber(i);
            }

            public static TransformTypeCase forNumber(int i) {
                if (i == 0) {
                    return TRANSFORMTYPE_NOT_SET;
                }
                switch (i) {
                    case 2:
                        return SET_TO_SERVER_VALUE;
                    case 3:
                        return INCREMENT;
                    case 4:
                        return MAXIMUM;
                    case 5:
                        return MINIMUM;
                    case 6:
                        return APPEND_MISSING_ELEMENTS;
                    case 7:
                        return REMOVE_ALL_FROM_ARRAY;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<FieldTransform, Builder> implements FieldTransformOrBuilder {
            private Builder() {
                super(FieldTransform.DEFAULT_INSTANCE);
            }

            public TransformTypeCase getTransformTypeCase() {
                return ((FieldTransform) this.instance).getTransformTypeCase();
            }

            public Builder clearTransformType() {
                copyOnWrite();
                ((FieldTransform) this.instance).clearTransformType();
                return this;
            }

            public String getFieldPath() {
                return ((FieldTransform) this.instance).getFieldPath();
            }

            public ByteString getFieldPathBytes() {
                return ((FieldTransform) this.instance).getFieldPathBytes();
            }

            public Builder setFieldPath(String str) {
                copyOnWrite();
                ((FieldTransform) this.instance).setFieldPath(str);
                return this;
            }

            public Builder clearFieldPath() {
                copyOnWrite();
                ((FieldTransform) this.instance).clearFieldPath();
                return this;
            }

            public Builder setFieldPathBytes(ByteString byteString) {
                copyOnWrite();
                ((FieldTransform) this.instance).setFieldPathBytes(byteString);
                return this;
            }

            public int getSetToServerValueValue() {
                return ((FieldTransform) this.instance).getSetToServerValueValue();
            }

            public Builder setSetToServerValueValue(int i) {
                copyOnWrite();
                ((FieldTransform) this.instance).setSetToServerValueValue(i);
                return this;
            }

            public ServerValue getSetToServerValue() {
                return ((FieldTransform) this.instance).getSetToServerValue();
            }

            public Builder setSetToServerValue(ServerValue serverValue) {
                copyOnWrite();
                ((FieldTransform) this.instance).setSetToServerValue(serverValue);
                return this;
            }

            public Builder clearSetToServerValue() {
                copyOnWrite();
                ((FieldTransform) this.instance).clearSetToServerValue();
                return this;
            }

            public Value getIncrement() {
                return ((FieldTransform) this.instance).getIncrement();
            }

            public Builder setIncrement(Value value) {
                copyOnWrite();
                ((FieldTransform) this.instance).setIncrement(value);
                return this;
            }

            public Builder setIncrement(com.google.firestore.v1.Value.Builder builder) {
                copyOnWrite();
                ((FieldTransform) this.instance).setIncrement(builder);
                return this;
            }

            public Builder mergeIncrement(Value value) {
                copyOnWrite();
                ((FieldTransform) this.instance).mergeIncrement(value);
                return this;
            }

            public Builder clearIncrement() {
                copyOnWrite();
                ((FieldTransform) this.instance).clearIncrement();
                return this;
            }

            public Value getMaximum() {
                return ((FieldTransform) this.instance).getMaximum();
            }

            public Builder setMaximum(Value value) {
                copyOnWrite();
                ((FieldTransform) this.instance).setMaximum(value);
                return this;
            }

            public Builder setMaximum(com.google.firestore.v1.Value.Builder builder) {
                copyOnWrite();
                ((FieldTransform) this.instance).setMaximum(builder);
                return this;
            }

            public Builder mergeMaximum(Value value) {
                copyOnWrite();
                ((FieldTransform) this.instance).mergeMaximum(value);
                return this;
            }

            public Builder clearMaximum() {
                copyOnWrite();
                ((FieldTransform) this.instance).clearMaximum();
                return this;
            }

            public Value getMinimum() {
                return ((FieldTransform) this.instance).getMinimum();
            }

            public Builder setMinimum(Value value) {
                copyOnWrite();
                ((FieldTransform) this.instance).setMinimum(value);
                return this;
            }

            public Builder setMinimum(com.google.firestore.v1.Value.Builder builder) {
                copyOnWrite();
                ((FieldTransform) this.instance).setMinimum(builder);
                return this;
            }

            public Builder mergeMinimum(Value value) {
                copyOnWrite();
                ((FieldTransform) this.instance).mergeMinimum(value);
                return this;
            }

            public Builder clearMinimum() {
                copyOnWrite();
                ((FieldTransform) this.instance).clearMinimum();
                return this;
            }

            public ArrayValue getAppendMissingElements() {
                return ((FieldTransform) this.instance).getAppendMissingElements();
            }

            public Builder setAppendMissingElements(ArrayValue arrayValue) {
                copyOnWrite();
                ((FieldTransform) this.instance).setAppendMissingElements(arrayValue);
                return this;
            }

            public Builder setAppendMissingElements(com.google.firestore.v1.ArrayValue.Builder builder) {
                copyOnWrite();
                ((FieldTransform) this.instance).setAppendMissingElements(builder);
                return this;
            }

            public Builder mergeAppendMissingElements(ArrayValue arrayValue) {
                copyOnWrite();
                ((FieldTransform) this.instance).mergeAppendMissingElements(arrayValue);
                return this;
            }

            public Builder clearAppendMissingElements() {
                copyOnWrite();
                ((FieldTransform) this.instance).clearAppendMissingElements();
                return this;
            }

            public ArrayValue getRemoveAllFromArray() {
                return ((FieldTransform) this.instance).getRemoveAllFromArray();
            }

            public Builder setRemoveAllFromArray(ArrayValue arrayValue) {
                copyOnWrite();
                ((FieldTransform) this.instance).setRemoveAllFromArray(arrayValue);
                return this;
            }

            public Builder setRemoveAllFromArray(com.google.firestore.v1.ArrayValue.Builder builder) {
                copyOnWrite();
                ((FieldTransform) this.instance).setRemoveAllFromArray(builder);
                return this;
            }

            public Builder mergeRemoveAllFromArray(ArrayValue arrayValue) {
                copyOnWrite();
                ((FieldTransform) this.instance).mergeRemoveAllFromArray(arrayValue);
                return this;
            }

            public Builder clearRemoveAllFromArray() {
                copyOnWrite();
                ((FieldTransform) this.instance).clearRemoveAllFromArray();
                return this;
            }
        }

        private FieldTransform() {
        }

        public TransformTypeCase getTransformTypeCase() {
            return TransformTypeCase.forNumber(this.transformTypeCase_);
        }

        private void clearTransformType() {
            this.transformTypeCase_ = 0;
            this.transformType_ = null;
        }

        public String getFieldPath() {
            return this.fieldPath_;
        }

        public ByteString getFieldPathBytes() {
            return ByteString.copyFromUtf8(this.fieldPath_);
        }

        private void setFieldPath(String str) {
            if (str != null) {
                this.fieldPath_ = str;
                return;
            }
            throw new NullPointerException();
        }

        private void clearFieldPath() {
            this.fieldPath_ = getDefaultInstance().getFieldPath();
        }

        private void setFieldPathBytes(ByteString byteString) {
            if (byteString != null) {
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.fieldPath_ = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public int getSetToServerValueValue() {
            return this.transformTypeCase_ == 2 ? ((Integer) this.transformType_).intValue() : 0;
        }

        public ServerValue getSetToServerValue() {
            if (this.transformTypeCase_ != 2) {
                return ServerValue.SERVER_VALUE_UNSPECIFIED;
            }
            ServerValue forNumber = ServerValue.forNumber(((Integer) this.transformType_).intValue());
            if (forNumber == null) {
                forNumber = ServerValue.UNRECOGNIZED;
            }
            return forNumber;
        }

        private void setSetToServerValueValue(int i) {
            this.transformTypeCase_ = 2;
            this.transformType_ = Integer.valueOf(i);
        }

        private void setSetToServerValue(ServerValue serverValue) {
            if (serverValue != null) {
                this.transformTypeCase_ = 2;
                this.transformType_ = Integer.valueOf(serverValue.getNumber());
                return;
            }
            throw new NullPointerException();
        }

        private void clearSetToServerValue() {
            if (this.transformTypeCase_ == 2) {
                this.transformTypeCase_ = 0;
                this.transformType_ = null;
            }
        }

        public Value getIncrement() {
            if (this.transformTypeCase_ == 3) {
                return (Value) this.transformType_;
            }
            return Value.getDefaultInstance();
        }

        private void setIncrement(Value value) {
            if (value != null) {
                this.transformType_ = value;
                this.transformTypeCase_ = 3;
                return;
            }
            throw new NullPointerException();
        }

        private void setIncrement(com.google.firestore.v1.Value.Builder builder) {
            this.transformType_ = builder.build();
            this.transformTypeCase_ = 3;
        }

        private void mergeIncrement(Value value) {
            if (this.transformTypeCase_ != 3 || this.transformType_ == Value.getDefaultInstance()) {
                this.transformType_ = value;
            } else {
                this.transformType_ = ((com.google.firestore.v1.Value.Builder) Value.newBuilder((Value) this.transformType_).mergeFrom(value)).buildPartial();
            }
            this.transformTypeCase_ = 3;
        }

        private void clearIncrement() {
            if (this.transformTypeCase_ == 3) {
                this.transformTypeCase_ = 0;
                this.transformType_ = null;
            }
        }

        public Value getMaximum() {
            if (this.transformTypeCase_ == 4) {
                return (Value) this.transformType_;
            }
            return Value.getDefaultInstance();
        }

        private void setMaximum(Value value) {
            if (value != null) {
                this.transformType_ = value;
                this.transformTypeCase_ = 4;
                return;
            }
            throw new NullPointerException();
        }

        private void setMaximum(com.google.firestore.v1.Value.Builder builder) {
            this.transformType_ = builder.build();
            this.transformTypeCase_ = 4;
        }

        private void mergeMaximum(Value value) {
            if (this.transformTypeCase_ != 4 || this.transformType_ == Value.getDefaultInstance()) {
                this.transformType_ = value;
            } else {
                this.transformType_ = ((com.google.firestore.v1.Value.Builder) Value.newBuilder((Value) this.transformType_).mergeFrom(value)).buildPartial();
            }
            this.transformTypeCase_ = 4;
        }

        private void clearMaximum() {
            if (this.transformTypeCase_ == 4) {
                this.transformTypeCase_ = 0;
                this.transformType_ = null;
            }
        }

        public Value getMinimum() {
            if (this.transformTypeCase_ == 5) {
                return (Value) this.transformType_;
            }
            return Value.getDefaultInstance();
        }

        private void setMinimum(Value value) {
            if (value != null) {
                this.transformType_ = value;
                this.transformTypeCase_ = 5;
                return;
            }
            throw new NullPointerException();
        }

        private void setMinimum(com.google.firestore.v1.Value.Builder builder) {
            this.transformType_ = builder.build();
            this.transformTypeCase_ = 5;
        }

        private void mergeMinimum(Value value) {
            if (this.transformTypeCase_ != 5 || this.transformType_ == Value.getDefaultInstance()) {
                this.transformType_ = value;
            } else {
                this.transformType_ = ((com.google.firestore.v1.Value.Builder) Value.newBuilder((Value) this.transformType_).mergeFrom(value)).buildPartial();
            }
            this.transformTypeCase_ = 5;
        }

        private void clearMinimum() {
            if (this.transformTypeCase_ == 5) {
                this.transformTypeCase_ = 0;
                this.transformType_ = null;
            }
        }

        public ArrayValue getAppendMissingElements() {
            if (this.transformTypeCase_ == 6) {
                return (ArrayValue) this.transformType_;
            }
            return ArrayValue.getDefaultInstance();
        }

        private void setAppendMissingElements(ArrayValue arrayValue) {
            if (arrayValue != null) {
                this.transformType_ = arrayValue;
                this.transformTypeCase_ = 6;
                return;
            }
            throw new NullPointerException();
        }

        private void setAppendMissingElements(com.google.firestore.v1.ArrayValue.Builder builder) {
            this.transformType_ = builder.build();
            this.transformTypeCase_ = 6;
        }

        private void mergeAppendMissingElements(ArrayValue arrayValue) {
            if (this.transformTypeCase_ != 6 || this.transformType_ == ArrayValue.getDefaultInstance()) {
                this.transformType_ = arrayValue;
            } else {
                this.transformType_ = ((com.google.firestore.v1.ArrayValue.Builder) ArrayValue.newBuilder((ArrayValue) this.transformType_).mergeFrom(arrayValue)).buildPartial();
            }
            this.transformTypeCase_ = 6;
        }

        private void clearAppendMissingElements() {
            if (this.transformTypeCase_ == 6) {
                this.transformTypeCase_ = 0;
                this.transformType_ = null;
            }
        }

        public ArrayValue getRemoveAllFromArray() {
            if (this.transformTypeCase_ == 7) {
                return (ArrayValue) this.transformType_;
            }
            return ArrayValue.getDefaultInstance();
        }

        private void setRemoveAllFromArray(ArrayValue arrayValue) {
            if (arrayValue != null) {
                this.transformType_ = arrayValue;
                this.transformTypeCase_ = 7;
                return;
            }
            throw new NullPointerException();
        }

        private void setRemoveAllFromArray(com.google.firestore.v1.ArrayValue.Builder builder) {
            this.transformType_ = builder.build();
            this.transformTypeCase_ = 7;
        }

        private void mergeRemoveAllFromArray(ArrayValue arrayValue) {
            if (this.transformTypeCase_ != 7 || this.transformType_ == ArrayValue.getDefaultInstance()) {
                this.transformType_ = arrayValue;
            } else {
                this.transformType_ = ((com.google.firestore.v1.ArrayValue.Builder) ArrayValue.newBuilder((ArrayValue) this.transformType_).mergeFrom(arrayValue)).buildPartial();
            }
            this.transformTypeCase_ = 7;
        }

        private void clearRemoveAllFromArray() {
            if (this.transformTypeCase_ == 7) {
                this.transformTypeCase_ = 0;
                this.transformType_ = null;
            }
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!this.fieldPath_.isEmpty()) {
                codedOutputStream.writeString(1, getFieldPath());
            }
            if (this.transformTypeCase_ == 2) {
                codedOutputStream.writeEnum(2, ((Integer) this.transformType_).intValue());
            }
            if (this.transformTypeCase_ == 3) {
                codedOutputStream.writeMessage(3, (Value) this.transformType_);
            }
            if (this.transformTypeCase_ == 4) {
                codedOutputStream.writeMessage(4, (Value) this.transformType_);
            }
            if (this.transformTypeCase_ == 5) {
                codedOutputStream.writeMessage(5, (Value) this.transformType_);
            }
            if (this.transformTypeCase_ == 6) {
                codedOutputStream.writeMessage(6, (ArrayValue) this.transformType_);
            }
            if (this.transformTypeCase_ == 7) {
                codedOutputStream.writeMessage(7, (ArrayValue) this.transformType_);
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            i = 0;
            if (!this.fieldPath_.isEmpty()) {
                i = 0 + CodedOutputStream.computeStringSize(1, getFieldPath());
            }
            if (this.transformTypeCase_ == 2) {
                i += CodedOutputStream.computeEnumSize(2, ((Integer) this.transformType_).intValue());
            }
            if (this.transformTypeCase_ == 3) {
                i += CodedOutputStream.computeMessageSize(3, (Value) this.transformType_);
            }
            if (this.transformTypeCase_ == 4) {
                i += CodedOutputStream.computeMessageSize(4, (Value) this.transformType_);
            }
            if (this.transformTypeCase_ == 5) {
                i += CodedOutputStream.computeMessageSize(5, (Value) this.transformType_);
            }
            if (this.transformTypeCase_ == 6) {
                i += CodedOutputStream.computeMessageSize(6, (ArrayValue) this.transformType_);
            }
            if (this.transformTypeCase_ == 7) {
                i += CodedOutputStream.computeMessageSize(7, (ArrayValue) this.transformType_);
            }
            this.memoizedSerializedSize = i;
            return i;
        }

        public static FieldTransform parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (FieldTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static FieldTransform parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (FieldTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static FieldTransform parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (FieldTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static FieldTransform parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (FieldTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static FieldTransform parseFrom(InputStream inputStream) throws IOException {
            return (FieldTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static FieldTransform parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (FieldTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static FieldTransform parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (FieldTransform) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static FieldTransform parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (FieldTransform) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static FieldTransform parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (FieldTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static FieldTransform parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (FieldTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FieldTransform fieldTransform) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(fieldTransform);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            boolean z = false;
            int i;
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new FieldTransform();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    Visitor visitor = (Visitor) obj;
                    FieldTransform fieldTransform = (FieldTransform) obj2;
                    this.fieldPath_ = visitor.visitString(this.fieldPath_.isEmpty() ^ true, this.fieldPath_, fieldTransform.fieldPath_.isEmpty() ^ true, fieldTransform.fieldPath_);
                    switch (fieldTransform.getTransformTypeCase()) {
                        case SET_TO_SERVER_VALUE:
                            if (this.transformTypeCase_ == 2) {
                                z = true;
                            }
                            this.transformType_ = visitor.visitOneofInt(z, this.transformType_, fieldTransform.transformType_);
                            break;
                        case INCREMENT:
                            if (this.transformTypeCase_ == 3) {
                                z = true;
                            }
                            this.transformType_ = visitor.visitOneofMessage(z, this.transformType_, fieldTransform.transformType_);
                            break;
                        case MAXIMUM:
                            if (this.transformTypeCase_ == 4) {
                                z = true;
                            }
                            this.transformType_ = visitor.visitOneofMessage(z, this.transformType_, fieldTransform.transformType_);
                            break;
                        case MINIMUM:
                            if (this.transformTypeCase_ == 5) {
                                z = true;
                            }
                            this.transformType_ = visitor.visitOneofMessage(z, this.transformType_, fieldTransform.transformType_);
                            break;
                        case APPEND_MISSING_ELEMENTS:
                            if (this.transformTypeCase_ == 6) {
                                z = true;
                            }
                            this.transformType_ = visitor.visitOneofMessage(z, this.transformType_, fieldTransform.transformType_);
                            break;
                        case REMOVE_ALL_FROM_ARRAY:
                            if (this.transformTypeCase_ == 7) {
                                z = true;
                            }
                            this.transformType_ = visitor.visitOneofMessage(z, this.transformType_, fieldTransform.transformType_);
                            break;
                        case TRANSFORMTYPE_NOT_SET:
                            if (this.transformTypeCase_ != 0) {
                                z = true;
                            }
                            visitor.visitOneofNotSet(z);
                            break;
                    }
                    if (visitor == MergeFromVisitor.INSTANCE) {
                        i = fieldTransform.transformTypeCase_;
                        if (i != 0) {
                            this.transformTypeCase_ = i;
                        }
                    }
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    while (!z) {
                        try {
                            i = codedInputStream.readTag();
                            if (i != 0) {
                                com.google.firestore.v1.Value.Builder builder;
                                com.google.firestore.v1.ArrayValue.Builder builder2;
                                if (i == 10) {
                                    this.fieldPath_ = codedInputStream.readStringRequireUtf8();
                                } else if (i == 16) {
                                    i = codedInputStream.readEnum();
                                    this.transformTypeCase_ = 2;
                                    this.transformType_ = Integer.valueOf(i);
                                } else if (i == 26) {
                                    builder = this.transformTypeCase_ == 3 ? (com.google.firestore.v1.Value.Builder) ((Value) this.transformType_).toBuilder() : null;
                                    this.transformType_ = codedInputStream.readMessage(Value.parser(), extensionRegistryLite);
                                    if (builder != null) {
                                        builder.mergeFrom((Value) this.transformType_);
                                        this.transformType_ = builder.buildPartial();
                                    }
                                    this.transformTypeCase_ = 3;
                                } else if (i == 34) {
                                    builder = this.transformTypeCase_ == 4 ? (com.google.firestore.v1.Value.Builder) ((Value) this.transformType_).toBuilder() : null;
                                    this.transformType_ = codedInputStream.readMessage(Value.parser(), extensionRegistryLite);
                                    if (builder != null) {
                                        builder.mergeFrom((Value) this.transformType_);
                                        this.transformType_ = builder.buildPartial();
                                    }
                                    this.transformTypeCase_ = 4;
                                } else if (i == 42) {
                                    builder = this.transformTypeCase_ == 5 ? (com.google.firestore.v1.Value.Builder) ((Value) this.transformType_).toBuilder() : null;
                                    this.transformType_ = codedInputStream.readMessage(Value.parser(), extensionRegistryLite);
                                    if (builder != null) {
                                        builder.mergeFrom((Value) this.transformType_);
                                        this.transformType_ = builder.buildPartial();
                                    }
                                    this.transformTypeCase_ = 5;
                                } else if (i == 50) {
                                    builder2 = this.transformTypeCase_ == 6 ? (com.google.firestore.v1.ArrayValue.Builder) ((ArrayValue) this.transformType_).toBuilder() : null;
                                    this.transformType_ = codedInputStream.readMessage(ArrayValue.parser(), extensionRegistryLite);
                                    if (builder2 != null) {
                                        builder2.mergeFrom((ArrayValue) this.transformType_);
                                        this.transformType_ = builder2.buildPartial();
                                    }
                                    this.transformTypeCase_ = 6;
                                } else if (i == 58) {
                                    builder2 = this.transformTypeCase_ == 7 ? (com.google.firestore.v1.ArrayValue.Builder) ((ArrayValue) this.transformType_).toBuilder() : null;
                                    this.transformType_ = codedInputStream.readMessage(ArrayValue.parser(), extensionRegistryLite);
                                    if (builder2 != null) {
                                        builder2.mergeFrom((ArrayValue) this.transformType_);
                                        this.transformType_ = builder2.buildPartial();
                                    }
                                    this.transformTypeCase_ = 7;
                                } else if (codedInputStream.skipField(i)) {
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case GET_DEFAULT_INSTANCE:
                    break;
                case GET_PARSER:
                    if (PARSER == null) {
                        synchronized (FieldTransform.class) {
                            if (PARSER == null) {
                                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        public static FieldTransform getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FieldTransform> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    private DocumentTransform() {
    }

    public String getDocument() {
        return this.document_;
    }

    public ByteString getDocumentBytes() {
        return ByteString.copyFromUtf8(this.document_);
    }

    private void setDocument(String str) {
        if (str != null) {
            this.document_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearDocument() {
        this.document_ = getDefaultInstance().getDocument();
    }

    private void setDocumentBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.document_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public List<FieldTransform> getFieldTransformsList() {
        return this.fieldTransforms_;
    }

    public List<? extends FieldTransformOrBuilder> getFieldTransformsOrBuilderList() {
        return this.fieldTransforms_;
    }

    public int getFieldTransformsCount() {
        return this.fieldTransforms_.size();
    }

    public FieldTransform getFieldTransforms(int i) {
        return (FieldTransform) this.fieldTransforms_.get(i);
    }

    public FieldTransformOrBuilder getFieldTransformsOrBuilder(int i) {
        return (FieldTransformOrBuilder) this.fieldTransforms_.get(i);
    }

    private void ensureFieldTransformsIsMutable() {
        if (!this.fieldTransforms_.isModifiable()) {
            this.fieldTransforms_ = GeneratedMessageLite.mutableCopy(this.fieldTransforms_);
        }
    }

    private void setFieldTransforms(int i, FieldTransform fieldTransform) {
        if (fieldTransform != null) {
            ensureFieldTransformsIsMutable();
            this.fieldTransforms_.set(i, fieldTransform);
            return;
        }
        throw new NullPointerException();
    }

    private void setFieldTransforms(int i, Builder builder) {
        ensureFieldTransformsIsMutable();
        this.fieldTransforms_.set(i, (FieldTransform) builder.build());
    }

    private void addFieldTransforms(FieldTransform fieldTransform) {
        if (fieldTransform != null) {
            ensureFieldTransformsIsMutable();
            this.fieldTransforms_.add(fieldTransform);
            return;
        }
        throw new NullPointerException();
    }

    private void addFieldTransforms(int i, FieldTransform fieldTransform) {
        if (fieldTransform != null) {
            ensureFieldTransformsIsMutable();
            this.fieldTransforms_.add(i, fieldTransform);
            return;
        }
        throw new NullPointerException();
    }

    private void addFieldTransforms(Builder builder) {
        ensureFieldTransformsIsMutable();
        this.fieldTransforms_.add((FieldTransform) builder.build());
    }

    private void addFieldTransforms(int i, Builder builder) {
        ensureFieldTransformsIsMutable();
        this.fieldTransforms_.add(i, (FieldTransform) builder.build());
    }

    private void addAllFieldTransforms(Iterable<? extends FieldTransform> iterable) {
        ensureFieldTransformsIsMutable();
        AbstractMessageLite.addAll(iterable, this.fieldTransforms_);
    }

    private void clearFieldTransforms() {
        this.fieldTransforms_ = GeneratedMessageLite.emptyProtobufList();
    }

    private void removeFieldTransforms(int i) {
        ensureFieldTransformsIsMutable();
        this.fieldTransforms_.remove(i);
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (!this.document_.isEmpty()) {
            codedOutputStream.writeString(1, getDocument());
        }
        for (int i = 0; i < this.fieldTransforms_.size(); i++) {
            codedOutputStream.writeMessage(2, (MessageLite) this.fieldTransforms_.get(i));
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        i = !this.document_.isEmpty() ? CodedOutputStream.computeStringSize(1, getDocument()) + 0 : 0;
        while (i2 < this.fieldTransforms_.size()) {
            i += CodedOutputStream.computeMessageSize(2, (MessageLite) this.fieldTransforms_.get(i2));
            i2++;
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static DocumentTransform parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (DocumentTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static DocumentTransform parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (DocumentTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static DocumentTransform parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (DocumentTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static DocumentTransform parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (DocumentTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static DocumentTransform parseFrom(InputStream inputStream) throws IOException {
        return (DocumentTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static DocumentTransform parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (DocumentTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static DocumentTransform parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (DocumentTransform) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static DocumentTransform parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (DocumentTransform) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static DocumentTransform parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (DocumentTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static DocumentTransform parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (DocumentTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(DocumentTransform documentTransform) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(documentTransform);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new DocumentTransform();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.fieldTransforms_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                DocumentTransform documentTransform = (DocumentTransform) obj2;
                this.document_ = visitor.visitString(this.document_.isEmpty() ^ true, this.document_, true ^ documentTransform.document_.isEmpty(), documentTransform.document_);
                this.fieldTransforms_ = visitor.visitList(this.fieldTransforms_, documentTransform.fieldTransforms_);
                if (visitor == MergeFromVisitor.INSTANCE) {
                    this.bitField0_ |= documentTransform.bitField0_;
                }
                return this;
            case MERGE_FROM_STREAM:
                CodedInputStream codedInputStream = (CodedInputStream) obj;
                ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                Object obj3 = null;
                while (obj3 == null) {
                    try {
                        int readTag = codedInputStream.readTag();
                        if (readTag != 0) {
                            if (readTag == 10) {
                                this.document_ = codedInputStream.readStringRequireUtf8();
                            } else if (readTag == 18) {
                                if (!this.fieldTransforms_.isModifiable()) {
                                    this.fieldTransforms_ = GeneratedMessageLite.mutableCopy(this.fieldTransforms_);
                                }
                                this.fieldTransforms_.add((FieldTransform) codedInputStream.readMessage(FieldTransform.parser(), extensionRegistryLite));
                            } else if (codedInputStream.skipField(readTag)) {
                            }
                        }
                        obj3 = 1;
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e.setUnfinishedMessage(this));
                    } catch (IOException e2) {
                        throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                    }
                }
                break;
            case GET_DEFAULT_INSTANCE:
                break;
            case GET_PARSER:
                if (PARSER == null) {
                    synchronized (DocumentTransform.class) {
                        if (PARSER == null) {
                            PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                        }
                    }
                }
                return PARSER;
            default:
                throw new UnsupportedOperationException();
        }
        return DEFAULT_INSTANCE;
    }

    static {
        DEFAULT_INSTANCE.makeImmutable();
    }

    public static DocumentTransform getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<DocumentTransform> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
