package com.horcrux.svg;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Region.Op;
import android.view.View;
import android.view.ViewParent;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.OnLayoutEvent;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactViewGroup;
import com.horcrux.svg.SVGLength.UnitType;
import java.util.ArrayList;
import javax.annotation.Nullable;

@SuppressLint({"ViewConstructor"})
public abstract class VirtualView extends ReactViewGroup {
    private static final int CLIP_RULE_EVENODD = 0;
    static final int CLIP_RULE_NONZERO = 1;
    static final float MIN_OPACITY_FOR_DRAW = 0.01f;
    private static final double M_SQRT1_2l = 0.7071067811865476d;
    private static final float[] sRawMatrix = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private double canvasDiagonal = -1.0d;
    private float canvasHeight = -1.0f;
    private float canvasWidth = -1.0f;
    ArrayList<PathElement> elements;
    private double fontSize = -1.0d;
    private GlyphContext glyphContext;
    RectF mBox;
    Matrix mCTM = new Matrix();
    boolean mCTMInvertible = true;
    private Path mCachedClipPath;
    private RectF mClientRect;
    @Nullable
    private String mClipPath;
    Region mClipRegion;
    Path mClipRegionPath;
    int mClipRule;
    final ReactContext mContext;
    Path mFillPath;
    Matrix mInvCTM = new Matrix();
    Matrix mInvMatrix = new Matrix();
    final Matrix mInvTransform = new Matrix();
    boolean mInvertible = true;
    @Nullable
    String mMarkerEnd;
    @Nullable
    String mMarkerMid;
    Path mMarkerPath;
    Region mMarkerRegion;
    @Nullable
    String mMarkerStart;
    @Nullable
    String mMask;
    Matrix mMatrix = new Matrix();
    String mName;
    private boolean mOnLayout;
    float mOpacity = 1.0f;
    Path mPath;
    Region mRegion;
    private boolean mResponsible;
    final float mScale;
    Path mStrokePath;
    Region mStrokeRegion;
    private GroupView mTextRoot;
    Matrix mTransform = new Matrix();
    boolean mTransformInvertible = true;
    private SvgView svgView;

    abstract void draw(Canvas canvas, Paint paint, float f);

    abstract Path getPath(Canvas canvas, Paint paint);

    abstract int hitTest(float[] fArr);

    VirtualView(ReactContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
        this.mScale = DisplayMetricsHolder.getScreenDisplayMetrics().density;
    }

    public void invalidate() {
        if (!(this instanceof RenderableView) || this.mPath != null) {
            clearCache();
            clearParentCache();
            super.invalidate();
        }
    }

    void clearCache() {
        this.canvasDiagonal = -1.0d;
        this.canvasHeight = -1.0f;
        this.canvasWidth = -1.0f;
        this.fontSize = -1.0d;
        this.mStrokeRegion = null;
        this.mMarkerRegion = null;
        this.mRegion = null;
        this.mPath = null;
    }

