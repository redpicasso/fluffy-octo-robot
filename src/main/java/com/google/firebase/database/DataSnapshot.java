package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import java.util.Iterator;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DataSnapshot {
    private final IndexedNode node;
    private final DatabaseReference query;

    DataSnapshot(DatabaseReference databaseReference, IndexedNode indexedNode) {
        this.node = indexedNode;
        this.query = databaseReference;
    }

    @PublicApi
    @NonNull
    public DataSnapshot child(@NonNull String str) {
        return new DataSnapshot(this.query.child(str), IndexedNode.from(this.node.getNode().getChild(new Path(str))));
    }

    @PublicApi
    public boolean hasChild(@NonNull String str) {
        if (this.query.getParent() == null) {
            Validation.validateRootPathString(str);
        } else {
            Validation.validatePathString(str);
        }
        return this.node.getNode().getChild(new Path(str)).isEmpty() ^ 1;
    }

    @PublicApi
    public boolean hasChildren() {
        return this.node.getNode().getChildCount() > 0;
    }

    @PublicApi
    public boolean exists() {
        return this.node.getNode().isEmpty() ^ 1;
    }

    @PublicApi
    @Nullable
    public Object getValue() {
        return this.node.getNode().getValue();
    }

    @PublicApi
    @Nullable
    public Object getValue(boolean z) {
        return this.node.getNode().getValue(z);
    }

    @PublicApi
    @Nullable
    public <T> T getValue(@NonNull Class<T> cls) {
        return CustomClassMapper.convertToCustomClass(this.node.getNode().getValue(), (Class) cls);
    }

    @PublicApi
    @Nullable
    public <T> T getValue(@NonNull GenericTypeIndicator<T> genericTypeIndicator) {
        return CustomClassMapper.convertToCustomClass(this.node.getNode().getValue(), (GenericTypeIndicator) genericTypeIndicator);
    }

    @PublicApi
    public long getChildrenCount() {
        return (long) this.node.getNode().getChildCount();
    }

    @PublicApi
    @NonNull
    public DatabaseReference getRef() {
        return this.query;
    }

    @PublicApi
    @Nullable
    public String getKey() {
        return this.query.getKey();
    }

    @PublicApi
    @NonNull
    public Iterable<DataSnapshot> getChildren() {
        final Iterator it = this.node.iterator();
        return new Iterable<DataSnapshot>() {
            public Iterator<DataSnapshot> iterator() {
                return new Iterator<DataSnapshot>() {
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @NonNull
                    public DataSnapshot next() {
                        NamedNode namedNode = (NamedNode) it.next();
                        return new DataSnapshot(DataSnapshot.this.query.child(namedNode.getName().asString()), IndexedNode.from(namedNode.getNode()));
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("remove called on immutable collection");
                    }
                };
            }
        };
    }

    @PublicApi
    @Nullable
    public Object getPriority() {
        Object value = this.node.getNode().getPriority().getValue();
        return value instanceof Long ? Double.valueOf((double) ((Long) value).longValue()) : value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataSnapshot { key = ");
        stringBuilder.append(this.query.getKey());
        stringBuilder.append(", value = ");
        stringBuilder.append(this.node.getNode().getValue(true));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
