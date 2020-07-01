package com.google.firebase.auth.api.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.LibraryVersion;
import com.google.android.gms.internal.firebase_auth.zzam;
import java.util.List;

public final class zzeg {
    private final int zzph;
    private final int zzpi = -1;

    private zzeg(String str, int i) {
        this.zzph = zzcd(str);
    }

    public final boolean zzej() {
        return this.zzph >= zzcd("16.2.1");
    }

    public static String zzek() {
        return zzcc("firebase-auth");
    }

    private static String zzcc(String str) {
        str = LibraryVersion.getInstance().getVersion(str);
        return (TextUtils.isEmpty(str) || str.equals("UNKNOWN")) ? "-1" : str;
    }

    public static zzeg zzel() {
        return new zzeg(zzcc("firebase-auth-impl"), -1);
    }

    private static int zzcd(String str) {
        List<String> zza = zzam.zzbp(".").zza((CharSequence) str);
        if (zza.size() == 1) {
            return Integer.parseInt(str);
        }
        if (zza.size() == 3) {
            return ((Integer.parseInt((String) zza.get(0)) * 1000000) + (Integer.parseInt((String) zza.get(1)) * 1000)) + Integer.parseInt((String) zza.get(2));
        }
        String str2 = "";
        for (String str3 : zza) {
            str2 = String.valueOf(str2);
            String str32 = String.valueOf(str32);
            str2 = str32.length() != 0 ? str2.concat(str32) : new String(str2);
        }
        return Integer.parseInt(str2);
    }
}
