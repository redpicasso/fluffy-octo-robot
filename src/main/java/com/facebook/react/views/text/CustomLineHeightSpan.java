package com.facebook.react.views.text;

import android.graphics.Paint.FontMetricsInt;
import android.text.style.LineHeightSpan;

public class CustomLineHeightSpan implements LineHeightSpan, ReactSpan {
    private final int mHeight;

    CustomLineHeightSpan(float f) {
        this.mHeight = (int) Math.ceil((double) f);
    }

    public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, FontMetricsInt fontMetricsInt) {
        int i5 = fontMetricsInt.descent;
        i = this.mHeight;
        if (i5 > i) {
            i5 = Math.min(i, fontMetricsInt.descent);
            fontMetricsInt.descent = i5;
            fontMetricsInt.bottom = i5;
            fontMetricsInt.ascent = 0;
            fontMetricsInt.top = 0;
        } else if ((-fontMetricsInt.ascent) + fontMetricsInt.descent > this.mHeight) {
            fontMetricsInt.bottom = fontMetricsInt.descent;
            i5 = (-this.mHeight) + fontMetricsInt.descent;
            fontMetricsInt.ascent = i5;
            fontMetricsInt.top = i5;
        } else if ((-fontMetricsInt.ascent) + fontMetricsInt.bottom > this.mHeight) {
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = fontMetricsInt.ascent + this.mHeight;
        } else {
            i5 = (-fontMetricsInt.top) + fontMetricsInt.bottom;
            i = this.mHeight;
            if (i5 > i) {
                fontMetricsInt.top = fontMetricsInt.bottom - this.mHeight;
                return;
            }
            double d = (double) (((float) (i - ((-fontMetricsInt.top) + fontMetricsInt.bottom))) / 2.0f);
            fontMetricsInt.top = (int) (((double) fontMetricsInt.top) - Math.ceil(d));
            fontMetricsInt.bottom = (int) (((double) fontMetricsInt.bottom) + Math.floor(d));
            fontMetricsInt.ascent = fontMetricsInt.top;
            fontMetricsInt.descent = fontMetricsInt.bottom;
        }
    }
}
