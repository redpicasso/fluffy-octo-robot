package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
@Beta
public final class Utf8 {
    public static int encodedLength(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < 128) {
            i++;
        }
        int i2 = length;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt >= 2048) {
                i2 += encodedLengthGeneral(charSequence, i);
                break;
            }
            i2 += (127 - charAt) >>> 31;
            i++;
        }
        if (i2 >= length) {
            return i2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UTF-8 length does not fit in int: ");
        stringBuilder.append(((long) i2) + 4294967296L);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static int encodedLengthGeneral(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 2048) {
                i2 += (127 - charAt) >>> 31;
            } else {
                i2 += 2;
                if (55296 <= charAt && charAt <= 57343) {
                    if (Character.codePointAt(charSequence, i) != charAt) {
                        i++;
                    } else {
                        throw new IllegalArgumentException(unpairedSurrogateMsg(i));
                    }
                }
            }
            i++;
        }
        return i2;
    }

    public static boolean isWellFormed(byte[] bArr) {
        return isWellFormed(bArr, 0, bArr.length);
    }

    public static boolean isWellFormed(byte[] bArr, int i, int i2) {
        i2 += i;
        Preconditions.checkPositionIndexes(i, i2, bArr.length);
        while (i < i2) {
            if (bArr[i] < (byte) 0) {
                return isWellFormedSlowPath(bArr, i, i2);
            }
            i++;
        }
        return true;
    }

    private static boolean isWellFormedSlowPath(byte[] bArr, int i, int i2) {
        while (i < i2) {
            int i3 = i + 1;
            byte b = bArr[i];
            if (b < (byte) 0) {
                byte b2;
                if (b < (byte) -32) {
                    if (i3 != i2 && b >= (byte) -62) {
                        i = i3 + 1;
                        if (bArr[i3] > (byte) -65) {
                        }
                    }
                    return false;
                } else if (b < (byte) -16) {
                    int i4 = i3 + 1;
                    if (i4 >= i2) {
                        return false;
                    }
                    b2 = bArr[i3];
                    if (b2 <= (byte) -65 && ((b != (byte) -32 || b2 >= (byte) -96) && (b != (byte) -19 || (byte) -96 > b2))) {
                        i = i4 + 1;
                        if (bArr[i4] > (byte) -65) {
                        }
                    }
                    return false;
                } else if (i3 + 2 >= i2) {
                    return false;
                } else {
                    int i5 = i3 + 1;
                    b2 = bArr[i3];
                    if (b2 <= (byte) -65 && (((b << 28) + (b2 + 112)) >> 30) == 0) {
                        i = i5 + 1;
                        if (bArr[i5] <= (byte) -65) {
                            i3 = i + 1;
                            if (bArr[i] > (byte) -65) {
                            }
                        }
                    }
                    return false;
                }
            }
            i = i3;
        }
        return true;
    }

    private static String unpairedSurrogateMsg(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unpaired surrogate at index ");
        stringBuilder.append(i);
        return stringBuilder.toString();
    }

    private Utf8() {
    }
}
