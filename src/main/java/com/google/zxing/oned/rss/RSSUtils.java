package com.google.zxing.oned.rss;

public final class RSSUtils {
    private RSSUtils() {
    }

    public static int getRSSvalue(int[] iArr, int i, boolean z) {
        int[] iArr2 = iArr;
        int i2 = i;
        int i3 = 0;
        for (int i4 : iArr2) {
            i3 += i4;
        }
        int length = iArr2.length;
        int i5 = i3;
        int i6 = 0;
        i3 = 0;
        int i42 = 0;
        while (true) {
            int i7 = length - 1;
            if (i6 >= i7) {
                return i3;
            }
            int i8 = 1 << i6;
            int i9 = i42 | i8;
            i42 = i3;
            i3 = 1;
            while (i3 < iArr2[i6]) {
                int i10;
                int i11 = i5 - i3;
                int i12 = length - i6;
                int i13 = i12 - 2;
                int combins = combins(i11 - 1, i13);
                if (z && i9 == 0) {
                    i10 = i12 - 1;
                    if (i11 - i10 >= i10) {
                        combins -= combins(i11 - i12, i13);
                    }
                }
                if (i12 - 1 > 1) {
                    i10 = i11 - i13;
                    i13 = 0;
                    while (i10 > i2) {
                        i13 += combins((i11 - i10) - 1, i12 - 3);
                        i10--;
                        iArr2 = iArr;
                    }
                    combins -= i13 * (i7 - i6);
                } else if (i11 > i2) {
                    combins--;
                }
                i42 += combins;
                i3++;
                i9 &= ~i8;
                iArr2 = iArr;
            }
            i5 -= i3;
            i6++;
            iArr2 = iArr;
            i3 = i42;
            i42 = i9;
        }
    }

    private static int combins(int i, int i2) {
        int i3 = i - i2;
        if (i3 > i2) {
            int i4 = i3;
            i3 = i2;
            i2 = i4;
        }
        int i5 = 1;
        int i6 = 1;
        while (i > i2) {
            i5 *= i;
            if (i6 <= i3) {
                i5 /= i6;
                i6++;
            }
            i--;
        }
        while (i6 <= i3) {
            i5 /= i6;
            i6++;
        }
        return i5;
    }
}
