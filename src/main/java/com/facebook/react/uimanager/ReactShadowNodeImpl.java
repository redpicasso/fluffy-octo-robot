package com.facebook.react.uimanager;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.uimanager.annotations.ReactPropertyHolder;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaBaselineFunction;
import com.facebook.yoga.YogaConfig;
import com.facebook.yoga.YogaConstants;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaDisplay;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.YogaOverflow;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaValue;
import com.facebook.yoga.YogaWrap;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.Nullable;

@ReactPropertyHolder
public class ReactShadowNodeImpl implements ReactShadowNode<ReactShadowNodeImpl> {
    private static final YogaConfig sYogaConfig = ReactYogaConfigProvider.get();
    @Nullable
    private ArrayList<ReactShadowNodeImpl> mChildren;
    private final Spacing mDefaultPadding = new Spacing(0.0f);
    private Integer mHeightMeasureSpec;
    private boolean mIsLayoutOnly;
    @Nullable
    private ReactShadowNodeImpl mLayoutParent;
    @Nullable
    private ArrayList<ReactShadowNodeImpl> mNativeChildren;
    @Nullable
    private ReactShadowNodeImpl mNativeParent;
    private boolean mNodeUpdated = true;
    private final float[] mPadding = new float[9];
    private final boolean[] mPaddingIsPercent = new boolean[9];
    @Nullable
    private ReactShadowNodeImpl mParent;
    private int mReactTag;
    private int mRootTag;
    private int mScreenHeight;
    private int mScreenWidth;
    private int mScreenX;
    private int mScreenY;
    private boolean mShouldNotifyOnLayout;
    @Nullable
    private ThemedReactContext mThemedContext;
    private int mTotalNativeChildren = 0;
    @Nullable
    private String mViewClassName;
    private Integer mWidthMeasureSpec;
    private YogaNode mYogaNode;

    public boolean hoistNativeChildren() {
        return false;
    }

    public boolean isVirtual() {
        return false;
    }

    public boolean isVirtualAnchor() {
        return false;
    }

    public void onAfterUpdateTransaction() {
    }

    public void onBeforeLayout(NativeViewHierarchyOptimizer nativeViewHierarchyOptimizer) {
    }

    public void onCollectExtraUpdates(UIViewOperationQueue uIViewOperationQueue) {
    }

    public void setLocalData(Object obj) {
    }

    public ReactShadowNodeImpl() {
        if (isVirtual()) {
            this.mYogaNode = null;
            return;
        }
        YogaNode yogaNode = (YogaNode) YogaNodePool.get().acquire();
        if (yogaNode == null) {
            yogaNode = YogaNode.create(sYogaConfig);
        }
        this.mYogaNode = yogaNode;
        this.mYogaNode.setData(this);
        Arrays.fill(this.mPadding, Float.NaN);
    }

    public boolean isYogaLeafNode() {
        return isMeasureDefined();
    }

    public final String getViewClass() {
        return (String) Assertions.assertNotNull(this.mViewClassName);
    }

    public final boolean hasUpdates() {
        return this.mNodeUpdated || hasNewLayout() || isDirty();
    }

    public final void markUpdateSeen() {
        this.mNodeUpdated = false;
        if (hasNewLayout()) {
            markLayoutSeen();
        }
    }

    public void markUpdated() {
        if (!this.mNodeUpdated) {
            this.mNodeUpdated = true;
            ReactShadowNodeImpl parent = getParent();
            if (parent != null) {
                parent.markUpdated();
            }
        }
    }

    public final boolean hasUnseenUpdates() {
        return this.mNodeUpdated;
    }

    public void dirty() {
        if (!isVirtual()) {
            this.mYogaNode.dirty();
        } else if (getParent() != null) {
            getParent().dirty();
        }
    }

    public final boolean isDirty() {
        YogaNode yogaNode = this.mYogaNode;
        return yogaNode != null && yogaNode.isDirty();
    }

