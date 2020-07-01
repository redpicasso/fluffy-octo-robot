package co.apptailor.googlesignin;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class RNGoogleSigninModule extends ReactContextBaseJavaModule {
    public static final String ERROR_USER_RECOVERABLE_AUTH = "ERROR_USER_RECOVERABLE_AUTH";
    public static final String MODULE_NAME = "RNGoogleSignin";
    public static final String PLAY_SERVICES_NOT_AVAILABLE = "PLAY_SERVICES_NOT_AVAILABLE";
    public static final int RC_SIGN_IN = 9001;
    public static final int REQUEST_CODE_RECOVER_AUTH = 53294;
    private static final String SHOULD_RECOVER = "SHOULD_RECOVER";
    private GoogleSignInClient _apiClient;
    private PendingAuthRecovery pendingAuthRecovery;
    private PromiseWrapper promiseWrapper = new PromiseWrapper();

    private static class AccessTokenRetrievalTask extends AsyncTask<WritableMap, Void, Void> {
        private WeakReference<RNGoogleSigninModule> weakModuleRef;

        AccessTokenRetrievalTask(RNGoogleSigninModule rNGoogleSigninModule) {
            this.weakModuleRef = new WeakReference(rNGoogleSigninModule);
        }

        protected Void doInBackground(WritableMap... writableMapArr) {
            WritableMap writableMap = writableMapArr[0];
            RNGoogleSigninModule rNGoogleSigninModule = (RNGoogleSigninModule) this.weakModuleRef.get();
            if (rNGoogleSigninModule == null) {
                return null;
            }
            try {
                insertAccessTokenIntoUserProperties(rNGoogleSigninModule, writableMap);
                rNGoogleSigninModule.getPromiseWrapper().resolve(writableMap);
            } catch (Exception e) {
                handleException(rNGoogleSigninModule, e, writableMap, writableMapArr.length >= 2 ? writableMapArr[1] : null);
            }
            return null;
        }

        private void insertAccessTokenIntoUserProperties(RNGoogleSigninModule rNGoogleSigninModule, WritableMap writableMap) throws IOException, GoogleAuthException {
            writableMap.putString("accessToken", GoogleAuthUtil.getToken(rNGoogleSigninModule.access$900(), new Account(writableMap.getMap("user").getString("email"), "com.google"), Utils.scopesToString(writableMap.getArray("scopes"))));
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x001d  */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x0019  */
        private void handleException(co.apptailor.googlesignin.RNGoogleSigninModule r3, java.lang.Exception r4, com.facebook.react.bridge.WritableMap r5, @androidx.annotation.Nullable com.facebook.react.bridge.WritableMap r6) {
            /*
            r2 = this;
            r0 = r4 instanceof com.google.android.gms.auth.UserRecoverableAuthException;
            if (r0 == 0) goto L_0x0027;
        L_0x0004:
            if (r6 == 0) goto L_0x0016;
        L_0x0006:
            r0 = "SHOULD_RECOVER";
            r1 = r6.hasKey(r0);
            if (r1 == 0) goto L_0x0016;
        L_0x000e:
            r6 = r6.getBoolean(r0);
            if (r6 == 0) goto L_0x0016;
        L_0x0014:
            r6 = 1;
            goto L_0x0017;
        L_0x0016:
            r6 = 0;
        L_0x0017:
            if (r6 == 0) goto L_0x001d;
        L_0x0019:
            r2.attemptRecovery(r3, r4, r5);
            goto L_0x0030;
        L_0x001d:
            r3 = r3.promiseWrapper;
            r5 = "ERROR_USER_RECOVERABLE_AUTH";
            r3.reject(r5, r4);
            goto L_0x0030;
        L_0x0027:
            r3 = r3.promiseWrapper;
            r5 = "RNGoogleSignin";
            r3.reject(r5, r4);
        L_0x0030:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: co.apptailor.googlesignin.RNGoogleSigninModule.AccessTokenRetrievalTask.handleException(co.apptailor.googlesignin.RNGoogleSigninModule, java.lang.Exception, com.facebook.react.bridge.WritableMap, com.facebook.react.bridge.WritableMap):void");
        }

        private void attemptRecovery(RNGoogleSigninModule rNGoogleSigninModule, Exception exception, WritableMap writableMap) {
            Activity access$700 = rNGoogleSigninModule.access$700();
            if (access$700 == null) {
                rNGoogleSigninModule.pendingAuthRecovery = null;
                PromiseWrapper access$400 = rNGoogleSigninModule.promiseWrapper;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot attempt recovery auth because app is not in foreground. ");
                stringBuilder.append(exception.getLocalizedMessage());
                access$400.reject(RNGoogleSigninModule.MODULE_NAME, stringBuilder.toString());
                return;
            }
            rNGoogleSigninModule.pendingAuthRecovery = new PendingAuthRecovery(writableMap);
            access$700.startActivityForResult(((UserRecoverableAuthException) exception).getIntent(), RNGoogleSigninModule.REQUEST_CODE_RECOVER_AUTH);
        }
    }

    private static class TokenClearingTask extends AsyncTask<String, Void, Void> {
        private WeakReference<RNGoogleSigninModule> weakModuleRef;

        TokenClearingTask(RNGoogleSigninModule rNGoogleSigninModule) {
            this.weakModuleRef = new WeakReference(rNGoogleSigninModule);
        }

        protected Void doInBackground(String... strArr) {
            RNGoogleSigninModule rNGoogleSigninModule = (RNGoogleSigninModule) this.weakModuleRef.get();
            if (rNGoogleSigninModule == null) {
                return null;
            }
            try {
                GoogleAuthUtil.clearToken(rNGoogleSigninModule.access$900(), strArr[0]);
                rNGoogleSigninModule.getPromiseWrapper().resolve(null);
            } catch (Throwable e) {
                rNGoogleSigninModule.promiseWrapper.reject(RNGoogleSigninModule.MODULE_NAME, e);
            }
            return null;
        }
    }

    private class RNGoogleSigninActivityEventListener extends BaseActivityEventListener {
        private RNGoogleSigninActivityEventListener() {
        }

        /* synthetic */ RNGoogleSigninActivityEventListener(RNGoogleSigninModule rNGoogleSigninModule, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
            if (i == RNGoogleSigninModule.RC_SIGN_IN) {
                RNGoogleSigninModule.this.handleSignInTaskResult(GoogleSignIn.getSignedInAccountFromIntent(intent));
            } else if (i != RNGoogleSigninModule.REQUEST_CODE_RECOVER_AUTH) {
            } else {
                if (i2 == -1) {
                    RNGoogleSigninModule.this.rerunFailedAuthTokenTask();
                } else {
                    RNGoogleSigninModule.this.promiseWrapper.reject(RNGoogleSigninModule.MODULE_NAME, "Failed authentication recovery attempt, probably user-rejected.");
                }
            }
        }
    }

    public String getName() {
        return MODULE_NAME;
    }

    public PromiseWrapper getPromiseWrapper() {
        return this.promiseWrapper;
    }

    public RNGoogleSigninModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContext.addActivityEventListener(new RNGoogleSigninActivityEventListener(this, null));
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> hashMap = new HashMap();
        Integer valueOf = Integer.valueOf(2);
        hashMap.put("BUTTON_SIZE_ICON", valueOf);
        Integer valueOf2 = Integer.valueOf(0);
        hashMap.put("BUTTON_SIZE_STANDARD", valueOf2);
        Integer valueOf3 = Integer.valueOf(1);
        hashMap.put("BUTTON_SIZE_WIDE", valueOf3);
        hashMap.put("BUTTON_COLOR_AUTO", valueOf);
        hashMap.put("BUTTON_COLOR_LIGHT", valueOf3);
        hashMap.put("BUTTON_COLOR_DARK", valueOf2);
        hashMap.put("SIGN_IN_CANCELLED", String.valueOf(GoogleSignInStatusCodes.SIGN_IN_CANCELLED));
        hashMap.put("SIGN_IN_REQUIRED", String.valueOf(4));
        hashMap.put("IN_PROGRESS", PromiseWrapper.ASYNC_OP_IN_PROGRESS);
        String str = PLAY_SERVICES_NOT_AVAILABLE;
        hashMap.put(str, str);
        return hashMap;
    }

    @ReactMethod
    public void playServicesAvailable(boolean z, Promise promise) {
        Context currentActivity = access$700();
        if (currentActivity == null) {
            String str = MODULE_NAME;
            Log.w(str, "could not determine playServicesAvailable, activity is null");
            promise.reject(str, "activity is null");
            return;
        }
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(currentActivity);
        if (isGooglePlayServicesAvailable != 0) {
            if (z && instance.isUserResolvableError(isGooglePlayServicesAvailable)) {
                instance.getErrorDialog(currentActivity, isGooglePlayServicesAvailable, 2404).show();
            }
            promise.reject(PLAY_SERVICES_NOT_AVAILABLE, "Play services not available");
        } else {
            promise.resolve(Boolean.valueOf(true));
        }
    }

    @ReactMethod
    public void configure(ReadableMap readableMap, Promise promise) {
        String str = "scopes";
        ReadableArray array = readableMap.hasKey(str) ? readableMap.getArray(str) : Arguments.createArray();
        String str2 = "webClientId";
        String string = readableMap.hasKey(str2) ? readableMap.getString(str2) : null;
        str2 = "offlineAccess";
        boolean z = readableMap.hasKey(str2) && readableMap.getBoolean(str2);
        String str3 = "forceCodeForRefreshToken";
        boolean z2 = readableMap.hasKey(str3) && readableMap.getBoolean(str3);
        str3 = "accountName";
        String string2 = readableMap.hasKey(str3) ? readableMap.getString(str3) : null;
        str3 = "hostedDomain";
        this._apiClient = GoogleSignIn.getClient(access$900(), Utils.getSignInOptions(Utils.createScopesArray(array), string, z, z2, string2, readableMap.hasKey(str3) ? readableMap.getString(str3) : null));
        promise.resolve(null);
    }

    @ReactMethod
    public void signInSilently(Promise promise) {
        if (this._apiClient == null) {
            rejectWithNullClientError(promise);
            return;
        }
        this.promiseWrapper.setPromiseWithInProgressCheck(promise, "signInSilently");
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                Task silentSignIn = RNGoogleSigninModule.this._apiClient.silentSignIn();
                if (silentSignIn.isSuccessful()) {
                    RNGoogleSigninModule.this.handleSignInTaskResult(silentSignIn);
                } else {
                    silentSignIn.addOnCompleteListener(new OnCompleteListener() {
                        public void onComplete(Task task) {
                            RNGoogleSigninModule.this.handleSignInTaskResult(task);
                        }
                    });
                }
            }
        });
    }

    private void handleSignInTaskResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) task.getResult(ApiException.class);
            if (googleSignInAccount == null) {
                this.promiseWrapper.reject(MODULE_NAME, "GoogleSignInAccount instance was null");
                return;
            }
            this.promiseWrapper.resolve(Utils.getUserProperties(googleSignInAccount));
        } catch (ApiException e) {
            int statusCode = e.getStatusCode();
            this.promiseWrapper.reject(String.valueOf(statusCode), GoogleSignInStatusCodes.getStatusCodeString(statusCode));
        }
    }

    @ReactMethod
    public void signIn(Promise promise) {
        if (this._apiClient == null) {
            rejectWithNullClientError(promise);
            return;
        }
        final Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(MODULE_NAME, "activity is null");
            return;
        }
        this.promiseWrapper.setPromiseWithInProgressCheck(promise, "signIn");
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                currentActivity.startActivityForResult(RNGoogleSigninModule.this._apiClient.getSignInIntent(), RNGoogleSigninModule.RC_SIGN_IN);
            }
        });
    }

    private void rerunFailedAuthTokenTask() {
        if (this.pendingAuthRecovery.getUserProperties() != null) {
            new AccessTokenRetrievalTask(this).execute(new WritableMap[]{r0, null});
            return;
        }
        this.promiseWrapper.reject(MODULE_NAME, "rerunFailedAuthTokenTask: recovery failed");
    }

    @ReactMethod
    public void signOut(final Promise promise) {
        GoogleSignInClient googleSignInClient = this._apiClient;
        if (googleSignInClient == null) {
            rejectWithNullClientError(promise);
        } else {
            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    RNGoogleSigninModule.this.handleSignOutOrRevokeAccessTask(task, promise);
                }
            });
        }
    }

    private void handleSignOutOrRevokeAccessTask(@NonNull Task<Void> task, Promise promise) {
        if (task.isSuccessful()) {
            promise.resolve(null);
            return;
        }
        int exceptionCode = Utils.getExceptionCode(task);
        promise.reject(String.valueOf(exceptionCode), GoogleSignInStatusCodes.getStatusCodeString(exceptionCode));
    }

    @ReactMethod
    public void revokeAccess(final Promise promise) {
        GoogleSignInClient googleSignInClient = this._apiClient;
        if (googleSignInClient == null) {
            rejectWithNullClientError(promise);
        } else {
            googleSignInClient.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    RNGoogleSigninModule.this.handleSignOutOrRevokeAccessTask(task, promise);
                }
            });
        }
    }

    @ReactMethod
    public void isSignedIn(Promise promise) {
        promise.resolve(Boolean.valueOf(GoogleSignIn.getLastSignedInAccount(access$900()) != null));
    }

    @ReactMethod
    public void getCurrentUser(Promise promise) {
        Object obj;
        GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(access$900());
        if (lastSignedInAccount == null) {
            obj = null;
        } else {
            obj = Utils.getUserProperties(lastSignedInAccount);
        }
        promise.resolve(obj);
    }

    @ReactMethod
    public void clearCachedAccessToken(String str, Promise promise) {
        this.promiseWrapper.setPromiseWithInProgressCheck(promise, "clearCachedAccessToken");
        new TokenClearingTask(this).execute(new String[]{str});
    }

    @ReactMethod
    public void getTokens(Promise promise) {
        GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(access$900());
        if (lastSignedInAccount == null) {
            promise.reject(MODULE_NAME, "getTokens requires a user to be signed in");
            return;
        }
        this.promiseWrapper.setPromiseWithInProgressCheck(promise, "getTokens");
        startTokenRetrievalTaskWithRecovery(lastSignedInAccount);
    }

    private void startTokenRetrievalTaskWithRecovery(GoogleSignInAccount googleSignInAccount) {
        WritableMap userProperties = Utils.getUserProperties(googleSignInAccount);
        Arguments.createMap().putBoolean(SHOULD_RECOVER, true);
        new AccessTokenRetrievalTask(this).execute(new WritableMap[]{userProperties, r0});
    }

    private void rejectWithNullClientError(Promise promise) {
        promise.reject(MODULE_NAME, "apiClient is null - call configure first");
    }
}
