package com.drew.lang;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public final class StringUtil {
    @NotNull
    public static String join(@NotNull Iterable<? extends CharSequence> iterable, @NotNull String str) {
        int length = str.length();
        Iterator it = iterable.iterator();
        int i = 0;
        if (it.hasNext()) {
            i = 0 + (((CharSequence) it.next()).length() + length);
        }
        StringBuilder stringBuilder = new StringBuilder(i);
        Iterator it2 = iterable.iterator();
        if (it2.hasNext()) {
            stringBuilder.append((CharSequence) it2.next());
            while (it2.hasNext()) {
                stringBuilder.append(str);
                stringBuilder.append((CharSequence) it2.next());
            }
        }
        return stringBuilder.toString();
    }

    @NotNull
    public static <T extends CharSequence> String join(@NotNull T[] tArr, @NotNull String str) {
        int length = str.length();
        int i = 0;
        for (CharSequence length2 : tArr) {
            i += length2.length() + length;
        }
        StringBuilder stringBuilder = new StringBuilder(i);
        Object obj = 1;
        for (CharSequence length22 : tArr) {
            if (obj == null) {
                stringBuilder.append(str);
            } else {
                obj = null;
            }
            stringBuilder.append(length22);
        }
        return stringBuilder.toString();
    }

    @NotNull
    public static String fromStream(@NotNull InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return stringBuilder.toString();
            }
            stringBuilder.append(readLine);
        }
    }

    public static int compare(@Nullable String str, @Nullable String str2) {
        Object obj = str == null ? 1 : null;
        Object obj2 = str2 == null ? 1 : null;
        if (obj != null && obj2 != null) {
            return 0;
        }
        if (obj != null) {
            return -1;
        }
        return obj2 != null ? 1 : str.compareTo(str2);
    }

    @NotNull
    public static String urlEncode(@NotNull String str) {
        return str.replace(" ", "%20");
    }
}
