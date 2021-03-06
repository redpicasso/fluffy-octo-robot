package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationBatch;
import com.google.firebase.firestore.util.Assert;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final class LocalDocumentsView {
    private final IndexManager indexManager;
    private final MutationQueue mutationQueue;
    private final RemoteDocumentCache remoteDocumentCache;

    LocalDocumentsView(RemoteDocumentCache remoteDocumentCache, MutationQueue mutationQueue, IndexManager indexManager) {
        this.remoteDocumentCache = remoteDocumentCache;
        this.mutationQueue = mutationQueue;
        this.indexManager = indexManager;
    }

    @Nullable
    MaybeDocument getDocument(DocumentKey documentKey) {
        return getDocument(documentKey, this.mutationQueue.getAllMutationBatchesAffectingDocumentKey(documentKey));
    }

    @Nullable
    private MaybeDocument getDocument(DocumentKey documentKey, List<MutationBatch> list) {
        MaybeDocument maybeDocument = this.remoteDocumentCache.get(documentKey);
        for (MutationBatch applyToLocalView : list) {
            maybeDocument = applyToLocalView.applyToLocalView(documentKey, maybeDocument);
        }
        return maybeDocument;
    }

    private Map<DocumentKey, MaybeDocument> applyLocalMutationsToDocuments(Map<DocumentKey, MaybeDocument> map, List<MutationBatch> list) {
        for (Entry entry : map.entrySet()) {
            MaybeDocument maybeDocument = (MaybeDocument) entry.getValue();
            for (MutationBatch applyToLocalView : list) {
                maybeDocument = applyToLocalView.applyToLocalView((DocumentKey) entry.getKey(), maybeDocument);
            }
            entry.setValue(maybeDocument);
        }
        return map;
    }

    ImmutableSortedMap<DocumentKey, MaybeDocument> getDocuments(Iterable<DocumentKey> iterable) {
        return getLocalViewOfDocuments(this.remoteDocumentCache.getAll(iterable));
    }

    ImmutableSortedMap<DocumentKey, MaybeDocument> getLocalViewOfDocuments(Map<DocumentKey, MaybeDocument> map) {
        ImmutableSortedMap<DocumentKey, MaybeDocument> emptyMaybeDocumentMap = DocumentCollections.emptyMaybeDocumentMap();
        for (Entry entry : applyLocalMutationsToDocuments(map, this.mutationQueue.getAllMutationBatchesAffectingDocumentKeys(map.keySet())).entrySet()) {
            DocumentKey documentKey = (DocumentKey) entry.getKey();
            Object obj = (MaybeDocument) entry.getValue();
            if (obj == null) {
                obj = new NoDocument(documentKey, SnapshotVersion.NONE, false);
            }
            emptyMaybeDocumentMap = emptyMaybeDocumentMap.insert(documentKey, obj);
        }
        return emptyMaybeDocumentMap;
    }

    ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingQuery(Query query) {
        ResourcePath path = query.getPath();
        if (query.isDocumentQuery()) {
            return getDocumentsMatchingDocumentQuery(path);
        }
        if (query.isCollectionGroupQuery()) {
            return getDocumentsMatchingCollectionGroupQuery(query);
        }
        return getDocumentsMatchingCollectionQuery(query);
    }

    private ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingDocumentQuery(ResourcePath resourcePath) {
        ImmutableSortedMap<DocumentKey, Document> emptyDocumentMap = DocumentCollections.emptyDocumentMap();
        MaybeDocument document = getDocument(DocumentKey.fromPath(resourcePath));
        return document instanceof Document ? emptyDocumentMap.insert(document.getKey(), (Document) document) : emptyDocumentMap;
    }

    private ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingCollectionGroupQuery(Query query) {
        Assert.hardAssert(query.getPath().isEmpty(), "Currently we only support collection group queries at the root.", new Object[0]);
        String collectionGroup = query.getCollectionGroup();
        ImmutableSortedMap<DocumentKey, Document> emptyDocumentMap = DocumentCollections.emptyDocumentMap();
        for (ResourcePath append : this.indexManager.getCollectionParents(collectionGroup)) {
            Iterator it = getDocumentsMatchingCollectionQuery(query.asCollectionQueryAtPath((ResourcePath) append.append(collectionGroup))).iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                emptyDocumentMap = emptyDocumentMap.insert((DocumentKey) entry.getKey(), (Document) entry.getValue());
            }
        }
        return emptyDocumentMap;
    }

    private ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingCollectionQuery(Query query) {
        ImmutableSortedMap<DocumentKey, Document> allDocumentsMatchingQuery = this.remoteDocumentCache.getAllDocumentsMatchingQuery(query);
        for (MutationBatch mutationBatch : this.mutationQueue.getAllMutationBatchesAffectingQuery(query)) {
            for (Mutation mutation : mutationBatch.getMutations()) {
                if (query.getPath().isImmediateParentOf(mutation.getKey().getPath())) {
                    DocumentKey key = mutation.getKey();
                    MaybeDocument maybeDocument = (MaybeDocument) allDocumentsMatchingQuery.get(key);
                    MaybeDocument applyToLocalView = mutation.applyToLocalView(maybeDocument, maybeDocument, mutationBatch.getLocalWriteTime());
                    if (applyToLocalView instanceof Document) {
                        allDocumentsMatchingQuery = allDocumentsMatchingQuery.insert(key, (Document) applyToLocalView);
                    } else {
                        allDocumentsMatchingQuery = allDocumentsMatchingQuery.remove(key);
                    }
                }
            }
        }
        Iterator it = allDocumentsMatchingQuery.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (!query.matches((Document) entry.getValue())) {
                allDocumentsMatchingQuery = allDocumentsMatchingQuery.remove((DocumentKey) entry.getKey());
            }
        }
        return allDocumentsMatchingQuery;
    }
}
