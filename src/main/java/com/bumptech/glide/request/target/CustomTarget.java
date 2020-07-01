package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.util.Util;

public abstract class CustomTarget<T> implements Target<T> {
    private final int height;
    @Nullable
    private Request request;
    private final int width;

    public void onDestroy() {
    }

    public void onLoadFailed(@Nullable Drawable drawable) {
    }

    public void onLoadStarted(@Nullable Drawable drawable) {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public final void removeCallback(@NonNull SizeReadyCallback sizeReadyCallback) {
    }

    public CustomTarget() {
        this(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public CustomTarget(int i, int i2) {
        if (Util.isValidDimensions(i, i2)) {
            this.width = i;
            this.height = i2;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given width: ");
        stringBuilder.append(i);
        stringBuilder.append(" and height: ");
        stringBuilder.append(i2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final void getSize(@NonNull SizeReadyCallback sizeReadyCallback) {
        sizeReadyCallback.onSizeReady(this.width, this.height);
    }

    public final void setRequest(@Nullable Request request) {
        this.request = request;
    }

    @Nullable
    public final Request getRequest() {
        return this.request;
    }
}
