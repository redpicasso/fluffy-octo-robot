package com.facebook.drawee.drawable;

import android.annotation.SuppressLint;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class DrawableProperties {
    private static final int UNSET = -1;
    private int mAlpha = -1;
    private ColorFilter mColorFilter = null;
    private int mDither = -1;
    private int mFilterBitmap = -1;
    private boolean mIsSetColorFilter = false;

    public void setAlpha(int i) {
        this.mAlpha = i;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        this.mIsSetColorFilter = true;
    }

    public void setDither(boolean z) {
        this.mDither = z;
    }

    public void setFilterBitmap(boolean z) {
        this.mFilterBitmap = z;
    }

    @SuppressLint({"Range"})
    public void applyTo(Drawable drawable) {
        if (drawable != null) {
            int i = this.mAlpha;
            if (i != -1) {
                drawable.setAlpha(i);
            }
            if (this.mIsSetColorFilter) {
                drawable.setColorFilter(this.mColorFilter);
            }
            i = this.mDither;
            boolean z = true;
            if (i != -1) {
                drawable.setDither(i != 0);
            }
            i = this.mFilterBitmap;
            if (i != -1) {
                if (i == 0) {
                    z = false;
                }
                drawable.setFilterBitmap(z);
            }
        }
    }
}
