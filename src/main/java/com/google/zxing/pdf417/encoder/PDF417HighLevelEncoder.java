package com.google.zxing.pdf417.encoder;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

final class PDF417HighLevelEncoder {
    private static final int BYTE_COMPACTION = 1;
    private static final Charset DEFAULT_ENCODING = StandardCharsets.ISO_8859_1;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final int LATCH_TO_BYTE = 924;
    private static final int LATCH_TO_BYTE_PADDED = 901;
    private static final int LATCH_TO_NUMERIC = 902;
    private static final int LATCH_TO_TEXT = 900;
    private static final byte[] MIXED = new byte[128];
    private static final int NUMERIC_COMPACTION = 2;
    private static final byte[] PUNCTUATION = new byte[128];
    private static final int SHIFT_TO_BYTE = 913;
    private static final int SUBMODE_ALPHA = 0;
    private static final int SUBMODE_LOWER = 1;
    private static final int SUBMODE_MIXED = 2;
    private static final int SUBMODE_PUNCTUATION = 3;
    private static final int TEXT_COMPACTION = 0;
    private static final byte[] TEXT_MIXED_RAW = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 38, Ascii.CR, (byte) 9, (byte) 44, (byte) 58, (byte) 35, (byte) 45, (byte) 46, (byte) 36, (byte) 47, (byte) 43, (byte) 37, (byte) 42, (byte) 61, (byte) 94, (byte) 0, (byte) 32, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] TEXT_PUNCTUATION_RAW = new byte[]{(byte) 59, (byte) 60, (byte) 62, SignedBytes.MAX_POWER_OF_TWO, (byte) 91, (byte) 92, (byte) 93, (byte) 95, (byte) 96, (byte) 126, (byte) 33, Ascii.CR, (byte) 9, (byte) 44, (byte) 58, (byte) 10, (byte) 45, (byte) 46, (byte) 36, (byte) 47, (byte) 34, (byte) 124, (byte) 42, (byte) 40, (byte) 41, (byte) 63, (byte) 123, (byte) 125, (byte) 39, (byte) 0};

    /* renamed from: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$pdf417$encoder$Compaction = new int[Compaction.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$google$zxing$pdf417$encoder$Compaction[com.google.zxing.pdf417.encoder.Compaction.NUMERIC.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.google.zxing.pdf417.encoder.Compaction.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$zxing$pdf417$encoder$Compaction = r0;
            r0 = $SwitchMap$com$google$zxing$pdf417$encoder$Compaction;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.zxing.pdf417.encoder.Compaction.TEXT;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$zxing$pdf417$encoder$Compaction;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.zxing.pdf417.encoder.Compaction.BYTE;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$zxing$pdf417$encoder$Compaction;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.zxing.pdf417.encoder.Compaction.NUMERIC;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.1.<clinit>():void");
        }
    }

    private static boolean isAlphaLower(char c) {
        return c == ' ' || (c >= 'a' && c <= 'z');
    }

    private static boolean isAlphaUpper(char c) {
        return c == ' ' || (c >= 'A' && c <= 'Z');
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isText(char c) {
        return c == 9 || c == 10 || c == 13 || (c >= ' ' && c <= '~');
    }

    static {
        Arrays.fill(MIXED, (byte) -1);
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = TEXT_MIXED_RAW;
            if (i2 >= bArr.length) {
                break;
            }
            byte b = bArr[i2];
            if (b > (byte) 0) {
                MIXED[b] = (byte) i2;
            }
            i2++;
        }
        Arrays.fill(PUNCTUATION, (byte) -1);
        while (true) {
            byte[] bArr2 = TEXT_PUNCTUATION_RAW;
            if (i < bArr2.length) {
                byte b2 = bArr2[i];
                if (b2 > (byte) 0) {
                    PUNCTUATION[b2] = (byte) i;
                }
                i++;
            } else {
                return;
            }
        }
    }

    private PDF417HighLevelEncoder() {
    }

    static String encodeHighLevel(String str, Compaction compaction, Charset charset) throws WriterException {
        StringBuilder stringBuilder = new StringBuilder(str.length());
        if (charset == null) {
            charset = DEFAULT_ENCODING;
        } else if (!DEFAULT_ENCODING.equals(charset)) {
            CharacterSetECI characterSetECIByName = CharacterSetECI.getCharacterSetECIByName(charset.name());
            if (characterSetECIByName != null) {
                encodingECI(characterSetECIByName.getValue(), stringBuilder);
            }
        }
        int length = str.length();
        int i = AnonymousClass1.$SwitchMap$com$google$zxing$pdf417$encoder$Compaction[compaction.ordinal()];
        if (i == 1) {
            encodeText(str, 0, length, stringBuilder, 0);
        } else if (i == 2) {
            byte[] bytes = str.getBytes(charset);
            encodeBinary(bytes, 0, bytes.length, 1, stringBuilder);
        } else if (i != 3) {
            i = 0;
            int i2 = 0;
            int i3 = 0;
            while (i < length) {
                int determineConsecutiveDigitCount = determineConsecutiveDigitCount(str, i);
                if (determineConsecutiveDigitCount >= 13) {
                    stringBuilder.append(902);
                    encodeNumeric(str, i, determineConsecutiveDigitCount, stringBuilder);
                    i += determineConsecutiveDigitCount;
                    i2 = 0;
                    i3 = 2;
                } else {
                    int determineConsecutiveTextCount = determineConsecutiveTextCount(str, i);
                    if (determineConsecutiveTextCount >= 5 || determineConsecutiveDigitCount == length) {
                        if (i3 != 0) {
                            stringBuilder.append(900);
                            i2 = 0;
                            i3 = 0;
                        }
                        i2 = encodeText(str, i, determineConsecutiveTextCount, stringBuilder, i2);
                        i += determineConsecutiveTextCount;
                    } else {
                        determineConsecutiveDigitCount = determineConsecutiveBinaryCount(str, i, charset);
                        if (determineConsecutiveDigitCount == 0) {
                            determineConsecutiveDigitCount = 1;
                        }
                        determineConsecutiveDigitCount += i;
                        byte[] bytes2 = str.substring(i, determineConsecutiveDigitCount).getBytes(charset);
                        if (bytes2.length == 1 && i3 == 0) {
                            encodeBinary(bytes2, 0, 1, 0, stringBuilder);
                        } else {
                            encodeBinary(bytes2, 0, bytes2.length, i3, stringBuilder);
                            i2 = 0;
                            i3 = 1;
                        }
                        i = determineConsecutiveDigitCount;
                    }
                }
            }
        } else {
            stringBuilder.append(902);
            encodeNumeric(str, 0, length, stringBuilder);
        }
        return stringBuilder.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:73:0x0011 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00f6 A:{SYNTHETIC, EDGE_INSN: B:68:0x00f6->B:53:0x00f6 ?: BREAK  } */
    private static int encodeText(java.lang.CharSequence r16, int r17, int r18, java.lang.StringBuilder r19, int r20) {
        /*
        r0 = r16;
        r1 = r18;
        r2 = r19;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r1);
        r4 = 2;
        r5 = 0;
        r6 = 1;
        r8 = r20;
        r7 = 0;
    L_0x0011:
        r9 = r17 + r7;
        r10 = r0.charAt(r9);
        r11 = 26;
        r12 = 32;
        r13 = 28;
        r14 = 27;
        r15 = 29;
        if (r8 == 0) goto L_0x00bc;
    L_0x0023:
        if (r8 == r6) goto L_0x0083;
    L_0x0025:
        if (r8 == r4) goto L_0x003c;
    L_0x0027:
        r9 = isPunctuation(r10);
        if (r9 == 0) goto L_0x0037;
    L_0x002d:
        r9 = PUNCTUATION;
        r9 = r9[r10];
        r9 = (char) r9;
        r3.append(r9);
        goto L_0x00f2;
    L_0x0037:
        r3.append(r15);
    L_0x003a:
        r8 = 0;
        goto L_0x0011;
    L_0x003c:
        r11 = isMixed(r10);
        if (r11 == 0) goto L_0x004c;
    L_0x0042:
        r9 = MIXED;
        r9 = r9[r10];
        r9 = (char) r9;
        r3.append(r9);
        goto L_0x00f2;
    L_0x004c:
        r11 = isAlphaUpper(r10);
        if (r11 == 0) goto L_0x0056;
    L_0x0052:
        r3.append(r13);
        goto L_0x003a;
    L_0x0056:
        r11 = isAlphaLower(r10);
        if (r11 == 0) goto L_0x0061;
    L_0x005c:
        r3.append(r14);
        goto L_0x00d8;
    L_0x0061:
        r9 = r9 + 1;
        if (r9 >= r1) goto L_0x0076;
    L_0x0065:
        r9 = r0.charAt(r9);
        r9 = isPunctuation(r9);
        if (r9 == 0) goto L_0x0076;
    L_0x006f:
        r8 = 3;
        r9 = 25;
        r3.append(r9);
        goto L_0x0011;
    L_0x0076:
        r3.append(r15);
        r9 = PUNCTUATION;
        r9 = r9[r10];
        r9 = (char) r9;
        r3.append(r9);
        goto L_0x00f2;
    L_0x0083:
        r9 = isAlphaLower(r10);
        if (r9 == 0) goto L_0x0096;
    L_0x0089:
        if (r10 != r12) goto L_0x008f;
    L_0x008b:
        r3.append(r11);
        goto L_0x00f2;
    L_0x008f:
        r10 = r10 + -97;
        r9 = (char) r10;
        r3.append(r9);
        goto L_0x00f2;
    L_0x0096:
        r9 = isAlphaUpper(r10);
        if (r9 == 0) goto L_0x00a6;
    L_0x009c:
        r3.append(r14);
        r10 = r10 + -65;
        r9 = (char) r10;
        r3.append(r9);
        goto L_0x00f2;
    L_0x00a6:
        r9 = isMixed(r10);
        if (r9 == 0) goto L_0x00b0;
    L_0x00ac:
        r3.append(r13);
        goto L_0x00e4;
    L_0x00b0:
        r3.append(r15);
        r9 = PUNCTUATION;
        r9 = r9[r10];
        r9 = (char) r9;
        r3.append(r9);
        goto L_0x00f2;
    L_0x00bc:
        r9 = isAlphaUpper(r10);
        if (r9 == 0) goto L_0x00cf;
    L_0x00c2:
        if (r10 != r12) goto L_0x00c8;
    L_0x00c4:
        r3.append(r11);
        goto L_0x00f2;
    L_0x00c8:
        r10 = r10 + -65;
        r9 = (char) r10;
        r3.append(r9);
        goto L_0x00f2;
    L_0x00cf:
        r9 = isAlphaLower(r10);
        if (r9 == 0) goto L_0x00db;
    L_0x00d5:
        r3.append(r14);
    L_0x00d8:
        r8 = 1;
        goto L_0x0011;
    L_0x00db:
        r9 = isMixed(r10);
        if (r9 == 0) goto L_0x00e7;
    L_0x00e1:
        r3.append(r13);
    L_0x00e4:
        r8 = 2;
        goto L_0x0011;
    L_0x00e7:
        r3.append(r15);
        r9 = PUNCTUATION;
        r9 = r9[r10];
        r9 = (char) r9;
        r3.append(r9);
    L_0x00f2:
        r7 = r7 + 1;
        if (r7 < r1) goto L_0x0011;
    L_0x00f6:
        r0 = r3.length();
        r1 = 0;
        r7 = 0;
    L_0x00fc:
        if (r1 >= r0) goto L_0x011a;
    L_0x00fe:
        r9 = r1 % 2;
        if (r9 == 0) goto L_0x0104;
    L_0x0102:
        r9 = 1;
        goto L_0x0105;
    L_0x0104:
        r9 = 0;
    L_0x0105:
        if (r9 == 0) goto L_0x0113;
    L_0x0107:
        r7 = r7 * 30;
        r9 = r3.charAt(r1);
        r7 = r7 + r9;
        r7 = (char) r7;
        r2.append(r7);
        goto L_0x0117;
    L_0x0113:
        r7 = r3.charAt(r1);
    L_0x0117:
        r1 = r1 + 1;
        goto L_0x00fc;
    L_0x011a:
        r0 = r0 % r4;
        if (r0 == 0) goto L_0x0124;
    L_0x011d:
        r7 = r7 * 30;
        r7 = r7 + r15;
        r0 = (char) r7;
        r2.append(r0);
    L_0x0124:
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.encodeText(java.lang.CharSequence, int, int, java.lang.StringBuilder, int):int");
    }

    private static void encodeBinary(byte[] bArr, int i, int i2, int i3, StringBuilder stringBuilder) {
        int i4;
        if (i2 == 1 && i3 == 0) {
            stringBuilder.append(913);
        } else if (i2 % 6 == 0) {
            stringBuilder.append(924);
        } else {
            stringBuilder.append(901);
        }
        if (i2 >= 6) {
            char[] cArr = new char[5];
            i4 = i;
            while ((i + i2) - i4 >= 6) {
                int i5;
                long j = 0;
                for (i5 = 0; i5 < 6; i5++) {
                    j = (j << 8) + ((long) (bArr[i4 + i5] & 255));
                }
                for (int i6 = 0; i6 < 5; i6++) {
                    cArr[i6] = (char) ((int) (j % 900));
                    j /= 900;
                }
                for (i5 = 4; i5 >= 0; i5--) {
                    stringBuilder.append(cArr[i5]);
                }
                i4 += 6;
            }
        } else {
            i4 = i;
        }
        while (i4 < i + i2) {
            stringBuilder.append((char) (bArr[i4] & 255));
            i4++;
        }
    }

    private static void encodeNumeric(String str, int i, int i2, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder((i2 / 3) + 1);
        BigInteger valueOf = BigInteger.valueOf(900);
        BigInteger valueOf2 = BigInteger.valueOf(0);
        int i3 = 0;
        while (i3 < i2) {
            stringBuilder2.setLength(0);
            int min = Math.min(44, i2 - i3);
            StringBuilder stringBuilder3 = new StringBuilder("1");
            int i4 = i + i3;
            stringBuilder3.append(str.substring(i4, i4 + min));
            BigInteger bigInteger = new BigInteger(stringBuilder3.toString());
            do {
                stringBuilder2.append((char) bigInteger.mod(valueOf).intValue());
                bigInteger = bigInteger.divide(valueOf);
            } while (!bigInteger.equals(valueOf2));
            for (int length = stringBuilder2.length() - 1; length >= 0; length--) {
                stringBuilder.append(stringBuilder2.charAt(length));
            }
            i3 += min;
        }
    }

    private static boolean isMixed(char c) {
        return MIXED[c] != (byte) -1;
    }

    private static boolean isPunctuation(char c) {
        return PUNCTUATION[c] != (byte) -1;
    }

    private static int determineConsecutiveDigitCount(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        if (i < length) {
            char charAt = charSequence.charAt(i);
            while (isDigit(charAt) && i < length) {
                i2++;
                i++;
                if (i < length) {
                    charAt = charSequence.charAt(i);
                }
            }
        }
        return i2;
    }

    private static int determineConsecutiveTextCount(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = i;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            int i3 = 0;
            while (i3 < 13 && isDigit(charAt) && i2 < length) {
                i3++;
                i2++;
                if (i2 < length) {
                    charAt = charSequence.charAt(i2);
                }
            }
            if (i3 < 13) {
                if (i3 <= 0) {
                    if (!isText(charSequence.charAt(i2))) {
                        break;
                    }
                    i2++;
                }
            } else {
                return (i2 - i) - i3;
            }
        }
        return i2 - i;
    }

    private static int determineConsecutiveBinaryCount(String str, int i, Charset charset) throws WriterException {
        CharsetEncoder newEncoder = charset.newEncoder();
        int length = str.length();
        int i2 = i;
        while (i2 < length) {
            char charAt = str.charAt(i2);
            int i3 = 0;
            while (i3 < 13 && isDigit(charAt)) {
                i3++;
                int i4 = i2 + i3;
                if (i4 >= length) {
                    break;
                }
                charAt = str.charAt(i4);
            }
            if (i3 >= 13) {
                return i2 - i;
            }
            charAt = str.charAt(i2);
            if (newEncoder.canEncode(charAt)) {
                i2++;
            } else {
                StringBuilder stringBuilder = new StringBuilder("Non-encodable character detected: ");
                stringBuilder.append(charAt);
                stringBuilder.append(" (Unicode: ");
                stringBuilder.append(charAt);
                stringBuilder.append(')');
                throw new WriterException(stringBuilder.toString());
            }
        }
        return i2 - i;
    }

    private static void encodingECI(int i, StringBuilder stringBuilder) throws WriterException {
        if (i >= 0 && i < LATCH_TO_TEXT) {
            stringBuilder.append(927);
            stringBuilder.append((char) i);
        } else if (i < 810900) {
            stringBuilder.append(926);
            stringBuilder.append((char) ((i / LATCH_TO_TEXT) - 1));
            stringBuilder.append((char) (i % LATCH_TO_TEXT));
        } else if (i < 811800) {
            stringBuilder.append(925);
            stringBuilder.append((char) (810900 - i));
        } else {
            throw new WriterException("ECI number not in valid range from 0..811799, but was ".concat(String.valueOf(i)));
        }
    }
}
