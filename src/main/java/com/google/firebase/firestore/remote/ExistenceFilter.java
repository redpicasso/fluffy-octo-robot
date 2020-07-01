package com.google.firebase.firestore.remote;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class ExistenceFilter {
    private final int count;

    public ExistenceFilter(int i) {
        this.count = i;
    }

    public int getCount() {
        return this.count;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ExistenceFilter{count=");
        stringBuilder.append(this.count);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
