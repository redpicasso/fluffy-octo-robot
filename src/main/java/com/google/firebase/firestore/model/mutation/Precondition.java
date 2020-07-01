package com.google.firebase.firestore.model.mutation;

import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Precondition {
    public static final Precondition NONE = new Precondition(null, null);
    @Nullable
    private final Boolean exists;
    @Nullable
    private final SnapshotVersion updateTime;

    private Precondition(@Nullable SnapshotVersion snapshotVersion, @Nullable Boolean bool) {
        boolean z = snapshotVersion == null || bool == null;
        Assert.hardAssert(z, "Precondition can specify \"exists\" or \"updateTime\" but not both", new Object[0]);
        this.updateTime = snapshotVersion;
        this.exists = bool;
    }

    public static Precondition exists(boolean z) {
        return new Precondition(null, Boolean.valueOf(z));
    }

    public static Precondition updateTime(SnapshotVersion snapshotVersion) {
        return new Precondition(snapshotVersion, null);
    }

    public boolean isNone() {
        return this.updateTime == null && this.exists == null;
    }

    @Nullable
    public SnapshotVersion getUpdateTime() {
        return this.updateTime;
    }

    @Nullable
    public Boolean getExists() {
        return this.exists;
    }

    public boolean isValidFor(@Nullable MaybeDocument maybeDocument) {
        boolean z = true;
        if (this.updateTime != null) {
            if (!((maybeDocument instanceof Document) && maybeDocument.getVersion().equals(this.updateTime))) {
                z = false;
            }
            return z;
        }
        Boolean bool = this.exists;
        if (bool != null) {
            if (bool.booleanValue() != (maybeDocument instanceof Document)) {
                z = false;
            }
            return z;
        }
        Assert.hardAssert(isNone(), "Precondition should be empty", new Object[0]);
        return true;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Precondition precondition = (Precondition) obj;
        SnapshotVersion snapshotVersion = this.updateTime;
        if (!snapshotVersion == null ? snapshotVersion.equals(precondition.updateTime) : precondition.updateTime == null) {
            return false;
        }
        Boolean bool = this.exists;
        if (bool != null) {
            z = bool.equals(precondition.exists);
        } else if (precondition.exists != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        SnapshotVersion snapshotVersion = this.updateTime;
        int i = 0;
        int hashCode = (snapshotVersion != null ? snapshotVersion.hashCode() : 0) * 31;
        Boolean bool = this.exists;
        if (bool != null) {
            i = bool.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        if (isNone()) {
            return "Precondition{<none>}";
        }
        String str = "}";
        StringBuilder stringBuilder;
        if (this.updateTime != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Precondition{updateTime=");
            stringBuilder.append(this.updateTime);
            stringBuilder.append(str);
            return stringBuilder.toString();
        } else if (this.exists != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Precondition{exists=");
            stringBuilder.append(this.exists);
            stringBuilder.append(str);
            return stringBuilder.toString();
        } else {
            throw Assert.fail("Invalid Precondition", new Object[0]);
        }
    }
}
