package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.EnumMap;
import java.util.Map;

final class UPCEANExtension5Support {
    private static final int[] CHECK_DIGIT_ENCODINGS = new int[]{24, 20, 18, 17, 12, 6, 3, 10, 9, 5};
    private final int[] decodeMiddleCounters = new int[4];
    private final StringBuilder decodeRowStringBuffer = new StringBuilder();

    UPCEANExtension5Support() {
    }

    Result decodeRow(int i, BitArray bitArray, int[] iArr) throws NotFoundException {
        StringBuilder stringBuilder = this.decodeRowStringBuffer;
        stringBuilder.setLength(0);
        int decodeMiddle = decodeMiddle(bitArray, iArr, stringBuilder);
        String stringBuilder2 = stringBuilder.toString();
        Map parseExtensionString = parseExtensionString(stringBuilder2);
        r4 = new ResultPoint[2];
        float f = (float) i;
        r4[0] = new ResultPoint(((float) (iArr[0] + iArr[1])) / 2.0f, f);
        r4[1] = new ResultPoint((float) decodeMiddle, f);
        Result result = new Result(stringBuilder2, null, r4, BarcodeFormat.UPC_EAN_EXTENSION);
        if (parseExtensionString != null) {
            result.putAllMetadata(parseExtensionString);
        }
        return result;
    }

    private int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder stringBuilder) throws NotFoundException {
        int[] iArr2 = this.decodeMiddleCounters;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int size = bitArray.getSize();
        int i = iArr[1];
        int i2 = 0;
        int i3 = 0;
        while (i2 < 5 && i < size) {
            int decodeDigit = UPCEANReader.decodeDigit(bitArray, iArr2, i, UPCEANReader.L_AND_G_PATTERNS);
            stringBuilder.append((char) ((decodeDigit % 10) + 48));
            int i4 = i;
            for (int i5 : iArr2) {
                i4 += i5;
            }
            if (decodeDigit >= 10) {
                i3 |= 1 << (4 - i2);
            }
            i = i2 != 4 ? bitArray.getNextUnset(bitArray.getNextSet(i4)) : i4;
            i2++;
        }
        if (stringBuilder.length() == 5) {
            if (extensionChecksum(stringBuilder.toString()) == determineCheckDigit(i3)) {
                return i;
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int extensionChecksum(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        for (int i2 = length - 2; i2 >= 0; i2 -= 2) {
            i += charSequence.charAt(i2) - 48;
        }
        i *= 3;
        for (length--; length >= 0; length -= 2) {
            i += charSequence.charAt(length) - 48;
        }
        return (i * 3) % 10;
    }

    private static int determineCheckDigit(int i) throws NotFoundException {
        for (int i2 = 0; i2 < 10; i2++) {
            if (i == CHECK_DIGIT_ENCODINGS[i2]) {
                return i2;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static Map<ResultMetadataType, Object> parseExtensionString(String str) {
        if (str.length() != 5) {
            return null;
        }
        str = parseExtension5String(str);
        if (str == null) {
            return null;
        }
        Map<ResultMetadataType, Object> enumMap = new EnumMap(ResultMetadataType.class);
        enumMap.put(ResultMetadataType.SUGGESTED_PRICE, str);
        return enumMap;
    }

    /* JADX WARNING: Missing block: B:15:0x0039, code:
            if (r6.equals("90000") != false) goto L_0x003d;
     */
    private static java.lang.String parseExtension5String(java.lang.String r6) {
        /*
        r0 = 0;
        r1 = r6.charAt(r0);
        r2 = 48;
        r3 = "";
        r4 = 1;
        if (r1 == r2) goto L_0x004f;
    L_0x000c:
        r2 = 53;
        if (r1 == r2) goto L_0x004c;
    L_0x0010:
        r2 = 57;
        if (r1 == r2) goto L_0x0015;
    L_0x0014:
        goto L_0x0051;
    L_0x0015:
        r1 = -1;
        r2 = r6.hashCode();
        r5 = 2;
        switch(r2) {
            case 54118329: goto L_0x0033;
            case 54395376: goto L_0x0029;
            case 54395377: goto L_0x001f;
            default: goto L_0x001e;
        };
    L_0x001e:
        goto L_0x003c;
    L_0x001f:
        r0 = "99991";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x003c;
    L_0x0027:
        r0 = 1;
        goto L_0x003d;
    L_0x0029:
        r0 = "99990";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x003c;
    L_0x0031:
        r0 = 2;
        goto L_0x003d;
    L_0x0033:
        r2 = "90000";
        r2 = r6.equals(r2);
        if (r2 == 0) goto L_0x003c;
    L_0x003b:
        goto L_0x003d;
    L_0x003c:
        r0 = -1;
    L_0x003d:
        if (r0 == 0) goto L_0x004a;
    L_0x003f:
        if (r0 == r4) goto L_0x0047;
    L_0x0041:
        if (r0 == r5) goto L_0x0044;
    L_0x0043:
        goto L_0x0051;
    L_0x0044:
        r6 = "Used";
        return r6;
    L_0x0047:
        r6 = "0.00";
        return r6;
    L_0x004a:
        r6 = 0;
        return r6;
    L_0x004c:
        r3 = "$";
        goto L_0x0051;
    L_0x004f:
        r3 = "£";
    L_0x0051:
        r6 = r6.substring(r4);
        r6 = java.lang.Integer.parseInt(r6);
        r0 = r6 / 100;
        r0 = java.lang.String.valueOf(r0);
        r6 = r6 % 100;
        r1 = 10;
        if (r6 >= r1) goto L_0x0070;
    L_0x0065:
        r6 = java.lang.String.valueOf(r6);
        r1 = "0";
        r6 = r1.concat(r6);
        goto L_0x0074;
    L_0x0070:
        r6 = java.lang.String.valueOf(r6);
    L_0x0074:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r1.append(r3);
        r1.append(r0);
        r0 = 46;
        r1.append(r0);
        r1.append(r6);
        r6 = r1.toString();
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.UPCEANExtension5Support.parseExtension5String(java.lang.String):java.lang.String");
    }
}
