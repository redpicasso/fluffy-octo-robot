package com.horcrux.svg;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.imagehelper.ImageSource;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nonnull;

@SuppressLint({"ViewConstructor"})
class ImageView extends RenderableView {
    private String mAlign;
    private SVGLength mH;
    private int mImageHeight;
    private int mImageWidth;
    private final AtomicBoolean mLoading = new AtomicBoolean(false);
    private int mMeetOrSlice;
    private SVGLength mW;
    private SVGLength mX;
    private SVGLength mY;
    private String uriString;

    public ImageView(ReactContext reactContext) {
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

    /* JADX WARNING: Removed duplicated region for block: B:17:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0043  */
    @com.facebook.react.uimanager.annotations.ReactProp(name = "src")
    public void setSrc(@javax.annotation.Nullable com.facebook.react.bridge.ReadableMap r4) {
        /*
        r3 = this;
        if (r4 == 0) goto L_0x004f;
    L_0x0002:
        r0 = "uri";
        r0 = r4.getString(r0);
        r3.uriString = r0;
        r0 = r3.uriString;
        if (r0 == 0) goto L_0x004f;
    L_0x000e:
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x0015;
    L_0x0014:
        goto L_0x004f;
    L_0x0015:
        r0 = "width";
        r1 = r4.hasKey(r0);
        if (r1 == 0) goto L_0x0032;
    L_0x001d:
        r1 = "height";
        r2 = r4.hasKey(r1);
        if (r2 == 0) goto L_0x0032;
    L_0x0025:
        r0 = r4.getInt(r0);
        r3.mImageWidth = r0;
        r4 = r4.getInt(r1);
        r3.mImageHeight = r4;
        goto L_0x0037;
    L_0x0032:
        r4 = 0;
        r3.mImageWidth = r4;
        r3.mImageHeight = r4;
    L_0x0037:
        r4 = r3.uriString;
        r4 = android.net.Uri.parse(r4);
        r4 = r4.getScheme();
        if (r4 != 0) goto L_0x004f;
    L_0x0043:
        r4 = com.facebook.react.views.imagehelper.ResourceDrawableIdHelper.getInstance();
        r0 = r3.mContext;
        r1 = r3.uriString;
        r4.getResourceDrawableUri(r0, r1);
    L_0x004f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.ImageView.setSrc(com.facebook.react.bridge.ReadableMap):void");
    }

    @ReactProp(name = "align")
    public void setAlign(String str) {
        this.mAlign = str;
        invalidate();
    }

    @ReactProp(name = "meetOrSlice")
    public void setMeetOrSlice(int i) {
        this.mMeetOrSlice = i;
        invalidate();
    }

    void draw(Canvas canvas, Paint paint, float f) {
        if (!this.mLoading.get()) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            ImageRequest fromUri = ImageRequest.fromUri(new ImageSource(this.mContext, this.uriString).getUri());
            if (imagePipeline.isInBitmapMemoryCache(fromUri)) {
                tryRenderFromBitmapCache(imagePipeline, fromUri, canvas, paint, f * this.mOpacity);
                return;
            }
            loadBitmap(imagePipeline, fromUri);
        }
    }

    Path getPath(Canvas canvas, Paint paint) {
        Path path = new Path();
        path.addRect(getRect(), Direction.CW);
        return path;
    }

    private void loadBitmap(ImagePipeline imagePipeline, ImageRequest imageRequest) {
        this.mLoading.set(true);
        imagePipeline.fetchDecodedImage(imageRequest, this.mContext).subscribe(new BaseBitmapDataSubscriber() {
            public void onNewResultImpl(Bitmap bitmap) {
                ImageView.this.mLoading.set(false);
                SvgView svgView = ImageView.this.getSvgView();
                if (svgView != null) {
                    svgView.invalidate();
                }
            }

            public void onFailureImpl(DataSource dataSource) {
                ImageView.this.mLoading.set(false);
                FLog.w(ReactConstants.TAG, dataSource.getFailureCause(), "RNSVG: fetchDecodedImage failed!", new Object[0]);
            }
        }, UiThreadImmediateExecutorService.getInstance());
    }

    @Nonnull
    private RectF getRect() {
        double relativeOnWidth = relativeOnWidth(this.mX);
        double relativeOnHeight = relativeOnHeight(this.mY);
        double relativeOnWidth2 = relativeOnWidth(this.mW);
        double relativeOnHeight2 = relativeOnHeight(this.mH);
        if (relativeOnWidth2 == 0.0d) {
            relativeOnWidth2 = (double) (((float) this.mImageWidth) * this.mScale);
        }
        if (relativeOnHeight2 == 0.0d) {
            relativeOnHeight2 = (double) (((float) this.mImageHeight) * this.mScale);
        }
        return new RectF((float) relativeOnWidth, (float) relativeOnHeight, (float) (relativeOnWidth + relativeOnWidth2), (float) (relativeOnHeight + relativeOnHeight2));
    }

    private void doRender(Canvas canvas, Paint paint, Bitmap bitmap, float f) {
        if (this.mImageWidth == 0 || this.mImageHeight == 0) {
            this.mImageWidth = bitmap.getWidth();
            this.mImageHeight = bitmap.getHeight();
        }
        RectF rect = getRect();
        RectF rectF = new RectF(0.0f, 0.0f, (float) this.mImageWidth, (float) this.mImageHeight);
        ViewBox.getTransform(rectF, rect, this.mAlign, this.mMeetOrSlice).mapRect(rectF);
        canvas.clipPath(getPath(canvas, paint));
        Path clipPath = getClipPath(canvas, paint);
        if (clipPath != null) {
            canvas.clipPath(clipPath);
        }
        paint = new Paint();
        paint.setAlpha((int) (f * 255.0f));
        canvas.drawBitmap(bitmap, null, rectF, paint);
        this.mCTM.mapRect(rectF);
        setClientRect(rectF);
    }

    private void tryRenderFromBitmapCache(ImagePipeline imagePipeline, ImageRequest imageRequest, Canvas canvas, Paint paint, float f) {
        DataSource fetchImageFromBitmapCache = imagePipeline.fetchImageFromBitmapCache(imageRequest, this.mContext);
        CloseableReference closeableReference;
        try {
            closeableReference = (CloseableReference) fetchImageFromBitmapCache.getResult();
            if (closeableReference == null) {
                fetchImageFromBitmapCache.close();
                return;
            }
            CloseableImage closeableImage = (CloseableImage) closeableReference.get();
            if (closeableImage instanceof CloseableBitmap) {
                Bitmap underlyingBitmap = ((CloseableBitmap) closeableImage).getUnderlyingBitmap();
                if (underlyingBitmap == null) {
                    CloseableReference.closeSafely(closeableReference);
                    fetchImageFromBitmapCache.close();
                    return;
                }
                doRender(canvas, paint, underlyingBitmap, f);
                CloseableReference.closeSafely(closeableReference);
                fetchImageFromBitmapCache.close();
                return;
            }
            CloseableReference.closeSafely(closeableReference);
            fetchImageFromBitmapCache.close();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        } catch (Throwable e2) {
            try {
                throw new IllegalStateException(e2);
            } catch (Throwable th) {
                fetchImageFromBitmapCache.close();
            }
        } catch (Throwable th2) {
            CloseableReference.closeSafely(closeableReference);
        }
    }
}
