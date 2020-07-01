package com.facebook.react.uimanager;

import android.content.Context;
import android.view.View;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.touch.JSResponderHandler;
import com.facebook.react.touch.ReactInterceptingViewGroup;
import com.facebook.react.uimanager.annotations.ReactPropertyHolder;
import com.facebook.yoga.YogaMeasureMode;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ReactPropertyHolder
public abstract class ViewManager<T extends View, C extends ReactShadowNode> extends BaseJavaModule {
    protected void addEventEmitters(@Nonnull ThemedReactContext themedReactContext, @Nonnull T t) {
    }

    @Nonnull
    protected abstract T createViewInstance(@Nonnull ThemedReactContext themedReactContext);

    @Nullable
    public Map<String, Integer> getCommandsMap() {
        return null;
    }

    @Nullable
    public Map<String, Object> getExportedCustomBubblingEventTypeConstants() {
        return null;
    }

    @Nullable
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return null;
    }

    @Nullable
    public Map<String, Object> getExportedViewConstants() {
        return null;
    }

    @Nonnull
    public abstract String getName();

    public abstract Class<? extends C> getShadowNodeClass();

    public long measure(Context context, ReadableMap readableMap, ReadableMap readableMap2, ReadableMap readableMap3, float f, YogaMeasureMode yogaMeasureMode, float f2, YogaMeasureMode yogaMeasureMode2) {
        return 0;
    }

    protected void onAfterUpdateTransaction(@Nonnull T t) {
    }

    public void onDropViewInstance(@Nonnull T t) {
    }

    public void receiveCommand(@Nonnull T t, int i, @Nullable ReadableArray readableArray) {
    }

    public abstract void updateExtraData(@Nonnull T t, Object obj);

    @Nullable
    public Object updateLocalData(@Nonnull T t, ReactStylesDiffMap reactStylesDiffMap, ReactStylesDiffMap reactStylesDiffMap2) {
        return null;
    }

    public void updateState(@Nonnull T t, StateWrapper stateWrapper) {
    }

    public void updateProperties(@Nonnull T t, ReactStylesDiffMap reactStylesDiffMap) {
        ViewManagerPropertyUpdater.updateProps(this, t, reactStylesDiffMap);
        onAfterUpdateTransaction(t);
    }

    @Nonnull
    private final T createView(@Nonnull ThemedReactContext themedReactContext, JSResponderHandler jSResponderHandler) {
        return createViewWithProps(themedReactContext, null, jSResponderHandler);
    }

    @Nonnull
    public T createViewWithProps(@Nonnull ThemedReactContext themedReactContext, ReactStylesDiffMap reactStylesDiffMap, JSResponderHandler jSResponderHandler) {
        T createViewInstanceWithProps = createViewInstanceWithProps(themedReactContext, reactStylesDiffMap);
        addEventEmitters(themedReactContext, createViewInstanceWithProps);
        if (createViewInstanceWithProps instanceof ReactInterceptingViewGroup) {
            ((ReactInterceptingViewGroup) createViewInstanceWithProps).setOnInterceptTouchEventListener(jSResponderHandler);
        }
        return createViewInstanceWithProps;
    }

    public C createShadowNodeInstance() {
        throw new RuntimeException("ViewManager subclasses must implement createShadowNodeInstance()");
    }

    @Nonnull
    public C createShadowNodeInstance(@Nonnull ReactApplicationContext reactApplicationContext) {
        return createShadowNodeInstance();
    }

    @Nonnull
    protected T createViewInstanceWithProps(@Nonnull ThemedReactContext themedReactContext, ReactStylesDiffMap reactStylesDiffMap) {
        T createViewInstance = createViewInstance(themedReactContext);
        if (reactStylesDiffMap != null) {
            updateProperties(createViewInstance, reactStylesDiffMap);
        }
        return createViewInstance;
    }

    public Map<String, String> getNativeProps() {
        return ViewManagerPropertyUpdater.getNativeProps(getClass(), getShadowNodeClass());
    }
}
