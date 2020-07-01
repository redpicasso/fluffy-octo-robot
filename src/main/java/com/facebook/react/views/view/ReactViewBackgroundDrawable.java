package com.facebook.react.views.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import androidx.core.view.ViewCompat;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.FloatUtil;
import com.facebook.react.uimanager.Spacing;
import com.facebook.yoga.YogaConstants;
import java.util.Arrays;
import java.util.Locale;
import javax.annotation.Nullable;

public class ReactViewBackgroundDrawable extends Drawable {
    private static final int ALL_BITS_SET = -1;
    private static final int ALL_BITS_UNSET = 0;
    private static final int DEFAULT_BORDER_ALPHA = 255;
    private static final int DEFAULT_BORDER_COLOR = -16777216;
    private static final int DEFAULT_BORDER_RGB = 0;
    private int mAlpha = 255;
    @Nullable
    private Spacing mBorderAlpha;
    @Nullable
    private float[] mBorderCornerRadii;
    @Nullable
    private Spacing mBorderRGB;
    private float mBorderRadius = Float.NaN;
    @Nullable
    private BorderStyle mBorderStyle;
    @Nullable
    private Spacing mBorderWidth;
    @Nullable
    private Path mCenterDrawPath;
    private int mColor = 0;
    private final Context mContext;
    @Nullable
    private PointF mInnerBottomLeftCorner;
    @Nullable
    private PointF mInnerBottomRightCorner;
    @Nullable
    private Path mInnerClipPathForBorderRadius;
    @Nullable
    private RectF mInnerClipTempRectForBorderRadius;
    @Nullable
    private PointF mInnerTopLeftCorner;
    @Nullable
    private PointF mInnerTopRightCorner;
    private int mLayoutDirection;
    private boolean mNeedUpdatePathForBorderRadius = false;
    @Nullable
    private Path mOuterClipPathForBorderRadius;
    @Nullable
    private RectF mOuterClipTempRectForBorderRadius;
    private final Paint mPaint = new Paint(1);
    @Nullable
    private PathEffect mPathEffectForBorderStyle;
    @Nullable
    private Path mPathForBorder;
    @Nullable
    private Path mPathForBorderRadiusOutline;
    @Nullable
    private RectF mTempRectForBorderRadiusOutline;
    @Nullable
    private RectF mTempRectForCenterDrawPath;

