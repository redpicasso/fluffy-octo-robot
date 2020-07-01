package io.invertase.firebase.instanceid;

import android.util.Log;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.firebase.iid.FirebaseInstanceId;
import java.io.IOException;

public class RNFirebaseInstanceId extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFirebaseInstanceId";

    public String getName() {
        return TAG;
    }

    public RNFirebaseInstanceId(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        Log.d(TAG, "New instance");
    }

    @ReactMethod
    public void delete(Promise promise) {
        String str = TAG;
        try {
            Log.d(str, "Deleting instance id");
            FirebaseInstanceId.getInstance().deleteInstanceId();
            promise.resolve(null);
        } catch (IOException e) {
            Log.e(str, e.getMessage());
            promise.reject("instance_id_error", e.getMessage());
        }
    }

    @ReactMethod
    public void get(Promise promise) {
        promise.resolve(FirebaseInstanceId.getInstance().getId());
    }

    @ReactMethod
    public void getToken(String str, String str2, Promise promise) {
        try {
            str2 = FirebaseInstanceId.getInstance().getToken(str, str2);
            String str3 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Firebase token for ");
            stringBuilder.append(str);
            stringBuilder.append(": ");
            stringBuilder.append(str2);
            Log.d(str3, stringBuilder.toString());
            promise.resolve(str2);
        } catch (Throwable e) {
            promise.reject("iid/request-failed", "getToken request failed", e);
        }
    }

    @ReactMethod
    public void deleteToken(String str, String str2, Promise promise) {
        try {
            FirebaseInstanceId.getInstance().deleteToken(str, str2);
            str2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Firebase token deleted for ");
            stringBuilder.append(str);
            Log.d(str2, stringBuilder.toString());
            promise.resolve(null);
        } catch (Throwable e) {
            promise.reject("iid/request-failed", "deleteToken request failed", e);
        }
    }
}
