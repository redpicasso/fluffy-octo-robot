package com.swmansion.gesturehandler;

import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;
import android.view.View;
import java.util.Arrays;

public class GestureHandler<T extends GestureHandler> {
    public static final int DIRECTION_DOWN = 8;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_UP = 4;
    private static final int HIT_SLOP_BOTTOM_IDX = 3;
    private static final int HIT_SLOP_HEIGHT_IDX = 5;
    private static final int HIT_SLOP_LEFT_IDX = 0;
    public static final float HIT_SLOP_NONE = Float.NaN;
    private static final int HIT_SLOP_RIGHT_IDX = 2;
    private static final int HIT_SLOP_TOP_IDX = 1;
    private static final int HIT_SLOP_WIDTH_IDX = 4;
    private static int MAX_POINTERS_COUNT = 11;
    public static final int STATE_ACTIVE = 4;
    public static final int STATE_BEGAN = 2;
    public static final int STATE_CANCELLED = 3;
    public static final int STATE_END = 5;
    public static final int STATE_FAILED = 1;
    public static final int STATE_UNDETERMINED = 0;
    private static PointerCoords[] sPointerCoords;
    private static PointerProperties[] sPointerProps;
    int mActivationIndex;
    private boolean mEnabled = true;
    private float[] mHitSlop;
    private GestureHandlerInteractionController mInteractionController;
    boolean mIsActive;
    boolean mIsAwaiting;
    private float mLastEventOffsetX;
    private float mLastEventOffsetY;
    private float mLastX;
    private float mLastY;
    private OnTouchEventListener<T> mListener;
    private int mNumberOfPointers = 0;
    private GestureHandlerOrchestrator mOrchestrator;
    private boolean mShouldCancelWhenOutside;
    private int mState = 0;
    private int mTag;
    private final int[] mTrackedPointerIDs = new int[MAX_POINTERS_COUNT];
    private int mTrackedPointersCount = 0;
    private View mView;
    private boolean mWithinBounds;
    private float mX;
    private float mY;

