package com.google.firebase.database.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.media.session.PlaybackStateCompat;
import com.RNFetchBlob.RNFetchBlobConst;
import com.bumptech.glide.load.Key;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.database.core.persistence.PersistenceStorageEngine;
import com.google.firebase.database.core.persistence.PruneForest;
import com.google.firebase.database.core.persistence.TrackedQuery;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.ImmutableTree.TreeVisitor;
import com.google.firebase.database.core.utilities.NodeSizeEstimator;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.util.JsonMapper;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class SqlPersistenceStorageEngine implements PersistenceStorageEngine {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CHILDREN_NODE_SPLIT_SIZE_THRESHOLD = 16384;
    private static final String CREATE_SERVER_CACHE = "CREATE TABLE serverCache (path TEXT PRIMARY KEY, value BLOB);";
    private static final String CREATE_TRACKED_KEYS = "CREATE TABLE trackedKeys (id INTEGER, key TEXT);";
    private static final String CREATE_TRACKED_QUERIES = "CREATE TABLE trackedQueries (id INTEGER PRIMARY KEY, path TEXT, queryParams TEXT, lastUse INTEGER, complete INTEGER, active INTEGER);";
    private static final String CREATE_WRITES = "CREATE TABLE writes (id INTEGER, path TEXT, type TEXT, part INTEGER, node BLOB, UNIQUE (id, part));";
    private static final String FIRST_PART_KEY = ".part-0000";
    private static final String LOGGER_COMPONENT = "Persistence";
    private static final String PART_KEY_FORMAT = ".part-%04d";
    private static final String PART_KEY_PREFIX = ".part-";
    private static final String PATH_COLUMN_NAME = "path";
    private static final String ROW_ID_COLUMN_NAME = "rowid";
    private static final int ROW_SPLIT_SIZE = 262144;
    private static final String SERVER_CACHE_TABLE = "serverCache";
    private static final String TRACKED_KEYS_ID_COLUMN_NAME = "id";
    private static final String TRACKED_KEYS_KEY_COLUMN_NAME = "key";
    private static final String TRACKED_KEYS_TABLE = "trackedKeys";
    private static final String TRACKED_QUERY_ACTIVE_COLUMN_NAME = "active";
    private static final String TRACKED_QUERY_COMPLETE_COLUMN_NAME = "complete";
    private static final String TRACKED_QUERY_ID_COLUMN_NAME = "id";
    private static final String TRACKED_QUERY_LAST_USE_COLUMN_NAME = "lastUse";
    private static final String TRACKED_QUERY_PARAMS_COLUMN_NAME = "queryParams";
    private static final String TRACKED_QUERY_PATH_COLUMN_NAME = "path";
    private static final String TRACKED_QUERY_TABLE = "trackedQueries";
    private static final Charset UTF8_CHARSET = Charset.forName(Key.STRING_CHARSET_NAME);
    private static final String VALUE_COLUMN_NAME = "value";
    private static final String WRITES_TABLE = "writes";
    private static final String WRITE_ID_COLUMN_NAME = "id";
    private static final String WRITE_NODE_COLUMN_NAME = "node";
    private static final String WRITE_PART_COLUMN_NAME = "part";
    private static final String WRITE_TYPE_COLUMN_NAME = "type";
    private static final String WRITE_TYPE_MERGE = "m";
    private static final String WRITE_TYPE_OVERWRITE = "o";
    private final SQLiteDatabase database;
    private boolean insideTransaction;
    private final LogWrapper logger;
    private long transactionStart = 0;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class PersistentCacheOpenHelper extends SQLiteOpenHelper {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int DATABASE_VERSION = 2;

        static {
            Class cls = SqlPersistenceStorageEngine.class;
        }

        public PersistentCacheOpenHelper(Context context, String str) {
            super(context, str, null, 2);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_SERVER_CACHE);
            sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_WRITES);
            sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_QUERIES);
            sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_KEYS);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i <= 1) {
                dropTable(sQLiteDatabase, SqlPersistenceStorageEngine.SERVER_CACHE_TABLE);
                sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_SERVER_CACHE);
                dropTable(sQLiteDatabase, SqlPersistenceStorageEngine.TRACKED_QUERY_COMPLETE_COLUMN_NAME);
                sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_KEYS);
                sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_QUERIES);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("We don't handle upgrading to ");
            stringBuilder.append(i2);
            throw new AssertionError(stringBuilder.toString());
        }

        private void dropTable(SQLiteDatabase sQLiteDatabase, String str) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DROP TABLE IF EXISTS ");
            stringBuilder.append(str);
            sQLiteDatabase.execSQL(stringBuilder.toString());
        }
    }

    public SqlPersistenceStorageEngine(Context context, com.google.firebase.database.core.Context context2, String str) {
        try {
            str = URLEncoder.encode(str, "utf-8");
            this.logger = context2.getLogger(LOGGER_COMPONENT);
            this.database = openDatabase(context, str);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserOverwrite(Path path, Node node, long j) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        Path path2 = path;
        long j2 = j;
        saveWrite(path2, j2, WRITE_TYPE_OVERWRITE, serializeObject(node.getValue(true)));
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Persisted user overwrite in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void saveUserMerge(Path path, CompoundWrite compoundWrite, long j) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        Path path2 = path;
        long j2 = j;
        saveWrite(path2, j2, WRITE_TYPE_MERGE, serializeObject(compoundWrite.getValue(true)));
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Persisted user merge in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void removeUserWrite(long j) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        int delete = this.database.delete(WRITES_TABLE, "id = ?", new String[]{String.valueOf(j)});
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Deleted %d write(s) with writeId %d in %dms", new Object[]{Integer.valueOf(delete), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public List<UserWriteRecord> loadUserWrites() {
        String[] strArr = new String[]{"id", RNFetchBlobConst.RNFB_RESPONSE_PATH, "type", WRITE_PART_COLUMN_NAME, WRITE_NODE_COLUMN_NAME};
        long currentTimeMillis = System.currentTimeMillis();
        Cursor query = this.database.query(WRITES_TABLE, strArr, null, null, null, null, "id, part");
        List<UserWriteRecord> arrayList = new ArrayList();
        while (query.moveToNext()) {
            try {
                byte[] blob;
                Object userWriteRecord;
                long j = query.getLong(0);
                Path path = new Path(query.getString(1));
                String string = query.getString(2);
                if (query.isNull(3)) {
                    blob = query.getBlob(4);
                } else {
                    List arrayList2 = new ArrayList();
                    do {
                        arrayList2.add(query.getBlob(4));
                        if (!query.moveToNext()) {
                            break;
                        }
                    } while (query.getLong(0) == j);
                    query.moveToPrevious();
                    blob = joinBytes(arrayList2);
                }
                Object parseJsonValue = JsonMapper.parseJsonValue(new String(blob, UTF8_CHARSET));
                if (WRITE_TYPE_OVERWRITE.equals(string)) {
                    UserWriteRecord userWriteRecord2 = new UserWriteRecord(j, path, NodeUtilities.NodeFromJSON(parseJsonValue), true);
                } else if (WRITE_TYPE_MERGE.equals(string)) {
                    userWriteRecord = new UserWriteRecord(j, path, CompoundWrite.fromValue((Map) parseJsonValue));
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Got invalid write type: ");
                    stringBuilder.append(string);
                    throw new IllegalStateException(stringBuilder.toString());
                }
                arrayList.add(userWriteRecord);
            } catch (Throwable e) {
                throw new RuntimeException("Failed to load writes", e);
            } catch (Throwable th) {
                query.close();
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Loaded %d writes in %dms", new Object[]{Integer.valueOf(arrayList.size()), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
        query.close();
        return arrayList;
    }

    private void saveWrite(Path path, long j, String str, byte[] bArr) {
        String str2 = str;
        byte[] bArr2 = bArr;
        verifyInsideTransaction();
        SQLiteDatabase sQLiteDatabase = this.database;
        String[] strArr = new String[1];
        int i = 0;
        strArr[0] = String.valueOf(j);
        String str3 = WRITES_TABLE;
        sQLiteDatabase.delete(str3, "id = ?", strArr);
        int length = bArr2.length;
        String str4 = WRITE_NODE_COLUMN_NAME;
        String str5 = WRITE_PART_COLUMN_NAME;
        String str6 = "type";
        String str7 = RNFetchBlobConst.RNFB_RESPONSE_PATH;
        String str8 = "id";
        ContentValues contentValues;
        if (length >= 262144) {
            List splitBytes = splitBytes(bArr2, 262144);
            while (i < splitBytes.size()) {
                contentValues = new ContentValues();
                contentValues.put(str8, Long.valueOf(j));
                contentValues.put(str7, pathToKey(path));
                contentValues.put(str6, str2);
                contentValues.put(str5, Integer.valueOf(i));
                contentValues.put(str4, (byte[]) splitBytes.get(i));
                this.database.insertWithOnConflict(str3, null, contentValues, 5);
                i++;
            }
            return;
        }
        contentValues = new ContentValues();
        contentValues.put(str8, Long.valueOf(j));
        contentValues.put(str7, pathToKey(path));
        contentValues.put(str6, str2);
        contentValues.put(str5, (Integer) null);
        contentValues.put(str4, bArr2);
        this.database.insertWithOnConflict(str3, null, contentValues, 5);
    }

    public Node serverCache(Path path) {
        return loadNested(path);
    }

    public void overwriteServerCache(Path path, Node node) {
        verifyInsideTransaction();
        updateServerCache(path, node, false);
    }

    public void mergeIntoServerCache(Path path, Node node) {
        verifyInsideTransaction();
        updateServerCache(path, node, true);
    }

    private void updateServerCache(Path path, Node node, boolean z) {
        int i;
        int i2;
        long currentTimeMillis = System.currentTimeMillis();
        String str = SERVER_CACHE_TABLE;
        if (z) {
            i = 0;
            int i3 = 0;
            for (NamedNode namedNode : node) {
                i += removeNested(str, path.child(namedNode.getName()));
                i3 += saveNested(path.child(namedNode.getName()), namedNode.getNode());
            }
            i2 = i3;
        } else {
            i = removeNested(str, path);
            i2 = saveNested(path, node);
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Persisted a total of %d rows and deleted %d rows for a set at %s in %dms", new Object[]{Integer.valueOf(i2), Integer.valueOf(i), path.toString(), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void mergeIntoServerCache(Path path, CompoundWrite compoundWrite) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        Iterator it = compoundWrite.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            i += removeNested(SERVER_CACHE_TABLE, path.child((Path) entry.getKey()));
            i2 += saveNested(path.child((Path) entry.getKey()), (Node) entry.getValue());
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Persisted a total of %d rows and deleted %d rows for a merge at %s in %dms", new Object[]{Integer.valueOf(i2), Integer.valueOf(i), path.toString(), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public long serverCacheEstimatedSizeInBytes() {
        Cursor rawQuery = this.database.rawQuery(String.format("SELECT sum(length(%s) + length(%s)) FROM %s", new Object[]{"value", RNFetchBlobConst.RNFB_RESPONSE_PATH, SERVER_CACHE_TABLE}), null);
        try {
            if (rawQuery.moveToFirst()) {
                long j = rawQuery.getLong(0);
                return j;
            }
            throw new IllegalStateException("Couldn't read database result!");
        } finally {
            rawQuery.close();
        }
    }

    public void saveTrackedQuery(TrackedQuery trackedQuery) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Long.valueOf(trackedQuery.id));
        contentValues.put(RNFetchBlobConst.RNFB_RESPONSE_PATH, pathToKey(trackedQuery.querySpec.getPath()));
        contentValues.put(TRACKED_QUERY_PARAMS_COLUMN_NAME, trackedQuery.querySpec.getParams().toJSON());
        contentValues.put(TRACKED_QUERY_LAST_USE_COLUMN_NAME, Long.valueOf(trackedQuery.lastUse));
        contentValues.put(TRACKED_QUERY_COMPLETE_COLUMN_NAME, Boolean.valueOf(trackedQuery.complete));
        contentValues.put("active", Boolean.valueOf(trackedQuery.active));
        this.database.insertWithOnConflict(TRACKED_QUERY_TABLE, null, contentValues, 5);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Saved new tracked query in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void deleteTrackedQuery(long j) {
        verifyInsideTransaction();
        String valueOf = String.valueOf(j);
        String str = "id = ?";
        this.database.delete(TRACKED_QUERY_TABLE, str, new String[]{valueOf});
        this.database.delete(TRACKED_KEYS_TABLE, str, new String[]{valueOf});
    }

    public List<TrackedQuery> loadTrackedQueries() {
        String[] strArr = new String[]{"id", RNFetchBlobConst.RNFB_RESPONSE_PATH, TRACKED_QUERY_PARAMS_COLUMN_NAME, TRACKED_QUERY_LAST_USE_COLUMN_NAME, TRACKED_QUERY_COMPLETE_COLUMN_NAME, "active"};
        long currentTimeMillis = System.currentTimeMillis();
        Cursor query = this.database.query(TRACKED_QUERY_TABLE, strArr, null, null, null, null, "id");
        List<TrackedQuery> arrayList = new ArrayList();
        while (query.moveToNext()) {
            try {
                arrayList.add(new TrackedQuery(query.getLong(0), QuerySpec.fromPathAndQueryObject(new Path(query.getString(1)), JsonMapper.parseJson(query.getString(2))), query.getLong(3), query.getInt(4) != 0, query.getInt(5) != 0));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } catch (Throwable th) {
                query.close();
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Loaded %d tracked queries in %dms", new Object[]{Integer.valueOf(arrayList.size()), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
        query.close();
        return arrayList;
    }

    public void resetPreviouslyActiveTrackedQueries(long j) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("active", Boolean.valueOf(false));
        contentValues.put(TRACKED_QUERY_LAST_USE_COLUMN_NAME, Long.valueOf(j));
        String[] strArr = new String[0];
        this.database.updateWithOnConflict(TRACKED_QUERY_TABLE, contentValues, "active = 1", strArr, 5);
        j = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Reset active tracked queries in %dms", new Object[]{Long.valueOf(j)}), new Object[0]);
        }
    }

    public void saveTrackedQueryKeys(long j, Set<ChildKey> set) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        String valueOf = String.valueOf(j);
        SQLiteDatabase sQLiteDatabase = this.database;
        String[] strArr = new String[]{valueOf};
        valueOf = TRACKED_KEYS_TABLE;
        sQLiteDatabase.delete(valueOf, "id = ?", strArr);
        for (ChildKey childKey : set) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Long.valueOf(j));
            contentValues.put(TRACKED_KEYS_KEY_COLUMN_NAME, childKey.asString());
            this.database.insertWithOnConflict(valueOf, null, contentValues, 5);
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Set %d tracked query keys for tracked query %d in %dms", new Object[]{Integer.valueOf(set.size()), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void updateTrackedQueryKeys(long j, Set<ChildKey> set, Set<ChildKey> set2) {
        String str;
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        String valueOf = String.valueOf(j);
        Iterator it = set2.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            str = TRACKED_KEYS_TABLE;
            if (!hasNext) {
                break;
            }
            ChildKey childKey = (ChildKey) it.next();
            this.database.delete(str, "id = ? AND key = ?", new String[]{valueOf, childKey.asString()});
        }
        for (ChildKey childKey2 : set) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Long.valueOf(j));
            contentValues.put(TRACKED_KEYS_KEY_COLUMN_NAME, childKey2.asString());
            this.database.insertWithOnConflict(str, null, contentValues, 5);
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Updated tracked query keys (%d added, %d removed) for tracked query id %d in %dms", new Object[]{Integer.valueOf(set.size()), Integer.valueOf(set2.size()), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public Set<ChildKey> loadTrackedQueryKeys(long j) {
        return loadTrackedQueryKeys(Collections.singleton(Long.valueOf(j)));
    }

    public Set<ChildKey> loadTrackedQueryKeys(Set<Long> set) {
        String[] strArr = new String[]{TRACKED_KEYS_KEY_COLUMN_NAME};
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id IN (");
        stringBuilder.append(commaSeparatedList(set));
        stringBuilder.append(")");
        Cursor query = this.database.query(true, TRACKED_KEYS_TABLE, strArr, stringBuilder.toString(), null, null, null, null, null);
        Set<ChildKey> hashSet = new HashSet();
        while (query.moveToNext()) {
            try {
                hashSet.add(ChildKey.fromString(query.getString(0)));
            } catch (Throwable th) {
                query.close();
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Loaded %d tracked queries keys for tracked queries %s in %dms", new Object[]{Integer.valueOf(hashSet.size()), set.toString(), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
        query.close();
        return hashSet;
    }

    public void pruneCache(Path path, PruneForest pruneForest) {
        Path path2 = path;
        PruneForest pruneForest2 = pruneForest;
        if (pruneForest.prunesAnything()) {
            int i;
            int i2;
            verifyInsideTransaction();
            long currentTimeMillis = System.currentTimeMillis();
            Cursor loadNestedQuery = loadNestedQuery(path2, new String[]{ROW_ID_COLUMN_NAME, RNFetchBlobConst.RNFB_RESPONSE_PATH});
            ImmutableTree immutableTree = new ImmutableTree(null);
            ImmutableTree immutableTree2 = new ImmutableTree(null);
            while (loadNestedQuery.moveToNext()) {
                long j = loadNestedQuery.getLong(0);
                Path path3 = new Path(loadNestedQuery.getString(1));
                String str = "We are pruning at ";
                LogWrapper logWrapper;
                StringBuilder stringBuilder;
                if (path2.contains(path3)) {
                    Path relative = Path.getRelative(path2, path3);
                    if (pruneForest2.shouldPruneUnkeptDescendants(relative)) {
                        immutableTree = immutableTree.set(relative, Long.valueOf(j));
                    } else if (pruneForest2.shouldKeep(relative)) {
                        immutableTree2 = immutableTree2.set(relative, Long.valueOf(j));
                    } else {
                        logWrapper = this.logger;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str);
                        stringBuilder.append(path2);
                        stringBuilder.append(" and have data at ");
                        stringBuilder.append(path3);
                        stringBuilder.append(" that isn't marked for pruning or keeping. Ignoring.");
                        logWrapper.warn(stringBuilder.toString());
                    }
                } else {
                    logWrapper = this.logger;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str);
                    stringBuilder.append(path2);
                    stringBuilder.append(" but we have data stored higher up at ");
                    stringBuilder.append(path3);
                    stringBuilder.append(". Ignoring.");
                    logWrapper.warn(stringBuilder.toString());
                }
            }
            if (immutableTree.isEmpty()) {
                i = 0;
                i2 = 0;
            } else {
                List<Pair> arrayList = new ArrayList();
                pruneTreeRecursive(path, Path.getEmptyPath(), immutableTree, immutableTree2, pruneForest, arrayList);
                Collection values = immutableTree.values();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("rowid IN (");
                stringBuilder2.append(commaSeparatedList(values));
                stringBuilder2.append(")");
                this.database.delete(SERVER_CACHE_TABLE, stringBuilder2.toString(), null);
                for (Pair pair : arrayList) {
                    saveNested(path2.child((Path) pair.getFirst()), (Node) pair.getSecond());
                }
                i = values.size();
                i2 = arrayList.size();
            }
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            if (this.logger.logsDebug()) {
                this.logger.debug(String.format("Pruned %d rows with %d nodes resaved in %dms", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Long.valueOf(currentTimeMillis2)}), new Object[0]);
            }
        }
    }

    private void pruneTreeRecursive(Path path, Path path2, ImmutableTree<Long> immutableTree, ImmutableTree<Long> immutableTree2, PruneForest pruneForest, List<Pair<Path, Node>> list) {
        final ImmutableTree<Long> immutableTree3 = immutableTree2;
        PruneForest pruneForest2 = pruneForest;
        if (immutableTree.getValue() != null) {
            if (((Integer) pruneForest2.foldKeptNodes(Integer.valueOf(0), new TreeVisitor<Void, Integer>() {
                public Integer onNodeValue(Path path, Void voidR, Integer num) {
                    return Integer.valueOf(immutableTree3.get(path) == null ? num.intValue() + 1 : num.intValue());
                }
            })).intValue() > 0) {
                Path child = path.child(path2);
                if (this.logger.logsDebug()) {
                    this.logger.debug(String.format("Need to rewrite %d nodes below path %s", new Object[]{Integer.valueOf(r1), child}), new Object[0]);
                }
                final Node loadNested = loadNested(child);
                final ImmutableTree<Long> immutableTree4 = immutableTree2;
                final List<Pair<Path, Node>> list2 = list;
                final Path path3 = path2;
                pruneForest2.foldKeptNodes(null, new TreeVisitor<Void, Void>() {
                    public Void onNodeValue(Path path, Void voidR, Void voidR2) {
                        if (immutableTree4.get(path) == null) {
                            list2.add(new Pair(path3.child(path), loadNested.getChild(path)));
                        }
                        return null;
                    }
                });
                return;
            }
            return;
        }
        Iterator it = immutableTree.getChildren().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            ChildKey childKey = (ChildKey) entry.getKey();
            PruneForest child2 = pruneForest2.child((ChildKey) entry.getKey());
            Path path4 = path2;
            pruneTreeRecursive(path, path2.child(childKey), (ImmutableTree) entry.getValue(), immutableTree3.getChild(childKey), child2, list);
        }
    }

    public void removeAllUserWrites() {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        int delete = this.database.delete(WRITES_TABLE, null, null);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Deleted %d (all) write(s) in %dms", new Object[]{Integer.valueOf(delete), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void purgeCache() {
        verifyInsideTransaction();
        this.database.delete(SERVER_CACHE_TABLE, null, null);
        this.database.delete(WRITES_TABLE, null, null);
        this.database.delete(TRACKED_QUERY_TABLE, null, null);
        this.database.delete(TRACKED_KEYS_TABLE, null, null);
    }

    public void beginTransaction() {
        Utilities.hardAssert(this.insideTransaction ^ true, "runInTransaction called when an existing transaction is already in progress.");
        if (this.logger.logsDebug()) {
            this.logger.debug("Starting transaction.", new Object[0]);
        }
        this.database.beginTransaction();
        this.insideTransaction = true;
        this.transactionStart = System.currentTimeMillis();
    }

    public void endTransaction() {
        this.database.endTransaction();
        this.insideTransaction = false;
        long currentTimeMillis = System.currentTimeMillis() - this.transactionStart;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Transaction completed. Elapsed: %dms", new Object[]{Long.valueOf(currentTimeMillis)}), new Object[0]);
        }
    }

    public void setTransactionSuccessful() {
        this.database.setTransactionSuccessful();
    }

    public void close() {
        this.database.close();
    }

    private SQLiteDatabase openDatabase(Context context, String str) {
        try {
            SQLiteDatabase writableDatabase = new PersistentCacheOpenHelper(context, str).getWritableDatabase();
            writableDatabase.rawQuery("PRAGMA locking_mode = EXCLUSIVE", null).close();
            writableDatabase.beginTransaction();
            writableDatabase.endTransaction();
            return writableDatabase;
        } catch (Throwable e) {
            if (e instanceof SQLiteDatabaseLockedException) {
                throw new DatabaseException("Failed to gain exclusive lock to Firebase Database's offline persistence. This generally means you are using Firebase Database from multiple processes in your app. Keep in mind that multi-process Android apps execute the code in your Application class in all processes, so you may need to avoid initializing FirebaseDatabase in your Application class. If you are intentionally using Firebase Database from multiple processes, you can only enable offline persistence (i.e. call setPersistenceEnabled(true)) in one of them.", e);
            }
            throw e;
        }
    }

    private void verifyInsideTransaction() {
        Utilities.hardAssert(this.insideTransaction, "Transaction expected to already be in progress.");
    }

    private int saveNested(Path path, Node node) {
        long estimateSerializedNodeSize = NodeSizeEstimator.estimateSerializedNodeSize(node);
        if (!(node instanceof ChildrenNode) || estimateSerializedNodeSize <= PlaybackStateCompat.ACTION_PREPARE) {
            saveNode(path, node);
            return 1;
        }
        int i = 0;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Node estimated serialized size at path %s of %d bytes exceeds limit of %d bytes. Splitting up.", new Object[]{path, Long.valueOf(estimateSerializedNodeSize), Integer.valueOf(16384)}), new Object[0]);
        }
        for (NamedNode namedNode : node) {
            i += saveNested(path.child(namedNode.getName()), namedNode.getNode());
        }
        if (!node.getPriority().isEmpty()) {
            saveNode(path.child(ChildKey.getPriorityKey()), node.getPriority());
            i++;
        }
        saveNode(path, EmptyNode.Empty());
        return i + 1;
    }

    private String partKey(Path path, int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(pathToKey(path));
        stringBuilder.append(String.format(PART_KEY_FORMAT, new Object[]{Integer.valueOf(i)}));
        return stringBuilder.toString();
    }

    private void saveNode(Path path, Node node) {
        byte[] serializeObject = serializeObject(node.getValue(true));
        int length = serializeObject.length;
        String str = SERVER_CACHE_TABLE;
        String str2 = "value";
        String str3 = RNFetchBlobConst.RNFB_RESPONSE_PATH;
        ContentValues contentValues;
        if (length >= 262144) {
            List splitBytes = splitBytes(serializeObject, 262144);
            int i = 0;
            if (this.logger.logsDebug()) {
                LogWrapper logWrapper = this.logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Saving huge leaf node with ");
                stringBuilder.append(splitBytes.size());
                stringBuilder.append(" parts.");
                logWrapper.debug(stringBuilder.toString(), new Object[0]);
            }
            while (i < splitBytes.size()) {
                contentValues = new ContentValues();
                contentValues.put(str3, partKey(path, i));
                contentValues.put(str2, (byte[]) splitBytes.get(i));
                this.database.insertWithOnConflict(str, null, contentValues, 5);
                i++;
            }
            return;
        }
        contentValues = new ContentValues();
        contentValues.put(str3, pathToKey(path));
        contentValues.put(str2, serializeObject);
        this.database.insertWithOnConflict(str, null, contentValues, 5);
    }

    private Node loadNested(Path path) {
        long j;
        long j2;
        Node child;
        Path path2 = path;
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        Cursor loadNestedQuery = loadNestedQuery(path2, new String[]{RNFetchBlobConst.RNFB_RESPONSE_PATH, "value"});
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        long currentTimeMillis3 = System.currentTimeMillis();
        while (loadNestedQuery.moveToNext()) {
            try {
                arrayList.add(loadNestedQuery.getString(0));
                arrayList2.add(loadNestedQuery.getBlob(1));
            } finally {
                loadNestedQuery.close();
            }
        }
        long currentTimeMillis4 = System.currentTimeMillis() - currentTimeMillis3;
        currentTimeMillis3 = System.currentTimeMillis();
        EmptyNode Empty = EmptyNode.Empty();
        Map hashMap = new HashMap();
        Object child2 = Empty;
        int i = 0;
        while (i < arrayList2.size()) {
            Path path3;
            Node deserializeNode;
            int i2;
            if (((String) arrayList.get(i)).endsWith(FIRST_PART_KEY)) {
                Path path4;
                String str = (String) arrayList.get(i);
                j = currentTimeMillis4;
                path3 = new Path(str.substring(0, str.length() - 10));
                int splitNodeRunLength = splitNodeRunLength(path3, arrayList, i);
                if (this.logger.logsDebug()) {
                    LogWrapper logWrapper = this.logger;
                    StringBuilder stringBuilder = new StringBuilder();
                    path4 = path3;
                    stringBuilder.append("Loading split node with ");
                    stringBuilder.append(splitNodeRunLength);
                    stringBuilder.append(" parts.");
                    j2 = currentTimeMillis2;
                    logWrapper.debug(stringBuilder.toString(), new Object[0]);
                } else {
                    j2 = currentTimeMillis2;
                    path4 = path3;
                }
                splitNodeRunLength += i;
                deserializeNode = deserializeNode(joinBytes(arrayList2.subList(i, splitNodeRunLength)));
                i2 = splitNodeRunLength - 1;
                path3 = path4;
            } else {
                j2 = currentTimeMillis2;
                j = currentTimeMillis4;
                Node deserializeNode2 = deserializeNode((byte[]) arrayList2.get(i));
                path3 = new Path((String) arrayList.get(i));
                i2 = i;
                deserializeNode = deserializeNode2;
            }
            if (path3.getBack() != null && path3.getBack().isPriorityChildName()) {
                hashMap.put(path3, deserializeNode);
            } else if (path3.contains(path2)) {
                Utilities.hardAssert(false ^ 1, "Descendants of path must come after ancestors.");
                child2 = deserializeNode.getChild(Path.getRelative(path3, path2));
            } else if (path2.contains(path3)) {
                child2 = child2.updateChild(Path.getRelative(path2, path3), deserializeNode);
            } else {
                throw new IllegalStateException(String.format("Loading an unrelated row with path %s for %s", new Object[]{path3, path2}));
            }
            i = i2 + 1;
            currentTimeMillis4 = j;
            currentTimeMillis2 = j2;
        }
        j2 = currentTimeMillis2;
        j = currentTimeMillis4;
        Node node = child2;
        for (Entry entry : hashMap.entrySet()) {
            node = node.updateChild(Path.getRelative(path2, (Path) entry.getKey()), (Node) entry.getValue());
        }
        long currentTimeMillis5 = System.currentTimeMillis() - currentTimeMillis3;
        long currentTimeMillis6 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Loaded a total of %d rows for a total of %d nodes at %s in %dms (Query: %dms, Loading: %dms, Serializing: %dms)", new Object[]{Integer.valueOf(arrayList2.size()), Integer.valueOf(NodeSizeEstimator.nodeCount(node)), path2, Long.valueOf(currentTimeMillis6), Long.valueOf(j2), Long.valueOf(j), Long.valueOf(currentTimeMillis5)}), new Object[0]);
        }
        return node;
    }

    private int splitNodeRunLength(Path path, List<String> list, int i) {
        int i2 = i + 1;
        String pathToKey = pathToKey(path);
        if (((String) list.get(i)).startsWith(pathToKey)) {
            while (i2 < list.size() && ((String) list.get(i2)).equals(partKey(path, i2 - i))) {
                i2++;
            }
            if (i2 < list.size()) {
                String str = (String) list.get(i2);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(pathToKey);
                stringBuilder.append(PART_KEY_PREFIX);
                if (str.startsWith(stringBuilder.toString())) {
                    throw new IllegalStateException("Run did not finish with all parts");
                }
            }
            return i2 - i;
        }
        throw new IllegalStateException("Extracting split nodes needs to start with path prefix");
    }

    private Cursor loadNestedQuery(Path path, String[] strArr) {
        String pathToKey = pathToKey(path);
        String pathPrefixStartToPrefixEnd = pathPrefixStartToPrefixEnd(pathToKey);
        String[] strArr2 = new String[(path.size() + 3)];
        String buildAncestorWhereClause = buildAncestorWhereClause(path, strArr2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(buildAncestorWhereClause);
        stringBuilder.append(" OR (path > ? AND path < ?)");
        String stringBuilder2 = stringBuilder.toString();
        strArr2[path.size() + 1] = pathToKey;
        strArr2[path.size() + 2] = pathPrefixStartToPrefixEnd;
        return this.database.query(SERVER_CACHE_TABLE, strArr, stringBuilder2, strArr2, null, null, RNFetchBlobConst.RNFB_RESPONSE_PATH);
    }

    private static String pathToKey(Path path) {
        String str = "/";
        if (path.isEmpty()) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path.toString());
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    private static String pathPrefixStartToPrefixEnd(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str.substring(0, str.length() - 1));
        stringBuilder.append('0');
        return stringBuilder.toString();
    }

    private static String buildAncestorWhereClause(Path path, String[] strArr) {
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder("(");
        while (true) {
            boolean isEmpty = path.isEmpty();
            String str = RNFetchBlobConst.RNFB_RESPONSE_PATH;
            if (isEmpty) {
                stringBuilder.append(str);
                stringBuilder.append(" = ?)");
                strArr[i] = pathToKey(Path.getEmptyPath());
                return stringBuilder.toString();
            }
            stringBuilder.append(str);
            stringBuilder.append(" = ? OR ");
            strArr[i] = pathToKey(path);
            path = path.getParent();
            i++;
        }
    }

    private int removeNested(String str, Path path) {
        String pathPrefixStartToPrefixEnd = pathPrefixStartToPrefixEnd(pathToKey(path));
        return this.database.delete(str, "path >= ? AND path < ?", new String[]{r6, pathPrefixStartToPrefixEnd});
    }

    private static List<byte[]> splitBytes(byte[] bArr, int i) {
        int length = ((bArr.length - 1) / i) + 1;
        List<byte[]> arrayList = new ArrayList(length);
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * i;
            int min = Math.min(i, bArr.length - i3);
            Object obj = new byte[min];
            System.arraycopy(bArr, i3, obj, 0, min);
            arrayList.add(obj);
        }
        return arrayList;
    }

    private byte[] joinBytes(List<byte[]> list) {
        int i = 0;
        for (byte[] length : list) {
            i += length.length;
        }
        Object obj = new byte[i];
        i = 0;
        for (byte[] length2 : list) {
            System.arraycopy(length2, 0, obj, i, length2.length);
            i += length2.length;
        }
        return obj;
    }

    private byte[] serializeObject(Object obj) {
        try {
            return JsonMapper.serializeJsonValue(obj).getBytes(UTF8_CHARSET);
        } catch (Throwable e) {
            throw new RuntimeException("Could not serialize leaf node", e);
        }
    }

    private Node deserializeNode(byte[] bArr) {
        try {
            bArr = NodeUtilities.NodeFromJSON(JsonMapper.parseJsonValue(new String(bArr, UTF8_CHARSET)));
            return bArr;
        } catch (Throwable e) {
            String str = new String(bArr, UTF8_CHARSET);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not deserialize node: ");
            stringBuilder.append(str);
            throw new RuntimeException(stringBuilder.toString(), e);
        }
    }

    private String commaSeparatedList(Collection<Long> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (Long longValue : collection) {
            long longValue2 = longValue.longValue();
            if (obj == null) {
                stringBuilder.append(",");
            }
            obj = null;
            stringBuilder.append(longValue2);
        }
        return stringBuilder.toString();
    }
}
