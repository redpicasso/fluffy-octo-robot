package com.swmansion.rnscreens;

import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewParent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public class LifecycleHelper {
    private OnLayoutChangeListener mRegisterOnLayoutChange = new OnLayoutChangeListener() {
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            LifecycleHelper.this.registerViewWithLifecycleOwner(view);
            view.removeOnLayoutChangeListener(this);
        }
    };
    private Map<View, Lifecycle> mViewToLifecycleMap = new HashMap();

    @Nullable
    public static Fragment findNearestScreenFragmentAncestor(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && !(parent instanceof Screen)) {
            parent = parent.getParent();
        }
        return parent != null ? ((Screen) parent).getFragment() : null;
    }

    private void registerViewWithLifecycleOwner(View view) {
        Fragment findNearestScreenFragmentAncestor = findNearestScreenFragmentAncestor(view);
        if (findNearestScreenFragmentAncestor != null && (view instanceof LifecycleObserver)) {
            Lifecycle lifecycle = findNearestScreenFragmentAncestor.getLifecycle();
            lifecycle.addObserver((LifecycleObserver) view);
            this.mViewToLifecycleMap.put(view, lifecycle);
        }
    }

    public <T extends View & LifecycleObserver> void register(T t) {
        t.addOnLayoutChangeListener(this.mRegisterOnLayoutChange);
    }

    public <T extends View & LifecycleObserver> void unregister(T t) {
        Lifecycle lifecycle = (Lifecycle) this.mViewToLifecycleMap.get(t);
        if (lifecycle != null) {
            lifecycle.removeObserver((LifecycleObserver) t);
        }
    }
}
