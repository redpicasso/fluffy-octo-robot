package com.google.zxing.oned;

import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.OlympusRawInfoMakernoteDirectory;
import com.drew.metadata.iptc.IptcDirectory;
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

public final class Code93Reader extends OneDReader {
    private static final char[] ALPHABET = ALPHABET_STRING.toCharArray();
    static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";
    private static final int ASTERISK_ENCODING;
    static final int[] CHARACTER_ENCODINGS;
    private final int[] counters = new int[6];
    private final StringBuilder decodeRowResult = new StringBuilder(20);

    static {
        int[] iArr = new int[]{276, 328, ExifDirectoryBase.TAG_TILE_OFFSETS, ExifDirectoryBase.TAG_TILE_WIDTH, 296, OlympusRawInfoMakernoteDirectory.TagWbRbLevelsEveningSunlight, OlympusRawInfoMakernoteDirectory.TagWbRbLevelsFineWeather, IptcDirectory.TAG_TIME_SENT, 274, 266, 424, 420, 418, 404, 402, 394, 360, IptcDirectory.TAG_UNIQUE_OBJECT_NAME, 354, 308, 282, 344, 332, IptcDirectory.TAG_DATE_SENT, 300, 278, 436, 434, 428, 422, 406, 410, 364, 358, 310, 314, 302, 468, 466, 458, 366, 374, 430, 294, 474, 470, 306, 350};
        CHARACTER_ENCODINGS = iArr;
        ASTERISK_ENCODING = iArr[47];
    }

    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int[] findAsteriskPattern = findAsteriskPattern(bitArray);
        int nextSet = bitArray.getNextSet(findAsteriskPattern[1]);
        int size = bitArray.getSize();
        int[] iArr = this.counters;
        Arrays.fill(iArr, 0);
        CharSequence charSequence = this.decodeRowResult;
        charSequence.setLength(0);
        while (true) {
            OneDReader.recordPattern(bitArray, nextSet, iArr);
            int toPattern = toPattern(iArr);
            if (toPattern >= 0) {
                char patternToChar = patternToChar(toPattern);
                charSequence.append(patternToChar);
                int i2 = nextSet;
                for (int i3 : iArr) {
                    i2 += i3;
                }
                int nextSet2 = bitArray.getNextSet(i2);
                if (patternToChar == '*') {
                    charSequence.deleteCharAt(charSequence.length() - 1);
                    i2 = 0;
                    for (int i32 : iArr) {
                        i2 += i32;
                    }
                    if (nextSet2 == size || !bitArray.get(nextSet2)) {
                        throw NotFoundException.getNotFoundInstance();
                    } else if (charSequence.length() >= 2) {
                        checkChecksums(charSequence);
                        charSequence.setLength(charSequence.length() - 2);
                        String decodeExtended = decodeExtended(charSequence);
                        float f = ((float) nextSet) + (((float) i2) / 2.0f);
                        r2 = new ResultPoint[2];
                        float f2 = (float) i;
                        r2[0] = new ResultPoint(((float) (findAsteriskPattern[1] + findAsteriskPattern[0])) / 2.0f, f2);
                        r2[1] = new ResultPoint(f, f2);
                        return new Result(decodeExtended, null, r2, BarcodeFormat.CODE_93);
                    } else {
                        throw NotFoundException.getNotFoundInstance();
                    }
                }
                nextSet = nextSet2;
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        }
    }

