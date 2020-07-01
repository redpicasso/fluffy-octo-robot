package com.facebook.react.views.scroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.HorizontalScrollView;
import android.widget.OverScroller;
import androidx.core.view.ViewCompat;
import com.facebook.infer.annotation.Assertions;
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

public class ReactHorizontalScrollView extends HorizontalScrollView implements ReactClippingViewGroup {
    @Nullable
    private static Field sScrollerField = null;
    private static boolean sTriedToGetScrollerField = false;
    private boolean mActivelyScrolling;
    @Nullable
    private Rect mClippingRect;
    private float mDecelerationRate;
    private boolean mDisableIntervalMomentum;
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

    public ReactHorizontalScrollView(Context context) {
        this(context, null);
    }

    public ReactHorizontalScrollView(Context context, @Nullable FpsListener fpsListener) {
        super(context);
        this.mOnScrollDispatchHelper = new OnScrollDispatchHelper();
        this.mVelocityHelper = new VelocityHelper();
        this.mRect = new Rect();
        this.mOverflow = ViewProps.HIDDEN;
        this.mPagingEnabled = false;
        this.mScrollEnabled = true;
        this.mFpsListener = null;
        this.mEndFillColor = 0;
        this.mDisableIntervalMomentum = false;
        this.mSnapInterval = 0;
        this.mDecelerationRate = 0.985f;
        this.mSnapToStart = true;
        this.mSnapToEnd = true;
        this.mReactBackgroundManager = new ReactViewBackgroundManager(this);
        this.mFpsListener = fpsListener;
        this.mScroller = getOverScrollerFromParent();
    }

