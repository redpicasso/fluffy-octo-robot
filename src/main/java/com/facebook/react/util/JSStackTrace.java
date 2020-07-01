package com.facebook.react.util;

import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.devsupport.StackTraceHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSStackTrace {
    private static final Pattern FILE_ID_PATTERN = Pattern.compile("\\b((?:seg-\\d+(?:_\\d+)?|\\d+)\\.js)");

    public static String format(String str, ReadableArray readableArray) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.append(", stack:\n");
        for (int i = 0; i < readableArray.size(); i++) {
            ReadableMap map = readableArray.getMap(i);
            stringBuilder.append(map.getString("methodName"));
            stringBuilder.append("@");
            stringBuilder.append(parseFileId(map));
            String str2 = StackTraceHelper.LINE_NUMBER_KEY;
            if (map.hasKey(str2) && !map.isNull(str2) && map.getType(str2) == ReadableType.Number) {
                stringBuilder.append(map.getInt(str2));
            } else {
                stringBuilder.append(-1);
            }
            str2 = StackTraceHelper.COLUMN_KEY;
            if (map.hasKey(str2) && !map.isNull(str2) && map.getType(str2) == ReadableType.Number) {
                stringBuilder.append(":");
                stringBuilder.append(map.getInt(str2));
            }
            stringBuilder.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
        }
        return stringBuilder.toString();
    }

    private static String parseFileId(ReadableMap readableMap) {
        String str = UriUtil.LOCAL_FILE_SCHEME;
        if (readableMap.hasKey(str) && !readableMap.isNull(str) && readableMap.getType(str) == ReadableType.String) {
            Matcher matcher = FILE_ID_PATTERN.matcher(readableMap.getString(str));
            if (matcher.find()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(matcher.group(1));
                stringBuilder.append(":");
                return stringBuilder.toString();
            }
        }
        return "";
    }
}
