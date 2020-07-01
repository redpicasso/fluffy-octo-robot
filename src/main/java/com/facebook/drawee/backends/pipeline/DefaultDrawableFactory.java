package com.facebook.drawee.backends.pipeline;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.OrientedDrawable;
import com.facebook.imagepipeline.drawable.DrawableFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import javax.annotation.Nullable;

public class DefaultDrawableFactory implements DrawableFactory {
    @Nullable
    private final DrawableFactory mAnimatedDrawableFactory;
    private final Resources mResources;

    public boolean supportsImageType(CloseableImage closeableImage) {
        return true;
    }

    public DefaultDrawableFactory(Resources resources, @Nullable DrawableFactory drawableFactory) {
        this.mResources = resources;
        this.mAnimatedDrawableFactory = drawableFactory;
    }

    @Nullable
    public Drawable createDrawable(CloseableImage closeableImage) {
        Drawable bitmapDrawable;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("DefaultDrawableFactory#createDrawable");
            }
            if (closeableImage instanceof CloseableStaticBitmap) {
                CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) closeableImage;
                bitmapDrawable = new BitmapDrawable(this.mResources, closeableStaticBitmap.getUnderlyingBitmap());
                if (!hasTransformableRotationAngle(closeableStaticBitmap) && !hasTransformableExifOrientation(closeableStaticBitmap)) {
                    return bitmapDrawable;
                }
                Drawable orientedDrawable = new OrientedDrawable(bitmapDrawable, closeableStaticBitmap.getRotationAngle(), closeableStaticBitmap.getExifOrientation());
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
                return orientedDrawable;
            } else if (this.mAnimatedDrawableFactory == null || !this.mAnimatedDrawableFactory.supportsImageType(closeableImage)) {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
                return null;
            } else {
                Drawable createDrawable = this.mAnimatedDrawableFactory.createDrawable(closeableImage);
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
                return createDrawable;
            }
        } finally {
            bitmapDrawable = FrescoSystrace.isTracing();
            if (bitmapDrawable != null) {
                FrescoSystrace.endSection();
            }
        }
    }

    private static boolean hasTransformableRotationAngle(CloseableStaticBitmap closeableStaticBitmap) {
        return (closeableStaticBitmap.getRotationAngle() == 0 || closeableStaticBitmap.getRotationAngle() == -1) ? false : true;
    }

    private static boolean hasTransformableExifOrientation(CloseableStaticBitmap closeableStaticBitmap) {
        if (closeableStaticBitmap.getExifOrientation() == 1 || closeableStaticBitmap.getExifOrientation() == 0) {
            return false;
        }
        return true;
    }
}