package net.zubricky.AndroidKeyboardAdjust;

import android.app.Activity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class AndroidKeyboardAdjustModule extends ReactContextBaseJavaModule {
    public String getName() {
        return "AndroidKeyboardAdjust";
    }

    public AndroidKeyboardAdjustModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void setStateUnspecified() {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    currentActivity.getWindow().setSoftInputMode(0);
                }
            });
        }
    }

    @ReactMethod
    public void setAdjustNothing() {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    currentActivity.getWindow().setSoftInputMode(48);
                }
            });
        }
    }

    @ReactMethod
    public void setAdjustPan() {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    currentActivity.getWindow().setSoftInputMode(32);
                }
            });
        }
    }

    @ReactMethod
    public void setAdjustResize() {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    currentActivity.getWindow().setSoftInputMode(16);
                }
            });
        }
    }

    @ReactMethod
    public void setAdjustUnspecified() {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    currentActivity.getWindow().setSoftInputMode(0);
                }
            });
        }
    }

    @ReactMethod
    public void setAlwaysHidden() {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    currentActivity.getWindow().setSoftInputMode(3);
                }
            });
        }
    }

    @ReactMethod
    public void setAlwaysVisible() {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    currentActivity.getWindow().setSoftInputMode(5);
                }
            });
        }
    }

    @ReactMethod
    public void setVisible() {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    currentActivity.getWindow().setSoftInputMode(4);
                }
            });
        }
    }

    @ReactMethod
    public void setHidden() {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    currentActivity.getWindow().setSoftInputMode(2);
                }
            });
        }
    }

    @ReactMethod
    public void setUnchanged() {
        final Activity currentActivity = access$700();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    currentActivity.getWindow().setSoftInputMode(1);
                }
            });
        }
    }
}
