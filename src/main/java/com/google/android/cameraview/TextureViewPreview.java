package com.google.android.cameraview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.ViewGroup;
import org.reactnative.camera.R;

@TargetApi(14)
class TextureViewPreview extends PreviewImpl {
    private int mDisplayOrientation;
    private final TextureView mTextureView;

    TextureViewPreview(Context context, ViewGroup viewGroup) {
        this.mTextureView = (TextureView) View.inflate(context, R.layout.texture_view, viewGroup).findViewById(R.id.texture_view);
        this.mTextureView.setSurfaceTextureListener(new SurfaceTextureListener() {
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }

            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                TextureViewPreview.this.setSize(i, i2);
                TextureViewPreview.this.configureTransform();
                TextureViewPreview.this.dispatchSurfaceChanged();
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
                TextureViewPreview.this.setSize(i, i2);
                TextureViewPreview.this.configureTransform();
                TextureViewPreview.this.dispatchSurfaceChanged();
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                TextureViewPreview.this.setSize(0, 0);
                TextureViewPreview.this.dispatchSurfaceDestroyed();
                return true;
            }
        });
    }

    @TargetApi(15)
    void setBufferSize(int i, int i2) {
        this.mTextureView.getSurfaceTexture().setDefaultBufferSize(i, i2);
    }

    Surface getSurface() {
        return new Surface(this.mTextureView.getSurfaceTexture());
    }

    SurfaceTexture getSurfaceTexture() {
        return this.mTextureView.getSurfaceTexture();
    }

    View getView() {
        return this.mTextureView;
    }

    Class getOutputClass() {
        return SurfaceTexture.class;
    }

    void setDisplayOrientation(int i) {
        this.mDisplayOrientation = i;
        configureTransform();
    }

    boolean isReady() {
        return this.mTextureView.getSurfaceTexture() != null;
    }

    void configureTransform() {
        Matrix matrix = new Matrix();
        int i = this.mDisplayOrientation;
        if (i % 180 == 90) {
            i = getWidth();
            int height = getHeight();
            r6 = new float[8];
            float f = (float) i;
            r6[2] = f;
            r6[3] = 0.0f;
            r6[4] = 0.0f;
            float f2 = (float) height;
            r6[5] = f2;
            r6[6] = f;
            r6[7] = f2;
            matrix.setPolyToPoly(r6, 0, this.mDisplayOrientation == 90 ? new float[]{0.0f, f2, 0.0f, 0.0f, f, f2, f, 0.0f} : new float[]{f, 0.0f, f, f2, 0.0f, 0.0f, 0.0f, f2}, 0, 4);
        } else if (i == 180) {
            matrix.postRotate(180.0f, (float) (getWidth() / 2), (float) (getHeight() / 2));
        }
        this.mTextureView.setTransform(matrix);
    }
}
