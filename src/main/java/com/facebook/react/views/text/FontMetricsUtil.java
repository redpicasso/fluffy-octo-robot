package com.facebook.react.views.text;

import android.content.Context;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

public class FontMetricsUtil {
    private static final float AMPLIFICATION_FACTOR = 100.0f;
    private static final String CAP_HEIGHT_MEASUREMENT_TEXT = "T";
    private static final String X_HEIGHT_MEASUREMENT_TEXT = "x";

    public static WritableArray getFontMetrics(CharSequence charSequence, Layout layout, TextPaint textPaint, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        WritableArray createArray = Arguments.createArray();
        TextPaint textPaint2 = new TextPaint(textPaint);
        textPaint2.setTextSize(textPaint2.getTextSize() * AMPLIFICATION_FACTOR);
        Rect rect = new Rect();
        int i = 0;
        textPaint2.getTextBounds("T", 0, 1, rect);
        double height = (double) ((((float) rect.height()) / AMPLIFICATION_FACTOR) / displayMetrics.density);
        rect = new Rect();
        String str = X_HEIGHT_MEASUREMENT_TEXT;
        textPaint2.getTextBounds(str, 0, 1, rect);
        double height2 = (double) ((((float) rect.height()) / AMPLIFICATION_FACTOR) / displayMetrics.density);
        while (i < layout.getLineCount()) {
            rect = new Rect();
            layout.getLineBounds(i, rect);
            WritableMap createMap = Arguments.createMap();
            createMap.putDouble(str, (double) (layout.getLineLeft(i) / displayMetrics.density));
            createMap.putDouble("y", (double) (((float) rect.top) / displayMetrics.density));
            createMap.putDouble("width", (double) (layout.getLineWidth(i) / displayMetrics.density));
            createMap.putDouble("height", (double) (((float) rect.height()) / displayMetrics.density));
            createMap.putDouble("descender", (double) (((float) layout.getLineDescent(i)) / displayMetrics.density));
            createMap.putDouble("ascender", (double) (((float) (-layout.getLineAscent(i))) / displayMetrics.density));
            createMap.putDouble("baseline", (double) (((float) layout.getLineBaseline(i)) / displayMetrics.density));
            createMap.putDouble("capHeight", height);
            createMap.putDouble("xHeight", height2);
            createMap.putString("text", charSequence.subSequence(layout.getLineStart(i), layout.getLineEnd(i)).toString());
            createArray.pushMap(createMap);
            i++;
        }
        return createArray;
    }
}
