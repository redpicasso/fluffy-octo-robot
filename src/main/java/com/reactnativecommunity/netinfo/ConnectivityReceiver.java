package com.reactnativecommunity.netinfo;

import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.reactnativecommunity.netinfo.types.CellularGeneration;
import com.reactnativecommunity.netinfo.types.ConnectionType;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

abstract class ConnectivityReceiver {
    @Nullable
    private CellularGeneration mCellularGeneration = null;
    @Nonnull
    private ConnectionType mConnectionType = ConnectionType.UNKNOWN;
    private final ConnectivityManager mConnectivityManager;
    private boolean mIsInternetReachable = false;
    private final ReactApplicationContext mReactContext;
    private final TelephonyManager mTelephonyManager;
    private final WifiManager mWifiManager;

    abstract void register();

    abstract void unregister();

    ConnectivityReceiver(ReactApplicationContext reactApplicationContext) {
        this.mReactContext = reactApplicationContext;
        this.mConnectivityManager = (ConnectivityManager) reactApplicationContext.getSystemService("connectivity");
        this.mWifiManager = (WifiManager) reactApplicationContext.getApplicationContext().getSystemService("wifi");
        this.mTelephonyManager = (TelephonyManager) reactApplicationContext.getSystemService("phone");
    }

    public void getCurrentState(Promise promise) {
        promise.resolve(createConnectivityEventMap());
    }

    ReactApplicationContext getReactContext() {
        return this.mReactContext;
    }

    ConnectivityManager getConnectivityManager() {
        return this.mConnectivityManager;
    }

    void updateConnectivity(@Nonnull ConnectionType connectionType, @Nullable CellularGeneration cellularGeneration, boolean z) {
        Object obj = 1;
        Object obj2 = connectionType != this.mConnectionType ? 1 : null;
        Object obj3 = cellularGeneration != this.mCellularGeneration ? 1 : null;
        if (z == this.mIsInternetReachable) {
            obj = null;
        }
        if (obj2 != null || obj3 != null || obj != null) {
            this.mConnectionType = connectionType;
            this.mCellularGeneration = cellularGeneration;
            this.mIsInternetReachable = z;
            sendConnectivityChangedEvent();
        }
    }

    private void sendConnectivityChangedEvent() {
        ((RCTDeviceEventEmitter) getReactContext().getJSModule(RCTDeviceEventEmitter.class)).emit("netInfo.networkStatusDidChange", createConnectivityEventMap());
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:30:0x00c2, code:
            r0.putMap("details", r2);
     */
    /* JADX WARNING: Missing block: B:31:0x00c7, code:
            return r0;
     */
    private com.facebook.react.bridge.WritableMap createConnectivityEventMap() {
        /*
        r6 = this;
        r0 = new com.facebook.react.bridge.WritableNativeMap;
        r0.<init>();
        r1 = r6.mConnectionType;
        r1 = r1.label;
        r2 = "type";
        r0.putString(r2, r1);
        r1 = r6.mConnectionType;
        r2 = com.reactnativecommunity.netinfo.types.ConnectionType.NONE;
        r1 = r1.equals(r2);
        if (r1 != 0) goto L_0x0024;
    L_0x0018:
        r1 = r6.mConnectionType;
        r2 = com.reactnativecommunity.netinfo.types.ConnectionType.UNKNOWN;
        r1 = r1.equals(r2);
        if (r1 != 0) goto L_0x0024;
    L_0x0022:
        r1 = 1;
        goto L_0x0025;
    L_0x0024:
        r1 = 0;
    L_0x0025:
        r2 = "isConnected";
        r0.putBoolean(r2, r1);
        r2 = r6.mIsInternetReachable;
        r3 = "isInternetReachable";
        r0.putBoolean(r3, r2);
        r2 = 0;
        if (r1 == 0) goto L_0x00c2;
    L_0x0034:
        r2 = new com.facebook.react.bridge.WritableNativeMap;
        r2.<init>();
        r1 = r6.getConnectivityManager();
        r1 = androidx.core.net.ConnectivityManagerCompat.isActiveNetworkMetered(r1);
        r3 = "isConnectionExpensive";
        r2.putBoolean(r3, r1);
        r1 = r6.mConnectionType;
        r3 = com.reactnativecommunity.netinfo.types.ConnectionType.CELLULAR;
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x0069;
    L_0x0050:
        r1 = r6.mCellularGeneration;
        if (r1 == 0) goto L_0x005b;
    L_0x0054:
        r1 = r1.label;
        r3 = "cellularGeneration";
        r2.putString(r3, r1);
    L_0x005b:
        r1 = r6.mTelephonyManager;
        r1 = r1.getNetworkOperatorName();
        if (r1 == 0) goto L_0x00c2;
    L_0x0063:
        r3 = "carrier";
        r2.putString(r3, r1);
        goto L_0x00c2;
    L_0x0069:
        r1 = r6.mConnectionType;
        r3 = com.reactnativecommunity.netinfo.types.ConnectionType.WIFI;
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x00c2;
    L_0x0073:
        r1 = r6.mWifiManager;
        r1 = r1.getConnectionInfo();
        if (r1 == 0) goto L_0x00c2;
    L_0x007b:
        r3 = r1.getSSID();	 Catch:{ Exception -> 0x0096 }
        if (r3 == 0) goto L_0x0096;
    L_0x0081:
        r4 = "<unknown ssid>";
        r4 = r3.contains(r4);	 Catch:{ Exception -> 0x0096 }
        if (r4 != 0) goto L_0x0096;
    L_0x0089:
        r4 = "\"";
        r5 = "";
        r3 = r3.replace(r4, r5);	 Catch:{ Exception -> 0x0096 }
        r4 = "ssid";
        r2.putString(r4, r3);	 Catch:{ Exception -> 0x0096 }
    L_0x0096:
        r3 = r1.getRssi();	 Catch:{ Exception -> 0x00a5 }
        r4 = 100;
        r3 = android.net.wifi.WifiManager.calculateSignalLevel(r3, r4);	 Catch:{ Exception -> 0x00a5 }
        r4 = "strength";
        r2.putInt(r4, r3);	 Catch:{ Exception -> 0x00a5 }
    L_0x00a5:
        r1 = r1.getIpAddress();	 Catch:{ Exception -> 0x00c2 }
        r3 = (long) r1;	 Catch:{ Exception -> 0x00c2 }
        r1 = java.math.BigInteger.valueOf(r3);	 Catch:{ Exception -> 0x00c2 }
        r1 = r1.toByteArray();	 Catch:{ Exception -> 0x00c2 }
        com.reactnativecommunity.netinfo.NetInfoUtils.reverseByteArray(r1);	 Catch:{ Exception -> 0x00c2 }
        r1 = java.net.InetAddress.getByAddress(r1);	 Catch:{ Exception -> 0x00c2 }
        r1 = r1.getHostAddress();	 Catch:{ Exception -> 0x00c2 }
        r3 = "ipAddress";
        r2.putString(r3, r1);	 Catch:{ Exception -> 0x00c2 }
    L_0x00c2:
        r1 = "details";
        r0.putMap(r1, r2);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactnativecommunity.netinfo.ConnectivityReceiver.createConnectivityEventMap():com.facebook.react.bridge.WritableMap");
    }
}
