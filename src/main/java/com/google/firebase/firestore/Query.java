package com.google.firebase.firestore;

import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.common.base.Preconditions;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.firestore.FirebaseFirestoreException.Code;
import com.google.firebase.firestore.core.ActivityScope;
import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.Bound;
import com.google.firebase.firestore.core.EventManager.ListenOptions;
import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.core.Filter.Operator;
import com.google.firebase.firestore.core.ListenerRegistrationImpl;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.RelationFilter;
import com.google.firebase.firestore.core.ViewSnapshot;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.ReferenceValue;
import com.google.firebase.firestore.model.value.ServerTimestampValue;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class Query {
    final FirebaseFirestore firestore;
    final com.google.firebase.firestore.core.Query query;

    @PublicApi
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Direction {
        ASCENDING,
        DESCENDING
    }

    Query(com.google.firebase.firestore.core.Query query, FirebaseFirestore firebaseFirestore) {
        this.query = (com.google.firebase.firestore.core.Query) Preconditions.checkNotNull(query);
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firebaseFirestore);
    }

    @PublicApi
    @NonNull
    public FirebaseFirestore getFirestore() {
        return this.firestore;
    }

    private void validateOrderByFieldMatchesInequality(FieldPath fieldPath, FieldPath fieldPath2) {
        if (!fieldPath.equals(fieldPath2)) {
            String canonicalString = fieldPath2.canonicalString();
            throw new IllegalArgumentException(String.format("Invalid query. You have an inequality where filter (whereLessThan(), whereGreaterThan(), etc.) on field '%s' and so you must also have '%s' as your first orderBy() field, but your first orderBy() is currently on field '%s' instead.", new Object[]{canonicalString, canonicalString, fieldPath.canonicalString()}));
        }
    }

    private void validateNewFilter(Filter filter) {
        if (filter instanceof RelationFilter) {
            RelationFilter relationFilter = (RelationFilter) filter;
            if (relationFilter.isInequality()) {
                FieldPath inequalityField = this.query.inequalityField();
                FieldPath field = filter.getField();
                if (inequalityField == null || inequalityField.equals(field)) {
                    inequalityField = this.query.getFirstOrderByField();
                    if (inequalityField != null) {
                        validateOrderByFieldMatchesInequality(inequalityField, field);
                        return;
                    }
                    return;
                }
                throw new IllegalArgumentException(String.format("All where filters other than whereEqualTo() must be on the same field. But you have filters on '%s' and '%s'", new Object[]{inequalityField.canonicalString(), field.canonicalString()}));
            } else if (relationFilter.getOperator() == Operator.ARRAY_CONTAINS && this.query.hasArrayContainsFilter()) {
                throw new IllegalArgumentException("Invalid Query. Queries only support having a single array-contains filter.");
            }
        }
    }

    @PublicApi
    @NonNull
    public Query whereEqualTo(@NonNull String str, @Nullable Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.EQUAL, obj);
    }

    @PublicApi
    @NonNull
    public Query whereEqualTo(@NonNull FieldPath fieldPath, @Nullable Object obj) {
        return whereHelper(fieldPath, Operator.EQUAL, obj);
    }

    @PublicApi
    @NonNull
    public Query whereLessThan(@NonNull String str, @NonNull Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.LESS_THAN, obj);
    }

    @PublicApi
    @NonNull
    public Query whereLessThan(@NonNull FieldPath fieldPath, @NonNull Object obj) {
        return whereHelper(fieldPath, Operator.LESS_THAN, obj);
    }

    @PublicApi
    @NonNull
    public Query whereLessThanOrEqualTo(@NonNull String str, @NonNull Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.LESS_THAN_OR_EQUAL, obj);
    }

    @PublicApi
    @NonNull
    public Query whereLessThanOrEqualTo(@NonNull FieldPath fieldPath, @NonNull Object obj) {
        return whereHelper(fieldPath, Operator.LESS_THAN_OR_EQUAL, obj);
    }

    @PublicApi
    @NonNull
    public Query whereGreaterThan(@NonNull String str, @NonNull Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.GREATER_THAN, obj);
    }

    @PublicApi
    @NonNull
    public Query whereGreaterThan(@NonNull FieldPath fieldPath, @NonNull Object obj) {
        return whereHelper(fieldPath, Operator.GREATER_THAN, obj);
    }

    @PublicApi
    @NonNull
    public Query whereGreaterThanOrEqualTo(@NonNull String str, @NonNull Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.GREATER_THAN_OR_EQUAL, obj);
    }

    @PublicApi
    @NonNull
    public Query whereGreaterThanOrEqualTo(@NonNull FieldPath fieldPath, @NonNull Object obj) {
        return whereHelper(fieldPath, Operator.GREATER_THAN_OR_EQUAL, obj);
    }

    @PublicApi
    @NonNull
    public Query whereArrayContains(@NonNull String str, @NonNull Object obj) {
        return whereHelper(FieldPath.fromDotSeparatedPath(str), Operator.ARRAY_CONTAINS, obj);
    }

    @PublicApi
    @NonNull
    public Query whereArrayContains(@NonNull FieldPath fieldPath, @NonNull Object obj) {
        return whereHelper(fieldPath, Operator.ARRAY_CONTAINS, obj);
    }

    private Query whereHelper(@NonNull FieldPath fieldPath, Operator operator, Object obj) {
        FieldValue parseQueryValue;
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        Preconditions.checkNotNull(operator, "Provided op must not be null.");
        StringBuilder stringBuilder;
        if (!fieldPath.getInternalPath().isKeyField()) {
            parseQueryValue = this.firestore.getDataConverter().parseQueryValue(obj);
        } else if (operator == Operator.ARRAY_CONTAINS) {
            throw new IllegalArgumentException("Invalid query. You can't perform array-contains queries on FieldPath.documentId() since document IDs are not arrays.");
        } else if (obj instanceof String) {
            String str = (String) obj;
            if (str.isEmpty()) {
                throw new IllegalArgumentException("Invalid query. When querying with FieldPath.documentId() you must provide a valid document ID, but it was an empty string.");
            } else if (this.query.isCollectionGroupQuery() || !str.contains("/")) {
                ResourcePath resourcePath = (ResourcePath) this.query.getPath().append(ResourcePath.fromString(str));
                if (DocumentKey.isDocumentKey(resourcePath)) {
                    parseQueryValue = ReferenceValue.valueOf(getFirestore().getDatabaseId(), DocumentKey.fromPath(resourcePath));
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid query. When querying a collection group by FieldPath.documentId(), the value provided must result in a valid document path, but '");
                    stringBuilder.append(resourcePath);
                    stringBuilder.append("' is not because it has an odd number of segments (");
                    stringBuilder.append(resourcePath.length());
                    stringBuilder.append(").");
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid query. When querying a collection by FieldPath.documentId() you must provide a plain document ID, but '");
                stringBuilder.append(str);
                stringBuilder.append("' contains a '/' character.");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        } else if (obj instanceof DocumentReference) {
            parseQueryValue = ReferenceValue.valueOf(getFirestore().getDatabaseId(), ((DocumentReference) obj).getKey());
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid query. When querying with FieldPath.documentId() you must provide a valid String or DocumentReference, but it was of type: ");
            stringBuilder.append(Util.typeName(obj));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        Filter create = Filter.create(fieldPath.getInternalPath(), operator, parseQueryValue);
        validateNewFilter(create);
        return new Query(this.query.filter(create), this.firestore);
    }

    private void validateOrderByField(FieldPath fieldPath) {
        FieldPath inequalityField = this.query.inequalityField();
        if (this.query.getFirstOrderByField() == null && inequalityField != null) {
            validateOrderByFieldMatchesInequality(fieldPath, inequalityField);
        }
    }

    @PublicApi
    @NonNull
    public Query orderBy(@NonNull String str) {
        return orderBy(FieldPath.fromDotSeparatedPath(str), Direction.ASCENDING);
    }

    @PublicApi
    @NonNull
    public Query orderBy(@NonNull FieldPath fieldPath) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        return orderBy(fieldPath.getInternalPath(), Direction.ASCENDING);
    }

    @PublicApi
    @NonNull
    public Query orderBy(@NonNull String str, @NonNull Direction direction) {
        return orderBy(FieldPath.fromDotSeparatedPath(str), direction);
    }

    @PublicApi
    @NonNull
    public Query orderBy(@NonNull FieldPath fieldPath, @NonNull Direction direction) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        return orderBy(fieldPath.getInternalPath(), direction);
    }

    private Query orderBy(@NonNull FieldPath fieldPath, @NonNull Direction direction) {
        Preconditions.checkNotNull(direction, "Provided direction must not be null.");
        if (this.query.getStartAt() != null) {
            throw new IllegalArgumentException("Invalid query. You must not call Query.startAt() or Query.startAfter() before calling Query.orderBy().");
        } else if (this.query.getEndAt() == null) {
            com.google.firebase.firestore.core.OrderBy.Direction direction2;
            validateOrderByField(fieldPath);
            if (direction == Direction.ASCENDING) {
                direction2 = com.google.firebase.firestore.core.OrderBy.Direction.ASCENDING;
            } else {
                direction2 = com.google.firebase.firestore.core.OrderBy.Direction.DESCENDING;
            }
            return new Query(this.query.orderBy(OrderBy.getInstance(direction2, fieldPath)), this.firestore);
        } else {
            throw new IllegalArgumentException("Invalid query. You must not call Query.endAt() or Query.endBefore() before calling Query.orderBy().");
        }
    }

    @PublicApi
    @NonNull
    public Query limit(long j) {
        if (j > 0) {
            return new Query(this.query.limit(j), this.firestore);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Query. Query limit (");
        stringBuilder.append(j);
        stringBuilder.append(") is invalid. Limit must be positive.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @PublicApi
    @NonNull
    public Query startAt(@NonNull DocumentSnapshot documentSnapshot) {
        return new Query(this.query.startAt(boundFromDocumentSnapshot("startAt", documentSnapshot, true)), this.firestore);
    }

    @PublicApi
    @NonNull
    public Query startAt(Object... objArr) {
        return new Query(this.query.startAt(boundFromFields("startAt", objArr, true)), this.firestore);
    }

    @PublicApi
    @NonNull
    public Query startAfter(@NonNull DocumentSnapshot documentSnapshot) {
        return new Query(this.query.startAt(boundFromDocumentSnapshot("startAfter", documentSnapshot, false)), this.firestore);
    }

    @PublicApi
    @NonNull
    public Query startAfter(Object... objArr) {
        return new Query(this.query.startAt(boundFromFields("startAfter", objArr, false)), this.firestore);
    }

    @PublicApi
    @NonNull
    public Query endBefore(@NonNull DocumentSnapshot documentSnapshot) {
        return new Query(this.query.endAt(boundFromDocumentSnapshot("endBefore", documentSnapshot, true)), this.firestore);
    }

    @PublicApi
    @NonNull
    public Query endBefore(Object... objArr) {
        return new Query(this.query.endAt(boundFromFields("endBefore", objArr, true)), this.firestore);
    }

    @PublicApi
    @NonNull
    public Query endAt(@NonNull DocumentSnapshot documentSnapshot) {
        return new Query(this.query.endAt(boundFromDocumentSnapshot("endAt", documentSnapshot, false)), this.firestore);
    }

    @PublicApi
    @NonNull
    public Query endAt(Object... objArr) {
        return new Query(this.query.endAt(boundFromFields("endAt", objArr, false)), this.firestore);
    }

    private Bound boundFromDocumentSnapshot(String str, DocumentSnapshot documentSnapshot, boolean z) {
        Preconditions.checkNotNull(documentSnapshot, "Provided snapshot must not be null.");
        if (documentSnapshot.exists()) {
            Document document = documentSnapshot.getDocument();
            List arrayList = new ArrayList();
            for (OrderBy orderBy : this.query.getOrderBy()) {
                if (orderBy.getField().equals(FieldPath.KEY_PATH)) {
                    arrayList.add(ReferenceValue.valueOf(this.firestore.getDatabaseId(), document.getKey()));
                } else {
                    FieldValue field = document.getField(orderBy.getField());
                    String str2 = "Invalid query. You are trying to start or end a query using a document for which the field '";
                    StringBuilder stringBuilder;
                    if (field instanceof ServerTimestampValue) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str2);
                        stringBuilder.append(orderBy.getField());
                        stringBuilder.append("' is an uncommitted server timestamp. (Since the value of this field is unknown, you cannot start/end a query with it.)");
                        throw new IllegalArgumentException(stringBuilder.toString());
                    } else if (field != null) {
                        arrayList.add(field);
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str2);
                        stringBuilder.append(orderBy.getField());
                        stringBuilder.append("' (used as the orderBy) does not exist.");
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                }
            }
            return new Bound(arrayList, z);
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Can't use a DocumentSnapshot for a document that doesn't exist for ");
        stringBuilder2.append(str);
        stringBuilder2.append("().");
        throw new IllegalArgumentException(stringBuilder2.toString());
    }

    private Bound boundFromFields(String str, Object[] objArr, boolean z) {
        List explicitOrderBy = this.query.getExplicitOrderBy();
        StringBuilder stringBuilder;
        if (objArr.length <= explicitOrderBy.size()) {
            List arrayList = new ArrayList();
            for (int i = 0; i < objArr.length; i++) {
                Object obj = objArr[i];
                if (!((OrderBy) explicitOrderBy.get(i)).getField().equals(FieldPath.KEY_PATH)) {
                    arrayList.add(this.firestore.getDataConverter().parseQueryValue(obj));
                } else if (obj instanceof String) {
                    String str2 = (String) obj;
                    if (this.query.isCollectionGroupQuery() || !str2.contains("/")) {
                        ResourcePath resourcePath = (ResourcePath) this.query.getPath().append(ResourcePath.fromString(str2));
                        if (DocumentKey.isDocumentKey(resourcePath)) {
                            arrayList.add(ReferenceValue.valueOf(this.firestore.getDatabaseId(), DocumentKey.fromPath(resourcePath)));
                        } else {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid query. When querying a collection group and ordering by FieldPath.documentId(), the value passed to ");
                            stringBuilder.append(str);
                            stringBuilder.append("() must result in a valid document path, but '");
                            stringBuilder.append(resourcePath);
                            stringBuilder.append("' is not because it contains an odd number of segments.");
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid query. When querying a collection and ordering by FieldPath.documentId(), the value passed to ");
                    stringBuilder.append(str);
                    stringBuilder.append("() must be a plain document ID, but '");
                    stringBuilder.append(str2);
                    stringBuilder.append("' contains a slash.");
                    throw new IllegalArgumentException(stringBuilder.toString());
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid query. Expected a string for document ID in ");
                    stringBuilder.append(str);
                    stringBuilder.append("(), but got ");
                    stringBuilder.append(obj);
                    stringBuilder.append(".");
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            return new Bound(arrayList, z);
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Too many arguments provided to ");
        stringBuilder.append(str);
        stringBuilder.append("(). The number of arguments must be less than or equal to the number of orderBy() clauses.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @PublicApi
    @NonNull
    public Task<QuerySnapshot> get() {
        return get(Source.DEFAULT);
    }

    @PublicApi
    @NonNull
    public Task<QuerySnapshot> get(Source source) {
        if (source == Source.CACHE) {
            return this.firestore.getClient().getDocumentsFromLocalCache(this.query).continueWith(Executors.DIRECT_EXECUTOR, Query$$Lambda$1.lambdaFactory$(this));
        }
        return getViaSnapshotListener(source);
    }

    private Task<QuerySnapshot> getViaSnapshotListener(Source source) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        TaskCompletionSource taskCompletionSource2 = new TaskCompletionSource();
        ListenOptions listenOptions = new ListenOptions();
        listenOptions.includeDocumentMetadataChanges = true;
        listenOptions.includeQueryMetadataChanges = true;
        listenOptions.waitForSyncWhenOnline = true;
        taskCompletionSource2.setResult(addSnapshotListenerInternal(Executors.DIRECT_EXECUTOR, listenOptions, null, Query$$Lambda$2.lambdaFactory$(taskCompletionSource, taskCompletionSource2, source)));
        return taskCompletionSource.getTask();
    }

    static /* synthetic */ void lambda$getViaSnapshotListener$1(TaskCompletionSource taskCompletionSource, TaskCompletionSource taskCompletionSource2, Source source, QuerySnapshot querySnapshot, FirebaseFirestoreException firebaseFirestoreException) {
        String str = "Failed to register a listener for a query result";
        if (firebaseFirestoreException != null) {
            taskCompletionSource.setException(firebaseFirestoreException);
            return;
        }
        try {
            ((ListenerRegistration) Tasks.await(taskCompletionSource2.getTask())).remove();
            if (querySnapshot.getMetadata().isFromCache() && source == Source.SERVER) {
                taskCompletionSource.setException(new FirebaseFirestoreException("Failed to get documents from server. (However, these documents may exist in the local cache. Run again without setting source to SERVER to retrieve the cached documents.)", Code.UNAVAILABLE));
            } else {
                taskCompletionSource.setResult(querySnapshot);
            }
        } catch (Throwable e) {
            throw Assert.fail(e, str, new Object[0]);
        } catch (Throwable e2) {
            Thread.currentThread().interrupt();
            throw Assert.fail(e2, str, new Object[0]);
        }
    }

    @PublicApi
    @NonNull
    public ListenerRegistration addSnapshotListener(@NonNull EventListener<QuerySnapshot> eventListener) {
        return addSnapshotListener(MetadataChanges.EXCLUDE, (EventListener) eventListener);
    }

    @PublicApi
    @NonNull
    public ListenerRegistration addSnapshotListener(@NonNull Executor executor, @NonNull EventListener<QuerySnapshot> eventListener) {
        return addSnapshotListener(executor, MetadataChanges.EXCLUDE, (EventListener) eventListener);
    }

    @PublicApi
    @NonNull
    public ListenerRegistration addSnapshotListener(@NonNull Activity activity, @NonNull EventListener<QuerySnapshot> eventListener) {
        return addSnapshotListener(activity, MetadataChanges.EXCLUDE, (EventListener) eventListener);
    }

    @PublicApi
    @NonNull
    public ListenerRegistration addSnapshotListener(@NonNull MetadataChanges metadataChanges, @NonNull EventListener<QuerySnapshot> eventListener) {
        return addSnapshotListener(Executors.DEFAULT_CALLBACK_EXECUTOR, metadataChanges, (EventListener) eventListener);
    }

    @PublicApi
    @NonNull
    public ListenerRegistration addSnapshotListener(@NonNull Executor executor, @NonNull MetadataChanges metadataChanges, @NonNull EventListener<QuerySnapshot> eventListener) {
        Preconditions.checkNotNull(executor, "Provided executor must not be null.");
        Preconditions.checkNotNull(metadataChanges, "Provided MetadataChanges value must not be null.");
        Preconditions.checkNotNull(eventListener, "Provided EventListener must not be null.");
        return addSnapshotListenerInternal(executor, internalOptions(metadataChanges), null, eventListener);
    }

    @PublicApi
    @NonNull
    public ListenerRegistration addSnapshotListener(@NonNull Activity activity, @NonNull MetadataChanges metadataChanges, @NonNull EventListener<QuerySnapshot> eventListener) {
        Preconditions.checkNotNull(activity, "Provided activity must not be null.");
        Preconditions.checkNotNull(metadataChanges, "Provided MetadataChanges value must not be null.");
        Preconditions.checkNotNull(eventListener, "Provided EventListener must not be null.");
        return addSnapshotListenerInternal(Executors.DEFAULT_CALLBACK_EXECUTOR, internalOptions(metadataChanges), activity, eventListener);
    }

    private ListenerRegistration addSnapshotListenerInternal(Executor executor, ListenOptions listenOptions, @Nullable Activity activity, EventListener<QuerySnapshot> eventListener) {
        Object asyncEventListener = new AsyncEventListener(executor, Query$$Lambda$3.lambdaFactory$(this, eventListener));
        return ActivityScope.bind(activity, new ListenerRegistrationImpl(this.firestore.getClient(), this.firestore.getClient().listen(this.query, listenOptions, asyncEventListener), asyncEventListener));
    }

    static /* synthetic */ void lambda$addSnapshotListenerInternal$2(Query query, EventListener eventListener, ViewSnapshot viewSnapshot, FirebaseFirestoreException firebaseFirestoreException) {
        if (firebaseFirestoreException != null) {
            eventListener.onEvent(null, firebaseFirestoreException);
            return;
        }
        Assert.hardAssert(viewSnapshot != null, "Got event without value or error set", new Object[0]);
        eventListener.onEvent(new QuerySnapshot(query, viewSnapshot, query.firestore), null);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Query)) {
            return false;
        }
        Query query = (Query) obj;
        if (!(this.query.equals(query.query) && this.firestore.equals(query.firestore))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (this.query.hashCode() * 31) + this.firestore.hashCode();
    }

    private static ListenOptions internalOptions(MetadataChanges metadataChanges) {
        ListenOptions listenOptions = new ListenOptions();
        boolean z = true;
        listenOptions.includeDocumentMetadataChanges = metadataChanges == MetadataChanges.INCLUDE;
        if (metadataChanges != MetadataChanges.INCLUDE) {
            z = false;
        }
        listenOptions.includeQueryMetadataChanges = z;
        listenOptions.waitForSyncWhenOnline = false;
        return listenOptions;
    }
}
