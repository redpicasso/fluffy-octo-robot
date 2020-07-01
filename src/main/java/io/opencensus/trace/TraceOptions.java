package io.opencensus.trace;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class TraceOptions {
    public static final TraceOptions DEFAULT = new TraceOptions((byte) 0);
    private static final byte DEFAULT_OPTIONS = (byte) 0;
    private static final byte IS_SAMPLED = (byte) 1;
    public static final int SIZE = 1;
    private final byte options;

    public static final class Builder {
        private byte options;

        private Builder(byte b) {
            this.options = b;
        }

        @Deprecated
        public Builder setIsSampled() {
            return setIsSampled(true);
        }

        public Builder setIsSampled(boolean z) {
            if (z) {
                this.options = (byte) (this.options | 1);
            } else {
                this.options = (byte) (this.options & -2);
            }
            return this;
        }

        public TraceOptions build() {
            return new TraceOptions(this.options);
        }
    }

    private TraceOptions(byte b) {
        this.options = b;
    }

    public static TraceOptions fromBytes(byte[] bArr) {
        Preconditions.checkNotNull(bArr, "buffer");
        Preconditions.checkArgument(bArr.length == 1, "Invalid size: expected %s, got %s", Integer.valueOf(1), Integer.valueOf(bArr.length));
        return new TraceOptions(bArr[0]);
    }

    public static TraceOptions fromBytes(byte[] bArr, int i) {
        Preconditions.checkElementIndex(i, bArr.length);
        return new TraceOptions(bArr[i]);
    }

    public byte[] getBytes() {
        return new byte[]{this.options};
    }

    public void copyBytesTo(byte[] bArr, int i) {
        Preconditions.checkElementIndex(i, bArr.length);
        bArr[i] = this.options;
    }

    public static Builder builder() {
        return new Builder((byte) 0);
    }

    public static Builder builder(TraceOptions traceOptions) {
        return new Builder(traceOptions.options);
    }

    public boolean isSampled() {
        return hasOption(1);
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TraceOptions)) {
            return false;
        }
        if (this.options != ((TraceOptions) obj).options) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(Byte.valueOf(this.options));
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("sampled", isSampled()).toString();
    }

    @VisibleForTesting
    byte getOptions() {
        return this.options;
    }

    private boolean hasOption(int i) {
        return (i & this.options) != 0;
    }
}
