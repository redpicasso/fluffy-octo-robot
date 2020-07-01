package com.google.android.gms.internal.firebase_ml;

public final class zzig extends zzij {
    private static final char[] zzaam = new char[]{'+'};
    private static final char[] zzaan = "0123456789ABCDEF".toCharArray();
    private final boolean zzaao;
    private final boolean[] zzaap;

    public zzig(String str, boolean z) {
        if (str.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        } else if (z && str.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        } else if (str.contains("%")) {
            throw new IllegalArgumentException("The '%' character cannot be specified as 'safe'");
        } else {
            this.zzaao = z;
            char[] toCharArray = str.toCharArray();
            int i = 122;
            for (char max : toCharArray) {
                i = Math.max(max, i);
            }
            boolean[] zArr = new boolean[(i + 1)];
            for (i = 48; i <= 57; i++) {
                zArr[i] = true;
            }
            for (i = 65; i <= 90; i++) {
                zArr[i] = true;
            }
            for (i = 97; i <= 122; i++) {
                zArr[i] = true;
            }
            for (char c : toCharArray) {
                zArr[c] = true;
            }
            this.zzaap = zArr;
        }
    }

    protected final int zza(CharSequence charSequence, int i, int i2) {
        while (i < i2) {
            char charAt = charSequence.charAt(i);
            boolean[] zArr = this.zzaap;
            if (charAt >= zArr.length || !zArr[charAt]) {
                break;
            }
            i++;
        }
        return i;
    }

    public final String zzaw(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            boolean[] zArr = this.zzaap;
            if (charAt >= zArr.length || !zArr[charAt]) {
                return zza(str, i);
            }
        }
        return str;
    }

    protected final char[] zzai(int i) {
        boolean[] zArr = this.zzaap;
        if (i < zArr.length && zArr[i]) {
            return null;
        }
        if (i == 32 && this.zzaao) {
            return zzaam;
        }
        char[] cArr;
        char[] cArr2;
        if (i <= 127) {
            cArr = new char[3];
            cArr2 = zzaan;
            cArr[2] = cArr2[i & 15];
            cArr[1] = cArr2[i >>> 4];
            return cArr;
        } else if (i <= 2047) {
            cArr = new char[6];
            cArr2 = zzaan;
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
            char[] cArr3 = zzaan;
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
            cArr2 = zzaan;
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
            StringBuilder stringBuilder = new StringBuilder(43);
            stringBuilder.append("Invalid unicode character value ");
            stringBuilder.append(i);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }
}
