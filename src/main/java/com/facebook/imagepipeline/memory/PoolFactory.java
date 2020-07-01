package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteStreams;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class PoolFactory {
    private BitmapPool mBitmapPool;
    private BufferMemoryChunkPool mBufferMemoryChunkPool;
    private final PoolConfig mConfig;
    private FlexByteArrayPool mFlexByteArrayPool;
    private NativeMemoryChunkPool mNativeMemoryChunkPool;
    private PooledByteBufferFactory mPooledByteBufferFactory;
    private PooledByteStreams mPooledByteStreams;
    private SharedByteArray mSharedByteArray;
    private ByteArrayPool mSmallByteArrayPool;

    public PoolFactory(PoolConfig poolConfig) {
        this.mConfig = (PoolConfig) Preconditions.checkNotNull(poolConfig);
    }

    public BitmapPool getBitmapPool() {
        if (this.mBitmapPool == null) {
            String bitmapPoolType = this.mConfig.getBitmapPoolType();
            int i = -1;
            switch (bitmapPoolType.hashCode()) {
                case -1868884870:
                    if (bitmapPoolType.equals(BitmapPoolType.LEGACY_DEFAULT_PARAMS)) {
                        i = 3;
                        break;
                    }
                    break;
                case -1106578487:
                    if (bitmapPoolType.equals("legacy")) {
                        i = 4;
                        break;
                    }
                    break;
                case -404562712:
                    if (bitmapPoolType.equals(BitmapPoolType.EXPERIMENTAL)) {
                        i = 2;
                        break;
                    }
                    break;
                case -402149703:
                    if (bitmapPoolType.equals(BitmapPoolType.DUMMY_WITH_TRACKING)) {
                        i = 1;
                        break;
                    }
                    break;
                case 95945896:
                    if (bitmapPoolType.equals(BitmapPoolType.DUMMY)) {
                        i = 0;
                        break;
                    }
                    break;
            }
            if (i == 0) {
                this.mBitmapPool = new DummyBitmapPool();
            } else if (i == 1) {
                this.mBitmapPool = new DummyTrackingInUseBitmapPool();
            } else if (i == 2) {
                MemoryTrimmableRegistry memoryTrimmableRegistry;
                i = this.mConfig.getBitmapPoolMaxPoolSize();
                int bitmapPoolMaxBitmapSize = this.mConfig.getBitmapPoolMaxBitmapSize();
                PoolStatsTracker instance = NoOpPoolStatsTracker.getInstance();
                if (this.mConfig.isRegisterLruBitmapPoolAsMemoryTrimmable()) {
                    memoryTrimmableRegistry = this.mConfig.getMemoryTrimmableRegistry();
                } else {
                    memoryTrimmableRegistry = null;
                }
                this.mBitmapPool = new LruBitmapPool(i, bitmapPoolMaxBitmapSize, instance, memoryTrimmableRegistry);
            } else if (i != 3) {
                this.mBitmapPool = new BucketsBitmapPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getBitmapPoolParams(), this.mConfig.getBitmapPoolStatsTracker());
            } else {
                this.mBitmapPool = new BucketsBitmapPool(this.mConfig.getMemoryTrimmableRegistry(), DefaultBitmapPoolParams.get(), this.mConfig.getBitmapPoolStatsTracker());
            }
        }
        return this.mBitmapPool;
    }

    public BufferMemoryChunkPool getBufferMemoryChunkPool() {
        if (this.mBufferMemoryChunkPool == null) {
            this.mBufferMemoryChunkPool = new BufferMemoryChunkPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getMemoryChunkPoolParams(), this.mConfig.getMemoryChunkPoolStatsTracker());
        }
        return this.mBufferMemoryChunkPool;
    }

    public FlexByteArrayPool getFlexByteArrayPool() {
        if (this.mFlexByteArrayPool == null) {
            this.mFlexByteArrayPool = new FlexByteArrayPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getFlexByteArrayPoolParams());
        }
        return this.mFlexByteArrayPool;
    }

    public int getFlexByteArrayPoolMaxNumThreads() {
        return this.mConfig.getFlexByteArrayPoolParams().maxNumThreads;
    }

    public NativeMemoryChunkPool getNativeMemoryChunkPool() {
        if (this.mNativeMemoryChunkPool == null) {
            this.mNativeMemoryChunkPool = new NativeMemoryChunkPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getMemoryChunkPoolParams(), this.mConfig.getMemoryChunkPoolStatsTracker());
        }
        return this.mNativeMemoryChunkPool;
    }

    public PooledByteBufferFactory getPooledByteBufferFactory() {
        return getPooledByteBufferFactory(0);
    }

    public PooledByteBufferFactory getPooledByteBufferFactory(int i) {
        if (this.mPooledByteBufferFactory == null) {
            this.mPooledByteBufferFactory = new MemoryPooledByteBufferFactory(getMemoryChunkPool(i), getPooledByteStreams());
        }
        return this.mPooledByteBufferFactory;
    }

    public PooledByteStreams getPooledByteStreams() {
        if (this.mPooledByteStreams == null) {
            this.mPooledByteStreams = new PooledByteStreams(getSmallByteArrayPool());
        }
        return this.mPooledByteStreams;
    }

    public SharedByteArray getSharedByteArray() {
        if (this.mSharedByteArray == null) {
            this.mSharedByteArray = new SharedByteArray(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getFlexByteArrayPoolParams());
        }
        return this.mSharedByteArray;
    }

    public ByteArrayPool getSmallByteArrayPool() {
        if (this.mSmallByteArrayPool == null) {
            this.mSmallByteArrayPool = new GenericByteArrayPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getSmallByteArrayPoolParams(), this.mConfig.getSmallByteArrayPoolStatsTracker());
        }
        return this.mSmallByteArrayPool;
    }

    private MemoryChunkPool getMemoryChunkPool(int i) {
        if (i == 0) {
            return getNativeMemoryChunkPool();
        }
        if (i == 1) {
            return getBufferMemoryChunkPool();
        }
        throw new IllegalArgumentException("Invalid MemoryChunkType");
    }
}
