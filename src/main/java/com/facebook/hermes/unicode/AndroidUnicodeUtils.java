package com.facebook.hermes.unicode;

import com.facebook.proguard.annotations.DoNotStrip;
import java.text.Collator;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;

@DoNotStrip
public class AndroidUnicodeUtils {
    @DoNotStrip
    public static int localeCompare(String str, String str2) {
        return Collator.getInstance().compare(str, str2);
    }

    @DoNotStrip
    public static String dateFormat(double d, boolean z, boolean z2) {
        DateFormat dateTimeInstance;
        if (z && z2) {
            dateTimeInstance = DateFormat.getDateTimeInstance(2, 2);
        } else if (z) {
            dateTimeInstance = DateFormat.getDateInstance(2);
        } else if (z2) {
            dateTimeInstance = DateFormat.getTimeInstance(2);
        } else {
            throw new RuntimeException("Bad dateFormat configuration");
        }
        return dateTimeInstance.format(Long.valueOf((long) d)).toString();
    }

    @DoNotStrip
    public static String convertToCase(String str, int i, boolean z) {
        Locale locale = z ? Locale.getDefault() : Locale.ENGLISH;
        if (i == 0) {
            return str.toUpperCase(locale);
        }
        if (i == 1) {
            return str.toLowerCase(locale);
        }
        throw new RuntimeException("Invalid target case");
    }

    @DoNotStrip
    public static String normalize(String str, int i) {
        if (i == 0) {
            return Normalizer.normalize(str, Form.NFC);
        }
        if (i == 1) {
            return Normalizer.normalize(str, Form.NFD);
        }
        if (i == 2) {
            return Normalizer.normalize(str, Form.NFKC);
        }
        if (i == 3) {
            return Normalizer.normalize(str, Form.NFKD);
        }
        throw new RuntimeException("Invalid form");
    }
}
