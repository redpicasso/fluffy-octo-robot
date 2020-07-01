package com.google.firebase.database.connection;

import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ConnectionUtils {
    public static List<String> stringToPath(String str) {
        List<String> arrayList = new ArrayList();
        String[] split = str.split("/", -1);
        for (int i = 0; i < split.length; i++) {
            if (!split[i].isEmpty()) {
                arrayList.add(split[i]);
            }
        }
        return arrayList;
    }

    public static String pathToString(List<String> list) {
        String str = "/";
        if (list.isEmpty()) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (String str2 : list) {
            if (obj == null) {
                stringBuilder.append(str);
            }
            obj = null;
            stringBuilder.append(str2);
        }
        return stringBuilder.toString();
    }

    public static Long longFromObject(Object obj) {
        if (obj instanceof Integer) {
            return Long.valueOf((long) ((Integer) obj).intValue());
        }
        return obj instanceof Long ? (Long) obj : null;
    }

    public static void hardAssert(boolean z) {
        hardAssert(z, "", new Object[0]);
    }

    public static void hardAssert(boolean z, String str, Object... objArr) {
        if (!z) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("hardAssert failed: ");
            stringBuilder.append(String.format(str, objArr));
            throw new AssertionError(stringBuilder.toString());
        }
    }
}
