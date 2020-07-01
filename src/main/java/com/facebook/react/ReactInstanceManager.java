package com.facebook.react;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import androidx.core.view.ViewCompat;
import com.facebook.common.logging.FLog;
import com.facebook.debug.holder.PrinterHolder;
import com.facebook.debug.tags.ReactDebugOverlayTags;
import com.facebook.infer.annotation.Assertions;
import com.facebook.infer.annotation.ThreadConfined;
import com.facebook.infer.annotation.ThreadSafe;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.CatalystInstanceImpl.Builder;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.JSIModulePackage;
import com.facebook.react.bridge.JavaJSExecutor.Factory;
import com.facebook.react.bridge.JavaScriptExecutor;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.bridge.NativeDeltaClient;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.NativeModuleRegistry;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.bridge.ProxyJavaScriptExecutor;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.UIManager;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.queue.ReactQueueConfigurationSpec;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.devsupport.DevSupportManagerFactory;
import com.facebook.react.devsupport.ReactInstanceManagerDevHelper;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.devsupport.interfaces.PackagerStatusCallback;
import com.facebook.react.modules.appregistry.AppRegistry;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import com.facebook.react.modules.fabric.ReactFabric;
import com.facebook.react.packagerconnection.RequestHandler;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.ReactRoot;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import com.facebook.soloader.SoLoader;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@ThreadSafe
public class ReactInstanceManager {
    private static final String TAG = "ReactInstanceManager";
    private final Context mApplicationContext;
    private final Set<ReactRoot> mAttachedReactRoots = Collections.synchronizedSet(new HashSet());
    @Nullable
    private final NotThreadSafeBridgeIdleDebugListener mBridgeIdleDebugListener;
    @Nullable
    private final JSBundleLoader mBundleLoader;
    @Nullable
    private volatile Thread mCreateReactContextThread;
    @Nullable
    private Activity mCurrentActivity;
    @Nullable
    private volatile ReactContext mCurrentReactContext;
    @ThreadConfined("UI")
    @Nullable
    private DefaultHardwareBackBtnHandler mDefaultBackButtonImpl;
    private final DevSupportManager mDevSupportManager;
    private volatile boolean mHasStartedCreatingInitialContext = false;
    private volatile Boolean mHasStartedDestroying = Boolean.valueOf(false);
    @Nullable
    private final JSIModulePackage mJSIModulePackage;
    @Nullable
    private final String mJSMainModulePath;
    private final JavaScriptExecutorFactory mJavaScriptExecutorFactory;
    private volatile LifecycleState mLifecycleState;
    private final MemoryPressureRouter mMemoryPressureRouter;
    @Nullable
    private final NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
    private final List<ReactPackage> mPackages;
    @ThreadConfined("UI")
    @Nullable
    private ReactContextInitParams mPendingReactContextInitParams;
    private final Object mReactContextLock = new Object();
    private final Collection<ReactInstanceEventListener> mReactInstanceEventListeners = Collections.synchronizedSet(new HashSet());
    private final boolean mUseDeveloperSupport;
    private List<ViewManager> mViewManagers;

    private class ReactContextInitParams {
        private final JSBundleLoader mJsBundleLoader;
        private final JavaScriptExecutorFactory mJsExecutorFactory;

        public ReactContextInitParams(JavaScriptExecutorFactory javaScriptExecutorFactory, JSBundleLoader jSBundleLoader) {
            this.mJsExecutorFactory = (JavaScriptExecutorFactory) Assertions.assertNotNull(javaScriptExecutorFactory);
            this.mJsBundleLoader = (JSBundleLoader) Assertions.assertNotNull(jSBundleLoader);
        }

        public JavaScriptExecutorFactory getJsExecutorFactory() {
            return this.mJsExecutorFactory;
        }

        public JSBundleLoader getJsBundleLoader() {
            return this.mJsBundleLoader;
        }
    }

    public interface ReactInstanceEventListener {
        void onReactContextInitialized(ReactContext reactContext);
    }

    public static ReactInstanceManagerBuilder builder() {
        return new ReactInstanceManagerBuilder();
    }

