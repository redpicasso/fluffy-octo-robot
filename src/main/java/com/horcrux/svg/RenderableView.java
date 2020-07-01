package com.horcrux.svg;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import androidx.core.view.ViewCompat;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public abstract class RenderableView extends VirtualView {
    private static final int CAP_BUTT = 0;
    static final int CAP_ROUND = 1;
    private static final int CAP_SQUARE = 2;
    private static final int FILL_RULE_EVENODD = 0;
    static final int FILL_RULE_NONZERO = 1;
    private static final int JOIN_BEVEL = 2;
    private static final int JOIN_MITER = 0;
    static final int JOIN_ROUND = 1;
    private static final int VECTOR_EFFECT_DEFAULT = 0;
    private static final int VECTOR_EFFECT_NON_SCALING_STROKE = 1;
    static RenderableView contextElement;
    private static final Pattern regex = Pattern.compile("[0-9.-]+");
    @Nullable
    public ReadableArray fill;
    public float fillOpacity = 1.0f;
    public FillType fillRule = FillType.WINDING;
    @Nullable
    private ArrayList<String> mAttributeList;
    @Nullable
    private ArrayList<String> mLastMergedList;
    @Nullable
    private ArrayList<Object> mOriginProperties;
    @Nullable
    private ArrayList<String> mPropList;
    @Nullable
    public ReadableArray stroke;
    @Nullable
    public SVGLength[] strokeDasharray;
    public float strokeDashoffset = 0.0f;
    public Cap strokeLinecap = Cap.ROUND;
    public Join strokeLinejoin = Join.ROUND;
    public float strokeMiterlimit = 4.0f;
    public float strokeOpacity = 1.0f;
    public SVGLength strokeWidth = new SVGLength(1.0d);
    public int vectorEffect = 0;

    /* renamed from: com.horcrux.svg.RenderableView$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$horcrux$svg$RNSVGMarkerType = new int[RNSVGMarkerType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$horcrux$svg$RNSVGMarkerType[com.horcrux.svg.RNSVGMarkerType.kEndMarker.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.horcrux.svg.RNSVGMarkerType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$horcrux$svg$RNSVGMarkerType = r0;
            r0 = $SwitchMap$com$horcrux$svg$RNSVGMarkerType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.horcrux.svg.RNSVGMarkerType.kStartMarker;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$horcrux$svg$RNSVGMarkerType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.horcrux.svg.RNSVGMarkerType.kMidMarker;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$horcrux$svg$RNSVGMarkerType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.horcrux.svg.RNSVGMarkerType.kEndMarker;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.RenderableView.1.<clinit>():void");
        }
    }

    private static double saturate(double d) {
        return d <= 0.0d ? 0.0d : d >= 1.0d ? 1.0d : d;
    }

    abstract Path getPath(Canvas canvas, Paint paint);

    RenderableView(ReactContext reactContext) {
        super(reactContext);
    }

    @ReactProp(name = "vectorEffect")
    public void setVectorEffect(int i) {
        this.vectorEffect = i;
        invalidate();
    }

    @ReactProp(name = "fill")
    public void setFill(@Nullable Dynamic dynamic) {
        if (dynamic == null || dynamic.isNull()) {
            this.fill = null;
            invalidate();
            return;
        }
        if (dynamic.getType().equals(ReadableType.Array)) {
            this.fill = dynamic.asArray();
        } else {
            ReadableArray javaOnlyArray = new JavaOnlyArray();
            int i = 0;
            javaOnlyArray.pushInt(0);
            Matcher matcher = regex.matcher(dynamic.asString());
            while (matcher.find()) {
                double parseDouble = Double.parseDouble(matcher.group());
                int i2 = i + 1;
                if (i < 3) {
                    parseDouble /= 255.0d;
                }
                javaOnlyArray.pushDouble(parseDouble);
                i = i2;
            }
            this.fill = javaOnlyArray;
        }
        invalidate();
    }

    @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
    public void setFillOpacity(float f) {
        this.fillOpacity = f;
        invalidate();
    }

    @ReactProp(defaultInt = 1, name = "fillRule")
    public void setFillRule(int i) {
        if (i == 0) {
            this.fillRule = FillType.EVEN_ODD;
        } else if (i != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fillRule ");
            stringBuilder.append(i);
            stringBuilder.append(" unrecognized");
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        }
        invalidate();
    }

    @ReactProp(name = "stroke")
    public void setStroke(@Nullable Dynamic dynamic) {
        if (dynamic == null || dynamic.isNull()) {
            this.stroke = null;
            invalidate();
            return;
        }
        if (dynamic.getType().equals(ReadableType.Array)) {
            this.stroke = dynamic.asArray();
        } else {
            ReadableArray javaOnlyArray = new JavaOnlyArray();
            javaOnlyArray.pushInt(0);
            Matcher matcher = regex.matcher(dynamic.asString());
            while (matcher.find()) {
                javaOnlyArray.pushDouble(Double.parseDouble(matcher.group()));
            }
            this.stroke = javaOnlyArray;
        }
        invalidate();
    }

    @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
    public void setStrokeOpacity(float f) {
        this.strokeOpacity = f;
        invalidate();
    }

    @ReactProp(name = "strokeDasharray")
    public void setStrokeDasharray(@Nullable ReadableArray readableArray) {
        if (readableArray != null) {
            int size = readableArray.size();
            this.strokeDasharray = new SVGLength[size];
            for (int i = 0; i < size; i++) {
                this.strokeDasharray[i] = SVGLength.from(readableArray.getDynamic(i));
            }
        } else {
            this.strokeDasharray = null;
        }
        invalidate();
    }

    @ReactProp(name = "strokeDashoffset")
    public void setStrokeDashoffset(float f) {
        this.strokeDashoffset = f * this.mScale;
        invalidate();
    }

    @ReactProp(name = "strokeWidth")
    public void setStrokeWidth(Dynamic dynamic) {
        this.strokeWidth = SVGLength.from(dynamic);
        invalidate();
    }

    @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
    public void setStrokeMiterlimit(float f) {
        this.strokeMiterlimit = f;
        invalidate();
    }

    @ReactProp(defaultInt = 1, name = "strokeLinecap")
    public void setStrokeLinecap(int i) {
        if (i == 0) {
            this.strokeLinecap = Cap.BUTT;
        } else if (i == 1) {
            this.strokeLinecap = Cap.ROUND;
        } else if (i == 2) {
            this.strokeLinecap = Cap.SQUARE;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("strokeLinecap ");
            stringBuilder.append(i);
            stringBuilder.append(" unrecognized");
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        }
        invalidate();
    }

    @ReactProp(defaultInt = 1, name = "strokeLinejoin")
    public void setStrokeLinejoin(int i) {
        if (i == 0) {
            this.strokeLinejoin = Join.MITER;
        } else if (i == 1) {
            this.strokeLinejoin = Join.ROUND;
        } else if (i == 2) {
            this.strokeLinejoin = Join.BEVEL;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("strokeLinejoin ");
            stringBuilder.append(i);
            stringBuilder.append(" unrecognized");
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        }
        invalidate();
    }

    @ReactProp(name = "propList")
    public void setPropList(@Nullable ReadableArray readableArray) {
        if (readableArray != null) {
            ArrayList arrayList = new ArrayList();
            this.mAttributeList = arrayList;
            this.mPropList = arrayList;
            for (int i = 0; i < readableArray.size(); i++) {
                this.mPropList.add(readableArray.getString(i));
            }
        }
        invalidate();
    }

    void render(Canvas canvas, Paint paint, float f) {
        Paint paint2 = paint;
        MaskView maskView = this.mMask != null ? (MaskView) getSvgView().getDefinedMask(this.mMask) : null;
        if (maskView != null) {
            Paint paint3;
            Rect clipBounds = canvas.getClipBounds();
            int height = clipBounds.height();
            int width = clipBounds.width();
            Bitmap createBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Bitmap createBitmap2 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Bitmap createBitmap3 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap2);
            Canvas canvas3 = new Canvas(createBitmap);
            Canvas canvas4 = new Canvas(createBitmap3);
            int i = width;
            canvas3.clipRect((float) relativeOnWidth(maskView.mX), (float) relativeOnHeight(maskView.mY), (float) relativeOnWidth(maskView.mW), (float) relativeOnHeight(maskView.mH));
            Paint paint4 = new Paint(1);
            maskView.draw(canvas3, paint4, 1.0f);
            width = i * height;
            int[] iArr = new int[width];
            Canvas canvas5 = canvas4;
            Canvas canvas6 = canvas2;
            Bitmap bitmap = createBitmap3;
            createBitmap.getPixels(iArr, 0, i, 0, 0, i, height);
            int i2 = 0;
            while (i2 < width) {
                int i3 = iArr[i2];
                paint3 = paint4;
                int i4 = width;
                iArr[i2] = ((int) (((double) (i3 >>> 24)) * saturate((((((double) ((i3 >> 16) & 255)) * 0.299d) + (((double) ((i3 >> 8) & 255)) * 0.587d)) + (((double) (i3 & 255)) * 0.144d)) / 255.0d))) << 24;
                i2++;
                width = i4;
                paint4 = paint3;
            }
            paint3 = paint4;
            createBitmap.setPixels(iArr, 0, i, 0, 0, i, height);
            draw(canvas6, paint2, f);
            paint4 = paint3;
            paint4.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
            Canvas canvas7 = canvas5;
            canvas7.drawBitmap(createBitmap2, 0.0f, 0.0f, null);
            canvas7.drawBitmap(createBitmap, 0.0f, 0.0f, paint4);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint2);
            return;
        }
        Canvas canvas8 = canvas;
        float f2 = f;
        draw(canvas, paint, f);
    }

    void draw(Canvas canvas, Paint paint, float f) {
        f *= this.mOpacity;
        if (f > 0.01f) {
            Object obj = null;
            Object obj2 = this.mPath == null ? 1 : null;
            if (obj2 != null) {
                this.mPath = getPath(canvas, paint);
                this.mPath.setFillType(this.fillRule);
            }
            if (this.vectorEffect == 1) {
                obj = 1;
            }
            Path path = this.mPath;
            if (obj != null) {
                path = new Path();
                this.mPath.transform(this.mCTM, path);
                canvas.setMatrix(null);
            }
            if (!(obj2 == null && path == this.mPath)) {
                this.mBox = new RectF();
                path.computeBounds(this.mBox, true);
            }
            RectF rectF = new RectF(this.mBox);
            this.mCTM.mapRect(rectF);
            setClientRect(rectF);
            clip(canvas, paint);
            if (setupFillPaint(paint, this.fillOpacity * f)) {
                if (obj2 != null) {
                    this.mFillPath = new Path();
                    paint.getFillPath(path, this.mFillPath);
                }
                canvas.drawPath(path, paint);
            }
            if (setupStrokePaint(paint, this.strokeOpacity * f)) {
                if (obj2 != null) {
                    this.mStrokePath = new Path();
                    paint.getFillPath(path, this.mStrokePath);
                }
                canvas.drawPath(path, paint);
            }
            renderMarkers(canvas, paint, f);
        }
    }

    void renderMarkers(Canvas canvas, Paint paint, float f) {
        MarkerView markerView = (MarkerView) getSvgView().getDefinedMarker(this.mMarkerStart);
        MarkerView markerView2 = (MarkerView) getSvgView().getDefinedMarker(this.mMarkerMid);
        MarkerView markerView3 = (MarkerView) getSvgView().getDefinedMarker(this.mMarkerEnd);
        if (this.elements == null) {
            return;
        }
        if (markerView != null || markerView2 != null || markerView3 != null) {
            contextElement = this;
            ArrayList fromPath = RNSVGMarkerPosition.fromPath(this.elements);
            SVGLength sVGLength = this.strokeWidth;
            float relativeOnOther = (float) (sVGLength != null ? relativeOnOther(sVGLength) : 1.0d);
            this.mMarkerPath = new Path();
            Iterator it = fromPath.iterator();
            while (it.hasNext()) {
                RNSVGMarkerPosition rNSVGMarkerPosition = (RNSVGMarkerPosition) it.next();
                int i = AnonymousClass1.$SwitchMap$com$horcrux$svg$RNSVGMarkerType[rNSVGMarkerPosition.type.ordinal()];
                MarkerView markerView4 = i != 1 ? i != 2 ? i != 3 ? null : markerView3 : markerView2 : markerView;
                if (markerView4 != null) {
                    markerView4.renderMarker(canvas, paint, f, rNSVGMarkerPosition, relativeOnOther);
                    this.mMarkerPath.addPath(markerView4.getPath(canvas, paint), markerView4.markerTransform);
                }
            }
            contextElement = null;
        }
    }

    private boolean setupFillPaint(Paint paint, float f) {
        ReadableArray readableArray = this.fill;
        if (readableArray == null || readableArray.size() <= 0) {
            return false;
        }
        paint.reset();
        paint.setFlags(385);
        paint.setStyle(Style.FILL);
        setupPaint(paint, f, this.fill);
        return true;
    }

    private boolean setupStrokePaint(Paint paint, float f) {
        paint.reset();
        double relativeOnOther = relativeOnOther(this.strokeWidth);
        int i = 0;
        if (relativeOnOther != 0.0d) {
            ReadableArray readableArray = this.stroke;
            if (!(readableArray == null || readableArray.size() == 0)) {
                paint.setFlags(385);
                paint.setStyle(Style.STROKE);
                paint.setStrokeCap(this.strokeLinecap);
                paint.setStrokeJoin(this.strokeLinejoin);
                paint.setStrokeMiter(this.strokeMiterlimit * this.mScale);
                paint.setStrokeWidth((float) relativeOnOther);
                setupPaint(paint, f, this.stroke);
                SVGLength[] sVGLengthArr = this.strokeDasharray;
                if (sVGLengthArr != null) {
                    int length = sVGLengthArr.length;
                    float[] fArr = new float[length];
                    while (i < length) {
                        fArr[i] = (float) relativeOnOther(this.strokeDasharray[i]);
                        i++;
                    }
                    paint.setPathEffect(new DashPathEffect(fArr, this.strokeDashoffset));
                }
                return true;
            }
        }
        return false;
    }

    private void setupPaint(Paint paint, float f, ReadableArray readableArray) {
        int i = readableArray.getInt(0);
        if (i != 0) {
            RenderableView renderableView;
            if (i == 1) {
                Brush definedBrush = getSvgView().getDefinedBrush(readableArray.getString(1));
                if (definedBrush != null) {
                    definedBrush.setupPaint(paint, this.mBox, this.mScale, f);
                }
            } else if (i == 2) {
                paint.setColor(getSvgView().mTintColor);
            } else if (i == 3) {
                renderableView = contextElement;
                if (renderableView != null) {
                    readableArray = renderableView.fill;
                    if (readableArray != null) {
                        setupPaint(paint, f, readableArray);
                    }
                }
            } else if (i == 4) {
                renderableView = contextElement;
                if (renderableView != null) {
                    readableArray = renderableView.stroke;
                    if (readableArray != null) {
                        setupPaint(paint, f, readableArray);
                    }
                }
            }
        } else if (readableArray.size() == 2) {
            int i2 = readableArray.getInt(1);
            paint.setColor((Math.round(((float) (i2 >>> 24)) * f) << 24) | (i2 & ViewCompat.MEASURED_SIZE_MASK));
        } else {
            paint.setARGB((int) (readableArray.size() > 4 ? (readableArray.getDouble(4) * ((double) f)) * 255.0d : (double) (f * 255.0f)), (int) (readableArray.getDouble(1) * 255.0d), (int) (readableArray.getDouble(2) * 255.0d), (int) (readableArray.getDouble(3) * 255.0d));
        }
    }

    int hitTest(float[] fArr) {
        if (this.mPath != null && this.mInvertible && this.mTransformInvertible) {
            float[] fArr2 = new float[2];
            this.mInvMatrix.mapPoints(fArr2, fArr);
            this.mInvTransform.mapPoints(fArr2);
            int round = Math.round(fArr2[0]);
            int round2 = Math.round(fArr2[1]);
            if (this.mRegion == null && this.mFillPath != null) {
                this.mRegion = getRegion(this.mFillPath);
            }
            if (this.mRegion == null && this.mPath != null) {
                this.mRegion = getRegion(this.mPath);
            }
            if (this.mStrokeRegion == null && this.mStrokePath != null) {
                this.mStrokeRegion = getRegion(this.mStrokePath);
            }
            if (this.mMarkerRegion == null && this.mMarkerPath != null) {
                this.mMarkerRegion = getRegion(this.mMarkerPath);
            }
            if ((this.mRegion != null && this.mRegion.contains(round, round2)) || (this.mStrokeRegion != null && (this.mStrokeRegion.contains(round, round2) || (this.mMarkerRegion != null && this.mMarkerRegion.contains(round, round2))))) {
                Path clipPath = getClipPath();
                if (clipPath != null) {
                    if (this.mClipRegionPath != clipPath) {
                        this.mClipRegionPath = clipPath;
                        this.mClipRegion = getRegion(clipPath);
                    }
                    if (!this.mClipRegion.contains(round, round2)) {
                        return -1;
                    }
                }
                return getId();
            }
        }
        return -1;
    }

    Region getRegion(Path path) {
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(path, new Region((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom)));
        return region;
    }

    private ArrayList<String> getAttributeList() {
        return this.mAttributeList;
    }

    void mergeProperties(RenderableView renderableView) {
        ArrayList attributeList = renderableView.getAttributeList();
        if (attributeList != null && attributeList.size() != 0) {
            this.mOriginProperties = new ArrayList();
            Collection collection = this.mPropList;
            this.mAttributeList = collection == null ? new ArrayList() : new ArrayList(collection);
            int i = 0;
            int size = attributeList.size();
            while (i < size) {
                try {
                    String str = (String) attributeList.get(i);
                    Field field = getClass().getField(str);
                    Object obj = field.get(renderableView);
                    this.mOriginProperties.add(field.get(this));
                    if (!hasOwnProperty(str)) {
                        this.mAttributeList.add(str);
                        field.set(this, obj);
                    }
                    i++;
                } catch (Throwable e) {
                    throw new IllegalStateException(e);
                }
            }
            this.mLastMergedList = attributeList;
        }
    }

    void resetProperties() {
        ArrayList arrayList = this.mLastMergedList;
        if (arrayList != null && this.mOriginProperties != null) {
            try {
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    getClass().getField((String) this.mLastMergedList.get(size)).set(this, this.mOriginProperties.get(size));
                }
                this.mLastMergedList = null;
                this.mOriginProperties = null;
                this.mAttributeList = this.mPropList;
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private boolean hasOwnProperty(String str) {
        ArrayList arrayList = this.mAttributeList;
        return arrayList != null && arrayList.contains(str);
    }
}
