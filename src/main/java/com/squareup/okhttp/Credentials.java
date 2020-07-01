package com.squareup.okhttp;

import java.io.UnsupportedEncodingException;
import okio.ByteString;

public final class Credentials {
    private Credentials() {
    }

    public static String basic(String str, String str2) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(":");
            stringBuilder.append(str2);
            str = ByteString.of(stringBuilder.toString().getBytes("ISO-8859-1")).base64();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Basic ");
            stringBuilder2.append(str);
            return stringBuilder2.toString();
        } catch (UnsupportedEncodingException unused) {
            throw new AssertionError();
        }
    }
}
