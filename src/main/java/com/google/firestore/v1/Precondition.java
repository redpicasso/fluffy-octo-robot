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
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Precondition extends GeneratedMessageLite<Precondition, Builder> implements PreconditionOrBuilder {
    private static final Precondition DEFAULT_INSTANCE = new Precondition();
    public static final int EXISTS_FIELD_NUMBER = 1;
    private static volatile Parser<Precondition> PARSER = null;
    public static final int UPDATE_TIME_FIELD_NUMBER = 2;
    private int conditionTypeCase_ = 0;
    private Object conditionType_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firestore.v1.Precondition$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase = new int[ConditionTypeCase.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:19:0x0062, code:
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase = new int[com.google.firestore.v1.Precondition.ConditionTypeCase.values().length];
     */
        /* JADX WARNING: Missing block: B:21:?, code:
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[com.google.firestore.v1.Precondition.ConditionTypeCase.EXISTS.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:23:?, code:
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[com.google.firestore.v1.Precondition.ConditionTypeCase.UPDATE_TIME.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:25:?, code:
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[com.google.firestore.v1.Precondition.ConditionTypeCase.CONDITIONTYPE_NOT_SET.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:26:0x0089, code:
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
            r3 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_BUILDER;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r5 = 4;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r3 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.VISIT;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r5 = 5;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            r3 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x004b }
            r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.MERGE_FROM_STREAM;	 Catch:{ NoSuchFieldError -> 0x004b }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x004b }
            r5 = 6;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x004b }
        L_0x004b:
            r3 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0056 }
            r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE;	 Catch:{ NoSuchFieldError -> 0x0056 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0056 }
            r5 = 7;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x0056 }
        L_0x0056:
            r3 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0062 }
            r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_PARSER;	 Catch:{ NoSuchFieldError -> 0x0062 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0062 }
            r5 = 8;
            r3[r4] = r5;	 Catch:{ NoSuchFieldError -> 0x0062 }
        L_0x0062:
            r3 = com.google.firestore.v1.Precondition.ConditionTypeCase.values();
            r3 = r3.length;
            r3 = new int[r3];
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase = r3;
            r3 = $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r4 = com.google.firestore.v1.Precondition.ConditionTypeCase.EXISTS;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0075 }
            r3[r4] = r0;	 Catch:{ NoSuchFieldError -> 0x0075 }
        L_0x0075:
            r0 = $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase;	 Catch:{ NoSuchFieldError -> 0x007f }
            r3 = com.google.firestore.v1.Precondition.ConditionTypeCase.UPDATE_TIME;	 Catch:{ NoSuchFieldError -> 0x007f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x007f }
            r0[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x007f }
        L_0x007f:
            r0 = $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r1 = com.google.firestore.v1.Precondition.ConditionTypeCase.CONDITIONTYPE_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0089 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0089 }
        L_0x0089:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firestore.v1.Precondition.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum ConditionTypeCase implements EnumLite {
        EXISTS(1),
        UPDATE_TIME(2),
        CONDITIONTYPE_NOT_SET(0);
        
        private final int value;

        private ConditionTypeCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static ConditionTypeCase valueOf(int i) {
            return forNumber(i);
        }

        public static ConditionTypeCase forNumber(int i) {
            if (i == 0) {
                return CONDITIONTYPE_NOT_SET;
            }
            if (i != 1) {
                return i != 2 ? null : UPDATE_TIME;
            } else {
                return EXISTS;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<Precondition, Builder> implements PreconditionOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(Precondition.DEFAULT_INSTANCE);
        }

        public ConditionTypeCase getConditionTypeCase() {
            return ((Precondition) this.instance).getConditionTypeCase();
        }

        public Builder clearConditionType() {
            copyOnWrite();
            ((Precondition) this.instance).clearConditionType();
            return this;
        }

        public boolean getExists() {
            return ((Precondition) this.instance).getExists();
        }

        public Builder setExists(boolean z) {
            copyOnWrite();
            ((Precondition) this.instance).setExists(z);
            return this;
        }

        public Builder clearExists() {
            copyOnWrite();
            ((Precondition) this.instance).clearExists();
            return this;
        }

        public Timestamp getUpdateTime() {
            return ((Precondition) this.instance).getUpdateTime();
        }

        public Builder setUpdateTime(Timestamp timestamp) {
            copyOnWrite();
            ((Precondition) this.instance).setUpdateTime(timestamp);
            return this;
        }

        public Builder setUpdateTime(com.google.protobuf.Timestamp.Builder builder) {
            copyOnWrite();
            ((Precondition) this.instance).setUpdateTime(builder);
            return this;
        }

        public Builder mergeUpdateTime(Timestamp timestamp) {
            copyOnWrite();
            ((Precondition) this.instance).mergeUpdateTime(timestamp);
            return this;
        }

        public Builder clearUpdateTime() {
            copyOnWrite();
            ((Precondition) this.instance).clearUpdateTime();
            return this;
        }
    }

    private Precondition() {
    }

    public ConditionTypeCase getConditionTypeCase() {
        return ConditionTypeCase.forNumber(this.conditionTypeCase_);
    }

    private void clearConditionType() {
        this.conditionTypeCase_ = 0;
        this.conditionType_ = null;
    }

    public boolean getExists() {
        return this.conditionTypeCase_ == 1 ? ((Boolean) this.conditionType_).booleanValue() : false;
    }

    private void setExists(boolean z) {
        this.conditionTypeCase_ = 1;
        this.conditionType_ = Boolean.valueOf(z);
    }

    private void clearExists() {
        if (this.conditionTypeCase_ == 1) {
            this.conditionTypeCase_ = 0;
            this.conditionType_ = null;
        }
    }

    public Timestamp getUpdateTime() {
        if (this.conditionTypeCase_ == 2) {
            return (Timestamp) this.conditionType_;
        }
        return Timestamp.getDefaultInstance();
    }

    private void setUpdateTime(Timestamp timestamp) {
        if (timestamp != null) {
            this.conditionType_ = timestamp;
            this.conditionTypeCase_ = 2;
            return;
        }
        throw new NullPointerException();
    }

    private void setUpdateTime(com.google.protobuf.Timestamp.Builder builder) {
        this.conditionType_ = builder.build();
        this.conditionTypeCase_ = 2;
    }

    private void mergeUpdateTime(Timestamp timestamp) {
        if (this.conditionTypeCase_ != 2 || this.conditionType_ == Timestamp.getDefaultInstance()) {
            this.conditionType_ = timestamp;
        } else {
            this.conditionType_ = ((com.google.protobuf.Timestamp.Builder) Timestamp.newBuilder((Timestamp) this.conditionType_).mergeFrom(timestamp)).buildPartial();
        }
        this.conditionTypeCase_ = 2;
    }

    private void clearUpdateTime() {
        if (this.conditionTypeCase_ == 2) {
            this.conditionTypeCase_ = 0;
            this.conditionType_ = null;
        }
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (this.conditionTypeCase_ == 1) {
            codedOutputStream.writeBool(1, ((Boolean) this.conditionType_).booleanValue());
        }
        if (this.conditionTypeCase_ == 2) {
            codedOutputStream.writeMessage(2, (Timestamp) this.conditionType_);
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (this.conditionTypeCase_ == 1) {
            i = 0 + CodedOutputStream.computeBoolSize(1, ((Boolean) this.conditionType_).booleanValue());
        }
        if (this.conditionTypeCase_ == 2) {
            i += CodedOutputStream.computeMessageSize(2, (Timestamp) this.conditionType_);
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static Precondition parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Precondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static Precondition parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Precondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Precondition parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Precondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static Precondition parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Precondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Precondition parseFrom(InputStream inputStream) throws IOException {
        return (Precondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Precondition parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Precondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Precondition parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Precondition) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Precondition parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Precondition) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Precondition parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Precondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Precondition parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Precondition) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Precondition precondition) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(precondition);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        int i;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Precondition();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                Precondition precondition = (Precondition) obj2;
                i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[precondition.getConditionTypeCase().ordinal()];
                if (i == 1) {
                    if (this.conditionTypeCase_ == 1) {
                        z = true;
                    }
                    this.conditionType_ = visitor.visitOneofBoolean(z, this.conditionType_, precondition.conditionType_);
                } else if (i == 2) {
                    if (this.conditionTypeCase_ == 2) {
                        z = true;
                    }
                    this.conditionType_ = visitor.visitOneofMessage(z, this.conditionType_, precondition.conditionType_);
                } else if (i == 3) {
                    if (this.conditionTypeCase_ != 0) {
                        z = true;
                    }
                    visitor.visitOneofNotSet(z);
                }
                if (visitor == MergeFromVisitor.INSTANCE) {
                    i = precondition.conditionTypeCase_;
                    if (i != 0) {
                        this.conditionTypeCase_ = i;
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
                            if (i == 8) {
                                this.conditionTypeCase_ = 1;
                                this.conditionType_ = Boolean.valueOf(codedInputStream.readBool());
                            } else if (i == 18) {
                                com.google.protobuf.Timestamp.Builder builder = this.conditionTypeCase_ == 2 ? (com.google.protobuf.Timestamp.Builder) ((Timestamp) this.conditionType_).toBuilder() : null;
                                this.conditionType_ = codedInputStream.readMessage(Timestamp.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom((Timestamp) this.conditionType_);
                                    this.conditionType_ = builder.buildPartial();
                                }
                                this.conditionTypeCase_ = 2;
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
                    synchronized (Precondition.class) {
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

    public static Precondition getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Precondition> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
