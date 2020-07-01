package com.swmansion.rnscreens;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewParent;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.views.view.ReactViewGroup;
import com.google.common.primitives.Ints;

public class ScreenStackHeaderSubview extends ReactViewGroup {
    private int mReactHeight;
    private int mReactWidth;
    private Type mType = Type.RIGHT;
    private final UIManagerModule mUIManager;

    public class Measurements {
        public int height;
        public int width;
    }

    public enum Type {
        LEFT,
        CENTER,
        TITLE,
        RIGHT
    }

    protected void onMeasure(int i, int i2) {
        if (MeasureSpec.getMode(i) == Ints.MAX_POWER_OF_TWO && MeasureSpec.getMode(i2) == Ints.MAX_POWER_OF_TWO) {
            this.mReactWidth = MeasureSpec.getSize(i);
            this.mReactHeight = MeasureSpec.getSize(i2);
            ViewParent parent = getParent();
            if (parent != null) {
                forceLayout();
                ((View) parent).requestLayout();
            }
        }
        setMeasuredDimension(this.mReactWidth, this.mReactHeight);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z && (this.mType == Type.CENTER || this.mType == Type.TITLE)) {
            Measurements measurements = new Measurements();
            measurements.width = i3 - i;
            if (this.mType == Type.CENTER) {
                int width = ((View) getParent()).getWidth();
                measurements.width = Math.max(0, width - (Math.max(width - i3, i) * 2));
            }
            measurements.height = i4 - i2;
            this.mUIManager.setViewLocalData(getId(), measurements);
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    public ScreenStackHeaderSubview(ReactContext reactContext) {
        super(reactContext);
        this.mUIManager = (UIManagerModule) reactContext.getNativeModule(UIManagerModule.class);
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public Type getType() {
        return this.mType;
    }
}
