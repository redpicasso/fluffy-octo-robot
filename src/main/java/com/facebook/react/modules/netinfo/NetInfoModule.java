package com.facebook.react.modules.netinfo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.core.net.ConnectivityManagerCompat;
import androidx.core.os.EnvironmentCompat;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;

@SuppressLint({"MissingPermission"})
@ReactModule(name = "NetInfo")
public class NetInfoModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private static final String CONNECTION_TYPE_BLUETOOTH = "bluetooth";
    private static final String CONNECTION_TYPE_CELLULAR = "cellular";
    private static final String CONNECTION_TYPE_ETHERNET = "ethernet";
    private static final String CONNECTION_TYPE_NONE = "none";
    private static final String CONNECTION_TYPE_NONE_DEPRECATED = "NONE";
    private static final String CONNECTION_TYPE_UNKNOWN = "unknown";
    private static final String CONNECTION_TYPE_UNKNOWN_DEPRECATED = "UNKNOWN";
    private static final String CONNECTION_TYPE_WIFI = "wifi";
    private static final String CONNECTION_TYPE_WIMAX = "wimax";
    private static final String EFFECTIVE_CONNECTION_TYPE_2G = "2g";
    private static final String EFFECTIVE_CONNECTION_TYPE_3G = "3g";
    private static final String EFFECTIVE_CONNECTION_TYPE_4G = "4g";
    private static final String EFFECTIVE_CONNECTION_TYPE_UNKNOWN = "unknown";
    private static final String ERROR_MISSING_PERMISSION = "E_MISSING_PERMISSION";
    private static final String MISSING_PERMISSION_MESSAGE = "To use NetInfo on Android, add the following to your AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />";
    public static final String NAME = "NetInfo";
    private String mConnectionType;
    private final ConnectivityBroadcastReceiver mConnectivityBroadcastReceiver;
    private String mConnectivityDeprecated = CONNECTION_TYPE_UNKNOWN_DEPRECATED;
    private final ConnectivityManager mConnectivityManager;
    private String mEffectiveConnectionType;
    private boolean mNoNetworkPermission = false;

    private class ConnectivityBroadcastReceiver extends BroadcastReceiver {
        private boolean isRegistered;

        private ConnectivityBroadcastReceiver() {
            this.isRegistered = false;
        }

        public void setRegistered(boolean z) {
            this.isRegistered = z;
        }

        public boolean isRegistered() {
            return this.isRegistered;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                NetInfoModule.this.updateAndSendConnectionType();
            }
        }
    }

    public String getName() {
        return NAME;
    }

    public void onHostDestroy() {
    }

    public NetInfoModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        String str = EnvironmentCompat.MEDIA_UNKNOWN;
        this.mConnectionType = str;
        this.mEffectiveConnectionType = str;
        this.mConnectivityManager = (ConnectivityManager) reactApplicationContext.getSystemService("connectivity");
        this.mConnectivityBroadcastReceiver = new ConnectivityBroadcastReceiver();
    }

    public void onHostResume() {
        registerReceiver();
    }

    public void onHostPause() {
        unregisterReceiver();
    }

    public void initialize() {
        access$200().addLifecycleEventListener(this);
    }

    @ReactMethod
    public void getCurrentConnectivity(Promise promise) {
        if (this.mNoNetworkPermission) {
            promise.reject(ERROR_MISSING_PERMISSION, MISSING_PERMISSION_MESSAGE);
        } else {
            promise.resolve(createConnectivityEventMap());
        }
    }

    @ReactMethod
    public void isConnectionMetered(Promise promise) {
        if (this.mNoNetworkPermission) {
            promise.reject(ERROR_MISSING_PERMISSION, MISSING_PERMISSION_MESSAGE);
        } else {
            promise.resolve(Boolean.valueOf(ConnectivityManagerCompat.isActiveNetworkMetered(this.mConnectivityManager)));
        }
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        access$200().registerReceiver(this.mConnectivityBroadcastReceiver, intentFilter);
        this.mConnectivityBroadcastReceiver.setRegistered(true);
        updateAndSendConnectionType();
    }

    private void unregisterReceiver() {
        if (this.mConnectivityBroadcastReceiver.isRegistered()) {
            access$200().unregisterReceiver(this.mConnectivityBroadcastReceiver);
            this.mConnectivityBroadcastReceiver.setRegistered(false);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0052  */
    private void updateAndSendConnectionType() {
        /*
        r6 = this;
        r0 = "unknown";
        r1 = 1;
        r2 = r6.mConnectivityManager;	 Catch:{ SecurityException -> 0x0043 }
        r2 = r2.getActiveNetworkInfo();	 Catch:{ SecurityException -> 0x0043 }
        if (r2 == 0) goto L_0x003d;
    L_0x000b:
        r3 = r2.isConnected();	 Catch:{ SecurityException -> 0x0043 }
        if (r3 != 0) goto L_0x0012;
    L_0x0011:
        goto L_0x003d;
    L_0x0012:
        r3 = r2.getType();	 Catch:{ SecurityException -> 0x0043 }
        if (r3 == 0) goto L_0x0034;
    L_0x0018:
        if (r3 == r1) goto L_0x0031;
    L_0x001a:
        r4 = 4;
        if (r3 == r4) goto L_0x0034;
    L_0x001d:
        r2 = 9;
        if (r3 == r2) goto L_0x002e;
    L_0x0021:
        r2 = 6;
        if (r3 == r2) goto L_0x002b;
    L_0x0024:
        r2 = 7;
        if (r3 == r2) goto L_0x0028;
    L_0x0027:
        goto L_0x0045;
    L_0x0028:
        r1 = "bluetooth";
        goto L_0x003f;
    L_0x002b:
        r1 = "wimax";
        goto L_0x003f;
    L_0x002e:
        r1 = "ethernet";
        goto L_0x003f;
    L_0x0031:
        r1 = "wifi";
        goto L_0x003f;
    L_0x0034:
        r3 = "cellular";
        r0 = r6.getEffectiveConnectionType(r2);	 Catch:{ SecurityException -> 0x0043 }
        r1 = r0;
        r0 = r3;
        goto L_0x0046;
    L_0x003d:
        r1 = "none";
    L_0x003f:
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0046;
    L_0x0043:
        r6.mNoNetworkPermission = r1;
    L_0x0045:
        r1 = r0;
    L_0x0046:
        r2 = r6.getCurrentConnectionType();
        r3 = r6.mConnectionType;
        r3 = r0.equalsIgnoreCase(r3);
        if (r3 == 0) goto L_0x0062;
    L_0x0052:
        r3 = r6.mEffectiveConnectionType;
        r3 = r1.equalsIgnoreCase(r3);
        if (r3 == 0) goto L_0x0062;
    L_0x005a:
        r3 = r6.mConnectivityDeprecated;
        r3 = r2.equalsIgnoreCase(r3);
        if (r3 != 0) goto L_0x006b;
    L_0x0062:
        r6.mConnectionType = r0;
        r6.mEffectiveConnectionType = r1;
        r6.mConnectivityDeprecated = r2;
        r6.sendConnectivityChangedEvent();
    L_0x006b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.netinfo.NetInfoModule.updateAndSendConnectionType():void");
    }

    private String getCurrentConnectionType() {
        String str = CONNECTION_TYPE_UNKNOWN_DEPRECATED;
        try {
            NetworkInfo activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return CONNECTION_TYPE_NONE_DEPRECATED;
            }
            if (ConnectivityManager.isNetworkTypeValid(activeNetworkInfo.getType())) {
                str = activeNetworkInfo.getTypeName().toUpperCase();
            }
            return str;
        } catch (SecurityException unused) {
            this.mNoNetworkPermission = true;
            return str;
        }
    }

    private String getEffectiveConnectionType(NetworkInfo networkInfo) {
        switch (networkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return EFFECTIVE_CONNECTION_TYPE_2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
                return EFFECTIVE_CONNECTION_TYPE_3G;
            case 13:
            case 15:
                return EFFECTIVE_CONNECTION_TYPE_4G;
            default:
                return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    private void sendConnectivityChangedEvent() {
        ((RCTDeviceEventEmitter) access$200().getJSModule(RCTDeviceEventEmitter.class)).emit("networkStatusDidChange", createConnectivityEventMap());
    }

    private WritableMap createConnectivityEventMap() {
        WritableMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putString("network_info", this.mConnectivityDeprecated);
        writableNativeMap.putString("connectionType", this.mConnectionType);
        writableNativeMap.putString("effectiveConnectionType", this.mEffectiveConnectionType);
        return writableNativeMap;
    }
}
