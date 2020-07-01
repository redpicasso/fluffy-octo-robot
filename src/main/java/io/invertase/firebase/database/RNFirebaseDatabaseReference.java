package io.invertase.firebase.database;

import android.os.AsyncTask;
import android.util.Log;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import io.invertase.firebase.Utils;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class RNFirebaseDatabaseReference {
    private static final String TAG = "RNFirebaseDBReference";
    private String appName;
    private HashMap<String, ChildEventListener> childEventListeners = new HashMap();
    private String dbURL;
    private String key;
    private Query query;
    private HashMap<String, ValueEventListener> valueEventListeners = new HashMap();

    private static class DataSnapshotToMapAsyncTask extends AsyncTask<Object, Void, WritableMap> {
        private WeakReference<RNFirebaseDatabaseReference> referenceWeakReference;

        protected void onPostExecute(WritableMap writableMap) {
        }

        DataSnapshotToMapAsyncTask(RNFirebaseDatabaseReference rNFirebaseDatabaseReference) {
            this.referenceWeakReference = new WeakReference(rNFirebaseDatabaseReference);
        }

        protected final WritableMap doInBackground(Object... objArr) {
            try {
                return RNFirebaseDatabaseUtils.snapshotToMap((DataSnapshot) objArr[0], (String) objArr[1]);
            } catch (Exception e) {
                if (isAvailable().booleanValue()) {
                    RNFirebaseDatabase.getReactApplicationContextInstance().handleException(e);
                }
                throw e;
            }
        }

        Boolean isAvailable() {
            boolean z = (RNFirebaseDatabase.getReactApplicationContextInstance() == null || this.referenceWeakReference.get() == null) ? false : true;
            return Boolean.valueOf(z);
        }
    }

    RNFirebaseDatabaseReference(String str, String str2, String str3, String str4, ReadableArray readableArray) {
        this.key = str3;
        this.query = null;
        this.appName = str;
        this.dbURL = str2;
        buildDatabaseQueryAtPathAndModifiers(str4, readableArray);
    }

    void removeAllEventListeners() {
        if (hasListeners().booleanValue()) {
            Iterator it = this.valueEventListeners.entrySet().iterator();
            while (it.hasNext()) {
                this.query.removeEventListener((ValueEventListener) ((Entry) it.next()).getValue());
                it.remove();
            }
            it = this.childEventListeners.entrySet().iterator();
            while (it.hasNext()) {
                this.query.removeEventListener((ChildEventListener) ((Entry) it.next()).getValue());
                it.remove();
            }
        }
    }

    Query getQuery() {
        return this.query;
    }

    private Boolean hasEventListener(String str) {
        boolean z = this.valueEventListeners.containsKey(str) || this.childEventListeners.containsKey(str);
        return Boolean.valueOf(z);
    }

    Boolean hasListeners() {
        boolean z = this.valueEventListeners.size() > 0 || this.childEventListeners.size() > 0;
        return Boolean.valueOf(z);
    }

    void removeEventListener(String str) {
        if (this.valueEventListeners.containsKey(str)) {
            this.query.removeEventListener((ValueEventListener) this.valueEventListeners.get(str));
            this.valueEventListeners.remove(str);
        }
        if (this.childEventListeners.containsKey(str)) {
            this.query.removeEventListener((ChildEventListener) this.childEventListeners.get(str));
            this.childEventListeners.remove(str);
        }
    }

    private void addEventListener(String str, ValueEventListener valueEventListener) {
        this.valueEventListeners.put(str, valueEventListener);
        this.query.addValueEventListener(valueEventListener);
    }

    private void addEventListener(String str, ChildEventListener childEventListener) {
        this.childEventListeners.put(str, childEventListener);
        this.query.addChildEventListener(childEventListener);
    }

    private void addOnceValueEventListener(final Promise promise) {
        final DataSnapshotToMapAsyncTask anonymousClass1 = new DataSnapshotToMapAsyncTask(this) {
            protected void onPostExecute(WritableMap writableMap) {
                if (isAvailable().booleanValue()) {
                    promise.resolve(writableMap);
                }
            }
        };
        this.query.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@Nonnull DataSnapshot dataSnapshot) {
                anonymousClass1.execute(new Object[]{dataSnapshot, null});
            }

            public void onCancelled(@Nonnull DatabaseError databaseError) {
                RNFirebaseDatabase.handlePromise(promise, databaseError);
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Added OnceValueEventListener for key: ");
        stringBuilder.append(this.key);
        Log.d(TAG, stringBuilder.toString());
    }

    private void addChildOnceEventListener(final String str, final Promise promise) {
        this.query.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(@Nonnull DataSnapshot dataSnapshot, String str) {
                if ("child_added".equals(str)) {
                    RNFirebaseDatabaseReference.this.query.removeEventListener((ChildEventListener) this);
                    promise.resolve(RNFirebaseDatabaseUtils.snapshotToMap(dataSnapshot, str));
                }
            }

            public void onChildChanged(@Nonnull DataSnapshot dataSnapshot, String str) {
                if ("child_changed".equals(str)) {
                    RNFirebaseDatabaseReference.this.query.removeEventListener((ChildEventListener) this);
                    promise.resolve(RNFirebaseDatabaseUtils.snapshotToMap(dataSnapshot, str));
                }
            }

            public void onChildRemoved(@Nonnull DataSnapshot dataSnapshot) {
                if ("child_removed".equals(str)) {
                    RNFirebaseDatabaseReference.this.query.removeEventListener((ChildEventListener) this);
                    promise.resolve(RNFirebaseDatabaseUtils.snapshotToMap(dataSnapshot, null));
                }
            }

            public void onChildMoved(@Nonnull DataSnapshot dataSnapshot, String str) {
                if ("child_moved".equals(str)) {
                    RNFirebaseDatabaseReference.this.query.removeEventListener((ChildEventListener) this);
                    promise.resolve(RNFirebaseDatabaseUtils.snapshotToMap(dataSnapshot, str));
                }
            }

            public void onCancelled(@Nonnull DatabaseError databaseError) {
                RNFirebaseDatabaseReference.this.query.removeEventListener((ChildEventListener) this);
                RNFirebaseDatabase.handlePromise(promise, databaseError);
            }
        });
    }

    void on(String str, ReadableMap readableMap) {
        if (str.equals("value")) {
            addValueEventListener(readableMap);
        } else {
            addChildEventListener(readableMap, str);
        }
    }

    void once(String str, Promise promise) {
        if (str.equals("value")) {
            addOnceValueEventListener(promise);
        } else {
            addChildOnceEventListener(str, promise);
        }
    }

    private void addChildEventListener(final ReadableMap readableMap, final String str) {
        final String string = readableMap.getString("eventRegistrationKey");
        readableMap.getString("registrationCancellationKey");
        if (!hasEventListener(string).booleanValue()) {
            addEventListener(string, new ChildEventListener() {
                public void onChildAdded(@Nonnull DataSnapshot dataSnapshot, String str) {
                    String str2 = "child_added";
                    if (str2.equals(str)) {
                        RNFirebaseDatabaseReference.this.handleDatabaseEvent(str2, readableMap, dataSnapshot, str);
                    }
                }

                public void onChildChanged(@Nonnull DataSnapshot dataSnapshot, String str) {
                    String str2 = "child_changed";
                    if (str2.equals(str)) {
                        RNFirebaseDatabaseReference.this.handleDatabaseEvent(str2, readableMap, dataSnapshot, str);
                    }
                }

                public void onChildRemoved(@Nonnull DataSnapshot dataSnapshot) {
                    String str = "child_removed";
                    if (str.equals(str)) {
                        RNFirebaseDatabaseReference.this.handleDatabaseEvent(str, readableMap, dataSnapshot, null);
                    }
                }

                public void onChildMoved(@Nonnull DataSnapshot dataSnapshot, String str) {
                    String str2 = "child_moved";
                    if (str2.equals(str)) {
                        RNFirebaseDatabaseReference.this.handleDatabaseEvent(str2, readableMap, dataSnapshot, str);
                    }
                }

                public void onCancelled(@Nonnull DatabaseError databaseError) {
                    RNFirebaseDatabaseReference.this.removeEventListener(string);
                    RNFirebaseDatabaseReference.this.handleDatabaseError(readableMap, databaseError);
                }
            });
        }
    }

    private void addValueEventListener(final ReadableMap readableMap) {
        final String string = readableMap.getString("eventRegistrationKey");
        if (!hasEventListener(string).booleanValue()) {
            addEventListener(string, new ValueEventListener() {
                public void onDataChange(@Nonnull DataSnapshot dataSnapshot) {
                    RNFirebaseDatabaseReference.this.handleDatabaseEvent("value", readableMap, dataSnapshot, null);
                }

                public void onCancelled(@Nonnull DatabaseError databaseError) {
                    RNFirebaseDatabaseReference.this.removeEventListener(string);
                    RNFirebaseDatabaseReference.this.handleDatabaseError(readableMap, databaseError);
                }
            });
        }
    }

    private void handleDatabaseEvent(final String str, final ReadableMap readableMap, DataSnapshot dataSnapshot, @Nullable String str2) {
        new DataSnapshotToMapAsyncTask(this) {
            protected void onPostExecute(WritableMap writableMap) {
                if (isAvailable().booleanValue()) {
                    WritableMap createMap = Arguments.createMap();
                    createMap.putMap("data", writableMap);
                    createMap.putString("key", RNFirebaseDatabaseReference.this.key);
                    createMap.putString("eventType", str);
                    createMap.putMap("registration", Utils.readableMapToWritableMap(readableMap));
                    Utils.sendEvent(RNFirebaseDatabase.getReactApplicationContextInstance(), "database_sync_event", createMap);
                }
            }
        }.execute(new Object[]{dataSnapshot, str2});
    }

    private void handleDatabaseError(ReadableMap readableMap, DatabaseError databaseError) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("key", this.key);
        createMap.putMap(ReactVideoView.EVENT_PROP_ERROR, RNFirebaseDatabase.getJSError(databaseError));
        createMap.putMap("registration", Utils.readableMapToWritableMap(readableMap));
        Utils.sendEvent(RNFirebaseDatabase.getReactApplicationContextInstance(), "database_sync_event", createMap);
    }

    private void buildDatabaseQueryAtPathAndModifiers(String str, ReadableArray readableArray) {
        this.query = RNFirebaseDatabase.getDatabaseForApp(this.appName, this.dbURL).getReference(str);
        for (Map map : Utils.recursivelyDeconstructReadableArray(readableArray)) {
            String str2 = (String) map.get("type");
            String str3 = (String) map.get(ConditionalUserProperty.NAME);
            if ("orderBy".equals(str2)) {
                applyOrderByModifier(str3, str2, map);
            } else if ("limit".equals(str2)) {
                applyLimitModifier(str3, str2, map);
            } else if ("filter".equals(str2)) {
                applyFilterModifier(str3, map);
            }
        }
    }

    private void applyOrderByModifier(java.lang.String r4, java.lang.String r5, java.util.Map r6) {
        /*
        r3 = this;
        r5 = r4.hashCode();
        r0 = 3;
        r1 = 2;
        r2 = 1;
        switch(r5) {
            case -626148087: goto L_0x0029;
            case 729747418: goto L_0x001f;
            case 1200288727: goto L_0x0015;
            case 1217630252: goto L_0x000b;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0033;
    L_0x000b:
        r5 = "orderByValue";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x0033;
    L_0x0013:
        r4 = 2;
        goto L_0x0034;
    L_0x0015:
        r5 = "orderByChild";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x0033;
    L_0x001d:
        r4 = 3;
        goto L_0x0034;
    L_0x001f:
        r5 = "orderByKey";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x0033;
    L_0x0027:
        r4 = 0;
        goto L_0x0034;
    L_0x0029:
        r5 = "orderByPriority";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x0033;
    L_0x0031:
        r4 = 1;
        goto L_0x0034;
    L_0x0033:
        r4 = -1;
    L_0x0034:
        if (r4 == 0) goto L_0x0060;
    L_0x0036:
        if (r4 == r2) goto L_0x0057;
    L_0x0038:
        if (r4 == r1) goto L_0x004e;
    L_0x003a:
        if (r4 == r0) goto L_0x003d;
    L_0x003c:
        goto L_0x0068;
    L_0x003d:
        r4 = "key";
        r4 = r6.get(r4);
        r4 = (java.lang.String) r4;
        r5 = r3.query;
        r4 = r5.orderByChild(r4);
        r3.query = r4;
        goto L_0x0068;
    L_0x004e:
        r4 = r3.query;
        r4 = r4.orderByValue();
        r3.query = r4;
        goto L_0x0068;
    L_0x0057:
        r4 = r3.query;
        r4 = r4.orderByPriority();
        r3.query = r4;
        goto L_0x0068;
    L_0x0060:
        r4 = r3.query;
        r4 = r4.orderByKey();
        r3.query = r4;
    L_0x0068:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.database.RNFirebaseDatabaseReference.applyOrderByModifier(java.lang.String, java.lang.String, java.util.Map):void");
    }

    private void applyLimitModifier(String str, String str2, Map map) {
        int intValue = ((Double) map.get("limit")).intValue();
        if ("limitToLast".equals(str)) {
            this.query = this.query.limitToLast(intValue);
        } else if ("limitToFirst".equals(str)) {
            this.query = this.query.limitToFirst(intValue);
        }
    }

    private void applyFilterModifier(String str, Map map) {
        String str2 = (String) map.get("valueType");
        String str3 = (String) map.get("key");
        if ("equalTo".equals(str)) {
            applyEqualToFilter(str3, str2, map);
        } else if ("endAt".equals(str)) {
            applyEndAtFilter(str3, str2, map);
        } else if ("startAt".equals(str)) {
            applyStartAtFilter(str3, str2, map);
        }
    }

    private void applyEqualToFilter(String str, String str2, Map map) {
        String str3 = "value";
        if ("number".equals(str2)) {
            double doubleValue = ((Double) map.get(str3)).doubleValue();
            if (str == null) {
                this.query = this.query.equalTo(doubleValue);
            } else {
                this.query = this.query.equalTo(doubleValue, str);
            }
        } else if ("boolean".equals(str2)) {
            boolean booleanValue = ((Boolean) map.get(str3)).booleanValue();
            if (str == null) {
                this.query = this.query.equalTo(booleanValue);
            } else {
                this.query = this.query.equalTo(booleanValue, str);
            }
        } else if ("string".equals(str2)) {
            str2 = (String) map.get(str3);
            if (str == null) {
                this.query = this.query.equalTo(str2);
            } else {
                this.query = this.query.equalTo(str2, str);
            }
        }
    }

    private void applyEndAtFilter(String str, String str2, Map map) {
        String str3 = "value";
        if ("number".equals(str2)) {
            double doubleValue = ((Double) map.get(str3)).doubleValue();
            if (str == null) {
                this.query = this.query.endAt(doubleValue);
            } else {
                this.query = this.query.endAt(doubleValue, str);
            }
        } else if ("boolean".equals(str2)) {
            boolean booleanValue = ((Boolean) map.get(str3)).booleanValue();
            if (str == null) {
                this.query = this.query.endAt(booleanValue);
            } else {
                this.query = this.query.endAt(booleanValue, str);
            }
        } else if ("string".equals(str2)) {
            str2 = (String) map.get(str3);
            if (str == null) {
                this.query = this.query.endAt(str2);
            } else {
                this.query = this.query.endAt(str2, str);
            }
        }
    }

    private void applyStartAtFilter(String str, String str2, Map map) {
        String str3 = "value";
        if ("number".equals(str2)) {
            double doubleValue = ((Double) map.get(str3)).doubleValue();
            if (str == null) {
                this.query = this.query.startAt(doubleValue);
            } else {
                this.query = this.query.startAt(doubleValue, str);
            }
        } else if ("boolean".equals(str2)) {
            boolean booleanValue = ((Boolean) map.get(str3)).booleanValue();
            if (str == null) {
                this.query = this.query.startAt(booleanValue);
            } else {
                this.query = this.query.startAt(booleanValue, str);
            }
        } else if ("string".equals(str2)) {
            str2 = (String) map.get(str3);
            if (str == null) {
                this.query = this.query.startAt(str2);
            } else {
                this.query = this.query.startAt(str2, str);
            }
        }
    }
}
