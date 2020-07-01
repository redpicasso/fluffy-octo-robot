package com.BV.LinearGradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.PixelUtil;

public class LinearGradientView extends View {
    private float mAngle = 45.0f;
    private float[] mAngleCenter = new float[]{0.5f, 0.5f};
    private float[] mBorderRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    private int[] mColors;
    private float[] mEndPos = new float[]{0.0f, 1.0f};
    private float[] mLocations;
    private final Paint mPaint = new Paint(1);
    private Path mPathForBorderRadius;
    private LinearGradient mShader;
    private int[] mSize = new int[]{0, 0};
    private float[] mStartPos = new float[]{0.0f, 0.0f};
    private RectF mTempRectForBorderRadius;
    private boolean mUseAngle = false;

    public LinearGradientView(Context context) {
        super(context);
    }

    public void setStartPosition(ReadableArray readableArray) {
        this.mStartPos = new float[]{(float) readableArray.getDouble(0), (float) readableArray.getDouble(1)};
        drawGradient();
    }

    public void setEndPosition(ReadableArray readableArray) {
        this.mEndPos = new float[]{(float) readableArray.getDouble(0), (float) readableArray.getDouble(1)};
        drawGradient();
    }

    public void setColors(ReadableArray readableArray) {
        int[] iArr = new int[readableArray.size()];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = readableArray.getInt(i);
        }
        this.mColors = iArr;
        drawGradient();
    }

    public void setLocations(ReadableArray readableArray) {
        float[] fArr = new float[readableArray.size()];
        for (int i = 0; i < fArr.length; i++) {
            fArr[i] = (float) readableArray.getDouble(i);
        }
        this.mLocations = fArr;
        drawGradient();
    }

    public void setUseAngle(boolean z) {
        this.mUseAngle = z;
        drawGradient();
    }

    public void setAngleCenter(ReadableArray readableArray) {
        this.mAngleCenter = new float[]{(float) readableArray.getDouble(0), (float) readableArray.getDouble(1)};
        drawGradient();
    }

    public void setAngle(float f) {
        this.mAngle = f;
        drawGradient();
    }

    public void setBorderRadii(ReadableArray readableArray) {
        float[] fArr = new float[readableArray.size()];
        for (int i = 0; i < fArr.length; i++) {
            fArr[i] = PixelUtil.toPixelFromDIP((float) readableArray.getDouble(i));
        }
        this.mBorderRadii = fArr;
        updatePath();
        drawGradient();
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        this.mSize = new int[]{i, i2};
        updatePath();
        drawGradient();
    }

    private float[] calculateGradientLocationWithAngle(float f) {
        float sqrt = (float) Math.sqrt(2.0d);
        r1 = new float[2];
        double d = (double) ((f - 90.0f) * 0.017453292f);
        r1[0] = ((float) Math.cos(d)) * sqrt;
        r1[1] = ((float) Math.sin(d)) * sqrt;
        return r1;
    }

    private void drawGradient() {
        int[] iArr = this.mColors;
        if (iArr != null) {
            float[] fArr = this.mLocations;
            if (fArr == null || iArr.length == fArr.length) {
                float[] fArr2 = this.mStartPos;
                fArr = this.mEndPos;
                if (this.mUseAngle && this.mAngleCenter != null) {
                    fArr2 = calculateGradientLocationWithAngle(this.mAngle);
                    r2 = new float[2];
                    float[] fArr3 = this.mAngleCenter;
                    r2[0] = fArr3[0] - (fArr2[0] / 2.0f);
                    r2[1] = fArr3[1] - (fArr2[1] / 2.0f);
                    fArr = new float[]{fArr3[0] + (fArr2[0] / 2.0f), fArr3[1] + (fArr2[1] / 2.0f)};
                    fArr2 = r2;
                }
                float f = fArr2[0];
                int[] iArr2 = this.mSize;
                float f2 = fArr2[1] * ((float) iArr2[1]);
                this.mShader = new LinearGradient(((float) iArr2[0]) * f, f2, fArr[0] * ((float) iArr2[0]), fArr[1] * ((float) iArr2[1]), this.mColors, this.mLocations, TileMode.CLAMP);
                this.mPaint.setShader(this.mShader);
                invalidate();
            }
        }
    }

    private void updatePath() {
        if (this.mPathForBorderRadius == null) {
            this.mPathForBorderRadius = new Path();
            this.mTempRectForBorderRadius = new RectF();
        }
        this.mPathForBorderRadius.reset();
        RectF rectF = this.mTempRectForBorderRadius;
        int[] iArr = this.mSize;
        rectF.set(0.0f, 0.0f, (float) iArr[0], (float) iArr[1]);
        this.mPathForBorderRadius.addRoundRect(this.mTempRectForBorderRadius, this.mBorderRadii, Direction.CW);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = this.mPathForBorderRadius;
        if (path == null) {
            canvas.drawPaint(this.mPaint);
        } else {
            canvas.drawPath(path, this.mPaint);
        }
    }
}
