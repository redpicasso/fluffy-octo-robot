package com.facebook.react.uimanager;

import android.os.SystemClock;
import android.view.View;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.modules.core.ReactChoreographer.CallbackType;
import com.facebook.react.uimanager.UIImplementation.LayoutUpdateListener;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class UIViewOperationQueue {
    public static final int DEFAULT_MIN_TIME_LEFT_IN_FRAME_FOR_NONBATCHED_OPERATION_MS = 8;
    private final Object mDispatchRunnablesLock = new Object();
    private final DispatchUIFrameCallback mDispatchUIFrameCallback;
    @GuardedBy("mDispatchRunnablesLock")
    private ArrayList<Runnable> mDispatchUIRunnables = new ArrayList();
    private boolean mIsDispatchUIFrameCallbackEnqueued = false;
    private boolean mIsInIllegalUIState = false;
    private boolean mIsProfilingNextBatch = false;
    private final int[] mMeasureBuffer = new int[4];
    private final NativeViewHierarchyManager mNativeViewHierarchyManager;
    private long mNonBatchedExecutionTotalTime;
    @GuardedBy("mNonBatchedOperationsLock")
    private ArrayDeque<UIOperation> mNonBatchedOperations = new ArrayDeque();
    private final Object mNonBatchedOperationsLock = new Object();
    private ArrayList<UIOperation> mOperations = new ArrayList();
    private long mProfiledBatchBatchedExecutionTime;
    private long mProfiledBatchCommitStartTime;
    private long mProfiledBatchDispatchViewUpdatesTime;
    private long mProfiledBatchLayoutTime;
    private long mProfiledBatchNonBatchedExecutionTime;
    private long mProfiledBatchRunStartTime;
    private final ReactApplicationContext mReactApplicationContext;
    private long mThreadCpuTime;
    @Nullable
    private NotThreadSafeViewHierarchyUpdateDebugListener mViewHierarchyUpdateDebugListener;

    /* renamed from: com.facebook.react.uimanager.UIViewOperationQueue$1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ int val$batchId;
        final /* synthetic */ ArrayList val$batchedOperations;
        final /* synthetic */ long val$commitStartTime;
        final /* synthetic */ long val$dispatchViewUpdatesTime;
        final /* synthetic */ long val$layoutTime;
        final /* synthetic */ long val$nativeModulesThreadCpuTime;
        final /* synthetic */ ArrayDeque val$nonBatchedOperations;

        AnonymousClass1(int i, ArrayDeque arrayDeque, ArrayList arrayList, long j, long j2, long j3, long j4) {
            this.val$batchId = i;
            this.val$nonBatchedOperations = arrayDeque;
            this.val$batchedOperations = arrayList;
            this.val$commitStartTime = j;
            this.val$layoutTime = j2;
            this.val$dispatchViewUpdatesTime = j3;
            this.val$nativeModulesThreadCpuTime = j4;
        }

        public void run() {
            SystraceMessage.beginSection(0, "DispatchUI").arg("BatchId", this.val$batchId).flush();
            try {
                Iterator it;
                long uptimeMillis = SystemClock.uptimeMillis();
                if (this.val$nonBatchedOperations != null) {
                    it = this.val$nonBatchedOperations.iterator();
                    while (it.hasNext()) {
                        ((UIOperation) it.next()).execute();
                    }
                }
                if (this.val$batchedOperations != null) {
                    it = this.val$batchedOperations.iterator();
                    while (it.hasNext()) {
                        ((UIOperation) it.next()).execute();
                    }
                }
                if (UIViewOperationQueue.this.mIsProfilingNextBatch && UIViewOperationQueue.this.mProfiledBatchCommitStartTime == 0) {
                    UIViewOperationQueue.this.mProfiledBatchCommitStartTime = this.val$commitStartTime;
                    UIViewOperationQueue.this.mProfiledBatchLayoutTime = this.val$layoutTime;
                    UIViewOperationQueue.this.mProfiledBatchDispatchViewUpdatesTime = this.val$dispatchViewUpdatesTime;
                    UIViewOperationQueue.this.mProfiledBatchRunStartTime = uptimeMillis;
                    UIViewOperationQueue.this.mThreadCpuTime = this.val$nativeModulesThreadCpuTime;
                    Systrace.beginAsyncSection(0, "delayBeforeDispatchViewUpdates", 0, UIViewOperationQueue.this.mProfiledBatchCommitStartTime * 1000000);
                    Systrace.endAsyncSection(0, "delayBeforeDispatchViewUpdates", 0, UIViewOperationQueue.this.mProfiledBatchDispatchViewUpdatesTime * 1000000);
                    Systrace.beginAsyncSection(0, "delayBeforeBatchRunStart", 0, UIViewOperationQueue.this.mProfiledBatchDispatchViewUpdatesTime * 1000000);
                    Systrace.endAsyncSection(0, "delayBeforeBatchRunStart", 0, UIViewOperationQueue.this.mProfiledBatchRunStartTime * 1000000);
                }
                UIViewOperationQueue.this.mNativeViewHierarchyManager.clearLayoutAnimation();
                if (UIViewOperationQueue.this.mViewHierarchyUpdateDebugListener != null) {
                    UIViewOperationQueue.this.mViewHierarchyUpdateDebugListener.onViewHierarchyUpdateFinished();
                }
                Systrace.endSection(0);
            } catch (Exception e) {
                UIViewOperationQueue.this.mIsInIllegalUIState = true;
                throw e;
            } catch (Throwable th) {
                Systrace.endSection(0);
            }
        }
    }

    public interface UIOperation {
        void execute();
    }

    /* renamed from: com.facebook.react.uimanager.UIViewOperationQueue$2 */
    class AnonymousClass2 extends GuardedRunnable {
        AnonymousClass2(ReactContext reactContext) {
            super(reactContext);
        }

        public void runGuarded() {
            UIViewOperationQueue.this.flushPendingBatches();
        }
    }

    private static abstract class AnimationOperation implements UIOperation {
        protected final int mAnimationID;

        public AnimationOperation(int i) {
            this.mAnimationID = i;
        }
    }

    private class ConfigureLayoutAnimationOperation implements UIOperation {
        private final Callback mAnimationComplete;
        private final ReadableMap mConfig;

        /* synthetic */ ConfigureLayoutAnimationOperation(UIViewOperationQueue uIViewOperationQueue, ReadableMap readableMap, Callback callback, AnonymousClass1 anonymousClass1) {
            this(readableMap, callback);
        }

        private ConfigureLayoutAnimationOperation(ReadableMap readableMap, Callback callback) {
            this.mConfig = readableMap;
            this.mAnimationComplete = callback;
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.configureLayoutAnimation(this.mConfig, this.mAnimationComplete);
        }
    }

    private final class DismissPopupMenuOperation implements UIOperation {
        private DismissPopupMenuOperation() {
        }

        /* synthetic */ DismissPopupMenuOperation(UIViewOperationQueue uIViewOperationQueue, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.dismissPopupMenu();
        }
    }

    private final class FindTargetForTouchOperation implements UIOperation {
        private final Callback mCallback;
        private final int mReactTag;
        private final float mTargetX;
        private final float mTargetY;

        /* synthetic */ FindTargetForTouchOperation(UIViewOperationQueue uIViewOperationQueue, int i, float f, float f2, Callback callback, AnonymousClass1 anonymousClass1) {
            this(i, f, f2, callback);
        }

        private FindTargetForTouchOperation(int i, float f, float f2, Callback callback) {
            this.mReactTag = i;
            this.mTargetX = f;
            this.mTargetY = f2;
            this.mCallback = callback;
        }

        public void execute() {
            try {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.mReactTag, UIViewOperationQueue.this.mMeasureBuffer);
                float f = (float) UIViewOperationQueue.this.mMeasureBuffer[0];
                float f2 = (float) UIViewOperationQueue.this.mMeasureBuffer[1];
                try {
                    UIViewOperationQueue.this.mNativeViewHierarchyManager.measure(UIViewOperationQueue.this.mNativeViewHierarchyManager.findTargetTagForTouch(this.mReactTag, this.mTargetX, this.mTargetY), UIViewOperationQueue.this.mMeasureBuffer);
                    f = PixelUtil.toDIPFromPixel(((float) UIViewOperationQueue.this.mMeasureBuffer[0]) - f);
                    f2 = PixelUtil.toDIPFromPixel(((float) UIViewOperationQueue.this.mMeasureBuffer[1]) - f2);
                    float toDIPFromPixel = PixelUtil.toDIPFromPixel((float) UIViewOperationQueue.this.mMeasureBuffer[2]);
                    float toDIPFromPixel2 = PixelUtil.toDIPFromPixel((float) UIViewOperationQueue.this.mMeasureBuffer[3]);
                    this.mCallback.invoke(Integer.valueOf(r4), Float.valueOf(f), Float.valueOf(f2), Float.valueOf(toDIPFromPixel), Float.valueOf(toDIPFromPixel2));
                } catch (IllegalViewOperationException unused) {
                    this.mCallback.invoke(new Object[0]);
                }
            } catch (IllegalViewOperationException unused2) {
                this.mCallback.invoke(new Object[0]);
            }
        }
    }

    private final class LayoutUpdateFinishedOperation implements UIOperation {
        private final LayoutUpdateListener mListener;
        private final ReactShadowNode mNode;

        /* synthetic */ LayoutUpdateFinishedOperation(UIViewOperationQueue uIViewOperationQueue, ReactShadowNode reactShadowNode, LayoutUpdateListener layoutUpdateListener, AnonymousClass1 anonymousClass1) {
            this(reactShadowNode, layoutUpdateListener);
        }

        private LayoutUpdateFinishedOperation(ReactShadowNode reactShadowNode, LayoutUpdateListener layoutUpdateListener) {
            this.mNode = reactShadowNode;
            this.mListener = layoutUpdateListener;
        }

        public void execute() {
            this.mListener.onLayoutUpdated(this.mNode);
        }
    }

    private final class MeasureInWindowOperation implements UIOperation {
        private final Callback mCallback;
        private final int mReactTag;

        /* synthetic */ MeasureInWindowOperation(UIViewOperationQueue uIViewOperationQueue, int i, Callback callback, AnonymousClass1 anonymousClass1) {
            this(i, callback);
        }

        private MeasureInWindowOperation(int i, Callback callback) {
            this.mReactTag = i;
            this.mCallback = callback;
        }

        public void execute() {
            try {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.measureInWindow(this.mReactTag, UIViewOperationQueue.this.mMeasureBuffer);
                float toDIPFromPixel = PixelUtil.toDIPFromPixel((float) UIViewOperationQueue.this.mMeasureBuffer[0]);
                float toDIPFromPixel2 = PixelUtil.toDIPFromPixel((float) UIViewOperationQueue.this.mMeasureBuffer[1]);
                float toDIPFromPixel3 = PixelUtil.toDIPFromPixel((float) UIViewOperationQueue.this.mMeasureBuffer[2]);
                float toDIPFromPixel4 = PixelUtil.toDIPFromPixel((float) UIViewOperationQueue.this.mMeasureBuffer[3]);
                this.mCallback.invoke(Float.valueOf(toDIPFromPixel), Float.valueOf(toDIPFromPixel2), Float.valueOf(toDIPFromPixel3), Float.valueOf(toDIPFromPixel4));
            } catch (NoSuchNativeViewException unused) {
                this.mCallback.invoke(new Object[0]);
            }
        }
    }

    private final class MeasureOperation implements UIOperation {
        private final Callback mCallback;
        private final int mReactTag;

        /* synthetic */ MeasureOperation(UIViewOperationQueue uIViewOperationQueue, int i, Callback callback, AnonymousClass1 anonymousClass1) {
            this(i, callback);
        }

        private MeasureOperation(int i, Callback callback) {
            this.mReactTag = i;
            this.mCallback = callback;
        }

        public void execute() {
            try {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.mReactTag, UIViewOperationQueue.this.mMeasureBuffer);
                float toDIPFromPixel = PixelUtil.toDIPFromPixel((float) UIViewOperationQueue.this.mMeasureBuffer[0]);
                float toDIPFromPixel2 = PixelUtil.toDIPFromPixel((float) UIViewOperationQueue.this.mMeasureBuffer[1]);
                float toDIPFromPixel3 = PixelUtil.toDIPFromPixel((float) UIViewOperationQueue.this.mMeasureBuffer[2]);
                float toDIPFromPixel4 = PixelUtil.toDIPFromPixel((float) UIViewOperationQueue.this.mMeasureBuffer[3]);
                this.mCallback.invoke(Integer.valueOf(0), Integer.valueOf(0), Float.valueOf(toDIPFromPixel3), Float.valueOf(toDIPFromPixel4), Float.valueOf(toDIPFromPixel), Float.valueOf(toDIPFromPixel2));
            } catch (NoSuchNativeViewException unused) {
                this.mCallback.invoke(new Object[0]);
            }
        }
    }

    private class SetLayoutAnimationEnabledOperation implements UIOperation {
        private final boolean mEnabled;

        /* synthetic */ SetLayoutAnimationEnabledOperation(UIViewOperationQueue uIViewOperationQueue, boolean z, AnonymousClass1 anonymousClass1) {
            this(z);
        }

        private SetLayoutAnimationEnabledOperation(boolean z) {
            this.mEnabled = z;
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.setLayoutAnimationEnabled(this.mEnabled);
        }
    }

    private class UIBlockOperation implements UIOperation {
        private final UIBlock mBlock;

        public UIBlockOperation(UIBlock uIBlock) {
            this.mBlock = uIBlock;
        }

        public void execute() {
            this.mBlock.execute(UIViewOperationQueue.this.mNativeViewHierarchyManager);
        }
    }

    private abstract class ViewOperation implements UIOperation {
        public int mTag;

        public ViewOperation(int i) {
            this.mTag = i;
        }
    }

    private final class ChangeJSResponderOperation extends ViewOperation {
        private final boolean mBlockNativeResponder;
        private final boolean mClearResponder;
        private final int mInitialTag;

        public ChangeJSResponderOperation(int i, int i2, boolean z, boolean z2) {
            super(i);
            this.mInitialTag = i2;
            this.mClearResponder = z;
            this.mBlockNativeResponder = z2;
        }

        public void execute() {
            if (this.mClearResponder) {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.clearJSResponder();
            } else {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.setJSResponder(this.mTag, this.mInitialTag, this.mBlockNativeResponder);
            }
        }
    }

    private final class CreateViewOperation extends ViewOperation {
        private final String mClassName;
        @Nullable
        private final ReactStylesDiffMap mInitialProps;
        private final ThemedReactContext mThemedContext;

        public CreateViewOperation(ThemedReactContext themedReactContext, int i, String str, @Nullable ReactStylesDiffMap reactStylesDiffMap) {
            super(i);
            this.mThemedContext = themedReactContext;
            this.mClassName = str;
            this.mInitialProps = reactStylesDiffMap;
            Systrace.startAsyncFlow(0, "createView", this.mTag);
        }

        public void execute() {
            Systrace.endAsyncFlow(0, "createView", this.mTag);
            UIViewOperationQueue.this.mNativeViewHierarchyManager.createView(this.mThemedContext, this.mTag, this.mClassName, this.mInitialProps);
        }
    }

    private final class DispatchCommandOperation extends ViewOperation {
        @Nullable
        private final ReadableArray mArgs;
        private final int mCommand;

        public DispatchCommandOperation(int i, int i2, @Nullable ReadableArray readableArray) {
            super(i);
            this.mCommand = i2;
            this.mArgs = readableArray;
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.dispatchCommand(this.mTag, this.mCommand, this.mArgs);
        }
    }

    private class DispatchUIFrameCallback extends GuardedFrameCallback {
        private static final int FRAME_TIME_MS = 16;
        private final int mMinTimeLeftInFrameForNonBatchedOperationMs;

        /* synthetic */ DispatchUIFrameCallback(UIViewOperationQueue uIViewOperationQueue, ReactContext reactContext, int i, AnonymousClass1 anonymousClass1) {
            this(reactContext, i);
        }

        private DispatchUIFrameCallback(ReactContext reactContext, int i) {
            super(reactContext);
            this.mMinTimeLeftInFrameForNonBatchedOperationMs = i;
        }

        public void doFrameGuarded(long j) {
            if (UIViewOperationQueue.this.mIsInIllegalUIState) {
                FLog.w(ReactConstants.TAG, "Not flushing pending UI operations because of previously thrown Exception");
                return;
            }
            Systrace.beginSection(0, "dispatchNonBatchedUIOperations");
            try {
                dispatchPendingNonBatchedOperations(j);
                UIViewOperationQueue.this.flushPendingBatches();
                ReactChoreographer.getInstance().postFrameCallback(CallbackType.DISPATCH_UI, this);
            } finally {
                Systrace.endSection(0);
            }
        }

        /* JADX WARNING: Missing block: B:11:?, code:
            r2 = android.os.SystemClock.uptimeMillis();
            r1.execute();
            com.facebook.react.uimanager.UIViewOperationQueue.access$2502(r8.this$0, com.facebook.react.uimanager.UIViewOperationQueue.access$2500(r8.this$0) + (android.os.SystemClock.uptimeMillis() - r2));
     */
        /* JADX WARNING: Missing block: B:13:0x004f, code:
            r9 = move-exception;
     */
        /* JADX WARNING: Missing block: B:14:0x0050, code:
            com.facebook.react.uimanager.UIViewOperationQueue.access$2102(r8.this$0, true);
     */
        /* JADX WARNING: Missing block: B:15:0x0056, code:
            throw r9;
     */
        private void dispatchPendingNonBatchedOperations(long r9) {
            /*
            r8 = this;
        L_0x0000:
            r0 = 16;
            r2 = java.lang.System.nanoTime();
            r2 = r2 - r9;
            r4 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
            r2 = r2 / r4;
            r0 = r0 - r2;
            r2 = r8.mMinTimeLeftInFrameForNonBatchedOperationMs;
            r2 = (long) r2;
            r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
            if (r4 >= 0) goto L_0x0014;
        L_0x0013:
            goto L_0x0028;
        L_0x0014:
            r0 = com.facebook.react.uimanager.UIViewOperationQueue.this;
            r0 = r0.mNonBatchedOperationsLock;
            monitor-enter(r0);
            r1 = com.facebook.react.uimanager.UIViewOperationQueue.this;	 Catch:{ all -> 0x0057 }
            r1 = r1.mNonBatchedOperations;	 Catch:{ all -> 0x0057 }
            r1 = r1.isEmpty();	 Catch:{ all -> 0x0057 }
            if (r1 == 0) goto L_0x0029;
        L_0x0027:
            monitor-exit(r0);	 Catch:{ all -> 0x0057 }
        L_0x0028:
            return;
        L_0x0029:
            r1 = com.facebook.react.uimanager.UIViewOperationQueue.this;	 Catch:{ all -> 0x0057 }
            r1 = r1.mNonBatchedOperations;	 Catch:{ all -> 0x0057 }
            r1 = r1.pollFirst();	 Catch:{ all -> 0x0057 }
            r1 = (com.facebook.react.uimanager.UIViewOperationQueue.UIOperation) r1;	 Catch:{ all -> 0x0057 }
            monitor-exit(r0);	 Catch:{ all -> 0x0057 }
            r2 = android.os.SystemClock.uptimeMillis();	 Catch:{ Exception -> 0x004f }
            r1.execute();	 Catch:{ Exception -> 0x004f }
            r0 = com.facebook.react.uimanager.UIViewOperationQueue.this;	 Catch:{ Exception -> 0x004f }
            r1 = com.facebook.react.uimanager.UIViewOperationQueue.this;	 Catch:{ Exception -> 0x004f }
            r4 = r1.mNonBatchedExecutionTotalTime;	 Catch:{ Exception -> 0x004f }
            r6 = android.os.SystemClock.uptimeMillis();	 Catch:{ Exception -> 0x004f }
            r6 = r6 - r2;
            r4 = r4 + r6;
            r0.mNonBatchedExecutionTotalTime = r4;	 Catch:{ Exception -> 0x004f }
            goto L_0x0000;
        L_0x004f:
            r9 = move-exception;
            r10 = com.facebook.react.uimanager.UIViewOperationQueue.this;
            r0 = 1;
            r10.mIsInIllegalUIState = r0;
            throw r9;
        L_0x0057:
            r9 = move-exception;
            monitor-exit(r0);	 Catch:{ all -> 0x0057 }
            throw r9;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.UIViewOperationQueue.DispatchUIFrameCallback.dispatchPendingNonBatchedOperations(long):void");
        }
    }

    private final class EmitOnLayoutEventOperation extends ViewOperation {
        private final int mScreenHeight;
        private final int mScreenWidth;
        private final int mScreenX;
        private final int mScreenY;

        public EmitOnLayoutEventOperation(int i, int i2, int i3, int i4, int i5) {
            super(i);
            this.mScreenX = i2;
            this.mScreenY = i3;
            this.mScreenWidth = i4;
            this.mScreenHeight = i5;
        }

        public void execute() {
            ((UIManagerModule) UIViewOperationQueue.this.mReactApplicationContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(OnLayoutEvent.obtain(this.mTag, this.mScreenX, this.mScreenY, this.mScreenWidth, this.mScreenHeight));
        }
    }

    private final class ManageChildrenOperation extends ViewOperation {
        @Nullable
        private final int[] mIndicesToDelete;
        @Nullable
        private final int[] mIndicesToRemove;
        @Nullable
        private final int[] mTagsToDelete;
        @Nullable
        private final ViewAtIndex[] mViewsToAdd;

        public ManageChildrenOperation(int i, @Nullable int[] iArr, @Nullable ViewAtIndex[] viewAtIndexArr, @Nullable int[] iArr2, @Nullable int[] iArr3) {
            super(i);
            this.mIndicesToRemove = iArr;
            this.mViewsToAdd = viewAtIndexArr;
            this.mTagsToDelete = iArr2;
            this.mIndicesToDelete = iArr3;
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.manageChildren(this.mTag, this.mIndicesToRemove, this.mViewsToAdd, this.mTagsToDelete, this.mIndicesToDelete);
        }
    }

    private final class RemoveRootViewOperation extends ViewOperation {
        public RemoveRootViewOperation(int i) {
            super(i);
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.removeRootView(this.mTag);
        }
    }

    private final class SendAccessibilityEvent extends ViewOperation {
        private final int mEventType;

        /* synthetic */ SendAccessibilityEvent(UIViewOperationQueue uIViewOperationQueue, int i, int i2, AnonymousClass1 anonymousClass1) {
            this(i, i2);
        }

        private SendAccessibilityEvent(int i, int i2) {
            super(i);
            this.mEventType = i2;
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.sendAccessibilityEvent(this.mTag, this.mEventType);
        }
    }

    private final class SetChildrenOperation extends ViewOperation {
        private final ReadableArray mChildrenTags;

        public SetChildrenOperation(int i, ReadableArray readableArray) {
            super(i);
            this.mChildrenTags = readableArray;
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.setChildren(this.mTag, this.mChildrenTags);
        }
    }

    private final class ShowPopupMenuOperation extends ViewOperation {
        private final Callback mError;
        private final ReadableArray mItems;
        private final Callback mSuccess;

        public ShowPopupMenuOperation(int i, ReadableArray readableArray, Callback callback, Callback callback2) {
            super(i);
            this.mItems = readableArray;
            this.mError = callback;
            this.mSuccess = callback2;
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.showPopupMenu(this.mTag, this.mItems, this.mSuccess, this.mError);
        }
    }

    private final class UpdateInstanceHandleOperation extends ViewOperation {
        private final long mInstanceHandle;

        /* synthetic */ UpdateInstanceHandleOperation(UIViewOperationQueue uIViewOperationQueue, int i, long j, AnonymousClass1 anonymousClass1) {
            this(i, j);
        }

        private UpdateInstanceHandleOperation(int i, long j) {
            super(i);
            this.mInstanceHandle = j;
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.updateInstanceHandle(this.mTag, this.mInstanceHandle);
        }
    }

    private final class UpdateLayoutOperation extends ViewOperation {
        private final int mHeight;
        private final int mParentTag;
        private final int mWidth;
        private final int mX;
        private final int mY;

        public UpdateLayoutOperation(int i, int i2, int i3, int i4, int i5, int i6) {
            super(i2);
            this.mParentTag = i;
            this.mX = i3;
            this.mY = i4;
            this.mWidth = i5;
            this.mHeight = i6;
            Systrace.startAsyncFlow(0, "updateLayout", this.mTag);
        }

        public void execute() {
            Systrace.endAsyncFlow(0, "updateLayout", this.mTag);
            UIViewOperationQueue.this.mNativeViewHierarchyManager.updateLayout(this.mParentTag, this.mTag, this.mX, this.mY, this.mWidth, this.mHeight);
        }
    }

    private final class UpdatePropertiesOperation extends ViewOperation {
        private final ReactStylesDiffMap mProps;

        /* synthetic */ UpdatePropertiesOperation(UIViewOperationQueue uIViewOperationQueue, int i, ReactStylesDiffMap reactStylesDiffMap, AnonymousClass1 anonymousClass1) {
            this(i, reactStylesDiffMap);
        }

        private UpdatePropertiesOperation(int i, ReactStylesDiffMap reactStylesDiffMap) {
            super(i);
            this.mProps = reactStylesDiffMap;
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.updateProperties(this.mTag, this.mProps);
        }
    }

    private final class UpdateViewExtraData extends ViewOperation {
        private final Object mExtraData;

        public UpdateViewExtraData(int i, Object obj) {
            super(i);
            this.mExtraData = obj;
        }

        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.updateViewExtraData(this.mTag, this.mExtraData);
        }
    }

    /*  JADX ERROR: NullPointerException in pass: BlockFinish
        java.lang.NullPointerException
        	at jadx.core.dex.visitors.blocksmaker.BlockFinish.fixSplitterBlock(BlockFinish.java:45)
        	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:29)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.lang.Iterable.forEach(Unknown Source)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    public void dispatchViewUpdates(int r19, long r20, long r22) {
        /*
        r18 = this;
        r14 = r18;
        r0 = r19;
        r12 = 0;
        r1 = "UIViewOperationQueue.dispatchViewUpdates";
        r1 = com.facebook.systrace.SystraceMessage.beginSection(r12, r1);
        r2 = "batchId";
        r1 = r1.arg(r2, r0);
        r1.flush();
        r10 = android.os.SystemClock.uptimeMillis();	 Catch:{ all -> 0x00a2 }
        r15 = android.os.SystemClock.currentThreadTimeMillis();	 Catch:{ all -> 0x00a2 }
        r1 = r14.mOperations;	 Catch:{ all -> 0x00a2 }
        r1 = r1.isEmpty();	 Catch:{ all -> 0x00a2 }
        r2 = 0;	 Catch:{ all -> 0x00a2 }
        if (r1 != 0) goto L_0x0031;	 Catch:{ all -> 0x00a2 }
    L_0x0026:
        r1 = r14.mOperations;	 Catch:{ all -> 0x00a2 }
        r3 = new java.util.ArrayList;	 Catch:{ all -> 0x00a2 }
        r3.<init>();	 Catch:{ all -> 0x00a2 }
        r14.mOperations = r3;	 Catch:{ all -> 0x00a2 }
        r5 = r1;	 Catch:{ all -> 0x00a2 }
        goto L_0x0032;	 Catch:{ all -> 0x00a2 }
    L_0x0031:
        r5 = r2;	 Catch:{ all -> 0x00a2 }
    L_0x0032:
        r1 = r14.mNonBatchedOperationsLock;	 Catch:{ all -> 0x00a2 }
        monitor-enter(r1);	 Catch:{ all -> 0x00a2 }
        r3 = r14.mNonBatchedOperations;	 Catch:{ all -> 0x009a, all -> 0x009e }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x009a, all -> 0x009e }
        if (r3 != 0) goto L_0x0046;	 Catch:{ all -> 0x009a, all -> 0x009e }
    L_0x003d:
        r2 = r14.mNonBatchedOperations;	 Catch:{ all -> 0x009a, all -> 0x009e }
        r3 = new java.util.ArrayDeque;	 Catch:{ all -> 0x009a, all -> 0x009e }
        r3.<init>();	 Catch:{ all -> 0x009a, all -> 0x009e }
        r14.mNonBatchedOperations = r3;	 Catch:{ all -> 0x009a, all -> 0x009e }
    L_0x0046:
        r4 = r2;	 Catch:{ all -> 0x009a, all -> 0x009e }
        monitor-exit(r1);	 Catch:{ all -> 0x009a, all -> 0x009e }
        r1 = r14.mViewHierarchyUpdateDebugListener;	 Catch:{ all -> 0x00a2 }
        if (r1 == 0) goto L_0x0051;	 Catch:{ all -> 0x00a2 }
    L_0x004c:
        r1 = r14.mViewHierarchyUpdateDebugListener;	 Catch:{ all -> 0x00a2 }
        r1.onViewHierarchyUpdateEnqueued();	 Catch:{ all -> 0x00a2 }
    L_0x0051:
        r8 = new com.facebook.react.uimanager.UIViewOperationQueue$1;	 Catch:{ all -> 0x00a2 }
        r1 = r8;
        r2 = r18;
        r3 = r19;
        r6 = r20;
        r17 = r8;
        r8 = r22;
        r12 = r15;
        r1.<init>(r3, r4, r5, r6, r8, r10, r12);	 Catch:{ all -> 0x0096 }
        r1 = "acquiring mDispatchRunnablesLock";	 Catch:{ all -> 0x0096 }
        r2 = 0;
        r1 = com.facebook.systrace.SystraceMessage.beginSection(r2, r1);	 Catch:{ all -> 0x009a, all -> 0x009e }
        r4 = "batchId";	 Catch:{ all -> 0x009a, all -> 0x009e }
        r0 = r1.arg(r4, r0);	 Catch:{ all -> 0x009a, all -> 0x009e }
        r0.flush();	 Catch:{ all -> 0x009a, all -> 0x009e }
        r1 = r14.mDispatchRunnablesLock;	 Catch:{ all -> 0x009a, all -> 0x009e }
        monitor-enter(r1);	 Catch:{ all -> 0x009a, all -> 0x009e }
        com.facebook.systrace.Systrace.endSection(r2);	 Catch:{ all -> 0x009a, all -> 0x009e }
        r0 = r14.mDispatchUIRunnables;	 Catch:{ all -> 0x009a, all -> 0x009e }
        r4 = r17;	 Catch:{ all -> 0x009a, all -> 0x009e }
        r0.add(r4);	 Catch:{ all -> 0x009a, all -> 0x009e }
        monitor-exit(r1);	 Catch:{ all -> 0x009a, all -> 0x009e }
        r0 = r14.mIsDispatchUIFrameCallbackEnqueued;	 Catch:{ all -> 0x009a, all -> 0x009e }
        if (r0 != 0) goto L_0x008f;	 Catch:{ all -> 0x009a, all -> 0x009e }
    L_0x0085:
        r0 = new com.facebook.react.uimanager.UIViewOperationQueue$2;	 Catch:{ all -> 0x009a, all -> 0x009e }
        r1 = r14.mReactApplicationContext;	 Catch:{ all -> 0x009a, all -> 0x009e }
        r0.<init>(r1);	 Catch:{ all -> 0x009a, all -> 0x009e }
        com.facebook.react.bridge.UiThreadUtil.runOnUiThread(r0);	 Catch:{ all -> 0x009a, all -> 0x009e }
    L_0x008f:
        com.facebook.systrace.Systrace.endSection(r2);
        return;
    L_0x0093:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x009a, all -> 0x009e }
        throw r0;	 Catch:{ all -> 0x009a, all -> 0x009e }
    L_0x0096:
        r0 = move-exception;
        r2 = 0;
        goto L_0x00a4;
    L_0x009a:
        r0 = move-exception;
        r2 = r12;
    L_0x009c:
        monitor-exit(r1);	 Catch:{ all -> 0x00a0 }
        throw r0;	 Catch:{ all -> 0x009a, all -> 0x009e }
    L_0x009e:
        r0 = move-exception;
        goto L_0x00a4;
    L_0x00a0:
        r0 = move-exception;
        goto L_0x009c;
    L_0x00a2:
        r0 = move-exception;
        r2 = r12;
    L_0x00a4:
        com.facebook.systrace.Systrace.endSection(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.UIViewOperationQueue.dispatchViewUpdates(int, long, long):void");
    }

    public UIViewOperationQueue(ReactApplicationContext reactApplicationContext, NativeViewHierarchyManager nativeViewHierarchyManager, int i) {
        this.mNativeViewHierarchyManager = nativeViewHierarchyManager;
        if (i == -1) {
            i = 8;
        }
        this.mDispatchUIFrameCallback = new DispatchUIFrameCallback(this, reactApplicationContext, i, null);
        this.mReactApplicationContext = reactApplicationContext;
    }

    NativeViewHierarchyManager getNativeViewHierarchyManager() {
        return this.mNativeViewHierarchyManager;
    }

    public void setViewHierarchyUpdateDebugListener(@Nullable NotThreadSafeViewHierarchyUpdateDebugListener notThreadSafeViewHierarchyUpdateDebugListener) {
        this.mViewHierarchyUpdateDebugListener = notThreadSafeViewHierarchyUpdateDebugListener;
    }

    public void profileNextBatch() {
        this.mIsProfilingNextBatch = true;
        this.mProfiledBatchCommitStartTime = 0;
    }

    public Map<String, Long> getProfiledBatchPerfCounters() {
        Map<String, Long> hashMap = new HashMap();
        hashMap.put("CommitStartTime", Long.valueOf(this.mProfiledBatchCommitStartTime));
        hashMap.put("LayoutTime", Long.valueOf(this.mProfiledBatchLayoutTime));
        hashMap.put("DispatchViewUpdatesTime", Long.valueOf(this.mProfiledBatchDispatchViewUpdatesTime));
        hashMap.put("RunStartTime", Long.valueOf(this.mProfiledBatchRunStartTime));
        hashMap.put("BatchedExecutionTime", Long.valueOf(this.mProfiledBatchBatchedExecutionTime));
        hashMap.put("NonBatchedExecutionTime", Long.valueOf(this.mProfiledBatchNonBatchedExecutionTime));
        hashMap.put("NativeModulesThreadCpuTime", Long.valueOf(this.mThreadCpuTime));
        return hashMap;
    }

    public boolean isEmpty() {
        return this.mOperations.isEmpty();
    }

    public void addRootView(int i, View view) {
        this.mNativeViewHierarchyManager.addRootView(i, view);
    }

    protected void enqueueUIOperation(UIOperation uIOperation) {
        SoftAssertions.assertNotNull(uIOperation);
        this.mOperations.add(uIOperation);
    }

    public void enqueueRemoveRootView(int i) {
        this.mOperations.add(new RemoveRootViewOperation(i));
    }

    public void enqueueSetJSResponder(int i, int i2, boolean z) {
        this.mOperations.add(new ChangeJSResponderOperation(i, i2, false, z));
    }

    public void enqueueClearJSResponder() {
        this.mOperations.add(new ChangeJSResponderOperation(0, 0, true, false));
    }

    public void enqueueDispatchCommand(int i, int i2, @Nullable ReadableArray readableArray) {
        this.mOperations.add(new DispatchCommandOperation(i, i2, readableArray));
    }

    public void enqueueUpdateExtraData(int i, Object obj) {
        this.mOperations.add(new UpdateViewExtraData(i, obj));
    }

    public void enqueueShowPopupMenu(int i, ReadableArray readableArray, Callback callback, Callback callback2) {
        this.mOperations.add(new ShowPopupMenuOperation(i, readableArray, callback, callback2));
    }

    public void enqueueDismissPopupMenu() {
        this.mOperations.add(new DismissPopupMenuOperation(this, null));
    }

    public void enqueueCreateView(ThemedReactContext themedReactContext, int i, String str, @Nullable ReactStylesDiffMap reactStylesDiffMap) {
        synchronized (this.mNonBatchedOperationsLock) {
            this.mNonBatchedOperations.addLast(new CreateViewOperation(themedReactContext, i, str, reactStylesDiffMap));
        }
    }

    public void enqueueUpdateInstanceHandle(int i, long j) {
        this.mOperations.add(new UpdateInstanceHandleOperation(this, i, j, null));
    }

    public void enqueueUpdateProperties(int i, String str, ReactStylesDiffMap reactStylesDiffMap) {
        this.mOperations.add(new UpdatePropertiesOperation(this, i, reactStylesDiffMap, null));
    }

    public void enqueueOnLayoutEvent(int i, int i2, int i3, int i4, int i5) {
        this.mOperations.add(new EmitOnLayoutEventOperation(i, i2, i3, i4, i5));
    }

    public void enqueueUpdateLayout(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mOperations.add(new UpdateLayoutOperation(i, i2, i3, i4, i5, i6));
    }

    public void enqueueManageChildren(int i, @Nullable int[] iArr, @Nullable ViewAtIndex[] viewAtIndexArr, @Nullable int[] iArr2, @Nullable int[] iArr3) {
        this.mOperations.add(new ManageChildrenOperation(i, iArr, viewAtIndexArr, iArr2, iArr3));
    }

    public void enqueueSetChildren(int i, ReadableArray readableArray) {
        this.mOperations.add(new SetChildrenOperation(i, readableArray));
    }

    public void enqueueSetLayoutAnimationEnabled(boolean z) {
        this.mOperations.add(new SetLayoutAnimationEnabledOperation(this, z, null));
    }

    public void enqueueConfigureLayoutAnimation(ReadableMap readableMap, Callback callback) {
        this.mOperations.add(new ConfigureLayoutAnimationOperation(this, readableMap, callback, null));
    }

    public void enqueueMeasure(int i, Callback callback) {
        this.mOperations.add(new MeasureOperation(this, i, callback, null));
    }

    public void enqueueMeasureInWindow(int i, Callback callback) {
        this.mOperations.add(new MeasureInWindowOperation(this, i, callback, null));
    }

    public void enqueueFindTargetForTouch(int i, float f, float f2, Callback callback) {
        this.mOperations.add(new FindTargetForTouchOperation(this, i, f, f2, callback, null));
    }

    public void enqueueSendAccessibilityEvent(int i, int i2) {
        this.mOperations.add(new SendAccessibilityEvent(this, i, i2, null));
    }

    public void enqueueLayoutUpdateFinished(ReactShadowNode reactShadowNode, LayoutUpdateListener layoutUpdateListener) {
        this.mOperations.add(new LayoutUpdateFinishedOperation(this, reactShadowNode, layoutUpdateListener, null));
    }

    public void enqueueUIBlock(UIBlock uIBlock) {
        this.mOperations.add(new UIBlockOperation(uIBlock));
    }

    public void prependUIBlock(UIBlock uIBlock) {
        this.mOperations.add(0, new UIBlockOperation(uIBlock));
    }

    void resumeFrameCallback() {
        this.mIsDispatchUIFrameCallbackEnqueued = true;
        ReactChoreographer.getInstance().postFrameCallback(CallbackType.DISPATCH_UI, this.mDispatchUIFrameCallback);
    }

    void pauseFrameCallback() {
        this.mIsDispatchUIFrameCallbackEnqueued = false;
        ReactChoreographer.getInstance().removeFrameCallback(CallbackType.DISPATCH_UI, this.mDispatchUIFrameCallback);
        flushPendingBatches();
    }

    /* JADX WARNING: Missing block: B:11:0x0021, code:
            r2 = android.os.SystemClock.uptimeMillis();
            r0 = r1.iterator();
     */
    /* JADX WARNING: Missing block: B:13:0x002d, code:
            if (r0.hasNext() == false) goto L_0x0039;
     */
    /* JADX WARNING: Missing block: B:14:0x002f, code:
            ((java.lang.Runnable) r0.next()).run();
     */
    /* JADX WARNING: Missing block: B:16:0x003d, code:
            if (r12.mIsProfilingNextBatch == false) goto L_0x005f;
     */
    /* JADX WARNING: Missing block: B:17:0x003f, code:
            r12.mProfiledBatchBatchedExecutionTime = android.os.SystemClock.uptimeMillis() - r2;
            r12.mProfiledBatchNonBatchedExecutionTime = r12.mNonBatchedExecutionTotalTime;
            r12.mIsProfilingNextBatch = false;
            com.facebook.systrace.Systrace.beginAsyncSection(0, "batchedExecutionTime", 0, 1000000 * r2);
            com.facebook.systrace.Systrace.endAsyncSection(0, "batchedExecutionTime", 0);
     */
    /* JADX WARNING: Missing block: B:18:0x005f, code:
            r12.mNonBatchedExecutionTotalTime = 0;
     */
    /* JADX WARNING: Missing block: B:19:0x0061, code:
            return;
     */
    private void flushPendingBatches() {
        /*
        r12 = this;
        r0 = r12.mIsInIllegalUIState;
        if (r0 == 0) goto L_0x000c;
    L_0x0004:
        r0 = "ReactNative";
        r1 = "Not flushing pending UI operations because of previously thrown Exception";
        com.facebook.common.logging.FLog.w(r0, r1);
        return;
    L_0x000c:
        r0 = r12.mDispatchRunnablesLock;
        monitor-enter(r0);
        r1 = r12.mDispatchUIRunnables;	 Catch:{ all -> 0x0064 }
        r1 = r1.isEmpty();	 Catch:{ all -> 0x0064 }
        if (r1 != 0) goto L_0x0062;
    L_0x0017:
        r1 = r12.mDispatchUIRunnables;	 Catch:{ all -> 0x0064 }
        r2 = new java.util.ArrayList;	 Catch:{ all -> 0x0064 }
        r2.<init>();	 Catch:{ all -> 0x0064 }
        r12.mDispatchUIRunnables = r2;	 Catch:{ all -> 0x0064 }
        monitor-exit(r0);	 Catch:{ all -> 0x0064 }
        r2 = android.os.SystemClock.uptimeMillis();
        r0 = r1.iterator();
    L_0x0029:
        r1 = r0.hasNext();
        if (r1 == 0) goto L_0x0039;
    L_0x002f:
        r1 = r0.next();
        r1 = (java.lang.Runnable) r1;
        r1.run();
        goto L_0x0029;
    L_0x0039:
        r0 = r12.mIsProfilingNextBatch;
        r4 = 0;
        if (r0 == 0) goto L_0x005f;
    L_0x003f:
        r0 = android.os.SystemClock.uptimeMillis();
        r0 = r0 - r2;
        r12.mProfiledBatchBatchedExecutionTime = r0;
        r0 = r12.mNonBatchedExecutionTotalTime;
        r12.mProfiledBatchNonBatchedExecutionTime = r0;
        r0 = 0;
        r12.mIsProfilingNextBatch = r0;
        r6 = 0;
        r9 = 0;
        r10 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
        r10 = r10 * r2;
        r8 = "batchedExecutionTime";
        com.facebook.systrace.Systrace.beginAsyncSection(r6, r8, r9, r10);
        r1 = "batchedExecutionTime";
        com.facebook.systrace.Systrace.endAsyncSection(r4, r1, r0);
    L_0x005f:
        r12.mNonBatchedExecutionTotalTime = r4;
        return;
    L_0x0062:
        monitor-exit(r0);	 Catch:{ all -> 0x0064 }
        return;
    L_0x0064:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0064 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.UIViewOperationQueue.flushPendingBatches():void");
    }
}
