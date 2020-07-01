package com.swmansion.rnscreens;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.swmansion.rnscreens.Screen.StackAnimation;
import com.swmansion.rnscreens.Screen.StackPresentation;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(name = "RNSScreen")
public class ScreenViewManager extends ViewGroupManager<Screen> {
    protected static final String REACT_CLASS = "RNSScreen";

    public String getName() {
        return REACT_CLASS;
    }

    protected Screen createViewInstance(ThemedReactContext themedReactContext) {
        return new Screen(themedReactContext);
    }

    @ReactProp(defaultFloat = 0.0f, name = "active")
    public void setActive(Screen screen, float f) {
        screen.setActive(f != 0.0f);
    }

    @ReactProp(name = "stackPresentation")
    public void setStackPresentation(Screen screen, String str) {
        if ("push".equals(str)) {
            screen.setStackPresentation(StackPresentation.PUSH);
        } else if ("modal".equals(str)) {
            screen.setStackPresentation(StackPresentation.MODAL);
        } else if ("transparentModal".equals(str)) {
            screen.setStackPresentation(StackPresentation.TRANSPARENT_MODAL);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown presentation type ");
            stringBuilder.append(str);
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        }
    }

    @ReactProp(name = "stackAnimation")
    public void setStackAnimation(Screen screen, String str) {
        if (str == null || "default".equals(str)) {
            screen.setStackAnimation(StackAnimation.DEFAULT);
        } else if (ViewProps.NONE.equals(str)) {
            screen.setStackAnimation(StackAnimation.NONE);
        } else if ("fade".equals(str)) {
            screen.setStackAnimation(StackAnimation.FADE);
        }
    }

    @Nullable
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(ScreenDismissedEvent.EVENT_NAME, MapBuilder.of("registrationName", "onDismissed"));
    }
}
