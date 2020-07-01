package io.invertase.firebase.functions;

import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import io.invertase.firebase.Utils;
import javax.annotation.Nonnull;

public class RNFirebaseFunctions extends ReactContextBaseJavaModule {
    private static final String CODE_KEY = "code";
    private static final String DATA_KEY = "data";
    private static final String DETAILS_KEY = "details";
    private static final String ERROR_KEY = "__error";
    private static final String MSG_KEY = "message";
    private static final String TAG = "RNFirebaseFunctions";

    public String getName() {
        return TAG;
    }

    RNFirebaseFunctions(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        Log.d(TAG, "New instance");
    }

    @ReactMethod
    public void useFunctionsEmulator(String str, String str2, String str3, Promise promise) {
        FirebaseFunctions.getInstance(FirebaseApp.getInstance(str), str2).useFunctionsEmulator(str3);
        promise.resolve(null);
    }

    @ReactMethod
    public void httpsCallable(String str, String str2, final String str3, ReadableMap readableMap, final Promise promise) {
        Object obj = readableMap.toHashMap().get("data");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("function:call:input:");
        stringBuilder.append(str3);
        stringBuilder.append(":");
        stringBuilder.append(obj != null ? obj.toString() : "null");
        Log.d(TAG, stringBuilder.toString());
        FirebaseFunctions.getInstance(FirebaseApp.getInstance(str), str2).getHttpsCallable(str3).call(obj).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                WritableMap createMap = Arguments.createMap();
                Object data = httpsCallableResult.getData();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("function:call:onSuccess:");
                stringBuilder.append(str3);
                String stringBuilder2 = stringBuilder.toString();
                String str = RNFirebaseFunctions.TAG;
                Log.d(str, stringBuilder2);
                stringBuilder = new StringBuilder();
                stringBuilder.append("function:call:onSuccess:result:type:");
                stringBuilder.append(str3);
                String str2 = ":";
                stringBuilder.append(str2);
                String str3 = "null";
                stringBuilder.append(data != null ? data.getClass().getName() : str3);
                Log.d(str, stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("function:call:onSuccess:result:data:");
                stringBuilder.append(str3);
                stringBuilder.append(str2);
                if (data != null) {
                    str3 = data.toString();
                }
                stringBuilder.append(str3);
                Log.d(str, stringBuilder.toString());
                Utils.mapPutValue("data", data, createMap);
                promise.resolve(createMap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@Nonnull Exception exception) {
                Object details;
                Object name;
                Object message;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("function:call:onFailure:");
                stringBuilder.append(str3);
                Log.d(RNFirebaseFunctions.TAG, stringBuilder.toString(), exception);
                WritableMap createMap = Arguments.createMap();
                if (exception instanceof FirebaseFunctionsException) {
                    FirebaseFunctionsException firebaseFunctionsException = (FirebaseFunctionsException) exception;
                    details = firebaseFunctionsException.getDetails();
                    name = firebaseFunctionsException.getCode().name();
                    message = firebaseFunctionsException.getMessage();
                } else {
                    message = exception.getMessage();
                    details = null;
                    name = "UNKNOWN";
                }
                Utils.mapPutValue(RNFirebaseFunctions.CODE_KEY, name, createMap);
                Utils.mapPutValue(RNFirebaseFunctions.MSG_KEY, message, createMap);
                Utils.mapPutValue(RNFirebaseFunctions.ERROR_KEY, Boolean.valueOf(true), createMap);
                Utils.mapPutValue(RNFirebaseFunctions.DETAILS_KEY, details, createMap);
                promise.resolve(createMap);
            }
        });
    }
}
