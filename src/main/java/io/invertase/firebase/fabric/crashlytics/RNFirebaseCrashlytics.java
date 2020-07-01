package io.invertase.firebase.fabric.crashlytics;

import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.devsupport.StackTraceHelper;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import java.util.ArrayList;

public class RNFirebaseCrashlytics extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFirebaseCrashlytics";

    public String getName() {
        return TAG;
    }

    public RNFirebaseCrashlytics(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        Log.d(TAG, "New instance");
    }

    @ReactMethod
    public void crash() {
        Crashlytics.getInstance().crash();
    }

    @ReactMethod
    public void log(String str) {
        Crashlytics.log(str);
    }

    @ReactMethod
    public void recordError(int i, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append(": ");
        stringBuilder.append(str);
        Crashlytics.logException(new Exception(stringBuilder.toString()));
    }

    @ReactMethod
    public void recordCustomError(String str, String str2, ReadableArray readableArray) {
        int i = 0;
        ArrayList arrayList = new ArrayList(0);
        while (i < readableArray.size()) {
            ReadableMap map = readableArray.getMap(i);
            String str3 = "additional";
            Object map2 = map.hasKey(str3) ? map.getMap(str3) : null;
            String str4 = "functionName";
            str4 = map.hasKey(str4) ? map.getString(str4) : "Unknown Function";
            String str5 = "className";
            str5 = map.hasKey(str5) ? map.getString(str5) : "Unknown Class";
            String str6 = "fileName";
            String string = map.getString(str6);
            String str7 = StackTraceHelper.LINE_NUMBER_KEY;
            int i2 = -1;
            arrayList.add(new StackTraceElement(str5, str4, string, map.hasKey(str7) ? map.getInt(str7) : -1));
            if (map2 != null) {
                str3 = map2.toString();
                str5 = map.getString(str6);
                if (map.hasKey(str7)) {
                    i2 = map.getInt(str7);
                }
                arrayList.add(new StackTraceElement("Additional Parameters", str3, str5, i2));
            }
            i++;
        }
        StackTraceElement[] stackTraceElementArr = new StackTraceElement[arrayList.size()];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
        stringBuilder.append(str2);
        Throwable exception = new Exception(stringBuilder.toString());
        exception.setStackTrace((StackTraceElement[]) arrayList.toArray(stackTraceElementArr));
        Crashlytics.logException(exception);
    }

    @ReactMethod
    public void setBoolValue(String str, boolean z) {
        Crashlytics.setBool(str, z);
    }

    @ReactMethod
    public void setFloatValue(String str, float f) {
        Crashlytics.setFloat(str, f);
    }

    @ReactMethod
    public void setIntValue(String str, int i) {
        Crashlytics.setInt(str, i);
    }

    @ReactMethod
    public void setStringValue(String str, String str2) {
        Crashlytics.setString(str, str2);
    }

    @ReactMethod
    public void setUserIdentifier(String str) {
        Crashlytics.setUserIdentifier(str);
    }

    @ReactMethod
    public void setUserName(String str) {
        Crashlytics.setUserName(str);
    }

    @ReactMethod
    public void setUserEmail(String str) {
        Crashlytics.setUserEmail(str);
    }

    @ReactMethod
    public void enableCrashlyticsCollection() {
        Fabric.with(access$700(), new Kit[]{new Crashlytics()});
    }
}
