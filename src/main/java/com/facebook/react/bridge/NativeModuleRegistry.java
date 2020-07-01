package com.facebook.react.bridge;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.systrace.Systrace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NativeModuleRegistry {
    private final Map<String, ModuleHolder> mModules;
    private final ReactApplicationContext mReactApplicationContext;

    public NativeModuleRegistry(ReactApplicationContext reactApplicationContext, Map<String, ModuleHolder> map) {
        this.mReactApplicationContext = reactApplicationContext;
        this.mModules = map;
    }

    private Map<String, ModuleHolder> getModuleMap() {
        return this.mModules;
    }

    private ReactApplicationContext getReactApplicationContext() {
        return this.mReactApplicationContext;
    }

    Collection<JavaModuleWrapper> getJavaModules(JSInstance jSInstance) {
        Collection arrayList = new ArrayList();
        for (Entry entry : this.mModules.entrySet()) {
            if (!((ModuleHolder) entry.getValue()).isCxxModule()) {
                arrayList.add(new JavaModuleWrapper(jSInstance, (ModuleHolder) entry.getValue()));
            }
        }
        return arrayList;
    }

    Collection<ModuleHolder> getCxxModules() {
        Collection arrayList = new ArrayList();
        for (Entry entry : this.mModules.entrySet()) {
            if (((ModuleHolder) entry.getValue()).isCxxModule()) {
                arrayList.add(entry.getValue());
            }
        }
        return arrayList;
    }

    void registerModules(NativeModuleRegistry nativeModuleRegistry) {
        Assertions.assertCondition(this.mReactApplicationContext.equals(nativeModuleRegistry.getReactApplicationContext()), "Extending native modules with non-matching application contexts.");
        for (Entry entry : nativeModuleRegistry.getModuleMap().entrySet()) {
            String str = (String) entry.getKey();
            if (!this.mModules.containsKey(str)) {
                this.mModules.put(str, (ModuleHolder) entry.getValue());
            }
        }
    }

    void notifyJSInstanceDestroy() {
        this.mReactApplicationContext.assertOnNativeModulesQueueThread();
        Systrace.beginSection(0, "NativeModuleRegistry_notifyJSInstanceDestroy");
        try {
            for (ModuleHolder destroy : this.mModules.values()) {
                destroy.destroy();
            }
        } finally {
            Systrace.endSection(0);
        }
    }

    void notifyJSInstanceInitialized() {
        this.mReactApplicationContext.assertOnNativeModulesQueueThread("From version React Native v0.44, native modules are explicitly not initialized on the UI thread. See https://github.com/facebook/react-native/wiki/Breaking-Changes#d4611211-reactnativeandroidbreaking-move-nativemodule-initialization-off-ui-thread---aaachiuuu  for more details.");
        ReactMarker.logMarker(ReactMarkerConstants.NATIVE_MODULE_INITIALIZE_START);
        Systrace.beginSection(0, "NativeModuleRegistry_notifyJSInstanceInitialized");
        try {
            for (ModuleHolder markInitializable : this.mModules.values()) {
                markInitializable.markInitializable();
            }
        } finally {
            Systrace.endSection(0);
            ReactMarker.logMarker(ReactMarkerConstants.NATIVE_MODULE_INITIALIZE_END);
        }
    }

    public void onBatchComplete() {
        ModuleHolder moduleHolder = (ModuleHolder) this.mModules.get(UIManagerModule.NAME);
        if (moduleHolder != null && moduleHolder.hasInstance()) {
            ((OnBatchCompleteListener) moduleHolder.getModule()).onBatchComplete();
        }
    }

    public <T extends NativeModule> boolean hasModule(Class<T> cls) {
        return this.mModules.containsKey(((ReactModule) cls.getAnnotation(ReactModule.class)).name());
    }

    public <T extends NativeModule> T getModule(Class<T> cls) {
        ReactModule reactModule = (ReactModule) cls.getAnnotation(ReactModule.class);
        if (reactModule != null) {
            Object obj = this.mModules.get(reactModule.name());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(reactModule.name());
            stringBuilder.append(" could not be found. Is it defined in ");
            stringBuilder.append(cls.getName());
            return ((ModuleHolder) Assertions.assertNotNull(obj, stringBuilder.toString())).getModule();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Could not find @ReactModule annotation in class ");
        stringBuilder2.append(cls.getName());
        throw new IllegalArgumentException(stringBuilder2.toString());
    }

    public boolean hasModule(String str) {
        return this.mModules.containsKey(str);
    }

    public NativeModule getModule(String str) {
        Object obj = this.mModules.get(str);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not find module with name ");
        stringBuilder.append(str);
        return ((ModuleHolder) Assertions.assertNotNull(obj, stringBuilder.toString())).getModule();
    }

    public List<NativeModule> getAllModules() {
        List<NativeModule> arrayList = new ArrayList();
        for (ModuleHolder module : this.mModules.values()) {
            arrayList.add(module.getModule());
        }
        return arrayList;
    }
}
