package com.reactnativecommunity.asyncstorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.media.session.PlaybackStateCompat;
import javax.annotation.Nullable;

public class ReactDatabaseSupplier extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "RKStorage";
    private static final int DATABASE_VERSION = 1;
    static final String KEY_COLUMN = "key";
    private static final int SLEEP_TIME_MS = 30;
    static final String TABLE_CATALYST = "catalystLocalStorage";
    static final String VALUE_COLUMN = "value";
    static final String VERSION_TABLE_CREATE = "CREATE TABLE catalystLocalStorage (key TEXT PRIMARY KEY, value TEXT NOT NULL)";
    @Nullable
    private static ReactDatabaseSupplier sReactDatabaseSupplierInstance;
    private Context mContext;
    @Nullable
    private SQLiteDatabase mDb;
    private long mMaximumDatabaseSize = ((BuildConfig.AsyncStorage_db_size.longValue() * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);

    private ReactDatabaseSupplier(Context context) {
        super(context, "RKStorage", null, 1);
        this.mContext = context;
    }

    public static ReactDatabaseSupplier getInstance(Context context) {
        if (sReactDatabaseSupplierInstance == null) {
            sReactDatabaseSupplierInstance = new ReactDatabaseSupplier(context.getApplicationContext());
        }
        return sReactDatabaseSupplierInstance;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(VERSION_TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i != i2) {
            deleteDatabase();
            onCreate(sQLiteDatabase);
        }
    }

    /* JADX WARNING: Missing block: B:35:0x002f, code:
            continue;
     */
    synchronized boolean ensureDatabase() {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = r5.mDb;	 Catch:{ all -> 0x0040 }
        r1 = 1;
        if (r0 == 0) goto L_0x0010;
    L_0x0006:
        r0 = r5.mDb;	 Catch:{ all -> 0x0040 }
        r0 = r0.isOpen();	 Catch:{ all -> 0x0040 }
        if (r0 == 0) goto L_0x0010;
    L_0x000e:
        monitor-exit(r5);
        return r1;
    L_0x0010:
        r0 = 0;
        r2 = 0;
    L_0x0012:
        r3 = 2;
        if (r2 >= r3) goto L_0x0032;
    L_0x0015:
        if (r2 <= 0) goto L_0x001a;
    L_0x0017:
        r5.deleteDatabase();	 Catch:{ SQLiteException -> 0x0021 }
    L_0x001a:
        r3 = r5.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0021 }
        r5.mDb = r3;	 Catch:{ SQLiteException -> 0x0021 }
        goto L_0x0032;
    L_0x0021:
        r0 = move-exception;
        r3 = 30;
        java.lang.Thread.sleep(r3);	 Catch:{ InterruptedException -> 0x0028 }
        goto L_0x002f;
    L_0x0028:
        r3 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0040 }
        r3.interrupt();	 Catch:{ all -> 0x0040 }
    L_0x002f:
        r2 = r2 + 1;
        goto L_0x0012;
    L_0x0032:
        r2 = r5.mDb;	 Catch:{ all -> 0x0040 }
        if (r2 == 0) goto L_0x003f;
    L_0x0036:
        r0 = r5.mDb;	 Catch:{ all -> 0x0040 }
        r2 = r5.mMaximumDatabaseSize;	 Catch:{ all -> 0x0040 }
        r0.setMaximumSize(r2);	 Catch:{ all -> 0x0040 }
        monitor-exit(r5);
        return r1;
    L_0x003f:
        throw r0;	 Catch:{ all -> 0x0040 }
    L_0x0040:
        r0 = move-exception;
        monitor-exit(r5);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactnativecommunity.asyncstorage.ReactDatabaseSupplier.ensureDatabase():boolean");
    }

    public synchronized SQLiteDatabase get() {
        ensureDatabase();
        return this.mDb;
    }

    /* JADX WARNING: Missing block: B:8:0x0016, code:
            if (deleteDatabase() != false) goto L_0x0018;
     */
    /* JADX WARNING: Missing block: B:9:0x0018, code:
            com.facebook.common.logging.FLog.d(com.facebook.react.common.ReactConstants.TAG, "Deleted Local Database RKStorage");
     */
    /* JADX WARNING: Missing block: B:11:0x0020, code:
            return;
     */
    /* JADX WARNING: Missing block: B:14:0x0028, code:
            throw new java.lang.RuntimeException("Clearing and deleting database RKStorage failed");
     */
    public synchronized void clearAndCloseDatabase() throws java.lang.RuntimeException {
        /*
        r2 = this;
        monitor-enter(r2);
        r2.clear();	 Catch:{ Exception -> 0x0012 }
        r2.closeDatabase();	 Catch:{ Exception -> 0x0012 }
        r0 = "ReactNative";
        r1 = "Cleaned RKStorage";
        com.facebook.common.logging.FLog.d(r0, r1);	 Catch:{ Exception -> 0x0012 }
        monitor-exit(r2);
        return;
    L_0x0010:
        r0 = move-exception;
        goto L_0x0029;
    L_0x0012:
        r0 = r2.deleteDatabase();	 Catch:{ all -> 0x0010 }
        if (r0 == 0) goto L_0x0021;
    L_0x0018:
        r0 = "ReactNative";
        r1 = "Deleted Local Database RKStorage";
        com.facebook.common.logging.FLog.d(r0, r1);	 Catch:{ all -> 0x0010 }
        monitor-exit(r2);
        return;
    L_0x0021:
        r0 = new java.lang.RuntimeException;	 Catch:{ all -> 0x0010 }
        r1 = "Clearing and deleting database RKStorage failed";
        r0.<init>(r1);	 Catch:{ all -> 0x0010 }
        throw r0;	 Catch:{ all -> 0x0010 }
    L_0x0029:
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactnativecommunity.asyncstorage.ReactDatabaseSupplier.clearAndCloseDatabase():void");
    }

    synchronized void clear() {
        get().delete(TABLE_CATALYST, null, null);
    }

    public synchronized void setMaximumSize(long j) {
        this.mMaximumDatabaseSize = j;
        if (this.mDb != null) {
            this.mDb.setMaximumSize(this.mMaximumDatabaseSize);
        }
    }

    private synchronized boolean deleteDatabase() {
        closeDatabase();
        return this.mContext.deleteDatabase("RKStorage");
    }

    private synchronized void closeDatabase() {
        if (this.mDb != null && this.mDb.isOpen()) {
            this.mDb.close();
            this.mDb = null;
        }
    }

    public static void deleteInstance() {
        sReactDatabaseSupplierInstance = null;
    }
}
