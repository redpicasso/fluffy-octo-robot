package io.invertase.firebase.analytics;

import android.app.Activity;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.google.firebase.analytics.FirebaseAnalytics;
import javax.annotation.Nullable;

public class RNFirebaseAnalytics extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFirebaseAnalytics";

    public String getName() {
        return TAG;
    }

    RNFirebaseAnalytics(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        Log.d(TAG, "New instance");
    }

    @ReactMethod
    public void logEvent(String str, @Nullable ReadableMap readableMap) {
        FirebaseAnalytics.getInstance(access$700()).logEvent(str, Arguments.toBundle(readableMap));
    }

    @ReactMethod
    public void setAnalyticsCollectionEnabled(Boolean bool) {
        FirebaseAnalytics.getInstance(access$700()).setAnalyticsCollectionEnabled(bool.booleanValue());
    }

    @ReactMethod
    public void setCurrentScreen(final String str, final String str2) {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setCurrentScreen ");
            stringBuilder.append(str);
            stringBuilder.append(" - ");
            stringBuilder.append(str2);
            Log.d(TAG, stringBuilder.toString());
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    FirebaseAnalytics.getInstance(RNFirebaseAnalytics.this.access$700()).setCurrentScreen(currentActivity, str, str2);
                }
            });
        }
    }

    @ReactMethod
    public void setMinimumSessionDuration(double d) {
        FirebaseAnalytics.getInstance(access$700()).setMinimumSessionDuration((long) d);
    }

    @ReactMethod
    public void setSessionTimeoutDuration(double d) {
        FirebaseAnalytics.getInstance(access$700()).setSessionTimeoutDuration((long) d);
    }

    @ReactMethod
    public void setUserId(String str) {
        FirebaseAnalytics.getInstance(access$700()).setUserId(str);
    }

    @ReactMethod
    public void setUserProperty(String str, String str2) {
        FirebaseAnalytics.getInstance(access$700()).setUserProperty(str, str2);
    }
}
