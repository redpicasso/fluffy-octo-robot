package com.google.common.math;

import com.airbnb.lottie.utils.Utils;
import com.drew.metadata.photoshop.PhotoshopDirectory;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;

@GwtCompatible(emulated = true)
public final class IntMath {
    @VisibleForTesting
    static final int FLOOR_SQRT_MAX_INT = 46340;
    @VisibleForTesting
    static final int MAX_POWER_OF_SQRT2_UNSIGNED = -1257966797;
    @VisibleForTesting
    static final int MAX_SIGNED_POWER_OF_TWO = 1073741824;
    @VisibleForTesting
    static int[] biggestBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};
    private static final int[] factorials = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};
    @VisibleForTesting
    static final int[] halfPowersOf10 = new int[]{3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
    @VisibleForTesting
    static final byte[] maxLog10ForLeadingZeros = new byte[]{(byte) 9, (byte) 9, (byte) 9, (byte) 8, (byte) 8, (byte) 8, (byte) 7, (byte) 7, (byte) 7, (byte) 6, (byte) 6, (byte) 6, (byte) 6, (byte) 5, (byte) 5, (byte) 5, (byte) 4, (byte) 4, (byte) 4, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
    @VisibleForTesting
    static final int[] powersOf10 = new int[]{1, 10, 100, 1000, PhotoshopDirectory.TAG_PRINT_FLAGS_INFO, 100000, 1000000, 10000000, 100000000, Utils.SECOND_IN_NANOS};

    /* renamed from: com.google.common.math.IntMath$1 */
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
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.IntMath.1.<clinit>():void");
        }
    }

    public static boolean isPowerOfTwo(int i) {
        int i2 = 0;
        int i3 = i > 0 ? 1 : 0;
        if ((i & (i - 1)) == 0) {
            i2 = 1;
        }
        return i3 & i2;
    }

    @VisibleForTesting
    static int lessThanBranchFree(int i, int i2) {
        return (~(~(i - i2))) >>> 31;
    }

    public static int mean(int i, int i2) {
        return (i & i2) + ((i ^ i2) >> 1);
    }

    @Beta
    public static int ceilingPowerOfTwo(int i) {
        MathPreconditions.checkPositive("x", i);
        if (i <= 1073741824) {
            return 1 << (-Integer.numberOfLeadingZeros(i - 1));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ceilingPowerOfTwo(");
        stringBuilder.append(i);
        stringBuilder.append(") not representable as an int");
        throw new ArithmeticException(stringBuilder.toString());
    }

    @Beta
    public static int floorPowerOfTwo(int i) {
        MathPreconditions.checkPositive("x", i);
        return Integer.highestOneBit(i);
    }

    public static int log2(int i, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", i);
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(i));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                return 32 - Integer.numberOfLeadingZeros(i - 1);
            case 6:
            case 7:
            case 8:
                int numberOfLeadingZeros = Integer.numberOfLeadingZeros(i);
                return (31 - numberOfLeadingZeros) + lessThanBranchFree(MAX_POWER_OF_SQRT2_UNSIGNED >>> numberOfLeadingZeros, i);
            default:
                throw new AssertionError();
        }
        return 31 - Integer.numberOfLeadingZeros(i);
    }

    /* JADX WARNING: Missing block: B:6:0x0027, code:
            return r0 + r3;
     */
    /* JADX WARNING: Missing block: B:12:0x0035, code:
            return r0;
     */
    @com.google.common.annotations.GwtIncompatible
    public static int log10(int r3, java.math.RoundingMode r4) {
        /*
        r0 = "x";
        com.google.common.math.MathPreconditions.checkPositive(r0, r3);
        r0 = log10Floor(r3);
        r1 = powersOf10;
        r1 = r1[r0];
        r2 = com.google.common.math.IntMath.AnonymousClass1.$SwitchMap$java$math$RoundingMode;
        r4 = r4.ordinal();
        r4 = r2[r4];
        switch(r4) {
            case 1: goto L_0x002d;
            case 2: goto L_0x0035;
            case 3: goto L_0x0035;
            case 4: goto L_0x0028;
            case 5: goto L_0x0028;
            case 6: goto L_0x001e;
            case 7: goto L_0x001e;
            case 8: goto L_0x001e;
            default: goto L_0x0018;
        };
    L_0x0018:
        r3 = new java.lang.AssertionError;
        r3.<init>();
        throw r3;
    L_0x001e:
        r4 = halfPowersOf10;
        r4 = r4[r0];
        r3 = lessThanBranchFree(r4, r3);
    L_0x0026:
        r0 = r0 + r3;
        return r0;
    L_0x0028:
        r3 = lessThanBranchFree(r1, r3);
        goto L_0x0026;
    L_0x002d:
        if (r3 != r1) goto L_0x0031;
    L_0x002f:
        r3 = 1;
        goto L_0x0032;
    L_0x0031:
        r3 = 0;
    L_0x0032:
        com.google.common.math.MathPreconditions.checkRoundingUnnecessary(r3);
    L_0x0035:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.IntMath.log10(int, java.math.RoundingMode):int");
    }

    private static int log10Floor(int i) {
        byte b = maxLog10ForLeadingZeros[Integer.numberOfLeadingZeros(i)];
        return b - lessThanBranchFree(i, powersOf10[b]);
    }

    @GwtIncompatible
    public static int pow(int i, int i2) {
        MathPreconditions.checkNonNegative("exponent", i2);
        int i3 = 0;
        if (i != -2) {
            int i4 = -1;
            if (i == -1) {
                if ((i2 & 1) == 0) {
                    i4 = 1;
                }
                return i4;
            } else if (i == 0) {
                if (i2 == 0) {
                    i3 = 1;
                }
                return i3;
            } else if (i == 1) {
                return 1;
            } else {
                if (i != 2) {
                    i4 = i;
                    i = 1;
                    while (i2 != 0) {
                        if (i2 == 1) {
                            return i4 * i;
                        }
                        i *= (i2 & 1) == 0 ? 1 : i4;
                        i4 *= i4;
                        i2 >>= 1;
                    }
                    return i;
                }
                if (i2 < 32) {
                    i3 = 1 << i2;
                }
                return i3;
            }
        } else if (i2 >= 32) {
            return 0;
        } else {
            return (i2 & 1) == 0 ? 1 << i2 : -(1 << i2);
        }
    }

    /* JADX WARNING: Missing block: B:6:0x0022, code:
            return r0 + r2;
     */
    /* JADX WARNING: Missing block: B:13:0x0034, code:
            return r0;
     */
    @com.google.common.annotations.GwtIncompatible
    public static int sqrt(int r2, java.math.RoundingMode r3) {
        /*
        r0 = "x";
        com.google.common.math.MathPreconditions.checkNonNegative(r0, r2);
        r0 = sqrtFloor(r2);
        r1 = com.google.common.math.IntMath.AnonymousClass1.$SwitchMap$java$math$RoundingMode;
        r3 = r3.ordinal();
        r3 = r1[r3];
        switch(r3) {
            case 1: goto L_0x002a;
            case 2: goto L_0x0034;
            case 3: goto L_0x0034;
            case 4: goto L_0x0023;
            case 5: goto L_0x0023;
            case 6: goto L_0x001a;
            case 7: goto L_0x001a;
            case 8: goto L_0x001a;
            default: goto L_0x0014;
        };
    L_0x0014:
        r2 = new java.lang.AssertionError;
        r2.<init>();
        throw r2;
    L_0x001a:
        r3 = r0 * r0;
        r3 = r3 + r0;
        r2 = lessThanBranchFree(r3, r2);
    L_0x0021:
        r0 = r0 + r2;
        return r0;
    L_0x0023:
        r3 = r0 * r0;
        r2 = lessThanBranchFree(r3, r2);
        goto L_0x0021;
    L_0x002a:
        r3 = r0 * r0;
        if (r3 != r2) goto L_0x0030;
    L_0x002e:
        r2 = 1;
        goto L_0x0031;
    L_0x0030:
        r2 = 0;
    L_0x0031:
        com.google.common.math.MathPreconditions.checkRoundingUnnecessary(r2);
    L_0x0034:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.IntMath.sqrt(int, java.math.RoundingMode):int");
    }

    private static int sqrtFloor(int i) {
        return (int) Math.sqrt((double) i);
    }

    /* JADX WARNING: Missing block: B:22:0x0044, code:
            if (((r7 == java.math.RoundingMode.HALF_EVEN ? 1 : 0) & ((r0 & 1) != 0 ? 1 : 0)) != 0) goto L_0x0058;
     */
    /* JADX WARNING: Missing block: B:23:0x0047, code:
            if (r1 > 0) goto L_0x0058;
     */
    /* JADX WARNING: Missing block: B:24:0x004a, code:
            if (r5 > 0) goto L_0x0058;
     */
    /* JADX WARNING: Missing block: B:25:0x004d, code:
            if (r5 < 0) goto L_0x0058;
     */
    public static int divide(int r5, int r6, java.math.RoundingMode r7) {
        /*
        com.google.common.base.Preconditions.checkNotNull(r7);
        if (r6 == 0) goto L_0x005c;
    L_0x0005:
        r0 = r5 / r6;
        r1 = r6 * r0;
        r1 = r5 - r1;
        if (r1 != 0) goto L_0x000e;
    L_0x000d:
        return r0;
    L_0x000e:
        r5 = r5 ^ r6;
        r5 = r5 >> 31;
        r2 = 1;
        r5 = r5 | r2;
        r3 = com.google.common.math.IntMath.AnonymousClass1.$SwitchMap$java$math$RoundingMode;
        r4 = r7.ordinal();
        r3 = r3[r4];
        r4 = 0;
        switch(r3) {
            case 1: goto L_0x0050;
            case 2: goto L_0x0057;
            case 3: goto L_0x004d;
            case 4: goto L_0x0058;
            case 5: goto L_0x004a;
            case 6: goto L_0x0025;
            case 7: goto L_0x0025;
            case 8: goto L_0x0025;
            default: goto L_0x001f;
        };
    L_0x001f:
        r5 = new java.lang.AssertionError;
        r5.<init>();
        throw r5;
    L_0x0025:
        r1 = java.lang.Math.abs(r1);
        r6 = java.lang.Math.abs(r6);
        r6 = r6 - r1;
        r1 = r1 - r6;
        if (r1 != 0) goto L_0x0047;
    L_0x0031:
        r6 = java.math.RoundingMode.HALF_UP;
        if (r7 == r6) goto L_0x0058;
    L_0x0035:
        r6 = java.math.RoundingMode.HALF_EVEN;
        if (r7 != r6) goto L_0x003b;
    L_0x0039:
        r6 = 1;
        goto L_0x003c;
    L_0x003b:
        r6 = 0;
    L_0x003c:
        r7 = r0 & 1;
        if (r7 == 0) goto L_0x0042;
    L_0x0040:
        r7 = 1;
        goto L_0x0043;
    L_0x0042:
        r7 = 0;
    L_0x0043:
        r6 = r6 & r7;
        if (r6 == 0) goto L_0x0057;
    L_0x0046:
        goto L_0x0058;
    L_0x0047:
        if (r1 <= 0) goto L_0x0057;
    L_0x0049:
        goto L_0x0058;
    L_0x004a:
        if (r5 <= 0) goto L_0x0057;
    L_0x004c:
        goto L_0x0058;
    L_0x004d:
        if (r5 >= 0) goto L_0x0057;
    L_0x004f:
        goto L_0x0058;
    L_0x0050:
        if (r1 != 0) goto L_0x0053;
    L_0x0052:
        goto L_0x0054;
    L_0x0053:
        r2 = 0;
    L_0x0054:
        com.google.common.math.MathPreconditions.checkRoundingUnnecessary(r2);
    L_0x0057:
        r2 = 0;
    L_0x0058:
        if (r2 == 0) goto L_0x005b;
    L_0x005a:
        r0 = r0 + r5;
    L_0x005b:
        return r0;
    L_0x005c:
        r5 = new java.lang.ArithmeticException;
        r6 = "/ by zero";
        r5.<init>(r6);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.IntMath.divide(int, int, java.math.RoundingMode):int");
    }

    public static int mod(int i, int i2) {
        if (i2 > 0) {
            i %= i2;
            return i >= 0 ? i : i + i2;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Modulus ");
            stringBuilder.append(i2);
            stringBuilder.append(" must be > 0");
            throw new ArithmeticException(stringBuilder.toString());
        }
    }

    public static int gcd(int i, int i2) {
        MathPreconditions.checkNonNegative("a", i);
        MathPreconditions.checkNonNegative("b", i2);
        if (i == 0) {
            return i2;
        }
        if (i2 == 0) {
            return i;
        }
        int numberOfTrailingZeros = Integer.numberOfTrailingZeros(i);
        i >>= numberOfTrailingZeros;
        int numberOfTrailingZeros2 = Integer.numberOfTrailingZeros(i2);
        i2 >>= numberOfTrailingZeros2;
        while (i != i2) {
            i -= i2;
            int i3 = (i >> 31) & i;
            i = (i - i3) - i3;
            i2 += i3;
            i >>= Integer.numberOfTrailingZeros(i);
        }
        return i << Math.min(numberOfTrailingZeros, numberOfTrailingZeros2);
    }

    public static int checkedAdd(int i, int i2) {
        long j = ((long) i) + ((long) i2);
        int i3 = (int) j;
        MathPreconditions.checkNoOverflow(j == ((long) i3), "checkedAdd", i, i2);
        return i3;
    }

    public static int checkedSubtract(int i, int i2) {
        long j = ((long) i) - ((long) i2);
        int i3 = (int) j;
        MathPreconditions.checkNoOverflow(j == ((long) i3), "checkedSubtract", i, i2);
        return i3;
    }

    public static int checkedMultiply(int i, int i2) {
        long j = ((long) i) * ((long) i2);
        int i3 = (int) j;
        MathPreconditions.checkNoOverflow(j == ((long) i3), "checkedMultiply", i, i2);
        return i3;
    }

    public static int checkedPow(int i, int i2) {
        MathPreconditions.checkNonNegative("exponent", i2);
        String str = "checkedPow";
        int i3 = -1;
        boolean z = false;
        if (i == -2) {
            if (i2 < 32) {
                z = true;
            }
            MathPreconditions.checkNoOverflow(z, str, i, i2);
            return (i2 & 1) == 0 ? 1 << i2 : -1 << i2;
        } else if (i == -1) {
            if ((i2 & 1) == 0) {
                i3 = 1;
            }
            return i3;
        } else if (i == 0) {
            int i4;
            if (i2 == 0) {
                i4 = 1;
            }
            return i4;
        } else if (i == 1) {
            return 1;
        } else {
            if (i != 2) {
                int i5 = i;
                i = 1;
                while (i2 != 0) {
                    if (i2 == 1) {
                        i = checkedMultiply(i, i5);
                        break;
                    }
                    if ((i2 & 1) != 0) {
                        i = checkedMultiply(i, i5);
                    }
                    i2 >>= 1;
                    if (i2 > 0) {
                        MathPreconditions.checkNoOverflow((-46340 <= i5 ? 1 : 0) & (i5 <= FLOOR_SQRT_MAX_INT ? 1 : 0), str, i5, i2);
                        i5 *= i5;
                    }
                }
                return i;
            }
            if (i2 < 31) {
                z = true;
            }
            MathPreconditions.checkNoOverflow(z, str, i, i2);
            return 1 << i2;
        }
    }

    @Beta
    public static int saturatedAdd(int i, int i2) {
        return Ints.saturatedCast(((long) i) + ((long) i2));
    }

    @Beta
    public static int saturatedSubtract(int i, int i2) {
        return Ints.saturatedCast(((long) i) - ((long) i2));
    }

    @Beta
    public static int saturatedMultiply(int i, int i2) {
        return Ints.saturatedCast(((long) i) * ((long) i2));
    }

    @Beta
    public static int saturatedPow(int i, int i2) {
        MathPreconditions.checkNonNegative("exponent", i2);
        int i3 = -1;
        if (i != -2) {
            if (i != -1) {
                int i4 = 0;
                if (i == 0) {
                    if (i2 == 0) {
                        i4 = 1;
                    }
                    return i4;
                } else if (i == 1) {
                    return 1;
                } else {
                    if (i != 2) {
                        i3 = ((i >>> 31) & (i2 & 1)) + Integer.MAX_VALUE;
                        int i5 = i;
                        i = 1;
                        while (i2 != 0) {
                            if (i2 == 1) {
                                i = saturatedMultiply(i, i5);
                                break;
                            }
                            if ((i2 & 1) != 0) {
                                i = saturatedMultiply(i, i5);
                            }
                            i2 >>= 1;
                            if (i2 > 0) {
                                if (((-46340 > i5 ? 1 : 0) | (i5 > FLOOR_SQRT_MAX_INT ? 1 : 0)) != 0) {
                                    return i3;
                                }
                                i5 *= i5;
                            }
                        }
                        return i;
                    } else if (i2 >= 31) {
                        return Integer.MAX_VALUE;
                    } else {
                        return 1 << i2;
                    }
                }
            }
            if ((i2 & 1) == 0) {
                i3 = 1;
            }
            return i3;
        } else if (i2 >= 32) {
            return (i2 & 1) + Integer.MAX_VALUE;
        } else {
            return (i2 & 1) == 0 ? 1 << i2 : -1 << i2;
        }
    }

    public static int factorial(int i) {
        MathPreconditions.checkNonNegative("n", i);
        int[] iArr = factorials;
        return i < iArr.length ? iArr[i] : Integer.MAX_VALUE;
    }

    public static int binomial(int i, int i2) {
        MathPreconditions.checkNonNegative("n", i);
        MathPreconditions.checkNonNegative("k", i2);
        int i3 = 0;
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        int[] iArr = biggestBinomials;
        if (i2 >= iArr.length || i > iArr[i2]) {
            return Integer.MAX_VALUE;
        }
        if (i2 == 0) {
            return 1;
        }
        if (i2 != 1) {
            long j = 1;
            while (i3 < i2) {
                i3++;
                j = (j * ((long) (i - i3))) / ((long) i3);
            }
            i = (int) j;
        }
        return i;
    }

    @GwtIncompatible
    @Beta
    public static boolean isPrime(int i) {
        return LongMath.isPrime((long) i);
    }

    private IntMath() {
    }
}