    ReactInstanceManager(Context context, @Nullable Activity activity, @Nullable DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler, JavaScriptExecutorFactory javaScriptExecutorFactory, @Nullable JSBundleLoader jSBundleLoader, @Nullable String str, List<ReactPackage> list, boolean z, @Nullable NotThreadSafeBridgeIdleDebugListener notThreadSafeBridgeIdleDebugListener, LifecycleState lifecycleState, @Nullable UIImplementationProvider uIImplementationProvider, NativeModuleCallExceptionHandler nativeModuleCallExceptionHandler, @Nullable RedBoxHandler redBoxHandler, boolean z2, @Nullable DevBundleDownloadListener devBundleDownloadListener, int i, int i2, @Nullable JSIModulePackage jSIModulePackage, @Nullable Map<String, RequestHandler> map) {
        Context context2 = context;
        Log.d(ReactConstants.TAG, "ReactInstanceManager.ctor()");
        initializeSoLoaderIfNecessary(context);
        DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(context);
        this.mApplicationContext = context2;
        this.mCurrentActivity = activity;
        this.mDefaultBackButtonImpl = defaultHardwareBackBtnHandler;
        this.mJavaScriptExecutorFactory = javaScriptExecutorFactory;
        this.mBundleLoader = jSBundleLoader;
        this.mJSMainModulePath = str;
        this.mPackages = new ArrayList();
        boolean z3 = z;
        this.mUseDeveloperSupport = z3;
        Systrace.beginSection(0, "ReactInstanceManager.initDevSupportManager");
        this.mDevSupportManager = DevSupportManagerFactory.create(context, createDevHelperInterface(), this.mJSMainModulePath, z3, redBoxHandler, devBundleDownloadListener, i, map);
        Systrace.endSection(0);
        this.mBridgeIdleDebugListener = notThreadSafeBridgeIdleDebugListener;
        this.mLifecycleState = lifecycleState;
        this.mMemoryPressureRouter = new MemoryPressureRouter(context);
        this.mNativeModuleCallExceptionHandler = nativeModuleCallExceptionHandler;
        synchronized (this.mPackages) {
            PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.RN_CORE, "RNCore: Use Split Packages");
            this.mPackages.add(new CoreModulesPackage(this, new DefaultHardwareBackBtnHandler() {
                public void invokeDefaultOnBackPressed() {
                    ReactInstanceManager.this.invokeDefaultOnBackPressed();
                }
            }, uIImplementationProvider, z2, i2));
            if (this.mUseDeveloperSupport) {
                this.mPackages.add(new DebugCorePackage());
            }
            this.mPackages.addAll(list);
        }
        this.mJSIModulePackage = jSIModulePackage;
        ReactChoreographer.initialize();
        if (this.mUseDeveloperSupport) {
            this.mDevSupportManager.startInspector();
        }
    }

    private ReactInstanceManagerDevHelper createDevHelperInterface() {
        return new ReactInstanceManagerDevHelper() {
            public void onReloadWithJSDebugger(Factory factory) {
                ReactInstanceManager.this.onReloadWithJSDebugger(factory);
            }

            public void onJSBundleLoadedFromServer(@Nullable NativeDeltaClient nativeDeltaClient) {
                ReactInstanceManager.this.onJSBundleLoadedFromServer(nativeDeltaClient);
            }

            public void toggleElementInspector() {
                ReactInstanceManager.this.toggleElementInspector();
            }

            @Nullable
            public Activity getCurrentActivity() {
                return ReactInstanceManager.this.mCurrentActivity;
            }
        };
    }

    public DevSupportManager getDevSupportManager() {
        return this.mDevSupportManager;
    }

    public MemoryPressureRouter getMemoryPressureRouter() {
        return this.mMemoryPressureRouter;
    }

    private static void initializeSoLoaderIfNecessary(Context context) {
        SoLoader.init(context, false);
    }

    @ThreadConfined("UI")
    public void createReactContextInBackground() {
        Log.d(ReactConstants.TAG, "ReactInstanceManager.createReactContextInBackground()");
        Assertions.assertCondition(this.mHasStartedCreatingInitialContext ^ true, "createReactContextInBackground should only be called when creating the react application for the first time. When reloading JS, e.g. from a new file, explicitlyuse recreateReactContextInBackground");
        this.mHasStartedCreatingInitialContext = true;
        recreateReactContextInBackgroundInner();
    }

    @ThreadConfined("UI")
    public void recreateReactContextInBackground() {
        Assertions.assertCondition(this.mHasStartedCreatingInitialContext, "recreateReactContextInBackground should only be called after the initial createReactContextInBackground call.");
        recreateReactContextInBackgroundInner();
    }

    @ThreadConfined("UI")
    private void recreateReactContextInBackgroundInner() {
        Log.d(ReactConstants.TAG, "ReactInstanceManager.recreateReactContextInBackgroundInner()");
        PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.RN_CORE, "RNCore: recreateReactContextInBackground");
        UiThreadUtil.assertOnUiThread();
        if (this.mUseDeveloperSupport && this.mJSMainModulePath != null) {
            final DeveloperSettings devSettings = this.mDevSupportManager.getDevSettings();
            if (this.mDevSupportManager.hasUpToDateJSBundleInCache() && !devSettings.isRemoteJSDebugEnabled()) {
                onJSBundleLoadedFromServer(null);
                return;
            } else if (!Systrace.isTracing(0)) {
                if (this.mBundleLoader == null) {
                    this.mDevSupportManager.handleReloadJS();
                } else {
                    this.mDevSupportManager.isPackagerRunning(new PackagerStatusCallback() {
                        public void onPackagerStatusFetched(final boolean z) {
                            UiThreadUtil.runOnUiThread(new Runnable() {
                                public void run() {
                                    if (z) {
                                        ReactInstanceManager.this.mDevSupportManager.handleReloadJS();
                                        return;
                                    }
                                    devSettings.setRemoteJSDebugEnabled(false);
                                    ReactInstanceManager.this.recreateReactContextInBackgroundFromBundleLoader();
                                }
                            });
                        }
                    });
                }
                return;
            }
        }
        recreateReactContextInBackgroundFromBundleLoader();
    }

    @ThreadConfined("UI")
    private void recreateReactContextInBackgroundFromBundleLoader() {
        Log.d(ReactConstants.TAG, "ReactInstanceManager.recreateReactContextInBackgroundFromBundleLoader()");
        PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.RN_CORE, "RNCore: load from BundleLoader");
        recreateReactContextInBackground(this.mJavaScriptExecutorFactory, this.mBundleLoader);
    }

    public boolean hasStartedCreatingInitialContext() {
        return this.mHasStartedCreatingInitialContext;
    }

    public void onBackPressed() {
        UiThreadUtil.assertOnUiThread();
        ReactContext reactContext = this.mCurrentReactContext;
        if (reactContext == null) {
            FLog.w(ReactConstants.TAG, "Instance detached from instance manager");
            invokeDefaultOnBackPressed();
            return;
        }
        ((DeviceEventManagerModule) reactContext.getNativeModule(DeviceEventManagerModule.class)).emitHardwareBackPressed();
    }

    private void invokeDefaultOnBackPressed() {
        UiThreadUtil.assertOnUiThread();
        DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler = this.mDefaultBackButtonImpl;
        if (defaultHardwareBackBtnHandler != null) {
            defaultHardwareBackBtnHandler.invokeDefaultOnBackPressed();
        }
    }

    @ThreadConfined("UI")
    public void onNewIntent(Intent intent) {
        UiThreadUtil.assertOnUiThread();
        ReactContext currentReactContext = getCurrentReactContext();
        if (currentReactContext == null) {
            FLog.w(ReactConstants.TAG, "Instance detached from instance manager");
            return;
        }
        String action = intent.getAction();
        Uri data = intent.getData();
        if ("android.intent.action.VIEW".equals(action) && data != null) {
            ((DeviceEventManagerModule) currentReactContext.getNativeModule(DeviceEventManagerModule.class)).emitNewIntentReceived(data);
        }
        currentReactContext.onNewIntent(this.mCurrentActivity, intent);
    }

    private void toggleElementInspector() {
        ReactContext currentReactContext = getCurrentReactContext();
        if (currentReactContext != null) {
            ((RCTDeviceEventEmitter) currentReactContext.getJSModule(RCTDeviceEventEmitter.class)).emit("toggleElementInspector", null);
        }
    }

    @ThreadConfined("UI")
    public void onHostPause() {
        UiThreadUtil.assertOnUiThread();
        this.mDefaultBackButtonImpl = null;
        if (this.mUseDeveloperSupport) {
            this.mDevSupportManager.setDevSupportEnabled(false);
        }
        moveToBeforeResumeLifecycleState();
    }

    @ThreadConfined("UI")
    public void onHostPause(Activity activity) {
        Assertions.assertNotNull(this.mCurrentActivity);
        boolean z = activity == this.mCurrentActivity;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Pausing an activity that is not the current activity, this is incorrect! Current activity: ");
        stringBuilder.append(this.mCurrentActivity.getClass().getSimpleName());
        stringBuilder.append(" Paused activity: ");
        stringBuilder.append(activity.getClass().getSimpleName());
        Assertions.assertCondition(z, stringBuilder.toString());
        onHostPause();
    }

    @ThreadConfined("UI")
    public void onHostResume(Activity activity, DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler) {
        UiThreadUtil.assertOnUiThread();
        this.mDefaultBackButtonImpl = defaultHardwareBackBtnHandler;
        onHostResume(activity);
    }

    @ThreadConfined("UI")
    public void onHostResume(Activity activity) {
        UiThreadUtil.assertOnUiThread();
        this.mCurrentActivity = activity;
        if (this.mUseDeveloperSupport) {
            final View decorView = this.mCurrentActivity.getWindow().getDecorView();
            if (ViewCompat.isAttachedToWindow(decorView)) {
                this.mDevSupportManager.setDevSupportEnabled(true);
            } else {
                decorView.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                    public void onViewDetachedFromWindow(View view) {
                    }

                    public void onViewAttachedToWindow(View view) {
                        decorView.removeOnAttachStateChangeListener(this);
                        ReactInstanceManager.this.mDevSupportManager.setDevSupportEnabled(true);
                    }
                });
            }
        }
        moveToResumedLifecycleState(false);
    }

    @ThreadConfined("UI")
    public void onHostDestroy() {
        UiThreadUtil.assertOnUiThread();
        if (this.mUseDeveloperSupport) {
            this.mDevSupportManager.setDevSupportEnabled(false);
        }
        moveToBeforeCreateLifecycleState();
        this.mCurrentActivity = null;
    }

    @ThreadConfined("UI")
    public void onHostDestroy(Activity activity) {
        if (activity == this.mCurrentActivity) {
            onHostDestroy();
        }
    }

    @ThreadConfined("UI")
    public void destroy() {
        UiThreadUtil.assertOnUiThread();
        PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.RN_CORE, "RNCore: Destroy");
        this.mHasStartedDestroying = Boolean.valueOf(true);
        if (this.mUseDeveloperSupport) {
            this.mDevSupportManager.setDevSupportEnabled(false);
            this.mDevSupportManager.stopInspector();
        }
        moveToBeforeCreateLifecycleState();
        if (this.mCreateReactContextThread != null) {
            this.mCreateReactContextThread = null;
        }
        this.mMemoryPressureRouter.destroy(this.mApplicationContext);
        synchronized (this.mReactContextLock) {
            if (this.mCurrentReactContext != null) {
                this.mCurrentReactContext.destroy();
                this.mCurrentReactContext = null;
            }
        }
        this.mHasStartedCreatingInitialContext = false;
        this.mCurrentActivity = null;
        ResourceDrawableIdHelper.getInstance().clear();
        this.mHasStartedDestroying = Boolean.valueOf(false);
        synchronized (this.mHasStartedDestroying) {
            this.mHasStartedDestroying.notifyAll();
        }
    }

    private synchronized void moveToResumedLifecycleState(boolean z) {
        ReactContext currentReactContext = getCurrentReactContext();
        if (currentReactContext != null && (z || this.mLifecycleState == LifecycleState.BEFORE_RESUME || this.mLifecycleState == LifecycleState.BEFORE_CREATE)) {
            currentReactContext.onHostResume(this.mCurrentActivity);
        }
        this.mLifecycleState = LifecycleState.RESUMED;
    }

    private synchronized void moveToBeforeResumeLifecycleState() {
        ReactContext currentReactContext = getCurrentReactContext();
        if (currentReactContext != null) {
            if (this.mLifecycleState == LifecycleState.BEFORE_CREATE) {
                currentReactContext.onHostResume(this.mCurrentActivity);
                currentReactContext.onHostPause();
            } else if (this.mLifecycleState == LifecycleState.RESUMED) {
                currentReactContext.onHostPause();
            }
        }
        this.mLifecycleState = LifecycleState.BEFORE_RESUME;
    }

    private synchronized void moveToBeforeCreateLifecycleState() {
        ReactContext currentReactContext = getCurrentReactContext();
        if (currentReactContext != null) {
            if (this.mLifecycleState == LifecycleState.RESUMED) {
                currentReactContext.onHostPause();
                this.mLifecycleState = LifecycleState.BEFORE_RESUME;
            }
            if (this.mLifecycleState == LifecycleState.BEFORE_RESUME) {
                currentReactContext.onHostDestroy();
            }
        }
        this.mLifecycleState = LifecycleState.BEFORE_CREATE;
    }

    private synchronized void moveReactContextToCurrentLifecycleState() {
        if (this.mLifecycleState == LifecycleState.RESUMED) {
            moveToResumedLifecycleState(true);
        }
    }

    @ThreadConfined("UI")
    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        ReactContext currentReactContext = getCurrentReactContext();
        if (currentReactContext != null) {
            currentReactContext.onActivityResult(activity, i, i2, intent);
        }
    }

    @ThreadConfined("UI")
    public void showDevOptionsDialog() {
        UiThreadUtil.assertOnUiThread();
        this.mDevSupportManager.showDevOptionsDialog();
    }

    private void clearReactRoot(ReactRoot reactRoot) {
        reactRoot.getRootViewGroup().removeAllViews();
        reactRoot.getRootViewGroup().setId(-1);
    }

    @ThreadConfined("UI")
    public void attachRootView(ReactRoot reactRoot) {
        UiThreadUtil.assertOnUiThread();
        this.mAttachedReactRoots.add(reactRoot);
        clearReactRoot(reactRoot);
        ReactContext currentReactContext = getCurrentReactContext();
        if (this.mCreateReactContextThread == null && currentReactContext != null) {
            attachRootViewToInstance(reactRoot);
        }
    }

    @ThreadConfined("UI")
    public void detachRootView(ReactRoot reactRoot) {
        UiThreadUtil.assertOnUiThread();
        synchronized (this.mAttachedReactRoots) {
            if (this.mAttachedReactRoots.contains(reactRoot)) {
                ReactContext currentReactContext = getCurrentReactContext();
                this.mAttachedReactRoots.remove(reactRoot);
                if (currentReactContext != null && currentReactContext.hasActiveCatalystInstance()) {
                    detachViewFromInstance(reactRoot, currentReactContext.getCatalystInstance());
                }
            }
        }
    }

    public List<ViewManager> getOrCreateViewManagers(ReactApplicationContext reactApplicationContext) {
        ReactMarker.logMarker(ReactMarkerConstants.CREATE_VIEW_MANAGERS_START);
        Systrace.beginSection(0, "createAllViewManagers");
        try {
            List<ViewManager> list;
            if (this.mViewManagers == null) {
                synchronized (this.mPackages) {
                    if (this.mViewManagers == null) {
                        this.mViewManagers = new ArrayList();
                        for (ReactPackage createViewManagers : this.mPackages) {
                            this.mViewManagers.addAll(createViewManagers.createViewManagers(reactApplicationContext));
                        }
                        list = this.mViewManagers;
                    }
                }
                Systrace.endSection(0);
                ReactMarker.logMarker(ReactMarkerConstants.CREATE_VIEW_MANAGERS_END);
                return list;
            }
            list = this.mViewManagers;
            Systrace.endSection(0);
            ReactMarker.logMarker(ReactMarkerConstants.CREATE_VIEW_MANAGERS_END);
            return list;
        } catch (Throwable th) {
            Systrace.endSection(0);
            ReactMarker.logMarker(ReactMarkerConstants.CREATE_VIEW_MANAGERS_END);
        }
    }

    /* JADX WARNING: Missing block: B:8:0x0014, code:
            r3 = r6.mPackages;
     */
    /* JADX WARNING: Missing block: B:9:0x0016, code:
            monitor-enter(r3);
     */
    /* JADX WARNING: Missing block: B:11:?, code:
            r0 = r6.mPackages.iterator();
     */
    /* JADX WARNING: Missing block: B:13:0x0021, code:
            if (r0.hasNext() == false) goto L_0x0037;
     */
    /* JADX WARNING: Missing block: B:14:0x0023, code:
            r4 = (com.facebook.react.ReactPackage) r0.next();
     */
    /* JADX WARNING: Missing block: B:15:0x002b, code:
            if ((r4 instanceof com.facebook.react.ViewManagerOnDemandReactPackage) == false) goto L_0x001d;
     */
    /* JADX WARNING: Missing block: B:16:0x002d, code:
            r4 = ((com.facebook.react.ViewManagerOnDemandReactPackage) r4).createViewManager(r1, r7);
     */
    /* JADX WARNING: Missing block: B:17:0x0033, code:
            if (r4 == null) goto L_0x001d;
     */
    /* JADX WARNING: Missing block: B:18:0x0035, code:
            monitor-exit(r3);
     */
    /* JADX WARNING: Missing block: B:19:0x0036, code:
            return r4;
     */
    /* JADX WARNING: Missing block: B:20:0x0037, code:
            monitor-exit(r3);
     */
    /* JADX WARNING: Missing block: B:21:0x0038, code:
            return null;
     */
    @javax.annotation.Nullable
    public com.facebook.react.uimanager.ViewManager createViewManager(java.lang.String r7) {
        /*
        r6 = this;
        r0 = r6.mReactContextLock;
        monitor-enter(r0);
        r1 = r6.getCurrentReactContext();	 Catch:{ all -> 0x003e }
        r1 = (com.facebook.react.bridge.ReactApplicationContext) r1;	 Catch:{ all -> 0x003e }
        r2 = 0;
        if (r1 == 0) goto L_0x003c;
    L_0x000c:
        r3 = r1.hasActiveCatalystInstance();	 Catch:{ all -> 0x003e }
        if (r3 != 0) goto L_0x0013;
    L_0x0012:
        goto L_0x003c;
    L_0x0013:
        monitor-exit(r0);	 Catch:{ all -> 0x003e }
        r3 = r6.mPackages;
        monitor-enter(r3);
        r0 = r6.mPackages;	 Catch:{ all -> 0x0039 }
        r0 = r0.iterator();	 Catch:{ all -> 0x0039 }
    L_0x001d:
        r4 = r0.hasNext();	 Catch:{ all -> 0x0039 }
        if (r4 == 0) goto L_0x0037;
    L_0x0023:
        r4 = r0.next();	 Catch:{ all -> 0x0039 }
        r4 = (com.facebook.react.ReactPackage) r4;	 Catch:{ all -> 0x0039 }
        r5 = r4 instanceof com.facebook.react.ViewManagerOnDemandReactPackage;	 Catch:{ all -> 0x0039 }
        if (r5 == 0) goto L_0x001d;
    L_0x002d:
        r4 = (com.facebook.react.ViewManagerOnDemandReactPackage) r4;	 Catch:{ all -> 0x0039 }
        r4 = r4.createViewManager(r1, r7);	 Catch:{ all -> 0x0039 }
        if (r4 == 0) goto L_0x001d;
    L_0x0035:
        monitor-exit(r3);	 Catch:{ all -> 0x0039 }
        return r4;
    L_0x0037:
        monitor-exit(r3);	 Catch:{ all -> 0x0039 }
        return r2;
    L_0x0039:
        r7 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0039 }
        throw r7;
    L_0x003c:
        monitor-exit(r0);	 Catch:{ all -> 0x003e }
        return r2;
    L_0x003e:
        r7 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x003e }
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.ReactInstanceManager.createViewManager(java.lang.String):com.facebook.react.uimanager.ViewManager");
    }

    /* JADX WARNING: Missing block: B:8:0x001a, code:
            r4 = r10.mPackages;
     */
    /* JADX WARNING: Missing block: B:9:0x001c, code:
            monitor-enter(r4);
     */
    /* JADX WARNING: Missing block: B:11:?, code:
            r2 = new java.util.HashSet();
            r5 = r10.mPackages.iterator();
     */
    /* JADX WARNING: Missing block: B:13:0x002c, code:
            if (r5.hasNext() == false) goto L_0x0062;
     */
    /* JADX WARNING: Missing block: B:14:0x002e, code:
            r6 = (com.facebook.react.ReactPackage) r5.next();
            com.facebook.systrace.SystraceMessage.beginSection(0, "ReactInstanceManager.getViewManagerName").arg("Package", r6.getClass().getSimpleName()).flush();
     */
    /* JADX WARNING: Missing block: B:15:0x004d, code:
            if ((r6 instanceof com.facebook.react.ViewManagerOnDemandReactPackage) == false) goto L_0x005a;
     */
    /* JADX WARNING: Missing block: B:16:0x004f, code:
            r6 = ((com.facebook.react.ViewManagerOnDemandReactPackage) r6).getViewManagerNames(r3);
     */
    /* JADX WARNING: Missing block: B:17:0x0055, code:
            if (r6 == null) goto L_0x005a;
     */
    /* JADX WARNING: Missing block: B:18:0x0057, code:
            r2.addAll(r6);
     */
    /* JADX WARNING: Missing block: B:19:0x005a, code:
            com.facebook.systrace.SystraceMessage.endSection(0).flush();
     */
    /* JADX WARNING: Missing block: B:20:0x0062, code:
            com.facebook.systrace.Systrace.endSection(0);
            r0 = new java.util.ArrayList(r2);
     */
    /* JADX WARNING: Missing block: B:21:0x006a, code:
            monitor-exit(r4);
     */
    /* JADX WARNING: Missing block: B:22:0x006b, code:
            return r0;
     */
    @javax.annotation.Nullable
    public java.util.List<java.lang.String> getViewManagerNames() {
        /*
        r10 = this;
        r0 = 0;
        r2 = "ReactInstanceManager.getViewManagerNames";
        com.facebook.systrace.Systrace.beginSection(r0, r2);
        r2 = r10.mReactContextLock;
        monitor-enter(r2);
        r3 = r10.getCurrentReactContext();	 Catch:{ all -> 0x0072 }
        r3 = (com.facebook.react.bridge.ReactApplicationContext) r3;	 Catch:{ all -> 0x0072 }
        if (r3 == 0) goto L_0x006f;
    L_0x0012:
        r4 = r3.hasActiveCatalystInstance();	 Catch:{ all -> 0x0072 }
        if (r4 != 0) goto L_0x0019;
    L_0x0018:
        goto L_0x006f;
    L_0x0019:
        monitor-exit(r2);	 Catch:{ all -> 0x0072 }
        r4 = r10.mPackages;
        monitor-enter(r4);
        r2 = new java.util.HashSet;	 Catch:{ all -> 0x006c }
        r2.<init>();	 Catch:{ all -> 0x006c }
        r5 = r10.mPackages;	 Catch:{ all -> 0x006c }
        r5 = r5.iterator();	 Catch:{ all -> 0x006c }
    L_0x0028:
        r6 = r5.hasNext();	 Catch:{ all -> 0x006c }
        if (r6 == 0) goto L_0x0062;
    L_0x002e:
        r6 = r5.next();	 Catch:{ all -> 0x006c }
        r6 = (com.facebook.react.ReactPackage) r6;	 Catch:{ all -> 0x006c }
        r7 = "ReactInstanceManager.getViewManagerName";
        r7 = com.facebook.systrace.SystraceMessage.beginSection(r0, r7);	 Catch:{ all -> 0x006c }
        r8 = "Package";
        r9 = r6.getClass();	 Catch:{ all -> 0x006c }
        r9 = r9.getSimpleName();	 Catch:{ all -> 0x006c }
        r7 = r7.arg(r8, r9);	 Catch:{ all -> 0x006c }
        r7.flush();	 Catch:{ all -> 0x006c }
        r7 = r6 instanceof com.facebook.react.ViewManagerOnDemandReactPackage;	 Catch:{ all -> 0x006c }
        if (r7 == 0) goto L_0x005a;
    L_0x004f:
        r6 = (com.facebook.react.ViewManagerOnDemandReactPackage) r6;	 Catch:{ all -> 0x006c }
        r6 = r6.getViewManagerNames(r3);	 Catch:{ all -> 0x006c }
        if (r6 == 0) goto L_0x005a;
    L_0x0057:
        r2.addAll(r6);	 Catch:{ all -> 0x006c }
    L_0x005a:
        r6 = com.facebook.systrace.SystraceMessage.endSection(r0);	 Catch:{ all -> 0x006c }
        r6.flush();	 Catch:{ all -> 0x006c }
        goto L_0x0028;
    L_0x0062:
        com.facebook.systrace.Systrace.endSection(r0);	 Catch:{ all -> 0x006c }
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x006c }
        r0.<init>(r2);	 Catch:{ all -> 0x006c }
        monitor-exit(r4);	 Catch:{ all -> 0x006c }
        return r0;
    L_0x006c:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x006c }
        throw r0;
    L_0x006f:
        r0 = 0;
        monitor-exit(r2);	 Catch:{ all -> 0x0072 }
        return r0;
    L_0x0072:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0072 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.ReactInstanceManager.getViewManagerNames():java.util.List<java.lang.String>");
    }

    public void addReactInstanceEventListener(ReactInstanceEventListener reactInstanceEventListener) {
        this.mReactInstanceEventListeners.add(reactInstanceEventListener);
    }

    public void removeReactInstanceEventListener(ReactInstanceEventListener reactInstanceEventListener) {
        this.mReactInstanceEventListeners.remove(reactInstanceEventListener);
    }

    @VisibleForTesting
    @Nullable
    public ReactContext getCurrentReactContext() {
        ReactContext reactContext;
        synchronized (this.mReactContextLock) {
            reactContext = this.mCurrentReactContext;
        }
        return reactContext;
    }

    public LifecycleState getLifecycleState() {
        return this.mLifecycleState;
    }

    public String getJsExecutorName() {
        return this.mJavaScriptExecutorFactory.toString();
    }

    @ThreadConfined("UI")
    private void onReloadWithJSDebugger(Factory factory) {
        Log.d(ReactConstants.TAG, "ReactInstanceManager.onReloadWithJSDebugger()");
        recreateReactContextInBackground(new ProxyJavaScriptExecutor.Factory(factory), JSBundleLoader.createRemoteDebuggerBundleLoader(this.mDevSupportManager.getJSBundleURLForRemoteDebugging(), this.mDevSupportManager.getSourceUrl()));
    }

    @ThreadConfined("UI")
    private void onJSBundleLoadedFromServer(@Nullable NativeDeltaClient nativeDeltaClient) {
        JSBundleLoader createCachedBundleFromNetworkLoader;
        Log.d(ReactConstants.TAG, "ReactInstanceManager.onJSBundleLoadedFromServer()");
        if (nativeDeltaClient == null) {
            createCachedBundleFromNetworkLoader = JSBundleLoader.createCachedBundleFromNetworkLoader(this.mDevSupportManager.getSourceUrl(), this.mDevSupportManager.getDownloadedJSBundleFile());
        } else {
            createCachedBundleFromNetworkLoader = JSBundleLoader.createDeltaFromNetworkLoader(this.mDevSupportManager.getSourceUrl(), nativeDeltaClient);
        }
        recreateReactContextInBackground(this.mJavaScriptExecutorFactory, createCachedBundleFromNetworkLoader);
    }

    @ThreadConfined("UI")
    private void recreateReactContextInBackground(JavaScriptExecutorFactory javaScriptExecutorFactory, JSBundleLoader jSBundleLoader) {
        Log.d(ReactConstants.TAG, "ReactInstanceManager.recreateReactContextInBackground()");
        UiThreadUtil.assertOnUiThread();
        ReactContextInitParams reactContextInitParams = new ReactContextInitParams(javaScriptExecutorFactory, jSBundleLoader);
        if (this.mCreateReactContextThread == null) {
            runCreateReactContextOnNewThread(reactContextInitParams);
        } else {
            this.mPendingReactContextInitParams = reactContextInitParams;
        }
    }

    @ThreadConfined("UI")
    private void runCreateReactContextOnNewThread(final ReactContextInitParams reactContextInitParams) {
        Log.d(ReactConstants.TAG, "ReactInstanceManager.runCreateReactContextOnNewThread()");
        UiThreadUtil.assertOnUiThread();
        synchronized (this.mAttachedReactRoots) {
            synchronized (this.mReactContextLock) {
                if (this.mCurrentReactContext != null) {
                    tearDownReactContext(this.mCurrentReactContext);
                    this.mCurrentReactContext = null;
                }
            }
        }
        this.mCreateReactContextThread = new Thread(null, new Runnable() {
            public void run() {
                ReactMarker.logMarker(ReactMarkerConstants.REACT_CONTEXT_THREAD_END);
                synchronized (ReactInstanceManager.this.mHasStartedDestroying) {
                    while (true) {
                        try {
                        } catch (InterruptedException unused) {
                            if (ReactInstanceManager.this.mHasStartedDestroying.booleanValue()) {
                                ReactInstanceManager.this.mHasStartedDestroying.wait();
                            } else {
                                ReactInstanceManager.this.mHasStartedCreatingInitialContext = true;
                                try {
                                    Process.setThreadPriority(-4);
                                    ReactMarker.logMarker(ReactMarkerConstants.VM_INIT);
                                    final ReactApplicationContext access$900 = ReactInstanceManager.this.createReactContext(reactContextInitParams.getJsExecutorFactory().create(), reactContextInitParams.getJsBundleLoader());
                                    ReactInstanceManager.this.mCreateReactContextThread = null;
                                    ReactMarker.logMarker(ReactMarkerConstants.PRE_SETUP_REACT_CONTEXT_START);
                                    Runnable anonymousClass1 = new Runnable() {
                                        public void run() {
                                            if (ReactInstanceManager.this.mPendingReactContextInitParams != null) {
                                                ReactInstanceManager.this.runCreateReactContextOnNewThread(ReactInstanceManager.this.mPendingReactContextInitParams);
                                                ReactInstanceManager.this.mPendingReactContextInitParams = null;
                                            }
                                        }
                                    };
                                    access$900.runOnNativeModulesQueueThread(new Runnable() {
                                        public void run() {
                                            try {
                                                ReactInstanceManager.this.setupReactContext(access$900);
                                            } catch (Exception e) {
                                                ReactInstanceManager.this.mDevSupportManager.handleException(e);
                                            }
                                        }
                                    });
                                    UiThreadUtil.runOnUiThread(anonymousClass1);
                                    return;
                                } catch (Exception e) {
                                    ReactInstanceManager.this.mDevSupportManager.handleException(e);
                                    return;
                                }
                            }
                        }
                    }
                    while (true) {
                    }
                }
            }
        }, "create_react_context");
        ReactMarker.logMarker(ReactMarkerConstants.REACT_CONTEXT_THREAD_START);
        this.mCreateReactContextThread.start();
    }

    private void setupReactContext(final ReactApplicationContext reactApplicationContext) {
        Log.d(ReactConstants.TAG, "ReactInstanceManager.setupReactContext()");
        ReactMarker.logMarker(ReactMarkerConstants.PRE_SETUP_REACT_CONTEXT_END);
        ReactMarker.logMarker(ReactMarkerConstants.SETUP_REACT_CONTEXT_START);
        Systrace.beginSection(0, "setupReactContext");
        synchronized (this.mAttachedReactRoots) {
            synchronized (this.mReactContextLock) {
                this.mCurrentReactContext = (ReactContext) Assertions.assertNotNull(reactApplicationContext);
            }
            CatalystInstance catalystInstance = (CatalystInstance) Assertions.assertNotNull(reactApplicationContext.getCatalystInstance());
            catalystInstance.initialize();
            this.mDevSupportManager.onNewReactContextCreated(reactApplicationContext);
            this.mMemoryPressureRouter.addMemoryPressureListener(catalystInstance);
            moveReactContextToCurrentLifecycleState();
            ReactMarker.logMarker(ReactMarkerConstants.ATTACH_MEASURED_ROOT_VIEWS_START);
            for (ReactRoot attachRootViewToInstance : this.mAttachedReactRoots) {
                attachRootViewToInstance(attachRootViewToInstance);
            }
            ReactMarker.logMarker(ReactMarkerConstants.ATTACH_MEASURED_ROOT_VIEWS_END);
        }
        final ReactInstanceEventListener[] reactInstanceEventListenerArr = (ReactInstanceEventListener[]) this.mReactInstanceEventListeners.toArray(new ReactInstanceEventListener[this.mReactInstanceEventListeners.size()]);
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                for (ReactInstanceEventListener onReactContextInitialized : reactInstanceEventListenerArr) {
                    onReactContextInitialized.onReactContextInitialized(reactApplicationContext);
                }
            }
        });
        Systrace.endSection(0);
        ReactMarker.logMarker(ReactMarkerConstants.SETUP_REACT_CONTEXT_END);
        reactApplicationContext.runOnJSQueueThread(new Runnable() {
            public void run() {
                Process.setThreadPriority(0);
                ReactMarker.logMarker(ReactMarkerConstants.CHANGE_THREAD_PRIORITY, "js_default");
            }
        });
        reactApplicationContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                Process.setThreadPriority(0);
            }
        });
    }

    private void attachRootViewToInstance(final ReactRoot reactRoot) {
        WritableMap writableNativeMap;
        Log.d(ReactConstants.TAG, "ReactInstanceManager.attachRootViewToInstance()");
        Systrace.beginSection(0, "attachRootViewToInstance");
        UIManager uIManager = UIManagerHelper.getUIManager(this.mCurrentReactContext, reactRoot.getUIManagerType());
        Bundle appProperties = reactRoot.getAppProperties();
        View rootViewGroup = reactRoot.getRootViewGroup();
        if (appProperties == null) {
            writableNativeMap = new WritableNativeMap();
        } else {
            writableNativeMap = Arguments.fromBundle(appProperties);
        }
        final int addRootView = uIManager.addRootView(rootViewGroup, writableNativeMap, reactRoot.getInitialUITemplate());
        reactRoot.setRootViewTag(addRootView);
        reactRoot.runApplication();
        Systrace.beginAsyncSection(0, "pre_rootView.onAttachedToReactInstance", addRootView);
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                Systrace.endAsyncSection(0, "pre_rootView.onAttachedToReactInstance", addRootView);
                reactRoot.onStage(101);
            }
        });
        Systrace.endSection(0);
    }

    private void detachViewFromInstance(ReactRoot reactRoot, CatalystInstance catalystInstance) {
        Log.d(ReactConstants.TAG, "ReactInstanceManager.detachViewFromInstance()");
        UiThreadUtil.assertOnUiThread();
        if (reactRoot.getUIManagerType() == 2) {
            ((ReactFabric) catalystInstance.getJSModule(ReactFabric.class)).unmountComponentAtNode(reactRoot.getRootViewTag());
        } else {
            ((AppRegistry) catalystInstance.getJSModule(AppRegistry.class)).unmountApplicationComponentAtRootTag(reactRoot.getRootViewTag());
        }
    }

    private void tearDownReactContext(ReactContext reactContext) {
        Log.d(ReactConstants.TAG, "ReactInstanceManager.tearDownReactContext()");
        UiThreadUtil.assertOnUiThread();
        if (this.mLifecycleState == LifecycleState.RESUMED) {
            reactContext.onHostPause();
        }
        synchronized (this.mAttachedReactRoots) {
            for (ReactRoot clearReactRoot : this.mAttachedReactRoots) {
                clearReactRoot(clearReactRoot);
            }
        }
        reactContext.destroy();
        this.mDevSupportManager.onReactInstanceDestroyed(reactContext);
        this.mMemoryPressureRouter.removeMemoryPressureListener(reactContext.getCatalystInstance());
    }

    private ReactApplicationContext createReactContext(JavaScriptExecutor javaScriptExecutor, JSBundleLoader jSBundleLoader) {
        Log.d(ReactConstants.TAG, "ReactInstanceManager.createReactContext()");
        ReactMarker.logMarker(ReactMarkerConstants.CREATE_REACT_CONTEXT_START, javaScriptExecutor.getName());
        ReactApplicationContext reactApplicationContext = new ReactApplicationContext(this.mApplicationContext);
        NativeModuleCallExceptionHandler nativeModuleCallExceptionHandler = this.mNativeModuleCallExceptionHandler;
        if (nativeModuleCallExceptionHandler == null) {
            nativeModuleCallExceptionHandler = this.mDevSupportManager;
        }
        reactApplicationContext.setNativeModuleCallExceptionHandler(nativeModuleCallExceptionHandler);
        Builder nativeModuleCallExceptionHandler2 = new Builder().setReactQueueConfigurationSpec(ReactQueueConfigurationSpec.createDefault()).setJSExecutor(javaScriptExecutor).setRegistry(processPackages(reactApplicationContext, this.mPackages, false)).setJSBundleLoader(jSBundleLoader).setNativeModuleCallExceptionHandler(nativeModuleCallExceptionHandler);
        ReactMarker.logMarker(ReactMarkerConstants.CREATE_CATALYST_INSTANCE_START);
        Systrace.beginSection(0, "createCatalystInstance");
        try {
            CatalystInstance build = nativeModuleCallExceptionHandler2.build();
            JSIModulePackage jSIModulePackage = this.mJSIModulePackage;
            if (jSIModulePackage != null) {
                build.addJSIModules(jSIModulePackage.getJSIModules(reactApplicationContext, build.getJavaScriptContextHolder()));
            }
            NotThreadSafeBridgeIdleDebugListener notThreadSafeBridgeIdleDebugListener = this.mBridgeIdleDebugListener;
            if (notThreadSafeBridgeIdleDebugListener != null) {
                build.addBridgeIdleDebugListener(notThreadSafeBridgeIdleDebugListener);
            }
            if (Systrace.isTracing(0)) {
                build.setGlobalVariable("__RCTProfileIsProfiling", "true");
            }
            ReactMarker.logMarker(ReactMarkerConstants.PRE_RUN_JS_BUNDLE_START);
            Systrace.beginSection(0, "runJSBundle");
            build.runJSBundle();
            Systrace.endSection(0);
            reactApplicationContext.initializeWithInstance(build);
            return reactApplicationContext;
        } finally {
            Systrace.endSection(0);
            ReactMarker.logMarker(ReactMarkerConstants.CREATE_CATALYST_INSTANCE_END);
        }
    }

    private NativeModuleRegistry processPackages(ReactApplicationContext reactApplicationContext, List<ReactPackage> list, boolean z) {
        NativeModuleRegistryBuilder nativeModuleRegistryBuilder = new NativeModuleRegistryBuilder(reactApplicationContext, this);
        ReactMarker.logMarker(ReactMarkerConstants.PROCESS_PACKAGES_START);
        synchronized (this.mPackages) {
            for (ReactPackage reactPackage : list) {
                if (!z || !this.mPackages.contains(reactPackage)) {
                    Systrace.beginSection(0, "createAndProcessCustomReactPackage");
                    if (z) {
                        try {
                            this.mPackages.add(reactPackage);
                        } catch (Throwable th) {
                            Systrace.endSection(0);
                        }
                    }
                    processPackage(reactPackage, nativeModuleRegistryBuilder);
                    Systrace.endSection(0);
                }
            }
        }
        ReactMarker.logMarker(ReactMarkerConstants.PROCESS_PACKAGES_END);
        ReactMarker.logMarker(ReactMarkerConstants.BUILD_NATIVE_MODULE_REGISTRY_START);
        Systrace.beginSection(0, "buildNativeModuleRegistry");
        try {
            NativeModuleRegistry build = nativeModuleRegistryBuilder.build();
            return build;
        } finally {
            Systrace.endSection(0);
            ReactMarker.logMarker(ReactMarkerConstants.BUILD_NATIVE_MODULE_REGISTRY_END);
        }
    }

    private void processPackage(ReactPackage reactPackage, NativeModuleRegistryBuilder nativeModuleRegistryBuilder) {
        SystraceMessage.beginSection(0, "processPackage").arg("className", reactPackage.getClass().getSimpleName()).flush();
        boolean z = reactPackage instanceof ReactPackageLogger;
        if (z) {
            ((ReactPackageLogger) reactPackage).startProcessPackage();
        }
        nativeModuleRegistryBuilder.processPackage(reactPackage);
        if (z) {
            ((ReactPackageLogger) reactPackage).endProcessPackage();
        }
        SystraceMessage.endSection(0).flush();
    }
}
