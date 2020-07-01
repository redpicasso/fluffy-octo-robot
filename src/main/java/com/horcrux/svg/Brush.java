package com.horcrux.svg;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import androidx.core.view.ViewCompat;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.ReactConstants;
import com.horcrux.svg.SVGLength.UnitType;

class Brush {
    private ReadableArray mColors;
    private Matrix mMatrix;
    private PatternView mPattern;
    private final SVGLength[] mPoints;
    private final BrushType mType;
    private boolean mUseContentObjectBoundingBoxUnits;
    private final boolean mUseObjectBoundingBox;
    private Rect mUserSpaceBoundingBox;

    enum BrushType {
        LINEAR_GRADIENT,
        RADIAL_GRADIENT,
        PATTERN
    }

    enum BrushUnits {
        OBJECT_BOUNDING_BOX,
        USER_SPACE_ON_USE
    }

    Brush(BrushType brushType, SVGLength[] sVGLengthArr, BrushUnits brushUnits) {
        this.mType = brushType;
        this.mPoints = sVGLengthArr;
        this.mUseObjectBoundingBox = brushUnits == BrushUnits.OBJECT_BOUNDING_BOX;
    }

    void setContentUnits(BrushUnits brushUnits) {
        this.mUseContentObjectBoundingBoxUnits = brushUnits == BrushUnits.OBJECT_BOUNDING_BOX;
    }

    void setPattern(PatternView patternView) {
        this.mPattern = patternView;
    }

