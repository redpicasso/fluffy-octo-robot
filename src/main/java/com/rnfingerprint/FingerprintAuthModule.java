package com.rnfingerprint;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.os.Build.VERSION;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.rnfingerprint.FingerprintDialog.DialogResultListener;
import javax.crypto.Cipher;

public class FingerprintAuthModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private static final String FRAGMENT_TAG = "fingerprint_dialog";
    public static boolean inProgress = false;
    private boolean isAppActive;
    private KeyguardManager keyguardManager;

    public String getName() {
        return "FingerprintAuth";
    }

    public FingerprintAuthModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContext.addLifecycleEventListener(this);
    }

    private KeyguardManager getKeyguardManager() {
        KeyguardManager keyguardManager = this.keyguardManager;
        if (keyguardManager != null) {
            return keyguardManager;
        }
        Activity currentActivity = access$700();
        if (currentActivity == null) {
            return null;
        }
        this.keyguardManager = (KeyguardManager) currentActivity.getSystemService("keyguard");
        return this.keyguardManager;
    }

    @ReactMethod
    public void isSupported(Callback callback, Callback callback2) {
        if (access$700() != null) {
            if (isFingerprintAuthAvailable() == 100) {
                callback2.invoke("Is supported.");
            } else {
                callback.invoke("Not supported.", Integer.valueOf(isFingerprintAuthAvailable()));
            }
        }
    }

    @TargetApi(23)
    @ReactMethod
    public void authenticate(String str, ReadableMap readableMap, Callback callback, Callback callback2) {
        Activity currentActivity = access$700();
        if (!(inProgress || !this.isAppActive || currentActivity == null)) {
            inProgress = true;
            String str2 = "Not supported";
            if (isFingerprintAuthAvailable() != 100) {
                inProgress = false;
                callback.invoke(str2, Integer.valueOf(r2));
                return;
            }
            Cipher cipher = new FingerprintCipher().getCipher();
            if (cipher == null) {
                inProgress = false;
                callback.invoke(str2, Integer.valueOf(103));
                return;
            }
            CryptoObject cryptoObject = new CryptoObject(cipher);
            DialogResultListener dialogResultHandler = new DialogResultHandler(callback, callback2);
            FingerprintDialog fingerprintDialog = new FingerprintDialog();
            fingerprintDialog.setCryptoObject(cryptoObject);
            fingerprintDialog.setReasonForAuthentication(str);
            fingerprintDialog.setAuthConfig(readableMap);
            fingerprintDialog.setDialogCallback(dialogResultHandler);
            if (this.isAppActive) {
                fingerprintDialog.show(currentActivity.getFragmentManager(), FRAGMENT_TAG);
            } else {
                inProgress = false;
            }
        }
    }

    private int isFingerprintAuthAvailable() {
        if (VERSION.SDK_INT < 23) {
            return 101;
        }
        Activity currentActivity = access$700();
        if (currentActivity == null) {
            return 103;
        }
        KeyguardManager keyguardManager = getKeyguardManager();
        FingerprintManager fingerprintManager = (FingerprintManager) currentActivity.getSystemService("fingerprint");
        if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
            return 102;
        }
        if (keyguardManager == null || !keyguardManager.isKeyguardSecure()) {
            return 103;
        }
        return !fingerprintManager.hasEnrolledFingerprints() ? 104 : 100;
    }

    public void onHostResume() {
        this.isAppActive = true;
    }

    public void onHostPause() {
        this.isAppActive = false;
    }

    public void onHostDestroy() {
        this.isAppActive = false;
    }
}
