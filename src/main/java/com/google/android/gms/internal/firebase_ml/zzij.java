package com.google.android.gms.internal.firebase_ml;

public abstract class zzij extends zzif {
    protected abstract int zza(CharSequence charSequence, int i, int i2);

    protected abstract char[] zzai(int i);

    protected final String zza(String str, int i) {
        int length = str.length();
        char[] zzhk = zzih.zzhk();
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            if (i < length) {
                int i4 = i + 1;
                int charAt = str.charAt(i);
                int i5 = 1;
                if (charAt >= 55296 && charAt <= 57343) {
                    String str2 = " at index ";
                    String str3 = "' with value ";
                    StringBuilder stringBuilder;
                    if (charAt > 56319) {
                        i4--;
                        stringBuilder = new StringBuilder(82);
                        stringBuilder.append("Unexpected low surrogate character '");
                        stringBuilder.append(charAt);
                        stringBuilder.append(str3);
                        stringBuilder.append(charAt);
                        stringBuilder.append(str2);
                        stringBuilder.append(i4);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    } else if (i4 == length) {
                        charAt = -charAt;
                    } else {
                        char charAt2 = str.charAt(i4);
                        if (Character.isLowSurrogate(charAt2)) {
                            charAt = Character.toCodePoint(charAt, charAt2);
                        } else {
                            stringBuilder = new StringBuilder(83);
                            stringBuilder.append("Expected low surrogate but got char '");
                            stringBuilder.append(charAt2);
                            stringBuilder.append(str3);
                            stringBuilder.append(charAt2);
                            stringBuilder.append(str2);
                            stringBuilder.append(i4);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                    }
                }
                if (charAt >= 0) {
                    Object zzai = zzai(charAt);
                    if (Character.isSupplementaryCodePoint(charAt)) {
                        i5 = 2;
                    }
                    i5 += i;
                    if (zzai != null) {
                        charAt = i - i2;
                        int i6 = i3 + charAt;
                        int length2 = zzai.length + i6;
                        if (zzhk.length < length2) {
                            zzhk = zza(zzhk, i3, ((length2 + length) - i) + 32);
                        }
                        if (charAt > 0) {
                            str.getChars(i2, i, zzhk, i3);
                            i3 = i6;
                        }
                        if (zzai.length > 0) {
                            System.arraycopy(zzai, 0, zzhk, i3, zzai.length);
                            i3 += zzai.length;
                        }
                        i2 = i5;
                    }
                    i = zza((CharSequence) str, i5, length);
                } else {
                    throw new IllegalArgumentException("Trailing high surrogate at end of input");
                }
            }
            throw new IndexOutOfBoundsException("Index exceeds specified range");
        }
        i = length - i2;
        if (i > 0) {
            i += i3;
            if (zzhk.length < i) {
                zzhk = zza(zzhk, i3, i);
            }
            str.getChars(i2, length, zzhk, i3);
        } else {
            i = i3;
        }
        return new String(zzhk, 0, i);
    }

    private static char[] zza(char[] cArr, int i, int i2) {
        Object obj = new char[i2];
        if (i > 0) {
            System.arraycopy(cArr, 0, obj, 0, i);
        }
        return obj;
    }
}
