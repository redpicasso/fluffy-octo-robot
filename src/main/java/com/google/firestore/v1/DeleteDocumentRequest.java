package com.google.firestore.v1;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class DeleteDocumentRequest extends GeneratedMessageLite<DeleteDocumentRequest, Builder> implements DeleteDocumentRequestOrBuilder {
    public static final int CURRENT_DOCUMENT_FIELD_NUMBER = 2;
    private static final DeleteDocumentRequest DEFAULT_INSTANCE = new DeleteDocumentRequest();
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile Parser<DeleteDocumentRequest> PARSER;
    private Precondition currentDocument_;
    private String name_ = "";

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<DeleteDocumentRequest, Builder> implements DeleteDocumentRequestOrBuilder {
        private Builder() {
            super(DeleteDocumentRequest.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((DeleteDocumentRequest) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((DeleteDocumentRequest) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((DeleteDocumentRequest) this.instance).setName(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((DeleteDocumentRequest) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((DeleteDocumentRequest) this.instance).setNameBytes(byteString);
            return this;
        }

        public boolean hasCurrentDocument() {
            return ((DeleteDocumentRequest) this.instance).hasCurrentDocument();
        }

        public Precondition getCurrentDocument() {
            return ((DeleteDocumentRequest) this.instance).getCurrentDocument();
        }

        public Builder setCurrentDocument(Precondition precondition) {
            copyOnWrite();
            ((DeleteDocumentRequest) this.instance).setCurrentDocument(precondition);
            return this;
        }

        public Builder setCurrentDocument(com.google.firestore.v1.Precondition.Builder builder) {
            copyOnWrite();
            ((DeleteDocumentRequest) this.instance).setCurrentDocument(builder);
            return this;
        }

        public Builder mergeCurrentDocument(Precondition precondition) {
            copyOnWrite();
            ((DeleteDocumentRequest) this.instance).mergeCurrentDocument(precondition);
            return this;
        }

        public Builder clearCurrentDocument() {
            copyOnWrite();
            ((DeleteDocumentRequest) this.instance).clearCurrentDocument();
            return this;
        }
    }

    private DeleteDocumentRequest() {
    }

    public String getName() {
        return this.name_;
    }

    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    private void setName(String str) {
        if (str != null) {
            this.name_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearName() {
        this.name_ = getDefaultInstance().getName();
    }

    private void setNameBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.name_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
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
        if (!this.name_.isEmpty()) {
            codedOutputStream.writeString(1, getName());
        }
        if (this.currentDocument_ != null) {
            codedOutputStream.writeMessage(2, getCurrentDocument());
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (!this.name_.isEmpty()) {
            i = 0 + CodedOutputStream.computeStringSize(1, getName());
        }
        if (this.currentDocument_ != null) {
            i += CodedOutputStream.computeMessageSize(2, getCurrentDocument());
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static DeleteDocumentRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (DeleteDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static DeleteDocumentRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (DeleteDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static DeleteDocumentRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (DeleteDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static DeleteDocumentRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (DeleteDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static DeleteDocumentRequest parseFrom(InputStream inputStream) throws IOException {
        return (DeleteDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static DeleteDocumentRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (DeleteDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static DeleteDocumentRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (DeleteDocumentRequest) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static DeleteDocumentRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (DeleteDocumentRequest) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static DeleteDocumentRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (DeleteDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static DeleteDocumentRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (DeleteDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(DeleteDocumentRequest deleteDocumentRequest) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(deleteDocumentRequest);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new DeleteDocumentRequest();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                DeleteDocumentRequest deleteDocumentRequest = (DeleteDocumentRequest) obj2;
                this.name_ = visitor.visitString(this.name_.isEmpty() ^ true, this.name_, true ^ deleteDocumentRequest.name_.isEmpty(), deleteDocumentRequest.name_);
                this.currentDocument_ = (Precondition) visitor.visitMessage(this.currentDocument_, deleteDocumentRequest.currentDocument_);
                MergeFromVisitor mergeFromVisitor = MergeFromVisitor.INSTANCE;
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
                                this.name_ = codedInputStream.readStringRequireUtf8();
                            } else if (readTag == 18) {
                                com.google.firestore.v1.Precondition.Builder builder = this.currentDocument_ != null ? (com.google.firestore.v1.Precondition.Builder) this.currentDocument_.toBuilder() : null;
                                this.currentDocument_ = (Precondition) codedInputStream.readMessage(Precondition.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom(this.currentDocument_);
                                    this.currentDocument_ = (Precondition) builder.buildPartial();
                                }
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
                    synchronized (DeleteDocumentRequest.class) {
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

    public static DeleteDocumentRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<DeleteDocumentRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
