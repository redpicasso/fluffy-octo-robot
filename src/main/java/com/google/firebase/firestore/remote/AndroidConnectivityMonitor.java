package com.google.firebase.firestore.remote;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import com.google.firebase.firestore.remote.ConnectivityMonitor.NetworkStatus;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Consumer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class AndroidConnectivityMonitor implements ConnectivityMonitor {
    private final List<Consumer<NetworkStatus>> callbacks = new ArrayList();
    @Nullable
    private final ConnectivityManager connectivityManager;
    private final Context context;
    @Nullable
    private Runnable unregisterRunnable;

    @TargetApi(24)
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private class DefaultNetworkCallback extends NetworkCallback {
        private DefaultNetworkCallback() {
        }

        /* synthetic */ DefaultNetworkCallback(AndroidConnectivityMonitor androidConnectivityMonitor, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void onAvailable(Network network) {
            synchronized (AndroidConnectivityMonitor.this.callbacks) {
                for (Consumer accept : AndroidConnectivityMonitor.this.callbacks) {
                    accept.accept(NetworkStatus.REACHABLE);
                }
            }
        }

        public void onLost(Network network) {
            synchronized (AndroidConnectivityMonitor.this.callbacks) {
                for (Consumer accept : AndroidConnectivityMonitor.this.callbacks) {
                    accept.accept(NetworkStatus.UNREACHABLE);
                }
            }
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private class NetworkReceiver extends BroadcastReceiver {
        private boolean isConnected;

        private NetworkReceiver() {
            this.isConnected = false;
        }

        /* synthetic */ NetworkReceiver(AndroidConnectivityMonitor androidConnectivityMonitor, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void onReceive(Context context, Intent intent) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            boolean z = this.isConnected;
            boolean z2 = activeNetworkInfo != null && activeNetworkInfo.isConnected();
            this.isConnected = z2;
            if (this.isConnected && !z) {
                synchronized (AndroidConnectivityMonitor.this.callbacks) {
                    for (Consumer accept : AndroidConnectivityMonitor.this.callbacks) {
                        accept.accept(NetworkStatus.REACHABLE);
                    }
                }
            } else if (!this.isConnected && z) {
                synchronized (AndroidConnectivityMonitor.this.callbacks) {
                    for (Consumer accept2 : AndroidConnectivityMonitor.this.callbacks) {
                        accept2.accept(NetworkStatus.UNREACHABLE);
                    }
                }
            }
        }
    }

    public AndroidConnectivityMonitor(Context context) {
        Assert.hardAssert(context != null, "Context must be non-null", new Object[0]);
        this.context = context;
        this.connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        configureNetworkMonitoring();
    }

    public void addCallback(Consumer<NetworkStatus> consumer) {
        synchronized (this.callbacks) {
            this.callbacks.add(consumer);
        }
    }

    public void shutdown() {
        Runnable runnable = this.unregisterRunnable;
        if (runnable != null) {
            runnable.run();
            this.unregisterRunnable = null;
        }
    }

    private void configureNetworkMonitoring() {
        if (VERSION.SDK_INT < 24 || this.connectivityManager == null) {
            final BroadcastReceiver networkReceiver = new NetworkReceiver(this, null);
            this.context.registerReceiver(networkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.unregisterRunnable = new Runnable() {
                public void run() {
                    AndroidConnectivityMonitor.this.context.unregisterReceiver(networkReceiver);
                }
            };
            return;
        }
        final NetworkCallback defaultNetworkCallback = new DefaultNetworkCallback(this, null);
        this.connectivityManager.registerDefaultNetworkCallback(defaultNetworkCallback);
        this.unregisterRunnable = new Runnable() {
            public void run() {
                AndroidConnectivityMonitor.this.connectivityManager.unregisterNetworkCallback(defaultNetworkCallback);
            }
        };
    }
}
