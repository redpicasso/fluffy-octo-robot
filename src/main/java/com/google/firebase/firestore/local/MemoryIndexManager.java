package com.google.firebase.firestore.local;

import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
class MemoryIndexManager implements IndexManager {
    private final MemoryCollectionParentIndex collectionParentsIndex = new MemoryCollectionParentIndex();

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class MemoryCollectionParentIndex {
        private final HashMap<String, HashSet<ResourcePath>> index = new HashMap();

        MemoryCollectionParentIndex() {
        }

        boolean add(ResourcePath resourcePath) {
            boolean z = true;
            if (resourcePath.length() % 2 != 1) {
                z = false;
            }
            Assert.hardAssert(z, "Expected a collection path.", new Object[0]);
            String lastSegment = resourcePath.getLastSegment();
            resourcePath = (ResourcePath) resourcePath.popLast();
            HashSet hashSet = (HashSet) this.index.get(lastSegment);
            if (hashSet == null) {
                hashSet = new HashSet();
                this.index.put(lastSegment, hashSet);
            }
            return hashSet.add(resourcePath);
        }

        List<ResourcePath> getEntries(String str) {
            HashSet hashSet = (HashSet) this.index.get(str);
            return hashSet != null ? new ArrayList(hashSet) : Collections.emptyList();
        }
    }

    MemoryIndexManager() {
    }

    public void addToCollectionParentIndex(ResourcePath resourcePath) {
        this.collectionParentsIndex.add(resourcePath);
    }

    public List<ResourcePath> getCollectionParents(String str) {
        return this.collectionParentsIndex.getEntries(str);
    }
}
