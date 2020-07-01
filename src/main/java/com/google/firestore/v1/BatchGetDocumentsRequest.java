package com.google.firestore.v1;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.ProtobufList;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class BatchGetDocumentsRequest extends GeneratedMessageLite<BatchGetDocumentsRequest, Builder> implements BatchGetDocumentsRequestOrBuilder {
    public static final int DATABASE_FIELD_NUMBER = 1;
    private static final BatchGetDocumentsRequest DEFAULT_INSTANCE = new BatchGetDocumentsRequest();
    public static final int DOCUMENTS_FIELD_NUMBER = 2;
    public static final int MASK_FIELD_NUMBER = 3;
    public static final int NEW_TRANSACTION_FIELD_NUMBER = 5;
    private static volatile Parser<BatchGetDocumentsRequest> PARSER = null;
    public static final int READ_TIME_FIELD_NUMBER = 7;
    public static final int TRANSACTION_FIELD_NUMBER = 4;
    private int bitField0_;
    private int consistencySelectorCase_ = 0;
    private Object consistencySelector_;
    private String database_ = "";
    private ProtobufList<String> documents_ = GeneratedMessageLite.emptyProtobufList();
    private DocumentMask mask_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firestore.v1.BatchGetDocumentsRequest$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase = new int[ConsistencySelectorCase.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:20:0x0062, code:
            $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase = new int[com.google.firestore.v1.BatchGetDocumentsRequest.ConsistencySelectorCase.values().length];
     */
        /* JADX WARNING: Missing block: B:22:?, code:
            $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase[com.google.firestore.v1.BatchGetDocumentsRequest.ConsistencySelectorCase.TRANSACTION.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:24:?, code:
            $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase[com.google.firestore.v1.BatchGetDocumentsRequest.ConsistencySelectorCase.NEW_TRANSACTION.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:26:?, code:
            $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase[com.google.firestore.v1.BatchGetDocumentsRequest.ConsistencySelectorCase.READ_TIME.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:28:?, code:
            $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase[com.google.firestore.v1.BatchGetDocumentsRequest.ConsistencySelectorCase.CONSISTENCYSELECTOR_NOT_SET.ordinal()] = 4;
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
            r4 = com.google.firestore.v1.BatchGetDocumentsRequest.ConsistencySelectorCase.values();
            r4 = r4.length;
            r4 = new int[r4];
            $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase = r4;
            r4 = $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r5 = com.google.firestore.v1.BatchGetDocumentsRequest.ConsistencySelectorCase.TRANSACTION;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0075 }
            r4[r5] = r0;	 Catch:{ NoSuchFieldError -> 0x0075 }
        L_0x0075:
            r0 = $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase;	 Catch:{ NoSuchFieldError -> 0x007f }
            r4 = com.google.firestore.v1.BatchGetDocumentsRequest.ConsistencySelectorCase.NEW_TRANSACTION;	 Catch:{ NoSuchFieldError -> 0x007f }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x007f }
            r0[r4] = r1;	 Catch:{ NoSuchFieldError -> 0x007f }
        L_0x007f:
            r0 = $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r1 = com.google.firestore.v1.BatchGetDocumentsRequest.ConsistencySelectorCase.READ_TIME;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0089 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0089 }
        L_0x0089:
            r0 = $SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase;	 Catch:{ NoSuchFieldError -> 0x0093 }
            r1 = com.google.firestore.v1.BatchGetDocumentsRequest.ConsistencySelectorCase.CONSISTENCYSELECTOR_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x0093 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0093 }
            r0[r1] = r3;	 Catch:{ NoSuchFieldError -> 0x0093 }
        L_0x0093:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firestore.v1.BatchGetDocumentsRequest.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum ConsistencySelectorCase implements EnumLite {
        TRANSACTION(4),
        NEW_TRANSACTION(5),
        READ_TIME(7),
        CONSISTENCYSELECTOR_NOT_SET(0);
        
        private final int value;

        private ConsistencySelectorCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static ConsistencySelectorCase valueOf(int i) {
            return forNumber(i);
        }

        public static ConsistencySelectorCase forNumber(int i) {
            if (i == 0) {
                return CONSISTENCYSELECTOR_NOT_SET;
            }
            if (i == 7) {
                return READ_TIME;
            }
            if (i != 4) {
                return i != 5 ? null : NEW_TRANSACTION;
            } else {
                return TRANSACTION;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<BatchGetDocumentsRequest, Builder> implements BatchGetDocumentsRequestOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(BatchGetDocumentsRequest.DEFAULT_INSTANCE);
        }

        public ConsistencySelectorCase getConsistencySelectorCase() {
            return ((BatchGetDocumentsRequest) this.instance).getConsistencySelectorCase();
        }

        public Builder clearConsistencySelector() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearConsistencySelector();
            return this;
        }

        public String getDatabase() {
            return ((BatchGetDocumentsRequest) this.instance).getDatabase();
        }

        public ByteString getDatabaseBytes() {
            return ((BatchGetDocumentsRequest) this.instance).getDatabaseBytes();
        }

        public Builder setDatabase(String str) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setDatabase(str);
            return this;
        }

        public Builder clearDatabase() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearDatabase();
            return this;
        }

        public Builder setDatabaseBytes(ByteString byteString) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setDatabaseBytes(byteString);
            return this;
        }

        public List<String> getDocumentsList() {
            return Collections.unmodifiableList(((BatchGetDocumentsRequest) this.instance).getDocumentsList());
        }

        public int getDocumentsCount() {
            return ((BatchGetDocumentsRequest) this.instance).getDocumentsCount();
        }

        public String getDocuments(int i) {
            return ((BatchGetDocumentsRequest) this.instance).getDocuments(i);
        }

        public ByteString getDocumentsBytes(int i) {
            return ((BatchGetDocumentsRequest) this.instance).getDocumentsBytes(i);
        }

        public Builder setDocuments(int i, String str) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setDocuments(i, str);
            return this;
        }

        public Builder addDocuments(String str) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).addDocuments(str);
            return this;
        }

        public Builder addAllDocuments(Iterable<String> iterable) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).addAllDocuments(iterable);
            return this;
        }

        public Builder clearDocuments() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearDocuments();
            return this;
        }

        public Builder addDocumentsBytes(ByteString byteString) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).addDocumentsBytes(byteString);
            return this;
        }

        public boolean hasMask() {
            return ((BatchGetDocumentsRequest) this.instance).hasMask();
        }

        public DocumentMask getMask() {
            return ((BatchGetDocumentsRequest) this.instance).getMask();
        }

        public Builder setMask(DocumentMask documentMask) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setMask(documentMask);
            return this;
        }

        public Builder setMask(com.google.firestore.v1.DocumentMask.Builder builder) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setMask(builder);
            return this;
        }

        public Builder mergeMask(DocumentMask documentMask) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).mergeMask(documentMask);
            return this;
        }

        public Builder clearMask() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearMask();
            return this;
        }

        public ByteString getTransaction() {
            return ((BatchGetDocumentsRequest) this.instance).getTransaction();
        }

        public Builder setTransaction(ByteString byteString) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setTransaction(byteString);
            return this;
        }

        public Builder clearTransaction() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearTransaction();
            return this;
        }

        public TransactionOptions getNewTransaction() {
            return ((BatchGetDocumentsRequest) this.instance).getNewTransaction();
        }

        public Builder setNewTransaction(TransactionOptions transactionOptions) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setNewTransaction(transactionOptions);
            return this;
        }

        public Builder setNewTransaction(com.google.firestore.v1.TransactionOptions.Builder builder) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setNewTransaction(builder);
            return this;
        }

        public Builder mergeNewTransaction(TransactionOptions transactionOptions) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).mergeNewTransaction(transactionOptions);
            return this;
        }

        public Builder clearNewTransaction() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearNewTransaction();
            return this;
        }

        public Timestamp getReadTime() {
            return ((BatchGetDocumentsRequest) this.instance).getReadTime();
        }

        public Builder setReadTime(Timestamp timestamp) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setReadTime(timestamp);
            return this;
        }

        public Builder setReadTime(com.google.protobuf.Timestamp.Builder builder) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setReadTime(builder);
            return this;
        }

        public Builder mergeReadTime(Timestamp timestamp) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).mergeReadTime(timestamp);
            return this;
        }

        public Builder clearReadTime() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearReadTime();
            return this;
        }
    }

    private BatchGetDocumentsRequest() {
    }

    public ConsistencySelectorCase getConsistencySelectorCase() {
        return ConsistencySelectorCase.forNumber(this.consistencySelectorCase_);
    }

    private void clearConsistencySelector() {
        this.consistencySelectorCase_ = 0;
        this.consistencySelector_ = null;
    }

    public String getDatabase() {
        return this.database_;
    }

    public ByteString getDatabaseBytes() {
        return ByteString.copyFromUtf8(this.database_);
    }

    private void setDatabase(String str) {
        if (str != null) {
            this.database_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearDatabase() {
        this.database_ = getDefaultInstance().getDatabase();
    }

    private void setDatabaseBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.database_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public List<String> getDocumentsList() {
        return this.documents_;
    }

    public int getDocumentsCount() {
        return this.documents_.size();
    }

    public String getDocuments(int i) {
        return (String) this.documents_.get(i);
    }

    public ByteString getDocumentsBytes(int i) {
        return ByteString.copyFromUtf8((String) this.documents_.get(i));
    }

    private void ensureDocumentsIsMutable() {
        if (!this.documents_.isModifiable()) {
            this.documents_ = GeneratedMessageLite.mutableCopy(this.documents_);
        }
    }

    private void setDocuments(int i, String str) {
        if (str != null) {
            ensureDocumentsIsMutable();
            this.documents_.set(i, str);
            return;
        }
        throw new NullPointerException();
    }

    private void addDocuments(String str) {
        if (str != null) {
            ensureDocumentsIsMutable();
            this.documents_.add(str);
            return;
        }
        throw new NullPointerException();
    }

    private void addAllDocuments(Iterable<String> iterable) {
        ensureDocumentsIsMutable();
        AbstractMessageLite.addAll(iterable, this.documents_);
    }

    private void clearDocuments() {
        this.documents_ = GeneratedMessageLite.emptyProtobufList();
    }

    private void addDocumentsBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            ensureDocumentsIsMutable();
            this.documents_.add(byteString.toStringUtf8());
            return;
        }
        throw new NullPointerException();
    }

    public boolean hasMask() {
        return this.mask_ != null;
    }

    public DocumentMask getMask() {
        DocumentMask documentMask = this.mask_;
        return documentMask == null ? DocumentMask.getDefaultInstance() : documentMask;
    }

    private void setMask(DocumentMask documentMask) {
        if (documentMask != null) {
            this.mask_ = documentMask;
            return;
        }
        throw new NullPointerException();
    }

    private void setMask(com.google.firestore.v1.DocumentMask.Builder builder) {
        this.mask_ = (DocumentMask) builder.build();
    }

    private void mergeMask(DocumentMask documentMask) {
        DocumentMask documentMask2 = this.mask_;
        if (documentMask2 == null || documentMask2 == DocumentMask.getDefaultInstance()) {
            this.mask_ = documentMask;
        } else {
            this.mask_ = (DocumentMask) ((com.google.firestore.v1.DocumentMask.Builder) DocumentMask.newBuilder(this.mask_).mergeFrom(documentMask)).buildPartial();
        }
    }

    private void clearMask() {
        this.mask_ = null;
    }

    public ByteString getTransaction() {
        if (this.consistencySelectorCase_ == 4) {
            return (ByteString) this.consistencySelector_;
        }
        return ByteString.EMPTY;
    }

    private void setTransaction(ByteString byteString) {
        if (byteString != null) {
            this.consistencySelectorCase_ = 4;
            this.consistencySelector_ = byteString;
            return;
        }
        throw new NullPointerException();
    }

    private void clearTransaction() {
        if (this.consistencySelectorCase_ == 4) {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }
    }

    public TransactionOptions getNewTransaction() {
        if (this.consistencySelectorCase_ == 5) {
            return (TransactionOptions) this.consistencySelector_;
        }
        return TransactionOptions.getDefaultInstance();
    }

    private void setNewTransaction(TransactionOptions transactionOptions) {
        if (transactionOptions != null) {
            this.consistencySelector_ = transactionOptions;
            this.consistencySelectorCase_ = 5;
            return;
        }
        throw new NullPointerException();
    }

    private void setNewTransaction(com.google.firestore.v1.TransactionOptions.Builder builder) {
        this.consistencySelector_ = builder.build();
        this.consistencySelectorCase_ = 5;
    }

    private void mergeNewTransaction(TransactionOptions transactionOptions) {
        if (this.consistencySelectorCase_ != 5 || this.consistencySelector_ == TransactionOptions.getDefaultInstance()) {
            this.consistencySelector_ = transactionOptions;
        } else {
            this.consistencySelector_ = ((com.google.firestore.v1.TransactionOptions.Builder) TransactionOptions.newBuilder((TransactionOptions) this.consistencySelector_).mergeFrom(transactionOptions)).buildPartial();
        }
        this.consistencySelectorCase_ = 5;
    }

    private void clearNewTransaction() {
        if (this.consistencySelectorCase_ == 5) {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }
    }

    public Timestamp getReadTime() {
        if (this.consistencySelectorCase_ == 7) {
            return (Timestamp) this.consistencySelector_;
        }
        return Timestamp.getDefaultInstance();
    }

    private void setReadTime(Timestamp timestamp) {
        if (timestamp != null) {
            this.consistencySelector_ = timestamp;
            this.consistencySelectorCase_ = 7;
            return;
        }
        throw new NullPointerException();
    }

    private void setReadTime(com.google.protobuf.Timestamp.Builder builder) {
        this.consistencySelector_ = builder.build();
        this.consistencySelectorCase_ = 7;
    }

    private void mergeReadTime(Timestamp timestamp) {
        if (this.consistencySelectorCase_ != 7 || this.consistencySelector_ == Timestamp.getDefaultInstance()) {
            this.consistencySelector_ = timestamp;
        } else {
            this.consistencySelector_ = ((com.google.protobuf.Timestamp.Builder) Timestamp.newBuilder((Timestamp) this.consistencySelector_).mergeFrom(timestamp)).buildPartial();
        }
        this.consistencySelectorCase_ = 7;
    }

    private void clearReadTime() {
        if (this.consistencySelectorCase_ == 7) {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (!this.database_.isEmpty()) {
            codedOutputStream.writeString(1, getDatabase());
        }
        for (int i = 0; i < this.documents_.size(); i++) {
            codedOutputStream.writeString(2, (String) this.documents_.get(i));
        }
        if (this.mask_ != null) {
            codedOutputStream.writeMessage(3, getMask());
        }
        if (this.consistencySelectorCase_ == 4) {
            codedOutputStream.writeBytes(4, (ByteString) this.consistencySelector_);
        }
        if (this.consistencySelectorCase_ == 5) {
            codedOutputStream.writeMessage(5, (TransactionOptions) this.consistencySelector_);
        }
        if (this.consistencySelectorCase_ == 7) {
            codedOutputStream.writeMessage(7, (Timestamp) this.consistencySelector_);
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        i = !this.database_.isEmpty() ? CodedOutputStream.computeStringSize(1, getDatabase()) + 0 : 0;
        int i3 = 0;
        while (i2 < this.documents_.size()) {
            i3 += CodedOutputStream.computeStringSizeNoTag((String) this.documents_.get(i2));
            i2++;
        }
        i = (i + i3) + (getDocumentsList().size() * 1);
        if (this.mask_ != null) {
            i += CodedOutputStream.computeMessageSize(3, getMask());
        }
        if (this.consistencySelectorCase_ == 4) {
            i += CodedOutputStream.computeBytesSize(4, (ByteString) this.consistencySelector_);
        }
        if (this.consistencySelectorCase_ == 5) {
            i += CodedOutputStream.computeMessageSize(5, (TransactionOptions) this.consistencySelector_);
        }
        if (this.consistencySelectorCase_ == 7) {
            i += CodedOutputStream.computeMessageSize(7, (Timestamp) this.consistencySelector_);
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static BatchGetDocumentsRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static BatchGetDocumentsRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static BatchGetDocumentsRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static BatchGetDocumentsRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static BatchGetDocumentsRequest parseFrom(InputStream inputStream) throws IOException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static BatchGetDocumentsRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static BatchGetDocumentsRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static BatchGetDocumentsRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static BatchGetDocumentsRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static BatchGetDocumentsRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(BatchGetDocumentsRequest batchGetDocumentsRequest) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(batchGetDocumentsRequest);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        int i;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new BatchGetDocumentsRequest();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.documents_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                BatchGetDocumentsRequest batchGetDocumentsRequest = (BatchGetDocumentsRequest) obj2;
                this.database_ = visitor.visitString(this.database_.isEmpty() ^ true, this.database_, batchGetDocumentsRequest.database_.isEmpty() ^ true, batchGetDocumentsRequest.database_);
                this.documents_ = visitor.visitList(this.documents_, batchGetDocumentsRequest.documents_);
                this.mask_ = (DocumentMask) visitor.visitMessage(this.mask_, batchGetDocumentsRequest.mask_);
                i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$BatchGetDocumentsRequest$ConsistencySelectorCase[batchGetDocumentsRequest.getConsistencySelectorCase().ordinal()];
                if (i == 1) {
                    if (this.consistencySelectorCase_ == 4) {
                        z = true;
                    }
                    this.consistencySelector_ = visitor.visitOneofByteString(z, this.consistencySelector_, batchGetDocumentsRequest.consistencySelector_);
                } else if (i == 2) {
                    if (this.consistencySelectorCase_ == 5) {
                        z = true;
                    }
                    this.consistencySelector_ = visitor.visitOneofMessage(z, this.consistencySelector_, batchGetDocumentsRequest.consistencySelector_);
                } else if (i == 3) {
                    if (this.consistencySelectorCase_ == 7) {
                        z = true;
                    }
                    this.consistencySelector_ = visitor.visitOneofMessage(z, this.consistencySelector_, batchGetDocumentsRequest.consistencySelector_);
                } else if (i == 4) {
                    if (this.consistencySelectorCase_ != 0) {
                        z = true;
                    }
                    visitor.visitOneofNotSet(z);
                }
                if (visitor == MergeFromVisitor.INSTANCE) {
                    i = batchGetDocumentsRequest.consistencySelectorCase_;
                    if (i != 0) {
                        this.consistencySelectorCase_ = i;
                    }
                    this.bitField0_ |= batchGetDocumentsRequest.bitField0_;
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
                                this.database_ = codedInputStream.readStringRequireUtf8();
                            } else if (i == 18) {
                                String readStringRequireUtf8 = codedInputStream.readStringRequireUtf8();
                                if (!this.documents_.isModifiable()) {
                                    this.documents_ = GeneratedMessageLite.mutableCopy(this.documents_);
                                }
                                this.documents_.add(readStringRequireUtf8);
                            } else if (i == 26) {
                                com.google.firestore.v1.DocumentMask.Builder builder = this.mask_ != null ? (com.google.firestore.v1.DocumentMask.Builder) this.mask_.toBuilder() : null;
                                this.mask_ = (DocumentMask) codedInputStream.readMessage(DocumentMask.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom(this.mask_);
                                    this.mask_ = (DocumentMask) builder.buildPartial();
                                }
                            } else if (i == 34) {
                                this.consistencySelectorCase_ = 4;
                                this.consistencySelector_ = codedInputStream.readBytes();
                            } else if (i == 42) {
                                com.google.firestore.v1.TransactionOptions.Builder builder2 = this.consistencySelectorCase_ == 5 ? (com.google.firestore.v1.TransactionOptions.Builder) ((TransactionOptions) this.consistencySelector_).toBuilder() : null;
                                this.consistencySelector_ = codedInputStream.readMessage(TransactionOptions.parser(), extensionRegistryLite);
                                if (builder2 != null) {
                                    builder2.mergeFrom((TransactionOptions) this.consistencySelector_);
                                    this.consistencySelector_ = builder2.buildPartial();
                                }
                                this.consistencySelectorCase_ = 5;
                            } else if (i == 58) {
                                com.google.protobuf.Timestamp.Builder builder3 = this.consistencySelectorCase_ == 7 ? (com.google.protobuf.Timestamp.Builder) ((Timestamp) this.consistencySelector_).toBuilder() : null;
                                this.consistencySelector_ = codedInputStream.readMessage(Timestamp.parser(), extensionRegistryLite);
                                if (builder3 != null) {
                                    builder3.mergeFrom((Timestamp) this.consistencySelector_);
                                    this.consistencySelector_ = builder3.buildPartial();
                                }
                                this.consistencySelectorCase_ = 7;
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
                    synchronized (BatchGetDocumentsRequest.class) {
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

    public static BatchGetDocumentsRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<BatchGetDocumentsRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
