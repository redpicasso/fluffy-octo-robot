package com.facebook.react.fabric.mounting;

import android.view.View.MeasureSpec;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.yoga.YogaMeasureMode;
import com.google.common.primitives.Ints;

public class LayoutMetricsConversions {
    public static float getMinSize(int i) {
        return MeasureSpec.getMode(i) == Ints.MAX_POWER_OF_TWO ? (float) MeasureSpec.getSize(i) : 0.0f;
    }

    public static float getMaxSize(int i) {
        return MeasureSpec.getMode(i) == 0 ? Float.POSITIVE_INFINITY : (float) MeasureSpec.getSize(i);
    }

    public static float getYogaSize(float f, float f2) {
        if (f == f2) {
            return PixelUtil.toPixelFromDIP(f2);
        }
        if (Float.isInfinite(f2)) {
            return Float.POSITIVE_INFINITY;
        }
        return PixelUtil.toPixelFromDIP(f2);
    }

    public static YogaMeasureMode getYogaMeasureMode(float f, float f2) {
        if (f == f2) {
            return YogaMeasureMode.EXACTLY;
        }
        if (Float.isInfinite(f2)) {
            return YogaMeasureMode.UNDEFINED;
        }
        return YogaMeasureMode.AT_MOST;
    }
}
