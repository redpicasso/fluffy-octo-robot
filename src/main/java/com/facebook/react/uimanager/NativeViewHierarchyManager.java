package com.facebook.react.uimanager;

import android.content.res.Resources;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import com.facebook.common.logging.FLog;
import com.facebook.react.R;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.react.touch.JSResponderHandler;
import com.facebook.react.uimanager.layoutanimation.LayoutAnimationController;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import com.google.common.primitives.Ints;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class NativeViewHierarchyManager {
    private static final String TAG = "NativeViewHierarchyManager";
    private final int[] mDroppedViewArray;
    private int mDroppedViewIndex;
    private final JSResponderHandler mJSResponderHandler;
    private boolean mLayoutAnimationEnabled;
    private final LayoutAnimationController mLayoutAnimator;
    private PopupMenu mPopupMenu;
    private final SparseBooleanArray mRootTags;
    private final RootViewManager mRootViewManager;
    private final Map<Integer, SparseIntArray> mTagsToPendingIndicesToDelete;
    private final SparseArray<ViewManager> mTagsToViewManagers;
    private final SparseArray<View> mTagsToViews;
    private final ViewManagerRegistry mViewManagers;

    private static class PopupMenuCallbackHandler implements OnMenuItemClickListener, OnDismissListener {
        boolean mConsumed;
        final Callback mSuccess;

        /* synthetic */ PopupMenuCallbackHandler(Callback callback, AnonymousClass1 anonymousClass1) {
            this(callback);
        }

        private PopupMenuCallbackHandler(Callback callback) {
            this.mConsumed = false;
            this.mSuccess = callback;
        }

        public void onDismiss(PopupMenu popupMenu) {
            if (!this.mConsumed) {
                this.mSuccess.invoke(UIManagerModuleConstants.ACTION_DISMISSED);
                this.mConsumed = true;
            }
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            if (this.mConsumed) {
                return false;
            }
            this.mSuccess.invoke(UIManagerModuleConstants.ACTION_ITEM_SELECTED, Integer.valueOf(menuItem.getOrder()));
            this.mConsumed = true;
            return true;
        }
    }

    public NativeViewHierarchyManager(ViewManagerRegistry viewManagerRegistry) {
        this(viewManagerRegistry, new RootViewManager());
    }

    public NativeViewHierarchyManager(ViewManagerRegistry viewManagerRegistry, RootViewManager rootViewManager) {
        this.mJSResponderHandler = new JSResponderHandler();
        this.mLayoutAnimator = new LayoutAnimationController();
        this.mTagsToPendingIndicesToDelete = new HashMap();
        this.mDroppedViewArray = new int[100];
        this.mDroppedViewIndex = 0;
        this.mViewManagers = viewManagerRegistry;
        this.mTagsToViews = new SparseArray();
        this.mTagsToViewManagers = new SparseArray();
        this.mRootTags = new SparseBooleanArray();
        this.mRootViewManager = rootViewManager;
    }

    public final synchronized View resolveView(int i) {
        View view;
        view = (View) this.mTagsToViews.get(i);
        if (view == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Trying to resolve view with tag ");
            stringBuilder.append(i);
            stringBuilder.append(" which doesn't exist");
            throw new IllegalViewOperationException(stringBuilder.toString());
        }
        return view;
    }

    public final synchronized ViewManager resolveViewManager(int i) {
        ViewManager viewManager;
        viewManager = (ViewManager) this.mTagsToViewManagers.get(i);
        if (viewManager == null) {
            boolean contains = Arrays.asList(new int[][]{this.mDroppedViewArray}).contains(Integer.valueOf(i));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewManager for tag ");
            stringBuilder.append(i);
            stringBuilder.append(" could not be found.\n View already dropped? ");
            stringBuilder.append(contains);
            stringBuilder.append(".\nLast index ");
            stringBuilder.append(this.mDroppedViewIndex);
            stringBuilder.append(" in last 100 views");
            stringBuilder.append(this.mDroppedViewArray.toString());
            throw new IllegalViewOperationException(stringBuilder.toString());
        }
        return viewManager;
    }

    public void setLayoutAnimationEnabled(boolean z) {
        this.mLayoutAnimationEnabled = z;
    }

    public synchronized void updateInstanceHandle(int i, long j) {
        UiThreadUtil.assertOnUiThread();
        try {
            updateInstanceHandle(resolveView(i), j);
        } catch (Throwable e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to update properties for view tag ");
            stringBuilder.append(i);
            FLog.e(str, stringBuilder.toString(), e);
        }
        return;
    }

    public synchronized void updateProperties(int i, ReactStylesDiffMap reactStylesDiffMap) {
        UiThreadUtil.assertOnUiThread();
        try {
            ViewManager resolveViewManager = resolveViewManager(i);
            View resolveView = resolveView(i);
            if (reactStylesDiffMap != null) {
                resolveViewManager.updateProperties(resolveView, reactStylesDiffMap);
            }
        } catch (Throwable e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to update properties for view tag ");
            stringBuilder.append(i);
            FLog.e(str, stringBuilder.toString(), e);
        }
        return;
    }

    public synchronized void updateViewExtraData(int i, Object obj) {
        UiThreadUtil.assertOnUiThread();
        resolveViewManager(i).updateExtraData(resolveView(i), obj);
    }

    public synchronized void updateLayout(int i, int i2, int i3, int i4, int i5, int i6) {
        UiThreadUtil.assertOnUiThread();
        SystraceMessage.beginSection(0, "NativeViewHierarchyManager_updateLayout").arg("parentTag", i).arg("tag", i2).flush();
        try {
            View resolveView = resolveView(i2);
            resolveView.measure(MeasureSpec.makeMeasureSpec(i5, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(i6, Ints.MAX_POWER_OF_TWO));
            ViewParent parent = resolveView.getParent();
            if (parent instanceof RootView) {
                parent.requestLayout();
            }
            if (this.mRootTags.get(i)) {
                updateLayout(resolveView, i3, i4, i5, i6);
            } else {
                ViewManager viewManager = (ViewManager) this.mTagsToViewManagers.get(i);
                if (viewManager instanceof IViewManagerWithChildren) {
                    IViewManagerWithChildren iViewManagerWithChildren = (IViewManagerWithChildren) viewManager;
                    if (!(iViewManagerWithChildren == null || iViewManagerWithChildren.needsCustomLayoutForChildren())) {
                        updateLayout(resolveView, i3, i4, i5, i6);
                    }
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Trying to use view with tag ");
                    stringBuilder.append(i);
                    stringBuilder.append(" as a parent, but its Manager doesn't implement IViewManagerWithChildren");
                    throw new IllegalViewOperationException(stringBuilder.toString());
                }
            }
        } finally {
            Systrace.endSection(0);
        }
    }

    private void updateInstanceHandle(View view, long j) {
        UiThreadUtil.assertOnUiThread();
        view.setTag(R.id.view_tag_instance_handle, Long.valueOf(j));
    }

    @Nullable
    public long getInstanceHandle(int i) {
        View view = (View) this.mTagsToViews.get(i);
        StringBuilder stringBuilder;
        if (view != null) {
            Long l = (Long) view.getTag(R.id.view_tag_instance_handle);
            if (l != null) {
                return l.longValue();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to find instanceHandle for tag: ");
            stringBuilder.append(i);
            throw new IllegalViewOperationException(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to find view for tag: ");
        stringBuilder.append(i);
        throw new IllegalViewOperationException(stringBuilder.toString());
    }

    private void updateLayout(View view, int i, int i2, int i3, int i4) {
        if (this.mLayoutAnimationEnabled && this.mLayoutAnimator.shouldAnimateLayout(view)) {
            this.mLayoutAnimator.applyLayoutUpdate(view, i, i2, i3, i4);
        } else {
            view.layout(i, i2, i3 + i, i4 + i2);
        }
    }

    public synchronized void createView(ThemedReactContext themedReactContext, int i, String str, @Nullable ReactStylesDiffMap reactStylesDiffMap) {
        UiThreadUtil.assertOnUiThread();
        SystraceMessage.beginSection(0, "NativeViewHierarchyManager_createView").arg("tag", i).arg("className", (Object) str).flush();
        try {
            ViewManager viewManager = this.mViewManagers.get(str);
            View createViewWithProps = viewManager.createViewWithProps(themedReactContext, null, this.mJSResponderHandler);
            this.mTagsToViews.put(i, createViewWithProps);
            this.mTagsToViewManagers.put(i, viewManager);
            createViewWithProps.setId(i);
            if (reactStylesDiffMap != null) {
                viewManager.updateProperties(createViewWithProps, reactStylesDiffMap);
            }
        } finally {
            Systrace.endSection(0);
        }
    }

    private static String constructManageChildrenErrorMessage(ViewGroup viewGroup, ViewGroupManager viewGroupManager, @Nullable int[] iArr, @Nullable ViewAtIndex[] viewAtIndexArr, @Nullable int[] iArr2) {
        StringBuilder stringBuilder;
        int i;
        StringBuilder stringBuilder2;
        int i2;
        int i3;
        int i4;
        StringBuilder stringBuilder3 = new StringBuilder();
        String str = " ],\n";
        String str2 = ",";
        String str3 = "): [\n";
        String str4 = ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE;
        if (viewGroup != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("View tag:");
            stringBuilder.append(viewGroup.getId());
            stringBuilder.append(str4);
            stringBuilder3.append(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("  children(");
            stringBuilder.append(viewGroupManager.getChildCount(viewGroup));
            stringBuilder.append(str3);
            stringBuilder3.append(stringBuilder.toString());
            for (i = 0; i < viewGroupManager.getChildCount(viewGroup); i += 16) {
                int i5 = 0;
                while (true) {
                    int i6 = i + i5;
                    if (i6 >= viewGroupManager.getChildCount(viewGroup) || i5 >= 16) {
                        stringBuilder3.append(str4);
                    } else {
                        StringBuilder stringBuilder4 = new StringBuilder();
                        stringBuilder4.append(viewGroupManager.getChildAt(viewGroup, i6).getId());
                        stringBuilder4.append(str2);
                        stringBuilder3.append(stringBuilder4.toString());
                        i5++;
                    }
                }
                stringBuilder3.append(str4);
            }
            stringBuilder3.append(str);
        }
        if (iArr != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("  indicesToRemove(");
            stringBuilder2.append(iArr.length);
            stringBuilder2.append(str3);
            stringBuilder3.append(stringBuilder2.toString());
            for (i2 = 0; i2 < iArr.length; i2 += 16) {
                i3 = 0;
                while (true) {
                    i = i2 + i3;
                    if (i >= iArr.length || i3 >= 16) {
                        stringBuilder3.append(str4);
                    } else {
                        StringBuilder stringBuilder5 = new StringBuilder();
                        stringBuilder5.append(iArr[i]);
                        stringBuilder5.append(str2);
                        stringBuilder3.append(stringBuilder5.toString());
                        i3++;
                    }
                }
                stringBuilder3.append(str4);
            }
            stringBuilder3.append(str);
        }
        if (viewAtIndexArr != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("  viewsToAdd(");
            stringBuilder2.append(viewAtIndexArr.length);
            stringBuilder2.append(str3);
            stringBuilder3.append(stringBuilder2.toString());
            for (i2 = 0; i2 < viewAtIndexArr.length; i2 += 16) {
                i3 = 0;
                while (true) {
                    i4 = i2 + i3;
                    if (i4 >= viewAtIndexArr.length || i3 >= 16) {
                        stringBuilder3.append(str4);
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("[");
                        stringBuilder.append(viewAtIndexArr[i4].mIndex);
                        stringBuilder.append(str2);
                        stringBuilder.append(viewAtIndexArr[i4].mTag);
                        stringBuilder.append("],");
                        stringBuilder3.append(stringBuilder.toString());
                        i3++;
                    }
                }
                stringBuilder3.append(str4);
            }
            stringBuilder3.append(str);
        }
        if (iArr2 != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("  tagsToDelete(");
            stringBuilder2.append(iArr2.length);
            stringBuilder2.append(str3);
            stringBuilder3.append(stringBuilder2.toString());
            for (i2 = 0; i2 < iArr2.length; i2 += 16) {
                i3 = 0;
                while (true) {
                    i4 = i2 + i3;
                    if (i4 >= iArr2.length || i3 >= 16) {
                        stringBuilder3.append(str4);
                    } else {
                        StringBuilder stringBuilder6 = new StringBuilder();
                        stringBuilder6.append(iArr2[i4]);
                        stringBuilder6.append(str2);
                        stringBuilder3.append(stringBuilder6.toString());
                        i3++;
                    }
                }
                stringBuilder3.append(str4);
            }
            stringBuilder3.append(" ]\n");
        }
        return stringBuilder3.toString();
    }

    private int normalizeIndex(int i, SparseIntArray sparseIntArray) {
        int i2 = i;
        for (int i3 = 0; i3 <= i; i3++) {
            i2 += sparseIntArray.get(i3);
        }
        return i2;
    }

    private SparseIntArray getOrCreatePendingIndicesToDelete(int i) {
        SparseIntArray sparseIntArray = (SparseIntArray) this.mTagsToPendingIndicesToDelete.get(Integer.valueOf(i));
        if (sparseIntArray != null) {
            return sparseIntArray;
        }
        sparseIntArray = new SparseIntArray();
        this.mTagsToPendingIndicesToDelete.put(Integer.valueOf(i), sparseIntArray);
        return sparseIntArray;
    }

    /* JADX WARNING: Missing block: B:61:0x01ad, code:
            return;
     */
    public synchronized void manageChildren(int r17, @javax.annotation.Nullable int[] r18, @javax.annotation.Nullable com.facebook.react.uimanager.ViewAtIndex[] r19, @javax.annotation.Nullable int[] r20, @javax.annotation.Nullable int[] r21) {
        /*
        r16 = this;
        r8 = r16;
        r0 = r17;
        r9 = r18;
        r10 = r19;
        r11 = r20;
        monitor-enter(r16);
        com.facebook.react.bridge.UiThreadUtil.assertOnUiThread();	 Catch:{ all -> 0x01d3 }
        r12 = r16.getOrCreatePendingIndicesToDelete(r17);	 Catch:{ all -> 0x01d3 }
        r1 = r8.mTagsToViews;	 Catch:{ all -> 0x01d3 }
        r1 = r1.get(r0);	 Catch:{ all -> 0x01d3 }
        r13 = r1;
        r13 = (android.view.ViewGroup) r13;	 Catch:{ all -> 0x01d3 }
        r1 = r16.resolveViewManager(r17);	 Catch:{ all -> 0x01d3 }
        r14 = r1;
        r14 = (com.facebook.react.uimanager.ViewGroupManager) r14;	 Catch:{ all -> 0x01d3 }
        if (r13 == 0) goto L_0x01ae;
    L_0x0024:
        r1 = r14.getChildCount(r13);	 Catch:{ all -> 0x01d3 }
        if (r9 == 0) goto L_0x00f2;
    L_0x002a:
        r2 = r9.length;	 Catch:{ all -> 0x01d3 }
        r2 = r2 + -1;
    L_0x002d:
        if (r2 < 0) goto L_0x00f2;
    L_0x002f:
        r3 = r9[r2];	 Catch:{ all -> 0x01d3 }
        if (r3 < 0) goto L_0x00c7;
    L_0x0033:
        r4 = r14.getChildCount(r13);	 Catch:{ all -> 0x01d3 }
        if (r3 < r4) goto L_0x0074;
    L_0x0039:
        r1 = r8.mRootTags;	 Catch:{ all -> 0x01d3 }
        r1 = r1.get(r0);	 Catch:{ all -> 0x01d3 }
        if (r1 == 0) goto L_0x0049;
    L_0x0041:
        r1 = r14.getChildCount(r13);	 Catch:{ all -> 0x01d3 }
        if (r1 != 0) goto L_0x0049;
    L_0x0047:
        monitor-exit(r16);
        return;
    L_0x0049:
        r1 = new com.facebook.react.uimanager.IllegalViewOperationException;	 Catch:{ all -> 0x01d3 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01d3 }
        r2.<init>();	 Catch:{ all -> 0x01d3 }
        r4 = "Trying to remove a view index above child count ";
        r2.append(r4);	 Catch:{ all -> 0x01d3 }
        r2.append(r3);	 Catch:{ all -> 0x01d3 }
        r3 = " view tag: ";
        r2.append(r3);	 Catch:{ all -> 0x01d3 }
        r2.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = "\n detail: ";
        r2.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = constructManageChildrenErrorMessage(r13, r14, r9, r10, r11);	 Catch:{ all -> 0x01d3 }
        r2.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = r2.toString();	 Catch:{ all -> 0x01d3 }
        r1.<init>(r0);	 Catch:{ all -> 0x01d3 }
        throw r1;	 Catch:{ all -> 0x01d3 }
    L_0x0074:
        if (r3 >= r1) goto L_0x009c;
    L_0x0076:
        r1 = r8.normalizeIndex(r3, r12);	 Catch:{ all -> 0x01d3 }
        r4 = r14.getChildAt(r13, r1);	 Catch:{ all -> 0x01d3 }
        r5 = r8.mLayoutAnimationEnabled;	 Catch:{ all -> 0x01d3 }
        if (r5 == 0) goto L_0x0095;
    L_0x0082:
        r5 = r8.mLayoutAnimator;	 Catch:{ all -> 0x01d3 }
        r5 = r5.shouldAnimateLayout(r4);	 Catch:{ all -> 0x01d3 }
        if (r5 == 0) goto L_0x0095;
    L_0x008a:
        r4 = r4.getId();	 Catch:{ all -> 0x01d3 }
        r4 = r8.arrayContains(r11, r4);	 Catch:{ all -> 0x01d3 }
        if (r4 == 0) goto L_0x0095;
    L_0x0094:
        goto L_0x0098;
    L_0x0095:
        r14.removeViewAt(r13, r1);	 Catch:{ all -> 0x01d3 }
    L_0x0098:
        r2 = r2 + -1;
        r1 = r3;
        goto L_0x002d;
    L_0x009c:
        r1 = new com.facebook.react.uimanager.IllegalViewOperationException;	 Catch:{ all -> 0x01d3 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01d3 }
        r2.<init>();	 Catch:{ all -> 0x01d3 }
        r4 = "Trying to remove an out of order view index:";
        r2.append(r4);	 Catch:{ all -> 0x01d3 }
        r2.append(r3);	 Catch:{ all -> 0x01d3 }
        r3 = " view tag: ";
        r2.append(r3);	 Catch:{ all -> 0x01d3 }
        r2.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = "\n detail: ";
        r2.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = constructManageChildrenErrorMessage(r13, r14, r9, r10, r11);	 Catch:{ all -> 0x01d3 }
        r2.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = r2.toString();	 Catch:{ all -> 0x01d3 }
        r1.<init>(r0);	 Catch:{ all -> 0x01d3 }
        throw r1;	 Catch:{ all -> 0x01d3 }
    L_0x00c7:
        r1 = new com.facebook.react.uimanager.IllegalViewOperationException;	 Catch:{ all -> 0x01d3 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01d3 }
        r2.<init>();	 Catch:{ all -> 0x01d3 }
        r4 = "Trying to remove a negative view index:";
        r2.append(r4);	 Catch:{ all -> 0x01d3 }
        r2.append(r3);	 Catch:{ all -> 0x01d3 }
        r3 = " view tag: ";
        r2.append(r3);	 Catch:{ all -> 0x01d3 }
        r2.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = "\n detail: ";
        r2.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = constructManageChildrenErrorMessage(r13, r14, r9, r10, r11);	 Catch:{ all -> 0x01d3 }
        r2.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = r2.toString();	 Catch:{ all -> 0x01d3 }
        r1.<init>(r0);	 Catch:{ all -> 0x01d3 }
        throw r1;	 Catch:{ all -> 0x01d3 }
    L_0x00f2:
        r0 = 0;
        if (r11 == 0) goto L_0x0165;
    L_0x00f5:
        r15 = 0;
    L_0x00f6:
        r1 = r11.length;	 Catch:{ all -> 0x01d3 }
        if (r15 >= r1) goto L_0x0165;
    L_0x00f9:
        r1 = r11[r15];	 Catch:{ all -> 0x01d3 }
        r7 = r21[r15];	 Catch:{ all -> 0x01d3 }
        r2 = r8.mTagsToViews;	 Catch:{ all -> 0x01d3 }
        r2 = r2.get(r1);	 Catch:{ all -> 0x01d3 }
        r6 = r2;
        r6 = (android.view.View) r6;	 Catch:{ all -> 0x01d3 }
        if (r6 == 0) goto L_0x013e;
    L_0x0108:
        r1 = r8.mLayoutAnimationEnabled;	 Catch:{ all -> 0x01d3 }
        if (r1 == 0) goto L_0x0132;
    L_0x010c:
        r1 = r8.mLayoutAnimator;	 Catch:{ all -> 0x01d3 }
        r1 = r1.shouldAnimateLayout(r6);	 Catch:{ all -> 0x01d3 }
        if (r1 == 0) goto L_0x0132;
    L_0x0114:
        r1 = r12.get(r7, r0);	 Catch:{ all -> 0x01d3 }
        r1 = r1 + 1;
        r12.put(r7, r1);	 Catch:{ all -> 0x01d3 }
        r5 = r8.mLayoutAnimator;	 Catch:{ all -> 0x01d3 }
        r4 = new com.facebook.react.uimanager.NativeViewHierarchyManager$1;	 Catch:{ all -> 0x01d3 }
        r1 = r4;
        r2 = r16;
        r3 = r14;
        r0 = r4;
        r4 = r13;
        r9 = r5;
        r5 = r6;
        r10 = r6;
        r6 = r12;
        r1.<init>(r3, r4, r5, r6, r7);	 Catch:{ all -> 0x01d3 }
        r9.deleteView(r10, r0);	 Catch:{ all -> 0x01d3 }
        goto L_0x0136;
    L_0x0132:
        r10 = r6;
        r8.dropView(r10);	 Catch:{ all -> 0x01d3 }
    L_0x0136:
        r15 = r15 + 1;
        r9 = r18;
        r10 = r19;
        r0 = 0;
        goto L_0x00f6;
    L_0x013e:
        r0 = new com.facebook.react.uimanager.IllegalViewOperationException;	 Catch:{ all -> 0x01d3 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01d3 }
        r2.<init>();	 Catch:{ all -> 0x01d3 }
        r3 = "Trying to destroy unknown view tag: ";
        r2.append(r3);	 Catch:{ all -> 0x01d3 }
        r2.append(r1);	 Catch:{ all -> 0x01d3 }
        r1 = "\n detail: ";
        r2.append(r1);	 Catch:{ all -> 0x01d3 }
        r1 = r18;
        r3 = r19;
        r1 = constructManageChildrenErrorMessage(r13, r14, r1, r3, r11);	 Catch:{ all -> 0x01d3 }
        r2.append(r1);	 Catch:{ all -> 0x01d3 }
        r1 = r2.toString();	 Catch:{ all -> 0x01d3 }
        r0.<init>(r1);	 Catch:{ all -> 0x01d3 }
        throw r0;	 Catch:{ all -> 0x01d3 }
    L_0x0165:
        r1 = r9;
        r3 = r10;
        if (r3 == 0) goto L_0x01ac;
    L_0x0169:
        r0 = 0;
    L_0x016a:
        r2 = r3.length;	 Catch:{ all -> 0x01d3 }
        if (r0 >= r2) goto L_0x01ac;
    L_0x016d:
        r2 = r3[r0];	 Catch:{ all -> 0x01d3 }
        r4 = r8.mTagsToViews;	 Catch:{ all -> 0x01d3 }
        r5 = r2.mTag;	 Catch:{ all -> 0x01d3 }
        r4 = r4.get(r5);	 Catch:{ all -> 0x01d3 }
        r4 = (android.view.View) r4;	 Catch:{ all -> 0x01d3 }
        if (r4 == 0) goto L_0x0187;
    L_0x017b:
        r2 = r2.mIndex;	 Catch:{ all -> 0x01d3 }
        r2 = r8.normalizeIndex(r2, r12);	 Catch:{ all -> 0x01d3 }
        r14.addView(r13, r4, r2);	 Catch:{ all -> 0x01d3 }
        r0 = r0 + 1;
        goto L_0x016a;
    L_0x0187:
        r0 = new com.facebook.react.uimanager.IllegalViewOperationException;	 Catch:{ all -> 0x01d3 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01d3 }
        r4.<init>();	 Catch:{ all -> 0x01d3 }
        r5 = "Trying to add unknown view tag: ";
        r4.append(r5);	 Catch:{ all -> 0x01d3 }
        r2 = r2.mTag;	 Catch:{ all -> 0x01d3 }
        r4.append(r2);	 Catch:{ all -> 0x01d3 }
        r2 = "\n detail: ";
        r4.append(r2);	 Catch:{ all -> 0x01d3 }
        r1 = constructManageChildrenErrorMessage(r13, r14, r1, r3, r11);	 Catch:{ all -> 0x01d3 }
        r4.append(r1);	 Catch:{ all -> 0x01d3 }
        r1 = r4.toString();	 Catch:{ all -> 0x01d3 }
        r0.<init>(r1);	 Catch:{ all -> 0x01d3 }
        throw r0;	 Catch:{ all -> 0x01d3 }
    L_0x01ac:
        monitor-exit(r16);
        return;
    L_0x01ae:
        r1 = r9;
        r3 = r10;
        r2 = new com.facebook.react.uimanager.IllegalViewOperationException;	 Catch:{ all -> 0x01d3 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01d3 }
        r4.<init>();	 Catch:{ all -> 0x01d3 }
        r5 = "Trying to manageChildren view with tag ";
        r4.append(r5);	 Catch:{ all -> 0x01d3 }
        r4.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = " which doesn't exist\n detail: ";
        r4.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = constructManageChildrenErrorMessage(r13, r14, r1, r3, r11);	 Catch:{ all -> 0x01d3 }
        r4.append(r0);	 Catch:{ all -> 0x01d3 }
        r0 = r4.toString();	 Catch:{ all -> 0x01d3 }
        r2.<init>(r0);	 Catch:{ all -> 0x01d3 }
        throw r2;	 Catch:{ all -> 0x01d3 }
    L_0x01d3:
        r0 = move-exception;
        monitor-exit(r16);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.NativeViewHierarchyManager.manageChildren(int, int[], com.facebook.react.uimanager.ViewAtIndex[], int[], int[]):void");
    }

    private boolean arrayContains(@Nullable int[] iArr, int i) {
        if (iArr == null) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    private static String constructSetChildrenErrorMessage(ViewGroup viewGroup, ViewGroupManager viewGroupManager, ReadableArray readableArray) {
        ViewAtIndex[] viewAtIndexArr = new ViewAtIndex[readableArray.size()];
        for (int i = 0; i < readableArray.size(); i++) {
            viewAtIndexArr[i] = new ViewAtIndex(readableArray.getInt(i), i);
        }
        return constructManageChildrenErrorMessage(viewGroup, viewGroupManager, null, viewAtIndexArr, null);
    }

    public synchronized void setChildren(int i, ReadableArray readableArray) {
        UiThreadUtil.assertOnUiThread();
        ViewGroup viewGroup = (ViewGroup) this.mTagsToViews.get(i);
        ViewGroupManager viewGroupManager = (ViewGroupManager) resolveViewManager(i);
        int i2 = 0;
        while (i2 < readableArray.size()) {
            View view = (View) this.mTagsToViews.get(readableArray.getInt(i2));
            if (view != null) {
                viewGroupManager.addView(viewGroup, view, i2);
                i2++;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Trying to add unknown view tag: ");
                stringBuilder.append(readableArray.getInt(i2));
                stringBuilder.append("\n detail: ");
                stringBuilder.append(constructSetChildrenErrorMessage(viewGroup, viewGroupManager, readableArray));
                throw new IllegalViewOperationException(stringBuilder.toString());
            }
        }
    }

    public synchronized void addRootView(int i, View view) {
        addRootViewGroup(i, view);
    }

    protected final synchronized void addRootViewGroup(int i, View view) {
        if (view.getId() != -1) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Trying to add a root view with an explicit id (");
            stringBuilder.append(view.getId());
            stringBuilder.append(") already set. React Native uses the id field to track react tags and will overwrite this field. If that is fine, explicitly overwrite the id field to View.NO_ID before calling addRootView.");
            FLog.e(str, stringBuilder.toString());
        }
        this.mTagsToViews.put(i, view);
        this.mTagsToViewManagers.put(i, this.mRootViewManager);
        this.mRootTags.put(i, true);
        view.setId(i);
    }

    private void cacheDroppedTag(int i) {
        int[] iArr = this.mDroppedViewArray;
        int i2 = this.mDroppedViewIndex;
        iArr[i2] = i;
        this.mDroppedViewIndex = (i2 + 1) % 100;
    }

    protected synchronized void dropView(View view) {
        UiThreadUtil.assertOnUiThread();
        if (view != null) {
            if (ReactFeatureFlags.logDroppedViews) {
                cacheDroppedTag(view.getId());
            }
            if (this.mTagsToViewManagers.get(view.getId()) != null) {
                if (!this.mRootTags.get(view.getId())) {
                    resolveViewManager(view.getId()).onDropViewInstance(view);
                }
                ViewManager viewManager = (ViewManager) this.mTagsToViewManagers.get(view.getId());
                if ((view instanceof ViewGroup) && (viewManager instanceof ViewGroupManager)) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    ViewGroupManager viewGroupManager = (ViewGroupManager) viewManager;
                    for (int childCount = viewGroupManager.getChildCount(viewGroup) - 1; childCount >= 0; childCount--) {
                        View childAt = viewGroupManager.getChildAt(viewGroup, childCount);
                        if (childAt == null) {
                            FLog.e(TAG, "Unable to drop null child view");
                        } else if (this.mTagsToViews.get(childAt.getId()) != null) {
                            dropView(childAt);
                        }
                    }
                    viewGroupManager.removeAllViews(viewGroup);
                }
                this.mTagsToPendingIndicesToDelete.remove(Integer.valueOf(view.getId()));
                this.mTagsToViews.remove(view.getId());
                this.mTagsToViewManagers.remove(view.getId());
            }
        }
    }

    public synchronized void removeRootView(int i) {
        UiThreadUtil.assertOnUiThread();
        if (!this.mRootTags.get(i)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("View with tag ");
            stringBuilder.append(i);
            stringBuilder.append(" is not registered as a root view");
            SoftAssertions.assertUnreachable(stringBuilder.toString());
        }
        dropView((View) this.mTagsToViews.get(i));
        this.mRootTags.delete(i);
    }

    public synchronized void measure(int i, int[] iArr) {
        UiThreadUtil.assertOnUiThread();
        View view = (View) this.mTagsToViews.get(i);
        StringBuilder stringBuilder;
        if (view != null) {
            View view2 = (View) RootViewUtil.getRootView(view);
            if (view2 != null) {
                view2.getLocationInWindow(iArr);
                int i2 = iArr[0];
                int i3 = iArr[1];
                view.getLocationInWindow(iArr);
                iArr[0] = iArr[0] - i2;
                iArr[1] = iArr[1] - i3;
                iArr[2] = view.getWidth();
                iArr[3] = view.getHeight();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Native view ");
                stringBuilder.append(i);
                stringBuilder.append(" is no longer on screen");
                throw new NoSuchNativeViewException(stringBuilder.toString());
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("No native view for ");
        stringBuilder.append(i);
        stringBuilder.append(" currently exists");
        throw new NoSuchNativeViewException(stringBuilder.toString());
    }

    public synchronized void measureInWindow(int i, int[] iArr) {
        UiThreadUtil.assertOnUiThread();
        View view = (View) this.mTagsToViews.get(i);
        if (view != null) {
            view.getLocationOnScreen(iArr);
            Resources resources = view.getContext().getResources();
            int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                iArr[1] = iArr[1] - ((int) resources.getDimension(identifier));
            }
            iArr[2] = view.getWidth();
            iArr[3] = view.getHeight();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No native view for ");
            stringBuilder.append(i);
            stringBuilder.append(" currently exists");
            throw new NoSuchNativeViewException(stringBuilder.toString());
        }
    }

    public synchronized int findTargetTagForTouch(int i, float f, float f2) {
        View view;
        UiThreadUtil.assertOnUiThread();
        view = (View) this.mTagsToViews.get(i);
        if (view != null) {
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find view with tag ");
            stringBuilder.append(i);
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        }
        return TouchTargetHelper.findTargetTagForTouch(f, f2, (ViewGroup) view);
    }

    public synchronized void setJSResponder(int i, int i2, boolean z) {
        if (z) {
            View view = (View) this.mTagsToViews.get(i);
            if (i2 == i || !(view instanceof ViewParent)) {
                if (this.mRootTags.get(i)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot block native responder on ");
                    stringBuilder.append(i);
                    stringBuilder.append(" that is a root view");
                    SoftAssertions.assertUnreachable(stringBuilder.toString());
                }
                this.mJSResponderHandler.setJSResponder(i2, view.getParent());
                return;
            }
            this.mJSResponderHandler.setJSResponder(i2, (ViewParent) view);
            return;
        }
        this.mJSResponderHandler.setJSResponder(i2, null);
    }

    public void clearJSResponder() {
        this.mJSResponderHandler.clearJSResponder();
    }

    void configureLayoutAnimation(ReadableMap readableMap, Callback callback) {
        this.mLayoutAnimator.initializeFromConfig(readableMap, callback);
    }

    void clearLayoutAnimation() {
        this.mLayoutAnimator.reset();
    }

    public synchronized void dispatchCommand(int i, int i2, @Nullable ReadableArray readableArray) {
        UiThreadUtil.assertOnUiThread();
        View view = (View) this.mTagsToViews.get(i);
        if (view != null) {
            resolveViewManager(i).receiveCommand(view, i2, readableArray);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Trying to send command to a non-existing view with tag ");
            stringBuilder.append(i);
            throw new IllegalViewOperationException(stringBuilder.toString());
        }
    }

    public synchronized void showPopupMenu(int i, ReadableArray readableArray, Callback callback, Callback callback2) {
        UiThreadUtil.assertOnUiThread();
        View view = (View) this.mTagsToViews.get(i);
        if (view == null) {
            Object[] objArr = new Object[1];
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't display popup. Could not find view with tag ");
            stringBuilder.append(i);
            objArr[0] = stringBuilder.toString();
            callback2.invoke(objArr);
            return;
        }
        this.mPopupMenu = new PopupMenu(getReactContextForView(i), view);
        Menu menu = this.mPopupMenu.getMenu();
        for (int i2 = 0; i2 < readableArray.size(); i2++) {
            menu.add(0, 0, i2, readableArray.getString(i2));
        }
        Object popupMenuCallbackHandler = new PopupMenuCallbackHandler(callback, null);
        this.mPopupMenu.setOnMenuItemClickListener(popupMenuCallbackHandler);
        this.mPopupMenu.setOnDismissListener(popupMenuCallbackHandler);
        this.mPopupMenu.show();
    }

    public void dismissPopupMenu() {
        PopupMenu popupMenu = this.mPopupMenu;
        if (popupMenu != null) {
            popupMenu.dismiss();
        }
    }

    private ThemedReactContext getReactContextForView(int i) {
        View view = (View) this.mTagsToViews.get(i);
        if (view != null) {
            return (ThemedReactContext) view.getContext();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not find view with tag ");
        stringBuilder.append(i);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    }

    public void sendAccessibilityEvent(int i, int i2) {
        View view = (View) this.mTagsToViews.get(i);
        if (view != null) {
            view.sendAccessibilityEvent(i2);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not find view with tag ");
        stringBuilder.append(i);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    }
}
