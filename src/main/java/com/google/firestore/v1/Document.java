package com.google.firestore.v1;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import com.google.protobuf.WireFormat.FieldType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Document extends GeneratedMessageLite<Document, Builder> implements DocumentOrBuilder {
    public static final int CREATE_TIME_FIELD_NUMBER = 3;
    private static final Document DEFAULT_INSTANCE = new Document();
    public static final int FIELDS_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile Parser<Document> PARSER = null;
    public static final int UPDATE_TIME_FIELD_NUMBER = 4;
    private int bitField0_;
    private Timestamp createTime_;
    private MapFieldLite<String, Value> fields_ = MapFieldLite.emptyMapField();
    private String name_ = "";
    private Timestamp updateTime_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private static final class FieldsDefaultEntryHolder {
        static final MapEntryLite<String, Value> defaultEntry = MapEntryLite.newDefaultInstance(FieldType.STRING, "", FieldType.MESSAGE, Value.getDefaultInstance());

        private FieldsDefaultEntryHolder() {
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<Document, Builder> implements DocumentOrBuilder {
        private Builder() {
            super(Document.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((Document) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((Document) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((Document) this.instance).setName(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Document) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((Document) this.instance).setNameBytes(byteString);
            return this;
        }

        public int getFieldsCount() {
            return ((Document) this.instance).getFieldsMap().size();
        }

        public boolean containsFields(String str) {
            if (str != null) {
                return ((Document) this.instance).getFieldsMap().containsKey(str);
            }
            throw new NullPointerException();
        }

        public Builder clearFields() {
            copyOnWrite();
            ((Document) this.instance).getMutableFieldsMap().clear();
            return this;
        }

        public Builder removeFields(String str) {
            if (str != null) {
                copyOnWrite();
                ((Document) this.instance).getMutableFieldsMap().remove(str);
                return this;
            }
            throw new NullPointerException();
        }

        @Deprecated
        public Map<String, Value> getFields() {
            return getFieldsMap();
        }

        public Map<String, Value> getFieldsMap() {
            return Collections.unmodifiableMap(((Document) this.instance).getFieldsMap());
        }

        public Value getFieldsOrDefault(String str, Value value) {
            if (str != null) {
                Map fieldsMap = ((Document) this.instance).getFieldsMap();
                return fieldsMap.containsKey(str) ? (Value) fieldsMap.get(str) : value;
            } else {
                throw new NullPointerException();
            }
        }

        public Value getFieldsOrThrow(String str) {
            if (str != null) {
                Map fieldsMap = ((Document) this.instance).getFieldsMap();
                if (fieldsMap.containsKey(str)) {
                    return (Value) fieldsMap.get(str);
                }
                throw new IllegalArgumentException();
            }
            throw new NullPointerException();
        }

        public Builder putFields(String str, Value value) {
            if (str == null) {
                throw new NullPointerException();
            } else if (value != null) {
                copyOnWrite();
                ((Document) this.instance).getMutableFieldsMap().put(str, value);
                return this;
            } else {
                throw new NullPointerException();
            }
        }

        public Builder putAllFields(Map<String, Value> map) {
            copyOnWrite();
            ((Document) this.instance).getMutableFieldsMap().putAll(map);
            return this;
        }

        public boolean hasCreateTime() {
            return ((Document) this.instance).hasCreateTime();
        }

        public Timestamp getCreateTime() {
            return ((Document) this.instance).getCreateTime();
        }

        public Builder setCreateTime(Timestamp timestamp) {
            copyOnWrite();
            ((Document) this.instance).setCreateTime(timestamp);
            return this;
        }

        public Builder setCreateTime(com.google.protobuf.Timestamp.Builder builder) {
            copyOnWrite();
            ((Document) this.instance).setCreateTime(builder);
            return this;
        }

        public Builder mergeCreateTime(Timestamp timestamp) {
            copyOnWrite();
            ((Document) this.instance).mergeCreateTime(timestamp);
            return this;
        }

        public Builder clearCreateTime() {
            copyOnWrite();
            ((Document) this.instance).clearCreateTime();
            return this;
        }

        public boolean hasUpdateTime() {
            return ((Document) this.instance).hasUpdateTime();
        }

        public Timestamp getUpdateTime() {
            return ((Document) this.instance).getUpdateTime();
        }

        public Builder setUpdateTime(Timestamp timestamp) {
            copyOnWrite();
            ((Document) this.instance).setUpdateTime(timestamp);
            return this;
        }

        public Builder setUpdateTime(com.google.protobuf.Timestamp.Builder builder) {
            copyOnWrite();
            ((Document) this.instance).setUpdateTime(builder);
            return this;
        }

        public Builder mergeUpdateTime(Timestamp timestamp) {
            copyOnWrite();
            ((Document) this.instance).mergeUpdateTime(timestamp);
            return this;
        }

        public Builder clearUpdateTime() {
            copyOnWrite();
            ((Document) this.instance).clearUpdateTime();
            return this;
        }
    }

    private Document() {
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

    private MapFieldLite<String, Value> internalGetFields() {
        return this.fields_;
    }

    private MapFieldLite<String, Value> internalGetMutableFields() {
        if (!this.fields_.isMutable()) {
            this.fields_ = this.fields_.mutableCopy();
        }
        return this.fields_;
    }

    public int getFieldsCount() {
        return internalGetFields().size();
    }

    public boolean containsFields(String str) {
        if (str != null) {
            return internalGetFields().containsKey(str);
        }
        throw new NullPointerException();
    }

    @Deprecated
    public Map<String, Value> getFields() {
        return getFieldsMap();
    }

    public Map<String, Value> getFieldsMap() {
        return Collections.unmodifiableMap(internalGetFields());
    }

    public Value getFieldsOrDefault(String str, Value value) {
        if (str != null) {
            Map internalGetFields = internalGetFields();
            return internalGetFields.containsKey(str) ? (Value) internalGetFields.get(str) : value;
        } else {
            throw new NullPointerException();
        }
    }

    public Value getFieldsOrThrow(String str) {
        if (str != null) {
            Map internalGetFields = internalGetFields();
            if (internalGetFields.containsKey(str)) {
                return (Value) internalGetFields.get(str);
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    private Map<String, Value> getMutableFieldsMap() {
        return internalGetMutableFields();
    }

    public boolean hasCreateTime() {
        return this.createTime_ != null;
    }

    public Timestamp getCreateTime() {
        Timestamp timestamp = this.createTime_;
        return timestamp == null ? Timestamp.getDefaultInstance() : timestamp;
    }

    private void setCreateTime(Timestamp timestamp) {
        if (timestamp != null) {
            this.createTime_ = timestamp;
            return;
        }
        throw new NullPointerException();
    }

    private void setCreateTime(com.google.protobuf.Timestamp.Builder builder) {
        this.createTime_ = (Timestamp) builder.build();
    }

    private void mergeCreateTime(Timestamp timestamp) {
        Timestamp timestamp2 = this.createTime_;
        if (timestamp2 == null || timestamp2 == Timestamp.getDefaultInstance()) {
            this.createTime_ = timestamp;
        } else {
            this.createTime_ = (Timestamp) ((com.google.protobuf.Timestamp.Builder) Timestamp.newBuilder(this.createTime_).mergeFrom(timestamp)).buildPartial();
        }
    }

    private void clearCreateTime() {
        this.createTime_ = null;
    }

    public boolean hasUpdateTime() {
        return this.updateTime_ != null;
    }

    public Timestamp getUpdateTime() {
        Timestamp timestamp = this.updateTime_;
        return timestamp == null ? Timestamp.getDefaultInstance() : timestamp;
    }

    private void setUpdateTime(Timestamp timestamp) {
        if (timestamp != null) {
            this.updateTime_ = timestamp;
            return;
        }
        throw new NullPointerException();
    }

    private void setUpdateTime(com.google.protobuf.Timestamp.Builder builder) {
        this.updateTime_ = (Timestamp) builder.build();
    }

    private void mergeUpdateTime(Timestamp timestamp) {
        Timestamp timestamp2 = this.updateTime_;
        if (timestamp2 == null || timestamp2 == Timestamp.getDefaultInstance()) {
            this.updateTime_ = timestamp;
        } else {
            this.updateTime_ = (Timestamp) ((com.google.protobuf.Timestamp.Builder) Timestamp.newBuilder(this.updateTime_).mergeFrom(timestamp)).buildPartial();
        }
    }

    private void clearUpdateTime() {
        this.updateTime_ = null;
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (!this.name_.isEmpty()) {
            codedOutputStream.writeString(1, getName());
        }
        for (Entry entry : internalGetFields().entrySet()) {
            FieldsDefaultEntryHolder.defaultEntry.serializeTo(codedOutputStream, 2, (String) entry.getKey(), (Value) entry.getValue());
        }
        if (this.createTime_ != null) {
            codedOutputStream.writeMessage(3, getCreateTime());
        }
        if (this.updateTime_ != null) {
            codedOutputStream.writeMessage(4, getUpdateTime());
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
        for (Entry entry : internalGetFields().entrySet()) {
            i += FieldsDefaultEntryHolder.defaultEntry.computeMessageSize(2, (String) entry.getKey(), (Value) entry.getValue());
        }
        if (this.createTime_ != null) {
            i += CodedOutputStream.computeMessageSize(3, getCreateTime());
        }
        if (this.updateTime_ != null) {
            i += CodedOutputStream.computeMessageSize(4, getUpdateTime());
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static Document parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static Document parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Document parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static Document parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Document parseFrom(InputStream inputStream) throws IOException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Document parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Document parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Document) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Document parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Document) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Document parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Document parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Document document) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(document);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Document();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.fields_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                Document document = (Document) obj2;
                this.name_ = visitor.visitString(this.name_.isEmpty() ^ true, this.name_, true ^ document.name_.isEmpty(), document.name_);
                this.fields_ = visitor.visitMap(this.fields_, document.internalGetFields());
                this.createTime_ = (Timestamp) visitor.visitMessage(this.createTime_, document.createTime_);
                this.updateTime_ = (Timestamp) visitor.visitMessage(this.updateTime_, document.updateTime_);
                if (visitor == MergeFromVisitor.INSTANCE) {
                    this.bitField0_ |= document.bitField0_;
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
                            com.google.protobuf.Timestamp.Builder builder;
                            if (readTag == 10) {
                                this.name_ = codedInputStream.readStringRequireUtf8();
                            } else if (readTag == 18) {
                                if (!this.fields_.isMutable()) {
                                    this.fields_ = this.fields_.mutableCopy();
                                }
                                FieldsDefaultEntryHolder.defaultEntry.parseInto(this.fields_, codedInputStream, extensionRegistryLite);
                            } else if (readTag == 26) {
                                builder = this.createTime_ != null ? (com.google.protobuf.Timestamp.Builder) this.createTime_.toBuilder() : null;
                                this.createTime_ = (Timestamp) codedInputStream.readMessage(Timestamp.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom(this.createTime_);
                                    this.createTime_ = (Timestamp) builder.buildPartial();
                                }
                            } else if (readTag == 34) {
                                builder = this.updateTime_ != null ? (com.google.protobuf.Timestamp.Builder) this.updateTime_.toBuilder() : null;
                                this.updateTime_ = (Timestamp) codedInputStream.readMessage(Timestamp.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom(this.updateTime_);
                                    this.updateTime_ = (Timestamp) builder.buildPartial();
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
                    synchronized (Document.class) {
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

    public static Document getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Document> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
