package org.wonday.orientation;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings.System;
import android.view.OrientationEventListener;
import android.view.WindowManager;
import androidx.exifinterface.media.ExifInterface;
import com.brentvatne.react.ReactVideoView;
import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public class OrientationModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    final ReactApplicationContext ctx;
    private boolean isLocked = false;
    private String lastDeviceOrientationValue;
    private String lastOrientationValue;
    final OrientationEventListener mOrientationListener;
    final BroadcastReceiver mReceiver;

    public String getName() {
        return ExifInterface.TAG_ORIENTATION;
    }

    public OrientationModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        String str = "";
        this.lastOrientationValue = str;
        this.lastDeviceOrientationValue = str;
        this.ctx = reactApplicationContext;
        this.mOrientationListener = new OrientationEventListener(reactApplicationContext, 2) {
            public void onOrientationChanged(int i) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("DeviceOrientation changed to ");
                stringBuilder.append(i);
                FLog.d(ReactConstants.TAG, stringBuilder.toString());
                String access$000 = OrientationModule.this.lastDeviceOrientationValue;
                if (i == -1) {
                    access$000 = "UNKNOWN";
                } else if (i > 355 || i < 5) {
                    access$000 = "PORTRAIT";
                } else if (i > 85 && i < 95) {
                    access$000 = "LANDSCAPE-RIGHT";
                } else if (i > NikonType2MakernoteDirectory.TAG_UNKNOWN_30 && i < NikonType2MakernoteDirectory.TAG_AF_TUNE) {
                    access$000 = "PORTRAIT-UPSIDEDOWN";
                } else if (i > 265 && i < 275) {
                    access$000 = "LANDSCAPE-LEFT";
                }
                if (!OrientationModule.this.lastDeviceOrientationValue.equals(access$000)) {
                    OrientationModule.this.lastDeviceOrientationValue = access$000;
                    WritableMap createMap = Arguments.createMap();
                    createMap.putString("deviceOrientation", access$000);
                    if (OrientationModule.this.ctx.hasActiveCatalystInstance()) {
                        ((RCTDeviceEventEmitter) OrientationModule.this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("deviceOrientationDidChange", createMap);
                    }
                }
            }
        };
        boolean canDetectOrientation = this.mOrientationListener.canDetectOrientation();
        str = ReactConstants.TAG;
        if (canDetectOrientation) {
            FLog.d(str, "orientation detect enabled.");
            this.mOrientationListener.enable();
        } else {
            FLog.d(str, "orientation detect disabled.");
            this.mOrientationListener.disable();
        }
        this.mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String access$100 = OrientationModule.this.getCurrentOrientation();
                OrientationModule.this.lastOrientationValue = access$100;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Orientation changed to ");
                stringBuilder.append(access$100);
                FLog.d(ReactConstants.TAG, stringBuilder.toString());
                WritableMap createMap = Arguments.createMap();
                createMap.putString(ReactVideoView.EVENT_PROP_ORIENTATION, access$100);
                if (OrientationModule.this.ctx.hasActiveCatalystInstance()) {
                    ((RCTDeviceEventEmitter) OrientationModule.this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("orientationDidChange", createMap);
                }
            }
        };
        this.ctx.addLifecycleEventListener(this);
    }

    private String getCurrentOrientation() {
        int rotation = ((WindowManager) access$700().getSystemService("window")).getDefaultDisplay().getRotation();
        if (rotation == 0) {
            return "PORTRAIT";
        }
        if (rotation == 1) {
            return "LANDSCAPE-LEFT";
        }
        if (rotation != 2) {
            return rotation != 3 ? "UNKNOWN" : "LANDSCAPE-RIGHT";
        } else {
            return "PORTRAIT-UPSIDEDOWN";
        }
    }

    @ReactMethod
    public void getOrientation(Callback callback) {
        callback.invoke(getCurrentOrientation());
    }

    @ReactMethod
    public void getDeviceOrientation(Callback callback) {
        callback.invoke(this.lastDeviceOrientationValue);
    }

    @ReactMethod
    public void lockToPortrait() {
        Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.setRequestedOrientation(1);
            this.isLocked = true;
            this.lastOrientationValue = "PORTRAIT";
            WritableMap createMap = Arguments.createMap();
            String str = this.lastOrientationValue;
            String str2 = ReactVideoView.EVENT_PROP_ORIENTATION;
            createMap.putString(str2, str);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("orientationDidChange", createMap);
            }
            createMap = Arguments.createMap();
            createMap.putString(str2, this.lastOrientationValue);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("lockDidChange", createMap);
            }
        }
    }

    @ReactMethod
    public void lockToPortraitUpsideDown() {
        Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.setRequestedOrientation(9);
            this.isLocked = true;
            this.lastOrientationValue = "PORTRAIT-UPSIDEDOWN";
            WritableMap createMap = Arguments.createMap();
            String str = this.lastOrientationValue;
            String str2 = ReactVideoView.EVENT_PROP_ORIENTATION;
            createMap.putString(str2, str);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("orientationDidChange", createMap);
            }
            createMap = Arguments.createMap();
            createMap.putString(str2, this.lastOrientationValue);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("lockDidChange", createMap);
            }
        }
    }

    @ReactMethod
    public void lockToLandscape() {
        Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.setRequestedOrientation(6);
            this.isLocked = true;
            this.lastOrientationValue = "LANDSCAPE-LEFT";
            WritableMap createMap = Arguments.createMap();
            String str = this.lastOrientationValue;
            String str2 = ReactVideoView.EVENT_PROP_ORIENTATION;
            createMap.putString(str2, str);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("orientationDidChange", createMap);
            }
            createMap = Arguments.createMap();
            createMap.putString(str2, this.lastOrientationValue);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("lockDidChange", createMap);
            }
        }
    }

    @ReactMethod
    public void lockToLandscapeLeft() {
        Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.setRequestedOrientation(0);
            this.isLocked = true;
            this.lastOrientationValue = "LANDSCAPE-LEFT";
            WritableMap createMap = Arguments.createMap();
            String str = this.lastOrientationValue;
            String str2 = ReactVideoView.EVENT_PROP_ORIENTATION;
            createMap.putString(str2, str);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("orientationDidChange", createMap);
            }
            createMap = Arguments.createMap();
            createMap.putString(str2, this.lastOrientationValue);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("lockDidChange", createMap);
            }
        }
    }

    @ReactMethod
    public void lockToLandscapeRight() {
        Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.setRequestedOrientation(8);
            this.isLocked = true;
            this.lastOrientationValue = "LANDSCAPE-RIGHT";
            WritableMap createMap = Arguments.createMap();
            String str = this.lastOrientationValue;
            String str2 = ReactVideoView.EVENT_PROP_ORIENTATION;
            createMap.putString(str2, str);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("orientationDidChange", createMap);
            }
            createMap = Arguments.createMap();
            createMap.putString(str2, this.lastOrientationValue);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("lockDidChange", createMap);
            }
        }
    }

    @ReactMethod
    public void unlockAllOrientations() {
        Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.setRequestedOrientation(-1);
            this.isLocked = false;
            this.lastOrientationValue = this.lastDeviceOrientationValue;
            WritableMap createMap = Arguments.createMap();
            String str = this.lastOrientationValue;
            String str2 = ReactVideoView.EVENT_PROP_ORIENTATION;
            createMap.putString(str2, str);
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("orientationDidChange", createMap);
            }
            createMap = Arguments.createMap();
            createMap.putString(str2, "UNKNOWN");
            if (this.ctx.hasActiveCatalystInstance()) {
                ((RCTDeviceEventEmitter) this.ctx.getJSModule(RCTDeviceEventEmitter.class)).emit("lockDidChange", createMap);
            }
        }
    }

    @ReactMethod
    public void getAutoRotateState(Callback callback) {
        boolean z = System.getInt(this.ctx.getContentResolver(), "accelerometer_rotation", 0) == 1;
        callback.invoke(Boolean.valueOf(z));
    }

    @Nullable
    public Map<String, Object> getConstants() {
        Map hashMap = new HashMap();
        hashMap.put("initialOrientation", getCurrentOrientation());
        return hashMap;
    }

    public void onHostResume() {
        FLog.i(ReactConstants.TAG, "orientation detect enabled.");
        this.mOrientationListener.enable();
        Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.registerReceiver(this.mReceiver, new IntentFilter("onConfigurationChanged"));
        }
    }

    public void onHostPause() {
        String str = ReactConstants.TAG;
        FLog.d(str, "orientation detect disabled.");
        this.mOrientationListener.disable();
        Activity currentActivity = access$700();
        if (currentActivity != null) {
            try {
                currentActivity.unregisterReceiver(this.mReceiver);
            } catch (Throwable e) {
                FLog.w(str, "Receiver already unregistered", e);
            }
        }
    }

    public void onHostDestroy() {
        String str = ReactConstants.TAG;
        FLog.d(str, "orientation detect disabled.");
        this.mOrientationListener.disable();
        Activity currentActivity = access$700();
        if (currentActivity != null) {
            try {
                currentActivity.unregisterReceiver(this.mReceiver);
            } catch (Throwable e) {
                FLog.w(str, "Receiver already unregistered", e);
            }
        }
    }
}
