package com.google.firestore.v1;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Internal.ProtobufList;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class ListCollectionIdsResponse extends GeneratedMessageLite<ListCollectionIdsResponse, Builder> implements ListCollectionIdsResponseOrBuilder {
    public static final int COLLECTION_IDS_FIELD_NUMBER = 1;
    private static final ListCollectionIdsResponse DEFAULT_INSTANCE = new ListCollectionIdsResponse();
    public static final int NEXT_PAGE_TOKEN_FIELD_NUMBER = 2;
    private static volatile Parser<ListCollectionIdsResponse> PARSER;
    private int bitField0_;
    private ProtobufList<String> collectionIds_ = GeneratedMessageLite.emptyProtobufList();
    private String nextPageToken_ = "";

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<ListCollectionIdsResponse, Builder> implements ListCollectionIdsResponseOrBuilder {
        private Builder() {
            super(ListCollectionIdsResponse.DEFAULT_INSTANCE);
        }

        public List<String> getCollectionIdsList() {
            return Collections.unmodifiableList(((ListCollectionIdsResponse) this.instance).getCollectionIdsList());
        }

        public int getCollectionIdsCount() {
            return ((ListCollectionIdsResponse) this.instance).getCollectionIdsCount();
        }

        public String getCollectionIds(int i) {
            return ((ListCollectionIdsResponse) this.instance).getCollectionIds(i);
        }

        public ByteString getCollectionIdsBytes(int i) {
            return ((ListCollectionIdsResponse) this.instance).getCollectionIdsBytes(i);
        }

        public Builder setCollectionIds(int i, String str) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).setCollectionIds(i, str);
            return this;
        }

        public Builder addCollectionIds(String str) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).addCollectionIds(str);
            return this;
        }

        public Builder addAllCollectionIds(Iterable<String> iterable) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).addAllCollectionIds(iterable);
            return this;
        }

        public Builder clearCollectionIds() {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).clearCollectionIds();
            return this;
        }

        public Builder addCollectionIdsBytes(ByteString byteString) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).addCollectionIdsBytes(byteString);
            return this;
        }

        public String getNextPageToken() {
            return ((ListCollectionIdsResponse) this.instance).getNextPageToken();
        }

        public ByteString getNextPageTokenBytes() {
            return ((ListCollectionIdsResponse) this.instance).getNextPageTokenBytes();
        }

        public Builder setNextPageToken(String str) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).setNextPageToken(str);
            return this;
        }

        public Builder clearNextPageToken() {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).clearNextPageToken();
            return this;
        }

        public Builder setNextPageTokenBytes(ByteString byteString) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).setNextPageTokenBytes(byteString);
            return this;
        }
    }

    private ListCollectionIdsResponse() {
    }

    public List<String> getCollectionIdsList() {
        return this.collectionIds_;
    }

    public int getCollectionIdsCount() {
        return this.collectionIds_.size();
    }

    public String getCollectionIds(int i) {
        return (String) this.collectionIds_.get(i);
    }

    public ByteString getCollectionIdsBytes(int i) {
        return ByteString.copyFromUtf8((String) this.collectionIds_.get(i));
    }

    private void ensureCollectionIdsIsMutable() {
        if (!this.collectionIds_.isModifiable()) {
            this.collectionIds_ = GeneratedMessageLite.mutableCopy(this.collectionIds_);
        }
    }

    private void setCollectionIds(int i, String str) {
        if (str != null) {
            ensureCollectionIdsIsMutable();
            this.collectionIds_.set(i, str);
            return;
        }
        throw new NullPointerException();
    }

    private void addCollectionIds(String str) {
        if (str != null) {
            ensureCollectionIdsIsMutable();
            this.collectionIds_.add(str);
            return;
        }
        throw new NullPointerException();
    }

    private void addAllCollectionIds(Iterable<String> iterable) {
        ensureCollectionIdsIsMutable();
        AbstractMessageLite.addAll(iterable, this.collectionIds_);
    }

    private void clearCollectionIds() {
        this.collectionIds_ = GeneratedMessageLite.emptyProtobufList();
    }

    private void addCollectionIdsBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            ensureCollectionIdsIsMutable();
            this.collectionIds_.add(byteString.toStringUtf8());
            return;
        }
        throw new NullPointerException();
    }

    public String getNextPageToken() {
        return this.nextPageToken_;
    }

    public ByteString getNextPageTokenBytes() {
        return ByteString.copyFromUtf8(this.nextPageToken_);
    }

    private void setNextPageToken(String str) {
        if (str != null) {
            this.nextPageToken_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearNextPageToken() {
        this.nextPageToken_ = getDefaultInstance().getNextPageToken();
    }

    private void setNextPageTokenBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.nextPageToken_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        for (int i = 0; i < this.collectionIds_.size(); i++) {
            codedOutputStream.writeString(1, (String) this.collectionIds_.get(i));
        }
        if (!this.nextPageToken_.isEmpty()) {
            codedOutputStream.writeString(2, getNextPageToken());
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.collectionIds_.size(); i3++) {
            i2 += CodedOutputStream.computeStringSizeNoTag((String) this.collectionIds_.get(i3));
        }
        i = (0 + i2) + (getCollectionIdsList().size() * 1);
        if (!this.nextPageToken_.isEmpty()) {
            i += CodedOutputStream.computeStringSize(2, getNextPageToken());
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static ListCollectionIdsResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static ListCollectionIdsResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static ListCollectionIdsResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static ListCollectionIdsResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static ListCollectionIdsResponse parseFrom(InputStream inputStream) throws IOException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static ListCollectionIdsResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static ListCollectionIdsResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static ListCollectionIdsResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static ListCollectionIdsResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static ListCollectionIdsResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ListCollectionIdsResponse listCollectionIdsResponse) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(listCollectionIdsResponse);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new ListCollectionIdsResponse();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.collectionIds_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                ListCollectionIdsResponse listCollectionIdsResponse = (ListCollectionIdsResponse) obj2;
                this.collectionIds_ = visitor.visitList(this.collectionIds_, listCollectionIdsResponse.collectionIds_);
                this.nextPageToken_ = visitor.visitString(this.nextPageToken_.isEmpty() ^ true, this.nextPageToken_, true ^ listCollectionIdsResponse.nextPageToken_.isEmpty(), listCollectionIdsResponse.nextPageToken_);
                if (visitor == MergeFromVisitor.INSTANCE) {
                    this.bitField0_ |= listCollectionIdsResponse.bitField0_;
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
                            if (readTag == 10) {
                                String readStringRequireUtf8 = codedInputStream.readStringRequireUtf8();
                                if (!this.collectionIds_.isModifiable()) {
                                    this.collectionIds_ = GeneratedMessageLite.mutableCopy(this.collectionIds_);
                                }
                                this.collectionIds_.add(readStringRequireUtf8);
                            } else if (readTag == 18) {
                                this.nextPageToken_ = codedInputStream.readStringRequireUtf8();
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
                    synchronized (ListCollectionIdsResponse.class) {
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

    public static ListCollectionIdsResponse getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ListCollectionIdsResponse> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
