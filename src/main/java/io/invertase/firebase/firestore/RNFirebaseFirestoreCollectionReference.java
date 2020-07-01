package io.invertase.firebase.firestore;

import android.util.Log;
import com.RNFetchBlob.RNFetchBlobConst;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import io.invertase.firebase.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

class RNFirebaseFirestoreCollectionReference {
    private static final String TAG = "RNFSCollectionReference";
    private static Map<String, ListenerRegistration> collectionSnapshotListeners = new HashMap();
    private final String appName;
    private final ReadableArray filters;
    private final ReadableMap options;
    private final ReadableArray orders;
    private final String path;
    private final Query query = buildQuery();
    private ReactContext reactContext;

    RNFirebaseFirestoreCollectionReference(ReactContext reactContext, String str, String str2, ReadableArray readableArray, ReadableArray readableArray2, ReadableMap readableMap) {
        this.appName = str;
        this.path = str2;
        this.filters = readableArray;
        this.orders = readableArray2;
        this.options = readableMap;
        this.reactContext = reactContext;
    }

    static void offSnapshot(String str) {
        ListenerRegistration listenerRegistration = (ListenerRegistration) collectionSnapshotListeners.remove(str);
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

    void get(ReadableMap readableMap, final Promise promise) {
        Source source;
        final QuerySnapshotSerializeAsyncTask anonymousClass1;
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
                anonymousClass1 = new QuerySnapshotSerializeAsyncTask(this.reactContext, this) {
                    protected void onPostExecute(WritableMap writableMap) {
                        promise.resolve(writableMap);
                    }
                };
                this.query.get(source).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    public void onComplete(@Nonnull Task<QuerySnapshot> task) {
                        boolean isSuccessful = task.isSuccessful();
                        String str = RNFirebaseFirestoreCollectionReference.TAG;
                        if (isSuccessful) {
                            Log.d(str, "get:onComplete:success");
                            anonymousClass1.execute(new Object[]{task.getResult()});
                            return;
                        }
                        Log.e(str, "get:onComplete:failure", task.getException());
                        RNFirebaseFirestore.promiseRejectException(promise, (FirebaseFirestoreException) task.getException());
                    }
                });
            }
        }
        source = Source.DEFAULT;
        anonymousClass1 = /* anonymous class already generated */;
        this.query.get(source).addOnCompleteListener(/* anonymous class already generated */);
    }

    void onSnapshot(final String str, ReadableMap readableMap) {
        if (!collectionSnapshotListeners.containsKey(str)) {
            MetadataChanges metadataChanges;
            EventListener anonymousClass3 = new EventListener<QuerySnapshot>() {
                public void onEvent(QuerySnapshot querySnapshot, FirebaseFirestoreException firebaseFirestoreException) {
                    if (firebaseFirestoreException == null) {
                        RNFirebaseFirestoreCollectionReference.this.handleQuerySnapshotEvent(str, querySnapshot);
                        return;
                    }
                    ListenerRegistration listenerRegistration = (ListenerRegistration) RNFirebaseFirestoreCollectionReference.collectionSnapshotListeners.remove(str);
                    if (listenerRegistration != null) {
                        listenerRegistration.remove();
                    }
                    RNFirebaseFirestoreCollectionReference.this.handleQuerySnapshotError(str, firebaseFirestoreException);
                }
            };
            if (readableMap != null) {
                String str2 = "includeMetadataChanges";
                if (readableMap.hasKey(str2) && readableMap.getBoolean(str2)) {
                    metadataChanges = MetadataChanges.INCLUDE;
                    collectionSnapshotListeners.put(str, this.query.addSnapshotListener(metadataChanges, anonymousClass3));
                }
            }
            metadataChanges = MetadataChanges.EXCLUDE;
            collectionSnapshotListeners.put(str, this.query.addSnapshotListener(metadataChanges, anonymousClass3));
        }
    }

    boolean hasListeners() {
        return collectionSnapshotListeners.isEmpty() ^ 1;
    }

    private Query buildQuery() {
        FirebaseFirestore firestoreForApp = RNFirebaseFirestore.getFirestoreForApp(this.appName);
        return applyOptions(firestoreForApp, applyOrders(applyFilters(firestoreForApp, firestoreForApp.collection(this.path))));
    }

    private com.google.firebase.firestore.Query applyFilters(com.google.firebase.firestore.FirebaseFirestore r18, com.google.firebase.firestore.Query r19) {
        /*
        r17 = this;
        r0 = r17;
        r3 = r19;
        r2 = 0;
    L_0x0005:
        r4 = r0.filters;
        r4 = r4.size();
        if (r2 >= r4) goto L_0x0148;
    L_0x000d:
        r4 = r0.filters;
        r4 = r4.getMap(r2);
        r5 = "fieldPath";
        r5 = r4.getMap(r5);
        r6 = "type";
        r6 = r5.getString(r6);
        r7 = "operator";
        r7 = r4.getString(r7);
        r8 = "value";
        r4 = r4.getMap(r8);
        r8 = r18;
        r4 = io.invertase.firebase.firestore.FirestoreSerialize.parseTypeMap(r8, r4);
        r9 = "string";
        r6 = r6.equals(r9);
        r10 = "GREATER_THAN_OR_EQUAL";
        r11 = "GREATER_THAN";
        r12 = "ARRAY_CONTAINS";
        r13 = "EQUAL";
        r14 = "LESS_THAN";
        r15 = "LESS_THAN_OR_EQUAL";
        r16 = -1;
        r1 = 1;
        if (r6 == 0) goto L_0x00bb;
    L_0x0048:
        r5 = r5.getString(r9);
        r6 = r7.hashCode();
        switch(r6) {
            case -2081783184: goto L_0x007c;
            case -1112834937: goto L_0x0074;
            case 66219796: goto L_0x006c;
            case 67210597: goto L_0x0064;
            case 972152550: goto L_0x005c;
            case 989027057: goto L_0x0054;
            default: goto L_0x0053;
        };
    L_0x0053:
        goto L_0x0084;
    L_0x0054:
        r6 = r7.equals(r10);
        if (r6 == 0) goto L_0x0084;
    L_0x005a:
        r6 = 2;
        goto L_0x0085;
    L_0x005c:
        r6 = r7.equals(r11);
        if (r6 == 0) goto L_0x0084;
    L_0x0062:
        r6 = 1;
        goto L_0x0085;
    L_0x0064:
        r6 = r7.equals(r12);
        if (r6 == 0) goto L_0x0084;
    L_0x006a:
        r6 = 5;
        goto L_0x0085;
    L_0x006c:
        r6 = r7.equals(r13);
        if (r6 == 0) goto L_0x0084;
    L_0x0072:
        r6 = 0;
        goto L_0x0085;
    L_0x0074:
        r6 = r7.equals(r14);
        if (r6 == 0) goto L_0x0084;
    L_0x007a:
        r6 = 3;
        goto L_0x0085;
    L_0x007c:
        r6 = r7.equals(r15);
        if (r6 == 0) goto L_0x0084;
    L_0x0082:
        r6 = 4;
        goto L_0x0085;
    L_0x0084:
        r6 = -1;
    L_0x0085:
        if (r6 == 0) goto L_0x00b5;
    L_0x0087:
        if (r6 == r1) goto L_0x00af;
    L_0x0089:
        r1 = 2;
        if (r6 == r1) goto L_0x00a9;
    L_0x008c:
        r1 = 3;
        if (r6 == r1) goto L_0x00a3;
    L_0x008f:
        r1 = 4;
        if (r6 == r1) goto L_0x009d;
    L_0x0092:
        r1 = 5;
        if (r6 == r1) goto L_0x0097;
    L_0x0095:
        goto L_0x0144;
    L_0x0097:
        r3 = r3.whereArrayContains(r5, r4);
        goto L_0x0144;
    L_0x009d:
        r3 = r3.whereLessThanOrEqualTo(r5, r4);
        goto L_0x0144;
    L_0x00a3:
        r3 = r3.whereLessThan(r5, r4);
        goto L_0x0144;
    L_0x00a9:
        r3 = r3.whereGreaterThanOrEqualTo(r5, r4);
        goto L_0x0144;
    L_0x00af:
        r3 = r3.whereGreaterThan(r5, r4);
        goto L_0x0144;
    L_0x00b5:
        r3 = r3.whereEqualTo(r5, r4);
        goto L_0x0144;
    L_0x00bb:
        r6 = "elements";
        r5 = r5.getArray(r6);
        r6 = r5.size();
        r6 = new java.lang.String[r6];
        r9 = 0;
    L_0x00c8:
        r1 = r5.size();
        if (r9 >= r1) goto L_0x00d7;
    L_0x00ce:
        r1 = r5.getString(r9);
        r6[r9] = r1;
        r9 = r9 + 1;
        goto L_0x00c8;
    L_0x00d7:
        r1 = com.google.firebase.firestore.FieldPath.of(r6);
        r5 = r7.hashCode();
        switch(r5) {
            case -2081783184: goto L_0x010b;
            case -1112834937: goto L_0x0103;
            case 66219796: goto L_0x00fb;
            case 67210597: goto L_0x00f3;
            case 972152550: goto L_0x00eb;
            case 989027057: goto L_0x00e3;
            default: goto L_0x00e2;
        };
    L_0x00e2:
        goto L_0x0113;
    L_0x00e3:
        r5 = r7.equals(r10);
        if (r5 == 0) goto L_0x0113;
    L_0x00e9:
        r5 = 2;
        goto L_0x0114;
    L_0x00eb:
        r5 = r7.equals(r11);
        if (r5 == 0) goto L_0x0113;
    L_0x00f1:
        r5 = 1;
        goto L_0x0114;
    L_0x00f3:
        r5 = r7.equals(r12);
        if (r5 == 0) goto L_0x0113;
    L_0x00f9:
        r5 = 5;
        goto L_0x0114;
    L_0x00fb:
        r5 = r7.equals(r13);
        if (r5 == 0) goto L_0x0113;
    L_0x0101:
        r5 = 0;
        goto L_0x0114;
    L_0x0103:
        r5 = r7.equals(r14);
        if (r5 == 0) goto L_0x0113;
    L_0x0109:
        r5 = 3;
        goto L_0x0114;
    L_0x010b:
        r5 = r7.equals(r15);
        if (r5 == 0) goto L_0x0113;
    L_0x0111:
        r5 = 4;
        goto L_0x0114;
    L_0x0113:
        r5 = -1;
    L_0x0114:
        if (r5 == 0) goto L_0x013f;
    L_0x0116:
        r6 = 1;
        if (r5 == r6) goto L_0x013a;
    L_0x0119:
        r6 = 2;
        if (r5 == r6) goto L_0x0135;
    L_0x011c:
        r6 = 3;
        if (r5 == r6) goto L_0x0130;
    L_0x011f:
        r6 = 4;
        if (r5 == r6) goto L_0x012b;
    L_0x0122:
        r6 = 5;
        if (r5 == r6) goto L_0x0126;
    L_0x0125:
        goto L_0x0144;
    L_0x0126:
        r1 = r3.whereArrayContains(r1, r4);
        goto L_0x0143;
    L_0x012b:
        r1 = r3.whereLessThanOrEqualTo(r1, r4);
        goto L_0x0143;
    L_0x0130:
        r1 = r3.whereLessThan(r1, r4);
        goto L_0x0143;
    L_0x0135:
        r1 = r3.whereGreaterThanOrEqualTo(r1, r4);
        goto L_0x0143;
    L_0x013a:
        r1 = r3.whereGreaterThan(r1, r4);
        goto L_0x0143;
    L_0x013f:
        r1 = r3.whereEqualTo(r1, r4);
    L_0x0143:
        r3 = r1;
    L_0x0144:
        r2 = r2 + 1;
        goto L_0x0005;
    L_0x0148:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.firestore.RNFirebaseFirestoreCollectionReference.applyFilters(com.google.firebase.firestore.FirebaseFirestore, com.google.firebase.firestore.Query):com.google.firebase.firestore.Query");
    }

    private Query applyOrders(Query query) {
        for (Map map : Utils.recursivelyDeconstructReadableArray(this.orders)) {
            String str = (String) map.get("direction");
            Map map2 = (Map) map2.get("fieldPath");
            String str2 = "string";
            if (((String) map2.get("type")).equals(str2)) {
                query = query.orderBy((String) map2.get(str2), Direction.valueOf(str));
            } else {
                List list = (List) map2.get("elements");
                query = query.orderBy(FieldPath.of((String[]) list.toArray(new String[list.size()])), Direction.valueOf(str));
            }
        }
        return query;
    }

    private Query applyOptions(FirebaseFirestore firebaseFirestore, Query query) {
        String str = "endAt";
        if (this.options.hasKey(str)) {
            query = query.endAt(FirestoreSerialize.parseReadableArray(firebaseFirestore, this.options.getArray(str)).toArray());
        }
        str = "endBefore";
        if (this.options.hasKey(str)) {
            query = query.endBefore(FirestoreSerialize.parseReadableArray(firebaseFirestore, this.options.getArray(str)).toArray());
        }
        str = "limit";
        if (this.options.hasKey(str)) {
            query = query.limit((long) this.options.getInt(str));
        }
        str = "startAfter";
        if (this.options.hasKey(str)) {
            query = query.startAfter(FirestoreSerialize.parseReadableArray(firebaseFirestore, this.options.getArray(str)).toArray());
        }
        str = "startAt";
        return this.options.hasKey(str) ? query.startAt(FirestoreSerialize.parseReadableArray(firebaseFirestore, this.options.getArray(str)).toArray()) : query;
    }

    private void handleQuerySnapshotEvent(final String str, QuerySnapshot querySnapshot) {
        new QuerySnapshotSerializeAsyncTask(this.reactContext, this) {
            protected void onPostExecute(WritableMap writableMap) {
                WritableMap createMap = Arguments.createMap();
                createMap.putString(RNFetchBlobConst.RNFB_RESPONSE_PATH, RNFirebaseFirestoreCollectionReference.this.path);
                createMap.putString("appName", RNFirebaseFirestoreCollectionReference.this.appName);
                createMap.putString("listenerId", str);
                createMap.putMap("querySnapshot", writableMap);
                Utils.sendEvent(RNFirebaseFirestoreCollectionReference.this.reactContext, "firestore_collection_sync_event", createMap);
            }
        }.execute(new Object[]{querySnapshot});
    }

    private void handleQuerySnapshotError(String str, FirebaseFirestoreException firebaseFirestoreException) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("appName", this.appName);
        createMap.putString(RNFetchBlobConst.RNFB_RESPONSE_PATH, this.path);
        createMap.putString("listenerId", str);
        createMap.putMap(ReactVideoView.EVENT_PROP_ERROR, RNFirebaseFirestore.getJSError(firebaseFirestoreException));
        Utils.sendEvent(this.reactContext, "firestore_collection_sync_event", createMap);
    }
}
