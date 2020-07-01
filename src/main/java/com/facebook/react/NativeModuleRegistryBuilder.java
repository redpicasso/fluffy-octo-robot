package com.facebook.react;

import com.facebook.react.bridge.ModuleHolder;
import com.facebook.react.bridge.NativeModuleRegistry;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.config.ReactFeatureFlags;
import java.util.HashMap;
import java.util.Map;

public class NativeModuleRegistryBuilder {
    private final Map<String, ModuleHolder> mModules = new HashMap();
    private final ReactApplicationContext mReactApplicationContext;
    private final ReactInstanceManager mReactInstanceManager;

    public NativeModuleRegistryBuilder(ReactApplicationContext reactApplicationContext, ReactInstanceManager reactInstanceManager) {
        this.mReactApplicationContext = reactApplicationContext;
        this.mReactInstanceManager = reactInstanceManager;
    }

    public void processPackage(ReactPackage reactPackage) {
        Iterable nativeModuleIterator;
        if (reactPackage instanceof LazyReactPackage) {
            nativeModuleIterator = ((LazyReactPackage) reactPackage).getNativeModuleIterator(this.mReactApplicationContext);
        } else if (reactPackage instanceof TurboReactPackage) {
            nativeModuleIterator = ((TurboReactPackage) reactPackage).getNativeModuleIterator(this.mReactApplicationContext);
        } else {
            nativeModuleIterator = ReactPackageHelper.getNativeModuleIterator(reactPackage, this.mReactApplicationContext, this.mReactInstanceManager);
        }
        for (ModuleHolder moduleHolder : nativeModuleIterator) {
            String name = moduleHolder.getName();
            if (this.mModules.containsKey(name)) {
                ModuleHolder moduleHolder2 = (ModuleHolder) this.mModules.get(name);
                if (moduleHolder.getCanOverrideExistingModule()) {
                    this.mModules.remove(moduleHolder2);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Native module ");
                    stringBuilder.append(name);
                    stringBuilder.append(" tried to override ");
                    stringBuilder.append(moduleHolder2.getClassName());
                    stringBuilder.append(". Check the getPackages() method in MainApplication.java, it might be that module is being created twice. If this was your intention, set canOverrideExistingModule=true");
                    throw new IllegalStateException(stringBuilder.toString());
                }
            }
            if (!ReactFeatureFlags.useTurboModules || !moduleHolder.isTurboModule()) {
                this.mModules.put(name, moduleHolder);
            }
        }
    }

    public NativeModuleRegistry build() {
        return new NativeModuleRegistry(this.mReactApplicationContext, this.mModules);
    }
}
