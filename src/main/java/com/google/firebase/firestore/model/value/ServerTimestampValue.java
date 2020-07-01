package com.google.firebase.firestore.model.value;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.util.Assert;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class ServerTimestampValue extends FieldValue {
    private final Timestamp localWriteTime;
    @Nullable
    private final FieldValue previousValue;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firebase.firestore.model.value.ServerTimestampValue$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$model$value$FieldValueOptions$ServerTimestampBehavior = new int[ServerTimestampBehavior.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$google$firebase$firestore$model$value$FieldValueOptions$ServerTimestampBehavior[com.google.firebase.firestore.model.value.FieldValueOptions.ServerTimestampBehavior.NONE.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.google.firebase.firestore.model.value.FieldValueOptions.ServerTimestampBehavior.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$firestore$model$value$FieldValueOptions$ServerTimestampBehavior = r0;
            r0 = $SwitchMap$com$google$firebase$firestore$model$value$FieldValueOptions$ServerTimestampBehavior;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.firestore.model.value.FieldValueOptions.ServerTimestampBehavior.PREVIOUS;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$firestore$model$value$FieldValueOptions$ServerTimestampBehavior;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.firestore.model.value.FieldValueOptions.ServerTimestampBehavior.ESTIMATE;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$firestore$model$value$FieldValueOptions$ServerTimestampBehavior;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.firestore.model.value.FieldValueOptions.ServerTimestampBehavior.NONE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.model.value.ServerTimestampValue.1.<clinit>():void");
        }
    }

    public int typeOrder() {
        return 3;
    }

    @Nullable
    public Object value() {
        return null;
    }

    public ServerTimestampValue(Timestamp timestamp, @Nullable FieldValue fieldValue) {
        this.localWriteTime = timestamp;
        this.previousValue = fieldValue;
    }

    @Nullable
    public Object value(FieldValueOptions fieldValueOptions) {
        int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$model$value$FieldValueOptions$ServerTimestampBehavior[fieldValueOptions.getServerTimestampBehavior().ordinal()];
        Object obj = null;
        if (i == 1) {
            FieldValue fieldValue = this.previousValue;
            if (fieldValue != null) {
                obj = fieldValue.value(fieldValueOptions);
            }
            return obj;
        } else if (i == 2) {
            return new TimestampValue(this.localWriteTime).value(fieldValueOptions);
        } else {
            if (i == 3) {
                return null;
            }
            throw Assert.fail("Unexpected case for ServerTimestampBehavior: %s", fieldValueOptions.getServerTimestampBehavior().name());
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<ServerTimestamp localTime=");
        stringBuilder.append(this.localWriteTime.toString());
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ServerTimestampValue) && this.localWriteTime.equals(((ServerTimestampValue) obj).localWriteTime);
    }

    public int hashCode() {
        return this.localWriteTime.hashCode();
    }

    public int compareTo(FieldValue fieldValue) {
        if (fieldValue instanceof ServerTimestampValue) {
            return this.localWriteTime.compareTo(((ServerTimestampValue) fieldValue).localWriteTime);
        }
        if (fieldValue instanceof TimestampValue) {
            return 1;
        }
        return defaultCompareTo(fieldValue);
    }
}
