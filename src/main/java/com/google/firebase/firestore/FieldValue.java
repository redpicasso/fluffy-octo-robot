package com.google.firebase.firestore;

import androidx.annotation.NonNull;
import com.google.firebase.annotations.PublicApi;
import java.util.Arrays;
import java.util.List;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public abstract class FieldValue {
    private static final DeleteFieldValue DELETE_INSTANCE = new DeleteFieldValue();
    private static final ServerTimestampFieldValue SERVER_TIMESTAMP_INSTANCE = new ServerTimestampFieldValue();

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class ArrayRemoveFieldValue extends FieldValue {
        private final List<Object> elements;

        String getMethodName() {
            return "FieldValue.arrayRemove";
        }

        ArrayRemoveFieldValue(List<Object> list) {
            this.elements = list;
        }

        List<Object> getElements() {
            return this.elements;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class ArrayUnionFieldValue extends FieldValue {
        private final List<Object> elements;

        String getMethodName() {
            return "FieldValue.arrayUnion";
        }

        ArrayUnionFieldValue(List<Object> list) {
            this.elements = list;
        }

        List<Object> getElements() {
            return this.elements;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class DeleteFieldValue extends FieldValue {
        String getMethodName() {
            return "FieldValue.delete";
        }

        DeleteFieldValue() {
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class NumericIncrementFieldValue extends FieldValue {
        private final Number operand;

        String getMethodName() {
            return "FieldValue.increment";
        }

        NumericIncrementFieldValue(Number number) {
            this.operand = number;
        }

        Number getOperand() {
            return this.operand;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class ServerTimestampFieldValue extends FieldValue {
        String getMethodName() {
            return "FieldValue.serverTimestamp";
        }

        ServerTimestampFieldValue() {
        }
    }

    abstract String getMethodName();

    FieldValue() {
    }

    @PublicApi
    @NonNull
    public static FieldValue delete() {
        return DELETE_INSTANCE;
    }

    @PublicApi
    @NonNull
    public static FieldValue serverTimestamp() {
        return SERVER_TIMESTAMP_INSTANCE;
    }

    @PublicApi
    @NonNull
    public static FieldValue arrayUnion(@NonNull Object... objArr) {
        return new ArrayUnionFieldValue(Arrays.asList(objArr));
    }

    @PublicApi
    @NonNull
    public static FieldValue arrayRemove(@NonNull Object... objArr) {
        return new ArrayRemoveFieldValue(Arrays.asList(objArr));
    }

    @PublicApi
    @NonNull
    public static FieldValue increment(long j) {
        return new NumericIncrementFieldValue(Long.valueOf(j));
    }

    @PublicApi
    @NonNull
    public static FieldValue increment(double d) {
        return new NumericIncrementFieldValue(Double.valueOf(d));
    }
}
