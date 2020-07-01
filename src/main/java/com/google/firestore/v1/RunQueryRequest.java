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
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class RunQueryRequest extends GeneratedMessageLite<RunQueryRequest, Builder> implements RunQueryRequestOrBuilder {
    private static final RunQueryRequest DEFAULT_INSTANCE = new RunQueryRequest();
    public static final int NEW_TRANSACTION_FIELD_NUMBER = 6;
    public static final int PARENT_FIELD_NUMBER = 1;
    private static volatile Parser<RunQueryRequest> PARSER = null;
    public static final int READ_TIME_FIELD_NUMBER = 7;
    public static final int STRUCTURED_QUERY_FIELD_NUMBER = 2;
    public static final int TRANSACTION_FIELD_NUMBER = 5;
    private int consistencySelectorCase_ = 0;
    private Object consistencySelector_;
    private String parent_ = "";
    private int queryTypeCase_ = 0;
    private Object queryType_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firestore.v1.RunQueryRequest$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$RunQueryRequest$ConsistencySelectorCase = new int[ConsistencySelectorCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$RunQueryRequest$QueryTypeCase = new int[QueryTypeCase.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:19:?, code:
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 8;
     */
        /* JADX WARNING: Missing block: B:29:0x0093, code:
            $SwitchMap$com$google$firestore$v1$RunQueryRequest$QueryTypeCase = new int[com.google.firestore.v1.RunQueryRequest.QueryTypeCase.values().length];
     */
        /* JADX WARNING: Missing block: B:31:?, code:
            $SwitchMap$com$google$firestore$v1$RunQueryRequest$QueryTypeCase[com.google.firestore.v1.RunQueryRequest.QueryTypeCase.STRUCTURED_QUERY.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:33:?, code:
            $SwitchMap$com$google$firestore$v1$RunQueryRequest$QueryTypeCase[com.google.firestore.v1.RunQueryRequest.QueryTypeCase.QUERYTYPE_NOT_SET.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:34:0x00b0, code:
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
            r4 = com.google.firestore.v1.RunQueryRequest.ConsistencySelectorCase.values();
            r4 = r4.length;
            r4 = new int[r4];
            $SwitchMap$com$google$firestore$v1$RunQueryRequest$ConsistencySelectorCase = r4;
            r4 = $SwitchMap$com$google$firestore$v1$RunQueryRequest$ConsistencySelectorCase;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r5 = com.google.firestore.v1.RunQueryRequest.ConsistencySelectorCase.TRANSACTION;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0075 }
            r4[r5] = r0;	 Catch:{ NoSuchFieldError -> 0x0075 }
        L_0x0075:
            r4 = $SwitchMap$com$google$firestore$v1$RunQueryRequest$ConsistencySelectorCase;	 Catch:{ NoSuchFieldError -> 0x007f }
            r5 = com.google.firestore.v1.RunQueryRequest.ConsistencySelectorCase.NEW_TRANSACTION;	 Catch:{ NoSuchFieldError -> 0x007f }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x007f }
            r4[r5] = r1;	 Catch:{ NoSuchFieldError -> 0x007f }
        L_0x007f:
            r4 = $SwitchMap$com$google$firestore$v1$RunQueryRequest$ConsistencySelectorCase;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r5 = com.google.firestore.v1.RunQueryRequest.ConsistencySelectorCase.READ_TIME;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0089 }
            r4[r5] = r2;	 Catch:{ NoSuchFieldError -> 0x0089 }
        L_0x0089:
            r2 = $SwitchMap$com$google$firestore$v1$RunQueryRequest$ConsistencySelectorCase;	 Catch:{ NoSuchFieldError -> 0x0093 }
            r4 = com.google.firestore.v1.RunQueryRequest.ConsistencySelectorCase.CONSISTENCYSELECTOR_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x0093 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0093 }
            r2[r4] = r3;	 Catch:{ NoSuchFieldError -> 0x0093 }
        L_0x0093:
            r2 = com.google.firestore.v1.RunQueryRequest.QueryTypeCase.values();
            r2 = r2.length;
            r2 = new int[r2];
            $SwitchMap$com$google$firestore$v1$RunQueryRequest$QueryTypeCase = r2;
            r2 = $SwitchMap$com$google$firestore$v1$RunQueryRequest$QueryTypeCase;	 Catch:{ NoSuchFieldError -> 0x00a6 }
            r3 = com.google.firestore.v1.RunQueryRequest.QueryTypeCase.STRUCTURED_QUERY;	 Catch:{ NoSuchFieldError -> 0x00a6 }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x00a6 }
            r2[r3] = r0;	 Catch:{ NoSuchFieldError -> 0x00a6 }
        L_0x00a6:
            r0 = $SwitchMap$com$google$firestore$v1$RunQueryRequest$QueryTypeCase;	 Catch:{ NoSuchFieldError -> 0x00b0 }
            r2 = com.google.firestore.v1.RunQueryRequest.QueryTypeCase.QUERYTYPE_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x00b0 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x00b0 }
            r0[r2] = r1;	 Catch:{ NoSuchFieldError -> 0x00b0 }
        L_0x00b0:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firestore.v1.RunQueryRequest.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum ConsistencySelectorCase implements EnumLite {
        TRANSACTION(5),
        NEW_TRANSACTION(6),
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
            if (i == 5) {
                return TRANSACTION;
            }
            if (i != 6) {
                return i != 7 ? null : READ_TIME;
            } else {
                return NEW_TRANSACTION;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum QueryTypeCase implements EnumLite {
        STRUCTURED_QUERY(2),
        QUERYTYPE_NOT_SET(0);
        
        private final int value;

        private QueryTypeCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static QueryTypeCase valueOf(int i) {
            return forNumber(i);
        }

        public static QueryTypeCase forNumber(int i) {
            if (i != 0) {
                return i != 2 ? null : STRUCTURED_QUERY;
            } else {
                return QUERYTYPE_NOT_SET;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<RunQueryRequest, Builder> implements RunQueryRequestOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(RunQueryRequest.DEFAULT_INSTANCE);
        }

        public QueryTypeCase getQueryTypeCase() {
            return ((RunQueryRequest) this.instance).getQueryTypeCase();
        }

        public Builder clearQueryType() {
            copyOnWrite();
            ((RunQueryRequest) this.instance).clearQueryType();
            return this;
        }

        public ConsistencySelectorCase getConsistencySelectorCase() {
            return ((RunQueryRequest) this.instance).getConsistencySelectorCase();
        }

        public Builder clearConsistencySelector() {
            copyOnWrite();
            ((RunQueryRequest) this.instance).clearConsistencySelector();
            return this;
        }

        public String getParent() {
            return ((RunQueryRequest) this.instance).getParent();
        }

        public ByteString getParentBytes() {
            return ((RunQueryRequest) this.instance).getParentBytes();
        }

        public Builder setParent(String str) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).setParent(str);
            return this;
        }

        public Builder clearParent() {
            copyOnWrite();
            ((RunQueryRequest) this.instance).clearParent();
            return this;
        }

        public Builder setParentBytes(ByteString byteString) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).setParentBytes(byteString);
            return this;
        }

        public StructuredQuery getStructuredQuery() {
            return ((RunQueryRequest) this.instance).getStructuredQuery();
        }

        public Builder setStructuredQuery(StructuredQuery structuredQuery) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).setStructuredQuery(structuredQuery);
            return this;
        }

        public Builder setStructuredQuery(com.google.firestore.v1.StructuredQuery.Builder builder) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).setStructuredQuery(builder);
            return this;
        }

        public Builder mergeStructuredQuery(StructuredQuery structuredQuery) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).mergeStructuredQuery(structuredQuery);
            return this;
        }

        public Builder clearStructuredQuery() {
            copyOnWrite();
            ((RunQueryRequest) this.instance).clearStructuredQuery();
            return this;
        }

        public ByteString getTransaction() {
            return ((RunQueryRequest) this.instance).getTransaction();
        }

        public Builder setTransaction(ByteString byteString) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).setTransaction(byteString);
            return this;
        }

        public Builder clearTransaction() {
            copyOnWrite();
            ((RunQueryRequest) this.instance).clearTransaction();
            return this;
        }

        public TransactionOptions getNewTransaction() {
            return ((RunQueryRequest) this.instance).getNewTransaction();
        }

        public Builder setNewTransaction(TransactionOptions transactionOptions) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).setNewTransaction(transactionOptions);
            return this;
        }

        public Builder setNewTransaction(com.google.firestore.v1.TransactionOptions.Builder builder) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).setNewTransaction(builder);
            return this;
        }

        public Builder mergeNewTransaction(TransactionOptions transactionOptions) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).mergeNewTransaction(transactionOptions);
            return this;
        }

        public Builder clearNewTransaction() {
            copyOnWrite();
            ((RunQueryRequest) this.instance).clearNewTransaction();
            return this;
        }

        public Timestamp getReadTime() {
            return ((RunQueryRequest) this.instance).getReadTime();
        }

        public Builder setReadTime(Timestamp timestamp) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).setReadTime(timestamp);
            return this;
        }

        public Builder setReadTime(com.google.protobuf.Timestamp.Builder builder) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).setReadTime(builder);
            return this;
        }

        public Builder mergeReadTime(Timestamp timestamp) {
            copyOnWrite();
            ((RunQueryRequest) this.instance).mergeReadTime(timestamp);
            return this;
        }

        public Builder clearReadTime() {
            copyOnWrite();
            ((RunQueryRequest) this.instance).clearReadTime();
            return this;
        }
    }

    private RunQueryRequest() {
    }

    public QueryTypeCase getQueryTypeCase() {
        return QueryTypeCase.forNumber(this.queryTypeCase_);
    }

    private void clearQueryType() {
        this.queryTypeCase_ = 0;
        this.queryType_ = null;
    }

    public ConsistencySelectorCase getConsistencySelectorCase() {
        return ConsistencySelectorCase.forNumber(this.consistencySelectorCase_);
    }

    private void clearConsistencySelector() {
        this.consistencySelectorCase_ = 0;
        this.consistencySelector_ = null;
    }

    public String getParent() {
        return this.parent_;
    }

    public ByteString getParentBytes() {
        return ByteString.copyFromUtf8(this.parent_);
    }

    private void setParent(String str) {
        if (str != null) {
            this.parent_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearParent() {
        this.parent_ = getDefaultInstance().getParent();
    }

    private void setParentBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.parent_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public StructuredQuery getStructuredQuery() {
        if (this.queryTypeCase_ == 2) {
            return (StructuredQuery) this.queryType_;
        }
        return StructuredQuery.getDefaultInstance();
    }

    private void setStructuredQuery(StructuredQuery structuredQuery) {
        if (structuredQuery != null) {
            this.queryType_ = structuredQuery;
            this.queryTypeCase_ = 2;
            return;
        }
        throw new NullPointerException();
    }

    private void setStructuredQuery(com.google.firestore.v1.StructuredQuery.Builder builder) {
        this.queryType_ = builder.build();
        this.queryTypeCase_ = 2;
    }

    private void mergeStructuredQuery(StructuredQuery structuredQuery) {
        if (this.queryTypeCase_ != 2 || this.queryType_ == StructuredQuery.getDefaultInstance()) {
            this.queryType_ = structuredQuery;
        } else {
            this.queryType_ = ((com.google.firestore.v1.StructuredQuery.Builder) StructuredQuery.newBuilder((StructuredQuery) this.queryType_).mergeFrom(structuredQuery)).buildPartial();
        }
        this.queryTypeCase_ = 2;
    }

    private void clearStructuredQuery() {
        if (this.queryTypeCase_ == 2) {
            this.queryTypeCase_ = 0;
            this.queryType_ = null;
        }
    }

    public ByteString getTransaction() {
        if (this.consistencySelectorCase_ == 5) {
            return (ByteString) this.consistencySelector_;
        }
        return ByteString.EMPTY;
    }

    private void setTransaction(ByteString byteString) {
        if (byteString != null) {
            this.consistencySelectorCase_ = 5;
            this.consistencySelector_ = byteString;
            return;
        }
        throw new NullPointerException();
    }

    private void clearTransaction() {
        if (this.consistencySelectorCase_ == 5) {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }
    }

    public TransactionOptions getNewTransaction() {
        if (this.consistencySelectorCase_ == 6) {
            return (TransactionOptions) this.consistencySelector_;
        }
        return TransactionOptions.getDefaultInstance();
    }

    private void setNewTransaction(TransactionOptions transactionOptions) {
        if (transactionOptions != null) {
            this.consistencySelector_ = transactionOptions;
            this.consistencySelectorCase_ = 6;
            return;
        }
        throw new NullPointerException();
    }

    private void setNewTransaction(com.google.firestore.v1.TransactionOptions.Builder builder) {
        this.consistencySelector_ = builder.build();
        this.consistencySelectorCase_ = 6;
    }

    private void mergeNewTransaction(TransactionOptions transactionOptions) {
        if (this.consistencySelectorCase_ != 6 || this.consistencySelector_ == TransactionOptions.getDefaultInstance()) {
            this.consistencySelector_ = transactionOptions;
        } else {
            this.consistencySelector_ = ((com.google.firestore.v1.TransactionOptions.Builder) TransactionOptions.newBuilder((TransactionOptions) this.consistencySelector_).mergeFrom(transactionOptions)).buildPartial();
        }
        this.consistencySelectorCase_ = 6;
    }

    private void clearNewTransaction() {
        if (this.consistencySelectorCase_ == 6) {
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
        if (!this.parent_.isEmpty()) {
            codedOutputStream.writeString(1, getParent());
        }
        if (this.queryTypeCase_ == 2) {
            codedOutputStream.writeMessage(2, (StructuredQuery) this.queryType_);
        }
        if (this.consistencySelectorCase_ == 5) {
            codedOutputStream.writeBytes(5, (ByteString) this.consistencySelector_);
        }
        if (this.consistencySelectorCase_ == 6) {
            codedOutputStream.writeMessage(6, (TransactionOptions) this.consistencySelector_);
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
        i = 0;
        if (!this.parent_.isEmpty()) {
            i = 0 + CodedOutputStream.computeStringSize(1, getParent());
        }
        if (this.queryTypeCase_ == 2) {
            i += CodedOutputStream.computeMessageSize(2, (StructuredQuery) this.queryType_);
        }
        if (this.consistencySelectorCase_ == 5) {
            i += CodedOutputStream.computeBytesSize(5, (ByteString) this.consistencySelector_);
        }
        if (this.consistencySelectorCase_ == 6) {
            i += CodedOutputStream.computeMessageSize(6, (TransactionOptions) this.consistencySelector_);
        }
        if (this.consistencySelectorCase_ == 7) {
            i += CodedOutputStream.computeMessageSize(7, (Timestamp) this.consistencySelector_);
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static RunQueryRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (RunQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static RunQueryRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (RunQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static RunQueryRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (RunQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static RunQueryRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (RunQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static RunQueryRequest parseFrom(InputStream inputStream) throws IOException {
        return (RunQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static RunQueryRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (RunQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static RunQueryRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (RunQueryRequest) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static RunQueryRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (RunQueryRequest) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static RunQueryRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (RunQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static RunQueryRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (RunQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(RunQueryRequest runQueryRequest) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(runQueryRequest);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        int i;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new RunQueryRequest();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                RunQueryRequest runQueryRequest = (RunQueryRequest) obj2;
                this.parent_ = visitor.visitString(this.parent_.isEmpty() ^ true, this.parent_, runQueryRequest.parent_.isEmpty() ^ true, runQueryRequest.parent_);
                i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$RunQueryRequest$QueryTypeCase[runQueryRequest.getQueryTypeCase().ordinal()];
                if (i == 1) {
                    this.queryType_ = visitor.visitOneofMessage(this.queryTypeCase_ == 2, this.queryType_, runQueryRequest.queryType_);
                } else if (i == 2) {
                    visitor.visitOneofNotSet(this.queryTypeCase_ != 0);
                }
                i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$RunQueryRequest$ConsistencySelectorCase[runQueryRequest.getConsistencySelectorCase().ordinal()];
                if (i == 1) {
                    if (this.consistencySelectorCase_ == 5) {
                        z = true;
                    }
                    this.consistencySelector_ = visitor.visitOneofByteString(z, this.consistencySelector_, runQueryRequest.consistencySelector_);
                } else if (i == 2) {
                    if (this.consistencySelectorCase_ == 6) {
                        z = true;
                    }
                    this.consistencySelector_ = visitor.visitOneofMessage(z, this.consistencySelector_, runQueryRequest.consistencySelector_);
                } else if (i == 3) {
                    if (this.consistencySelectorCase_ == 7) {
                        z = true;
                    }
                    this.consistencySelector_ = visitor.visitOneofMessage(z, this.consistencySelector_, runQueryRequest.consistencySelector_);
                } else if (i == 4) {
                    if (this.consistencySelectorCase_ != 0) {
                        z = true;
                    }
                    visitor.visitOneofNotSet(z);
                }
                if (visitor == MergeFromVisitor.INSTANCE) {
                    i = runQueryRequest.queryTypeCase_;
                    if (i != 0) {
                        this.queryTypeCase_ = i;
                    }
                    i = runQueryRequest.consistencySelectorCase_;
                    if (i != 0) {
                        this.consistencySelectorCase_ = i;
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
                                this.parent_ = codedInputStream.readStringRequireUtf8();
                            } else if (i == 18) {
                                com.google.firestore.v1.StructuredQuery.Builder builder = this.queryTypeCase_ == 2 ? (com.google.firestore.v1.StructuredQuery.Builder) ((StructuredQuery) this.queryType_).toBuilder() : null;
                                this.queryType_ = codedInputStream.readMessage(StructuredQuery.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom((StructuredQuery) this.queryType_);
                                    this.queryType_ = builder.buildPartial();
                                }
                                this.queryTypeCase_ = 2;
                            } else if (i == 42) {
                                this.consistencySelectorCase_ = 5;
                                this.consistencySelector_ = codedInputStream.readBytes();
                            } else if (i == 50) {
                                com.google.firestore.v1.TransactionOptions.Builder builder2 = this.consistencySelectorCase_ == 6 ? (com.google.firestore.v1.TransactionOptions.Builder) ((TransactionOptions) this.consistencySelector_).toBuilder() : null;
                                this.consistencySelector_ = codedInputStream.readMessage(TransactionOptions.parser(), extensionRegistryLite);
                                if (builder2 != null) {
                                    builder2.mergeFrom((TransactionOptions) this.consistencySelector_);
                                    this.consistencySelector_ = builder2.buildPartial();
                                }
                                this.consistencySelectorCase_ = 6;
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
                    synchronized (RunQueryRequest.class) {
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

    public static RunQueryRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<RunQueryRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
