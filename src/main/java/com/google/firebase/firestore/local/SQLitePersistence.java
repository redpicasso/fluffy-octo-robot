package com.google.firebase.firestore.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteProgram;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteTransactionListener;
import androidx.annotation.VisibleForTesting;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.local.LruGarbageCollector.Params;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.firestore.util.Supplier;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class SQLitePersistence extends Persistence {
    private SQLiteDatabase db;
    private final SQLiteIndexManager indexManager;
    private final OpenHelper opener;
    private final SQLiteQueryCache queryCache;
    private final SQLiteLruReferenceDelegate referenceDelegate;
    private final SQLiteRemoteDocumentCache remoteDocumentCache;
    private final LocalSerializer serializer;
    private boolean started;
    private final SQLiteTransactionListener transactionListener = new SQLiteTransactionListener() {
        public void onRollback() {
        }

        public void onBegin() {
            SQLitePersistence.this.referenceDelegate.onTransactionStarted();
        }

        public void onCommit() {
            SQLitePersistence.this.referenceDelegate.onTransactionCommitted();
        }
    };

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class LongQuery {
        private static final int LIMIT = 900;
        private final List<Object> argsHead;
        private final Iterator<Object> argsIter;
        private final SQLitePersistence db;
        private final String head;
        private int subqueriesPerformed = 0;
        private final String tail;

        LongQuery(SQLitePersistence sQLitePersistence, String str, List<Object> list, String str2) {
            this.db = sQLitePersistence;
            this.head = str;
            this.argsHead = Collections.emptyList();
            this.tail = str2;
            this.argsIter = list.iterator();
        }

        LongQuery(SQLitePersistence sQLitePersistence, String str, List<Object> list, List<Object> list2, String str2) {
            this.db = sQLitePersistence;
            this.head = str;
            this.argsHead = list;
            this.tail = str2;
            this.argsIter = list2.iterator();
        }

        boolean hasMoreSubqueries() {
            return this.argsIter.hasNext();
        }

        Query performNextSubquery() {
            this.subqueriesPerformed++;
            List arrayList = new ArrayList(this.argsHead);
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            while (this.argsIter.hasNext() && i < 900 - this.argsHead.size()) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append("?");
                arrayList.add(this.argsIter.next());
                i++;
            }
            String stringBuilder2 = stringBuilder.toString();
            SQLitePersistence sQLitePersistence = this.db;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(this.head);
            stringBuilder3.append(stringBuilder2);
            stringBuilder3.append(this.tail);
            return sQLitePersistence.query(stringBuilder3.toString()).binding(arrayList.toArray());
        }

        int getSubqueriesPerformed() {
            return this.subqueriesPerformed;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private static class OpenHelper extends SQLiteOpenHelper {
        private boolean configured;

        OpenHelper(Context context, String str) {
            super(context, str, null, 8);
        }

        public void onConfigure(SQLiteDatabase sQLiteDatabase) {
            this.configured = true;
            sQLiteDatabase.rawQuery("PRAGMA locking_mode = EXCLUSIVE", new String[0]).close();
        }

        private void ensureConfigured(SQLiteDatabase sQLiteDatabase) {
            if (!this.configured) {
                onConfigure(sQLiteDatabase);
            }
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            ensureConfigured(sQLiteDatabase);
            new SQLiteSchema(sQLiteDatabase).runMigrations(0);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            ensureConfigured(sQLiteDatabase);
            new SQLiteSchema(sQLiteDatabase).runMigrations(i);
        }

        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            ensureConfigured(sQLiteDatabase);
        }

        public void onOpen(SQLiteDatabase sQLiteDatabase) {
            ensureConfigured(sQLiteDatabase);
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class Query {
        private CursorFactory cursorFactory;
        private final SQLiteDatabase db;
        private final String sql;

        Query(SQLiteDatabase sQLiteDatabase, String str) {
            this.db = sQLiteDatabase;
            this.sql = str;
        }

        Query binding(Object... objArr) {
            this.cursorFactory = SQLitePersistence$Query$$Lambda$1.lambdaFactory$(objArr);
            return this;
        }

        /* JADX WARNING: Removed duplicated region for block: B:13:0x001a  */
        void forEach(com.google.firebase.firestore.util.Consumer<android.database.Cursor> r3) {
            /*
            r2 = this;
            r0 = r2.startQuery();	 Catch:{ all -> 0x0016 }
        L_0x0004:
            r1 = r0.moveToNext();	 Catch:{ all -> 0x0014 }
            if (r1 == 0) goto L_0x000e;
        L_0x000a:
            r3.accept(r0);	 Catch:{ all -> 0x0014 }
            goto L_0x0004;
        L_0x000e:
            if (r0 == 0) goto L_0x0013;
        L_0x0010:
            r0.close();
        L_0x0013:
            return;
        L_0x0014:
            r3 = move-exception;
            goto L_0x0018;
        L_0x0016:
            r3 = move-exception;
            r0 = 0;
        L_0x0018:
            if (r0 == 0) goto L_0x001d;
        L_0x001a:
            r0.close();
        L_0x001d:
            throw r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.local.SQLitePersistence.Query.forEach(com.google.firebase.firestore.util.Consumer):void");
        }

        /* JADX WARNING: Removed duplicated region for block: B:18:0x0021  */
        int first(com.google.firebase.firestore.util.Consumer<android.database.Cursor> r3) {
            /*
            r2 = this;
            r0 = r2.startQuery();	 Catch:{ all -> 0x001d }
            r1 = r0.moveToFirst();	 Catch:{ all -> 0x001b }
            if (r1 == 0) goto L_0x0014;
        L_0x000a:
            r3.accept(r0);	 Catch:{ all -> 0x001b }
            r3 = 1;
            if (r0 == 0) goto L_0x0013;
        L_0x0010:
            r0.close();
        L_0x0013:
            return r3;
        L_0x0014:
            r3 = 0;
            if (r0 == 0) goto L_0x001a;
        L_0x0017:
            r0.close();
        L_0x001a:
            return r3;
        L_0x001b:
            r3 = move-exception;
            goto L_0x001f;
        L_0x001d:
            r3 = move-exception;
            r0 = 0;
        L_0x001f:
            if (r0 == 0) goto L_0x0024;
        L_0x0021:
            r0.close();
        L_0x0024:
            throw r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.local.SQLitePersistence.Query.first(com.google.firebase.firestore.util.Consumer):int");
        }

        /* JADX WARNING: Removed duplicated region for block: B:17:0x0021  */
        @javax.annotation.Nullable
        <T> T firstValue(com.google.common.base.Function<android.database.Cursor, T> r4) {
            /*
            r3 = this;
            r0 = 0;
            r1 = r3.startQuery();	 Catch:{ all -> 0x001d }
            r2 = r1.moveToFirst();	 Catch:{ all -> 0x001b }
            if (r2 == 0) goto L_0x0015;
        L_0x000b:
            r4 = r4.apply(r1);	 Catch:{ all -> 0x001b }
            if (r1 == 0) goto L_0x0014;
        L_0x0011:
            r1.close();
        L_0x0014:
            return r4;
        L_0x0015:
            if (r1 == 0) goto L_0x001a;
        L_0x0017:
            r1.close();
        L_0x001a:
            return r0;
        L_0x001b:
            r4 = move-exception;
            goto L_0x001f;
        L_0x001d:
            r4 = move-exception;
            r1 = r0;
        L_0x001f:
            if (r1 == 0) goto L_0x0024;
        L_0x0021:
            r1.close();
        L_0x0024:
            throw r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.local.SQLitePersistence.Query.firstValue(com.google.common.base.Function):T");
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x0016  */
        boolean isEmpty() {
            /*
            r2 = this;
            r0 = r2.startQuery();	 Catch:{ all -> 0x0012 }
            r1 = r0.moveToFirst();	 Catch:{ all -> 0x0010 }
            r1 = r1 ^ 1;
            if (r0 == 0) goto L_0x000f;
        L_0x000c:
            r0.close();
        L_0x000f:
            return r1;
        L_0x0010:
            r1 = move-exception;
            goto L_0x0014;
        L_0x0012:
            r1 = move-exception;
            r0 = 0;
        L_0x0014:
            if (r0 == 0) goto L_0x0019;
        L_0x0016:
            r0.close();
        L_0x0019:
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.local.SQLitePersistence.Query.isEmpty():boolean");
        }

        private Cursor startQuery() {
            CursorFactory cursorFactory = this.cursorFactory;
            if (cursorFactory != null) {
                return this.db.rawQueryWithFactory(cursorFactory, this.sql, null, null);
            }
            return this.db.rawQuery(this.sql, null);
        }
    }

    @VisibleForTesting
    public static String databaseName(String str, DatabaseId databaseId) {
        String str2 = ".";
        String str3 = "utf-8";
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("firestore.");
            stringBuilder.append(URLEncoder.encode(str, str3));
            stringBuilder.append(str2);
            stringBuilder.append(URLEncoder.encode(databaseId.getProjectId(), str3));
            stringBuilder.append(str2);
            stringBuilder.append(URLEncoder.encode(databaseId.getDatabaseId(), str3));
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public SQLitePersistence(Context context, String str, DatabaseId databaseId, LocalSerializer localSerializer, Params params) {
        this.opener = new OpenHelper(context, databaseName(str, databaseId));
        this.serializer = localSerializer;
        this.queryCache = new SQLiteQueryCache(this, this.serializer);
        this.indexManager = new SQLiteIndexManager(this);
        this.remoteDocumentCache = new SQLiteRemoteDocumentCache(this, this.serializer);
        this.referenceDelegate = new SQLiteLruReferenceDelegate(this, params);
    }

    public void start() {
        Assert.hardAssert(this.started ^ true, "SQLitePersistence double-started!", new Object[0]);
        this.started = true;
        try {
            this.db = this.opener.getWritableDatabase();
            this.queryCache.start();
            this.referenceDelegate.start(this.queryCache.getHighestListenSequenceNumber());
        } catch (Throwable e) {
            throw new RuntimeException("Failed to gain exclusive lock to the Firestore client's offline persistence. This generally means you are using Firestore from multiple processes in your app. Keep in mind that multi-process Android apps execute the code in your Application class in all processes, so you may need to avoid initializing Firestore in your Application class. If you are intentionally using Firestore from multiple processes, you can only enable offline persistence (i.e. call setPersistenceEnabled(true)) in one of them.", e);
        }
    }

    public void shutdown() {
        Assert.hardAssert(this.started, "SQLitePersistence shutdown without start!", new Object[0]);
        this.started = false;
        this.db.close();
        this.db = null;
    }

    public boolean isStarted() {
        return this.started;
    }

    public SQLiteLruReferenceDelegate getReferenceDelegate() {
        return this.referenceDelegate;
    }

    MutationQueue getMutationQueue(User user) {
        return new SQLiteMutationQueue(this, this.serializer, user);
    }

    SQLiteQueryCache getQueryCache() {
        return this.queryCache;
    }

    IndexManager getIndexManager() {
        return this.indexManager;
    }

    RemoteDocumentCache getRemoteDocumentCache() {
        return this.remoteDocumentCache;
    }

    void runTransaction(String str, Runnable runnable) {
        Logger.debug(TAG, "Starting transaction: %s", str);
        this.db.beginTransactionWithListener(this.transactionListener);
        try {
            runnable.run();
            this.db.setTransactionSuccessful();
        } finally {
            this.db.endTransaction();
        }
    }

    <T> T runTransaction(String str, Supplier<T> supplier) {
        Logger.debug(TAG, "Starting transaction: %s", str);
        this.db.beginTransactionWithListener(this.transactionListener);
        try {
            T t = supplier.get();
            this.db.setTransactionSuccessful();
            return t;
        } finally {
            this.db.endTransaction();
        }
    }

    long getByteSize() {
        return getPageCount() * getPageSize();
    }

    private long getPageSize() {
        return ((Long) query("PRAGMA page_size").firstValue(SQLitePersistence$$Lambda$1.lambdaFactory$())).longValue();
    }

    private long getPageCount() {
        return ((Long) query("PRAGMA page_count").firstValue(SQLitePersistence$$Lambda$2.lambdaFactory$())).longValue();
    }

    void execute(String str, Object... objArr) {
        this.db.execSQL(str, objArr);
    }

    SQLiteStatement prepare(String str) {
        return this.db.compileStatement(str);
    }

    int execute(SQLiteStatement sQLiteStatement, Object... objArr) {
        sQLiteStatement.clearBindings();
        bind(sQLiteStatement, objArr);
        return sQLiteStatement.executeUpdateDelete();
    }

    Query query(String str) {
        return new Query(this.db, str);
    }

    private static void bind(SQLiteProgram sQLiteProgram, Object[] objArr) {
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj == null) {
                sQLiteProgram.bindNull(i + 1);
            } else if (obj instanceof String) {
                sQLiteProgram.bindString(i + 1, (String) obj);
            } else if (obj instanceof Integer) {
                sQLiteProgram.bindLong(i + 1, (long) ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                sQLiteProgram.bindLong(i + 1, ((Long) obj).longValue());
            } else if (obj instanceof Double) {
                sQLiteProgram.bindDouble(i + 1, ((Double) obj).doubleValue());
            } else if (obj instanceof byte[]) {
                sQLiteProgram.bindBlob(i + 1, (byte[]) obj);
            } else {
                throw Assert.fail("Unknown argument %s of type %s", obj, obj.getClass());
            }
        }
    }
}
