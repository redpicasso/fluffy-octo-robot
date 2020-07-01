package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.gifdecoder.GifDecoder.BitmapProvider;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

public final class GifBitmapProvider implements BitmapProvider {
    @Nullable
    private final ArrayPool arrayPool;
    private final BitmapPool bitmapPool;

    public GifBitmapProvider(BitmapPool bitmapPool) {
        this(bitmapPool, null);
    }

    public GifBitmapProvider(BitmapPool bitmapPool, @Nullable ArrayPool arrayPool) {
        this.bitmapPool = bitmapPool;
        this.arrayPool = arrayPool;
    }

    @NonNull
    public Bitmap obtain(int i, int i2, @NonNull Config config) {
        return this.bitmapPool.getDirty(i, i2, config);
    }

    public void release(@NonNull Bitmap bitmap) {
        this.bitmapPool.put(bitmap);
    }

    @NonNull
    public byte[] obtainByteArray(int i) {
        ArrayPool arrayPool = this.arrayPool;
        if (arrayPool == null) {
            return new byte[i];
        }
        return (byte[]) arrayPool.get(i, byte[].class);
    }

    public void release(@NonNull byte[] bArr) {
        ArrayPool arrayPool = this.arrayPool;
        if (arrayPool != null) {
            arrayPool.put(bArr);
        }
    }

    @NonNull
    public int[] obtainIntArray(int i) {
        ArrayPool arrayPool = this.arrayPool;
        if (arrayPool == null) {
            return new int[i];
        }
        return (int[]) arrayPool.get(i, int[].class);
    }

    public void release(@NonNull int[] iArr) {
        ArrayPool arrayPool = this.arrayPool;
        if (arrayPool != null) {
            arrayPool.put(iArr);
        }
    }
}