    public void addChildAt(ReactShadowNodeImpl reactShadowNodeImpl, int i) {
        if (this.mChildren == null) {
            this.mChildren = new ArrayList(4);
        }
        this.mChildren.add(i, reactShadowNodeImpl);
        reactShadowNodeImpl.mParent = this;
        if (!(this.mYogaNode == null || isYogaLeafNode())) {
            YogaNode yogaNode = reactShadowNodeImpl.mYogaNode;
            if (yogaNode != null) {
                this.mYogaNode.addChildAt(yogaNode, i);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot add a child that doesn't have a YogaNode to a parent without a measure function! (Trying to add a '");
                stringBuilder.append(reactShadowNodeImpl.toString());
                stringBuilder.append("' to a '");
                stringBuilder.append(toString());
                stringBuilder.append("')");
                throw new RuntimeException(stringBuilder.toString());
            }
        }
        markUpdated();
        int totalNativeNodeContributionToParent = reactShadowNodeImpl.getTotalNativeNodeContributionToParent();
        this.mTotalNativeChildren += totalNativeNodeContributionToParent;
        updateNativeChildrenCountInParent(totalNativeNodeContributionToParent);
    }

    public ReactShadowNodeImpl removeChildAt(int i) {
        ArrayList arrayList = this.mChildren;
        if (arrayList != null) {
            ReactShadowNodeImpl reactShadowNodeImpl = (ReactShadowNodeImpl) arrayList.remove(i);
            reactShadowNodeImpl.mParent = null;
            if (!(this.mYogaNode == null || isYogaLeafNode())) {
                this.mYogaNode.removeChildAt(i);
            }
            markUpdated();
            i = reactShadowNodeImpl.getTotalNativeNodeContributionToParent();
            this.mTotalNativeChildren -= i;
            updateNativeChildrenCountInParent(-i);
            return reactShadowNodeImpl;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index ");
        stringBuilder.append(i);
        stringBuilder.append(" out of bounds: node has no children");
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    public final int getChildCount() {
        ArrayList arrayList = this.mChildren;
        return arrayList == null ? 0 : arrayList.size();
    }

    public final ReactShadowNodeImpl getChildAt(int i) {
        ArrayList arrayList = this.mChildren;
        if (arrayList != null) {
            return (ReactShadowNodeImpl) arrayList.get(i);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index ");
        stringBuilder.append(i);
        stringBuilder.append(" out of bounds: node has no children");
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    public final int indexOf(ReactShadowNodeImpl reactShadowNodeImpl) {
        ArrayList arrayList = this.mChildren;
        return arrayList == null ? -1 : arrayList.indexOf(reactShadowNodeImpl);
    }

    public void removeAndDisposeAllChildren() {
        if (getChildCount() != 0) {
            int i = 0;
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                if (!(this.mYogaNode == null || isYogaLeafNode())) {
                    this.mYogaNode.removeChildAt(childCount);
                }
                ReactShadowNodeImpl childAt = getChildAt(childCount);
                childAt.mParent = null;
                i += childAt.getTotalNativeNodeContributionToParent();
                childAt.dispose();
            }
            ((ArrayList) Assertions.assertNotNull(this.mChildren)).clear();
            markUpdated();
            this.mTotalNativeChildren -= i;
            updateNativeChildrenCountInParent(-i);
        }
    }

    private void updateNativeChildrenCountInParent(int i) {
        if (getNativeKind() != NativeKind.PARENT) {
            ReactShadowNodeImpl parent = getParent();
            while (parent != null) {
                parent.mTotalNativeChildren += i;
                if (parent.getNativeKind() != NativeKind.PARENT) {
                    parent = parent.getParent();
                } else {
                    return;
                }
            }
        }
    }

    public final void updateProperties(ReactStylesDiffMap reactStylesDiffMap) {
        ViewManagerPropertyUpdater.updateProps(this, reactStylesDiffMap);
        onAfterUpdateTransaction();
    }

    public boolean dispatchUpdates(float f, float f2, UIViewOperationQueue uIViewOperationQueue, @Nullable NativeViewHierarchyOptimizer nativeViewHierarchyOptimizer) {
        if (this.mNodeUpdated) {
            onCollectExtraUpdates(uIViewOperationQueue);
        }
        boolean z = false;
        if (hasNewLayout()) {
            float layoutX = getLayoutX();
            float layoutY = getLayoutY();
            f += layoutX;
            int round = Math.round(f);
            f2 += layoutY;
            int round2 = Math.round(f2);
            int round3 = Math.round(f + getLayoutWidth());
            int round4 = Math.round(f2 + getLayoutHeight());
            int round5 = Math.round(layoutX);
            int round6 = Math.round(layoutY);
            round3 -= round;
            round4 -= round2;
            if (!(round5 == this.mScreenX && round6 == this.mScreenY && round3 == this.mScreenWidth && round4 == this.mScreenHeight)) {
                z = true;
            }
            this.mScreenX = round5;
            this.mScreenY = round6;
            this.mScreenWidth = round3;
            this.mScreenHeight = round4;
            if (z) {
                if (nativeViewHierarchyOptimizer != null) {
                    nativeViewHierarchyOptimizer.handleUpdateLayout(this);
                } else {
                    uIViewOperationQueue.enqueueUpdateLayout(getParent().getReactTag(), getReactTag(), getScreenX(), getScreenY(), getScreenWidth(), getScreenHeight());
                }
            }
        }
        return z;
    }

    public final int getReactTag() {
        return this.mReactTag;
    }

    public void setReactTag(int i) {
        this.mReactTag = i;
    }

    public final int getRootTag() {
        Assertions.assertCondition(this.mRootTag != 0);
        return this.mRootTag;
    }

    public final void setRootTag(int i) {
        this.mRootTag = i;
    }

    public final void setViewClassName(String str) {
        this.mViewClassName = str;
    }

    @Nullable
    public final ReactShadowNodeImpl getParent() {
        return this.mParent;
    }

    @Nullable
    public final ReactShadowNodeImpl getLayoutParent() {
        ReactShadowNodeImpl reactShadowNodeImpl = this.mLayoutParent;
        return reactShadowNodeImpl != null ? reactShadowNodeImpl : getNativeParent();
    }

    public final void setLayoutParent(@Nullable ReactShadowNodeImpl reactShadowNodeImpl) {
        this.mLayoutParent = reactShadowNodeImpl;
    }

    public final ThemedReactContext getThemedContext() {
        return (ThemedReactContext) Assertions.assertNotNull(this.mThemedContext);
    }

    public void setThemedContext(ThemedReactContext themedReactContext) {
        this.mThemedContext = themedReactContext;
    }

    public final boolean shouldNotifyOnLayout() {
        return this.mShouldNotifyOnLayout;
    }

    public void calculateLayout() {
        calculateLayout(Float.NaN, Float.NaN);
    }

    public void calculateLayout(float f, float f2) {
        this.mYogaNode.calculateLayout(f, f2);
    }

    public final boolean hasNewLayout() {
        YogaNode yogaNode = this.mYogaNode;
        return yogaNode != null && yogaNode.hasNewLayout();
    }

    public final void markLayoutSeen() {
        YogaNode yogaNode = this.mYogaNode;
        if (yogaNode != null) {
            yogaNode.markLayoutSeen();
        }
    }

    public final void addNativeChildAt(ReactShadowNodeImpl reactShadowNodeImpl, int i) {
        boolean z = true;
        Assertions.assertCondition(getNativeKind() == NativeKind.PARENT);
        if (reactShadowNodeImpl.getNativeKind() == NativeKind.NONE) {
            z = false;
        }
        Assertions.assertCondition(z);
        if (this.mNativeChildren == null) {
            this.mNativeChildren = new ArrayList(4);
        }
        this.mNativeChildren.add(i, reactShadowNodeImpl);
        reactShadowNodeImpl.mNativeParent = this;
    }

    public final ReactShadowNodeImpl removeNativeChildAt(int i) {
        Assertions.assertNotNull(this.mNativeChildren);
        ReactShadowNodeImpl reactShadowNodeImpl = (ReactShadowNodeImpl) this.mNativeChildren.remove(i);
        reactShadowNodeImpl.mNativeParent = null;
        return reactShadowNodeImpl;
    }

    public final void removeAllNativeChildren() {
        ArrayList arrayList = this.mNativeChildren;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((ReactShadowNodeImpl) this.mNativeChildren.get(size)).mNativeParent = null;
            }
            this.mNativeChildren.clear();
        }
    }

    public final int getNativeChildCount() {
        ArrayList arrayList = this.mNativeChildren;
        return arrayList == null ? 0 : arrayList.size();
    }

    public final int indexOfNativeChild(ReactShadowNodeImpl reactShadowNodeImpl) {
        Assertions.assertNotNull(this.mNativeChildren);
        return this.mNativeChildren.indexOf(reactShadowNodeImpl);
    }

    @Nullable
    public final ReactShadowNodeImpl getNativeParent() {
        return this.mNativeParent;
    }

    public final void setIsLayoutOnly(boolean z) {
        boolean z2 = true;
        Assertions.assertCondition(getParent() == null, "Must remove from no opt parent first");
        Assertions.assertCondition(this.mNativeParent == null, "Must remove from native parent first");
        if (getNativeChildCount() != 0) {
            z2 = false;
        }
        Assertions.assertCondition(z2, "Must remove all native children first");
        this.mIsLayoutOnly = z;
    }

    public final boolean isLayoutOnly() {
        return this.mIsLayoutOnly;
    }

    public NativeKind getNativeKind() {
        if (isVirtual() || isLayoutOnly()) {
            return NativeKind.NONE;
        }
        return hoistNativeChildren() ? NativeKind.LEAF : NativeKind.PARENT;
    }

    public final int getTotalNativeChildren() {
        return this.mTotalNativeChildren;
    }

    public boolean isDescendantOf(ReactShadowNodeImpl reactShadowNodeImpl) {
        for (ReactShadowNodeImpl parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent == reactShadowNodeImpl) {
                return true;
            }
        }
        return false;
    }

