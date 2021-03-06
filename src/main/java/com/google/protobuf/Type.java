package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Internal.ProtobufList;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public final class Type extends GeneratedMessageLite<Type, Builder> implements TypeOrBuilder {
    private static final Type DEFAULT_INSTANCE = new Type();
    public static final int FIELDS_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int ONEOFS_FIELD_NUMBER = 3;
    public static final int OPTIONS_FIELD_NUMBER = 4;
    private static volatile Parser<Type> PARSER = null;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
    public static final int SYNTAX_FIELD_NUMBER = 6;
    private int bitField0_;
    private ProtobufList<Field> fields_ = GeneratedMessageLite.emptyProtobufList();
    private String name_ = "";
    private ProtobufList<String> oneofs_ = GeneratedMessageLite.emptyProtobufList();
    private ProtobufList<Option> options_ = GeneratedMessageLite.emptyProtobufList();
    private SourceContext sourceContext_;
    private int syntax_;

    /* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<Type, Builder> implements TypeOrBuilder {
        private Builder() {
            super(Type.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((Type) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((Type) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((Type) this.instance).setName(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Type) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((Type) this.instance).setNameBytes(byteString);
            return this;
        }

        public List<Field> getFieldsList() {
            return Collections.unmodifiableList(((Type) this.instance).getFieldsList());
        }

        public int getFieldsCount() {
            return ((Type) this.instance).getFieldsCount();
        }

        public Field getFields(int i) {
            return ((Type) this.instance).getFields(i);
        }

        public Builder setFields(int i, Field field) {
            copyOnWrite();
            ((Type) this.instance).setFields(i, field);
            return this;
        }

        public Builder setFields(int i, com.google.protobuf.Field.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).setFields(i, builder);
            return this;
        }

        public Builder addFields(Field field) {
            copyOnWrite();
            ((Type) this.instance).addFields(field);
            return this;
        }

        public Builder addFields(int i, Field field) {
            copyOnWrite();
            ((Type) this.instance).addFields(i, field);
            return this;
        }

        public Builder addFields(com.google.protobuf.Field.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).addFields(builder);
            return this;
        }

        public Builder addFields(int i, com.google.protobuf.Field.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).addFields(i, builder);
            return this;
        }

        public Builder addAllFields(Iterable<? extends Field> iterable) {
            copyOnWrite();
            ((Type) this.instance).addAllFields(iterable);
            return this;
        }

        public Builder clearFields() {
            copyOnWrite();
            ((Type) this.instance).clearFields();
            return this;
        }

        public Builder removeFields(int i) {
            copyOnWrite();
            ((Type) this.instance).removeFields(i);
            return this;
        }

        public List<String> getOneofsList() {
            return Collections.unmodifiableList(((Type) this.instance).getOneofsList());
        }

        public int getOneofsCount() {
            return ((Type) this.instance).getOneofsCount();
        }

        public String getOneofs(int i) {
            return ((Type) this.instance).getOneofs(i);
        }

        public ByteString getOneofsBytes(int i) {
            return ((Type) this.instance).getOneofsBytes(i);
        }

        public Builder setOneofs(int i, String str) {
            copyOnWrite();
            ((Type) this.instance).setOneofs(i, str);
            return this;
        }

        public Builder addOneofs(String str) {
            copyOnWrite();
            ((Type) this.instance).addOneofs(str);
            return this;
        }

        public Builder addAllOneofs(Iterable<String> iterable) {
            copyOnWrite();
            ((Type) this.instance).addAllOneofs(iterable);
            return this;
        }

        public Builder clearOneofs() {
            copyOnWrite();
            ((Type) this.instance).clearOneofs();
            return this;
        }

        public Builder addOneofsBytes(ByteString byteString) {
            copyOnWrite();
            ((Type) this.instance).addOneofsBytes(byteString);
            return this;
        }

        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Type) this.instance).getOptionsList());
        }

        public int getOptionsCount() {
            return ((Type) this.instance).getOptionsCount();
        }

        public Option getOptions(int i) {
            return ((Type) this.instance).getOptions(i);
        }

        public Builder setOptions(int i, Option option) {
            copyOnWrite();
            ((Type) this.instance).setOptions(i, option);
            return this;
        }

        public Builder setOptions(int i, com.google.protobuf.Option.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).setOptions(i, builder);
            return this;
        }

        public Builder addOptions(Option option) {
            copyOnWrite();
            ((Type) this.instance).addOptions(option);
            return this;
        }

        public Builder addOptions(int i, Option option) {
            copyOnWrite();
            ((Type) this.instance).addOptions(i, option);
            return this;
        }

        public Builder addOptions(com.google.protobuf.Option.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).addOptions(builder);
            return this;
        }

        public Builder addOptions(int i, com.google.protobuf.Option.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).addOptions(i, builder);
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            copyOnWrite();
            ((Type) this.instance).addAllOptions(iterable);
            return this;
        }

        public Builder clearOptions() {
            copyOnWrite();
            ((Type) this.instance).clearOptions();
            return this;
        }

        public Builder removeOptions(int i) {
            copyOnWrite();
            ((Type) this.instance).removeOptions(i);
            return this;
        }

        public boolean hasSourceContext() {
            return ((Type) this.instance).hasSourceContext();
        }

        public SourceContext getSourceContext() {
            return ((Type) this.instance).getSourceContext();
        }

        public Builder setSourceContext(SourceContext sourceContext) {
            copyOnWrite();
            ((Type) this.instance).setSourceContext(sourceContext);
            return this;
        }

        public Builder setSourceContext(com.google.protobuf.SourceContext.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).setSourceContext(builder);
            return this;
        }

        public Builder mergeSourceContext(SourceContext sourceContext) {
            copyOnWrite();
            ((Type) this.instance).mergeSourceContext(sourceContext);
            return this;
        }

        public Builder clearSourceContext() {
            copyOnWrite();
            ((Type) this.instance).clearSourceContext();
            return this;
        }

        public int getSyntaxValue() {
            return ((Type) this.instance).getSyntaxValue();
        }

        public Builder setSyntaxValue(int i) {
            copyOnWrite();
            ((Type) this.instance).setSyntaxValue(i);
            return this;
        }

        public Syntax getSyntax() {
            return ((Type) this.instance).getSyntax();
        }

        public Builder setSyntax(Syntax syntax) {
            copyOnWrite();
            ((Type) this.instance).setSyntax(syntax);
            return this;
        }

        public Builder clearSyntax() {
            copyOnWrite();
            ((Type) this.instance).clearSyntax();
            return this;
        }
    }

    private Type() {
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

    public List<Field> getFieldsList() {
        return this.fields_;
    }

    public List<? extends FieldOrBuilder> getFieldsOrBuilderList() {
        return this.fields_;
    }

    public int getFieldsCount() {
        return this.fields_.size();
    }

    public Field getFields(int i) {
        return (Field) this.fields_.get(i);
    }

    public FieldOrBuilder getFieldsOrBuilder(int i) {
        return (FieldOrBuilder) this.fields_.get(i);
    }

    private void ensureFieldsIsMutable() {
        if (!this.fields_.isModifiable()) {
            this.fields_ = GeneratedMessageLite.mutableCopy(this.fields_);
        }
    }

    private void setFields(int i, Field field) {
        if (field != null) {
            ensureFieldsIsMutable();
            this.fields_.set(i, field);
            return;
        }
        throw new NullPointerException();
    }

    private void setFields(int i, com.google.protobuf.Field.Builder builder) {
        ensureFieldsIsMutable();
        this.fields_.set(i, (Field) builder.build());
    }

    private void addFields(Field field) {
        if (field != null) {
            ensureFieldsIsMutable();
            this.fields_.add(field);
            return;
        }
        throw new NullPointerException();
    }

    private void addFields(int i, Field field) {
        if (field != null) {
            ensureFieldsIsMutable();
            this.fields_.add(i, field);
            return;
        }
        throw new NullPointerException();
    }

    private void addFields(com.google.protobuf.Field.Builder builder) {
        ensureFieldsIsMutable();
        this.fields_.add((Field) builder.build());
    }

    private void addFields(int i, com.google.protobuf.Field.Builder builder) {
        ensureFieldsIsMutable();
        this.fields_.add(i, (Field) builder.build());
    }

    private void addAllFields(Iterable<? extends Field> iterable) {
        ensureFieldsIsMutable();
        AbstractMessageLite.addAll(iterable, this.fields_);
    }

    private void clearFields() {
        this.fields_ = GeneratedMessageLite.emptyProtobufList();
    }

    private void removeFields(int i) {
        ensureFieldsIsMutable();
        this.fields_.remove(i);
    }

    public List<String> getOneofsList() {
        return this.oneofs_;
    }

    public int getOneofsCount() {
        return this.oneofs_.size();
    }

    public String getOneofs(int i) {
        return (String) this.oneofs_.get(i);
    }

    public ByteString getOneofsBytes(int i) {
        return ByteString.copyFromUtf8((String) this.oneofs_.get(i));
    }

    private void ensureOneofsIsMutable() {
        if (!this.oneofs_.isModifiable()) {
            this.oneofs_ = GeneratedMessageLite.mutableCopy(this.oneofs_);
        }
    }

    private void setOneofs(int i, String str) {
        if (str != null) {
            ensureOneofsIsMutable();
            this.oneofs_.set(i, str);
            return;
        }
        throw new NullPointerException();
    }

    private void addOneofs(String str) {
        if (str != null) {
            ensureOneofsIsMutable();
            this.oneofs_.add(str);
            return;
        }
        throw new NullPointerException();
    }

    private void addAllOneofs(Iterable<String> iterable) {
        ensureOneofsIsMutable();
        AbstractMessageLite.addAll(iterable, this.oneofs_);
    }

    private void clearOneofs() {
        this.oneofs_ = GeneratedMessageLite.emptyProtobufList();
    }

    private void addOneofsBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            ensureOneofsIsMutable();
            this.oneofs_.add(byteString.toStringUtf8());
            return;
        }
        throw new NullPointerException();
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

    public boolean hasSourceContext() {
        return this.sourceContext_ != null;
    }

    public SourceContext getSourceContext() {
        SourceContext sourceContext = this.sourceContext_;
        return sourceContext == null ? SourceContext.getDefaultInstance() : sourceContext;
    }

    private void setSourceContext(SourceContext sourceContext) {
        if (sourceContext != null) {
            this.sourceContext_ = sourceContext;
            return;
        }
        throw new NullPointerException();
    }

    private void setSourceContext(com.google.protobuf.SourceContext.Builder builder) {
        this.sourceContext_ = (SourceContext) builder.build();
    }

    private void mergeSourceContext(SourceContext sourceContext) {
        SourceContext sourceContext2 = this.sourceContext_;
        if (sourceContext2 == null || sourceContext2 == SourceContext.getDefaultInstance()) {
            this.sourceContext_ = sourceContext;
        } else {
            this.sourceContext_ = (SourceContext) ((com.google.protobuf.SourceContext.Builder) SourceContext.newBuilder(this.sourceContext_).mergeFrom(sourceContext)).buildPartial();
        }
    }

    private void clearSourceContext() {
        this.sourceContext_ = null;
    }

    public int getSyntaxValue() {
        return this.syntax_;
    }

    public Syntax getSyntax() {
        Syntax forNumber = Syntax.forNumber(this.syntax_);
        return forNumber == null ? Syntax.UNRECOGNIZED : forNumber;
    }

    private void setSyntaxValue(int i) {
        this.syntax_ = i;
    }

    private void setSyntax(Syntax syntax) {
        if (syntax != null) {
            this.syntax_ = syntax.getNumber();
            return;
        }
        throw new NullPointerException();
    }

    private void clearSyntax() {
        this.syntax_ = 0;
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        int i;
        if (!this.name_.isEmpty()) {
            codedOutputStream.writeString(1, getName());
        }
        for (i = 0; i < this.fields_.size(); i++) {
            codedOutputStream.writeMessage(2, (MessageLite) this.fields_.get(i));
        }
        for (i = 0; i < this.oneofs_.size(); i++) {
            codedOutputStream.writeString(3, (String) this.oneofs_.get(i));
        }
        for (int i2 = 0; i2 < this.options_.size(); i2++) {
            codedOutputStream.writeMessage(4, (MessageLite) this.options_.get(i2));
        }
        if (this.sourceContext_ != null) {
            codedOutputStream.writeMessage(5, getSourceContext());
        }
        if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
            codedOutputStream.writeEnum(6, this.syntax_);
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        int computeStringSize = !this.name_.isEmpty() ? CodedOutputStream.computeStringSize(1, getName()) + 0 : 0;
        for (i = 0; i < this.fields_.size(); i++) {
            computeStringSize += CodedOutputStream.computeMessageSize(2, (MessageLite) this.fields_.get(i));
        }
        int i3 = 0;
        for (i = 0; i < this.oneofs_.size(); i++) {
            i3 += CodedOutputStream.computeStringSizeNoTag((String) this.oneofs_.get(i));
        }
        computeStringSize = (computeStringSize + i3) + (getOneofsList().size() * 1);
        while (i2 < this.options_.size()) {
            computeStringSize += CodedOutputStream.computeMessageSize(4, (MessageLite) this.options_.get(i2));
            i2++;
        }
        if (this.sourceContext_ != null) {
            computeStringSize += CodedOutputStream.computeMessageSize(5, getSourceContext());
        }
        if (this.syntax_ != Syntax.SYNTAX_PROTO2.getNumber()) {
            computeStringSize += CodedOutputStream.computeEnumSize(6, this.syntax_);
        }
        this.memoizedSerializedSize = computeStringSize;
        return computeStringSize;
    }

    public static Type parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static Type parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Type parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static Type parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Type parseFrom(InputStream inputStream) throws IOException {
        return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Type parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Type parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Type) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Type parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Type) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Type parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Type parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Type) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Type type) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(type);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Type();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.fields_.makeImmutable();
                this.oneofs_.makeImmutable();
                this.options_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                Type type = (Type) obj2;
                this.name_ = visitor.visitString(this.name_.isEmpty() ^ true, this.name_, type.name_.isEmpty() ^ true, type.name_);
                this.fields_ = visitor.visitList(this.fields_, type.fields_);
                this.oneofs_ = visitor.visitList(this.oneofs_, type.oneofs_);
                this.options_ = visitor.visitList(this.options_, type.options_);
                this.sourceContext_ = (SourceContext) visitor.visitMessage(this.sourceContext_, type.sourceContext_);
                boolean z2 = this.syntax_ != 0;
                int i = this.syntax_;
                if (type.syntax_ != 0) {
                    z = true;
                }
                this.syntax_ = visitor.visitInt(z2, i, z, type.syntax_);
                if (visitor == MergeFromVisitor.INSTANCE) {
                    this.bitField0_ |= type.bitField0_;
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
                            } else if (readTag == 18) {
                                if (!this.fields_.isModifiable()) {
                                    this.fields_ = GeneratedMessageLite.mutableCopy(this.fields_);
                                }
                                this.fields_.add((Field) codedInputStream.readMessage(Field.parser(), extensionRegistryLite));
                            } else if (readTag == 26) {
                                String readStringRequireUtf8 = codedInputStream.readStringRequireUtf8();
                                if (!this.oneofs_.isModifiable()) {
                                    this.oneofs_ = GeneratedMessageLite.mutableCopy(this.oneofs_);
                                }
                                this.oneofs_.add(readStringRequireUtf8);
                            } else if (readTag == 34) {
                                if (!this.options_.isModifiable()) {
                                    this.options_ = GeneratedMessageLite.mutableCopy(this.options_);
                                }
                                this.options_.add((Option) codedInputStream.readMessage(Option.parser(), extensionRegistryLite));
                            } else if (readTag == 42) {
                                com.google.protobuf.SourceContext.Builder builder = this.sourceContext_ != null ? (com.google.protobuf.SourceContext.Builder) this.sourceContext_.toBuilder() : null;
                                this.sourceContext_ = (SourceContext) codedInputStream.readMessage(SourceContext.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom(this.sourceContext_);
                                    this.sourceContext_ = (SourceContext) builder.buildPartial();
                                }
                            } else if (readTag == 48) {
                                this.syntax_ = codedInputStream.readEnum();
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
                    synchronized (Type.class) {
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

    public static Type getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Type> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
