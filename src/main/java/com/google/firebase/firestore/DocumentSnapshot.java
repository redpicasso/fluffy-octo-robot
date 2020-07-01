package com.google.firebase.firestore;

import androidx.annotation.NonNull;
import com.google.common.base.Preconditions;
import com.google.firebase.Timestamp;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.value.ArrayValue;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.FieldValueOptions;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.model.value.ReferenceValue;
import com.google.firebase.firestore.util.CustomClassMapper;
import com.google.firebase.firestore.util.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class DocumentSnapshot {
    @Nullable
    private final Document doc;
    private final FirebaseFirestore firestore;
    private final DocumentKey key;
    private final SnapshotMetadata metadata;

    @PublicApi
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum ServerTimestampBehavior {
        NONE,
        ESTIMATE,
        PREVIOUS;
        
        static final ServerTimestampBehavior DEFAULT = null;

        static {
            DEFAULT = r4;
        }
    }

    DocumentSnapshot(FirebaseFirestore firebaseFirestore, DocumentKey documentKey, @Nullable Document document, boolean z, boolean z2) {
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firebaseFirestore);
        this.key = (DocumentKey) Preconditions.checkNotNull(documentKey);
        this.doc = document;
        this.metadata = new SnapshotMetadata(z2, z);
    }

    static DocumentSnapshot fromDocument(FirebaseFirestore firebaseFirestore, Document document, boolean z, boolean z2) {
        return new DocumentSnapshot(firebaseFirestore, document.getKey(), document, z, z2);
    }

    static DocumentSnapshot fromNoDocument(FirebaseFirestore firebaseFirestore, DocumentKey documentKey, boolean z, boolean z2) {
        return new DocumentSnapshot(firebaseFirestore, documentKey, null, z, z2);
    }

    @PublicApi
    @NonNull
    public String getId() {
        return this.key.getPath().getLastSegment();
    }

    @PublicApi
    @NonNull
    public SnapshotMetadata getMetadata() {
        return this.metadata;
    }

    @PublicApi
    public boolean exists() {
        return this.doc != null;
    }

    @Nullable
    Document getDocument() {
        return this.doc;
    }

    @PublicApi
    @Nullable
    public Map<String, Object> getData() {
        return getData(ServerTimestampBehavior.DEFAULT);
    }

    @PublicApi
    @Nullable
    public Map<String, Object> getData(@NonNull ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        Document document = this.doc;
        if (document == null) {
            return null;
        }
        return convertObject(document.getData(), FieldValueOptions.create(serverTimestampBehavior, this.firestore.getFirestoreSettings().areTimestampsInSnapshotsEnabled()));
    }

    @PublicApi
    @Nullable
    public <T> T toObject(@NonNull Class<T> cls) {
        return toObject(cls, ServerTimestampBehavior.DEFAULT);
    }

    @PublicApi
    @Nullable
    public <T> T toObject(@NonNull Class<T> cls, @NonNull ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(cls, "Provided POJO type must not be null.");
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        Map data = getData(serverTimestampBehavior);
        if (data == null) {
            return null;
        }
        return CustomClassMapper.convertToCustomClass(data, cls);
    }

    @PublicApi
    public boolean contains(@NonNull String str) {
        return contains(FieldPath.fromDotSeparatedPath(str));
    }

    @PublicApi
    public boolean contains(@NonNull FieldPath fieldPath) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        Document document = this.doc;
        return (document == null || document.getField(fieldPath.getInternalPath()) == null) ? false : true;
    }

    @PublicApi
    @Nullable
    public Object get(@NonNull String str) {
        return get(FieldPath.fromDotSeparatedPath(str), ServerTimestampBehavior.DEFAULT);
    }

    @PublicApi
    @Nullable
    public Object get(@NonNull String str, @NonNull ServerTimestampBehavior serverTimestampBehavior) {
        return get(FieldPath.fromDotSeparatedPath(str), serverTimestampBehavior);
    }

    @PublicApi
    @Nullable
    public Object get(@NonNull FieldPath fieldPath) {
        return get(fieldPath, ServerTimestampBehavior.DEFAULT);
    }

    @PublicApi
    @Nullable
    public Object get(@NonNull FieldPath fieldPath, @NonNull ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        return getInternal(fieldPath.getInternalPath(), FieldValueOptions.create(serverTimestampBehavior, this.firestore.getFirestoreSettings().areTimestampsInSnapshotsEnabled()));
    }

    @PublicApi
    @Nullable
    public <T> T get(@NonNull String str, @NonNull Class<T> cls) {
        return get(FieldPath.fromDotSeparatedPath(str), (Class) cls, ServerTimestampBehavior.DEFAULT);
    }

    @PublicApi
    @Nullable
    public <T> T get(@NonNull String str, @NonNull Class<T> cls, @NonNull ServerTimestampBehavior serverTimestampBehavior) {
        return get(FieldPath.fromDotSeparatedPath(str), (Class) cls, serverTimestampBehavior);
    }

    @PublicApi
    @Nullable
    public <T> T get(@NonNull FieldPath fieldPath, @NonNull Class<T> cls) {
        return get(fieldPath, (Class) cls, ServerTimestampBehavior.DEFAULT);
    }

    @PublicApi
    @Nullable
    public <T> T get(@NonNull FieldPath fieldPath, @NonNull Class<T> cls, @NonNull ServerTimestampBehavior serverTimestampBehavior) {
        Object obj = get(fieldPath, serverTimestampBehavior);
        if (obj == null) {
            return null;
        }
        return CustomClassMapper.convertToCustomClass(obj, cls);
    }

    @PublicApi
    @Nullable
    public Boolean getBoolean(@NonNull String str) {
        return (Boolean) getTypedValue(str, Boolean.class);
    }

    @PublicApi
    @Nullable
    public Double getDouble(@NonNull String str) {
        Number number = (Number) getTypedValue(str, Number.class);
        return number != null ? Double.valueOf(number.doubleValue()) : null;
    }

    @PublicApi
    @Nullable
    public String getString(@NonNull String str) {
        return (String) getTypedValue(str, String.class);
    }

    @PublicApi
    @Nullable
    public Long getLong(@NonNull String str) {
        Number number = (Number) getTypedValue(str, Number.class);
        return number != null ? Long.valueOf(number.longValue()) : null;
    }

    @PublicApi
    @Nullable
    public Date getDate(@NonNull String str) {
        return getDate(str, ServerTimestampBehavior.DEFAULT);
    }

    @PublicApi
    @Nullable
    public Date getDate(@NonNull String str, @NonNull ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(str, "Provided field path must not be null.");
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        return (Date) castTypedValue(getInternal(FieldPath.fromDotSeparatedPath(str).getInternalPath(), FieldValueOptions.create(serverTimestampBehavior, false)), str, Date.class);
    }

    @PublicApi
    @Nullable
    public Timestamp getTimestamp(@NonNull String str) {
        return getTimestamp(str, ServerTimestampBehavior.DEFAULT);
    }

    @PublicApi
    @Nullable
    public Timestamp getTimestamp(@NonNull String str, @NonNull ServerTimestampBehavior serverTimestampBehavior) {
        Preconditions.checkNotNull(str, "Provided field path must not be null.");
        Preconditions.checkNotNull(serverTimestampBehavior, "Provided serverTimestampBehavior value must not be null.");
        return (Timestamp) castTypedValue(getInternal(FieldPath.fromDotSeparatedPath(str).getInternalPath(), FieldValueOptions.create(serverTimestampBehavior, true)), str, Timestamp.class);
    }

    @PublicApi
    @Nullable
    public Blob getBlob(@NonNull String str) {
        return (Blob) getTypedValue(str, Blob.class);
    }

    @PublicApi
    @Nullable
    public GeoPoint getGeoPoint(@NonNull String str) {
        return (GeoPoint) getTypedValue(str, GeoPoint.class);
    }

    @PublicApi
    @Nullable
    public DocumentReference getDocumentReference(@NonNull String str) {
        return (DocumentReference) getTypedValue(str, DocumentReference.class);
    }

    @PublicApi
    @NonNull
    public DocumentReference getReference() {
        return new DocumentReference(this.key, this.firestore);
    }

    @Nullable
    private <T> T getTypedValue(String str, Class<T> cls) {
        Preconditions.checkNotNull(str, "Provided field must not be null.");
        return castTypedValue(get(str, ServerTimestampBehavior.DEFAULT), str, cls);
    }

    @Nullable
    private <T> T castTypedValue(Object obj, String str, Class<T> cls) {
        if (obj == null) {
            return null;
        }
        if (cls.isInstance(obj)) {
            return cls.cast(obj);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Field '");
        stringBuilder.append(str);
        stringBuilder.append("' is not a ");
        stringBuilder.append(cls.getName());
        throw new RuntimeException(stringBuilder.toString());
    }

    @Nullable
    private Object convertValue(FieldValue fieldValue, FieldValueOptions fieldValueOptions) {
        if (fieldValue instanceof ObjectValue) {
            return convertObject((ObjectValue) fieldValue, fieldValueOptions);
        }
        if (fieldValue instanceof ArrayValue) {
            return convertArray((ArrayValue) fieldValue, fieldValueOptions);
        }
        if (!(fieldValue instanceof ReferenceValue)) {
            return fieldValue.value(fieldValueOptions);
        }
        ReferenceValue referenceValue = (ReferenceValue) fieldValue;
        DocumentKey documentKey = (DocumentKey) referenceValue.value(fieldValueOptions);
        if (!referenceValue.getDatabaseId().equals(this.firestore.getDatabaseId())) {
            Logger.warn("DocumentSnapshot", "Document %s contains a document reference within a different database (%s/%s) which is not supported. It will be treated as a reference in the current database (%s/%s) instead.", documentKey.getPath(), referenceValue.getDatabaseId().getProjectId(), referenceValue.getDatabaseId().getDatabaseId(), this.firestore.getDatabaseId().getProjectId(), this.firestore.getDatabaseId().getDatabaseId());
        }
        return new DocumentReference(documentKey, this.firestore);
    }

    private Map<String, Object> convertObject(ObjectValue objectValue, FieldValueOptions fieldValueOptions) {
        Map<String, Object> hashMap = new HashMap();
        Iterator it = objectValue.getInternalValue().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            hashMap.put((String) entry.getKey(), convertValue((FieldValue) entry.getValue(), fieldValueOptions));
        }
        return hashMap;
    }

    private List<Object> convertArray(ArrayValue arrayValue, FieldValueOptions fieldValueOptions) {
        List arrayList = new ArrayList(arrayValue.getInternalValue().size());
        for (FieldValue convertValue : arrayValue.getInternalValue()) {
            arrayList.add(convertValue(convertValue, fieldValueOptions));
        }
        return arrayList;
    }

    @Nullable
    private Object getInternal(@NonNull FieldPath fieldPath, @NonNull FieldValueOptions fieldValueOptions) {
        Document document = this.doc;
        if (document != null) {
            FieldValue field = document.getField(fieldPath);
            if (field != null) {
                return convertValue(field, fieldValueOptions);
            }
        }
        return null;
    }

    /* JADX WARNING: Missing block: B:17:0x0039, code:
            if (r4.metadata.equals(r5.metadata) != false) goto L_0x003d;
     */
    public boolean equals(@javax.annotation.Nullable java.lang.Object r5) {
        /*
        r4 = this;
        r0 = 1;
        if (r4 != r5) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = r5 instanceof com.google.firebase.firestore.DocumentSnapshot;
        r2 = 0;
        if (r1 != 0) goto L_0x000a;
    L_0x0009:
        return r2;
    L_0x000a:
        r5 = (com.google.firebase.firestore.DocumentSnapshot) r5;
        r1 = r4.firestore;
        r3 = r5.firestore;
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x003c;
    L_0x0016:
        r1 = r4.key;
        r3 = r5.key;
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x003c;
    L_0x0020:
        r1 = r4.doc;
        if (r1 != 0) goto L_0x0029;
    L_0x0024:
        r1 = r5.doc;
        if (r1 != 0) goto L_0x003c;
    L_0x0028:
        goto L_0x0031;
    L_0x0029:
        r3 = r5.doc;
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x003c;
    L_0x0031:
        r1 = r4.metadata;
        r5 = r5.metadata;
        r5 = r1.equals(r5);
        if (r5 == 0) goto L_0x003c;
    L_0x003b:
        goto L_0x003d;
    L_0x003c:
        r0 = 0;
    L_0x003d:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.DocumentSnapshot.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int hashCode = ((this.firestore.hashCode() * 31) + this.key.hashCode()) * 31;
        Document document = this.doc;
        return ((hashCode + (document != null ? document.hashCode() : 0)) * 31) + this.metadata.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DocumentSnapshot{key=");
        stringBuilder.append(this.key);
        stringBuilder.append(", metadata=");
        stringBuilder.append(this.metadata);
        stringBuilder.append(", doc=");
        stringBuilder.append(this.doc);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