    private static void parseGradientStops(ReadableArray readableArray, int i, float[] fArr, int[] iArr, float f) {
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 2;
            fArr[i2] = (float) readableArray.getDouble(i3);
            i3 = readableArray.getInt(i3 + 1);
            iArr[i2] = (i3 & ViewCompat.MEASURED_SIZE_MASK) | (Math.round(((float) (i3 >>> 24)) * f) << 24);
        }
    }

    void setUserSpaceBoundingBox(Rect rect) {
        this.mUserSpaceBoundingBox = rect;
    }

    void setGradientColors(ReadableArray readableArray) {
        this.mColors = readableArray;
    }

    void setGradientTransform(Matrix matrix) {
        this.mMatrix = matrix;
    }

    private RectF getPaintRect(RectF rectF) {
        float f;
        if (!this.mUseObjectBoundingBox) {
            rectF = new RectF(this.mUserSpaceBoundingBox);
        }
        float width = rectF.width();
        float height = rectF.height();
        float f2 = 0.0f;
        if (this.mUseObjectBoundingBox) {
            f2 = rectF.left;
            f = rectF.top;
        } else {
            f = 0.0f;
        }
        return new RectF(f2, f, width + f2, height + f);
    }

    private double getVal(SVGLength sVGLength, double d, float f, float f2) {
        double d2;
        if (!this.mUseObjectBoundingBox) {
            SVGLength sVGLength2 = sVGLength;
        } else if (sVGLength.unit == UnitType.NUMBER) {
            d2 = d;
            return PropHelper.fromRelative(sVGLength, d, 0.0d, d2, (double) f2);
        }
        d2 = (double) f;
        return PropHelper.fromRelative(sVGLength, d, 0.0d, d2, (double) f2);
    }

    void setupPaint(Paint paint, RectF rectF, float f, float f2) {
        Paint paint2 = paint;
        float f3 = f2;
        RectF paintRect = getPaintRect(rectF);
        float width = paintRect.width();
        float height = paintRect.height();
        float f4 = paintRect.left;
        float f5 = paintRect.top;
        float textSize = paint.getTextSize();
        double d;
        double val;
        double d2;
        double d3;
        double val2;
        Matrix matrix;
        Matrix matrix2;
        if (this.mType == BrushType.PATTERN) {
            d = (double) width;
            val = getVal(this.mPoints[0], d, f, textSize);
            d2 = (double) height;
            double d4 = d2;
            double d5 = val;
            val = getVal(this.mPoints[1], d2, f, textSize);
            d2 = d;
            d = val;
            val = getVal(this.mPoints[2], d2, f, textSize);
            d3 = val;
            val2 = getVal(this.mPoints[3], d4, f, textSize);
            if (d3 > 1.0d && val2 > 1.0d) {
                Bitmap createBitmap = Bitmap.createBitmap((int) d3, (int) val2, Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                RectF viewBox = this.mPattern.getViewBox();
                if (viewBox != null && viewBox.width() > 0.0f && viewBox.height() > 0.0f) {
                    canvas.concat(ViewBox.getTransform(viewBox, new RectF((float) d5, (float) d, (float) d3, (float) val2), this.mPattern.mAlign, this.mPattern.mMeetOrSlice));
                }
                if (this.mUseContentObjectBoundingBoxUnits) {
                    canvas.scale(width / f, height / f);
                }
                this.mPattern.draw(canvas, new Paint(), f2);
                matrix = new Matrix();
                matrix2 = this.mMatrix;
                if (matrix2 != null) {
                    matrix.preConcat(matrix2);
                }
                Shader bitmapShader = new BitmapShader(createBitmap, TileMode.REPEAT, TileMode.REPEAT);
                bitmapShader.setLocalMatrix(matrix);
                paint.setShader(bitmapShader);
            }
            return;
        }
        float f6 = f3;
        int size = this.mColors.size();
        String str = ReactConstants.TAG;
        if (size == 0) {
            FLog.w(str, "Gradient contains no stops");
            return;
        }
        int[] iArr;
        float[] fArr;
        size /= 2;
        int[] iArr2 = new int[size];
        float[] fArr2 = new float[size];
        parseGradientStops(this.mColors, size, fArr2, iArr2, f6);
        if (fArr2.length == 1) {
            int[] iArr3 = new int[]{iArr2[0], iArr2[0]};
            float[] fArr3 = new float[]{fArr2[0], fArr2[0]};
            FLog.w(str, "Gradient contains only one stop");
            iArr = iArr3;
            fArr = fArr3;
        } else {
            iArr = iArr2;
            fArr = fArr2;
        }
        double d6;
        Shader linearGradient;
        if (this.mType == BrushType.LINEAR_GRADIENT) {
            val = (double) width;
            double d7 = val;
            val2 = getVal(this.mPoints[0], val, f, textSize);
            val = (double) f4;
            d2 = val2 + val;
            d6 = (double) height;
            double d8 = d2;
            double d9 = val;
            d = (double) f5;
            val = getVal(this.mPoints[1], d6, f, textSize) + d;
            d3 = val;
            val = getVal(this.mPoints[2], d7, f, textSize) + d9;
            d2 = d6;
            d6 = val;
            linearGradient = new LinearGradient((float) d8, (float) d3, (float) d6, (float) (getVal(this.mPoints[3], d2, f, textSize) + d), iArr, fArr, TileMode.CLAMP);
            if (this.mMatrix != null) {
                matrix = new Matrix();
                matrix.preConcat(this.mMatrix);
                linearGradient.setLocalMatrix(matrix);
            }
            paint2.setShader(linearGradient);
        } else if (this.mType == BrushType.RADIAL_GRADIENT) {
            d3 = (double) width;
            val = getVal(this.mPoints[2], d3, f, textSize);
            d6 = (double) height;
            int[] iArr4 = iArr;
            double d10 = val;
            val = getVal(this.mPoints[3], d6, f, textSize) / d10;
            d2 = d3;
            d3 = val;
            val = getVal(this.mPoints[4], d2, f, textSize) + ((double) f4);
            d2 = d6;
            linearGradient = new RadialGradient((float) val, (float) (getVal(this.mPoints[5], d2, f, textSize) + (((double) f5) / d3)), (float) d10, iArr4, fArr, TileMode.CLAMP);
            matrix = new Matrix();
            matrix.preScale(1.0f, (float) d3);
            matrix2 = this.mMatrix;
            if (matrix2 != null) {
                matrix.preConcat(matrix2);
            }
            linearGradient.setLocalMatrix(matrix);
            paint.setShader(linearGradient);
        }
    }
}
