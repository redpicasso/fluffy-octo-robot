package com.google.firebase.firestore.core;

import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.util.Assert;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class OrderBy {
    private final Direction direction;
    final FieldPath field;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Direction {
        ASCENDING(1),
        DESCENDING(-1);
        
        private final int comparisonModifier;

        private Direction(int i) {
            this.comparisonModifier = i;
        }

        int getComparisonModifier() {
            return this.comparisonModifier;
        }
    }

    public static OrderBy getInstance(Direction direction, FieldPath fieldPath) {
        return new OrderBy(direction, fieldPath);
    }

    public Direction getDirection() {
        return this.direction;
    }

    public FieldPath getField() {
        return this.field;
    }

    private OrderBy(Direction direction, FieldPath fieldPath) {
        this.direction = direction;
        this.field = fieldPath;
    }

    int compare(Document document, Document document2) {
        int comparisonModifier;
        int compareTo;
        if (this.field.equals(FieldPath.KEY_PATH)) {
            comparisonModifier = this.direction.getComparisonModifier();
            compareTo = document.getKey().compareTo(document2.getKey());
        } else {
            FieldValue field = document.getField(this.field);
            FieldValue field2 = document2.getField(this.field);
            boolean z = (field == null || field2 == null) ? false : true;
            Assert.hardAssert(z, "Trying to compare documents on fields that don't exist.", new Object[0]);
            comparisonModifier = this.direction.getComparisonModifier();
            compareTo = field.compareTo(field2);
        }
        return comparisonModifier * compareTo;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OrderBy)) {
            return false;
        }
        OrderBy orderBy = (OrderBy) obj;
        if (this.direction == orderBy.direction && this.field.equals(orderBy.field)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((899 + this.direction.hashCode()) * 31) + this.field.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.direction == Direction.ASCENDING ? "" : "-");
        stringBuilder.append(this.field.canonicalString());
        return stringBuilder.toString();
    }
}
