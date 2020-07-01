package com.facebook.react.fabric.mounting;

import android.view.View;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ThemedReactContext;

public interface ViewFactory {
    View getOrCreateView(String str, ReactStylesDiffMap reactStylesDiffMap, ThemedReactContext themedReactContext);

    void recycle(ThemedReactContext themedReactContext, String str, View view);
}
