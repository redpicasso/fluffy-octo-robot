package com.facebook.react.fabric.mounting;

import android.view.View;
import androidx.annotation.UiThread;
import com.facebook.react.common.ClearableSynchronizedPool;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerRegistry;
import java.util.HashMap;
import java.util.Map;

public final class ViewPool {
    private static final int POOL_SIZE = 512;
    private final ViewManagerRegistry mViewManagerRegistry;
    private final Map<String, ClearableSynchronizedPool<View>> mViewPool = new HashMap();

    ViewPool(ViewManagerRegistry viewManagerRegistry) {
        this.mViewManagerRegistry = viewManagerRegistry;
    }

    @UiThread
    void createView(String str, ReactStylesDiffMap reactStylesDiffMap, ThemedReactContext themedReactContext) {
        getViewPoolForComponent(str).release(this.mViewManagerRegistry.get(str).createViewWithProps(themedReactContext, reactStylesDiffMap, null));
    }

    @UiThread
    View getOrCreateView(String str, ReactStylesDiffMap reactStylesDiffMap, ThemedReactContext themedReactContext) {
        ClearableSynchronizedPool viewPoolForComponent = getViewPoolForComponent(str);
        View view = (View) viewPoolForComponent.acquire();
        if (view != null) {
            return view;
        }
        createView(str, reactStylesDiffMap, themedReactContext);
        return (View) viewPoolForComponent.acquire();
    }

    @UiThread
    void returnToPool(String str, View view) {
        ClearableSynchronizedPool clearableSynchronizedPool = (ClearableSynchronizedPool) this.mViewPool.get(str);
        if (clearableSynchronizedPool != null) {
            clearableSynchronizedPool.release(view);
        }
    }

    private ClearableSynchronizedPool<View> getViewPoolForComponent(String str) {
        ClearableSynchronizedPool<View> clearableSynchronizedPool = (ClearableSynchronizedPool) this.mViewPool.get(str);
        if (clearableSynchronizedPool != null) {
            return clearableSynchronizedPool;
        }
        clearableSynchronizedPool = new ClearableSynchronizedPool(512);
        this.mViewPool.put(str, clearableSynchronizedPool);
        return clearableSynchronizedPool;
    }
}
