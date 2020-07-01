package com.rnfingerprint;

import com.facebook.react.bridge.Callback;
import com.rnfingerprint.FingerprintDialog.DialogResultListener;

public class DialogResultHandler implements DialogResultListener {
    private Callback errorCallback;
    private Callback successCallback;

    public DialogResultHandler(Callback callback, Callback callback2) {
        this.errorCallback = callback;
        this.successCallback = callback2;
    }

    public void onAuthenticated() {
        FingerprintAuthModule.inProgress = false;
        this.successCallback.invoke("Successfully authenticated.");
    }

    public void onError(String str, int i) {
        FingerprintAuthModule.inProgress = false;
        this.errorCallback.invoke(str, Integer.valueOf(i));
    }

    public void onCancelled() {
        FingerprintAuthModule.inProgress = false;
        this.errorCallback.invoke("cancelled", Integer.valueOf(106));
    }
}
