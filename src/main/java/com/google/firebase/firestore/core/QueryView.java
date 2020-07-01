package com.google.firebase.firestore.core;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final class QueryView {
    private final Query query;
    private final int targetId;
    private final View view;

    QueryView(Query query, int i, View view) {
        this.query = query;
        this.targetId = i;
        this.view = view;
    }

    public Query getQuery() {
        return this.query;
    }

    public int getTargetId() {
        return this.targetId;
    }

    public View getView() {
        return this.view;
    }
}
