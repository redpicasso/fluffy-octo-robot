package com.swmansion.gesturehandler.react;

import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.ReactConstants;
import com.swmansion.gesturehandler.GestureHandler;
import com.swmansion.gesturehandler.GestureHandlerOrchestrator;

public class RNGestureHandlerRootHelper {
    private static final float MIN_ALPHA_FOR_TOUCH = 0.1f;
    private final ReactContext mContext;
    private final GestureHandler mJSGestureHandler;
    private final GestureHandlerOrchestrator mOrchestrator;
    private boolean mPassingTouch = false;
    private final ReactRootView mReactRootView;
    private boolean mShouldIntercept = false;

    private class RootViewGestureHandler extends GestureHandler {
        private RootViewGestureHandler() {
        }

        /* synthetic */ RootViewGestureHandler(RNGestureHandlerRootHelper rNGestureHandlerRootHelper, AnonymousClass1 anonymousClass1) {
            this();
        }

        protected void onHandle(MotionEvent motionEvent) {
            if (getState() == 0) {
                begin();
                RNGestureHandlerRootHelper.this.mShouldIntercept = false;
            }
            if (motionEvent.getActionMasked() == 1) {
                end();
            }
        }

        protected void onCancel() {
            RNGestureHandlerRootHelper.this.mShouldIntercept = true;
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
            obtain.setAction(3);
            RNGestureHandlerRootHelper.this.mReactRootView.onChildStartedNativeGesture(obtain);
        }
    }

    private static ReactRootView findRootViewTag(ViewGroup viewGroup) {
        UiThreadUtil.assertOnUiThread();
        ViewParent viewParent = viewGroup;
        while (viewParent != null && !(viewParent instanceof ReactRootView)) {
            viewParent = viewParent.getParent();
        }
        if (viewParent != null) {
            return (ReactRootView) viewParent;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(viewGroup);
        stringBuilder.append(" has not been mounted under ReactRootView");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public RNGestureHandlerRootHelper(ReactContext reactContext, ViewGroup viewGroup) {
        UiThreadUtil.assertOnUiThread();
        int id = viewGroup.getId();
        if (id >= 1) {
            RNGestureHandlerModule rNGestureHandlerModule = (RNGestureHandlerModule) reactContext.getNativeModule(RNGestureHandlerModule.class);
            RNGestureHandlerRegistry registry = rNGestureHandlerModule.getRegistry();
            this.mReactRootView = findRootViewTag(viewGroup);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[GESTURE HANDLER] Initialize gesture handler for root view ");
            stringBuilder.append(this.mReactRootView);
            Log.i(ReactConstants.TAG, stringBuilder.toString());
            this.mContext = reactContext;
            this.mOrchestrator = new GestureHandlerOrchestrator(viewGroup, registry, new RNViewConfigurationHelper());
            this.mOrchestrator.setMinimumAlphaForTraversal(MIN_ALPHA_FOR_TOUCH);
            this.mJSGestureHandler = new RootViewGestureHandler(this, null);
            this.mJSGestureHandler.setTag(-id);
            registry.registerHandler(this.mJSGestureHandler);
            registry.attachHandlerToView(this.mJSGestureHandler.getTag(), id);
            rNGestureHandlerModule.registerRootHelper(this);
            return;
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Expect view tag to be set for ");
        stringBuilder2.append(viewGroup);
        throw new IllegalStateException(stringBuilder2.toString());
    }

    public void tearDown() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[GESTURE HANDLER] Tearing down gesture handler registered for root view ");
        stringBuilder.append(this.mReactRootView);
        Log.i(ReactConstants.TAG, stringBuilder.toString());
        RNGestureHandlerModule rNGestureHandlerModule = (RNGestureHandlerModule) this.mContext.getNativeModule(RNGestureHandlerModule.class);
        rNGestureHandlerModule.getRegistry().dropHandler(this.mJSGestureHandler.getTag());
        rNGestureHandlerModule.unregisterRootHelper(this);
    }

    public ReactRootView getRootView() {
        return this.mReactRootView;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (this.mOrchestrator != null && !this.mPassingTouch) {
            tryCancelAllHandlers();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.mPassingTouch = true;
        this.mOrchestrator.onTouchEvent(motionEvent);
        this.mPassingTouch = false;
        if (this.mShouldIntercept) {
            return true;
        }
        return false;
    }

    private void tryCancelAllHandlers() {
        GestureHandler gestureHandler = this.mJSGestureHandler;
        if (gestureHandler != null && gestureHandler.getState() == 2) {
            this.mJSGestureHandler.activate();
            this.mJSGestureHandler.end();
        }
    }

    void handleSetJSResponder(int i, boolean z) {
        if (z) {
            UiThreadUtil.runOnUiThread(new Runnable() {
                public void run() {
                    RNGestureHandlerRootHelper.this.tryCancelAllHandlers();
                }
            });
        }
    }
}
