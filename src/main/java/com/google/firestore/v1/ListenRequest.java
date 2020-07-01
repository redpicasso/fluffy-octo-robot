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
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.Parser;
import com.google.protobuf.WireFormat.FieldType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class ListenRequest extends GeneratedMessageLite<ListenRequest, Builder> implements ListenRequestOrBuilder {
    public static final int ADD_TARGET_FIELD_NUMBER = 2;
    public static final int DATABASE_FIELD_NUMBER = 1;
    private static final ListenRequest DEFAULT_INSTANCE = new ListenRequest();
    public static final int LABELS_FIELD_NUMBER = 4;
    private static volatile Parser<ListenRequest> PARSER = null;
    public static final int REMOVE_TARGET_FIELD_NUMBER = 3;
    private int bitField0_;
    private String database_ = "";
    private MapFieldLite<String, String> labels_ = MapFieldLite.emptyMapField();
    private int targetChangeCase_ = 0;
    private Object targetChange_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firestore.v1.ListenRequest$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$ListenRequest$TargetChangeCase = new int[TargetChangeCase.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:19:0x0062, code:
            $SwitchMap$com$google$firestore$v1$ListenRequest$TargetChangeCase = new int[com.google.firestore.v1.ListenRequest.TargetChangeCase.values().length];
     */
        /* JADX WARNING: Missing block: B:21:?, code:
            $SwitchMap$com$google$firestore$v1$ListenRequest$TargetChangeCase[com.google.firestore.v1.ListenRequest.TargetChangeCase.ADD_TARGET.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:23:?, code:
            $SwitchMap$com$google$firestore$v1$ListenRequest$TargetChangeCase[com.google.firestore.v1.ListenRequest.TargetChangeCase.REMOVE_TARGET.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:25:?, code:
            $SwitchMap$com$google$firestore$v1$ListenRequest$TargetChangeCase[com.google.firestore.v1.ListenRequest.TargetChangeCase.TARGETCHANGE_NOT_SET.ordinal()] = 3;
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
            r3 = com.google.firestore.v1.ListenRequest.TargetChangeCase.values();
            r3 = r3.length;
            r3 = new int[r3];
            $SwitchMap$com$google$firestore$v1$ListenRequest$TargetChangeCase = r3;
            r3 = $SwitchMap$com$google$firestore$v1$ListenRequest$TargetChangeCase;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r4 = com.google.firestore.v1.ListenRequest.TargetChangeCase.ADD_TARGET;	 Catch:{ NoSuchFieldError -> 0x0075 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0075 }
            r3[r4] = r0;	 Catch:{ NoSuchFieldError -> 0x0075 }
        L_0x0075:
            r0 = $SwitchMap$com$google$firestore$v1$ListenRequest$TargetChangeCase;	 Catch:{ NoSuchFieldError -> 0x007f }
            r3 = com.google.firestore.v1.ListenRequest.TargetChangeCase.REMOVE_TARGET;	 Catch:{ NoSuchFieldError -> 0x007f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x007f }
            r0[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x007f }
        L_0x007f:
            r0 = $SwitchMap$com$google$firestore$v1$ListenRequest$TargetChangeCase;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r1 = com.google.firestore.v1.ListenRequest.TargetChangeCase.TARGETCHANGE_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x0089 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0089 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0089 }
        L_0x0089:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firestore.v1.ListenRequest.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private static final class LabelsDefaultEntryHolder {
        static final MapEntryLite<String, String> defaultEntry;

        private LabelsDefaultEntryHolder() {
        }

        static {
            String str = "";
            defaultEntry = MapEntryLite.newDefaultInstance(FieldType.STRING, str, FieldType.STRING, str);
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum TargetChangeCase implements EnumLite {
        ADD_TARGET(2),
        REMOVE_TARGET(3),
        TARGETCHANGE_NOT_SET(0);
        
        private final int value;

        private TargetChangeCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static TargetChangeCase valueOf(int i) {
            return forNumber(i);
        }

        public static TargetChangeCase forNumber(int i) {
            if (i == 0) {
                return TARGETCHANGE_NOT_SET;
            }
            if (i != 2) {
                return i != 3 ? null : REMOVE_TARGET;
            } else {
                return ADD_TARGET;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<ListenRequest, Builder> implements ListenRequestOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(ListenRequest.DEFAULT_INSTANCE);
        }

        public TargetChangeCase getTargetChangeCase() {
            return ((ListenRequest) this.instance).getTargetChangeCase();
        }

        public Builder clearTargetChange() {
            copyOnWrite();
            ((ListenRequest) this.instance).clearTargetChange();
            return this;
        }

        public String getDatabase() {
            return ((ListenRequest) this.instance).getDatabase();
        }

        public ByteString getDatabaseBytes() {
            return ((ListenRequest) this.instance).getDatabaseBytes();
        }

        public Builder setDatabase(String str) {
            copyOnWrite();
            ((ListenRequest) this.instance).setDatabase(str);
            return this;
        }

        public Builder clearDatabase() {
            copyOnWrite();
            ((ListenRequest) this.instance).clearDatabase();
            return this;
        }

        public Builder setDatabaseBytes(ByteString byteString) {
            copyOnWrite();
            ((ListenRequest) this.instance).setDatabaseBytes(byteString);
            return this;
        }

        public Target getAddTarget() {
            return ((ListenRequest) this.instance).getAddTarget();
        }

        public Builder setAddTarget(Target target) {
            copyOnWrite();
            ((ListenRequest) this.instance).setAddTarget(target);
            return this;
        }

        public Builder setAddTarget(com.google.firestore.v1.Target.Builder builder) {
            copyOnWrite();
            ((ListenRequest) this.instance).setAddTarget(builder);
            return this;
        }

        public Builder mergeAddTarget(Target target) {
            copyOnWrite();
            ((ListenRequest) this.instance).mergeAddTarget(target);
            return this;
        }

        public Builder clearAddTarget() {
            copyOnWrite();
            ((ListenRequest) this.instance).clearAddTarget();
            return this;
        }

        public int getRemoveTarget() {
            return ((ListenRequest) this.instance).getRemoveTarget();
        }

        public Builder setRemoveTarget(int i) {
            copyOnWrite();
            ((ListenRequest) this.instance).setRemoveTarget(i);
            return this;
        }

        public Builder clearRemoveTarget() {
            copyOnWrite();
            ((ListenRequest) this.instance).clearRemoveTarget();
            return this;
        }

        public int getLabelsCount() {
            return ((ListenRequest) this.instance).getLabelsMap().size();
        }

        public boolean containsLabels(String str) {
            if (str != null) {
                return ((ListenRequest) this.instance).getLabelsMap().containsKey(str);
            }
            throw new NullPointerException();
        }

        public Builder clearLabels() {
            copyOnWrite();
            ((ListenRequest) this.instance).getMutableLabelsMap().clear();
            return this;
        }

        public Builder removeLabels(String str) {
            if (str != null) {
                copyOnWrite();
                ((ListenRequest) this.instance).getMutableLabelsMap().remove(str);
                return this;
            }
            throw new NullPointerException();
        }

        @Deprecated
        public Map<String, String> getLabels() {
            return getLabelsMap();
        }

        public Map<String, String> getLabelsMap() {
            return Collections.unmodifiableMap(((ListenRequest) this.instance).getLabelsMap());
        }

        public String getLabelsOrDefault(String str, String str2) {
            if (str != null) {
                Map labelsMap = ((ListenRequest) this.instance).getLabelsMap();
                return labelsMap.containsKey(str) ? (String) labelsMap.get(str) : str2;
            } else {
                throw new NullPointerException();
            }
        }

        public String getLabelsOrThrow(String str) {
            if (str != null) {
                Map labelsMap = ((ListenRequest) this.instance).getLabelsMap();
                if (labelsMap.containsKey(str)) {
                    return (String) labelsMap.get(str);
                }
                throw new IllegalArgumentException();
            }
            throw new NullPointerException();
        }

        public Builder putLabels(String str, String str2) {
            if (str == null) {
                throw new NullPointerException();
            } else if (str2 != null) {
                copyOnWrite();
                ((ListenRequest) this.instance).getMutableLabelsMap().put(str, str2);
                return this;
            } else {
                throw new NullPointerException();
            }
        }

        public Builder putAllLabels(Map<String, String> map) {
            copyOnWrite();
            ((ListenRequest) this.instance).getMutableLabelsMap().putAll(map);
            return this;
        }
    }

    private ListenRequest() {
    }

    public TargetChangeCase getTargetChangeCase() {
        return TargetChangeCase.forNumber(this.targetChangeCase_);
    }

    private void clearTargetChange() {
        this.targetChangeCase_ = 0;
        this.targetChange_ = null;
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

    public Target getAddTarget() {
        if (this.targetChangeCase_ == 2) {
            return (Target) this.targetChange_;
        }
        return Target.getDefaultInstance();
    }

    private void setAddTarget(Target target) {
        if (target != null) {
            this.targetChange_ = target;
            this.targetChangeCase_ = 2;
            return;
        }
        throw new NullPointerException();
    }

    private void setAddTarget(com.google.firestore.v1.Target.Builder builder) {
        this.targetChange_ = builder.build();
        this.targetChangeCase_ = 2;
    }

    private void mergeAddTarget(Target target) {
        if (this.targetChangeCase_ != 2 || this.targetChange_ == Target.getDefaultInstance()) {
            this.targetChange_ = target;
        } else {
            this.targetChange_ = ((com.google.firestore.v1.Target.Builder) Target.newBuilder((Target) this.targetChange_).mergeFrom(target)).buildPartial();
        }
        this.targetChangeCase_ = 2;
    }

    private void clearAddTarget() {
        if (this.targetChangeCase_ == 2) {
            this.targetChangeCase_ = 0;
            this.targetChange_ = null;
        }
    }

    public int getRemoveTarget() {
        return this.targetChangeCase_ == 3 ? ((Integer) this.targetChange_).intValue() : 0;
    }

    private void setRemoveTarget(int i) {
        this.targetChangeCase_ = 3;
        this.targetChange_ = Integer.valueOf(i);
    }

    private void clearRemoveTarget() {
        if (this.targetChangeCase_ == 3) {
            this.targetChangeCase_ = 0;
            this.targetChange_ = null;
        }
    }

    private MapFieldLite<String, String> internalGetLabels() {
        return this.labels_;
    }

    private MapFieldLite<String, String> internalGetMutableLabels() {
        if (!this.labels_.isMutable()) {
            this.labels_ = this.labels_.mutableCopy();
        }
        return this.labels_;
    }

    public int getLabelsCount() {
        return internalGetLabels().size();
    }

    public boolean containsLabels(String str) {
        if (str != null) {
            return internalGetLabels().containsKey(str);
        }
        throw new NullPointerException();
    }

    @Deprecated
    public Map<String, String> getLabels() {
        return getLabelsMap();
    }

    public Map<String, String> getLabelsMap() {
        return Collections.unmodifiableMap(internalGetLabels());
    }

    public String getLabelsOrDefault(String str, String str2) {
        if (str != null) {
            Map internalGetLabels = internalGetLabels();
            return internalGetLabels.containsKey(str) ? (String) internalGetLabels.get(str) : str2;
        } else {
            throw new NullPointerException();
        }
    }

    public String getLabelsOrThrow(String str) {
        if (str != null) {
            Map internalGetLabels = internalGetLabels();
            if (internalGetLabels.containsKey(str)) {
                return (String) internalGetLabels.get(str);
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    private Map<String, String> getMutableLabelsMap() {
        return internalGetMutableLabels();
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (!this.database_.isEmpty()) {
            codedOutputStream.writeString(1, getDatabase());
        }
        if (this.targetChangeCase_ == 2) {
            codedOutputStream.writeMessage(2, (Target) this.targetChange_);
        }
        if (this.targetChangeCase_ == 3) {
            codedOutputStream.writeInt32(3, ((Integer) this.targetChange_).intValue());
        }
        for (Entry entry : internalGetLabels().entrySet()) {
            LabelsDefaultEntryHolder.defaultEntry.serializeTo(codedOutputStream, 4, (String) entry.getKey(), (String) entry.getValue());
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (!this.database_.isEmpty()) {
            i = 0 + CodedOutputStream.computeStringSize(1, getDatabase());
        }
        if (this.targetChangeCase_ == 2) {
            i += CodedOutputStream.computeMessageSize(2, (Target) this.targetChange_);
        }
        if (this.targetChangeCase_ == 3) {
            i += CodedOutputStream.computeInt32Size(3, ((Integer) this.targetChange_).intValue());
        }
        for (Entry entry : internalGetLabels().entrySet()) {
            i += LabelsDefaultEntryHolder.defaultEntry.computeMessageSize(4, (String) entry.getKey(), (String) entry.getValue());
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static ListenRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (ListenRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static ListenRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (ListenRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static ListenRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (ListenRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static ListenRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (ListenRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static ListenRequest parseFrom(InputStream inputStream) throws IOException {
        return (ListenRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static ListenRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListenRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static ListenRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (ListenRequest) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static ListenRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListenRequest) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static ListenRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (ListenRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static ListenRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListenRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ListenRequest listenRequest) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(listenRequest);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        int i;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new ListenRequest();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.labels_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                ListenRequest listenRequest = (ListenRequest) obj2;
                this.database_ = visitor.visitString(this.database_.isEmpty() ^ true, this.database_, listenRequest.database_.isEmpty() ^ true, listenRequest.database_);
                this.labels_ = visitor.visitMap(this.labels_, listenRequest.internalGetLabels());
                i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$ListenRequest$TargetChangeCase[listenRequest.getTargetChangeCase().ordinal()];
                if (i == 1) {
                    if (this.targetChangeCase_ == 2) {
                        z = true;
                    }
                    this.targetChange_ = visitor.visitOneofMessage(z, this.targetChange_, listenRequest.targetChange_);
                } else if (i == 2) {
                    if (this.targetChangeCase_ == 3) {
                        z = true;
                    }
                    this.targetChange_ = visitor.visitOneofInt(z, this.targetChange_, listenRequest.targetChange_);
                } else if (i == 3) {
                    if (this.targetChangeCase_ != 0) {
                        z = true;
                    }
                    visitor.visitOneofNotSet(z);
                }
                if (visitor == MergeFromVisitor.INSTANCE) {
                    i = listenRequest.targetChangeCase_;
                    if (i != 0) {
                        this.targetChangeCase_ = i;
                    }
                    this.bitField0_ |= listenRequest.bitField0_;
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
                                com.google.firestore.v1.Target.Builder builder = this.targetChangeCase_ == 2 ? (com.google.firestore.v1.Target.Builder) ((Target) this.targetChange_).toBuilder() : null;
                                this.targetChange_ = codedInputStream.readMessage(Target.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom((Target) this.targetChange_);
                                    this.targetChange_ = builder.buildPartial();
                                }
                                this.targetChangeCase_ = 2;
                            } else if (i == 24) {
                                this.targetChangeCase_ = 3;
                                this.targetChange_ = Integer.valueOf(codedInputStream.readInt32());
                            } else if (i == 34) {
                                if (!this.labels_.isMutable()) {
                                    this.labels_ = this.labels_.mutableCopy();
                                }
                                LabelsDefaultEntryHolder.defaultEntry.parseInto(this.labels_, codedInputStream, extensionRegistryLite);
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
                    synchronized (ListenRequest.class) {
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

    public static ListenRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ListenRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
