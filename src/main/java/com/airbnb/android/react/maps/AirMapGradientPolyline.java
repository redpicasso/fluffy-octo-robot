package com.airbnb.android.react.maps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.geometry.Point;
import com.google.maps.android.projection.SphericalMercatorProjection;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

public class AirMapGradientPolyline extends AirMapFeature {
    private int[] colors;
    protected final Context context;
    private GoogleMap map;
    private List<LatLng> points;
    private TileOverlay tileOverlay;
    private TileOverlayOptions tileOverlayOptions;
    private AirMapGradientPolylineProvider tileProvider;
    private float width;
    private float zIndex;

    public static class MutPoint {
        public double x;
        public double y;

        public MutPoint set(Point point, float f, int i, int i2, int i3) {
            double d = (double) f;
            this.x = (point.x * d) - ((double) (i * i3));
            this.y = (point.y * d) - ((double) (i2 * i3));
            return this;
        }
    }

    public class AirMapGradientPolylineProvider implements TileProvider {
        public static final int BASE_TILE_SIZE = 256;
        protected final int[] colors;
        protected final float density;
        protected final List<LatLng> points;
        protected Point[] projectedPtMids;
        protected Point[] projectedPts;
        protected final SphericalMercatorProjection projection = new SphericalMercatorProjection(256.0d);
        protected final int tileDimension = ((int) (this.density * 256.0f));
        protected LatLng[] trailLatLngs;
        protected final float width;

        public AirMapGradientPolylineProvider(Context context, List<LatLng> list, int[] iArr, float f) {
            this.points = list;
            this.colors = iArr;
            this.width = f;
            this.density = context.getResources().getDisplayMetrics().density;
            calculatePoints();
        }

        public void calculatePoints() {
            this.trailLatLngs = new LatLng[this.points.size()];
            this.projectedPts = new Point[this.points.size()];
            int i = 0;
            this.projectedPtMids = new Point[Math.max(this.points.size() - 1, 0)];
            while (i < this.points.size()) {
                LatLng latLng = (LatLng) this.points.get(i);
                this.trailLatLngs[i] = latLng;
                this.projectedPts[i] = this.projection.toPoint(latLng);
                if (i > 0) {
                    int i2 = i - 1;
                    this.projectedPtMids[i2] = this.projection.toPoint(SphericalUtil.interpolate((LatLng) this.points.get(i2), latLng, 0.5d));
                }
                i++;
            }
        }

        public Tile getTile(int i, int i2, int i3) {
            int i4 = this.tileDimension;
            Bitmap createBitmap = Bitmap.createBitmap(i4, i4, Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Matrix matrix = new Matrix();
            Paint paint = new Paint();
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth(this.width);
            paint.setStrokeCap(Cap.BUTT);
            paint.setStrokeJoin(Join.ROUND);
            paint.setFlags(1);
            paint.setShader(new LinearGradient(0.0f, 0.0f, 1.0f, 0.0f, this.colors, null, TileMode.CLAMP));
            paint.getShader().setLocalMatrix(matrix);
            Paint paint2 = new Paint();
            paint2.setStyle(Style.STROKE);
            paint2.setStrokeWidth(this.width);
            paint2.setStrokeCap(Cap.BUTT);
            paint2.setStrokeJoin(Join.ROUND);
            paint2.setFlags(1);
            renderTrail(canvas, matrix, paint, paint2, (float) (Math.pow(2.0d, (double) i3) * ((double) this.density)), i, i2);
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            createBitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            int i5 = this.tileDimension;
            return new Tile(i5, i5, byteArrayOutputStream.toByteArray());
        }

        public void renderTrail(Canvas canvas, Matrix matrix, Paint paint, Paint paint2, float f, int i, int i2) {
            Canvas canvas2 = canvas;
            Paint paint3 = paint2;
            MutPoint mutPoint = new MutPoint();
            MutPoint mutPoint2 = new MutPoint();
            MutPoint mutPoint3 = new MutPoint();
            MutPoint mutPoint4 = new MutPoint();
            MutPoint mutPoint5 = new MutPoint();
            float f2 = 1.0f;
            float f3;
            int i3;
            int i4;
            if (this.points.size() == 1) {
                mutPoint.set(this.projectedPts[0], f, i, i2, this.tileDimension);
                paint3.setStyle(Style.FILL);
                paint3.setColor(AirMapGradientPolyline.interpolateColor(this.colors, 1.0f));
                canvas2.drawCircle((float) mutPoint.x, (float) mutPoint.y, paint2.getStrokeWidth() / 2.0f, paint3);
                paint3.setStyle(Style.STROKE);
            } else if (this.points.size() == 2) {
                f3 = f;
                i3 = i;
                i4 = i2;
                mutPoint.set(this.projectedPts[0], f3, i3, i4, this.tileDimension);
                mutPoint2.set(this.projectedPts[1], f3, i3, i4, this.tileDimension);
                drawLine(canvas, paint2, mutPoint, mutPoint2, 0.0f);
            } else {
                int i5 = 2;
                while (i5 < this.points.size()) {
                    int i6 = i5 - 2;
                    f3 = f;
                    i3 = i;
                    i4 = i2;
                    mutPoint.set(this.projectedPts[i6], f3, i3, i4, this.tileDimension);
                    int i7 = i5 - 1;
                    mutPoint2.set(this.projectedPts[i7], f3, i3, i4, this.tileDimension);
                    mutPoint3.set(this.projectedPts[i5], f3, i3, i4, this.tileDimension);
                    mutPoint4.set(this.projectedPtMids[i6], f3, i3, i4, this.tileDimension);
                    mutPoint5.set(this.projectedPtMids[i7], f3, i3, i4, this.tileDimension);
                    float f4 = (float) i5;
                    float size = (f4 - 2.0f) / ((float) this.points.size());
                    float size2 = (f4 - f2) / ((float) this.points.size());
                    float f5 = (size + size2) / 2.0f;
                    Log.d("AirMapGradientPolyline", String.valueOf(f5));
                    paint3.setStyle(Style.FILL);
                    paint3.setColor(AirMapGradientPolyline.interpolateColor(this.colors, f5));
                    canvas2.drawCircle((float) mutPoint2.x, (float) mutPoint2.y, paint2.getStrokeWidth() / 2.0f, paint3);
                    paint3.setStyle(Style.STROKE);
                    float f6 = f5;
                    int i8 = i5;
                    drawLine(canvas, matrix, paint, paint2, i6 == 0 ? mutPoint : mutPoint4, mutPoint2, size, f6);
                    drawLine(canvas, matrix, paint, paint2, mutPoint2, i8 == this.points.size() + -1 ? mutPoint3 : mutPoint5, f6, size2);
                    i5 = i8 + 1;
                    canvas2 = canvas;
                    f2 = 1.0f;
                }
            }
        }

        public void drawLine(Canvas canvas, Matrix matrix, Paint paint, Paint paint2, MutPoint mutPoint, MutPoint mutPoint2, float f, float f2) {
            Matrix matrix2 = matrix;
            MutPoint mutPoint3 = mutPoint;
            MutPoint mutPoint4 = mutPoint2;
            float f3 = f;
            if (f3 == f2) {
                drawLine(canvas, paint2, mutPoint, mutPoint2, f);
                return;
            }
            matrix.reset();
            matrix.preRotate((float) Math.toDegrees(Math.atan2(mutPoint4.y - mutPoint3.y, mutPoint4.x - mutPoint3.x)), (float) mutPoint3.x, (float) mutPoint3.y);
            matrix.preTranslate((float) mutPoint3.x, (float) mutPoint3.y);
            float sqrt = (float) Math.sqrt(Math.pow(mutPoint4.x - mutPoint3.x, 2.0d) + Math.pow(mutPoint4.y - mutPoint3.y, 2.0d));
            matrix.preScale(sqrt, sqrt);
            sqrt = 1.0f / (f2 - f3);
            matrix.preScale(sqrt, sqrt);
            matrix.preTranslate(-f3, 0.0f);
            paint.getShader().setLocalMatrix(matrix);
            canvas.drawLine((float) mutPoint3.x, (float) mutPoint3.y, (float) mutPoint4.x, (float) mutPoint4.y, paint);
        }

        public void drawLine(Canvas canvas, Paint paint, MutPoint mutPoint, MutPoint mutPoint2, float f) {
            paint.setColor(AirMapGradientPolyline.interpolateColor(this.colors, f));
            canvas.drawLine((float) mutPoint.x, (float) mutPoint.y, (float) mutPoint2.x, (float) mutPoint2.y, paint);
        }
    }

