package com.facebook.imagepipeline.platform;

import android.annotation.TargetApi;
import android.graphics.Bitmap.Config;
import com.facebook.soloader.DoNotOptimize;

@DoNotOptimize
class PreverificationHelper {
    PreverificationHelper() {
    }

    @DoNotOptimize
    @TargetApi(26)
    boolean shouldUseHardwareBitmapConfig(Config config) {
        return config == Config.HARDWARE;
    }
}
