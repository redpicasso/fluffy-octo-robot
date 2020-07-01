package com.facebook.common.util;

public class Hex {
    private static final byte[] DIGITS = new byte[103];
    private static final char[] FIRST_CHAR = new char[256];
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] SECOND_CHAR = new char[256];

    static {
        for (int i = 0; i < 256; i++) {
            char[] cArr = FIRST_CHAR;
            char[] cArr2 = HEX_DIGITS;
            cArr[i] = cArr2[(i >> 4) & 15];
            SECOND_CHAR[i] = cArr2[i & 15];
        }
        for (int i2 = 0; i2 <= 70; i2++) {
            DIGITS[i2] = (byte) -1;
        }
        for (byte b = (byte) 0; b < (byte) 10; b = (byte) (b + 1)) {
            DIGITS[b + 48] = b;
        }
        for (int i3 = 0; i3 < 6; i3 = (byte) (i3 + 1)) {
            byte[] bArr = DIGITS;
            byte b2 = (byte) (i3 + 10);
            bArr[i3 + 65] = b2;
            bArr[i3 + 97] = b2;
        }
    }

    public static String byte2Hex(int i) {
        if (i > 255 || i < 0) {
            throw new IllegalArgumentException("The int converting to hex should be in range 0~255");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(FIRST_CHAR[i]));
        stringBuilder.append(String.valueOf(SECOND_CHAR[i]));
        return stringBuilder.toString();
    }

    public static String encodeHex(byte[] bArr, boolean z) {
        char[] cArr = new char[(bArr.length * 2)];
        int i = 0;
        for (byte b : bArr) {
            int i2 = b & 255;
            if (i2 == 0 && z) {
                break;
            }
            int i3 = i + 1;
            cArr[i] = FIRST_CHAR[i2];
            i = i3 + 1;
            cArr[i3] = SECOND_CHAR[i2];
        }
        return new String(cArr, 0, i);
    }

    public static byte[] decodeHex(String str) {
        int length = str.length();
        if ((length & 1) == 0) {
            int i;
            byte[] bArr = new byte[(length >> 1)];
            int i2 = 0;
            int i3 = 0;
            while (true) {
                i = 1;
                if (i2 >= length) {
                    i = 0;
                    break;
                }
                int i4 = i2 + 1;
                char charAt = str.charAt(i2);
                if (charAt > 'f') {
                    break;
                }
                byte b = DIGITS[charAt];
                if (b == (byte) -1) {
                    break;
                }
                int i5 = i4 + 1;
                char charAt2 = str.charAt(i4);
                if (charAt2 > 'f') {
                    break;
                }
                byte b2 = DIGITS[charAt2];
                if (b2 == (byte) -1) {
                    break;
                }
                bArr[i3] = (byte) ((b << 4) | b2);
                i3++;
                i2 = i5;
            }
            if (i == 0) {
                return bArr;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid hexadecimal digit: ");
            stringBuilder.append(str);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        throw new IllegalArgumentException("Odd number of characters.");
    }

    public static byte[] hexStringToByteArray(String str) {
        return decodeHex(str.replaceAll(" ", ""));
    }
}
