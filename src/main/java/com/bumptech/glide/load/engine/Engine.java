package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pools.Pool;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemoryCache.ResourceRemovedListener;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.request.ResourceCallback;
import com.bumptech.glide.util.Executors;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.FactoryPools.Factory;
import java.util.Map;

public class Engine implements EngineJobListener, ResourceRemovedListener, ResourceListener {
    private static final int JOB_POOL_SIZE = 150;
    private static final String TAG = "Engine";
    private static final boolean VERBOSE_IS_LOGGABLE = Log.isLoggable(TAG, 2);
    private final ActiveResources activeResources;
    private final MemoryCache cache;
    private final DecodeJobFactory decodeJobFactory;
    private final LazyDiskCacheProvider diskCacheProvider;
    private final EngineJobFactory engineJobFactory;
    private final Jobs jobs;
    private final EngineKeyFactory keyFactory;
    private final ResourceRecycler resourceRecycler;

    @VisibleForTesting
    static class DecodeJobFactory {
        private int creationOrder;
        final DiskCacheProvider diskCacheProvider;
        final Pool<DecodeJob<?>> pool = FactoryPools.threadSafe(150, new Factory<DecodeJob<?>>() {
            public DecodeJob<?> create() {
                return new DecodeJob(DecodeJobFactory.this.diskCacheProvider, DecodeJobFactory.this.pool);
            }
        });

        DecodeJobFactory(DiskCacheProvider diskCacheProvider) {
            this.diskCacheProvider = diskCacheProvider;
        }

        <R> DecodeJob<R> build(GlideContext glideContext, Object obj, EngineKey engineKey, Key key, int i, int i2, Class<?> cls, Class<R> cls2, Priority priority, DiskCacheStrategy diskCacheStrategy, Map<Class<?>, Transformation<?>> map, boolean z, boolean z2, boolean z3, Options options, Callback<R> callback) {
            GlideContext glideContext2 = glideContext;
            Object obj2 = obj;
            EngineKey engineKey2 = engineKey;
            Key key2 = key;
            int i3 = i;
            int i4 = i2;
            Class<?> cls3 = cls;
            Class<R> cls4 = cls2;
            Priority priority2 = priority;
            DiskCacheStrategy diskCacheStrategy2 = diskCacheStrategy;
            Map<Class<?>, Transformation<?>> map2 = map;
            boolean z4 = z;
            boolean z5 = z2;
            boolean z6 = z3;
            Options options2 = options;
            Callback<R> callback2 = callback;
            DecodeJob decodeJob = (DecodeJob) Preconditions.checkNotNull((DecodeJob) this.pool.acquire());
            int i5 = this.creationOrder;
            int i6 = i5;
            this.creationOrder = i5 + 1;
            return decodeJob.init(glideContext2, obj2, engineKey2, key2, i3, i4, cls3, cls4, priority2, diskCacheStrategy2, map2, z4, z5, z6, options2, callback2, i6);
        }
    }

    @VisibleForTesting
    static class EngineJobFactory {
        final GlideExecutor animationExecutor;
        final GlideExecutor diskCacheExecutor;
        final EngineJobListener listener;
        final Pool<EngineJob<?>> pool = FactoryPools.threadSafe(150, new Factory<EngineJob<?>>() {
            public EngineJob<?> create() {
                return new EngineJob(EngineJobFactory.this.diskCacheExecutor, EngineJobFactory.this.sourceExecutor, EngineJobFactory.this.sourceUnlimitedExecutor, EngineJobFactory.this.animationExecutor, EngineJobFactory.this.listener, EngineJobFactory.this.pool);
            }
        });
        final GlideExecutor sourceExecutor;
        final GlideExecutor sourceUnlimitedExecutor;

        EngineJobFactory(GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, EngineJobListener engineJobListener) {
            this.diskCacheExecutor = glideExecutor;
            this.sourceExecutor = glideExecutor2;
            this.sourceUnlimitedExecutor = glideExecutor3;
            this.animationExecutor = glideExecutor4;
            this.listener = engineJobListener;
        }

        @VisibleForTesting
        void shutdown() {
            Executors.shutdownAndAwaitTermination(this.diskCacheExecutor);
            Executors.shutdownAndAwaitTermination(this.sourceExecutor);
            Executors.shutdownAndAwaitTermination(this.sourceUnlimitedExecutor);
            Executors.shutdownAndAwaitTermination(this.animationExecutor);
        }

