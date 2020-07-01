package io.opencensus.trace;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import java.util.Arrays;
import java.util.Random;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class SpanId implements Comparable<SpanId> {
    public static final SpanId INVALID = new SpanId(new byte[8]);
    public static final int SIZE = 8;
    private final byte[] bytes;

    private SpanId(byte[] bArr) {
        this.bytes = bArr;
    }

    public static SpanId fromBytes(byte[] bArr) {
        Preconditions.checkNotNull(bArr, "buffer");
        Preconditions.checkArgument(bArr.length == 8, "Invalid size: expected %s, got %s", Integer.valueOf(8), Integer.valueOf(bArr.length));
        return new SpanId(Arrays.copyOf(bArr, 8));
    }

    public static SpanId fromBytes(byte[] bArr, int i) {
        Object obj = new byte[8];
        System.arraycopy(bArr, i, obj, 0, 8);
        return new SpanId(obj);
    }

    public static SpanId fromLowerBase16(CharSequence charSequence) {
        Preconditions.checkArgument(charSequence.length() == 16, "Invalid size: expected %s, got %s", Integer.valueOf(16), Integer.valueOf(charSequence.length()));
        return new SpanId(BaseEncoding.base16().lowerCase().decode(charSequence));
    }

    public static SpanId generateRandomId(Random random) {
        byte[] bArr = new byte[8];
        do {
            random.nextBytes(bArr);
        } while (Arrays.equals(bArr, INVALID.bytes));
        return new SpanId(bArr);
    }

    public byte[] getBytes() {
        return Arrays.copyOf(this.bytes, 8);
    }

    public void copyBytesTo(byte[] bArr, int i) {
        System.arraycopy(this.bytes, 0, bArr, i, 8);
    }

    public boolean isValid() {
        return Arrays.equals(this.bytes, INVALID.bytes) ^ 1;
    }

    public String toLowerBase16() {
        return BaseEncoding.base16().lowerCase().encode(this.bytes);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SpanId)) {
            return false;
        }
        return Arrays.equals(this.bytes, ((SpanId) obj).bytes);
    }

    public int hashCode() {
        return Arrays.hashCode(this.bytes);
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("spanId", BaseEncoding.base16().lowerCase().encode(this.bytes)).toString();
    }

    public int compareTo(SpanId spanId) {
        for (int i = 0; i < 8; i++) {
            byte[] bArr = this.bytes;
            byte b = bArr[i];
            byte[] bArr2 = spanId.bytes;
            if (b != bArr2[i]) {
                return bArr[i] < bArr2[i] ? -1 : 1;
            }
        }
        return 0;
    }
}
