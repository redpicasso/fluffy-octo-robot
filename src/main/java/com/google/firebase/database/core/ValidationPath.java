package com.google.firebase.database.core;

import com.google.common.base.Ascii;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ValidationPath {
    public static final int MAX_PATH_DEPTH = 32;
    public static final int MAX_PATH_LENGTH_BYTES = 768;
    private int byteLength;
    private final List<String> parts = new ArrayList();

    private ValidationPath(Path path) throws DatabaseException {
        int i = 0;
        this.byteLength = 0;
        Iterator it = path.iterator();
        while (it.hasNext()) {
            this.parts.add(((ChildKey) it.next()).asString());
        }
        this.byteLength = Math.max(1, this.parts.size());
        while (i < this.parts.size()) {
            this.byteLength += utf8Bytes((CharSequence) this.parts.get(i));
            i++;
        }
        checkValid();
    }

    public static void validateWithObject(Path path, Object obj) throws DatabaseException {
        new ValidationPath(path).withObject(obj);
    }

    private void withObject(Object obj) throws DatabaseException {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            for (String str : map.keySet()) {
                if (!str.startsWith(".")) {
                    push(str);
                    withObject(map.get(str));
                    pop();
                }
            }
            return;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            for (int i = 0; i < list.size(); i++) {
                push(Integer.toString(i));
                withObject(list.get(i));
                pop();
            }
        }
    }

    private void push(String str) throws DatabaseException {
        if (this.parts.size() > 0) {
            this.byteLength++;
        }
        this.parts.add(str);
        this.byteLength += utf8Bytes(str);
        checkValid();
    }

    private String pop() {
        List list = this.parts;
        String str = (String) list.remove(list.size() - 1);
        this.byteLength -= utf8Bytes(str);
        if (this.parts.size() > 0) {
            this.byteLength--;
        }
        return str;
    }

    private void checkValid() throws DatabaseException {
        StringBuilder stringBuilder;
        if (this.byteLength > 768) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Data has a key path longer than 768 bytes (");
            stringBuilder.append(this.byteLength);
            stringBuilder.append(").");
            throw new DatabaseException(stringBuilder.toString());
        } else if (this.parts.size() > 32) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Path specified exceeds the maximum depth that can be written (32) or object contains a cycle ");
            stringBuilder.append(toErrorString());
            throw new DatabaseException(stringBuilder.toString());
        }
    }

    private String toErrorString() {
        if (this.parts.size() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("in path '");
        stringBuilder.append(joinStringList("/", this.parts));
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    private static String joinStringList(String str, List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                stringBuilder.append(str);
            }
            stringBuilder.append((String) list.get(i));
        }
        return stringBuilder.toString();
    }

    private static int utf8Bytes(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt <= Ascii.MAX) {
                i2++;
            } else if (charAt <= 2047) {
                i2 += 2;
            } else if (Character.isHighSurrogate(charAt)) {
                i2 += 4;
                i++;
            } else {
                i2 += 3;
            }
            i++;
        }
        return i2;
    }
}