    private int getTotalNativeNodeContributionToParent() {
        NativeKind nativeKind = getNativeKind();
        if (nativeKind == NativeKind.NONE) {
            return this.mTotalNativeChildren;
        }
        return nativeKind == NativeKind.LEAF ? 1 + this.mTotalNativeChildren : 1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mViewClassName);
        stringBuilder.append(" ");
        stringBuilder.append(getReactTag());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final int getNativeOffsetForChild(ReactShadowNodeImpl reactShadowNodeImpl) {
        Object obj = null;
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            ReactShadowNodeImpl childAt = getChildAt(i2);
            if (reactShadowNodeImpl == childAt) {
                obj = 1;
                break;
            }
            i += childAt.getTotalNativeNodeContributionToParent();
        }
        if (obj != null) {
            return i;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Child ");
        stringBuilder.append(reactShadowNodeImpl.getReactTag());
        stringBuilder.append(" was not a child of ");
        stringBuilder.append(this.mReactTag);
        throw new RuntimeException(stringBuilder.toString());
    }

    public final float getLayoutX() {
        return this.mYogaNode.getLayoutX();
    }

    public final float getLayoutY() {
        return this.mYogaNode.getLayoutY();
    }

    public final float getLayoutWidth() {
        return this.mYogaNode.getLayoutWidth();
    }

