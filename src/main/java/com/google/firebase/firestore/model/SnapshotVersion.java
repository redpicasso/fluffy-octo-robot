package com.google.firebase.firestore.model;

import com.google.firebase.Timestamp;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class SnapshotVersion implements Comparable<SnapshotVersion> {
    public static final SnapshotVersion NONE = new SnapshotVersion(new Timestamp(0, 0));
    private final Timestamp timestamp;

    public SnapshotVersion(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public int compareTo(SnapshotVersion snapshotVersion) {
        return this.timestamp.compareTo(snapshotVersion.timestamp);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SnapshotVersion)) {
            return false;
        }
        if (compareTo((SnapshotVersion) obj) != 0) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return getTimestamp().hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SnapshotVersion(seconds=");
        stringBuilder.append(this.timestamp.getSeconds());
        stringBuilder.append(", nanos=");
        stringBuilder.append(this.timestamp.getNanoseconds());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
