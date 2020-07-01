package com.google.zxing.oned;

public final class CodaBarWriter extends OneDimensionalCodeWriter {
    private static final char[] ALT_START_END_CHARS = new char[]{'T', 'N', '*', 'E'};
    private static final char[] CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED = new char[]{'/', ':', '+', '.'};
    private static final char DEFAULT_GUARD = START_END_CHARS[0];
    private static final char[] START_END_CHARS = new char[]{'A', 'B', 'C', 'D'};

    public boolean[] encode(String str) {
        StringBuilder stringBuilder;
        if (str.length() < 2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(DEFAULT_GUARD);
            stringBuilder.append(str);
            stringBuilder.append(DEFAULT_GUARD);
            str = stringBuilder.toString();
        } else {
            char toUpperCase = Character.toUpperCase(str.charAt(0));
            char toUpperCase2 = Character.toUpperCase(str.charAt(str.length() - 1));
            boolean arrayContains = CodaBarReader.arrayContains(START_END_CHARS, toUpperCase);
            boolean arrayContains2 = CodaBarReader.arrayContains(START_END_CHARS, toUpperCase2);
            boolean arrayContains3 = CodaBarReader.arrayContains(ALT_START_END_CHARS, toUpperCase);
            boolean arrayContains4 = CodaBarReader.arrayContains(ALT_START_END_CHARS, toUpperCase2);
            String str2 = "Invalid start/end guards: ";
            if (arrayContains) {
                if (!arrayContains2) {
                    throw new IllegalArgumentException(str2.concat(String.valueOf(str)));
                }
            } else if (arrayContains3) {
                if (!arrayContains4) {
                    throw new IllegalArgumentException(str2.concat(String.valueOf(str)));
                }
            } else if (arrayContains2 || arrayContains4) {
                throw new IllegalArgumentException(str2.concat(String.valueOf(str)));
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(DEFAULT_GUARD);
                stringBuilder.append(str);
                stringBuilder.append(DEFAULT_GUARD);
                str = stringBuilder.toString();
            }
        }
        int i = 1;
        int i2 = 20;
        while (i < str.length() - 1) {
            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '-' || str.charAt(i) == '$') {
                i2 += 9;
            } else if (CodaBarReader.arrayContains(CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED, str.charAt(i))) {
                i2 += 10;
            } else {
                StringBuilder stringBuilder2 = new StringBuilder("Cannot encode : '");
                stringBuilder2.append(str.charAt(i));
                stringBuilder2.append('\'');
                throw new IllegalArgumentException(stringBuilder2.toString());
            }
            i++;
        }
        boolean[] zArr = new boolean[(i2 + (str.length() - 1))];
        i2 = 0;
        int i3 = 0;
        while (i2 < str.length()) {
            int i4;
            int i5;
            char toUpperCase3 = Character.toUpperCase(str.charAt(i2));
            if (i2 == 0 || i2 == str.length() - 1) {
                if (toUpperCase3 == '*') {
                    toUpperCase3 = 'C';
                } else if (toUpperCase3 == 'E') {
                    toUpperCase3 = 'D';
                } else if (toUpperCase3 == 'N') {
                    toUpperCase3 = 'B';
                } else if (toUpperCase3 == 'T') {
                    toUpperCase3 = 'A';
                }
            }
            for (i4 = 0; i4 < CodaBarReader.ALPHABET.length; i4++) {
                if (toUpperCase3 == CodaBarReader.ALPHABET[i4]) {
                    i5 = CodaBarReader.CHARACTER_ENCODINGS[i4];
                    break;
                }
            }
            i5 = 0;
            i4 = i3;
            i3 = 0;
            boolean z = true;
            while (true) {
                int i6 = 0;
                while (i3 < 7) {
                    zArr[i4] = z;
                    i4++;
                    if (((i5 >> (6 - i3)) & 1) == 0 || i6 == 1) {
                        z ^= 1;
                        i3++;
                    } else {
                        i6++;
                    }
                }
                break;
            }
            if (i2 < str.length() - 1) {
                zArr[i4] = false;
                i4++;
            }
            i3 = i4;
            i2++;
        }
        return zArr;
    }
}
