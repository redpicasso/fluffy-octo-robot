package com.facebook.imagepipeline.platform;

import android.os.Build.VERSION;
import androidx.core.util.Pools.SynchronizedPool;
import com.facebook.imagepipeline.memory.PoolFactory;

public class PlatformDecoderFactory {
    public static PlatformDecoder buildPlatformDecoder(PoolFactory poolFactory, boolean z) {
        int flexByteArrayPoolMaxNumThreads;
        if (VERSION.SDK_INT >= 26) {
            flexByteArrayPoolMaxNumThreads = poolFactory.getFlexByteArrayPoolMaxNumThreads();
            return new OreoDecoder(poolFactory.getBitmapPool(), flexByteArrayPoolMaxNumThreads, new SynchronizedPool(flexByteArrayPoolMaxNumThreads));
        } else if (VERSION.SDK_INT >= 21) {
            flexByteArrayPoolMaxNumThreads = poolFactory.getFlexByteArrayPoolMaxNumThreads();
            return new ArtDecoder(poolFactory.getBitmapPool(), flexByteArrayPoolMaxNumThreads, new SynchronizedPool(flexByteArrayPoolMaxNumThreads));
        } else if (!z || VERSION.SDK_INT >= 19) {
            return new KitKatPurgeableDecoder(poolFactory.getFlexByteArrayPool());
        } else {
            return new GingerbreadPurgeableDecoder();
        }
    }
}