    void clearChildCache() {
        clearCache();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof VirtualView) {
                ((VirtualView) childAt).clearChildCache();
            }
        }
    }

    private void clearParentCache() {
        VirtualView virtualView = this;
        while (true) {
            ViewParent parent = virtualView.getParent();
            if (parent instanceof VirtualView) {
                virtualView = (VirtualView) parent;
                if (virtualView.mPath != null) {
                    virtualView.clearCache();
                } else {
                    return;
                }
            }
            return;
        }
    }

    @Nullable
    GroupView getTextRoot() {
        if (this.mTextRoot == null) {
            VirtualView virtualView = this;
            while (virtualView != null) {
                if (virtualView instanceof GroupView) {
                    GroupView groupView = (GroupView) virtualView;
                    if (groupView.getGlyphContext() != null) {
                        this.mTextRoot = groupView;
                        break;
                    }
                }
                ViewParent parent = virtualView.getParent();
                if (parent instanceof VirtualView) {
                    virtualView = (VirtualView) parent;
                } else {
                    virtualView = null;
                }
            }
        }
        return this.mTextRoot;
    }

    @Nullable
    GroupView getParentTextRoot() {
        ViewParent parent = getParent();
        if (parent instanceof VirtualView) {
            return ((VirtualView) parent).getTextRoot();
        }
        return null;
    }

    private double getFontSizeFromContext() {
        double d = this.fontSize;
        if (d != -1.0d) {
            return d;
        }
        GroupView textRoot = getTextRoot();
        if (textRoot == null) {
            return 12.0d;
        }
        if (this.glyphContext == null) {
            this.glyphContext = textRoot.getGlyphContext();
        }
        this.fontSize = this.glyphContext.getFontSize();
        return this.fontSize;
    }

    void render(Canvas canvas, Paint paint, float f) {
        draw(canvas, paint, f);
    }

    int saveAndSetupCanvas(Canvas canvas, Matrix matrix) {
        int save = canvas.save();
        this.mCTM.setConcat(this.mMatrix, this.mTransform);
        canvas.concat(this.mCTM);
        this.mCTM.preConcat(matrix);
        this.mCTMInvertible = this.mCTM.invert(this.mInvCTM);
        return save;
    }

    void restoreCanvas(Canvas canvas, int i) {
        canvas.restoreToCount(i);
    }

    @ReactProp(name = "name")
    public void setName(String str) {
        this.mName = str;
        invalidate();
    }

    @ReactProp(name = "onLayout")
    public void setOnLayout(boolean z) {
        this.mOnLayout = z;
        invalidate();
    }

    @ReactProp(name = "mask")
    public void setMask(String str) {
        this.mMask = str;
        invalidate();
    }

    @ReactProp(name = "markerStart")
    public void setMarkerStart(String str) {
        this.mMarkerStart = str;
        invalidate();
    }

    @ReactProp(name = "markerMid")
    public void setMarkerMid(String str) {
        this.mMarkerMid = str;
        invalidate();
    }

    @ReactProp(name = "markerEnd")
    public void setMarkerEnd(String str) {
        this.mMarkerEnd = str;
        invalidate();
    }

    @ReactProp(name = "clipPath")
    public void setClipPath(String str) {
        this.mCachedClipPath = null;
        this.mClipPath = str;
        invalidate();
    }

    @ReactProp(defaultInt = 1, name = "clipRule")
    public void setClipRule(int i) {
        this.mClipRule = i;
        invalidate();
    }

    @ReactProp(defaultFloat = 1.0f, name = "opacity")
    public void setOpacity(float f) {
        this.mOpacity = f;
        invalidate();
    }

    @ReactProp(name = "matrix")
    public void setMatrix(Dynamic dynamic) {
        ReadableType type = dynamic.getType();
        if (dynamic.isNull() || !type.equals(ReadableType.Array)) {
            this.mMatrix = null;
            this.mInvMatrix = null;
            this.mInvertible = false;
        } else {
            int toMatrixData = PropHelper.toMatrixData(dynamic.asArray(), sRawMatrix, this.mScale);
            if (toMatrixData == 6) {
                if (this.mMatrix == null) {
                    this.mMatrix = new Matrix();
                    this.mInvMatrix = new Matrix();
                }
                this.mMatrix.setValues(sRawMatrix);
                this.mInvertible = this.mMatrix.invert(this.mInvMatrix);
            } else if (toMatrixData != -1) {
                FLog.w(ReactConstants.TAG, "RNSVG: Transform matrices must be of size 6");
            }
        }
        super.invalidate();
        clearParentCache();
    }

    @ReactProp(name = "responsible")
    public void setResponsible(boolean z) {
        this.mResponsible = z;
        invalidate();
    }

    @Nullable
    Path getClipPath() {
        return this.mCachedClipPath;
    }

    @Nullable
    Path getClipPath(Canvas canvas, Paint paint) {
        if (this.mClipPath != null) {
            ClipPathView clipPathView = (ClipPathView) getSvgView().getDefinedClipPath(this.mClipPath);
            String str = ReactConstants.TAG;
            if (clipPathView != null) {
                Path path;
                if (clipPathView.mClipRule == 0) {
                    path = clipPathView.getPath(canvas, paint);
                } else {
                    path = clipPathView.getPath(canvas, paint, Op.UNION);
                }
                int i = clipPathView.mClipRule;
                if (i == 0) {
                    path.setFillType(FillType.EVEN_ODD);
                } else if (i != 1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("RNSVG: clipRule: ");
                    stringBuilder.append(this.mClipRule);
                    stringBuilder.append(" unrecognized");
                    FLog.w(str, stringBuilder.toString());
                }
                this.mCachedClipPath = path;
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("RNSVG: Undefined clipPath: ");
                stringBuilder2.append(this.mClipPath);
                FLog.w(str, stringBuilder2.toString());
            }
        }
        return getClipPath();
    }

    void clip(Canvas canvas, Paint paint) {
        Path clipPath = getClipPath(canvas, paint);
        if (clipPath != null) {
            canvas.clipPath(clipPath);
        }
    }

    boolean isResponsible() {
        return this.mResponsible;
    }

    SvgView getSvgView() {
        SvgView svgView = this.svgView;
        if (svgView != null) {
            return svgView;
        }
        ViewParent parent = getParent();
        if (parent == null) {
            return null;
        }
        if (parent instanceof SvgView) {
            this.svgView = (SvgView) parent;
        } else if (parent instanceof VirtualView) {
            this.svgView = ((VirtualView) parent).getSvgView();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RNSVG: ");
            stringBuilder.append(getClass().getName());
            stringBuilder.append(" should be descendant of a SvgView.");
            FLog.e(ReactConstants.TAG, stringBuilder.toString());
        }
        return this.svgView;
    }

    double relativeOnWidth(SVGLength sVGLength) {
        double d;
        float f;
        UnitType unitType = sVGLength.unit;
        if (unitType == UnitType.NUMBER) {
            d = sVGLength.value;
            f = this.mScale;
        } else if (unitType != UnitType.PERCENTAGE) {
            return fromRelativeFast(sVGLength);
        } else {
            d = sVGLength.value / 100.0d;
            f = getCanvasWidth();
        }
        return d * ((double) f);
    }

    double relativeOnHeight(SVGLength sVGLength) {
        double d;
        float f;
        UnitType unitType = sVGLength.unit;
        if (unitType == UnitType.NUMBER) {
            d = sVGLength.value;
            f = this.mScale;
        } else if (unitType != UnitType.PERCENTAGE) {
            return fromRelativeFast(sVGLength);
        } else {
            d = sVGLength.value / 100.0d;
            f = getCanvasHeight();
        }
        return d * ((double) f);
    }

    double relativeOnOther(SVGLength sVGLength) {
        double d;
        double d2;
        UnitType unitType = sVGLength.unit;
        if (unitType == UnitType.NUMBER) {
            d = sVGLength.value;
            d2 = (double) this.mScale;
        } else if (unitType != UnitType.PERCENTAGE) {
            return fromRelativeFast(sVGLength);
        } else {
            d = sVGLength.value / 100.0d;
            d2 = getCanvasDiagonal();
        }
        return d * d2;
    }

    private double fromRelativeFast(SVGLength sVGLength) {
        double fontSizeFromContext;
        switch (sVGLength.unit) {
            case EMS:
                fontSizeFromContext = getFontSizeFromContext();
                break;
            case EXS:
                fontSizeFromContext = getFontSizeFromContext() / 2.0d;
                break;
            case CM:
                fontSizeFromContext = 35.43307d;
                break;
            case MM:
                fontSizeFromContext = 3.543307d;
                break;
            case IN:
                fontSizeFromContext = 90.0d;
                break;
            case PT:
                fontSizeFromContext = 1.25d;
                break;
            case PC:
                fontSizeFromContext = 15.0d;
                break;
            default:
                fontSizeFromContext = 1.0d;
                break;
        }
        return (sVGLength.value * fontSizeFromContext) * ((double) this.mScale);
    }

    private float getCanvasWidth() {
        float f = this.canvasWidth;
        if (f != -1.0f) {
            return f;
        }
        GroupView textRoot = getTextRoot();
        if (textRoot == null) {
            this.canvasWidth = (float) getSvgView().getCanvasBounds().width();
        } else {
            this.canvasWidth = textRoot.getGlyphContext().getWidth();
        }
        return this.canvasWidth;
    }

    private float getCanvasHeight() {
        float f = this.canvasHeight;
        if (f != -1.0f) {
            return f;
        }
        GroupView textRoot = getTextRoot();
        if (textRoot == null) {
            this.canvasHeight = (float) getSvgView().getCanvasBounds().height();
        } else {
            this.canvasHeight = textRoot.getGlyphContext().getHeight();
        }
        return this.canvasHeight;
    }

    private double getCanvasDiagonal() {
        double d = this.canvasDiagonal;
        if (d != -1.0d) {
            return d;
        }
        this.canvasDiagonal = Math.sqrt(Math.pow((double) getCanvasWidth(), 2.0d) + Math.pow((double) getCanvasHeight(), 2.0d)) * M_SQRT1_2l;
        return this.canvasDiagonal;
    }

    void saveDefinition() {
        if (this.mName != null) {
            getSvgView().defineTemplate(this, this.mName);
        }
    }

    protected void onMeasure(int i, int i2) {
        RectF rectF = this.mClientRect;
        if (rectF != null) {
            i = (int) Math.ceil((double) rectF.width());
        } else {
            i = getDefaultSize(getSuggestedMinimumWidth(), i);
        }
        rectF = this.mClientRect;
        if (rectF != null) {
            i2 = (int) Math.ceil((double) rectF.height());
        } else {
            i2 = getDefaultSize(getSuggestedMinimumHeight(), i2);
        }
        setMeasuredDimension(i, i2);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        RectF rectF = this.mClientRect;
        if (rectF != null) {
            if (!(this instanceof GroupView)) {
                i = (int) Math.floor((double) this.mClientRect.top);
                i2 = (int) Math.ceil((double) this.mClientRect.right);
                i3 = (int) Math.ceil((double) this.mClientRect.bottom);
                setLeft((int) Math.floor((double) rectF.left));
                setTop(i);
                setRight(i2);
                setBottom(i3);
            }
            setMeasuredDimension((int) Math.ceil((double) this.mClientRect.width()), (int) Math.ceil((double) this.mClientRect.height()));
        }
    }

    void setClientRect(RectF rectF) {
        RectF rectF2 = this.mClientRect;
        if (rectF2 == null || !rectF2.equals(rectF)) {
            this.mClientRect = rectF;
            if (this.mClientRect != null && (this.mResponsible || this.mOnLayout)) {
                int floor = (int) Math.floor((double) this.mClientRect.left);
                int floor2 = (int) Math.floor((double) this.mClientRect.top);
                int ceil = (int) Math.ceil((double) this.mClientRect.width());
                int ceil2 = (int) Math.ceil((double) this.mClientRect.height());
                if (this.mResponsible) {
                    int ceil3 = (int) Math.ceil((double) this.mClientRect.right);
                    int ceil4 = (int) Math.ceil((double) this.mClientRect.bottom);
                    if (!(this instanceof GroupView)) {
                        setLeft(floor);
                        setTop(floor2);
                        setRight(ceil3);
                        setBottom(ceil4);
                    }
                    setMeasuredDimension(ceil, ceil2);
                }
                if (this.mOnLayout) {
                    ((UIManagerModule) this.mContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(OnLayoutEvent.obtain(getId(), floor, floor2, ceil, ceil2));
                }
            }
        }
    }

    RectF getClientRect() {
        return this.mClientRect;
    }
}
