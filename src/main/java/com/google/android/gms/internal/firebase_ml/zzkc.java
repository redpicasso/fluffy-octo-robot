package com.google.android.gms.internal.firebase_ml;

import com.google.firebase.analytics.FirebaseAnalytics.Param;

public abstract class zzkc {
    public static zzkc zza(char c) {
        return new zzke(',');
    }

    public abstract boolean zzb(char c);

    protected zzkc() {
    }

    public int zza(CharSequence charSequence, int i) {
        int length = charSequence.length();
        zzks.zza(i, length, Param.INDEX);
        while (i < length) {
            if (zzb(charSequence.charAt(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private static String zzc(char c) {
        char[] cArr = new char[]{'\\', 'u', 0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            cArr[5 - i] = "0123456789ABCDEF".charAt(c & 15);
            int c2 = (char) (c2 >> 4);
        }
        return String.copyValueOf(cArr);
    }
}
