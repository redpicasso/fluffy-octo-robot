package com.adobe.xmp.impl;

public class Base64 {
    private static final byte EQUAL = (byte) -3;
    private static final byte INVALID = (byte) -1;
    private static final byte WHITESPACE = (byte) -2;
    private static byte[] ascii = new byte[255];
    private static byte[] base64 = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};

    static {
        int i = 0;
        for (int i2 = 0; i2 < 255; i2++) {
            ascii[i2] = (byte) -1;
        }
        while (true) {
            byte[] bArr = base64;
            if (i < bArr.length) {
                ascii[bArr[i]] = (byte) i;
                i++;
            } else {
                bArr = ascii;
                bArr[9] = WHITESPACE;
                bArr[10] = WHITESPACE;
                bArr[13] = WHITESPACE;
                bArr[32] = WHITESPACE;
                bArr[61] = EQUAL;
                return;
            }
        }
    }

    public static final String decode(String str) {
        return new String(decode(str.getBytes()));
    }

    public static final byte[] decode(byte[] bArr) throws IllegalArgumentException {
        int i;
        int i2 = 0;
        int i3 = 0;
        for (byte b : bArr) {
            byte b2 = ascii[b];
            if (b2 >= (byte) 0) {
                i = i3 + 1;
                bArr[i3] = b2;
                i3 = i;
            } else if (b2 == (byte) -1) {
                throw new IllegalArgumentException("Invalid base 64 string");
            }
        }
        while (i3 > 0 && bArr[i3 - 1] == EQUAL) {
            i3--;
        }
        byte[] bArr2 = new byte[((i3 * 3) / 4)];
        i3 = 0;
        while (i2 < bArr2.length - 2) {
            i = i3 + 1;
            bArr2[i2] = (byte) (((bArr[i3] << 2) & 255) | ((bArr[i] >>> 4) & 3));
            int i4 = i3 + 2;
            bArr2[i2 + 1] = (byte) (((bArr[i] << 4) & 255) | ((bArr[i4] >>> 2) & 15));
            bArr2[i2 + 2] = (byte) (((bArr[i4] << 6) & 255) | (bArr[i3 + 3] & 63));
            i3 += 4;
            i2 += 3;
        }
        if (i2 < bArr2.length) {
            bArr2[i2] = (byte) (((bArr[i3] << 2) & 255) | ((bArr[i3 + 1] >>> 4) & 3));
        }
        i2++;
        if (i2 < bArr2.length) {
            bArr2[i2] = (byte) (((bArr[i3 + 2] >>> 2) & 15) | ((bArr[i3 + 1] << 4) & 255));
        }
        return bArr2;
    }

    public static final String encode(String str) {
        return new String(encode(str.getBytes()));
    }

    public static final byte[] encode(byte[] bArr) {
        return encode(bArr, 0);
    }

    public static final byte[] encode(byte[] bArr, int i) {
        i = (i / 4) * 4;
        if (i < 0) {
            i = 0;
        }
        int length = ((bArr.length + 2) / 3) * 4;
        if (i > 0) {
            length += (length - 1) / i;
        }
        byte[] bArr2 = new byte[length];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 + 3 <= bArr.length) {
            int i5 = i2 + 1;
            int i6 = i5 + 1;
            i2 = ((bArr[i2] & 255) << 16) | ((bArr[i5] & 255) << 8);
            i5 = i6 + 1;
            i2 |= (bArr[i6] & 255) << 0;
            i6 = (i2 & 16515072) >> 18;
            int i7 = i3 + 1;
            byte[] bArr3 = base64;
            bArr2[i3] = bArr3[i6];
            i6 = i7 + 1;
            bArr2[i7] = bArr3[(i2 & 258048) >> 12];
            int i8 = i6 + 1;
            bArr2[i6] = bArr3[(i2 & 4032) >> 6];
            i3 = i8 + 1;
            bArr2[i8] = bArr3[i2 & 63];
            i4 += 4;
            if (i3 < length && i > 0 && i4 % i == 0) {
                i2 = i3 + 1;
                bArr2[i3] = (byte) 10;
                i3 = i2;
            }
            i2 = i5;
        }
        int i9;
        byte[] bArr4;
        if (bArr.length - i2 == 2) {
            i9 = ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2] & 255) << 16);
            i = (i9 & 16515072) >> 18;
            length = i3 + 1;
            bArr4 = base64;
            bArr2[i3] = bArr4[i];
            i2 = length + 1;
            bArr2[length] = bArr4[(i9 & 258048) >> 12];
            i = i2 + 1;
            bArr2[i2] = bArr4[(i9 & 4032) >> 6];
            bArr2[i] = (byte) 61;
        } else if (bArr.length - i2 == 1) {
            i9 = (bArr[i2] & 255) << 16;
            i = (i9 & 16515072) >> 18;
            length = i3 + 1;
            bArr4 = base64;
            bArr2[i3] = bArr4[i];
            i = length + 1;
            bArr2[length] = bArr4[(i9 & 258048) >> 12];
            i9 = i + 1;
            bArr2[i] = (byte) 61;
            bArr2[i9] = (byte) 61;
        }
        return bArr2;
    }
}