    public final float getLayoutHeight() {
        return this.mYogaNode.getLayoutHeight();
    }

    public int getScreenX() {
        return this.mScreenX;
    }

    public int getScreenY() {
        return this.mScreenY;
    }

    public int getScreenWidth() {
        return this.mScreenWidth;
    }

    public int getScreenHeight() {
        return this.mScreenHeight;
    }

    public final YogaDirection getLayoutDirection() {
        return this.mYogaNode.getLayoutDirection();
    }

    public void setLayoutDirection(YogaDirection yogaDirection) {
        this.mYogaNode.setDirection(yogaDirection);
    }

    public final YogaValue getStyleWidth() {
        return this.mYogaNode.getWidth();
    }

    public void setStyleWidth(float f) {
        this.mYogaNode.setWidth(f);
    }

    public void setStyleWidthPercent(float f) {
        this.mYogaNode.setWidthPercent(f);
    }

    public void setStyleWidthAuto() {
        this.mYogaNode.setWidthAuto();
    }

    public void setStyleMinWidth(float f) {
        this.mYogaNode.setMinWidth(f);
    }

    public void setStyleMinWidthPercent(float f) {
        this.mYogaNode.setMinWidthPercent(f);
    }

    public void setStyleMaxWidth(float f) {
        this.mYogaNode.setMaxWidth(f);
    }

