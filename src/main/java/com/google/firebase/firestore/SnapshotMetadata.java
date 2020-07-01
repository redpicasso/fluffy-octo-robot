package com.google.firebase.firestore;

import com.google.firebase.annotations.PublicApi;
import javax.annotation.Nullable;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class SnapshotMetadata {
    private final boolean hasPendingWrites;
    private final boolean isFromCache;

    SnapshotMetadata(boolean z, boolean z2) {
        this.hasPendingWrites = z;
        this.isFromCache = z2;
    }

    @PublicApi
    public boolean hasPendingWrites() {
        return this.hasPendingWrites;
    }

    @PublicApi
    public boolean isFromCache() {
        return this.isFromCache;
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SnapshotMetadata)) {
            return false;
        }
        SnapshotMetadata snapshotMetadata = (SnapshotMetadata) obj;
        if (!(this.hasPendingWrites == snapshotMetadata.hasPendingWrites && this.isFromCache == snapshotMetadata.isFromCache)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (this.hasPendingWrites * 31) + this.isFromCache;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SnapshotMetadata{hasPendingWrites=");
        stringBuilder.append(this.hasPendingWrites);
        stringBuilder.append(", isFromCache=");
        stringBuilder.append(this.isFromCache);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
