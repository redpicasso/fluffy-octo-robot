package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.OrderBy.Direction;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.util.Assert;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Bound {
    private final boolean before;
    private final List<FieldValue> position;

    public Bound(List<FieldValue> list, boolean z) {
        this.position = list;
        this.before = z;
    }

    public List<FieldValue> getPosition() {
        return this.position;
    }

    public boolean isBefore() {
        return this.before;
    }

    public String canonicalString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.before) {
            stringBuilder.append("b:");
        } else {
            stringBuilder.append("a:");
        }
        for (FieldValue fieldValue : this.position) {
            stringBuilder.append(fieldValue.toString());
        }
        return stringBuilder.toString();
    }

    public boolean sortsBeforeDocument(List<OrderBy> list, Document document) {
        Assert.hardAssert(this.position.size() <= list.size(), "Bound has more components than query's orderBy", new Object[0]);
        int i = 0;
        for (int i2 = 0; i2 < this.position.size(); i2++) {
            int compareTo;
            OrderBy orderBy = (OrderBy) list.get(i2);
            FieldValue fieldValue = (FieldValue) this.position.get(i2);
            if (orderBy.field.equals(FieldPath.KEY_PATH)) {
                Object value = fieldValue.value();
                Assert.hardAssert(value instanceof DocumentKey, "Bound has a non-key value where the key path is being used %s", fieldValue);
                compareTo = ((DocumentKey) value).compareTo(document.getKey());
            } else {
                FieldValue field = document.getField(orderBy.getField());
                Assert.hardAssert(field != null, "Field should exist since document matched the orderBy already.", new Object[0]);
                compareTo = fieldValue.compareTo(field);
            }
            if (orderBy.getDirection().equals(Direction.DESCENDING)) {
                compareTo *= -1;
            }
            i = compareTo;
            if (i != 0) {
                break;
            }
        }
        if (this.before) {
            if (i <= 0) {
                return true;
            }
        } else if (i < 0) {
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Bound bound = (Bound) obj;
        if (!(this.before == bound.before && this.position.equals(bound.position))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (this.before * 31) + this.position.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bound{before=");
        stringBuilder.append(this.before);
        stringBuilder.append(", position=");
        stringBuilder.append(this.position);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
