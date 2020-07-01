package com.google.firebase.firestore;

import androidx.annotation.NonNull;
import com.google.common.base.Preconditions;
import com.google.firebase.annotations.PublicApi;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class FieldPath {
    private static final FieldPath DOCUMENT_ID_INSTANCE = new FieldPath(com.google.firebase.firestore.model.FieldPath.KEY_PATH);
    private static final Pattern RESERVED = Pattern.compile("[~*/\\[\\]]");
    private final com.google.firebase.firestore.model.FieldPath internalPath;

    private FieldPath(List<String> list) {
        this.internalPath = com.google.firebase.firestore.model.FieldPath.fromSegments(list);
    }

    private FieldPath(com.google.firebase.firestore.model.FieldPath fieldPath) {
        this.internalPath = fieldPath;
    }

    com.google.firebase.firestore.model.FieldPath getInternalPath() {
        return this.internalPath;
    }

    @PublicApi
    public static FieldPath of(@NonNull String... strArr) {
        Preconditions.checkArgument(strArr.length > 0, "Invalid field path. Provided path must not be empty.");
        int i = 0;
        while (i < strArr.length) {
            boolean z = (strArr[i] == null || strArr[i].isEmpty()) ? false : true;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid field name at argument ");
            i++;
            stringBuilder.append(i);
            stringBuilder.append(". Field names must not be null or empty.");
            Preconditions.checkArgument(z, stringBuilder.toString());
        }
        return new FieldPath(Arrays.asList(strArr));
    }

    @PublicApi
    @NonNull
    public static FieldPath documentId() {
        return DOCUMENT_ID_INSTANCE;
    }

    static FieldPath fromDotSeparatedPath(@NonNull String str) {
        Preconditions.checkNotNull(str, "Provided field path must not be null.");
        boolean find = RESERVED.matcher(str).find() ^ 1;
        StringBuilder stringBuilder = new StringBuilder();
        String str2 = "Invalid field path (";
        stringBuilder.append(str2);
        stringBuilder.append(str);
        stringBuilder.append("). Paths must not contain '~', '*', '/', '[', or ']'");
        Preconditions.checkArgument(find, stringBuilder.toString());
        try {
            str = of(str.split("\\.", -1));
            return str;
        } catch (IllegalArgumentException unused) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(str);
            stringBuilder.append("). Paths must not be empty, begin with '.', end with '.', or contain '..'");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public String toString() {
        return this.internalPath.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.internalPath.equals(((FieldPath) obj).internalPath);
    }

    public int hashCode() {
        return this.internalPath.hashCode();
    }
}
