package com.inprogress.reactnativeyoutube;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;

public class YouTubeModule extends ReactContextBaseJavaModule {
    private static final String E_MODULE_ERROR = "E_MODULE_ERROR";
    private ReactApplicationContext mReactContext;

    public String getName() {
        return "YouTubeModule";
    }

    public YouTubeModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mReactContext = reactApplicationContext;
    }

    @ReactMethod
    public void getVideosIndex(final int i, final Promise promise) {
        try {
            ((UIManagerModule) this.mReactContext.getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
                public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                    promise.resolve(Integer.valueOf(((YouTubeManager) nativeViewHierarchyManager.resolveViewManager(i)).getVideosIndex((YouTubeView) nativeViewHierarchyManager.resolveView(i))));
                }
            });
        } catch (Throwable e) {
            promise.reject(E_MODULE_ERROR, e);
        }
    }

    @ReactMethod
    public void getCurrentTime(final int i, final Promise promise) {
        try {
            ((UIManagerModule) this.mReactContext.getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
                public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                    promise.resolve(Integer.valueOf(((YouTubeManager) nativeViewHierarchyManager.resolveViewManager(i)).getCurrentTime((YouTubeView) nativeViewHierarchyManager.resolveView(i))));
                }
            });
        } catch (Throwable e) {
            promise.reject(E_MODULE_ERROR, e);
        }
    }

    @ReactMethod
    public void getDuration(final int i, final Promise promise) {
        try {
            ((UIManagerModule) this.mReactContext.getNativeModule(UIManagerModule.class)).addUIBlock(new UIBlock() {
                public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                    promise.resolve(Integer.valueOf(((YouTubeManager) nativeViewHierarchyManager.resolveViewManager(i)).getDuration((YouTubeView) nativeViewHierarchyManager.resolveView(i))));
                }
            });
        } catch (Throwable e) {
            promise.reject(E_MODULE_ERROR, e);
        }
    }
}
