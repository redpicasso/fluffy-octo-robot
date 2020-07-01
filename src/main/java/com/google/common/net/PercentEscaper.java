package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.escape.UnicodeEscaper;

@GwtCompatible
@Beta
public final class PercentEscaper extends UnicodeEscaper {
    private static final char[] PLUS_SIGN = new char[]{'+'};
    private static final char[] UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private final boolean plusForSpace;
    private final boolean[] safeOctets;

    public PercentEscaper(String str, boolean z) {
        Preconditions.checkNotNull(str);
        if (str.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        str = stringBuilder.toString();
        if (z && str.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        this.plusForSpace = z;
        this.safeOctets = createSafeOctets(str);
    }

    private static boolean[] createSafeOctets(String str) {
        char[] toCharArray = str.toCharArray();
        int i = -1;
        for (char max : toCharArray) {
            i = Math.max(max, i);
        }
        boolean[] zArr = new boolean[(i + 1)];
        for (char max2 : toCharArray) {
            zArr[max2] = true;
        }
        return zArr;
    }

    protected int nextEscapeIndex(CharSequence charSequence, int i, int i2) {
        Preconditions.checkNotNull(charSequence);
        while (i < i2) {
            char charAt = charSequence.charAt(i);
            boolean[] zArr = this.safeOctets;
            if (charAt >= zArr.length || !zArr[charAt]) {
                break;
            }
            i++;
        }
        return i;
    }

    public String escape(String str) {
        Preconditions.checkNotNull(str);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            boolean[] zArr = this.safeOctets;
            if (charAt >= zArr.length || !zArr[charAt]) {
                return escapeSlow(str, i);
            }
        }
        return str;
    }

    protected char[] escape(int i) {
        boolean[] zArr = this.safeOctets;
        if (i < zArr.length && zArr[i]) {
            return null;
        }
        if (i == 32 && this.plusForSpace) {
            return PLUS_SIGN;
        }
        char[] cArr;
        char[] cArr2;
        if (i <= 127) {
            cArr = new char[3];
            cArr2 = UPPER_HEX_DIGITS;
            cArr[2] = cArr2[i & 15];
            cArr[1] = cArr2[i >>> 4];
            return cArr;
        } else if (i <= 2047) {
            cArr = new char[6];
            cArr2 = UPPER_HEX_DIGITS;
            cArr[5] = cArr2[i & 15];
            i >>>= 4;
            cArr[4] = cArr2[(i & 3) | 8];
            i >>>= 2;
            cArr[2] = cArr2[i & 15];
            cArr[1] = cArr2[(i >>> 4) | 12];
            return cArr;
        } else if (i <= 65535) {
            cArr = new char[9];
            cArr[0] = '%';
            cArr[1] = 'E';
            cArr[3] = '%';
            cArr[6] = '%';
            char[] cArr3 = UPPER_HEX_DIGITS;
            cArr[8] = cArr3[i & 15];
            i >>>= 4;
            cArr[7] = cArr3[(i & 3) | 8];
            i >>>= 2;
            cArr[5] = cArr3[i & 15];
            i >>>= 4;
            cArr[4] = cArr3[(i & 3) | 8];
            cArr[2] = cArr3[i >>> 2];
            return cArr;
        } else if (i <= 1114111) {
            cArr = new char[12];
            cArr[0] = '%';
            cArr[1] = 'F';
            cArr[3] = '%';
            cArr[6] = '%';
            cArr[9] = '%';
            cArr2 = UPPER_HEX_DIGITS;
            cArr[11] = cArr2[i & 15];
            i >>>= 4;
            cArr[10] = cArr2[(i & 3) | 8];
            i >>>= 2;
            cArr[8] = cArr2[i & 15];
            i >>>= 4;
            cArr[7] = cArr2[(i & 3) | 8];
            i >>>= 2;
            cArr[5] = cArr2[i & 15];
            i >>>= 4;
            cArr[4] = cArr2[(i & 3) | 8];
            cArr[2] = cArr2[(i >>> 2) & 7];
            return cArr;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid unicode character value ");
            stringBuilder.append(i);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }
}
