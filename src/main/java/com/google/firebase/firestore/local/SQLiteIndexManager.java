package com.google.firebase.firestore.local;

import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final class SQLiteIndexManager implements IndexManager {
    private final MemoryCollectionParentIndex collectionParentsCache = new MemoryCollectionParentIndex();
    private final SQLitePersistence db;

    SQLiteIndexManager(SQLitePersistence sQLitePersistence) {
        this.db = sQLitePersistence;
    }

    public void addToCollectionParentIndex(ResourcePath resourcePath) {
        Assert.hardAssert(resourcePath.length() % 2 == 1, "Expected a collection path.", new Object[0]);
        if (this.collectionParentsCache.add(resourcePath)) {
            String lastSegment = resourcePath.getLastSegment();
            resourcePath = (ResourcePath) resourcePath.popLast();
            this.db.execute("INSERT OR REPLACE INTO collection_parents (collection_id, parent) VALUES (?, ?)", lastSegment, EncodedPath.encode(resourcePath));
        }
    }

    public List<ResourcePath> getCollectionParents(String str) {
        List arrayList = new ArrayList();
        this.db.query("SELECT parent FROM collection_parents WHERE collection_id = ?").binding(str).forEach(SQLiteIndexManager$$Lambda$1.lambdaFactory$(arrayList));
        return arrayList;
    }
}
