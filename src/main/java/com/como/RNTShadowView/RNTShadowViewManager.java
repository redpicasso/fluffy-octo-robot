package com.como.RNTShadowView;

import android.graphics.Color;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RNTShadowViewManager extends ViewGroupManager<ShadowView> {
    public static final String REACT_CLASS = "RNTShadowView";

    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(defaultDouble = 0.0d, name = "borderRadius")
    public void setBorderRadius(ShadowView shadowView, @Nullable double d) {
        if (shadowView != null) {
            shadowView.setBorderRadius(d);
        }
    }

    @ReactProp(name = "borderColor")
    public void setBorderColor(ShadowView shadowView, @Nullable String str) {
        if (shadowView != null) {
            shadowView.setBorderColor(parseColor(str));
        }
    }

    @ReactProp(name = "borderWidth")
    public void setBorderWidth(ShadowView shadowView, @Nullable double d) {
        if (shadowView != null) {
            shadowView.setBorderWidth(d);
        }
    }

    @ReactProp(name = "backgroundColor")
    public void setBackgroundColor(ShadowView shadowView, @Nullable String str) {
        if (shadowView != null) {
            shadowView.setBackgroundColor(parseColor(str));
        }
    }

    @ReactProp(name = "shadowColor")
    public void setShadowColor(ShadowView shadowView, @Nullable String str) {
        if (shadowView != null) {
            shadowView.setShadowColor(parseColor(str));
        }
    }

    @ReactProp(defaultDouble = 0.0d, name = "shadowOffsetX")
    public void setShadowOffsetX(ShadowView shadowView, @Nullable double d) {
        if (shadowView != null) {
            shadowView.setShadowOffsetX(d);
        }
    }

    @ReactProp(defaultDouble = 0.0d, name = "shadowOffsetY")
    public void setShadowOffsetY(ShadowView shadowView, @Nullable double d) {
        if (shadowView != null) {
            shadowView.setShadowOffsetY(d);
        }
    }

    @ReactProp(defaultDouble = 1.0d, name = "shadowOpacity")
    public void setShadowOpacity(ShadowView shadowView, @Nullable double d) {
        if (shadowView != null) {
            shadowView.setShadowOpacity(d);
        }
    }

    @ReactProp(name = "shadowRadius")
    public void setShadowRadius(ShadowView shadowView, double d) {
        if (shadowView != null) {
            shadowView.setShadowRadius(d);
        }
    }

    public ShadowView createViewInstance(ThemedReactContext themedReactContext) {
        return new ShadowView(themedReactContext);
    }

    private int parseColor(String str) {
        if (str == null) {
            return 0;
        }
        Matcher matcher = Pattern.compile("\\((\\d+),(\\d+),(\\d+)(,([\\d|\\.]+)|.*?)").matcher(str);
        if (matcher.find()) {
            int parseInt = Integer.parseInt(matcher.group(1));
            int parseInt2 = Integer.parseInt(matcher.group(2));
            int parseInt3 = Integer.parseInt(matcher.group(3));
            int i = 255;
            if (matcher.groupCount() == 5) {
                matcher = Pattern.compile("[\\d|\\.]+").matcher(matcher.group(4));
                if (matcher.find()) {
                    i = (int) (Double.parseDouble(matcher.group(0)) * 255.0d);
                }
            }
            return Color.argb(i, parseInt, parseInt2, parseInt3);
        }
        try {
            return Color.parseColor(str);
        } catch (Exception unused) {
            return ViewCompat.MEASURED_STATE_MASK;
        }
    }
}
