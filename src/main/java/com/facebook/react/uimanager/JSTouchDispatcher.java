package com.facebook.react.uimanager;

import android.view.MotionEvent;
import android.view.ViewGroup;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.TouchEvent;
import com.facebook.react.uimanager.events.TouchEventCoalescingKeyHelper;
import com.facebook.react.uimanager.events.TouchEventType;

public class JSTouchDispatcher {
    private boolean mChildIsHandlingNativeGesture = false;
    private long mGestureStartTime = Long.MIN_VALUE;
    private final ViewGroup mRootViewGroup;
    private final float[] mTargetCoordinates = new float[2];
    private int mTargetTag = -1;
    private final TouchEventCoalescingKeyHelper mTouchEventCoalescingKeyHelper = new TouchEventCoalescingKeyHelper();

    public JSTouchDispatcher(ViewGroup viewGroup) {
        this.mRootViewGroup = viewGroup;
    }

    public void onChildStartedNativeGesture(MotionEvent motionEvent, EventDispatcher eventDispatcher) {
        if (!this.mChildIsHandlingNativeGesture) {
            dispatchCancelEvent(motionEvent, eventDispatcher);
            this.mChildIsHandlingNativeGesture = true;
            this.mTargetTag = -1;
        }
    }

    public void handleTouchEvent(MotionEvent motionEvent, EventDispatcher eventDispatcher) {
        EventDispatcher eventDispatcher2 = eventDispatcher;
        int action = motionEvent.getAction() & 255;
        String str = ReactConstants.TAG;
        float[] fArr;
        if (action == 0) {
            if (this.mTargetTag != -1) {
                FLog.e(str, "Got DOWN touch before receiving UP or CANCEL from last gesture");
            }
            this.mChildIsHandlingNativeGesture = false;
            this.mGestureStartTime = motionEvent.getEventTime();
            this.mTargetTag = findTargetTagAndSetCoordinates(motionEvent);
            int i = this.mTargetTag;
            TouchEventType touchEventType = TouchEventType.START;
            long j = this.mGestureStartTime;
            fArr = this.mTargetCoordinates;
            eventDispatcher2.dispatchEvent(TouchEvent.obtain(i, touchEventType, motionEvent, j, fArr[0], fArr[1], this.mTouchEventCoalescingKeyHelper));
        } else if (!this.mChildIsHandlingNativeGesture) {
            int i2 = this.mTargetTag;
            int i3;
            TouchEventType touchEventType2;
            long j2;
            TouchEventType touchEventType3;
            long j3;
            if (i2 == -1) {
                FLog.e(str, "Unexpected state: received touch event but didn't get starting ACTION_DOWN for this gesture before");
            } else if (action == 1) {
                findTargetTagAndSetCoordinates(motionEvent);
                i3 = this.mTargetTag;
                touchEventType2 = TouchEventType.END;
                j2 = this.mGestureStartTime;
                fArr = this.mTargetCoordinates;
                eventDispatcher2.dispatchEvent(TouchEvent.obtain(i3, touchEventType2, motionEvent, j2, fArr[0], fArr[1], this.mTouchEventCoalescingKeyHelper));
                this.mTargetTag = -1;
                this.mGestureStartTime = Long.MIN_VALUE;
            } else if (action == 2) {
                findTargetTagAndSetCoordinates(motionEvent);
                i3 = this.mTargetTag;
                touchEventType2 = TouchEventType.MOVE;
                j2 = this.mGestureStartTime;
                fArr = this.mTargetCoordinates;
                eventDispatcher2.dispatchEvent(TouchEvent.obtain(i3, touchEventType2, motionEvent, j2, fArr[0], fArr[1], this.mTouchEventCoalescingKeyHelper));
            } else if (action == 5) {
                touchEventType3 = TouchEventType.START;
                j3 = this.mGestureStartTime;
                fArr = this.mTargetCoordinates;
                eventDispatcher2.dispatchEvent(TouchEvent.obtain(i2, touchEventType3, motionEvent, j3, fArr[0], fArr[1], this.mTouchEventCoalescingKeyHelper));
            } else if (action == 6) {
                touchEventType3 = TouchEventType.END;
                j3 = this.mGestureStartTime;
                fArr = this.mTargetCoordinates;
                eventDispatcher2.dispatchEvent(TouchEvent.obtain(i2, touchEventType3, motionEvent, j3, fArr[0], fArr[1], this.mTouchEventCoalescingKeyHelper));
            } else if (action == 3) {
                if (this.mTouchEventCoalescingKeyHelper.hasCoalescingKey(motionEvent.getDownTime())) {
                    dispatchCancelEvent(motionEvent, eventDispatcher);
                } else {
                    FLog.e(str, "Received an ACTION_CANCEL touch event for which we have no corresponding ACTION_DOWN");
                }
                this.mTargetTag = -1;
                this.mGestureStartTime = Long.MIN_VALUE;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Warning : touch event was ignored. Action=");
                stringBuilder.append(action);
                stringBuilder.append(" Target=");
                stringBuilder.append(this.mTargetTag);
                FLog.w(str, stringBuilder.toString());
            }
        }
    }

    private int findTargetTagAndSetCoordinates(MotionEvent motionEvent) {
        return TouchTargetHelper.findTargetTagAndCoordinatesForTouch(motionEvent.getX(), motionEvent.getY(), this.mRootViewGroup, this.mTargetCoordinates, null);
    }

    private void dispatchCancelEvent(MotionEvent motionEvent, EventDispatcher eventDispatcher) {
        if (this.mTargetTag == -1) {
            FLog.w(ReactConstants.TAG, "Can't cancel already finished gesture. Is a child View trying to start a gesture from an UP/CANCEL event?");
            return;
        }
        Assertions.assertCondition(this.mChildIsHandlingNativeGesture ^ true, "Expected to not have already sent a cancel for this gesture");
        eventDispatcher = (EventDispatcher) Assertions.assertNotNull(eventDispatcher);
        int i = this.mTargetTag;
        TouchEventType touchEventType = TouchEventType.CANCEL;
        long j = this.mGestureStartTime;
        float[] fArr = this.mTargetCoordinates;
        eventDispatcher.dispatchEvent(TouchEvent.obtain(i, touchEventType, motionEvent, j, fArr[0], fArr[1], this.mTouchEventCoalescingKeyHelper));
    }
}
