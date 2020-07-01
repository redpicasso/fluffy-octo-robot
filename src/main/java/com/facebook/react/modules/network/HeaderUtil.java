package com.facebook.react.modules.network;

import com.google.common.base.Ascii;

public class HeaderUtil {
    public static String stripHeaderName(String str) {
        StringBuilder stringBuilder = new StringBuilder(str.length());
        int length = str.length();
        Object obj = null;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt <= ' ' || charAt >= Ascii.MAX) {
                obj = 1;
            } else {
                stringBuilder.append(charAt);
            }
        }
        return obj != null ? stringBuilder.toString() : str;
    }

    public static String stripHeaderValue(String str) {
        StringBuilder stringBuilder = new StringBuilder(str.length());
        int length = str.length();
        Object obj = null;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((charAt <= 31 || charAt >= Ascii.MAX) && charAt != 9) {
                obj = 1;
            } else {
                stringBuilder.append(charAt);
            }
        }
        return obj != null ? stringBuilder.toString() : str;
    }
}
