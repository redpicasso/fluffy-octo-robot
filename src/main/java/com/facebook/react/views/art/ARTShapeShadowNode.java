package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nullable;

public class ARTShapeShadowNode extends ARTVirtualNode {
    private static final int CAP_BUTT = 0;
    private static final int CAP_ROUND = 1;
    private static final int CAP_SQUARE = 2;
    private static final int COLOR_TYPE_LINEAR_GRADIENT = 1;
    private static final int COLOR_TYPE_PATTERN = 3;
    private static final int COLOR_TYPE_RADIAL_GRADIENT = 2;
    private static final int COLOR_TYPE_SOLID_COLOR = 0;
    private static final int JOIN_BEVEL = 2;
    private static final int JOIN_MITER = 0;
    private static final int JOIN_ROUND = 1;
    private static final int PATH_TYPE_ARC = 4;
    private static final int PATH_TYPE_CLOSE = 1;
    private static final int PATH_TYPE_CURVETO = 3;
    private static final int PATH_TYPE_LINETO = 2;
    private static final int PATH_TYPE_MOVETO = 0;
    @Nullable
    private float[] mBrushData;
    @Nullable
    protected Path mPath;
    private int mStrokeCap = 1;
    @Nullable
    private float[] mStrokeColor;
    @Nullable
    private float[] mStrokeDash;
    private int mStrokeJoin = 1;
    private float mStrokeWidth = 1.0f;

    private float modulus(float f, float f2) {
        f %= f2;
        return f < 0.0f ? f + f2 : f;
    }

    @ReactProp(name = "d")
    public void setShapePath(@Nullable ReadableArray readableArray) {
        this.mPath = createPath(PropHelper.toFloatArray(readableArray));
        markUpdated();
    }

    @ReactProp(name = "stroke")
    public void setStroke(@Nullable ReadableArray readableArray) {
        this.mStrokeColor = PropHelper.toFloatArray(readableArray);
        markUpdated();
    }

    @ReactProp(name = "strokeDash")
    public void setStrokeDash(@Nullable ReadableArray readableArray) {
        this.mStrokeDash = PropHelper.toFloatArray(readableArray);
        markUpdated();
    }

    @ReactProp(name = "fill")
    public void setFill(@Nullable ReadableArray readableArray) {
        this.mBrushData = PropHelper.toFloatArray(readableArray);
        markUpdated();
    }

    @ReactProp(defaultFloat = 1.0f, name = "strokeWidth")
    public void setStrokeWidth(float f) {
        this.mStrokeWidth = f;
        markUpdated();
    }

    @ReactProp(defaultInt = 1, name = "strokeCap")
    public void setStrokeCap(int i) {
        this.mStrokeCap = i;
        markUpdated();
    }

    @ReactProp(defaultInt = 1, name = "strokeJoin")
    public void setStrokeJoin(int i) {
        this.mStrokeJoin = i;
        markUpdated();
    }

    public void draw(Canvas canvas, Paint paint, float f) {
        f *= this.mOpacity;
        if (f > 0.01f) {
            saveAndSetupCanvas(canvas);
            if (this.mPath != null) {
                if (setupFillPaint(paint, f)) {
                    canvas.drawPath(this.mPath, paint);
                }
                if (setupStrokePaint(paint, f)) {
                    canvas.drawPath(this.mPath, paint);
                }
                restoreCanvas(canvas);
            } else {
                throw new JSApplicationIllegalArgumentException("Shapes should have a valid path (d) prop");
            }
        }
        markUpdateSeen();
    }

    protected boolean setupStrokePaint(Paint paint, float f) {
        if (this.mStrokeWidth != 0.0f) {
            float[] fArr = this.mStrokeColor;
            if (!(fArr == null || fArr.length == 0)) {
                StringBuilder stringBuilder;
                paint.reset();
                paint.setFlags(1);
                paint.setStyle(Style.STROKE);
                int i = this.mStrokeCap;
                String str = " unrecognized";
                if (i == 0) {
                    paint.setStrokeCap(Cap.BUTT);
                } else if (i == 1) {
                    paint.setStrokeCap(Cap.ROUND);
                } else if (i == 2) {
                    paint.setStrokeCap(Cap.SQUARE);
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("strokeCap ");
                    stringBuilder.append(this.mStrokeCap);
                    stringBuilder.append(str);
                    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
                }
                i = this.mStrokeJoin;
                if (i == 0) {
                    paint.setStrokeJoin(Join.MITER);
                } else if (i == 1) {
                    paint.setStrokeJoin(Join.ROUND);
                } else if (i == 2) {
                    paint.setStrokeJoin(Join.BEVEL);
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("strokeJoin ");
                    stringBuilder.append(this.mStrokeJoin);
                    stringBuilder.append(str);
                    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
                }
                paint.setStrokeWidth(this.mStrokeWidth * this.mScale);
                float[] fArr2 = this.mStrokeColor;
                int i2 = (int) (fArr2.length > 3 ? (fArr2[3] * f) * 255.0f : f * 255.0f);
                fArr2 = this.mStrokeColor;
                paint.setARGB(i2, (int) (fArr2[0] * 255.0f), (int) (fArr2[1] * 255.0f), (int) (fArr2[2] * 255.0f));
                float[] fArr3 = this.mStrokeDash;
                if (fArr3 != null && fArr3.length > 0) {
                    paint.setPathEffect(new DashPathEffect(fArr3, 0.0f));
                }
                return true;
            }
        }
        return false;
    }

