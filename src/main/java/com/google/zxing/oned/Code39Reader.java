package com.google.zxing.oned;

import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.drew.metadata.exif.makernotes.OlympusRawInfoMakernoteDirectory;
import com.google.common.base.Ascii;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public final class Code39Reader extends OneDReader {
    static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%";
    static final int ASTERISK_ENCODING = 148;
    static final int[] CHARACTER_ENCODINGS = new int[]{52, 289, 97, 352, 49, OlympusRawInfoMakernoteDirectory.TagWbRbLevelsDaylightFluor, 112, 37, OlympusRawInfoMakernoteDirectory.TagWbRbLevelsEveningSunlight, 100, 265, 73, 328, 25, 280, 88, 13, 268, 76, 28, 259, 67, ExifDirectoryBase.TAG_TILE_WIDTH, 19, 274, 82, 7, 262, 70, 22, 385, 193, 448, 145, 400, 208, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, 388, 196, NikonType2MakernoteDirectory.TAG_FLASH_INFO, 162, 138, 42};
    private final int[] counters;
    private final StringBuilder decodeRowResult;
    private final boolean extendedMode;
    private final boolean usingCheckDigit;

    public Code39Reader() {
        this(false);
    }

    public Code39Reader(boolean z) {
        this(z, false);
    }

    public Code39Reader(boolean z, boolean z2) {
        this.usingCheckDigit = z;
        this.extendedMode = z2;
        this.decodeRowResult = new StringBuilder(20);
        this.counters = new int[9];
    }

    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int[] iArr = this.counters;
        Arrays.fill(iArr, 0);
        Object obj = this.decodeRowResult;
        obj.setLength(0);
        int[] findAsteriskPattern = findAsteriskPattern(bitArray, iArr);
        int nextSet = bitArray.getNextSet(findAsteriskPattern[1]);
        int size = bitArray.getSize();
        while (true) {
            OneDReader.recordPattern(bitArray, nextSet, iArr);
            int toNarrowWidePattern = toNarrowWidePattern(iArr);
            if (toNarrowWidePattern >= 0) {
                int i2;
                char patternToChar = patternToChar(toNarrowWidePattern);
                obj.append(patternToChar);
                int i3 = nextSet;
                for (int i4 : iArr) {
                    i3 += i4;
                }
                int nextSet2 = bitArray.getNextSet(i3);
                if (patternToChar == '*') {
                    obj.setLength(obj.length() - 1);
                    i2 = 0;
                    for (int i32 : iArr) {
                        i2 += i32;
                    }
                    int i5 = (nextSet2 - nextSet) - i2;
                    if (nextSet2 == size || (i5 << 1) >= i2) {
                        if (this.usingCheckDigit) {
                            String str;
                            i5 = obj.length() - 1;
                            int i6 = 0;
                            size = 0;
                            while (true) {
                                str = ALPHABET_STRING;
                                if (i6 >= i5) {
                                    break;
                                }
                                size += str.indexOf(this.decodeRowResult.charAt(i6));
                                i6++;
                            }
                            if (obj.charAt(i5) == str.charAt(size % 43)) {
                                obj.setLength(i5);
                            } else {
                                throw ChecksumException.getChecksumInstance();
                            }
                        }
                        if (obj.length() != 0) {
                            String decodeExtended;
                            if (this.extendedMode) {
                                decodeExtended = decodeExtended(obj);
                            } else {
                                decodeExtended = obj.toString();
                            }
                            float f = ((float) nextSet) + (((float) i2) / 2.0f);
                            r5 = new ResultPoint[2];
                            float f2 = (float) i;
                            r5[0] = new ResultPoint(((float) (findAsteriskPattern[1] + findAsteriskPattern[0])) / 2.0f, f2);
                            r5[1] = new ResultPoint(f, f2);
                            return new Result(decodeExtended, null, r5, BarcodeFormat.CODE_39);
                        }
                        throw NotFoundException.getNotFoundInstance();
                    }
                    throw NotFoundException.getNotFoundInstance();
                }
                nextSet = nextSet2;
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
    }

    private static int[] findAsteriskPattern(BitArray bitArray, int[] iArr) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        int length = iArr.length;
        int i = nextSet;
        int i2 = 0;
        int i3 = 0;
        while (nextSet < size) {
            if (bitArray.get(nextSet) != i2) {
                iArr[i3] = iArr[i3] + 1;
            } else {
                if (i3 != length - 1) {
                    i3++;
                } else if (toNarrowWidePattern(iArr) == 148 && bitArray.isRange(Math.max(0, i - ((nextSet - i) / 2)), i, false)) {
                    return new int[]{i, nextSet};
                } else {
                    i += iArr[0] + iArr[1];
                    int i4 = i3 - 1;
                    System.arraycopy(iArr, 2, iArr, 0, i4);
                    iArr[i4] = 0;
                    iArr[i3] = 0;
                    i3--;
                }
                iArr[i3] = 1;
                i2 ^= 1;
            }
            nextSet++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int toNarrowWidePattern(int[] iArr) {
        int length = iArr.length;
        int i = 0;
        while (true) {
            int i2 = Integer.MAX_VALUE;
            for (int i3 : iArr) {
                if (i3 < i2 && i3 > i) {
                    i2 = i3;
                }
            }
            int i4 = 0;
            int i5 = 0;
            int i32 = 0;
            for (i = 0; i < length; i++) {
                int i6 = iArr[i];
                if (i6 > i2) {
                    i5 |= 1 << ((length - 1) - i);
                    i4++;
                    i32 += i6;
                }
            }
            if (i4 == 3) {
                for (int i7 = 0; i7 < length && i4 > 0; i7++) {
                    i = iArr[i7];
                    if (i > i2) {
                        i4--;
                        if ((i << 1) >= i32) {
                            return -1;
                        }
                    }
                }
                return i5;
            } else if (i4 <= 3) {
                return -1;
            } else {
                i = i2;
            }
        }
    }

    private static char patternToChar(int i) throws NotFoundException {
        int i2 = 0;
        while (true) {
            int[] iArr = CHARACTER_ENCODINGS;
            if (i2 < iArr.length) {
                if (iArr[i2] == i) {
                    return ALPHABET_STRING.charAt(i2);
                }
                i2++;
            } else if (i == 148) {
                return '*';
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
    }

    private static String decodeExtended(CharSequence charSequence) throws FormatException {
        int length = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt == '+' || charAt == '$' || charAt == '%' || charAt == '/') {
                int i2;
                i++;
                char charAt2 = charSequence.charAt(i);
                if (charAt != '$') {
                    if (charAt != '%') {
                        if (charAt != '+') {
                            if (charAt == '/') {
                                if (charAt2 >= 'A' && charAt2 <= 'O') {
                                    i2 = charAt2 - 32;
                                } else if (charAt2 == 'Z') {
                                    charAt = ':';
                                    stringBuilder.append(charAt);
                                } else {
                                    throw FormatException.getFormatInstance();
                                }
                            }
                        } else if (charAt2 < 'A' || charAt2 > 'Z') {
                            throw FormatException.getFormatInstance();
                        } else {
                            i2 = charAt2 + 32;
                        }
                    } else if (charAt2 >= 'A' && charAt2 <= 'E') {
                        i2 = charAt2 - 38;
                    } else if (charAt2 >= 'F' && charAt2 <= 'J') {
                        i2 = charAt2 - 11;
                    } else if (charAt2 >= 'K' && charAt2 <= 'O') {
                        i2 = charAt2 + 16;
                    } else if (charAt2 >= 'P' && charAt2 <= 'T') {
                        i2 = charAt2 + 43;
                    } else if (charAt2 != 'U') {
                        if (charAt2 == 'V') {
                            charAt = '@';
                        } else if (charAt2 == 'W') {
                            charAt = '`';
                        } else if (charAt2 == 'X' || charAt2 == 'Y' || charAt2 == 'Z') {
                            charAt = Ascii.MAX;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                        stringBuilder.append(charAt);
                    }
                    charAt = 0;
                    stringBuilder.append(charAt);
                } else if (charAt2 < 'A' || charAt2 > 'Z') {
                    throw FormatException.getFormatInstance();
                } else {
                    i2 = charAt2 - 64;
                }
                charAt = (char) i2;
                stringBuilder.append(charAt);
            } else {
                stringBuilder.append(charAt);
            }
            i++;
        }
        return stringBuilder.toString();
    }
}
