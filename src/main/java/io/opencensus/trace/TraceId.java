package io.opencensus.trace;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import java.util.Arrays;
import java.util.Random;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class TraceId implements Comparable<TraceId> {
    public static final TraceId INVALID = new TraceId(new byte[16]);
    public static final int SIZE = 16;
    private final byte[] bytes;

    private TraceId(byte[] bArr) {
        this.bytes = bArr;
    }

    public static TraceId fromBytes(byte[] bArr) {
        Preconditions.checkNotNull(bArr, "buffer");
        Preconditions.checkArgument(bArr.length == 16, "Invalid size: expected %s, got %s", Integer.valueOf(16), Integer.valueOf(bArr.length));
        return new TraceId(Arrays.copyOf(bArr, 16));
    }

    public static TraceId fromBytes(byte[] bArr, int i) {
        Object obj = new byte[16];
        System.arraycopy(bArr, i, obj, 0, 16);
        return new TraceId(obj);
    }

    public static TraceId fromLowerBase16(CharSequence charSequence) {
        Preconditions.checkArgument(charSequence.length() == 32, "Invalid size: expected %s, got %s", Integer.valueOf(32), Integer.valueOf(charSequence.length()));
        return new TraceId(BaseEncoding.base16().lowerCase().decode(charSequence));
    }

    public static TraceId generateRandomId(Random random) {
        byte[] bArr = new byte[16];
        do {
            random.nextBytes(bArr);
        } while (Arrays.equals(bArr, INVALID.bytes));
        return new TraceId(bArr);
    }

    public byte[] getBytes() {
        return Arrays.copyOf(this.bytes, 16);
    }

    public void copyBytesTo(byte[] bArr, int i) {
        System.arraycopy(this.bytes, 0, bArr, i, 16);
    }

    public boolean isValid() {
        return Arrays.equals(this.bytes, INVALID.bytes) ^ 1;
    }

    public String toLowerBase16() {
        return BaseEncoding.base16().lowerCase().encode(this.bytes);
    }

    public long getLowerLong() {
        long j = 0;
        for (int i = 0; i < 8; i++) {
            j = (j << 8) | ((long) (this.bytes[i] & 255));
        }
        return j < 0 ? -j : j;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TraceId)) {
            return false;
        }
        return Arrays.equals(this.bytes, ((TraceId) obj).bytes);
    }

    public int hashCode() {
        return Arrays.hashCode(this.bytes);
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("traceId", BaseEncoding.base16().lowerCase().encode(this.bytes)).toString();
    }

    public int compareTo(TraceId traceId) {
        for (int i = 0; i < 16; i++) {
            byte[] bArr = this.bytes;
            byte b = bArr[i];
            byte[] bArr2 = traceId.bytes;
            if (b != bArr2[i]) {
                return bArr[i] < bArr2[i] ? -1 : 1;
            }
        }
        return 0;
    }
}
