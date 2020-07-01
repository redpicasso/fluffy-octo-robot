package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.BasePath;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Assert;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final class SQLiteRemoteDocumentCache implements RemoteDocumentCache {
    private final SQLitePersistence db;
    private final LocalSerializer serializer;

    SQLiteRemoteDocumentCache(SQLitePersistence sQLitePersistence, LocalSerializer localSerializer) {
        this.db = sQLitePersistence;
        this.serializer = localSerializer;
    }

    public void add(MaybeDocument maybeDocument) {
        String pathForKey = pathForKey(maybeDocument.getKey());
        MessageLite encodeMaybeDocument = this.serializer.encodeMaybeDocument(maybeDocument);
        this.db.execute("INSERT OR REPLACE INTO remote_documents (path, contents) VALUES (?, ?)", pathForKey, encodeMaybeDocument.toByteArray());
        this.db.getIndexManager().addToCollectionParentIndex((ResourcePath) maybeDocument.getKey().getPath().popLast());
    }

    public void remove(DocumentKey documentKey) {
        String pathForKey = pathForKey(documentKey);
        this.db.execute("DELETE FROM remote_documents WHERE path = ?", pathForKey);
    }

    @Nullable
    public MaybeDocument get(DocumentKey documentKey) {
        String pathForKey = pathForKey(documentKey);
        return (MaybeDocument) this.db.query("SELECT contents FROM remote_documents WHERE path = ?").binding(pathForKey).firstValue(SQLiteRemoteDocumentCache$$Lambda$1.lambdaFactory$(this));
    }

    public Map<DocumentKey, MaybeDocument> getAll(Iterable<DocumentKey> iterable) {
        List arrayList = new ArrayList();
        for (DocumentKey path : iterable) {
            arrayList.add(EncodedPath.encode(path.getPath()));
        }
        Map<DocumentKey, MaybeDocument> hashMap = new HashMap();
        for (DocumentKey path2 : iterable) {
            hashMap.put(path2, null);
        }
        LongQuery longQuery = new LongQuery(this.db, "SELECT contents FROM remote_documents WHERE path IN (", arrayList, ") ORDER BY path");
        while (longQuery.hasMoreSubqueries()) {
            longQuery.performNextSubquery().forEach(SQLiteRemoteDocumentCache$$Lambda$2.lambdaFactory$(this, hashMap));
        }
        return hashMap;
    }

    static /* synthetic */ void lambda$getAll$1(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, Map map, Cursor cursor) {
        MaybeDocument decodeMaybeDocument = sQLiteRemoteDocumentCache.decodeMaybeDocument(cursor.getBlob(0));
        map.put(decodeMaybeDocument.getKey(), decodeMaybeDocument);
    }

    public ImmutableSortedMap<DocumentKey, Document> getAllDocumentsMatchingQuery(Query query) {
        Assert.hardAssert(query.isCollectionGroupQuery() ^ true, "CollectionGroup queries should be handled in LocalDocumentsView", new Object[0]);
        BasePath path = query.getPath();
        int length = path.length() + 1;
        String prefixSuccessor = EncodedPath.prefixSuccessor(EncodedPath.encode(path));
        Map hashMap = new HashMap();
        this.db.query("SELECT path, contents FROM remote_documents WHERE path >= ? AND path < ?").binding(r0, prefixSuccessor).forEach(SQLiteRemoteDocumentCache$$Lambda$3.lambdaFactory$(this, length, query, hashMap));
        return Builder.fromMap(hashMap, DocumentKey.comparator());
    }

    static /* synthetic */ void lambda$getAllDocumentsMatchingQuery$2(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, int i, Query query, Map map, Cursor cursor) {
        if (EncodedPath.decodeResourcePath(cursor.getString(0)).length() == i) {
            MaybeDocument decodeMaybeDocument = sQLiteRemoteDocumentCache.decodeMaybeDocument(cursor.getBlob(1));
            if (decodeMaybeDocument instanceof Document) {
                Document document = (Document) decodeMaybeDocument;
                if (query.matches(document)) {
                    map.put(document.getKey(), document);
                }
            }
        }
    }

    private String pathForKey(DocumentKey documentKey) {
        return EncodedPath.encode(documentKey.getPath());
    }

    private MaybeDocument decodeMaybeDocument(byte[] bArr) {
        try {
            return this.serializer.decodeMaybeDocument(com.google.firebase.firestore.proto.MaybeDocument.parseFrom(bArr));
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("MaybeDocument failed to parse: %s", e);
        }
    }
}