    public static String stateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? null : "END" : "ACTIVE" : "CANCELLED" : "BEGIN" : "FAILED" : "UNDETERMINED";
    }

    protected void onCancel() {
    }

    protected void onReset() {
    }

    protected void onStateChange(int i, int i2) {
    }

    private static void initPointerProps(int i) {
        if (sPointerProps == null) {
            int i2 = MAX_POINTERS_COUNT;
            sPointerProps = new PointerProperties[i2];
            sPointerCoords = new PointerCoords[i2];
        }
        while (i > 0) {
            PointerProperties[] pointerPropertiesArr = sPointerProps;
            int i3 = i - 1;
            if (pointerPropertiesArr[i3] == null) {
                pointerPropertiesArr[i3] = new PointerProperties();
                sPointerCoords[i3] = new PointerCoords();
                i--;
            } else {
                return;
            }
        }
    }

    private static boolean hitSlopSet(float f) {
        return Float.isNaN(f) ^ 1;
    }

    void dispatchStateChange(int i, int i2) {
        OnTouchEventListener onTouchEventListener = this.mListener;
        if (onTouchEventListener != null) {
            onTouchEventListener.onStateChange(this, i, i2);
        }
    }

    void dispatchTouchEvent(MotionEvent motionEvent) {
        OnTouchEventListener onTouchEventListener = this.mListener;
        if (onTouchEventListener != null) {
            onTouchEventListener.onTouchEvent(this, motionEvent);
        }
    }

    public boolean hasCommonPointers(GestureHandler gestureHandler) {
        int i = 0;
        while (true) {
            int[] iArr = this.mTrackedPointerIDs;
            if (i >= iArr.length) {
                return false;
            }
            if (iArr[i] != -1 && gestureHandler.mTrackedPointerIDs[i] != -1) {
                return true;
            }
            i++;
        }
    }

    public T setShouldCancelWhenOutside(boolean z) {
        this.mShouldCancelWhenOutside = z;
        return this;
    }

    public T setEnabled(boolean z) {
        if (this.mView != null) {
            cancel();
        }
        this.mEnabled = z;
        return this;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public T setHitSlop(float f, float f2, float f3, float f4, float f5, float f6) {
        if (this.mHitSlop == null) {
            this.mHitSlop = new float[6];
        }
        float[] fArr = this.mHitSlop;
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        fArr[3] = f4;
        fArr[4] = f5;
        fArr[5] = f6;
        if (hitSlopSet(f5) && hitSlopSet(f) && hitSlopSet(f3)) {
            throw new IllegalArgumentException("Cannot have all of left, right and width defined");
        } else if (hitSlopSet(f5) && !hitSlopSet(f) && !hitSlopSet(f3)) {
            throw new IllegalArgumentException("When width is set one of left or right pads need to be defined");
        } else if (hitSlopSet(f6) && hitSlopSet(f4) && hitSlopSet(f2)) {
            throw new IllegalArgumentException("Cannot have all of top, bottom and height defined");
        } else if (!hitSlopSet(f6) || hitSlopSet(f4) || hitSlopSet(f2)) {
            return this;
        } else {
            throw new IllegalArgumentException("When height is set one of top or bottom pads need to be defined");
        }
    }

    public T setHitSlop(float f) {
        return setHitSlop(f, f, f, f, Float.NaN, Float.NaN);
    }

    public T setInteractionController(GestureHandlerInteractionController gestureHandlerInteractionController) {
        this.mInteractionController = gestureHandlerInteractionController;
        return this;
    }

    public void setTag(int i) {
        this.mTag = i;
    }

    public int getTag() {
        return this.mTag;
    }

    public View getView() {
        return this.mView;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public int getNumberOfPointers() {
        return this.mNumberOfPointers;
    }

    public boolean isWithinBounds() {
        return this.mWithinBounds;
    }

    public final void prepare(View view, GestureHandlerOrchestrator gestureHandlerOrchestrator) {
        if (this.mView == null && this.mOrchestrator == null) {
            Arrays.fill(this.mTrackedPointerIDs, -1);
            this.mTrackedPointersCount = 0;
            this.mState = 0;
            this.mView = view;
            this.mOrchestrator = gestureHandlerOrchestrator;
            return;
        }
        throw new IllegalStateException("Already prepared or hasn't been reset");
    }

    private int findNextLocalPointerId() {
        int i = 0;
        while (i < this.mTrackedPointersCount) {
            int i2 = 0;
            while (true) {
                int[] iArr = this.mTrackedPointerIDs;
                if (i2 < iArr.length && iArr[i2] != i) {
                    i2++;
                }
            }
            if (i2 == this.mTrackedPointerIDs.length) {
                return i;
            }
            i++;
        }
        return i;
    }

    public void startTrackingPointer(int i) {
        int[] iArr = this.mTrackedPointerIDs;
        if (iArr[i] == -1) {
            iArr[i] = findNextLocalPointerId();
            this.mTrackedPointersCount++;
        }
    }

    public void stopTrackingPointer(int i) {
        int[] iArr = this.mTrackedPointerIDs;
        if (iArr[i] != -1) {
            iArr[i] = -1;
            this.mTrackedPointersCount--;
        }
    }

    private boolean needAdapt(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != this.mTrackedPointersCount) {
            return true;
        }
        int i = 0;
        while (true) {
            int[] iArr = this.mTrackedPointerIDs;
            if (i >= iArr.length) {
                return false;
            }
            if (iArr[i] != -1 && iArr[i] != i) {
                return true;
            }
            i++;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006b  */
    private android.view.MotionEvent adaptEvent(android.view.MotionEvent r26) {
        /*
        r25 = this;
        r0 = r25;
        r1 = r26;
        r2 = r25.needAdapt(r26);
        if (r2 != 0) goto L_0x000b;
    L_0x000a:
        return r1;
    L_0x000b:
        r2 = r26.getActionMasked();
        r3 = 2;
        r4 = 5;
        r5 = 0;
        r6 = -1;
        r7 = 1;
        if (r2 == 0) goto L_0x0037;
    L_0x0016:
        if (r2 != r4) goto L_0x0019;
    L_0x0018:
        goto L_0x0037;
    L_0x0019:
        r4 = 6;
        if (r2 == r7) goto L_0x0022;
    L_0x001c:
        if (r2 != r4) goto L_0x001f;
    L_0x001e:
        goto L_0x0022;
    L_0x001f:
        r3 = r2;
        r2 = -1;
        goto L_0x004b;
    L_0x0022:
        r2 = r26.getActionIndex();
        r8 = r1.getPointerId(r2);
        r9 = r0.mTrackedPointerIDs;
        r8 = r9[r8];
        if (r8 == r6) goto L_0x004b;
    L_0x0030:
        r3 = r0.mTrackedPointersCount;
        if (r3 != r7) goto L_0x0035;
    L_0x0034:
        r4 = 1;
    L_0x0035:
        r3 = r4;
        goto L_0x004b;
    L_0x0037:
        r2 = r26.getActionIndex();
        r8 = r1.getPointerId(r2);
        r9 = r0.mTrackedPointerIDs;
        r8 = r9[r8];
        if (r8 == r6) goto L_0x004b;
    L_0x0045:
        r3 = r0.mTrackedPointersCount;
        if (r3 != r7) goto L_0x0035;
    L_0x0049:
        r4 = 0;
        goto L_0x0035;
    L_0x004b:
        r4 = r0.mTrackedPointersCount;
        initPointerProps(r4);
        r4 = r26.getX();
        r7 = r26.getY();
        r8 = r26.getRawX();
        r9 = r26.getRawY();
        r1.setLocation(r8, r9);
        r8 = r26.getPointerCount();
        r13 = r3;
        r14 = 0;
    L_0x0069:
        if (r5 >= r8) goto L_0x0097;
    L_0x006b:
        r3 = r1.getPointerId(r5);
        r9 = r0.mTrackedPointerIDs;
        r9 = r9[r3];
        if (r9 == r6) goto L_0x0094;
    L_0x0075:
        r9 = sPointerProps;
        r9 = r9[r14];
        r1.getPointerProperties(r5, r9);
        r9 = sPointerProps;
        r9 = r9[r14];
        r10 = r0.mTrackedPointerIDs;
        r3 = r10[r3];
        r9.id = r3;
        r3 = sPointerCoords;
        r3 = r3[r14];
        r1.getPointerCoords(r5, r3);
        if (r5 != r2) goto L_0x0092;
    L_0x008f:
        r3 = r14 << 8;
        r13 = r13 | r3;
    L_0x0092:
        r14 = r14 + 1;
    L_0x0094:
        r5 = r5 + 1;
        goto L_0x0069;
    L_0x0097:
        r9 = r26.getDownTime();
        r11 = r26.getEventTime();
        r15 = sPointerProps;
        r16 = sPointerCoords;
        r17 = r26.getMetaState();
        r18 = r26.getButtonState();
        r19 = r26.getXPrecision();
        r20 = r26.getYPrecision();
        r21 = r26.getDeviceId();
        r22 = r26.getEdgeFlags();
        r23 = r26.getSource();
        r24 = r26.getFlags();
        r2 = android.view.MotionEvent.obtain(r9, r11, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24);
        r1.setLocation(r4, r7);
        r2.setLocation(r4, r7);
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.swmansion.gesturehandler.GestureHandler.adaptEvent(android.view.MotionEvent):android.view.MotionEvent");
    }

    public final void handle(MotionEvent motionEvent) {
        if (this.mEnabled) {
            int i = this.mState;
            if (!(i == 3 || i == 1 || i == 5 || this.mTrackedPointersCount < 1)) {
                MotionEvent adaptEvent = adaptEvent(motionEvent);
                this.mX = adaptEvent.getX();
                this.mY = adaptEvent.getY();
                this.mNumberOfPointers = adaptEvent.getPointerCount();
                this.mWithinBounds = isWithinBounds(this.mView, this.mX, this.mY);
                if (!this.mShouldCancelWhenOutside || this.mWithinBounds) {
                    this.mLastX = GestureUtils.getLastPointerX(adaptEvent, true);
                    this.mLastY = GestureUtils.getLastPointerY(adaptEvent, true);
                    this.mLastEventOffsetX = adaptEvent.getRawX() - adaptEvent.getX();
                    this.mLastEventOffsetY = adaptEvent.getRawY() - adaptEvent.getY();
                    onHandle(adaptEvent);
                    if (adaptEvent != motionEvent) {
                        adaptEvent.recycle();
                    }
                } else {
                    int i2 = this.mState;
                    if (i2 == 4) {
                        cancel();
                    } else if (i2 == 2) {
                        fail();
                    }
                }
            }
        }
    }

    private void moveToState(int i) {
        int i2 = this.mState;
        if (i2 != i) {
            this.mState = i;
            this.mOrchestrator.onHandlerStateChange(this, i, i2);
            onStateChange(i, i2);
        }
    }

    public boolean wantEvents() {
        if (this.mEnabled) {
            int i = this.mState;
            if (!(i == 1 || i == 3 || i == 5 || this.mTrackedPointersCount <= 0)) {
                return true;
            }
        }
        return false;
    }

    public int getState() {
        return this.mState;
    }

    public boolean shouldRequireToWaitForFailure(GestureHandler gestureHandler) {
        if (gestureHandler != this) {
            GestureHandlerInteractionController gestureHandlerInteractionController = this.mInteractionController;
            if (gestureHandlerInteractionController != null) {
                return gestureHandlerInteractionController.shouldRequireHandlerToWaitForFailure(this, gestureHandler);
            }
        }
        return false;
    }

    public boolean shouldWaitForHandlerFailure(GestureHandler gestureHandler) {
        if (gestureHandler != this) {
            GestureHandlerInteractionController gestureHandlerInteractionController = this.mInteractionController;
            if (gestureHandlerInteractionController != null) {
                return gestureHandlerInteractionController.shouldWaitForHandlerFailure(this, gestureHandler);
            }
        }
        return false;
    }

    public boolean shouldRecognizeSimultaneously(GestureHandler gestureHandler) {
        if (gestureHandler == this) {
            return true;
        }
        GestureHandlerInteractionController gestureHandlerInteractionController = this.mInteractionController;
        return gestureHandlerInteractionController != null ? gestureHandlerInteractionController.shouldRecognizeSimultaneously(this, gestureHandler) : false;
    }

    public boolean shouldBeCancelledBy(GestureHandler gestureHandler) {
        if (gestureHandler == this) {
            return false;
        }
        GestureHandlerInteractionController gestureHandlerInteractionController = this.mInteractionController;
        if (gestureHandlerInteractionController != null) {
            return gestureHandlerInteractionController.shouldHandlerBeCancelledBy(this, gestureHandler);
        }
        return false;
    }

    public boolean isWithinBounds(View view, float f, float f2) {
        float f3;
        float width = (float) view.getWidth();
        float height = (float) view.getHeight();
        float[] fArr = this.mHitSlop;
        float f4 = 0.0f;
        if (fArr != null) {
            float f5 = fArr[0];
            float f6 = fArr[1];
            float f7 = fArr[2];
            float f8 = fArr[3];
            f3 = hitSlopSet(f5) ? 0.0f - f5 : 0.0f;
            if (hitSlopSet(f6)) {
                f4 = 0.0f - f8;
            }
            if (hitSlopSet(f7)) {
                width += f7;
            }
            if (hitSlopSet(f8)) {
                height += f8;
            }
            fArr = this.mHitSlop;
            f6 = fArr[4];
            f8 = fArr[5];
            if (hitSlopSet(f6)) {
                if (!hitSlopSet(f5)) {
                    f3 = width - f6;
                } else if (!hitSlopSet(f7)) {
                    width = f6 + f3;
                }
            }
            if (hitSlopSet(f8)) {
                if (!hitSlopSet(f4)) {
                    f4 = height - f8;
                } else if (!hitSlopSet(height)) {
                    height = f4 + f8;
                }
            }
        } else {
            f3 = 0.0f;
        }
        if (f < f3 || f > width || f2 < f4 || f2 > height) {
            return false;
        }
        return true;
    }

    public final void cancel() {
        int i = this.mState;
        if (i == 4 || i == 0 || i == 2) {
            onCancel();
            moveToState(3);
        }
    }

    public final void fail() {
        int i = this.mState;
        if (i == 4 || i == 0 || i == 2) {
            moveToState(1);
        }
    }

    public final void activate() {
        int i = this.mState;
        if (i == 0 || i == 2) {
            moveToState(4);
        }
    }

    public final void begin() {
        if (this.mState == 0) {
            moveToState(2);
        }
    }

    public final void end() {
        int i = this.mState;
        if (i == 2 || i == 4) {
            moveToState(5);
        }
    }

    protected void onHandle(MotionEvent motionEvent) {
        moveToState(1);
    }

    public final void reset() {
        this.mView = null;
        this.mOrchestrator = null;
        Arrays.fill(this.mTrackedPointerIDs, -1);
        this.mTrackedPointersCount = 0;
        onReset();
    }

    public GestureHandler setOnTouchEventListener(OnTouchEventListener<T> onTouchEventListener) {
        this.mListener = onTouchEventListener;
        return this;
    }

    public String toString() {
        View view = this.mView;
        String simpleName = view == null ? null : view.getClass().getSimpleName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append("@[");
        stringBuilder.append(this.mTag);
        stringBuilder.append("]:");
        stringBuilder.append(simpleName);
        return stringBuilder.toString();
    }

    public float getLastAbsolutePositionX() {
        return this.mLastX;
    }

    public float getLastAbsolutePositionY() {
        return this.mLastY;
    }

    public float getLastRelativePositionX() {
        return this.mLastX - this.mLastEventOffsetX;
    }

    public float getLastRelativePositionY() {
        return this.mLastY - this.mLastEventOffsetY;
    }
}
