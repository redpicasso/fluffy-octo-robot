package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public final class CodaBarReader extends OneDReader {
    static final char[] ALPHABET = ALPHABET_STRING.toCharArray();
    private static final String ALPHABET_STRING = "0123456789-$:/.+ABCD";
    static final int[] CHARACTER_ENCODINGS = new int[]{3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
    private static final float MAX_ACCEPTABLE = 2.0f;
    private static final int MIN_CHARACTER_LENGTH = 3;
    private static final float PADDING = 1.5f;
    private static final char[] STARTEND_ENCODING = new char[]{'A', 'B', 'C', 'D'};
    private int counterLength = 0;
    private int[] counters = new int[80];
    private final StringBuilder decodeRowResult = new StringBuilder(20);

    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        int toNarrowWidePattern;
        Arrays.fill(this.counters, 0);
        setCounters(bitArray);
        int findStartPattern = findStartPattern();
        this.decodeRowResult.setLength(0);
        int i2 = findStartPattern;
        do {
            toNarrowWidePattern = toNarrowWidePattern(i2);
            if (toNarrowWidePattern != -1) {
                this.decodeRowResult.append((char) toNarrowWidePattern);
                i2 += 8;
                if (this.decodeRowResult.length() > 1 && arrayContains(STARTEND_ENCODING, ALPHABET[toNarrowWidePattern])) {
                    break;
                }
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } while (i2 < this.counterLength);
        int i3 = i2 - 1;
        toNarrowWidePattern = this.counters[i3];
        int i4 = 0;
        for (int i5 = -8; i5 < -1; i5++) {
            i4 += this.counters[i2 + i5];
        }
        if (i2 >= this.counterLength || toNarrowWidePattern >= i4 / 2) {
            validatePattern(findStartPattern);
            for (i2 = 0; i2 < this.decodeRowResult.length(); i2++) {
                StringBuilder stringBuilder = this.decodeRowResult;
                stringBuilder.setCharAt(i2, ALPHABET[stringBuilder.charAt(i2)]);
            }
            if (arrayContains(STARTEND_ENCODING, this.decodeRowResult.charAt(0))) {
                StringBuilder stringBuilder2 = this.decodeRowResult;
                if (!arrayContains(STARTEND_ENCODING, stringBuilder2.charAt(stringBuilder2.length() - 1))) {
                    throw NotFoundException.getNotFoundInstance();
                } else if (this.decodeRowResult.length() > 3) {
                    if (map == null || !map.containsKey(DecodeHintType.RETURN_CODABAR_START_END)) {
                        StringBuilder stringBuilder3 = this.decodeRowResult;
                        stringBuilder3.deleteCharAt(stringBuilder3.length() - 1);
                        this.decodeRowResult.deleteCharAt(0);
                    }
                    i2 = 0;
                    for (int i6 = 0; i6 < findStartPattern; i6++) {
                        i2 += this.counters[i6];
                    }
                    float f = (float) i2;
                    while (findStartPattern < i3) {
                        i2 += this.counters[findStartPattern];
                        findStartPattern++;
                    }
                    float f2 = (float) i2;
                    String stringBuilder4 = this.decodeRowResult.toString();
                    r4 = new ResultPoint[2];
                    float f3 = (float) i;
                    r4[0] = new ResultPoint(f, f3);
                    r4[1] = new ResultPoint(f2, f3);
                    return new Result(stringBuilder4, null, r4, BarcodeFormat.CODABAR);
                } else {
                    throw NotFoundException.getNotFoundInstance();
                }
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void validatePattern(int i) throws NotFoundException {
        int i2;
        int[] iArr = new int[]{0, 0, 0, 0};
        int[] iArr2 = new int[]{0, 0, 0, 0};
        int length = this.decodeRowResult.length() - 1;
        int i3 = 0;
        int i4 = i;
        int i5 = 0;
        while (true) {
            int i6 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(i5)];
            for (i2 = 6; i2 >= 0; i2--) {
                int i7 = (i2 & 1) + ((i6 & 1) << 1);
                iArr[i7] = iArr[i7] + this.counters[i4 + i2];
                iArr2[i7] = iArr2[i7] + 1;
                i6 >>= 1;
            }
            if (i5 >= length) {
                break;
            }
            i4 += 8;
            i5++;
        }
        float[] fArr = new float[4];
        float[] fArr2 = new float[4];
        for (i4 = 0; i4 < 2; i4++) {
            fArr2[i4] = 0.0f;
            i2 = i4 + 2;
            fArr2[i2] = ((((float) iArr[i4]) / ((float) iArr2[i4])) + (((float) iArr[i2]) / ((float) iArr2[i2]))) / MAX_ACCEPTABLE;
            fArr[i4] = fArr2[i2];
            fArr[i2] = ((((float) iArr[i2]) * MAX_ACCEPTABLE) + PADDING) / ((float) iArr2[i2]);
        }
        loop3:
        while (true) {
            int i8 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(i3)];
            int i9 = 6;
            while (i9 >= 0) {
                i4 = (i9 & 1) + ((i8 & 1) << 1);
                float f = (float) this.counters[i + i9];
                if (f >= fArr2[i4] && f <= fArr[i4]) {
                    i8 >>= 1;
                    i9--;
                }
            }
            if (i3 < length) {
                i += 8;
                i3++;
            } else {
                return;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void setCounters(BitArray bitArray) throws NotFoundException {
        int i = 0;
        this.counterLength = 0;
        int nextUnset = bitArray.getNextUnset(0);
        int size = bitArray.getSize();
        if (nextUnset < size) {
            int i2 = 1;
            while (nextUnset < size) {
                if (bitArray.get(nextUnset) != i2) {
                    i++;
                } else {
                    counterAppend(i);
                    i2 ^= 1;
                    i = 1;
                }
                nextUnset++;
            }
            counterAppend(i);
            return;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void counterAppend(int i) {
        Object obj = this.counters;
        int i2 = this.counterLength;
        obj[i2] = i;
        this.counterLength = i2 + 1;
        i = this.counterLength;
        if (i >= obj.length) {
            Object obj2 = new int[(i << 1)];
            System.arraycopy(obj, 0, obj2, 0, i);
            this.counters = obj2;
        }
    }

    private int findStartPattern() throws NotFoundException {
        int i = 1;
        while (i < this.counterLength) {
            int toNarrowWidePattern = toNarrowWidePattern(i);
            if (toNarrowWidePattern != -1 && arrayContains(STARTEND_ENCODING, ALPHABET[toNarrowWidePattern])) {
                int i2 = 0;
                for (toNarrowWidePattern = i; toNarrowWidePattern < i + 7; toNarrowWidePattern++) {
                    i2 += this.counters[toNarrowWidePattern];
                }
                if (i == 1 || this.counters[i - 1] >= i2 / 2) {
                    return i;
                }
            }
            i += 2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    static boolean arrayContains(char[] cArr, char c) {
        if (cArr != null) {
            for (char c2 : cArr) {
                if (c2 == c) {
                    return true;
                }
            }
        }
        return false;
    }

    private int toNarrowWidePattern(int i) {
        int i2 = i + 7;
        if (i2 >= this.counterLength) {
            return -1;
        }
        int i3;
        int i4;
        int[] iArr = this.counters;
        int i5 = Integer.MAX_VALUE;
        int i6 = 0;
        int i7 = Integer.MAX_VALUE;
        int i8 = 0;
        for (i3 = i; i3 < i2; i3 += 2) {
            i4 = iArr[i3];
            if (i4 < i7) {
                i7 = i4;
            }
            if (i4 > i8) {
                i8 = i4;
            }
        }
        i7 = (i7 + i8) / 2;
        i8 = 0;
        for (i3 = i + 1; i3 < i2; i3 += 2) {
            i4 = iArr[i3];
            if (i4 < i5) {
                i5 = i4;
            }
            if (i4 > i8) {
                i8 = i4;
            }
        }
        i2 = (i5 + i8) / 2;
        i3 = 128;
        i8 = 0;
        for (i5 = 0; i5 < 7; i5++) {
            i3 >>= 1;
            if (iArr[i + i5] > ((i5 & 1) == 0 ? i7 : i2)) {
                i8 |= i3;
            }
        }
        while (true) {
            int[] iArr2 = CHARACTER_ENCODINGS;
            if (i6 >= iArr2.length) {
                return -1;
            }
            if (iArr2[i6] == i8) {
                return i6;
            }
            i6++;
        }
    }
}
