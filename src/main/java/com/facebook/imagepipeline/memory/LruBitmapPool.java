package com.facebook.imagepipeline.memory;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import javax.annotation.Nullable;

public class LruBitmapPool implements BitmapPool {
    private int mCurrentSize;
    private int mMaxBitmapSize;
    private final int mMaxPoolSize;
    private final PoolStatsTracker mPoolStatsTracker;
    protected final PoolBackend<Bitmap> mStrategy = new BitmapPoolBackend();

    public LruBitmapPool(int i, int i2, PoolStatsTracker poolStatsTracker, @Nullable MemoryTrimmableRegistry memoryTrimmableRegistry) {
        this.mMaxPoolSize = i;
        this.mMaxBitmapSize = i2;
        this.mPoolStatsTracker = poolStatsTracker;
        if (memoryTrimmableRegistry != null) {
            memoryTrimmableRegistry.registerMemoryTrimmable(this);
        }
    }

    public void trim(MemoryTrimType memoryTrimType) {
        trimTo((int) (((double) this.mMaxPoolSize) * (1.0d - memoryTrimType.getSuggestedTrimRatio())));
    }

    private synchronized void trimTo(int i) {
        while (this.mCurrentSize > i) {
            Bitmap bitmap = (Bitmap) this.mStrategy.pop();
            if (bitmap == null) {
                break;
            }
            int size = this.mStrategy.getSize(bitmap);
            this.mCurrentSize -= size;
            this.mPoolStatsTracker.onFree(size);
        }
    }

    public synchronized Bitmap get(int i) {
        if (this.mCurrentSize > this.mMaxPoolSize) {
            trimTo(this.mMaxPoolSize);
        }
        Bitmap bitmap = (Bitmap) this.mStrategy.get(i);
        if (bitmap != null) {
            i = this.mStrategy.getSize(bitmap);
            this.mCurrentSize -= i;
            this.mPoolStatsTracker.onValueReuse(i);
            return bitmap;
        }
        return alloc(i);
    }

    @VisibleForTesting
    private Bitmap alloc(int i) {
        this.mPoolStatsTracker.onAlloc(i);
        return Bitmap.createBitmap(1, i, Config.ALPHA_8);
    }

    public void release(Bitmap bitmap) {
        int size = this.mStrategy.getSize(bitmap);
        if (size <= this.mMaxBitmapSize) {
            this.mPoolStatsTracker.onValueRelease(size);
            this.mStrategy.put(bitmap);
            synchronized (this) {
                this.mCurrentSize += size;
            }
        }
    }
}
