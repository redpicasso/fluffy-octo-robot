package com.google.firebase.auth.api.internal;

import java.util.Locale;

public final class zzem {
    public static String zzem() {
        Locale locale = Locale.getDefault();
        StringBuilder stringBuilder = new StringBuilder();
        zza(stringBuilder, locale);
        if (!locale.equals(Locale.US)) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            zza(stringBuilder, Locale.US);
        }
        return stringBuilder.toString();
    }

    private static void zza(StringBuilder stringBuilder, Locale locale) {
        String language = locale.getLanguage();
        if (language != null) {
            stringBuilder.append(language);
            String country = locale.getCountry();
            if (country != null) {
                stringBuilder.append("-");
                stringBuilder.append(country);
            }
        }
    }
}
