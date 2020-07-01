package io.opencensus.trace;

import io.opencensus.trace.MessageEvent.Type;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AutoValue_MessageEvent extends MessageEvent {
    private final long compressedMessageSize;
    private final long messageId;
    private final Type type;
    private final long uncompressedMessageSize;

    static final class Builder extends io.opencensus.trace.MessageEvent.Builder {
        private Long compressedMessageSize;
        private Long messageId;
        private Type type;
        private Long uncompressedMessageSize;

        Builder() {
        }

        io.opencensus.trace.MessageEvent.Builder setType(Type type) {
            if (type != null) {
                this.type = type;
                return this;
            }
            throw new NullPointerException("Null type");
        }

        io.opencensus.trace.MessageEvent.Builder setMessageId(long j) {
            this.messageId = Long.valueOf(j);
            return this;
        }

        public io.opencensus.trace.MessageEvent.Builder setUncompressedMessageSize(long j) {
            this.uncompressedMessageSize = Long.valueOf(j);
            return this;
        }

        public io.opencensus.trace.MessageEvent.Builder setCompressedMessageSize(long j) {
            this.compressedMessageSize = Long.valueOf(j);
            return this;
        }

        public MessageEvent build() {
            StringBuilder stringBuilder;
            String str = "";
            if (this.type == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" type");
                str = stringBuilder.toString();
            }
            if (this.messageId == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" messageId");
                str = stringBuilder.toString();
            }
            if (this.uncompressedMessageSize == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" uncompressedMessageSize");
                str = stringBuilder.toString();
            }
            if (this.compressedMessageSize == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" compressedMessageSize");
                str = stringBuilder.toString();
            }
            if (str.isEmpty()) {
                return new AutoValue_MessageEvent(this.type, this.messageId.longValue(), this.uncompressedMessageSize.longValue(), this.compressedMessageSize.longValue());
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Missing required properties:");
            stringBuilder2.append(str);
            throw new IllegalStateException(stringBuilder2.toString());
        }
    }

    private AutoValue_MessageEvent(Type type, long j, long j2, long j3) {
        this.type = type;
        this.messageId = j;
        this.uncompressedMessageSize = j2;
        this.compressedMessageSize = j3;
    }

    public Type getType() {
        return this.type;
    }

    public long getMessageId() {
        return this.messageId;
    }

    public long getUncompressedMessageSize() {
        return this.uncompressedMessageSize;
    }

    public long getCompressedMessageSize() {
        return this.compressedMessageSize;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MessageEvent{type=");
        stringBuilder.append(this.type);
        stringBuilder.append(", messageId=");
        stringBuilder.append(this.messageId);
        stringBuilder.append(", uncompressedMessageSize=");
        stringBuilder.append(this.uncompressedMessageSize);
        stringBuilder.append(", compressedMessageSize=");
        stringBuilder.append(this.compressedMessageSize);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MessageEvent)) {
            return false;
        }
        MessageEvent messageEvent = (MessageEvent) obj;
        if (!(this.type.equals(messageEvent.getType()) && this.messageId == messageEvent.getMessageId() && this.uncompressedMessageSize == messageEvent.getUncompressedMessageSize() && this.compressedMessageSize == messageEvent.getCompressedMessageSize())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long hashCode = (long) ((this.type.hashCode() ^ 1000003) * 1000003);
        long j = this.messageId;
        hashCode = (long) (((int) (hashCode ^ (j ^ (j >>> 32)))) * 1000003);
        j = this.uncompressedMessageSize;
        long j2 = (long) (((int) (hashCode ^ (j ^ (j >>> 32)))) * 1000003);
        long j3 = this.compressedMessageSize;
        return (int) (j2 ^ (j3 ^ (j3 >>> 32)));
    }
}
