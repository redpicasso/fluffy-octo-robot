package io.invertase.firebase.firestore;

import android.util.Log;
import com.RNFetchBlob.RNFetchBlobConst;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;
import io.invertase.firebase.Utils;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

public class RNFirebaseFirestoreDocumentReference {
    private static final String TAG = "RNFBFSDocumentReference";
    private static Map<String, ListenerRegistration> documentSnapshotListeners = new HashMap();
    private final String appName;
    private final String path;
    private ReactContext reactContext;
    private final DocumentReference ref;

    RNFirebaseFirestoreDocumentReference(ReactContext reactContext, String str, String str2) {
        this.path = str2;
        this.appName = str;
        this.reactContext = reactContext;
        this.ref = RNFirebaseFirestore.getFirestoreForApp(str).document(str2);
    }

    static void offSnapshot(String str) {
        ListenerRegistration listenerRegistration = (ListenerRegistration) documentSnapshotListeners.remove(str);
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

    void delete(final Promise promise) {
        this.ref.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseFirestoreDocumentReference.TAG;
                if (isSuccessful) {
                    Log.d(str, "delete:onComplete:success");
                    promise.resolve(null);
                    return;
                }
                Log.e(str, "delete:onComplete:failure", task.getException());
                RNFirebaseFirestore.promiseRejectException(promise, (FirebaseFirestoreException) task.getException());
            }
        });
    }

    void get(ReadableMap readableMap, final Promise promise) {
        Source source;
        final DocumentSnapshotSerializeAsyncTask anonymousClass2;
        if (readableMap != null) {
            String str = Param.SOURCE;
            if (readableMap.hasKey(str)) {
                String string = readableMap.getString(str);
                if ("server".equals(string)) {
                    source = Source.SERVER;
                } else if ("cache".equals(string)) {
                    source = Source.CACHE;
                } else {
                    source = Source.DEFAULT;
                }
                anonymousClass2 = new DocumentSnapshotSerializeAsyncTask(this.reactContext, this) {
                    protected void onPostExecute(WritableMap writableMap) {
                        promise.resolve(writableMap);
                    }
                };
                this.ref.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(@Nonnull Task<DocumentSnapshot> task) {
                        boolean isSuccessful = task.isSuccessful();
                        String str = RNFirebaseFirestoreDocumentReference.TAG;
                        if (isSuccessful) {
                            Log.d(str, "get:onComplete:success");
                            anonymousClass2.execute(new Object[]{task.getResult()});
                            return;
                        }
                        Log.e(str, "get:onComplete:failure", task.getException());
                        RNFirebaseFirestore.promiseRejectException(promise, (FirebaseFirestoreException) task.getException());
                    }
                });
            }
        }
        source = Source.DEFAULT;
        anonymousClass2 = /* anonymous class already generated */;
        this.ref.get(source).addOnCompleteListener(/* anonymous class already generated */);
    }

    void onSnapshot(final String str, ReadableMap readableMap) {
        if (!documentSnapshotListeners.containsKey(str)) {
            MetadataChanges metadataChanges;
            EventListener anonymousClass4 = new EventListener<DocumentSnapshot>() {
                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException firebaseFirestoreException) {
                    if (firebaseFirestoreException == null) {
                        RNFirebaseFirestoreDocumentReference.this.handleDocumentSnapshotEvent(str, documentSnapshot);
                        return;
                    }
                    ListenerRegistration listenerRegistration = (ListenerRegistration) RNFirebaseFirestoreDocumentReference.documentSnapshotListeners.remove(str);
                    if (listenerRegistration != null) {
                        listenerRegistration.remove();
                    }
                    RNFirebaseFirestoreDocumentReference.this.handleDocumentSnapshotError(str, firebaseFirestoreException);
                }
            };
            if (readableMap != null) {
                String str2 = "includeMetadataChanges";
                if (readableMap.hasKey(str2) && readableMap.getBoolean(str2)) {
                    metadataChanges = MetadataChanges.INCLUDE;
                    documentSnapshotListeners.put(str, this.ref.addSnapshotListener(metadataChanges, anonymousClass4));
                }
            }
            metadataChanges = MetadataChanges.EXCLUDE;
            documentSnapshotListeners.put(str, this.ref.addSnapshotListener(metadataChanges, anonymousClass4));
        }
    }

    public void set(ReadableMap readableMap, ReadableMap readableMap2, final Promise promise) {
        Task task;
        Map parseReadableMap = FirestoreSerialize.parseReadableMap(RNFirebaseFirestore.getFirestoreForApp(this.appName), readableMap);
        if (readableMap2 != null) {
            String str = "merge";
            if (readableMap2.hasKey(str) && readableMap2.getBoolean(str)) {
                task = this.ref.set(parseReadableMap, SetOptions.merge());
                task.addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@Nonnull Task<Void> task) {
                        boolean isSuccessful = task.isSuccessful();
                        String str = RNFirebaseFirestoreDocumentReference.TAG;
                        if (isSuccessful) {
                            Log.d(str, "set:onComplete:success");
                            promise.resolve(null);
                            return;
                        }
                        Log.e(str, "set:onComplete:failure", task.getException());
                        RNFirebaseFirestore.promiseRejectException(promise, (FirebaseFirestoreException) task.getException());
                    }
                });
            }
        }
        task = this.ref.set(parseReadableMap);
        task.addOnCompleteListener(/* anonymous class already generated */);
    }

    void update(ReadableMap readableMap, final Promise promise) {
        this.ref.update(FirestoreSerialize.parseReadableMap(RNFirebaseFirestore.getFirestoreForApp(this.appName), readableMap)).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@Nonnull Task<Void> task) {
                boolean isSuccessful = task.isSuccessful();
                String str = RNFirebaseFirestoreDocumentReference.TAG;
                if (isSuccessful) {
                    Log.d(str, "update:onComplete:success");
                    promise.resolve(null);
                    return;
                }
                Log.e(str, "update:onComplete:failure", task.getException());
                RNFirebaseFirestore.promiseRejectException(promise, (FirebaseFirestoreException) task.getException());
            }
        });
    }

    DocumentReference getRef() {
        return this.ref;
    }

    boolean hasListeners() {
        return documentSnapshotListeners.isEmpty() ^ 1;
    }

    private void handleDocumentSnapshotEvent(final String str, DocumentSnapshot documentSnapshot) {
        new DocumentSnapshotSerializeAsyncTask(this.reactContext, this) {
            protected void onPostExecute(WritableMap writableMap) {
                WritableMap createMap = Arguments.createMap();
                createMap.putString(RNFetchBlobConst.RNFB_RESPONSE_PATH, RNFirebaseFirestoreDocumentReference.this.path);
                createMap.putString("appName", RNFirebaseFirestoreDocumentReference.this.appName);
                createMap.putString("listenerId", str);
                createMap.putMap("documentSnapshot", writableMap);
                Utils.sendEvent(RNFirebaseFirestoreDocumentReference.this.reactContext, "firestore_document_sync_event", createMap);
            }
        }.execute(new Object[]{documentSnapshot});
    }

    private void handleDocumentSnapshotError(String str, FirebaseFirestoreException firebaseFirestoreException) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(RNFetchBlobConst.RNFB_RESPONSE_PATH, this.path);
        createMap.putString("appName", this.appName);
        createMap.putString("listenerId", str);
        createMap.putMap(ReactVideoView.EVENT_PROP_ERROR, RNFirebaseFirestore.getJSError(firebaseFirestoreException));
        Utils.sendEvent(this.reactContext, "firestore_document_sync_event", createMap);
    }
}
