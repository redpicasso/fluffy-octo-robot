package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public class Code93Writer extends OneDimensionalCodeWriter {
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_93) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_93, but got ".concat(String.valueOf(barcodeFormat)));
    }

    public boolean[] encode(String str) {
        int length = str.length();
        if (length <= 80) {
            int[] iArr = new int[9];
            int length2 = (((str.length() + 2) + 2) * 9) + 1;
            toIntArray(Code93Reader.CHARACTER_ENCODINGS[47], iArr);
            boolean[] zArr = new boolean[length2];
            int i = 0;
            int appendPattern = appendPattern(zArr, 0, iArr);
            while (true) {
                String str2 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";
                if (i < length) {
                    toIntArray(Code93Reader.CHARACTER_ENCODINGS[str2.indexOf(str.charAt(i))], iArr);
                    appendPattern += appendPattern(zArr, appendPattern, iArr);
                    i++;
                } else {
                    length = computeChecksumIndex(str, 20);
                    toIntArray(Code93Reader.CHARACTER_ENCODINGS[length], iArr);
                    appendPattern += appendPattern(zArr, appendPattern, iArr);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(str);
                    stringBuilder.append(str2.charAt(length));
                    toIntArray(Code93Reader.CHARACTER_ENCODINGS[computeChecksumIndex(stringBuilder.toString(), 15)], iArr);
                    appendPattern += appendPattern(zArr, appendPattern, iArr);
                    toIntArray(Code93Reader.CHARACTER_ENCODINGS[47], iArr);
                    zArr[appendPattern + appendPattern(zArr, appendPattern, iArr)] = true;
                    return zArr;
                }
            }
        }
        throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got ".concat(String.valueOf(length)));
    }

    private static void toIntArray(int i, int[] iArr) {
        for (int i2 = 0; i2 < 9; i2++) {
            int i3 = 1;
            if (((1 << (8 - i2)) & i) == 0) {
                i3 = 0;
            }
            iArr[i2] = i3;
        }
    }

    @Deprecated
    protected static int appendPattern(boolean[] zArr, int i, int[] iArr, boolean z) {
        return appendPattern(zArr, i, iArr);
    }

    private static int appendPattern(boolean[] zArr, int i, int[] iArr) {
        int length = iArr.length;
        int i2 = i;
        i = 0;
        while (i < length) {
            int i3 = i2 + 1;
            zArr[i2] = iArr[i] != 0;
            i++;
            i2 = i3;
        }
        return 9;
    }

    private static int computeChecksumIndex(String str, int i) {
        int i2 = 0;
        int i3 = 1;
        for (int length = str.length() - 1; length >= 0; length--) {
            i2 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(length)) * i3;
            i3++;
            if (i3 > i) {
                i3 = 1;
            }
        }
        return i2 % 47;
    }
}
