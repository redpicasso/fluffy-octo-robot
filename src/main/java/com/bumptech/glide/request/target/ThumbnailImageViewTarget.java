package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import androidx.annotation.Nullable;

public abstract class ThumbnailImageViewTarget<T> extends ImageViewTarget<T> {
    protected abstract Drawable getDrawable(T t);

    public ThumbnailImageViewTarget(ImageView imageView) {
        super(imageView);
    }

    @Deprecated
    public ThumbnailImageViewTarget(ImageView imageView, boolean z) {
        super(imageView, z);
    }

    protected void setResource(@Nullable T t) {
        LayoutParams layoutParams = ((ImageView) this.view).getLayoutParams();
        Drawable drawable = getDrawable(t);
        if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
            drawable = new FixedSizeDrawable(drawable, layoutParams.width, layoutParams.height);
        }
        ((ImageView) this.view).setImageDrawable(drawable);
    }
}
