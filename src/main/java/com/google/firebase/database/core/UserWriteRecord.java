package com.google.firebase.database.core;

import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class UserWriteRecord {
    private final CompoundWrite merge;
    private final Node overwrite;
    private final Path path;
    private final boolean visible;
    private final long writeId;

    public UserWriteRecord(long j, Path path, Node node, boolean z) {
        this.writeId = j;
        this.path = path;
        this.overwrite = node;
        this.merge = null;
        this.visible = z;
    }

    public UserWriteRecord(long j, Path path, CompoundWrite compoundWrite) {
        this.writeId = j;
        this.path = path;
        this.overwrite = null;
        this.merge = compoundWrite;
        this.visible = true;
    }

    public long getWriteId() {
        return this.writeId;
    }

    public Path getPath() {
        return this.path;
    }

    public Node getOverwrite() {
        Node node = this.overwrite;
        if (node != null) {
            return node;
        }
        throw new IllegalArgumentException("Can't access overwrite when write is a merge!");
    }

    public CompoundWrite getMerge() {
        CompoundWrite compoundWrite = this.merge;
        if (compoundWrite != null) {
            return compoundWrite;
        }
        throw new IllegalArgumentException("Can't access merge when write is an overwrite!");
    }

    public boolean isMerge() {
        return this.merge != null;
    }

    public boolean isOverwrite() {
        return this.overwrite != null;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserWriteRecord userWriteRecord = (UserWriteRecord) obj;
        if (this.writeId != userWriteRecord.writeId || !this.path.equals(userWriteRecord.path) || this.visible != userWriteRecord.visible) {
            return false;
        }
        Node node = this.overwrite;
        if (!node == null ? node.equals(userWriteRecord.overwrite) : userWriteRecord.overwrite == null) {
            return false;
        }
        CompoundWrite compoundWrite = this.merge;
        return compoundWrite == null ? userWriteRecord.merge == null : compoundWrite.equals(userWriteRecord.merge);
    }

    public int hashCode() {
        int hashCode = ((((Long.valueOf(this.writeId).hashCode() * 31) + Boolean.valueOf(this.visible).hashCode()) * 31) + this.path.hashCode()) * 31;
        Node node = this.overwrite;
        int i = 0;
        hashCode = (hashCode + (node != null ? node.hashCode() : 0)) * 31;
        CompoundWrite compoundWrite = this.merge;
        if (compoundWrite != null) {
            i = compoundWrite.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UserWriteRecord{id=");
        stringBuilder.append(this.writeId);
        stringBuilder.append(" path=");
        stringBuilder.append(this.path);
        stringBuilder.append(" visible=");
        stringBuilder.append(this.visible);
        stringBuilder.append(" overwrite=");
        stringBuilder.append(this.overwrite);
        stringBuilder.append(" merge=");
        stringBuilder.append(this.merge);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
