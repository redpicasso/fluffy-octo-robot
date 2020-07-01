package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nullable;

public class ARTTextShadowNode extends ARTShapeShadowNode {
    private static final int DEFAULT_FONT_SIZE = 12;
    private static final String PROP_FONT = "font";
    private static final String PROP_FONT_FAMILY = "fontFamily";
    private static final String PROP_FONT_SIZE = "fontSize";
    private static final String PROP_FONT_STYLE = "fontStyle";
    private static final String PROP_FONT_WEIGHT = "fontWeight";
    private static final String PROP_LINES = "lines";
    private static final int TEXT_ALIGNMENT_CENTER = 2;
    private static final int TEXT_ALIGNMENT_LEFT = 0;
    private static final int TEXT_ALIGNMENT_RIGHT = 1;
    @Nullable
    private ReadableMap mFrame;
    private int mTextAlignment = 0;

    @ReactProp(name = "frame")
    public void setFrame(@Nullable ReadableMap readableMap) {
        this.mFrame = readableMap;
    }

    @ReactProp(defaultInt = 0, name = "alignment")
    public void setAlignment(int i) {
        this.mTextAlignment = i;
    }

    public void draw(Canvas canvas, Paint paint, float f) {
        if (this.mFrame != null) {
            f *= this.mOpacity;
            if (f > 0.01f) {
                ReadableMap readableMap = this.mFrame;
                String str = PROP_LINES;
                if (readableMap.hasKey(str)) {
                    ReadableArray array = this.mFrame.getArray(str);
                    if (!(array == null || array.size() == 0)) {
                        saveAndSetupCanvas(canvas);
                        String[] strArr = new String[array.size()];
                        for (int i = 0; i < strArr.length; i++) {
                            strArr[i] = array.getString(i);
                        }
                        String join = TextUtils.join(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE, strArr);
                        if (setupStrokePaint(paint, f)) {
                            applyTextPropertiesToPaint(paint);
                            if (this.mPath == null) {
                                canvas.drawText(join, 0.0f, -paint.ascent(), paint);
                            } else {
                                canvas.drawTextOnPath(join, this.mPath, 0.0f, 0.0f, paint);
                            }
                        }
                        if (setupFillPaint(paint, f)) {
                            applyTextPropertiesToPaint(paint);
                            if (this.mPath == null) {
                                canvas.drawText(join, 0.0f, -paint.ascent(), paint);
                            } else {
                                canvas.drawTextOnPath(join, this.mPath, 0.0f, 0.0f, paint);
                            }
                        }
                        restoreCanvas(canvas);
                        markUpdateSeen();
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0077 A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007f  */
    private void applyTextPropertiesToPaint(android.graphics.Paint r8) {
        /*
        r7 = this;
        r0 = r7.mTextAlignment;
        r1 = 2;
        r2 = 1;
        if (r0 == 0) goto L_0x0017;
    L_0x0006:
        if (r0 == r2) goto L_0x0011;
    L_0x0008:
        if (r0 == r1) goto L_0x000b;
    L_0x000a:
        goto L_0x001c;
    L_0x000b:
        r0 = android.graphics.Paint.Align.CENTER;
        r8.setTextAlign(r0);
        goto L_0x001c;
    L_0x0011:
        r0 = android.graphics.Paint.Align.RIGHT;
        r8.setTextAlign(r0);
        goto L_0x001c;
    L_0x0017:
        r0 = android.graphics.Paint.Align.LEFT;
        r8.setTextAlign(r0);
    L_0x001c:
        r0 = r7.mFrame;
        if (r0 == 0) goto L_0x0090;
    L_0x0020:
        r3 = "font";
        r0 = r0.hasKey(r3);
        if (r0 == 0) goto L_0x0090;
    L_0x0028:
        r0 = r7.mFrame;
        r0 = r0.getMap(r3);
        if (r0 == 0) goto L_0x0090;
    L_0x0030:
        r3 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r4 = "fontSize";
        r5 = r0.hasKey(r4);
        if (r5 == 0) goto L_0x003f;
    L_0x003a:
        r3 = r0.getDouble(r4);
        r3 = (float) r3;
    L_0x003f:
        r4 = r7.mScale;
        r3 = r3 * r4;
        r8.setTextSize(r3);
        r3 = "fontWeight";
        r4 = r0.hasKey(r3);
        r5 = 0;
        if (r4 == 0) goto L_0x005d;
    L_0x004f:
        r3 = r0.getString(r3);
        r4 = "bold";
        r3 = r4.equals(r3);
        if (r3 == 0) goto L_0x005d;
    L_0x005b:
        r3 = 1;
        goto L_0x005e;
    L_0x005d:
        r3 = 0;
    L_0x005e:
        r4 = "fontStyle";
        r6 = r0.hasKey(r4);
        if (r6 == 0) goto L_0x0074;
    L_0x0066:
        r4 = r0.getString(r4);
        r6 = "italic";
        r4 = r6.equals(r4);
        if (r4 == 0) goto L_0x0074;
    L_0x0072:
        r4 = 1;
        goto L_0x0075;
    L_0x0074:
        r4 = 0;
    L_0x0075:
        if (r3 == 0) goto L_0x007b;
    L_0x0077:
        if (r4 == 0) goto L_0x007b;
    L_0x0079:
        r1 = 3;
        goto L_0x0083;
    L_0x007b:
        if (r3 == 0) goto L_0x007f;
    L_0x007d:
        r1 = 1;
        goto L_0x0083;
    L_0x007f:
        if (r4 == 0) goto L_0x0082;
    L_0x0081:
        goto L_0x0083;
    L_0x0082:
        r1 = 0;
    L_0x0083:
        r2 = "fontFamily";
        r0 = r0.getString(r2);
        r0 = android.graphics.Typeface.create(r0, r1);
        r8.setTypeface(r0);
    L_0x0090:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.art.ARTTextShadowNode.applyTextPropertiesToPaint(android.graphics.Paint):void");
    }
}
