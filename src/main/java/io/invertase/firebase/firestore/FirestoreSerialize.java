package io.invertase.firebase.firestore;

import android.util.Base64;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentChange.Type;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;
import io.invertase.firebase.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

class FirestoreSerialize {
    private static final String CHANGE_ADDED = "added";
    private static final String CHANGE_MODIFIED = "modified";
    private static final String CHANGE_REMOVED = "removed";
    private static final String KEY_CHANGES = "changes";
    private static final String KEY_DATA = "data";
    private static final String KEY_DOCUMENTS = "documents";
    private static final String KEY_DOC_CHANGE_DOCUMENT = "document";
    private static final String KEY_DOC_CHANGE_NEW_INDEX = "newIndex";
    private static final String KEY_DOC_CHANGE_OLD_INDEX = "oldIndex";
    private static final String KEY_DOC_CHANGE_TYPE = "type";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_META = "metadata";
    private static final String KEY_META_FROM_CACHE = "fromCache";
    private static final String KEY_META_HAS_PENDING_WRITES = "hasPendingWrites";
    private static final String KEY_NANOSECONDS = "nanoseconds";
    private static final String KEY_OPTIONS = "options";
    private static final String KEY_PATH = "path";
    private static final String KEY_SECONDS = "seconds";
    private static final String TAG = "FirestoreSerialize";
    private static final String TYPE = "type";
    private static final String TYPE_ARRAY = "array";
    private static final String TYPE_BLOB = "blob";
    private static final String TYPE_BOOLEAN = "boolean";
    private static final String TYPE_DATE = "date";
    private static final String TYPE_DOCUMENTID = "documentid";
    private static final String TYPE_FIELDVALUE = "fieldvalue";
    private static final String TYPE_FIELDVALUE_DELETE = "delete";
    private static final String TYPE_FIELDVALUE_ELEMENTS = "elements";
    private static final String TYPE_FIELDVALUE_INCREMENT = "increment";
    private static final String TYPE_FIELDVALUE_REMOVE = "remove";
    private static final String TYPE_FIELDVALUE_TIMESTAMP = "timestamp";
    private static final String TYPE_FIELDVALUE_TYPE = "type";
    private static final String TYPE_FIELDVALUE_UNION = "union";
    private static final String TYPE_GEOPOINT = "geopoint";
    private static final String TYPE_INFINITY = "infinity";
    private static final String TYPE_NAN = "nan";
    private static final String TYPE_NULL = "null";
    private static final String TYPE_NUMBER = "number";
    private static final String TYPE_OBJECT = "object";
    private static final String TYPE_REFERENCE = "reference";
    private static final String TYPE_STRING = "string";
    private static final String TYPE_TIMESTAMP = "timestamp";
    private static final String VALUE = "value";

