package com.facebook.imagepipeline.filter;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;

public final class InPlaceRoundFilter {
    private InPlaceRoundFilter() {
    }

    public static void roundBitmapInPlace(Bitmap bitmap) {
        Preconditions.checkNotNull(bitmap);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int min = Math.min(width, height) / 2;
        int i = width / 2;
        int i2 = height / 2;
        if (min != 0) {
            int i3;
            int i4;
            int i5;
            int i6;
            Preconditions.checkArgument(min >= 1);
            boolean z = width > 0 && ((float) width) <= 2048.0f;
            Preconditions.checkArgument(z);
            boolean z2 = height > 0 && ((float) height) <= 2048.0f;
            Preconditions.checkArgument(z2);
            z2 = i > 0 && i < width;
            Preconditions.checkArgument(z2);
            z2 = i2 > 0 && i2 < height;
            Preconditions.checkArgument(z2);
            Object obj = new int[(width * height)];
            bitmap.getPixels(obj, 0, width, 0, 0, width, height);
            int i7 = min - 1;
            z = i - i7 >= 0 && i2 - i7 >= 0 && i + i7 < width && i2 + i7 < height;
            Preconditions.checkArgument(z);
            int i8 = (-min) * 2;
            Object obj2 = new int[width];
            int i9 = i8 + 1;
            int i10 = 0;
            int i11 = 1;
            int i12 = 1;
            while (i7 >= i10) {
                i3 = i + i7;
                int i13 = i - i7;
                int i14 = i + i10;
                i4 = min;
                min = i - i10;
                int i15 = i2 + i7;
                int i16 = i2 - i7;
                int i17 = i;
                i = i2 + i10;
                i5 = i2 - i10;
                boolean z3 = i7 >= 0 && i14 < width && min >= 0 && i < height && i5 >= 0;
                Preconditions.checkArgument(z3);
                i *= width;
                i6 = height;
                height = width * i5;
                i5 = i2;
                i2 = width * i15;
                i15 = i8;
                i8 = width * i16;
                i16 = i11;
                System.arraycopy(obj2, 0, obj, i, i13);
                System.arraycopy(obj2, 0, obj, height, i13);
                System.arraycopy(obj2, 0, obj, i2, min);
                System.arraycopy(obj2, 0, obj, i8, min);
                min = width - i3;
                System.arraycopy(obj2, 0, obj, i + i3, min);
                System.arraycopy(obj2, 0, obj, height + i3, min);
                i3 = width - i14;
                System.arraycopy(obj2, 0, obj, i2 + i14, i3);
                System.arraycopy(obj2, 0, obj, i8 + i14, i3);
                if (i9 <= 0) {
                    i10++;
                    i12 += 2;
                    i9 += i12;
                }
                if (i9 > 0) {
                    i7--;
                    i11 = i16 + 2;
                    i9 += i11 + i15;
                    min = i4;
                    i8 = i15;
                } else {
                    min = i4;
                    i8 = i15;
                    i11 = i16;
                }
                i = i17;
                i2 = i5;
                height = i6;
            }
            i6 = height;
            i4 = min;
            i5 = i2;
            for (i2 = i5 - i4; i2 >= 0; i2--) {
                System.arraycopy(obj2, 0, obj, i2 * width, width);
            }
            i3 = i6;
            for (i2 = i5 + i4; i2 < i3; i2++) {
                System.arraycopy(obj2, 0, obj, i2 * width, width);
            }
            bitmap.setPixels(obj, 0, width, 0, 0, width, i3);
        }
    }
}
