package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

final class DecodedBitStreamParser {
    private static final int AL = 28;
    private static final int AS = 27;
    private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
    private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
    private static final int BYTE_COMPACTION_MODE_LATCH = 901;
    private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final BigInteger[] EXP900;
    private static final int LL = 27;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_ADDRESSEE = 4;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_CHECKSUM = 6;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_FILE_NAME = 0;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_FILE_SIZE = 5;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_SEGMENT_COUNT = 1;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_SENDER = 3;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_TIME_STAMP = 2;
    private static final int MACRO_PDF417_TERMINATOR = 922;
    private static final int MAX_NUMERIC_CODEWORDS = 15;
    private static final char[] MIXED_CHARS = "0123456789&\r\t,:#-.$/+%*=^".toCharArray();
    private static final int ML = 28;
    private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
    private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
    private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
    private static final int PAL = 29;
    private static final int PL = 25;
    private static final int PS = 29;
    private static final char[] PUNCT_CHARS = ";<>@[\\]_`~!\r\t,:\n-.$/\"|*()?{}'".toCharArray();
    private static final int TEXT_COMPACTION_MODE_LATCH = 900;

    private enum Mode {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT
    }

    static {
        BigInteger[] bigIntegerArr = new BigInteger[16];
        EXP900 = bigIntegerArr;
        bigIntegerArr[0] = BigInteger.ONE;
        BigInteger valueOf = BigInteger.valueOf(900);
        EXP900[1] = valueOf;
        int i = 2;
        while (true) {
            BigInteger[] bigIntegerArr2 = EXP900;
            if (i < bigIntegerArr2.length) {
                bigIntegerArr2[i] = bigIntegerArr2[i - 1].multiply(valueOf);
                i++;
            } else {
                return;
            }
        }
    }

    private DecodedBitStreamParser() {
    }

