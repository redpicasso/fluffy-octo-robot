package com.google.rpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Duration;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public final class RetryInfo extends GeneratedMessageLite<RetryInfo, Builder> implements RetryInfoOrBuilder {
    private static final RetryInfo DEFAULT_INSTANCE = new RetryInfo();
    private static volatile Parser<RetryInfo> PARSER = null;
    public static final int RETRY_DELAY_FIELD_NUMBER = 1;
    private Duration retryDelay_;

    /* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<RetryInfo, Builder> implements RetryInfoOrBuilder {
        private Builder() {
            super(RetryInfo.DEFAULT_INSTANCE);
        }

        public boolean hasRetryDelay() {
            return ((RetryInfo) this.instance).hasRetryDelay();
        }

        public Duration getRetryDelay() {
            return ((RetryInfo) this.instance).getRetryDelay();
        }

        public Builder setRetryDelay(Duration duration) {
            copyOnWrite();
            ((RetryInfo) this.instance).setRetryDelay(duration);
            return this;
        }

        public Builder setRetryDelay(com.google.protobuf.Duration.Builder builder) {
            copyOnWrite();
            ((RetryInfo) this.instance).setRetryDelay(builder);
            return this;
        }

        public Builder mergeRetryDelay(Duration duration) {
            copyOnWrite();
            ((RetryInfo) this.instance).mergeRetryDelay(duration);
            return this;
        }

        public Builder clearRetryDelay() {
            copyOnWrite();
            ((RetryInfo) this.instance).clearRetryDelay();
            return this;
        }
    }

    private RetryInfo() {
    }

    public boolean hasRetryDelay() {
        return this.retryDelay_ != null;
    }

    public Duration getRetryDelay() {
        Duration duration = this.retryDelay_;
        return duration == null ? Duration.getDefaultInstance() : duration;
    }

    private void setRetryDelay(Duration duration) {
        if (duration != null) {
            this.retryDelay_ = duration;
            return;
        }
        throw new NullPointerException();
    }

    private void setRetryDelay(com.google.protobuf.Duration.Builder builder) {
        this.retryDelay_ = (Duration) builder.build();
    }

    private void mergeRetryDelay(Duration duration) {
        Duration duration2 = this.retryDelay_;
        if (duration2 == null || duration2 == Duration.getDefaultInstance()) {
            this.retryDelay_ = duration;
        } else {
            this.retryDelay_ = (Duration) ((com.google.protobuf.Duration.Builder) Duration.newBuilder(this.retryDelay_).mergeFrom(duration)).buildPartial();
        }
    }

    private void clearRetryDelay() {
        this.retryDelay_ = null;
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (this.retryDelay_ != null) {
            codedOutputStream.writeMessage(1, getRetryDelay());
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (this.retryDelay_ != null) {
            i = 0 + CodedOutputStream.computeMessageSize(1, getRetryDelay());
        }
        this.memoizedSerializedSize = i;
        return i;
    }

    public static RetryInfo parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (RetryInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static RetryInfo parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (RetryInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static RetryInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (RetryInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static RetryInfo parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (RetryInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static RetryInfo parseFrom(InputStream inputStream) throws IOException {
        return (RetryInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static RetryInfo parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (RetryInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static RetryInfo parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (RetryInfo) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static RetryInfo parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (RetryInfo) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static RetryInfo parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (RetryInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static RetryInfo parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (RetryInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(RetryInfo retryInfo) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(retryInfo);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new RetryInfo();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                this.retryDelay_ = (Duration) ((Visitor) obj).visitMessage(this.retryDelay_, ((RetryInfo) obj2).retryDelay_);
                MergeFromVisitor mergeFromVisitor = MergeFromVisitor.INSTANCE;
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
                                com.google.protobuf.Duration.Builder builder = this.retryDelay_ != null ? (com.google.protobuf.Duration.Builder) this.retryDelay_.toBuilder() : null;
                                this.retryDelay_ = (Duration) codedInputStream.readMessage(Duration.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom(this.retryDelay_);
                                    this.retryDelay_ = (Duration) builder.buildPartial();
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
                    synchronized (RetryInfo.class) {
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

    public static RetryInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<RetryInfo> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}