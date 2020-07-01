package io.invertase.firebase;

import android.app.Activity;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseOptions.Builder;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RNFirebaseModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFirebase";

    public String getName() {
        return TAG;
    }

    public RNFirebaseModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void initializeApp(String str, ReadableMap readableMap, Callback callback) {
        Builder builder = new Builder();
        builder.setApiKey(readableMap.getString("apiKey"));
        builder.setApplicationId(readableMap.getString("appId"));
        builder.setProjectId(readableMap.getString("projectId"));
        builder.setDatabaseUrl(readableMap.getString("databaseURL"));
        builder.setStorageBucket(readableMap.getString("storageBucket"));
        builder.setGcmSenderId(readableMap.getString("messagingSenderId"));
        FirebaseApp.initializeApp(access$700(), builder.build(), str);
        Arguments.createMap().putString("result", Param.SUCCESS);
        callback.invoke(null, r3);
    }

    @ReactMethod
    public void deleteApp(String str, Promise promise) {
        FirebaseApp instance = FirebaseApp.getInstance(str);
        if (instance != null) {
            instance.delete();
        }
        promise.resolve(null);
    }

    @ReactMethod
    public void getPlayServicesStatus(Promise promise) {
        promise.resolve(getPlayServicesStatusMap());
    }

    private WritableMap getPlayServicesStatusMap() {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(access$700());
        WritableMap createMap = Arguments.createMap();
        createMap.putInt(NotificationCompat.CATEGORY_STATUS, isGooglePlayServicesAvailable);
        String str = "isAvailable";
        if (isGooglePlayServicesAvailable == 0) {
            createMap.putBoolean(str, true);
        } else {
            createMap.putBoolean(str, false);
            createMap.putString(ReactVideoView.EVENT_PROP_ERROR, instance.getErrorString(isGooglePlayServicesAvailable));
            createMap.putBoolean("isUserResolvableError", instance.isUserResolvableError(isGooglePlayServicesAvailable));
            createMap.putBoolean("hasResolution", new ConnectionResult(isGooglePlayServicesAvailable).hasResolution());
        }
        return createMap;
    }

    @ReactMethod
    public void promptForPlayServices() {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(access$700());
        if (isGooglePlayServicesAvailable != 0 && instance.isUserResolvableError(isGooglePlayServicesAvailable)) {
            Activity currentActivity = access$700();
            if (currentActivity != null) {
                instance.getErrorDialog(currentActivity, isGooglePlayServicesAvailable, isGooglePlayServicesAvailable).show();
            }
        }
    }

    @ReactMethod
    public void resolutionForPlayServices() {
        int isGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(access$700());
        ConnectionResult connectionResult = new ConnectionResult(isGooglePlayServicesAvailable);
        if (!connectionResult.isSuccess() && connectionResult.hasResolution()) {
            Activity currentActivity = access$700();
            if (currentActivity != null) {
                try {
                    connectionResult.startResolutionForResult(currentActivity, isGooglePlayServicesAvailable);
                } catch (Throwable e) {
                    Log.d(TAG, "resolutionForPlayServices", e);
                }
            }
        }
    }

    @ReactMethod
    public void makePlayServicesAvailable() {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        if (instance.isGooglePlayServicesAvailable(access$700()) != 0) {
            Activity currentActivity = access$700();
            if (currentActivity != null) {
                instance.makeGooglePlayServicesAvailable(currentActivity);
            }
        }
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> hashMap = new HashMap();
        List arrayList = new ArrayList();
        for (FirebaseApp firebaseApp : FirebaseApp.getApps(access$700())) {
            String name = firebaseApp.getName();
            FirebaseOptions options = firebaseApp.getOptions();
            Map hashMap2 = new HashMap();
            hashMap2.put(ConditionalUserProperty.NAME, name);
            hashMap2.put("apiKey", options.getApiKey());
            hashMap2.put("appId", options.getApplicationId());
            String str = "projectId";
            hashMap2.put(str, options.getProjectId());
            hashMap2.put(str, options.getProjectId());
            hashMap2.put("databaseURL", options.getDatabaseUrl());
            hashMap2.put("messagingSenderId", options.getGcmSenderId());
            hashMap2.put("storageBucket", options.getStorageBucket());
            arrayList.add(hashMap2);
        }
        hashMap.put("apps", arrayList);
        hashMap.put("playServicesAvailability", getPlayServicesStatusMap());
        return hashMap;
    }
}