    static DecoderResult decode(int[] iArr, String str) throws FormatException {
        StringBuilder stringBuilder = new StringBuilder(iArr.length << 1);
        Charset charset = StandardCharsets.ISO_8859_1;
        int i = iArr[1];
        PDF417ResultMetadata pDF417ResultMetadata = new PDF417ResultMetadata();
        int i2 = 2;
        while (i2 < iArr[0]) {
            if (i != MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                switch (i) {
                    case TEXT_COMPACTION_MODE_LATCH /*900*/:
                        i = textCompaction(iArr, i2, stringBuilder);
                        break;
                    case BYTE_COMPACTION_MODE_LATCH /*901*/:
                        i = byteCompaction(i, iArr, charset, i2, stringBuilder);
                        break;
                    case NUMERIC_COMPACTION_MODE_LATCH /*902*/:
                        i = numericCompaction(iArr, i2, stringBuilder);
                        break;
                    default:
                        switch (i) {
                            case MACRO_PDF417_TERMINATOR /*922*/:
                            case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*923*/:
                                throw FormatException.getFormatInstance();
                            case BYTE_COMPACTION_MODE_LATCH_6 /*924*/:
                                break;
                            case ECI_USER_DEFINED /*925*/:
                                i = i2 + 1;
                                break;
                            case ECI_GENERAL_PURPOSE /*926*/:
                                i = i2 + 2;
                                break;
                            case ECI_CHARSET /*927*/:
                                i = i2 + 1;
                                charset = Charset.forName(CharacterSetECI.getCharacterSetECIByValue(iArr[i2]).name());
                                break;
                            case 928:
                                i = decodeMacroBlock(iArr, i2, pDF417ResultMetadata);
                                break;
                            default:
                                i = textCompaction(iArr, i2 - 1, stringBuilder);
                                break;
                        }
                        i = byteCompaction(i, iArr, charset, i2, stringBuilder);
                        break;
                }
            }
            i = i2 + 1;
            stringBuilder.append((char) iArr[i2]);
            if (i < iArr.length) {
                i2 = i + 1;
                i = iArr[i];
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (stringBuilder.length() != 0) {
            DecoderResult decoderResult = new DecoderResult(null, stringBuilder.toString(), null, str);
            decoderResult.setOther(pDF417ResultMetadata);
            return decoderResult;
        }
        throw FormatException.getFormatInstance();
    }

    static int decodeMacroBlock(int[] iArr, int i, PDF417ResultMetadata pDF417ResultMetadata) throws FormatException {
        if (i + 2 <= iArr[0]) {
            int[] iArr2 = new int[2];
            int i2 = i;
            i = 0;
            while (i < 2) {
                iArr2[i] = iArr[i2];
                i++;
                i2++;
            }
            pDF417ResultMetadata.setSegmentIndex(Integer.parseInt(decodeBase900toBase10(iArr2, 2)));
            StringBuilder stringBuilder = new StringBuilder();
            int textCompaction = textCompaction(iArr, i2, stringBuilder);
            pDF417ResultMetadata.setFileId(stringBuilder.toString());
            i = iArr[textCompaction] == BEGIN_MACRO_PDF417_OPTIONAL_FIELD ? textCompaction + 1 : -1;
            while (textCompaction < iArr[0]) {
                int i3 = iArr[textCompaction];
                if (i3 == MACRO_PDF417_TERMINATOR) {
                    textCompaction++;
                    pDF417ResultMetadata.setLastSegment(true);
                } else if (i3 == BEGIN_MACRO_PDF417_OPTIONAL_FIELD) {
                    textCompaction++;
                    StringBuilder stringBuilder2;
                    switch (iArr[textCompaction]) {
                        case 0:
                            stringBuilder2 = new StringBuilder();
                            textCompaction = textCompaction(iArr, textCompaction + 1, stringBuilder2);
                            pDF417ResultMetadata.setFileName(stringBuilder2.toString());
                            break;
                        case 1:
                            stringBuilder2 = new StringBuilder();
                            textCompaction = numericCompaction(iArr, textCompaction + 1, stringBuilder2);
                            pDF417ResultMetadata.setSegmentCount(Integer.parseInt(stringBuilder2.toString()));
                            break;
                        case 2:
                            stringBuilder2 = new StringBuilder();
                            textCompaction = numericCompaction(iArr, textCompaction + 1, stringBuilder2);
                            pDF417ResultMetadata.setTimestamp(Long.parseLong(stringBuilder2.toString()));
                            break;
                        case 3:
                            stringBuilder2 = new StringBuilder();
                            textCompaction = textCompaction(iArr, textCompaction + 1, stringBuilder2);
                            pDF417ResultMetadata.setSender(stringBuilder2.toString());
                            break;
                        case 4:
                            stringBuilder2 = new StringBuilder();
                            textCompaction = textCompaction(iArr, textCompaction + 1, stringBuilder2);
                            pDF417ResultMetadata.setAddressee(stringBuilder2.toString());
                            break;
                        case 5:
                            stringBuilder2 = new StringBuilder();
                            textCompaction = numericCompaction(iArr, textCompaction + 1, stringBuilder2);
                            pDF417ResultMetadata.setFileSize(Long.parseLong(stringBuilder2.toString()));
                            break;
                        case 6:
                            stringBuilder2 = new StringBuilder();
                            textCompaction = numericCompaction(iArr, textCompaction + 1, stringBuilder2);
                            pDF417ResultMetadata.setChecksum(Integer.parseInt(stringBuilder2.toString()));
                            break;
                        default:
                            throw FormatException.getFormatInstance();
                    }
                } else {
                    throw FormatException.getFormatInstance();
                }
            }
            if (i != -1) {
                int i4 = textCompaction - i;
                if (pDF417ResultMetadata.isLastSegment()) {
                    i4--;
                }
                pDF417ResultMetadata.setOptionalData(Arrays.copyOfRange(iArr, i, i4 + i));
            }
            return textCompaction;
        }
        throw FormatException.getFormatInstance();
    }

    private static int textCompaction(int[] iArr, int i, StringBuilder stringBuilder) {
        int[] iArr2 = new int[((iArr[0] - i) << 1)];
        int[] iArr3 = new int[((iArr[0] - i) << 1)];
        Object obj = null;
        int i2 = 0;
        while (i < iArr[0] && obj == null) {
            int i3 = i + 1;
            i = iArr[i];
            if (i < TEXT_COMPACTION_MODE_LATCH) {
                iArr2[i2] = i / 30;
                iArr2[i2 + 1] = i % 30;
                i2 += 2;
            } else if (i != MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                if (i != 928) {
                    switch (i) {
                        case TEXT_COMPACTION_MODE_LATCH /*900*/:
                            i = i2 + 1;
                            iArr2[i2] = TEXT_COMPACTION_MODE_LATCH;
                            i2 = i;
                            break;
                        case BYTE_COMPACTION_MODE_LATCH /*901*/:
                        case NUMERIC_COMPACTION_MODE_LATCH /*902*/:
                            break;
                        default:
                            switch (i) {
                                case MACRO_PDF417_TERMINATOR /*922*/:
                                case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*923*/:
                                case BYTE_COMPACTION_MODE_LATCH_6 /*924*/:
                                    break;
                            }
                            break;
                    }
                }
                i = i3 - 1;
                obj = 1;
            } else {
                iArr2[i2] = MODE_SHIFT_TO_BYTE_COMPACTION_MODE;
                i = i3 + 1;
                iArr3[i2] = iArr[i3];
                i2++;
            }
            i = i3;
        }
        decodeTextCompaction(iArr2, iArr3, i2, stringBuilder);
        return i;
    }

    /* JADX WARNING: Missing block: B:10:0x0037, code:
            r1 = r3;
     */
    /* JADX WARNING: Missing block: B:15:0x0047, code:
            r4 = r1;
            r1 = r3;
     */
    /* JADX WARNING: Missing block: B:52:0x00c7, code:
            r4 = (char) r4;
     */
    /* JADX WARNING: Missing block: B:57:0x00d3, code:
            r4 = 0;
            r11 = r3;
            r3 = r1;
            r1 = r11;
     */
    /* JADX WARNING: Missing block: B:60:0x00de, code:
            r4 = ' ';
     */
    /* JADX WARNING: Missing block: B:64:0x00eb, code:
            if (r4 == 0) goto L_0x00f0;
     */
    /* JADX WARNING: Missing block: B:65:0x00ed, code:
            r15.append(r4);
     */
    /* JADX WARNING: Missing block: B:66:0x00f0, code:
            r0 = r0 + 1;
     */
    private static void decodeTextCompaction(int[] r12, int[] r13, int r14, java.lang.StringBuilder r15) {
        /*
        r0 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        r2 = 0;
        r3 = r1;
        r1 = r0;
        r0 = 0;
    L_0x0008:
        if (r0 >= r14) goto L_0x00f4;
    L_0x000a:
        r4 = r12[r0];
        r5 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.AnonymousClass1.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode;
        r6 = r1.ordinal();
        r5 = r5[r6];
        r6 = 32;
        r7 = 29;
        r8 = 26;
        r9 = 913; // 0x391 float:1.28E-42 double:4.51E-321;
        r10 = 900; // 0x384 float:1.261E-42 double:4.447E-321;
        switch(r5) {
            case 1: goto L_0x00c3;
            case 2: goto L_0x00a3;
            case 3: goto L_0x0077;
            case 4: goto L_0x0057;
            case 5: goto L_0x0042;
            case 6: goto L_0x0023;
            default: goto L_0x0021;
        };
    L_0x0021:
        goto L_0x00ea;
    L_0x0023:
        if (r4 >= r7) goto L_0x002a;
    L_0x0025:
        r1 = PUNCT_CHARS;
        r1 = r1[r4];
        goto L_0x0047;
    L_0x002a:
        if (r4 == r7) goto L_0x003e;
    L_0x002c:
        if (r4 == r10) goto L_0x003a;
    L_0x002e:
        if (r4 == r9) goto L_0x0031;
    L_0x0030:
        goto L_0x0037;
    L_0x0031:
        r1 = r13[r0];
        r1 = (char) r1;
        r15.append(r1);
    L_0x0037:
        r1 = r3;
        goto L_0x00ea;
    L_0x003a:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        goto L_0x00ea;
    L_0x003e:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        goto L_0x00ea;
    L_0x0042:
        if (r4 >= r8) goto L_0x004b;
    L_0x0044:
        r4 = r4 + 65;
        r1 = (char) r4;
    L_0x0047:
        r4 = r1;
        r1 = r3;
        goto L_0x00eb;
    L_0x004b:
        if (r4 == r8) goto L_0x0054;
    L_0x004d:
        if (r4 == r10) goto L_0x0050;
    L_0x004f:
        goto L_0x0037;
    L_0x0050:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        goto L_0x00ea;
    L_0x0054:
        r1 = r3;
        goto L_0x00de;
    L_0x0057:
        if (r4 >= r7) goto L_0x005f;
    L_0x0059:
        r5 = PUNCT_CHARS;
        r4 = r5[r4];
        goto L_0x00eb;
    L_0x005f:
        if (r4 == r7) goto L_0x0073;
    L_0x0061:
        if (r4 == r10) goto L_0x006f;
    L_0x0063:
        if (r4 == r9) goto L_0x0067;
    L_0x0065:
        goto L_0x00ea;
    L_0x0067:
        r4 = r13[r0];
        r4 = (char) r4;
        r15.append(r4);
        goto L_0x00ea;
    L_0x006f:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        goto L_0x00ea;
    L_0x0073:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        goto L_0x00ea;
    L_0x0077:
        r5 = 25;
        if (r4 >= r5) goto L_0x0081;
    L_0x007b:
        r5 = MIXED_CHARS;
        r4 = r5[r4];
        goto L_0x00eb;
    L_0x0081:
        if (r4 == r10) goto L_0x00a0;
    L_0x0083:
        if (r4 == r9) goto L_0x0099;
    L_0x0085:
        switch(r4) {
            case 25: goto L_0x0095;
            case 26: goto L_0x00de;
            case 27: goto L_0x0091;
            case 28: goto L_0x008d;
            case 29: goto L_0x008a;
            default: goto L_0x0088;
        };
    L_0x0088:
        goto L_0x00ea;
    L_0x008a:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT_SHIFT;
        goto L_0x00d3;
    L_0x008d:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        goto L_0x00ea;
    L_0x0091:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.LOWER;
        goto L_0x00ea;
    L_0x0095:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT;
        goto L_0x00ea;
    L_0x0099:
        r4 = r13[r0];
        r4 = (char) r4;
        r15.append(r4);
        goto L_0x00ea;
    L_0x00a0:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        goto L_0x00ea;
    L_0x00a3:
        if (r4 >= r8) goto L_0x00a8;
    L_0x00a5:
        r4 = r4 + 97;
        goto L_0x00c7;
    L_0x00a8:
        if (r4 == r10) goto L_0x00c0;
    L_0x00aa:
        if (r4 == r9) goto L_0x00b9;
    L_0x00ac:
        switch(r4) {
            case 26: goto L_0x00de;
            case 27: goto L_0x00b6;
            case 28: goto L_0x00b3;
            case 29: goto L_0x00b0;
            default: goto L_0x00af;
        };
    L_0x00af:
        goto L_0x00ea;
    L_0x00b0:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT_SHIFT;
        goto L_0x00d3;
    L_0x00b3:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.MIXED;
        goto L_0x00ea;
    L_0x00b6:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA_SHIFT;
        goto L_0x00d3;
    L_0x00b9:
        r4 = r13[r0];
        r4 = (char) r4;
        r15.append(r4);
        goto L_0x00ea;
    L_0x00c0:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
        goto L_0x00ea;
    L_0x00c3:
        if (r4 >= r8) goto L_0x00c9;
    L_0x00c5:
        r4 = r4 + 65;
    L_0x00c7:
        r4 = (char) r4;
        goto L_0x00eb;
    L_0x00c9:
        if (r4 == r10) goto L_0x00e8;
    L_0x00cb:
        if (r4 == r9) goto L_0x00e1;
    L_0x00cd:
        switch(r4) {
            case 26: goto L_0x00de;
            case 27: goto L_0x00db;
            case 28: goto L_0x00d8;
            case 29: goto L_0x00d1;
            default: goto L_0x00d0;
        };
    L_0x00d0:
        goto L_0x00ea;
    L_0x00d1:
        r3 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.PUNCT_SHIFT;
    L_0x00d3:
        r4 = 0;
        r11 = r3;
        r3 = r1;
        r1 = r11;
        goto L_0x00eb;
    L_0x00d8:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.MIXED;
        goto L_0x00ea;
    L_0x00db:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.LOWER;
        goto L_0x00ea;
    L_0x00de:
        r4 = 32;
        goto L_0x00eb;
    L_0x00e1:
        r4 = r13[r0];
        r4 = (char) r4;
        r15.append(r4);
        goto L_0x00ea;
    L_0x00e8:
        r1 = com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode.ALPHA;
    L_0x00ea:
        r4 = 0;
    L_0x00eb:
        if (r4 == 0) goto L_0x00f0;
    L_0x00ed:
        r15.append(r4);
    L_0x00f0:
        r0 = r0 + 1;
        goto L_0x0008;
    L_0x00f4:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.DecodedBitStreamParser.decodeTextCompaction(int[], int[], int, java.lang.StringBuilder):void");
    }

    private static int byteCompaction(int i, int[] iArr, Charset charset, int i2, StringBuilder stringBuilder) {
        int i3 = i;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        long j = 900;
        int i4 = 6;
        int i5 = 0;
        if (i3 == BYTE_COMPACTION_MODE_LATCH) {
            int i6;
            int i7;
            int[] iArr2 = new int[6];
            int i8 = i2 + 1;
            int i9 = iArr[i2];
            Object obj = null;
            while (true) {
                i6 = 0;
                long j2 = 0;
                while (i8 < iArr[0] && obj == null) {
                    int i10 = i6 + 1;
                    iArr2[i6] = i9;
                    j2 = (j2 * j) + ((long) i9);
                    i7 = i8 + 1;
                    i9 = iArr[i8];
                    if (i9 != 928) {
                        switch (i9) {
                            case TEXT_COMPACTION_MODE_LATCH /*900*/:
                            case BYTE_COMPACTION_MODE_LATCH /*901*/:
                            case NUMERIC_COMPACTION_MODE_LATCH /*902*/:
                                break;
                            default:
                                switch (i9) {
                                    case MACRO_PDF417_TERMINATOR /*922*/:
                                    case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*923*/:
                                    case BYTE_COMPACTION_MODE_LATCH_6 /*924*/:
                                        break;
                                    default:
                                        if (i10 % 5 != 0 || i10 <= 0) {
                                            obj = obj;
                                            i8 = i7;
                                            i6 = i10;
                                            j = 900;
                                            i4 = 6;
                                            break;
                                        }
                                        Object obj2;
                                        i8 = 0;
                                        while (i8 < i4) {
                                            obj2 = obj;
                                            byteArrayOutputStream.write((byte) ((int) (j2 >> ((5 - i8) * 8))));
                                            i8++;
                                            i4 = 6;
                                            obj = obj2;
                                        }
                                        obj2 = obj;
                                        i8 = i7;
                                        j = 900;
                                        break;
                                }
                        }
                    }
                    i8 = i7 - 1;
                    i6 = i10;
                    j = 900;
                    i4 = 6;
                    obj = 1;
                }
            }
            if (i8 == iArr[0] && i9 < TEXT_COMPACTION_MODE_LATCH) {
                i7 = i6 + 1;
                iArr2[i6] = i9;
                i6 = i7;
            }
            while (i5 < i6) {
                byteArrayOutputStream.write((byte) iArr2[i5]);
                i5++;
            }
            i3 = i8;
        } else if (i3 != BYTE_COMPACTION_MODE_LATCH_6) {
            i3 = i2;
        } else {
            i3 = i2;
            Object obj3 = null;
            while (true) {
                int i11 = 0;
                long j3 = 0;
                while (i3 < iArr[0] && r2 == null) {
                    int i12 = i3 + 1;
                    i3 = iArr[i3];
                    if (i3 < TEXT_COMPACTION_MODE_LATCH) {
                        i11++;
                        j3 = (j3 * 900) + ((long) i3);
                    } else {
                        if (i3 != 928) {
                            switch (i3) {
                                case TEXT_COMPACTION_MODE_LATCH /*900*/:
                                case BYTE_COMPACTION_MODE_LATCH /*901*/:
                                case NUMERIC_COMPACTION_MODE_LATCH /*902*/:
                                    break;
                                default:
                                    switch (i3) {
                                        case MACRO_PDF417_TERMINATOR /*922*/:
                                        case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*923*/:
                                        case BYTE_COMPACTION_MODE_LATCH_6 /*924*/:
                                            break;
                                    }
                                    break;
                            }
                        }
                        i3 = i12 - 1;
                        obj3 = 1;
                        if (i11 % 5 != 0 && i11 > 0) {
                            for (int i13 = 0; i13 < 6; i13++) {
                                byteArrayOutputStream.write((byte) ((int) (j3 >> ((5 - i13) * 8))));
                            }
                        }
                    }
                    i3 = i12;
                    if (i11 % 5 != 0) {
                    }
                }
            }
        }
        stringBuilder.append(new String(byteArrayOutputStream.toByteArray(), charset));
        return i3;
    }

    private static int numericCompaction(int[] iArr, int i, StringBuilder stringBuilder) throws FormatException {
        int[] iArr2 = new int[15];
        Object obj = null;
        int i2 = 0;
        while (i < iArr[0] && r2 == null) {
            int i3 = i + 1;
            i = iArr[i];
            if (i3 == iArr[0]) {
                obj = 1;
            }
            if (i < TEXT_COMPACTION_MODE_LATCH) {
                iArr2[i2] = i;
                i2++;
            } else {
                if (!(i == TEXT_COMPACTION_MODE_LATCH || i == BYTE_COMPACTION_MODE_LATCH || i == 928)) {
                    switch (i) {
                        case MACRO_PDF417_TERMINATOR /*922*/:
                        case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /*923*/:
                        case BYTE_COMPACTION_MODE_LATCH_6 /*924*/:
                            break;
                    }
                }
                i3--;
                obj = 1;
            }
            if ((i2 % 15 == 0 || i == NUMERIC_COMPACTION_MODE_LATCH || obj != null) && i2 > 0) {
                stringBuilder.append(decodeBase900toBase10(iArr2, i2));
                i2 = 0;
            }
            i = i3;
        }
        return i;
    }

    private static String decodeBase900toBase10(int[] iArr, int i) throws FormatException {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i2 = 0; i2 < i; i2++) {
            bigInteger = bigInteger.add(EXP900[(i - i2) - 1].multiply(BigInteger.valueOf((long) iArr[i2])));
        }
        String bigInteger2 = bigInteger.toString();
        if (bigInteger2.charAt(0) == '1') {
            return bigInteger2.substring(1);
        }
        throw FormatException.getFormatInstance();
    }
}
