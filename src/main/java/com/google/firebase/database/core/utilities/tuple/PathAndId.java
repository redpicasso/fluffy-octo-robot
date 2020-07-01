package com.google.firebase.database.core.utilities.tuple;

import com.google.firebase.database.core.Path;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class PathAndId {
    private long id;
    private Path path;

    public PathAndId(Path path, long j) {
        this.path = path;
        this.id = j;
    }

    public Path getPath() {
        return this.path;
    }

    public long getId() {
        return this.id;
    }
}
