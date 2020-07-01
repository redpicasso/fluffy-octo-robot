package com.facebook.imagepipeline.filter;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import java.util.Locale;

public abstract class IterativeBoxBlurFilter {
    private static final String TAG = "IterativeBoxBlurFilter";

    private static int bound(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public static void boxBlurBitmapInPlace(Bitmap bitmap, int i, int i2) {
        Preconditions.checkNotNull(bitmap);
        Preconditions.checkArgument(bitmap.isMutable());
        Preconditions.checkArgument(((float) bitmap.getHeight()) <= 2048.0f);
        Preconditions.checkArgument(((float) bitmap.getWidth()) <= 2048.0f);
        boolean z = i2 > 0 && i2 <= 25;
        Preconditions.checkArgument(z);
        Preconditions.checkArgument(i > 0);
        try {
            fastBoxBlur(bitmap, i, i2);
        } catch (OutOfMemoryError e) {
            FLog.e(TAG, String.format((Locale) null, "OOM: %d iterations on %dx%d with %d radius", new Object[]{Integer.valueOf(i), Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight()), Integer.valueOf(i2)}));
            throw e;
        }
    }

    private static void fastBoxBlur(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Object obj = new int[(width * height)];
        bitmap.getPixels(obj, 0, width, 0, 0, width, height);
        int i3 = i2 + 1;
        int i4 = i3 + i2;
        int[] iArr = new int[(i4 * 256)];
        int i5 = 1;
        while (true) {
            int i6 = 0;
            if (i5 > 255) {
                break;
            }
            while (i6 < i4) {
                iArr[i3] = i5;
                i3++;
                i6++;
            }
            i5++;
        }
        Object obj2 = new int[Math.max(width, height)];
        int i7 = i;
        for (int i8 = 0; i8 < i7; i8++) {
            int i9;
            for (i9 = 0; i9 < height; i9++) {
                internalHorizontalBlur(obj, obj2, width, i9, i4, iArr);
                System.arraycopy(obj2, 0, obj, i9 * width, width);
            }
            i9 = 0;
            while (i9 < width) {
                int i10 = i9;
                internalVerticalBlur(obj, obj2, width, height, i9, i4, iArr);
                i5 = i10;
                for (i3 = 0; i3 < height; i3++) {
                    obj[i5] = obj2[i3];
                    i5 += width;
                }
                i9 = i10 + 1;
            }
        }
        bitmap.setPixels(obj, 0, width, 0, 0, width, height);
    }

    private static void internalHorizontalBlur(int[] iArr, int[] iArr2, int i, int i2, int i3, int[] iArr3) {
        int i4 = i * i2;
        i2 = ((i2 + 1) * i) - 1;
        int i5 = i3 >> 1;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        for (int i10 = -i5; i10 < i + i5; i10++) {
            int i11 = iArr[bound(i4 + i10, i4, i2)];
            i6 += (i11 >> 16) & 255;
            i7 += (i11 >> 8) & 255;
            i8 += i11 & 255;
            i9 += i11 >>> 24;
            if (i10 >= i5) {
                iArr2[i10 - i5] = (((iArr3[i9] << 24) | (iArr3[i6] << 16)) | (iArr3[i7] << 8)) | iArr3[i8];
                i11 = iArr[bound((i10 - (i3 - 1)) + i4, i4, i2)];
                i6 -= (i11 >> 16) & 255;
                i7 -= (i11 >> 8) & 255;
                i8 -= i11 & 255;
                i9 -= i11 >>> 24;
            }
        }
    }

    private static void internalVerticalBlur(int[] iArr, int[] iArr2, int i, int i2, int i3, int i4, int[] iArr3) {
        i2 = ((i2 - 1) * i) + i3;
        int i5 = (i4 >> 1) * i;
        i4 = (i4 - 1) * i;
        int i6 = i3 - i5;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        while (i6 <= i2 + i5) {
            int i12 = iArr[bound(i6, i3, i2)];
            i7 += (i12 >> 16) & 255;
            i8 += (i12 >> 8) & 255;
            i9 += i12 & 255;
            i10 += i12 >>> 24;
            if (i6 - i5 >= i3) {
                iArr2[i11] = (((iArr3[i10] << 24) | (iArr3[i7] << 16)) | (iArr3[i8] << 8)) | iArr3[i9];
                i11++;
                i12 = iArr[bound(i6 - i4, i3, i2)];
                i7 -= (i12 >> 16) & 255;
                i8 -= (i12 >> 8) & 255;
                i9 -= i12 & 255;
                i10 -= i12 >>> 24;
            }
            i6 += i;
        }
    }
}
