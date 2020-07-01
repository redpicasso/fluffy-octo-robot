package com.google.firebase.functions;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
final /* synthetic */ class FirebaseFunctions$$Lambda$1 implements Runnable {
    private final Context arg$1;

    private FirebaseFunctions$$Lambda$1(Context context) {
        this.arg$1 = context;
    }

    public static Runnable lambdaFactory$(Context context) {
        return new FirebaseFunctions$$Lambda$1(context);
    }

    public void run() {
        ProviderInstaller.installIfNeededAsync(this.arg$1, new ProviderInstallListener() {
            public void onProviderInstalled() {
                FirebaseFunctions.providerInstalled.setResult(null);
            }

            public void onProviderInstallFailed(int i, Intent intent) {
                Log.d("FirebaseFunctions", "Failed to update ssl context");
                FirebaseFunctions.providerInstalled.setResult(null);
            }
        });
    }
}
