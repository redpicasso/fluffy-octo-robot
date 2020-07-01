package com.facebook.imagepipeline.cache;

import android.os.Build.VERSION;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.imagepipeline.cache.CountingMemoryCache.CacheTrimStrategy;

public class BitmapMemoryCacheTrimStrategy implements CacheTrimStrategy {
    private static final String TAG = "BitmapMemoryCacheTrimStrategy";

    /* renamed from: com.facebook.imagepipeline.cache.BitmapMemoryCacheTrimStrategy$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$common$memory$MemoryTrimType = new int[MemoryTrimType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:11:0x0040, code:
            return;
     */
        static {
            /*
            r0 = com.facebook.common.memory.MemoryTrimType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$common$memory$MemoryTrimType = r0;
            r0 = $SwitchMap$com$facebook$common$memory$MemoryTrimType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.common.memory.MemoryTrimType.OnCloseToDalvikHeapLimit;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$common$memory$MemoryTrimType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.common.memory.MemoryTrimType.OnAppBackgrounded;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$common$memory$MemoryTrimType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.common.memory.MemoryTrimType.OnSystemMemoryCriticallyLowWhileAppInForeground;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$facebook$common$memory$MemoryTrimType;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.facebook.common.memory.MemoryTrimType.OnSystemLowMemoryWhileAppInForeground;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r0 = $SwitchMap$com$facebook$common$memory$MemoryTrimType;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = com.facebook.common.memory.MemoryTrimType.OnSystemLowMemoryWhileAppInBackground;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r2 = 5;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.cache.BitmapMemoryCacheTrimStrategy.1.<clinit>():void");
        }
    }

    public double getTrimRatio(MemoryTrimType memoryTrimType) {
        int i = AnonymousClass1.$SwitchMap$com$facebook$common$memory$MemoryTrimType[memoryTrimType.ordinal()];
        if (i != 1) {
            if (i == 2 || i == 3 || i == 4 || i == 5) {
                return 1.0d;
            }
            FLog.wtf(TAG, "unknown trim type: %s", memoryTrimType);
            return 0.0d;
        } else if (VERSION.SDK_INT >= 21) {
            return MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio();
        } else {
            return 0.0d;
        }
    }
}
