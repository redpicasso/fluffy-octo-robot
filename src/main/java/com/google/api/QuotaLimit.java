package com.google.api;

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
import com.google.protobuf.WireFormat.FieldType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public final class QuotaLimit extends GeneratedMessageLite<QuotaLimit, Builder> implements QuotaLimitOrBuilder {
    private static final QuotaLimit DEFAULT_INSTANCE = new QuotaLimit();
    public static final int DEFAULT_LIMIT_FIELD_NUMBER = 3;
    public static final int DESCRIPTION_FIELD_NUMBER = 2;
    public static final int DISPLAY_NAME_FIELD_NUMBER = 12;
    public static final int DURATION_FIELD_NUMBER = 5;
    public static final int FREE_TIER_FIELD_NUMBER = 7;
    public static final int MAX_LIMIT_FIELD_NUMBER = 4;
    public static final int METRIC_FIELD_NUMBER = 8;
    public static final int NAME_FIELD_NUMBER = 6;
    private static volatile Parser<QuotaLimit> PARSER = null;
    public static final int UNIT_FIELD_NUMBER = 9;
    public static final int VALUES_FIELD_NUMBER = 10;
    private int bitField0_;
    private long defaultLimit_;
    private String description_;
    private String displayName_;
    private String duration_;
    private long freeTier_;
    private long maxLimit_;
    private String metric_;
    private String name_;
    private String unit_;
    private MapFieldLite<String, Long> values_ = MapFieldLite.emptyMapField();

    /* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
    private static final class ValuesDefaultEntryHolder {
        static final MapEntryLite<String, Long> defaultEntry = MapEntryLite.newDefaultInstance(FieldType.STRING, "", FieldType.INT64, Long.valueOf(0));

        private ValuesDefaultEntryHolder() {
        }
    }

    /* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<QuotaLimit, Builder> implements QuotaLimitOrBuilder {
        private Builder() {
            super(QuotaLimit.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((QuotaLimit) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((QuotaLimit) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setName(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((QuotaLimit) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setNameBytes(byteString);
            return this;
        }

        public String getDescription() {
            return ((QuotaLimit) this.instance).getDescription();
        }

        public ByteString getDescriptionBytes() {
            return ((QuotaLimit) this.instance).getDescriptionBytes();
        }

        public Builder setDescription(String str) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setDescription(str);
            return this;
        }

        public Builder clearDescription() {
            copyOnWrite();
            ((QuotaLimit) this.instance).clearDescription();
            return this;
        }

        public Builder setDescriptionBytes(ByteString byteString) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setDescriptionBytes(byteString);
            return this;
        }

        public long getDefaultLimit() {
            return ((QuotaLimit) this.instance).getDefaultLimit();
        }

        public Builder setDefaultLimit(long j) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setDefaultLimit(j);
            return this;
        }

        public Builder clearDefaultLimit() {
            copyOnWrite();
            ((QuotaLimit) this.instance).clearDefaultLimit();
            return this;
        }

        public long getMaxLimit() {
            return ((QuotaLimit) this.instance).getMaxLimit();
        }

        public Builder setMaxLimit(long j) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setMaxLimit(j);
            return this;
        }

        public Builder clearMaxLimit() {
            copyOnWrite();
            ((QuotaLimit) this.instance).clearMaxLimit();
            return this;
        }

        public long getFreeTier() {
            return ((QuotaLimit) this.instance).getFreeTier();
        }

        public Builder setFreeTier(long j) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setFreeTier(j);
            return this;
        }

        public Builder clearFreeTier() {
            copyOnWrite();
            ((QuotaLimit) this.instance).clearFreeTier();
            return this;
        }

        public String getDuration() {
            return ((QuotaLimit) this.instance).getDuration();
        }

        public ByteString getDurationBytes() {
            return ((QuotaLimit) this.instance).getDurationBytes();
        }

        public Builder setDuration(String str) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setDuration(str);
            return this;
        }

        public Builder clearDuration() {
            copyOnWrite();
            ((QuotaLimit) this.instance).clearDuration();
            return this;
        }

        public Builder setDurationBytes(ByteString byteString) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setDurationBytes(byteString);
            return this;
        }

        public String getMetric() {
            return ((QuotaLimit) this.instance).getMetric();
        }

        public ByteString getMetricBytes() {
            return ((QuotaLimit) this.instance).getMetricBytes();
        }

        public Builder setMetric(String str) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setMetric(str);
            return this;
        }

        public Builder clearMetric() {
            copyOnWrite();
            ((QuotaLimit) this.instance).clearMetric();
            return this;
        }

        public Builder setMetricBytes(ByteString byteString) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setMetricBytes(byteString);
            return this;
        }

        public String getUnit() {
            return ((QuotaLimit) this.instance).getUnit();
        }

        public ByteString getUnitBytes() {
            return ((QuotaLimit) this.instance).getUnitBytes();
        }

        public Builder setUnit(String str) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setUnit(str);
            return this;
        }

        public Builder clearUnit() {
            copyOnWrite();
            ((QuotaLimit) this.instance).clearUnit();
            return this;
        }

        public Builder setUnitBytes(ByteString byteString) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setUnitBytes(byteString);
            return this;
        }

        public int getValuesCount() {
            return ((QuotaLimit) this.instance).getValuesMap().size();
        }

        public boolean containsValues(String str) {
            if (str != null) {
                return ((QuotaLimit) this.instance).getValuesMap().containsKey(str);
            }
            throw new NullPointerException();
        }

        public Builder clearValues() {
            copyOnWrite();
            ((QuotaLimit) this.instance).getMutableValuesMap().clear();
            return this;
        }

        public Builder removeValues(String str) {
            if (str != null) {
                copyOnWrite();
                ((QuotaLimit) this.instance).getMutableValuesMap().remove(str);
                return this;
            }
            throw new NullPointerException();
        }

        @Deprecated
        public Map<String, Long> getValues() {
            return getValuesMap();
        }

        public Map<String, Long> getValuesMap() {
            return Collections.unmodifiableMap(((QuotaLimit) this.instance).getValuesMap());
        }

        public long getValuesOrDefault(String str, long j) {
            if (str != null) {
                Map valuesMap = ((QuotaLimit) this.instance).getValuesMap();
                return valuesMap.containsKey(str) ? ((Long) valuesMap.get(str)).longValue() : j;
            } else {
                throw new NullPointerException();
            }
        }

        public long getValuesOrThrow(String str) {
            if (str != null) {
                Map valuesMap = ((QuotaLimit) this.instance).getValuesMap();
                if (valuesMap.containsKey(str)) {
                    return ((Long) valuesMap.get(str)).longValue();
                }
                throw new IllegalArgumentException();
            }
            throw new NullPointerException();
        }

        public Builder putValues(String str, long j) {
            if (str != null) {
                copyOnWrite();
                ((QuotaLimit) this.instance).getMutableValuesMap().put(str, Long.valueOf(j));
                return this;
            }
            throw new NullPointerException();
        }

        public Builder putAllValues(Map<String, Long> map) {
            copyOnWrite();
            ((QuotaLimit) this.instance).getMutableValuesMap().putAll(map);
            return this;
        }

        public String getDisplayName() {
            return ((QuotaLimit) this.instance).getDisplayName();
        }

        public ByteString getDisplayNameBytes() {
            return ((QuotaLimit) this.instance).getDisplayNameBytes();
        }

        public Builder setDisplayName(String str) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setDisplayName(str);
            return this;
        }

        public Builder clearDisplayName() {
            copyOnWrite();
            ((QuotaLimit) this.instance).clearDisplayName();
            return this;
        }

        public Builder setDisplayNameBytes(ByteString byteString) {
            copyOnWrite();
            ((QuotaLimit) this.instance).setDisplayNameBytes(byteString);
            return this;
        }
    }

    private QuotaLimit() {
        String str = "";
        this.name_ = str;
        this.description_ = str;
        this.duration_ = str;
        this.metric_ = str;
        this.unit_ = str;
        this.displayName_ = str;
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

    public String getDescription() {
        return this.description_;
    }

    public ByteString getDescriptionBytes() {
        return ByteString.copyFromUtf8(this.description_);
    }

    private void setDescription(String str) {
        if (str != null) {
            this.description_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearDescription() {
        this.description_ = getDefaultInstance().getDescription();
    }

    private void setDescriptionBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.description_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public long getDefaultLimit() {
        return this.defaultLimit_;
    }

    private void setDefaultLimit(long j) {
        this.defaultLimit_ = j;
    }

    private void clearDefaultLimit() {
        this.defaultLimit_ = 0;
    }

    public long getMaxLimit() {
        return this.maxLimit_;
    }

    private void setMaxLimit(long j) {
        this.maxLimit_ = j;
    }

    private void clearMaxLimit() {
        this.maxLimit_ = 0;
    }

    public long getFreeTier() {
        return this.freeTier_;
    }

    private void setFreeTier(long j) {
        this.freeTier_ = j;
    }

    private void clearFreeTier() {
        this.freeTier_ = 0;
    }

    public String getDuration() {
        return this.duration_;
    }

    public ByteString getDurationBytes() {
        return ByteString.copyFromUtf8(this.duration_);
    }

    private void setDuration(String str) {
        if (str != null) {
            this.duration_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearDuration() {
        this.duration_ = getDefaultInstance().getDuration();
    }

    private void setDurationBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.duration_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public String getMetric() {
        return this.metric_;
    }

    public ByteString getMetricBytes() {
        return ByteString.copyFromUtf8(this.metric_);
    }

    private void setMetric(String str) {
        if (str != null) {
            this.metric_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearMetric() {
        this.metric_ = getDefaultInstance().getMetric();
    }

    private void setMetricBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.metric_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public String getUnit() {
        return this.unit_;
    }

    public ByteString getUnitBytes() {
        return ByteString.copyFromUtf8(this.unit_);
    }

    private void setUnit(String str) {
        if (str != null) {
            this.unit_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearUnit() {
        this.unit_ = getDefaultInstance().getUnit();
    }

    private void setUnitBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.unit_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    private MapFieldLite<String, Long> internalGetValues() {
        return this.values_;
    }

    private MapFieldLite<String, Long> internalGetMutableValues() {
        if (!this.values_.isMutable()) {
            this.values_ = this.values_.mutableCopy();
        }
        return this.values_;
    }

    public int getValuesCount() {
        return internalGetValues().size();
    }

    public boolean containsValues(String str) {
        if (str != null) {
            return internalGetValues().containsKey(str);
        }
        throw new NullPointerException();
    }

    @Deprecated
    public Map<String, Long> getValues() {
        return getValuesMap();
    }

    public Map<String, Long> getValuesMap() {
        return Collections.unmodifiableMap(internalGetValues());
    }

    public long getValuesOrDefault(String str, long j) {
        if (str != null) {
            Map internalGetValues = internalGetValues();
            return internalGetValues.containsKey(str) ? ((Long) internalGetValues.get(str)).longValue() : j;
        } else {
            throw new NullPointerException();
        }
    }

    public long getValuesOrThrow(String str) {
        if (str != null) {
            Map internalGetValues = internalGetValues();
            if (internalGetValues.containsKey(str)) {
                return ((Long) internalGetValues.get(str)).longValue();
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    private Map<String, Long> getMutableValuesMap() {
        return internalGetMutableValues();
    }

    public String getDisplayName() {
        return this.displayName_;
    }

    public ByteString getDisplayNameBytes() {
        return ByteString.copyFromUtf8(this.displayName_);
    }

    private void setDisplayName(String str) {
        if (str != null) {
            this.displayName_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearDisplayName() {
        this.displayName_ = getDefaultInstance().getDisplayName();
    }

    private void setDisplayNameBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.displayName_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (!this.description_.isEmpty()) {
            codedOutputStream.writeString(2, getDescription());
        }
        long j = this.defaultLimit_;
        if (j != 0) {
            codedOutputStream.writeInt64(3, j);
        }
        j = this.maxLimit_;
        if (j != 0) {
            codedOutputStream.writeInt64(4, j);
        }
        if (!this.duration_.isEmpty()) {
            codedOutputStream.writeString(5, getDuration());
        }
        if (!this.name_.isEmpty()) {
            codedOutputStream.writeString(6, getName());
        }
        j = this.freeTier_;
        if (j != 0) {
            codedOutputStream.writeInt64(7, j);
        }
        if (!this.metric_.isEmpty()) {
            codedOutputStream.writeString(8, getMetric());
        }
        if (!this.unit_.isEmpty()) {
            codedOutputStream.writeString(9, getUnit());
        }
        for (Entry entry : internalGetValues().entrySet()) {
            ValuesDefaultEntryHolder.defaultEntry.serializeTo(codedOutputStream, 10, (String) entry.getKey(), (Long) entry.getValue());
        }
        if (!this.displayName_.isEmpty()) {
            codedOutputStream.writeString(12, getDisplayName());
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (!this.description_.isEmpty()) {
            i = 0 + CodedOutputStream.computeStringSize(2, getDescription());
        }
        long j = this.defaultLimit_;
        if (j != 0) {
            i += CodedOutputStream.computeInt64Size(3, j);
        }
        j = this.maxLimit_;
        if (j != 0) {
            i += CodedOutputStream.computeInt64Size(4, j);
        }
        if (!this.duration_.isEmpty()) {
            i += CodedOutputStream.computeStringSize(5, getDuration());
        }
        if (!this.name_.isEmpty()) {
            i += CodedOutputStream.computeStringSize(6, getName());
        }
        j = this.freeTier_;
        if (j != 0) {
            i += CodedOutputStream.computeInt64Size(7, j);
        }
        if (!this.metric_.isEmpty()) {
            i += CodedOutputStream.computeStringSize(8, getMetric());
        }
        if (!this.unit_.isEmpty()) {
            i += CodedOutputStream.computeStringSize(9, getUnit());
        }
        for (Entry entry : internalGetValues().entrySet()) {
            i += ValuesDefaultEntryHolder.defaultEntry.computeMessageSize(10, (String) entry.getKey(), (Long) entry.getValue());
        }
        if (!this.displayName_.isEmpty()) {
            i += CodedOutputStream.computeStringSize(12, getDisplayName());
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static QuotaLimit parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (QuotaLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static QuotaLimit parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (QuotaLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static QuotaLimit parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (QuotaLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static QuotaLimit parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (QuotaLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static QuotaLimit parseFrom(InputStream inputStream) throws IOException {
        return (QuotaLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static QuotaLimit parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (QuotaLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static QuotaLimit parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (QuotaLimit) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static QuotaLimit parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (QuotaLimit) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static QuotaLimit parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (QuotaLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static QuotaLimit parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (QuotaLimit) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(QuotaLimit quotaLimit) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(quotaLimit);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        Object obj3 = null;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new QuotaLimit();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.values_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                QuotaLimit quotaLimit = (QuotaLimit) obj2;
                this.name_ = visitor.visitString(this.name_.isEmpty() ^ true, this.name_, quotaLimit.name_.isEmpty() ^ true, quotaLimit.name_);
                this.description_ = visitor.visitString(this.description_.isEmpty() ^ true, this.description_, quotaLimit.description_.isEmpty() ^ true, quotaLimit.description_);
                this.defaultLimit_ = visitor.visitLong(this.defaultLimit_ != 0, this.defaultLimit_, quotaLimit.defaultLimit_ != 0, quotaLimit.defaultLimit_);
                this.maxLimit_ = visitor.visitLong(this.maxLimit_ != 0, this.maxLimit_, quotaLimit.maxLimit_ != 0, quotaLimit.maxLimit_);
                this.freeTier_ = visitor.visitLong(this.freeTier_ != 0, this.freeTier_, quotaLimit.freeTier_ != 0, quotaLimit.freeTier_);
                this.duration_ = visitor.visitString(this.duration_.isEmpty() ^ true, this.duration_, quotaLimit.duration_.isEmpty() ^ true, quotaLimit.duration_);
                this.metric_ = visitor.visitString(this.metric_.isEmpty() ^ true, this.metric_, quotaLimit.metric_.isEmpty() ^ true, quotaLimit.metric_);
                this.unit_ = visitor.visitString(this.unit_.isEmpty() ^ true, this.unit_, quotaLimit.unit_.isEmpty() ^ true, quotaLimit.unit_);
                this.values_ = visitor.visitMap(this.values_, quotaLimit.internalGetValues());
                this.displayName_ = visitor.visitString(this.displayName_.isEmpty() ^ true, this.displayName_, quotaLimit.displayName_.isEmpty() ^ true, quotaLimit.displayName_);
                if (visitor == MergeFromVisitor.INSTANCE) {
                    this.bitField0_ |= quotaLimit.bitField0_;
                }
                return this;
            case MERGE_FROM_STREAM:
                CodedInputStream codedInputStream = (CodedInputStream) obj;
                ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                while (obj3 == null) {
                    try {
                        int readTag = codedInputStream.readTag();
                        switch (readTag) {
                            case 0:
                                obj3 = 1;
                                break;
                            case 18:
                                this.description_ = codedInputStream.readStringRequireUtf8();
                                break;
                            case 24:
                                this.defaultLimit_ = codedInputStream.readInt64();
                                break;
                            case 32:
                                this.maxLimit_ = codedInputStream.readInt64();
                                break;
                            case 42:
                                this.duration_ = codedInputStream.readStringRequireUtf8();
                                break;
                            case 50:
                                this.name_ = codedInputStream.readStringRequireUtf8();
                                break;
                            case 56:
                                this.freeTier_ = codedInputStream.readInt64();
                                break;
                            case 66:
                                this.metric_ = codedInputStream.readStringRequireUtf8();
                                break;
                            case 74:
                                this.unit_ = codedInputStream.readStringRequireUtf8();
                                break;
                            case 82:
                                if (!this.values_.isMutable()) {
                                    this.values_ = this.values_.mutableCopy();
                                }
                                ValuesDefaultEntryHolder.defaultEntry.parseInto(this.values_, codedInputStream, extensionRegistryLite);
                                break;
                            case 98:
                                this.displayName_ = codedInputStream.readStringRequireUtf8();
                                break;
                            default:
                                if (codedInputStream.skipField(readTag)) {
                                    break;
                                }
                                obj3 = 1;
                                break;
                        }
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
                    synchronized (QuotaLimit.class) {
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

    public static QuotaLimit getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<QuotaLimit> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
