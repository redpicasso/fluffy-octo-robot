package com.google.firebase.firestore.model.mutation;

import androidx.annotation.Nullable;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.value.ArrayValue;
import com.google.firebase.firestore.model.value.FieldValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public abstract class ArrayTransformOperation implements TransformOperation {
    private final List<FieldValue> elements;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class Remove extends ArrayTransformOperation {
        public Remove(List<FieldValue> list) {
            super(list);
        }

        protected ArrayValue apply(FieldValue fieldValue) {
            List coercedFieldValuesArray = ArrayTransformOperation.coercedFieldValuesArray(fieldValue);
            for (FieldValue singleton : getElements()) {
                coercedFieldValuesArray.removeAll(Collections.singleton(singleton));
            }
            return ArrayValue.fromList(coercedFieldValuesArray);
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class Union extends ArrayTransformOperation {
        public Union(List<FieldValue> list) {
            super(list);
        }

        protected ArrayValue apply(FieldValue fieldValue) {
            List coercedFieldValuesArray = ArrayTransformOperation.coercedFieldValuesArray(fieldValue);
            for (FieldValue fieldValue2 : getElements()) {
                if (!coercedFieldValuesArray.contains(fieldValue2)) {
                    coercedFieldValuesArray.add(fieldValue2);
                }
            }
            return ArrayValue.fromList(coercedFieldValuesArray);
        }
    }

    protected abstract ArrayValue apply(FieldValue fieldValue);

    public boolean isIdempotent() {
        return true;
    }

    ArrayTransformOperation(List<FieldValue> list) {
        this.elements = Collections.unmodifiableList(list);
    }

    public List<FieldValue> getElements() {
        return this.elements;
    }

    public FieldValue applyToLocalView(FieldValue fieldValue, Timestamp timestamp) {
        return apply(fieldValue);
    }

    public FieldValue applyToRemoteDocument(FieldValue fieldValue, FieldValue fieldValue2) {
        return apply(fieldValue);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.elements.equals(((ArrayTransformOperation) obj).elements);
    }

    public int hashCode() {
        return (getClass().hashCode() * 31) + this.elements.hashCode();
    }

    static ArrayList<FieldValue> coercedFieldValuesArray(@Nullable FieldValue fieldValue) {
        if (fieldValue instanceof ArrayValue) {
            return new ArrayList(((ArrayValue) fieldValue).getInternalValue());
        }
        return new ArrayList();
    }
}
