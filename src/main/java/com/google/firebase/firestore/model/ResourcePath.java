package com.google.firebase.firestore.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class ResourcePath extends BasePath<ResourcePath> {
    public static final ResourcePath EMPTY = new ResourcePath(Collections.emptyList());

    private ResourcePath(List<String> list) {
        super(list);
    }

    ResourcePath createPathWithSegments(List<String> list) {
        return new ResourcePath(list);
    }

    public static ResourcePath fromSegments(List<String> list) {
        return list.isEmpty() ? EMPTY : new ResourcePath(list);
    }

    public static ResourcePath fromString(String str) {
        if (str.contains("//")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid path (");
            stringBuilder.append(str);
            stringBuilder.append("). Paths must not contain // in them.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        String[] split = str.split("/");
        List arrayList = new ArrayList(split.length);
        for (String str2 : split) {
            if (!str2.isEmpty()) {
                arrayList.add(str2);
            }
        }
        return new ResourcePath(arrayList);
    }

    public String canonicalString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.segments.size(); i++) {
            if (i > 0) {
                stringBuilder.append("/");
            }
            stringBuilder.append((String) this.segments.get(i));
        }
        return stringBuilder.toString();
    }
}