    public void setStyleMaxWidthPercent(float f) {
        this.mYogaNode.setMaxWidthPercent(f);
    }

    public final YogaValue getStyleHeight() {
        return this.mYogaNode.getHeight();
    }

    public void setStyleHeight(float f) {
        this.mYogaNode.setHeight(f);
    }

    public void setStyleHeightPercent(float f) {
        this.mYogaNode.setHeightPercent(f);
    }

    public void setStyleHeightAuto() {
        this.mYogaNode.setHeightAuto();
    }

    public void setStyleMinHeight(float f) {
        this.mYogaNode.setMinHeight(f);
    }

    public void setStyleMinHeightPercent(float f) {
        this.mYogaNode.setMinHeightPercent(f);
    }

    public void setStyleMaxHeight(float f) {
        this.mYogaNode.setMaxHeight(f);
    }

    public void setStyleMaxHeightPercent(float f) {
        this.mYogaNode.setMaxHeightPercent(f);
    }

    public void setFlex(float f) {
        this.mYogaNode.setFlex(f);
    }

    public void setFlexGrow(float f) {
        this.mYogaNode.setFlexGrow(f);
    }

    public void setFlexShrink(float f) {
        this.mYogaNode.setFlexShrink(f);
    }

    public void setFlexBasis(float f) {
        this.mYogaNode.setFlexBasis(f);
    }

    public void setFlexBasisAuto() {
        this.mYogaNode.setFlexBasisAuto();
    }

    public void setFlexBasisPercent(float f) {
        this.mYogaNode.setFlexBasisPercent(f);
    }

    public void setStyleAspectRatio(float f) {
        this.mYogaNode.setAspectRatio(f);
    }

    public void setFlexDirection(YogaFlexDirection yogaFlexDirection) {
        this.mYogaNode.setFlexDirection(yogaFlexDirection);
    }

    public void setFlexWrap(YogaWrap yogaWrap) {
        this.mYogaNode.setWrap(yogaWrap);
    }

    public void setAlignSelf(YogaAlign yogaAlign) {
        this.mYogaNode.setAlignSelf(yogaAlign);
    }

    public void setAlignItems(YogaAlign yogaAlign) {
        this.mYogaNode.setAlignItems(yogaAlign);
    }

    public void setAlignContent(YogaAlign yogaAlign) {
        this.mYogaNode.setAlignContent(yogaAlign);
    }

    public void setJustifyContent(YogaJustify yogaJustify) {
        this.mYogaNode.setJustifyContent(yogaJustify);
    }

    public void setOverflow(YogaOverflow yogaOverflow) {
        this.mYogaNode.setOverflow(yogaOverflow);
    }

    public void setDisplay(YogaDisplay yogaDisplay) {
        this.mYogaNode.setDisplay(yogaDisplay);
    }

    public void setMargin(int i, float f) {
        this.mYogaNode.setMargin(YogaEdge.fromInt(i), f);
    }

    public void setMarginPercent(int i, float f) {
        this.mYogaNode.setMarginPercent(YogaEdge.fromInt(i), f);
    }

    public void setMarginAuto(int i) {
        this.mYogaNode.setMarginAuto(YogaEdge.fromInt(i));
    }

    public final float getPadding(int i) {
        return this.mYogaNode.getLayoutPadding(YogaEdge.fromInt(i));
    }

    public final YogaValue getStylePadding(int i) {
        return this.mYogaNode.getPadding(YogaEdge.fromInt(i));
    }

    public void setDefaultPadding(int i, float f) {
        this.mDefaultPadding.set(i, f);
        updatePadding();
    }

    public void setPadding(int i, float f) {
        this.mPadding[i] = f;
        this.mPaddingIsPercent[i] = false;
        updatePadding();
    }

    public void setPaddingPercent(int i, float f) {
        this.mPadding[i] = f;
        this.mPaddingIsPercent[i] = YogaConstants.isUndefined(f) ^ 1;
        updatePadding();
    }