        <R> EngineJob<R> build(Key key, boolean z, boolean z2, boolean z3, boolean z4) {
            return ((EngineJob) Preconditions.checkNotNull((EngineJob) this.pool.acquire())).init(key, z, z2, z3, z4);
        }
    }

    public class LoadStatus {
        private final ResourceCallback cb;
        private final EngineJob<?> engineJob;

        LoadStatus(ResourceCallback resourceCallback, EngineJob<?> engineJob) {
            this.cb = resourceCallback;
            this.engineJob = engineJob;
        }

        public void cancel() {
            synchronized (Engine.this) {
                this.engineJob.removeCallback(this.cb);
            }
        }
    }

    private static class LazyDiskCacheProvider implements DiskCacheProvider {
        private volatile DiskCache diskCache;
        private final DiskCache.Factory factory;

        LazyDiskCacheProvider(DiskCache.Factory factory) {
            this.factory = factory;
        }

        @VisibleForTesting
        synchronized void clearDiskCacheIfCreated() {
            if (this.diskCache != null) {
                this.diskCache.clear();
            }
        }

        public DiskCache getDiskCache() {
            if (this.diskCache == null) {
                synchronized (this) {
                    if (this.diskCache == null) {
                        this.diskCache = this.factory.build();
                    }
                    if (this.diskCache == null) {
                        this.diskCache = new DiskCacheAdapter();
                    }
                }
            }
            return this.diskCache;
        }
    }

    public Engine(MemoryCache memoryCache, DiskCache.Factory factory, GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, boolean z) {
        this(memoryCache, factory, glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, null, null, null, null, null, null, z);
    }

    @VisibleForTesting
    Engine(MemoryCache memoryCache, DiskCache.Factory factory, GlideExecutor glideExecutor, GlideExecutor glideExecutor2, GlideExecutor glideExecutor3, GlideExecutor glideExecutor4, Jobs jobs, EngineKeyFactory engineKeyFactory, ActiveResources activeResources, EngineJobFactory engineJobFactory, DecodeJobFactory decodeJobFactory, ResourceRecycler resourceRecycler, boolean z) {
        EngineJobFactory engineJobFactory2;
        this.cache = memoryCache;
        DiskCache.Factory factory2 = factory;
        this.diskCacheProvider = new LazyDiskCacheProvider(factory);
        ActiveResources activeResources2 = activeResources == null ? new ActiveResources(z) : activeResources;
        this.activeResources = activeResources2;
        activeResources2.setListener(this);
        this.keyFactory = engineKeyFactory == null ? new EngineKeyFactory() : engineKeyFactory;
        this.jobs = jobs == null ? new Jobs() : jobs;
        if (engineJobFactory == null) {
            EngineJobFactory engineJobFactory3 = new EngineJobFactory(glideExecutor, glideExecutor2, glideExecutor3, glideExecutor4, this);
        } else {
            engineJobFactory2 = engineJobFactory;
        }
        this.engineJobFactory = engineJobFactory2;
        this.decodeJobFactory = decodeJobFactory == null ? new DecodeJobFactory(this.diskCacheProvider) : decodeJobFactory;
        this.resourceRecycler = resourceRecycler == null ? new ResourceRecycler() : resourceRecycler;
        memoryCache.setResourceRemovedListener(this);
    }

