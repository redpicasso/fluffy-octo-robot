package com.facebook.react.uimanager;

import android.os.SystemClock;
import android.view.View;
import android.view.View.MeasureSpec;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.UIManagerModule.ViewManagerResolver;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import com.facebook.yoga.YogaDirection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class UIImplementation {
    protected final EventDispatcher mEventDispatcher;
    private long mLastCalculateLayoutTime;
    @Nullable
    protected LayoutUpdateListener mLayoutUpdateListener;
    private final int[] mMeasureBuffer;
    private final NativeViewHierarchyOptimizer mNativeViewHierarchyOptimizer;
    private final UIViewOperationQueue mOperationsQueue;
    protected final ReactApplicationContext mReactContext;
    protected final ShadowNodeRegistry mShadowNodeRegistry;
    private final ViewManagerRegistry mViewManagers;
    protected Object uiImplementationThreadLock;

    public interface LayoutUpdateListener {
        void onLayoutUpdated(ReactShadowNode reactShadowNode);
    }

    public void onHostDestroy() {
    }

    public UIImplementation(ReactApplicationContext reactApplicationContext, ViewManagerResolver viewManagerResolver, EventDispatcher eventDispatcher, int i) {
        this(reactApplicationContext, new ViewManagerRegistry(viewManagerResolver), eventDispatcher, i);
    }

    public UIImplementation(ReactApplicationContext reactApplicationContext, List<ViewManager> list, EventDispatcher eventDispatcher, int i) {
        this(reactApplicationContext, new ViewManagerRegistry((List) list), eventDispatcher, i);
    }

    UIImplementation(ReactApplicationContext reactApplicationContext, ViewManagerRegistry viewManagerRegistry, EventDispatcher eventDispatcher, int i) {
        this(reactApplicationContext, viewManagerRegistry, new UIViewOperationQueue(reactApplicationContext, new NativeViewHierarchyManager(viewManagerRegistry), i), eventDispatcher);
    }

    protected UIImplementation(ReactApplicationContext reactApplicationContext, ViewManagerRegistry viewManagerRegistry, UIViewOperationQueue uIViewOperationQueue, EventDispatcher eventDispatcher) {
        this.uiImplementationThreadLock = new Object();
        this.mShadowNodeRegistry = new ShadowNodeRegistry();
        this.mMeasureBuffer = new int[4];
        this.mLastCalculateLayoutTime = 0;
        this.mReactContext = reactApplicationContext;
        this.mViewManagers = viewManagerRegistry;
        this.mOperationsQueue = uIViewOperationQueue;
        this.mNativeViewHierarchyOptimizer = new NativeViewHierarchyOptimizer(this.mOperationsQueue, this.mShadowNodeRegistry);
        this.mEventDispatcher = eventDispatcher;
    }

    protected ReactShadowNode createRootShadowNode() {
        ReactShadowNode reactShadowNodeImpl = new ReactShadowNodeImpl();
        if (I18nUtil.getInstance().isRTL(this.mReactContext)) {
            reactShadowNodeImpl.setLayoutDirection(YogaDirection.RTL);
        }
        reactShadowNodeImpl.setViewClassName("Root");
        return reactShadowNodeImpl;
    }

    protected ReactShadowNode createShadowNode(String str) {
        return this.mViewManagers.get(str).createShadowNodeInstance(this.mReactContext);
    }

    public final ReactShadowNode resolveShadowNode(int i) {
        return this.mShadowNodeRegistry.getNode(i);
    }

    protected final ViewManager resolveViewManager(String str) {
        return this.mViewManagers.get(str);
    }

    UIViewOperationQueue getUIViewOperationQueue() {
        return this.mOperationsQueue;
    }

    public void updateRootView(int i, int i2, int i3) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Tried to update non-existent root tag: ");
            stringBuilder.append(i);
            FLog.w(ReactConstants.TAG, stringBuilder.toString());
            return;
        }
        updateRootView(node, i2, i3);
    }

    public void updateRootView(ReactShadowNode reactShadowNode, int i, int i2) {
        reactShadowNode.setMeasureSpecs(i, i2);
    }

    public <T extends View> void registerRootView(T t, int i, ThemedReactContext themedReactContext) {
        synchronized (this.uiImplementationThreadLock) {
            final ReactShadowNode createRootShadowNode = createRootShadowNode();
            createRootShadowNode.setReactTag(i);
            createRootShadowNode.setThemedContext(themedReactContext);
            themedReactContext.runOnNativeModulesQueueThread(new Runnable() {
                public void run() {
                    UIImplementation.this.mShadowNodeRegistry.addRootNode(createRootShadowNode);
                }
            });
            this.mOperationsQueue.addRootView(i, t);
        }
    }

    public void removeRootView(int i) {
        removeRootShadowNode(i);
        this.mOperationsQueue.enqueueRemoveRootView(i);
    }

    public void removeRootShadowNode(int i) {
        synchronized (this.uiImplementationThreadLock) {
            this.mShadowNodeRegistry.removeRootNode(i);
        }
    }

    public void updateNodeSize(int i, int i2, int i3) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Tried to update size of non-existent tag: ");
            stringBuilder.append(i);
            FLog.w(ReactConstants.TAG, stringBuilder.toString());
            return;
        }
        node.setStyleWidth((float) i2);
        node.setStyleHeight((float) i3);
        dispatchViewUpdatesIfNeeded();
    }

    public void setViewLocalData(int i, Object obj) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attempt to set local data for view with unknown tag: ");
            stringBuilder.append(i);
            FLog.w(ReactConstants.TAG, stringBuilder.toString());
            return;
        }
        node.setLocalData(obj);
        dispatchViewUpdatesIfNeeded();
    }

    public void profileNextBatch() {
        this.mOperationsQueue.profileNextBatch();
    }

    public Map<String, Long> getProfiledBatchPerfCounters() {
        return this.mOperationsQueue.getProfiledBatchPerfCounters();
    }

    public void createView(int i, String str, int i2, ReadableMap readableMap) {
        synchronized (this.uiImplementationThreadLock) {
            ReactShadowNode createShadowNode = createShadowNode(str);
            ReactShadowNode node = this.mShadowNodeRegistry.getNode(i2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Root node with tag ");
            stringBuilder.append(i2);
            stringBuilder.append(" doesn't exist");
            Assertions.assertNotNull(node, stringBuilder.toString());
            createShadowNode.setReactTag(i);
            createShadowNode.setViewClassName(str);
            createShadowNode.setRootTag(node.getReactTag());
            createShadowNode.setThemedContext(node.getThemedContext());
            this.mShadowNodeRegistry.addNode(createShadowNode);
            ReactStylesDiffMap reactStylesDiffMap = null;
            if (readableMap != null) {
                reactStylesDiffMap = new ReactStylesDiffMap(readableMap);
                createShadowNode.updateProperties(reactStylesDiffMap);
            }
            handleCreateView(createShadowNode, i2, reactStylesDiffMap);
        }
    }

    protected void handleCreateView(ReactShadowNode reactShadowNode, int i, @Nullable ReactStylesDiffMap reactStylesDiffMap) {
        if (!reactShadowNode.isVirtual()) {
            this.mNativeViewHierarchyOptimizer.handleCreateView(reactShadowNode, reactShadowNode.getThemedContext(), reactStylesDiffMap);
        }
    }

    public void updateView(int i, String str, ReadableMap readableMap) {
        StringBuilder stringBuilder;
        if (this.mViewManagers.get(str) != null) {
            ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
            if (node == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Trying to update non-existent view with tag ");
                stringBuilder.append(i);
                throw new IllegalViewOperationException(stringBuilder.toString());
            } else if (readableMap != null) {
                ReactStylesDiffMap reactStylesDiffMap = new ReactStylesDiffMap(readableMap);
                node.updateProperties(reactStylesDiffMap);
                handleUpdateView(node, str, reactStylesDiffMap);
                return;
            } else {
                return;
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Got unknown view type: ");
        stringBuilder.append(str);
        throw new IllegalViewOperationException(stringBuilder.toString());
    }

    public void synchronouslyUpdateViewOnUIThread(int i, ReactStylesDiffMap reactStylesDiffMap) {
        UiThreadUtil.assertOnUiThread();
        this.mOperationsQueue.getNativeViewHierarchyManager().updateProperties(i, reactStylesDiffMap);
    }

    protected void handleUpdateView(ReactShadowNode reactShadowNode, String str, ReactStylesDiffMap reactStylesDiffMap) {
        if (!reactShadowNode.isVirtual()) {
            this.mNativeViewHierarchyOptimizer.handleUpdateView(reactShadowNode, str, reactStylesDiffMap);
        }
    }

    public void manageChildren(int i, @Nullable ReadableArray readableArray, @Nullable ReadableArray readableArray2, @Nullable ReadableArray readableArray3, @Nullable ReadableArray readableArray4, @Nullable ReadableArray readableArray5) {
        Throwable th;
        ReadableArray readableArray6 = readableArray;
        ReadableArray readableArray7 = readableArray2;
        ReadableArray readableArray8 = readableArray3;
        ReadableArray readableArray9 = readableArray4;
        ReadableArray readableArray10 = readableArray5;
        synchronized (this.uiImplementationThreadLock) {
            try {
                int i2;
                int i3;
                int i4;
                ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
                if (readableArray6 == null) {
                    i2 = 0;
                } else {
                    i2 = readableArray.size();
                }
                if (readableArray8 == null) {
                    i3 = 0;
                } else {
                    i3 = readableArray3.size();
                }
                if (readableArray10 == null) {
                    i4 = 0;
                } else {
                    i4 = readableArray5.size();
                }
                if (i2 != 0 && (readableArray7 == null || i2 != readableArray2.size())) {
                    throw new IllegalViewOperationException("Size of moveFrom != size of moveTo!");
                } else if (i3 == 0 || (readableArray9 != null && i3 == readableArray4.size())) {
                    ViewAtIndex[] viewAtIndexArr = new ViewAtIndex[(i2 + i3)];
                    int[] iArr = new int[(i2 + i4)];
                    int[] iArr2 = new int[iArr.length];
                    int[] iArr3 = new int[i4];
                    try {
                        int i5;
                        int[] iArr4;
                        int i6;
                        int[] iArr5;
                        ReactShadowNode reactShadowNode;
                        ReactShadowNode reactShadowNode2;
                        int i7;
                        int[] iArr6 = new int[i4];
                        if (i2 > 0) {
                            Assertions.assertNotNull(readableArray);
                            Assertions.assertNotNull(readableArray2);
                            i5 = 0;
                            while (i5 < i2) {
                                iArr4 = iArr6;
                                i6 = readableArray6.getInt(i5);
                                int reactTag = node.getChildAt(i6).getReactTag();
                                iArr5 = iArr3;
                                reactShadowNode = node;
                                viewAtIndexArr[i5] = new ViewAtIndex(reactTag, readableArray7.getInt(i5));
                                iArr[i5] = i6;
                                iArr2[i5] = reactTag;
                                i5++;
                                readableArray6 = readableArray;
                                iArr6 = iArr4;
                                iArr3 = iArr5;
                                node = reactShadowNode;
                            }
                        }
                        iArr4 = iArr6;
                        iArr5 = iArr3;
                        reactShadowNode = node;
                        if (i3 > 0) {
                            Assertions.assertNotNull(readableArray3);
                            Assertions.assertNotNull(readableArray4);
                            for (i5 = 0; i5 < i3; i5++) {
                                viewAtIndexArr[i2 + i5] = new ViewAtIndex(readableArray8.getInt(i5), readableArray9.getInt(i5));
                            }
                        }
                        if (i4 > 0) {
                            Assertions.assertNotNull(readableArray5);
                            i5 = 0;
                            while (i5 < i4) {
                                i6 = readableArray10.getInt(i5);
                                reactShadowNode2 = reactShadowNode;
                                int reactTag2 = reactShadowNode2.getChildAt(i6).getReactTag();
                                i7 = i2 + i5;
                                iArr[i7] = i6;
                                iArr2[i7] = reactTag2;
                                iArr5[i5] = reactTag2;
                                iArr4[i5] = i6;
                                i5++;
                                reactShadowNode = reactShadowNode2;
                            }
                        }
                        reactShadowNode2 = reactShadowNode;
                        Arrays.sort(viewAtIndexArr, ViewAtIndex.COMPARATOR);
                        Arrays.sort(iArr);
                        i5 = iArr.length - 1;
                        i6 = -1;
                        while (i5 >= 0) {
                            if (iArr[i5] != i6) {
                                reactShadowNode2.removeChildAt(iArr[i5]);
                                i6 = iArr[i5];
                                i5--;
                            } else {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Repeated indices in Removal list for view tag: ");
                                stringBuilder.append(i);
                                throw new IllegalViewOperationException(stringBuilder.toString());
                            }
                        }
                        i5 = 0;
                        while (i5 < viewAtIndexArr.length) {
                            ViewAtIndex viewAtIndex = viewAtIndexArr[i5];
                            int[] iArr7 = iArr4;
                            ReactShadowNode node2 = this.mShadowNodeRegistry.getNode(viewAtIndex.mTag);
                            if (node2 != null) {
                                reactShadowNode2.addChildAt(node2, viewAtIndex.mIndex);
                                i5++;
                                iArr4 = iArr7;
                            } else {
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("Trying to add unknown view tag: ");
                                stringBuilder2.append(viewAtIndex.mTag);
                                throw new IllegalViewOperationException(stringBuilder2.toString());
                            }
                        }
                        this.mNativeViewHierarchyOptimizer.handleManageChildren(reactShadowNode2, iArr, iArr2, viewAtIndexArr, iArr5, iArr4);
                        int[] iArr8 = iArr5;
                        for (int i72 : iArr8) {
                            removeShadowNode(this.mShadowNodeRegistry.getNode(i72));
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                } else {
                    throw new IllegalViewOperationException("Size of addChildTags != size of addAtIndices!");
                }
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    public void setChildren(int i, ReadableArray readableArray) {
        synchronized (this.uiImplementationThreadLock) {
            ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
            int i2 = 0;
            while (i2 < readableArray.size()) {
                ReactShadowNode node2 = this.mShadowNodeRegistry.getNode(readableArray.getInt(i2));
                if (node2 != null) {
                    node.addChildAt(node2, i2);
                    i2++;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Trying to add unknown view tag: ");
                    stringBuilder.append(readableArray.getInt(i2));
                    throw new IllegalViewOperationException(stringBuilder.toString());
                }
            }
            this.mNativeViewHierarchyOptimizer.handleSetChildren(node, readableArray);
        }
    }

    public void replaceExistingNonRootView(int i, int i2) {
        if (this.mShadowNodeRegistry.isRootNode(i) || this.mShadowNodeRegistry.isRootNode(i2)) {
            throw new IllegalViewOperationException("Trying to add or replace a root tag!");
        }
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        StringBuilder stringBuilder;
        if (node != null) {
            ReactShadowNode parent = node.getParent();
            if (parent != null) {
                i = parent.indexOf(node);
                if (i >= 0) {
                    ReadableArray createArray = Arguments.createArray();
                    createArray.pushInt(i2);
                    ReadableArray createArray2 = Arguments.createArray();
                    createArray2.pushInt(i);
                    ReadableArray createArray3 = Arguments.createArray();
                    createArray3.pushInt(i);
                    manageChildren(parent.getReactTag(), null, null, createArray, createArray2, createArray3);
                    return;
                }
                throw new IllegalStateException("Didn't find child tag in parent");
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Node is not attached to a parent: ");
            stringBuilder.append(i);
            throw new IllegalViewOperationException(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Trying to replace unknown view tag: ");
        stringBuilder.append(i);
        throw new IllegalViewOperationException(stringBuilder.toString());
    }

    public void removeSubviewsFromContainerWithID(int i) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node != null) {
            ReadableArray createArray = Arguments.createArray();
            for (int i2 = 0; i2 < node.getChildCount(); i2++) {
                createArray.pushInt(i2);
            }
            manageChildren(i, null, null, null, null, createArray);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Trying to remove subviews of an unknown view tag: ");
        stringBuilder.append(i);
        throw new IllegalViewOperationException(stringBuilder.toString());
    }

    public void findSubviewIn(int i, float f, float f2, Callback callback) {
        this.mOperationsQueue.enqueueFindTargetForTouch(i, f, f2, callback);
    }

    public void viewIsDescendantOf(int i, int i2, Callback callback) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        ReactShadowNode node2 = this.mShadowNodeRegistry.getNode(i2);
        if (node == null || node2 == null) {
            callback.invoke(Boolean.valueOf(false));
            return;
        }
        callback.invoke(Boolean.valueOf(node.isDescendantOf(node2)));
    }

    public void measure(int i, Callback callback) {
        this.mOperationsQueue.enqueueMeasure(i, callback);
    }

    public void measureInWindow(int i, Callback callback) {
        this.mOperationsQueue.enqueueMeasureInWindow(i, callback);
    }

    public void measureLayout(int i, int i2, Callback callback, Callback callback2) {
        try {
            measureLayout(i, i2, this.mMeasureBuffer);
            float toDIPFromPixel = PixelUtil.toDIPFromPixel((float) this.mMeasureBuffer[0]);
            float toDIPFromPixel2 = PixelUtil.toDIPFromPixel((float) this.mMeasureBuffer[1]);
            float toDIPFromPixel3 = PixelUtil.toDIPFromPixel((float) this.mMeasureBuffer[2]);
            float toDIPFromPixel4 = PixelUtil.toDIPFromPixel((float) this.mMeasureBuffer[3]);
            callback2.invoke(Float.valueOf(toDIPFromPixel), Float.valueOf(toDIPFromPixel2), Float.valueOf(toDIPFromPixel3), Float.valueOf(toDIPFromPixel4));
        } catch (IllegalViewOperationException e) {
            callback.invoke(e.getMessage());
        }
    }

    public void measureLayoutRelativeToParent(int i, Callback callback, Callback callback2) {
        try {
            measureLayoutRelativeToParent(i, this.mMeasureBuffer);
            float toDIPFromPixel = PixelUtil.toDIPFromPixel((float) this.mMeasureBuffer[0]);
            float toDIPFromPixel2 = PixelUtil.toDIPFromPixel((float) this.mMeasureBuffer[1]);
            float toDIPFromPixel3 = PixelUtil.toDIPFromPixel((float) this.mMeasureBuffer[2]);
            float toDIPFromPixel4 = PixelUtil.toDIPFromPixel((float) this.mMeasureBuffer[3]);
            callback2.invoke(Float.valueOf(toDIPFromPixel), Float.valueOf(toDIPFromPixel2), Float.valueOf(toDIPFromPixel3), Float.valueOf(toDIPFromPixel4));
        } catch (IllegalViewOperationException e) {
            callback.invoke(e.getMessage());
        }
    }

    public void dispatchViewUpdates(int i) {
        SystraceMessage.beginSection(0, "UIImplementation.dispatchViewUpdates").arg("batchId", i).flush();
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            updateViewHierarchy();
            this.mNativeViewHierarchyOptimizer.onBatchComplete();
            this.mOperationsQueue.dispatchViewUpdates(i, uptimeMillis, this.mLastCalculateLayoutTime);
        } finally {
            Systrace.endSection(0);
        }
    }

    private void dispatchViewUpdatesIfNeeded() {
        if (this.mOperationsQueue.isEmpty()) {
            dispatchViewUpdates(-1);
        }
    }

    protected void updateViewHierarchy() {
        String str = "rootTag";
        Systrace.beginSection(0, "UIImplementation.updateViewHierarchy");
        int i = 0;
        while (i < this.mShadowNodeRegistry.getRootNodeCount()) {
            try {
                ReactShadowNode node = this.mShadowNodeRegistry.getNode(this.mShadowNodeRegistry.getRootTag(i));
                if (!(node.getWidthMeasureSpec() == null || node.getHeightMeasureSpec() == null)) {
                    SystraceMessage.beginSection(0, "UIImplementation.notifyOnBeforeLayoutRecursive").arg(str, node.getReactTag()).flush();
                    notifyOnBeforeLayoutRecursive(node);
                    calculateRootLayout(node);
                    SystraceMessage.beginSection(0, "UIImplementation.applyUpdatesRecursive").arg(str, node.getReactTag()).flush();
                    applyUpdatesRecursive(node, 0.0f, 0.0f);
                    if (this.mLayoutUpdateListener != null) {
                        this.mOperationsQueue.enqueueLayoutUpdateFinished(node, this.mLayoutUpdateListener);
                    }
                }
                i++;
            } catch (Throwable th) {
                str = th;
            } finally {
                Systrace.endSection(0);
            }
        }
        Systrace.endSection(0);
    }

    public void setLayoutAnimationEnabledExperimental(boolean z) {
        this.mOperationsQueue.enqueueSetLayoutAnimationEnabled(z);
    }

    public void configureNextLayoutAnimation(ReadableMap readableMap, Callback callback) {
        this.mOperationsQueue.enqueueConfigureLayoutAnimation(readableMap, callback);
    }

    public void setJSResponder(int i, boolean z) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        if (node != null) {
            while (node.getNativeKind() == NativeKind.NONE) {
                node = node.getParent();
            }
            this.mOperationsQueue.enqueueSetJSResponder(node.getReactTag(), i, z);
        }
    }

    public void clearJSResponder() {
        this.mOperationsQueue.enqueueClearJSResponder();
    }

    public void dispatchViewManagerCommand(int i, int i2, @Nullable ReadableArray readableArray) {
        assertViewExists(i, "dispatchViewManagerCommand");
        this.mOperationsQueue.enqueueDispatchCommand(i, i2, readableArray);
    }

    public void showPopupMenu(int i, ReadableArray readableArray, Callback callback, Callback callback2) {
        assertViewExists(i, "showPopupMenu");
        this.mOperationsQueue.enqueueShowPopupMenu(i, readableArray, callback, callback2);
    }

    public void dismissPopupMenu() {
        this.mOperationsQueue.enqueueDismissPopupMenu();
    }

    public void sendAccessibilityEvent(int i, int i2) {
        this.mOperationsQueue.enqueueSendAccessibilityEvent(i, i2);
    }

    public void onHostResume() {
        this.mOperationsQueue.resumeFrameCallback();
    }

    public void onHostPause() {
        this.mOperationsQueue.pauseFrameCallback();
    }

    public void setViewHierarchyUpdateDebugListener(@Nullable NotThreadSafeViewHierarchyUpdateDebugListener notThreadSafeViewHierarchyUpdateDebugListener) {
        this.mOperationsQueue.setViewHierarchyUpdateDebugListener(notThreadSafeViewHierarchyUpdateDebugListener);
    }

    protected final void removeShadowNode(ReactShadowNode reactShadowNode) {
        removeShadowNodeRecursive(reactShadowNode);
        reactShadowNode.dispose();
    }

    private void removeShadowNodeRecursive(ReactShadowNode reactShadowNode) {
        NativeViewHierarchyOptimizer.handleRemoveNode(reactShadowNode);
        this.mShadowNodeRegistry.removeNode(reactShadowNode.getReactTag());
        for (int childCount = reactShadowNode.getChildCount() - 1; childCount >= 0; childCount--) {
            removeShadowNodeRecursive(reactShadowNode.getChildAt(childCount));
        }
        reactShadowNode.removeAndDisposeAllChildren();
    }

    private void measureLayout(int i, int i2, int[] iArr) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        ReactShadowNode node2 = this.mShadowNodeRegistry.getNode(i2);
        String str = "Tag ";
        if (node == null || node2 == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            if (node != null) {
                i = i2;
            }
            stringBuilder.append(i);
            stringBuilder.append(" does not exist");
            throw new IllegalViewOperationException(stringBuilder.toString());
        }
        if (node != node2) {
            ReactShadowNode parent = node.getParent();
            while (parent != node2) {
                if (parent != null) {
                    parent = parent.getParent();
                } else {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(str);
                    stringBuilder2.append(i2);
                    stringBuilder2.append(" is not an ancestor of tag ");
                    stringBuilder2.append(i);
                    throw new IllegalViewOperationException(stringBuilder2.toString());
                }
            }
        }
        measureLayoutRelativeToVerifiedAncestor(node, node2, iArr);
    }

    private void measureLayoutRelativeToParent(int i, int[] iArr) {
        ReactShadowNode node = this.mShadowNodeRegistry.getNode(i);
        StringBuilder stringBuilder;
        if (node != null) {
            ReactShadowNode parent = node.getParent();
            if (parent != null) {
                measureLayoutRelativeToVerifiedAncestor(node, parent, iArr);
                return;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("View with tag ");
            stringBuilder.append(i);
            stringBuilder.append(" doesn't have a parent!");
            throw new IllegalViewOperationException(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("No native view for tag ");
        stringBuilder.append(i);
        stringBuilder.append(" exists!");
        throw new IllegalViewOperationException(stringBuilder.toString());
    }

    private void measureLayoutRelativeToVerifiedAncestor(ReactShadowNode reactShadowNode, ReactShadowNode reactShadowNode2, int[] iArr) {
        int round;
        int round2;
        if (reactShadowNode != reactShadowNode2) {
            round = Math.round(reactShadowNode.getLayoutX());
            round2 = Math.round(reactShadowNode.getLayoutY());
            for (ReactShadowNode parent = reactShadowNode.getParent(); parent != reactShadowNode2; parent = parent.getParent()) {
                Assertions.assertNotNull(parent);
                assertNodeDoesNotNeedCustomLayoutForChildren(parent);
                round += Math.round(parent.getLayoutX());
                round2 += Math.round(parent.getLayoutY());
            }
            assertNodeDoesNotNeedCustomLayoutForChildren(reactShadowNode2);
        } else {
            round = 0;
            round2 = 0;
        }
        iArr[0] = round;
        iArr[1] = round2;
        iArr[2] = reactShadowNode.getScreenWidth();
        iArr[3] = reactShadowNode.getScreenHeight();
    }

    private void assertViewExists(int i, String str) {
        if (this.mShadowNodeRegistry.getNode(i) == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to execute operation ");
            stringBuilder.append(str);
            stringBuilder.append(" on view with tag: ");
            stringBuilder.append(i);
            stringBuilder.append(", since the view does not exists");
            throw new IllegalViewOperationException(stringBuilder.toString());
        }
    }

    private void assertNodeDoesNotNeedCustomLayoutForChildren(ReactShadowNode reactShadowNode) {
        ViewManager viewManager = (ViewManager) Assertions.assertNotNull(this.mViewManagers.get(reactShadowNode.getViewClass()));
        StringBuilder stringBuilder;
        if (viewManager instanceof IViewManagerWithChildren) {
            IViewManagerWithChildren iViewManagerWithChildren = (IViewManagerWithChildren) viewManager;
            if (iViewManagerWithChildren != null && iViewManagerWithChildren.needsCustomLayoutForChildren()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Trying to measure a view using measureLayout/measureLayoutRelativeToParent relative to an ancestor that requires custom layout for it's children (");
                stringBuilder.append(reactShadowNode.getViewClass());
                stringBuilder.append("). Use measure instead.");
                throw new IllegalViewOperationException(stringBuilder.toString());
            }
            return;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Trying to use view ");
        stringBuilder.append(reactShadowNode.getViewClass());
        stringBuilder.append(" as a parent, but its Manager doesn't extends ViewGroupManager");
        throw new IllegalViewOperationException(stringBuilder.toString());
    }

    private void notifyOnBeforeLayoutRecursive(ReactShadowNode reactShadowNode) {
        if (reactShadowNode.hasUpdates()) {
            for (int i = 0; i < reactShadowNode.getChildCount(); i++) {
                notifyOnBeforeLayoutRecursive(reactShadowNode.getChildAt(i));
            }
            reactShadowNode.onBeforeLayout(this.mNativeViewHierarchyOptimizer);
        }
    }

    protected void calculateRootLayout(ReactShadowNode reactShadowNode) {
        SystraceMessage.beginSection(0, "cssRoot.calculateLayout").arg("rootTag", reactShadowNode.getReactTag()).flush();
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            float f;
            int intValue = reactShadowNode.getWidthMeasureSpec().intValue();
            int intValue2 = reactShadowNode.getHeightMeasureSpec().intValue();
            float f2 = Float.NaN;
            if (MeasureSpec.getMode(intValue) == 0) {
                f = Float.NaN;
            } else {
                f = (float) MeasureSpec.getSize(intValue);
            }
            if (MeasureSpec.getMode(intValue2) != 0) {
                f2 = (float) MeasureSpec.getSize(intValue2);
            }
            reactShadowNode.calculateLayout(f, f2);
        } finally {
            Systrace.endSection(0);
            this.mLastCalculateLayoutTime = SystemClock.uptimeMillis() - uptimeMillis;
        }
    }

    protected void applyUpdatesRecursive(ReactShadowNode reactShadowNode, float f, float f2) {
        if (reactShadowNode.hasUpdates()) {
            Iterable<ReactShadowNode> calculateLayoutOnChildren = reactShadowNode.calculateLayoutOnChildren();
            if (calculateLayoutOnChildren != null) {
                for (ReactShadowNode applyUpdatesRecursive : calculateLayoutOnChildren) {
                    applyUpdatesRecursive(applyUpdatesRecursive, reactShadowNode.getLayoutX() + f, reactShadowNode.getLayoutY() + f2);
                }
            }
            int reactTag = reactShadowNode.getReactTag();
            if (!this.mShadowNodeRegistry.isRootNode(reactTag) && reactShadowNode.dispatchUpdates(f, f2, this.mOperationsQueue, this.mNativeViewHierarchyOptimizer) && reactShadowNode.shouldNotifyOnLayout()) {
                this.mEventDispatcher.dispatchEvent(OnLayoutEvent.obtain(reactTag, reactShadowNode.getScreenX(), reactShadowNode.getScreenY(), reactShadowNode.getScreenWidth(), reactShadowNode.getScreenHeight()));
            }
            reactShadowNode.markUpdateSeen();
        }
    }

    public void addUIBlock(UIBlock uIBlock) {
        this.mOperationsQueue.enqueueUIBlock(uIBlock);
    }

    public void prependUIBlock(UIBlock uIBlock) {
        this.mOperationsQueue.prependUIBlock(uIBlock);
    }

    public int resolveRootTagFromReactTag(int i) {
        if (this.mShadowNodeRegistry.isRootNode(i)) {
            return i;
        }
        ReactShadowNode resolveShadowNode = resolveShadowNode(i);
        int i2 = 0;
        if (resolveShadowNode != null) {
            i2 = resolveShadowNode.getRootTag();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Warning : attempted to resolve a non-existent react shadow node. reactTag=");
            stringBuilder.append(i);
            FLog.w(ReactConstants.TAG, stringBuilder.toString());
        }
        return i2;
    }

    public void setLayoutUpdateListener(LayoutUpdateListener layoutUpdateListener) {
        this.mLayoutUpdateListener = layoutUpdateListener;
    }

    public void removeLayoutUpdateListener() {
        this.mLayoutUpdateListener = null;
    }
}