    public AirMapGradientPolyline(Context context) {
        super(context);
        this.context = context;
    }

    public void setCoordinates(List<LatLng> list) {
        this.points = list;
        TileOverlay tileOverlay = this.tileOverlay;
        if (tileOverlay != null) {
            tileOverlay.remove();
        }
        GoogleMap googleMap = this.map;
        if (googleMap != null) {
            this.tileOverlay = googleMap.addTileOverlay(createTileOverlayOptions());
        }
    }

    public void setStrokeColors(int[] iArr) {
        this.colors = iArr;
        TileOverlay tileOverlay = this.tileOverlay;
        if (tileOverlay != null) {
            tileOverlay.remove();
        }
        GoogleMap googleMap = this.map;
        if (googleMap != null) {
            this.tileOverlay = googleMap.addTileOverlay(createTileOverlayOptions());
        }
    }

    public void setZIndex(float f) {
        this.zIndex = f;
        TileOverlay tileOverlay = this.tileOverlay;
        if (tileOverlay != null) {
            tileOverlay.setZIndex(f);
        }
    }

    public void setWidth(float f) {
        this.width = f;
        TileOverlay tileOverlay = this.tileOverlay;
        if (tileOverlay != null) {
            tileOverlay.remove();
        }
        GoogleMap googleMap = this.map;
        if (googleMap != null) {
            this.tileOverlay = googleMap.addTileOverlay(createTileOverlayOptions());
        }
    }

    private TileOverlayOptions createTileOverlayOptions() {
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.zIndex(this.zIndex);
        this.tileProvider = new AirMapGradientPolylineProvider(this.context, this.points, this.colors, this.width);
        tileOverlayOptions.tileProvider(this.tileProvider);
        return tileOverlayOptions;
    }

    public static int interpolateColor(int[] iArr, float f) {
        f *= (float) (iArr.length - 1);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < iArr.length; i4++) {
            float max = Math.max(1.0f - Math.abs(f - ((float) i4)), 0.0f);
            i += (int) (((float) Color.red(iArr[i4])) * max);
            i2 += (int) (((float) Color.green(iArr[i4])) * max);
            i3 += (int) (((float) Color.blue(iArr[i4])) * max);
        }
        return Color.rgb(i, i2, i3);
    }

    public Object getFeature() {
        return this.tileOverlay;
    }

    public void addToMap(GoogleMap googleMap) {
        Log.d("AirMapGradientPolyline", "ADDTOMAP");
        this.map = googleMap;
        this.tileOverlay = googleMap.addTileOverlay(createTileOverlayOptions());
    }

    public void removeFromMap(GoogleMap googleMap) {
        this.tileOverlay.remove();
    }
}