    /* JADX WARNING: Missing block: B:13:0x0041, code:
            return null;
     */
    /* JADX WARNING: Missing block: B:21:0x0057, code:
            return null;
     */
    public synchronized <R> com.bumptech.glide.load.engine.Engine.LoadStatus load(com.bumptech.glide.GlideContext r31, java.lang.Object r32, com.bumptech.glide.load.Key r33, int r34, int r35, java.lang.Class<?> r36, java.lang.Class<R> r37, com.bumptech.glide.Priority r38, com.bumptech.glide.load.engine.DiskCacheStrategy r39, java.util.Map<java.lang.Class<?>, com.bumptech.glide.load.Transformation<?>> r40, boolean r41, boolean r42, com.bumptech.glide.load.Options r43, boolean r44, boolean r45, boolean r46, boolean r47, com.bumptech.glide.request.ResourceCallback r48, java.util.concurrent.Executor r49) {
        /*
        r30 = this;
        r1 = r30;
        r0 = r44;
        r8 = r48;
        r9 = r49;
        monitor-enter(r30);
        r2 = VERBOSE_IS_LOGGABLE;	 Catch:{ all -> 0x00c5 }
        if (r2 == 0) goto L_0x0012;
    L_0x000d:
        r2 = com.bumptech.glide.util.LogTime.getLogTime();	 Catch:{ all -> 0x00c5 }
        goto L_0x0014;
    L_0x0012:
        r2 = 0;
    L_0x0014:
        r10 = r2;
        r12 = r1.keyFactory;	 Catch:{ all -> 0x00c5 }
        r13 = r32;
        r14 = r33;
        r15 = r34;
        r16 = r35;
        r17 = r40;
        r18 = r36;
        r19 = r37;
        r20 = r43;
        r12 = r12.buildKey(r13, r14, r15, r16, r17, r18, r19, r20);	 Catch:{ all -> 0x00c5 }
        r2 = r1.loadFromActiveResources(r12, r0);	 Catch:{ all -> 0x00c5 }
        r3 = 0;
        if (r2 == 0) goto L_0x0042;
    L_0x0032:
        r0 = com.bumptech.glide.load.DataSource.MEMORY_CACHE;	 Catch:{ all -> 0x00c5 }
        r8.onResourceReady(r2, r0);	 Catch:{ all -> 0x00c5 }
        r0 = VERBOSE_IS_LOGGABLE;	 Catch:{ all -> 0x00c5 }
        if (r0 == 0) goto L_0x0040;
    L_0x003b:
        r0 = "Loaded resource from active resources";
        logWithTimeAndKey(r0, r10, r12);	 Catch:{ all -> 0x00c5 }
    L_0x0040:
        monitor-exit(r30);
        return r3;
    L_0x0042:
        r2 = r1.loadFromCache(r12, r0);	 Catch:{ all -> 0x00c5 }
        if (r2 == 0) goto L_0x0058;
    L_0x0048:
        r0 = com.bumptech.glide.load.DataSource.MEMORY_CACHE;	 Catch:{ all -> 0x00c5 }
        r8.onResourceReady(r2, r0);	 Catch:{ all -> 0x00c5 }
        r0 = VERBOSE_IS_LOGGABLE;	 Catch:{ all -> 0x00c5 }
        if (r0 == 0) goto L_0x0056;
    L_0x0051:
        r0 = "Loaded resource from cache";
        logWithTimeAndKey(r0, r10, r12);	 Catch:{ all -> 0x00c5 }
    L_0x0056:
        monitor-exit(r30);
        return r3;
    L_0x0058:
        r2 = r1.jobs;	 Catch:{ all -> 0x00c5 }
        r15 = r47;
        r2 = r2.get(r12, r15);	 Catch:{ all -> 0x00c5 }
        if (r2 == 0) goto L_0x0075;
    L_0x0062:
        r2.addCallback(r8, r9);	 Catch:{ all -> 0x00c5 }
        r0 = VERBOSE_IS_LOGGABLE;	 Catch:{ all -> 0x00c5 }
        if (r0 == 0) goto L_0x006e;
    L_0x0069:
        r0 = "Added to existing load";
        logWithTimeAndKey(r0, r10, r12);	 Catch:{ all -> 0x00c5 }
    L_0x006e:
        r0 = new com.bumptech.glide.load.engine.Engine$LoadStatus;	 Catch:{ all -> 0x00c5 }
        r0.<init>(r8, r2);	 Catch:{ all -> 0x00c5 }
        monitor-exit(r30);
        return r0;
    L_0x0075:
        r2 = r1.engineJobFactory;	 Catch:{ all -> 0x00c5 }
        r3 = r12;
        r4 = r44;
        r5 = r45;
        r6 = r46;
        r7 = r47;
        r0 = r2.build(r3, r4, r5, r6, r7);	 Catch:{ all -> 0x00c5 }
        r13 = r1.decodeJobFactory;	 Catch:{ all -> 0x00c5 }
        r14 = r31;
        r15 = r32;
        r16 = r12;
        r17 = r33;
        r18 = r34;
        r19 = r35;
        r20 = r36;
        r21 = r37;
        r22 = r38;
        r23 = r39;
        r24 = r40;
        r25 = r41;
        r26 = r42;
        r27 = r47;
        r28 = r43;
        r29 = r0;
        r2 = r13.build(r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29);	 Catch:{ all -> 0x00c5 }
        r3 = r1.jobs;	 Catch:{ all -> 0x00c5 }
        r3.put(r12, r0);	 Catch:{ all -> 0x00c5 }
        r0.addCallback(r8, r9);	 Catch:{ all -> 0x00c5 }
        r0.start(r2);	 Catch:{ all -> 0x00c5 }
        r2 = VERBOSE_IS_LOGGABLE;	 Catch:{ all -> 0x00c5 }
        if (r2 == 0) goto L_0x00be;
    L_0x00b9:
        r2 = "Started new load";
        logWithTimeAndKey(r2, r10, r12);	 Catch:{ all -> 0x00c5 }
    L_0x00be:
        r2 = new com.bumptech.glide.load.engine.Engine$LoadStatus;	 Catch:{ all -> 0x00c5 }
        r2.<init>(r8, r0);	 Catch:{ all -> 0x00c5 }
        monitor-exit(r30);
        return r2;
    L_0x00c5:
        r0 = move-exception;
        monitor-exit(r30);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.engine.Engine.load(com.bumptech.glide.GlideContext, java.lang.Object, com.bumptech.glide.load.Key, int, int, java.lang.Class, java.lang.Class, com.bumptech.glide.Priority, com.bumptech.glide.load.engine.DiskCacheStrategy, java.util.Map, boolean, boolean, com.bumptech.glide.load.Options, boolean, boolean, boolean, boolean, com.bumptech.glide.request.ResourceCallback, java.util.concurrent.Executor):com.bumptech.glide.load.engine.Engine$LoadStatus");
    }

    private static void logWithTimeAndKey(String str, long j, Key key) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" in ");
        stringBuilder.append(LogTime.getElapsedMillis(j));
        stringBuilder.append("ms, key: ");
        stringBuilder.append(key);
        Log.v(TAG, stringBuilder.toString());
    }

    @Nullable
    private EngineResource<?> loadFromActiveResources(Key key, boolean z) {
        if (!z) {
            return null;
        }
        EngineResource<?> engineResource = this.activeResources.get(key);
        if (engineResource != null) {
            engineResource.acquire();
        }
        return engineResource;
    }

    private EngineResource<?> loadFromCache(Key key, boolean z) {
        if (!z) {
            return null;
        }
        EngineResource<?> engineResourceFromCache = getEngineResourceFromCache(key);
        if (engineResourceFromCache != null) {
            engineResourceFromCache.acquire();
            this.activeResources.activate(key, engineResourceFromCache);
        }
        return engineResourceFromCache;
    }

    private EngineResource<?> getEngineResourceFromCache(Key key) {
        Resource remove = this.cache.remove(key);
        if (remove == null) {
            return null;
        }
        if (remove instanceof EngineResource) {
            return (EngineResource) remove;
        }
        return new EngineResource(remove, true, true);
    }

    public void release(Resource<?> resource) {
        if (resource instanceof EngineResource) {
            ((EngineResource) resource).release();
            return;
        }
        throw new IllegalArgumentException("Cannot release anything but an EngineResource");
    }

    public synchronized void onEngineJobComplete(EngineJob<?> engineJob, Key key, EngineResource<?> engineResource) {
        if (engineResource != null) {
            engineResource.setResourceListener(key, this);
            if (engineResource.isCacheable()) {
                this.activeResources.activate(key, engineResource);
            }
        }
        this.jobs.removeIfCurrent(key, engineJob);
    }

    public synchronized void onEngineJobCancelled(EngineJob<?> engineJob, Key key) {
        this.jobs.removeIfCurrent(key, engineJob);
    }

    public void onResourceRemoved(@NonNull Resource<?> resource) {
        this.resourceRecycler.recycle(resource);
    }

    public synchronized void onResourceReleased(Key key, EngineResource<?> engineResource) {
        this.activeResources.deactivate(key);
        if (engineResource.isCacheable()) {
            this.cache.put(key, engineResource);
        } else {
            this.resourceRecycler.recycle(engineResource);
        }
    }

    public void clearDiskCache() {
        this.diskCacheProvider.getDiskCache().clear();
    }

    @VisibleForTesting
    public void shutdown() {
        this.engineJobFactory.shutdown();
        this.diskCacheProvider.clearDiskCacheIfCreated();
        this.activeResources.shutdown();
    }
}
