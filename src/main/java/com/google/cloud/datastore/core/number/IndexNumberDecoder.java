package com.google.cloud.datastore.core.number;

import com.drew.metadata.exif.ExifDirectoryBase;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class IndexNumberDecoder {
    private String doubleResultRepProblemMessage;
    private String longResultRepProblemMessage;
    private double resultAsDouble;
    private long resultAsLong;
    private int resultExponent;
    private boolean resultNegative;
    private long resultSignificand;

    private static long decodeTrailingSignificandByte(int i, int i2) {
        return ((long) (i & ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE)) << (i2 - 1);
    }

    public IndexNumberDecoder() {
        reset();
    }

    public void reset() {
        String str = "No bytes decoded.";
        this.longResultRepProblemMessage = str;
        this.doubleResultRepProblemMessage = str;
    }

    public boolean isResultLong() {
        updateResultLongState();
        return this.longResultRepProblemMessage.isEmpty();
    }

    public long resultAsLong() {
        updateResultLongState();
        if (this.longResultRepProblemMessage.isEmpty()) {
            return this.resultAsLong;
        }
        throw new IllegalArgumentException(this.longResultRepProblemMessage);
    }

    public boolean isResultDouble() {
        updateResultDoubleState();
        return this.doubleResultRepProblemMessage.isEmpty();
    }

    private void updateResultLongState() {
        if (this.longResultRepProblemMessage == null) {
            String str = "Number is not an integer.";
            String str2 = "Number is outside the long range.";
            this.longResultRepProblemMessage = "";
            int i = this.resultExponent;
            if (i == Integer.MAX_VALUE) {
                if (this.resultSignificand != 0) {
                    this.longResultRepProblemMessage = "NaN is not an integer.";
                } else if (this.resultNegative) {
                    this.longResultRepProblemMessage = "+Infinity is not an integer.";
                } else {
                    this.longResultRepProblemMessage = "-Infinity is not an integer.";
                }
            } else if (i == Integer.MIN_VALUE && this.resultSignificand == 0) {
                this.resultAsLong = 0;
            } else {
                i = this.resultExponent;
                if (i < 0) {
                    this.longResultRepProblemMessage = str;
                } else if (i >= 64) {
                    this.longResultRepProblemMessage = str2;
                } else if (i != 63) {
                    int numberOfTrailingZeros = 64 - Long.numberOfTrailingZeros(this.resultSignificand);
                    int i2 = this.resultExponent;
                    if (i2 < numberOfTrailingZeros) {
                        this.longResultRepProblemMessage = str;
                    } else {
                        long j = (1 << i2) ^ (this.resultSignificand >>> ((63 - i2) + 1));
                        if (this.resultNegative) {
                            j = -j;
                        }
                        this.resultAsLong = j;
                    }
                } else if (this.resultSignificand == 0 && this.resultNegative) {
                    this.resultAsLong = Long.MIN_VALUE;
                } else {
                    this.longResultRepProblemMessage = str2;
                }
            }
        }
    }

    public double resultAsDouble() {
        updateResultDoubleState();
        if (this.doubleResultRepProblemMessage.isEmpty()) {
            return this.resultAsDouble;
        }
        throw new IllegalArgumentException(this.doubleResultRepProblemMessage);
    }

    private void updateResultDoubleState() {
        if (this.doubleResultRepProblemMessage == null) {
            this.doubleResultRepProblemMessage = "";
            int i = this.resultExponent;
            long j = 0;
            if (i == Integer.MAX_VALUE) {
                if (this.resultSignificand != 0) {
                    this.resultAsDouble = Double.NaN;
                } else if (this.resultNegative) {
                    this.resultAsDouble = Double.NEGATIVE_INFINITY;
                } else {
                    this.resultAsDouble = Double.POSITIVE_INFINITY;
                }
            } else if (i == Integer.MIN_VALUE && this.resultSignificand == 0) {
                this.resultAsDouble = 0.0d;
            } else if (64 - Long.numberOfTrailingZeros(this.resultSignificand) > 52) {
                this.doubleResultRepProblemMessage = "Number has too many significant bits for a double.";
            } else {
                this.resultSignificand >>>= 12;
                i = this.resultExponent;
                if (i >= -1022) {
                    this.resultExponent = i + 1023;
                } else {
                    int i2 = -1022 - i;
                    long j2 = this.resultSignificand;
                    this.resultSignificand = j2 >>> i2;
                    if ((this.resultSignificand << i2) != j2) {
                        this.doubleResultRepProblemMessage = "Number has too many significant bits for a subnormal double.";
                    }
                    this.resultSignificand |= 1 << (52 - i2);
                    this.resultExponent = 0;
                }
                long j3 = (((long) this.resultExponent) << 52) | this.resultSignificand;
                if (this.resultNegative) {
                    j = Long.MIN_VALUE;
                }
                this.resultAsDouble = Double.longBitsToDouble(j3 | j);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0103  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0103  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0103  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x012c  */
    public int decode(boolean r18, byte[] r19, int r20) {
        /*
        r17 = this;
        r0 = r17;
        r1 = r20 + 1;
        r2 = r19[r20];
        r3 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r2 = r2 & r3;
        r4 = r2 & 128;
        r6 = 1;
        if (r4 != 0) goto L_0x0010;
    L_0x000e:
        r4 = 1;
        goto L_0x0011;
    L_0x0010:
        r4 = 0;
    L_0x0011:
        if (r4 == 0) goto L_0x0016;
    L_0x0013:
        r7 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        goto L_0x0017;
    L_0x0016:
        r7 = 0;
    L_0x0017:
        r2 = r2 ^ r7;
        r8 = r4 ^ r18;
        r9 = r2 & 64;
        if (r9 != 0) goto L_0x0020;
    L_0x001e:
        r9 = 1;
        goto L_0x0021;
    L_0x0020:
        r9 = 0;
    L_0x0021:
        if (r9 == 0) goto L_0x0026;
    L_0x0023:
        r10 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        goto L_0x0027;
    L_0x0026:
        r10 = 0;
    L_0x0027:
        r11 = r2 ^ r10;
        r12 = decodeMarker(r11);
        r13 = -4;
        r14 = 0;
        if (r12 == r13) goto L_0x00fb;
    L_0x0032:
        r13 = -3;
        r5 = -1;
        if (r12 == r13) goto L_0x00e8;
    L_0x0036:
        r13 = -2;
        if (r12 == r13) goto L_0x00e8;
    L_0x0039:
        if (r12 == r5) goto L_0x00e8;
    L_0x003b:
        if (r12 == r6) goto L_0x00cf;
    L_0x003d:
        r2 = 2;
        if (r12 == r2) goto L_0x00a2;
    L_0x0040:
        r2 = 3;
        if (r12 == r2) goto L_0x0085;
    L_0x0043:
        r2 = 6;
        r5 = "Invalid encoded byte array";
        if (r12 != r2) goto L_0x007f;
    L_0x0048:
        r2 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r6 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        if (r4 == 0) goto L_0x0073;
    L_0x004f:
        if (r9 == 0) goto L_0x0055;
    L_0x0051:
        r0.recordNumber(r8, r2, r14);
        goto L_0x007c;
    L_0x0055:
        r2 = r1 + 1;
        r1 = r19[r1];
        r1 = r1 & r3;
        r3 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        if (r1 != r3) goto L_0x0062;
    L_0x005e:
        r0.recordNumber(r8, r6, r14);
        goto L_0x006b;
    L_0x0062:
        r3 = 96;
        if (r1 != r3) goto L_0x006d;
    L_0x0066:
        r3 = 1;
        r0.recordNumber(r8, r6, r3);
    L_0x006b:
        r1 = r2;
        goto L_0x007c;
    L_0x006d:
        r1 = new java.lang.IllegalArgumentException;
        r1.<init>(r5);
        throw r1;
    L_0x0073:
        if (r9 == 0) goto L_0x0079;
    L_0x0075:
        r0.recordNumber(r8, r2, r14);
        goto L_0x007c;
    L_0x0079:
        r0.recordNumber(r8, r6, r14);
    L_0x007c:
        r1 = r1 - r20;
        return r1;
    L_0x007f:
        r1 = new java.lang.IllegalArgumentException;
        r1.<init>(r5);
        throw r1;
    L_0x0085:
        r2 = r2 & r11;
        r2 = r2 << 8;
        r4 = r1 + 1;
        r1 = r19[r1];
        r1 = r1 & r3;
        r1 = r1 ^ r7;
        r1 = r1 ^ r10;
        r1 = r1 | r2;
        r5 = r1 + 148;
        r1 = r4 + 1;
        r2 = r19[r4];
        r2 = r2 & r3;
        r2 = r2 ^ r7;
        r4 = 57;
        r10 = decodeTrailingSignificandByte(r2, r4);
        r14 = r14 | r10;
        r10 = 57;
        goto L_0x0100;
    L_0x00a2:
        r2 = r11 & 7;
        r2 = r2 << 4;
        r4 = r1 + 1;
        r1 = r19[r1];
        r1 = r1 & r3;
        r1 = r1 ^ r7;
        r5 = r1 ^ r10;
        r5 = r5 >>> 4;
        r2 = r2 | r5;
        r5 = r2 + 20;
        r2 = 60;
        r10 = (long) r1;
        r12 = 15;
        r10 = r10 & r12;
        r1 = r10 << r2;
        r1 = r1 | r14;
        r10 = r4 + 1;
        r4 = r19[r4];
        r4 = r4 & r3;
        r4 = r4 ^ r7;
        r11 = 53;
        r12 = decodeTrailingSignificandByte(r4, r11);
        r14 = r1 | r12;
        r2 = r4;
        r1 = r10;
        r10 = 53;
        goto L_0x0100;
    L_0x00cf:
        r2 = r11 & 15;
        r5 = r2 + 4;
        r2 = r1 + 1;
        r1 = r19[r1];
        r1 = r1 & r3;
        r1 = r1 ^ r7;
        r4 = 57;
        r10 = decodeTrailingSignificandByte(r1, r4);
        r14 = r14 | r10;
        r10 = 57;
    L_0x00e2:
        r16 = r2;
        r2 = r1;
        r1 = r16;
        goto L_0x0100;
    L_0x00e8:
        r4 = r12 + 4;
        r10 = 64;
        r10 = r10 - r4;
        r11 = r4 + 1;
        r5 = r5 << r11;
        r5 = ~r5;
        r5 = r5 & 126;
        r5 = r5 & r2;
        r11 = (long) r5;
        r5 = r10 + -1;
        r11 = r11 << r5;
        r14 = r14 | r11;
        r5 = r4;
        goto L_0x0100;
    L_0x00fb:
        r10 = 64;
        if (r9 != 0) goto L_0x0133;
    L_0x00ff:
        r5 = 0;
    L_0x0100:
        r2 = r2 & r6;
        if (r2 == 0) goto L_0x012a;
    L_0x0103:
        r2 = r1 + 1;
        r1 = r19[r1];
        r1 = r1 & r3;
        r1 = r1 ^ r7;
        r10 = r10 + -7;
        if (r10 < 0) goto L_0x0113;
    L_0x010d:
        r11 = decodeTrailingSignificandByte(r1, r10);
        r14 = r14 | r11;
        goto L_0x00e2;
    L_0x0113:
        r4 = r1 & 254;
        r11 = (long) r4;
        r10 = r10 + -1;
        r4 = -r10;
        r10 = r11 >>> r4;
        r14 = r14 | r10;
        r4 = r1 & 1;
        if (r4 != 0) goto L_0x0122;
    L_0x0120:
        r10 = 0;
        goto L_0x00e2;
    L_0x0122:
        r1 = new java.lang.IllegalArgumentException;
        r2 = "Invalid encoded byte array: overlong sequence";
        r1.<init>(r2);
        throw r1;
    L_0x012a:
        if (r9 == 0) goto L_0x012d;
    L_0x012c:
        r5 = -r5;
    L_0x012d:
        r0.recordNumber(r8, r5, r14);
        r1 = r1 - r20;
        return r1;
    L_0x0133:
        r1 = new java.lang.IllegalArgumentException;
        r2 = "Invalid encoded number: exponent negative zero is invalid";
        r1.<init>(r2);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.cloud.datastore.core.number.IndexNumberDecoder.decode(boolean, byte[], int):int");
    }

    private void recordNumber(boolean z, int i, long j) {
        this.longResultRepProblemMessage = null;
        this.doubleResultRepProblemMessage = null;
        this.resultNegative = z;
        this.resultExponent = i;
        this.resultSignificand = j;
    }

    static int decodeMarker(int i) {
        Object obj = (i & 32) != 0 ? 1 : null;
        if (obj != null) {
            i ^= 255;
        }
        i = 5 - (31 - Integer.numberOfLeadingZeros(i & 63));
        return obj != null ? i : -i;
    }
}
