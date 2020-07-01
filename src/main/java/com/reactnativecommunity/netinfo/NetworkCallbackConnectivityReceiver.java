package com.reactnativecommunity.netinfo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import com.facebook.react.bridge.ReactApplicationContext;
import com.reactnativecommunity.netinfo.types.CellularGeneration;
import com.reactnativecommunity.netinfo.types.ConnectionType;

@TargetApi(24)
class NetworkCallbackConnectivityReceiver extends ConnectivityReceiver {
    private Network mNetwork = null;
    private final ConnectivityNetworkCallback mNetworkCallback = new ConnectivityNetworkCallback();
    private NetworkCapabilities mNetworkCapabilities = null;

    private class ConnectivityNetworkCallback extends NetworkCallback {
        private ConnectivityNetworkCallback() {
        }

        public void onAvailable(Network network) {
            NetworkCallbackConnectivityReceiver.this.mNetwork = network;
            NetworkCallbackConnectivityReceiver networkCallbackConnectivityReceiver = NetworkCallbackConnectivityReceiver.this;
            networkCallbackConnectivityReceiver.mNetworkCapabilities = networkCallbackConnectivityReceiver.getConnectivityManager().getNetworkCapabilities(network);
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }

        public void onLosing(Network network, int i) {
            NetworkCallbackConnectivityReceiver.this.mNetwork = network;
            NetworkCallbackConnectivityReceiver networkCallbackConnectivityReceiver = NetworkCallbackConnectivityReceiver.this;
            networkCallbackConnectivityReceiver.mNetworkCapabilities = networkCallbackConnectivityReceiver.getConnectivityManager().getNetworkCapabilities(network);
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }

        public void onLost(Network network) {
            NetworkCallbackConnectivityReceiver.this.mNetwork = null;
            NetworkCallbackConnectivityReceiver.this.mNetworkCapabilities = null;
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }

        public void onUnavailable() {
            NetworkCallbackConnectivityReceiver.this.mNetwork = null;
            NetworkCallbackConnectivityReceiver.this.mNetworkCapabilities = null;
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            NetworkCallbackConnectivityReceiver.this.mNetwork = network;
            NetworkCallbackConnectivityReceiver.this.mNetworkCapabilities = networkCapabilities;
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }

        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            NetworkCallbackConnectivityReceiver.this.mNetwork = network;
            NetworkCallbackConnectivityReceiver networkCallbackConnectivityReceiver = NetworkCallbackConnectivityReceiver.this;
            networkCallbackConnectivityReceiver.mNetworkCapabilities = networkCallbackConnectivityReceiver.getConnectivityManager().getNetworkCapabilities(network);
            NetworkCallbackConnectivityReceiver.this.updateAndSend();
        }
    }

    public NetworkCallbackConnectivityReceiver(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @SuppressLint({"MissingPermission"})
    void register() {
        try {
            getConnectivityManager().registerDefaultNetworkCallback(this.mNetworkCallback);
        } catch (SecurityException unused) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:2:0x0009 A:{RETURN, ExcHandler: java.lang.SecurityException (unused java.lang.SecurityException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Missing block: B:2:0x0009, code:
            return;
     */
    void unregister() {
        /*
        r2 = this;
        r0 = r2.getConnectivityManager();	 Catch:{ SecurityException -> 0x0009, SecurityException -> 0x0009 }
        r1 = r2.mNetworkCallback;	 Catch:{ SecurityException -> 0x0009, SecurityException -> 0x0009 }
        r0.unregisterNetworkCallback(r1);	 Catch:{ SecurityException -> 0x0009, SecurityException -> 0x0009 }
    L_0x0009:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactnativecommunity.netinfo.NetworkCallbackConnectivityReceiver.unregister():void");
    }

    @SuppressLint({"MissingPermission"})
    private void updateAndSend() {
        ConnectionType connectionType = ConnectionType.UNKNOWN;
        NetworkCapabilities networkCapabilities = this.mNetworkCapabilities;
        boolean z = false;
        CellularGeneration cellularGeneration = null;
        if (networkCapabilities != null) {
            if (networkCapabilities.hasTransport(2)) {
                connectionType = ConnectionType.BLUETOOTH;
            } else if (this.mNetworkCapabilities.hasTransport(0)) {
                connectionType = ConnectionType.CELLULAR;
            } else if (this.mNetworkCapabilities.hasTransport(3)) {
                connectionType = ConnectionType.ETHERNET;
            } else if (this.mNetworkCapabilities.hasTransport(1)) {
                connectionType = ConnectionType.WIFI;
            } else if (this.mNetworkCapabilities.hasTransport(4)) {
                connectionType = ConnectionType.VPN;
            }
            if (this.mNetworkCapabilities.hasCapability(12) && this.mNetworkCapabilities.hasCapability(16)) {
                z = true;
            }
            if (this.mNetwork != null && r0 == ConnectionType.CELLULAR) {
                cellularGeneration = CellularGeneration.fromNetworkInfo(getConnectivityManager().getNetworkInfo(this.mNetwork));
            }
        } else {
            connectionType = ConnectionType.NONE;
        }
        updateConnectivity(connectionType, cellularGeneration, z);
    }
}
