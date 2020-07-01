package com.google.firebase.firestore.local;

import com.google.common.base.Preconditions;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.remote.WatchStream;
import com.google.protobuf.ByteString;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class QueryData {
    private final QueryPurpose purpose;
    private final Query query;
    private final ByteString resumeToken;
    private final long sequenceNumber;
    private final SnapshotVersion snapshotVersion;
    private final int targetId;

    public QueryData(Query query, int i, long j, QueryPurpose queryPurpose, SnapshotVersion snapshotVersion, ByteString byteString) {
        this.query = (Query) Preconditions.checkNotNull(query);
        this.targetId = i;
        this.sequenceNumber = j;
        this.purpose = queryPurpose;
        this.snapshotVersion = (SnapshotVersion) Preconditions.checkNotNull(snapshotVersion);
        this.resumeToken = (ByteString) Preconditions.checkNotNull(byteString);
    }

    public QueryData(Query query, int i, long j, QueryPurpose queryPurpose) {
        this(query, i, j, queryPurpose, SnapshotVersion.NONE, WatchStream.EMPTY_RESUME_TOKEN);
    }

    public Query getQuery() {
        return this.query;
    }

    public int getTargetId() {
        return this.targetId;
    }

    public long getSequenceNumber() {
        return this.sequenceNumber;
    }

    public QueryPurpose getPurpose() {
        return this.purpose;
    }

    public SnapshotVersion getSnapshotVersion() {
        return this.snapshotVersion;
    }

    public ByteString getResumeToken() {
        return this.resumeToken;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        QueryData queryData = (QueryData) obj;
        if (!(this.query.equals(queryData.query) && this.targetId == queryData.targetId && this.sequenceNumber == queryData.sequenceNumber && this.purpose.equals(queryData.purpose) && this.snapshotVersion.equals(queryData.snapshotVersion) && this.resumeToken.equals(queryData.resumeToken))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((((((((this.query.hashCode() * 31) + this.targetId) * 31) + ((int) this.sequenceNumber)) * 31) + this.purpose.hashCode()) * 31) + this.snapshotVersion.hashCode()) * 31) + this.resumeToken.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("QueryData{query=");
        stringBuilder.append(this.query);
        stringBuilder.append(", targetId=");
        stringBuilder.append(this.targetId);
        stringBuilder.append(", sequenceNumber=");
        stringBuilder.append(this.sequenceNumber);
        stringBuilder.append(", purpose=");
        stringBuilder.append(this.purpose);
        stringBuilder.append(", snapshotVersion=");
        stringBuilder.append(this.snapshotVersion);
        stringBuilder.append(", resumeToken=");
        stringBuilder.append(this.resumeToken);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public QueryData copy(SnapshotVersion snapshotVersion, ByteString byteString, long j) {
        return new QueryData(this.query, this.targetId, j, this.purpose, snapshotVersion, byteString);
    }
}
