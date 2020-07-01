package com.google.android.gms.internal.firebase_ml;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class zzfg {
    private static final Pattern zztz = Pattern.compile("[\\w!#$&.+\\-\\^_]+|[*]");
    private static final Pattern zzua = Pattern.compile("[\\p{ASCII}&&[^\\p{Cntrl} ;/=\\[\\]\\(\\)\\<\\>\\@\\,\\:\\\"\\?\\=]]+");
    private static final Pattern zzub;
    private static final Pattern zzuc;
    private String type = "application";
    private String zzud = "octet-stream";
    private final SortedMap<String, String> zzue = new TreeMap();
    private String zzuf;

    public zzfg(String str) {
        Matcher matcher = zzub.matcher(str);
        zzks.checkArgument(matcher.matches(), "Type must be in the 'maintype/subtype; parameter=value' format");
        Object group = matcher.group(1);
        zzks.checkArgument(zztz.matcher(group).matches(), "Type contains reserved characters");
        this.type = group;
        this.zzuf = null;
        Object group2 = matcher.group(2);
        zzks.checkArgument(zztz.matcher(group2).matches(), "Subtype contains reserved characters");
        this.zzud = group2;
        this.zzuf = null;
        CharSequence group3 = matcher.group(3);
        if (group3 != null) {
            matcher = zzuc.matcher(group3);
            while (matcher.find()) {
                String group4 = matcher.group(1);
                String group5 = matcher.group(3);
                if (group5 == null) {
                    group5 = matcher.group(2);
                }
                zza(group4, group5);
            }
        }
    }

    private final zzfg zza(String str, String str2) {
        if (str2 == null) {
            this.zzuf = null;
            this.zzue.remove(str.toLowerCase(Locale.US));
            return this;
        }
        zzks.checkArgument(zzua.matcher(str).matches(), "Name contains reserved characters");
        this.zzuf = null;
        this.zzue.put(str.toLowerCase(Locale.US), str2);
        return this;
    }

    static boolean zzaf(String str) {
        return zzua.matcher(str).matches();
    }

    public final String zzew() {
        String str = this.zzuf;
        if (str != null) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.type);
        stringBuilder.append('/');
        stringBuilder.append(this.zzud);
        SortedMap sortedMap = this.zzue;
        if (sortedMap != null) {
            for (Entry entry : sortedMap.entrySet()) {
                String str2 = (String) entry.getValue();
                stringBuilder.append("; ");
                stringBuilder.append((String) entry.getKey());
                stringBuilder.append("=");
                if (!zzaf(str2)) {
                    String replace = str2.replace("\\", "\\\\");
                    str2 = "\"";
                    replace = replace.replace(str2, "\\\"");
                    StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(replace).length() + 2);
                    stringBuilder2.append(str2);
                    stringBuilder2.append(replace);
                    stringBuilder2.append(str2);
                    str2 = stringBuilder2.toString();
                }
                stringBuilder.append(str2);
            }
        }
        this.zzuf = stringBuilder.toString();
        return this.zzuf;
    }

    public final String toString() {
        return zzew();
    }

    private final boolean zza(zzfg zzfg) {
        return zzfg != null && this.type.equalsIgnoreCase(zzfg.type) && this.zzud.equalsIgnoreCase(zzfg.zzud);
    }

    public static boolean zzb(String str, String str2) {
        return str2 != null && new zzfg(str).zza(new zzfg(str2));
    }

    public final zzfg zza(Charset charset) {
        zza("charset", charset == null ? null : charset.name());
        return this;
    }

    public final Charset zzey() {
        String str = (String) this.zzue.get("charset".toLowerCase(Locale.US));
        if (str == null) {
            return null;
        }
        return Charset.forName(str);
    }

    public final int hashCode() {
        return zzew().hashCode();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzfg)) {
            return false;
        }
        zzfg zzfg = (zzfg) obj;
        if (zza(zzfg) && this.zzue.equals(zzfg.zzue)) {
            return true;
        }
        return false;
    }

    static {
        String str = "[^\\s/=;\"]+";
        String str2 = ";.*";
        StringBuilder stringBuilder = new StringBuilder(((str.length() + 14) + str.length()) + str2.length());
        stringBuilder.append("\\s*(");
        stringBuilder.append(str);
        stringBuilder.append(")/(");
        stringBuilder.append(str);
        stringBuilder.append(")\\s*(");
        stringBuilder.append(str2);
        stringBuilder.append(")?");
        zzub = Pattern.compile(stringBuilder.toString(), 32);
        String str3 = "\"([^\"]*)\"";
        String str4 = "[^\\s;\"]*";
        StringBuilder stringBuilder2 = new StringBuilder((str3.length() + 1) + str4.length());
        stringBuilder2.append(str3);
        stringBuilder2.append("|");
        stringBuilder2.append(str4);
        str3 = stringBuilder2.toString();
        stringBuilder = new StringBuilder((str.length() + 12) + String.valueOf(str3).length());
        stringBuilder.append("\\s*;\\s*(");
        stringBuilder.append(str);
        stringBuilder.append(")=(");
        stringBuilder.append(str3);
        stringBuilder.append(")");
        zzuc = Pattern.compile(stringBuilder.toString());
    }
}
