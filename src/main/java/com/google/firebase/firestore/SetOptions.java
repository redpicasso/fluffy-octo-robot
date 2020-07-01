package com.google.firebase.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import com.google.common.base.Preconditions;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.firestore.model.mutation.FieldMask;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class SetOptions {
    private static final SetOptions MERGE_ALL_FIELDS = new SetOptions(true, null);
    static final SetOptions OVERWRITE = new SetOptions(false, null);
    @Nullable
    private final FieldMask fieldMask;
    private final boolean merge;

    private SetOptions(boolean z, @Nullable FieldMask fieldMask) {
        boolean z2 = fieldMask == null || z;
        Preconditions.checkArgument(z2, "Cannot specify a fieldMask for non-merge sets()");
        this.merge = z;
        this.fieldMask = fieldMask;
    }

    boolean isMerge() {
        return this.merge;
    }

    @Nullable
    @RestrictTo({Scope.LIBRARY_GROUP})
    public FieldMask getFieldMask() {
        return this.fieldMask;
    }

    @PublicApi
    @NonNull
    public static SetOptions merge() {
        return MERGE_ALL_FIELDS;
    }

    @PublicApi
    @NonNull
    public static SetOptions mergeFields(@NonNull List<String> list) {
        Set hashSet = new HashSet();
        for (String fromDotSeparatedPath : list) {
            hashSet.add(FieldPath.fromDotSeparatedPath(fromDotSeparatedPath).getInternalPath());
        }
        return new SetOptions(true, FieldMask.fromSet(hashSet));
    }

    @PublicApi
    @NonNull
    public static SetOptions mergeFields(String... strArr) {
        Set hashSet = new HashSet();
        for (String fromDotSeparatedPath : strArr) {
            hashSet.add(FieldPath.fromDotSeparatedPath(fromDotSeparatedPath).getInternalPath());
        }
        return new SetOptions(true, FieldMask.fromSet(hashSet));
    }

    @PublicApi
    @NonNull
    public static SetOptions mergeFieldPaths(@NonNull List<FieldPath> list) {
        Set hashSet = new HashSet();
        for (FieldPath internalPath : list) {
            hashSet.add(internalPath.getInternalPath());
        }
        return new SetOptions(true, FieldMask.fromSet(hashSet));
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SetOptions setOptions = (SetOptions) obj;
        if (this.merge != setOptions.merge) {
            return false;
        }
        FieldMask fieldMask = this.fieldMask;
        if (fieldMask != null) {
            z = fieldMask.equals(setOptions.fieldMask);
        } else if (setOptions.fieldMask != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i = this.merge * 31;
        FieldMask fieldMask = this.fieldMask;
        return i + (fieldMask != null ? fieldMask.hashCode() : 0);
    }
}
