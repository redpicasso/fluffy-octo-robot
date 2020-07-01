package com.google.firestore.v1;

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
public final class ListenResponse extends GeneratedMessageLite<ListenResponse, Builder> implements ListenResponseOrBuilder {
    private static final ListenResponse DEFAULT_INSTANCE = new ListenResponse();
    public static final int DOCUMENT_CHANGE_FIELD_NUMBER = 3;
    public static final int DOCUMENT_DELETE_FIELD_NUMBER = 4;
    public static final int DOCUMENT_REMOVE_FIELD_NUMBER = 6;
    public static final int FILTER_FIELD_NUMBER = 5;
    private static volatile Parser<ListenResponse> PARSER = null;
    public static final int TARGET_CHANGE_FIELD_NUMBER = 2;
    private int responseTypeCase_ = 0;
    private Object responseType_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum ResponseTypeCase implements EnumLite {
        TARGET_CHANGE(2),
        DOCUMENT_CHANGE(3),
        DOCUMENT_DELETE(4),
        DOCUMENT_REMOVE(6),
        FILTER(5),
        RESPONSETYPE_NOT_SET(0);
        
        private final int value;

        private ResponseTypeCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static ResponseTypeCase valueOf(int i) {
            return forNumber(i);
        }

        public static ResponseTypeCase forNumber(int i) {
            if (i == 0) {
                return RESPONSETYPE_NOT_SET;
            }
            if (i == 2) {
                return TARGET_CHANGE;
            }
            if (i == 3) {
                return DOCUMENT_CHANGE;
            }
            if (i == 4) {
                return DOCUMENT_DELETE;
            }
            if (i != 5) {
                return i != 6 ? null : DOCUMENT_REMOVE;
            } else {
                return FILTER;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<ListenResponse, Builder> implements ListenResponseOrBuilder {
        private Builder() {
            super(ListenResponse.DEFAULT_INSTANCE);
        }

        public ResponseTypeCase getResponseTypeCase() {
            return ((ListenResponse) this.instance).getResponseTypeCase();
        }

        public Builder clearResponseType() {
            copyOnWrite();
            ((ListenResponse) this.instance).clearResponseType();
            return this;
        }

        public TargetChange getTargetChange() {
            return ((ListenResponse) this.instance).getTargetChange();
        }

        public Builder setTargetChange(TargetChange targetChange) {
            copyOnWrite();
            ((ListenResponse) this.instance).setTargetChange(targetChange);
            return this;
        }

        public Builder setTargetChange(com.google.firestore.v1.TargetChange.Builder builder) {
            copyOnWrite();
            ((ListenResponse) this.instance).setTargetChange(builder);
            return this;
        }

        public Builder mergeTargetChange(TargetChange targetChange) {
            copyOnWrite();
            ((ListenResponse) this.instance).mergeTargetChange(targetChange);
            return this;
        }

        public Builder clearTargetChange() {
            copyOnWrite();
            ((ListenResponse) this.instance).clearTargetChange();
            return this;
        }

        public DocumentChange getDocumentChange() {
            return ((ListenResponse) this.instance).getDocumentChange();
        }

        public Builder setDocumentChange(DocumentChange documentChange) {
            copyOnWrite();
            ((ListenResponse) this.instance).setDocumentChange(documentChange);
            return this;
        }

        public Builder setDocumentChange(com.google.firestore.v1.DocumentChange.Builder builder) {
            copyOnWrite();
            ((ListenResponse) this.instance).setDocumentChange(builder);
            return this;
        }

        public Builder mergeDocumentChange(DocumentChange documentChange) {
            copyOnWrite();
            ((ListenResponse) this.instance).mergeDocumentChange(documentChange);
            return this;
        }

        public Builder clearDocumentChange() {
            copyOnWrite();
            ((ListenResponse) this.instance).clearDocumentChange();
            return this;
        }

        public DocumentDelete getDocumentDelete() {
            return ((ListenResponse) this.instance).getDocumentDelete();
        }

        public Builder setDocumentDelete(DocumentDelete documentDelete) {
            copyOnWrite();
            ((ListenResponse) this.instance).setDocumentDelete(documentDelete);
            return this;
        }

        public Builder setDocumentDelete(com.google.firestore.v1.DocumentDelete.Builder builder) {
            copyOnWrite();
            ((ListenResponse) this.instance).setDocumentDelete(builder);
            return this;
        }

        public Builder mergeDocumentDelete(DocumentDelete documentDelete) {
            copyOnWrite();
            ((ListenResponse) this.instance).mergeDocumentDelete(documentDelete);
            return this;
        }

        public Builder clearDocumentDelete() {
            copyOnWrite();
            ((ListenResponse) this.instance).clearDocumentDelete();
            return this;
        }

        public DocumentRemove getDocumentRemove() {
            return ((ListenResponse) this.instance).getDocumentRemove();
        }

        public Builder setDocumentRemove(DocumentRemove documentRemove) {
            copyOnWrite();
            ((ListenResponse) this.instance).setDocumentRemove(documentRemove);
            return this;
        }

        public Builder setDocumentRemove(com.google.firestore.v1.DocumentRemove.Builder builder) {
            copyOnWrite();
            ((ListenResponse) this.instance).setDocumentRemove(builder);
            return this;
        }

        public Builder mergeDocumentRemove(DocumentRemove documentRemove) {
            copyOnWrite();
            ((ListenResponse) this.instance).mergeDocumentRemove(documentRemove);
            return this;
        }

        public Builder clearDocumentRemove() {
            copyOnWrite();
            ((ListenResponse) this.instance).clearDocumentRemove();
            return this;
        }

        public ExistenceFilter getFilter() {
            return ((ListenResponse) this.instance).getFilter();
        }

        public Builder setFilter(ExistenceFilter existenceFilter) {
            copyOnWrite();
            ((ListenResponse) this.instance).setFilter(existenceFilter);
            return this;
        }

        public Builder setFilter(com.google.firestore.v1.ExistenceFilter.Builder builder) {
            copyOnWrite();
            ((ListenResponse) this.instance).setFilter(builder);
            return this;
        }

        public Builder mergeFilter(ExistenceFilter existenceFilter) {
            copyOnWrite();
            ((ListenResponse) this.instance).mergeFilter(existenceFilter);
            return this;
        }

        public Builder clearFilter() {
            copyOnWrite();
            ((ListenResponse) this.instance).clearFilter();
            return this;
        }
    }

    private ListenResponse() {
    }

    public ResponseTypeCase getResponseTypeCase() {
        return ResponseTypeCase.forNumber(this.responseTypeCase_);
    }

    private void clearResponseType() {
        this.responseTypeCase_ = 0;
        this.responseType_ = null;
    }

    public TargetChange getTargetChange() {
        if (this.responseTypeCase_ == 2) {
            return (TargetChange) this.responseType_;
        }
        return TargetChange.getDefaultInstance();
    }

    private void setTargetChange(TargetChange targetChange) {
        if (targetChange != null) {
            this.responseType_ = targetChange;
            this.responseTypeCase_ = 2;
            return;
        }
        throw new NullPointerException();
    }

    private void setTargetChange(com.google.firestore.v1.TargetChange.Builder builder) {
        this.responseType_ = builder.build();
        this.responseTypeCase_ = 2;
    }

    private void mergeTargetChange(TargetChange targetChange) {
        if (this.responseTypeCase_ != 2 || this.responseType_ == TargetChange.getDefaultInstance()) {
            this.responseType_ = targetChange;
        } else {
            this.responseType_ = ((com.google.firestore.v1.TargetChange.Builder) TargetChange.newBuilder((TargetChange) this.responseType_).mergeFrom(targetChange)).buildPartial();
        }
        this.responseTypeCase_ = 2;
    }

    private void clearTargetChange() {
        if (this.responseTypeCase_ == 2) {
            this.responseTypeCase_ = 0;
            this.responseType_ = null;
        }
    }

    public DocumentChange getDocumentChange() {
        if (this.responseTypeCase_ == 3) {
            return (DocumentChange) this.responseType_;
        }
        return DocumentChange.getDefaultInstance();
    }

    private void setDocumentChange(DocumentChange documentChange) {
        if (documentChange != null) {
            this.responseType_ = documentChange;
            this.responseTypeCase_ = 3;
            return;
        }
        throw new NullPointerException();
    }

    private void setDocumentChange(com.google.firestore.v1.DocumentChange.Builder builder) {
        this.responseType_ = builder.build();
        this.responseTypeCase_ = 3;
    }

    private void mergeDocumentChange(DocumentChange documentChange) {
        if (this.responseTypeCase_ != 3 || this.responseType_ == DocumentChange.getDefaultInstance()) {
            this.responseType_ = documentChange;
        } else {
            this.responseType_ = ((com.google.firestore.v1.DocumentChange.Builder) DocumentChange.newBuilder((DocumentChange) this.responseType_).mergeFrom(documentChange)).buildPartial();
        }
        this.responseTypeCase_ = 3;
    }

    private void clearDocumentChange() {
        if (this.responseTypeCase_ == 3) {
            this.responseTypeCase_ = 0;
            this.responseType_ = null;
        }
    }

    public DocumentDelete getDocumentDelete() {
        if (this.responseTypeCase_ == 4) {
            return (DocumentDelete) this.responseType_;
        }
        return DocumentDelete.getDefaultInstance();
    }

    private void setDocumentDelete(DocumentDelete documentDelete) {
        if (documentDelete != null) {
            this.responseType_ = documentDelete;
            this.responseTypeCase_ = 4;
            return;
        }
        throw new NullPointerException();
    }

    private void setDocumentDelete(com.google.firestore.v1.DocumentDelete.Builder builder) {
        this.responseType_ = builder.build();
        this.responseTypeCase_ = 4;
    }

    private void mergeDocumentDelete(DocumentDelete documentDelete) {
        if (this.responseTypeCase_ != 4 || this.responseType_ == DocumentDelete.getDefaultInstance()) {
            this.responseType_ = documentDelete;
        } else {
            this.responseType_ = ((com.google.firestore.v1.DocumentDelete.Builder) DocumentDelete.newBuilder((DocumentDelete) this.responseType_).mergeFrom(documentDelete)).buildPartial();
        }
        this.responseTypeCase_ = 4;
    }

    private void clearDocumentDelete() {
        if (this.responseTypeCase_ == 4) {
            this.responseTypeCase_ = 0;
            this.responseType_ = null;
        }
    }

    public DocumentRemove getDocumentRemove() {
        if (this.responseTypeCase_ == 6) {
            return (DocumentRemove) this.responseType_;
        }
        return DocumentRemove.getDefaultInstance();
    }

    private void setDocumentRemove(DocumentRemove documentRemove) {
        if (documentRemove != null) {
            this.responseType_ = documentRemove;
            this.responseTypeCase_ = 6;
            return;
        }
        throw new NullPointerException();
    }

    private void setDocumentRemove(com.google.firestore.v1.DocumentRemove.Builder builder) {
        this.responseType_ = builder.build();
        this.responseTypeCase_ = 6;
    }

    private void mergeDocumentRemove(DocumentRemove documentRemove) {
        if (this.responseTypeCase_ != 6 || this.responseType_ == DocumentRemove.getDefaultInstance()) {
            this.responseType_ = documentRemove;
        } else {
            this.responseType_ = ((com.google.firestore.v1.DocumentRemove.Builder) DocumentRemove.newBuilder((DocumentRemove) this.responseType_).mergeFrom(documentRemove)).buildPartial();
        }
        this.responseTypeCase_ = 6;
    }

    private void clearDocumentRemove() {
        if (this.responseTypeCase_ == 6) {
            this.responseTypeCase_ = 0;
            this.responseType_ = null;
        }
    }

    public ExistenceFilter getFilter() {
        if (this.responseTypeCase_ == 5) {
            return (ExistenceFilter) this.responseType_;
        }
        return ExistenceFilter.getDefaultInstance();
    }

    private void setFilter(ExistenceFilter existenceFilter) {
        if (existenceFilter != null) {
            this.responseType_ = existenceFilter;
            this.responseTypeCase_ = 5;
            return;
        }
        throw new NullPointerException();
    }

    private void setFilter(com.google.firestore.v1.ExistenceFilter.Builder builder) {
        this.responseType_ = builder.build();
        this.responseTypeCase_ = 5;
    }

    private void mergeFilter(ExistenceFilter existenceFilter) {
        if (this.responseTypeCase_ != 5 || this.responseType_ == ExistenceFilter.getDefaultInstance()) {
            this.responseType_ = existenceFilter;
        } else {
            this.responseType_ = ((com.google.firestore.v1.ExistenceFilter.Builder) ExistenceFilter.newBuilder((ExistenceFilter) this.responseType_).mergeFrom(existenceFilter)).buildPartial();
        }
        this.responseTypeCase_ = 5;
    }

    private void clearFilter() {
        if (this.responseTypeCase_ == 5) {
            this.responseTypeCase_ = 0;
            this.responseType_ = null;
        }
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (this.responseTypeCase_ == 2) {
            codedOutputStream.writeMessage(2, (TargetChange) this.responseType_);
        }
        if (this.responseTypeCase_ == 3) {
            codedOutputStream.writeMessage(3, (DocumentChange) this.responseType_);
        }
        if (this.responseTypeCase_ == 4) {
            codedOutputStream.writeMessage(4, (DocumentDelete) this.responseType_);
        }
        if (this.responseTypeCase_ == 5) {
            codedOutputStream.writeMessage(5, (ExistenceFilter) this.responseType_);
        }
        if (this.responseTypeCase_ == 6) {
            codedOutputStream.writeMessage(6, (DocumentRemove) this.responseType_);
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (this.responseTypeCase_ == 2) {
            i = 0 + CodedOutputStream.computeMessageSize(2, (TargetChange) this.responseType_);
        }
        if (this.responseTypeCase_ == 3) {
            i += CodedOutputStream.computeMessageSize(3, (DocumentChange) this.responseType_);
        }
        if (this.responseTypeCase_ == 4) {
            i += CodedOutputStream.computeMessageSize(4, (DocumentDelete) this.responseType_);
        }
        if (this.responseTypeCase_ == 5) {
            i += CodedOutputStream.computeMessageSize(5, (ExistenceFilter) this.responseType_);
        }
        if (this.responseTypeCase_ == 6) {
            i += CodedOutputStream.computeMessageSize(6, (DocumentRemove) this.responseType_);
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static ListenResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (ListenResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static ListenResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (ListenResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static ListenResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (ListenResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static ListenResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (ListenResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static ListenResponse parseFrom(InputStream inputStream) throws IOException {
        return (ListenResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static ListenResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListenResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static ListenResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (ListenResponse) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static ListenResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListenResponse) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static ListenResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (ListenResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static ListenResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListenResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ListenResponse listenResponse) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(listenResponse);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        int i;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new ListenResponse();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                ListenResponse listenResponse = (ListenResponse) obj2;
                switch (listenResponse.getResponseTypeCase()) {
                    case TARGET_CHANGE:
                        if (this.responseTypeCase_ == 2) {
                            z = true;
                        }
                        this.responseType_ = visitor.visitOneofMessage(z, this.responseType_, listenResponse.responseType_);
                        break;
                    case DOCUMENT_CHANGE:
                        if (this.responseTypeCase_ == 3) {
                            z = true;
                        }
                        this.responseType_ = visitor.visitOneofMessage(z, this.responseType_, listenResponse.responseType_);
                        break;
                    case DOCUMENT_DELETE:
                        if (this.responseTypeCase_ == 4) {
                            z = true;
                        }
                        this.responseType_ = visitor.visitOneofMessage(z, this.responseType_, listenResponse.responseType_);
                        break;
                    case DOCUMENT_REMOVE:
                        if (this.responseTypeCase_ == 6) {
                            z = true;
                        }
                        this.responseType_ = visitor.visitOneofMessage(z, this.responseType_, listenResponse.responseType_);
                        break;
                    case FILTER:
                        if (this.responseTypeCase_ == 5) {
                            z = true;
                        }
                        this.responseType_ = visitor.visitOneofMessage(z, this.responseType_, listenResponse.responseType_);
                        break;
                    case RESPONSETYPE_NOT_SET:
                        if (this.responseTypeCase_ != 0) {
                            z = true;
                        }
                        visitor.visitOneofNotSet(z);
                        break;
                }
                if (visitor == MergeFromVisitor.INSTANCE) {
                    i = listenResponse.responseTypeCase_;
                    if (i != 0) {
                        this.responseTypeCase_ = i;
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
                            if (i == 18) {
                                com.google.firestore.v1.TargetChange.Builder builder = this.responseTypeCase_ == 2 ? (com.google.firestore.v1.TargetChange.Builder) ((TargetChange) this.responseType_).toBuilder() : null;
                                this.responseType_ = codedInputStream.readMessage(TargetChange.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom((TargetChange) this.responseType_);
                                    this.responseType_ = builder.buildPartial();
                                }
                                this.responseTypeCase_ = 2;
                            } else if (i == 26) {
                                com.google.firestore.v1.DocumentChange.Builder builder2 = this.responseTypeCase_ == 3 ? (com.google.firestore.v1.DocumentChange.Builder) ((DocumentChange) this.responseType_).toBuilder() : null;
                                this.responseType_ = codedInputStream.readMessage(DocumentChange.parser(), extensionRegistryLite);
                                if (builder2 != null) {
                                    builder2.mergeFrom((DocumentChange) this.responseType_);
                                    this.responseType_ = builder2.buildPartial();
                                }
                                this.responseTypeCase_ = 3;
                            } else if (i == 34) {
                                com.google.firestore.v1.DocumentDelete.Builder builder3 = this.responseTypeCase_ == 4 ? (com.google.firestore.v1.DocumentDelete.Builder) ((DocumentDelete) this.responseType_).toBuilder() : null;
                                this.responseType_ = codedInputStream.readMessage(DocumentDelete.parser(), extensionRegistryLite);
                                if (builder3 != null) {
                                    builder3.mergeFrom((DocumentDelete) this.responseType_);
                                    this.responseType_ = builder3.buildPartial();
                                }
                                this.responseTypeCase_ = 4;
                            } else if (i == 42) {
                                com.google.firestore.v1.ExistenceFilter.Builder builder4 = this.responseTypeCase_ == 5 ? (com.google.firestore.v1.ExistenceFilter.Builder) ((ExistenceFilter) this.responseType_).toBuilder() : null;
                                this.responseType_ = codedInputStream.readMessage(ExistenceFilter.parser(), extensionRegistryLite);
                                if (builder4 != null) {
                                    builder4.mergeFrom((ExistenceFilter) this.responseType_);
                                    this.responseType_ = builder4.buildPartial();
                                }
                                this.responseTypeCase_ = 5;
                            } else if (i == 50) {
                                com.google.firestore.v1.DocumentRemove.Builder builder5 = this.responseTypeCase_ == 6 ? (com.google.firestore.v1.DocumentRemove.Builder) ((DocumentRemove) this.responseType_).toBuilder() : null;
                                this.responseType_ = codedInputStream.readMessage(DocumentRemove.parser(), extensionRegistryLite);
                                if (builder5 != null) {
                                    builder5.mergeFrom((DocumentRemove) this.responseType_);
                                    this.responseType_ = builder5.buildPartial();
                                }
                                this.responseTypeCase_ = 6;
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
                    synchronized (ListenResponse.class) {
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

    public static ListenResponse getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ListenResponse> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
