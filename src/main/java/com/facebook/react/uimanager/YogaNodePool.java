package com.facebook.react.uimanager;

import com.facebook.react.common.ClearableSynchronizedPool;
import com.facebook.yoga.YogaNode;

public class YogaNodePool {
    private static final Object sInitLock = new Object();
    private static ClearableSynchronizedPool<YogaNode> sPool;

    public static ClearableSynchronizedPool<YogaNode> get() {
        ClearableSynchronizedPool<YogaNode> clearableSynchronizedPool = sPool;
        if (clearableSynchronizedPool != null) {
            return clearableSynchronizedPool;
        }
        ClearableSynchronizedPool<YogaNode> clearableSynchronizedPool2;
        synchronized (sInitLock) {
            if (sPool == null) {
                sPool = new ClearableSynchronizedPool(1024);
            }
            clearableSynchronizedPool2 = sPool;
        }
        return clearableSynchronizedPool2;
    }
}