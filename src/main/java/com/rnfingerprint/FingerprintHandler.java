package com.rnfingerprint;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.AuthenticationCallback;
import android.hardware.fingerprint.FingerprintManager.AuthenticationResult;
import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.os.CancellationSignal;

@TargetApi(23)
public class FingerprintHandler extends AuthenticationCallback {
    private CancellationSignal cancellationSignal;
    private final Callback mCallback;
    private final FingerprintManager mFingerprintManager;
    private boolean selfCancelled;

    public interface Callback {
        void onAuthenticated();

        void onCancelled();

        void onError(String str, int i);
    }

    public FingerprintHandler(Context context, Callback callback) {
        this.mFingerprintManager = (FingerprintManager) context.getSystemService(FingerprintManager.class);
        this.mCallback = callback;
    }

    public void startAuth(CryptoObject cryptoObject) {
        this.cancellationSignal = new CancellationSignal();
        this.selfCancelled = false;
        this.mFingerprintManager.authenticate(cryptoObject, this.cancellationSignal, 0, this, null);
    }

    public void endAuth() {
        cancelAuthenticationSignal();
    }

    public void onAuthenticationError(int i, CharSequence charSequence) {
        if (!this.selfCancelled) {
            this.mCallback.onError(charSequence.toString(), i);
        }
    }

    public void onAuthenticationFailed() {
        this.mCallback.onError("Not recognized. Try again.", 105);
    }

    public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        this.mCallback.onAuthenticated();
        cancelAuthenticationSignal();
    }

    private void cancelAuthenticationSignal() {
        this.selfCancelled = true;
        CancellationSignal cancellationSignal = this.cancellationSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            this.cancellationSignal = null;
        }
    }
}
