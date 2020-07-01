package io.invertase.firebase.firestore;

import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import androidx.core.os.EnvironmentCompat;
import com.RNFetchBlob.RNFetchBlobConst;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreException.Code;
import com.google.firebase.firestore.FirebaseFirestoreSettings.Builder;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.Transaction.Function;
import com.google.firebase.firestore.WriteBatch;
import io.invertase.firebase.ErrorUtils;
import io.invertase.firebase.Utils;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

public class RNFirebaseFirestore extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFirebaseFirestore";
    private SparseArray<RNFirebaseFirestoreTransactionHandler> transactionHandlers = new SparseArray();

    /* renamed from: io.invertase.firebase.firestore.RNFirebaseFirestore$5 */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code = new int[Code.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:35:0x00ce, code:
            return;
     */
        static {
            /*
            r0 = com.google.firebase.firestore.FirebaseFirestoreException.Code.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code = r0;
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.OK;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.CANCELLED;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.UNKNOWN;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.INVALID_ARGUMENT;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.DEADLINE_EXCEEDED;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r2 = 5;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x004b }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.NOT_FOUND;	 Catch:{ NoSuchFieldError -> 0x004b }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x004b }
            r2 = 6;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x004b }
        L_0x004b:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x0056 }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.ALREADY_EXISTS;	 Catch:{ NoSuchFieldError -> 0x0056 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0056 }
            r2 = 7;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0056 }
        L_0x0056:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x0062 }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.PERMISSION_DENIED;	 Catch:{ NoSuchFieldError -> 0x0062 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0062 }
            r2 = 8;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0062 }
        L_0x0062:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x006e }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED;	 Catch:{ NoSuchFieldError -> 0x006e }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x006e }
            r2 = 9;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x006e }
        L_0x006e:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x007a }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.FAILED_PRECONDITION;	 Catch:{ NoSuchFieldError -> 0x007a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x007a }
            r2 = 10;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x007a }
        L_0x007a:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x0086 }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.ABORTED;	 Catch:{ NoSuchFieldError -> 0x0086 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0086 }
            r2 = 11;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0086 }
        L_0x0086:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x0092 }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.OUT_OF_RANGE;	 Catch:{ NoSuchFieldError -> 0x0092 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0092 }
            r2 = 12;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0092 }
        L_0x0092:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x009e }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.UNIMPLEMENTED;	 Catch:{ NoSuchFieldError -> 0x009e }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x009e }
            r2 = 13;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x009e }
        L_0x009e:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x00aa }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.INTERNAL;	 Catch:{ NoSuchFieldError -> 0x00aa }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x00aa }
            r2 = 14;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00aa }
        L_0x00aa:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x00b6 }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.UNAVAILABLE;	 Catch:{ NoSuchFieldError -> 0x00b6 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x00b6 }
            r2 = 15;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00b6 }
        L_0x00b6:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x00c2 }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.DATA_LOSS;	 Catch:{ NoSuchFieldError -> 0x00c2 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x00c2 }
            r2 = 16;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00c2 }
        L_0x00c2:
            r0 = $SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code;	 Catch:{ NoSuchFieldError -> 0x00ce }
            r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.UNAUTHENTICATED;	 Catch:{ NoSuchFieldError -> 0x00ce }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x00ce }
            r2 = 17;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00ce }
        L_0x00ce:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.firestore.RNFirebaseFirestore.5.<clinit>():void");
        }
    }

    public String getName() {
        return TAG;
    }

    RNFirebaseFirestore(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    static void promiseRejectException(Promise promise, FirebaseFirestoreException firebaseFirestoreException) {
        WritableMap jSError = getJSError(firebaseFirestoreException);
        promise.reject(jSError.getString("code"), jSError.getString("message"), (Throwable) firebaseFirestoreException);
    }

    static FirebaseFirestore getFirestoreForApp(String str) {
        return FirebaseFirestore.getInstance(FirebaseApp.getInstance(str));
    }

    static WritableMap getJSError(FirebaseFirestoreException firebaseFirestoreException) {
        String codeWithService;
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("nativeErrorCode", firebaseFirestoreException.getCode().value());
        createMap.putString("nativeErrorMessage", firebaseFirestoreException.getMessage());
        int i = AnonymousClass5.$SwitchMap$com$google$firebase$firestore$FirebaseFirestoreException$Code[firebaseFirestoreException.getCode().ordinal()];
        String str = EnvironmentCompat.MEDIA_UNKNOWN;
        String str2 = "Firestore";
        switch (i) {
            case 1:
                codeWithService = ErrorUtils.getCodeWithService(str2, "ok");
                str = ErrorUtils.getMessageWithService("Ok.", str2, codeWithService);
                break;
            case 2:
                codeWithService = ErrorUtils.getCodeWithService(str2, "cancelled");
                str = ErrorUtils.getMessageWithService("The operation was cancelled.", str2, codeWithService);
                break;
            case 3:
                codeWithService = ErrorUtils.getCodeWithService(str2, str);
                str = ErrorUtils.getMessageWithService("Unknown error or an error from a different error domain.", str2, codeWithService);
                break;
            case 4:
                codeWithService = ErrorUtils.getCodeWithService(str2, "invalid-argument");
                str = ErrorUtils.getMessageWithService("Client specified an invalid argument.", str2, codeWithService);
                break;
            case 5:
                codeWithService = ErrorUtils.getCodeWithService(str2, "deadline-exceeded");
                str = ErrorUtils.getMessageWithService("Deadline expired before operation could complete.", str2, codeWithService);
                break;
            case 6:
                codeWithService = ErrorUtils.getCodeWithService(str2, "not-found");
                str = ErrorUtils.getMessageWithService("Some requested document was not found.", str2, codeWithService);
                break;
            case 7:
                codeWithService = ErrorUtils.getCodeWithService(str2, "already-exists");
                str = ErrorUtils.getMessageWithService("Some document that we attempted to create already exists.", str2, codeWithService);
                break;
            case 8:
                codeWithService = ErrorUtils.getCodeWithService(str2, "permission-denied");
                str = ErrorUtils.getMessageWithService("The caller does not have permission to execute the specified operation.", str2, codeWithService);
                break;
            case 9:
                codeWithService = ErrorUtils.getCodeWithService(str2, "resource-exhausted");
                str = ErrorUtils.getMessageWithService("Some resource has been exhausted, perhaps a per-user quota, or perhaps the entire file system is out of space.", str2, codeWithService);
                break;
            case 10:
                codeWithService = ErrorUtils.getCodeWithService(str2, "failed-precondition");
                str = ErrorUtils.getMessageWithService("Operation was rejected because the system is not in a state required for the operation`s execution.", str2, codeWithService);
                break;
            case 11:
                codeWithService = ErrorUtils.getCodeWithService(str2, "aborted");
                str = ErrorUtils.getMessageWithService("The operation was aborted, typically due to a concurrency issue like transaction aborts, etc.", str2, codeWithService);
                break;
            case 12:
                codeWithService = ErrorUtils.getCodeWithService(str2, "out-of-range");
                str = ErrorUtils.getMessageWithService("Operation was attempted past the valid range.", str2, codeWithService);
                break;
            case 13:
                codeWithService = ErrorUtils.getCodeWithService(str2, "unimplemented");
                str = ErrorUtils.getMessageWithService("Operation is not implemented or not supported/enabled.", str2, codeWithService);
                break;
            case 14:
                codeWithService = ErrorUtils.getCodeWithService(str2, "internal");
                str = ErrorUtils.getMessageWithService("Internal errors.", str2, codeWithService);
                break;
            case 15:
                codeWithService = ErrorUtils.getCodeWithService(str2, "unavailable");
                str = ErrorUtils.getMessageWithService("The service is currently unavailable.", str2, codeWithService);
                break;
            case 16:
                codeWithService = ErrorUtils.getCodeWithService(str2, "data-loss");
                str = ErrorUtils.getMessageWithService("Unrecoverable data loss or corruption.", str2, codeWithService);
                break;
            case 17:
                codeWithService = ErrorUtils.getCodeWithService(str2, "unauthenticated");
                str = ErrorUtils.getMessageWithService("The request does not have valid authentication credentials for the operation.", str2, codeWithService);
                break;
            default:
                codeWithService = ErrorUtils.getCodeWithService(str2, str);
                str = ErrorUtils.getMessageWithService("An unknown error occurred.", str2, codeWithService);
                break;
        }
        createMap.putString("code", codeWithService);
        createMap.putString("message", str);
        return createMap;
    }

    @ReactMethod
    public void disableNetwork(String str, final Promise promise) {
        getFirestoreForApp(str).disableNetwork().addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseFirestore.TAG;
                if (isSuccessful) {
                    Log.d(str, "disableNetwork:onComplete:success");
                    promise.resolve(null);
                    return;
                }
                Log.e(str, "disableNetwork:onComplete:failure", task.getException());
                RNFirebaseFirestore.promiseRejectException(promise, (FirebaseFirestoreException) task.getException());
            }
        });
    }

    @ReactMethod
    public void setLogLevel(String str) {
        if ("debug".equals(str) || ReactVideoView.EVENT_PROP_ERROR.equals(str)) {
            FirebaseFirestore.setLoggingEnabled(true);
        } else {
            FirebaseFirestore.setLoggingEnabled(false);
        }
    }

    @ReactMethod
    public void enableNetwork(String str, final Promise promise) {
        getFirestoreForApp(str).enableNetwork().addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseFirestore.TAG;
                if (isSuccessful) {
                    Log.d(str, "enableNetwork:onComplete:success");
                    promise.resolve(null);
                    return;
                }
                Log.e(str, "enableNetwork:onComplete:failure", task.getException());
                RNFirebaseFirestore.promiseRejectException(promise, (FirebaseFirestoreException) task.getException());
            }
        });
    }

    @ReactMethod
    public void collectionGet(String str, String str2, ReadableArray readableArray, ReadableArray readableArray2, ReadableMap readableMap, ReadableMap readableMap2, Promise promise) {
        getCollectionForAppPath(str, str2, readableArray, readableArray2, readableMap).get(readableMap2, promise);
    }

    @ReactMethod
    public void collectionOffSnapshot(String str, String str2, ReadableArray readableArray, ReadableArray readableArray2, ReadableMap readableMap, String str3) {
        RNFirebaseFirestoreCollectionReference.offSnapshot(str3);
    }

    @ReactMethod
    public void collectionOnSnapshot(String str, String str2, ReadableArray readableArray, ReadableArray readableArray2, ReadableMap readableMap, String str3, ReadableMap readableMap2) {
        getCollectionForAppPath(str, str2, readableArray, readableArray2, readableMap).onSnapshot(str3, readableMap2);
    }

    @ReactMethod
    public void documentBatch(String str, ReadableArray readableArray, final Promise promise) {
        FirebaseFirestore firestoreForApp = getFirestoreForApp(str);
        WriteBatch batch = firestoreForApp.batch();
        for (Map map : FirestoreSerialize.parseDocumentBatches(firestoreForApp, readableArray)) {
            Map map2;
            String str2 = (String) map2.get("type");
            Map map3 = (Map) map2.get("data");
            DocumentReference document = firestoreForApp.document((String) map2.get(RNFetchBlobConst.RNFB_RESPONSE_PATH));
            int i = -1;
            int hashCode = str2.hashCode();
            if (hashCode != -1785516855) {
                if (hashCode != 81986) {
                    if (hashCode == 2012838315 && str2.equals("DELETE")) {
                        i = 0;
                    }
                } else if (str2.equals("SET")) {
                    i = 1;
                }
            } else if (str2.equals("UPDATE")) {
                i = 2;
            }
            if (i == 0) {
                batch = batch.delete(document);
            } else if (i == 1) {
                map2 = (Map) map2.get("options");
                if (map2 != null) {
                    str2 = "merge";
                    if (map2.containsKey(str2) && ((Boolean) map2.get(str2)).booleanValue()) {
                        batch = batch.set(document, map3, SetOptions.merge());
                    }
                }
                batch = batch.set(document, map3);
            } else if (i == 2) {
                batch = batch.update(document, map3);
            }
        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseFirestore.TAG;
                if (isSuccessful) {
                    Log.d(str, "documentBatch:onComplete:success");
                    promise.resolve(null);
                    return;
                }
                Log.e(str, "documentBatch:onComplete:failure", task.getException());
                RNFirebaseFirestore.promiseRejectException(promise, (FirebaseFirestoreException) task.getException());
            }
        });
    }

    @ReactMethod
    public void documentDelete(String str, String str2, Promise promise) {
        getDocumentForAppPath(str, str2).delete(promise);
    }

    @ReactMethod
    public void documentGet(String str, String str2, ReadableMap readableMap, Promise promise) {
        getDocumentForAppPath(str, str2).get(readableMap, promise);
    }

    @ReactMethod
    public void documentOffSnapshot(String str, String str2, String str3) {
        RNFirebaseFirestoreDocumentReference.offSnapshot(str3);
    }

    @ReactMethod
    public void documentOnSnapshot(String str, String str2, String str3, ReadableMap readableMap) {
        getDocumentForAppPath(str, str2).onSnapshot(str3, readableMap);
    }

    @ReactMethod
    public void documentSet(String str, String str2, ReadableMap readableMap, ReadableMap readableMap2, Promise promise) {
        getDocumentForAppPath(str, str2).set(readableMap, readableMap2, promise);
    }

    @ReactMethod
    public void documentUpdate(String str, String str2, ReadableMap readableMap, Promise promise) {
        getDocumentForAppPath(str, str2).update(readableMap, promise);
    }

    @ReactMethod
    public void settings(String str, ReadableMap readableMap, Promise promise) {
        FirebaseFirestore firestoreForApp = getFirestoreForApp(str);
        Builder builder = new Builder();
        String str2 = "host";
        if (readableMap.hasKey(str2)) {
            builder.setHost(readableMap.getString(str2));
        } else {
            builder.setHost(firestoreForApp.getFirestoreSettings().getHost());
        }
        str2 = "persistence";
        if (readableMap.hasKey(str2)) {
            builder.setPersistenceEnabled(readableMap.getBoolean(str2));
        } else {
            builder.setPersistenceEnabled(firestoreForApp.getFirestoreSettings().isPersistenceEnabled());
        }
        str2 = "cacheSizeBytes";
        if (readableMap.hasKey(str2)) {
            int i = readableMap.getInt(str2);
            if (i == -1) {
                builder.setCacheSizeBytes(-1);
            } else {
                builder.setCacheSizeBytes((long) i);
            }
        } else {
            builder.setCacheSizeBytes(firestoreForApp.getFirestoreSettings().getCacheSizeBytes());
        }
        str2 = "ssl";
        if (readableMap.hasKey(str2)) {
            builder.setSslEnabled(readableMap.getBoolean(str2));
        } else {
            builder.setSslEnabled(firestoreForApp.getFirestoreSettings().isSslEnabled());
        }
        str2 = "timestampsInSnapshots";
        if (readableMap.hasKey(str2)) {
            builder.setTimestampsInSnapshotsEnabled(readableMap.getBoolean(str2));
        }
        firestoreForApp.setFirestoreSettings(builder.build());
        promise.resolve(null);
    }

    public void onCatalystInstanceDestroy() {
        int size = this.transactionHandlers.size();
        for (int i = 0; i < size; i++) {
            RNFirebaseFirestoreTransactionHandler rNFirebaseFirestoreTransactionHandler = (RNFirebaseFirestoreTransactionHandler) this.transactionHandlers.get(i);
            if (rNFirebaseFirestoreTransactionHandler != null) {
                rNFirebaseFirestoreTransactionHandler.abort();
            }
        }
        this.transactionHandlers.clear();
    }

    @ReactMethod
    public void transactionGetDocument(String str, int i, String str2, Promise promise) {
        RNFirebaseFirestoreTransactionHandler rNFirebaseFirestoreTransactionHandler = (RNFirebaseFirestoreTransactionHandler) this.transactionHandlers.get(i);
        if (rNFirebaseFirestoreTransactionHandler == null) {
            promise.reject("internal-error", "An internal error occurred whilst attempting to find a native transaction by id.");
        } else {
            rNFirebaseFirestoreTransactionHandler.getDocument(getDocumentForAppPath(str, str2).getRef(), promise);
        }
    }

    @ReactMethod
    public void transactionDispose(String str, int i) {
        RNFirebaseFirestoreTransactionHandler rNFirebaseFirestoreTransactionHandler = (RNFirebaseFirestoreTransactionHandler) this.transactionHandlers.get(i);
        if (rNFirebaseFirestoreTransactionHandler != null) {
            rNFirebaseFirestoreTransactionHandler.abort();
            this.transactionHandlers.delete(i);
        }
    }

    @ReactMethod
    public void transactionApplyBuffer(String str, int i, ReadableArray readableArray) {
        RNFirebaseFirestoreTransactionHandler rNFirebaseFirestoreTransactionHandler = (RNFirebaseFirestoreTransactionHandler) this.transactionHandlers.get(i);
        if (rNFirebaseFirestoreTransactionHandler != null) {
            rNFirebaseFirestoreTransactionHandler.signalBufferReceived(readableArray);
        }
    }

    @ReactMethod
    public void transactionBegin(final String str, int i) {
        final RNFirebaseFirestoreTransactionHandler rNFirebaseFirestoreTransactionHandler = new RNFirebaseFirestoreTransactionHandler(str, i);
        this.transactionHandlers.put(i, rNFirebaseFirestoreTransactionHandler);
        AsyncTask.execute(new Runnable() {
            public void run() {
                RNFirebaseFirestore.getFirestoreForApp(str).runTransaction(new Function<Void>() {
                    public Void apply(@Nonnull Transaction transaction) throws FirebaseFirestoreException {
                        rNFirebaseFirestoreTransactionHandler.resetState(transaction);
                        AsyncTask.execute(new Runnable() {
                            public void run() {
                                Utils.sendEvent(RNFirebaseFirestore.this.access$700(), "firestore_transaction_event", rNFirebaseFirestoreTransactionHandler.createEventMap(null, "update"));
                            }
                        });
                        rNFirebaseFirestoreTransactionHandler.await();
                        if (rNFirebaseFirestoreTransactionHandler.aborted) {
                            throw new FirebaseFirestoreException("abort", Code.ABORTED);
                        } else if (rNFirebaseFirestoreTransactionHandler.timeout) {
                            throw new FirebaseFirestoreException("timeout", Code.DEADLINE_EXCEEDED);
                        } else {
                            ReadableArray commandBuffer = rNFirebaseFirestoreTransactionHandler.getCommandBuffer();
                            if (commandBuffer == null) {
                                return null;
                            }
                            int size = commandBuffer.size();
                            for (int i = 0; i < size; i++) {
                                ReadableMap map = commandBuffer.getMap(i);
                                String string = map.getString(RNFetchBlobConst.RNFB_RESPONSE_PATH);
                                String string2 = map.getString("type");
                                RNFirebaseFirestoreDocumentReference access$300 = RNFirebaseFirestore.this.getDocumentForAppPath(str, string);
                                int i2 = -1;
                                int hashCode = string2.hashCode();
                                if (hashCode != -1335458389) {
                                    if (hashCode != -838846263) {
                                        if (hashCode == 113762 && string2.equals("set")) {
                                            i2 = 0;
                                        }
                                    } else if (string2.equals("update")) {
                                        i2 = 1;
                                    }
                                } else if (string2.equals("delete")) {
                                    i2 = 2;
                                }
                                string2 = "data";
                                if (i2 == 0) {
                                    ReadableMap map2 = map.getMap(string2);
                                    map = map.getMap("options");
                                    Map parseReadableMap = FirestoreSerialize.parseReadableMap(RNFirebaseFirestore.getFirestoreForApp(str), map2);
                                    if (map != null) {
                                        String str = "merge";
                                        if (map.hasKey(str) && map.getBoolean(str)) {
                                            transaction.set(access$300.getRef(), parseReadableMap, SetOptions.merge());
                                        }
                                    }
                                    transaction.set(access$300.getRef(), parseReadableMap);
                                } else if (i2 == 1) {
                                    transaction.update(access$300.getRef(), FirestoreSerialize.parseReadableMap(RNFirebaseFirestore.getFirestoreForApp(str), map.getMap(string2)));
                                } else if (i2 == 2) {
                                    transaction.delete(access$300.getRef());
                                } else {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Unknown command type at index ");
                                    stringBuilder.append(i);
                                    stringBuilder.append(".");
                                    throw new IllegalArgumentException(stringBuilder.toString());
                                }
                            }
                            return null;
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void voidR) {
                        if (!rNFirebaseFirestoreTransactionHandler.aborted) {
                            Log.d(RNFirebaseFirestore.TAG, "Transaction onSuccess!");
                            Utils.sendEvent(RNFirebaseFirestore.this.access$700(), "firestore_transaction_event", rNFirebaseFirestoreTransactionHandler.createEventMap(null, "complete"));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@Nonnull Exception exception) {
                        if (!rNFirebaseFirestoreTransactionHandler.aborted) {
                            Log.w(RNFirebaseFirestore.TAG, "Transaction onFailure.", exception);
                            Utils.sendEvent(RNFirebaseFirestore.this.access$700(), "firestore_transaction_event", rNFirebaseFirestoreTransactionHandler.createEventMap((FirebaseFirestoreException) exception, ReactVideoView.EVENT_PROP_ERROR));
                        }
                    }
                });
            }
        });
    }

    private RNFirebaseFirestoreCollectionReference getCollectionForAppPath(String str, String str2, ReadableArray readableArray, ReadableArray readableArray2, ReadableMap readableMap) {
        return new RNFirebaseFirestoreCollectionReference(access$700(), str, str2, readableArray, readableArray2, readableMap);
    }

    private RNFirebaseFirestoreDocumentReference getDocumentForAppPath(String str, String str2) {
        return new RNFirebaseFirestoreDocumentReference(access$700(), str, str2);
    }

    public Map<String, Object> getConstants() {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("deleteFieldValue", FieldValue.delete().toString());
        hashMap.put("serverTimestampFieldValue", FieldValue.serverTimestamp().toString());
        return hashMap;
    }
}
