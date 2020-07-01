package com.google.firebase.database.core.utilities;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Tree<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private ChildKey name;
    private TreeNode<T> node;
    private Tree<T> parent;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface TreeFilter<T> {
        boolean filterTreeNode(Tree<T> tree);
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface TreeVisitor<T> {
        void visitTree(Tree<T> tree);
    }

    public Tree(ChildKey childKey, Tree<T> tree, TreeNode<T> treeNode) {
        this.name = childKey;
        this.parent = tree;
        this.node = treeNode;
    }

    public Tree() {
        this(null, null, new TreeNode());
    }

    public TreeNode<T> lastNodeOnPath(Path path) {
        TreeNode<T> treeNode = this.node;
        Object front = path.getFront();
        while (front != null) {
            TreeNode<T> treeNode2 = treeNode.children.containsKey(front) ? (TreeNode) treeNode.children.get(front) : null;
            if (treeNode2 == null) {
                return treeNode;
            }
            path = path.popFront();
            TreeNode<T> treeNode3 = treeNode2;
            ChildKey front2 = path.getFront();
            treeNode = treeNode3;
        }
        return treeNode;
    }

    public Tree<T> subTree(Path path) {
        ChildKey front = path.getFront();
        Path path2 = path;
        Tree<T> tree = this;
        while (front != null) {
            Tree<T> tree2 = new Tree(front, tree, tree.node.children.containsKey(front) ? (TreeNode) tree.node.children.get(front) : new TreeNode());
            path2 = path2.popFront();
            front = path2.getFront();
            tree = tree2;
        }
        return tree;
    }

    public T getValue() {
        return this.node.value;
    }

    public void setValue(T t) {
        this.node.value = t;
        updateParents();
    }

    public Tree<T> getParent() {
        return this.parent;
    }

    public ChildKey getName() {
        return this.name;
    }

    public Path getPath() {
        Tree tree = this.parent;
        if (tree != null) {
            return tree.getPath().child(this.name);
        }
        Path path;
        if (this.name != null) {
            path = new Path(this.name);
        } else {
            path = Path.getEmptyPath();
        }
        return path;
    }

    public boolean hasChildren() {
        return this.node.children.isEmpty() ^ 1;
    }

    public boolean isEmpty() {
        return this.node.value == null && this.node.children.isEmpty();
    }

    public void forEachDescendant(TreeVisitor<T> treeVisitor) {
        forEachDescendant(treeVisitor, false, false);
    }

    public void forEachDescendant(TreeVisitor<T> treeVisitor, boolean z) {
        forEachDescendant(treeVisitor, z, false);
    }

    public void forEachDescendant(final TreeVisitor<T> treeVisitor, boolean z, final boolean z2) {
        if (z && !z2) {
            treeVisitor.visitTree(this);
        }
        forEachChild(new TreeVisitor<T>() {
            public void visitTree(Tree<T> tree) {
                tree.forEachDescendant(treeVisitor, true, z2);
            }
        });
        if (z && z2) {
            treeVisitor.visitTree(this);
        }
    }

    public boolean forEachAncestor(TreeFilter<T> treeFilter) {
        return forEachAncestor(treeFilter, false);
    }

    public boolean forEachAncestor(TreeFilter<T> treeFilter, boolean z) {
        Tree tree = z ? this : this.parent;
        while (tree != null) {
            if (treeFilter.filterTreeNode(tree)) {
                return true;
            }
            tree = tree.parent;
        }
        return false;
    }

    public void forEachChild(TreeVisitor<T> treeVisitor) {
        Object[] toArray = this.node.children.entrySet().toArray();
        for (Object obj : toArray) {
            Entry entry = (Entry) obj;
            treeVisitor.visitTree(new Tree((ChildKey) entry.getKey(), this, (TreeNode) entry.getValue()));
        }
    }

    private void updateParents() {
        Tree tree = this.parent;
        if (tree != null) {
            tree.updateChild(this.name, this);
        }
    }

    private void updateChild(ChildKey childKey, Tree<T> tree) {
        boolean isEmpty = tree.isEmpty();
        boolean containsKey = this.node.children.containsKey(childKey);
        if (isEmpty && containsKey) {
            this.node.children.remove(childKey);
            updateParents();
        } else if (!isEmpty && !containsKey) {
            this.node.children.put(childKey, tree.node);
            updateParents();
        }
    }

    public String toString() {
        return toString("");
    }

    String toString(String str) {
        ChildKey childKey = this.name;
        String asString = childKey == null ? "<anon>" : childKey.asString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(asString);
        stringBuilder.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
        TreeNode treeNode = this.node;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append("\t");
        stringBuilder.append(treeNode.toString(stringBuilder2.toString()));
        return stringBuilder.toString();
    }
}
