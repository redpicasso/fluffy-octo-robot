package com.facebook.react.views.unimplementedview;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nullable;

@ReactModule(name = "UnimplementedNativeView")
public class ReactUnimplementedViewManager extends ViewGroupManager<ReactUnimplementedView> {
    public static final String REACT_CLASS = "UnimplementedNativeView";

    public String getName() {
        return REACT_CLASS;
    }

    protected ReactUnimplementedView createViewInstance(ThemedReactContext themedReactContext) {
        return new ReactUnimplementedView(themedReactContext);
    }

    @ReactProp(name = "name")
    public void setName(ReactUnimplementedView reactUnimplementedView, @Nullable String str) {
        reactUnimplementedView.setName(str);
    }
}
