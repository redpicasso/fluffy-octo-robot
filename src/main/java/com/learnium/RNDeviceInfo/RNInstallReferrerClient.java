package com.learnium.RNDeviceInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import java.io.PrintStream;

public class RNInstallReferrerClient {
    private InstallReferrerStateListener installReferrerStateListener = new InstallReferrerStateListener() {
        public void onInstallReferrerSetupFinished(int i) {
            String str = "InstallReferrerState";
            if (i == 0) {
                try {
                    Log.d(str, "OK");
                    ReferrerDetails installReferrer = RNInstallReferrerClient.this.mReferrerClient.getInstallReferrer();
                    installReferrer.getInstallReferrer();
                    installReferrer.getReferrerClickTimestampSeconds();
                    installReferrer.getInstallBeginTimestampSeconds();
                    Editor edit = RNInstallReferrerClient.this.sharedPreferences.edit();
                    edit.putString("installReferrer", RNInstallReferrerClient.this.getInstallReferrer());
                    edit.apply();
                    RNInstallReferrerClient.this.mReferrerClient.endConnection();
                } catch (Exception e) {
                    PrintStream printStream = System.err;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("RNInstallReferrerClient exception. getInstallReferrer will be unavailable: ");
                    stringBuilder.append(e.getMessage());
                    printStream.println(stringBuilder.toString());
                    e.printStackTrace(System.err);
                }
            } else if (i == 1) {
                Log.d(str, "SERVICE_UNAVAILABLE");
            } else if (i == 2) {
                Log.d(str, "FEATURE_NOT_SUPPORTED");
            }
        }

        public void onInstallReferrerServiceDisconnected() {
            Log.d("RNInstallReferrerClient", "InstallReferrerService disconnected");
        }
    };
    private InstallReferrerClient mReferrerClient;
    private SharedPreferences sharedPreferences;

    RNInstallReferrerClient(Context context) {
        this.sharedPreferences = context.getSharedPreferences("react-native-device-info", 0);
        this.mReferrerClient = InstallReferrerClient.newBuilder(context).build();
        try {
            this.mReferrerClient.startConnection(this.installReferrerStateListener);
        } catch (Exception e) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RNInstallReferrerClient exception. getInstallReferrer will be unavailable: ");
            stringBuilder.append(e.getMessage());
            printStream.println(stringBuilder.toString());
            e.printStackTrace(System.err);
        }
    }

    private String getInstallReferrer() {
        try {
            return this.mReferrerClient.getInstallReferrer().getInstallReferrer();
        } catch (Exception e) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RNInstallReferrerClient exception. getInstallReferrer will be unavailable: ");
            stringBuilder.append(e.getMessage());
            printStream.println(stringBuilder.toString());
            e.printStackTrace(System.err);
            return null;
        }
    }
}
