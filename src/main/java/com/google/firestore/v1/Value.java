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
import com.google.protobuf.NullValue;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import com.google.type.LatLng;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Value extends GeneratedMessageLite<Value, Builder> implements ValueOrBuilder {
    public static final int ARRAY_VALUE_FIELD_NUMBER = 9;
    public static final int BOOLEAN_VALUE_FIELD_NUMBER = 1;
    public static final int BYTES_VALUE_FIELD_NUMBER = 18;
    private static final Value DEFAULT_INSTANCE = new Value();
    public static final int DOUBLE_VALUE_FIELD_NUMBER = 3;
    public static final int GEO_POINT_VALUE_FIELD_NUMBER = 8;
    public static final int INTEGER_VALUE_FIELD_NUMBER = 2;
    public static final int MAP_VALUE_FIELD_NUMBER = 6;
    public static final int NULL_VALUE_FIELD_NUMBER = 11;
    private static volatile Parser<Value> PARSER = null;
    public static final int REFERENCE_VALUE_FIELD_NUMBER = 5;
    public static final int STRING_VALUE_FIELD_NUMBER = 17;
    public static final int TIMESTAMP_VALUE_FIELD_NUMBER = 10;
    private int valueTypeCase_ = 0;
    private Object valueType_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum ValueTypeCase implements EnumLite {
        NULL_VALUE(11),
        BOOLEAN_VALUE(1),
        INTEGER_VALUE(2),
        DOUBLE_VALUE(3),
        TIMESTAMP_VALUE(10),
        STRING_VALUE(17),
        BYTES_VALUE(18),
        REFERENCE_VALUE(5),
        GEO_POINT_VALUE(8),
        ARRAY_VALUE(9),
        MAP_VALUE(6),
        VALUETYPE_NOT_SET(0);
        
        private final int value;

        private ValueTypeCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static ValueTypeCase valueOf(int i) {
            return forNumber(i);
        }

        public static ValueTypeCase forNumber(int i) {
            if (i == 0) {
                return VALUETYPE_NOT_SET;
            }
            if (i == 1) {
                return BOOLEAN_VALUE;
            }
            if (i == 2) {
                return INTEGER_VALUE;
            }
            if (i == 3) {
                return DOUBLE_VALUE;
            }
            if (i == 5) {
                return REFERENCE_VALUE;
            }
            if (i == 6) {
                return MAP_VALUE;
            }
            if (i == 17) {
                return STRING_VALUE;
            }
            if (i == 18) {
                return BYTES_VALUE;
            }
            switch (i) {
                case 8:
                    return GEO_POINT_VALUE;
                case 9:
                    return ARRAY_VALUE;
                case 10:
                    return TIMESTAMP_VALUE;
                case 11:
                    return NULL_VALUE;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<Value, Builder> implements ValueOrBuilder {
        private Builder() {
            super(Value.DEFAULT_INSTANCE);
        }

        public ValueTypeCase getValueTypeCase() {
            return ((Value) this.instance).getValueTypeCase();
        }

        public Builder clearValueType() {
            copyOnWrite();
            ((Value) this.instance).clearValueType();
            return this;
        }

        public int getNullValueValue() {
            return ((Value) this.instance).getNullValueValue();
        }

        public Builder setNullValueValue(int i) {
            copyOnWrite();
            ((Value) this.instance).setNullValueValue(i);
            return this;
        }

        public NullValue getNullValue() {
            return ((Value) this.instance).getNullValue();
        }

        public Builder setNullValue(NullValue nullValue) {
            copyOnWrite();
            ((Value) this.instance).setNullValue(nullValue);
            return this;
        }

        public Builder clearNullValue() {
            copyOnWrite();
            ((Value) this.instance).clearNullValue();
            return this;
        }

        public boolean getBooleanValue() {
            return ((Value) this.instance).getBooleanValue();
        }

        public Builder setBooleanValue(boolean z) {
            copyOnWrite();
            ((Value) this.instance).setBooleanValue(z);
            return this;
        }

        public Builder clearBooleanValue() {
            copyOnWrite();
            ((Value) this.instance).clearBooleanValue();
            return this;
        }

        public long getIntegerValue() {
            return ((Value) this.instance).getIntegerValue();
        }

        public Builder setIntegerValue(long j) {
            copyOnWrite();
            ((Value) this.instance).setIntegerValue(j);
            return this;
        }

        public Builder clearIntegerValue() {
            copyOnWrite();
            ((Value) this.instance).clearIntegerValue();
            return this;
        }

        public double getDoubleValue() {
            return ((Value) this.instance).getDoubleValue();
        }

        public Builder setDoubleValue(double d) {
            copyOnWrite();
            ((Value) this.instance).setDoubleValue(d);
            return this;
        }

        public Builder clearDoubleValue() {
            copyOnWrite();
            ((Value) this.instance).clearDoubleValue();
            return this;
        }

        public Timestamp getTimestampValue() {
            return ((Value) this.instance).getTimestampValue();
        }

        public Builder setTimestampValue(Timestamp timestamp) {
            copyOnWrite();
            ((Value) this.instance).setTimestampValue(timestamp);
            return this;
        }

        public Builder setTimestampValue(com.google.protobuf.Timestamp.Builder builder) {
            copyOnWrite();
            ((Value) this.instance).setTimestampValue(builder);
            return this;
        }

        public Builder mergeTimestampValue(Timestamp timestamp) {
            copyOnWrite();
            ((Value) this.instance).mergeTimestampValue(timestamp);
            return this;
        }

        public Builder clearTimestampValue() {
            copyOnWrite();
            ((Value) this.instance).clearTimestampValue();
            return this;
        }

        public String getStringValue() {
            return ((Value) this.instance).getStringValue();
        }

        public ByteString getStringValueBytes() {
            return ((Value) this.instance).getStringValueBytes();
        }

        public Builder setStringValue(String str) {
            copyOnWrite();
            ((Value) this.instance).setStringValue(str);
            return this;
        }

        public Builder clearStringValue() {
            copyOnWrite();
            ((Value) this.instance).clearStringValue();
            return this;
        }

        public Builder setStringValueBytes(ByteString byteString) {
            copyOnWrite();
            ((Value) this.instance).setStringValueBytes(byteString);
            return this;
        }

        public ByteString getBytesValue() {
            return ((Value) this.instance).getBytesValue();
        }

        public Builder setBytesValue(ByteString byteString) {
            copyOnWrite();
            ((Value) this.instance).setBytesValue(byteString);
            return this;
        }

        public Builder clearBytesValue() {
            copyOnWrite();
            ((Value) this.instance).clearBytesValue();
            return this;
        }

        public String getReferenceValue() {
            return ((Value) this.instance).getReferenceValue();
        }

        public ByteString getReferenceValueBytes() {
            return ((Value) this.instance).getReferenceValueBytes();
        }

        public Builder setReferenceValue(String str) {
            copyOnWrite();
            ((Value) this.instance).setReferenceValue(str);
            return this;
        }

        public Builder clearReferenceValue() {
            copyOnWrite();
            ((Value) this.instance).clearReferenceValue();
            return this;
        }

        public Builder setReferenceValueBytes(ByteString byteString) {
            copyOnWrite();
            ((Value) this.instance).setReferenceValueBytes(byteString);
            return this;
        }

        public LatLng getGeoPointValue() {
            return ((Value) this.instance).getGeoPointValue();
        }

        public Builder setGeoPointValue(LatLng latLng) {
            copyOnWrite();
            ((Value) this.instance).setGeoPointValue(latLng);
            return this;
        }

        public Builder setGeoPointValue(com.google.type.LatLng.Builder builder) {
            copyOnWrite();
            ((Value) this.instance).setGeoPointValue(builder);
            return this;
        }

        public Builder mergeGeoPointValue(LatLng latLng) {
            copyOnWrite();
            ((Value) this.instance).mergeGeoPointValue(latLng);
            return this;
        }

        public Builder clearGeoPointValue() {
            copyOnWrite();
            ((Value) this.instance).clearGeoPointValue();
            return this;
        }

        public ArrayValue getArrayValue() {
            return ((Value) this.instance).getArrayValue();
        }

        public Builder setArrayValue(ArrayValue arrayValue) {
            copyOnWrite();
            ((Value) this.instance).setArrayValue(arrayValue);
            return this;
        }

        public Builder setArrayValue(com.google.firestore.v1.ArrayValue.Builder builder) {
            copyOnWrite();
            ((Value) this.instance).setArrayValue(builder);
            return this;
        }

        public Builder mergeArrayValue(ArrayValue arrayValue) {
            copyOnWrite();
            ((Value) this.instance).mergeArrayValue(arrayValue);
            return this;
        }

        public Builder clearArrayValue() {
            copyOnWrite();
            ((Value) this.instance).clearArrayValue();
            return this;
        }

        public MapValue getMapValue() {
            return ((Value) this.instance).getMapValue();
        }

        public Builder setMapValue(MapValue mapValue) {
            copyOnWrite();
            ((Value) this.instance).setMapValue(mapValue);
            return this;
        }

        public Builder setMapValue(com.google.firestore.v1.MapValue.Builder builder) {
            copyOnWrite();
            ((Value) this.instance).setMapValue(builder);
            return this;
        }

        public Builder mergeMapValue(MapValue mapValue) {
            copyOnWrite();
            ((Value) this.instance).mergeMapValue(mapValue);
            return this;
        }

        public Builder clearMapValue() {
            copyOnWrite();
            ((Value) this.instance).clearMapValue();
            return this;
        }
    }

    private Value() {
    }

    public ValueTypeCase getValueTypeCase() {
        return ValueTypeCase.forNumber(this.valueTypeCase_);
    }

    private void clearValueType() {
        this.valueTypeCase_ = 0;
        this.valueType_ = null;
    }

    public int getNullValueValue() {
        return this.valueTypeCase_ == 11 ? ((Integer) this.valueType_).intValue() : 0;
    }

    public NullValue getNullValue() {
        if (this.valueTypeCase_ != 11) {
            return NullValue.NULL_VALUE;
        }
        NullValue forNumber = NullValue.forNumber(((Integer) this.valueType_).intValue());
        if (forNumber == null) {
            forNumber = NullValue.UNRECOGNIZED;
        }
        return forNumber;
    }

    private void setNullValueValue(int i) {
        this.valueTypeCase_ = 11;
        this.valueType_ = Integer.valueOf(i);
    }

    private void setNullValue(NullValue nullValue) {
        if (nullValue != null) {
            this.valueTypeCase_ = 11;
            this.valueType_ = Integer.valueOf(nullValue.getNumber());
            return;
        }
        throw new NullPointerException();
    }

    private void clearNullValue() {
        if (this.valueTypeCase_ == 11) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public boolean getBooleanValue() {
        return this.valueTypeCase_ == 1 ? ((Boolean) this.valueType_).booleanValue() : false;
    }

    private void setBooleanValue(boolean z) {
        this.valueTypeCase_ = 1;
        this.valueType_ = Boolean.valueOf(z);
    }

    private void clearBooleanValue() {
        if (this.valueTypeCase_ == 1) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public long getIntegerValue() {
        return this.valueTypeCase_ == 2 ? ((Long) this.valueType_).longValue() : 0;
    }

    private void setIntegerValue(long j) {
        this.valueTypeCase_ = 2;
        this.valueType_ = Long.valueOf(j);
    }

    private void clearIntegerValue() {
        if (this.valueTypeCase_ == 2) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public double getDoubleValue() {
        return this.valueTypeCase_ == 3 ? ((Double) this.valueType_).doubleValue() : 0.0d;
    }

    private void setDoubleValue(double d) {
        this.valueTypeCase_ = 3;
        this.valueType_ = Double.valueOf(d);
    }

    private void clearDoubleValue() {
        if (this.valueTypeCase_ == 3) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public Timestamp getTimestampValue() {
        if (this.valueTypeCase_ == 10) {
            return (Timestamp) this.valueType_;
        }
        return Timestamp.getDefaultInstance();
    }

    private void setTimestampValue(Timestamp timestamp) {
        if (timestamp != null) {
            this.valueType_ = timestamp;
            this.valueTypeCase_ = 10;
            return;
        }
        throw new NullPointerException();
    }

    private void setTimestampValue(com.google.protobuf.Timestamp.Builder builder) {
        this.valueType_ = builder.build();
        this.valueTypeCase_ = 10;
    }

    private void mergeTimestampValue(Timestamp timestamp) {
        if (this.valueTypeCase_ != 10 || this.valueType_ == Timestamp.getDefaultInstance()) {
            this.valueType_ = timestamp;
        } else {
            this.valueType_ = ((com.google.protobuf.Timestamp.Builder) Timestamp.newBuilder((Timestamp) this.valueType_).mergeFrom(timestamp)).buildPartial();
        }
        this.valueTypeCase_ = 10;
    }

    private void clearTimestampValue() {
        if (this.valueTypeCase_ == 10) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public String getStringValue() {
        return this.valueTypeCase_ == 17 ? (String) this.valueType_ : "";
    }

    public ByteString getStringValueBytes() {
        return ByteString.copyFromUtf8(this.valueTypeCase_ == 17 ? (String) this.valueType_ : "");
    }

    private void setStringValue(String str) {
        if (str != null) {
            this.valueTypeCase_ = 17;
            this.valueType_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearStringValue() {
        if (this.valueTypeCase_ == 17) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    private void setStringValueBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.valueTypeCase_ = 17;
            this.valueType_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public ByteString getBytesValue() {
        if (this.valueTypeCase_ == 18) {
            return (ByteString) this.valueType_;
        }
        return ByteString.EMPTY;
    }

    private void setBytesValue(ByteString byteString) {
        if (byteString != null) {
            this.valueTypeCase_ = 18;
            this.valueType_ = byteString;
            return;
        }
        throw new NullPointerException();
    }

    private void clearBytesValue() {
        if (this.valueTypeCase_ == 18) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public String getReferenceValue() {
        return this.valueTypeCase_ == 5 ? (String) this.valueType_ : "";
    }

    public ByteString getReferenceValueBytes() {
        return ByteString.copyFromUtf8(this.valueTypeCase_ == 5 ? (String) this.valueType_ : "");
    }

    private void setReferenceValue(String str) {
        if (str != null) {
            this.valueTypeCase_ = 5;
            this.valueType_ = str;
            return;
        }
        throw new NullPointerException();
    }

    private void clearReferenceValue() {
        if (this.valueTypeCase_ == 5) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    private void setReferenceValueBytes(ByteString byteString) {
        if (byteString != null) {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.valueTypeCase_ = 5;
            this.valueType_ = byteString.toStringUtf8();
            return;
        }
        throw new NullPointerException();
    }

    public LatLng getGeoPointValue() {
        if (this.valueTypeCase_ == 8) {
            return (LatLng) this.valueType_;
        }
        return LatLng.getDefaultInstance();
    }

    private void setGeoPointValue(LatLng latLng) {
        if (latLng != null) {
            this.valueType_ = latLng;
            this.valueTypeCase_ = 8;
            return;
        }
        throw new NullPointerException();
    }

    private void setGeoPointValue(com.google.type.LatLng.Builder builder) {
        this.valueType_ = builder.build();
        this.valueTypeCase_ = 8;
    }

    private void mergeGeoPointValue(LatLng latLng) {
        if (this.valueTypeCase_ != 8 || this.valueType_ == LatLng.getDefaultInstance()) {
            this.valueType_ = latLng;
        } else {
            this.valueType_ = ((com.google.type.LatLng.Builder) LatLng.newBuilder((LatLng) this.valueType_).mergeFrom(latLng)).buildPartial();
        }
        this.valueTypeCase_ = 8;
    }

    private void clearGeoPointValue() {
        if (this.valueTypeCase_ == 8) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public ArrayValue getArrayValue() {
        if (this.valueTypeCase_ == 9) {
            return (ArrayValue) this.valueType_;
        }
        return ArrayValue.getDefaultInstance();
    }

    private void setArrayValue(ArrayValue arrayValue) {
        if (arrayValue != null) {
            this.valueType_ = arrayValue;
            this.valueTypeCase_ = 9;
            return;
        }
        throw new NullPointerException();
    }

    private void setArrayValue(com.google.firestore.v1.ArrayValue.Builder builder) {
        this.valueType_ = builder.build();
        this.valueTypeCase_ = 9;
    }

    private void mergeArrayValue(ArrayValue arrayValue) {
        if (this.valueTypeCase_ != 9 || this.valueType_ == ArrayValue.getDefaultInstance()) {
            this.valueType_ = arrayValue;
        } else {
            this.valueType_ = ((com.google.firestore.v1.ArrayValue.Builder) ArrayValue.newBuilder((ArrayValue) this.valueType_).mergeFrom(arrayValue)).buildPartial();
        }
        this.valueTypeCase_ = 9;
    }

    private void clearArrayValue() {
        if (this.valueTypeCase_ == 9) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public MapValue getMapValue() {
        if (this.valueTypeCase_ == 6) {
            return (MapValue) this.valueType_;
        }
        return MapValue.getDefaultInstance();
    }

    private void setMapValue(MapValue mapValue) {
        if (mapValue != null) {
            this.valueType_ = mapValue;
            this.valueTypeCase_ = 6;
            return;
        }
        throw new NullPointerException();
    }

    private void setMapValue(com.google.firestore.v1.MapValue.Builder builder) {
        this.valueType_ = builder.build();
        this.valueTypeCase_ = 6;
    }

    private void mergeMapValue(MapValue mapValue) {
        if (this.valueTypeCase_ != 6 || this.valueType_ == MapValue.getDefaultInstance()) {
            this.valueType_ = mapValue;
        } else {
            this.valueType_ = ((com.google.firestore.v1.MapValue.Builder) MapValue.newBuilder((MapValue) this.valueType_).mergeFrom(mapValue)).buildPartial();
        }
        this.valueTypeCase_ = 6;
    }

    private void clearMapValue() {
        if (this.valueTypeCase_ == 6) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (this.valueTypeCase_ == 1) {
            codedOutputStream.writeBool(1, ((Boolean) this.valueType_).booleanValue());
        }
        if (this.valueTypeCase_ == 2) {
            codedOutputStream.writeInt64(2, ((Long) this.valueType_).longValue());
        }
        if (this.valueTypeCase_ == 3) {
            codedOutputStream.writeDouble(3, ((Double) this.valueType_).doubleValue());
        }
        if (this.valueTypeCase_ == 5) {
            codedOutputStream.writeString(5, getReferenceValue());
        }
        if (this.valueTypeCase_ == 6) {
            codedOutputStream.writeMessage(6, (MapValue) this.valueType_);
        }
        if (this.valueTypeCase_ == 8) {
            codedOutputStream.writeMessage(8, (LatLng) this.valueType_);
        }
        if (this.valueTypeCase_ == 9) {
            codedOutputStream.writeMessage(9, (ArrayValue) this.valueType_);
        }
        if (this.valueTypeCase_ == 10) {
            codedOutputStream.writeMessage(10, (Timestamp) this.valueType_);
        }
        if (this.valueTypeCase_ == 11) {
            codedOutputStream.writeEnum(11, ((Integer) this.valueType_).intValue());
        }
        if (this.valueTypeCase_ == 17) {
            codedOutputStream.writeString(17, getStringValue());
        }
        if (this.valueTypeCase_ == 18) {
            codedOutputStream.writeBytes(18, (ByteString) this.valueType_);
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (this.valueTypeCase_ == 1) {
            i = 0 + CodedOutputStream.computeBoolSize(1, ((Boolean) this.valueType_).booleanValue());
        }
        if (this.valueTypeCase_ == 2) {
            i += CodedOutputStream.computeInt64Size(2, ((Long) this.valueType_).longValue());
        }
        if (this.valueTypeCase_ == 3) {
            i += CodedOutputStream.computeDoubleSize(3, ((Double) this.valueType_).doubleValue());
        }
        if (this.valueTypeCase_ == 5) {
            i += CodedOutputStream.computeStringSize(5, getReferenceValue());
        }
        if (this.valueTypeCase_ == 6) {
            i += CodedOutputStream.computeMessageSize(6, (MapValue) this.valueType_);
        }
        if (this.valueTypeCase_ == 8) {
            i += CodedOutputStream.computeMessageSize(8, (LatLng) this.valueType_);
        }
        if (this.valueTypeCase_ == 9) {
            i += CodedOutputStream.computeMessageSize(9, (ArrayValue) this.valueType_);
        }
        if (this.valueTypeCase_ == 10) {
            i += CodedOutputStream.computeMessageSize(10, (Timestamp) this.valueType_);
        }
        if (this.valueTypeCase_ == 11) {
            i += CodedOutputStream.computeEnumSize(11, ((Integer) this.valueType_).intValue());
        }
        if (this.valueTypeCase_ == 17) {
            i += CodedOutputStream.computeStringSize(17, getStringValue());
        }
        if (this.valueTypeCase_ == 18) {
            i += CodedOutputStream.computeBytesSize(18, (ByteString) this.valueType_);
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static Value parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static Value parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Value parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static Value parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Value parseFrom(InputStream inputStream) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Value parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Value parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Value) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Value parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Value parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Value parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Value value) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(value);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        int i = 11;
        boolean z = false;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Value();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                Value value = (Value) obj2;
                switch (value.getValueTypeCase()) {
                    case NULL_VALUE:
                        if (this.valueTypeCase_ == 11) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofInt(z, this.valueType_, value.valueType_);
                        break;
                    case BOOLEAN_VALUE:
                        if (this.valueTypeCase_ == 1) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofBoolean(z, this.valueType_, value.valueType_);
                        break;
                    case INTEGER_VALUE:
                        if (this.valueTypeCase_ == 2) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofLong(z, this.valueType_, value.valueType_);
                        break;
                    case DOUBLE_VALUE:
                        if (this.valueTypeCase_ == 3) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofDouble(z, this.valueType_, value.valueType_);
                        break;
                    case TIMESTAMP_VALUE:
                        if (this.valueTypeCase_ == 10) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofMessage(z, this.valueType_, value.valueType_);
                        break;
                    case STRING_VALUE:
                        if (this.valueTypeCase_ == 17) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofString(z, this.valueType_, value.valueType_);
                        break;
                    case BYTES_VALUE:
                        if (this.valueTypeCase_ == 18) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofByteString(z, this.valueType_, value.valueType_);
                        break;
                    case REFERENCE_VALUE:
                        if (this.valueTypeCase_ == 5) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofString(z, this.valueType_, value.valueType_);
                        break;
                    case GEO_POINT_VALUE:
                        if (this.valueTypeCase_ == 8) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofMessage(z, this.valueType_, value.valueType_);
                        break;
                    case ARRAY_VALUE:
                        if (this.valueTypeCase_ == 9) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofMessage(z, this.valueType_, value.valueType_);
                        break;
                    case MAP_VALUE:
                        if (this.valueTypeCase_ == 6) {
                            z = true;
                        }
                        this.valueType_ = visitor.visitOneofMessage(z, this.valueType_, value.valueType_);
                        break;
                    case VALUETYPE_NOT_SET:
                        if (this.valueTypeCase_ != 0) {
                            z = true;
                        }
                        visitor.visitOneofNotSet(z);
                        break;
                }
                if (visitor == MergeFromVisitor.INSTANCE) {
                    int i2 = value.valueTypeCase_;
                    if (i2 != 0) {
                        this.valueTypeCase_ = i2;
                    }
                }
                return this;
            case MERGE_FROM_STREAM:
                CodedInputStream codedInputStream = (CodedInputStream) obj;
                ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                while (!z) {
                    try {
                        int readTag = codedInputStream.readTag();
                        switch (readTag) {
                            case 0:
                                z = true;
                                break;
                            case 8:
                                this.valueTypeCase_ = 1;
                                this.valueType_ = Boolean.valueOf(codedInputStream.readBool());
                                break;
                            case 16:
                                this.valueTypeCase_ = 2;
                                this.valueType_ = Long.valueOf(codedInputStream.readInt64());
                                break;
                            case 25:
                                this.valueTypeCase_ = 3;
                                this.valueType_ = Double.valueOf(codedInputStream.readDouble());
                                break;
                            case 42:
                                String readStringRequireUtf8 = codedInputStream.readStringRequireUtf8();
                                this.valueTypeCase_ = 5;
                                this.valueType_ = readStringRequireUtf8;
                                break;
                            case 50:
                                com.google.firestore.v1.MapValue.Builder builder = this.valueTypeCase_ == 6 ? (com.google.firestore.v1.MapValue.Builder) ((MapValue) this.valueType_).toBuilder() : null;
                                this.valueType_ = codedInputStream.readMessage(MapValue.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom((MapValue) this.valueType_);
                                    this.valueType_ = builder.buildPartial();
                                }
                                this.valueTypeCase_ = 6;
                                break;
                            case 66:
                                com.google.type.LatLng.Builder builder2 = this.valueTypeCase_ == 8 ? (com.google.type.LatLng.Builder) ((LatLng) this.valueType_).toBuilder() : null;
                                this.valueType_ = codedInputStream.readMessage(LatLng.parser(), extensionRegistryLite);
                                if (builder2 != null) {
                                    builder2.mergeFrom((LatLng) this.valueType_);
                                    this.valueType_ = builder2.buildPartial();
                                }
                                this.valueTypeCase_ = 8;
                                break;
                            case 74:
                                com.google.firestore.v1.ArrayValue.Builder builder3 = this.valueTypeCase_ == 9 ? (com.google.firestore.v1.ArrayValue.Builder) ((ArrayValue) this.valueType_).toBuilder() : null;
                                this.valueType_ = codedInputStream.readMessage(ArrayValue.parser(), extensionRegistryLite);
                                if (builder3 != null) {
                                    builder3.mergeFrom((ArrayValue) this.valueType_);
                                    this.valueType_ = builder3.buildPartial();
                                }
                                this.valueTypeCase_ = 9;
                                break;
                            case 82:
                                com.google.protobuf.Timestamp.Builder builder4 = this.valueTypeCase_ == 10 ? (com.google.protobuf.Timestamp.Builder) ((Timestamp) this.valueType_).toBuilder() : null;
                                this.valueType_ = codedInputStream.readMessage(Timestamp.parser(), extensionRegistryLite);
                                if (builder4 != null) {
                                    builder4.mergeFrom((Timestamp) this.valueType_);
                                    this.valueType_ = builder4.buildPartial();
                                }
                                this.valueTypeCase_ = 10;
                                break;
                            case 88:
                                readTag = codedInputStream.readEnum();
                                this.valueTypeCase_ = i;
                                this.valueType_ = Integer.valueOf(readTag);
                                break;
                            case 138:
                                String readStringRequireUtf82 = codedInputStream.readStringRequireUtf8();
                                this.valueTypeCase_ = 17;
                                this.valueType_ = readStringRequireUtf82;
                                break;
                            case 146:
                                this.valueTypeCase_ = 18;
                                this.valueType_ = codedInputStream.readBytes();
                                break;
                            default:
                                if (codedInputStream.skipField(readTag)) {
                                    break;
                                }
                                z = true;
                                break;
                        }
                        i = 11;
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
                    synchronized (Value.class) {
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

    public static Value getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Value> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
