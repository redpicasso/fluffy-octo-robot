package co.apptailor.googlesignin;

import android.util.Log;
import com.facebook.react.bridge.Promise;

public class PromiseWrapper {
    public static final String ASYNC_OP_IN_PROGRESS = "ASYNC_OP_IN_PROGRESS";
    private String nameOfCallInProgress;
    private Promise promise;

    public void setPromiseWithInProgressCheck(Promise promise, String str) {
        Promise promise2 = this.promise;
        if (promise2 != null) {
            rejectPreviousPromiseBecauseNewOneIsInProgress(promise2, str);
        }
        this.promise = promise;
        this.nameOfCallInProgress = str;
    }

    public void resolve(Object obj) {
        Promise promise = this.promise;
        if (promise == null) {
            Log.w(RNGoogleSigninModule.MODULE_NAME, "cannot resolve promise because it's null");
            return;
        }
        resetMembers();
        promise.resolve(obj);
    }

    public void reject(String str, Throwable th) {
        Promise promise = this.promise;
        if (promise == null) {
            Log.w(RNGoogleSigninModule.MODULE_NAME, "cannot reject promise because it's null");
            return;
        }
        resetMembers();
        promise.reject(str, th.getLocalizedMessage(), th);
    }

    public void reject(String str, String str2) {
        Promise promise = this.promise;
        if (promise == null) {
            Log.w(RNGoogleSigninModule.MODULE_NAME, "cannot reject promise because it's null");
            return;
        }
        resetMembers();
        promise.reject(str, str2);
    }

    public String getNameOfCallInProgress() {
        return this.nameOfCallInProgress;
    }

    private void resetMembers() {
        this.promise = null;
        this.nameOfCallInProgress = null;
    }

    private void rejectPreviousPromiseBecauseNewOneIsInProgress(Promise promise, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Warning: previous promise did not settle and was overwritten. You've called \"");
        stringBuilder.append(str);
        stringBuilder.append("\" while \"");
        stringBuilder.append(getNameOfCallInProgress());
        stringBuilder.append("\" was already in progress and has not completed yet.");
        promise.reject(ASYNC_OP_IN_PROGRESS, stringBuilder.toString());
    }
}
