package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
public final class Strings {
    private Strings() {
    }

    public static String nullToEmpty(@NullableDecl String str) {
        return Platform.nullToEmpty(str);
    }

    @NullableDecl
    public static String emptyToNull(@NullableDecl String str) {
        return Platform.emptyToNull(str);
    }

    public static boolean isNullOrEmpty(@NullableDecl String str) {
        return Platform.stringIsNullOrEmpty(str);
    }

    public static String padStart(String str, int i, char c) {
        Preconditions.checkNotNull(str);
        if (str.length() >= i) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder(i);
        for (int length = str.length(); length < i; length++) {
            stringBuilder.append(c);
        }
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    public static String padEnd(String str, int i, char c) {
        Preconditions.checkNotNull(str);
        if (str.length() >= i) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder(i);
        stringBuilder.append(str);
        for (int length = str.length(); length < i; length++) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String repeat(String str, int i) {
        Preconditions.checkNotNull(str);
        boolean z = true;
        if (i <= 1) {
            if (i < 0) {
                z = false;
            }
            Preconditions.checkArgument(z, "invalid count: %s", i);
            if (i == 0) {
                str = "";
            }
            return str;
        }
        int length = str.length();
        long j = ((long) length) * ((long) i);
        i = (int) j;
        if (((long) i) == j) {
            Object obj = new char[i];
            str.getChars(0, length, obj, 0);
            while (true) {
                int i2 = i - length;
                if (length < i2) {
                    System.arraycopy(obj, 0, obj, length, length);
                    length <<= 1;
                } else {
                    System.arraycopy(obj, 0, obj, length, i2);
                    return new String(obj);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Required array size too large: ");
        stringBuilder.append(j);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    public static String commonPrefix(CharSequence charSequence, CharSequence charSequence2) {
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        int min = Math.min(charSequence.length(), charSequence2.length());
        int i = 0;
        while (i < min && charSequence.charAt(i) == charSequence2.charAt(i)) {
            i++;
        }
        min = i - 1;
        if (validSurrogatePairAt(charSequence, min) || validSurrogatePairAt(charSequence2, min)) {
            i--;
        }
        return charSequence.subSequence(0, i).toString();
    }

    public static String commonSuffix(CharSequence charSequence, CharSequence charSequence2) {
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        int min = Math.min(charSequence.length(), charSequence2.length());
        int i = 0;
        while (i < min && charSequence.charAt((charSequence.length() - i) - 1) == charSequence2.charAt((charSequence2.length() - i) - 1)) {
            i++;
        }
        if (validSurrogatePairAt(charSequence, (charSequence.length() - i) - 1) || validSurrogatePairAt(charSequence2, (charSequence2.length() - i) - 1)) {
            i--;
        }
        return charSequence.subSequence(charSequence.length() - i, charSequence.length()).toString();
    }

    @VisibleForTesting
    static boolean validSurrogatePairAt(CharSequence charSequence, int i) {
        if (i < 0 || i > charSequence.length() - 2 || !Character.isHighSurrogate(charSequence.charAt(i)) || !Character.isLowSurrogate(charSequence.charAt(i + 1))) {
            return false;
        }
        return true;
    }

    public static String lenientFormat(@NullableDecl String str, @NullableDecl Object... objArr) {
        CharSequence valueOf = String.valueOf(str);
        int i = 0;
        if (objArr == null) {
            objArr = new Object[]{"(Object[])null"};
        } else {
            for (int i2 = 0; i2 < objArr.length; i2++) {
                objArr[i2] = lenientToString(objArr[i2]);
            }
        }
        StringBuilder stringBuilder = new StringBuilder(valueOf.length() + (objArr.length * 16));
        int i3 = 0;
        while (i < objArr.length) {
            int indexOf = valueOf.indexOf("%s", i3);
            if (indexOf == -1) {
                break;
            }
            stringBuilder.append(valueOf, i3, indexOf);
            i3 = i + 1;
            stringBuilder.append(objArr[i]);
            int i4 = i3;
            i3 = indexOf + 2;
            i = i4;
        }
        stringBuilder.append(valueOf, i3, valueOf.length());
        if (i < objArr.length) {
            stringBuilder.append(" [");
            int i5 = i + 1;
            stringBuilder.append(objArr[i]);
            while (i5 < objArr.length) {
                stringBuilder.append(", ");
                i = i5 + 1;
                stringBuilder.append(objArr[i5]);
                i5 = i;
            }
            stringBuilder.append(']');
        }
        return stringBuilder.toString();
    }

    private static String lenientToString(@NullableDecl Object obj) {
        try {
            obj = String.valueOf(obj);
            return obj;
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(obj.getClass().getName());
            stringBuilder.append('@');
            stringBuilder.append(Integer.toHexString(System.identityHashCode(obj)));
            String stringBuilder2 = stringBuilder.toString();
            Logger logger = Logger.getLogger("com.google.common.base.Strings");
            Level level = Level.WARNING;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Exception during lenientFormat for ");
            stringBuilder3.append(stringBuilder2);
            logger.log(level, stringBuilder3.toString(), e);
            stringBuilder = new StringBuilder();
            stringBuilder.append("<");
            stringBuilder.append(stringBuilder2);
            stringBuilder.append(" threw ");
            stringBuilder.append(e.getClass().getName());
            stringBuilder.append(">");
            return stringBuilder.toString();
        }
    }
}