    @Nullable
    private OverScroller getOverScrollerFromParent() {
        boolean z = sTriedToGetScrollerField;
        String str = ReactConstants.TAG;
        if (!z) {
            sTriedToGetScrollerField = true;
            try {
                sScrollerField = HorizontalScrollView.class.getDeclaredField("mScroller");
                sScrollerField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
                Log.w(str, "Failed to get mScroller field for HorizontalScrollView! This app will exhibit the bounce-back scrolling bug :(");
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
            Log.w(str, "Failed to cast mScroller field in HorizontalScrollView (probably due to OEM changes to AOSP)! This app will exhibit the bounce-back scrolling bug :(");
            return null;
        } catch (Throwable e) {
            throw new RuntimeException("Failed to get mScroller from HorizontalScrollView!", e);
        }
    }

    public void setScrollPerfTag(@Nullable String str) {
        this.mScrollPerfTag = str;
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

    public void setDisableIntervalMomentum(boolean z) {
        this.mDisableIntervalMomentum = z;
    }

    public void setSendMomentumEvents(boolean z) {
        this.mSendMomentumEvents = z;
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

    protected void onDraw(Canvas canvas) {
        getDrawingRect(this.mRect);
        String str = this.mOverflow;
        Object obj = (str.hashCode() == 466743410 && str.equals(ViewProps.VISIBLE)) ? null : -1;
        if (obj != null) {
            canvas.clipRect(this.mRect);
        }
        super.onDraw(canvas);
    }

    protected void onMeasure(int i, int i2) {
        MeasureSpecAssertions.assertExplicitMeasureSpec(i, i2);
        setMeasuredDimension(MeasureSpec.getSize(i), MeasureSpec.getSize(i2));
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        scrollTo(getScrollX(), getScrollY());
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

    public void fling(int i) {
        i = (int) (((float) Math.abs(i)) * Math.signum(this.mOnScrollDispatchHelper.getXFlingVelocity()));
        if (this.mPagingEnabled) {
            flingAndSnap(i);
        } else if (this.mScroller != null) {
            int width = (getWidth() - ViewCompat.getPaddingStart(this)) - ViewCompat.getPaddingEnd(this);
            this.mScroller.fling(getScrollX(), getScrollY(), i, 0, 0, Integer.MAX_VALUE, 0, 0, width / 2, 0);
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            super.fling(i);
        }
        handlePostTouchScrolling(i, 0);
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

    private int getSnapInterval() {
        int i = this.mSnapInterval;
        if (i != 0) {
            return i;
        }
        return getWidth();
    }

    public void setEndFillColor(int i) {
        if (i != this.mEndFillColor) {
            this.mEndFillColor = i;
            this.mEndBackground = new ColorDrawable(this.mEndFillColor);
        }
    }

    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        OverScroller overScroller = this.mScroller;
        if (!(overScroller == null || overScroller.isFinished() || this.mScroller.getCurrX() == this.mScroller.getFinalX())) {
            int computeHorizontalScrollRange = computeHorizontalScrollRange() - getWidth();
            if (i >= computeHorizontalScrollRange) {
                this.mScroller.abortAnimation();
                i = computeHorizontalScrollRange;
            }
        }
        super.onOverScrolled(i, i2, z, z2);
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

    public void draw(Canvas canvas) {
        if (this.mEndFillColor != 0) {
            View childAt = getChildAt(0);
            if (!(this.mEndBackground == null || childAt == null || childAt.getRight() >= getWidth())) {
                this.mEndBackground.setBounds(childAt.getRight(), 0, getWidth(), getHeight());
                this.mEndBackground.draw(canvas);
            }
        }
        super.draw(canvas);
    }

    private void handlePostTouchScrolling(int i, int i2) {
        if ((this.mSendMomentumEvents || this.mPagingEnabled || isScrollPerfLoggingEnabled()) && this.mPostTouchRunnable == null) {
            if (this.mSendMomentumEvents) {
                ReactScrollViewHelper.emitScrollMomentumBeginEvent(this, i, i2);
            }
            this.mActivelyScrolling = false;
            this.mPostTouchRunnable = new Runnable() {
                private boolean mSnappingToPage = false;

                public void run() {
                    if (ReactHorizontalScrollView.this.mActivelyScrolling) {
                        ReactHorizontalScrollView.this.mActivelyScrolling = false;
                        ViewCompat.postOnAnimationDelayed(ReactHorizontalScrollView.this, this, 20);
                    } else if (!ReactHorizontalScrollView.this.mPagingEnabled || this.mSnappingToPage) {
                        if (ReactHorizontalScrollView.this.mSendMomentumEvents) {
                            ReactScrollViewHelper.emitScrollMomentumEndEvent(ReactHorizontalScrollView.this);
                        }
                        ReactHorizontalScrollView.this.mPostTouchRunnable = null;
                        ReactHorizontalScrollView.this.disableFpsListener();
                    } else {
                        this.mSnappingToPage = true;
                        ReactHorizontalScrollView.this.flingAndSnap(0);
                        ViewCompat.postOnAnimationDelayed(ReactHorizontalScrollView.this, this, 20);
                    }
                }
            };
            ViewCompat.postOnAnimationDelayed(this, this.mPostTouchRunnable, 20);
        }
    }

    private int predictFinalScrollPosition(int i) {
        OverScroller overScroller = new OverScroller(getContext());
        overScroller.setFriction(1.0f - this.mDecelerationRate);
        int width = ((getWidth() - ViewCompat.getPaddingStart(this)) - ViewCompat.getPaddingEnd(this)) / 2;
        OverScroller overScroller2 = overScroller;
        overScroller2.fling(getScrollX(), getScrollY(), i, 0, 0, Math.max(0, computeHorizontalScrollRange() - getWidth()), 0, 0, width, 0);
        return overScroller.getFinalX();
    }

    private void smoothScrollAndSnap(int i) {
        double snapInterval = (double) getSnapInterval();
        double scrollX = (double) getScrollX();
        double d = scrollX / snapInterval;
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
        if (d2 != scrollX) {
            this.mActivelyScrolling = true;
            smoothScrollTo((int) d2, getScrollY());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x010b  */
    private void flingAndSnap(int r28) {
        /*
        r27 = this;
        r0 = r27;
        r1 = r27.getChildCount();
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
        r27.smoothScrollAndSnap(r28);
        return;
    L_0x0015:
        r1 = r27.computeHorizontalScrollRange();
        r2 = r27.getWidth();
        r1 = r1 - r2;
        r2 = 0;
        r1 = java.lang.Math.max(r2, r1);
        r3 = r27.predictFinalScrollPosition(r28);
        r4 = r0.mDisableIntervalMomentum;
        if (r4 == 0) goto L_0x002f;
    L_0x002b:
        r3 = r27.getScrollX();
    L_0x002f:
        r4 = r27.getWidth();
        r5 = androidx.core.view.ViewCompat.getPaddingStart(r27);
        r4 = r4 - r5;
        r5 = androidx.core.view.ViewCompat.getPaddingEnd(r27);
        r4 = r4 - r5;
        r5 = java.util.Locale.getDefault();
        r5 = androidx.core.text.TextUtilsCompat.getLayoutDirectionFromLocale(r5);
        r6 = 1;
        if (r5 != r6) goto L_0x004a;
    L_0x0048:
        r5 = 1;
        goto L_0x004b;
    L_0x004a:
        r5 = 0;
    L_0x004b:
        if (r5 == 0) goto L_0x0053;
    L_0x004d:
        r3 = r1 - r3;
        r7 = r28;
        r7 = -r7;
        goto L_0x0055;
    L_0x0053:
        r7 = r28;
    L_0x0055:
        r8 = r0.mSnapOffsets;
        if (r8 == 0) goto L_0x00a3;
    L_0x0059:
        r8 = r8.get(r2);
        r8 = (java.lang.Integer) r8;
        r8 = r8.intValue();
        r9 = r0.mSnapOffsets;
        r10 = r9.size();
        r10 = r10 - r6;
        r9 = r9.get(r10);
        r9 = (java.lang.Integer) r9;
        r9 = r9.intValue();
        r12 = r1;
        r10 = 0;
        r11 = 0;
    L_0x0077:
        r13 = r0.mSnapOffsets;
        r13 = r13.size();
        if (r10 >= r13) goto L_0x00a0;
    L_0x007f:
        r13 = r0.mSnapOffsets;
        r13 = r13.get(r10);
        r13 = (java.lang.Integer) r13;
        r13 = r13.intValue();
        if (r13 > r3) goto L_0x0094;
    L_0x008d:
        r14 = r3 - r13;
        r15 = r3 - r11;
        if (r14 >= r15) goto L_0x0094;
    L_0x0093:
        r11 = r13;
    L_0x0094:
        if (r13 < r3) goto L_0x009d;
    L_0x0096:
        r14 = r13 - r3;
        r15 = r12 - r3;
        if (r14 >= r15) goto L_0x009d;
    L_0x009c:
        r12 = r13;
    L_0x009d:
        r10 = r10 + 1;
        goto L_0x0077;
    L_0x00a0:
        r10 = r8;
        r8 = r12;
        goto L_0x00bf;
    L_0x00a3:
        r8 = r27.getSnapInterval();
        r8 = (double) r8;
        r10 = (double) r3;
        r10 = r10 / r8;
        r12 = java.lang.Math.floor(r10);
        r12 = r12 * r8;
        r12 = (int) r12;
        r10 = java.lang.Math.ceil(r10);
        r10 = r10 * r8;
        r8 = (int) r10;
        r8 = java.lang.Math.min(r8, r1);
        r9 = r1;
        r11 = r12;
        r10 = 0;
    L_0x00bf:
        r12 = r3 - r11;
        r13 = r8 - r3;
        if (r12 >= r13) goto L_0x00c7;
    L_0x00c5:
        r14 = r11;
        goto L_0x00c8;
    L_0x00c7:
        r14 = r8;
    L_0x00c8:
        r15 = r27.getScrollX();
        if (r5 == 0) goto L_0x00d0;
    L_0x00ce:
        r15 = r1 - r15;
    L_0x00d0:
        r6 = r0.mSnapToEnd;
        if (r6 != 0) goto L_0x00db;
    L_0x00d4:
        if (r3 < r9) goto L_0x00db;
    L_0x00d6:
        if (r15 < r9) goto L_0x00d9;
    L_0x00d8:
        goto L_0x00e3;
    L_0x00d9:
        r14 = r9;
        goto L_0x00fa;
    L_0x00db:
        r6 = r0.mSnapToStart;
        if (r6 != 0) goto L_0x00e7;
    L_0x00df:
        if (r3 > r10) goto L_0x00e7;
    L_0x00e1:
        if (r15 > r10) goto L_0x00e5;
    L_0x00e3:
        r14 = r3;
        goto L_0x00fa;
    L_0x00e5:
        r14 = r10;
        goto L_0x00fa;
    L_0x00e7:
        r9 = 4621819117588971520; // 0x4024000000000000 float:0.0 double:10.0;
        if (r7 <= 0) goto L_0x00f2;
    L_0x00eb:
        r11 = (double) r13;
        r11 = r11 * r9;
        r3 = (int) r11;
        r7 = r7 + r3;
        r14 = r8;
        goto L_0x00fa;
    L_0x00f2:
        if (r7 >= 0) goto L_0x00fa;
    L_0x00f4:
        r12 = (double) r12;
        r12 = r12 * r9;
        r3 = (int) r12;
        r7 = r7 - r3;
        r14 = r11;
    L_0x00fa:
        r3 = java.lang.Math.max(r2, r14);
        r3 = java.lang.Math.min(r3, r1);
        if (r5 == 0) goto L_0x0107;
    L_0x0104:
        r3 = r1 - r3;
        r7 = -r7;
    L_0x0107:
        r5 = r0.mScroller;
        if (r5 == 0) goto L_0x0144;
    L_0x010b:
        r6 = 1;
        r0.mActivelyScrolling = r6;
        r17 = r27.getScrollX();
        r18 = r27.getScrollY();
        if (r7 == 0) goto L_0x011b;
    L_0x0118:
        r19 = r7;
        goto L_0x0123;
    L_0x011b:
        r6 = r27.getScrollX();
        r6 = r3 - r6;
        r19 = r6;
    L_0x0123:
        r20 = 0;
        r23 = 0;
        r24 = 0;
        if (r3 == 0) goto L_0x0131;
    L_0x012b:
        if (r3 != r1) goto L_0x012e;
    L_0x012d:
        goto L_0x0131;
    L_0x012e:
        r25 = 0;
        goto L_0x0135;
    L_0x0131:
        r2 = r4 / 2;
        r25 = r2;
    L_0x0135:
        r26 = 0;
        r16 = r5;
        r21 = r3;
        r22 = r3;
        r16.fling(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26);
        r27.postInvalidateOnAnimation();
        goto L_0x014b;
    L_0x0144:
        r1 = r27.getScrollY();
        r0.smoothScrollTo(r3, r1);
    L_0x014b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.scroll.ReactHorizontalScrollView.flingAndSnap(int):void");
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
