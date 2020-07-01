package com.facebook.cache.disk;

import android.content.Context;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.CacheErrorLogger.CacheErrorCategory;
import com.facebook.cache.common.CacheEvent;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.CacheEventListener.EvictionReason;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.CacheKeyUtil;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.DiskStorage.DiskDumpInfo;
import com.facebook.cache.disk.DiskStorage.Entry;
import com.facebook.cache.disk.DiskStorage.Inserter;
import com.facebook.common.disk.DiskTrimmable;
import com.facebook.common.disk.DiskTrimmableRegistry;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.common.statfs.StatFsHelper;
import com.facebook.common.statfs.StatFsHelper.StorageType;
import com.facebook.common.time.Clock;
import com.facebook.common.time.SystemClock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class DiskStorageCache implements FileCache, DiskTrimmable {
    private static final long FILECACHE_SIZE_UPDATE_PERIOD_MS = TimeUnit.MINUTES.toMillis(30);
    private static final long FUTURE_TIMESTAMP_THRESHOLD_MS = TimeUnit.HOURS.toMillis(2);
    private static final String SHARED_PREFS_FILENAME_PREFIX = "disk_entries_list";
    public static final int START_OF_VERSIONING = 1;
    private static final Class<?> TAG = DiskStorageCache.class;
    private static final double TRIMMING_LOWER_BOUND = 0.02d;
    private static final long UNINITIALIZED = -1;
    private final CacheErrorLogger mCacheErrorLogger;
    private final CacheEventListener mCacheEventListener;
    private long mCacheSizeLastUpdateTime;
    private long mCacheSizeLimit;
    private final long mCacheSizeLimitMinimum;
    private final CacheStats mCacheStats;
    private final Clock mClock;
    private final CountDownLatch mCountDownLatch;
    private final long mDefaultCacheSizeLimit;
    private final EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier;
    private final boolean mIndexPopulateAtStartupEnabled;
    private boolean mIndexReady;
    private final Object mLock = new Object();
    private final long mLowDiskSpaceCacheSizeLimit;
    @GuardedBy("mLock")
    @VisibleForTesting
    final Set<String> mResourceIndex;
    private final StatFsHelper mStatFsHelper;
    private final DiskStorage mStorage;

    @VisibleForTesting
    static class CacheStats {
        private long mCount = -1;
        private boolean mInitialized = false;
        private long mSize = -1;

        CacheStats() {
        }

        public synchronized boolean isInitialized() {
            return this.mInitialized;
        }

        public synchronized void reset() {
            this.mInitialized = false;
            this.mCount = -1;
            this.mSize = -1;
        }

        public synchronized void set(long j, long j2) {
            this.mCount = j2;
            this.mSize = j;
            this.mInitialized = true;
        }

        public synchronized void increment(long j, long j2) {
            if (this.mInitialized) {
                this.mSize += j;
                this.mCount += j2;
            }
        }

        public synchronized long getSize() {
            return this.mSize;
        }

        public synchronized long getCount() {
            return this.mCount;
        }
    }

    public static class Params {
        public final long mCacheSizeLimitMinimum;
        public final long mDefaultCacheSizeLimit;
        public final long mLowDiskSpaceCacheSizeLimit;

        public Params(long j, long j2, long j3) {
            this.mCacheSizeLimitMinimum = j;
            this.mLowDiskSpaceCacheSizeLimit = j2;
            this.mDefaultCacheSizeLimit = j3;
        }
    }

    public DiskStorageCache(DiskStorage diskStorage, EntryEvictionComparatorSupplier entryEvictionComparatorSupplier, Params params, CacheEventListener cacheEventListener, CacheErrorLogger cacheErrorLogger, @Nullable DiskTrimmableRegistry diskTrimmableRegistry, Context context, Executor executor, boolean z) {
        this.mLowDiskSpaceCacheSizeLimit = params.mLowDiskSpaceCacheSizeLimit;
        this.mDefaultCacheSizeLimit = params.mDefaultCacheSizeLimit;
        this.mCacheSizeLimit = params.mDefaultCacheSizeLimit;
        this.mStatFsHelper = StatFsHelper.getInstance();
        this.mStorage = diskStorage;
        this.mEntryEvictionComparatorSupplier = entryEvictionComparatorSupplier;
        this.mCacheSizeLastUpdateTime = -1;
        this.mCacheEventListener = cacheEventListener;
        this.mCacheSizeLimitMinimum = params.mCacheSizeLimitMinimum;
        this.mCacheErrorLogger = cacheErrorLogger;
        this.mCacheStats = new CacheStats();
        this.mClock = SystemClock.get();
        this.mIndexPopulateAtStartupEnabled = z;
        this.mResourceIndex = new HashSet();
        if (diskTrimmableRegistry != null) {
            diskTrimmableRegistry.registerDiskTrimmable(this);
        }
        if (this.mIndexPopulateAtStartupEnabled) {
            this.mCountDownLatch = new CountDownLatch(1);
            executor.execute(new Runnable() {
                public void run() {
                    synchronized (DiskStorageCache.this.mLock) {
                        DiskStorageCache.this.maybeUpdateFileCacheSize();
                    }
                    DiskStorageCache.this.mIndexReady = true;
                    DiskStorageCache.this.mCountDownLatch.countDown();
                }
            });
            return;
        }
        this.mCountDownLatch = new CountDownLatch(0);
    }

    public DiskDumpInfo getDumpInfo() throws IOException {
        return this.mStorage.getDumpInfo();
    }

    public boolean isEnabled() {
        return this.mStorage.isEnabled();
    }

    @VisibleForTesting
    protected void awaitIndex() {
        try {
            this.mCountDownLatch.await();
        } catch (InterruptedException unused) {
            FLog.e(TAG, "Memory Index is not ready yet. ");
        }
    }

    public boolean isIndexReady() {
        return this.mIndexReady || !this.mIndexPopulateAtStartupEnabled;
    }

    @Nullable
    public BinaryResource getResource(CacheKey cacheKey) {
        Object cacheKey2 = SettableCacheEvent.obtain().setCacheKey(cacheKey);
        try {
            BinaryResource binaryResource;
            synchronized (this.mLock) {
                List resourceIds = CacheKeyUtil.getResourceIds(cacheKey);
                Object obj = null;
                binaryResource = obj;
                for (int i = 0; i < resourceIds.size(); i++) {
                    obj = (String) resourceIds.get(i);
                    cacheKey2.setResourceId(obj);
                    binaryResource = this.mStorage.getResource(obj, cacheKey);
                    if (binaryResource != null) {
                        break;
                    }
                }
                if (binaryResource == null) {
                    this.mCacheEventListener.onMiss(cacheKey2);
                    this.mResourceIndex.remove(obj);
                } else {
                    this.mCacheEventListener.onHit(cacheKey2);
                    this.mResourceIndex.add(obj);
                }
            }
            cacheKey2.recycle();
            return binaryResource;
        } catch (Throwable e) {
            try {
                this.mCacheErrorLogger.logError(CacheErrorCategory.GENERIC_IO, TAG, "getResource", e);
                cacheKey2.setException(e);
                this.mCacheEventListener.onReadException(cacheKey2);
                return null;
            } finally {
                cacheKey2.recycle();
            }
        }
    }

    public boolean probe(CacheKey cacheKey) {
        Throwable th;
        IOException e;
        String str = null;
        try {
            synchronized (this.mLock) {
                String str2;
                try {
                    List resourceIds = CacheKeyUtil.getResourceIds(cacheKey);
                    String str3 = null;
                    int i = 0;
                    while (i < resourceIds.size()) {
                        try {
                            str2 = (String) resourceIds.get(i);
                            try {
                                if (this.mStorage.touch(str2, cacheKey)) {
                                    this.mResourceIndex.add(str2);
                                    return true;
                                }
                                i++;
                                str3 = str2;
                            } catch (Throwable th2) {
                                th = th2;
                                try {
                                    throw th;
                                } catch (IOException e2) {
                                    e = e2;
                                    str = str2;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            str2 = str3;
                            throw th;
                        }
                    }
                    return false;
                } catch (Throwable th4) {
                    str2 = null;
                    th = th4;
                    throw th;
                }
            }
        } catch (IOException e3) {
            e = e3;
            Object exception = SettableCacheEvent.obtain().setCacheKey(cacheKey).setResourceId(str).setException(e);
            this.mCacheEventListener.onReadException(exception);
            exception.recycle();
            return false;
        }
    }

    private Inserter startInsert(String str, CacheKey cacheKey) throws IOException {
        maybeEvictFilesInCacheDir();
        return this.mStorage.insert(str, cacheKey);
    }

    private BinaryResource endInsert(Inserter inserter, CacheKey cacheKey, String str) throws IOException {
        BinaryResource commit;
        synchronized (this.mLock) {
            commit = inserter.commit(cacheKey);
            this.mResourceIndex.add(str);
            this.mCacheStats.increment(commit.size(), 1);
        }
        return commit;
    }

    public BinaryResource insert(CacheKey cacheKey, WriterCallback writerCallback) throws IOException {
        String firstResourceId;
        CacheEvent cacheKey2 = SettableCacheEvent.obtain().setCacheKey(cacheKey);
        this.mCacheEventListener.onWriteAttempt(cacheKey2);
        synchronized (this.mLock) {
            firstResourceId = CacheKeyUtil.getFirstResourceId(cacheKey);
        }
        cacheKey2.setResourceId(firstResourceId);
        Inserter startInsert;
        try {
            startInsert = startInsert(firstResourceId, cacheKey);
            startInsert.writeData(writerCallback, cacheKey);
            BinaryResource endInsert = endInsert(startInsert, cacheKey, firstResourceId);
            cacheKey2.setItemSize(endInsert.size()).setCacheSize(this.mCacheStats.getSize());
            this.mCacheEventListener.onWriteSuccess(cacheKey2);
            if (!startInsert.cleanUp()) {
                FLog.e(TAG, "Failed to delete temp file");
            }
            cacheKey2.recycle();
            return endInsert;
        } catch (Throwable e) {
            try {
                cacheKey2.setException(e);
                this.mCacheEventListener.onWriteException(cacheKey2);
                FLog.e(TAG, "Failed inserting a file into the cache", e);
                throw e;
            } catch (Throwable th) {
                cacheKey2.recycle();
            }
        } catch (Throwable th2) {
            if (!startInsert.cleanUp()) {
                FLog.e(TAG, "Failed to delete temp file");
            }
        }
    }

    public void remove(CacheKey cacheKey) {
        synchronized (this.mLock) {
            try {
                List resourceIds = CacheKeyUtil.getResourceIds(cacheKey);
                for (int i = 0; i < resourceIds.size(); i++) {
                    String str = (String) resourceIds.get(i);
                    this.mStorage.remove(str);
                    this.mResourceIndex.remove(str);
                }
            } catch (Throwable e) {
                CacheErrorLogger cacheErrorLogger = this.mCacheErrorLogger;
                CacheErrorCategory cacheErrorCategory = CacheErrorCategory.DELETE_FILE;
                Class cls = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("delete: ");
                stringBuilder.append(e.getMessage());
                cacheErrorLogger.logError(cacheErrorCategory, cls, stringBuilder.toString(), e);
            }
        }
    }

    public long clearOldEntries(long j) {
        long j2;
        Throwable e;
        CacheErrorLogger cacheErrorLogger;
        CacheErrorCategory cacheErrorCategory;
        Class cls;
        StringBuilder stringBuilder;
        synchronized (this.mLock) {
            try {
                long now = this.mClock.now();
                Collection<Entry> entries = this.mStorage.getEntries();
                long size = this.mCacheStats.getSize();
                int i = 0;
                long j3 = 0;
                j2 = 0;
                for (Entry entry : entries) {
                    try {
                        long j4 = now;
                        long max = Math.max(1, Math.abs(now - entry.getTimestamp()));
                        if (max >= j) {
                            max = this.mStorage.remove(entry);
                            this.mResourceIndex.remove(entry.getId());
                            if (max > 0) {
                                i++;
                                j3 += max;
                                Object cacheSize = SettableCacheEvent.obtain().setResourceId(entry.getId()).setEvictionReason(EvictionReason.CONTENT_STALE).setItemSize(max).setCacheSize(size - j3);
                                this.mCacheEventListener.onEviction(cacheSize);
                                cacheSize.recycle();
                            }
                        } else {
                            j2 = Math.max(j2, max);
                        }
                        now = j4;
                    } catch (IOException e2) {
                        e = e2;
                        cacheErrorLogger = this.mCacheErrorLogger;
                        cacheErrorCategory = CacheErrorCategory.EVICTION;
                        cls = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("clearOldEntries: ");
                        stringBuilder.append(e.getMessage());
                        cacheErrorLogger.logError(cacheErrorCategory, cls, stringBuilder.toString(), e);
                        return j2;
                    }
                }
                this.mStorage.purgeUnexpectedResources();
                if (i > 0) {
                    maybeUpdateFileCacheSize();
                    this.mCacheStats.increment(-j3, (long) (-i));
                }
            } catch (IOException e3) {
                e = e3;
                j2 = 0;
                cacheErrorLogger = this.mCacheErrorLogger;
                cacheErrorCategory = CacheErrorCategory.EVICTION;
                cls = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("clearOldEntries: ");
                stringBuilder.append(e.getMessage());
                cacheErrorLogger.logError(cacheErrorCategory, cls, stringBuilder.toString(), e);
                return j2;
            }
        }
        return j2;
    }

    private void maybeEvictFilesInCacheDir() throws IOException {
        synchronized (this.mLock) {
            boolean maybeUpdateFileCacheSize = maybeUpdateFileCacheSize();
            updateFileCacheSizeLimit();
            long size = this.mCacheStats.getSize();
            if (size > this.mCacheSizeLimit && !maybeUpdateFileCacheSize) {
                this.mCacheStats.reset();
                maybeUpdateFileCacheSize();
            }
            if (size > this.mCacheSizeLimit) {
                evictAboveSize((this.mCacheSizeLimit * 9) / 10, EvictionReason.CACHE_FULL);
            }
        }
    }

    @GuardedBy("mLock")
    private void evictAboveSize(long j, EvictionReason evictionReason) throws IOException {
        long j2 = j;
        try {
            Collection<Entry> sortedEntries = getSortedEntries(this.mStorage.getEntries());
            long size = this.mCacheStats.getSize();
            long j3 = size - j2;
            int i = 0;
            long j4 = 0;
            for (Entry entry : sortedEntries) {
                if (j4 > j3) {
                    break;
                }
                long remove = this.mStorage.remove(entry);
                this.mResourceIndex.remove(entry.getId());
                if (remove > 0) {
                    i++;
                    j4 += remove;
                    Object cacheLimit = SettableCacheEvent.obtain().setResourceId(entry.getId()).setEvictionReason(evictionReason).setItemSize(remove).setCacheSize(size - j4).setCacheLimit(j2);
                    this.mCacheEventListener.onEviction(cacheLimit);
                    cacheLimit.recycle();
                } else {
                    EvictionReason evictionReason2 = evictionReason;
                }
            }
            this.mCacheStats.increment(-j4, (long) (-i));
            this.mStorage.purgeUnexpectedResources();
        } catch (Throwable e) {
            CacheErrorLogger cacheErrorLogger = this.mCacheErrorLogger;
            CacheErrorCategory cacheErrorCategory = CacheErrorCategory.EVICTION;
            Class cls = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("evictAboveSize: ");
            stringBuilder.append(e.getMessage());
            cacheErrorLogger.logError(cacheErrorCategory, cls, stringBuilder.toString(), e);
            throw e;
        }
    }

    private Collection<Entry> getSortedEntries(Collection<Entry> collection) {
        long now = this.mClock.now() + FUTURE_TIMESTAMP_THRESHOLD_MS;
        Collection arrayList = new ArrayList(collection.size());
        Collection arrayList2 = new ArrayList(collection.size());
        for (Entry entry : collection) {
            if (entry.getTimestamp() > now) {
                arrayList.add(entry);
            } else {
                arrayList2.add(entry);
            }
        }
        Collections.sort(arrayList2, this.mEntryEvictionComparatorSupplier.get());
        arrayList.addAll(arrayList2);
        return arrayList;
    }

    @GuardedBy("mLock")
    private void updateFileCacheSizeLimit() {
        if (this.mStatFsHelper.testLowDiskSpace(this.mStorage.isExternal() ? StorageType.EXTERNAL : StorageType.INTERNAL, this.mDefaultCacheSizeLimit - this.mCacheStats.getSize())) {
            this.mCacheSizeLimit = this.mLowDiskSpaceCacheSizeLimit;
        } else {
            this.mCacheSizeLimit = this.mDefaultCacheSizeLimit;
        }
    }

    public long getSize() {
        return this.mCacheStats.getSize();
    }

    public long getCount() {
        return this.mCacheStats.getCount();
    }

    public void clearAll() {
        Throwable e;
        synchronized (this.mLock) {
            try {
                this.mStorage.clearAll();
                this.mResourceIndex.clear();
                this.mCacheEventListener.onCleared();
            } catch (IOException e2) {
                e = e2;
            } catch (NullPointerException e3) {
                e = e3;
            }
            this.mCacheStats.reset();
        }
        CacheErrorLogger cacheErrorLogger = this.mCacheErrorLogger;
        CacheErrorCategory cacheErrorCategory = CacheErrorCategory.EVICTION;
        Class cls = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("clearAll: ");
        stringBuilder.append(e.getMessage());
        cacheErrorLogger.logError(cacheErrorCategory, cls, stringBuilder.toString(), e);
        this.mCacheStats.reset();
    }

    public boolean hasKeySync(CacheKey cacheKey) {
        synchronized (this.mLock) {
            List resourceIds = CacheKeyUtil.getResourceIds(cacheKey);
            for (int i = 0; i < resourceIds.size(); i++) {
                if (this.mResourceIndex.contains((String) resourceIds.get(i))) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean hasKey(CacheKey cacheKey) {
        synchronized (this.mLock) {
            if (hasKeySync(cacheKey)) {
                return true;
            }
            try {
                List resourceIds = CacheKeyUtil.getResourceIds(cacheKey);
                for (int i = 0; i < resourceIds.size(); i++) {
                    String str = (String) resourceIds.get(i);
                    if (this.mStorage.contains(str, cacheKey)) {
                        this.mResourceIndex.add(str);
                        return true;
                    }
                }
                return false;
            } catch (IOException unused) {
                return false;
            }
        }
    }

    /* JADX WARNING: Missing block: B:13:0x0034, code:
            return;
     */
    /* JADX WARNING: Missing block: B:15:0x0036, code:
            return;
     */
    public void trimToMinimum() {
        /*
        r8 = this;
        r0 = r8.mLock;
        monitor-enter(r0);
        r8.maybeUpdateFileCacheSize();	 Catch:{ all -> 0x0037 }
        r1 = r8.mCacheStats;	 Catch:{ all -> 0x0037 }
        r1 = r1.getSize();	 Catch:{ all -> 0x0037 }
        r3 = r8.mCacheSizeLimitMinimum;	 Catch:{ all -> 0x0037 }
        r5 = 0;
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r7 <= 0) goto L_0x0035;
    L_0x0014:
        r3 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1));
        if (r3 <= 0) goto L_0x0035;
    L_0x0018:
        r3 = r8.mCacheSizeLimitMinimum;	 Catch:{ all -> 0x0037 }
        r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
        if (r5 >= 0) goto L_0x001f;
    L_0x001e:
        goto L_0x0035;
    L_0x001f:
        r3 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r5 = r8.mCacheSizeLimitMinimum;	 Catch:{ all -> 0x0037 }
        r5 = (double) r5;	 Catch:{ all -> 0x0037 }
        r1 = (double) r1;	 Catch:{ all -> 0x0037 }
        r5 = r5 / r1;
        r3 = r3 - r5;
        r1 = 4581421828931458171; // 0x3f947ae147ae147b float:89128.96 double:0.02;
        r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1));
        if (r5 <= 0) goto L_0x0033;
    L_0x0030:
        r8.trimBy(r3);	 Catch:{ all -> 0x0037 }
    L_0x0033:
        monitor-exit(r0);	 Catch:{ all -> 0x0037 }
        return;
    L_0x0035:
        monitor-exit(r0);	 Catch:{ all -> 0x0037 }
        return;
    L_0x0037:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0037 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.cache.disk.DiskStorageCache.trimToMinimum():void");
    }

    public void trimToNothing() {
        clearAll();
    }

    private void trimBy(double d) {
        synchronized (this.mLock) {
            try {
                this.mCacheStats.reset();
                maybeUpdateFileCacheSize();
                long size = this.mCacheStats.getSize();
                evictAboveSize(size - ((long) (d * ((double) size))), EvictionReason.CACHE_MANAGER_TRIMMED);
            } catch (Throwable e) {
                CacheErrorLogger cacheErrorLogger = this.mCacheErrorLogger;
                CacheErrorCategory cacheErrorCategory = CacheErrorCategory.EVICTION;
                Class cls = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("trimBy: ");
                stringBuilder.append(e.getMessage());
                cacheErrorLogger.logError(cacheErrorCategory, cls, stringBuilder.toString(), e);
            }
        }
    }

    @GuardedBy("mLock")
    private boolean maybeUpdateFileCacheSize() {
        long now = this.mClock.now();
        if (this.mCacheStats.isInitialized()) {
            long j = this.mCacheSizeLastUpdateTime;
            if (j != -1 && now - j <= FILECACHE_SIZE_UPDATE_PERIOD_MS) {
                return false;
            }
        }
        return maybeUpdateFileCacheSizeAndIndex();
    }

    @GuardedBy("mLock")
    private boolean maybeUpdateFileCacheSizeAndIndex() {
        long now = this.mClock.now();
        long j = FUTURE_TIMESTAMP_THRESHOLD_MS + now;
        Set hashSet = (this.mIndexPopulateAtStartupEnabled && this.mResourceIndex.isEmpty()) ? this.mResourceIndex : this.mIndexPopulateAtStartupEnabled ? new HashSet() : null;
        try {
            long j2 = -1;
            int i = 0;
            int i2 = 0;
            long j3 = 0;
            Object obj = null;
            int i3 = 0;
            for (Entry entry : this.mStorage.getEntries()) {
                long j4;
                i3++;
                j3 += entry.getSize();
                if (entry.getTimestamp() > j) {
                    i++;
                    j4 = j;
                    int size = (int) (((long) i2) + entry.getSize());
                    j2 = Math.max(entry.getTimestamp() - now, j2);
                    i2 = size;
                    obj = 1;
                } else {
                    j4 = j;
                    if (this.mIndexPopulateAtStartupEnabled) {
                        hashSet.add(entry.getId());
                    }
                }
                j = j4;
            }
            if (obj != null) {
                CacheErrorLogger cacheErrorLogger = this.mCacheErrorLogger;
                CacheErrorCategory cacheErrorCategory = CacheErrorCategory.READ_INVALID_ENTRY;
                Class cls = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Future timestamp found in ");
                stringBuilder.append(i);
                stringBuilder.append(" files , with a total size of ");
                stringBuilder.append(i2);
                stringBuilder.append(" bytes, and a maximum time delta of ");
                stringBuilder.append(j2);
                stringBuilder.append("ms");
                cacheErrorLogger.logError(cacheErrorCategory, cls, stringBuilder.toString(), null);
            }
            j2 = (long) i3;
            if (!(this.mCacheStats.getCount() == j2 && this.mCacheStats.getSize() == j3)) {
                if (this.mIndexPopulateAtStartupEnabled && this.mResourceIndex != hashSet) {
                    this.mResourceIndex.clear();
                    this.mResourceIndex.addAll(hashSet);
                }
                this.mCacheStats.set(j3, j2);
            }
            this.mCacheSizeLastUpdateTime = now;
            return true;
        } catch (Throwable e) {
            CacheErrorLogger cacheErrorLogger2 = this.mCacheErrorLogger;
            CacheErrorCategory cacheErrorCategory2 = CacheErrorCategory.GENERIC_IO;
            Class cls2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("calcFileCacheSize: ");
            stringBuilder2.append(e.getMessage());
            cacheErrorLogger2.logError(cacheErrorCategory2, cls2, stringBuilder2.toString(), e);
            return false;
        }
    }
}
