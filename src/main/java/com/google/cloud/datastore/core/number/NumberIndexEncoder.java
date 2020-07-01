package com.google.cloud.datastore.core.number;

import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.facebook.imageutils.JfifUtil;
import com.google.common.primitives.UnsignedBytes;
import java.util.Arrays;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class NumberIndexEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte[] ENCODED_NAN = new byte[]{(byte) 0, (byte) 96};
    private static final byte[] ENCODED_NEGATIVE_INFINITY = new byte[]{(byte) 0, UnsignedBytes.MAX_POWER_OF_TWO};
    private static final byte[] ENCODED_POSITIVE_INFINITY = new byte[]{(byte) -1};
    private static final byte[] ENCODED_ZERO = new byte[]{UnsignedBytes.MAX_POWER_OF_TWO};
    private static final int EXP1_END = 4;
    private static final int EXP2_END = 20;
    private static final int EXP3_END = 148;
    private static final int EXP4_END = 1172;
    private static final int MAX_ENCODED_BYTES = 11;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class DecodedNumberParts {
        private final int bytesRead;
        private final NumberParts parts;

        private DecodedNumberParts(int i, NumberParts numberParts) {
            this.bytesRead = i;
            this.parts = numberParts;
        }

        public int bytesRead() {
            return this.bytesRead;
        }

        public NumberParts parts() {
            return this.parts;
        }

        static DecodedNumberParts create(int i, NumberParts numberParts) {
            return new DecodedNumberParts(i, numberParts);
        }
    }

    private static long decodeTrailingSignificandByte(int i, int i2) {
        return ((long) (i & ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE)) << (i2 - 1);
    }

    private static int topSignificandByte(long j) {
        return ((int) (j >>> 56)) & ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE;
    }

    public static byte[] encodeDouble(double d) {
        return encode(NumberParts.fromDouble(d));
    }

    public static byte[] encodeLong(long j) {
        return encode(NumberParts.fromLong(j));
    }

    public static byte[] encode(NumberParts numberParts) {
        if (numberParts.isZero()) {
            return copyOf(ENCODED_ZERO);
        }
        if (numberParts.isNaN()) {
            return copyOf(ENCODED_NAN);
        }
        if (numberParts.isInfinite()) {
            byte[] copyOf;
            if (numberParts.negative()) {
                copyOf = copyOf(ENCODED_NEGATIVE_INFINITY);
            } else {
                copyOf = copyOf(ENCODED_POSITIVE_INFINITY);
            }
            return copyOf;
        }
        int i;
        int i2;
        int i3;
        long j;
        int exponent = numberParts.exponent();
        long significand = numberParts.significand();
        byte[] bArr = new byte[11];
        int i4 = 0;
        int i5 = numberParts.negative() ? 255 : 0;
        if (exponent < 0) {
            exponent = -exponent;
            i = 255;
        } else {
            i = 0;
        }
        if (exponent < 4) {
            i2 = exponent + 1;
            int i6 = 1 << i2;
            i3 = (((int) (significand >>> (64 - i2))) & (i6 - 2)) | (i6 | JfifUtil.MARKER_SOFn);
            j = significand << exponent;
            if (i != 0) {
                i3 ^= (-1 << i2) & 126;
            }
        } else if (exponent < 20) {
            bArr[0] = (byte) (((exponent - 4) | CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY) ^ ((i & 127) ^ i5));
            i3 = topSignificandByte(significand);
            j = significand << 7;
            i4 = 1;
        } else {
            if (exponent < 148) {
                exponent -= 20;
                bArr[0] = (byte) (((exponent >>> 4) | 240) ^ ((i & 127) ^ i5));
                significand <<= 4;
                bArr[1] = (byte) ((((exponent << 4) & 240) | ((int) (significand >>> 60))) ^ ((i & 240) ^ i5));
                exponent = topSignificandByte(significand);
            } else if (exponent < EXP4_END) {
                exponent -= 148;
                bArr[0] = (byte) ((248 | (exponent >>> 8)) ^ ((i & 127) ^ i5));
                bArr[1] = (byte) ((exponent & 255) ^ ((i & 255) ^ i5));
                exponent = topSignificandByte(significand);
            } else {
                throw new IllegalStateException("unimplemented");
            }
            i3 = exponent;
            j = significand << 7;
            i4 = 2;
        }
        while (j != 0) {
            i2 = i4 + 1;
            bArr[i4] = (byte) ((i3 | 1) ^ i5);
            i3 = topSignificandByte(j);
            j <<= 7;
            i4 = i2;
        }
        exponent = i4 + 1;
        bArr[i4] = (byte) (i5 ^ i3);
        return Arrays.copyOf(bArr, exponent);
    }

    public static double decodeDouble(byte[] bArr) {
        return decode(bArr).parts().asDouble();
    }

    public static long decodeLong(byte[] bArr) {
        return decode(bArr).parts().asLong();
    }

    public static DecodedNumberParts decode(byte[] bArr) {
        byte[] bArr2 = bArr;
        String str = "Invalid encoded byte array";
        int i = 1;
        if (bArr2.length >= 1) {
            int i2;
            int i3;
            int i4;
            int i5 = bArr2[0] & 255;
            boolean z = (i5 & 128) == 0;
            int i6 = z ? 255 : 0;
            i5 ^= i6;
            Object obj = (i5 & 64) == 0 ? 1 : null;
            int i7 = obj != null ? 255 : 0;
            int i8 = i5 ^ i7;
            int decodeMarker = decodeMarker(i8);
            long j = 0;
            int i9 = 3;
            if (decodeMarker == -4) {
                i2 = 64;
                if (obj == null) {
                    i3 = i5;
                    i5 = 0;
                    i9 = 1;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid encoded number ");
                    stringBuilder.append(Arrays.toString(bArr));
                    stringBuilder.append(": exponent negative zero is invalid");
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            } else if (decodeMarker == -3 || decodeMarker == -2 || decodeMarker == -1) {
                i3 = decodeMarker + 4;
                i7 = 64 - i3;
                j = 0 | (((long) (((~(-1 << (i3 + 1))) & 126) & i5)) << (i7 - 1));
                i2 = i7;
                i9 = 1;
                int i10 = i5;
                i5 = i3;
                i3 = i10;
            } else {
                if (decodeMarker != 1) {
                    if (decodeMarker != 2) {
                        if (decodeMarker != 3) {
                            if (decodeMarker == 6) {
                                NumberParts create;
                                if (z) {
                                    if (obj != null) {
                                        create = NumberParts.create(true, Integer.MIN_VALUE, 0);
                                    } else if (bArr2.length >= 2) {
                                        int i11 = bArr2[1] & 255;
                                        if (i11 == 128) {
                                            create = NumberParts.create(true, Integer.MAX_VALUE, 0);
                                        } else if (i11 == 96) {
                                            create = NumberParts.create(true, Integer.MAX_VALUE, 1);
                                        } else {
                                            throw new IllegalArgumentException(str);
                                        }
                                        i = 2;
                                    } else {
                                        throw new IllegalArgumentException(str);
                                    }
                                } else if (obj != null) {
                                    create = NumberParts.create(false, Integer.MIN_VALUE, 0);
                                } else {
                                    create = NumberParts.create(false, Integer.MAX_VALUE, 0);
                                }
                                return DecodedNumberParts.create(i, create);
                            }
                            throw new IllegalArgumentException(str);
                        } else if (bArr2.length >= 3) {
                            i5 = (((i8 & 3) << 8) | (((bArr2[1] & 255) ^ i6) ^ i7)) + 148;
                            i3 = (bArr2[2] & 255) ^ i6;
                            j = 0 | decodeTrailingSignificandByte(i3, 57);
                        } else {
                            throw new IllegalArgumentException(str);
                        }
                    } else if (bArr2.length >= 3) {
                        i4 = (bArr2[1] & 255) ^ i6;
                        i5 = (((i8 & 7) << 4) | ((i7 ^ i4) >>> 4)) + 20;
                        i3 = (bArr2[2] & 255) ^ i6;
                        j = decodeTrailingSignificandByte(i3, 53) | (((((long) i4) & 15) << 60) | 0);
                        i2 = 53;
                    } else {
                        throw new IllegalArgumentException(str);
                    }
                } else if (bArr2.length >= 2) {
                    i5 = (i8 & 15) + 4;
                    i4 = (bArr2[1] & 255) ^ i6;
                    j = 0 | decodeTrailingSignificandByte(i4, 57);
                    i3 = i4;
                    i9 = 2;
                } else {
                    throw new IllegalArgumentException(str);
                }
                i2 = 57;
            }
            while ((i3 & 1) != 0) {
                if (i9 < bArr2.length) {
                    i3 = i9 + 1;
                    i4 = (bArr2[i9] & 255) ^ i6;
                    i8 = i2 - 7;
                    if (i8 >= 0) {
                        j |= decodeTrailingSignificandByte(i4, i8);
                        i9 = i3;
                        i3 = i4;
                        i2 = i8;
                    } else {
                        j |= ((long) (i4 & ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE)) >>> (-(i8 - 1));
                        if ((i4 & 1) == 0) {
                            i9 = i3;
                            i3 = i4;
                            i2 = 0;
                        } else {
                            throw new IllegalArgumentException("Invalid encoded byte array: overlong sequence");
                        }
                    }
                }
                throw new IllegalArgumentException(str);
            }
            if (obj != null) {
                i5 = -i5;
            }
            return DecodedNumberParts.create(i9, NumberParts.create(z, i5, j));
        }
        throw new IllegalArgumentException(str);
    }

    static int decodeMarker(int i) {
        Object obj = (i & 32) != 0 ? 1 : null;
        if (obj != null) {
            i ^= 255;
        }
        i = 5 - (31 - Integer.numberOfLeadingZeros(i & 63));
        return obj != null ? i : -i;
    }

    private static byte[] copyOf(byte[] bArr) {
        return (byte[]) bArr.clone();
    }
}
