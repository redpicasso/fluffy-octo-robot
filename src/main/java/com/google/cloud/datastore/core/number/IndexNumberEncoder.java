package com.google.cloud.datastore.core.number;

import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.facebook.imageutils.JfifUtil;
import com.google.common.primitives.UnsignedBytes;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class IndexNumberEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int DOUBLE_EXPONENT_BIAS = 1023;
    static final int DOUBLE_MIN_EXPONENT = -1022;
    static final int DOUBLE_SIGNIFICAND_BITS = 52;
    static final long DOUBLE_SIGN_BIT = Long.MIN_VALUE;
    static final int EXP1_END = 4;
    static final int EXP2_END = 20;
    static final int EXP3_END = 148;
    static final int EXP4_END = 1172;
    public static final int MAX_ENCODED_BYTES = 11;
    static final int NEGATIVE_INFINITE_EXPONENT = Integer.MIN_VALUE;
    static final int POSITIVE_INFINITE_EXPONENT = Integer.MAX_VALUE;
    static final int SIGNIFICAND_BITS = 64;

    private static int topSignificandByte(long j) {
        return ((int) (j >>> 56)) & ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE;
    }

    private IndexNumberEncoder() {
    }

    public static int encodeLong(boolean z, long j, byte[] bArr, int i) {
        int i2 = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i2 == 0) {
            return encodeZero(bArr, i);
        }
        if (i2 < 0) {
            z ^= 1;
            j = -j;
        }
        boolean z2 = z;
        int numberOfLeadingZeros = Long.numberOfLeadingZeros(j);
        int i3 = 63 - numberOfLeadingZeros;
        return encodeNumber(z2, i3, (j & (~(1 << i3))) << (numberOfLeadingZeros + 1), bArr, i);
    }

    public static int encodeDouble(boolean z, double d, byte[] bArr, int i) {
        if (d == 0.0d) {
            return encodeZero(bArr, i);
        }
        long doubleToLongBits = Double.doubleToLongBits(d);
        boolean z2 = (d < 0.0d ? 1 : 0) ^ z;
        int i2 = ((int) ((doubleToLongBits >>> 52) & 2047)) - DOUBLE_EXPONENT_BIAS;
        doubleToLongBits &= 4503599627370495L;
        int i3;
        if (i2 < DOUBLE_MIN_EXPONENT) {
            int numberOfLeadingZeros = Long.numberOfLeadingZeros(doubleToLongBits);
            doubleToLongBits = (doubleToLongBits & (~(1 << (63 - numberOfLeadingZeros)))) << (numberOfLeadingZeros + 1);
            i2 -= numberOfLeadingZeros - 12;
        } else if (i2 <= DOUBLE_EXPONENT_BIAS) {
            doubleToLongBits <<= 12;
        } else if (doubleToLongBits != 0) {
            i3 = i + 1;
            bArr[i] = (byte) 0;
            bArr[i3] = (byte) 96;
            return 2;
        } else if (z2) {
            i3 = i + 1;
            bArr[i] = (byte) 0;
            bArr[i3] = UnsignedBytes.MAX_POWER_OF_TWO;
            return 2;
        } else {
            bArr[i] = (byte) -1;
            return 1;
        }
        return encodeNumber(z2, i2, doubleToLongBits, bArr, i);
    }

    private static int encodeZero(byte[] bArr, int i) {
        bArr[i] = UnsignedBytes.MAX_POWER_OF_TWO;
        return 1;
    }

    private static int encodeNumber(boolean z, int i, long j, byte[] bArr, int i2) {
        int i3;
        int i4;
        long j2;
        int i5 = 0;
        int i6 = z ? 255 : 0;
        if (i < 0) {
            i = -i;
            i5 = 255;
        }
        if (i < 4) {
            i3 = i + 1;
            i4 = 1 << i3;
            i4 = ((i4 - 2) & ((int) (j >>> (64 - i3)))) | (i4 | JfifUtil.MARKER_SOFn);
            j2 = j << i;
            if (i5 != 0) {
                i4 ^= (-1 << i3) & 126;
            }
            i5 = i2;
        } else {
            if (i < 20) {
                i = ((i - 4) | CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY) ^ ((i5 & 127) ^ i6);
                i5 = i2 + 1;
                bArr[i2] = (byte) i;
                i4 = topSignificandByte(j);
            } else if (i < 148) {
                i -= 20;
                int i7 = i2 + 1;
                bArr[i2] = (byte) (((i >>> 4) | 240) ^ ((i5 & 127) ^ i6));
                j <<= 4;
                i = (((i << 4) & 240) | ((int) (j >>> 60))) ^ ((i5 & 240) ^ i6);
                i5 = i7 + 1;
                bArr[i7] = (byte) i;
                i4 = topSignificandByte(j);
            } else if (i < EXP4_END) {
                i -= 148;
                int i8 = i2 + 1;
                bArr[i2] = (byte) ((248 | (i >>> 8)) ^ ((i5 & 127) ^ i6));
                i = (i & 255) ^ ((i5 & 255) ^ i6);
                i5 = i8 + 1;
                bArr[i8] = (byte) i;
                i4 = topSignificandByte(j);
            } else {
                throw new IllegalStateException("unimplemented");
            }
            j2 = j << 7;
        }
        while (j2 != 0) {
            i3 = i5 + 1;
            bArr[i5] = (byte) ((i4 | 1) ^ i6);
            i4 = topSignificandByte(j2);
            j2 <<= 7;
            i5 = i3;
        }
        i = i5 + 1;
        bArr[i5] = (byte) (i6 ^ i4);
        return i - i2;
    }
}
