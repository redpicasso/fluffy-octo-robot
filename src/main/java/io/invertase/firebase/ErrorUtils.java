package io.invertase.firebase;

public class ErrorUtils {
    public static String getMessageWithService(String str, String str2, String str3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(": ");
        stringBuilder.append(str);
        stringBuilder.append(" (");
        stringBuilder.append(str3.toLowerCase());
        stringBuilder.append(").");
        return stringBuilder.toString();
    }

    public static String getCodeWithService(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str.toLowerCase());
        stringBuilder.append("/");
        stringBuilder.append(str2.toLowerCase());
        return stringBuilder.toString();
    }
}
