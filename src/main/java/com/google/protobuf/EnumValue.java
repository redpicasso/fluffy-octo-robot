package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Internal.ProtobufList;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public final class EnumValue extends GeneratedMessageLite<EnumValue, Builder> implements EnumValueOrBuilder {
    private static final EnumValue DEFAULT_INSTANCE = new EnumValue();
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int NUMBER_FIELD_NUMBER = 2;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    private static volatile Parser<EnumValue> PARSER;
    private int bitField0_;
    private String name_ = "";
    private int number_;
    private ProtobufList<Option> options_ = GeneratedMessageLite.emptyProtobufList();

    /* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<EnumValue, Builder> implements EnumValueOrBuilder {
        private Builder() {
            super(EnumValue.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((EnumValue) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((EnumValue) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((EnumValue) this.instance).setName(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((EnumValue) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((EnumValue) this.instance).setNameBytes(byteString);
            return this;
        }

        public int getNumber() {
            return ((EnumValue) this.instance).getNumber();
        }

        public Builder setNumber(int i) {
            copyOnWrite();
            ((EnumValue) this.instance).setNumber(i);
            return this;
        }

        public Builder clearNumber() {
            copyOnWrite();
            ((EnumValue) this.instance).clearNumber();
            return this;
        }

        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((EnumValue) this.instance).getOptionsList());
        }

        public int getOptionsCount() {
            return ((EnumValue) this.instance).getOptionsCount();
        }

        public Option getOptions(int i) {
            return ((EnumValue) this.instance).getOptions(i);
        }

        public Builder setOptions(int i, Option option) {
            copyOnWrite();
            ((EnumValue) this.instance).setOptions(i, option);
            return this;
        }

        public Builder setOptions(int i, com.google.protobuf.Option.Builder builder) {
            copyOnWrite();
            ((EnumValue) this.instance).setOptions(i, builder);
            return this;
        }

        public Builder addOptions(Option option) {
            copyOnWrite();
            ((EnumValue) this.instance).addOptions(option);
            return this;
        }

        public Builder addOptions(int i, Option option) {
            copyOnWrite();
            ((EnumValue) this.instance).addOptions(i, option);
            return this;
        }

        public Builder addOptions(com.google.protobuf.Option.Builder builder) {
            copyOnWrite();
            ((EnumValue) this.instance).addOptions(builder);
            return this;
        }

        public Builder addOptions(int i, com.google.protobuf.Option.Builder builder) {
            copyOnWrite();
            ((EnumValue) this.instance).addOptions(i, builder);
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            copyOnWrite();
            ((EnumValue) this.instance).addAllOptions(iterable);
            return this;
        }

        public Builder clearOptions() {
            copyOnWrite();
            ((EnumValue) this.instance).clearOptions();
            return this;
        }

        public Builder removeOptions(int i) {
            copyOnWrite();
            ((EnumValue) this.instance).removeOptions(i);
            return this;
        }
    }

    private EnumValue() {
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

    public int getNumber() {
        return this.number_;
    }

    private void setNumber(int i) {
        this.number_ = i;
    }

    private void clearNumber() {
        this.number_ = 0;
    }

    public List<Option> getOptionsList() {
        return this.options_;
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    public int getOptionsCount() {
        return this.options_.size();
    }

    public Option getOptions(int i) {
        return (Option) this.options_.get(i);
    }

    public OptionOrBuilder getOptionsOrBuilder(int i) {
        return (OptionOrBuilder) this.options_.get(i);
    }

    private void ensureOptionsIsMutable() {
        if (!this.options_.isModifiable()) {
            this.options_ = GeneratedMessageLite.mutableCopy(this.options_);
        }
    }

    private void setOptions(int i, Option option) {
        if (option != null) {
            ensureOptionsIsMutable();
            this.options_.set(i, option);
            return;
        }
        throw new NullPointerException();
    }

    private void setOptions(int i, com.google.protobuf.Option.Builder builder) {
        ensureOptionsIsMutable();
        this.options_.set(i, (Option) builder.build());
    }

    private void addOptions(Option option) {
        if (option != null) {
            ensureOptionsIsMutable();
            this.options_.add(option);
            return;
        }
        throw new NullPointerException();
    }

    private void addOptions(int i, Option option) {
        if (option != null) {
            ensureOptionsIsMutable();
            this.options_.add(i, option);
            return;
        }
        throw new NullPointerException();
    }

    private void addOptions(com.google.protobuf.Option.Builder builder) {
        ensureOptionsIsMutable();
        this.options_.add((Option) builder.build());
    }

    private void addOptions(int i, com.google.protobuf.Option.Builder builder) {
        ensureOptionsIsMutable();
        this.options_.add(i, (Option) builder.build());
    }

    private void addAllOptions(Iterable<? extends Option> iterable) {
        ensureOptionsIsMutable();
        AbstractMessageLite.addAll(iterable, this.options_);
    }

    private void clearOptions() {
        this.options_ = GeneratedMessageLite.emptyProtobufList();
    }

    private void removeOptions(int i) {
        ensureOptionsIsMutable();
        this.options_.remove(i);
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (!this.name_.isEmpty()) {
            codedOutputStream.writeString(1, getName());
        }
        int i = this.number_;
        if (i != 0) {
            codedOutputStream.writeInt32(2, i);
        }
        for (i = 0; i < this.options_.size(); i++) {
            codedOutputStream.writeMessage(3, (MessageLite) this.options_.get(i));
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        i = !this.name_.isEmpty() ? CodedOutputStream.computeStringSize(1, getName()) + 0 : 0;
        int i3 = this.number_;
        if (i3 != 0) {
            i += CodedOutputStream.computeInt32Size(2, i3);
        }
        while (i2 < this.options_.size()) {
            i += CodedOutputStream.computeMessageSize(3, (MessageLite) this.options_.get(i2));
            i2++;
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static EnumValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static EnumValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static EnumValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static EnumValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static EnumValue parseFrom(InputStream inputStream) throws IOException {
        return (EnumValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static EnumValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (EnumValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static EnumValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (EnumValue) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static EnumValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (EnumValue) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static EnumValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (EnumValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static EnumValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (EnumValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(EnumValue enumValue) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(enumValue);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new EnumValue();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.options_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                EnumValue enumValue = (EnumValue) obj2;
                this.name_ = visitor.visitString(this.name_.isEmpty() ^ true, this.name_, enumValue.name_.isEmpty() ^ true, enumValue.name_);
                boolean z2 = this.number_ != 0;
                int i = this.number_;
                if (enumValue.number_ != 0) {
                    z = true;
                }
                this.number_ = visitor.visitInt(z2, i, z, enumValue.number_);
                this.options_ = visitor.visitList(this.options_, enumValue.options_);
                if (visitor == MergeFromVisitor.INSTANCE) {
                    this.bitField0_ |= enumValue.bitField0_;
                }
                return this;
            case MERGE_FROM_STREAM:
                CodedInputStream codedInputStream = (CodedInputStream) obj;
                ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                while (!z) {
                    try {
                        int readTag = codedInputStream.readTag();
                        if (readTag != 0) {
                            if (readTag == 10) {
                                this.name_ = codedInputStream.readStringRequireUtf8();
                            } else if (readTag == 16) {
                                this.number_ = codedInputStream.readInt32();
                            } else if (readTag == 26) {
                                if (!this.options_.isModifiable()) {
                                    this.options_ = GeneratedMessageLite.mutableCopy(this.options_);
                                }
                                this.options_.add((Option) codedInputStream.readMessage(Option.parser(), extensionRegistryLite));
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
                    synchronized (EnumValue.class) {
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

    public static EnumValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<EnumValue> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
