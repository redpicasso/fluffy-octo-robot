package com.google.android.gms.internal.firebase_ml;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.util.PlatformVersion;
import java.util.Locale;

public final class zznk {
    private static final GmsLogger zzaoz = new GmsLogger("CommonUtils", "");

    public static String zza(@NonNull Context context) {
        try {
            return String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
        } catch (NameNotFoundException e) {
            GmsLogger gmsLogger = zzaoz;
            String valueOf = String.valueOf(e);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 48);
            stringBuilder.append("Exception thrown when trying to get app version ");
            stringBuilder.append(valueOf);
            gmsLogger.e("CommonUtils", stringBuilder.toString());
            return "";
        }
    }

    @NonNull
    static String zza(@NonNull Locale locale) {
        if (PlatformVersion.isAtLeastLollipop()) {
            return locale.toLanguageTag();
        }
        StringBuilder stringBuilder = new StringBuilder(locale.getLanguage());
        String str = "-";
        if (!TextUtils.isEmpty(locale.getCountry())) {
            stringBuilder.append(str);
            stringBuilder.append(locale.getCountry());
        }
        if (!TextUtils.isEmpty(locale.getVariant())) {
            stringBuilder.append(str);
            stringBuilder.append(locale.getVariant());
        }
        return stringBuilder.toString();
    }
}
