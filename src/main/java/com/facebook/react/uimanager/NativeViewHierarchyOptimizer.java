package com.facebook.react.uimanager;

import android.util.SparseBooleanArray;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import javax.annotation.Nullable;

public class NativeViewHierarchyOptimizer {
    private static final boolean ENABLED = true;
    private final ShadowNodeRegistry mShadowNodeRegistry;
    private final SparseBooleanArray mTagsWithLayoutVisited = new SparseBooleanArray();
    private final UIViewOperationQueue mUIViewOperationQueue;

    private static class NodeIndexPair {
        public final int index;
        public final ReactShadowNode node;

        NodeIndexPair(ReactShadowNode reactShadowNode, int i) {
            this.node = reactShadowNode;
            this.index = i;
        }
    }

    public static void assertNodeSupportedWithoutOptimizer(ReactShadowNode reactShadowNode) {
        Assertions.assertCondition(reactShadowNode.getNativeKind() != NativeKind.LEAF, "Nodes with NativeKind.LEAF are not supported when the optimizer is disabled");
    }

    public NativeViewHierarchyOptimizer(UIViewOperationQueue uIViewOperationQueue, ShadowNodeRegistry shadowNodeRegistry) {
        this.mUIViewOperationQueue = uIViewOperationQueue;
        this.mShadowNodeRegistry = shadowNodeRegistry;
    }

    public void handleCreateView(ReactShadowNode reactShadowNode, ThemedReactContext themedReactContext, @Nullable ReactStylesDiffMap reactStylesDiffMap) {
        boolean z = reactShadowNode.getViewClass().equals("RCTView") && isLayoutOnlyAndCollapsable(reactStylesDiffMap);
        reactShadowNode.setIsLayoutOnly(z);
        if (reactShadowNode.getNativeKind() != NativeKind.NONE) {
            this.mUIViewOperationQueue.enqueueCreateView(themedReactContext, reactShadowNode.getReactTag(), reactShadowNode.getViewClass(), reactStylesDiffMap);
        }
    }

    public static void handleRemoveNode(ReactShadowNode reactShadowNode) {
        reactShadowNode.removeAllNativeChildren();
    }

    public void handleUpdateView(ReactShadowNode reactShadowNode, String str, ReactStylesDiffMap reactStylesDiffMap) {
        Object obj = (!reactShadowNode.isLayoutOnly() || isLayoutOnlyAndCollapsable(reactStylesDiffMap)) ? null : 1;
        if (obj != null) {
            transitionLayoutOnlyViewToNativeView(reactShadowNode, reactStylesDiffMap);
        } else if (!reactShadowNode.isLayoutOnly()) {
            this.mUIViewOperationQueue.enqueueUpdateProperties(reactShadowNode.getReactTag(), str, reactStylesDiffMap);
        }
    }

    public void handleManageChildren(ReactShadowNode reactShadowNode, int[] iArr, int[] iArr2, ViewAtIndex[] viewAtIndexArr, int[] iArr3, int[] iArr4) {
        for (int i : iArr2) {
            boolean z;
            for (int i2 : iArr3) {
                if (i2 == i) {
                    z = true;
                    break;
                }
            }
            z = false;
            removeNodeFromParent(this.mShadowNodeRegistry.getNode(i), z);
        }
        for (ViewAtIndex viewAtIndex : viewAtIndexArr) {
            addNodeToNode(reactShadowNode, this.mShadowNodeRegistry.getNode(viewAtIndex.mTag), viewAtIndex.mIndex);
        }
    }

    public void handleSetChildren(ReactShadowNode reactShadowNode, ReadableArray readableArray) {
        for (int i = 0; i < readableArray.size(); i++) {
            addNodeToNode(reactShadowNode, this.mShadowNodeRegistry.getNode(readableArray.getInt(i)), i);
        }
    }

    public void handleUpdateLayout(ReactShadowNode reactShadowNode) {
        applyLayoutBase(reactShadowNode);
    }

    public void handleForceViewToBeNonLayoutOnly(ReactShadowNode reactShadowNode) {
        if (reactShadowNode.isLayoutOnly()) {
            transitionLayoutOnlyViewToNativeView(reactShadowNode, null);
        }
    }

