package com.google.android.cameraview;

import android.graphics.SurfaceTexture;
import android.media.CamcorderProfile;
import android.os.Handler;
import android.view.View;
import com.facebook.react.bridge.ReadableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;

abstract class CameraViewImpl {
    protected final Handler mBgHandler;
    protected final Callback mCallback;
    protected final PreviewImpl mPreview;

    interface Callback {
        void onCameraClosed();

        void onCameraOpened();

        void onFramePreview(byte[] bArr, int i, int i2, int i3);

        void onMountError();

        void onPictureTaken(byte[] bArr, int i);

        void onRecordingEnd();

        void onRecordingStart(String str, int i, int i2);

        void onVideoRecorded(String str, int i, int i2);
    }

    abstract AspectRatio getAspectRatio();

    abstract boolean getAutoFocus();

    abstract SortedSet<Size> getAvailablePictureSizes(AspectRatio aspectRatio);

    abstract String getCameraId();

    abstract List<Properties> getCameraIds();

    abstract int getCameraOrientation();

    abstract float getExposureCompensation();

    abstract int getFacing();

    abstract int getFlash();

    abstract float getFocusDepth();

    abstract Size getPictureSize();

    abstract boolean getPlaySoundOnCapture();

    public abstract Size getPreviewSize();

    abstract boolean getScanning();

    abstract Set<AspectRatio> getSupportedAspectRatios();

    public abstract ArrayList<int[]> getSupportedPreviewFpsRange();

    abstract int getWhiteBalance();

    abstract float getZoom();

    abstract boolean isCameraOpened();

    public abstract void pausePreview();

    abstract boolean record(String str, int i, int i2, boolean z, CamcorderProfile camcorderProfile, int i3, int i4);

    public abstract void resumePreview();

    abstract boolean setAspectRatio(AspectRatio aspectRatio);

    abstract void setAutoFocus(boolean z);

    abstract void setCameraId(String str);

    abstract void setDeviceOrientation(int i);

    abstract void setDisplayOrientation(int i);

    abstract void setExposureCompensation(float f);

    abstract void setFacing(int i);

    abstract void setFlash(int i);

    abstract void setFocusArea(float f, float f2);

    abstract void setFocusDepth(float f);

    abstract void setPictureSize(Size size);

    abstract void setPlaySoundOnCapture(boolean z);

    public abstract void setPreviewTexture(SurfaceTexture surfaceTexture);

    abstract void setScanning(boolean z);

    abstract void setWhiteBalance(int i);

    abstract void setZoom(float f);

    abstract boolean start();

    abstract void stop();

    abstract void stopRecording();

    abstract void takePicture(ReadableMap readableMap);

    CameraViewImpl(Callback callback, PreviewImpl previewImpl, Handler handler) {
        this.mCallback = callback;
        this.mPreview = previewImpl;
        this.mBgHandler = handler;
    }

    View getView() {
        return this.mPreview.getView();
    }
}
