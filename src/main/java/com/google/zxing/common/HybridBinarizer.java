package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import java.lang.reflect.Array;

public final class HybridBinarizer extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    private static int cap(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    public BitMatrix getBlackMatrix() throws NotFoundException {
        BitMatrix bitMatrix = this.matrix;
        if (bitMatrix != null) {
            return bitMatrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width < 40 || height < 40) {
            this.matrix = super.getBlackMatrix();
        } else {
            byte[] matrix = luminanceSource.getMatrix();
            int i = width >> 3;
            if ((width & 7) != 0) {
                i++;
            }
            int i2 = i;
            i = height >> 3;
            if ((height & 7) != 0) {
                i++;
            }
            int i3 = i;
            int[][] calculateBlackPoints = calculateBlackPoints(matrix, i2, i3, width, height);
            bitMatrix = new BitMatrix(width, height);
            calculateThresholdForBlock(matrix, i2, i3, width, height, calculateBlackPoints, bitMatrix);
            this.matrix = bitMatrix;
        }
        return this.matrix;
    }

    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    private static void calculateThresholdForBlock(byte[] bArr, int i, int i2, int i3, int i4, int[][] iArr, BitMatrix bitMatrix) {
        int i5 = i;
        int i6 = i2;
        int i7 = i4 - 8;
        int i8 = i3 - 8;
        for (int i9 = 0; i9 < i6; i9++) {
            int i10 = i9 << 3;
            int i11 = i10 > i7 ? i7 : i10;
            int cap = cap(i9, 2, i6 - 3);
            for (int i12 = 0; i12 < i5; i12++) {
                i10 = i12 << 3;
                int i13 = i10 > i8 ? i8 : i10;
                i10 = cap(i12, 2, i5 - 3);
                int i14 = 0;
                for (int i15 = -2; i15 <= 2; i15++) {
                    int[] iArr2 = iArr[cap + i15];
                    i14 += (((iArr2[i10 - 2] + iArr2[i10 - 1]) + iArr2[i10]) + iArr2[i10 + 1]) + iArr2[i10 + 2];
                }
                thresholdBlock(bArr, i13, i11, i14 / 25, i3, bitMatrix);
            }
        }
    }

    private static void thresholdBlock(byte[] bArr, int i, int i2, int i3, int i4, BitMatrix bitMatrix) {
        int i5 = (i2 * i4) + i;
        int i6 = 0;
        while (i6 < 8) {
            for (int i7 = 0; i7 < 8; i7++) {
                if ((bArr[i5 + i7] & 255) <= i3) {
                    bitMatrix.set(i + i7, i2 + i6);
                }
            }
            i6++;
            i5 += i4;
        }
    }

    private static int[][] calculateBlackPoints(byte[] bArr, int i, int i2, int i3, int i4) {
        int i5 = i;
        int i6 = i2;
        int i7 = 8;
        int i8 = i4 - 8;
        int i9 = i3 - 8;
        int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{i6, i5});
        for (int i10 = 0; i10 < i6; i10++) {
            int i11 = i10 << 3;
            if (i11 > i8) {
                i11 = i8;
            }
            int i12 = 0;
            while (i12 < i5) {
                int i13;
                int i14 = i12 << 3;
                if (i14 > i9) {
                    i14 = i9;
                }
                int i15 = (i11 * i3) + i14;
                int i16 = 0;
                int i17 = 0;
                int i18 = 0;
                int i19 = 255;
                while (i16 < i7) {
                    int i20 = i17;
                    i17 = 0;
                    while (i17 < i7) {
                        i7 = bArr[i15 + i17] & 255;
                        i20 += i7;
                        if (i7 < i19) {
                            i19 = i7;
                        }
                        if (i7 > i18) {
                            i18 = i7;
                        }
                        i17++;
                        i7 = 8;
                    }
                    if (i18 - i19 > 24) {
                        i16++;
                        i15 += i3;
                        i7 = 8;
                        while (i16 < 8) {
                            for (i13 = 0; i13 < 8; i13++) {
                                i20 += bArr[i15 + i13] & 255;
                            }
                            i16++;
                            i15 += i3;
                        }
                    } else {
                        i7 = 8;
                    }
                    i17 = i20;
                    i16++;
                    i15 += i3;
                }
                i14 = i17 >> 6;
                if (i18 - i19 <= 24) {
                    i14 = i19 / 2;
                    if (i10 > 0 && i12 > 0) {
                        i13 = i10 - 1;
                        i18 = i12 - 1;
                        i13 = ((iArr[i13][i12] + (iArr[i10][i18] * 2)) + iArr[i13][i18]) / 4;
                        if (i19 < i13) {
                            i14 = i13;
                        }
                    }
                }
                iArr[i10][i12] = i14;
                i12++;
            }
        }
        return iArr;
    }
}