    protected boolean setupFillPaint(Paint paint, float f) {
        Paint paint2 = paint;
        float[] fArr = this.mBrushData;
        int i = 0;
        if (fArr == null || fArr.length <= 0) {
            return false;
        }
        boolean z;
        paint.reset();
        paint2.setFlags(1);
        paint2.setStyle(Style.FILL);
        float[] fArr2 = this.mBrushData;
        int i2 = (int) fArr2[0];
        if (i2 != 0) {
            String str = ReactConstants.TAG;
            StringBuilder stringBuilder;
            if (i2 != 1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("ART: Color type ");
                stringBuilder.append(i2);
                stringBuilder.append(" not supported!");
                FLog.w(str, stringBuilder.toString());
            } else {
                int i3 = 5;
                if (fArr2.length < 5) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("[ARTShapeShadowNode setupFillPaint] expects 5 elements, received ");
                    stringBuilder.append(this.mBrushData.length);
                    FLog.w(str, stringBuilder.toString());
                    return false;
                }
                int[] iArr;
                float[] fArr3;
                float f2 = fArr2[1] * this.mScale;
                float f3 = this.mBrushData[2] * this.mScale;
                float f4 = this.mBrushData[3] * this.mScale;
                float f5 = this.mBrushData[4] * this.mScale;
                int length = (this.mBrushData.length - 5) / 5;
                if (length > 0) {
                    int[] iArr2 = new int[length];
                    float[] fArr4 = new float[length];
                    while (i < length) {
                        float[] fArr5 = this.mBrushData;
                        fArr4[i] = fArr5[((length * 4) + i3) + i];
                        int i4 = (i * 4) + i3;
                        iArr2[i] = Color.argb((int) (fArr5[i4 + 3] * 255.0f), (int) (fArr5[i4 + 0] * 255.0f), (int) (fArr5[i4 + 1] * 255.0f), (int) (fArr5[i4 + 2] * 255.0f));
                        i++;
                        i3 = 5;
                    }
                    iArr = iArr2;
                    fArr3 = fArr4;
                } else {
                    iArr = null;
                    fArr3 = iArr;
                }
                paint2.setShader(new LinearGradient(f2, f3, f4, f5, iArr, fArr3, TileMode.CLAMP));
            }
            z = true;
        } else {
            int i5 = (int) (fArr2.length > 4 ? (fArr2[4] * f) * 255.0f : f * 255.0f);
            float[] fArr6 = this.mBrushData;
            z = true;
            paint2.setARGB(i5, (int) (fArr6[1] * 255.0f), (int) (fArr6[2] * 255.0f), (int) (fArr6[3] * 255.0f));
        }
        return z;
    }

    private Path createPath(float[] fArr) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        int i = 0;
        while (i < fArr.length) {
            int i2;
            int i3 = i + 1;
            i = (int) fArr[i];
            if (i != 0) {
                Object obj = 1;
                if (i == 1) {
                    path.close();
                    i = i3;
                } else if (i != 2) {
                    int i4;
                    float f;
                    float f2;
                    float f3;
                    if (i == 3) {
                        i = i3 + 1;
                        i2 = i + 1;
                        f = this.mScale * fArr[i];
                        i = i2 + 1;
                        f2 = this.mScale * fArr[i2];
                        i2 = i + 1;
                        f3 = this.mScale * fArr[i];
                        i = i2 + 1;
                        i4 = i + 1;
                        path.cubicTo(fArr[i3] * this.mScale, f, f2, f3, this.mScale * fArr[i2], fArr[i] * this.mScale);
                    } else if (i == 4) {
                        i = i3 + 1;
                        float f4 = fArr[i3] * this.mScale;
                        int i5 = i + 1;
                        float f5 = fArr[i] * this.mScale;
                        int i6 = i5 + 1;
                        f = fArr[i5] * this.mScale;
                        int i7 = i6 + 1;
                        f2 = (float) Math.toDegrees((double) fArr[i6]);
                        int i8 = i7 + 1;
                        f3 = (float) Math.toDegrees((double) fArr[i7]);
                        i4 = i8 + 1;
                        if (fArr[i8] == 1.0f) {
                            obj = null;
                        }
                        f3 -= f2;
                        if (Math.abs(f3) >= 360.0f) {
                            path.addCircle(f4, f5, f, obj != null ? Direction.CCW : Direction.CW);
                        } else {
                            f3 = modulus(f3, 360.0f);
                            if (obj != null && f3 < 360.0f) {
                                f3 = (360.0f - f3) * -1.0f;
                            }
                            path.arcTo(new RectF(f4 - f, f5 - f, f4 + f, f5 + f), f2, f3);
                        }
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unrecognized drawing instruction ");
                        stringBuilder.append(i);
                        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
                    }
                    i = i4;
                } else {
                    i = i3 + 1;
                    i2 = i + 1;
                    path.lineTo(fArr[i3] * this.mScale, fArr[i] * this.mScale);
                }
            } else {
                i = i3 + 1;
                i2 = i + 1;
                path.moveTo(fArr[i3] * this.mScale, fArr[i] * this.mScale);
            }
            i = i2;
        }
        return path;
    }
}