    /* renamed from: io.invertase.firebase.firestore.FirestoreSerialize$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$DocumentChange$Type = new int[Type.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$google$firebase$firestore$DocumentChange$Type[com.google.firebase.firestore.DocumentChange.Type.REMOVED.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.google.firebase.firestore.DocumentChange.Type.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$firestore$DocumentChange$Type = r0;
            r0 = $SwitchMap$com$google$firebase$firestore$DocumentChange$Type;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.firestore.DocumentChange.Type.ADDED;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$firestore$DocumentChange$Type;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.firestore.DocumentChange.Type.MODIFIED;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$firestore$DocumentChange$Type;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.firestore.DocumentChange.Type.REMOVED;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.firestore.FirestoreSerialize.1.<clinit>():void");
        }
    }

    FirestoreSerialize() {
    }

    static WritableMap snapshotToWritableMap(DocumentSnapshot documentSnapshot) {
        WritableMap createMap = Arguments.createMap();
        WritableMap createMap2 = Arguments.createMap();
        SnapshotMetadata metadata = documentSnapshot.getMetadata();
        createMap.putBoolean(KEY_META_FROM_CACHE, metadata.isFromCache());
        createMap.putBoolean(KEY_META_HAS_PENDING_WRITES, metadata.hasPendingWrites());
        createMap2.putMap("metadata", createMap);
        createMap2.putString("path", documentSnapshot.getReference().getPath());
        if (documentSnapshot.exists()) {
            createMap2.putMap("data", objectMapToWritable(documentSnapshot.getData()));
        }
        return createMap2;
    }

    static WritableMap snapshotToWritableMap(QuerySnapshot querySnapshot) {
        WritableMap createMap = Arguments.createMap();
        WritableMap createMap2 = Arguments.createMap();
        WritableArray createArray = Arguments.createArray();
        SnapshotMetadata metadata = querySnapshot.getMetadata();
        List<DocumentSnapshot> documents = querySnapshot.getDocuments();
        List documentChanges = querySnapshot.getDocumentChanges();
        for (DocumentSnapshot snapshotToWritableMap : documents) {
            createArray.pushMap(snapshotToWritableMap(snapshotToWritableMap));
        }
        createMap.putBoolean(KEY_META_FROM_CACHE, metadata.isFromCache());
        createMap.putBoolean(KEY_META_HAS_PENDING_WRITES, metadata.hasPendingWrites());
        createMap2.putMap("metadata", createMap);
        createMap2.putArray(KEY_DOCUMENTS, createArray);
        createMap2.putArray(KEY_CHANGES, documentChangesToWritableArray(documentChanges));
        return createMap2;
    }

    private static WritableArray documentChangesToWritableArray(List<DocumentChange> list) {
        WritableArray createArray = Arguments.createArray();
        for (DocumentChange documentChangeToWritableMap : list) {
            createArray.pushMap(documentChangeToWritableMap(documentChangeToWritableMap));
        }
        return createArray;
    }

    private static WritableMap documentChangeToWritableMap(DocumentChange documentChange) {
        WritableMap createMap = Arguments.createMap();
        int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$DocumentChange$Type[documentChange.getType().ordinal()];
        String str = "type";
        if (i == 1) {
            createMap.putString(str, CHANGE_ADDED);
        } else if (i == 2) {
            createMap.putString(str, CHANGE_MODIFIED);
        } else if (i == 3) {
            createMap.putString(str, CHANGE_REMOVED);
        }
        createMap.putMap(KEY_DOC_CHANGE_DOCUMENT, snapshotToWritableMap(documentChange.getDocument()));
        createMap.putInt(KEY_DOC_CHANGE_NEW_INDEX, documentChange.getNewIndex());
        createMap.putInt(KEY_DOC_CHANGE_OLD_INDEX, documentChange.getOldIndex());
        return createMap;
    }

    private static WritableMap objectMapToWritable(Map<String, Object> map) {
        WritableMap createMap = Arguments.createMap();
        for (Entry entry : map.entrySet()) {
            createMap.putMap((String) entry.getKey(), buildTypeMap(entry.getValue()));
        }
        return createMap;
    }

    private static WritableArray objectArrayToWritable(Object[] objArr) {
        WritableArray createArray = Arguments.createArray();
        for (Object buildTypeMap : objArr) {
            createArray.pushMap(buildTypeMap(buildTypeMap));
        }
        return createArray;
    }

    private static WritableMap buildTypeMap(Object obj) {
        WritableMap createMap = Arguments.createMap();
        String str = TYPE_NULL;
        String str2 = "value";
        String str3 = "type";
        if (obj == null) {
            createMap.putString(str3, str);
            createMap.putNull(str2);
            return createMap;
        } else if (obj instanceof Boolean) {
            createMap.putString(str3, TYPE_BOOLEAN);
            createMap.putBoolean(str2, ((Boolean) obj).booleanValue());
            return createMap;
        } else {
            boolean z = obj instanceof Integer;
            String str4 = TYPE_NUMBER;
            WritableMap createMap2;
            if (z) {
                createMap.putString(str3, str4);
                createMap.putDouble(str2, ((Integer) obj).doubleValue());
                return createMap;
            } else if (obj instanceof Double) {
                Double d = (Double) obj;
                if (Double.isInfinite(d.doubleValue())) {
                    createMap.putString(str3, TYPE_INFINITY);
                    return createMap;
                } else if (Double.isNaN(d.doubleValue())) {
                    createMap.putString(str3, TYPE_NAN);
                    return createMap;
                } else {
                    createMap.putString(str3, str4);
                    createMap.putDouble(str2, d.doubleValue());
                    return createMap;
                }
            } else if (obj instanceof Float) {
                createMap.putString(str3, str4);
                createMap.putDouble(str2, ((Float) obj).doubleValue());
                return createMap;
            } else if (obj instanceof Long) {
                createMap.putString(str3, str4);
                createMap.putDouble(str2, ((Long) obj).doubleValue());
                return createMap;
            } else if (obj instanceof String) {
                createMap.putString(str3, TYPE_STRING);
                createMap.putString(str2, (String) obj);
                return createMap;
            } else if (obj instanceof Date) {
                createMap.putString(str3, TYPE_DATE);
                createMap.putDouble(str2, (double) ((Date) obj).getTime());
                return createMap;
            } else if (Map.class.isAssignableFrom(obj.getClass())) {
                createMap.putString(str3, TYPE_OBJECT);
                createMap.putMap(str2, objectMapToWritable((Map) obj));
                return createMap;
            } else if (List.class.isAssignableFrom(obj.getClass())) {
                createMap.putString(str3, TYPE_ARRAY);
                List list = (List) obj;
                createMap.putArray(str2, objectArrayToWritable(list.toArray(new Object[list.size()])));
                return createMap;
            } else if (obj instanceof DocumentReference) {
                createMap.putString(str3, TYPE_REFERENCE);
                createMap.putString(str2, ((DocumentReference) obj).getPath());
                return createMap;
            } else if (obj instanceof Timestamp) {
                createMap2 = Arguments.createMap();
                Timestamp timestamp = (Timestamp) obj;
                createMap2.putDouble(KEY_SECONDS, (double) timestamp.getSeconds());
                createMap2.putInt(KEY_NANOSECONDS, timestamp.getNanoseconds());
                createMap.putString(str3, "timestamp");
                createMap.putMap(str2, createMap2);
                return createMap;
            } else if (obj instanceof GeoPoint) {
                createMap2 = Arguments.createMap();
                GeoPoint geoPoint = (GeoPoint) obj;
                createMap2.putDouble(KEY_LATITUDE, geoPoint.getLatitude());
                createMap2.putDouble(KEY_LONGITUDE, geoPoint.getLongitude());
                createMap.putMap(str2, createMap2);
                createMap.putString(str3, TYPE_GEOPOINT);
                return createMap;
            } else if (obj instanceof Blob) {
                createMap.putString(str3, TYPE_BLOB);
                createMap.putString(str2, Base64.encodeToString(((Blob) obj).toBytes(), 2));
                return createMap;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown object of type ");
                stringBuilder.append(obj.getClass());
                Log.w(TAG, stringBuilder.toString());
                createMap.putString(str3, str);
                createMap.putNull(str2);
                return createMap;
            }
        }
    }

    static Map<String, Object> parseReadableMap(FirebaseFirestore firebaseFirestore, @Nullable ReadableMap readableMap) {
        Map<String, Object> hashMap = new HashMap();
        if (readableMap == null) {
            return hashMap;
        }
        ReadableMapKeySetIterator keySetIterator = readableMap.keySetIterator();
        while (keySetIterator.hasNextKey()) {
            String nextKey = keySetIterator.nextKey();
            hashMap.put(nextKey, parseTypeMap(firebaseFirestore, readableMap.getMap(nextKey)));
        }
        return hashMap;
    }

    static List<Object> parseReadableArray(FirebaseFirestore firebaseFirestore, @Nullable ReadableArray readableArray) {
        List<Object> arrayList = new ArrayList();
        if (readableArray == null) {
            return arrayList;
        }
        for (int i = 0; i < readableArray.size(); i++) {
            arrayList.add(parseTypeMap(firebaseFirestore, readableArray.getMap(i)));
        }
        return arrayList;
    }

    static Object parseTypeMap(FirebaseFirestore firebaseFirestore, ReadableMap readableMap) {
        String str = "type";
        String string = readableMap.getString(str);
        if (TYPE_NULL.equals(string)) {
            return null;
        }
        String str2 = "value";
        if (TYPE_BOOLEAN.equals(string)) {
            return Boolean.valueOf(readableMap.getBoolean(str2));
        }
        if (TYPE_NAN.equals(string)) {
            return Double.valueOf(Double.NaN);
        }
        if (TYPE_NUMBER.equals(string)) {
            return Double.valueOf(readableMap.getDouble(str2));
        }
        if (TYPE_INFINITY.equals(string)) {
            return Double.valueOf(Double.POSITIVE_INFINITY);
        }
        if (TYPE_STRING.equals(string)) {
            return readableMap.getString(str2);
        }
        if (TYPE_ARRAY.equals(string)) {
            return parseReadableArray(firebaseFirestore, readableMap.getArray(str2));
        }
        if (TYPE_OBJECT.equals(string)) {
            return parseReadableMap(firebaseFirestore, readableMap.getMap(str2));
        }
        if (TYPE_DATE.equals(string)) {
            return new Date(Double.valueOf(readableMap.getDouble(str2)).longValue());
        }
        if (TYPE_DOCUMENTID.equals(string)) {
            return FieldPath.documentId();
        }
        ReadableMap map;
        if (TYPE_GEOPOINT.equals(string)) {
            map = readableMap.getMap(str2);
            return new GeoPoint(map.getDouble(KEY_LATITUDE), map.getDouble(KEY_LONGITUDE));
        } else if (TYPE_BLOB.equals(string)) {
            return Blob.fromBytes(Base64.decode(readableMap.getString(str2), 2));
        } else {
            if (TYPE_REFERENCE.equals(string)) {
                return firebaseFirestore.document(readableMap.getString(str2));
            }
            String str3 = "timestamp";
            if (str3.equals(string)) {
                map = readableMap.getMap(str2);
                return new Timestamp((long) map.getDouble(KEY_SECONDS), map.getInt(KEY_NANOSECONDS));
            }
            boolean equals = TYPE_FIELDVALUE.equals(string);
            String str4 = TAG;
            StringBuilder stringBuilder;
            if (equals) {
                readableMap = readableMap.getMap(str2);
                str = readableMap.getString(str);
                if (str3.equals(str)) {
                    return FieldValue.serverTimestamp();
                }
                boolean equals2 = TYPE_FIELDVALUE_INCREMENT.equals(str);
                str3 = TYPE_FIELDVALUE_ELEMENTS;
                if (equals2) {
                    return FieldValue.increment(readableMap.getDouble(str3));
                }
                if (TYPE_FIELDVALUE_DELETE.equals(str)) {
                    return FieldValue.delete();
                }
                if (TYPE_FIELDVALUE_UNION.equals(str)) {
                    return FieldValue.arrayUnion(parseReadableArray(firebaseFirestore, readableMap.getArray(str3)).toArray());
                }
                if (TYPE_FIELDVALUE_REMOVE.equals(str)) {
                    return FieldValue.arrayRemove(parseReadableArray(firebaseFirestore, readableMap.getArray(str3)).toArray());
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown FieldValue type: ");
                stringBuilder.append(str);
                Log.w(str4, stringBuilder.toString());
                return null;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown object of type ");
            stringBuilder.append(string);
            Log.w(str4, stringBuilder.toString());
            return null;
        }
    }

    static List<Object> parseDocumentBatches(FirebaseFirestore firebaseFirestore, ReadableArray readableArray) {
        List<Object> arrayList = new ArrayList(readableArray.size());
        for (int i = 0; i < readableArray.size(); i++) {
            Map hashMap = new HashMap();
            ReadableMap map = readableArray.getMap(i);
            String str = "type";
            hashMap.put(str, map.getString(str));
            str = "path";
            hashMap.put(str, map.getString(str));
            str = "data";
            if (map.hasKey(str)) {
                hashMap.put(str, parseReadableMap(firebaseFirestore, map.getMap(str)));
            }
            str = KEY_OPTIONS;
            if (map.hasKey(str)) {
                hashMap.put(str, Utils.recursivelyDeconstructReadableMap(map.getMap(str)));
            }
            arrayList.add(hashMap);
        }
        return arrayList;
    }
}