    private void updatePadding() {
        int i = 0;
        while (i <= 8) {
            if (i == 0 || i == 2 || i == 4 || i == 5) {
                if (YogaConstants.isUndefined(this.mPadding[i]) && YogaConstants.isUndefined(this.mPadding[6]) && YogaConstants.isUndefined(this.mPadding[8])) {
                    this.mYogaNode.setPadding(YogaEdge.fromInt(i), this.mDefaultPadding.getRaw(i));
                    i++;
                }
            } else if (i == 1 || i == 3) {
                if (YogaConstants.isUndefined(this.mPadding[i]) && YogaConstants.isUndefined(this.mPadding[7]) && YogaConstants.isUndefined(this.mPadding[8])) {
                    this.mYogaNode.setPadding(YogaEdge.fromInt(i), this.mDefaultPadding.getRaw(i));
                    i++;
                }
            } else if (YogaConstants.isUndefined(this.mPadding[i])) {
                this.mYogaNode.setPadding(YogaEdge.fromInt(i), this.mDefaultPadding.getRaw(i));
                i++;
            }
            if (this.mPaddingIsPercent[i]) {
                this.mYogaNode.setPaddingPercent(YogaEdge.fromInt(i), this.mPadding[i]);
            } else {
                this.mYogaNode.setPadding(YogaEdge.fromInt(i), this.mPadding[i]);
            }
            i++;
        }
    }

    public void setBorder(int i, float f) {
        this.mYogaNode.setBorder(YogaEdge.fromInt(i), f);
    }

    public void setPosition(int i, float f) {
        this.mYogaNode.setPosition(YogaEdge.fromInt(i), f);
    }

    public void setPositionPercent(int i, float f) {
        this.mYogaNode.setPositionPercent(YogaEdge.fromInt(i), f);
    }

    public void setPositionType(YogaPositionType yogaPositionType) {
        this.mYogaNode.setPositionType(yogaPositionType);
    }

    public void setShouldNotifyOnLayout(boolean z) {
        this.mShouldNotifyOnLayout = z;
    }

    public void setBaselineFunction(YogaBaselineFunction yogaBaselineFunction) {
        this.mYogaNode.setBaselineFunction(yogaBaselineFunction);
    }

    public void setMeasureFunction(YogaMeasureFunction yogaMeasureFunction) {
        this.mYogaNode.setMeasureFunction(yogaMeasureFunction);
    }

    public boolean isMeasureDefined() {
        return this.mYogaNode.isMeasureDefined();
    }

    public String getHierarchyInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        getHierarchyInfoWithIndentation(stringBuilder, 0);
        return stringBuilder.toString();
    }

    private void getHierarchyInfoWithIndentation(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            stringBuilder.append("  ");
        }
        stringBuilder.append("<");
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(" view='");
        stringBuilder.append(getViewClass());
        stringBuilder.append("' tag=");
        stringBuilder.append(getReactTag());
        if (this.mYogaNode != null) {
            stringBuilder.append(" layout='x:");
            stringBuilder.append(getScreenX());
            stringBuilder.append(" y:");
            stringBuilder.append(getScreenY());
            stringBuilder.append(" w:");
            stringBuilder.append(getLayoutWidth());
            stringBuilder.append(" h:");
            stringBuilder.append(getLayoutHeight());
            stringBuilder.append("'");
        } else {
            stringBuilder.append("(virtual node)");
        }
        stringBuilder.append(">\n");
        if (getChildCount() != 0) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                getChildAt(i3).getHierarchyInfoWithIndentation(stringBuilder, i + 1);
            }
        }
    }

    public void dispose() {
        YogaNode yogaNode = this.mYogaNode;
        if (yogaNode != null) {
            yogaNode.reset();
            YogaNodePool.get().release(this.mYogaNode);
        }
    }

    public void setMeasureSpecs(int i, int i2) {
        this.mWidthMeasureSpec = Integer.valueOf(i);
        this.mHeightMeasureSpec = Integer.valueOf(i2);
    }

    public Integer getWidthMeasureSpec() {
        return this.mWidthMeasureSpec;
    }

    public Integer getHeightMeasureSpec() {
        return this.mHeightMeasureSpec;
    }

    public Iterable<? extends ReactShadowNode> calculateLayoutOnChildren() {
        return isVirtualAnchor() ? null : this.mChildren;
    }
}
