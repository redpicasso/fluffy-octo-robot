package io.opencensus.trace;

import io.opencensus.common.Timestamp;
import io.opencensus.trace.NetworkEvent.Type;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@Deprecated
final class AutoValue_NetworkEvent extends NetworkEvent {
    private final long compressedMessageSize;
    private final Timestamp kernelTimestamp;
    private final long messageId;
    private final Type type;
    private final long uncompressedMessageSize;

    static final class Builder extends io.opencensus.trace.NetworkEvent.Builder {
        private Long compressedMessageSize;
        private Timestamp kernelTimestamp;
        private Long messageId;
        private Type type;
        private Long uncompressedMessageSize;

        Builder() {
        }

        public io.opencensus.trace.NetworkEvent.Builder setKernelTimestamp(@Nullable Timestamp timestamp) {
            this.kernelTimestamp = timestamp;
            return this;
        }

        io.opencensus.trace.NetworkEvent.Builder setType(Type type) {
            if (type != null) {
                this.type = type;
                return this;
            }
            throw new NullPointerException("Null type");
        }

        io.opencensus.trace.NetworkEvent.Builder setMessageId(long j) {
            this.messageId = Long.valueOf(j);
            return this;
        }

        public io.opencensus.trace.NetworkEvent.Builder setUncompressedMessageSize(long j) {
            this.uncompressedMessageSize = Long.valueOf(j);
            return this;
        }

        public io.opencensus.trace.NetworkEvent.Builder setCompressedMessageSize(long j) {
            this.compressedMessageSize = Long.valueOf(j);
            return this;
        }

        public NetworkEvent build() {
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
                return new AutoValue_NetworkEvent(this.kernelTimestamp, this.type, this.messageId.longValue(), this.uncompressedMessageSize.longValue(), this.compressedMessageSize.longValue());
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Missing required properties:");
            stringBuilder2.append(str);
            throw new IllegalStateException(stringBuilder2.toString());
        }
    }

    private AutoValue_NetworkEvent(@Nullable Timestamp timestamp, Type type, long j, long j2, long j3) {
        this.kernelTimestamp = timestamp;
        this.type = type;
        this.messageId = j;
        this.uncompressedMessageSize = j2;
        this.compressedMessageSize = j3;
    }

    @Nullable
    public Timestamp getKernelTimestamp() {
        return this.kernelTimestamp;
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
        stringBuilder.append("NetworkEvent{kernelTimestamp=");
        stringBuilder.append(this.kernelTimestamp);
        stringBuilder.append(", type=");
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

    /* JADX WARNING: Missing block: B:18:0x0048, code:
            if (r7.compressedMessageSize == r8.getCompressedMessageSize()) goto L_0x004c;
     */
    public boolean equals(java.lang.Object r8) {
        /*
        r7 = this;
        r0 = 1;
        if (r8 != r7) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = r8 instanceof io.opencensus.trace.NetworkEvent;
        r2 = 0;
        if (r1 == 0) goto L_0x004d;
    L_0x0009:
        r8 = (io.opencensus.trace.NetworkEvent) r8;
        r1 = r7.kernelTimestamp;
        if (r1 != 0) goto L_0x0016;
    L_0x000f:
        r1 = r8.getKernelTimestamp();
        if (r1 != 0) goto L_0x004b;
    L_0x0015:
        goto L_0x0020;
    L_0x0016:
        r3 = r8.getKernelTimestamp();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x004b;
    L_0x0020:
        r1 = r7.type;
        r3 = r8.getType();
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x004b;
    L_0x002c:
        r3 = r7.messageId;
        r5 = r8.getMessageId();
        r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r1 != 0) goto L_0x004b;
    L_0x0036:
        r3 = r7.uncompressedMessageSize;
        r5 = r8.getUncompressedMessageSize();
        r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r1 != 0) goto L_0x004b;
    L_0x0040:
        r3 = r7.compressedMessageSize;
        r5 = r8.getCompressedMessageSize();
        r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r8 != 0) goto L_0x004b;
    L_0x004a:
        goto L_0x004c;
    L_0x004b:
        r0 = 0;
    L_0x004c:
        return r0;
    L_0x004d:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.opencensus.trace.AutoValue_NetworkEvent.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        Timestamp timestamp = this.kernelTimestamp;
        long hashCode = (long) (((((timestamp == null ? 0 : timestamp.hashCode()) ^ 1000003) * 1000003) ^ this.type.hashCode()) * 1000003);
        long j = this.messageId;
        hashCode = (long) (((int) (hashCode ^ (j ^ (j >>> 32)))) * 1000003);
        j = this.uncompressedMessageSize;
        long j2 = (long) (((int) (hashCode ^ (j ^ (j >>> 32)))) * 1000003);
        long j3 = this.compressedMessageSize;
        return (int) (j2 ^ (j3 ^ (j3 >>> 32)));
    }
}
