package com.github.wumke.RNExitApp;

import android.app.AlarmManager;
import android.os.Process;
import androidx.core.app.NotificationCompat;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNExitAppModule extends ReactContextBaseJavaModule {
    AlarmManager alarmManager;
    ReactApplicationContext reactContext;

    public String getName() {
        return "RNExitApp";
    }

    public RNExitAppModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.reactContext = reactApplicationContext;
        this.alarmManager = (AlarmManager) reactApplicationContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
    }

    @ReactMethod
    public void exitApp() {
        Process.killProcess(Process.myPid());
    }
}
