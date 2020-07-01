package com.facebook.react.views.view;

import android.view.View.MeasureSpec;
import com.facebook.yoga.YogaMeasureMode;
import com.google.common.primitives.Ints;

public class MeasureUtil {
    public static int getMeasureSpec(float f, YogaMeasureMode yogaMeasureMode) {
        if (yogaMeasureMode == YogaMeasureMode.EXACTLY) {
            return MeasureSpec.makeMeasureSpec((int) f, Ints.MAX_POWER_OF_TWO);
        }
        if (yogaMeasureMode == YogaMeasureMode.AT_MOST) {
            return MeasureSpec.makeMeasureSpec((int) f, Integer.MIN_VALUE);
        }
        return MeasureSpec.makeMeasureSpec(0, 0);
    }
}
