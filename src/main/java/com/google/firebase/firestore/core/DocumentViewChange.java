package com.google.firebase.firestore.core;

import com.google.firebase.firestore.model.Document;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class DocumentViewChange {
    private final Document document;
    private final Type type;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Type {
        REMOVED,
        ADDED,
        MODIFIED,
        METADATA
    }

    public static DocumentViewChange create(Type type, Document document) {
        return new DocumentViewChange(type, document);
    }

    private DocumentViewChange(Type type, Document document) {
        this.type = type;
        this.document = document;
    }

    public Document getDocument() {
        return this.document;
    }

    public Type getType() {
        return this.type;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof DocumentViewChange)) {
            return false;
        }
        DocumentViewChange documentViewChange = (DocumentViewChange) obj;
        if (this.type.equals(documentViewChange.type) && this.document.equals(documentViewChange.document)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return ((1891 + this.type.hashCode()) * 31) + this.document.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DocumentViewChange(");
        stringBuilder.append(this.document);
        stringBuilder.append(",");
        stringBuilder.append(this.type);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