    private int[] findAsteriskPattern(BitArray bitArray) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        Arrays.fill(this.counters, 0);
        Object obj = this.counters;
        int length = obj.length;
        int i = nextSet;
        int i2 = 0;
        int i3 = 0;
        while (nextSet < size) {
            if (bitArray.get(nextSet) != i2) {
                obj[i3] = obj[i3] + 1;
            } else {
                if (i3 != length - 1) {
                    i3++;
                } else if (toPattern(obj) == ASTERISK_ENCODING) {
                    return new int[]{i, nextSet};
                } else {
                    i += obj[0] + obj[1];
                    int i4 = i3 - 1;
                    System.arraycopy(obj, 2, obj, 0, i4);
                    obj[i4] = null;
                    obj[i3] = null;
                    i3--;
                }
                obj[i3] = 1;
                i2 ^= 1;
            }
            nextSet++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int toPattern(int[] iArr) {
        int i;
        int i2 = 0;
        for (int i3 : iArr) {
            i2 += i3;
        }
        int length = iArr.length;
        int i32 = 0;
        for (i = 0; i < length; i++) {
            int round = Math.round((((float) iArr[i]) * 9.0f) / ((float) i2));
            if (round <= 0 || round > 4) {
                return -1;
            }
            if ((i & 1) == 0) {
                int i4 = i32;
                for (i32 = 0; i32 < round; i32++) {
                    i4 = (i4 << 1) | 1;
                }
                i32 = i4;
            } else {
                i32 <<= round;
            }
        }
        return i32;
    }

    private static char patternToChar(int i) throws NotFoundException {
        int i2 = 0;
        while (true) {
            int[] iArr = CHARACTER_ENCODINGS;
            if (i2 >= iArr.length) {
                throw NotFoundException.getNotFoundInstance();
            } else if (iArr[i2] == i) {
                return ALPHABET[i2];
            } else {
                i2++;
            }
        }
    }

    private static java.lang.String decodeExtended(java.lang.CharSequence r9) throws com.google.zxing.FormatException {
        /*
        r0 = r9.length();
        r1 = new java.lang.StringBuilder;
        r1.<init>(r0);
        r2 = 0;
        r3 = 0;
    L_0x000b:
        if (r3 >= r0) goto L_0x009f;
    L_0x000d:
        r4 = r9.charAt(r3);
        r5 = 97;
        if (r4 < r5) goto L_0x0098;
    L_0x0015:
        r5 = 100;
        if (r4 > r5) goto L_0x0098;
    L_0x0019:
        r5 = r0 + -1;
        if (r3 >= r5) goto L_0x0093;
    L_0x001d:
        r3 = r3 + 1;
        r5 = r9.charAt(r3);
        r6 = 79;
        r7 = 90;
        r8 = 65;
        switch(r4) {
            case 97: goto L_0x0082;
            case 98: goto L_0x004c;
            case 99: goto L_0x003b;
            case 100: goto L_0x002f;
            default: goto L_0x002c;
        };
    L_0x002c:
        r4 = 0;
        goto L_0x008f;
    L_0x002f:
        if (r5 < r8) goto L_0x0036;
    L_0x0031:
        if (r5 > r7) goto L_0x0036;
    L_0x0033:
        r5 = r5 + 32;
        goto L_0x0088;
    L_0x0036:
        r9 = com.google.zxing.FormatException.getFormatInstance();
        throw r9;
    L_0x003b:
        if (r5 < r8) goto L_0x0042;
    L_0x003d:
        if (r5 > r6) goto L_0x0042;
    L_0x003f:
        r5 = r5 + -32;
        goto L_0x0088;
    L_0x0042:
        if (r5 != r7) goto L_0x0047;
    L_0x0044:
        r4 = 58;
        goto L_0x008f;
    L_0x0047:
        r9 = com.google.zxing.FormatException.getFormatInstance();
        throw r9;
    L_0x004c:
        if (r5 < r8) goto L_0x0055;
    L_0x004e:
        r4 = 69;
        if (r5 > r4) goto L_0x0055;
    L_0x0052:
        r5 = r5 + -38;
        goto L_0x0088;
    L_0x0055:
        r4 = 70;
        if (r5 < r4) goto L_0x0060;
    L_0x0059:
        r4 = 74;
        if (r5 > r4) goto L_0x0060;
    L_0x005d:
        r5 = r5 + -11;
        goto L_0x0088;
    L_0x0060:
        r4 = 75;
        if (r5 < r4) goto L_0x0069;
    L_0x0064:
        if (r5 > r6) goto L_0x0069;
    L_0x0066:
        r5 = r5 + 16;
        goto L_0x0088;
    L_0x0069:
        r4 = 80;
        if (r5 < r4) goto L_0x0074;
    L_0x006d:
        r4 = 83;
        if (r5 > r4) goto L_0x0074;
    L_0x0071:
        r5 = r5 + 43;
        goto L_0x0088;
    L_0x0074:
        r4 = 84;
        if (r5 < r4) goto L_0x007d;
    L_0x0078:
        if (r5 > r7) goto L_0x007d;
    L_0x007a:
        r4 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        goto L_0x008f;
    L_0x007d:
        r9 = com.google.zxing.FormatException.getFormatInstance();
        throw r9;
    L_0x0082:
        if (r5 < r8) goto L_0x008a;
    L_0x0084:
        if (r5 > r7) goto L_0x008a;
    L_0x0086:
        r5 = r5 + -64;
    L_0x0088:
        r4 = (char) r5;
        goto L_0x008f;
    L_0x008a:
        r9 = com.google.zxing.FormatException.getFormatInstance();
        throw r9;
    L_0x008f:
        r1.append(r4);
        goto L_0x009b;
    L_0x0093:
        r9 = com.google.zxing.FormatException.getFormatInstance();
        throw r9;
    L_0x0098:
        r1.append(r4);
    L_0x009b:
        r3 = r3 + 1;
        goto L_0x000b;
    L_0x009f:
        r9 = r1.toString();
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code93Reader.decodeExtended(java.lang.CharSequence):java.lang.String");
    }

    private static void checkChecksums(CharSequence charSequence) throws ChecksumException {
        int length = charSequence.length();
        checkOneChecksum(charSequence, length - 2, 20);
        checkOneChecksum(charSequence, length - 1, 15);
    }

    private static void checkOneChecksum(CharSequence charSequence, int i, int i2) throws ChecksumException {
        int i3 = 0;
        int i4 = 1;
        for (int i5 = i - 1; i5 >= 0; i5--) {
            i3 += ALPHABET_STRING.indexOf(charSequence.charAt(i5)) * i4;
            i4++;
            if (i4 > i2) {
                i4 = 1;
            }
        }
        if (charSequence.charAt(i) != ALPHABET[i3 % 47]) {
            throw ChecksumException.getChecksumInstance();
        }
    }
}
