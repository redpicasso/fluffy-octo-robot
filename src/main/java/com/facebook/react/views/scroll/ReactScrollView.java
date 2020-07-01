package com.facebook.react.views.scroll;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.core.view.ViewCompat;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.facebook.react.views.view.ReactViewBackgroundManager;
import java.lang.reflect.Field;
import java.util.List;
import javax.annotation.Nullable;

public class ReactScrollView extends ScrollView implements ReactClippingViewGroup, OnHierarchyChangeListener, OnLayoutChangeListener {
    @Nullable
    private static Field sScrollerField = null;
    private static boolean sTriedToGetScrollerField = false;
    private boolean mActivelyScrolling;
    @Nullable
    private Rect mClippingRect;
    private View mContentView;
    private float mDecelerationRate;
    private boolean mDragging;
    @Nullable
    private Drawable mEndBackground;
    private int mEndFillColor;
    @Nullable
    private FpsListener mFpsListener;
    private final OnScrollDispatchHelper mOnScrollDispatchHelper;
    @Nullable
    private String mOverflow;
    private boolean mPagingEnabled;
    @Nullable
    private Runnable mPostTouchRunnable;
    private ReactViewBackgroundManager mReactBackgroundManager;
    private final Rect mRect;
    private boolean mRemoveClippedSubviews;
    private boolean mScrollEnabled;
    @Nullable
    private String mScrollPerfTag;
    @Nullable
    private final OverScroller mScroller;
    private boolean mSendMomentumEvents;
    private int mSnapInterval;
    @Nullable
    private List<Integer> mSnapOffsets;
    private boolean mSnapToEnd;
    private boolean mSnapToStart;
    private final VelocityHelper mVelocityHelper;

    public ReactScrollView(ReactContext reactContext) {
        this(reactContext, null);
    }

    public ReactScrollView(ReactContext reactContext, @Nullable FpsListener fpsListener) {
        super(reactContext);
        this.mOnScrollDispatchHelper = new OnScrollDispatchHelper();
        this.mVelocityHelper = new VelocityHelper();
        this.mRect = new Rect();
        this.mOverflow = ViewProps.HIDDEN;
        this.mPagingEnabled = false;
        this.mScrollEnabled = true;
        this.mFpsListener = null;
        this.mEndFillColor = 0;
        this.mSnapInterval = 0;
        this.mDecelerationRate = 0.985f;
        this.mSnapToStart = true;
        this.mSnapToEnd = true;
        this.mFpsListener = fpsListener;
        this.mReactBackgroundManager = new ReactViewBackgroundManager(this);
        this.mScroller = getOverScrollerFromParent();
        setOnHierarchyChangeListener(this);
        setScrollBarStyle(33554432);
    }

    @Nullable
    private OverScroller getOverScrollerFromParent() {
        boolean z = sTriedToGetScrollerField;
        String str = ReactConstants.TAG;
        if (!z) {
            sTriedToGetScrollerField = true;
            try {
                sScrollerField = ScrollView.class.getDeclaredField("mScroller");
                sScrollerField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
                Log.w(str, "Failed to get mScroller field for ScrollView! This app will exhibit the bounce-back scrolling bug :(");
            }
        }
        Field field = sScrollerField;
        if (field == null) {
            return null;
        }
        try {
            Object obj = field.get(this);
            if (obj instanceof OverScroller) {
                return (OverScroller) obj;
            }
            Log.w(str, "Failed to cast mScroller field in ScrollView (probably due to OEM changes to AOSP)! This app will exhibit the bounce-back scrolling bug :(");
            return null;
        } catch (Throwable e) {
            throw new RuntimeException("Failed to get mScroller from ScrollView!", e);
        }
    }

    public void setSendMomentumEvents(boolean z) {
        this.mSendMomentumEvents = z;
    }

    public void setScrollPerfTag(@Nullable String str) {
        this.mScrollPerfTag = str;
    }

    public void setScrollEnabled(boolean z) {
        this.mScrollEnabled = z;
    }

    public void setPagingEnabled(boolean z) {
        this.mPagingEnabled = z;
    }

    public void setDecelerationRate(float f) {
        this.mDecelerationRate = f;
        OverScroller overScroller = this.mScroller;
        if (overScroller != null) {
            overScroller.setFriction(1.0f - this.mDecelerationRate);
        }
    }

