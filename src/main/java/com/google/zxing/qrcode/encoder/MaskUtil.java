package com.google.zxing.qrcode.encoder;

final class MaskUtil {
    private static final int N1 = 3;
    private static final int N2 = 3;
    private static final int N3 = 40;
    private static final int N4 = 10;

    private MaskUtil() {
    }

    static int applyMaskPenaltyRule1(ByteMatrix byteMatrix) {
        return applyMaskPenaltyRule1Internal(byteMatrix, true) + applyMaskPenaltyRule1Internal(byteMatrix, false);
    }

    static int applyMaskPenaltyRule2(ByteMatrix byteMatrix) {
        byte[][] array = byteMatrix.getArray();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        int i = 0;
        int i2 = 0;
        while (i < height - 1) {
            byte[] bArr = array[i];
            int i3 = i2;
            i2 = 0;
            while (i2 < width - 1) {
                byte b = bArr[i2];
                int i4 = i2 + 1;
                if (b == bArr[i4]) {
                    int i5 = i + 1;
                    if (b == array[i5][i2] && b == array[i5][i4]) {
                        i3++;
                    }
                }
                i2 = i4;
            }
            i++;
            i2 = i3;
        }
        return i2 * 3;
    }

    static int applyMaskPenaltyRule3(ByteMatrix byteMatrix) {
        byte[][] array = byteMatrix.getArray();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        int i = 0;
        int i2 = 0;
        while (i < height) {
            int i3 = i2;
            i2 = 0;
            while (i2 < width) {
                byte[] bArr = array[i];
                int i4 = i2 + 6;
                if (i4 < width && bArr[i2] == (byte) 1 && bArr[i2 + 1] == (byte) 0 && bArr[i2 + 2] == (byte) 1 && bArr[i2 + 3] == (byte) 1 && bArr[i2 + 4] == (byte) 1 && bArr[i2 + 5] == (byte) 0 && bArr[i4] == (byte) 1 && (isWhiteHorizontal(bArr, i2 - 4, i2) || isWhiteHorizontal(bArr, i2 + 7, i2 + 11))) {
                    i3++;
                }
                int i5 = i + 6;
                if (i5 < height && array[i][i2] == (byte) 1 && array[i + 1][i2] == (byte) 0 && array[i + 2][i2] == (byte) 1 && array[i + 3][i2] == (byte) 1 && array[i + 4][i2] == (byte) 1 && array[i + 5][i2] == (byte) 0 && array[i5][i2] == (byte) 1 && (isWhiteVertical(array, i2, i - 4, i) || isWhiteVertical(array, i2, i + 7, i + 11))) {
                    i3++;
                }
                i2++;
            }
            i++;
            i2 = i3;
        }
        return i2 * 40;
    }

    private static boolean isWhiteHorizontal(byte[] bArr, int i, int i2) {
        i2 = Math.min(i2, bArr.length);
        for (i = Math.max(i, 0); i < i2; i++) {
            if (bArr[i] == (byte) 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isWhiteVertical(byte[][] bArr, int i, int i2, int i3) {
        i3 = Math.min(i3, bArr.length);
        for (i2 = Math.max(i2, 0); i2 < i3; i2++) {
            if (bArr[i2][i] == (byte) 1) {
                return false;
            }
        }
        return true;
    }

    static int applyMaskPenaltyRule4(ByteMatrix byteMatrix) {
        byte[][] array = byteMatrix.getArray();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        int i = 0;
        int i2 = 0;
        while (i < height) {
            byte[] bArr = array[i];
            int i3 = i2;
            for (i2 = 0; i2 < width; i2++) {
                if (bArr[i2] == (byte) 1) {
                    i3++;
                }
            }
            i++;
            i2 = i3;
        }
        int height2 = byteMatrix.getHeight() * byteMatrix.getWidth();
        return ((Math.abs((i2 << 1) - height2) * 10) / height2) * 10;
    }

    /* JADX WARNING: Missing block: B:6:0x0024, code:
            r1 = r1 & 1;
     */
    /* JADX WARNING: Missing block: B:11:0x003a, code:
            r3 = r3 + r2;
     */
    /* JADX WARNING: Missing block: B:12:0x003b, code:
            r1 = r3 & 1;
     */
    /* JADX WARNING: Missing block: B:13:0x003d, code:
            if (r1 != 0) goto L_0x0040;
     */
    /* JADX WARNING: Missing block: B:14:0x003f, code:
            return true;
     */
    /* JADX WARNING: Missing block: B:16:0x0041, code:
            return false;
     */
    static boolean getDataMaskBit(int r1, int r2, int r3) {
        /*
        r0 = 1;
        switch(r1) {
            case 0: goto L_0x003a;
            case 1: goto L_0x003b;
            case 2: goto L_0x0037;
            case 3: goto L_0x0033;
            case 4: goto L_0x002e;
            case 5: goto L_0x0026;
            case 6: goto L_0x001d;
            case 7: goto L_0x0014;
            default: goto L_0x0004;
        };
    L_0x0004:
        r2 = new java.lang.IllegalArgumentException;
        r1 = java.lang.String.valueOf(r1);
        r3 = "Invalid mask pattern: ";
        r1 = r3.concat(r1);
        r2.<init>(r1);
        throw r2;
    L_0x0014:
        r1 = r3 * r2;
        r1 = r1 % 3;
        r3 = r3 + r2;
        r2 = r3 & 1;
        r1 = r1 + r2;
        goto L_0x0024;
    L_0x001d:
        r3 = r3 * r2;
        r1 = r3 & 1;
        r3 = r3 % 3;
        r1 = r1 + r3;
    L_0x0024:
        r1 = r1 & r0;
        goto L_0x003d;
    L_0x0026:
        r3 = r3 * r2;
        r1 = r3 & 1;
        r3 = r3 % 3;
        r1 = r1 + r3;
        goto L_0x003d;
    L_0x002e:
        r3 = r3 / 2;
        r2 = r2 / 3;
        goto L_0x003a;
    L_0x0033:
        r3 = r3 + r2;
        r1 = r3 % 3;
        goto L_0x003d;
    L_0x0037:
        r1 = r2 % 3;
        goto L_0x003d;
    L_0x003a:
        r3 = r3 + r2;
    L_0x003b:
        r1 = r3 & 1;
    L_0x003d:
        if (r1 != 0) goto L_0x0040;
    L_0x003f:
        return r0;
    L_0x0040:
        r1 = 0;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.encoder.MaskUtil.getDataMaskBit(int, int, int):boolean");
    }

    private static int applyMaskPenaltyRule1Internal(ByteMatrix byteMatrix, boolean z) {
        int height = z ? byteMatrix.getHeight() : byteMatrix.getWidth();
        int width = z ? byteMatrix.getWidth() : byteMatrix.getHeight();
        byte[][] array = byteMatrix.getArray();
        int i = 0;
        int i2 = 0;
        while (i < height) {
            int i3 = i2;
            i2 = 0;
            int i4 = 0;
            byte b = (byte) -1;
            while (i2 < width) {
                byte b2 = z ? array[i][i2] : array[i2][i];
                if (b2 == b) {
                    i4++;
                } else {
                    if (i4 >= 5) {
                        i3 += (i4 - 5) + 3;
                    }
                    i4 = 1;
                    b = b2;
                }
                i2++;
            }
            if (i4 >= 5) {
                i3 += (i4 - 5) + 3;
            }
            i2 = i3;
            i++;
        }
        return i2;
    }
}
