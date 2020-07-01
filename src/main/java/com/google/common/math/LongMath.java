package com.google.common.math;

import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.drew.metadata.exif.makernotes.OlympusCameraSettingsMakernoteDirectory;
import com.drew.metadata.exif.makernotes.OlympusImageProcessingMakernoteDirectory;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedLongs;
import java.math.RoundingMode;

@GwtCompatible(emulated = true)
public final class LongMath {
    @VisibleForTesting
    static final long FLOOR_SQRT_MAX_LONG = 3037000499L;
    @VisibleForTesting
    static final long MAX_POWER_OF_SQRT2_UNSIGNED = -5402926248376769404L;
    @VisibleForTesting
    static final long MAX_SIGNED_POWER_OF_TWO = 4611686018427387904L;
    private static final int SIEVE_30 = -545925251;
    static final int[] biggestBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 3810779, 121977, 16175, 4337, 1733, 887, 534, 361, 265, 206, 169, 143, 125, 111, 101, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66};
    @VisibleForTesting
    static final int[] biggestSimpleBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 2642246, 86251, 11724, 3218, OlympusCameraSettingsMakernoteDirectory.TagPictureModeSaturation, 684, 419, OlympusImageProcessingMakernoteDirectory.TagWbGLevel, 214, 169, NikonType2MakernoteDirectory.TAG_LENS_STOPS, 119, 105, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61};
    static final long[] factorials = new long[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
    @GwtIncompatible
    @VisibleForTesting
    static final long[] halfPowersOf10 = new long[]{3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L};
    @VisibleForTesting
    static final byte[] maxLog10ForLeadingZeros = new byte[]{(byte) 19, Ascii.DC2, Ascii.DC2, Ascii.DC2, Ascii.DC2, (byte) 17, (byte) 17, (byte) 17, Ascii.DLE, Ascii.DLE, Ascii.DLE, Ascii.SI, Ascii.SI, Ascii.SI, Ascii.SI, Ascii.SO, Ascii.SO, Ascii.SO, Ascii.CR, Ascii.CR, Ascii.CR, Ascii.FF, Ascii.FF, Ascii.FF, Ascii.FF, Ascii.VT, Ascii.VT, Ascii.VT, (byte) 10, (byte) 10, (byte) 10, (byte) 9, (byte) 9, (byte) 9, (byte) 9, (byte) 8, (byte) 8, (byte) 8, (byte) 7, (byte) 7, (byte) 7, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 5, (byte) 5, (byte) 4, (byte) 4, (byte) 4, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0};
    private static final long[][] millerRabinBaseSets = new long[][]{new long[]{291830, 126401071349994536L}, new long[]{885594168, 725270293939359937L, 3569819667048198375L}, new long[]{273919523040L, 15, 7363882082L, 992620450144556L}, new long[]{47636622961200L, 2, 2570940, 211991001, 3749873356L}, new long[]{7999252175582850L, 2, 4130806001517L, 149795463772692060L, 186635894390467037L, 3967304179347715805L}, new long[]{585226005592931976L, 2, 123635709730000L, 9233062284813009L, 43835965440333360L, 761179012939631437L, 1263739024124850375L}, new long[]{Long.MAX_VALUE, 2, 325, 9375, 28178, 450775, 9780504, 1795265022}};
    @GwtIncompatible
    @VisibleForTesting
    static final long[] powersOf10 = new long[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};

    /* renamed from: com.google.common.math.LongMath$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode = new int[RoundingMode.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:17:0x0062, code:
            return;
     */
        static {
            /*
            r0 = java.math.RoundingMode.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$java$math$RoundingMode = r0;
            r0 = $SwitchMap$java$math$RoundingMode;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = java.math.RoundingMode.UNNECESSARY;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$java$math$RoundingMode;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = java.math.RoundingMode.DOWN;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$java$math$RoundingMode;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = java.math.RoundingMode.FLOOR;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$java$math$RoundingMode;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = java.math.RoundingMode.UP;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r0 = $SwitchMap$java$math$RoundingMode;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = java.math.RoundingMode.CEILING;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r2 = 5;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            r0 = $SwitchMap$java$math$RoundingMode;	 Catch:{ NoSuchFieldError -> 0x004b }
            r1 = java.math.RoundingMode.HALF_DOWN;	 Catch:{ NoSuchFieldError -> 0x004b }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x004b }
            r2 = 6;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x004b }
        L_0x004b:
            r0 = $SwitchMap$java$math$RoundingMode;	 Catch:{ NoSuchFieldError -> 0x0056 }
            r1 = java.math.RoundingMode.HALF_UP;	 Catch:{ NoSuchFieldError -> 0x0056 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0056 }
            r2 = 7;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0056 }
        L_0x0056:
            r0 = $SwitchMap$java$math$RoundingMode;	 Catch:{ NoSuchFieldError -> 0x0062 }
            r1 = java.math.RoundingMode.HALF_EVEN;	 Catch:{ NoSuchFieldError -> 0x0062 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0062 }
            r2 = 8;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0062 }
        L_0x0062:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.LongMath.1.<clinit>():void");
        }
    }

    private enum MillerRabinTester {
        SMALL {
            long mulMod(long j, long j2, long j3) {
                return (j * j2) % j3;
            }

            long squareMod(long j, long j2) {
                return (j * j) % j2;
            }
        },
        LARGE {
            private long plusMod(long j, long j2, long j3) {
                return j >= j3 - j2 ? (j + j2) - j3 : j + j2;
            }

            private long times2ToThe32Mod(long j, long j2) {
                int i = 32;
                do {
                    int min = Math.min(i, Long.numberOfLeadingZeros(j));
                    j = UnsignedLongs.remainder(j << min, j2);
                    i -= min;
                } while (i > 0);
                return j;
            }

            long mulMod(long j, long j2, long j3) {
                long j4 = j3;
                long j5 = j >>> 32;
                long j6 = j2 >>> 32;
                long j7 = j & 4294967295L;
                long j8 = j2 & 4294967295L;
                long times2ToThe32Mod = times2ToThe32Mod(j5 * j6, j4) + (j5 * j8);
                if (times2ToThe32Mod < 0) {
                    times2ToThe32Mod = UnsignedLongs.remainder(times2ToThe32Mod, j4);
                }
                Long.signum(j7);
                return plusMod(times2ToThe32Mod(times2ToThe32Mod + (j6 * j7), j4), UnsignedLongs.remainder(j7 * j8, j4), j3);
            }

            long squareMod(long j, long j2) {
                long j3 = j >>> 32;
                j &= 4294967295L;
                long times2ToThe32Mod = times2ToThe32Mod(j3 * j3, j2);
                j3 = (j3 * j) * 2;
                if (j3 < 0) {
                    j3 = UnsignedLongs.remainder(j3, j2);
                }
                return plusMod(times2ToThe32Mod(times2ToThe32Mod + j3, j2), UnsignedLongs.remainder(j * j, j2), j2);
            }
        };

        abstract long mulMod(long j, long j2, long j3);

        abstract long squareMod(long j, long j2);

        static boolean test(long j, long j2) {
            return (j2 <= LongMath.FLOOR_SQRT_MAX_LONG ? SMALL : LARGE).testWitness(j, j2);
        }

        private long powMod(long j, long j2, long j3) {
            long j4 = 1;
            while (j2 != 0) {
                if ((j2 & 1) != 0) {
                    j4 = mulMod(j4, j, j3);
                }
                j = squareMod(j, j3);
                j2 >>= 1;
            }
            return j4;
        }

        private boolean testWitness(long j, long j2) {
            long j3 = j2;
            long j4 = j3 - 1;
            int numberOfTrailingZeros = Long.numberOfTrailingZeros(j4);
            long j5 = j4 >> numberOfTrailingZeros;
            long j6 = j % j3;
            if (j6 == 0) {
                return true;
            }
            long powMod = powMod(j6, j5, j2);
            if (powMod == 1) {
                return true;
            }
            int i = 0;
            while (powMod != j4) {
                i++;
                if (i == numberOfTrailingZeros) {
                    return false;
                }
                powMod = squareMod(powMod, j3);
            }
            return true;
        }
    }

    static boolean fitsInInt(long j) {
        return ((long) ((int) j)) == j;
    }

    public static boolean isPowerOfTwo(long j) {
        int i = 1;
        int i2 = j > 0 ? 1 : 0;
        if ((j & (j - 1)) != 0) {
            i = 0;
        }
        return i2 & i;
    }

    @VisibleForTesting
    static int lessThanBranchFree(long j, long j2) {
        return (int) ((~(~(j - j2))) >>> 63);
    }

    public static long mean(long j, long j2) {
        return (j & j2) + ((j ^ j2) >> 1);
    }

    @Beta
    public static long saturatedAdd(long j, long j2) {
        long j3 = j + j2;
        int i = 1;
        int i2 = (j2 ^ j) < 0 ? 1 : 0;
        if ((j ^ j3) < 0) {
            i = 0;
        }
        return (i2 | i) != 0 ? j3 : ((j3 >>> 63) ^ 1) + Long.MAX_VALUE;
    }

    @Beta
    public static long saturatedSubtract(long j, long j2) {
        long j3 = j - j2;
        int i = 1;
        int i2 = (j2 ^ j) >= 0 ? 1 : 0;
        if ((j ^ j3) < 0) {
            i = 0;
        }
        return (i2 | i) != 0 ? j3 : ((j3 >>> 63) ^ 1) + Long.MAX_VALUE;
    }

    @Beta
    public static long ceilingPowerOfTwo(long j) {
        MathPreconditions.checkPositive("x", j);
        if (j <= 4611686018427387904L) {
            return 1 << (-Long.numberOfLeadingZeros(j - 1));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ceilingPowerOfTwo(");
        stringBuilder.append(j);
        stringBuilder.append(") is not representable as a long");
        throw new ArithmeticException(stringBuilder.toString());
    }

    @Beta
    public static long floorPowerOfTwo(long j) {
        MathPreconditions.checkPositive("x", j);
        return 1 << (63 - Long.numberOfLeadingZeros(j));
    }

    public static int log2(long j, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", j);
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(j));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 64 - Long.numberOfLeadingZeros(j - 1);
            case 6:
            case 7:
            case 8:
                int numberOfLeadingZeros = Long.numberOfLeadingZeros(j);
                return (63 - numberOfLeadingZeros) + lessThanBranchFree(MAX_POWER_OF_SQRT2_UNSIGNED >>> numberOfLeadingZeros, j);
            default:
                throw new AssertionError("impossible");
        }
        return 63 - Long.numberOfLeadingZeros(j);
    }

    /* JADX WARNING: Missing block: B:6:0x0027, code:
            return r0 + r4;
     */
    /* JADX WARNING: Missing block: B:13:0x0037, code:
            return r0;
     */
    @com.google.common.annotations.GwtIncompatible
    public static int log10(long r4, java.math.RoundingMode r6) {
        /*
        r0 = "x";
        com.google.common.math.MathPreconditions.checkPositive(r0, r4);
        r0 = log10Floor(r4);
        r1 = powersOf10;
        r2 = r1[r0];
        r1 = com.google.common.math.LongMath.AnonymousClass1.$SwitchMap$java$math$RoundingMode;
        r6 = r6.ordinal();
        r6 = r1[r6];
        switch(r6) {
            case 1: goto L_0x002d;
            case 2: goto L_0x0037;
            case 3: goto L_0x0037;
            case 4: goto L_0x0028;
            case 5: goto L_0x0028;
            case 6: goto L_0x001e;
            case 7: goto L_0x001e;
            case 8: goto L_0x001e;
            default: goto L_0x0018;
        };
    L_0x0018:
        r4 = new java.lang.AssertionError;
        r4.<init>();
        throw r4;
    L_0x001e:
        r6 = halfPowersOf10;
        r1 = r6[r0];
        r4 = lessThanBranchFree(r1, r4);
    L_0x0026:
        r0 = r0 + r4;
        return r0;
    L_0x0028:
        r4 = lessThanBranchFree(r2, r4);
        goto L_0x0026;
    L_0x002d:
        r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
        if (r6 != 0) goto L_0x0033;
    L_0x0031:
        r4 = 1;
        goto L_0x0034;
    L_0x0033:
        r4 = 0;
    L_0x0034:
        com.google.common.math.MathPreconditions.checkRoundingUnnecessary(r4);
    L_0x0037:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.LongMath.log10(long, java.math.RoundingMode):int");
    }

    @GwtIncompatible
    static int log10Floor(long j) {
        byte b = maxLog10ForLeadingZeros[Long.numberOfLeadingZeros(j)];
        return b - lessThanBranchFree(j, powersOf10[b]);
    }

    @GwtIncompatible
    public static long pow(long j, int i) {
        MathPreconditions.checkNonNegative("exponent", i);
        long j2 = 1;
        if (-2 > j || j > 2) {
            long j3 = j;
            j = 1;
            while (i != 0) {
                if (i == 1) {
                    j *= j3;
                    break;
                }
                j *= (i & 1) == 0 ? 1 : j3;
                j3 *= j3;
                i >>= 1;
            }
            return j;
        }
        int i2 = (int) j;
        long j4 = 0;
        if (i2 != -2) {
            if (i2 == -1) {
                if ((i & 1) != 0) {
                    j2 = -1;
                }
                return j2;
            } else if (i2 == 0) {
                if (i != 0) {
                    j2 = 0;
                }
                return j2;
            } else if (i2 == 1) {
                return 1;
            } else {
                if (i2 == 2) {
                    if (i < 64) {
                        j4 = 1 << i;
                    }
                    return j4;
                }
                throw new AssertionError();
            }
        } else if (i >= 64) {
            return 0;
        } else {
            return (i & 1) == 0 ? 1 << i : -(1 << i);
        }
    }

    @GwtIncompatible
    public static long sqrt(long j, RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", j);
        if (fitsInInt(j)) {
            return (long) IntMath.sqrt((int) j, roundingMode);
        }
        long sqrt = (long) Math.sqrt((double) j);
        long j2 = sqrt * sqrt;
        boolean z = true;
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                if (j2 != j) {
                    z = false;
                }
                MathPreconditions.checkRoundingUnnecessary(z);
                return sqrt;
            case 2:
            case 3:
                if (j < j2) {
                    sqrt--;
                }
                return sqrt;
            case 4:
            case 5:
                if (j > j2) {
                    sqrt++;
                }
                return sqrt;
            case 6:
            case 7:
            case 8:
                int i;
                if (j >= j2) {
                    i = 0;
                }
                sqrt -= (long) i;
                return sqrt + ((long) lessThanBranchFree((sqrt * sqrt) + sqrt, j));
            default:
                throw new AssertionError();
        }
    }

    /* JADX WARNING: Missing block: B:21:0x0051, code:
            if (r11 > 0) goto L_0x0062;
     */
    /* JADX WARNING: Missing block: B:22:0x0054, code:
            if (r10 > 0) goto L_0x0062;
     */
    /* JADX WARNING: Missing block: B:23:0x0057, code:
            if (r10 < 0) goto L_0x0062;
     */
    @com.google.common.annotations.GwtIncompatible
    public static long divide(long r9, long r11, java.math.RoundingMode r13) {
        /*
        com.google.common.base.Preconditions.checkNotNull(r13);
        r0 = r9 / r11;
        r2 = r11 * r0;
        r2 = r9 - r2;
        r4 = 0;
        r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r6 != 0) goto L_0x0010;
    L_0x000f:
        return r0;
    L_0x0010:
        r9 = r9 ^ r11;
        r7 = 63;
        r9 = r9 >> r7;
        r10 = (int) r9;
        r9 = 1;
        r10 = r10 | r9;
        r7 = com.google.common.math.LongMath.AnonymousClass1.$SwitchMap$java$math$RoundingMode;
        r8 = r13.ordinal();
        r7 = r7[r8];
        r8 = 0;
        switch(r7) {
            case 1: goto L_0x005a;
            case 2: goto L_0x0061;
            case 3: goto L_0x0057;
            case 4: goto L_0x0062;
            case 5: goto L_0x0054;
            case 6: goto L_0x0029;
            case 7: goto L_0x0029;
            case 8: goto L_0x0029;
            default: goto L_0x0023;
        };
    L_0x0023:
        r9 = new java.lang.AssertionError;
        r9.<init>();
        throw r9;
    L_0x0029:
        r2 = java.lang.Math.abs(r2);
        r11 = java.lang.Math.abs(r11);
        r11 = r11 - r2;
        r2 = r2 - r11;
        r11 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r11 != 0) goto L_0x0051;
    L_0x0037:
        r11 = java.math.RoundingMode.HALF_UP;
        if (r13 != r11) goto L_0x003d;
    L_0x003b:
        r11 = 1;
        goto L_0x003e;
    L_0x003d:
        r11 = 0;
    L_0x003e:
        r12 = java.math.RoundingMode.HALF_EVEN;
        if (r13 != r12) goto L_0x0044;
    L_0x0042:
        r12 = 1;
        goto L_0x0045;
    L_0x0044:
        r12 = 0;
    L_0x0045:
        r2 = 1;
        r2 = r2 & r0;
        r13 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r13 == 0) goto L_0x004d;
    L_0x004c:
        goto L_0x004e;
    L_0x004d:
        r9 = 0;
    L_0x004e:
        r9 = r9 & r12;
        r9 = r9 | r11;
        goto L_0x0062;
    L_0x0051:
        if (r11 <= 0) goto L_0x0061;
    L_0x0053:
        goto L_0x0062;
    L_0x0054:
        if (r10 <= 0) goto L_0x0061;
    L_0x0056:
        goto L_0x0062;
    L_0x0057:
        if (r10 >= 0) goto L_0x0061;
    L_0x0059:
        goto L_0x0062;
    L_0x005a:
        if (r6 != 0) goto L_0x005d;
    L_0x005c:
        goto L_0x005e;
    L_0x005d:
        r9 = 0;
    L_0x005e:
        com.google.common.math.MathPreconditions.checkRoundingUnnecessary(r9);
    L_0x0061:
        r9 = 0;
    L_0x0062:
        if (r9 == 0) goto L_0x0066;
    L_0x0064:
        r9 = (long) r10;
        r0 = r0 + r9;
    L_0x0066:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.LongMath.divide(long, long, java.math.RoundingMode):long");
    }

    @GwtIncompatible
    public static int mod(long j, int i) {
        return (int) mod(j, (long) i);
    }

    @GwtIncompatible
    public static long mod(long j, long j2) {
        if (j2 > 0) {
            j %= j2;
            return j >= 0 ? j : j + j2;
        } else {
            throw new ArithmeticException("Modulus must be positive");
        }
    }

    public static long gcd(long j, long j2) {
        MathPreconditions.checkNonNegative("a", j);
        MathPreconditions.checkNonNegative("b", j2);
        if (j == 0) {
            return j2;
        }
        if (j2 == 0) {
            return j;
        }
        int numberOfTrailingZeros = Long.numberOfTrailingZeros(j);
        j >>= numberOfTrailingZeros;
        int numberOfTrailingZeros2 = Long.numberOfTrailingZeros(j2);
        j2 >>= numberOfTrailingZeros2;
        while (j != j2) {
            j -= j2;
            long j3 = (j >> 63) & j;
            j = (j - j3) - j3;
            j2 += j3;
            j >>= Long.numberOfTrailingZeros(j);
        }
        return j << Math.min(numberOfTrailingZeros, numberOfTrailingZeros2);
    }

    @GwtIncompatible
    public static long checkedAdd(long j, long j2) {
        long j3 = j + j2;
        int i = 1;
        int i2 = (j ^ j2) < 0 ? 1 : 0;
        if ((j ^ j3) < 0) {
            i = 0;
        }
        MathPreconditions.checkNoOverflow(i2 | i, "checkedAdd", j, j2);
        return j3;
    }

    @GwtIncompatible
    public static long checkedSubtract(long j, long j2) {
        long j3 = j - j2;
        int i = 1;
        int i2 = (j ^ j2) >= 0 ? 1 : 0;
        if ((j ^ j3) < 0) {
            i = 0;
        }
        MathPreconditions.checkNoOverflow(i2 | i, "checkedSubtract", j, j2);
        return j3;
    }

    public static long checkedMultiply(long j, long j2) {
        long j3 = j;
        long j4 = j2;
        int numberOfLeadingZeros = ((Long.numberOfLeadingZeros(j) + Long.numberOfLeadingZeros(~j3)) + Long.numberOfLeadingZeros(j2)) + Long.numberOfLeadingZeros(~j4);
        if (numberOfLeadingZeros > 65) {
            return j3 * j4;
        }
        MathPreconditions.checkNoOverflow(numberOfLeadingZeros >= 64, "checkedMultiply", j, j2);
        int i = (j3 > 0 ? 1 : (j3 == 0 ? 0 : -1));
        MathPreconditions.checkNoOverflow((i >= 0 ? 1 : 0) | (j4 != Long.MIN_VALUE ? 1 : 0), "checkedMultiply", j, j2);
        long j5 = j3 * j4;
        boolean z = i == 0 || j5 / j3 == j4;
        MathPreconditions.checkNoOverflow(z, "checkedMultiply", j, j2);
        return j5;
    }

    @GwtIncompatible
    public static long checkedPow(long j, int i) {
        long j2 = j;
        int i2 = i;
        MathPreconditions.checkNonNegative("exponent", i2);
        boolean z = false;
        long j3 = 1;
        if (((j2 >= -2 ? 1 : 0) & (j2 <= 2 ? 1 : 0)) != 0) {
            int i3 = (int) j2;
            if (i3 == -2) {
                if (i2 < 64) {
                    z = true;
                }
                MathPreconditions.checkNoOverflow(z, "checkedPow", j, (long) i2);
                return (i2 & 1) == 0 ? 1 << i2 : -1 << i2;
            } else if (i3 == -1) {
                if ((i2 & 1) != 0) {
                    j3 = -1;
                }
                return j3;
            } else if (i3 == 0) {
                if (i2 != 0) {
                    j3 = 0;
                }
                return j3;
            } else if (i3 == 1) {
                return 1;
            } else {
                if (i3 == 2) {
                    if (i2 < 63) {
                        z = true;
                    }
                    MathPreconditions.checkNoOverflow(z, "checkedPow", j, (long) i2);
                    return 1 << i2;
                }
                throw new AssertionError();
            }
        }
        while (i2 != 0) {
            if (i2 == 1) {
                return checkedMultiply(j3, j2);
            }
            if ((i2 & 1) != 0) {
                j3 = checkedMultiply(j3, j2);
            }
            i2 >>= 1;
            if (i2 > 0) {
                boolean z2 = -3037000499L <= j2 && j2 <= FLOOR_SQRT_MAX_LONG;
                MathPreconditions.checkNoOverflow(z2, "checkedPow", j2, (long) i2);
                j2 *= j2;
            }
        }
        return j3;
    }

    @Beta
    public static long saturatedMultiply(long j, long j2) {
        int numberOfLeadingZeros = ((Long.numberOfLeadingZeros(j) + Long.numberOfLeadingZeros(~j)) + Long.numberOfLeadingZeros(j2)) + Long.numberOfLeadingZeros(~j2);
        if (numberOfLeadingZeros > 65) {
            return j * j2;
        }
        long j3 = ((j ^ j2) >>> 63) + Long.MAX_VALUE;
        int i = 1;
        numberOfLeadingZeros = numberOfLeadingZeros < 64 ? 1 : 0;
        int i2 = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        int i3 = i2 < 0 ? 1 : 0;
        if (j2 != Long.MIN_VALUE) {
            i = 0;
        }
        if ((numberOfLeadingZeros | (i & i3)) != 0) {
            return j3;
        }
        long j4 = j * j2;
        return (i2 == 0 || j4 / j == j2) ? j4 : j3;
    }

    @Beta
    public static long saturatedPow(long j, int i) {
        MathPreconditions.checkNonNegative("exponent", i);
        long j2 = 1;
        if (((j >= -2 ? 1 : 0) & (j <= 2 ? 1 : 0)) != 0) {
            int i2 = (int) j;
            if (i2 != -2) {
                if (i2 == -1) {
                    if ((i & 1) != 0) {
                        j2 = -1;
                    }
                    return j2;
                } else if (i2 == 0) {
                    if (i != 0) {
                        j2 = 0;
                    }
                    return j2;
                } else if (i2 == 1) {
                    return 1;
                } else {
                    if (i2 == 2) {
                        return i >= 63 ? Long.MAX_VALUE : 1 << i;
                    } else {
                        throw new AssertionError();
                    }
                }
            } else if (i >= 64) {
                return ((long) (i & 1)) + Long.MAX_VALUE;
            } else {
                return (i & 1) == 0 ? 1 << i : -1 << i;
            }
        }
        long j3 = ((j >>> 63) & ((long) (i & 1))) + Long.MAX_VALUE;
        while (i != 0) {
            if (i == 1) {
                return saturatedMultiply(j2, j);
            }
            if ((i & 1) != 0) {
                j2 = saturatedMultiply(j2, j);
            }
            i >>= 1;
            if (i > 0) {
                if (((-3037000499L > j ? 1 : 0) | (j > FLOOR_SQRT_MAX_LONG ? 1 : 0)) != 0) {
                    return j3;
                }
                j *= j;
            }
        }
        return j2;
    }

    @GwtIncompatible
    public static long factorial(int i) {
        MathPreconditions.checkNonNegative("n", i);
        long[] jArr = factorials;
        return i < jArr.length ? jArr[i] : Long.MAX_VALUE;
    }

    public static long binomial(int i, int i2) {
        MathPreconditions.checkNonNegative("n", i);
        MathPreconditions.checkNonNegative("k", i2);
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        if (i2 == 0) {
            return 1;
        }
        if (i2 == 1) {
            return (long) i;
        }
        long[] jArr = factorials;
        if (i < jArr.length) {
            return jArr[i] / (jArr[i2] * jArr[i - i2]);
        }
        int[] iArr = biggestBinomials;
        if (i2 >= iArr.length || i > iArr[i2]) {
            return Long.MAX_VALUE;
        }
        iArr = biggestSimpleBinomials;
        int i3 = 2;
        int log2;
        if (i2 >= iArr.length || i > iArr[i2]) {
            long j = (long) i;
            log2 = log2(j, RoundingMode.CEILING);
            long j2 = 1;
            long j3 = j;
            int i4 = i - 1;
            i = log2;
            j = j2;
            while (i3 <= i2) {
                i += log2;
                if (i < 63) {
                    j3 *= (long) i4;
                    j2 *= (long) i3;
                } else {
                    long multiplyFraction = multiplyFraction(j, j3, j2);
                    i = log2;
                    j2 = (long) i3;
                    j3 = (long) i4;
                    j = multiplyFraction;
                }
                i3++;
                i4--;
            }
            return multiplyFraction(j, j3, j2);
        }
        log2 = i - 1;
        long j4 = (long) i;
        while (i3 <= i2) {
            j4 = (j4 * ((long) log2)) / ((long) i3);
            log2--;
            i3++;
        }
        return j4;
    }

    static long multiplyFraction(long j, long j2, long j3) {
        if (j == 1) {
            return j2 / j3;
        }
        long gcd = gcd(j, j3);
        return (j / gcd) * (j2 / (j3 / gcd));
    }

    @GwtIncompatible
    @Beta
    public static boolean isPrime(long j) {
        int i = (j > 2 ? 1 : (j == 2 ? 0 : -1));
        if (i < 0) {
            MathPreconditions.checkNonNegative("n", j);
            return false;
        } else if (i == 0 || j == 3 || j == 5 || j == 7 || j == 11 || j == 13) {
            return true;
        } else {
            if ((SIEVE_30 & (1 << ((int) (j % 30)))) != 0 || j % 7 == 0 || j % 11 == 0 || j % 13 == 0) {
                return false;
            }
            if (j < 289) {
                return true;
            }
            for (long[] jArr : millerRabinBaseSets) {
                if (j <= jArr[0]) {
                    for (int i2 = 1; i2 < jArr.length; i2++) {
                        if (!MillerRabinTester.test(jArr[i2], j)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            throw new AssertionError();
        }
    }

    private LongMath() {
    }
}