    public void onBatchComplete() {
        this.mTagsWithLayoutVisited.clear();
    }

    private NodeIndexPair walkUpUntilNativeKindIsParent(ReactShadowNode reactShadowNode, int i) {
        while (reactShadowNode.getNativeKind() != NativeKind.PARENT) {
            ReactShadowNode parent = reactShadowNode.getParent();
            if (parent == null) {
                return null;
            }
            i = (i + (reactShadowNode.getNativeKind() == NativeKind.LEAF ? 1 : 0)) + parent.getNativeOffsetForChild(reactShadowNode);
            reactShadowNode = parent;
        }
        return new NodeIndexPair(reactShadowNode, i);
    }

    private void addNodeToNode(ReactShadowNode reactShadowNode, ReactShadowNode reactShadowNode2, int i) {
        i = reactShadowNode.getNativeOffsetForChild(reactShadowNode.getChildAt(i));
        if (reactShadowNode.getNativeKind() != NativeKind.PARENT) {
            NodeIndexPair walkUpUntilNativeKindIsParent = walkUpUntilNativeKindIsParent(reactShadowNode, i);
            if (walkUpUntilNativeKindIsParent != null) {
                ReactShadowNode reactShadowNode3 = walkUpUntilNativeKindIsParent.node;
                i = walkUpUntilNativeKindIsParent.index;
                reactShadowNode = reactShadowNode3;
            } else {
                return;
            }
        }
        if (reactShadowNode2.getNativeKind() != NativeKind.NONE) {
            addNativeChild(reactShadowNode, reactShadowNode2, i);
        } else {
            addNonNativeChild(reactShadowNode, reactShadowNode2, i);
        }
    }

    private void removeNodeFromParent(ReactShadowNode reactShadowNode, boolean z) {
        if (reactShadowNode.getNativeKind() != NativeKind.PARENT) {
            for (int childCount = reactShadowNode.getChildCount() - 1; childCount >= 0; childCount--) {
                removeNodeFromParent(reactShadowNode.getChildAt(childCount), z);
            }
        }
        ReactShadowNode nativeParent = reactShadowNode.getNativeParent();
        if (nativeParent != null) {
            nativeParent.removeNativeChildAt(nativeParent.indexOfNativeChild(reactShadowNode));
            this.mUIViewOperationQueue.enqueueManageChildren(nativeParent.getReactTag(), new int[]{nativeParent.indexOfNativeChild(reactShadowNode)}, null, z ? new int[]{reactShadowNode.getReactTag()} : null, z ? new int[]{nativeParent.indexOfNativeChild(reactShadowNode)} : null);
        }
    }

    private void addNonNativeChild(ReactShadowNode reactShadowNode, ReactShadowNode reactShadowNode2, int i) {
        addGrandchildren(reactShadowNode, reactShadowNode2, i);
    }

    private void addNativeChild(ReactShadowNode reactShadowNode, ReactShadowNode reactShadowNode2, int i) {
        reactShadowNode.addNativeChildAt(reactShadowNode2, i);
        this.mUIViewOperationQueue.enqueueManageChildren(reactShadowNode.getReactTag(), null, new ViewAtIndex[]{new ViewAtIndex(reactShadowNode2.getReactTag(), i)}, null, null);
        if (reactShadowNode2.getNativeKind() != NativeKind.PARENT) {
            addGrandchildren(reactShadowNode, reactShadowNode2, i + 1);
        }
    }

    private void addGrandchildren(ReactShadowNode reactShadowNode, ReactShadowNode reactShadowNode2, int i) {
        Assertions.assertCondition(reactShadowNode2.getNativeKind() != NativeKind.PARENT);
        int i2 = i;
        for (i = 0; i < reactShadowNode2.getChildCount(); i++) {
            ReactShadowNode childAt = reactShadowNode2.getChildAt(i);
            Assertions.assertCondition(childAt.getNativeParent() == null);
            int nativeChildCount = reactShadowNode.getNativeChildCount();
            if (childAt.getNativeKind() == NativeKind.NONE) {
                addNonNativeChild(reactShadowNode, childAt, i2);
            } else {
                addNativeChild(reactShadowNode, childAt, i2);
            }
            i2 += reactShadowNode.getNativeChildCount() - nativeChildCount;
        }
    }

