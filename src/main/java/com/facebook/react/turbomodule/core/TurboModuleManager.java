package com.facebook.react.turbomodule.core;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.JSIModule;
import com.facebook.react.bridge.JavaScriptContextHolder;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.turbomodule.core.interfaces.JSCallInvokerHolder;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;
import com.facebook.soloader.SoLoader;

public class TurboModuleManager implements JSIModule {
    @DoNotStrip
    private final HybridData mHybridData;
    private final ReactApplicationContext mReactApplicationContext;
    private final TurboModuleManagerDelegate mTurbomoduleManagerDelegate;

    public interface ModuleProvider {
        TurboModule getModule(String str, ReactApplicationContext reactApplicationContext);
    }

    private native HybridData initHybrid(long j, JSCallInvokerHolderImpl jSCallInvokerHolderImpl, TurboModuleManagerDelegate turboModuleManagerDelegate);

    private native void installJSIBindings();

    public void initialize() {
    }

    public void onCatalystInstanceDestroy() {
    }

    static {
        SoLoader.loadLibrary("turbomodulejsijni");
    }

    public TurboModuleManager(ReactApplicationContext reactApplicationContext, JavaScriptContextHolder javaScriptContextHolder, TurboModuleManagerDelegate turboModuleManagerDelegate, JSCallInvokerHolder jSCallInvokerHolder) {
        this.mReactApplicationContext = reactApplicationContext;
        this.mHybridData = initHybrid(javaScriptContextHolder.get(), (JSCallInvokerHolderImpl) jSCallInvokerHolder, turboModuleManagerDelegate);
        this.mTurbomoduleManagerDelegate = turboModuleManagerDelegate;
    }

    @DoNotStrip
    private TurboModule getJavaModule(String str) {
        return this.mTurbomoduleManagerDelegate.getModule(str);
    }

    public void installBindings() {
        installJSIBindings();
    }
}
