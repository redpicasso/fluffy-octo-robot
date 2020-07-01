package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import com.google.android.gms.common.internal.Objects;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.database.core.ChildEventRegistration;
import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.Repo;
import com.google.firebase.database.core.ValueEventRegistration;
import com.google.firebase.database.core.ZombieEventManager;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.KeyIndex;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.PathIndex;
import com.google.firebase.database.snapshot.PriorityIndex;
import com.google.firebase.database.snapshot.PriorityUtilities;
import com.google.firebase.database.snapshot.StringNode;
import com.google.firebase.database.snapshot.ValueIndex;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Query {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final boolean orderByCalled;
    protected final QueryParams params;
    protected final Path path;
    protected final Repo repo;

    Query(Repo repo, Path path, QueryParams queryParams, boolean z) throws DatabaseException {
        this.repo = repo;
        this.path = path;
        this.params = queryParams;
        this.orderByCalled = z;
        Utilities.hardAssert(queryParams.isValid(), "Validation of queries failed.");
    }

    Query(Repo repo, Path path) {
        this.repo = repo;
        this.path = path;
        this.params = QueryParams.DEFAULT_PARAMS;
        this.orderByCalled = false;
    }

    private void validateQueryEndpoints(QueryParams queryParams) {
        if (queryParams.getIndex().equals(KeyIndex.getInstance())) {
            Node indexStartValue;
            String str = "You must use startAt(String value), endAt(String value) or equalTo(String value) in combination with orderByKey(). Other type of values or using the version with 2 parameters is not supported";
            if (queryParams.hasStart()) {
                indexStartValue = queryParams.getIndexStartValue();
                if (!(Objects.equal(queryParams.getIndexStartName(), ChildKey.getMinName()) && (indexStartValue instanceof StringNode))) {
                    throw new IllegalArgumentException(str);
                }
            }
            if (queryParams.hasEnd()) {
                indexStartValue = queryParams.getIndexEndValue();
                if (!queryParams.getIndexEndName().equals(ChildKey.getMaxName()) || !(indexStartValue instanceof StringNode)) {
                    throw new IllegalArgumentException(str);
                }
            }
        } else if (!queryParams.getIndex().equals(PriorityIndex.getInstance())) {
        } else {
            if ((queryParams.hasStart() && !PriorityUtilities.isValidPriority(queryParams.getIndexStartValue())) || (queryParams.hasEnd() && !PriorityUtilities.isValidPriority(queryParams.getIndexEndValue()))) {
                throw new IllegalArgumentException("When using orderByPriority(), values provided to startAt(), endAt(), or equalTo() must be valid priorities.");
            }
        }
    }

    private void validateLimit(QueryParams queryParams) {
        if (queryParams.hasStart() && queryParams.hasEnd() && queryParams.hasLimit() && !queryParams.hasAnchoredLimit()) {
            throw new IllegalArgumentException("Can't combine startAt(), endAt() and limit(). Use limitToFirst() or limitToLast() instead");
        }
    }

    private void validateEqualToCall() {
        if (this.params.hasStart()) {
            throw new IllegalArgumentException("Can't call equalTo() and startAt() combined");
        } else if (this.params.hasEnd()) {
            throw new IllegalArgumentException("Can't call equalTo() and endAt() combined");
        }
    }

    private void validateNoOrderByCall() {
        if (this.orderByCalled) {
            throw new IllegalArgumentException("You can't combine multiple orderBy calls!");
        }
    }

    @PublicApi
    @NonNull
    public ValueEventListener addValueEventListener(@NonNull ValueEventListener valueEventListener) {
        addEventRegistration(new ValueEventRegistration(this.repo, valueEventListener, getSpec()));
        return valueEventListener;
    }

    @PublicApi
    @NonNull
    public ChildEventListener addChildEventListener(@NonNull ChildEventListener childEventListener) {
        addEventRegistration(new ChildEventRegistration(this.repo, childEventListener, getSpec()));
        return childEventListener;
    }

    @PublicApi
    public void addListenerForSingleValueEvent(@NonNull final ValueEventListener valueEventListener) {
        addEventRegistration(new ValueEventRegistration(this.repo, new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Query.this.removeEventListener((ValueEventListener) this);
                valueEventListener.onDataChange(dataSnapshot);
            }

            public void onCancelled(DatabaseError databaseError) {
                valueEventListener.onCancelled(databaseError);
            }
        }, getSpec()));
    }

    @PublicApi
    public void removeEventListener(@NonNull ValueEventListener valueEventListener) {
        if (valueEventListener != null) {
            removeEventRegistration(new ValueEventRegistration(this.repo, valueEventListener, getSpec()));
            return;
        }
        throw new NullPointerException("listener must not be null");
    }

    @PublicApi
    public void removeEventListener(@NonNull ChildEventListener childEventListener) {
        if (childEventListener != null) {
            removeEventRegistration(new ChildEventRegistration(this.repo, childEventListener, getSpec()));
            return;
        }
        throw new NullPointerException("listener must not be null");
    }

    private void removeEventRegistration(final EventRegistration eventRegistration) {
        ZombieEventManager.getInstance().zombifyForRemove(eventRegistration);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                Query.this.repo.removeEventCallback(eventRegistration);
            }
        });
    }

    private void addEventRegistration(final EventRegistration eventRegistration) {
        ZombieEventManager.getInstance().recordEventRegistration(eventRegistration);
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                Query.this.repo.addEventCallback(eventRegistration);
            }
        });
    }

    @PublicApi
    public void keepSynced(final boolean z) {
        if (this.path.isEmpty() || !this.path.getFront().equals(ChildKey.getInfoKey())) {
            this.repo.scheduleNow(new Runnable() {
                public void run() {
                    Query.this.repo.keepSynced(Query.this.getSpec(), z);
                }
            });
            return;
        }
        throw new DatabaseException("Can't call keepSynced() on .info paths.");
    }

    @PublicApi
    @NonNull
    public Query startAt(@Nullable String str) {
        return startAt(str, null);
    }

    @PublicApi
    @NonNull
    public Query startAt(double d) {
        return startAt(d, null);
    }

    @PublicApi
    @NonNull
    public Query startAt(boolean z) {
        return startAt(z, null);
    }

    @PublicApi
    @NonNull
    public Query startAt(@Nullable String str, @Nullable String str2) {
        return startAt(str != null ? new StringNode(str, PriorityUtilities.NullPriority()) : EmptyNode.Empty(), str2);
    }

    @PublicApi
    @NonNull
    public Query startAt(double d, @Nullable String str) {
        return startAt(new DoubleNode(Double.valueOf(d), PriorityUtilities.NullPriority()), str);
    }

    @PublicApi
    @NonNull
    public Query startAt(boolean z, @Nullable String str) {
        return startAt(new BooleanNode(Boolean.valueOf(z), PriorityUtilities.NullPriority()), str);
    }

    private Query startAt(Node node, String str) {
        Validation.validateNullableKey(str);
        if (!node.isLeafNode() && !node.isEmpty()) {
            throw new IllegalArgumentException("Can only use simple values for startAt()");
        } else if (this.params.hasStart()) {
            throw new IllegalArgumentException("Can't call startAt() or equalTo() multiple times");
        } else {
            QueryParams startAt = this.params.startAt(node, str != null ? ChildKey.fromString(str) : null);
            validateLimit(startAt);
            validateQueryEndpoints(startAt);
            return new Query(this.repo, this.path, startAt, this.orderByCalled);
        }
    }

    @PublicApi
    @NonNull
    public Query endAt(@Nullable String str) {
        return endAt(str, null);
    }

    @PublicApi
    @NonNull
    public Query endAt(double d) {
        return endAt(d, null);
    }

    @PublicApi
    @NonNull
    public Query endAt(boolean z) {
        return endAt(z, null);
    }

    @PublicApi
    @NonNull
    public Query endAt(@Nullable String str, @Nullable String str2) {
        return endAt(str != null ? new StringNode(str, PriorityUtilities.NullPriority()) : EmptyNode.Empty(), str2);
    }

    @PublicApi
    @NonNull
    public Query endAt(double d, @Nullable String str) {
        return endAt(new DoubleNode(Double.valueOf(d), PriorityUtilities.NullPriority()), str);
    }

    @PublicApi
    @NonNull
    public Query endAt(boolean z, @Nullable String str) {
        return endAt(new BooleanNode(Boolean.valueOf(z), PriorityUtilities.NullPriority()), str);
    }

    private Query endAt(Node node, String str) {
        Validation.validateNullableKey(str);
        if (node.isLeafNode() || node.isEmpty()) {
            ChildKey fromString = str != null ? ChildKey.fromString(str) : null;
            if (this.params.hasEnd()) {
                throw new IllegalArgumentException("Can't call endAt() or equalTo() multiple times");
            }
            QueryParams endAt = this.params.endAt(node, fromString);
            validateLimit(endAt);
            validateQueryEndpoints(endAt);
            return new Query(this.repo, this.path, endAt, this.orderByCalled);
        }
        throw new IllegalArgumentException("Can only use simple values for endAt()");
    }

    @PublicApi
    @NonNull
    public Query equalTo(@Nullable String str) {
        validateEqualToCall();
        return startAt(str).endAt(str);
    }

    @PublicApi
    @NonNull
    public Query equalTo(double d) {
        validateEqualToCall();
        return startAt(d).endAt(d);
    }

    @PublicApi
    @NonNull
    public Query equalTo(boolean z) {
        validateEqualToCall();
        return startAt(z).endAt(z);
    }

    @PublicApi
    @NonNull
    public Query equalTo(@Nullable String str, @Nullable String str2) {
        validateEqualToCall();
        return startAt(str, str2).endAt(str, str2);
    }

    @PublicApi
    @NonNull
    public Query equalTo(double d, @Nullable String str) {
        validateEqualToCall();
        return startAt(d, str).endAt(d, str);
    }

    @PublicApi
    @NonNull
    public Query equalTo(boolean z, @Nullable String str) {
        validateEqualToCall();
        return startAt(z, str).endAt(z, str);
    }

    @PublicApi
    @NonNull
    public Query limitToFirst(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Limit must be a positive integer!");
        } else if (!this.params.hasLimit()) {
            return new Query(this.repo, this.path, this.params.limitToFirst(i), this.orderByCalled);
        } else {
            throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
        }
    }

    @PublicApi
    @NonNull
    public Query limitToLast(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Limit must be a positive integer!");
        } else if (!this.params.hasLimit()) {
            return new Query(this.repo, this.path, this.params.limitToLast(i), this.orderByCalled);
        } else {
            throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
        }
    }

    @PublicApi
    @NonNull
    public Query orderByChild(@NonNull String str) {
        if (str != null) {
            String str2 = "Can't use '";
            StringBuilder stringBuilder;
            if (str.equals("$key") || str.equals(".key")) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(str);
                stringBuilder.append("' as path, please use orderByKey() instead!");
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (str.equals("$priority") || str.equals(".priority")) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(str);
                stringBuilder.append("' as path, please use orderByPriority() instead!");
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (str.equals("$value") || str.equals(".value")) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(str);
                stringBuilder.append("' as path, please use orderByValue() instead!");
                throw new IllegalArgumentException(stringBuilder.toString());
            } else {
                Validation.validatePathString(str);
                validateNoOrderByCall();
                Path path = new Path(str);
                if (path.size() != 0) {
                    return new Query(this.repo, this.path, this.params.orderBy(new PathIndex(path)), true);
                }
                throw new IllegalArgumentException("Can't use empty path, use orderByValue() instead!");
            }
        }
        throw new NullPointerException("Key can't be null");
    }

    @PublicApi
    @NonNull
    public Query orderByPriority() {
        validateNoOrderByCall();
        QueryParams orderBy = this.params.orderBy(PriorityIndex.getInstance());
        validateQueryEndpoints(orderBy);
        return new Query(this.repo, this.path, orderBy, true);
    }

    @PublicApi
    @NonNull
    public Query orderByKey() {
        validateNoOrderByCall();
        QueryParams orderBy = this.params.orderBy(KeyIndex.getInstance());
        validateQueryEndpoints(orderBy);
        return new Query(this.repo, this.path, orderBy, true);
    }

    @PublicApi
    @NonNull
    public Query orderByValue() {
        validateNoOrderByCall();
        return new Query(this.repo, this.path, this.params.orderBy(ValueIndex.getInstance()), true);
    }

    @PublicApi
    @NonNull
    public DatabaseReference getRef() {
        return new DatabaseReference(this.repo, getPath());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public Path getPath() {
        return this.path;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public Repo getRepo() {
        return this.repo;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public QuerySpec getSpec() {
        return new QuerySpec(this.path, this.params);
    }
}
