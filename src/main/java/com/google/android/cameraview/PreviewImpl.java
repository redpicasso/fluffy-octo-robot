package com.google.android.cameraview;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

abstract class PreviewImpl {
    private Callback mCallback;
    private int mHeight;
    private int mWidth;

    interface Callback {
        void onSurfaceChanged();

        void onSurfaceDestroyed();
    }

    abstract Class getOutputClass();

    abstract Surface getSurface();

    SurfaceHolder getSurfaceHolder() {
        return null;
    }

    Object getSurfaceTexture() {
        return null;
    }

    abstract View getView();

    abstract boolean isReady();

    void setBufferSize(int i, int i2) {
    }

    abstract void setDisplayOrientation(int i);

    PreviewImpl() {
    }

    void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    protected void dispatchSurfaceChanged() {
        this.mCallback.onSurfaceChanged();
    }

    protected void dispatchSurfaceDestroyed() {
        this.mCallback.onSurfaceDestroyed();
    }

    void setSize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    int getWidth() {
        return this.mWidth;
    }

    int getHeight() {
        return this.mHeight;
    }
}
