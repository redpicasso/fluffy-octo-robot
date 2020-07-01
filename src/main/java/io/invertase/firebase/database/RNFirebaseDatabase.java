package io.invertase.firebase.database;

import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import androidx.core.os.EnvironmentCompat;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger.Level;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;
import io.invertase.firebase.ErrorUtils;
import io.invertase.firebase.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nonnull;

public class RNFirebaseDatabase extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFirebaseDatabase";
    private static boolean enableLogging = false;
    private static HashMap<String, Boolean> loggingLevelSet = new HashMap();
    private static ReactApplicationContext reactApplicationContext;
    private static HashMap<String, RNFirebaseDatabaseReference> references = new HashMap();
    private static SparseArray<RNFirebaseTransactionHandler> transactionHandlers = new SparseArray();

    public String getName() {
        return TAG;
    }

    RNFirebaseDatabase(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    static ReactApplicationContext getReactApplicationContextInstance() {
        return reactApplicationContext;
    }

    static void handlePromise(Promise promise, DatabaseError databaseError) {
        if (databaseError != null) {
            WritableMap jSError = getJSError(databaseError);
            promise.reject(jSError.getString("code"), jSError.getString("message"), databaseError.toException());
            return;
        }
        promise.resolve(null);
    }

    static FirebaseDatabase getDatabaseForApp(String str, String str2) {
        FirebaseDatabase instance;
        StringBuilder stringBuilder;
        if (str2 == null || str2.length() <= 0) {
            instance = FirebaseDatabase.getInstance(FirebaseApp.getInstance(str));
        } else if (str == null || str.length() <= 0) {
            instance = FirebaseDatabase.getInstance(str2);
        } else {
            instance = FirebaseDatabase.getInstance(FirebaseApp.getInstance(str), str2);
        }
        Boolean bool = (Boolean) loggingLevelSet.get(instance.getApp().getName());
        boolean z = enableLogging;
        String str3 = "WARNING: enableLogging(bool) must be called before any other use of database(). \nIf you are sure you've done this then this message can be ignored during development as \nRN reloads can cause false positives. APP: ";
        String str4 = TAG;
        if (z && (bool == null || !bool.booleanValue())) {
            try {
                loggingLevelSet.put(instance.getApp().getName(), Boolean.valueOf(enableLogging));
                instance.setLogLevel(Level.DEBUG);
            } catch (DatabaseException unused) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str3);
                stringBuilder.append(instance.getApp().getName());
                Log.w(str4, stringBuilder.toString());
            }
        } else if (!(enableLogging || bool == null || !bool.booleanValue())) {
            try {
                loggingLevelSet.put(instance.getApp().getName(), Boolean.valueOf(enableLogging));
                instance.setLogLevel(Level.WARN);
            } catch (DatabaseException unused2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str3);
                stringBuilder.append(instance.getApp().getName());
                Log.w(str4, stringBuilder.toString());
            }
        }
        return instance;
    }

    static WritableMap getJSError(DatabaseError databaseError) {
        String codeWithService;
        String messageWithService;
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("nativeErrorCode", databaseError.getCode());
        createMap.putString("nativeErrorMessage", databaseError.getMessage());
        int code = databaseError.getCode();
        String str = "Database";
        if (code == -25) {
            codeWithService = ErrorUtils.getCodeWithService(str, "write-cancelled");
            messageWithService = ErrorUtils.getMessageWithService("The write was canceled by the user.", str, codeWithService);
        } else if (code == -24) {
            codeWithService = ErrorUtils.getCodeWithService(str, "network-error");
            messageWithService = ErrorUtils.getMessageWithService("The operation could not be performed due to a network error.", str, codeWithService);
        } else if (code == -4) {
            codeWithService = ErrorUtils.getCodeWithService(str, "disconnected");
            messageWithService = ErrorUtils.getMessageWithService("The operation had to be aborted due to a network disconnect.", str, codeWithService);
        } else if (code == -3) {
            codeWithService = ErrorUtils.getCodeWithService(str, "permission-denied");
            messageWithService = ErrorUtils.getMessageWithService("Client doesn't have permission to access the desired data.", str, codeWithService);
        } else if (code == -2) {
            codeWithService = ErrorUtils.getCodeWithService(str, "failure");
            messageWithService = ErrorUtils.getMessageWithService("The server indicated that this operation failed.", str, codeWithService);
        } else if (code != -1) {
            switch (code) {
                case DatabaseError.USER_CODE_EXCEPTION /*-11*/:
                    codeWithService = ErrorUtils.getCodeWithService(str, "user-code-exception");
                    messageWithService = ErrorUtils.getMessageWithService("User code called from the Firebase Database runloop threw an exception.", str, codeWithService);
                    break;
                case DatabaseError.UNAVAILABLE /*-10*/:
                    codeWithService = ErrorUtils.getCodeWithService(str, "unavailable");
                    messageWithService = ErrorUtils.getMessageWithService("The service is unavailable.", str, codeWithService);
                    break;
                case DatabaseError.OVERRIDDEN_BY_SET /*-9*/:
                    codeWithService = ErrorUtils.getCodeWithService(str, "overridden-by-set");
                    messageWithService = ErrorUtils.getMessageWithService("The transaction was overridden by a subsequent set.", str, codeWithService);
                    break;
                case DatabaseError.MAX_RETRIES /*-8*/:
                    codeWithService = ErrorUtils.getCodeWithService(str, "max-retries");
                    messageWithService = ErrorUtils.getMessageWithService("The transaction had too many retries.", str, codeWithService);
                    break;
                case DatabaseError.INVALID_TOKEN /*-7*/:
                    codeWithService = ErrorUtils.getCodeWithService(str, "invalid-token");
                    messageWithService = ErrorUtils.getMessageWithService("The supplied auth token was invalid.", str, codeWithService);
                    break;
                case DatabaseError.EXPIRED_TOKEN /*-6*/:
                    codeWithService = ErrorUtils.getCodeWithService(str, "expired-token");
                    messageWithService = ErrorUtils.getMessageWithService("The supplied auth token has expired.", str, codeWithService);
                    break;
                default:
                    codeWithService = ErrorUtils.getCodeWithService(str, EnvironmentCompat.MEDIA_UNKNOWN);
                    messageWithService = ErrorUtils.getMessageWithService("An unknown error occurred.", str, codeWithService);
                    break;
            }
        } else {
            codeWithService = ErrorUtils.getCodeWithService(str, "data-stale");
            messageWithService = ErrorUtils.getMessageWithService("The transaction needs to be run again with current data.", str, codeWithService);
        }
        createMap.putString("code", codeWithService);
        createMap.putString("message", messageWithService);
        return createMap;
    }

    public void initialize() {
        super.initialize();
        Log.d(TAG, "RNFirebaseDatabase:initialized");
        reactApplicationContext = access$700();
    }

    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        Iterator it = references.entrySet().iterator();
        while (it.hasNext()) {
            ((RNFirebaseDatabaseReference) ((Entry) it.next()).getValue()).removeAllEventListeners();
            it.remove();
        }
    }

    @ReactMethod
    public void goOnline(String str, String str2) {
        getDatabaseForApp(str, str2).goOnline();
    }

    @ReactMethod
    public void goOffline(String str, String str2) {
        getDatabaseForApp(str, str2).goOffline();
    }

    @ReactMethod
    public void setPersistence(String str, String str2, Boolean bool) {
        getDatabaseForApp(str, str2).setPersistenceEnabled(bool.booleanValue());
    }

    @ReactMethod
    public void setPersistenceCacheSizeBytes(String str, String str2, int i) {
        getDatabaseForApp(str, str2).setPersistenceCacheSizeBytes((long) i);
    }

    @ReactMethod
    public void enableLogging(Boolean bool) {
        enableLogging = bool.booleanValue();
        for (FirebaseApp firebaseApp : FirebaseApp.getApps(access$700())) {
            loggingLevelSet.put(firebaseApp.getName(), bool);
            try {
                if (enableLogging) {
                    FirebaseDatabase.getInstance(firebaseApp).setLogLevel(Level.DEBUG);
                } else {
                    FirebaseDatabase.getInstance(firebaseApp).setLogLevel(Level.WARN);
                }
            } catch (DatabaseException unused) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("WARNING: enableLogging(bool) must be called before any other use of database(). \nIf you are sure you've done this then this message can be ignored during development as \nRN reloads can cause false positives. APP: ");
                stringBuilder.append(firebaseApp.getName());
                Log.w(TAG, stringBuilder.toString());
            }
        }
    }

    @ReactMethod
    public void keepSynced(String str, String str2, String str3, String str4, ReadableArray readableArray, Boolean bool) {
        getInternalReferenceForApp(str, str2, str3, str4, readableArray).getQuery().keepSynced(bool.booleanValue());
    }

    @ReactMethod
    public void transactionTryCommit(String str, String str2, int i, ReadableMap readableMap) {
        RNFirebaseTransactionHandler rNFirebaseTransactionHandler = (RNFirebaseTransactionHandler) transactionHandlers.get(i);
        if (rNFirebaseTransactionHandler != null) {
            rNFirebaseTransactionHandler.signalUpdateReceived(readableMap);
        }
    }

    @ReactMethod
    public void transactionStart(String str, String str2, String str3, int i, Boolean bool) {
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final int i2 = i;
        final Boolean bool2 = bool;
        AsyncTask.execute(new Runnable() {
            public void run() {
                RNFirebaseDatabase.this.getReferenceForAppPath(str4, str5, str6).runTransaction(new Handler() {
                    @Nonnull
                    public Result doTransaction(@Nonnull MutableData mutableData) {
                        RNFirebaseTransactionHandler rNFirebaseTransactionHandler = new RNFirebaseTransactionHandler(i2, str4, str5);
                        RNFirebaseDatabase.transactionHandlers.put(i2, rNFirebaseTransactionHandler);
                        final WritableMap createUpdateMap = rNFirebaseTransactionHandler.createUpdateMap(mutableData);
                        AsyncTask.execute(new Runnable() {
                            public void run() {
                                Utils.sendEvent(RNFirebaseDatabase.this.access$700(), "database_transaction_event", createUpdateMap);
                            }
                        });
                        try {
                            rNFirebaseTransactionHandler.await();
                            if (rNFirebaseTransactionHandler.abort) {
                                return Transaction.abort();
                            }
                            if (rNFirebaseTransactionHandler.timeout) {
                                return Transaction.abort();
                            }
                            mutableData.setValue(rNFirebaseTransactionHandler.value);
                            return Transaction.success(mutableData);
                        } catch (InterruptedException unused) {
                            rNFirebaseTransactionHandler.interrupted = true;
                            return Transaction.abort();
                        }
                    }

                    public void onComplete(DatabaseError databaseError, boolean z, DataSnapshot dataSnapshot) {
                        Utils.sendEvent(RNFirebaseDatabase.this.access$700(), "database_transaction_event", ((RNFirebaseTransactionHandler) RNFirebaseDatabase.transactionHandlers.get(i2)).createResultMap(databaseError, z, dataSnapshot));
                        RNFirebaseDatabase.transactionHandlers.delete(i2);
                    }
                }, bool2.booleanValue());
            }
        });
    }

    @com.facebook.react.bridge.ReactMethod
    public void onDisconnectSet(java.lang.String r6, java.lang.String r7, java.lang.String r8, com.facebook.react.bridge.ReadableMap r9, final com.facebook.react.bridge.Promise r10) {
        /*
        r5 = this;
        r0 = "type";
        r0 = r9.getString(r0);
        r6 = r5.getReferenceForAppPath(r6, r7, r8);
        r6 = r6.onDisconnect();
        r7 = new io.invertase.firebase.database.RNFirebaseDatabase$2;
        r7.<init>(r10);
        r8 = r0.hashCode();
        r10 = 5;
        r1 = 4;
        r2 = 3;
        r3 = 2;
        r4 = 1;
        switch(r8) {
            case -1034364087: goto L_0x0052;
            case -1023368385: goto L_0x0048;
            case -891985903: goto L_0x003e;
            case 3392903: goto L_0x0034;
            case 64711720: goto L_0x002a;
            case 93090393: goto L_0x0020;
            default: goto L_0x001f;
        };
    L_0x001f:
        goto L_0x005c;
    L_0x0020:
        r8 = "array";
        r8 = r0.equals(r8);
        if (r8 == 0) goto L_0x005c;
    L_0x0028:
        r8 = 1;
        goto L_0x005d;
    L_0x002a:
        r8 = "boolean";
        r8 = r0.equals(r8);
        if (r8 == 0) goto L_0x005c;
    L_0x0032:
        r8 = 4;
        goto L_0x005d;
    L_0x0034:
        r8 = "null";
        r8 = r0.equals(r8);
        if (r8 == 0) goto L_0x005c;
    L_0x003c:
        r8 = 5;
        goto L_0x005d;
    L_0x003e:
        r8 = "string";
        r8 = r0.equals(r8);
        if (r8 == 0) goto L_0x005c;
    L_0x0046:
        r8 = 2;
        goto L_0x005d;
    L_0x0048:
        r8 = "object";
        r8 = r0.equals(r8);
        if (r8 == 0) goto L_0x005c;
    L_0x0050:
        r8 = 0;
        goto L_0x005d;
    L_0x0052:
        r8 = "number";
        r8 = r0.equals(r8);
        if (r8 == 0) goto L_0x005c;
    L_0x005a:
        r8 = 3;
        goto L_0x005d;
    L_0x005c:
        r8 = -1;
    L_0x005d:
        r0 = "value";
        if (r8 == 0) goto L_0x009d;
    L_0x0061:
        if (r8 == r4) goto L_0x0091;
    L_0x0063:
        if (r8 == r3) goto L_0x0089;
    L_0x0065:
        if (r8 == r2) goto L_0x007d;
    L_0x0067:
        if (r8 == r1) goto L_0x0071;
    L_0x0069:
        if (r8 == r10) goto L_0x006c;
    L_0x006b:
        goto L_0x00a8;
    L_0x006c:
        r8 = 0;
        r6.setValue(r8, r7);
        goto L_0x00a8;
    L_0x0071:
        r8 = r9.getBoolean(r0);
        r8 = java.lang.Boolean.valueOf(r8);
        r6.setValue(r8, r7);
        goto L_0x00a8;
    L_0x007d:
        r8 = r9.getDouble(r0);
        r8 = java.lang.Double.valueOf(r8);
        r6.setValue(r8, r7);
        goto L_0x00a8;
    L_0x0089:
        r8 = r9.getString(r0);
        r6.setValue(r8, r7);
        goto L_0x00a8;
    L_0x0091:
        r8 = r9.getArray(r0);
        r8 = io.invertase.firebase.Utils.recursivelyDeconstructReadableArray(r8);
        r6.setValue(r8, r7);
        goto L_0x00a8;
    L_0x009d:
        r8 = r9.getMap(r0);
        r8 = io.invertase.firebase.Utils.recursivelyDeconstructReadableMap(r8);
        r6.setValue(r8, r7);
    L_0x00a8:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.database.RNFirebaseDatabase.onDisconnectSet(java.lang.String, java.lang.String, java.lang.String, com.facebook.react.bridge.ReadableMap, com.facebook.react.bridge.Promise):void");
    }

    @ReactMethod
    public void onDisconnectUpdate(String str, String str2, String str3, ReadableMap readableMap, final Promise promise) {
        getReferenceForAppPath(str, str2, str3).onDisconnect().updateChildren(Utils.recursivelyDeconstructReadableMap(readableMap), new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                RNFirebaseDatabase.handlePromise(promise, databaseError);
            }
        });
    }

    @ReactMethod
    public void onDisconnectRemove(String str, String str2, String str3, final Promise promise) {
        getReferenceForAppPath(str, str2, str3).onDisconnect().removeValue(new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                RNFirebaseDatabase.handlePromise(promise, databaseError);
            }
        });
    }

    @ReactMethod
    public void onDisconnectCancel(String str, String str2, String str3, final Promise promise) {
        getReferenceForAppPath(str, str2, str3).onDisconnect().cancel(new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                RNFirebaseDatabase.handlePromise(promise, databaseError);
            }
        });
    }

    @ReactMethod
    public void set(String str, String str2, String str3, ReadableMap readableMap, final Promise promise) {
        getReferenceForAppPath(str, str2, str3).setValue(Utils.recursivelyDeconstructReadableMap(readableMap).get("value"), new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                RNFirebaseDatabase.handlePromise(promise, databaseError);
            }
        });
    }

    @ReactMethod
    public void setPriority(String str, String str2, String str3, ReadableMap readableMap, final Promise promise) {
        getReferenceForAppPath(str, str2, str3).setPriority(Utils.recursivelyDeconstructReadableMap(readableMap).get("value"), new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                RNFirebaseDatabase.handlePromise(promise, databaseError);
            }
        });
    }

    @ReactMethod
    public void setWithPriority(String str, String str2, String str3, ReadableMap readableMap, ReadableMap readableMap2, final Promise promise) {
        DatabaseReference referenceForAppPath = getReferenceForAppPath(str, str2, str3);
        str3 = "value";
        referenceForAppPath.setValue(Utils.recursivelyDeconstructReadableMap(readableMap).get(str3), Utils.recursivelyDeconstructReadableMap(readableMap2).get(str3), new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                RNFirebaseDatabase.handlePromise(promise, databaseError);
            }
        });
    }

    @ReactMethod
    public void update(String str, String str2, String str3, ReadableMap readableMap, final Promise promise) {
        getReferenceForAppPath(str, str2, str3).updateChildren(Utils.recursivelyDeconstructReadableMap(readableMap), new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                RNFirebaseDatabase.handlePromise(promise, databaseError);
            }
        });
    }

    @ReactMethod
    public void remove(String str, String str2, String str3, final Promise promise) {
        getReferenceForAppPath(str, str2, str3).removeValue(new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                RNFirebaseDatabase.handlePromise(promise, databaseError);
            }
        });
    }

    @ReactMethod
    public void once(String str, String str2, String str3, String str4, ReadableArray readableArray, String str5, Promise promise) {
        getInternalReferenceForApp(str, str2, str3, str4, readableArray).once(str5, promise);
    }

    @ReactMethod
    public void on(String str, String str2, ReadableMap readableMap) {
        getCachedInternalReferenceForApp(str, str2, readableMap).on(readableMap.getString("eventType"), readableMap.getMap("registration"));
    }

    @ReactMethod
    public void off(String str, String str2) {
        RNFirebaseDatabaseReference rNFirebaseDatabaseReference = (RNFirebaseDatabaseReference) references.get(str);
        if (rNFirebaseDatabaseReference != null) {
            rNFirebaseDatabaseReference.removeEventListener(str2);
            if (!rNFirebaseDatabaseReference.hasListeners().booleanValue()) {
                references.remove(str);
            }
        }
    }

    private DatabaseReference getReferenceForAppPath(String str, String str2, String str3) {
        return getDatabaseForApp(str, str2).getReference(str3);
    }

    private RNFirebaseDatabaseReference getInternalReferenceForApp(String str, String str2, String str3, String str4, ReadableArray readableArray) {
        return new RNFirebaseDatabaseReference(str, str2, str3, str4, readableArray);
    }

    private RNFirebaseDatabaseReference getCachedInternalReferenceForApp(String str, String str2, ReadableMap readableMap) {
        String string = readableMap.getString("key");
        String string2 = readableMap.getString(RNFetchBlobConst.RNFB_RESPONSE_PATH);
        ReadableArray array = readableMap.getArray("modifiers");
        RNFirebaseDatabaseReference rNFirebaseDatabaseReference = (RNFirebaseDatabaseReference) references.get(string);
        if (rNFirebaseDatabaseReference != null) {
            return rNFirebaseDatabaseReference;
        }
        rNFirebaseDatabaseReference = getInternalReferenceForApp(str, str2, string, string2, array);
        references.put(string, rNFirebaseDatabaseReference);
        return rNFirebaseDatabaseReference;
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("serverValueTimestamp", ServerValue.TIMESTAMP);
        return hashMap;
    }
}
