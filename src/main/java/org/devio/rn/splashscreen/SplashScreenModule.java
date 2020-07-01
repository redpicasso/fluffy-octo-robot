package org.devio.rn.splashscreen;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class SplashScreenModule extends ReactContextBaseJavaModule {
    public String getName() {
        return "SplashScreen";
    }

    public SplashScreenModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void show() {
        SplashScreen.show(access$700());
    }

    @ReactMethod
    public void hide() {
        SplashScreen.hide(access$700());
    }
}
