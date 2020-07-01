package com.facebook.imagepipeline.cache;

import bolts.Task;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.FileCache;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteStreams;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

public class BufferedDiskCache {
    private static final Class<?> TAG = BufferedDiskCache.class;
    private final FileCache mFileCache;
    private final ImageCacheStatsTracker mImageCacheStatsTracker;
    private final PooledByteBufferFactory mPooledByteBufferFactory;
    private final PooledByteStreams mPooledByteStreams;
    private final Executor mReadExecutor;
    private final StagingArea mStagingArea = StagingArea.getInstance();
    private final Executor mWriteExecutor;

    public BufferedDiskCache(FileCache fileCache, PooledByteBufferFactory pooledByteBufferFactory, PooledByteStreams pooledByteStreams, Executor executor, Executor executor2, ImageCacheStatsTracker imageCacheStatsTracker) {
        this.mFileCache = fileCache;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mPooledByteStreams = pooledByteStreams;
        this.mReadExecutor = executor;
        this.mWriteExecutor = executor2;
        this.mImageCacheStatsTracker = imageCacheStatsTracker;
    }

    public boolean containsSync(CacheKey cacheKey) {
        return this.mStagingArea.containsKey(cacheKey) || this.mFileCache.hasKeySync(cacheKey);
    }

    public Task<Boolean> contains(CacheKey cacheKey) {
        if (containsSync(cacheKey)) {
            return Task.forResult(Boolean.valueOf(true));
        }
        return containsAsync(cacheKey);
    }

