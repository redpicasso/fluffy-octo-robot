package com.google.zxing.oned;

import com.google.common.base.Ascii;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class Code128Writer extends OneDimensionalCodeWriter {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final char ESCAPE_FNC_1 = 'ñ';
    private static final char ESCAPE_FNC_2 = 'ò';
    private static final char ESCAPE_FNC_3 = 'ó';
    private static final char ESCAPE_FNC_4 = 'ô';

    private enum CType {
        UNCODABLE,
        ONE_DIGIT,
        TWO_DIGITS,
        FNC_1
    }

    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_128) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got ".concat(String.valueOf(barcodeFormat)));
    }

    public boolean[] encode(String str) {
        int length = str.length();
        if (length <= 0 || length > 80) {
            throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got ".concat(String.valueOf(length)));
        }
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            switch (charAt) {
                case 241:
                case 242:
                case 243:
                case 244:
                    break;
                default:
                    if (charAt <= Ascii.MAX) {
                        break;
                    }
                    throw new IllegalArgumentException("Bad character in input: ".concat(String.valueOf(charAt)));
            }
        }
        Collection<int[]> arrayList = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 1;
        while (i3 < length) {
            int chooseCode = chooseCode(str, i3, i5);
            int i7 = 100;
            if (chooseCode == i5) {
                switch (str.charAt(i3)) {
                    case 241:
                        i7 = 102;
                        break;
                    case 242:
                        i7 = 97;
                        break;
                    case 243:
                        i7 = 96;
                        break;
                    case 244:
                        if (i5 == 101) {
                            i7 = 101;
                            break;
                        }
                        break;
                    default:
                        if (i5 != 100) {
                            if (i5 == 101) {
                                i7 = str.charAt(i3) - 32;
                                if (i7 < 0) {
                                    i7 += 96;
                                    break;
                                }
                            }
                            i7 = Integer.parseInt(str.substring(i3, i3 + 2));
                            i3++;
                            break;
                        }
                        i7 = str.charAt(i3) - 32;
                        break;
                        break;
                }
                i3++;
            } else {
                i7 = i5 == 0 ? chooseCode != 100 ? chooseCode != 101 ? 105 : 103 : 104 : chooseCode;
                i5 = chooseCode;
            }
            arrayList.add(Code128Reader.CODE_PATTERNS[i7]);
            i4 += i7 * i6;
            if (i3 != 0) {
                i6++;
            }
        }
        arrayList.add(Code128Reader.CODE_PATTERNS[i4 % 103]);
        arrayList.add(Code128Reader.CODE_PATTERNS[106]);
        length = 0;
        for (int[] iArr : arrayList) {
            i5 = length;
            for (int i62 : (int[]) r13.next()) {
                i5 += i62;
            }
            length = i5;
        }
        boolean[] zArr = new boolean[length];
        for (int[] appendPattern : arrayList) {
            i += OneDimensionalCodeWriter.appendPattern(zArr, i, appendPattern, true);
        }
        return zArr;
    }

    private static CType findCType(CharSequence charSequence, int i) {
        int length = charSequence.length();
        if (i >= length) {
            return CType.UNCODABLE;
        }
        char charAt = charSequence.charAt(i);
        if (charAt == ESCAPE_FNC_1) {
            return CType.FNC_1;
        }
        if (charAt < '0' || charAt > '9') {
            return CType.UNCODABLE;
        }
        i++;
        if (i >= length) {
            return CType.ONE_DIGIT;
        }
        char charAt2 = charSequence.charAt(i);
        if (charAt2 < '0' || charAt2 > '9') {
            return CType.ONE_DIGIT;
        }
        return CType.TWO_DIGITS;
    }

    private static int chooseCode(CharSequence charSequence, int i, int i2) {
        CType findCType = findCType(charSequence, i);
        if (findCType == CType.ONE_DIGIT) {
            return 100;
        }
        if (findCType == CType.UNCODABLE) {
            if (i < charSequence.length()) {
                char charAt = charSequence.charAt(i);
                if (charAt < ' ' || (i2 == 101 && charAt < '`')) {
                    return 101;
                }
            }
            return 100;
        } else if (i2 == 99) {
            return 99;
        } else {
            if (i2 != 100) {
                if (findCType == CType.FNC_1) {
                    findCType = findCType(charSequence, i + 1);
                }
                if (findCType == CType.TWO_DIGITS) {
                    return 99;
                }
                return 100;
            } else if (findCType == CType.FNC_1) {
                return 100;
            } else {
                CType findCType2 = findCType(charSequence, i + 2);
                if (findCType2 == CType.UNCODABLE || findCType2 == CType.ONE_DIGIT) {
                    return 100;
                }
                if (findCType2 != CType.FNC_1) {
                    i += 4;
                    while (true) {
                        findCType2 = findCType(charSequence, i);
                        if (findCType2 != CType.TWO_DIGITS) {
                            break;
                        }
                        i += 2;
                    }
                    if (findCType2 == CType.ONE_DIGIT) {
                        return 100;
                    }
                    return 99;
                } else if (findCType(charSequence, i + 3) == CType.TWO_DIGITS) {
                    return 99;
                } else {
                    return 100;
                }
            }
        }
    }
}
