package com.google.android.gms.internal.firebase_ml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class zzeo {
    private static final zzeo zzti = new zzeo();
    private final String zztj;

    zzeo() {
        String property = System.getProperty("java.version");
        if (property.startsWith("9")) {
            property = "9.0.0";
        } else {
            property = zzq(property);
        }
        this(property, zzkz.OS_NAME.value(), zzkz.OS_VERSION.value(), zzeh.VERSION);
    }

    private zzeo(String str, String str2, String str3, String str4) {
        StringBuilder stringBuilder = new StringBuilder("java/");
        stringBuilder.append(zzq(str));
        stringBuilder.append(" http-google-%s/");
        stringBuilder.append(zzq(str4));
        if (!(str2 == null || str3 == null)) {
            stringBuilder.append(" ");
            stringBuilder.append(zzp(str2));
            stringBuilder.append("/");
            stringBuilder.append(zzq(str3));
        }
        this.zztj = stringBuilder.toString();
    }

    final String zzo(String str) {
        return String.format(this.zztj, new Object[]{zzp(str)});
    }

    private static zzeo zzeq() {
        return zzti;
    }

    private static String zzp(String str) {
        return str.toLowerCase().replaceAll("[^\\w\\d\\-]", "-");
    }

    private static String zzq(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = Pattern.compile("(\\d+\\.\\d+\\.\\d+).*").matcher(str);
        if (matcher.find()) {
            str = matcher.group(1);
        }
        return str;
    }
}
