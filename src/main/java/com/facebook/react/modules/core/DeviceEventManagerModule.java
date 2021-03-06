package com.facebook.react.modules.core;

import android.net.Uri;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.google.android.gms.common.internal.ImagesContract;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ReactModule(name = "DeviceEventManager")
public class DeviceEventManagerModule extends ReactContextBaseJavaModule {
    public static final String NAME = "DeviceEventManager";
    private final Runnable mInvokeDefaultBackPressRunnable;

    public interface RCTDeviceEventEmitter extends JavaScriptModule {
        void emit(@Nonnull String str, @Nullable Object obj);
    }

    public String getName() {
        return NAME;
    }

    public DeviceEventManagerModule(ReactApplicationContext reactApplicationContext, final DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler) {
        super(reactApplicationContext);
        this.mInvokeDefaultBackPressRunnable = new Runnable() {
            public void run() {
                UiThreadUtil.assertOnUiThread();
                defaultHardwareBackBtnHandler.invokeDefaultOnBackPressed();
            }
        };
    }

    public void emitHardwareBackPressed() {
        ((RCTDeviceEventEmitter) access$100().getJSModule(RCTDeviceEventEmitter.class)).emit("hardwareBackPress", null);
    }

    public void emitNewIntentReceived(Uri uri) {
        WritableMap createMap = Arguments.createMap();
        String uri2 = uri.toString();
        String str = ImagesContract.URL;
        createMap.putString(str, uri2);
        ((RCTDeviceEventEmitter) access$100().getJSModule(RCTDeviceEventEmitter.class)).emit(str, createMap);
    }

    @ReactMethod
    public void invokeDefaultBackPressHandler() {
        access$100().runOnUiQueueThread(this.mInvokeDefaultBackPressRunnable);
    }
}
