package com.google.firestore.v1;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Write extends GeneratedMessageLite<Write, Builder> implements WriteOrBuilder {
    public static final int CURRENT_DOCUMENT_FIELD_NUMBER = 4;
    private static final Write DEFAULT_INSTANCE = new Write();
    public static final int DELETE_FIELD_NUMBER = 2;
    private static volatile Parser<Write> PARSER = null;
    public static final int TRANSFORM_FIELD_NUMBER = 6;
    public static final int UPDATE_FIELD_NUMBER = 1;
    public static final int UPDATE_MASK_FIELD_NUMBER = 3;
    private Precondition currentDocument_;
    private int operationCase_ = 0;
    private Object operation_;
    private DocumentMask updateMask_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firestore.v1.Write$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$Write$OperationCase = new int[OperationCase.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:20:0x0062, code:
            $SwitchMap$com$google$firestore$v1$Write$OperationCase = new int[com.google.firestore.v1.Write.OperationCase.values().length];
     */
        /* JADX WARNING: Missing block: B:22:?, code:
            $SwitchMap$com$google$firestore$v1$Write$OperationCase[com.google.firestore.v1.Write.OperationCase.UPDATE.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:24:?, code:
            $SwitchMap$com$google$firestore$v1$Write$OperationCase[com.google.firestore.v1.Write.OperationCase.DELETE.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:26:?, code:
            $SwitchMap$com$google$firestore$v1$Write$OperationCase[com.google.firestore.v1.Write.OperationCase.TRANSFORM.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:28:?, code:
            $SwitchMap$com$google$firestore$v1$Write$OperationCase[com.google.firestore.v1.Write.OperationCase.OPERATION_NOT_SET.ordinal()] = 4;
     */
        /* JADX WARNING: Missing block: B:29:0x0093, code:
            return;
     */
        static {
            /*
            r0 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = r0;
            r0 = 1;
            r1 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = 2;
            r2 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.IS_INITIALIZED;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r2 = 3;
            r3 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.MAKE_IMMUTABLE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r3[r4] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r3 = 4;
            r4 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r5 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_BUILDER;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r4[r5] = r3;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r4 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r5 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.VISIT;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r6 = 5;
            r4[r5] = r6;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            r4 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x004b }
            r5 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.MERGE_FROM_STREAM;	 Catch:{ NoSuchFieldError -> 0x004b }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x004b }
            r6 = 6;
            r4[r5] = r6;	 Catch:{ NoSuchFieldError -> 0x004b }
        L_0x004b:
            r4 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0056 }
            r5 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE;	 Catch:{ NoSuchFieldError -> 0x0056 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0056 }
            r6 = 7;
            r4[r5] = r6;	 Catch:{ NoSuchFieldError -> 0x0056 }
        L_0x0056:
            r4 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0062 }
            r5 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_PARSER;	 Catch:{ NoSuchFieldError -> 0x0062 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0062 }
            r6 = 8;
            r4[r5] = r6;	 Catch:{ NoSuchFieldError -> 0x0062 }
        L_0x0062:
            r4 = com.google.firestore.v1.Write.OperationCase.values();
            r4 = r4.length;
            r4 = new int[r4];
            $SwitchMap$com$google$firestore$v1$Write$OperationCase = r4;
            r4 = $SwitchMap$com$google$firestore$v1$Write$OperationCase;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r5 = com.google.firestore.v1.Write.OperationCase.UPDATE;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0075 }
            r4[r5] = r0;	 Catch:{ NoSuchFieldError -> 0x0075 }
        L_0x0075:
            r0 = $SwitchMap$com$google$firestore$v1$Write$OperationCase;	 Catch:{ NoSuchFieldError -> 0x007f }
            r4 = com.google.firestore.v1.Write.OperationCase.DELETE;	 Catch:{ NoSuchFieldError -> 0x007f }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x007f }
            r0[r4] = r1;	 Catch:{ NoSuchFieldError -> 0x007f }
        L_0x007f:
            r0 = $SwitchMap$com$google$firestore$v1$Write$OperationCase;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r1 = com.google.firestore.v1.Write.OperationCase.TRANSFORM;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0089 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0089 }
        L_0x0089:
            r0 = $SwitchMap$com$google$firestore$v1$Write$OperationCase;	 Catch:{ NoSuchFieldError -> 0x0093 }
            r1 = com.google.firestore.v1.Write.OperationCase.OPERATION_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x0093 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0093 }
            r0[r1] = r3;	 Catch:{ NoSuchFieldError -> 0x0093 }
        L_0x0093:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firestore.v1.Write.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum OperationCase implements EnumLite {
        UPDATE(1),
        DELETE(2),
        TRANSFORM(6),
        OPERATION_NOT_SET(0);
        
        private final int value;

        private OperationCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static OperationCase valueOf(int i) {
            return forNumber(i);
        }

        public static OperationCase forNumber(int i) {
            if (i == 0) {
                return OPERATION_NOT_SET;
            }
            if (i == 1) {
                return UPDATE;
            }
            if (i != 2) {
                return i != 6 ? null : TRANSFORM;
            } else {
                return DELETE;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<Write, Builder> implements WriteOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(Write.DEFAULT_INSTANCE);
        }

        public OperationCase getOperationCase() {
            return ((Write) this.instance).getOperationCase();
        }

        public Builder clearOperation() {
            copyOnWrite();
            ((Write) this.instance).clearOperation();
            return this;
        }

        public Document getUpdate() {
            return ((Write) this.instance).getUpdate();
        }

        public Builder setUpdate(Document document) {
            copyOnWrite();
            ((Write) this.instance).setUpdate(document);
            return this;
        }

        public Builder setUpdate(com.google.firestore.v1.Document.Builder builder) {
            copyOnWrite();
            ((Write) this.instance).setUpdate(builder);
            return this;
        }

        public Builder mergeUpdate(Document document) {
            copyOnWrite();
            ((Write) this.instance).mergeUpdate(document);
            return this;
        }

        public Builder clearUpdate() {
            copyOnWrite();
            ((Write) this.instance).clearUpdate();
            return this;
        }

        public String getDelete() {
            return ((Write) this.instance).getDelete();
        }

        public ByteString getDeleteBytes() {
            return ((Write) this.instance).getDeleteBytes();
        }

        public Builder setDelete(String str) {
            copyOnWrite();
            ((Write) this.instance).setDelete(str);
            return this;
        }

        public Builder clearDelete() {
            copyOnWrite();
            ((Write) this.instance).clearDelete();
            return this;
        }

        public Builder setDeleteBytes(ByteString byteString) {
            copyOnWrite();
            ((Write) this.instance).setDeleteBytes(byteString);
            return this;
        }

        public DocumentTransform getTransform() {
            return ((Write) this.instance).getTransform();
        }

        public Builder setTransform(DocumentTransform documentTransform) {
            copyOnWrite();
            ((Write) this.instance).setTransform(documentTransform);
            return this;
        }

        public Builder setTransform(com.google.firestore.v1.DocumentTransform.Builder builder) {
            copyOnWrite();
            ((Write) this.instance).setTransform(builder);
            return this;
        }

        public Builder mergeTransform(DocumentTransform documentTransform) {
            copyOnWrite();
            ((Write) this.instance).mergeTransform(documentTransform);
            return this;
        }

        public Builder clearTransform() {
            copyOnWrite();
            ((Write) this.instance).clearTransform();
            return this;
        }

        public boolean hasUpdateMask() {
            return ((Write) this.instance).hasUpdateMask();
        }

        public DocumentMask getUpdateMask() {
            return ((Write) this.instance).getUpdateMask();
        }

        public Builder setUpdateMask(DocumentMask documentMask) {
            copyOnWrite();
            ((Write) this.instance).setUpdateMask(documentMask);
            return this;
        }

        public Builder setUpdateMask(com.google.firestore.v1.DocumentMask.Builder builder) {
            copyOnWrite();
            ((Write) this.instance).setUpdateMask(builder);
            return this;
        }

        public Builder mergeUpdateMask(DocumentMask documentMask) {
            copyOnWrite();
            ((Write) this.instance).mergeUpdateMask(documentMask);
            return this;
        }

        public Builder clearUpdateMask() {
            copyOnWrite();
            ((Write) this.instance).clearUpdateMask();
            return this;
        }

        public boolean hasCurrentDocument() {
            return ((Write) this.instance).hasCurrentDocument();
        }

        public Precondition getCurrentDocument() {
            return ((Write) this.instance).getCurrentDocument();
        }

        public Builder setCurrentDocument(Precondition precondition) {
            copyOnWrite();
            ((Write) this.instance).setCurrentDocument(precondition);
            return this;
        }

        public Builder setCurrentDocument(com.google.firestore.v1.Precondition.Builder builder) {
            copyOnWrite();
            ((Write) this.instance).setCurrentDocument(builder);
            return this;
        }

        public Builder mergeCurrentDocument(Precondition precondition) {
            copyOnWrite();
            ((Write) this.instance).mergeCurrentDocument(precondition);
            return this;
        }

        public Builder clearCurrentDocument() {
            copyOnWrite();
            ((Write) this.instance).clearCurrentDocument();
            return this;
        }
    }

    private Write() {
    }

    public OperationCase getOperationCase() {
        return OperationCase.forNumber(this.operationCase_);
    }

    private void clearOperation() {
        this.operationCase_ = 0;
        this.operation_ = null;
    }

    public Document getUpdate() {
        if (this.operationCase_ == 1) {
            return (Document) this.operation_;
        }
        return Document.getDefaultInstance();
    }

    private void setUpdate(Document document) {
        if (document != null) {
            this.operation_ = document;
            this.operationCase_ = 1;
            return;
        }
        throw new NullPointerException();
    }

    private void setUpdate(com.google.firestore.v1.Document.Builder builder) {
        this.operation_ = builder.build();
        this.operationCase_ = 1;
    }

    private void mergeUpdate(Document document) {
        if (this.operationCase_ != 1 || this.operation_ == Document.getDefaultInstance()) {
            this.operation_ = document;
        } else {
            this.operation_ = ((com.google.firestore.v1.Document.Builder) Document.newBuilder((Document) this.operation_).mergeFrom(document)).buildPartial();
        }
        this.operationCase_ = 1;
    }

    private void clearUpdate() {
        if (this.operationCase_ == 1) {
            this.operationCase_ = 0;
            this.operation_ = null;
        }
    }

    public String getDelete() {
        return this.operationCase_ == 2 ? (String) this.operation_ : "";
    }

    public ByteString getDeleteBytes() {
        return ByteString.copyFromUtf8(this.operationCase_ == 2 ? (String) this.operation_ : "");
    }

    private void setDelete(String str) {
        if (str != null) {
            this.operationCase_ = 2;
            this.operation_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearDelete() {
        if (this.operationCase_ == 2) {
            this.operationCase_ = 0;
            this.operation_ = null;
        }
    }

    private void setDeleteBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.operationCase_ = 2;
            this.operation_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public DocumentTransform getTransform() {
        if (this.operationCase_ == 6) {
            return (DocumentTransform) this.operation_;
        }
        return DocumentTransform.getDefaultInstance();
    }

    private void setTransform(DocumentTransform documentTransform) {
        if (documentTransform != null) {
            this.operation_ = documentTransform;
            this.operationCase_ = 6;
            return;
        }
        throw new NullPointerException();
    }

    private void setTransform(com.google.firestore.v1.DocumentTransform.Builder builder) {
        this.operation_ = builder.build();
        this.operationCase_ = 6;
    }

    private void mergeTransform(DocumentTransform documentTransform) {
        if (this.operationCase_ != 6 || this.operation_ == DocumentTransform.getDefaultInstance()) {
            this.operation_ = documentTransform;
        } else {
            this.operation_ = ((com.google.firestore.v1.DocumentTransform.Builder) DocumentTransform.newBuilder((DocumentTransform) this.operation_).mergeFrom(documentTransform)).buildPartial();
        }
        this.operationCase_ = 6;
    }

    private void clearTransform() {
        if (this.operationCase_ == 6) {
            this.operationCase_ = 0;
            this.operation_ = null;
        }
    }

    public boolean hasUpdateMask() {
        return this.updateMask_ != null;
    }

    public DocumentMask getUpdateMask() {
        DocumentMask documentMask = this.updateMask_;
        return documentMask == null ? DocumentMask.getDefaultInstance() : documentMask;
    }

    private void setUpdateMask(DocumentMask documentMask) {
        if (documentMask != null) {
            this.updateMask_ = documentMask;
            return;
        }
        throw new NullPointerException();
    }

    private void setUpdateMask(com.google.firestore.v1.DocumentMask.Builder builder) {
        this.updateMask_ = (DocumentMask) builder.build();
    }

    private void mergeUpdateMask(DocumentMask documentMask) {
        DocumentMask documentMask2 = this.updateMask_;
        if (documentMask2 == null || documentMask2 == DocumentMask.getDefaultInstance()) {
            this.updateMask_ = documentMask;
        } else {
            this.updateMask_ = (DocumentMask) ((com.google.firestore.v1.DocumentMask.Builder) DocumentMask.newBuilder(this.updateMask_).mergeFrom(documentMask)).buildPartial();
        }
    }

    private void clearUpdateMask() {
        this.updateMask_ = null;
    }

    public boolean hasCurrentDocument() {
        return this.currentDocument_ != null;
    }

    public Precondition getCurrentDocument() {
        Precondition precondition = this.currentDocument_;
        return precondition == null ? Precondition.getDefaultInstance() : precondition;
    }

    private void setCurrentDocument(Precondition precondition) {
        if (precondition != null) {
            this.currentDocument_ = precondition;
            return;
        }
        throw new NullPointerException();
    }

    private void setCurrentDocument(com.google.firestore.v1.Precondition.Builder builder) {
        this.currentDocument_ = (Precondition) builder.build();
    }

    private void mergeCurrentDocument(Precondition precondition) {
        Precondition precondition2 = this.currentDocument_;
        if (precondition2 == null || precondition2 == Precondition.getDefaultInstance()) {
            this.currentDocument_ = precondition;
        } else {
            this.currentDocument_ = (Precondition) ((com.google.firestore.v1.Precondition.Builder) Precondition.newBuilder(this.currentDocument_).mergeFrom(precondition)).buildPartial();
        }
    }

    private void clearCurrentDocument() {
        this.currentDocument_ = null;
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (this.operationCase_ == 1) {
            codedOutputStream.writeMessage(1, (Document) this.operation_);
        }
        if (this.operationCase_ == 2) {
            codedOutputStream.writeString(2, getDelete());
        }
        if (this.updateMask_ != null) {
            codedOutputStream.writeMessage(3, getUpdateMask());
        }
        if (this.currentDocument_ != null) {
            codedOutputStream.writeMessage(4, getCurrentDocument());
        }
        if (this.operationCase_ == 6) {
            codedOutputStream.writeMessage(6, (DocumentTransform) this.operation_);
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (this.operationCase_ == 1) {
            i = 0 + CodedOutputStream.computeMessageSize(1, (Document) this.operation_);
        }
        if (this.operationCase_ == 2) {
            i += CodedOutputStream.computeStringSize(2, getDelete());
        }
        if (this.updateMask_ != null) {
            i += CodedOutputStream.computeMessageSize(3, getUpdateMask());
        }
        if (this.currentDocument_ != null) {
            i += CodedOutputStream.computeMessageSize(4, getCurrentDocument());
        }
        if (this.operationCase_ == 6) {
            i += CodedOutputStream.computeMessageSize(6, (DocumentTransform) this.operation_);
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static Write parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Write) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static Write parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Write) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Write parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Write) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static Write parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Write) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Write parseFrom(InputStream inputStream) throws IOException {
        return (Write) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Write parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Write) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Write parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Write) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Write parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Write) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Write parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Write) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Write parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Write) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Write write) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(write);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        int i;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Write();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                Write write = (Write) obj2;
                this.updateMask_ = (DocumentMask) visitor.visitMessage(this.updateMask_, write.updateMask_);
                this.currentDocument_ = (Precondition) visitor.visitMessage(this.currentDocument_, write.currentDocument_);
                i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$Write$OperationCase[write.getOperationCase().ordinal()];
                if (i == 1) {
                    if (this.operationCase_ == 1) {
                        z = true;
                    }
                    this.operation_ = visitor.visitOneofMessage(z, this.operation_, write.operation_);
                } else if (i == 2) {
                    if (this.operationCase_ == 2) {
                        z = true;
                    }
                    this.operation_ = visitor.visitOneofString(z, this.operation_, write.operation_);
                } else if (i == 3) {
                    if (this.operationCase_ == 6) {
                        z = true;
                    }
                    this.operation_ = visitor.visitOneofMessage(z, this.operation_, write.operation_);
                } else if (i == 4) {
                    if (this.operationCase_ != 0) {
                        z = true;
                    }
                    visitor.visitOneofNotSet(z);
                }
                if (visitor == MergeFromVisitor.INSTANCE) {
                    i = write.operationCase_;
                    if (i != 0) {
                        this.operationCase_ = i;
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
                            if (i == 10) {
                                com.google.firestore.v1.Document.Builder builder = this.operationCase_ == 1 ? (com.google.firestore.v1.Document.Builder) ((Document) this.operation_).toBuilder() : null;
                                this.operation_ = codedInputStream.readMessage(Document.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom((Document) this.operation_);
                                    this.operation_ = builder.buildPartial();
                                }
                                this.operationCase_ = 1;
                            } else if (i == 18) {
                                String readStringRequireUtf8 = codedInputStream.readStringRequireUtf8();
                                this.operationCase_ = 2;
                                this.operation_ = readStringRequireUtf8;
                            } else if (i == 26) {
                                com.google.firestore.v1.DocumentMask.Builder builder2 = this.updateMask_ != null ? (com.google.firestore.v1.DocumentMask.Builder) this.updateMask_.toBuilder() : null;
                                this.updateMask_ = (DocumentMask) codedInputStream.readMessage(DocumentMask.parser(), extensionRegistryLite);
                                if (builder2 != null) {
                                    builder2.mergeFrom(this.updateMask_);
                                    this.updateMask_ = (DocumentMask) builder2.buildPartial();
                                }
                            } else if (i == 34) {
                                com.google.firestore.v1.Precondition.Builder builder3 = this.currentDocument_ != null ? (com.google.firestore.v1.Precondition.Builder) this.currentDocument_.toBuilder() : null;
                                this.currentDocument_ = (Precondition) codedInputStream.readMessage(Precondition.parser(), extensionRegistryLite);
                                if (builder3 != null) {
                                    builder3.mergeFrom(this.currentDocument_);
                                    this.currentDocument_ = (Precondition) builder3.buildPartial();
                                }
                            } else if (i == 50) {
                                com.google.firestore.v1.DocumentTransform.Builder builder4 = this.operationCase_ == 6 ? (com.google.firestore.v1.DocumentTransform.Builder) ((DocumentTransform) this.operation_).toBuilder() : null;
                                this.operation_ = codedInputStream.readMessage(DocumentTransform.parser(), extensionRegistryLite);
                                if (builder4 != null) {
                                    builder4.mergeFrom((DocumentTransform) this.operation_);
                                    this.operation_ = builder4.buildPartial();
                                }
                                this.operationCase_ = 6;
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
                    synchronized (Write.class) {
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

    public static Write getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Write> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
