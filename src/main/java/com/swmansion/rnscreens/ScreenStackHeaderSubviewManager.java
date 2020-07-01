package com.swmansion.rnscreens;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactViewGroup;
import com.facebook.react.views.view.ReactViewManager;
import com.swmansion.rnscreens.ScreenStackHeaderSubview.Measurements;
import com.swmansion.rnscreens.ScreenStackHeaderSubview.Type;

@ReactModule(name = "RNSScreenStackHeaderSubview")
public class ScreenStackHeaderSubviewManager extends ReactViewManager {
    protected static final String REACT_CLASS = "RNSScreenStackHeaderSubview";

    private static class SubviewShadowNode extends LayoutShadowNode {
        private SubviewShadowNode() {
        }

        public void setLocalData(Object obj) {
            Measurements measurements = (Measurements) obj;
            setStyleWidth((float) measurements.width);
            setStyleHeight((float) measurements.height);
        }
    }

    public String getName() {
        return REACT_CLASS;
    }

    public ReactViewGroup createViewInstance(ThemedReactContext themedReactContext) {
        return new ScreenStackHeaderSubview(themedReactContext);
    }

    public LayoutShadowNode createShadowNodeInstance(ReactApplicationContext reactApplicationContext) {
        return new SubviewShadowNode();
    }

    @ReactProp(name = "type")
    public void setType(ScreenStackHeaderSubview screenStackHeaderSubview, String str) {
        if (ViewProps.LEFT.equals(str)) {
            screenStackHeaderSubview.setType(Type.LEFT);
        } else if ("center".equals(str)) {
            screenStackHeaderSubview.setType(Type.CENTER);
        } else if ("title".equals(str)) {
            screenStackHeaderSubview.setType(Type.TITLE);
        } else if (ViewProps.RIGHT.equals(str)) {
            screenStackHeaderSubview.setType(Type.RIGHT);
        }
    }
}