    private Task<Boolean> containsAsync(final CacheKey cacheKey) {
        try {
            cacheKey = Task.call(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    return Boolean.valueOf(BufferedDiskCache.this.checkInStagingAreaAndFileCache(cacheKey));
                }
            }, this.mReadExecutor);
            return cacheKey;
        } catch (Throwable e) {
            FLog.w(TAG, e, "Failed to schedule disk-cache read for %s", cacheKey.getUriString());
            return Task.forError(e);
        }
    }

    public boolean diskCheckSync(CacheKey cacheKey) {
        if (containsSync(cacheKey)) {
            return true;
        }
        return checkInStagingAreaAndFileCache(cacheKey);
    }

    public Task<EncodedImage> get(CacheKey cacheKey, AtomicBoolean atomicBoolean) {
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("BufferedDiskCache#get");
            }
            EncodedImage encodedImage = this.mStagingArea.get(cacheKey);
            Task<EncodedImage> foundPinnedImage;
            if (encodedImage != null) {
                foundPinnedImage = foundPinnedImage(cacheKey, encodedImage);
                return foundPinnedImage;
            }
            foundPinnedImage = getAsync(cacheKey, atomicBoolean);
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            return foundPinnedImage;
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    private boolean checkInStagingAreaAndFileCache(CacheKey cacheKey) {
        EncodedImage encodedImage = this.mStagingArea.get(cacheKey);
        if (encodedImage != null) {
            encodedImage.close();
            FLog.v(TAG, "Found image for %s in staging area", cacheKey.getUriString());
            this.mImageCacheStatsTracker.onStagingAreaHit(cacheKey);
            return true;
        }
        FLog.v(TAG, "Did not find image for %s in staging area", cacheKey.getUriString());
        this.mImageCacheStatsTracker.onStagingAreaMiss();
        try {
            return this.mFileCache.hasKey(cacheKey);
        } catch (Exception unused) {
            return false;
        }
    }

    private Task<EncodedImage> getAsync(final CacheKey cacheKey, final AtomicBoolean atomicBoolean) {
        try {
            cacheKey = Task.call(new Callable<EncodedImage>() {
                /* JADX WARNING: Missing block: B:39:0x00a4, code:
            if (com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing() == false) goto L_0x00a9;
     */
                /* JADX WARNING: Missing block: B:40:0x00a6, code:
            com.facebook.imagepipeline.systrace.FrescoSystrace.endSection();
     */
                /* JADX WARNING: Missing block: B:41:0x00a9, code:
            return null;
     */
                @javax.annotation.Nullable
                public com.facebook.imagepipeline.image.EncodedImage call() throws java.lang.Exception {
                    /*
                    r4 = this;
                    r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing();	 Catch:{ all -> 0x00b0 }
                    if (r0 == 0) goto L_0x000b;
                L_0x0006:
                    r0 = "BufferedDiskCache#getAsync";
                    com.facebook.imagepipeline.systrace.FrescoSystrace.beginSection(r0);	 Catch:{ all -> 0x00b0 }
                L_0x000b:
                    r0 = r5;	 Catch:{ all -> 0x00b0 }
                    r0 = r0.get();	 Catch:{ all -> 0x00b0 }
                    if (r0 != 0) goto L_0x00aa;
                L_0x0013:
                    r0 = com.facebook.imagepipeline.cache.BufferedDiskCache.this;	 Catch:{ all -> 0x00b0 }
                    r0 = r0.mStagingArea;	 Catch:{ all -> 0x00b0 }
                    r1 = r4;	 Catch:{ all -> 0x00b0 }
                    r0 = r0.get(r1);	 Catch:{ all -> 0x00b0 }
                    if (r0 == 0) goto L_0x003c;
                L_0x0021:
                    r1 = com.facebook.imagepipeline.cache.BufferedDiskCache.TAG;	 Catch:{ all -> 0x00b0 }
                    r2 = "Found image for %s in staging area";
                    r3 = r4;	 Catch:{ all -> 0x00b0 }
                    r3 = r3.getUriString();	 Catch:{ all -> 0x00b0 }
                    com.facebook.common.logging.FLog.v(r1, r2, r3);	 Catch:{ all -> 0x00b0 }
                    r1 = com.facebook.imagepipeline.cache.BufferedDiskCache.this;	 Catch:{ all -> 0x00b0 }
                    r1 = r1.mImageCacheStatsTracker;	 Catch:{ all -> 0x00b0 }
                    r2 = r4;	 Catch:{ all -> 0x00b0 }
                    r1.onStagingAreaHit(r2);	 Catch:{ all -> 0x00b0 }
                    goto L_0x0076;
                L_0x003c:
                    r0 = com.facebook.imagepipeline.cache.BufferedDiskCache.TAG;	 Catch:{ all -> 0x00b0 }
                    r1 = "Did not find image for %s in staging area";
                    r2 = r4;	 Catch:{ all -> 0x00b0 }
                    r2 = r2.getUriString();	 Catch:{ all -> 0x00b0 }
                    com.facebook.common.logging.FLog.v(r0, r1, r2);	 Catch:{ all -> 0x00b0 }
                    r0 = com.facebook.imagepipeline.cache.BufferedDiskCache.this;	 Catch:{ all -> 0x00b0 }
                    r0 = r0.mImageCacheStatsTracker;	 Catch:{ all -> 0x00b0 }
                    r0.onStagingAreaMiss();	 Catch:{ all -> 0x00b0 }
                    r0 = 0;
                    r1 = com.facebook.imagepipeline.cache.BufferedDiskCache.this;	 Catch:{ Exception -> 0x009f }
                    r2 = r4;	 Catch:{ Exception -> 0x009f }
                    r1 = r1.readFromDiskCache(r2);	 Catch:{ Exception -> 0x009f }
                    if (r1 != 0) goto L_0x0069;
                L_0x005f:
                    r1 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing();
                    if (r1 == 0) goto L_0x0068;
                L_0x0065:
                    com.facebook.imagepipeline.systrace.FrescoSystrace.endSection();
                L_0x0068:
                    return r0;
                L_0x0069:
                    r1 = com.facebook.common.references.CloseableReference.of(r1);	 Catch:{ Exception -> 0x009f }
                    r2 = new com.facebook.imagepipeline.image.EncodedImage;	 Catch:{ all -> 0x009a }
                    r2.<init>(r1);	 Catch:{ all -> 0x009a }
                    com.facebook.common.references.CloseableReference.closeSafely(r1);	 Catch:{ Exception -> 0x009f }
                    r0 = r2;
                L_0x0076:
                    r1 = java.lang.Thread.interrupted();	 Catch:{ all -> 0x00b0 }
                    if (r1 == 0) goto L_0x0090;
                L_0x007c:
                    r1 = com.facebook.imagepipeline.cache.BufferedDiskCache.TAG;	 Catch:{ all -> 0x00b0 }
                    r2 = "Host thread was interrupted, decreasing reference count";
                    com.facebook.common.logging.FLog.v(r1, r2);	 Catch:{ all -> 0x00b0 }
                    if (r0 == 0) goto L_0x008a;
                L_0x0087:
                    r0.close();	 Catch:{ all -> 0x00b0 }
                L_0x008a:
                    r0 = new java.lang.InterruptedException;	 Catch:{ all -> 0x00b0 }
                    r0.<init>();	 Catch:{ all -> 0x00b0 }
                    throw r0;	 Catch:{ all -> 0x00b0 }
                L_0x0090:
                    r1 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing();
                    if (r1 == 0) goto L_0x0099;
                L_0x0096:
                    com.facebook.imagepipeline.systrace.FrescoSystrace.endSection();
                L_0x0099:
                    return r0;
                L_0x009a:
                    r2 = move-exception;
                    com.facebook.common.references.CloseableReference.closeSafely(r1);	 Catch:{ Exception -> 0x009f }
                    throw r2;	 Catch:{ Exception -> 0x009f }
                    r1 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing();
                    if (r1 == 0) goto L_0x00a9;
                L_0x00a6:
                    com.facebook.imagepipeline.systrace.FrescoSystrace.endSection();
                L_0x00a9:
                    return r0;
                L_0x00aa:
                    r0 = new java.util.concurrent.CancellationException;	 Catch:{ all -> 0x00b0 }
                    r0.<init>();	 Catch:{ all -> 0x00b0 }
                    throw r0;	 Catch:{ all -> 0x00b0 }
                L_0x00b0:
                    r0 = move-exception;
                    r1 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing();
                    if (r1 == 0) goto L_0x00ba;
                L_0x00b7:
                    com.facebook.imagepipeline.systrace.FrescoSystrace.endSection();
                L_0x00ba:
                    throw r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.cache.BufferedDiskCache.2.call():com.facebook.imagepipeline.image.EncodedImage");
                }
            }, this.mReadExecutor);
            return cacheKey;
        } catch (Throwable e) {
            FLog.w(TAG, e, "Failed to schedule disk-cache read for %s", cacheKey.getUriString());
            return Task.forError(e);
        }
    }

    public void put(final CacheKey cacheKey, EncodedImage encodedImage) {
        final EncodedImage cloneOrNull;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("BufferedDiskCache#put");
            }
            Preconditions.checkNotNull(cacheKey);
            Preconditions.checkArgument(EncodedImage.isValid(encodedImage));
            this.mStagingArea.put(cacheKey, encodedImage);
            cloneOrNull = EncodedImage.cloneOrNull(encodedImage);
            this.mWriteExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        if (FrescoSystrace.isTracing()) {
                            FrescoSystrace.beginSection("BufferedDiskCache#putAsync");
                        }
                        BufferedDiskCache.this.writeToDiskCache(cacheKey, cloneOrNull);
                    } finally {
                        BufferedDiskCache.this.mStagingArea.remove(cacheKey, cloneOrNull);
                        EncodedImage.closeSafely(cloneOrNull);
                        if (FrescoSystrace.isTracing()) {
                            FrescoSystrace.endSection();
                        }
                    }
                }
            });
        } catch (Throwable e) {
            FLog.w(TAG, e, "Failed to schedule disk-cache write for %s", cacheKey.getUriString());
            this.mStagingArea.remove(cacheKey, encodedImage);
            EncodedImage.closeSafely(cloneOrNull);
        } catch (Throwable th) {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
    }

    public Task<Void> remove(final CacheKey cacheKey) {
        Preconditions.checkNotNull(cacheKey);
        this.mStagingArea.remove(cacheKey);
        try {
            cacheKey = Task.call(new Callable<Void>() {
                public Void call() throws Exception {
                    try {
                        if (FrescoSystrace.isTracing()) {
                            FrescoSystrace.beginSection("BufferedDiskCache#remove");
                        }
                        BufferedDiskCache.this.mStagingArea.remove(cacheKey);
                        BufferedDiskCache.this.mFileCache.remove(cacheKey);
                        return null;
                    } finally {
                        if (FrescoSystrace.isTracing()) {
                            FrescoSystrace.endSection();
                        }
                    }
                }
            }, this.mWriteExecutor);
            return cacheKey;
        } catch (Throwable e) {
            FLog.w(TAG, e, "Failed to schedule disk-cache remove for %s", cacheKey.getUriString());
            return Task.forError(e);
        }
    }

    public Task<Void> clearAll() {
        this.mStagingArea.clearAll();
        try {
            return Task.call(new Callable<Void>() {
                public Void call() throws Exception {
                    BufferedDiskCache.this.mStagingArea.clearAll();
                    BufferedDiskCache.this.mFileCache.clearAll();
                    return null;
                }
            }, this.mWriteExecutor);
        } catch (Throwable e) {
            FLog.w(TAG, e, "Failed to schedule disk-cache clear", new Object[0]);
            return Task.forError(e);
        }
    }

    public long getSize() {
        return this.mFileCache.getSize();
    }

    private Task<EncodedImage> foundPinnedImage(CacheKey cacheKey, EncodedImage encodedImage) {
        FLog.v(TAG, "Found image for %s in staging area", cacheKey.getUriString());
        this.mImageCacheStatsTracker.onStagingAreaHit(cacheKey);
        return Task.forResult(encodedImage);
    }

    @Nullable
    private PooledByteBuffer readFromDiskCache(CacheKey cacheKey) throws IOException {
        InputStream openStream;
        try {
            FLog.v(TAG, "Disk cache read for %s", cacheKey.getUriString());
            BinaryResource resource = this.mFileCache.getResource(cacheKey);
            if (resource == null) {
                FLog.v(TAG, "Disk cache miss for %s", cacheKey.getUriString());
                this.mImageCacheStatsTracker.onDiskCacheMiss();
                return null;
            }
            FLog.v(TAG, "Found entry in disk cache for %s", cacheKey.getUriString());
            this.mImageCacheStatsTracker.onDiskCacheHit(cacheKey);
            openStream = resource.openStream();
            PooledByteBuffer newByteBuffer = this.mPooledByteBufferFactory.newByteBuffer(openStream, (int) resource.size());
            openStream.close();
            FLog.v(TAG, "Successful read from disk cache for %s", cacheKey.getUriString());
            return newByteBuffer;
        } catch (Throwable e) {
            FLog.w(TAG, e, "Exception reading from cache for %s", cacheKey.getUriString());
            this.mImageCacheStatsTracker.onDiskCacheGetFail();
            throw e;
        } catch (Throwable th) {
            openStream.close();
        }
    }

    private void writeToDiskCache(CacheKey cacheKey, final EncodedImage encodedImage) {
        FLog.v(TAG, "About to write to disk-cache for key %s", cacheKey.getUriString());
        try {
            this.mFileCache.insert(cacheKey, new WriterCallback() {
                public void write(OutputStream outputStream) throws IOException {
                    BufferedDiskCache.this.mPooledByteStreams.copy(encodedImage.getInputStream(), outputStream);
                }
            });
            FLog.v(TAG, "Successful disk-cache write for key %s", cacheKey.getUriString());
        } catch (Throwable e) {
            FLog.w(TAG, e, "Failed to write to disk-cache for key %s", cacheKey.getUriString());
        }
    }
}
