package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

public final class Code128Reader extends OneDReader {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    static final int[][] CODE_PATTERNS = new int[][]{new int[]{2, 1, 2, 2, 2, 2}, new int[]{2, 2, 2, 1, 2, 2}, new int[]{2, 2, 2, 2, 2, 1}, new int[]{1, 2, 1, 2, 2, 3}, new int[]{1, 2, 1, 3, 2, 2}, new int[]{1, 3, 1, 2, 2, 2}, new int[]{1, 2, 2, 2, 1, 3}, new int[]{1, 2, 2, 3, 1, 2}, new int[]{1, 3, 2, 2, 1, 2}, new int[]{2, 2, 1, 2, 1, 3}, new int[]{2, 2, 1, 3, 1, 2}, new int[]{2, 3, 1, 2, 1, 2}, new int[]{1, 1, 2, 2, 3, 2}, new int[]{1, 2, 2, 1, 3, 2}, new int[]{1, 2, 2, 2, 3, 1}, new int[]{1, 1, 3, 2, 2, 2}, new int[]{1, 2, 3, 1, 2, 2}, new int[]{1, 2, 3, 2, 2, 1}, new int[]{2, 2, 3, 2, 1, 1}, new int[]{2, 2, 1, 1, 3, 2}, new int[]{2, 2, 1, 2, 3, 1}, new int[]{2, 1, 3, 2, 1, 2}, new int[]{2, 2, 3, 1, 1, 2}, new int[]{3, 1, 2, 1, 3, 1}, new int[]{3, 1, 1, 2, 2, 2}, new int[]{3, 2, 1, 1, 2, 2}, new int[]{3, 2, 1, 2, 2, 1}, new int[]{3, 1, 2, 2, 1, 2}, new int[]{3, 2, 2, 1, 1, 2}, new int[]{3, 2, 2, 2, 1, 1}, new int[]{2, 1, 2, 1, 2, 3}, new int[]{2, 1, 2, 3, 2, 1}, new int[]{2, 3, 2, 1, 2, 1}, new int[]{1, 1, 1, 3, 2, 3}, new int[]{1, 3, 1, 1, 2, 3}, new int[]{1, 3, 1, 3, 2, 1}, new int[]{1, 1, 2, 3, 1, 3}, new int[]{1, 3, 2, 1, 1, 3}, new int[]{1, 3, 2, 3, 1, 1}, new int[]{2, 1, 1, 3, 1, 3}, new int[]{2, 3, 1, 1, 1, 3}, new int[]{2, 3, 1, 3, 1, 1}, new int[]{1, 1, 2, 1, 3, 3}, new int[]{1, 1, 2, 3, 3, 1}, new int[]{1, 3, 2, 1, 3, 1}, new int[]{1, 1, 3, 1, 2, 3}, new int[]{1, 1, 3, 3, 2, 1}, new int[]{1, 3, 3, 1, 2, 1}, new int[]{3, 1, 3, 1, 2, 1}, new int[]{2, 1, 1, 3, 3, 1}, new int[]{2, 3, 1, 1, 3, 1}, new int[]{2, 1, 3, 1, 1, 3}, new int[]{2, 1, 3, 3, 1, 1}, new int[]{2, 1, 3, 1, 3, 1}, new int[]{3, 1, 1, 1, 2, 3}, new int[]{3, 1, 1, 3, 2, 1}, new int[]{3, 3, 1, 1, 2, 1}, new int[]{3, 1, 2, 1, 1, 3}, new int[]{3, 1, 2, 3, 1, 1}, new int[]{3, 3, 2, 1, 1, 1}, new int[]{3, 1, 4, 1, 1, 1}, new int[]{2, 2, 1, 4, 1, 1}, new int[]{4, 3, 1, 1, 1, 1}, new int[]{1, 1, 1, 2, 2, 4}, new int[]{1, 1, 1, 4, 2, 2}, new int[]{1, 2, 1, 1, 2, 4}, new int[]{1, 2, 1, 4, 2, 1}, new int[]{1, 4, 1, 1, 2, 2}, new int[]{1, 4, 1, 2, 2, 1}, new int[]{1, 1, 2, 2, 1, 4}, new int[]{1, 1, 2, 4, 1, 2}, new int[]{1, 2, 2, 1, 1, 4}, new int[]{1, 2, 2, 4, 1, 1}, new int[]{1, 4, 2, 1, 1, 2}, new int[]{1, 4, 2, 2, 1, 1}, new int[]{2, 4, 1, 2, 1, 1}, new int[]{2, 2, 1, 1, 1, 4}, new int[]{4, 1, 3, 1, 1, 1}, new int[]{2, 4, 1, 1, 1, 2}, new int[]{1, 3, 4, 1, 1, 1}, new int[]{1, 1, 1, 2, 4, 2}, new int[]{1, 2, 1, 1, 4, 2}, new int[]{1, 2, 1, 2, 4, 1}, new int[]{1, 1, 4, 2, 1, 2}, new int[]{1, 2, 4, 1, 1, 2}, new int[]{1, 2, 4, 2, 1, 1}, new int[]{4, 1, 1, 2, 1, 2}, new int[]{4, 2, 1, 1, 1, 2}, new int[]{4, 2, 1, 2, 1, 1}, new int[]{2, 1, 2, 1, 4, 1}, new int[]{2, 1, 4, 1, 2, 1}, new int[]{4, 1, 2, 1, 2, 1}, new int[]{1, 1, 1, 1, 4, 3}, new int[]{1, 1, 1, 3, 4, 1}, new int[]{1, 3, 1, 1, 4, 1}, new int[]{1, 1, 4, 1, 1, 3}, new int[]{1, 1, 4, 3, 1, 1}, new int[]{4, 1, 1, 1, 1, 3}, new int[]{4, 1, 1, 3, 1, 1}, new int[]{1, 1, 3, 1, 4, 1}, new int[]{1, 1, 4, 1, 3, 1}, new int[]{3, 1, 1, 1, 4, 1}, new int[]{4, 1, 1, 1, 3, 1}, new int[]{2, 1, 1, 4, 1, 2}, new int[]{2, 1, 1, 2, 1, 4}, new int[]{2, 1, 1, 2, 3, 2}, new int[]{2, 3, 3, 1, 1, 1, 2}};
    private static final int CODE_SHIFT = 98;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final float MAX_AVG_VARIANCE = 0.25f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;