    public void setSnapInterval(int i) {
        this.mSnapInterval = i;
    }

    public void setSnapOffsets(List<Integer> list) {
        this.mSnapOffsets = list;
    }

    public void setSnapToStart(boolean z) {
        this.mSnapToStart = z;
    }

    public void setSnapToEnd(boolean z) {
        this.mSnapToEnd = z;
    }

    public void flashScrollIndicators() {
        awakenScrollBars();
    }

    public void setOverflow(String str) {
        this.mOverflow = str;
        invalidate();
    }

    protected void onMeasure(int i, int i2) {
        MeasureSpecAssertions.assertExplicitMeasureSpec(i, i2);
        setMeasuredDimension(MeasureSpec.getSize(i), MeasureSpec.getSize(i2));
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        scrollTo(getScrollX(), getScrollY());
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mRemoveClippedSubviews) {
            updateClippingRect();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mRemoveClippedSubviews) {
            updateClippingRect();
        }
    }

    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        this.mActivelyScrolling = true;
        if (this.mOnScrollDispatchHelper.onScrollChanged(i, i2)) {
            if (this.mRemoveClippedSubviews) {
                updateClippingRect();
            }
            ReactScrollViewHelper.emitScrollEvent(this, this.mOnScrollDispatchHelper.getXFlingVelocity(), this.mOnScrollDispatchHelper.getYFlingVelocity());
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mScrollEnabled) {
            return false;
        }
        try {
            if (super.onInterceptTouchEvent(motionEvent)) {
                NativeGestureUtil.notifyNativeGestureStarted(this, motionEvent);
                ReactScrollViewHelper.emitScrollBeginDragEvent(this);
                this.mDragging = true;
                enableFpsListener();
                return true;
            }
        } catch (Throwable e) {
            Log.w(ReactConstants.TAG, "Error intercepting touch event.", e);
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mScrollEnabled) {
            return false;
        }
        this.mVelocityHelper.calculateVelocity(motionEvent);
        if ((motionEvent.getAction() & 255) == 1 && this.mDragging) {
            float xVelocity = this.mVelocityHelper.getXVelocity();
            float yVelocity = this.mVelocityHelper.getYVelocity();
            ReactScrollViewHelper.emitScrollEndDragEvent(this, xVelocity, yVelocity);
            this.mDragging = false;
            handlePostTouchScrolling(Math.round(xVelocity), Math.round(yVelocity));
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setRemoveClippedSubviews(boolean z) {
        if (z && this.mClippingRect == null) {
            this.mClippingRect = new Rect();
        }
        this.mRemoveClippedSubviews = z;
        updateClippingRect();
    }

    public boolean getRemoveClippedSubviews() {
        return this.mRemoveClippedSubviews;
    }

    public void updateClippingRect() {
        if (this.mRemoveClippedSubviews) {
            Assertions.assertNotNull(this.mClippingRect);
            ReactClippingViewGroupHelper.calculateClippingRect(this, this.mClippingRect);
            View childAt = getChildAt(0);
            if (childAt instanceof ReactClippingViewGroup) {
                ((ReactClippingViewGroup) childAt).updateClippingRect();
            }
        }
    }

    public void getClippingRect(Rect rect) {
        rect.set((Rect) Assertions.assertNotNull(this.mClippingRect));
    }

    public void fling(int i) {
        float signum = Math.signum(this.mOnScrollDispatchHelper.getYFlingVelocity());
        if (signum == 0.0f) {
            signum = Math.signum((float) i);
        }
        i = (int) (((float) Math.abs(i)) * signum);
        if (this.mPagingEnabled) {
            flingAndSnap(i);
        } else if (this.mScroller != null) {
            int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
            this.mScroller.fling(getScrollX(), getScrollY(), 0, i, 0, 0, 0, Integer.MAX_VALUE, 0, height / 2);
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            super.fling(i);
        }
        handlePostTouchScrolling(0, i);
    }

    private void enableFpsListener() {
        if (isScrollPerfLoggingEnabled()) {
            Assertions.assertNotNull(this.mFpsListener);
            Assertions.assertNotNull(this.mScrollPerfTag);
            this.mFpsListener.enable(this.mScrollPerfTag);
        }
    }

    private void disableFpsListener() {
        if (isScrollPerfLoggingEnabled()) {
            Assertions.assertNotNull(this.mFpsListener);
            Assertions.assertNotNull(this.mScrollPerfTag);
            this.mFpsListener.disable(this.mScrollPerfTag);
        }
    }

    private boolean isScrollPerfLoggingEnabled() {
        if (this.mFpsListener != null) {
            String str = this.mScrollPerfTag;
            if (!(str == null || str.isEmpty())) {
                return true;
            }
        }
        return false;
    }

    private int getMaxScrollY() {
        return Math.max(0, this.mContentView.getHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop()));
    }

    public void draw(Canvas canvas) {
        int i = 0;
        if (this.mEndFillColor != 0) {
            View childAt = getChildAt(0);
            if (!(this.mEndBackground == null || childAt == null || childAt.getBottom() >= getHeight())) {
                this.mEndBackground.setBounds(0, childAt.getBottom(), getWidth(), getHeight());
                this.mEndBackground.draw(canvas);
            }
        }
        getDrawingRect(this.mRect);
        String str = this.mOverflow;
        if (!(str.hashCode() == 466743410 && str.equals(ViewProps.VISIBLE))) {
            i = -1;
        }
        if (i != 0) {
            canvas.clipRect(this.mRect);
        }
        super.draw(canvas);
    }

    private void handlePostTouchScrolling(int i, int i2) {
        if ((this.mSendMomentumEvents || this.mPagingEnabled || isScrollPerfLoggingEnabled()) && this.mPostTouchRunnable == null) {
            if (this.mSendMomentumEvents) {
                enableFpsListener();
                ReactScrollViewHelper.emitScrollMomentumBeginEvent(this, i, i2);
            }
            this.mActivelyScrolling = false;
            this.mPostTouchRunnable = new Runnable() {
                private boolean mSnappingToPage = false;

                public void run() {
                    if (ReactScrollView.this.mActivelyScrolling) {
                        ReactScrollView.this.mActivelyScrolling = false;
                        ViewCompat.postOnAnimationDelayed(ReactScrollView.this, this, 20);
                    } else if (!ReactScrollView.this.mPagingEnabled || this.mSnappingToPage) {
                        if (ReactScrollView.this.mSendMomentumEvents) {
                            ReactScrollViewHelper.emitScrollMomentumEndEvent(ReactScrollView.this);
                        }
                        ReactScrollView.this.mPostTouchRunnable = null;
                        ReactScrollView.this.disableFpsListener();
                    } else {
                        this.mSnappingToPage = true;
                        ReactScrollView.this.flingAndSnap(0);
                        ViewCompat.postOnAnimationDelayed(ReactScrollView.this, this, 20);
                    }
                }
            };
            ViewCompat.postOnAnimationDelayed(this, this.mPostTouchRunnable, 20);
        }
    }

    private int predictFinalScrollPosition(int i) {
        OverScroller overScroller = new OverScroller(getContext());
        overScroller.setFriction(1.0f - this.mDecelerationRate);
        int height = ((getHeight() - getPaddingBottom()) - getPaddingTop()) / 2;
        OverScroller overScroller2 = overScroller;
        overScroller2.fling(getScrollX(), getScrollY(), 0, i, 0, 0, 0, getMaxScrollY(), 0, height);
        return overScroller.getFinalY();
    }

    private void smoothScrollAndSnap(int i) {
        double snapInterval = (double) getSnapInterval();
        double scrollY = (double) getScrollY();
        double d = scrollY / snapInterval;
        int floor = (int) Math.floor(d);
        int ceil = (int) Math.ceil(d);
        int round = (int) Math.round(d);
        int round2 = (int) Math.round(((double) predictFinalScrollPosition(i)) / snapInterval);
        if (i > 0 && ceil == floor) {
            ceil++;
        } else if (i < 0 && floor == ceil) {
            floor--;
        }
        if (i > 0 && round < ceil && round2 > floor) {
            round = ceil;
        } else if (i < 0 && round > floor && round2 < ceil) {
            round = floor;
        }
        double d2 = ((double) round) * snapInterval;
        if (d2 != scrollY) {
            this.mActivelyScrolling = true;
            smoothScrollTo(getScrollX(), (int) d2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:60:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f2  */
    private void flingAndSnap(int r20) {
        /*
        r19 = this;
        r0 = r19;
        r1 = r19.getChildCount();
        if (r1 > 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r1 = r0.mSnapInterval;
        if (r1 != 0) goto L_0x0015;
    L_0x000d:
        r1 = r0.mSnapOffsets;
        if (r1 != 0) goto L_0x0015;
    L_0x0011:
        r19.smoothScrollAndSnap(r20);
        return;
    L_0x0015:
        r1 = r19.getMaxScrollY();
        r2 = r19.predictFinalScrollPosition(r20);
        r3 = r19.getHeight();
        r4 = r19.getPaddingBottom();
        r3 = r3 - r4;
        r4 = r19.getPaddingTop();
        r3 = r3 - r4;
        r4 = r0.mSnapOffsets;
        r5 = 1;
        r6 = 0;
        if (r4 == 0) goto L_0x0080;
    L_0x0031:
        r4 = r4.get(r6);
        r4 = (java.lang.Integer) r4;
        r4 = r4.intValue();
        r7 = r0.mSnapOffsets;
        r8 = r7.size();
        r8 = r8 - r5;
        r7 = r7.get(r8);
        r7 = (java.lang.Integer) r7;
        r7 = r7.intValue();
        r10 = r1;
        r8 = 0;
        r9 = 0;
    L_0x004f:
        r11 = r0.mSnapOffsets;
        r11 = r11.size();
        if (r8 >= r11) goto L_0x0078;
    L_0x0057:
        r11 = r0.mSnapOffsets;
        r11 = r11.get(r8);
        r11 = (java.lang.Integer) r11;
        r11 = r11.intValue();
        if (r11 > r2) goto L_0x006c;
    L_0x0065:
        r12 = r2 - r11;
        r13 = r2 - r9;
        if (r12 >= r13) goto L_0x006c;
    L_0x006b:
        r9 = r11;
    L_0x006c:
        if (r11 < r2) goto L_0x0075;
    L_0x006e:
        r12 = r11 - r2;
        r13 = r10 - r2;
        if (r12 >= r13) goto L_0x0075;
    L_0x0074:
        r10 = r11;
    L_0x0075:
        r8 = r8 + 1;
        goto L_0x004f;
    L_0x0078:
        r8 = r7;
        r7 = r10;
        r18 = r9;
        r9 = r4;
        r4 = r18;
        goto L_0x009b;
    L_0x0080:
        r4 = r19.getSnapInterval();
        r7 = (double) r4;
        r9 = (double) r2;
        r9 = r9 / r7;
        r11 = java.lang.Math.floor(r9);
        r11 = r11 * r7;
        r4 = (int) r11;
        r9 = java.lang.Math.ceil(r9);
        r9 = r9 * r7;
        r7 = (int) r9;
        r7 = java.lang.Math.min(r7, r1);
        r8 = r1;
        r9 = 0;
    L_0x009b:
        r10 = r2 - r4;
        r11 = r7 - r2;
        if (r10 >= r11) goto L_0x00a3;
    L_0x00a1:
        r12 = r4;
        goto L_0x00a4;
    L_0x00a3:
        r12 = r7;
    L_0x00a4:
        r13 = r0.mSnapToEnd;
        if (r13 != 0) goto L_0x00b5;
    L_0x00a8:
        if (r2 < r8) goto L_0x00b5;
    L_0x00aa:
        r4 = r19.getScrollY();
        if (r4 < r8) goto L_0x00b1;
    L_0x00b0:
        goto L_0x00c1;
    L_0x00b1:
        r4 = r20;
        r2 = r8;
        goto L_0x00e6;
    L_0x00b5:
        r8 = r0.mSnapToStart;
        if (r8 != 0) goto L_0x00c8;
    L_0x00b9:
        if (r2 > r9) goto L_0x00c8;
    L_0x00bb:
        r4 = r19.getScrollY();
        if (r4 > r9) goto L_0x00c4;
    L_0x00c1:
        r4 = r20;
        goto L_0x00e6;
    L_0x00c4:
        r4 = r20;
        r2 = r9;
        goto L_0x00e6;
    L_0x00c8:
        r8 = 4621819117588971520; // 0x4024000000000000 float:0.0 double:10.0;
        if (r20 <= 0) goto L_0x00d5;
    L_0x00cc:
        r10 = (double) r11;
        r10 = r10 * r8;
        r2 = (int) r10;
        r2 = r20 + r2;
        r4 = r2;
        r2 = r7;
        goto L_0x00e6;
    L_0x00d5:
        if (r20 >= 0) goto L_0x00e3;
    L_0x00d7:
        r10 = (double) r10;
        r10 = r10 * r8;
        r2 = (int) r10;
        r2 = r20 - r2;
        r18 = r4;
        r4 = r2;
        r2 = r18;
        goto L_0x00e6;
    L_0x00e3:
        r4 = r20;
        r2 = r12;
    L_0x00e6:
        r2 = java.lang.Math.max(r6, r2);
        r15 = java.lang.Math.min(r2, r1);
        r7 = r0.mScroller;
        if (r7 == 0) goto L_0x011f;
    L_0x00f2:
        r0.mActivelyScrolling = r5;
        r8 = r19.getScrollX();
        r9 = r19.getScrollY();
        r10 = 0;
        if (r4 == 0) goto L_0x0100;
    L_0x00ff:
        goto L_0x0106;
    L_0x0100:
        r2 = r19.getScrollY();
        r4 = r15 - r2;
    L_0x0106:
        r11 = r4;
        r12 = 0;
        r13 = 0;
        r16 = 0;
        if (r15 == 0) goto L_0x0113;
    L_0x010d:
        if (r15 != r1) goto L_0x0110;
    L_0x010f:
        goto L_0x0113;
    L_0x0110:
        r17 = 0;
        goto L_0x0117;
    L_0x0113:
        r6 = r3 / 2;
        r17 = r6;
    L_0x0117:
        r14 = r15;
        r7.fling(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17);
        r19.postInvalidateOnAnimation();
        goto L_0x0126;
    L_0x011f:
        r1 = r19.getScrollX();
        r0.smoothScrollTo(r1, r15);
    L_0x0126:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.scroll.ReactScrollView.flingAndSnap(int):void");
    }

    private int getSnapInterval() {
        int i = this.mSnapInterval;
        if (i != 0) {
            return i;
        }
        return getHeight();
    }

    public void setEndFillColor(int i) {
        if (i != this.mEndFillColor) {
            this.mEndFillColor = i;
            this.mEndBackground = new ColorDrawable(this.mEndFillColor);
        }
    }

    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        OverScroller overScroller = this.mScroller;
        if (!(overScroller == null || this.mContentView == null || overScroller.isFinished() || this.mScroller.getCurrY() == this.mScroller.getFinalY())) {
            int maxScrollY = getMaxScrollY();
            if (i2 >= maxScrollY) {
                this.mScroller.abortAnimation();
                i2 = maxScrollY;
            }
        }
        super.onOverScrolled(i, i2, z, z2);
    }

    public void onChildViewAdded(View view, View view2) {
        this.mContentView = view2;
        this.mContentView.addOnLayoutChangeListener(this);
    }

    public void onChildViewRemoved(View view, View view2) {
        this.mContentView.removeOnLayoutChangeListener(this);
        this.mContentView = null;
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (this.mContentView != null) {
            int scrollY = getScrollY();
            i = getMaxScrollY();
            if (scrollY > i) {
                scrollTo(getScrollX(), i);
            }
        }
    }

    public void setBackgroundColor(int i) {
        this.mReactBackgroundManager.setBackgroundColor(i);
    }

    public void setBorderWidth(int i, float f) {
        this.mReactBackgroundManager.setBorderWidth(i, f);
    }

    public void setBorderColor(int i, float f, float f2) {
        this.mReactBackgroundManager.setBorderColor(i, f, f2);
    }

    public void setBorderRadius(float f) {
        this.mReactBackgroundManager.setBorderRadius(f);
    }

    public void setBorderRadius(float f, int i) {
        this.mReactBackgroundManager.setBorderRadius(f, i);
    }

    public void setBorderStyle(@Nullable String str) {
        this.mReactBackgroundManager.setBorderStyle(str);
    }
}
