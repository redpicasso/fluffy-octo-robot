package com.google.zxing.common;

public final class BitSource {
    private int bitOffset;
    private int byteOffset;
    private final byte[] bytes;

    public BitSource(byte[] bArr) {
        this.bytes = bArr;
    }

    public int getBitOffset() {
        return this.bitOffset;
    }

    public int getByteOffset() {
        return this.byteOffset;
    }

    public int readBits(int i) {
        if (i <= 0 || i > 32 || i > available()) {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        int i2;
        int i3 = this.bitOffset;
        if (i3 > 0) {
            i3 = 8 - i3;
            i2 = i < i3 ? i : i3;
            i3 -= i2;
            int i4 = (255 >> (8 - i2)) << i3;
            byte[] bArr = this.bytes;
            int i5 = this.byteOffset;
            i3 = (i4 & bArr[i5]) >> i3;
            i -= i2;
            this.bitOffset += i2;
            if (this.bitOffset == 8) {
                this.bitOffset = 0;
                this.byteOffset = i5 + 1;
            }
        } else {
            i3 = 0;
        }
        if (i <= 0) {
            return i3;
        }
        while (i >= 8) {
            i3 <<= 8;
            byte[] bArr2 = this.bytes;
            i2 = this.byteOffset;
            i3 |= bArr2[i2] & 255;
            this.byteOffset = i2 + 1;
            i -= 8;
        }
        if (i <= 0) {
            return i3;
        }
        int i6 = 8 - i;
        i3 = (i3 << i) | ((((255 >> i6) << i6) & this.bytes[this.byteOffset]) >> i6);
        this.bitOffset += i;
        return i3;
    }

    public int available() {
        return ((this.bytes.length - this.byteOffset) * 8) - this.bitOffset;
    }
}
