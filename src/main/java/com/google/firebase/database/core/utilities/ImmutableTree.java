package com.google.firebase.database.core.utilities;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.collection.StandardComparator;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ImmutableTree<T> implements Iterable<Entry<Path, T>> {
    private static final ImmutableTree EMPTY = new ImmutableTree(null, EMPTY_CHILDREN);
    private static final ImmutableSortedMap EMPTY_CHILDREN = Builder.emptyMap(StandardComparator.getComparator(ChildKey.class));
    private final ImmutableSortedMap<ChildKey, ImmutableTree<T>> children;
    private final T value;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface TreeVisitor<T, R> {
        R onNodeValue(Path path, T t, R r);
    }

    public static <V> ImmutableTree<V> emptyInstance() {
        return EMPTY;
    }

    public ImmutableTree(T t, ImmutableSortedMap<ChildKey, ImmutableTree<T>> immutableSortedMap) {
        this.value = t;
        this.children = immutableSortedMap;
    }

    public ImmutableTree(T t) {
        this(t, EMPTY_CHILDREN);
    }

    public T getValue() {
        return this.value;
    }

    public ImmutableSortedMap<ChildKey, ImmutableTree<T>> getChildren() {
        return this.children;
    }

    public boolean isEmpty() {
        return this.value == null && this.children.isEmpty();
    }

    public Path findRootMostMatchingPath(Path path, Predicate<? super T> predicate) {
        Object obj = this.value;
        if (obj != null && predicate.evaluate(obj)) {
            return Path.getEmptyPath();
        }
        if (path.isEmpty()) {
            return null;
        }
        ImmutableTree immutableTree = (ImmutableTree) this.children.get(path.getFront());
        if (immutableTree != null) {
            path = immutableTree.findRootMostMatchingPath(path.popFront(), predicate);
            if (path != null) {
                return new Path(r0).child(path);
            }
        }
        return null;
    }

    public Path findRootMostPathWithValue(Path path) {
        return findRootMostMatchingPath(path, Predicate.TRUE);
    }

    public T rootMostValue(Path path) {
        return rootMostValueMatching(path, Predicate.TRUE);
    }

    public T rootMostValueMatching(Path path, Predicate<? super T> predicate) {
        Object obj = this.value;
        if (obj != null && predicate.evaluate(obj)) {
            return this.value;
        }
        Iterator it = path.iterator();
        ImmutableTree immutableTree = this;
        while (it.hasNext()) {
            immutableTree = (ImmutableTree) immutableTree.children.get((ChildKey) it.next());
            if (immutableTree == null) {
                return null;
            }
            Object obj2 = immutableTree.value;
            if (obj2 != null && predicate.evaluate(obj2)) {
                return immutableTree.value;
            }
        }
        return null;
    }

    public T leafMostValue(Path path) {
        return leafMostValueMatching(path, Predicate.TRUE);
    }

    public T leafMostValueMatching(Path path, Predicate<? super T> predicate) {
        Object obj = this.value;
        T t = (obj == null || !predicate.evaluate(obj)) ? null : this.value;
        Iterator it = path.iterator();
        ImmutableTree immutableTree = this;
        while (it.hasNext()) {
            immutableTree = (ImmutableTree) immutableTree.children.get((ChildKey) it.next());
            if (immutableTree == null) {
                return t;
            }
            Object obj2 = immutableTree.value;
            if (obj2 != null && predicate.evaluate(obj2)) {
                t = immutableTree.value;
            }
        }
        return t;
    }

    public boolean containsMatchingValue(Predicate<? super T> predicate) {
        Object obj = this.value;
        if (obj != null && predicate.evaluate(obj)) {
            return true;
        }
        Iterator it = this.children.iterator();
        while (it.hasNext()) {
            if (((ImmutableTree) ((Entry) it.next()).getValue()).containsMatchingValue(predicate)) {
                return true;
            }
        }
        return false;
    }

    public ImmutableTree<T> getChild(ChildKey childKey) {
        ImmutableTree<T> immutableTree = (ImmutableTree) this.children.get(childKey);
        if (immutableTree != null) {
            return immutableTree;
        }
        return emptyInstance();
    }

    public ImmutableTree<T> subtree(Path path) {
        if (path.isEmpty()) {
            return this;
        }
        ImmutableTree immutableTree = (ImmutableTree) this.children.get(path.getFront());
        if (immutableTree != null) {
            return immutableTree.subtree(path.popFront());
        }
        return emptyInstance();
    }

    public ImmutableTree<T> set(Path path, T t) {
        if (path.isEmpty()) {
            return new ImmutableTree(t, this.children);
        }
        ChildKey front = path.getFront();
        ImmutableTree immutableTree = (ImmutableTree) this.children.get(front);
        if (immutableTree == null) {
            immutableTree = emptyInstance();
        }
        return new ImmutableTree(this.value, this.children.insert(front, immutableTree.set(path.popFront(), t)));
    }

    public ImmutableTree<T> remove(Path path) {
        if (!path.isEmpty()) {
            ChildKey front = path.getFront();
            ImmutableTree immutableTree = (ImmutableTree) this.children.get(front);
            if (immutableTree == null) {
                return this;
            }
            ImmutableSortedMap remove;
            ImmutableTree remove2 = immutableTree.remove(path.popFront());
            if (remove2.isEmpty()) {
                remove = this.children.remove(front);
            } else {
                remove = this.children.insert(front, remove2);
            }
            if (this.value == null && remove.isEmpty()) {
                return emptyInstance();
            }
            return new ImmutableTree(this.value, remove);
        } else if (this.children.isEmpty()) {
            return emptyInstance();
        } else {
            return new ImmutableTree(null, this.children);
        }
    }

    public T get(Path path) {
        if (path.isEmpty()) {
            return this.value;
        }
        ImmutableTree immutableTree = (ImmutableTree) this.children.get(path.getFront());
        return immutableTree != null ? immutableTree.get(path.popFront()) : null;
    }

    public ImmutableTree<T> setTree(Path path, ImmutableTree<T> immutableTree) {
        if (path.isEmpty()) {
            return immutableTree;
        }
        ImmutableSortedMap remove;
        ChildKey front = path.getFront();
        ImmutableTree immutableTree2 = (ImmutableTree) this.children.get(front);
        if (immutableTree2 == null) {
            immutableTree2 = emptyInstance();
        }
        ImmutableTree tree = immutableTree2.setTree(path.popFront(), immutableTree);
        if (tree.isEmpty()) {
            remove = this.children.remove(front);
        } else {
            remove = this.children.insert(front, tree);
        }
        return new ImmutableTree(this.value, remove);
    }

    public void foreach(TreeVisitor<T, Void> treeVisitor) {
        fold(Path.getEmptyPath(), treeVisitor, null);
    }

    public <R> R fold(R r, TreeVisitor<? super T, R> treeVisitor) {
        return fold(Path.getEmptyPath(), treeVisitor, r);
    }

    private <R> R fold(Path path, TreeVisitor<? super T, R> treeVisitor, R r) {
        Iterator it = this.children.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            r = ((ImmutableTree) entry.getValue()).fold(path.child((ChildKey) entry.getKey()), treeVisitor, r);
        }
        Object obj = this.value;
        return obj != null ? treeVisitor.onNodeValue(path, obj, r) : r;
    }

    public Collection<T> values() {
        final Collection arrayList = new ArrayList();
        foreach(new TreeVisitor<T, Void>() {
            public Void onNodeValue(Path path, T t, Void voidR) {
                arrayList.add(t);
                return null;
            }
        });
        return arrayList;
    }

    public Iterator<Entry<Path, T>> iterator() {
        final List arrayList = new ArrayList();
        foreach(new TreeVisitor<T, Void>() {
            public Void onNodeValue(Path path, T t, Void voidR) {
                arrayList.add(new SimpleImmutableEntry(path, t));
                return null;
            }
        });
        return arrayList.iterator();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImmutableTree { value=");
        stringBuilder.append(getValue());
        stringBuilder.append(", children={");
        Iterator it = this.children.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            stringBuilder.append(((ChildKey) entry.getKey()).asString());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
        }
        stringBuilder.append("} }");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ImmutableTree immutableTree = (ImmutableTree) obj;
        ImmutableSortedMap immutableSortedMap = this.children;
        if (!immutableSortedMap == null ? immutableSortedMap.equals(immutableTree.children) : immutableTree.children == null) {
            return false;
        }
        Object obj2 = this.value;
        return obj2 == null ? immutableTree.value == null : obj2.equals(immutableTree.value);
    }

    public int hashCode() {
        Object obj = this.value;
        int i = 0;
        int hashCode = (obj != null ? obj.hashCode() : 0) * 31;
        ImmutableSortedMap immutableSortedMap = this.children;
        if (immutableSortedMap != null) {
            i = immutableSortedMap.hashCode();
        }
        return hashCode + i;
    }
}
