package com.facebook.react.modules.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.common.ModuleDataCleaner.Cleanable;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;

@ReactModule(name = "AsyncSQLiteDBStorage")
public final class AsyncStorageModule extends ReactContextBaseJavaModule implements Cleanable {
    private static final int MAX_SQL_KEYS = 999;
    public static final String NAME = "AsyncSQLiteDBStorage";
    private final SerialExecutor executor;
    private ReactDatabaseSupplier mReactDatabaseSupplier;
    private boolean mShuttingDown;

    private class SerialExecutor implements Executor {
        private final Executor executor;
        private Runnable mActive;
        private final ArrayDeque<Runnable> mTasks = new ArrayDeque();

        SerialExecutor(Executor executor) {
            this.executor = executor;
        }

        public synchronized void execute(final Runnable runnable) {
            this.mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        SerialExecutor.this.scheduleNext();
                    }
                }
            });
            if (this.mActive == null) {
                scheduleNext();
            }
        }

        synchronized void scheduleNext() {
            Runnable runnable = (Runnable) this.mTasks.poll();
            this.mActive = runnable;
            if (runnable != null) {
                this.executor.execute(this.mActive);
            }
        }
    }

    public String getName() {
        return NAME;
    }

    public AsyncStorageModule(ReactApplicationContext reactApplicationContext) {
        this(reactApplicationContext, AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @VisibleForTesting
    AsyncStorageModule(ReactApplicationContext reactApplicationContext, Executor executor) {
        super(reactApplicationContext);
        this.mShuttingDown = false;
        this.executor = new SerialExecutor(executor);
        this.mReactDatabaseSupplier = ReactDatabaseSupplier.getInstance(reactApplicationContext);
    }

    public void initialize() {
        super.initialize();
        this.mShuttingDown = false;
    }

    public void onCatalystInstanceDestroy() {
        this.mShuttingDown = true;
    }

    public void clearSensitiveData() {
        this.mReactDatabaseSupplier.clearAndCloseDatabase();
    }

    @ReactMethod
    public void multiGet(final ReadableArray readableArray, final Callback callback) {
        if (readableArray == null) {
            callback.invoke(AsyncStorageErrorUtil.getInvalidKeyError(null), null);
            return;
        }
        new GuardedAsyncTask<Void, Void>(access$200()) {
            protected void doInBackgroundGuarded(Void... voidArr) {
                if (AsyncStorageModule.this.ensureDatabase()) {
                    String[] strArr = new String[]{"key", "value"};
                    HashSet hashSet = new HashSet();
                    WritableArray createArray = Arguments.createArray();
                    while (0 < readableArray.size()) {
                        int min = Math.min(readableArray.size() - 0, 999);
                        int i = min;
                        int i2 = 0;
                        Cursor query = AsyncStorageModule.this.mReactDatabaseSupplier.get().query("catalystLocalStorage", strArr, AsyncLocalStorageUtil.buildKeySelection(min), AsyncLocalStorageUtil.buildKeySelectionArgs(readableArray, 0, min), null, null, null);
                        hashSet.clear();
                        try {
                            if (query.getCount() != readableArray.size()) {
                                for (int i3 = i2; i3 < i2 + i; i3++) {
                                    hashSet.add(readableArray.getString(i3));
                                }
                            }
                            if (query.moveToFirst()) {
                                do {
                                    WritableArray createArray2 = Arguments.createArray();
                                    createArray2.pushString(query.getString(0));
                                    createArray2.pushString(query.getString(1));
                                    createArray.pushArray(createArray2);
                                    hashSet.remove(query.getString(0));
                                } while (query.moveToNext());
                            }
                            query.close();
                            Iterator it = hashSet.iterator();
                            while (it.hasNext()) {
                                String str = (String) it.next();
                                WritableArray createArray3 = Arguments.createArray();
                                createArray3.pushString(str);
                                createArray3.pushNull();
                                createArray.pushArray(createArray3);
                            }
                            hashSet.clear();
                            int i4 = i2 + 999;
                        } catch (Throwable e) {
                            FLog.w(ReactConstants.TAG, e.getMessage(), e);
                            callback.invoke(AsyncStorageErrorUtil.getError(null, e.getMessage()), null);
                            query.close();
                            return;
                        } catch (Throwable e2) {
                            query.close();
                            throw e2;
                        }
                    }
                    callback.invoke(null, createArray);
                    return;
                }
                callback.invoke(AsyncStorageErrorUtil.getDBError(null), null);
            }
        }.executeOnExecutor(this.executor, new Void[0]);
    }

    @ReactMethod
    public void multiSet(final ReadableArray readableArray, final Callback callback) {
        if (readableArray.size() == 0) {
            callback.invoke(AsyncStorageErrorUtil.getInvalidKeyError(null));
            return;
        }
        new GuardedAsyncTask<Void, Void>(access$200()) {
            protected void doInBackgroundGuarded(Void... voidArr) {
                WritableMap invalidValueError;
                String str = ReactConstants.TAG;
                String str2 = null;
                if (AsyncStorageModule.this.ensureDatabase()) {
                    SQLiteStatement compileStatement = AsyncStorageModule.this.mReactDatabaseSupplier.get().compileStatement("INSERT OR REPLACE INTO catalystLocalStorage VALUES (?, ?);");
                    try {
                        AsyncStorageModule.this.mReactDatabaseSupplier.get().beginTransaction();
                        int i = 0;
                        while (i < readableArray.size()) {
                            if (readableArray.getArray(i).size() != 2) {
                                invalidValueError = AsyncStorageErrorUtil.getInvalidValueError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                } catch (Throwable e) {
                                    FLog.w(str, e.getMessage(), e);
                                    if (invalidValueError == null) {
                                        AsyncStorageErrorUtil.getError(null, e.getMessage());
                                    }
                                }
                                return;
                            } else if (readableArray.getArray(i).getString(0) == null) {
                                invalidValueError = AsyncStorageErrorUtil.getInvalidKeyError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                } catch (Throwable e2) {
                                    FLog.w(str, e2.getMessage(), e2);
                                    if (invalidValueError == null) {
                                        AsyncStorageErrorUtil.getError(null, e2.getMessage());
                                    }
                                }
                                return;
                            } else if (readableArray.getArray(i).getString(1) == null) {
                                invalidValueError = AsyncStorageErrorUtil.getInvalidValueError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                } catch (Throwable e22) {
                                    FLog.w(str, e22.getMessage(), e22);
                                    if (invalidValueError == null) {
                                        AsyncStorageErrorUtil.getError(null, e22.getMessage());
                                    }
                                }
                                return;
                            } else {
                                compileStatement.clearBindings();
                                compileStatement.bindString(1, readableArray.getArray(i).getString(0));
                                compileStatement.bindString(2, readableArray.getArray(i).getString(1));
                                compileStatement.execute();
                                i++;
                            }
                        }
                        AsyncStorageModule.this.mReactDatabaseSupplier.get().setTransactionSuccessful();
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Throwable e3) {
                            FLog.w(str, e3.getMessage(), e3);
                            str2 = AsyncStorageErrorUtil.getError(null, e3.getMessage());
                        }
                    } catch (Throwable e32) {
                        FLog.w(str, e32.getMessage(), e32);
                        invalidValueError = AsyncStorageErrorUtil.getError(null, e32.getMessage());
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Throwable e4) {
                            FLog.w(str, e4.getMessage(), e4);
                            if (invalidValueError == null) {
                                str2 = AsyncStorageErrorUtil.getError(null, e4.getMessage());
                            }
                        }
                        str2 = invalidValueError;
                    } catch (Throwable e322) {
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Throwable e222) {
                            FLog.w(str, e222.getMessage(), e222);
                            AsyncStorageErrorUtil.getError(null, e222.getMessage());
                        }
                        throw e322;
                    }
                    if (str2 != null) {
                        callback.invoke(str2);
                    } else {
                        callback.invoke(new Object[0]);
                    }
                    return;
                }
                callback.invoke(AsyncStorageErrorUtil.getDBError(null));
            }
        }.executeOnExecutor(this.executor, new Void[0]);
    }

    @ReactMethod
    public void multiRemove(final ReadableArray readableArray, final Callback callback) {
        if (readableArray.size() == 0) {
            callback.invoke(AsyncStorageErrorUtil.getInvalidKeyError(null));
            return;
        }
        new GuardedAsyncTask<Void, Void>(access$200()) {
            protected void doInBackgroundGuarded(Void... voidArr) {
                String str = ReactConstants.TAG;
                String str2 = null;
                if (AsyncStorageModule.this.ensureDatabase()) {
                    try {
                        AsyncStorageModule.this.mReactDatabaseSupplier.get().beginTransaction();
                        for (int i = 0; i < readableArray.size(); i += 999) {
                            int min = Math.min(readableArray.size() - i, 999);
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().delete("catalystLocalStorage", AsyncLocalStorageUtil.buildKeySelection(min), AsyncLocalStorageUtil.buildKeySelectionArgs(readableArray, i, min));
                        }
                        AsyncStorageModule.this.mReactDatabaseSupplier.get().setTransactionSuccessful();
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Throwable e) {
                            FLog.w(str, e.getMessage(), e);
                            str2 = AsyncStorageErrorUtil.getError(null, e.getMessage());
                        }
                    } catch (Throwable e2) {
                        FLog.w(str, e2.getMessage(), e2);
                        WritableMap error = AsyncStorageErrorUtil.getError(null, e2.getMessage());
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Throwable e3) {
                            FLog.w(str, e3.getMessage(), e3);
                            if (error == null) {
                                str2 = AsyncStorageErrorUtil.getError(null, e3.getMessage());
                            }
                        }
                        str2 = error;
                    } catch (Throwable e22) {
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Throwable e4) {
                            FLog.w(str, e4.getMessage(), e4);
                            AsyncStorageErrorUtil.getError(null, e4.getMessage());
                        }
                        throw e22;
                    }
                    if (str2 != null) {
                        callback.invoke(str2);
                    } else {
                        callback.invoke(new Object[0]);
                    }
                    return;
                }
                callback.invoke(AsyncStorageErrorUtil.getDBError(null));
            }
        }.executeOnExecutor(this.executor, new Void[0]);
    }

    @ReactMethod
    public void multiMerge(final ReadableArray readableArray, final Callback callback) {
        new GuardedAsyncTask<Void, Void>(access$200()) {
            protected void doInBackgroundGuarded(Void... voidArr) {
                WritableMap invalidValueError;
                String str = ReactConstants.TAG;
                String str2 = null;
                if (AsyncStorageModule.this.ensureDatabase()) {
                    try {
                        AsyncStorageModule.this.mReactDatabaseSupplier.get().beginTransaction();
                        int i = 0;
                        while (i < readableArray.size()) {
                            if (readableArray.getArray(i).size() != 2) {
                                invalidValueError = AsyncStorageErrorUtil.getInvalidValueError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                } catch (Throwable e) {
                                    FLog.w(str, e.getMessage(), e);
                                    if (invalidValueError == null) {
                                        AsyncStorageErrorUtil.getError(null, e.getMessage());
                                    }
                                }
                                return;
                            } else if (readableArray.getArray(i).getString(0) == null) {
                                invalidValueError = AsyncStorageErrorUtil.getInvalidKeyError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                } catch (Throwable e2) {
                                    FLog.w(str, e2.getMessage(), e2);
                                    if (invalidValueError == null) {
                                        AsyncStorageErrorUtil.getError(null, e2.getMessage());
                                    }
                                }
                                return;
                            } else if (readableArray.getArray(i).getString(1) == null) {
                                invalidValueError = AsyncStorageErrorUtil.getInvalidValueError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                } catch (Throwable e22) {
                                    FLog.w(str, e22.getMessage(), e22);
                                    if (invalidValueError == null) {
                                        AsyncStorageErrorUtil.getError(null, e22.getMessage());
                                    }
                                }
                                return;
                            } else if (AsyncLocalStorageUtil.mergeImpl(AsyncStorageModule.this.mReactDatabaseSupplier.get(), readableArray.getArray(i).getString(0), readableArray.getArray(i).getString(1))) {
                                i++;
                            } else {
                                invalidValueError = AsyncStorageErrorUtil.getDBError(null);
                                try {
                                    AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                                } catch (Throwable e222) {
                                    FLog.w(str, e222.getMessage(), e222);
                                    if (invalidValueError == null) {
                                        AsyncStorageErrorUtil.getError(null, e222.getMessage());
                                    }
                                }
                                return;
                            }
                        }
                        AsyncStorageModule.this.mReactDatabaseSupplier.get().setTransactionSuccessful();
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Throwable e3) {
                            FLog.w(str, e3.getMessage(), e3);
                            str2 = AsyncStorageErrorUtil.getError(null, e3.getMessage());
                        }
                    } catch (Throwable e32) {
                        FLog.w(str, e32.getMessage(), e32);
                        invalidValueError = AsyncStorageErrorUtil.getError(null, e32.getMessage());
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Throwable e4) {
                            FLog.w(str, e4.getMessage(), e4);
                            if (invalidValueError == null) {
                                str2 = AsyncStorageErrorUtil.getError(null, e4.getMessage());
                            }
                        }
                        str2 = invalidValueError;
                    } catch (Throwable e322) {
                        try {
                            AsyncStorageModule.this.mReactDatabaseSupplier.get().endTransaction();
                        } catch (Throwable e2222) {
                            FLog.w(str, e2222.getMessage(), e2222);
                            AsyncStorageErrorUtil.getError(null, e2222.getMessage());
                        }
                        throw e322;
                    }
                    if (str2 != null) {
                        callback.invoke(str2);
                    } else {
                        callback.invoke(new Object[0]);
                    }
                    return;
                }
                callback.invoke(AsyncStorageErrorUtil.getDBError(null));
            }
        }.executeOnExecutor(this.executor, new Void[0]);
    }

    @ReactMethod
    public void clear(final Callback callback) {
        new GuardedAsyncTask<Void, Void>(access$200()) {
            protected void doInBackgroundGuarded(Void... voidArr) {
                if (AsyncStorageModule.this.mReactDatabaseSupplier.ensureDatabase()) {
                    try {
                        AsyncStorageModule.this.mReactDatabaseSupplier.clear();
                        callback.invoke(new Object[0]);
                    } catch (Throwable e) {
                        FLog.w(ReactConstants.TAG, e.getMessage(), e);
                        callback.invoke(AsyncStorageErrorUtil.getError(null, e.getMessage()));
                    }
                    return;
                }
                callback.invoke(AsyncStorageErrorUtil.getDBError(null));
            }
        }.executeOnExecutor(this.executor, new Void[0]);
    }

    @ReactMethod
    public void getAllKeys(final Callback callback) {
        new GuardedAsyncTask<Void, Void>(access$200()) {
            protected void doInBackgroundGuarded(Void... voidArr) {
                if (AsyncStorageModule.this.ensureDatabase()) {
                    WritableArray createArray = Arguments.createArray();
                    Cursor query = AsyncStorageModule.this.mReactDatabaseSupplier.get().query("catalystLocalStorage", new String[]{"key"}, null, null, null, null, null);
                    try {
                        if (query.moveToFirst()) {
                            do {
                                createArray.pushString(query.getString(0));
                            } while (query.moveToNext());
                        }
                        query.close();
                        callback.invoke(null, createArray);
                        return;
                    } catch (Throwable e) {
                        FLog.w(ReactConstants.TAG, e.getMessage(), e);
                        callback.invoke(AsyncStorageErrorUtil.getError(null, e.getMessage()), null);
                        query.close();
                        return;
                    } catch (Throwable e2) {
                        query.close();
                        throw e2;
                    }
                }
                callback.invoke(AsyncStorageErrorUtil.getDBError(null), null);
            }
        }.executeOnExecutor(this.executor, new Void[0]);
    }

    private boolean ensureDatabase() {
        return !this.mShuttingDown && this.mReactDatabaseSupplier.ensureDatabase();
    }
}
