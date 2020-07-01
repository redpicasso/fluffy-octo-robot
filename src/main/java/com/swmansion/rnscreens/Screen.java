package com.swmansion.rnscreens;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ReactPointerEventsView;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;

public class Screen extends ViewGroup implements ReactPointerEventsView {
    private static OnAttachStateChangeListener sShowSoftKeyboardOnAttach = new OnAttachStateChangeListener() {
        public void onViewDetachedFromWindow(View view) {
        }

        public void onViewAttachedToWindow(View view) {
            ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 0);
            view.removeOnAttachStateChangeListener(Screen.sShowSoftKeyboardOnAttach);
        }
    };
    private boolean mActive;
    @Nullable
    private ScreenContainer mContainer;
    private final EventDispatcher mEventDispatcher;
    private final Fragment mFragment = new ScreenFragment(this);
    private StackAnimation mStackAnimation = StackAnimation.DEFAULT;
    private StackPresentation mStackPresentation = StackPresentation.PUSH;
    private boolean mTransitioning;

    public enum StackAnimation {
        DEFAULT,
        NONE,
        FADE
    }

    public enum StackPresentation {
        PUSH,
        MODAL,
        TRANSPARENT_MODAL
    }

    public static class ScreenFragment extends Fragment {
        private Screen mScreenView;

        public ScreenFragment() {
            throw new IllegalStateException("Screen fragments should never be restored");
        }

        @SuppressLint({"ValidFragment"})
        public ScreenFragment(Screen screen) {
            this.mScreenView = screen;
        }

        public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
            return this.mScreenView;
        }

        public void onDestroy() {
            super.onDestroy();
            this.mScreenView.mEventDispatcher.dispatchEvent(new ScreenDismissedEvent(this.mScreenView.getId()));
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    public void setLayerType(int i, @Nullable Paint paint) {
    }

    public Screen(ReactContext reactContext) {
        super(reactContext);
        this.mEventDispatcher = ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearDisappearingChildren();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        View focusedChild = getFocusedChild();
        if (focusedChild != null) {
            while (focusedChild instanceof ViewGroup) {
                focusedChild = ((ViewGroup) focusedChild).getFocusedChild();
            }
            if (focusedChild instanceof TextView) {
                TextView textView = (TextView) focusedChild;
                if (textView.getShowSoftInputOnFocus()) {
                    textView.addOnAttachStateChangeListener(sShowSoftKeyboardOnAttach);
                }
            }
        }
    }

    public void setTransitioning(boolean z) {
        if (this.mTransitioning != z) {
            this.mTransitioning = z;
            super.setLayerType(z ? 2 : 0, null);
        }
    }

    public void setStackPresentation(StackPresentation stackPresentation) {
        this.mStackPresentation = stackPresentation;
    }

    public void setStackAnimation(StackAnimation stackAnimation) {
        this.mStackAnimation = stackAnimation;
    }

    public StackAnimation getStackAnimation() {
        return this.mStackAnimation;
    }

    public StackPresentation getStackPresentation() {
        return this.mStackPresentation;
    }

    public PointerEvents getPointerEvents() {
        return this.mTransitioning ? PointerEvents.NONE : PointerEvents.AUTO;
    }

    protected void setContainer(@Nullable ScreenContainer screenContainer) {
        this.mContainer = screenContainer;
    }

    @Nullable
    protected ScreenContainer getContainer() {
        return this.mContainer;
    }

    protected Fragment getFragment() {
        return this.mFragment;
    }

    public void setActive(boolean z) {
        if (z != this.mActive) {
            this.mActive = z;
            ScreenContainer screenContainer = this.mContainer;
            if (screenContainer != null) {
                screenContainer.notifyChildUpdate();
            }
        }
    }

    public boolean isActive() {
        return this.mActive;
    }
}
