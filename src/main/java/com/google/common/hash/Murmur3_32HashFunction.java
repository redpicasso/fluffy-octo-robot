package com.google.common.hash;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
final class Murmur3_32HashFunction extends AbstractHashFunction implements Serializable {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final int CHUNK_SIZE = 4;
    static final HashFunction GOOD_FAST_HASH_32 = new Murmur3_32HashFunction(Hashing.GOOD_FAST_HASH_SEED);
    static final HashFunction MURMUR3_32 = new Murmur3_32HashFunction(0);
    private static final long serialVersionUID = 0;
    private final int seed;

    @CanIgnoreReturnValue
    private static final class Murmur3_32Hasher extends AbstractHasher {
        private long buffer;
        private int h1;
        private boolean isDone = false;
        private int length = 0;
        private int shift;

        Murmur3_32Hasher(int i) {
            this.h1 = i;
        }

        private void update(int i, long j) {
            long j2 = this.buffer;
            j &= 4294967295L;
            int i2 = this.shift;
            this.buffer = (j << i2) | j2;
            this.shift = i2 + (i * 8);
            this.length += i;
            if (this.shift >= 32) {
                this.h1 = Murmur3_32HashFunction.mixH1(this.h1, Murmur3_32HashFunction.mixK1((int) this.buffer));
                this.buffer >>>= 32;
                this.shift -= 32;
            }
        }

        public Hasher putByte(byte b) {
            update(1, (long) (b & 255));
            return this;
        }

        public Hasher putBytes(byte[] bArr, int i, int i2) {
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            int i3 = 0;
            while (true) {
                int i4 = i3 + 4;
                if (i4 > i2) {
                    break;
                }
                update(4, (long) Murmur3_32HashFunction.getIntLittleEndian(bArr, i3 + i));
                i3 = i4;
            }
            while (i3 < i2) {
                putByte(bArr[i + i3]);
                i3++;
            }
            return this;
        }

        public Hasher putBytes(ByteBuffer byteBuffer) {
            ByteOrder order = byteBuffer.order();
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            while (byteBuffer.remaining() >= 4) {
                putInt(byteBuffer.getInt());
            }
            while (byteBuffer.hasRemaining()) {
                putByte(byteBuffer.get());
            }
            byteBuffer.order(order);
            return this;
        }

        public Hasher putInt(int i) {
            update(4, (long) i);
            return this;
        }

        public Hasher putLong(long j) {
            update(4, (long) ((int) j));
            update(4, j >>> 32);
            return this;
        }

        public Hasher putChar(char c) {
            update(2, (long) c);
            return this;
        }

        public Hasher putString(CharSequence charSequence, Charset charset) {
            if (!Charsets.UTF_8.equals(charset)) {
                return super.putString(charSequence, charset);
            }
            char charAt;
            int length = charSequence.length();
            int i = 0;
            while (true) {
                int i2 = i + 4;
                if (i2 > length) {
                    break;
                }
                char charAt2 = charSequence.charAt(i);
                charAt = charSequence.charAt(i + 1);
                char charAt3 = charSequence.charAt(i + 2);
                char charAt4 = charSequence.charAt(i + 3);
                if (charAt2 >= 128 || charAt >= 128 || charAt3 >= 128 || charAt4 >= 128) {
                    break;
                }
                update(4, (long) ((((charAt << 8) | charAt2) | (charAt3 << 16)) | (charAt4 << 24)));
                i = i2;
            }
            while (i < length) {
                char charAt5 = charSequence.charAt(i);
                if (charAt5 < 128) {
                    update(1, (long) charAt5);
                } else if (charAt5 < 2048) {
                    update(2, Murmur3_32HashFunction.charToTwoUtf8Bytes(charAt5));
                } else if (charAt5 < 55296 || charAt5 > 57343) {
                    update(3, Murmur3_32HashFunction.charToThreeUtf8Bytes(charAt5));
                } else {
                    charAt = Character.codePointAt(charSequence, i);
                    if (charAt == charAt5) {
                        putBytes(charSequence.subSequence(i, length).toString().getBytes(charset));
                        return this;
                    }
                    i++;
                    update(4, Murmur3_32HashFunction.codePointToFourUtf8Bytes(charAt));
                }
                i++;
            }
            return this;
        }

        public HashCode hash() {
            Preconditions.checkState(this.isDone ^ true);
            this.isDone = true;
            this.h1 ^= Murmur3_32HashFunction.mixK1((int) this.buffer);
            return Murmur3_32HashFunction.fmix(this.h1, this.length);
        }
    }

    private static long charToThreeUtf8Bytes(char c) {
        return (long) ((((c & 63) | 128) << 16) | ((((c >>> 12) | 480) & 255) | ((((c >>> 6) & 63) | 128) << 8)));
    }

    private static long charToTwoUtf8Bytes(char c) {
        return (long) ((((c & 63) | 128) << 8) | (((c >>> 6) | 960) & 255));
    }

