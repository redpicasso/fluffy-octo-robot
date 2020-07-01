package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

@GwtCompatible
@Beta
public abstract class UnicodeEscaper extends Escaper {
    private static final int DEST_PAD = 32;

    protected abstract char[] escape(int i);

    protected UnicodeEscaper() {
    }

    public String escape(String str) {
        Preconditions.checkNotNull(str);
        int length = str.length();
        int nextEscapeIndex = nextEscapeIndex(str, 0, length);
        return nextEscapeIndex == length ? str : escapeSlow(str, nextEscapeIndex);
    }

    protected int nextEscapeIndex(CharSequence charSequence, int i, int i2) {
        while (i < i2) {
            int codePointAt = codePointAt(charSequence, i, i2);
            if (codePointAt < 0 || escape(codePointAt) != null) {
                break;
            }
            i += Character.isSupplementaryCodePoint(codePointAt) ? 2 : 1;
        }
        return i;
    }

    protected final String escapeSlow(String str, int i) {
        int length = str.length();
        char[] charBufferFromThreadLocal = Platform.charBufferFromThreadLocal();
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            int codePointAt = codePointAt(str, i, length);
            if (codePointAt >= 0) {
                Object escape = escape(codePointAt);
                codePointAt = (Character.isSupplementaryCodePoint(codePointAt) ? 2 : 1) + i;
                if (escape != null) {
                    int i4 = i - i2;
                    int i5 = i3 + i4;
                    int length2 = escape.length + i5;
                    if (charBufferFromThreadLocal.length < length2) {
                        charBufferFromThreadLocal = growBuffer(charBufferFromThreadLocal, i3, (length2 + (length - i)) + 32);
                    }
                    if (i4 > 0) {
                        str.getChars(i2, i, charBufferFromThreadLocal, i3);
                        i3 = i5;
                    }
                    if (escape.length > 0) {
                        System.arraycopy(escape, 0, charBufferFromThreadLocal, i3, escape.length);
                        i3 += escape.length;
                    }
                    i2 = codePointAt;
                }
                i = nextEscapeIndex(str, codePointAt, length);
            } else {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
        }
        i = length - i2;
        if (i > 0) {
            i += i3;
            if (charBufferFromThreadLocal.length < i) {
                charBufferFromThreadLocal = growBuffer(charBufferFromThreadLocal, i3, i);
            }
            str.getChars(i2, length, charBufferFromThreadLocal, i3);
        } else {
            i = i3;
        }
        return new String(charBufferFromThreadLocal, 0, i);
    }

    protected static int codePointAt(CharSequence charSequence, int i, int i2) {
        Preconditions.checkNotNull(charSequence);
        if (i < i2) {
            int i3 = i + 1;
            char charAt = charSequence.charAt(i);
            if (charAt < 55296 || charAt > 57343) {
                return charAt;
            }
            String str = "'";
            String str2 = " in '";
            String str3 = " at index ";
            String str4 = "' with value ";
            StringBuilder stringBuilder;
            if (charAt > 56319) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected low surrogate character '");
                stringBuilder.append(charAt);
                stringBuilder.append(str4);
                stringBuilder.append(charAt);
                stringBuilder.append(str3);
                stringBuilder.append(i3 - 1);
                stringBuilder.append(str2);
                stringBuilder.append(charSequence);
                stringBuilder.append(str);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (i3 == i2) {
                return -charAt;
            } else {
                char charAt2 = charSequence.charAt(i3);
                if (Character.isLowSurrogate(charAt2)) {
                    return Character.toCodePoint(charAt, charAt2);
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("Expected low surrogate but got char '");
                stringBuilder.append(charAt2);
                stringBuilder.append(str4);
                stringBuilder.append(charAt2);
                stringBuilder.append(str3);
                stringBuilder.append(i3);
                stringBuilder.append(str2);
                stringBuilder.append(charSequence);
                stringBuilder.append(str);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        throw new IndexOutOfBoundsException("Index exceeds specified range");
    }

    private static char[] growBuffer(char[] cArr, int i, int i2) {
        if (i2 >= 0) {
            Object obj = new char[i2];
            if (i > 0) {
                System.arraycopy(cArr, 0, obj, 0, i);
            }
            return obj;
        }
        throw new AssertionError("Cannot increase internal buffer any further");
    }
}
