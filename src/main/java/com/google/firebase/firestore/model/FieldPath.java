package com.google.firebase.firestore.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class FieldPath extends BasePath<FieldPath> {
    public static final FieldPath EMPTY_PATH = new FieldPath(Collections.emptyList());
    public static final FieldPath KEY_PATH = fromSingleSegment(DocumentKey.KEY_FIELD_NAME);

    private FieldPath(List<String> list) {
        super(list);
    }

    public static FieldPath fromSingleSegment(String str) {
        return new FieldPath(Collections.singletonList(str));
    }

    public static FieldPath fromSegments(List<String> list) {
        return list.isEmpty() ? EMPTY_PATH : new FieldPath(list);
    }

    FieldPath createPathWithSegments(List<String> list) {
        return new FieldPath(list);
    }

    public static FieldPath fromServerFormat(String str) {
        List arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int i2 = 0;
        while (true) {
            String str2 = "). Paths must not be empty, begin with '.', end with '.', or contain '..'";
            String str3 = "Invalid field path (";
            String stringBuilder2;
            if (i < str.length()) {
                char charAt = str.charAt(i);
                if (charAt == '\\') {
                    i++;
                    if (i != str.length()) {
                        stringBuilder.append(str.charAt(i));
                    } else {
                        throw new IllegalArgumentException("Trailing escape character is not allowed");
                    }
                } else if (charAt == '.') {
                    if (i2 == 0) {
                        stringBuilder2 = stringBuilder.toString();
                        if (stringBuilder2.isEmpty()) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(str3);
                            stringBuilder.append(str);
                            stringBuilder.append(str2);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        StringBuilder stringBuilder3 = new StringBuilder();
                        arrayList.add(stringBuilder2);
                        stringBuilder = stringBuilder3;
                    } else {
                        stringBuilder.append(charAt);
                    }
                } else if (charAt == '`') {
                    i2 ^= 1;
                } else {
                    stringBuilder.append(charAt);
                }
                i++;
            } else {
                stringBuilder2 = stringBuilder.toString();
                if (stringBuilder2.isEmpty()) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str3);
                    stringBuilder.append(str);
                    stringBuilder.append(str2);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                arrayList.add(stringBuilder2);
                return new FieldPath(arrayList);
            }
        }
    }

    private static boolean isValidIdentifier(String str) {
        if (str.isEmpty()) {
            return false;
        }
        char charAt = str.charAt(0);
        if (charAt != '_' && ((charAt < 'a' || charAt > 'z') && (charAt < 'A' || charAt > 'Z'))) {
            return false;
        }
        for (int i = 1; i < str.length(); i++) {
            char charAt2 = str.charAt(i);
            if (charAt2 != '_' && ((charAt2 < 'a' || charAt2 > 'z') && ((charAt2 < 'A' || charAt2 > 'Z') && (charAt2 < '0' || charAt2 > '9')))) {
                return false;
            }
        }
        return true;
    }

    public String canonicalString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.segments.size(); i++) {
            if (i > 0) {
                stringBuilder.append(".");
            }
            String replace = ((String) this.segments.get(i)).replace("\\", "\\\\").replace("`", "\\`");
            if (!isValidIdentifier(replace)) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append('`');
                stringBuilder2.append(replace);
                stringBuilder2.append('`');
                replace = stringBuilder2.toString();
            }
            stringBuilder.append(replace);
        }
        return stringBuilder.toString();
    }

    public boolean isKeyField() {
        return equals(KEY_PATH);
    }
}