    private static long codePointToFourUtf8Bytes(int i) {
        return ((((((long) (i >>> 18)) | 240) & 255) | ((((long) ((i >>> 12) & 63)) | 128) << 8)) | ((((long) ((i >>> 6) & 63)) | 128) << 16)) | ((((long) (i & 63)) | 128) << 24);
    }

    public int bits() {
        return 32;
    }

    Murmur3_32HashFunction(int i) {
        this.seed = i;
    }

    public Hasher newHasher() {
        return new Murmur3_32Hasher(this.seed);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hashing.murmur3_32(");
        stringBuilder.append(this.seed);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof Murmur3_32HashFunction)) {
            return false;
        }
        if (this.seed == ((Murmur3_32HashFunction) obj).seed) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return getClass().hashCode() ^ this.seed;
    }

    public HashCode hashInt(int i) {
        return fmix(mixH1(this.seed, mixK1(i)), 4);
    }

    public HashCode hashLong(long j) {
        int i = (int) (j >>> 32);
        return fmix(mixH1(mixH1(this.seed, mixK1((int) j)), mixK1(i)), 8);
    }

    public HashCode hashUnencodedChars(CharSequence charSequence) {
        int i = this.seed;
        for (int i2 = 1; i2 < charSequence.length(); i2 += 2) {
            i = mixH1(i, mixK1(charSequence.charAt(i2 - 1) | (charSequence.charAt(i2) << 16)));
        }
        if ((charSequence.length() & 1) == 1) {
            i ^= mixK1(charSequence.charAt(charSequence.length() - 1));
        }
        return fmix(i, charSequence.length() * 2);
    }

    public HashCode hashString(CharSequence charSequence, Charset charset) {
        if (!Charsets.UTF_8.equals(charset)) {
            return hashBytes(charSequence.toString().getBytes(charset));
        }
        char charAt;
        int length = charSequence.length();
        int i = 0;
        int i2 = this.seed;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i3 + 4;
            if (i5 > length) {
                break;
            }
            char charAt2 = charSequence.charAt(i3);
            char charAt3 = charSequence.charAt(i3 + 1);
            charAt = charSequence.charAt(i3 + 2);
            char charAt4 = charSequence.charAt(i3 + 3);
            if (charAt2 >= 128 || charAt3 >= 128 || charAt >= 128 || charAt4 >= 128) {
                break;
            }
            i2 = mixH1(i2, mixK1((((charAt3 << 8) | charAt2) | (charAt << 16)) | (charAt4 << 24)));
            i4 += 4;
            i3 = i5;
        }
        long j = 0;
        while (i3 < length) {
            char charAt5 = charSequence.charAt(i3);
            long j2;
            if (charAt5 < 128) {
                j |= ((long) charAt5) << j2;
                j2 += 8;
                i4++;
            } else if (charAt5 < 2048) {
                j |= charToTwoUtf8Bytes(charAt5) << j2;
                j2 += 16;
                i4 += 2;
            } else if (charAt5 < 55296 || charAt5 > 57343) {
                j |= charToThreeUtf8Bytes(charAt5) << j2;
                j2 += 24;
                i4 += 3;
            } else {
                charAt = Character.codePointAt(charSequence, i3);
                if (charAt == charAt5) {
                    return hashBytes(charSequence.toString().getBytes(charset));
                }
                i3++;
                j |= codePointToFourUtf8Bytes(charAt) << j2;
                i4 += 4;
            }
            if (i >= 32) {
                i2 = mixH1(i2, mixK1((int) j));
                j >>>= 32;
                i -= 32;
            }
            i3++;
        }
        return fmix(mixK1((int) j) ^ i2, i4);
    }

    public HashCode hashBytes(byte[] bArr, int i, int i2) {
        int i3;
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
        int i4 = 0;
        int i5 = this.seed;
        int i6 = 0;
        while (true) {
            i3 = i6 + 4;
            if (i3 > i2) {
                break;
            }
            i5 = mixH1(i5, mixK1(getIntLittleEndian(bArr, i6 + i)));
            i6 = i3;
        }
        i3 = 0;
        while (i6 < i2) {
            i4 ^= UnsignedBytes.toInt(bArr[i + i6]) << i3;
            i6++;
            i3 += 8;
        }
        return fmix(mixK1(i4) ^ i5, i2);
    }

    private static int getIntLittleEndian(byte[] bArr, int i) {
        return Ints.fromBytes(bArr[i + 3], bArr[i + 2], bArr[i + 1], bArr[i]);
    }

    private static int mixK1(int i) {
        return Integer.rotateLeft(i * C1, 15) * C2;
    }

    private static int mixH1(int i, int i2) {
        return (Integer.rotateLeft(i ^ i2, 13) * 5) - 430675100;
    }

    private static HashCode fmix(int i, int i2) {
        i ^= i2;
        i = (i ^ (i >>> 16)) * -2048144789;
        i = (i ^ (i >>> 13)) * -1028477387;
        return HashCode.fromInt(i ^ (i >>> 16));
    }
}
