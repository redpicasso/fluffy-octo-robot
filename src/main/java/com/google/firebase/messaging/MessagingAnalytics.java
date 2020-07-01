package com.google.firebase.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.measurement.AppMeasurement.UserProperty;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.analytics.connector.AnalyticsConnector;

@KeepForSdk
/* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
public class MessagingAnalytics {
    @KeepForSdk
    public static void logNotificationReceived(Intent intent) {
        zza("_nr", intent);
    }

    @KeepForSdk
    public static void logNotificationOpen(Intent intent) {
        if (intent != null) {
            String str = "FirebaseMessaging";
            if ("1".equals(intent.getStringExtra("google.c.a.tc"))) {
                AnalyticsConnector analyticsConnector = (AnalyticsConnector) FirebaseApp.getInstance().get(AnalyticsConnector.class);
                if (Log.isLoggable(str, 3)) {
                    Log.d(str, "Received event with track-conversion=true. Setting user property and reengagement event");
                }
                if (analyticsConnector != null) {
                    String stringExtra = intent.getStringExtra("google.c.a.c_id");
                    str = "fcm";
                    analyticsConnector.setUserProperty(str, UserProperty.FIREBASE_LAST_NOTIFICATION, stringExtra);
                    Bundle bundle = new Bundle();
                    bundle.putString(Param.SOURCE, "Firebase");
                    bundle.putString("medium", "notification");
                    bundle.putString(Param.CAMPAIGN, stringExtra);
                    analyticsConnector.logEvent(str, "_cmp", bundle);
                } else {
                    Log.w(str, "Unable to set user property for conversion tracking:  analytics library is missing");
                }
            } else if (Log.isLoggable(str, 3)) {
                Log.d(str, "Received event with track-conversion=false. Do not set user property");
            }
        }
        zza("_no", intent);
    }

    @KeepForSdk
    public static void logNotificationDismiss(Intent intent) {
        zza("_nd", intent);
    }

    @KeepForSdk
    public static void logNotificationForeground(Intent intent) {
        zza("_nf", intent);
    }

    @KeepForSdk
    public static boolean shouldUploadMetrics(Intent intent) {
        if (intent == null) {
            return false;
        }
        if ("com.google.firebase.messaging.RECEIVE_DIRECT_BOOT".equals(intent.getAction())) {
            return false;
        }
        return "1".equals(intent.getStringExtra("google.c.a.e"));
    }

    private static void zza(String str, Intent intent) {
        String str2;
        Bundle bundle = new Bundle();
        String stringExtra = intent.getStringExtra("google.c.a.c_id");
        if (stringExtra != null) {
            bundle.putString("_nmid", stringExtra);
        }
        stringExtra = intent.getStringExtra("google.c.a.c_l");
        if (stringExtra != null) {
            bundle.putString("_nmn", stringExtra);
        }
        Object stringExtra2 = intent.getStringExtra("google.c.a.m_l");
        if (!TextUtils.isEmpty(stringExtra2)) {
            bundle.putString("label", stringExtra2);
        }
        stringExtra2 = intent.getStringExtra("google.c.a.m_c");
        if (!TextUtils.isEmpty(stringExtra2)) {
            bundle.putString("message_channel", stringExtra2);
        }
        stringExtra = intent.getStringExtra("from");
        if (stringExtra == null || !stringExtra.startsWith("/topics/")) {
            stringExtra = null;
        }
        if (stringExtra != null) {
            bundle.putString("_nt", stringExtra);
        }
        stringExtra = "google.c.a.ts";
        String str3 = "FirebaseMessaging";
        if (intent.hasExtra(stringExtra)) {
            try {
                bundle.putInt("_nmt", Integer.parseInt(intent.getStringExtra(stringExtra)));
            } catch (Throwable e) {
                Log.w(str3, "Error while parsing timestamp in GCM event", e);
            }
        }
        stringExtra = "google.c.a.udt";
        if (intent.hasExtra(stringExtra)) {
            try {
                bundle.putInt("_ndt", Integer.parseInt(intent.getStringExtra(stringExtra)));
            } catch (Throwable e2) {
                Log.w(str3, "Error while parsing use_device_time in GCM event", e2);
            }
        }
        if ("_nr".equals(str) || "_nf".equals(str)) {
            str2 = (intent.getExtras() == null || !zzk.zza(intent.getExtras())) ? "data" : "display";
            bundle.putString("_nmc", str2);
        }
        if (Log.isLoggable(str3, 3)) {
            str2 = String.valueOf(bundle);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 22) + String.valueOf(str2).length());
            stringBuilder.append("Sending event=");
            stringBuilder.append(str);
            stringBuilder.append(" params=");
            stringBuilder.append(str2);
            Log.d(str3, stringBuilder.toString());
        }
        AnalyticsConnector analyticsConnector = (AnalyticsConnector) FirebaseApp.getInstance().get(AnalyticsConnector.class);
        if (analyticsConnector != null) {
            analyticsConnector.logEvent("fcm", str, bundle);
        } else {
            Log.w(str3, "Unable to log event: analytics library is missing");
        }
    }
}
