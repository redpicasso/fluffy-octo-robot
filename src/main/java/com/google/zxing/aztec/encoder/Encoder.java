package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;

public final class Encoder {
    public static final int DEFAULT_AZTEC_LAYERS = 0;
    public static final int DEFAULT_EC_PERCENT = 33;
    private static final int MAX_NB_BITS = 32;
    private static final int MAX_NB_BITS_COMPACT = 4;
    private static final int[] WORD_SIZE = new int[]{4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    private static int totalBitsInLayer(int i, boolean z) {
        return ((z ? 88 : 112) + (i << 4)) * i;
    }

    private Encoder() {
    }

    public static AztecCode encode(byte[] bArr) {
        return encode(bArr, 33, 0);
    }

    public static AztecCode encode(byte[] bArr, int i, int i2) {
        boolean z;
        int abs;
        int i3;
        int i4;
        int size;
        int i5;
        int totalBitsInLayer;
        int i6;
        int i7;
        BitArray encode = new HighLevelEncoder(bArr).encode();
        int i8 = 11;
        int size2 = ((encode.getSize() * i) / 100) + 11;
        int size3 = encode.getSize() + size2;
        int i9 = 32;
        int i10 = 0;
        int i11 = 1;
        if (i2 != 0) {
            z = i2 < 0;
            abs = Math.abs(i2);
            if (z) {
                i9 = 4;
            }
            if (abs <= i9) {
                i9 = totalBitsInLayer(abs, z);
                i3 = WORD_SIZE[abs];
                i4 = i9 - (i9 % i3);
                encode = stuffBits(encode, i3);
                size = encode.getSize() + size2;
                String str = "Data to large for user specified layer";
                if (size > i4) {
                    throw new IllegalArgumentException(str);
                } else if (z && encode.getSize() > (i3 << 6)) {
                    throw new IllegalArgumentException(str);
                }
            }
            throw new IllegalArgumentException(String.format("Illegal value %s for layers", new Object[]{Integer.valueOf(i2)}));
        }
        BitArray bitArray = null;
        abs = 0;
        i4 = 0;
        while (abs <= 32) {
            boolean z2 = abs <= 3;
            i5 = z2 ? abs + 1 : abs;
            totalBitsInLayer = totalBitsInLayer(i5, z2);
            if (size3 <= totalBitsInLayer) {
                BitArray stuffBits;
                if (bitArray == null || i4 != WORD_SIZE[i5]) {
                    i3 = WORD_SIZE[i5];
                    stuffBits = stuffBits(encode, i3);
                } else {
                    int i12 = i4;
                    stuffBits = bitArray;
                    i3 = i12;
                }
                i6 = totalBitsInLayer - (totalBitsInLayer % i3);
                if ((!z2 || stuffBits.getSize() <= (i3 << 6)) && stuffBits.getSize() + size2 <= i6) {
                    encode = stuffBits;
                    z = z2;
                    abs = i5;
                    i9 = totalBitsInLayer;
                } else {
                    BitArray bitArray2 = stuffBits;
                    i4 = i3;
                    bitArray = bitArray2;
                }
            }
            abs++;
            i10 = 0;
            i11 = 1;
        }
        throw new IllegalArgumentException("Data too large for an Aztec code");
        BitArray generateCheckWords = generateCheckWords(encode, i9, i3);
        int size4 = encode.getSize() / i3;
        BitArray generateModeMessage = generateModeMessage(z, abs, size4);
        if (!z) {
            i8 = 14;
        }
        i8 += abs << 2;
        int[] iArr = new int[i8];
        i4 = 2;
        if (z) {
            for (size = 0; size < iArr.length; size++) {
                iArr[size] = size;
            }
            size = i8;
        } else {
            i5 = i8 / 2;
            size = (i8 + 1) + (((i5 - 1) / 15) * 2);
            totalBitsInLayer = size / 2;
            for (i6 = 0; i6 < i5; i6++) {
                i7 = (i6 / 15) + i6;
                iArr[(i5 - i6) - i11] = (totalBitsInLayer - i7) - 1;
                iArr[i5 + i6] = (i7 + totalBitsInLayer) + i11;
            }
        }
        BitMatrix bitMatrix = new BitMatrix(size);
        totalBitsInLayer = 0;
        i6 = 0;
        while (totalBitsInLayer < abs) {
            i7 = ((abs - totalBitsInLayer) << i4) + (z ? 9 : 12);
            int i13 = 0;
            while (i13 < i7) {
                int i14 = i13 << 1;
                for (i4 = 
/*
Method generation error in method: com.google.zxing.aztec.encoder.Encoder.encode(byte[], int, int):com.google.zxing.aztec.encoder.AztecCode, dex: classes2.dex
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r9_12 'i4' int) = (r9_11 'i4' int), (r9_24 'i4' int) binds: {(r9_11 'i4' int)=B:63:0x0121, (r9_24 'i4' int)=B:80:0x0196} in method: com.google.zxing.aztec.encoder.Encoder.encode(byte[], int, int):com.google.zxing.aztec.encoder.AztecCode, dex: classes2.dex
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:228)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:183)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:218)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:218)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:173)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:323)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:260)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:222)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:77)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:10)
	at jadx.core.ProcessClass.process(ProcessClass.java:38)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
Caused by: jadx.core.utils.exceptions.CodegenException: PHI can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:539)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:511)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:222)
	... 25 more

*/

    private static void drawBullsEye(BitMatrix bitMatrix, int i, int i2) {
        int i3;
        int i4;
        for (i3 = 0; i3 < i2; i3 += 2) {
            i4 = i - i3;
            int i5 = i4;
            while (true) {
                int i6 = i + i3;
                if (i5 > i6) {
                    break;
                }
                bitMatrix.set(i5, i4);
                bitMatrix.set(i5, i6);
                bitMatrix.set(i4, i5);
                bitMatrix.set(i6, i5);
                i5++;
            }
        }
        i3 = i - i2;
        bitMatrix.set(i3, i3);
        i4 = i3 + 1;
        bitMatrix.set(i4, i3);
        bitMatrix.set(i3, i4);
        i += i2;
        bitMatrix.set(i, i3);
        bitMatrix.set(i, i4);
        bitMatrix.set(i, i - 1);
    }

    static BitArray generateModeMessage(boolean z, int i, int i2) {
        BitArray bitArray = new BitArray();
        if (z) {
            bitArray.appendBits(i - 1, 2);
            bitArray.appendBits(i2 - 1, 6);
            return generateCheckWords(bitArray, 28, 4);
        }
        bitArray.appendBits(i - 1, 5);
        bitArray.appendBits(i2 - 1, 11);
        return generateCheckWords(bitArray, 40, 4);
    }

    private static void drawModeMessage(BitMatrix bitMatrix, boolean z, int i, BitArray bitArray) {
        i /= 2;
        int i2 = 0;
        int i3;
        if (z) {
            while (i2 < 7) {
                i3 = (i - 3) + i2;
                if (bitArray.get(i2)) {
                    bitMatrix.set(i3, i - 5);
                }
                if (bitArray.get(i2 + 7)) {
                    bitMatrix.set(i + 5, i3);
                }
                if (bitArray.get(20 - i2)) {
                    bitMatrix.set(i3, i + 5);
                }
                if (bitArray.get(27 - i2)) {
                    bitMatrix.set(i - 5, i3);
                }
                i2++;
            }
            return;
        }
        while (i2 < 10) {
            i3 = ((i - 5) + i2) + (i2 / 5);
            if (bitArray.get(i2)) {
                bitMatrix.set(i3, i - 7);
            }
            if (bitArray.get(i2 + 10)) {
                bitMatrix.set(i + 7, i3);
            }
            if (bitArray.get(29 - i2)) {
                bitMatrix.set(i3, i + 7);
            }
            if (bitArray.get(39 - i2)) {
                bitMatrix.set(i - 7, i3);
            }
            i2++;
        }
    }

    private static BitArray generateCheckWords(BitArray bitArray, int i, int i2) {
        int size = bitArray.getSize() / i2;
        ReedSolomonEncoder reedSolomonEncoder = new ReedSolomonEncoder(getGF(i2));
        int i3 = i / i2;
        int[] bitsToWords = bitsToWords(bitArray, i2, i3);
        reedSolomonEncoder.encode(bitsToWords, i3 - size);
        i %= i2;
        BitArray bitArray2 = new BitArray();
        int i4 = 0;
        bitArray2.appendBits(0, i);
        i = bitsToWords.length;
        while (i4 < i) {
            bitArray2.appendBits(bitsToWords[i4], i2);
            i4++;
        }
        return bitArray2;
    }

    private static int[] bitsToWords(BitArray bitArray, int i, int i2) {
        int[] iArr = new int[i2];
        int size = bitArray.getSize() / i;
        for (int i3 = 0; i3 < size; i3++) {
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                i4 |= bitArray.get((i3 * i) + i5) ? 1 << ((i - i5) - 1) : 0;
            }
            iArr[i3] = i4;
        }
        return iArr;
    }

    private static GenericGF getGF(int i) {
        if (i == 4) {
            return GenericGF.AZTEC_PARAM;
        }
        if (i == 6) {
            return GenericGF.AZTEC_DATA_6;
        }
        if (i == 8) {
            return GenericGF.AZTEC_DATA_8;
        }
        if (i == 10) {
            return GenericGF.AZTEC_DATA_10;
        }
        if (i == 12) {
            return GenericGF.AZTEC_DATA_12;
        }
        throw new IllegalArgumentException("Unsupported word size ".concat(String.valueOf(i)));
    }

    static BitArray stuffBits(BitArray bitArray, int i) {
        BitArray bitArray2 = new BitArray();
        int size = bitArray.getSize();
        int i2 = (1 << i) - 2;
        int i3 = 0;
        while (i3 < size) {
            int i4;
            int i5 = 0;
            for (i4 = 0; i4 < i; i4++) {
                int i6 = i3 + i4;
                if (i6 >= size || bitArray.get(i6)) {
                    i5 |= 1 << ((i - 1) - i4);
                }
            }
            i4 = i5 & i2;
            if (i4 == i2) {
                bitArray2.appendBits(i4, i);
            } else if (i4 == 0) {
                bitArray2.appendBits(i5 | 1, i);
            } else {
                bitArray2.appendBits(i5, i);
                i3 += i;
            }
            i3--;
            i3 += i;
        }
        return bitArray2;
    }
}
