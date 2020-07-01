package com.facebook.react;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.ExceptionsManagerModule;
import com.facebook.react.modules.core.HeadlessJsTaskSupportModule;
import com.facebook.react.modules.core.Timing;
import com.facebook.react.modules.debug.DevSettingsModule;
import com.facebook.react.modules.debug.SourceCodeModule;
import com.facebook.react.modules.deviceinfo.DeviceInfoModule;
import com.facebook.react.modules.systeminfo.AndroidInfoModule;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.UIManagerModule.ViewManagerResolver;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.systrace.Systrace;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

class CoreModulesPackage extends TurboReactPackage implements ReactPackageLogger {
    private final DefaultHardwareBackBtnHandler mHardwareBackBtnHandler;
    private final boolean mLazyViewManagersEnabled;
    private final int mMinTimeLeftInFrameForNonBatchedOperationMs;
    private final ReactInstanceManager mReactInstanceManager;

    CoreModulesPackage(ReactInstanceManager reactInstanceManager, DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler, @Nullable UIImplementationProvider uIImplementationProvider, boolean z, int i) {
        this.mReactInstanceManager = reactInstanceManager;
        this.mHardwareBackBtnHandler = defaultHardwareBackBtnHandler;
        this.mLazyViewManagersEnabled = z;
        this.mMinTimeLeftInFrameForNonBatchedOperationMs = i;
    }

    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        String str = "No ReactModuleInfoProvider for CoreModulesPackage$$ReactModuleInfoProvider";
        try {
            return (ReactModuleInfoProvider) Class.forName("com.facebook.react.CoreModulesPackage$$ReactModuleInfoProvider").newInstance();
        } catch (ClassNotFoundException unused) {
            r0 = new Class[9];
            int i = 0;
            r0[0] = AndroidInfoModule.class;
            r0[1] = DeviceEventManagerModule.class;
            r0[2] = DeviceInfoModule.class;
            r0[3] = DevSettingsModule.class;
            r0[4] = ExceptionsManagerModule.class;
            r0[5] = HeadlessJsTaskSupportModule.class;
            r0[6] = SourceCodeModule.class;
            r0[7] = Timing.class;
            r0[8] = UIManagerModule.class;
            final Map hashMap = new HashMap();
            int length = r0.length;
            while (i < length) {
                Class cls = r0[i];
                ReactModule reactModule = (ReactModule) cls.getAnnotation(ReactModule.class);
                hashMap.put(reactModule.name(), new ReactModuleInfo(reactModule.name(), cls.getName(), reactModule.canOverrideExistingModule(), reactModule.needsEagerInit(), reactModule.hasConstants(), reactModule.isCxxModule(), false));
                i++;
            }
            return new ReactModuleInfoProvider() {
                public Map<String, ReactModuleInfo> getReactModuleInfos() {
                    return hashMap;
                }
            };
        } catch (Throwable e) {
            throw new RuntimeException(str, e);
        } catch (Throwable e2) {
            throw new RuntimeException(str, e2);
        }
    }

    public com.facebook.react.bridge.NativeModule getModule(java.lang.String r3, com.facebook.react.bridge.ReactApplicationContext r4) {
        /*
        r2 = this;
        r0 = r3.hashCode();
        switch(r0) {
            case -1789797270: goto L_0x0059;
            case -1633589448: goto L_0x004f;
            case -1520650172: goto L_0x0044;
            case -1037217463: goto L_0x003a;
            case -790603268: goto L_0x0030;
            case 512434409: goto L_0x0026;
            case 881516744: goto L_0x001c;
            case 1256514152: goto L_0x0012;
            case 1861242489: goto L_0x0008;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0063;
    L_0x0008:
        r0 = "UIManager";
        r0 = r3.equals(r0);
        if (r0 == 0) goto L_0x0063;
    L_0x0010:
        r0 = 7;
        goto L_0x0064;
    L_0x0012:
        r0 = "HeadlessJsTaskSupport";
        r0 = r3.equals(r0);
        if (r0 == 0) goto L_0x0063;
    L_0x001a:
        r0 = 4;
        goto L_0x0064;
    L_0x001c:
        r0 = "SourceCode";
        r0 = r3.equals(r0);
        if (r0 == 0) goto L_0x0063;
    L_0x0024:
        r0 = 5;
        goto L_0x0064;
    L_0x0026:
        r0 = "ExceptionsManager";
        r0 = r3.equals(r0);
        if (r0 == 0) goto L_0x0063;
    L_0x002e:
        r0 = 3;
        goto L_0x0064;
    L_0x0030:
        r0 = "PlatformConstants";
        r0 = r3.equals(r0);
        if (r0 == 0) goto L_0x0063;
    L_0x0038:
        r0 = 0;
        goto L_0x0064;
    L_0x003a:
        r0 = "DeviceEventManager";
        r0 = r3.equals(r0);
        if (r0 == 0) goto L_0x0063;
    L_0x0042:
        r0 = 1;
        goto L_0x0064;
    L_0x0044:
        r0 = "DeviceInfo";
        r0 = r3.equals(r0);
        if (r0 == 0) goto L_0x0063;
    L_0x004c:
        r0 = 8;
        goto L_0x0064;
    L_0x004f:
        r0 = "DevSettings";
        r0 = r3.equals(r0);
        if (r0 == 0) goto L_0x0063;
    L_0x0057:
        r0 = 2;
        goto L_0x0064;
    L_0x0059:
        r0 = "Timing";
        r0 = r3.equals(r0);
        if (r0 == 0) goto L_0x0063;
    L_0x0061:
        r0 = 6;
        goto L_0x0064;
    L_0x0063:
        r0 = -1;
    L_0x0064:
        switch(r0) {
            case 0: goto L_0x00c1;
            case 1: goto L_0x00b9;
            case 2: goto L_0x00ad;
            case 3: goto L_0x00a1;
            case 4: goto L_0x009b;
            case 5: goto L_0x0095;
            case 6: goto L_0x0089;
            case 7: goto L_0x0084;
            case 8: goto L_0x007e;
            default: goto L_0x0067;
        };
    L_0x0067:
        r4 = new java.lang.IllegalArgumentException;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "In CoreModulesPackage, could not find Native module for ";
        r0.append(r1);
        r0.append(r3);
        r3 = r0.toString();
        r4.<init>(r3);
        throw r4;
    L_0x007e:
        r3 = new com.facebook.react.modules.deviceinfo.DeviceInfoModule;
        r3.<init>(r4);
        return r3;
    L_0x0084:
        r3 = r2.createUIManager(r4);
        return r3;
    L_0x0089:
        r3 = new com.facebook.react.modules.core.Timing;
        r0 = r2.mReactInstanceManager;
        r0 = r0.getDevSupportManager();
        r3.<init>(r4, r0);
        return r3;
    L_0x0095:
        r3 = new com.facebook.react.modules.debug.SourceCodeModule;
        r3.<init>(r4);
        return r3;
    L_0x009b:
        r3 = new com.facebook.react.modules.core.HeadlessJsTaskSupportModule;
        r3.<init>(r4);
        return r3;
    L_0x00a1:
        r3 = new com.facebook.react.modules.core.ExceptionsManagerModule;
        r4 = r2.mReactInstanceManager;
        r4 = r4.getDevSupportManager();
        r3.<init>(r4);
        return r3;
    L_0x00ad:
        r3 = new com.facebook.react.modules.debug.DevSettingsModule;
        r4 = r2.mReactInstanceManager;
        r4 = r4.getDevSupportManager();
        r3.<init>(r4);
        return r3;
    L_0x00b9:
        r3 = new com.facebook.react.modules.core.DeviceEventManagerModule;
        r0 = r2.mHardwareBackBtnHandler;
        r3.<init>(r4, r0);
        return r3;
    L_0x00c1:
        r3 = new com.facebook.react.modules.systeminfo.AndroidInfoModule;
        r3.<init>(r4);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.CoreModulesPackage.getModule(java.lang.String, com.facebook.react.bridge.ReactApplicationContext):com.facebook.react.bridge.NativeModule");
    }

    private UIManagerModule createUIManager(ReactApplicationContext reactApplicationContext) {
        ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_START);
        Systrace.beginSection(0, "createUIManagerModule");
        try {
            UIManagerModule uIManagerModule;
            if (this.mLazyViewManagersEnabled) {
                uIManagerModule = new UIManagerModule(reactApplicationContext, new ViewManagerResolver() {
                    @Nullable
                    public ViewManager getViewManager(String str) {
                        return CoreModulesPackage.this.mReactInstanceManager.createViewManager(str);
                    }

                    public List<String> getViewManagerNames() {
                        return CoreModulesPackage.this.mReactInstanceManager.getViewManagerNames();
                    }
                }, this.mMinTimeLeftInFrameForNonBatchedOperationMs);
                return uIManagerModule;
            }
            uIManagerModule = this.mReactInstanceManager.getOrCreateViewManagers(reactApplicationContext);
            UIManagerModule uIManagerModule2 = new UIManagerModule(reactApplicationContext, (List) uIManagerModule, this.mMinTimeLeftInFrameForNonBatchedOperationMs);
            Systrace.endSection(0);
            ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_END);
            return uIManagerModule2;
        } finally {
            Systrace.endSection(0);
            ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_END);
        }
    }

    public void startProcessPackage() {
        ReactMarker.logMarker(ReactMarkerConstants.PROCESS_CORE_REACT_PACKAGE_START);
    }

    public void endProcessPackage() {
        ReactMarker.logMarker(ReactMarkerConstants.PROCESS_CORE_REACT_PACKAGE_END);
    }
}