    /* renamed from: com.facebook.react.views.view.ReactViewBackgroundDrawable$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$views$view$ReactViewBackgroundDrawable$BorderStyle = new int[BorderStyle.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$facebook$react$views$view$ReactViewBackgroundDrawable$BorderStyle[com.facebook.react.views.view.ReactViewBackgroundDrawable.BorderStyle.DOTTED.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.facebook.react.views.view.ReactViewBackgroundDrawable.BorderStyle.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$views$view$ReactViewBackgroundDrawable$BorderStyle = r0;
            r0 = $SwitchMap$com$facebook$react$views$view$ReactViewBackgroundDrawable$BorderStyle;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.views.view.ReactViewBackgroundDrawable.BorderStyle.SOLID;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$views$view$ReactViewBackgroundDrawable$BorderStyle;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.views.view.ReactViewBackgroundDrawable.BorderStyle.DASHED;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$react$views$view$ReactViewBackgroundDrawable$BorderStyle;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.react.views.view.ReactViewBackgroundDrawable.BorderStyle.DOTTED;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.view.ReactViewBackgroundDrawable.1.<clinit>():void");
        }
    }

    public enum BorderRadiusLocation {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT,
        TOP_START,
        TOP_END,
        BOTTOM_START,
        BOTTOM_END
    }

    private enum BorderStyle {
        SOLID,
        DASHED,
        DOTTED;

        @Nullable
        public static PathEffect getPathEffect(BorderStyle borderStyle, float f) {
            int i = AnonymousClass1.$SwitchMap$com$facebook$react$views$view$ReactViewBackgroundDrawable$BorderStyle[borderStyle.ordinal()];
            if (i == 1) {
                return null;
            }
            if (i == 2) {
                r0 = new float[4];
                f *= 3.0f;
                r0[0] = f;
                r0[1] = f;
                r0[2] = f;
                r0[3] = f;
                return new DashPathEffect(r0, 0.0f);
            } else if (i != 3) {
                return null;
            } else {
                return new DashPathEffect(new float[]{f, f, f, f}, 0.0f);
            }
        }
    }

    private static int colorFromAlphaAndRGBComponents(float f, float f2) {
        return ((((int) f) << 24) & -16777216) | (((int) f2) & ViewCompat.MEASURED_SIZE_MASK);
    }

    private static int fastBorderCompatibleColorOrZero(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = -1;
        int i10 = ((i > 0 ? i5 : -1) & (i2 > 0 ? i6 : -1)) & (i3 > 0 ? i7 : -1);
        if (i4 > 0) {
            i9 = i8;
        }
        i9 &= i10;
        if (i <= 0) {
            i5 = 0;
        }
        if (i2 <= 0) {
            i6 = 0;
        }
        i = i5 | i6;
        if (i3 <= 0) {
            i7 = 0;
        }
        i |= i7;
        if (i4 <= 0) {
            i8 = 0;
        }
        return i9 == (i | i8) ? i9 : 0;
    }

    public boolean onResolvedLayoutDirectionChanged(int i) {
        return false;
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public ReactViewBackgroundDrawable(Context context) {
        this.mContext = context;
    }

    public void draw(Canvas canvas) {
        updatePathEffect();
        if (hasRoundedBorders()) {
            drawRoundedBackgroundWithBorders(canvas);
        } else {
            drawRectangularBackgroundWithBorders(canvas);
        }
    }

    public boolean hasRoundedBorders() {
        if (!YogaConstants.isUndefined(this.mBorderRadius) && this.mBorderRadius > 0.0f) {
            return true;
        }
        float[] fArr = this.mBorderCornerRadii;
        if (fArr != null) {
            for (float f : fArr) {
                if (!YogaConstants.isUndefined(f) && f > 0.0f) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mNeedUpdatePathForBorderRadius = true;
    }

    public void setAlpha(int i) {
        if (i != this.mAlpha) {
            this.mAlpha = i;
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public int getOpacity() {
        return ColorUtil.getOpacityFromColor(ColorUtil.multiplyColorAlpha(this.mColor, this.mAlpha));
    }

    public void getOutline(Outline outline) {
        if (VERSION.SDK_INT < 21) {
            super.getOutline(outline);
            return;
        }
        if ((YogaConstants.isUndefined(this.mBorderRadius) || this.mBorderRadius <= 0.0f) && this.mBorderCornerRadii == null) {
            outline.setRect(getBounds());
        } else {
            updatePath();
            outline.setConvexPath(this.mPathForBorderRadiusOutline);
        }
    }

    public void setBorderWidth(int i, float f) {
        if (this.mBorderWidth == null) {
            this.mBorderWidth = new Spacing();
        }
        if (!FloatUtil.floatsEqual(this.mBorderWidth.getRaw(i), f)) {
            this.mBorderWidth.set(i, f);
            if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 8) {
                this.mNeedUpdatePathForBorderRadius = true;
            }
            invalidateSelf();
        }
    }

    public void setBorderColor(int i, float f, float f2) {
        setBorderRGB(i, f);
        setBorderAlpha(i, f2);
    }

    private void setBorderRGB(int i, float f) {
        if (this.mBorderRGB == null) {
            this.mBorderRGB = new Spacing(0.0f);
        }
        if (!FloatUtil.floatsEqual(this.mBorderRGB.getRaw(i), f)) {
            this.mBorderRGB.set(i, f);
            invalidateSelf();
        }
    }

    private void setBorderAlpha(int i, float f) {
        if (this.mBorderAlpha == null) {
            this.mBorderAlpha = new Spacing(255.0f);
        }
        if (!FloatUtil.floatsEqual(this.mBorderAlpha.getRaw(i), f)) {
            this.mBorderAlpha.set(i, f);
            invalidateSelf();
        }
    }

    public void setBorderStyle(@Nullable String str) {
        BorderStyle borderStyle;
        if (str == null) {
            borderStyle = null;
        } else {
            borderStyle = BorderStyle.valueOf(str.toUpperCase(Locale.US));
        }
        if (this.mBorderStyle != borderStyle) {
            this.mBorderStyle = borderStyle;
            this.mNeedUpdatePathForBorderRadius = true;
            invalidateSelf();
        }
    }

    public void setRadius(float f) {
        if (!FloatUtil.floatsEqual(this.mBorderRadius, f)) {
            this.mBorderRadius = f;
            this.mNeedUpdatePathForBorderRadius = true;
            invalidateSelf();
        }
    }

    public void setRadius(float f, int i) {
        if (this.mBorderCornerRadii == null) {
            this.mBorderCornerRadii = new float[8];
            Arrays.fill(this.mBorderCornerRadii, Float.NaN);
        }
        if (!FloatUtil.floatsEqual(this.mBorderCornerRadii[i], f)) {
            this.mBorderCornerRadii[i] = f;
            this.mNeedUpdatePathForBorderRadius = true;
            invalidateSelf();
        }
    }

    public float getFullBorderRadius() {
        return YogaConstants.isUndefined(this.mBorderRadius) ? 0.0f : this.mBorderRadius;
    }

    public float getBorderRadius(BorderRadiusLocation borderRadiusLocation) {
        return getBorderRadiusOrDefaultTo(Float.NaN, borderRadiusLocation);
    }

    public float getBorderRadiusOrDefaultTo(float f, BorderRadiusLocation borderRadiusLocation) {
        float[] fArr = this.mBorderCornerRadii;
        if (fArr == null) {
            return f;
        }
        float f2 = fArr[borderRadiusLocation.ordinal()];
        return YogaConstants.isUndefined(f2) ? f : f2;
    }

    public void setColor(int i) {
        this.mColor = i;
        invalidateSelf();
    }

    public int getResolvedLayoutDirection() {
        return this.mLayoutDirection;
    }

    public boolean setResolvedLayoutDirection(int i) {
        if (this.mLayoutDirection == i) {
            return false;
        }
        this.mLayoutDirection = i;
        return onResolvedLayoutDirectionChanged(i);
    }

    @VisibleForTesting
    public int getColor() {
        return this.mColor;
    }

    /* JADX WARNING: Removed duplicated region for block: B:60:0x015f  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0132  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x016d  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0193  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01ba  */
    private void drawRoundedBackgroundWithBorders(android.graphics.Canvas r23) {
        /*
        r22 = this;
        r11 = r22;
        r12 = r23;
        r22.updatePath();
        r23.save();
        r0 = r11.mColor;
        r1 = r11.mAlpha;
        r0 = com.facebook.react.views.view.ColorUtil.multiplyColorAlpha(r0, r1);
        r1 = android.graphics.Color.alpha(r0);
        if (r1 == 0) goto L_0x002b;
    L_0x0018:
        r1 = r11.mPaint;
        r1.setColor(r0);
        r0 = r11.mPaint;
        r1 = android.graphics.Paint.Style.FILL;
        r0.setStyle(r1);
        r0 = r11.mInnerClipPathForBorderRadius;
        r1 = r11.mPaint;
        r12.drawPath(r0, r1);
    L_0x002b:
        r13 = r22.getDirectionAwareBorderInsets();
        r0 = r13.top;
        r14 = 0;
        r0 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1));
        if (r0 > 0) goto L_0x0048;
    L_0x0036:
        r0 = r13.bottom;
        r0 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1));
        if (r0 > 0) goto L_0x0048;
    L_0x003c:
        r0 = r13.left;
        r0 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1));
        if (r0 > 0) goto L_0x0048;
    L_0x0042:
        r0 = r13.right;
        r0 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1));
        if (r0 <= 0) goto L_0x01db;
    L_0x0048:
        r0 = r22.getFullBorderWidth();
        r1 = r13.top;
        r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1));
        if (r1 != 0) goto L_0x008e;
    L_0x0052:
        r1 = r13.bottom;
        r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1));
        if (r1 != 0) goto L_0x008e;
    L_0x0058:
        r1 = r13.left;
        r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1));
        if (r1 != 0) goto L_0x008e;
    L_0x005e:
        r1 = r13.right;
        r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1));
        if (r1 != 0) goto L_0x008e;
    L_0x0064:
        r1 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1));
        if (r1 <= 0) goto L_0x01db;
    L_0x0068:
        r1 = 8;
        r1 = r11.getBorderColor(r1);
        r2 = r11.mPaint;
        r3 = r11.mAlpha;
        r1 = com.facebook.react.views.view.ColorUtil.multiplyColorAlpha(r1, r3);
        r2.setColor(r1);
        r1 = r11.mPaint;
        r2 = android.graphics.Paint.Style.STROKE;
        r1.setStyle(r2);
        r1 = r11.mPaint;
        r1.setStrokeWidth(r0);
        r0 = r11.mCenterDrawPath;
        r1 = r11.mPaint;
        r12.drawPath(r0, r1);
        goto L_0x01db;
    L_0x008e:
        r0 = r11.mPaint;
        r1 = android.graphics.Paint.Style.FILL;
        r0.setStyle(r1);
        r0 = r11.mOuterClipPathForBorderRadius;
        r1 = android.graphics.Region.Op.INTERSECT;
        r12.clipPath(r0, r1);
        r0 = r11.mInnerClipPathForBorderRadius;
        r1 = android.graphics.Region.Op.DIFFERENCE;
        r12.clipPath(r0, r1);
        r0 = 0;
        r1 = r11.getBorderColor(r0);
        r2 = 1;
        r15 = r11.getBorderColor(r2);
        r3 = 2;
        r3 = r11.getBorderColor(r3);
        r4 = 3;
        r16 = r11.getBorderColor(r4);
        r4 = android.os.Build.VERSION.SDK_INT;
        r5 = 17;
        if (r4 < r5) goto L_0x0119;
    L_0x00bd:
        r4 = r22.getResolvedLayoutDirection();
        if (r4 != r2) goto L_0x00c4;
    L_0x00c3:
        r0 = 1;
    L_0x00c4:
        r2 = 4;
        r4 = r11.getBorderColor(r2);
        r5 = 5;
        r6 = r11.getBorderColor(r5);
        r7 = com.facebook.react.modules.i18nmanager.I18nUtil.getInstance();
        r8 = r11.mContext;
        r7 = r7.doLeftAndRightSwapInRTL(r8);
        if (r7 == 0) goto L_0x00f6;
    L_0x00da:
        r2 = r11.isBorderColorDefined(r2);
        if (r2 != 0) goto L_0x00e1;
    L_0x00e0:
        goto L_0x00e2;
    L_0x00e1:
        r1 = r4;
    L_0x00e2:
        r2 = r11.isBorderColorDefined(r5);
        if (r2 != 0) goto L_0x00e9;
    L_0x00e8:
        goto L_0x00ea;
    L_0x00e9:
        r3 = r6;
    L_0x00ea:
        if (r0 == 0) goto L_0x00ee;
    L_0x00ec:
        r2 = r3;
        goto L_0x00ef;
    L_0x00ee:
        r2 = r1;
    L_0x00ef:
        if (r0 == 0) goto L_0x00f2;
    L_0x00f1:
        goto L_0x00f3;
    L_0x00f2:
        r1 = r3;
    L_0x00f3:
        r17 = r1;
        goto L_0x011c;
    L_0x00f6:
        if (r0 == 0) goto L_0x00fa;
    L_0x00f8:
        r7 = r6;
        goto L_0x00fb;
    L_0x00fa:
        r7 = r4;
    L_0x00fb:
        if (r0 == 0) goto L_0x00fe;
    L_0x00fd:
        goto L_0x00ff;
    L_0x00fe:
        r4 = r6;
    L_0x00ff:
        r2 = r11.isBorderColorDefined(r2);
        r5 = r11.isBorderColorDefined(r5);
        if (r0 == 0) goto L_0x010b;
    L_0x0109:
        r6 = r5;
        goto L_0x010c;
    L_0x010b:
        r6 = r2;
    L_0x010c:
        if (r0 == 0) goto L_0x010f;
    L_0x010e:
        goto L_0x0110;
    L_0x010f:
        r2 = r5;
    L_0x0110:
        if (r6 == 0) goto L_0x0113;
    L_0x0112:
        r1 = r7;
    L_0x0113:
        if (r2 == 0) goto L_0x0119;
    L_0x0115:
        r2 = r1;
        r17 = r4;
        goto L_0x011c;
    L_0x0119:
        r2 = r1;
        r17 = r3;
    L_0x011c:
        r0 = r11.mOuterClipTempRectForBorderRadius;
        r10 = r0.left;
        r0 = r11.mOuterClipTempRectForBorderRadius;
        r9 = r0.right;
        r0 = r11.mOuterClipTempRectForBorderRadius;
        r8 = r0.top;
        r0 = r11.mOuterClipTempRectForBorderRadius;
        r7 = r0.bottom;
        r0 = r13.left;
        r0 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1));
        if (r0 <= 0) goto L_0x015f;
    L_0x0132:
        r0 = r11.mInnerTopLeftCorner;
        r5 = r0.x;
        r0 = r11.mInnerTopLeftCorner;
        r6 = r0.y;
        r0 = r11.mInnerBottomLeftCorner;
        r4 = r0.x;
        r0 = r11.mInnerBottomLeftCorner;
        r3 = r0.y;
        r0 = r22;
        r1 = r23;
        r18 = r3;
        r3 = r10;
        r19 = r4;
        r4 = r8;
        r20 = r7;
        r7 = r19;
        r19 = r8;
        r8 = r18;
        r18 = r9;
        r9 = r10;
        r21 = r10;
        r10 = r20;
        r0.drawQuadrilateral(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
        goto L_0x0167;
    L_0x015f:
        r20 = r7;
        r19 = r8;
        r18 = r9;
        r21 = r10;
    L_0x0167:
        r0 = r13.top;
        r0 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1));
        if (r0 <= 0) goto L_0x018d;
    L_0x016d:
        r0 = r11.mInnerTopLeftCorner;
        r5 = r0.x;
        r0 = r11.mInnerTopLeftCorner;
        r6 = r0.y;
        r0 = r11.mInnerTopRightCorner;
        r7 = r0.x;
        r0 = r11.mInnerTopRightCorner;
        r8 = r0.y;
        r0 = r22;
        r1 = r23;
        r2 = r15;
        r3 = r21;
        r4 = r19;
        r9 = r18;
        r10 = r19;
        r0.drawQuadrilateral(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
    L_0x018d:
        r0 = r13.right;
        r0 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1));
        if (r0 <= 0) goto L_0x01b4;
    L_0x0193:
        r0 = r11.mInnerTopRightCorner;
        r5 = r0.x;
        r0 = r11.mInnerTopRightCorner;
        r6 = r0.y;
        r0 = r11.mInnerBottomRightCorner;
        r7 = r0.x;
        r0 = r11.mInnerBottomRightCorner;
        r8 = r0.y;
        r0 = r22;
        r1 = r23;
        r2 = r17;
        r3 = r18;
        r4 = r19;
        r9 = r18;
        r10 = r20;
        r0.drawQuadrilateral(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
    L_0x01b4:
        r0 = r13.bottom;
        r0 = (r0 > r14 ? 1 : (r0 == r14 ? 0 : -1));
        if (r0 <= 0) goto L_0x01db;
    L_0x01ba:
        r0 = r11.mInnerBottomLeftCorner;
        r5 = r0.x;
        r0 = r11.mInnerBottomLeftCorner;
        r6 = r0.y;
        r0 = r11.mInnerBottomRightCorner;
        r7 = r0.x;
        r0 = r11.mInnerBottomRightCorner;
        r8 = r0.y;
        r0 = r22;
        r1 = r23;
        r2 = r16;
        r3 = r21;
        r4 = r20;
        r9 = r18;
        r10 = r20;
        r0.drawQuadrilateral(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
    L_0x01db:
        r23.restore();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.view.ReactViewBackgroundDrawable.drawRoundedBackgroundWithBorders(android.graphics.Canvas):void");
    }

    private void updatePath() {
        if (this.mNeedUpdatePathForBorderRadius) {
            float borderRadius;
            float borderRadius2;
            float borderRadius3;
            float borderRadius4;
            float f;
            this.mNeedUpdatePathForBorderRadius = false;
            if (this.mInnerClipPathForBorderRadius == null) {
                this.mInnerClipPathForBorderRadius = new Path();
            }
            if (this.mOuterClipPathForBorderRadius == null) {
                this.mOuterClipPathForBorderRadius = new Path();
            }
            if (this.mPathForBorderRadiusOutline == null) {
                this.mPathForBorderRadiusOutline = new Path();
            }
            if (this.mCenterDrawPath == null) {
                this.mCenterDrawPath = new Path();
            }
            if (this.mInnerClipTempRectForBorderRadius == null) {
                this.mInnerClipTempRectForBorderRadius = new RectF();
            }
            if (this.mOuterClipTempRectForBorderRadius == null) {
                this.mOuterClipTempRectForBorderRadius = new RectF();
            }
            if (this.mTempRectForBorderRadiusOutline == null) {
                this.mTempRectForBorderRadiusOutline = new RectF();
            }
            if (this.mTempRectForCenterDrawPath == null) {
                this.mTempRectForCenterDrawPath = new RectF();
            }
            this.mInnerClipPathForBorderRadius.reset();
            this.mOuterClipPathForBorderRadius.reset();
            this.mPathForBorderRadiusOutline.reset();
            this.mCenterDrawPath.reset();
            this.mInnerClipTempRectForBorderRadius.set(getBounds());
            this.mOuterClipTempRectForBorderRadius.set(getBounds());
            this.mTempRectForBorderRadiusOutline.set(getBounds());
            this.mTempRectForCenterDrawPath.set(getBounds());
            float fullBorderWidth = getFullBorderWidth();
            if (fullBorderWidth > 0.0f) {
                fullBorderWidth *= 0.5f;
                this.mTempRectForCenterDrawPath.inset(fullBorderWidth, fullBorderWidth);
            }
            RectF directionAwareBorderInsets = getDirectionAwareBorderInsets();
            RectF rectF = this.mInnerClipTempRectForBorderRadius;
            rectF.top += directionAwareBorderInsets.top;
            rectF = this.mInnerClipTempRectForBorderRadius;
            rectF.bottom -= directionAwareBorderInsets.bottom;
            rectF = this.mInnerClipTempRectForBorderRadius;
            rectF.left += directionAwareBorderInsets.left;
            rectF = this.mInnerClipTempRectForBorderRadius;
            rectF.right -= directionAwareBorderInsets.right;
            float fullBorderRadius = getFullBorderRadius();
            float borderRadiusOrDefaultTo = getBorderRadiusOrDefaultTo(fullBorderRadius, BorderRadiusLocation.TOP_LEFT);
            float borderRadiusOrDefaultTo2 = getBorderRadiusOrDefaultTo(fullBorderRadius, BorderRadiusLocation.TOP_RIGHT);
            float borderRadiusOrDefaultTo3 = getBorderRadiusOrDefaultTo(fullBorderRadius, BorderRadiusLocation.BOTTOM_LEFT);
            fullBorderRadius = getBorderRadiusOrDefaultTo(fullBorderRadius, BorderRadiusLocation.BOTTOM_RIGHT);
            if (VERSION.SDK_INT >= 17) {
                Object obj = getResolvedLayoutDirection() == 1 ? 1 : null;
                borderRadius = getBorderRadius(BorderRadiusLocation.TOP_START);
                borderRadius2 = getBorderRadius(BorderRadiusLocation.TOP_END);
                borderRadius3 = getBorderRadius(BorderRadiusLocation.BOTTOM_START);
                borderRadius4 = getBorderRadius(BorderRadiusLocation.BOTTOM_END);
                if (I18nUtil.getInstance().doLeftAndRightSwapInRTL(this.mContext)) {
                    if (!YogaConstants.isUndefined(borderRadius)) {
                        borderRadiusOrDefaultTo = borderRadius;
                    }
                    if (!YogaConstants.isUndefined(borderRadius2)) {
                        borderRadiusOrDefaultTo2 = borderRadius2;
                    }
                    if (!YogaConstants.isUndefined(borderRadius3)) {
                        borderRadiusOrDefaultTo3 = borderRadius3;
                    }
                    if (!YogaConstants.isUndefined(borderRadius4)) {
                        fullBorderRadius = borderRadius4;
                    }
                    borderRadius = obj != null ? borderRadiusOrDefaultTo2 : borderRadiusOrDefaultTo;
                    if (obj != null) {
                        borderRadiusOrDefaultTo2 = borderRadiusOrDefaultTo;
                    }
                    borderRadiusOrDefaultTo = obj != null ? fullBorderRadius : borderRadiusOrDefaultTo3;
                    if (obj != null) {
                        fullBorderRadius = borderRadiusOrDefaultTo3;
                    }
                    borderRadiusOrDefaultTo3 = borderRadiusOrDefaultTo;
                    borderRadiusOrDefaultTo = borderRadius;
                } else {
                    f = obj != null ? borderRadius2 : borderRadius;
                    if (obj == null) {
                        borderRadius = borderRadius2;
                    }
                    borderRadius2 = obj != null ? borderRadius4 : borderRadius3;
                    if (obj == null) {
                        borderRadius3 = borderRadius4;
                    }
                    if (!YogaConstants.isUndefined(f)) {
                        borderRadiusOrDefaultTo = f;
                    }
                    if (!YogaConstants.isUndefined(borderRadius)) {
                        borderRadiusOrDefaultTo2 = borderRadius;
                    }
                    if (!YogaConstants.isUndefined(borderRadius2)) {
                        borderRadiusOrDefaultTo3 = borderRadius2;
                    }
                    if (!YogaConstants.isUndefined(borderRadius3)) {
                        fullBorderRadius = borderRadius3;
                    }
                }
            }
            float max = Math.max(borderRadiusOrDefaultTo - directionAwareBorderInsets.left, 0.0f);
            borderRadius = Math.max(borderRadiusOrDefaultTo - directionAwareBorderInsets.top, 0.0f);
            borderRadius2 = Math.max(borderRadiusOrDefaultTo2 - directionAwareBorderInsets.right, 0.0f);
            borderRadius3 = Math.max(borderRadiusOrDefaultTo2 - directionAwareBorderInsets.top, 0.0f);
            borderRadius4 = Math.max(fullBorderRadius - directionAwareBorderInsets.right, 0.0f);
            f = Math.max(fullBorderRadius - directionAwareBorderInsets.bottom, 0.0f);
            float max2 = Math.max(borderRadiusOrDefaultTo3 - directionAwareBorderInsets.left, 0.0f);
            fullBorderWidth = Math.max(borderRadiusOrDefaultTo3 - directionAwareBorderInsets.bottom, 0.0f);
            float f2 = borderRadiusOrDefaultTo3;
            float f3 = fullBorderRadius;
            this.mInnerClipPathForBorderRadius.addRoundRect(this.mInnerClipTempRectForBorderRadius, new float[]{max, borderRadius, borderRadius2, borderRadius3, borderRadius4, f, max2, fullBorderWidth}, Direction.CW);
            this.mOuterClipPathForBorderRadius.addRoundRect(this.mOuterClipTempRectForBorderRadius, new float[]{borderRadiusOrDefaultTo, borderRadiusOrDefaultTo, borderRadiusOrDefaultTo2, borderRadiusOrDefaultTo2, f3, f3, f2, f2}, Direction.CW);
            Spacing spacing = this.mBorderWidth;
            float f4 = spacing != null ? spacing.get(8) / 2.0f : 0.0f;
            Path path = this.mPathForBorderRadiusOutline;
            RectF rectF2 = this.mTempRectForBorderRadiusOutline;
            r3 = new float[8];
            float f5 = borderRadiusOrDefaultTo + f4;
            r3[0] = f5;
            r3[1] = f5;
            f5 = borderRadiusOrDefaultTo2 + f4;
            r3[2] = f5;
            r3[3] = f5;
            f5 = f3 + f4;
            r3[4] = f5;
            r3[5] = f5;
            f5 = f2 + f4;
            r3[6] = f5;
            r3[7] = f5;
            path.addRoundRect(rectF2, r3, Direction.CW);
            Path path2 = this.mCenterDrawPath;
            rectF = this.mTempRectForCenterDrawPath;
            float[] fArr = new float[8];
            int i = (borderRadiusOrDefaultTo > 0.0f ? 1 : (borderRadiusOrDefaultTo == 0.0f ? 0 : -1));
            fArr[0] = max + (i > 0 ? f4 : 0.0f);
            fArr[1] = (i > 0 ? f4 : 0.0f) + borderRadius;
            i = (borderRadiusOrDefaultTo2 > 0.0f ? 1 : (borderRadiusOrDefaultTo2 == 0.0f ? 0 : -1));
            fArr[2] = (i > 0 ? f4 : 0.0f) + borderRadius2;
            fArr[3] = (i > 0 ? f4 : 0.0f) + borderRadius3;
            i = (f3 > 0.0f ? 1 : (f3 == 0.0f ? 0 : -1));
            fArr[4] = (i > 0 ? f4 : 0.0f) + borderRadius4;
            fArr[5] = (i > 0 ? f4 : 0.0f) + f;
            i = (f2 > 0.0f ? 1 : (f2 == 0.0f ? 0 : -1));
            fArr[6] = (i > 0 ? f4 : 0.0f) + max2;
            if (i <= 0) {
                f4 = 0.0f;
            }
            fArr[7] = f4 + fullBorderWidth;
            path2.addRoundRect(rectF, fArr, Direction.CW);
            if (this.mInnerTopLeftCorner == null) {
                this.mInnerTopLeftCorner = new PointF();
            }
            this.mInnerTopLeftCorner.x = this.mInnerClipTempRectForBorderRadius.left;
            this.mInnerTopLeftCorner.y = this.mInnerClipTempRectForBorderRadius.top;
            getEllipseIntersectionWithLine((double) this.mInnerClipTempRectForBorderRadius.left, (double) this.mInnerClipTempRectForBorderRadius.top, (double) (this.mInnerClipTempRectForBorderRadius.left + (max * 2.0f)), (double) (this.mInnerClipTempRectForBorderRadius.top + (borderRadius * 2.0f)), (double) this.mOuterClipTempRectForBorderRadius.left, (double) this.mOuterClipTempRectForBorderRadius.top, (double) this.mInnerClipTempRectForBorderRadius.left, (double) this.mInnerClipTempRectForBorderRadius.top, this.mInnerTopLeftCorner);
            if (this.mInnerBottomLeftCorner == null) {
                this.mInnerBottomLeftCorner = new PointF();
            }
            this.mInnerBottomLeftCorner.x = this.mInnerClipTempRectForBorderRadius.left;
            this.mInnerBottomLeftCorner.y = this.mInnerClipTempRectForBorderRadius.bottom;
            getEllipseIntersectionWithLine((double) this.mInnerClipTempRectForBorderRadius.left, (double) (this.mInnerClipTempRectForBorderRadius.bottom - (fullBorderWidth * 2.0f)), (double) (this.mInnerClipTempRectForBorderRadius.left + (max2 * 2.0f)), (double) this.mInnerClipTempRectForBorderRadius.bottom, (double) this.mOuterClipTempRectForBorderRadius.left, (double) this.mOuterClipTempRectForBorderRadius.bottom, (double) this.mInnerClipTempRectForBorderRadius.left, (double) this.mInnerClipTempRectForBorderRadius.bottom, this.mInnerBottomLeftCorner);
            if (this.mInnerTopRightCorner == null) {
                this.mInnerTopRightCorner = new PointF();
            }
            this.mInnerTopRightCorner.x = this.mInnerClipTempRectForBorderRadius.right;
            this.mInnerTopRightCorner.y = this.mInnerClipTempRectForBorderRadius.top;
            getEllipseIntersectionWithLine((double) (this.mInnerClipTempRectForBorderRadius.right - (borderRadius2 * 2.0f)), (double) this.mInnerClipTempRectForBorderRadius.top, (double) this.mInnerClipTempRectForBorderRadius.right, (double) (this.mInnerClipTempRectForBorderRadius.top + (borderRadius3 * 2.0f)), (double) this.mOuterClipTempRectForBorderRadius.right, (double) this.mOuterClipTempRectForBorderRadius.top, (double) this.mInnerClipTempRectForBorderRadius.right, (double) this.mInnerClipTempRectForBorderRadius.top, this.mInnerTopRightCorner);
            if (this.mInnerBottomRightCorner == null) {
                this.mInnerBottomRightCorner = new PointF();
            }
            this.mInnerBottomRightCorner.x = this.mInnerClipTempRectForBorderRadius.right;
            this.mInnerBottomRightCorner.y = this.mInnerClipTempRectForBorderRadius.bottom;
            getEllipseIntersectionWithLine((double) (this.mInnerClipTempRectForBorderRadius.right - (borderRadius4 * 2.0f)), (double) (this.mInnerClipTempRectForBorderRadius.bottom - (f * 2.0f)), (double) this.mInnerClipTempRectForBorderRadius.right, (double) this.mInnerClipTempRectForBorderRadius.bottom, (double) this.mOuterClipTempRectForBorderRadius.right, (double) this.mOuterClipTempRectForBorderRadius.bottom, (double) this.mInnerClipTempRectForBorderRadius.right, (double) this.mInnerClipTempRectForBorderRadius.bottom, this.mInnerBottomRightCorner);
        }
    }

    private static void getEllipseIntersectionWithLine(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, PointF pointF) {
        PointF pointF2 = pointF;
        double d9 = (d + d3) / 2.0d;
        double d10 = (d2 + d4) / 2.0d;
        double d11 = d5 - d9;
        double d12 = d6 - d10;
        double d13 = d8 - d10;
        double abs = Math.abs(d3 - d) / 2.0d;
        double abs2 = Math.abs(d4 - d2) / 2.0d;
        d13 = (d13 - d12) / ((d7 - d9) - d11);
        d12 -= d11 * d13;
        abs2 *= abs2;
        d11 = abs * abs;
        double d14 = abs2 + ((d11 * d13) * d13);
        double d15 = (((abs * 2.0d) * abs) * d12) * d13;
        d11 = (-(d11 * ((d12 * d12) - abs2))) / d14;
        d14 *= 2.0d;
        double sqrt = ((-d15) / d14) - Math.sqrt(d11 + Math.pow(d15 / d14, 2.0d));
        sqrt += d9;
        d13 = ((d13 * sqrt) + d12) + d10;
        if (!Double.isNaN(sqrt) && !Double.isNaN(d13)) {
            PointF pointF3 = pointF;
            pointF3.x = (float) sqrt;
            pointF3.y = (float) d13;
        }
    }

    public float getBorderWidthOrDefaultTo(float f, int i) {
        Spacing spacing = this.mBorderWidth;
        if (spacing == null) {
            return f;
        }
        float raw = spacing.getRaw(i);
        return YogaConstants.isUndefined(raw) ? f : raw;
    }

    private void updatePathEffect() {
        BorderStyle borderStyle = this.mBorderStyle;
        this.mPathEffectForBorderStyle = borderStyle != null ? BorderStyle.getPathEffect(borderStyle, getFullBorderWidth()) : null;
        this.mPaint.setPathEffect(this.mPathEffectForBorderStyle);
    }

    public float getFullBorderWidth() {
        Spacing spacing = this.mBorderWidth;
        return (spacing == null || YogaConstants.isUndefined(spacing.getRaw(8))) ? 0.0f : this.mBorderWidth.getRaw(8);
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x013a  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00e6  */
    private void drawRectangularBackgroundWithBorders(android.graphics.Canvas r25) {
        /*
        r24 = this;
        r11 = r24;
        r0 = r11.mColor;
        r1 = r11.mAlpha;
        r0 = com.facebook.react.views.view.ColorUtil.multiplyColorAlpha(r0, r1);
        r1 = android.graphics.Color.alpha(r0);
        if (r1 == 0) goto L_0x0028;
    L_0x0010:
        r1 = r11.mPaint;
        r1.setColor(r0);
        r0 = r11.mPaint;
        r1 = android.graphics.Paint.Style.FILL;
        r0.setStyle(r1);
        r0 = r24.getBounds();
        r1 = r11.mPaint;
        r12 = r25;
        r12.drawRect(r0, r1);
        goto L_0x002a;
    L_0x0028:
        r12 = r25;
    L_0x002a:
        r0 = r24.getDirectionAwareBorderInsets();
        r1 = r0.left;
        r13 = java.lang.Math.round(r1);
        r1 = r0.top;
        r14 = java.lang.Math.round(r1);
        r1 = r0.right;
        r15 = java.lang.Math.round(r1);
        r0 = r0.bottom;
        r16 = java.lang.Math.round(r0);
        if (r13 > 0) goto L_0x004e;
    L_0x0048:
        if (r15 > 0) goto L_0x004e;
    L_0x004a:
        if (r14 > 0) goto L_0x004e;
    L_0x004c:
        if (r16 <= 0) goto L_0x0137;
    L_0x004e:
        r0 = r24.getBounds();
        r1 = 0;
        r2 = r11.getBorderColor(r1);
        r10 = 1;
        r17 = r11.getBorderColor(r10);
        r3 = 2;
        r3 = r11.getBorderColor(r3);
        r4 = 3;
        r18 = r11.getBorderColor(r4);
        r4 = android.os.Build.VERSION.SDK_INT;
        r5 = 17;
        if (r4 < r5) goto L_0x00cb;
    L_0x006c:
        r4 = r24.getResolvedLayoutDirection();
        if (r4 != r10) goto L_0x0074;
    L_0x0072:
        r4 = 1;
        goto L_0x0075;
    L_0x0074:
        r4 = 0;
    L_0x0075:
        r5 = 4;
        r6 = r11.getBorderColor(r5);
        r7 = 5;
        r8 = r11.getBorderColor(r7);
        r9 = com.facebook.react.modules.i18nmanager.I18nUtil.getInstance();
        r10 = r11.mContext;
        r9 = r9.doLeftAndRightSwapInRTL(r10);
        if (r9 == 0) goto L_0x00a8;
    L_0x008b:
        r5 = r11.isBorderColorDefined(r5);
        if (r5 != 0) goto L_0x0092;
    L_0x0091:
        goto L_0x0093;
    L_0x0092:
        r2 = r6;
    L_0x0093:
        r5 = r11.isBorderColorDefined(r7);
        if (r5 != 0) goto L_0x009a;
    L_0x0099:
        goto L_0x009b;
    L_0x009a:
        r3 = r8;
    L_0x009b:
        if (r4 == 0) goto L_0x009f;
    L_0x009d:
        r5 = r3;
        goto L_0x00a0;
    L_0x009f:
        r5 = r2;
    L_0x00a0:
        if (r4 == 0) goto L_0x00a3;
    L_0x00a2:
        goto L_0x00a4;
    L_0x00a3:
        r2 = r3;
    L_0x00a4:
        r19 = r2;
        r10 = r5;
        goto L_0x00ce;
    L_0x00a8:
        if (r4 == 0) goto L_0x00ac;
    L_0x00aa:
        r9 = r8;
        goto L_0x00ad;
    L_0x00ac:
        r9 = r6;
    L_0x00ad:
        if (r4 == 0) goto L_0x00b0;
    L_0x00af:
        goto L_0x00b1;
    L_0x00b0:
        r6 = r8;
    L_0x00b1:
        r5 = r11.isBorderColorDefined(r5);
        r7 = r11.isBorderColorDefined(r7);
        if (r4 == 0) goto L_0x00bd;
    L_0x00bb:
        r8 = r7;
        goto L_0x00be;
    L_0x00bd:
        r8 = r5;
    L_0x00be:
        if (r4 == 0) goto L_0x00c1;
    L_0x00c0:
        goto L_0x00c2;
    L_0x00c1:
        r5 = r7;
    L_0x00c2:
        if (r8 == 0) goto L_0x00c5;
    L_0x00c4:
        r2 = r9;
    L_0x00c5:
        if (r5 == 0) goto L_0x00cb;
    L_0x00c7:
        r10 = r2;
        r19 = r6;
        goto L_0x00ce;
    L_0x00cb:
        r10 = r2;
        r19 = r3;
    L_0x00ce:
        r9 = r0.left;
        r8 = r0.top;
        r2 = r13;
        r3 = r14;
        r4 = r15;
        r5 = r16;
        r6 = r10;
        r7 = r17;
        r1 = r8;
        r8 = r19;
        r12 = r9;
        r9 = r18;
        r2 = fastBorderCompatibleColorOrZero(r2, r3, r4, r5, r6, r7, r8, r9);
        if (r2 == 0) goto L_0x013a;
    L_0x00e6:
        r3 = android.graphics.Color.alpha(r2);
        if (r3 == 0) goto L_0x0137;
    L_0x00ec:
        r8 = r0.right;
        r0 = r0.bottom;
        r3 = r11.mPaint;
        r3.setColor(r2);
        if (r13 <= 0) goto L_0x0106;
    L_0x00f7:
        r9 = r12 + r13;
        r3 = (float) r12;
        r4 = (float) r1;
        r5 = (float) r9;
        r2 = r0 - r16;
        r6 = (float) r2;
        r7 = r11.mPaint;
        r2 = r25;
        r2.drawRect(r3, r4, r5, r6, r7);
    L_0x0106:
        if (r14 <= 0) goto L_0x0117;
    L_0x0108:
        r2 = r1 + r14;
        r9 = r12 + r13;
        r3 = (float) r9;
        r4 = (float) r1;
        r5 = (float) r8;
        r6 = (float) r2;
        r7 = r11.mPaint;
        r2 = r25;
        r2.drawRect(r3, r4, r5, r6, r7);
    L_0x0117:
        if (r15 <= 0) goto L_0x0127;
    L_0x0119:
        r2 = r8 - r15;
        r3 = (float) r2;
        r1 = r1 + r14;
        r4 = (float) r1;
        r5 = (float) r8;
        r6 = (float) r0;
        r7 = r11.mPaint;
        r2 = r25;
        r2.drawRect(r3, r4, r5, r6, r7);
    L_0x0127:
        if (r16 <= 0) goto L_0x0137;
    L_0x0129:
        r1 = r0 - r16;
        r3 = (float) r12;
        r4 = (float) r1;
        r8 = r8 - r15;
        r5 = (float) r8;
        r6 = (float) r0;
        r7 = r11.mPaint;
        r2 = r25;
        r2.drawRect(r3, r4, r5, r6, r7);
    L_0x0137:
        r0 = r11;
        goto L_0x01cb;
    L_0x013a:
        r2 = r11.mPaint;
        r3 = 0;
        r2.setAntiAlias(r3);
        r20 = r0.width();
        r21 = r0.height();
        if (r13 <= 0) goto L_0x016e;
    L_0x014a:
        r9 = (float) r12;
        r4 = (float) r1;
        r0 = r12 + r13;
        r7 = (float) r0;
        r8 = r1 + r14;
        r6 = (float) r8;
        r8 = r1 + r21;
        r0 = r8 - r16;
        r5 = (float) r0;
        r8 = (float) r8;
        r0 = r24;
        r3 = r1;
        r1 = r25;
        r2 = r10;
        r10 = r3;
        r3 = r9;
        r22 = r5;
        r5 = r7;
        r23 = r8;
        r8 = r22;
        r11 = r10;
        r10 = r23;
        r0.drawQuadrilateral(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
        goto L_0x016f;
    L_0x016e:
        r11 = r1;
    L_0x016f:
        if (r14 <= 0) goto L_0x018a;
    L_0x0171:
        r3 = (float) r12;
        r10 = (float) r11;
        r9 = r12 + r13;
        r5 = (float) r9;
        r8 = r11 + r14;
        r8 = (float) r8;
        r9 = r12 + r20;
        r0 = r9 - r15;
        r7 = (float) r0;
        r9 = (float) r9;
        r0 = r24;
        r1 = r25;
        r2 = r17;
        r4 = r10;
        r6 = r8;
        r0.drawQuadrilateral(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
    L_0x018a:
        if (r15 <= 0) goto L_0x01a6;
    L_0x018c:
        r9 = r12 + r20;
        r5 = (float) r9;
        r4 = (float) r11;
        r8 = r11 + r21;
        r6 = (float) r8;
        r9 = r9 - r15;
        r9 = (float) r9;
        r8 = r8 - r16;
        r8 = (float) r8;
        r0 = r11 + r14;
        r10 = (float) r0;
        r0 = r24;
        r1 = r25;
        r2 = r19;
        r3 = r5;
        r7 = r9;
        r0.drawQuadrilateral(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
    L_0x01a6:
        if (r16 <= 0) goto L_0x01c3;
    L_0x01a8:
        r3 = (float) r12;
        r8 = r11 + r21;
        r6 = (float) r8;
        r9 = r12 + r20;
        r5 = (float) r9;
        r9 = r9 - r15;
        r7 = (float) r9;
        r8 = r8 - r16;
        r10 = (float) r8;
        r9 = r12 + r13;
        r9 = (float) r9;
        r0 = r24;
        r1 = r25;
        r2 = r18;
        r4 = r6;
        r8 = r10;
        r0.drawQuadrilateral(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
        goto L_0x01c5;
    L_0x01c3:
        r0 = r24;
    L_0x01c5:
        r1 = r0.mPaint;
        r2 = 1;
        r1.setAntiAlias(r2);
    L_0x01cb:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.view.ReactViewBackgroundDrawable.drawRectangularBackgroundWithBorders(android.graphics.Canvas):void");
    }

    private void drawQuadrilateral(Canvas canvas, int i, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        if (i != 0) {
            if (this.mPathForBorder == null) {
                this.mPathForBorder = new Path();
            }
            this.mPaint.setColor(i);
            this.mPathForBorder.reset();
            this.mPathForBorder.moveTo(f, f2);
            this.mPathForBorder.lineTo(f3, f4);
            this.mPathForBorder.lineTo(f5, f6);
            this.mPathForBorder.lineTo(f7, f8);
            this.mPathForBorder.lineTo(f, f2);
            canvas.drawPath(this.mPathForBorder, this.mPaint);
        }
    }

    private int getBorderWidth(int i) {
        Spacing spacing = this.mBorderWidth;
        if (spacing == null) {
            return 0;
        }
        float f = spacing.get(i);
        return YogaConstants.isUndefined(f) ? -1 : Math.round(f);
    }

    private boolean isBorderColorDefined(int i) {
        Spacing spacing = this.mBorderRGB;
        float f = Float.NaN;
        float f2 = spacing != null ? spacing.get(i) : Float.NaN;
        Spacing spacing2 = this.mBorderAlpha;
        if (spacing2 != null) {
            f = spacing2.get(i);
        }
        return (YogaConstants.isUndefined(f2) || YogaConstants.isUndefined(f)) ? false : true;
    }

    private int getBorderColor(int i) {
        Spacing spacing = this.mBorderRGB;
        float f = spacing != null ? spacing.get(i) : 0.0f;
        Spacing spacing2 = this.mBorderAlpha;
        return colorFromAlphaAndRGBComponents(spacing2 != null ? spacing2.get(i) : 255.0f, f);
    }

    /* JADX WARNING: Missing block: B:18:0x005b, code:
            if (r1 != 0) goto L_0x0074;
     */
    /* JADX WARNING: Missing block: B:28:0x0072, code:
            if (com.facebook.yoga.YogaConstants.isUndefined(r4) == false) goto L_0x0074;
     */
    public android.graphics.RectF getDirectionAwareBorderInsets() {
        /*
        r9 = this;
        r0 = 0;
        r1 = 8;
        r0 = r9.getBorderWidthOrDefaultTo(r0, r1);
        r1 = 1;
        r2 = r9.getBorderWidthOrDefaultTo(r0, r1);
        r3 = 3;
        r3 = r9.getBorderWidthOrDefaultTo(r0, r3);
        r4 = 0;
        r5 = r9.getBorderWidthOrDefaultTo(r0, r4);
        r6 = 2;
        r0 = r9.getBorderWidthOrDefaultTo(r0, r6);
        r6 = android.os.Build.VERSION.SDK_INT;
        r7 = 17;
        if (r6 < r7) goto L_0x0075;
    L_0x0021:
        r6 = r9.mBorderWidth;
        if (r6 == 0) goto L_0x0075;
    L_0x0025:
        r6 = r9.getResolvedLayoutDirection();
        if (r6 != r1) goto L_0x002c;
    L_0x002b:
        goto L_0x002d;
    L_0x002c:
        r1 = 0;
    L_0x002d:
        r4 = r9.mBorderWidth;
        r6 = 4;
        r4 = r4.getRaw(r6);
        r6 = r9.mBorderWidth;
        r7 = 5;
        r6 = r6.getRaw(r7);
        r7 = com.facebook.react.modules.i18nmanager.I18nUtil.getInstance();
        r8 = r9.mContext;
        r7 = r7.doLeftAndRightSwapInRTL(r8);
        if (r7 == 0) goto L_0x005e;
    L_0x0047:
        r7 = com.facebook.yoga.YogaConstants.isUndefined(r4);
        if (r7 == 0) goto L_0x004e;
    L_0x004d:
        r4 = r5;
    L_0x004e:
        r5 = com.facebook.yoga.YogaConstants.isUndefined(r6);
        if (r5 == 0) goto L_0x0055;
    L_0x0054:
        goto L_0x0056;
    L_0x0055:
        r0 = r6;
    L_0x0056:
        if (r1 == 0) goto L_0x005a;
    L_0x0058:
        r5 = r0;
        goto L_0x005b;
    L_0x005a:
        r5 = r4;
    L_0x005b:
        if (r1 == 0) goto L_0x0075;
    L_0x005d:
        goto L_0x0074;
    L_0x005e:
        if (r1 == 0) goto L_0x0062;
    L_0x0060:
        r7 = r6;
        goto L_0x0063;
    L_0x0062:
        r7 = r4;
    L_0x0063:
        if (r1 == 0) goto L_0x0066;
    L_0x0065:
        goto L_0x0067;
    L_0x0066:
        r4 = r6;
    L_0x0067:
        r1 = com.facebook.yoga.YogaConstants.isUndefined(r7);
        if (r1 != 0) goto L_0x006e;
    L_0x006d:
        r5 = r7;
    L_0x006e:
        r1 = com.facebook.yoga.YogaConstants.isUndefined(r4);
        if (r1 != 0) goto L_0x0075;
    L_0x0074:
        r0 = r4;
    L_0x0075:
        r1 = new android.graphics.RectF;
        r1.<init>(r5, r2, r0, r3);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.view.ReactViewBackgroundDrawable.getDirectionAwareBorderInsets():android.graphics.RectF");
    }
}
