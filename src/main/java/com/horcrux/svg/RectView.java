package com.horcrux.svg;

import android.annotation.SuppressLint;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

@SuppressLint({"ViewConstructor"})
class RectView extends RenderableView {
    private SVGLength mH;
    private SVGLength mRx;
    private SVGLength mRy;
    private SVGLength mW;
    private SVGLength mX;
    private SVGLength mY;

    public RectView(ReactContext reactContext) {
        super(reactContext);
    }

    @ReactProp(name = "x")
    public void setX(Dynamic dynamic) {
        this.mX = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "y")
    public void setY(Dynamic dynamic) {
        this.mY = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "width")
    public void setWidth(Dynamic dynamic) {
        this.mW = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "height")
    public void setHeight(Dynamic dynamic) {
        this.mH = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "rx")
    public void setRx(Dynamic dynamic) {
        this.mRx = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(name = "ry")
    public void setRy(Dynamic dynamic) {
        this.mRy = SVGLength.from(dynamic);
        invalidate();
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0075  */
    android.graphics.Path getPath(android.graphics.Canvas r20, android.graphics.Paint r21) {
        /*
        r19 = this;
        r0 = r19;
        r9 = new android.graphics.Path;
        r9.<init>();
        r1 = r0.mX;
        r1 = r0.relativeOnWidth(r1);
        r3 = r0.mY;
        r3 = r0.relativeOnHeight(r3);
        r5 = r0.mW;
        r5 = r0.relativeOnWidth(r5);
        r7 = r0.mH;
        r7 = r0.relativeOnHeight(r7);
        r10 = r0.mRx;
        if (r10 != 0) goto L_0x003e;
    L_0x0023:
        r10 = r0.mRy;
        if (r10 == 0) goto L_0x0028;
    L_0x0027:
        goto L_0x003e;
    L_0x0028:
        r10 = (float) r1;
        r11 = (float) r3;
        r1 = r1 + r5;
        r5 = (float) r1;
        r3 = r3 + r7;
        r6 = (float) r3;
        r7 = android.graphics.Path.Direction.CW;
        r1 = r9;
        r2 = r10;
        r3 = r11;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r1.addRect(r2, r3, r4, r5, r6);
        r9.close();
        goto L_0x009d;
    L_0x003e:
        r10 = r0.mRx;
        if (r10 != 0) goto L_0x004a;
    L_0x0042:
        r10 = r0.mRy;
        r10 = r0.relativeOnHeight(r10);
    L_0x0048:
        r12 = r10;
        goto L_0x005d;
    L_0x004a:
        r11 = r0.mRy;
        if (r11 != 0) goto L_0x0053;
    L_0x004e:
        r10 = r0.relativeOnWidth(r10);
        goto L_0x0048;
    L_0x0053:
        r10 = r0.relativeOnWidth(r10);
        r12 = r0.mRy;
        r12 = r0.relativeOnHeight(r12);
    L_0x005d:
        r14 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r16 = r5 / r14;
        r18 = (r10 > r16 ? 1 : (r10 == r16 ? 0 : -1));
        if (r18 <= 0) goto L_0x0067;
    L_0x0065:
        r10 = r16;
    L_0x0067:
        r14 = r7 / r14;
        r16 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r16 <= 0) goto L_0x006e;
    L_0x006d:
        goto L_0x006f;
    L_0x006e:
        r14 = r12;
    L_0x006f:
        r12 = android.os.Build.VERSION.SDK_INT;
        r13 = 21;
        if (r12 < r13) goto L_0x008b;
    L_0x0075:
        r12 = (float) r1;
        r13 = (float) r3;
        r1 = r1 + r5;
        r5 = (float) r1;
        r3 = r3 + r7;
        r6 = (float) r3;
        r7 = (float) r10;
        r8 = (float) r14;
        r10 = android.graphics.Path.Direction.CW;
        r1 = r9;
        r2 = r12;
        r3 = r13;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r10;
        r1.addRoundRect(r2, r3, r4, r5, r6, r7, r8);
        goto L_0x009d;
    L_0x008b:
        r12 = new android.graphics.RectF;
        r13 = (float) r1;
        r0 = (float) r3;
        r1 = r1 + r5;
        r1 = (float) r1;
        r3 = r3 + r7;
        r2 = (float) r3;
        r12.<init>(r13, r0, r1, r2);
        r0 = (float) r10;
        r1 = (float) r14;
        r2 = android.graphics.Path.Direction.CW;
        r9.addRoundRect(r12, r0, r1, r2);
    L_0x009d:
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.RectView.getPath(android.graphics.Canvas, android.graphics.Paint):android.graphics.Path");
    }
}
