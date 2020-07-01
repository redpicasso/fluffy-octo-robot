package com.facebook.react.turbomodule.core;

import com.facebook.jni.HybridData;
import com.facebook.react.bridge.CxxModuleWrapper;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;
import com.facebook.soloader.SoLoader;
import javax.annotation.Nullable;

public abstract class TurboModuleManagerDelegate {
    private final HybridData mHybridData = initHybrid();
    private final ReactApplicationContext mReactApplicationContext;

    @Nullable
    public abstract CxxModuleWrapper getLegacyCxxModule(String str);

    @Nullable
    public abstract TurboModule getModule(String str);

    protected abstract HybridData initHybrid();

    static {
        SoLoader.loadLibrary("turbomodulejsijni");
    }

    protected TurboModuleManagerDelegate(ReactApplicationContext reactApplicationContext) {
        this.mReactApplicationContext = reactApplicationContext;
    }

    protected ReactApplicationContext getReactApplicationContext() {
        return this.mReactApplicationContext;
    }
}
