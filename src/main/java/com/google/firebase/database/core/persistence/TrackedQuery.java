package com.google.firebase.database.core.persistence;

import com.google.firebase.database.core.view.QuerySpec;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class TrackedQuery {
    public final boolean active;
    public final boolean complete;
    public final long id;
    public final long lastUse;
    public final QuerySpec querySpec;

    public TrackedQuery(long j, QuerySpec querySpec, long j2, boolean z, boolean z2) {
        this.id = j;
        if (!querySpec.loadsAllData() || querySpec.isDefault()) {
            this.querySpec = querySpec;
            this.lastUse = j2;
            this.complete = z;
            this.active = z2;
            return;
        }
        throw new IllegalArgumentException("Can't create TrackedQuery for a non-default query that loads all data");
    }

    public TrackedQuery updateLastUse(long j) {
        return new TrackedQuery(this.id, this.querySpec, j, this.complete, this.active);
    }

    public TrackedQuery setComplete() {
        return new TrackedQuery(this.id, this.querySpec, this.lastUse, true, this.active);
    }

    public TrackedQuery setActiveState(boolean z) {
        return new TrackedQuery(this.id, this.querySpec, this.lastUse, this.complete, z);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        TrackedQuery trackedQuery = (TrackedQuery) obj;
        if (!(this.id == trackedQuery.id && this.querySpec.equals(trackedQuery.querySpec) && this.lastUse == trackedQuery.lastUse && this.complete == trackedQuery.complete && this.active == trackedQuery.active)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((((((Long.valueOf(this.id).hashCode() * 31) + this.querySpec.hashCode()) * 31) + Long.valueOf(this.lastUse).hashCode()) * 31) + Boolean.valueOf(this.complete).hashCode()) * 31) + Boolean.valueOf(this.active).hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TrackedQuery{id=");
        stringBuilder.append(this.id);
        stringBuilder.append(", querySpec=");
        stringBuilder.append(this.querySpec);
        stringBuilder.append(", lastUse=");
        stringBuilder.append(this.lastUse);
        stringBuilder.append(", complete=");
        stringBuilder.append(this.complete);
        stringBuilder.append(", active=");
        stringBuilder.append(this.active);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
