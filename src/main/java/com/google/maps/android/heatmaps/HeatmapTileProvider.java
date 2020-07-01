package com.google.maps.android.heatmaps;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import androidx.collection.LongSparseArray;
import com.facebook.imageutils.JfifUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;
import com.google.maps.android.geometry.Bounds;
import com.google.maps.android.quadtree.PointQuadTree;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class HeatmapTileProvider implements TileProvider {
    public static final Gradient DEFAULT_GRADIENT = new Gradient(DEFAULT_GRADIENT_COLORS, DEFAULT_GRADIENT_START_POINTS);
    private static final int[] DEFAULT_GRADIENT_COLORS = new int[]{Color.rgb(102, JfifUtil.MARKER_APP1, 0), Color.rgb(255, 0, 0)};
    private static final float[] DEFAULT_GRADIENT_START_POINTS = new float[]{0.2f, 1.0f};
    private static final int DEFAULT_MAX_ZOOM = 11;
    private static final int DEFAULT_MIN_ZOOM = 5;
    public static final double DEFAULT_OPACITY = 0.7d;
    public static final int DEFAULT_RADIUS = 20;
    private static final int MAX_RADIUS = 50;
    private static final int MAX_ZOOM_LEVEL = 22;
    private static final int MIN_RADIUS = 10;
    private static final int SCREEN_SIZE = 1280;
    private static final int TILE_DIM = 512;
    static final double WORLD_WIDTH = 1.0d;
    private Bounds mBounds;
    private int[] mColorMap;
    private Collection<WeightedLatLng> mData;
    private Gradient mGradient;
    private double[] mKernel;
    private double[] mMaxIntensity;
    private double mOpacity;
    private int mRadius;
    private PointQuadTree<WeightedLatLng> mTree;

    public static class Builder {
        private Collection<WeightedLatLng> data;
        private Gradient gradient = HeatmapTileProvider.DEFAULT_GRADIENT;
        private double opacity = 0.7d;
        private int radius = 20;

        public Builder data(Collection<LatLng> collection) {
            return weightedData(HeatmapTileProvider.wrapData(collection));
        }

        public Builder weightedData(Collection<WeightedLatLng> collection) {
            this.data = collection;
            if (!this.data.isEmpty()) {
                return this;
            }
            throw new IllegalArgumentException("No input points.");
        }

        public Builder radius(int i) {
            this.radius = i;
            i = this.radius;
            if (i >= 10 && i <= 50) {
                return this;
            }
            throw new IllegalArgumentException("Radius not within bounds.");
        }

        public Builder gradient(Gradient gradient) {
            this.gradient = gradient;
            return this;
        }

        public Builder opacity(double d) {
            this.opacity = d;
            d = this.opacity;
            if (d >= 0.0d && d <= 1.0d) {
                return this;
            }
            throw new IllegalArgumentException("Opacity must be in range [0, 1]");
        }

        public HeatmapTileProvider build() {
            if (this.data != null) {
                return new HeatmapTileProvider(this);
            }
            throw new IllegalStateException("No input data: you must use either .data or .weightedData before building");
        }
    }

    private HeatmapTileProvider(Builder builder) {
        this.mData = builder.data;
        this.mRadius = builder.radius;
        this.mGradient = builder.gradient;
        this.mOpacity = builder.opacity;
        int i = this.mRadius;
        this.mKernel = generateKernel(i, ((double) i) / 3.0d);
        setGradient(this.mGradient);
        setWeightedData(this.mData);
    }

    public void setWeightedData(Collection<WeightedLatLng> collection) {
        this.mData = collection;
        if (this.mData.isEmpty()) {
            throw new IllegalArgumentException("No input points.");
        }
        this.mBounds = getBounds(this.mData);
        this.mTree = new PointQuadTree(this.mBounds);
        for (WeightedLatLng add : this.mData) {
            this.mTree.add(add);
        }
        this.mMaxIntensity = getMaxIntensities(this.mRadius);
    }

    public void setData(Collection<LatLng> collection) {
        setWeightedData(wrapData(collection));
    }

    private static Collection<WeightedLatLng> wrapData(Collection<LatLng> collection) {
        Collection arrayList = new ArrayList();
        for (LatLng weightedLatLng : collection) {
            arrayList.add(new WeightedLatLng(weightedLatLng));
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x00af  */
    public com.google.android.gms.maps.model.Tile getTile(int r36, int r37, int r38) {
        /*
        r35 = this;
        r0 = r35;
        r1 = r36;
        r2 = r37;
        r3 = r38;
        r4 = (double) r3;
        r6 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r4 = java.lang.Math.pow(r6, r4);
        r8 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r4 = r8 / r4;
        r10 = r0.mRadius;
        r11 = (double) r10;
        r11 = r11 * r4;
        r13 = 4647714815446351872; // 0x4080000000000000 float:0.0 double:512.0;
        r11 = r11 / r13;
        r6 = r6 * r11;
        r6 = r6 + r4;
        r10 = r10 * 2;
        r10 = r10 + 512;
        r13 = (double) r10;
        r6 = r6 / r13;
        r13 = (double) r1;
        r13 = r13 * r4;
        r13 = r13 - r11;
        r1 = r1 + 1;
        r8 = (double) r1;
        r8 = r8 * r4;
        r18 = r8 + r11;
        r8 = (double) r2;
        r8 = r8 * r4;
        r8 = r8 - r11;
        r1 = r2 + 1;
        r1 = (double) r1;
        r1 = r1 * r4;
        r1 = r1 + r11;
        r4 = new java.util.ArrayList;
        r4.<init>();
        r20 = 0;
        r5 = (r13 > r20 ? 1 : (r13 == r20 ? 0 : -1));
        if (r5 >= 0) goto L_0x0060;
    L_0x0044:
        r4 = new com.google.maps.android.geometry.Bounds;
        r15 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r21 = r13 + r15;
        r23 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r20 = r4;
        r25 = r8;
        r27 = r1;
        r20.<init>(r21, r23, r25, r27);
        r15 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r5 = r0.mTree;
        r4 = r5.search(r4);
    L_0x005d:
        r24 = r15;
        goto L_0x007e;
    L_0x0060:
        r15 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r5 = (r18 > r15 ? 1 : (r18 == r15 ? 0 : -1));
        if (r5 <= 0) goto L_0x007c;
    L_0x0066:
        r4 = new com.google.maps.android.geometry.Bounds;
        r21 = 0;
        r23 = r18 - r15;
        r20 = r4;
        r25 = r8;
        r27 = r1;
        r20.<init>(r21, r23, r25, r27);
        r5 = r0.mTree;
        r4 = r5.search(r4);
        goto L_0x005d;
    L_0x007c:
        r24 = r20;
    L_0x007e:
        r5 = new com.google.maps.android.geometry.Bounds;
        r15 = r5;
        r16 = r13;
        r20 = r8;
        r22 = r1;
        r15.<init>(r16, r18, r20, r22);
        r1 = new com.google.maps.android.geometry.Bounds;
        r2 = r0.mBounds;
        r2 = r2.minX;
        r27 = r2 - r11;
        r2 = r0.mBounds;
        r2 = r2.maxX;
        r29 = r2 + r11;
        r2 = r0.mBounds;
        r2 = r2.minY;
        r31 = r2 - r11;
        r2 = r0.mBounds;
        r2 = r2.maxY;
        r33 = r2 + r11;
        r26 = r1;
        r26.<init>(r27, r29, r31, r33);
        r1 = r5.intersects(r1);
        if (r1 != 0) goto L_0x00b2;
    L_0x00af:
        r1 = com.google.android.gms.maps.model.TileProvider.NO_TILE;
        return r1;
    L_0x00b2:
        r1 = r0.mTree;
        r1 = r1.search(r5);
        r2 = r1.isEmpty();
        if (r2 == 0) goto L_0x00c1;
    L_0x00be:
        r1 = com.google.android.gms.maps.model.TileProvider.NO_TILE;
        return r1;
    L_0x00c1:
        r2 = r0.mRadius;
        r3 = r2 * 2;
        r3 = r3 + 512;
        r2 = r2 * 2;
        r2 = r2 + 512;
        r2 = new int[]{r3, r2};
        r3 = double.class;
        r2 = java.lang.reflect.Array.newInstance(r3, r2);
        r2 = (double[][]) r2;
        r1 = r1.iterator();
    L_0x00db:
        r3 = r1.hasNext();
        if (r3 == 0) goto L_0x0101;
    L_0x00e1:
        r3 = r1.next();
        r3 = (com.google.maps.android.heatmaps.WeightedLatLng) r3;
        r5 = r3.getPoint();
        r10 = r5.x;
        r10 = r10 - r13;
        r10 = r10 / r6;
        r10 = (int) r10;
        r11 = r5.y;
        r11 = r11 - r8;
        r11 = r11 / r6;
        r5 = (int) r11;
        r10 = r2[r10];
        r11 = r10[r5];
        r15 = r3.getIntensity();
        r11 = r11 + r15;
        r10[r5] = r11;
        goto L_0x00db;
    L_0x0101:
        r1 = r4.iterator();
    L_0x0105:
        r3 = r1.hasNext();
        if (r3 == 0) goto L_0x012d;
    L_0x010b:
        r3 = r1.next();
        r3 = (com.google.maps.android.heatmaps.WeightedLatLng) r3;
        r4 = r3.getPoint();
        r10 = r4.x;
        r10 = r10 + r24;
        r10 = r10 - r13;
        r10 = r10 / r6;
        r5 = (int) r10;
        r10 = r4.y;
        r10 = r10 - r8;
        r10 = r10 / r6;
        r4 = (int) r10;
        r5 = r2[r5];
        r10 = r5[r4];
        r15 = r3.getIntensity();
        r10 = r10 + r15;
        r5[r4] = r10;
        goto L_0x0105;
    L_0x012d:
        r1 = r0.mKernel;
        r1 = convolve(r2, r1);
        r2 = r0.mColorMap;
        r3 = r0.mMaxIntensity;
        r4 = r3[r38];
        r1 = colorize(r1, r2, r4);
        r1 = convertBitmap(r1);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.maps.android.heatmaps.HeatmapTileProvider.getTile(int, int, int):com.google.android.gms.maps.model.Tile");
    }

    public void setGradient(Gradient gradient) {
        this.mGradient = gradient;
        this.mColorMap = gradient.generateColorMap(this.mOpacity);
    }

    public void setRadius(int i) {
        this.mRadius = i;
        i = this.mRadius;
        this.mKernel = generateKernel(i, ((double) i) / 3.0d);
        this.mMaxIntensity = getMaxIntensities(this.mRadius);
    }

    public void setOpacity(double d) {
        this.mOpacity = d;
        setGradient(this.mGradient);
    }

    private double[] getMaxIntensities(int i) {
        int i2;
        double[] dArr = new double[22];
        int i3 = 5;
        while (true) {
            i2 = 11;
            if (i3 >= 11) {
                break;
            }
            dArr[i3] = getMaxValue(this.mData, this.mBounds, i, (int) (Math.pow(2.0d, (double) (i3 - 3)) * 1280.0d));
            if (i3 == 5) {
                for (i2 = 0; i2 < i3; i2++) {
                    dArr[i2] = dArr[i3];
                }
            }
            i3++;
        }
        while (i2 < 22) {
            dArr[i2] = dArr[10];
            i2++;
        }
        return dArr;
    }

    private static Tile convertBitmap(Bitmap bitmap) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
        return new Tile(512, 512, byteArrayOutputStream.toByteArray());
    }

    static Bounds getBounds(Collection<WeightedLatLng> collection) {
        Iterator it = collection.iterator();
        WeightedLatLng weightedLatLng = (WeightedLatLng) it.next();
        double d = weightedLatLng.getPoint().x;
        double d2 = weightedLatLng.getPoint().x;
        double d3 = d;
        double d4 = d2;
        double d5 = weightedLatLng.getPoint().y;
        double d6 = weightedLatLng.getPoint().y;
        while (it.hasNext()) {
            weightedLatLng = (WeightedLatLng) it.next();
            d = weightedLatLng.getPoint().x;
            d2 = weightedLatLng.getPoint().y;
            if (d < d3) {
                d3 = d;
            }
            if (d > d4) {
                d4 = d;
            }
            if (d2 < d5) {
                d5 = d2;
            }
            if (d2 > d6) {
                d6 = d2;
            }
        }
        return new Bounds(d3, d4, d5, d6);
    }

    static double[] generateKernel(int i, double d) {
        double[] dArr = new double[((i * 2) + 1)];
        for (int i2 = -i; i2 <= i; i2++) {
            dArr[i2 + i] = Math.exp(((double) ((-i2) * i2)) / ((2.0d * d) * d));
        }
        return dArr;
    }

    static double[][] convolve(double[][] dArr, double[] dArr2) {
        int i;
        int i2;
        double[][] dArr3 = dArr;
        double[] dArr4 = dArr2;
        int floor = (int) Math.floor(((double) dArr4.length) / 2.0d);
        int length = dArr3.length;
        int i3 = length - (floor * 2);
        int i4 = (floor + i3) - 1;
        double[][] dArr5 = (double[][]) Array.newInstance(double.class, new int[]{length, length});
        for (i = 0; i < length; i++) {
            for (int i5 = 0; i5 < length; i5++) {
                double d = dArr3[i][i5];
                if (d != 0.0d) {
                    int i6 = i + floor;
                    if (i4 < i6) {
                        i6 = i4;
                    }
                    i6++;
                    int i7 = i - floor;
                    for (i2 = floor > i7 ? floor : i7; i2 < i6; i2++) {
                        double[] dArr6 = dArr5[i2];
                        dArr6[i5] = dArr6[i5] + (dArr4[i2 - i7] * d);
                    }
                }
            }
        }
        dArr3 = (double[][]) Array.newInstance(double.class, new int[]{i3, i3});
        for (i3 = floor; i3 < i4 + 1; i3++) {
            for (i2 = 0; i2 < length; i2++) {
                double d2 = dArr5[i3][i2];
                if (d2 != 0.0d) {
                    i = i2 + floor;
                    if (i4 < i) {
                        i = i4;
                    }
                    i++;
                    int i8 = i2 - floor;
                    int i9 = floor > i8 ? floor : i8;
                    while (i9 < i) {
                        double[] dArr7 = dArr3[i3 - floor];
                        int i10 = i9 - floor;
                        dArr7[i10] = dArr7[i10] + (dArr4[i9 - i8] * d2);
                        i9++;
                    }
                }
            }
        }
        return dArr3;
    }

    static Bitmap colorize(double[][] dArr, int[] iArr, double d) {
        double[][] dArr2 = dArr;
        int[] iArr2 = iArr;
        int i = iArr2[iArr2.length - 1];
        double length = ((double) (iArr2.length - 1)) / d;
        int length2 = dArr2.length;
        int[] iArr3 = new int[(length2 * length2)];
        for (int i2 = 0; i2 < length2; i2++) {
            for (int i3 = 0; i3 < length2; i3++) {
                double d2 = dArr2[i3][i2];
                int i4 = (i2 * length2) + i3;
                int i5 = (int) (d2 * length);
                if (d2 == 0.0d) {
                    iArr3[i4] = 0;
                } else if (i5 < iArr2.length) {
                    iArr3[i4] = iArr2[i5];
                } else {
                    iArr3[i4] = i;
                }
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(length2, length2, Config.ARGB_8888);
        createBitmap.setPixels(iArr3, 0, length2, 0, 0, length2, length2);
        return createBitmap;
    }

    static double getMaxValue(Collection<WeightedLatLng> collection, Bounds bounds, int i, int i2) {
        Bounds bounds2 = bounds;
        double d = bounds2.minX;
        double d2 = bounds2.maxX;
        double d3 = bounds2.minY;
        d2 -= d;
        double d4 = bounds2.maxY - d3;
        if (d2 <= d4) {
            d2 = d4;
        }
        d4 = ((double) ((int) (((double) (i2 / (i * 2))) + 0.5d))) / d2;
        LongSparseArray longSparseArray = new LongSparseArray();
        double d5 = 0.0d;
        for (WeightedLatLng weightedLatLng : collection) {
            int i3 = (int) ((weightedLatLng.getPoint().y - d3) * d4);
            long j = (long) ((int) ((weightedLatLng.getPoint().x - d) * d4));
            LongSparseArray longSparseArray2 = (LongSparseArray) longSparseArray.get(j);
            if (longSparseArray2 == null) {
                longSparseArray2 = new LongSparseArray();
                longSparseArray.put(j, longSparseArray2);
            }
            j = (long) i3;
            Double d6 = (Double) longSparseArray2.get(j);
            if (d6 == null) {
                d6 = Double.valueOf(0.0d);
            }
            Double valueOf = Double.valueOf(d6.doubleValue() + weightedLatLng.getIntensity());
            longSparseArray2.put(j, valueOf);
            if (valueOf.doubleValue() > d5) {
                d5 = valueOf.doubleValue();
            }
        }
        return d5;
    }
}
