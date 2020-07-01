package com.google.firebase.firestore.proto;

import com.google.firestore.v1.Document;
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
public final class MaybeDocument extends GeneratedMessageLite<MaybeDocument, Builder> implements MaybeDocumentOrBuilder {
    private static final MaybeDocument DEFAULT_INSTANCE = new MaybeDocument();
    public static final int DOCUMENT_FIELD_NUMBER = 2;
    public static final int HAS_COMMITTED_MUTATIONS_FIELD_NUMBER = 4;
    public static final int NO_DOCUMENT_FIELD_NUMBER = 1;
    private static volatile Parser<MaybeDocument> PARSER = null;
    public static final int UNKNOWN_DOCUMENT_FIELD_NUMBER = 3;
    private int documentTypeCase_ = 0;
    private Object documentType_;
    private boolean hasCommittedMutations_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firebase.firestore.proto.MaybeDocument$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase = new int[DocumentTypeCase.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:20:0x0062, code:
            $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase = new int[com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.values().length];
     */
        /* JADX WARNING: Missing block: B:22:?, code:
            $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase[com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.NO_DOCUMENT.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:24:?, code:
            $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase[com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.DOCUMENT.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:26:?, code:
            $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase[com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.UNKNOWN_DOCUMENT.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:28:?, code:
            $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase[com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.DOCUMENTTYPE_NOT_SET.ordinal()] = 4;
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
            r4 = com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.values();
            r4 = r4.length;
            r4 = new int[r4];
            $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase = r4;
            r4 = $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r5 = com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.NO_DOCUMENT;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0075 }
            r4[r5] = r0;	 Catch:{ NoSuchFieldError -> 0x0075 }
        L_0x0075:
            r0 = $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase;	 Catch:{ NoSuchFieldError -> 0x007f }
            r4 = com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.DOCUMENT;	 Catch:{ NoSuchFieldError -> 0x007f }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x007f }
            r0[r4] = r1;	 Catch:{ NoSuchFieldError -> 0x007f }
        L_0x007f:
            r0 = $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r1 = com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.UNKNOWN_DOCUMENT;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0089 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0089 }
        L_0x0089:
            r0 = $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase;	 Catch:{ NoSuchFieldError -> 0x0093 }
            r1 = com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.DOCUMENTTYPE_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x0093 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0093 }
            r0[r1] = r3;	 Catch:{ NoSuchFieldError -> 0x0093 }
        L_0x0093:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.proto.MaybeDocument.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum DocumentTypeCase implements EnumLite {
        NO_DOCUMENT(1),
        DOCUMENT(2),
        UNKNOWN_DOCUMENT(3),
        DOCUMENTTYPE_NOT_SET(0);
        
        private final int value;

        private DocumentTypeCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static DocumentTypeCase valueOf(int i) {
            return forNumber(i);
        }

        public static DocumentTypeCase forNumber(int i) {
            if (i == 0) {
                return DOCUMENTTYPE_NOT_SET;
            }
            if (i == 1) {
                return NO_DOCUMENT;
            }
            if (i != 2) {
                return i != 3 ? null : UNKNOWN_DOCUMENT;
            } else {
                return DOCUMENT;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<MaybeDocument, Builder> implements MaybeDocumentOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(MaybeDocument.DEFAULT_INSTANCE);
        }

        public DocumentTypeCase getDocumentTypeCase() {
            return ((MaybeDocument) this.instance).getDocumentTypeCase();
        }

        public Builder clearDocumentType() {
            copyOnWrite();
            ((MaybeDocument) this.instance).clearDocumentType();
            return this;
        }

        public NoDocument getNoDocument() {
            return ((MaybeDocument) this.instance).getNoDocument();
        }

        public Builder setNoDocument(NoDocument noDocument) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setNoDocument(noDocument);
            return this;
        }

        public Builder setNoDocument(com.google.firebase.firestore.proto.NoDocument.Builder builder) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setNoDocument(builder);
            return this;
        }

        public Builder mergeNoDocument(NoDocument noDocument) {
            copyOnWrite();
            ((MaybeDocument) this.instance).mergeNoDocument(noDocument);
            return this;
        }

        public Builder clearNoDocument() {
            copyOnWrite();
            ((MaybeDocument) this.instance).clearNoDocument();
            return this;
        }

        public Document getDocument() {
            return ((MaybeDocument) this.instance).getDocument();
        }

        public Builder setDocument(Document document) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setDocument(document);
            return this;
        }

        public Builder setDocument(com.google.firestore.v1.Document.Builder builder) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setDocument(builder);
            return this;
        }

        public Builder mergeDocument(Document document) {
            copyOnWrite();
            ((MaybeDocument) this.instance).mergeDocument(document);
            return this;
        }

        public Builder clearDocument() {
            copyOnWrite();
            ((MaybeDocument) this.instance).clearDocument();
            return this;
        }

        public UnknownDocument getUnknownDocument() {
            return ((MaybeDocument) this.instance).getUnknownDocument();
        }

        public Builder setUnknownDocument(UnknownDocument unknownDocument) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setUnknownDocument(unknownDocument);
            return this;
        }

        public Builder setUnknownDocument(com.google.firebase.firestore.proto.UnknownDocument.Builder builder) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setUnknownDocument(builder);
            return this;
        }

        public Builder mergeUnknownDocument(UnknownDocument unknownDocument) {
            copyOnWrite();
            ((MaybeDocument) this.instance).mergeUnknownDocument(unknownDocument);
            return this;
        }

        public Builder clearUnknownDocument() {
            copyOnWrite();
            ((MaybeDocument) this.instance).clearUnknownDocument();
            return this;
        }

        public boolean getHasCommittedMutations() {
            return ((MaybeDocument) this.instance).getHasCommittedMutations();
        }

        public Builder setHasCommittedMutations(boolean z) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setHasCommittedMutations(z);
            return this;
        }

        public Builder clearHasCommittedMutations() {
            copyOnWrite();
            ((MaybeDocument) this.instance).clearHasCommittedMutations();
            return this;
        }
    }

    private MaybeDocument() {
    }

    public DocumentTypeCase getDocumentTypeCase() {
        return DocumentTypeCase.forNumber(this.documentTypeCase_);
    }

    private void clearDocumentType() {
        this.documentTypeCase_ = 0;
        this.documentType_ = null;
    }

    public NoDocument getNoDocument() {
        if (this.documentTypeCase_ == 1) {
            return (NoDocument) this.documentType_;
        }
        return NoDocument.getDefaultInstance();
    }

    private void setNoDocument(NoDocument noDocument) {
        if (noDocument != null) {
            this.documentType_ = noDocument;
            this.documentTypeCase_ = 1;
            return;
        }
        throw new NullPointerException();
    }

    private void setNoDocument(com.google.firebase.firestore.proto.NoDocument.Builder builder) {
        this.documentType_ = builder.build();
        this.documentTypeCase_ = 1;
    }

    private void mergeNoDocument(NoDocument noDocument) {
        if (this.documentTypeCase_ != 1 || this.documentType_ == NoDocument.getDefaultInstance()) {
            this.documentType_ = noDocument;
        } else {
            this.documentType_ = ((com.google.firebase.firestore.proto.NoDocument.Builder) NoDocument.newBuilder((NoDocument) this.documentType_).mergeFrom(noDocument)).buildPartial();
        }
        this.documentTypeCase_ = 1;
    }

    private void clearNoDocument() {
        if (this.documentTypeCase_ == 1) {
            this.documentTypeCase_ = 0;
            this.documentType_ = null;
        }
    }

    public Document getDocument() {
        if (this.documentTypeCase_ == 2) {
            return (Document) this.documentType_;
        }
        return Document.getDefaultInstance();
    }

    private void setDocument(Document document) {
        if (document != null) {
            this.documentType_ = document;
            this.documentTypeCase_ = 2;
            return;
        }
        throw new NullPointerException();
    }

    private void setDocument(com.google.firestore.v1.Document.Builder builder) {
        this.documentType_ = builder.build();
        this.documentTypeCase_ = 2;
    }

    private void mergeDocument(Document document) {
        if (this.documentTypeCase_ != 2 || this.documentType_ == Document.getDefaultInstance()) {
            this.documentType_ = document;
        } else {
            this.documentType_ = ((com.google.firestore.v1.Document.Builder) Document.newBuilder((Document) this.documentType_).mergeFrom(document)).buildPartial();
        }
        this.documentTypeCase_ = 2;
    }

    private void clearDocument() {
        if (this.documentTypeCase_ == 2) {
            this.documentTypeCase_ = 0;
            this.documentType_ = null;
        }
    }

    public UnknownDocument getUnknownDocument() {
        if (this.documentTypeCase_ == 3) {
            return (UnknownDocument) this.documentType_;
        }
        return UnknownDocument.getDefaultInstance();
    }

    private void setUnknownDocument(UnknownDocument unknownDocument) {
        if (unknownDocument != null) {
            this.documentType_ = unknownDocument;
            this.documentTypeCase_ = 3;
            return;
        }
        throw new NullPointerException();
    }

    private void setUnknownDocument(com.google.firebase.firestore.proto.UnknownDocument.Builder builder) {
        this.documentType_ = builder.build();
        this.documentTypeCase_ = 3;
    }

    private void mergeUnknownDocument(UnknownDocument unknownDocument) {
        if (this.documentTypeCase_ != 3 || this.documentType_ == UnknownDocument.getDefaultInstance()) {
            this.documentType_ = unknownDocument;
        } else {
            this.documentType_ = ((com.google.firebase.firestore.proto.UnknownDocument.Builder) UnknownDocument.newBuilder((UnknownDocument) this.documentType_).mergeFrom(unknownDocument)).buildPartial();
        }
        this.documentTypeCase_ = 3;
    }

    private void clearUnknownDocument() {
        if (this.documentTypeCase_ == 3) {
            this.documentTypeCase_ = 0;
            this.documentType_ = null;
        }
    }

    public boolean getHasCommittedMutations() {
        return this.hasCommittedMutations_;
    }

    private void setHasCommittedMutations(boolean z) {
        this.hasCommittedMutations_ = z;
    }

    private void clearHasCommittedMutations() {
        this.hasCommittedMutations_ = false;
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (this.documentTypeCase_ == 1) {
            codedOutputStream.writeMessage(1, (NoDocument) this.documentType_);
        }
        if (this.documentTypeCase_ == 2) {
            codedOutputStream.writeMessage(2, (Document) this.documentType_);
        }
        if (this.documentTypeCase_ == 3) {
            codedOutputStream.writeMessage(3, (UnknownDocument) this.documentType_);
        }
        boolean z = this.hasCommittedMutations_;
        if (z) {
            codedOutputStream.writeBool(4, z);
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (this.documentTypeCase_ == 1) {
            i = 0 + CodedOutputStream.computeMessageSize(1, (NoDocument) this.documentType_);
        }
        if (this.documentTypeCase_ == 2) {
            i += CodedOutputStream.computeMessageSize(2, (Document) this.documentType_);
        }
        if (this.documentTypeCase_ == 3) {
            i += CodedOutputStream.computeMessageSize(3, (UnknownDocument) this.documentType_);
        }
        boolean z = this.hasCommittedMutations_;
        if (z) {
            i += CodedOutputStream.computeBoolSize(4, z);
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static MaybeDocument parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static MaybeDocument parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static MaybeDocument parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static MaybeDocument parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static MaybeDocument parseFrom(InputStream inputStream) throws IOException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static MaybeDocument parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static MaybeDocument parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (MaybeDocument) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static MaybeDocument parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (MaybeDocument) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static MaybeDocument parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static MaybeDocument parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(MaybeDocument maybeDocument) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(maybeDocument);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        int i;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new MaybeDocument();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                MaybeDocument maybeDocument = (MaybeDocument) obj2;
                boolean z2 = this.hasCommittedMutations_;
                boolean z3 = maybeDocument.hasCommittedMutations_;
                this.hasCommittedMutations_ = visitor.visitBoolean(z2, z2, z3, z3);
                i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase[maybeDocument.getDocumentTypeCase().ordinal()];
                if (i == 1) {
                    if (this.documentTypeCase_ == 1) {
                        z = true;
                    }
                    this.documentType_ = visitor.visitOneofMessage(z, this.documentType_, maybeDocument.documentType_);
                } else if (i == 2) {
                    if (this.documentTypeCase_ == 2) {
                        z = true;
                    }
                    this.documentType_ = visitor.visitOneofMessage(z, this.documentType_, maybeDocument.documentType_);
                } else if (i == 3) {
                    if (this.documentTypeCase_ == 3) {
                        z = true;
                    }
                    this.documentType_ = visitor.visitOneofMessage(z, this.documentType_, maybeDocument.documentType_);
                } else if (i == 4) {
                    if (this.documentTypeCase_ != 0) {
                        z = true;
                    }
                    visitor.visitOneofNotSet(z);
                }
                if (visitor == MergeFromVisitor.INSTANCE) {
                    i = maybeDocument.documentTypeCase_;
                    if (i != 0) {
                        this.documentTypeCase_ = i;
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
                                com.google.firebase.firestore.proto.NoDocument.Builder builder = this.documentTypeCase_ == 1 ? (com.google.firebase.firestore.proto.NoDocument.Builder) ((NoDocument) this.documentType_).toBuilder() : null;
                                this.documentType_ = codedInputStream.readMessage(NoDocument.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom((NoDocument) this.documentType_);
                                    this.documentType_ = builder.buildPartial();
                                }
                                this.documentTypeCase_ = 1;
                            } else if (i == 18) {
                                com.google.firestore.v1.Document.Builder builder2 = this.documentTypeCase_ == 2 ? (com.google.firestore.v1.Document.Builder) ((Document) this.documentType_).toBuilder() : null;
                                this.documentType_ = codedInputStream.readMessage(Document.parser(), extensionRegistryLite);
                                if (builder2 != null) {
                                    builder2.mergeFrom((Document) this.documentType_);
                                    this.documentType_ = builder2.buildPartial();
                                }
                                this.documentTypeCase_ = 2;
                            } else if (i == 26) {
                                com.google.firebase.firestore.proto.UnknownDocument.Builder builder3 = this.documentTypeCase_ == 3 ? (com.google.firebase.firestore.proto.UnknownDocument.Builder) ((UnknownDocument) this.documentType_).toBuilder() : null;
                                this.documentType_ = codedInputStream.readMessage(UnknownDocument.parser(), extensionRegistryLite);
                                if (builder3 != null) {
                                    builder3.mergeFrom((UnknownDocument) this.documentType_);
                                    this.documentType_ = builder3.buildPartial();
                                }
                                this.documentTypeCase_ = 3;
                            } else if (i == 32) {
                                this.hasCommittedMutations_ = codedInputStream.readBool();
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
                    synchronized (MaybeDocument.class) {
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

    public static MaybeDocument getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MaybeDocument> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
