package com.google.firestore.v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class TransactionOptions extends GeneratedMessageLite<TransactionOptions, Builder> implements TransactionOptionsOrBuilder {
    private static final TransactionOptions DEFAULT_INSTANCE = new TransactionOptions();
    private static volatile Parser<TransactionOptions> PARSER = null;
    public static final int READ_ONLY_FIELD_NUMBER = 2;
    public static final int READ_WRITE_FIELD_NUMBER = 3;
    private int modeCase_ = 0;
    private Object mode_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firestore.v1.TransactionOptions$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$TransactionOptions$ModeCase = new int[ModeCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$TransactionOptions$ReadOnly$ConsistencySelectorCase = new int[ConsistencySelectorCase.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:13:?, code:
            $SwitchMap$com$google$firestore$v1$TransactionOptions$ReadOnly$ConsistencySelectorCase[com.google.firestore.v1.TransactionOptions.ReadOnly.ConsistencySelectorCase.CONSISTENCYSELECTOR_NOT_SET.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:30:?, code:
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 8;
     */
        static {
            /*
            r0 = com.google.firestore.v1.TransactionOptions.ModeCase.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firestore$v1$TransactionOptions$ModeCase = r0;
            r0 = 1;
            r1 = $SwitchMap$com$google$firestore$v1$TransactionOptions$ModeCase;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = com.google.firestore.v1.TransactionOptions.ModeCase.READ_ONLY;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = 2;
            r2 = $SwitchMap$com$google$firestore$v1$TransactionOptions$ModeCase;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = com.google.firestore.v1.TransactionOptions.ModeCase.READ_WRITE;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r2 = 3;
            r3 = $SwitchMap$com$google$firestore$v1$TransactionOptions$ModeCase;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = com.google.firestore.v1.TransactionOptions.ModeCase.MODE_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r3[r4] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r3 = com.google.firestore.v1.TransactionOptions.ReadOnly.ConsistencySelectorCase.values();
            r3 = r3.length;
            r3 = new int[r3];
            $SwitchMap$com$google$firestore$v1$TransactionOptions$ReadOnly$ConsistencySelectorCase = r3;
            r3 = $SwitchMap$com$google$firestore$v1$TransactionOptions$ReadOnly$ConsistencySelectorCase;	 Catch:{ NoSuchFieldError -> 0x003d }
            r4 = com.google.firestore.v1.TransactionOptions.ReadOnly.ConsistencySelectorCase.READ_TIME;	 Catch:{ NoSuchFieldError -> 0x003d }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x003d }
            r3[r4] = r0;	 Catch:{ NoSuchFieldError -> 0x003d }
        L_0x003d:
            r3 = $SwitchMap$com$google$firestore$v1$TransactionOptions$ReadOnly$ConsistencySelectorCase;	 Catch:{ NoSuchFieldError -> 0x0047 }
            r4 = com.google.firestore.v1.TransactionOptions.ReadOnly.ConsistencySelectorCase.CONSISTENCYSELECTOR_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x0047 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0047 }
            r3[r4] = r1;	 Catch:{ NoSuchFieldError -> 0x0047 }
        L_0x0047:
            r3 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.values();
            r3 = r3.length;
            r3 = new int[r3];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = r3;
            r3 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x005a }
            r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE;	 Catch:{ NoSuchFieldError -> 0x005a }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x005a }
            r3[r4] = r0;	 Catch:{ NoSuchFieldError -> 0x005a }
        L_0x005a:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0064 }
            r3 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.IS_INITIALIZED;	 Catch:{ NoSuchFieldError -> 0x0064 }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x0064 }
            r0[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x0064 }
        L_0x0064:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x006e }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.MAKE_IMMUTABLE;	 Catch:{ NoSuchFieldError -> 0x006e }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x006e }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x006e }
        L_0x006e:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0079 }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_BUILDER;	 Catch:{ NoSuchFieldError -> 0x0079 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0079 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0079 }
        L_0x0079:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0084 }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.VISIT;	 Catch:{ NoSuchFieldError -> 0x0084 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0084 }
            r2 = 5;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0084 }
        L_0x0084:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x008f }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.MERGE_FROM_STREAM;	 Catch:{ NoSuchFieldError -> 0x008f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x008f }
            r2 = 6;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x008f }
        L_0x008f:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x009a }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE;	 Catch:{ NoSuchFieldError -> 0x009a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x009a }
            r2 = 7;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x009a }
        L_0x009a:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x00a6 }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_PARSER;	 Catch:{ NoSuchFieldError -> 0x00a6 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x00a6 }
            r2 = 8;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00a6 }
        L_0x00a6:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firestore.v1.TransactionOptions.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum ModeCase implements EnumLite {
        READ_ONLY(2),
        READ_WRITE(3),
        MODE_NOT_SET(0);
        
        private final int value;

        private ModeCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static ModeCase valueOf(int i) {
            return forNumber(i);
        }

        public static ModeCase forNumber(int i) {
            if (i == 0) {
                return MODE_NOT_SET;
            }
            if (i != 2) {
                return i != 3 ? null : READ_WRITE;
            } else {
                return READ_ONLY;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface ReadOnlyOrBuilder extends MessageLiteOrBuilder {
        ConsistencySelectorCase getConsistencySelectorCase();

        Timestamp getReadTime();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface ReadWriteOrBuilder extends MessageLiteOrBuilder {
        ByteString getRetryTransaction();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<TransactionOptions, Builder> implements TransactionOptionsOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(TransactionOptions.DEFAULT_INSTANCE);
        }

        public ModeCase getModeCase() {
            return ((TransactionOptions) this.instance).getModeCase();
        }

        public Builder clearMode() {
            copyOnWrite();
            ((TransactionOptions) this.instance).clearMode();
            return this;
        }

        public ReadOnly getReadOnly() {
            return ((TransactionOptions) this.instance).getReadOnly();
        }

        public Builder setReadOnly(ReadOnly readOnly) {
            copyOnWrite();
            ((TransactionOptions) this.instance).setReadOnly(readOnly);
            return this;
        }

        public Builder setReadOnly(Builder builder) {
            copyOnWrite();
            ((TransactionOptions) this.instance).setReadOnly(builder);
            return this;
        }

        public Builder mergeReadOnly(ReadOnly readOnly) {
            copyOnWrite();
            ((TransactionOptions) this.instance).mergeReadOnly(readOnly);
            return this;
        }

        public Builder clearReadOnly() {
            copyOnWrite();
            ((TransactionOptions) this.instance).clearReadOnly();
            return this;
        }

        public ReadWrite getReadWrite() {
            return ((TransactionOptions) this.instance).getReadWrite();
        }

        public Builder setReadWrite(ReadWrite readWrite) {
            copyOnWrite();
            ((TransactionOptions) this.instance).setReadWrite(readWrite);
            return this;
        }

        public Builder setReadWrite(Builder builder) {
            copyOnWrite();
            ((TransactionOptions) this.instance).setReadWrite(builder);
            return this;
        }

        public Builder mergeReadWrite(ReadWrite readWrite) {
            copyOnWrite();
            ((TransactionOptions) this.instance).mergeReadWrite(readWrite);
            return this;
        }

        public Builder clearReadWrite() {
            copyOnWrite();
            ((TransactionOptions) this.instance).clearReadWrite();
            return this;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class ReadOnly extends GeneratedMessageLite<ReadOnly, Builder> implements ReadOnlyOrBuilder {
        private static final ReadOnly DEFAULT_INSTANCE = new ReadOnly();
        private static volatile Parser<ReadOnly> PARSER = null;
        public static final int READ_TIME_FIELD_NUMBER = 2;
        private int consistencySelectorCase_ = 0;
        private Object consistencySelector_;

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public enum ConsistencySelectorCase implements EnumLite {
            READ_TIME(2),
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
                if (i != 0) {
                    return i != 2 ? null : READ_TIME;
                } else {
                    return CONSISTENCYSELECTOR_NOT_SET;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<ReadOnly, Builder> implements ReadOnlyOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(ReadOnly.DEFAULT_INSTANCE);
            }

            public ConsistencySelectorCase getConsistencySelectorCase() {
                return ((ReadOnly) this.instance).getConsistencySelectorCase();
            }

            public Builder clearConsistencySelector() {
                copyOnWrite();
                ((ReadOnly) this.instance).clearConsistencySelector();
                return this;
            }

            public Timestamp getReadTime() {
                return ((ReadOnly) this.instance).getReadTime();
            }

            public Builder setReadTime(Timestamp timestamp) {
                copyOnWrite();
                ((ReadOnly) this.instance).setReadTime(timestamp);
                return this;
            }

            public Builder setReadTime(com.google.protobuf.Timestamp.Builder builder) {
                copyOnWrite();
                ((ReadOnly) this.instance).setReadTime(builder);
                return this;
            }

            public Builder mergeReadTime(Timestamp timestamp) {
                copyOnWrite();
                ((ReadOnly) this.instance).mergeReadTime(timestamp);
                return this;
            }

            public Builder clearReadTime() {
                copyOnWrite();
                ((ReadOnly) this.instance).clearReadTime();
                return this;
            }
        }

        private ReadOnly() {
        }

        public ConsistencySelectorCase getConsistencySelectorCase() {
            return ConsistencySelectorCase.forNumber(this.consistencySelectorCase_);
        }

        private void clearConsistencySelector() {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }

        public Timestamp getReadTime() {
            if (this.consistencySelectorCase_ == 2) {
                return (Timestamp) this.consistencySelector_;
            }
            return Timestamp.getDefaultInstance();
        }

        private void setReadTime(Timestamp timestamp) {
            if (timestamp != null) {
                this.consistencySelector_ = timestamp;
                this.consistencySelectorCase_ = 2;
                return;
            }
            throw new NullPointerException();
        }

        private void setReadTime(com.google.protobuf.Timestamp.Builder builder) {
            this.consistencySelector_ = builder.build();
            this.consistencySelectorCase_ = 2;
        }

        private void mergeReadTime(Timestamp timestamp) {
            if (this.consistencySelectorCase_ != 2 || this.consistencySelector_ == Timestamp.getDefaultInstance()) {
                this.consistencySelector_ = timestamp;
            } else {
                this.consistencySelector_ = ((com.google.protobuf.Timestamp.Builder) Timestamp.newBuilder((Timestamp) this.consistencySelector_).mergeFrom(timestamp)).buildPartial();
            }
            this.consistencySelectorCase_ = 2;
        }

        private void clearReadTime() {
            if (this.consistencySelectorCase_ == 2) {
                this.consistencySelectorCase_ = 0;
                this.consistencySelector_ = null;
            }
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (this.consistencySelectorCase_ == 2) {
                codedOutputStream.writeMessage(2, (Timestamp) this.consistencySelector_);
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            i = 0;
            if (this.consistencySelectorCase_ == 2) {
                i = 0 + CodedOutputStream.computeMessageSize(2, (Timestamp) this.consistencySelector_);
            }
            this.memoizedSerializedSize = i;
            return i;
        }

        public static ReadOnly parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReadOnly) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static ReadOnly parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadOnly) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReadOnly parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReadOnly) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static ReadOnly parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadOnly) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ReadOnly parseFrom(InputStream inputStream) throws IOException {
            return (ReadOnly) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadOnly parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadOnly) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadOnly parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReadOnly) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadOnly parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadOnly) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadOnly parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReadOnly) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReadOnly parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadOnly) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ReadOnly readOnly) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(readOnly);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            boolean z = false;
            int i;
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ReadOnly();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    Visitor visitor = (Visitor) obj;
                    ReadOnly readOnly = (ReadOnly) obj2;
                    i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$TransactionOptions$ReadOnly$ConsistencySelectorCase[readOnly.getConsistencySelectorCase().ordinal()];
                    if (i == 1) {
                        if (this.consistencySelectorCase_ == 2) {
                            z = true;
                        }
                        this.consistencySelector_ = visitor.visitOneofMessage(z, this.consistencySelector_, readOnly.consistencySelector_);
                    } else if (i == 2) {
                        if (this.consistencySelectorCase_ != 0) {
                            z = true;
                        }
                        visitor.visitOneofNotSet(z);
                    }
                    if (visitor == MergeFromVisitor.INSTANCE) {
                        i = readOnly.consistencySelectorCase_;
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
                                if (i == 18) {
                                    com.google.protobuf.Timestamp.Builder builder = this.consistencySelectorCase_ == 2 ? (com.google.protobuf.Timestamp.Builder) ((Timestamp) this.consistencySelector_).toBuilder() : null;
                                    this.consistencySelector_ = codedInputStream.readMessage(Timestamp.parser(), extensionRegistryLite);
                                    if (builder != null) {
                                        builder.mergeFrom((Timestamp) this.consistencySelector_);
                                        this.consistencySelector_ = builder.buildPartial();
                                    }
                                    this.consistencySelectorCase_ = 2;
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
                        synchronized (ReadOnly.class) {
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

        public static ReadOnly getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ReadOnly> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class ReadWrite extends GeneratedMessageLite<ReadWrite, Builder> implements ReadWriteOrBuilder {
        private static final ReadWrite DEFAULT_INSTANCE = new ReadWrite();
        private static volatile Parser<ReadWrite> PARSER = null;
        public static final int RETRY_TRANSACTION_FIELD_NUMBER = 1;
        private ByteString retryTransaction_ = ByteString.EMPTY;

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<ReadWrite, Builder> implements ReadWriteOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(ReadWrite.DEFAULT_INSTANCE);
            }

            public ByteString getRetryTransaction() {
                return ((ReadWrite) this.instance).getRetryTransaction();
            }

            public Builder setRetryTransaction(ByteString byteString) {
                copyOnWrite();
                ((ReadWrite) this.instance).setRetryTransaction(byteString);
                return this;
            }

            public Builder clearRetryTransaction() {
                copyOnWrite();
                ((ReadWrite) this.instance).clearRetryTransaction();
                return this;
            }
        }

        private ReadWrite() {
        }

        public ByteString getRetryTransaction() {
            return this.retryTransaction_;
        }

        private void setRetryTransaction(ByteString byteString) {
            if (byteString != null) {
                this.retryTransaction_ = byteString;
                return;
            }
            throw new NullPointerException();
        }

        private void clearRetryTransaction() {
            this.retryTransaction_ = getDefaultInstance().getRetryTransaction();
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!this.retryTransaction_.isEmpty()) {
                codedOutputStream.writeBytes(1, this.retryTransaction_);
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            i = 0;
            if (!this.retryTransaction_.isEmpty()) {
                i = 0 + CodedOutputStream.computeBytesSize(1, this.retryTransaction_);
            }
            this.memoizedSerializedSize = i;
            return i;
        }

        public static ReadWrite parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReadWrite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static ReadWrite parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadWrite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReadWrite parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReadWrite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static ReadWrite parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadWrite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ReadWrite parseFrom(InputStream inputStream) throws IOException {
            return (ReadWrite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadWrite parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadWrite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadWrite parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReadWrite) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadWrite parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadWrite) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadWrite parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReadWrite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReadWrite parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadWrite) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ReadWrite readWrite) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(readWrite);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            boolean z = false;
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ReadWrite();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    Visitor visitor = (Visitor) obj;
                    ReadWrite readWrite = (ReadWrite) obj2;
                    boolean z2 = this.retryTransaction_ != ByteString.EMPTY;
                    ByteString byteString = this.retryTransaction_;
                    if (readWrite.retryTransaction_ != ByteString.EMPTY) {
                        z = true;
                    }
                    this.retryTransaction_ = visitor.visitByteString(z2, byteString, z, readWrite.retryTransaction_);
                    MergeFromVisitor mergeFromVisitor = MergeFromVisitor.INSTANCE;
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    while (!z) {
                        try {
                            int readTag = codedInputStream.readTag();
                            if (readTag != 0) {
                                if (readTag == 10) {
                                    this.retryTransaction_ = codedInputStream.readBytes();
                                } else if (codedInputStream.skipField(readTag)) {
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
                        synchronized (ReadWrite.class) {
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

        public static ReadWrite getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ReadWrite> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    private TransactionOptions() {
    }

    public ModeCase getModeCase() {
        return ModeCase.forNumber(this.modeCase_);
    }

    private void clearMode() {
        this.modeCase_ = 0;
        this.mode_ = null;
    }

    public ReadOnly getReadOnly() {
        if (this.modeCase_ == 2) {
            return (ReadOnly) this.mode_;
        }
        return ReadOnly.getDefaultInstance();
    }

    private void setReadOnly(ReadOnly readOnly) {
        if (readOnly != null) {
            this.mode_ = readOnly;
            this.modeCase_ = 2;
            return;
        }
        throw new NullPointerException();
    }

    private void setReadOnly(Builder builder) {
        this.mode_ = builder.build();
        this.modeCase_ = 2;
    }

    private void mergeReadOnly(ReadOnly readOnly) {
        if (this.modeCase_ != 2 || this.mode_ == ReadOnly.getDefaultInstance()) {
            this.mode_ = readOnly;
        } else {
            this.mode_ = ((Builder) ReadOnly.newBuilder((ReadOnly) this.mode_).mergeFrom(readOnly)).buildPartial();
        }
        this.modeCase_ = 2;
    }

    private void clearReadOnly() {
        if (this.modeCase_ == 2) {
            this.modeCase_ = 0;
            this.mode_ = null;
        }
    }

    public ReadWrite getReadWrite() {
        if (this.modeCase_ == 3) {
            return (ReadWrite) this.mode_;
        }
        return ReadWrite.getDefaultInstance();
    }

    private void setReadWrite(ReadWrite readWrite) {
        if (readWrite != null) {
            this.mode_ = readWrite;
            this.modeCase_ = 3;
            return;
        }
        throw new NullPointerException();
    }

    private void setReadWrite(Builder builder) {
        this.mode_ = builder.build();
        this.modeCase_ = 3;
    }

    private void mergeReadWrite(ReadWrite readWrite) {
        if (this.modeCase_ != 3 || this.mode_ == ReadWrite.getDefaultInstance()) {
            this.mode_ = readWrite;
        } else {
            this.mode_ = ((Builder) ReadWrite.newBuilder((ReadWrite) this.mode_).mergeFrom(readWrite)).buildPartial();
        }
        this.modeCase_ = 3;
    }

    private void clearReadWrite() {
        if (this.modeCase_ == 3) {
            this.modeCase_ = 0;
            this.mode_ = null;
        }
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (this.modeCase_ == 2) {
            codedOutputStream.writeMessage(2, (ReadOnly) this.mode_);
        }
        if (this.modeCase_ == 3) {
            codedOutputStream.writeMessage(3, (ReadWrite) this.mode_);
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (this.modeCase_ == 2) {
            i = 0 + CodedOutputStream.computeMessageSize(2, (ReadOnly) this.mode_);
        }
        if (this.modeCase_ == 3) {
            i += CodedOutputStream.computeMessageSize(3, (ReadWrite) this.mode_);
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static TransactionOptions parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (TransactionOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static TransactionOptions parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (TransactionOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static TransactionOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (TransactionOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static TransactionOptions parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (TransactionOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static TransactionOptions parseFrom(InputStream inputStream) throws IOException {
        return (TransactionOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static TransactionOptions parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (TransactionOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static TransactionOptions parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (TransactionOptions) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static TransactionOptions parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (TransactionOptions) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static TransactionOptions parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (TransactionOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static TransactionOptions parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (TransactionOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(TransactionOptions transactionOptions) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(transactionOptions);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        int i;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new TransactionOptions();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                TransactionOptions transactionOptions = (TransactionOptions) obj2;
                i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$TransactionOptions$ModeCase[transactionOptions.getModeCase().ordinal()];
                if (i == 1) {
                    if (this.modeCase_ == 2) {
                        z = true;
                    }
                    this.mode_ = visitor.visitOneofMessage(z, this.mode_, transactionOptions.mode_);
                } else if (i == 2) {
                    if (this.modeCase_ == 3) {
                        z = true;
                    }
                    this.mode_ = visitor.visitOneofMessage(z, this.mode_, transactionOptions.mode_);
                } else if (i == 3) {
                    if (this.modeCase_ != 0) {
                        z = true;
                    }
                    visitor.visitOneofNotSet(z);
                }
                if (visitor == MergeFromVisitor.INSTANCE) {
                    i = transactionOptions.modeCase_;
                    if (i != 0) {
                        this.modeCase_ = i;
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
                                Builder builder = this.modeCase_ == 2 ? (Builder) ((ReadOnly) this.mode_).toBuilder() : null;
                                this.mode_ = codedInputStream.readMessage(ReadOnly.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom((ReadOnly) this.mode_);
                                    this.mode_ = builder.buildPartial();
                                }
                                this.modeCase_ = 2;
                            } else if (i == 26) {
                                Builder builder2 = this.modeCase_ == 3 ? (Builder) ((ReadWrite) this.mode_).toBuilder() : null;
                                this.mode_ = codedInputStream.readMessage(ReadWrite.parser(), extensionRegistryLite);
                                if (builder2 != null) {
                                    builder2.mergeFrom((ReadWrite) this.mode_);
                                    this.mode_ = builder2.buildPartial();
                                }
                                this.modeCase_ = 3;
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
                    synchronized (TransactionOptions.class) {
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

    public static TransactionOptions getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<TransactionOptions> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
