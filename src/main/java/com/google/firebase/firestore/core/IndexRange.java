package com.google.firebase.firestore.core;

import androidx.annotation.Nullable;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.util.Assert;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class IndexRange {
    @Nullable
    private final FieldValue end;
    private final FieldPath fieldPath;
    @Nullable
    private final FieldValue start;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class Builder {
        private FieldValue end;
        private FieldPath fieldPath;
        private FieldValue start;

        public Builder setFieldPath(FieldPath fieldPath) {
            this.fieldPath = fieldPath;
            return this;
        }

        public Builder setStart(FieldValue fieldValue) {
            this.start = fieldValue;
            return this;
        }

        public Builder setEnd(FieldValue fieldValue) {
            this.end = fieldValue;
            return this;
        }

        public IndexRange build() {
            Assert.hardAssert(this.fieldPath != null, "Field path must be specified", new Object[0]);
            return new IndexRange(this);
        }
    }

    private IndexRange(Builder builder) {
        this.fieldPath = builder.fieldPath;
        this.start = builder.start;
        this.end = builder.end;
    }

    public FieldPath getFieldPath() {
        return this.fieldPath;
    }

    @Nullable
    public FieldValue getStart() {
        return this.start;
    }

    @Nullable
    public FieldValue getEnd() {
        return this.end;
    }

    public static Builder builder() {
        return new Builder();
    }
}