    private static int[] findStartPattern(BitArray bitArray) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        Object obj = new int[6];
        int i = nextSet;
        int i2 = 0;
        int i3 = 0;
        while (nextSet < size) {
            if (bitArray.get(nextSet) != i2) {
                obj[i3] = obj[i3] + 1;
            } else {
                if (i3 == 5) {
                    int i4;
                    float f = MAX_AVG_VARIANCE;
                    int i5 = -1;
                    for (i4 = 103; i4 <= 105; i4++) {
                        float patternMatchVariance = OneDReader.patternMatchVariance(obj, CODE_PATTERNS[i4], MAX_INDIVIDUAL_VARIANCE);
                        if (patternMatchVariance < f) {
                            i5 = i4;
                            f = patternMatchVariance;
                        }
                    }
                    if (i5 < 0 || !bitArray.isRange(Math.max(0, i - ((nextSet - i) / 2)), i, false)) {
                        i += obj[0] + obj[1];
                        i4 = i3 - 1;
                        System.arraycopy(obj, 2, obj, 0, i4);
                        obj[i4] = null;
                        obj[i3] = null;
                        i3--;
                    } else {
                        return new int[]{i, nextSet, i5};
                    }
                }
                i3++;
                obj[i3] = 1;
                i2 ^= 1;
            }
            nextSet++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int decodeCode(BitArray bitArray, int[] iArr, int i) throws NotFoundException {
        OneDReader.recordPattern(bitArray, i, iArr);
        float f = MAX_AVG_VARIANCE;
        i = -1;
        int i2 = 0;
        while (true) {
            int[][] iArr2 = CODE_PATTERNS;
            if (i2 >= iArr2.length) {
                break;
            }
            float patternMatchVariance = OneDReader.patternMatchVariance(iArr, iArr2[i2], MAX_INDIVIDUAL_VARIANCE);
            if (patternMatchVariance < f) {
                i = i2;
                f = patternMatchVariance;
            }
            i2++;
        }
        if (i >= 0) {
            return i;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARNING: Missing block: B:51:0x00e1, code:
            if (r5 != null) goto L_0x0135;
     */
    /* JADX WARNING: Missing block: B:58:0x0100, code:
            r5 = null;
     */
    /* JADX WARNING: Missing block: B:59:0x0103, code:
            r12 = null;
     */
    /* JADX WARNING: Missing block: B:73:0x012c, code:
            r5 = null;
            r11 = 1;
     */
    /* JADX WARNING: Missing block: B:75:0x0133, code:
            if (r5 != null) goto L_0x0135;
     */
    /* JADX WARNING: Missing block: B:76:0x0135, code:
            r5 = null;
            r11 = null;
     */
    /* JADX WARNING: Missing block: B:77:0x013a, code:
            r5 = null;
            r12 = 1;
     */
    /* JADX WARNING: Missing block: B:78:0x013f, code:
            r12 = r5;
            r3 = 99;
     */
    /* JADX WARNING: Missing block: B:80:0x0146, code:
            r5 = 1;
     */
    /* JADX WARNING: Missing block: B:81:0x0148, code:
            r6 = 1;
     */
    /* JADX WARNING: Missing block: B:82:0x0149, code:
            r12 = r5;
     */
    /* JADX WARNING: Missing block: B:83:0x014a, code:
            r5 = null;
     */
    /* JADX WARNING: Missing block: B:104:0x0189, code:
            r12 = r5;
     */
    /* JADX WARNING: Missing block: B:105:0x018a, code:
            r5 = null;
     */
    /* JADX WARNING: Missing block: B:106:0x018b, code:
            if (r14 == null) goto L_0x0197;
     */
    /* JADX WARNING: Missing block: B:108:0x018f, code:
            if (r3 != 101) goto L_0x0194;
     */
    /* JADX WARNING: Missing block: B:109:0x0191, code:
            r3 = 100;
     */
    /* JADX WARNING: Missing block: B:110:0x0194, code:
            r3 = 101;
     */
    /* JADX WARNING: Missing block: B:112:0x0199, code:
            r14 = r5;
            r5 = r12;
            r15 = 6;
            r12 = r8;
            r8 = r21;
            r23 = r16;
            r16 = r9;
            r9 = r23;
     */
    public com.google.zxing.Result decodeRow(int r25, com.google.zxing.common.BitArray r26, java.util.Map<com.google.zxing.DecodeHintType, ?> r27) throws com.google.zxing.NotFoundException, com.google.zxing.FormatException, com.google.zxing.ChecksumException {
        /*
        r24 = this;
        r0 = r26;
        r1 = r27;
        r2 = 1;
        r3 = 0;
        if (r1 == 0) goto L_0x0012;
    L_0x0008:
        r4 = com.google.zxing.DecodeHintType.ASSUME_GS1;
        r1 = r1.containsKey(r4);
        if (r1 == 0) goto L_0x0012;
    L_0x0010:
        r1 = 1;
        goto L_0x0013;
    L_0x0012:
        r1 = 0;
    L_0x0013:
        r4 = findStartPattern(r26);
        r5 = 2;
        r6 = r4[r5];
        r7 = new java.util.ArrayList;
        r8 = 20;
        r7.<init>(r8);
        r9 = (byte) r6;
        r9 = java.lang.Byte.valueOf(r9);
        r7.add(r9);
        switch(r6) {
            case 103: goto L_0x0037;
            case 104: goto L_0x0034;
            case 105: goto L_0x0031;
            default: goto L_0x002c;
        };
    L_0x002c:
        r0 = com.google.zxing.FormatException.getFormatInstance();
        throw r0;
    L_0x0031:
        r12 = 99;
        goto L_0x0039;
    L_0x0034:
        r12 = 100;
        goto L_0x0039;
    L_0x0037:
        r12 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x0039:
        r13 = new java.lang.StringBuilder;
        r13.<init>(r8);
        r8 = r4[r3];
        r14 = r4[r2];
        r15 = 6;
        r2 = new int[r15];
        r17 = r6;
        r3 = r12;
        r5 = 0;
        r6 = 0;
        r9 = 0;
        r11 = 0;
        r16 = 0;
        r18 = 0;
        r19 = 1;
        r12 = r8;
        r8 = r14;
        r14 = 0;
    L_0x0055:
        if (r6 != 0) goto L_0x01a7;
    L_0x0057:
        r9 = decodeCode(r0, r2, r8);
        r12 = (byte) r9;
        r12 = java.lang.Byte.valueOf(r12);
        r7.add(r12);
        r12 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r9 == r12) goto L_0x0069;
    L_0x0067:
        r19 = 1;
    L_0x0069:
        if (r9 == r12) goto L_0x0071;
    L_0x006b:
        r18 = r18 + 1;
        r20 = r18 * r9;
        r17 = r17 + r20;
    L_0x0071:
        r21 = r8;
        r10 = 0;
    L_0x0074:
        if (r10 >= r15) goto L_0x007d;
    L_0x0076:
        r22 = r2[r10];
        r21 = r21 + r22;
        r10 = r10 + 1;
        goto L_0x0074;
    L_0x007d:
        switch(r9) {
            case 103: goto L_0x008b;
            case 104: goto L_0x008b;
            case 105: goto L_0x008b;
            default: goto L_0x0080;
        };
    L_0x0080:
        r10 = 96;
        r15 = "]C1";
        switch(r3) {
            case 99: goto L_0x014e;
            case 100: goto L_0x00ed;
            case 101: goto L_0x0090;
            default: goto L_0x0087;
        };
    L_0x0087:
        r10 = 100;
        goto L_0x0189;
    L_0x008b:
        r0 = com.google.zxing.FormatException.getFormatInstance();
        throw r0;
    L_0x0090:
        r12 = 64;
        if (r9 >= r12) goto L_0x00a7;
    L_0x0094:
        if (r5 != r11) goto L_0x009e;
    L_0x0096:
        r5 = r9 + 32;
        r5 = (char) r5;
        r13.append(r5);
        goto L_0x0100;
    L_0x009e:
        r5 = r9 + 32;
        r5 = r5 + 128;
        r5 = (char) r5;
        r13.append(r5);
        goto L_0x0100;
    L_0x00a7:
        if (r9 >= r10) goto L_0x00b9;
    L_0x00a9:
        if (r5 != r11) goto L_0x00b2;
    L_0x00ab:
        r5 = r9 + -64;
        r5 = (char) r5;
        r13.append(r5);
        goto L_0x0100;
    L_0x00b2:
        r5 = r9 + 64;
        r5 = (char) r5;
        r13.append(r5);
        goto L_0x0100;
    L_0x00b9:
        r10 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r9 == r10) goto L_0x00bf;
    L_0x00bd:
        r19 = 0;
    L_0x00bf:
        if (r9 == r10) goto L_0x0148;
    L_0x00c1:
        switch(r9) {
            case 96: goto L_0x0149;
            case 97: goto L_0x0149;
            case 98: goto L_0x00e9;
            case 99: goto L_0x013f;
            case 100: goto L_0x00e4;
            case 101: goto L_0x00da;
            case 102: goto L_0x00c6;
            default: goto L_0x00c4;
        };
    L_0x00c4:
        goto L_0x0149;
    L_0x00c6:
        if (r1 == 0) goto L_0x0149;
    L_0x00c8:
        r10 = r13.length();
        if (r10 != 0) goto L_0x00d3;
    L_0x00ce:
        r13.append(r15);
        goto L_0x0149;
    L_0x00d3:
        r10 = 29;
        r13.append(r10);
        goto L_0x0149;
    L_0x00da:
        if (r11 != 0) goto L_0x00df;
    L_0x00dc:
        if (r5 == 0) goto L_0x00df;
    L_0x00de:
        goto L_0x012c;
    L_0x00df:
        if (r11 == 0) goto L_0x013a;
    L_0x00e1:
        if (r5 == 0) goto L_0x013a;
    L_0x00e3:
        goto L_0x0135;
    L_0x00e4:
        r12 = r5;
        r3 = 100;
        goto L_0x014a;
    L_0x00e9:
        r12 = r5;
        r3 = 100;
        goto L_0x0146;
    L_0x00ed:
        if (r9 >= r10) goto L_0x0106;
    L_0x00ef:
        if (r5 != r11) goto L_0x00f8;
    L_0x00f1:
        r5 = r9 + 32;
        r5 = (char) r5;
        r13.append(r5);
        goto L_0x0100;
    L_0x00f8:
        r5 = r9 + 32;
        r5 = r5 + 128;
        r5 = (char) r5;
        r13.append(r5);
    L_0x0100:
        r5 = 0;
        r10 = 100;
    L_0x0103:
        r12 = 0;
        goto L_0x018b;
    L_0x0106:
        r10 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r9 == r10) goto L_0x010c;
    L_0x010a:
        r19 = 0;
    L_0x010c:
        if (r9 == r10) goto L_0x0148;
    L_0x010e:
        switch(r9) {
            case 96: goto L_0x0149;
            case 97: goto L_0x0149;
            case 98: goto L_0x0143;
            case 99: goto L_0x013f;
            case 100: goto L_0x0128;
            case 101: goto L_0x0124;
            case 102: goto L_0x0112;
            default: goto L_0x0111;
        };
    L_0x0111:
        goto L_0x0149;
    L_0x0112:
        if (r1 == 0) goto L_0x0149;
    L_0x0114:
        r10 = r13.length();
        if (r10 != 0) goto L_0x011e;
    L_0x011a:
        r13.append(r15);
        goto L_0x0149;
    L_0x011e:
        r10 = 29;
        r13.append(r10);
        goto L_0x0149;
    L_0x0124:
        r12 = r5;
        r3 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x014a;
    L_0x0128:
        if (r11 != 0) goto L_0x0131;
    L_0x012a:
        if (r5 == 0) goto L_0x0131;
    L_0x012c:
        r5 = 0;
        r10 = 100;
        r11 = 1;
        goto L_0x0103;
    L_0x0131:
        if (r11 == 0) goto L_0x013a;
    L_0x0133:
        if (r5 == 0) goto L_0x013a;
    L_0x0135:
        r5 = 0;
        r10 = 100;
        r11 = 0;
        goto L_0x0103;
    L_0x013a:
        r5 = 0;
        r10 = 100;
        r12 = 1;
        goto L_0x018b;
    L_0x013f:
        r12 = r5;
        r3 = 99;
        goto L_0x014a;
    L_0x0143:
        r12 = r5;
        r3 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x0146:
        r5 = 1;
        goto L_0x014b;
    L_0x0148:
        r6 = 1;
    L_0x0149:
        r12 = r5;
    L_0x014a:
        r5 = 0;
    L_0x014b:
        r10 = 100;
        goto L_0x018b;
    L_0x014e:
        r10 = 100;
        if (r9 >= r10) goto L_0x015f;
    L_0x0152:
        r12 = 10;
        if (r9 >= r12) goto L_0x015b;
    L_0x0156:
        r12 = 48;
        r13.append(r12);
    L_0x015b:
        r13.append(r9);
        goto L_0x0189;
    L_0x015f:
        r12 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r9 == r12) goto L_0x0165;
    L_0x0163:
        r19 = 0;
    L_0x0165:
        if (r9 == r12) goto L_0x0185;
    L_0x0167:
        switch(r9) {
            case 100: goto L_0x0181;
            case 101: goto L_0x017d;
            case 102: goto L_0x016b;
            default: goto L_0x016a;
        };
    L_0x016a:
        goto L_0x0189;
    L_0x016b:
        if (r1 == 0) goto L_0x0189;
    L_0x016d:
        r12 = r13.length();
        if (r12 != 0) goto L_0x0177;
    L_0x0173:
        r13.append(r15);
        goto L_0x0189;
    L_0x0177:
        r12 = 29;
        r13.append(r12);
        goto L_0x0189;
    L_0x017d:
        r12 = r5;
        r3 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x018a;
    L_0x0181:
        r12 = r5;
        r3 = 100;
        goto L_0x018a;
    L_0x0185:
        r12 = r5;
        r5 = 0;
        r6 = 1;
        goto L_0x018b;
    L_0x0189:
        r12 = r5;
    L_0x018a:
        r5 = 0;
    L_0x018b:
        if (r14 == 0) goto L_0x0197;
    L_0x018d:
        r14 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r3 != r14) goto L_0x0194;
    L_0x0191:
        r3 = 100;
        goto L_0x0199;
    L_0x0194:
        r3 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x0199;
    L_0x0197:
        r14 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x0199:
        r14 = r5;
        r5 = r12;
        r15 = 6;
        r12 = r8;
        r8 = r21;
        r23 = r16;
        r16 = r9;
        r9 = r23;
        goto L_0x0055;
    L_0x01a7:
        r1 = r8 - r12;
        r2 = r0.getNextUnset(r8);
        r5 = r26.getSize();
        r6 = r2 - r12;
        r8 = 2;
        r6 = r6 / r8;
        r6 = r6 + r2;
        r5 = java.lang.Math.min(r5, r6);
        r6 = 0;
        r0 = r0.isRange(r2, r5, r6);
        if (r0 == 0) goto L_0x0235;
    L_0x01c1:
        r18 = r18 * r9;
        r17 = r17 - r18;
        r0 = r17 % 103;
        if (r0 != r9) goto L_0x0230;
    L_0x01c9:
        r0 = r13.length();
        if (r0 == 0) goto L_0x022b;
    L_0x01cf:
        if (r0 <= 0) goto L_0x01e2;
    L_0x01d1:
        if (r19 == 0) goto L_0x01e2;
    L_0x01d3:
        r2 = 99;
        if (r3 != r2) goto L_0x01dd;
    L_0x01d7:
        r2 = r0 + -2;
        r13.delete(r2, r0);
        goto L_0x01e2;
    L_0x01dd:
        r2 = r0 + -1;
        r13.delete(r2, r0);
    L_0x01e2:
        r0 = 1;
        r2 = r4[r0];
        r0 = 0;
        r3 = r4[r0];
        r2 = r2 + r3;
        r0 = (float) r2;
        r2 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r0 / r2;
        r3 = (float) r12;
        r1 = (float) r1;
        r1 = r1 / r2;
        r3 = r3 + r1;
        r1 = r7.size();
        r2 = new byte[r1];
        r4 = 0;
    L_0x01f8:
        if (r4 >= r1) goto L_0x0209;
    L_0x01fa:
        r5 = r7.get(r4);
        r5 = (java.lang.Byte) r5;
        r5 = r5.byteValue();
        r2[r4] = r5;
        r4 = r4 + 1;
        goto L_0x01f8;
    L_0x0209:
        r1 = new com.google.zxing.Result;
        r4 = r13.toString();
        r5 = 2;
        r5 = new com.google.zxing.ResultPoint[r5];
        r6 = new com.google.zxing.ResultPoint;
        r7 = r25;
        r7 = (float) r7;
        r6.<init>(r0, r7);
        r0 = 0;
        r5[r0] = r6;
        r0 = new com.google.zxing.ResultPoint;
        r0.<init>(r3, r7);
        r3 = 1;
        r5[r3] = r0;
        r0 = com.google.zxing.BarcodeFormat.CODE_128;
        r1.<init>(r4, r2, r5, r0);
        return r1;
    L_0x022b:
        r0 = com.google.zxing.NotFoundException.getNotFoundInstance();
        throw r0;
    L_0x0230:
        r0 = com.google.zxing.ChecksumException.getChecksumInstance();
        throw r0;
    L_0x0235:
        r0 = com.google.zxing.NotFoundException.getNotFoundInstance();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code128Reader.decodeRow(int, com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }
}
