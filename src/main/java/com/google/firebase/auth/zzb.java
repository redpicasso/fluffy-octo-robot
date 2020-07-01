package com.google.firebase.auth;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzaz;
import java.util.Set;

public final class zzb {
    private static final zzaz<String, Integer> zzhz = zzaz.zzb("recoverEmail", Integer.valueOf(2), "resetPassword", Integer.valueOf(0), "signIn", Integer.valueOf(4), "verifyEmail", Integer.valueOf(1));
    private final String zzht;
    private final String zzhu;
    private final String zzhv;
    @Nullable
    private final String zzhw;
    @Nullable
    private final String zzhx;
    @Nullable
    private final String zzhy;

    private zzb(String str) {
        this.zzht = zza(str, "apiKey");
        this.zzhu = zza(str, "oobCode");
        this.zzhv = zza(str, "mode");
        if (this.zzht == null || this.zzhu == null || this.zzhv == null) {
            throw new IllegalArgumentException(String.format("%s, %s and %s are required in a valid action code URL", new Object[]{r0, r1, r2}));
        }
        this.zzhw = zza(str, "continueUrl");
        this.zzhx = zza(str, "languageCode");
        this.zzhy = zza(str, "tenantId");
    }

    @Nullable
    public static zzb zzbr(String str) {
        Preconditions.checkNotEmpty(str);
        try {
            return new zzb(str);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public final int getOperation() {
        return ((Integer) zzhz.getOrDefault(this.zzhv, Integer.valueOf(3))).intValue();
    }

    public final String zzcn() {
        return this.zzhu;
    }

    @Nullable
    public final String zzba() {
        return this.zzhy;
    }

    private static String zza(String str, String str2) {
        String str3 = "link";
        Uri parse = Uri.parse(str);
        try {
            Set queryParameterNames = parse.getQueryParameterNames();
            if (queryParameterNames.contains(str2)) {
                return parse.getQueryParameter(str2);
            }
            if (queryParameterNames.contains(str3)) {
                return Uri.parse(parse.getQueryParameter(str3)).getQueryParameter(str2);
            }
        } catch (UnsupportedOperationException unused) {
            return null;
        }
    }
}
