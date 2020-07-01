package com.facebook.react.fabric.mounting;

import android.view.View;
import androidx.annotation.UiThread;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerRegistry;
import java.util.WeakHashMap;

public final class ContextBasedViewPool implements ViewFactory {
    private final WeakHashMap<ThemedReactContext, ViewPool> mContextViewPoolHashMap = new WeakHashMap();
    private final ViewManagerRegistry mViewManagerRegistry;

    ContextBasedViewPool(ViewManagerRegistry viewManagerRegistry) {
        this.mViewManagerRegistry = viewManagerRegistry;
    }

    @UiThread
    void createView(ThemedReactContext themedReactContext, ReactStylesDiffMap reactStylesDiffMap, String str) {
        getViewPool(themedReactContext).createView(str, reactStylesDiffMap, themedReactContext);
    }

    @UiThread
    public View getOrCreateView(String str, ReactStylesDiffMap reactStylesDiffMap, ThemedReactContext themedReactContext) {
        return getViewPool(themedReactContext).getOrCreateView(str, reactStylesDiffMap, themedReactContext);
    }

    @UiThread
    public void recycle(ThemedReactContext themedReactContext, String str, View view) {
        getViewPool(themedReactContext).returnToPool(str, view);
    }

    @UiThread
    private ViewPool getViewPool(ThemedReactContext themedReactContext) {
        ViewPool viewPool = (ViewPool) this.mContextViewPoolHashMap.get(themedReactContext);
        if (viewPool != null) {
            return viewPool;
        }
        viewPool = new ViewPool(this.mViewManagerRegistry);
        this.mContextViewPoolHashMap.put(themedReactContext, viewPool);
        return viewPool;
    }
}
