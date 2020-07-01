package io.invertase.firebase.auth;

import android.app.Activity;
import android.net.Uri;
import android.os.Parcel;
import android.util.Log;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseAuth.IdTokenListener;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest.Builder;
import io.invertase.firebase.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;

class RNFirebaseAuth extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFirebaseAuth";
    private static HashMap<String, AuthStateListener> mAuthListeners = new HashMap();
    private static HashMap<String, IdTokenListener> mIdTokenListeners = new HashMap();
    private PhoneAuthCredential mCredential;
    private ForceResendingToken mForceResendingToken;
    private String mLastPhoneNumber;
    private ReactContext mReactContext;
    private String mVerificationId;

    public String getName() {
        return TAG;
    }

    RNFirebaseAuth(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mReactContext = reactApplicationContext;
        Log.d(TAG, "instance-created");
    }

    public void initialize() {
        super.initialize();
        Log.d(TAG, "instance-initialized");
    }

    public void onCatalystInstanceDestroy() {
        Entry entry;
        super.onCatalystInstanceDestroy();
        Log.d(TAG, "instance-destroyed");
        Iterator it = mAuthListeners.entrySet().iterator();
        while (it.hasNext()) {
            entry = (Entry) it.next();
            FirebaseAuth.getInstance(FirebaseApp.getInstance((String) entry.getKey())).removeAuthStateListener((AuthStateListener) entry.getValue());
            it.remove();
        }
        it = mIdTokenListeners.entrySet().iterator();
        while (it.hasNext()) {
            entry = (Entry) it.next();
            FirebaseAuth.getInstance(FirebaseApp.getInstance((String) entry.getKey())).removeIdTokenListener((IdTokenListener) entry.getValue());
            it.remove();
        }
    }

    @ReactMethod
    public void addAuthStateListener(final String str) {
        Log.d(TAG, "addAuthStateListener");
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        if (((AuthStateListener) mAuthListeners.get(str)) == null) {
            AuthStateListener anonymousClass1 = new AuthStateListener() {
                public void onAuthStateChanged(@Nonnull FirebaseAuth firebaseAuth) {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    WritableMap createMap = Arguments.createMap();
                    String str = "auth_state_changed";
                    String str2 = "appName";
                    if (currentUser != null) {
                        createMap.putString(str2, str);
                        createMap.putMap("user", RNFirebaseAuth.this.firebaseUserToMap(currentUser));
                        Utils.sendEvent(RNFirebaseAuth.this.mReactContext, str, createMap);
                        return;
                    }
                    createMap.putString(str2, str);
                    Utils.sendEvent(RNFirebaseAuth.this.mReactContext, str, createMap);
                }
            };
            instance.addAuthStateListener(anonymousClass1);
            mAuthListeners.put(str, anonymousClass1);
        }
    }

    @ReactMethod
    public void removeAuthStateListener(String str) {
        Log.d(TAG, "removeAuthStateListener");
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        AuthStateListener authStateListener = (AuthStateListener) mAuthListeners.get(str);
        if (authStateListener != null) {
            instance.removeAuthStateListener(authStateListener);
            mAuthListeners.remove(str);
        }
    }

    @ReactMethod
    public void addIdTokenListener(final String str) {
        Log.d(TAG, "addIdTokenListener");
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        if (!mIdTokenListeners.containsKey(str)) {
            IdTokenListener anonymousClass2 = new IdTokenListener() {
                public void onIdTokenChanged(@Nonnull FirebaseAuth firebaseAuth) {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    WritableMap createMap = Arguments.createMap();
                    String str = "auth_id_token_changed";
                    String str2 = "authenticated";
                    String str3 = "appName";
                    if (currentUser != null) {
                        createMap.putBoolean(str2, true);
                        createMap.putString(str3, str);
                        createMap.putMap("user", RNFirebaseAuth.this.firebaseUserToMap(currentUser));
                        Utils.sendEvent(RNFirebaseAuth.this.mReactContext, str, createMap);
                        return;
                    }
                    createMap.putString(str3, str);
                    createMap.putBoolean(str2, false);
                    Utils.sendEvent(RNFirebaseAuth.this.mReactContext, str, createMap);
                }
            };
            instance.addIdTokenListener(anonymousClass2);
            mIdTokenListeners.put(str, anonymousClass2);
        }
    }

    @ReactMethod
    public void removeIdTokenListener(String str) {
        Log.d(TAG, "removeIdTokenListener");
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        IdTokenListener idTokenListener = (IdTokenListener) mIdTokenListeners.get(str);
        if (idTokenListener != null) {
            instance.removeIdTokenListener(idTokenListener);
            mIdTokenListeners.remove(str);
        }
    }

    @ReactMethod
    public void setAutoRetrievedSmsCodeForPhoneNumber(String str, String str2, String str3, Promise promise) {
        Log.d(TAG, "setAutoRetrievedSmsCodeForPhoneNumber");
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).getFirebaseAuthSettings().setAutoRetrievedSmsCodeForPhoneNumber(str2, str3);
        promise.resolve(null);
    }

    @ReactMethod
    public void signOut(String str, Promise promise) {
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        Log.d(TAG, "signOut");
        if (instance == null || instance.getCurrentUser() == null) {
            promiseNoUser(promise, Boolean.valueOf(true));
            return;
        }
        instance.signOut();
        promiseNoUser(promise, Boolean.valueOf(false));
    }

    @ReactMethod
    private void signInAnonymously(String str, final Promise promise) {
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        Log.d(TAG, "signInAnonymously");
        instance.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                Log.d(RNFirebaseAuth.TAG, "signInAnonymously:onComplete:success");
                RNFirebaseAuth.this.promiseWithAuthResult(authResult, promise);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@Nonnull Exception exception) {
                Log.e(RNFirebaseAuth.TAG, "signInAnonymously:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    private void createUserWithEmailAndPassword(String str, String str2, String str3, final Promise promise) {
        Log.d(TAG, "createUserWithEmailAndPassword");
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).createUserWithEmailAndPassword(str2, str3).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                Log.d(RNFirebaseAuth.TAG, "createUserWithEmailAndPassword:onComplete:success");
                RNFirebaseAuth.this.promiseWithAuthResult(authResult, promise);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@Nonnull Exception exception) {
                Log.e(RNFirebaseAuth.TAG, "createUserWithEmailAndPassword:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    private void signInWithEmailAndPassword(String str, String str2, String str3, final Promise promise) {
        Log.d(TAG, "signInWithEmailAndPassword");
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).signInWithEmailAndPassword(str2, str3).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                Log.d(RNFirebaseAuth.TAG, "signInWithEmailAndPassword:onComplete:success");
                RNFirebaseAuth.this.promiseWithAuthResult(authResult, promise);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@Nonnull Exception exception) {
                Log.e(RNFirebaseAuth.TAG, "signInWithEmailAndPassword:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    private void signInWithEmailLink(String str, String str2, String str3, final Promise promise) {
        Log.d(TAG, "signInWithEmailLink");
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).signInWithEmailLink(str2, str3).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                Log.d(RNFirebaseAuth.TAG, "signInWithEmailLink:onComplete:success");
                RNFirebaseAuth.this.promiseWithAuthResult(authResult, promise);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@Nonnull Exception exception) {
                Log.e(RNFirebaseAuth.TAG, "signInWithEmailLink:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    private void signInWithCustomToken(String str, String str2, final Promise promise) {
        Log.d(TAG, "signInWithCustomToken");
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).signInWithCustomToken(str2).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                Log.d(RNFirebaseAuth.TAG, "signInWithCustomToken:onComplete:success");
                RNFirebaseAuth.this.promiseWithAuthResult(authResult, promise);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@Nonnull Exception exception) {
                Log.e(RNFirebaseAuth.TAG, "signInWithCustomToken:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    public void sendPasswordResetEmail(String str, String str2, ReadableMap readableMap, final Promise promise) {
        Log.d(TAG, "sendPasswordResetEmail");
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        OnCompleteListener anonymousClass13 = new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "sendPasswordResetEmail:onComplete:success");
                    RNFirebaseAuth.this.promiseNoUser(promise, Boolean.valueOf(false));
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "sendPasswordResetEmail:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        };
        if (readableMap == null) {
            instance.sendPasswordResetEmail(str2).addOnCompleteListener(anonymousClass13);
        } else {
            instance.sendPasswordResetEmail(str2, buildActionCodeSettings(readableMap)).addOnCompleteListener(anonymousClass13);
        }
    }

    @ReactMethod
    public void sendSignInLinkToEmail(String str, String str2, ReadableMap readableMap, final Promise promise) {
        Log.d(TAG, "sendSignInLinkToEmail");
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        instance.sendSignInLinkToEmail(str2, buildActionCodeSettings(readableMap)).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "sendSignInLinkToEmail:onComplete:success");
                    RNFirebaseAuth.this.promiseNoUser(promise, Boolean.valueOf(false));
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "sendSignInLinkToEmail:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    public void delete(String str, final Promise promise) {
        FirebaseUser currentUser = FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).getCurrentUser();
        String str2 = TAG;
        Log.d(str2, "delete");
        if (currentUser != null) {
            currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@Nonnull Task<Void> task) {
                    boolean isSuccessful = task.isSuccessful();
                    String str = RNFirebaseAuth.TAG;
                    if (isSuccessful) {
                        Log.d(str, "delete:onComplete:success");
                        RNFirebaseAuth.this.promiseNoUser(promise, Boolean.valueOf(false));
                        return;
                    }
                    Throwable exception = task.getException();
                    Log.e(str, "delete:onComplete:failure", exception);
                    RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
                }
            });
            return;
        }
        Log.e(str2, "delete:failure:noCurrentUser");
        promiseNoUser(promise, Boolean.valueOf(true));
    }

    @ReactMethod
    public void reload(String str, final Promise promise) {
        final FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        FirebaseUser currentUser = instance.getCurrentUser();
        String str2 = TAG;
        Log.d(str2, "reload");
        if (currentUser == null) {
            promiseNoUser(promise, Boolean.valueOf(false));
            Log.e(str2, "reload:failure:noCurrentUser");
            return;
        }
        currentUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "reload:onComplete:success");
                    RNFirebaseAuth.this.promiseWithUser(instance.getCurrentUser(), promise);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "reload:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    public void sendEmailVerification(String str, ReadableMap readableMap, final Promise promise) {
        final FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        FirebaseUser currentUser = instance.getCurrentUser();
        String str2 = TAG;
        Log.d(str2, "sendEmailVerification");
        if (currentUser == null) {
            promiseNoUser(promise, Boolean.valueOf(false));
            Log.e(str2, "sendEmailVerification:failure:noCurrentUser");
            return;
        }
        OnCompleteListener anonymousClass17 = new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "sendEmailVerification:onComplete:success");
                    RNFirebaseAuth.this.promiseWithUser(instance.getCurrentUser(), promise);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "sendEmailVerification:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        };
        if (readableMap == null) {
            currentUser.sendEmailVerification().addOnCompleteListener(anonymousClass17);
        } else {
            currentUser.sendEmailVerification(buildActionCodeSettings(readableMap)).addOnCompleteListener(anonymousClass17);
        }
    }

    @ReactMethod
    public void updateEmail(String str, String str2, final Promise promise) {
        final FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        FirebaseUser currentUser = instance.getCurrentUser();
        String str3 = TAG;
        Log.d(str3, "updateEmail");
        if (currentUser == null) {
            promiseNoUser(promise, Boolean.valueOf(false));
            Log.e(str3, "updateEmail:failure:noCurrentUser");
            return;
        }
        currentUser.updateEmail(str2).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "updateEmail:onComplete:success");
                    RNFirebaseAuth.this.promiseWithUser(instance.getCurrentUser(), promise);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "updateEmail:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    public void updatePassword(String str, String str2, final Promise promise) {
        final FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        FirebaseUser currentUser = instance.getCurrentUser();
        String str3 = TAG;
        Log.d(str3, "updatePassword");
        if (currentUser == null) {
            promiseNoUser(promise, Boolean.valueOf(false));
            Log.e(str3, "updatePassword:failure:noCurrentUser");
            return;
        }
        currentUser.updatePassword(str2).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "updatePassword:onComplete:success");
                    RNFirebaseAuth.this.promiseWithUser(instance.getCurrentUser(), promise);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "updatePassword:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    private void updatePhoneNumber(String str, String str2, String str3, String str4, final Promise promise) {
        final FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        FirebaseUser currentUser = instance.getCurrentUser();
        String str5 = "auth/invalid-credential";
        if (!str2.equals("phone")) {
            promise.reject(str5, "The supplied auth credential does not have a phone provider.");
        }
        PhoneAuthCredential phoneAuthCredential = getPhoneAuthCredential(str3, str4);
        if (phoneAuthCredential == null) {
            promise.reject(str5, "The supplied auth credential is malformed, has expired or is not currently supported.");
            return;
        }
        str3 = TAG;
        if (currentUser == null) {
            promiseNoUser(promise, Boolean.valueOf(false));
            Log.e(str3, "updatePhoneNumber:failure:noCurrentUser");
            return;
        }
        Log.d(str3, "updatePhoneNumber");
        currentUser.updatePhoneNumber(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "updatePhoneNumber:onComplete:success");
                    RNFirebaseAuth.this.promiseWithUser(instance.getCurrentUser(), promise);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "updatePhoneNumber:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    public void updateProfile(String str, ReadableMap readableMap, final Promise promise) {
        final FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        FirebaseUser currentUser = instance.getCurrentUser();
        String str2 = TAG;
        Log.d(str2, "updateProfile");
        if (currentUser == null) {
            promiseNoUser(promise, Boolean.valueOf(false));
            Log.e(str2, "updateProfile:failure:noCurrentUser");
            return;
        }
        Builder builder = new Builder();
        String str3 = "displayName";
        if (readableMap.hasKey(str3)) {
            builder.setDisplayName(readableMap.getString(str3));
        }
        str3 = "photoURL";
        if (readableMap.hasKey(str3)) {
            Uri uri;
            String string = readableMap.getString(str3);
            if (string == null) {
                uri = null;
            } else {
                uri = Uri.parse(string);
            }
            builder.setPhotoUri(uri);
        }
        currentUser.updateProfile(builder.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "updateProfile:onComplete:success");
                    RNFirebaseAuth.this.promiseWithUser(instance.getCurrentUser(), promise);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "updateProfile:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    private void signInWithCredential(String str, String str2, String str3, String str4, final Promise promise) {
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        AuthCredential credentialForProvider = getCredentialForProvider(str2, str3, str4);
        if (credentialForProvider == null) {
            promise.reject("auth/invalid-credential", "The supplied auth credential is malformed, has expired or is not currently supported.");
            return;
        }
        Log.d(TAG, "signInWithCredential");
        instance.signInWithCredential(credentialForProvider).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(@Nonnull Task<AuthResult> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "signInWithCredential:onComplete:success");
                    RNFirebaseAuth.this.promiseWithAuthResult((AuthResult) task.getResult(), promise);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "signInWithCredential:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    public void signInWithPhoneNumber(String str, String str2, boolean z, final Promise promise) {
        Log.d(TAG, "signInWithPhoneNumber");
        final FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        Activity currentActivity = this.mReactContext.getCurrentActivity();
        if (!str2.equals(this.mLastPhoneNumber)) {
            this.mForceResendingToken = null;
            this.mLastPhoneNumber = str2;
        }
        this.mVerificationId = null;
        OnVerificationStateChangedCallbacks anonymousClass23 = new OnVerificationStateChangedCallbacks() {
            private boolean promiseResolved = false;

            public void onVerificationCompleted(final PhoneAuthCredential phoneAuthCredential) {
                instance.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    public void onComplete(@Nonnull Task<AuthResult> task) {
                        boolean isSuccessful = task.isSuccessful();
                        String str = RNFirebaseAuth.TAG;
                        if (isSuccessful) {
                            Log.d(str, "signInWithPhoneNumber:autoVerified:signInWithCredential:onComplete:success");
                            if (!AnonymousClass23.this.promiseResolved) {
                                WritableMap createMap = Arguments.createMap();
                                Parcel obtain = Parcel.obtain();
                                phoneAuthCredential.writeToParcel(obtain, 0);
                                obtain.setDataPosition(16);
                                str = obtain.readString();
                                RNFirebaseAuth.this.mVerificationId = str;
                                obtain.recycle();
                                createMap.putString("verificationId", str);
                                promise.resolve(createMap);
                                return;
                            }
                            return;
                        }
                        Throwable exception = task.getException();
                        Log.e(str, "signInWithPhoneNumber:autoVerified:signInWithCredential:onComplete:failure", exception);
                        if (!AnonymousClass23.this.promiseResolved) {
                            RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
                        }
                    }
                });
            }

            public void onVerificationFailed(FirebaseException firebaseException) {
                Log.d(RNFirebaseAuth.TAG, "signInWithPhoneNumber:verification:failed");
                RNFirebaseAuth.this.promiseRejectAuthException(promise, firebaseException);
            }

            public void onCodeSent(String str, ForceResendingToken forceResendingToken) {
                RNFirebaseAuth.this.mVerificationId = str;
                RNFirebaseAuth.this.mForceResendingToken = forceResendingToken;
                WritableMap createMap = Arguments.createMap();
                createMap.putString("verificationId", str);
                promise.resolve(createMap);
                this.promiseResolved = true;
            }

            public void onCodeAutoRetrievalTimeOut(String str) {
                super.onCodeAutoRetrievalTimeOut(str);
            }
        };
        if (currentActivity == null) {
            return;
        }
        if (!z || this.mForceResendingToken == null) {
            PhoneAuthProvider.getInstance(instance).verifyPhoneNumber(str2, 60, TimeUnit.SECONDS, currentActivity, anonymousClass23);
            return;
        }
        PhoneAuthProvider.getInstance(instance).verifyPhoneNumber(str2, 60, TimeUnit.SECONDS, currentActivity, anonymousClass23, this.mForceResendingToken);
    }

    @ReactMethod
    public void _confirmVerificationCode(String str, String str2, final Promise promise) {
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).signInWithCredential(PhoneAuthProvider.getCredential(this.mVerificationId, str2)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(@Nonnull Task<AuthResult> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "_confirmVerificationCode:signInWithCredential:onComplete:success");
                    RNFirebaseAuth.this.promiseWithUser(((AuthResult) task.getResult()).getUser(), promise);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "_confirmVerificationCode:signInWithCredential:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    public void verifyPhoneNumber(final String str, String str2, final String str3, int i, boolean z) {
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        Activity currentActivity = this.mReactContext.getCurrentActivity();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("verifyPhoneNumber:");
        stringBuilder.append(str2);
        Log.d(TAG, stringBuilder.toString());
        if (!str2.equals(this.mLastPhoneNumber)) {
            this.mForceResendingToken = null;
            this.mLastPhoneNumber = str2;
        }
        this.mCredential = null;
        OnVerificationStateChangedCallbacks anonymousClass25 = new OnVerificationStateChangedCallbacks() {
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                RNFirebaseAuth.this.mCredential = phoneAuthCredential;
                Log.d(RNFirebaseAuth.TAG, "verifyPhoneNumber:verification:onVerificationCompleted");
                WritableMap createMap = Arguments.createMap();
                Parcel obtain = Parcel.obtain();
                phoneAuthCredential.writeToParcel(obtain, 0);
                obtain.setDataPosition(16);
                String readString = obtain.readString();
                obtain.setDataPosition(obtain.dataPosition() + 8);
                createMap.putString("code", obtain.readString());
                createMap.putString("verificationId", readString);
                obtain.recycle();
                RNFirebaseAuth.this.sendPhoneStateEvent(str, str3, "onVerificationComplete", createMap);
            }

            public void onVerificationFailed(FirebaseException firebaseException) {
                Log.d(RNFirebaseAuth.TAG, "verifyPhoneNumber:verification:onVerificationFailed");
                WritableMap createMap = Arguments.createMap();
                createMap.putMap(ReactVideoView.EVENT_PROP_ERROR, RNFirebaseAuth.this.getJSError(firebaseException));
                RNFirebaseAuth.this.sendPhoneStateEvent(str, str3, "onVerificationFailed", createMap);
            }

            public void onCodeSent(String str, ForceResendingToken forceResendingToken) {
                Log.d(RNFirebaseAuth.TAG, "verifyPhoneNumber:verification:onCodeSent");
                RNFirebaseAuth.this.mForceResendingToken = forceResendingToken;
                WritableMap createMap = Arguments.createMap();
                String str2 = "verificationId";
                createMap.putString(str2, str);
                createMap.putString(str2, str);
                RNFirebaseAuth.this.sendPhoneStateEvent(str, str3, "onCodeSent", createMap);
            }

            public void onCodeAutoRetrievalTimeOut(String str) {
                super.onCodeAutoRetrievalTimeOut(str);
                Log.d(RNFirebaseAuth.TAG, "verifyPhoneNumber:verification:onCodeAutoRetrievalTimeOut");
                WritableMap createMap = Arguments.createMap();
                createMap.putString("verificationId", str);
                RNFirebaseAuth.this.sendPhoneStateEvent(str, str3, "onCodeAutoRetrievalTimeout", createMap);
            }
        };
        if (currentActivity == null) {
            return;
        }
        if (!z || this.mForceResendingToken == null) {
            PhoneAuthProvider.getInstance(instance).verifyPhoneNumber(str2, (long) i, TimeUnit.SECONDS, currentActivity, anonymousClass25);
            return;
        }
        PhoneAuthProvider.getInstance(instance).verifyPhoneNumber(str2, (long) i, TimeUnit.SECONDS, currentActivity, anonymousClass25, this.mForceResendingToken);
    }

    @ReactMethod
    public void confirmPasswordReset(String str, String str2, String str3, final Promise promise) {
        Log.d(TAG, "confirmPasswordReset");
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).confirmPasswordReset(str2, str3).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "confirmPasswordReset:onComplete:success");
                    RNFirebaseAuth.this.promiseNoUser(promise, Boolean.valueOf(false));
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "confirmPasswordReset:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    public void applyActionCode(String str, String str2, final Promise promise) {
        Log.d(TAG, "applyActionCode");
        final FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        instance.applyActionCode(str2).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "applyActionCode:onComplete:success");
                    RNFirebaseAuth.this.promiseWithUser(instance.getCurrentUser(), promise);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "applyActionCode:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    public void checkActionCode(String str, String str2, final Promise promise) {
        Log.d(TAG, "checkActionCode");
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).checkActionCode(str2).addOnCompleteListener(new OnCompleteListener<ActionCodeResult>() {
            public void onComplete(@Nonnull Task<ActionCodeResult> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "checkActionCode:onComplete:success");
                    ActionCodeResult actionCodeResult = (ActionCodeResult) task.getResult();
                    WritableMap createMap = Arguments.createMap();
                    WritableMap createMap2 = Arguments.createMap();
                    createMap2.putString("email", actionCodeResult.getData(0));
                    createMap2.putString("fromEmail", actionCodeResult.getData(1));
                    createMap.putMap("data", createMap2);
                    int operation = actionCodeResult.getOperation();
                    String str2 = operation != 0 ? operation != 1 ? operation != 2 ? operation != 3 ? operation != 4 ? "UNKNOWN" : "EMAIL_SIGNIN" : "ERROR" : "RECOVER_EMAIL" : "VERIFY_EMAIL" : "PASSWORD_RESET";
                    createMap.putString("operation", str2);
                    promise.resolve(createMap);
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "checkActionCode:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    private void linkWithCredential(String str, String str2, String str3, String str4, final Promise promise) {
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        AuthCredential credentialForProvider = getCredentialForProvider(str2, str3, str4);
        if (credentialForProvider == null) {
            promise.reject("auth/invalid-credential", "The supplied auth credential is malformed, has expired or is not currently supported.");
            return;
        }
        FirebaseUser currentUser = instance.getCurrentUser();
        Log.d(TAG, "link");
        if (currentUser != null) {
            currentUser.linkWithCredential(credentialForProvider).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(@Nonnull Task<AuthResult> task) {
                    boolean isSuccessful = task.isSuccessful();
                    String str = RNFirebaseAuth.TAG;
                    if (isSuccessful) {
                        Log.d(str, "link:onComplete:success");
                        RNFirebaseAuth.this.promiseWithAuthResult((AuthResult) task.getResult(), promise);
                        return;
                    }
                    Throwable exception = task.getException();
                    Log.e(str, "link:onComplete:failure", exception);
                    RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
                }
            });
        } else {
            promiseNoUser(promise, Boolean.valueOf(true));
        }
    }

    @ReactMethod
    public void unlink(String str, String str2, final Promise promise) {
        FirebaseUser currentUser = FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).getCurrentUser();
        Log.d(TAG, "unlink");
        if (currentUser != null) {
            currentUser.unlink(str2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(@Nonnull Task<AuthResult> task) {
                    boolean isSuccessful = task.isSuccessful();
                    String str = RNFirebaseAuth.TAG;
                    if (isSuccessful) {
                        Log.d(str, "unlink:onComplete:success");
                        RNFirebaseAuth.this.promiseWithUser(((AuthResult) task.getResult()).getUser(), promise);
                        return;
                    }
                    Throwable exception = task.getException();
                    Log.e(str, "unlink:onComplete:failure", exception);
                    RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
                }
            });
        } else {
            promiseNoUser(promise, Boolean.valueOf(true));
        }
    }

    @ReactMethod
    private void reauthenticateWithCredential(String str, String str2, String str3, String str4, final Promise promise) {
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        AuthCredential credentialForProvider = getCredentialForProvider(str2, str3, str4);
        if (credentialForProvider == null) {
            promise.reject("auth/invalid-credential", "The supplied auth credential is malformed, has expired or is not currently supported.");
            return;
        }
        FirebaseUser currentUser = instance.getCurrentUser();
        Log.d(TAG, "reauthenticate");
        if (currentUser != null) {
            currentUser.reauthenticateAndRetrieveData(credentialForProvider).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(@Nonnull Task<AuthResult> task) {
                    boolean isSuccessful = task.isSuccessful();
                    String str = RNFirebaseAuth.TAG;
                    if (isSuccessful) {
                        Log.d(str, "reauthenticate:onComplete:success");
                        RNFirebaseAuth.this.promiseWithAuthResult((AuthResult) task.getResult(), promise);
                        return;
                    }
                    Throwable exception = task.getException();
                    Log.e(str, "reauthenticate:onComplete:failure", exception);
                    RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
                }
            });
        } else {
            promiseNoUser(promise, Boolean.valueOf(true));
        }
    }

    private com.google.firebase.auth.AuthCredential getCredentialForProvider(java.lang.String r2, java.lang.String r3, java.lang.String r4) {
        /*
        r1 = this;
        r0 = r2.hashCode();
        switch(r0) {
            case -1830313082: goto L_0x004e;
            case -1536293812: goto L_0x0044;
            case -364826023: goto L_0x003a;
            case 105516695: goto L_0x0030;
            case 106642798: goto L_0x0026;
            case 1216985755: goto L_0x001c;
            case 1985010934: goto L_0x0012;
            case 2120171958: goto L_0x0008;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0058;
    L_0x0008:
        r0 = "emailLink";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x0058;
    L_0x0010:
        r0 = 7;
        goto L_0x0059;
    L_0x0012:
        r0 = "github.com";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x0058;
    L_0x001a:
        r0 = 3;
        goto L_0x0059;
    L_0x001c:
        r0 = "password";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x0058;
    L_0x0024:
        r0 = 6;
        goto L_0x0059;
    L_0x0026:
        r0 = "phone";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x0058;
    L_0x002e:
        r0 = 5;
        goto L_0x0059;
    L_0x0030:
        r0 = "oauth";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x0058;
    L_0x0038:
        r0 = 4;
        goto L_0x0059;
    L_0x003a:
        r0 = "facebook.com";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x0058;
    L_0x0042:
        r0 = 0;
        goto L_0x0059;
    L_0x0044:
        r0 = "google.com";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x0058;
    L_0x004c:
        r0 = 1;
        goto L_0x0059;
    L_0x004e:
        r0 = "twitter.com";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x0058;
    L_0x0056:
        r0 = 2;
        goto L_0x0059;
    L_0x0058:
        r0 = -1;
    L_0x0059:
        switch(r0) {
            case 0: goto L_0x0081;
            case 1: goto L_0x007c;
            case 2: goto L_0x0077;
            case 3: goto L_0x0072;
            case 4: goto L_0x006d;
            case 5: goto L_0x0068;
            case 6: goto L_0x0063;
            case 7: goto L_0x005e;
            default: goto L_0x005c;
        };
    L_0x005c:
        r2 = 0;
        return r2;
    L_0x005e:
        r2 = com.google.firebase.auth.EmailAuthProvider.getCredentialWithLink(r3, r4);
        return r2;
    L_0x0063:
        r2 = com.google.firebase.auth.EmailAuthProvider.getCredential(r3, r4);
        return r2;
    L_0x0068:
        r2 = r1.getPhoneAuthCredential(r3, r4);
        return r2;
    L_0x006d:
        r2 = com.google.firebase.auth.OAuthProvider.getCredential(r2, r3, r4);
        return r2;
    L_0x0072:
        r2 = com.google.firebase.auth.GithubAuthProvider.getCredential(r3);
        return r2;
    L_0x0077:
        r2 = com.google.firebase.auth.TwitterAuthProvider.getCredential(r3, r4);
        return r2;
    L_0x007c:
        r2 = com.google.firebase.auth.GoogleAuthProvider.getCredential(r3, r4);
        return r2;
    L_0x0081:
        r2 = com.google.firebase.auth.FacebookAuthProvider.getCredential(r3);
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.auth.RNFirebaseAuth.getCredentialForProvider(java.lang.String, java.lang.String, java.lang.String):com.google.firebase.auth.AuthCredential");
    }

    private PhoneAuthCredential getPhoneAuthCredential(String str, String str2) {
        if (str == null) {
            PhoneAuthCredential phoneAuthCredential = this.mCredential;
            if (phoneAuthCredential != null) {
                this.mCredential = null;
                return phoneAuthCredential;
            }
        }
        if (str != null) {
            return PhoneAuthProvider.getCredential(str, str2);
        }
        return null;
    }

    @ReactMethod
    public void getIdToken(String str, Boolean bool, final Promise promise) {
        Log.d(TAG, "getIdToken");
        FirebaseUser currentUser = FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).getCurrentUser();
        if (currentUser == null) {
            promiseNoUser(promise, Boolean.valueOf(true));
        } else {
            currentUser.getIdToken(bool.booleanValue()).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                public void onComplete(@Nonnull Task<GetTokenResult> task) {
                    boolean isSuccessful = task.isSuccessful();
                    String str = RNFirebaseAuth.TAG;
                    if (isSuccessful) {
                        Log.d(str, "getIdToken:onComplete:success");
                        promise.resolve(((GetTokenResult) task.getResult()).getToken());
                        return;
                    }
                    Throwable exception = task.getException();
                    Log.e(str, "getIdToken:onComplete:failure", exception);
                    RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
                }
            });
        }
    }

    @ReactMethod
    public void getIdTokenResult(String str, Boolean bool, final Promise promise) {
        Log.d(TAG, "getIdTokenResult");
        FirebaseUser currentUser = FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).getCurrentUser();
        if (currentUser == null) {
            promiseNoUser(promise, Boolean.valueOf(true));
        } else {
            currentUser.getIdToken(bool.booleanValue()).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                public void onComplete(@Nonnull Task<GetTokenResult> task) {
                    boolean isSuccessful = task.isSuccessful();
                    String str = RNFirebaseAuth.TAG;
                    if (isSuccessful) {
                        Log.d(str, "getIdTokenResult:onComplete:success");
                        GetTokenResult getTokenResult = (GetTokenResult) task.getResult();
                        WritableMap createMap = Arguments.createMap();
                        Utils.mapPutValue("authTime", Utils.timestampToUTC(getTokenResult.getAuthTimestamp()), createMap);
                        Utils.mapPutValue("expirationTime", Utils.timestampToUTC(getTokenResult.getExpirationTimestamp()), createMap);
                        Utils.mapPutValue("issuedAtTime", Utils.timestampToUTC(getTokenResult.getIssuedAtTimestamp()), createMap);
                        Utils.mapPutValue("claims", getTokenResult.getClaims(), createMap);
                        Utils.mapPutValue("signInProvider", getTokenResult.getSignInProvider(), createMap);
                        Utils.mapPutValue("token", getTokenResult.getToken(), createMap);
                        promise.resolve(createMap);
                        return;
                    }
                    Throwable exception = task.getException();
                    Log.e(str, "getIdTokenResult:onComplete:failure", exception);
                    RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
                }
            });
        }
    }

    @ReactMethod
    public void fetchSignInMethodsForEmail(String str, String str2, final Promise promise) {
        FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(str));
        Log.d(TAG, "fetchProvidersForEmail");
        instance.fetchSignInMethodsForEmail(str2).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            public void onComplete(@Nonnull Task<SignInMethodQueryResult> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "fetchProvidersForEmail:onComplete:success");
                    List<String> signInMethods = ((SignInMethodQueryResult) task.getResult()).getSignInMethods();
                    WritableArray createArray = Arguments.createArray();
                    if (signInMethods != null) {
                        for (String str2 : signInMethods) {
                            createArray.pushString(str2);
                        }
                    }
                    promise.resolve(createArray);
                    return;
                }
                Throwable exception = task.getException();
                Log.d(str2, "fetchProvidersForEmail:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    @ReactMethod
    public void setLanguageCode(String str, String str2) {
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).setLanguageCode(str2);
    }

    @ReactMethod
    public void useDeviceLanguage(String str) {
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).useAppLanguage();
    }

    @ReactMethod
    public void verifyPasswordResetCode(String str, String str2, final Promise promise) {
        Log.d(TAG, "verifyPasswordResetCode");
        FirebaseAuth.getInstance(FirebaseApp.getInstance(str)).verifyPasswordResetCode(str2).addOnCompleteListener(new OnCompleteListener<String>() {
            public void onComplete(@Nonnull Task<String> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseAuth.TAG;
                if (isSuccessful) {
                    Log.d(str, "verifyPasswordResetCode:onComplete:success");
                    promise.resolve(task.getResult());
                    return;
                }
                Throwable exception = task.getException();
                Log.e(str, "verifyPasswordResetCode:onComplete:failure", exception);
                RNFirebaseAuth.this.promiseRejectAuthException(promise, exception);
            }
        });
    }

    private void promiseNoUser(Promise promise, Boolean bool) {
        if (bool.booleanValue()) {
            promise.reject("auth/no-current-user", "No user currently signed in.");
        } else {
            promise.resolve(null);
        }
    }

    private void promiseWithUser(FirebaseUser firebaseUser, Promise promise) {
        if (firebaseUser != null) {
            promise.resolve(firebaseUserToMap(firebaseUser));
        } else {
            promiseNoUser(promise, Boolean.valueOf(true));
        }
    }

    private void promiseWithAuthResult(AuthResult authResult, Promise promise) {
        if (authResult == null || authResult.getUser() == null) {
            promiseNoUser(promise, Boolean.valueOf(true));
            return;
        }
        WritableMap createMap = Arguments.createMap();
        WritableMap firebaseUserToMap = firebaseUserToMap(authResult.getUser());
        if (authResult.getAdditionalUserInfo() != null) {
            WritableMap createMap2 = Arguments.createMap();
            createMap2.putBoolean("isNewUser", authResult.getAdditionalUserInfo().isNewUser());
            if (authResult.getAdditionalUserInfo().getProfile() != null) {
                Utils.mapPutValue(Scopes.PROFILE, authResult.getAdditionalUserInfo().getProfile(), createMap2);
            }
            if (authResult.getAdditionalUserInfo().getProviderId() != null) {
                createMap2.putString("providerId", authResult.getAdditionalUserInfo().getProviderId());
            }
            if (authResult.getAdditionalUserInfo().getUsername() != null) {
                createMap2.putString("username", authResult.getAdditionalUserInfo().getUsername());
            }
            createMap.putMap("additionalUserInfo", createMap2);
        }
        createMap.putMap("user", firebaseUserToMap);
        promise.resolve(createMap);
    }

    private void promiseRejectAuthException(Promise promise, Exception exception) {
        WritableMap jSError = getJSError(exception);
        promise.reject(jSError.getString("code"), jSError.getString("message"), (Throwable) exception);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:io.invertase.firebase.auth.RNFirebaseAuth.getJSError(java.lang.Exception):com.facebook.react.bridge.WritableMap, dom blocks: []
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.lang.Iterable.forEach(Unknown Source)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    private com.facebook.react.bridge.WritableMap getJSError(java.lang.Exception r11) {
        /*
        r10 = this;
        r0 = "The user's credential is no longer valid. The user must sign in again.";
        r1 = "UNKNOWN";
        r2 = "INVALID_EMAIL";
        r3 = com.facebook.react.bridge.Arguments.createMap();
        r4 = r11.getMessage();
        r5 = "The email address is badly formatted.";
        r6 = r11;	 Catch:{ Exception -> 0x0024 }
        r6 = (com.google.firebase.auth.FirebaseAuthException) r6;	 Catch:{ Exception -> 0x0024 }
        r7 = r6.getErrorCode();	 Catch:{ Exception -> 0x0024 }
        r8 = "nativeErrorCode";	 Catch:{ Exception -> 0x0022 }
        r3.putString(r8, r7);	 Catch:{ Exception -> 0x0022 }
        r0 = r6.getMessage();	 Catch:{ Exception -> 0x0022 }
        goto L_0x0131;
        goto L_0x0025;
    L_0x0024:
        r7 = r1;
    L_0x0025:
        r6 = "([A-Z]*_[A-Z]*)";
        r6 = java.util.regex.Pattern.compile(r6);
        r6 = r6.matcher(r4);
        r8 = r6.find();
        if (r8 == 0) goto L_0x0130;
    L_0x0035:
        r7 = 1;
        r6 = r6.group(r7);
        r6 = r6.trim();
        r8 = -1;
        r9 = r6.hashCode();
        switch(r9) {
            case -2127468245: goto L_0x00f4;
            case -1971163201: goto L_0x00ea;
            case -1112393964: goto L_0x00e2;
            case -1035666916: goto L_0x00d7;
            case -333672188: goto L_0x00cc;
            case -324930558: goto L_0x00c3;
            case -311841705: goto L_0x00b8;
            case -75433118: goto L_0x00ad;
            case -49749054: goto L_0x00a3;
            case -40686718: goto L_0x0098;
            case 583750925: goto L_0x008d;
            case 748182870: goto L_0x0082;
            case 864281573: goto L_0x0077;
            case 1072360691: goto L_0x006c;
            case 1388786705: goto L_0x0060;
            case 1433767024: goto L_0x0054;
            case 1563975629: goto L_0x0048;
            default: goto L_0x0046;
        };
    L_0x0046:
        goto L_0x00ff;
    L_0x0048:
        r7 = "INVALID_USER_TOKEN";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x0050:
        r7 = 13;
        goto L_0x0100;
    L_0x0054:
        r7 = "USER_DISABLED";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x005c:
        r7 = 10;
        goto L_0x0100;
    L_0x0060:
        r7 = "INVALID_IDENTIFIER";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x0068:
        r7 = 16;
        goto L_0x0100;
    L_0x006c:
        r7 = "INVALID_CUSTOM_TOKEN";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x0074:
        r7 = 0;
        goto L_0x0100;
    L_0x0077:
        r7 = "ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x007f:
        r7 = 7;
        goto L_0x0100;
    L_0x0082:
        r7 = "REQUIRES_RECENT_LOGIN";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x008a:
        r7 = 6;
        goto L_0x0100;
    L_0x008d:
        r7 = "WRONG_PASSWORD";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x0095:
        r7 = 4;
        goto L_0x0100;
    L_0x0098:
        r7 = "WEAK_PASSWORD";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x00a0:
        r7 = 14;
        goto L_0x0100;
    L_0x00a3:
        r7 = "USER_MISMATCH";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x00ab:
        r7 = 5;
        goto L_0x0100;
    L_0x00ad:
        r7 = "USER_NOT_FOUND";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x00b5:
        r7 = 12;
        goto L_0x0100;
    L_0x00b8:
        r7 = "EMAIL_ALREADY_IN_USE";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x00c0:
        r7 = 8;
        goto L_0x0100;
    L_0x00c3:
        r9 = "CUSTOM_TOKEN_MISMATCH";
        r9 = r6.equals(r9);
        if (r9 == 0) goto L_0x00ff;
    L_0x00cb:
        goto L_0x0100;
    L_0x00cc:
        r7 = "OPERATION_NOT_ALLOWED";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x00d4:
        r7 = 15;
        goto L_0x0100;
    L_0x00d7:
        r7 = "CREDENTIAL_ALREADY_IN_USE";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x00df:
        r7 = 9;
        goto L_0x0100;
    L_0x00e2:
        r7 = r6.equals(r2);
        if (r7 == 0) goto L_0x00ff;
    L_0x00e8:
        r7 = 3;
        goto L_0x0100;
    L_0x00ea:
        r7 = "INVALID_CREDENTIAL";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x00f2:
        r7 = 2;
        goto L_0x0100;
    L_0x00f4:
        r7 = "USER_TOKEN_EXPIRED";
        r7 = r6.equals(r7);
        if (r7 == 0) goto L_0x00ff;
    L_0x00fc:
        r7 = 11;
        goto L_0x0100;
    L_0x00ff:
        r7 = -1;
    L_0x0100:
        switch(r7) {
            case 0: goto L_0x012d;
            case 1: goto L_0x012a;
            case 2: goto L_0x0127;
            case 3: goto L_0x0125;
            case 4: goto L_0x0122;
            case 5: goto L_0x011f;
            case 6: goto L_0x011c;
            case 7: goto L_0x0119;
            case 8: goto L_0x0116;
            case 9: goto L_0x0113;
            case 10: goto L_0x0110;
            case 11: goto L_0x0132;
            case 12: goto L_0x010d;
            case 13: goto L_0x0132;
            case 14: goto L_0x010a;
            case 15: goto L_0x0107;
            case 16: goto L_0x0105;
            default: goto L_0x0103;
        };
    L_0x0103:
        r0 = r4;
        goto L_0x0132;
    L_0x0105:
        r6 = r2;
        goto L_0x0125;
    L_0x0107:
        r0 = "This operation is not allowed. You must enable this service in the console.";
        goto L_0x0132;
    L_0x010a:
        r0 = "The given password is invalid.";
        goto L_0x0132;
    L_0x010d:
        r0 = "There is no user record corresponding to this identifier. The user may have been deleted.";
        goto L_0x0132;
    L_0x0110:
        r0 = "The user account has been disabled by an administrator.";
        goto L_0x0132;
    L_0x0113:
        r0 = "This credential is already associated with a different user account.";
        goto L_0x0132;
    L_0x0116:
        r0 = "The email address is already in use by another account.";
        goto L_0x0132;
    L_0x0119:
        r0 = "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.";
        goto L_0x0132;
    L_0x011c:
        r0 = "This operation is sensitive and requires recent authentication. Log in again before retrying this request.";
        goto L_0x0132;
    L_0x011f:
        r0 = "The supplied credentials do not correspond to the previously signed in user.";
        goto L_0x0132;
    L_0x0122:
        r0 = "The password is invalid or the user does not have a password.";
        goto L_0x0132;
    L_0x0125:
        r0 = r5;
        goto L_0x0132;
    L_0x0127:
        r0 = "The supplied auth credential is malformed or has expired.";
        goto L_0x0132;
    L_0x012a:
        r0 = "The custom token corresponds to a different audience.";
        goto L_0x0132;
    L_0x012d:
        r0 = "The custom token format is incorrect. Please check the documentation.";
        goto L_0x0132;
    L_0x0130:
        r0 = r4;
    L_0x0131:
        r6 = r7;
    L_0x0132:
        r1 = r6.equals(r1);
        if (r1 == 0) goto L_0x013e;
    L_0x0138:
        r1 = r11 instanceof com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
        if (r1 == 0) goto L_0x013e;
    L_0x013c:
        r0 = r5;
        goto L_0x013f;
    L_0x013e:
        r2 = r6;
    L_0x013f:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r4 = "auth/";
        r1.append(r4);
        r2 = r2.toLowerCase();
        r4 = "error_";
        r5 = "";
        r2 = r2.replace(r4, r5);
        r4 = 95;
        r5 = 45;
        r2 = r2.replace(r4, r5);
        r1.append(r2);
        r1 = r1.toString();
        r2 = "code";
        r3.putString(r2, r1);
        r1 = "message";
        r3.putString(r1, r0);
        r11 = r11.getMessage();
        r0 = "nativeErrorMessage";
        r3.putString(r0, r11);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.auth.RNFirebaseAuth.getJSError(java.lang.Exception):com.facebook.react.bridge.WritableMap");
    }

    private WritableArray convertProviderData(List<? extends UserInfo> list, FirebaseUser firebaseUser) {
        WritableArray createArray = Arguments.createArray();
        for (UserInfo userInfo : list) {
            if (!FirebaseAuthProvider.PROVIDER_ID.equals(userInfo.getProviderId())) {
                WritableMap createMap = Arguments.createMap();
                createMap.putString("providerId", userInfo.getProviderId());
                createMap.putString("uid", userInfo.getUid());
                createMap.putString("displayName", userInfo.getDisplayName());
                Uri photoUrl = userInfo.getPhotoUrl();
                String str = "photoURL";
                String str2 = "";
                if (photoUrl == null || str2.equals(photoUrl.toString())) {
                    createMap.putNull(str);
                } else {
                    createMap.putString(str, photoUrl.toString());
                }
                String phoneNumber = userInfo.getPhoneNumber();
                String str3 = "phoneNumber";
                if ("phone".equals(userInfo.getProviderId()) && (userInfo.getPhoneNumber() == null || str2.equals(userInfo.getPhoneNumber()))) {
                    createMap.putString(str3, firebaseUser.getPhoneNumber());
                } else if (phoneNumber == null || str2.equals(phoneNumber)) {
                    createMap.putNull(str3);
                } else {
                    createMap.putString(str3, phoneNumber);
                }
                str = "email";
                if ("password".equals(userInfo.getProviderId()) && (userInfo.getEmail() == null || str2.equals(userInfo.getEmail()))) {
                    createMap.putString(str, userInfo.getUid());
                } else if (userInfo.getEmail() == null || str2.equals(userInfo.getEmail())) {
                    createMap.putNull(str);
                } else {
                    createMap.putString(str, userInfo.getEmail());
                }
                createArray.pushMap(createMap);
            }
        }
        return createArray;
    }

    private WritableMap firebaseUserToMap(FirebaseUser firebaseUser) {
        WritableMap createMap = Arguments.createMap();
        String uid = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        Uri photoUrl = firebaseUser.getPhotoUrl();
        String displayName = firebaseUser.getDisplayName();
        String providerId = firebaseUser.getProviderId();
        Boolean valueOf = Boolean.valueOf(firebaseUser.isEmailVerified());
        String phoneNumber = firebaseUser.getPhoneNumber();
        createMap.putString("uid", uid);
        createMap.putString("providerId", providerId);
        createMap.putBoolean("emailVerified", valueOf.booleanValue());
        createMap.putBoolean("isAnonymous", firebaseUser.isAnonymous());
        uid = "email";
        providerId = "";
        if (email == null || providerId.equals(email)) {
            createMap.putNull(uid);
        } else {
            createMap.putString(uid, email);
        }
        uid = "displayName";
        if (displayName == null || providerId.equals(displayName)) {
            createMap.putNull(uid);
        } else {
            createMap.putString(uid, displayName);
        }
        uid = "photoURL";
        if (photoUrl == null || providerId.equals(photoUrl.toString())) {
            createMap.putNull(uid);
        } else {
            createMap.putString(uid, photoUrl.toString());
        }
        uid = "phoneNumber";
        if (phoneNumber == null || providerId.equals(phoneNumber)) {
            createMap.putNull(uid);
        } else {
            createMap.putString(uid, phoneNumber);
        }
        createMap.putArray("providerData", convertProviderData(firebaseUser.getProviderData(), firebaseUser));
        WritableMap createMap2 = Arguments.createMap();
        FirebaseUserMetadata metadata = firebaseUser.getMetadata();
        if (metadata != null) {
            createMap2.putDouble("creationTime", (double) metadata.getCreationTimestamp());
            createMap2.putDouble("lastSignInTime", (double) metadata.getLastSignInTimestamp());
        }
        createMap.putMap(ReactVideoView.EVENT_PROP_METADATA, createMap2);
        return createMap;
    }

    private ActionCodeSettings buildActionCodeSettings(ReadableMap readableMap) {
        ActionCodeSettings.Builder newBuilder = ActionCodeSettings.newBuilder();
        ReadableMap map = readableMap.getMap("android");
        ReadableMap map2 = readableMap.getMap("iOS");
        String string = readableMap.getString(ImagesContract.URL);
        if (map != null) {
            String str = "installApp";
            boolean z = map.hasKey(str) && map.getBoolean(str);
            String str2 = "minimumVersion";
            newBuilder = newBuilder.setAndroidPackageName(map.getString("packageName"), z, map.hasKey(str2) ? map.getString(str2) : null);
        }
        String str3 = "handleCodeInApp";
        if (readableMap.hasKey(str3)) {
            newBuilder = newBuilder.setHandleCodeInApp(readableMap.getBoolean(str3));
        }
        if (map2 != null) {
            String str4 = "bundleId";
            if (map2.hasKey(str4)) {
                newBuilder = newBuilder.setIOSBundleId(map2.getString(str4));
            }
        }
        if (string != null) {
            newBuilder = newBuilder.setUrl(string);
        }
        return newBuilder.build();
    }

    private void sendPhoneStateEvent(String str, String str2, String str3, WritableMap writableMap) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("appName", str);
        createMap.putString("requestKey", str2);
        createMap.putString("type", str3);
        createMap.putMap("state", writableMap);
        Utils.sendEvent(this.mReactContext, "phone_auth_state_changed", createMap);
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> hashMap = new HashMap();
        List<FirebaseApp> apps = FirebaseApp.getApps(access$700());
        Map hashMap2 = new HashMap();
        Map hashMap3 = new HashMap();
        for (FirebaseApp name : apps) {
            String name2 = name.getName();
            FirebaseAuth instance = FirebaseAuth.getInstance(FirebaseApp.getInstance(name2));
            FirebaseUser currentUser = instance.getCurrentUser();
            hashMap2.put(name2, instance.getLanguageCode());
            if (currentUser != null) {
                hashMap3.put(name2, firebaseUserToMap(currentUser));
            }
        }
        hashMap.put("APP_LANGUAGE", hashMap2);
        hashMap.put("APP_USER", hashMap3);
        return hashMap;
    }
}
