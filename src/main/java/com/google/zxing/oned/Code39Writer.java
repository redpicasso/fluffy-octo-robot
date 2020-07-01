package com.google.zxing.oned;

import com.google.common.base.Ascii;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class Code39Writer extends OneDimensionalCodeWriter {
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_39) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_39, but got ".concat(String.valueOf(barcodeFormat)));
    }

    public boolean[] encode(String str) {
        int length = str.length();
        String str2 = "Requested contents should be less than 80 digits long, but got ";
        if (length <= 80) {
            String str3;
            int i;
            int i2 = 0;
            while (true) {
                str3 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%";
                if (i2 >= length) {
                    break;
                } else if (str3.indexOf(str.charAt(i2)) < 0) {
                    str = tryToConvertToExtendedMode(str);
                    length = str.length();
                    if (length > 80) {
                        StringBuilder stringBuilder = new StringBuilder(str2);
                        stringBuilder.append(length);
                        stringBuilder.append(" (extended full ASCII mode)");
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                } else {
                    i2++;
                }
            }
            int[] iArr = new int[9];
            int i3 = length + 25;
            i2 = 0;
            while (i2 < length) {
                toIntArray(Code39Reader.CHARACTER_ENCODINGS[str3.indexOf(str.charAt(i2))], iArr);
                i = i3;
                for (i3 = 0; i3 < 9; i3++) {
                    i += iArr[i3];
                }
                i2++;
                i3 = i;
            }
            boolean[] zArr = new boolean[i3];
            toIntArray(148, iArr);
            i = OneDimensionalCodeWriter.appendPattern(zArr, 0, iArr, true);
            int[] iArr2 = new int[]{1};
            int appendPattern = i + OneDimensionalCodeWriter.appendPattern(zArr, i, iArr2, false);
            for (i = 0; i < length; i++) {
                toIntArray(Code39Reader.CHARACTER_ENCODINGS[str3.indexOf(str.charAt(i))], iArr);
                appendPattern += OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, iArr, true);
                appendPattern += OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, iArr2, false);
            }
            toIntArray(148, iArr);
            OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, iArr, true);
            return zArr;
        }
        throw new IllegalArgumentException(str2.concat(String.valueOf(length)));
    }

    private static void toIntArray(int i, int[] iArr) {
        for (int i2 = 0; i2 < 9; i2++) {
            int i3 = 1;
            if (((1 << (8 - i2)) & i) != 0) {
                i3 = 2;
            }
            iArr[i2] = i3;
        }
    }

    private static String tryToConvertToExtendedMode(String str) {
        int length = str.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt != 0) {
                if (charAt != ' ') {
                    if (charAt == '@') {
                        stringBuilder.append("%V");
                    } else if (charAt == '`') {
                        stringBuilder.append("%W");
                    } else if (!(charAt == '-' || charAt == '.')) {
                        if (charAt <= 26) {
                            stringBuilder.append('$');
                            stringBuilder.append((char) ((charAt - 1) + 65));
                        } else if (charAt < ' ') {
                            stringBuilder.append('%');
                            stringBuilder.append((char) ((charAt - 27) + 65));
                        } else if (charAt <= ',' || charAt == '/' || charAt == ':') {
                            stringBuilder.append('/');
                            stringBuilder.append((char) ((charAt - 33) + 65));
                        } else if (charAt <= '9') {
                            stringBuilder.append((char) ((charAt - 48) + 48));
                        } else if (charAt <= '?') {
                            stringBuilder.append('%');
                            stringBuilder.append((char) ((charAt - 59) + 70));
                        } else if (charAt <= 'Z') {
                            stringBuilder.append((char) ((charAt - 65) + 65));
                        } else if (charAt <= '_') {
                            stringBuilder.append('%');
                            stringBuilder.append((char) ((charAt - 91) + 75));
                        } else if (charAt <= 'z') {
                            stringBuilder.append('+');
                            stringBuilder.append((char) ((charAt - 97) + 65));
                        } else if (charAt <= Ascii.MAX) {
                            stringBuilder.append('%');
                            stringBuilder.append((char) ((charAt - 123) + 80));
                        } else {
                            stringBuilder = new StringBuilder("Requested content contains a non-encodable character: '");
                            stringBuilder.append(str.charAt(i));
                            stringBuilder.append("'");
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                    }
                }
                stringBuilder.append(charAt);
            } else {
                stringBuilder.append("%U");
            }
        }
        return stringBuilder.toString();
    }
}
