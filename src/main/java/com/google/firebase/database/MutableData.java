package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.SnapshotHolder;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Iterator;
import java.util.NoSuchElementException;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class MutableData {
    private final SnapshotHolder holder;
    private final Path prefixPath;

    /* synthetic */ MutableData(SnapshotHolder snapshotHolder, Path path, AnonymousClass1 anonymousClass1) {
        this(snapshotHolder, path);
    }

    MutableData(Node node) {
        this(new SnapshotHolder(node), new Path(""));
    }

    private MutableData(SnapshotHolder snapshotHolder, Path path) {
        this.holder = snapshotHolder;
        this.prefixPath = path;
        ValidationPath.validateWithObject(this.prefixPath, getValue());
    }

    Node getNode() {
        return this.holder.getNode(this.prefixPath);
    }

    @PublicApi
    public boolean hasChildren() {
        Node node = getNode();
        return (node.isLeafNode() || node.isEmpty()) ? false : true;
    }

    @PublicApi
    public boolean hasChild(String str) {
        return getNode().getChild(new Path(str)).isEmpty() ^ 1;
    }

    @PublicApi
    @NonNull
    public MutableData child(@NonNull String str) {
        Validation.validatePathString(str);
        return new MutableData(this.holder, this.prefixPath.child(new Path(str)));
    }

    @PublicApi
    public long getChildrenCount() {
        return (long) getNode().getChildCount();
    }

    @PublicApi
    @NonNull
    public Iterable<MutableData> getChildren() {
        Node node = getNode();
        if (node.isEmpty() || node.isLeafNode()) {
            return new Iterable<MutableData>() {
                public Iterator<MutableData> iterator() {
                    return new Iterator<MutableData>() {
                        public boolean hasNext() {
                            return false;
                        }

                        @NonNull
                        public MutableData next() {
                            throw new NoSuchElementException();
                        }

                        public void remove() {
                            throw new UnsupportedOperationException("remove called on immutable collection");
                        }
                    };
                }
            };
        }
        final Iterator it = IndexedNode.from(node).iterator();
        return new Iterable<MutableData>() {
            public Iterator<MutableData> iterator() {
                return new Iterator<MutableData>() {
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @NonNull
                    public MutableData next() {
                        return new MutableData(MutableData.this.holder, MutableData.this.prefixPath.child(((NamedNode) it.next()).getName()), null);
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
    public String getKey() {
        return this.prefixPath.getBack() != null ? this.prefixPath.getBack().asString() : null;
    }

    @PublicApi
    @Nullable
    public Object getValue() {
        return getNode().getValue();
    }

    @PublicApi
    @Nullable
    public <T> T getValue(@NonNull Class<T> cls) {
        return CustomClassMapper.convertToCustomClass(getNode().getValue(), (Class) cls);
    }

    @PublicApi
    @Nullable
    public <T> T getValue(@NonNull GenericTypeIndicator<T> genericTypeIndicator) {
        return CustomClassMapper.convertToCustomClass(getNode().getValue(), (GenericTypeIndicator) genericTypeIndicator);
    }

    @PublicApi
    public void setValue(@Nullable Object obj) throws DatabaseException {
        ValidationPath.validateWithObject(this.prefixPath, obj);
        obj = CustomClassMapper.convertToPlainJavaTypes(obj);
        Validation.validateWritableObject(obj);
        this.holder.update(this.prefixPath, NodeUtilities.NodeFromJSON(obj));
    }

    @PublicApi
    public void setPriority(@Nullable Object obj) {
        this.holder.update(this.prefixPath, getNode().updatePriority(PriorityUtilities.parsePriority(this.prefixPath, obj)));
    }

    @PublicApi
    @Nullable
    public Object getPriority() {
        return getNode().getPriority().getValue();
    }

    public boolean equals(Object obj) {
        if (obj instanceof MutableData) {
            MutableData mutableData = (MutableData) obj;
            if (this.holder.equals(mutableData.holder) && this.prefixPath.equals(mutableData.prefixPath)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        ChildKey front = this.prefixPath.getFront();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MutableData { key = ");
        stringBuilder.append(front != null ? front.asString() : "<none>");
        stringBuilder.append(", value = ");
        stringBuilder.append(this.holder.getRootNode().getValue(true));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
