package com.facebook.imagepipeline.transcoder;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import javax.annotation.Nullable;

public class DownsampleUtil {
    public static final int DEFAULT_SAMPLE_SIZE = 1;
    private static final float INTERVAL_ROUNDING = 0.33333334f;

    @VisibleForTesting
    public static int ratioToSampleSizeJPEG(float f) {
        if (f > 0.6666667f) {
            return 1;
        }
        int i = 2;
        while (true) {
            int i2 = i * 2;
            double d = 1.0d / ((double) i2);
            if (d + (0.3333333432674408d * d) <= ((double) f)) {
                return i;
            }
            i = i2;
        }
    }

    @VisibleForTesting
    public static int roundToPowerOfTwo(int i) {
        int i2 = 1;
        while (i2 < i) {
            i2 *= 2;
        }
        return i2;
    }

    private DownsampleUtil() {
    }

    public static int determineSampleSize(RotationOptions rotationOptions, @Nullable ResizeOptions resizeOptions, EncodedImage encodedImage, int i) {
        if (!EncodedImage.isMetaDataAvailable(encodedImage)) {
            return 1;
        }
        int ratioToSampleSizeJPEG;
        float determineDownsampleRatio = determineDownsampleRatio(rotationOptions, resizeOptions, encodedImage);
        if (encodedImage.getImageFormat() == DefaultImageFormats.JPEG) {
            ratioToSampleSizeJPEG = ratioToSampleSizeJPEG(determineDownsampleRatio);
        } else {
            ratioToSampleSizeJPEG = ratioToSampleSize(determineDownsampleRatio);
        }
        int max = Math.max(encodedImage.getHeight(), encodedImage.getWidth());
        float f = resizeOptions != null ? resizeOptions.maxBitmapSize : (float) i;
        while (((float) (max / ratioToSampleSizeJPEG)) > f) {
            ratioToSampleSizeJPEG = encodedImage.getImageFormat() == DefaultImageFormats.JPEG ? ratioToSampleSizeJPEG * 2 : ratioToSampleSizeJPEG + 1;
        }
        return ratioToSampleSizeJPEG;
    }

    @VisibleForTesting
    public static float determineDownsampleRatio(RotationOptions rotationOptions, @Nullable ResizeOptions resizeOptions, EncodedImage encodedImage) {
        Preconditions.checkArgument(EncodedImage.isMetaDataAvailable(encodedImage));
        if (resizeOptions == null || resizeOptions.height <= 0 || resizeOptions.width <= 0 || encodedImage.getWidth() == 0 || encodedImage.getHeight() == 0) {
            return 1.0f;
        }
        int rotationAngle = getRotationAngle(rotationOptions, encodedImage);
        Object obj = (rotationAngle == 90 || rotationAngle == 270) ? 1 : null;
        FLog.v("DownsampleUtil", "Downsample - Specified size: %dx%d, image size: %dx%d ratio: %.1f x %.1f, ratio: %.3f", Integer.valueOf(resizeOptions.width), Integer.valueOf(resizeOptions.height), Integer.valueOf(obj != null ? encodedImage.getHeight() : encodedImage.getWidth()), Integer.valueOf(obj != null ? encodedImage.getWidth() : encodedImage.getHeight()), Float.valueOf(((float) resizeOptions.width) / ((float) (obj != null ? encodedImage.getHeight() : encodedImage.getWidth()))), Float.valueOf(((float) resizeOptions.height) / ((float) (obj != null ? encodedImage.getWidth() : encodedImage.getHeight()))), Float.valueOf(Math.max(((float) resizeOptions.width) / ((float) (obj != null ? encodedImage.getHeight() : encodedImage.getWidth())), ((float) resizeOptions.height) / ((float) (obj != null ? encodedImage.getWidth() : encodedImage.getHeight())))));
        return Math.max(((float) resizeOptions.width) / ((float) (obj != null ? encodedImage.getHeight() : encodedImage.getWidth())), ((float) resizeOptions.height) / ((float) (obj != null ? encodedImage.getWidth() : encodedImage.getHeight())));
    }

    @VisibleForTesting
    public static int ratioToSampleSize(float f) {
        if (f > 0.6666667f) {
            return 1;
        }
        int i = 2;
        while (true) {
            double d = (double) i;
            if ((1.0d / d) + ((1.0d / (Math.pow(d, 2.0d) - d)) * 0.3333333432674408d) <= ((double) f)) {
                return i - 1;
            }
            i++;
        }
    }

    private static int getRotationAngle(RotationOptions rotationOptions, EncodedImage encodedImage) {
        boolean z = false;
        if (!rotationOptions.useImageMetadata()) {
            return 0;
        }
        int rotationAngle = encodedImage.getRotationAngle();
        if (rotationAngle == 0 || rotationAngle == 90 || rotationAngle == 180 || rotationAngle == 270) {
            z = true;
        }
        Preconditions.checkArgument(z);
        return rotationAngle;
    }
}
