package com.google.android.gms.internal.firebase_ml;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class zzhk implements Serializable {
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    private static final Pattern zzzm = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})([Tt](\\d{2}):(\\d{2}):(\\d{2})(\\.\\d+)?)?([Zz]|([+-])(\\d{2}):(\\d{2}))?");
    private final long value;
    private final boolean zzzn;
    private final int zzzo;

    public zzhk(long j) {
        this(false, 0, null);
    }

    private zzhk(boolean z, long j, Integer num) {
        this.zzzn = z;
        this.value = j;
        int offset = z ? 0 : num == null ? TimeZone.getDefault().getOffset(j) / 60000 : num.intValue();
        this.zzzo = offset;
    }

    public final String zzhe() {
        StringBuilder stringBuilder = new StringBuilder();
        Calendar gregorianCalendar = new GregorianCalendar(GMT);
        gregorianCalendar.setTimeInMillis(this.value + (((long) this.zzzo) * 60000));
        zza(stringBuilder, gregorianCalendar.get(1), 4);
        stringBuilder.append('-');
        zza(stringBuilder, gregorianCalendar.get(2) + 1, 2);
        stringBuilder.append('-');
        zza(stringBuilder, gregorianCalendar.get(5), 2);
        if (!this.zzzn) {
            stringBuilder.append('T');
            zza(stringBuilder, gregorianCalendar.get(11), 2);
            stringBuilder.append(':');
            zza(stringBuilder, gregorianCalendar.get(12), 2);
            stringBuilder.append(':');
            zza(stringBuilder, gregorianCalendar.get(13), 2);
            if (gregorianCalendar.isSet(14)) {
                stringBuilder.append('.');
                zza(stringBuilder, gregorianCalendar.get(14), 3);
            }
            int i = this.zzzo;
            if (i == 0) {
                stringBuilder.append('Z');
            } else {
                if (i > 0) {
                    stringBuilder.append('+');
                } else {
                    stringBuilder.append('-');
                    i = -i;
                }
                int i2 = i / 60;
                i %= 60;
                zza(stringBuilder, i2, 2);
                stringBuilder.append(':');
                zza(stringBuilder, i, 2);
            }
        }
        return stringBuilder.toString();
    }

    public final String toString() {
        return zzhe();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzhk)) {
            return false;
        }
        zzhk zzhk = (zzhk) obj;
        return this.zzzn == zzhk.zzzn && this.value == zzhk.value && this.zzzo == zzhk.zzzo;
    }

    public final int hashCode() {
        long[] jArr = new long[3];
        jArr[0] = this.value;
        jArr[1] = this.zzzn ? 1 : 0;
        jArr[2] = (long) this.zzzo;
        return Arrays.hashCode(jArr);
    }

    public static zzhk zzap(String str) throws NumberFormatException {
        Matcher matcher = zzzm.matcher(str);
        String str2;
        String valueOf;
        if (matcher.matches()) {
            int parseInt = Integer.parseInt(matcher.group(1));
            int parseInt2 = Integer.parseInt(matcher.group(2)) - 1;
            int parseInt3 = Integer.parseInt(matcher.group(3));
            int i = matcher.group(4) != null ? 1 : 0;
            String group = matcher.group(9);
            Object obj = group != null ? 1 : null;
            Integer num = null;
            if (obj == null || i != 0) {
                int parseInt4;
                int i2;
                int parseInt5;
                int i3;
                int i4;
                if (i != 0) {
                    int parseInt6 = Integer.parseInt(matcher.group(5));
                    int parseInt7 = Integer.parseInt(matcher.group(6));
                    parseInt4 = Integer.parseInt(matcher.group(7));
                    if (matcher.group(8) != null) {
                        i2 = i;
                        parseInt5 = (int) (((double) ((float) Integer.parseInt(matcher.group(8).substring(1)))) / Math.pow(10.0d, (double) (matcher.group(8).substring(1).length() - 3)));
                        i3 = parseInt7;
                        i4 = parseInt4;
                    } else {
                        i2 = i;
                        i3 = parseInt7;
                        i4 = parseInt4;
                        parseInt5 = 0;
                    }
                    parseInt4 = parseInt6;
                } else {
                    i2 = i;
                    parseInt4 = 0;
                    i3 = 0;
                    i4 = 0;
                    parseInt5 = 0;
                }
                Calendar gregorianCalendar = new GregorianCalendar(GMT);
                gregorianCalendar.set(parseInt, parseInt2, parseInt3, parseInt4, i3, i4);
                gregorianCalendar.set(14, parseInt5);
                long timeInMillis = gregorianCalendar.getTimeInMillis();
                if (!(i2 == 0 || obj == null)) {
                    int i5;
                    if (Character.toUpperCase(group.charAt(0)) == 'Z') {
                        i5 = 0;
                    } else {
                        int parseInt8 = (Integer.parseInt(matcher.group(11)) * 60) + Integer.parseInt(matcher.group(12));
                        parseInt5 = matcher.group(10).charAt(0) == '-' ? -parseInt8 : parseInt8;
                        timeInMillis -= ((long) parseInt5) * 60000;
                        i5 = parseInt5;
                    }
                    num = Integer.valueOf(i5);
                }
                return new zzhk(i2 ^ 1, timeInMillis, num);
            }
            str2 = "Invalid date/time format, cannot specify time zone shift without specifying time: ";
            valueOf = String.valueOf(str);
            throw new NumberFormatException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
        str2 = "Invalid date/time format: ";
        valueOf = String.valueOf(str);
        throw new NumberFormatException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
    }

    private static void zza(StringBuilder stringBuilder, int i, int i2) {
        if (i < 0) {
            stringBuilder.append('-');
            i = -i;
        }
        int i3 = i2;
        i2 = i;
        while (i2 > 0) {
            i2 /= 10;
            i3--;
        }
        for (i2 = 0; i2 < i3; i2++) {
            stringBuilder.append('0');
        }
        if (i != 0) {
            stringBuilder.append(i);
        }
    }
}