    private void applyLayoutBase(ReactShadowNode reactShadowNode) {
        int reactTag = reactShadowNode.getReactTag();
        if (!this.mTagsWithLayoutVisited.get(reactTag)) {
            this.mTagsWithLayoutVisited.put(reactTag, true);
            ReactShadowNode parent = reactShadowNode.getParent();
            int screenX = reactShadowNode.getScreenX();
            int screenY = reactShadowNode.getScreenY();
            while (parent != null && parent.getNativeKind() != NativeKind.PARENT) {
                if (!parent.isVirtual()) {
                    screenX += Math.round(parent.getLayoutX());
                    screenY += Math.round(parent.getLayoutY());
                }
                parent = parent.getParent();
            }
            applyLayoutRecursive(reactShadowNode, screenX, screenY);
        }
    }

    private void applyLayoutRecursive(ReactShadowNode reactShadowNode, int i, int i2) {
        if (reactShadowNode.getNativeKind() == NativeKind.NONE || reactShadowNode.getNativeParent() == null) {
            for (int i3 = 0; i3 < reactShadowNode.getChildCount(); i3++) {
                ReactShadowNode childAt = reactShadowNode.getChildAt(i3);
                int reactTag = childAt.getReactTag();
                if (!this.mTagsWithLayoutVisited.get(reactTag)) {
                    this.mTagsWithLayoutVisited.put(reactTag, true);
                    applyLayoutRecursive(childAt, childAt.getScreenX() + i, childAt.getScreenY() + i2);
                }
            }
            return;
        }
        this.mUIViewOperationQueue.enqueueUpdateLayout(reactShadowNode.getLayoutParent().getReactTag(), reactShadowNode.getReactTag(), i, i2, reactShadowNode.getScreenWidth(), reactShadowNode.getScreenHeight());
    }

    private void transitionLayoutOnlyViewToNativeView(ReactShadowNode reactShadowNode, @Nullable ReactStylesDiffMap reactStylesDiffMap) {
        ReactShadowNode parent = reactShadowNode.getParent();
        int i = 0;
        if (parent == null) {
            reactShadowNode.setIsLayoutOnly(false);
            return;
        }
        int indexOf = parent.indexOf(reactShadowNode);
        parent.removeChildAt(indexOf);
        removeNodeFromParent(reactShadowNode, false);
        reactShadowNode.setIsLayoutOnly(false);
        this.mUIViewOperationQueue.enqueueCreateView(reactShadowNode.getThemedContext(), reactShadowNode.getReactTag(), reactShadowNode.getViewClass(), reactStylesDiffMap);
        parent.addChildAt(reactShadowNode, indexOf);
        addNodeToNode(parent, reactShadowNode, indexOf);
        for (int i2 = 0; i2 < reactShadowNode.getChildCount(); i2++) {
            addNodeToNode(reactShadowNode, reactShadowNode.getChildAt(i2), i2);
        }
        Assertions.assertCondition(this.mTagsWithLayoutVisited.size() == 0);
        applyLayoutBase(reactShadowNode);
        while (i < reactShadowNode.getChildCount()) {
            applyLayoutBase(reactShadowNode.getChildAt(i));
            i++;
        }
        this.mTagsWithLayoutVisited.clear();
    }

    private static boolean isLayoutOnlyAndCollapsable(@Nullable ReactStylesDiffMap reactStylesDiffMap) {
        if (reactStylesDiffMap == null) {
            return true;
        }
        String str = ViewProps.COLLAPSABLE;
        if (reactStylesDiffMap.hasKey(str) && !reactStylesDiffMap.getBoolean(str, true)) {
            return false;
        }
        ReadableMapKeySetIterator keySetIterator = reactStylesDiffMap.mBackingMap.keySetIterator();
        while (keySetIterator.hasNextKey()) {
            if (!ViewProps.isLayoutOnly(reactStylesDiffMap.mBackingMap, keySetIterator.nextKey())) {
                return false;
            }
        }
        return true;
    }
}