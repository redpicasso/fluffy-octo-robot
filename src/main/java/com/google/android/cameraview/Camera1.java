package com.google.android.cameraview;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.media.MediaActionSound;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import androidx.collection.SparseArrayCompat;
import androidx.core.app.NotificationManagerCompat;
import com.brentvatne.react.ReactVideoView;
import com.drew.metadata.exif.makernotes.LeicaMakernoteDirectory;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ViewProps;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactnative.camera.utils.ObjectUtils;

class Camera1 extends CameraViewImpl implements OnInfoListener, OnErrorListener, PreviewCallback {
    private static final int DELAY_MILLIS_BEFORE_RESETTING_FOCUS = 3000;
    private static final SparseArrayCompat<String> FLASH_MODES = new SparseArrayCompat();
    private static final int FOCUS_AREA_SIZE_DEFAULT = 300;
    private static final int FOCUS_METERING_AREA_WEIGHT_DEFAULT = 1000;
    private static final int INVALID_CAMERA_ID = -1;
    private static final SparseArrayCompat<String> WB_MODES = new SparseArrayCompat();
    private String _mCameraId;
    private final AtomicBoolean isPictureCaptureInProgress = new AtomicBoolean(false);
    private AspectRatio mAspectRatio;
    private boolean mAutoFocus;
    Camera mCamera;
    private int mCameraId;
    private final CameraInfo mCameraInfo = new CameraInfo();
    private Parameters mCameraParameters;
    private int mDeviceOrientation;
    private int mDisplayOrientation;
    private float mExposure;
    private int mFacing;
    private int mFlash;
    private Handler mHandler = new Handler();
    private boolean mIsPreviewActive = false;
    private final AtomicBoolean mIsRecording = new AtomicBoolean(false);
    private boolean mIsScanning;
    private MediaRecorder mMediaRecorder;
    private int mOrientation = 0;
    private Size mPictureSize;
    private final SizeMap mPictureSizes = new SizeMap();
    private Boolean mPlaySoundOnCapture = Boolean.valueOf(false);
    private final SizeMap mPreviewSizes = new SizeMap();
    private SurfaceTexture mPreviewTexture;
    private boolean mShowingPreview = true;
    private String mVideoPath;
    private int mWhiteBalance;
    private float mZoom;
    private boolean mustUpdateSurface;
    MediaActionSound sound = new MediaActionSound();
    private boolean surfaceWasDestroyed;

    private boolean isLandscape(int i) {
        return i == 90 || i == 270;
    }

    int displayOrientationToOrientationEnum(int i) {
        return i != 0 ? i != 90 ? i != 180 ? i != 270 ? 1 : 3 : 2 : 4 : 1;
    }

    float getFocusDepth() {
        return 0.0f;
    }

    int orientationEnumToRotation(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? 1 : 90 : 270 : 180 : 0;
    }

    public void setFocusDepth(float f) {
    }

    static {
        FLASH_MODES.put(0, "off");
        FLASH_MODES.put(1, ViewProps.ON);
        FLASH_MODES.put(2, "torch");
        String str = "auto";
        FLASH_MODES.put(3, str);
        FLASH_MODES.put(4, "red-eye");
        WB_MODES.put(0, str);
        WB_MODES.put(1, "cloudy-daylight");
        WB_MODES.put(2, "daylight");
        WB_MODES.put(3, "shade");
        WB_MODES.put(4, "fluorescent");
        WB_MODES.put(5, "incandescent");
    }

    Camera1(Callback callback, PreviewImpl previewImpl, Handler handler) {
        super(callback, previewImpl, handler);
        previewImpl.setCallback(new Callback() {
            public void onSurfaceChanged() {
                synchronized (Camera1.this) {
                    if (Camera1.this.surfaceWasDestroyed) {
                        Camera1.this.mBgHandler.post(new Runnable() {
                            public void run() {
                                Camera1.this.start();
                            }
                        });
                    } else {
                        Camera1.this.updateSurface();
                    }
                }
            }

            public void onSurfaceDestroyed() {
                synchronized (Camera1.this) {
                    if (Camera1.this.mCamera != null) {
                        Camera1.this.surfaceWasDestroyed = true;
                        try {
                            Camera1.this.mCamera.setPreviewCallback(null);
                            Camera1.this.mCamera.setPreviewDisplay(null);
                        } catch (Throwable e) {
                            Log.e("CAMERA_1::", "onSurfaceDestroyed preview cleanup failed", e);
                        }
                    }
                }
                Camera1.this.mBgHandler.post(new Runnable() {
                    public void run() {
                        Camera1.this.stop();
                    }
                });
            }
        });
    }

    private void updateSurface() {
        if (this.mCamera == null) {
            return;
        }
        if (this.isPictureCaptureInProgress.get() || this.mIsRecording.get()) {
            this.mustUpdateSurface = true;
        } else {
            this.mBgHandler.post(new Runnable() {
                public void run() {
                    synchronized (Camera1.this) {
                        if (Camera1.this.mCamera != null) {
                            Camera1.this.mustUpdateSurface = false;
                            Camera1.this.setUpPreview();
                            Camera1.this.adjustCameraParameters();
                            if (Camera1.this.mShowingPreview) {
                                Camera1.this.startCameraPreview();
                            }
                        }
                    }
                }
            });
        }
    }

    /* JADX WARNING: Missing block: B:13:0x0025, code:
            return true;
     */
    boolean start() {
        /*
        r2 = this;
        monitor-enter(r2);
        r2.chooseCamera();	 Catch:{ all -> 0x0026 }
        r0 = r2.openCamera();	 Catch:{ all -> 0x0026 }
        r1 = 1;
        if (r0 != 0) goto L_0x0012;
    L_0x000b:
        r0 = r2.mCallback;	 Catch:{ all -> 0x0026 }
        r0.onMountError();	 Catch:{ all -> 0x0026 }
        monitor-exit(r2);	 Catch:{ all -> 0x0026 }
        return r1;
    L_0x0012:
        r0 = r2.mPreview;	 Catch:{ all -> 0x0026 }
        r0 = r0.isReady();	 Catch:{ all -> 0x0026 }
        if (r0 == 0) goto L_0x0024;
    L_0x001a:
        r2.setUpPreview();	 Catch:{ all -> 0x0026 }
        r0 = r2.mShowingPreview;	 Catch:{ all -> 0x0026 }
        if (r0 == 0) goto L_0x0024;
    L_0x0021:
        r2.startCameraPreview();	 Catch:{ all -> 0x0026 }
    L_0x0024:
        monitor-exit(r2);	 Catch:{ all -> 0x0026 }
        return r1;
    L_0x0026:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0026 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.cameraview.Camera1.start():boolean");
    }

    void stop() {
        synchronized (this) {
            if (this.mMediaRecorder != null) {
                try {
                    this.mMediaRecorder.stop();
                } catch (Throwable e) {
                    Log.e("CAMERA_1::", "mMediaRecorder.stop() failed", e);
                }
                try {
                    this.mMediaRecorder.reset();
                    this.mMediaRecorder.release();
                } catch (Throwable e2) {
                    Log.e("CAMERA_1::", "mMediaRecorder.release() failed", e2);
                }
                this.mMediaRecorder = null;
                if (this.mIsRecording.get()) {
                    this.mCallback.onRecordingEnd();
                    int displayOrientationToOrientationEnum = displayOrientationToOrientationEnum(this.mDeviceOrientation);
                    this.mCallback.onVideoRecorded(this.mVideoPath, this.mOrientation != 0 ? this.mOrientation : displayOrientationToOrientationEnum, displayOrientationToOrientationEnum);
                }
            }
            if (this.mCamera != null) {
                this.mIsPreviewActive = false;
                try {
                    this.mCamera.stopPreview();
                    this.mCamera.setPreviewCallback(null);
                } catch (Throwable e22) {
                    Log.e("CAMERA_1::", "stop preview cleanup failed", e22);
                }
            }
            releaseCamera();
        }
        return;
    }

    @SuppressLint({"NewApi"})
    void setUpPreview() {
        try {
            this.surfaceWasDestroyed = false;
            if (this.mCamera == null) {
                return;
            }
            if (this.mPreviewTexture != null) {
                this.mCamera.setPreviewTexture(this.mPreviewTexture);
            } else if (this.mPreview.getOutputClass() == SurfaceHolder.class) {
                Object obj = (!this.mIsPreviewActive || VERSION.SDK_INT >= 14) ? null : 1;
                if (obj != null) {
                    this.mCamera.stopPreview();
                    this.mIsPreviewActive = false;
                }
                this.mCamera.setPreviewDisplay(this.mPreview.getSurfaceHolder());
                if (obj != null) {
                    startCameraPreview();
                }
            } else {
                this.mCamera.setPreviewTexture((SurfaceTexture) this.mPreview.getSurfaceTexture());
            }
        } catch (Throwable e) {
            Log.e("CAMERA_1::", "setUpPreview failed", e);
        }
    }

    private void startCameraPreview() {
        if (!this.mIsPreviewActive) {
            Camera camera = this.mCamera;
            if (camera != null) {
                try {
                    this.mIsPreviewActive = true;
                    camera.startPreview();
                    if (this.mIsScanning) {
                        this.mCamera.setPreviewCallback(this);
                    }
                } catch (Throwable e) {
                    this.mIsPreviewActive = false;
                    Log.e("CAMERA_1::", "startCameraPreview failed", e);
                }
            }
        }
    }

    public void resumePreview() {
        this.mBgHandler.post(new Runnable() {
            public void run() {
                synchronized (this) {
                    Camera1.this.mShowingPreview = true;
                    Camera1.this.startCameraPreview();
                }
            }
        });
    }

    public void pausePreview() {
        synchronized (this) {
            this.mIsPreviewActive = false;
            this.mShowingPreview = false;
            if (this.mCamera != null) {
                this.mCamera.stopPreview();
            }
        }
    }

    boolean isCameraOpened() {
        return this.mCamera != null;
    }

    void setFacing(int i) {
        if (this.mFacing != i) {
            this.mFacing = i;
            this.mBgHandler.post(new Runnable() {
                public void run() {
                    if (Camera1.this.isCameraOpened()) {
                        Camera1.this.stop();
                        Camera1.this.start();
                    }
                }
            });
        }
    }

    int getFacing() {
        return this.mFacing;
    }

    void setCameraId(String str) {
        if (!ObjectUtils.equals(this._mCameraId, str)) {
            this._mCameraId = str;
            if (!ObjectUtils.equals(this._mCameraId, String.valueOf(this.mCameraId))) {
                this.mBgHandler.post(new Runnable() {
                    public void run() {
                        if (Camera1.this.isCameraOpened()) {
                            Camera1.this.stop();
                            Camera1.this.start();
                        }
                    }
                });
            }
        }
    }

    String getCameraId() {
        return this._mCameraId;
    }

    Set<AspectRatio> getSupportedAspectRatios() {
        SizeMap sizeMap = this.mPreviewSizes;
        for (AspectRatio aspectRatio : sizeMap.ratios()) {
            if (this.mPictureSizes.sizes(aspectRatio) == null) {
                sizeMap.remove(aspectRatio);
            }
        }
        return sizeMap.ratios();
    }

    List<Properties> getCameraIds() {
        List<Properties> arrayList = new ArrayList();
        CameraInfo cameraInfo = new CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Properties properties = new Properties();
            Camera.getCameraInfo(i, cameraInfo);
            properties.put("id", String.valueOf(i));
            properties.put("type", String.valueOf(cameraInfo.facing));
            arrayList.add(properties);
        }
        return arrayList;
    }

    SortedSet<Size> getAvailablePictureSizes(AspectRatio aspectRatio) {
        return this.mPictureSizes.sizes(aspectRatio);
    }

    void setPictureSize(Size size) {
        if (size == null) {
            AspectRatio aspectRatio = this.mAspectRatio;
            if (aspectRatio != null) {
                SortedSet sizes = this.mPictureSizes.sizes(aspectRatio);
                if (!(sizes == null || sizes.isEmpty())) {
                    this.mPictureSize = (Size) sizes.last();
                }
            } else {
                return;
            }
        }
        this.mPictureSize = size;
        synchronized (this) {
            if (!(this.mCameraParameters == null || this.mCamera == null)) {
                this.mCameraParameters.setPictureSize(this.mPictureSize.getWidth(), this.mPictureSize.getHeight());
                try {
                    this.mCamera.setParameters(this.mCameraParameters);
                } catch (Throwable e) {
                    Log.e("CAMERA_1::", "setParameters failed", e);
                }
            }
        }
    }

    Size getPictureSize() {
        return this.mPictureSize;
    }

    boolean setAspectRatio(AspectRatio aspectRatio) {
        if (this.mAspectRatio == null || !isCameraOpened()) {
            this.mAspectRatio = aspectRatio;
            return true;
        }
        if (!this.mAspectRatio.equals(aspectRatio)) {
            if (this.mPreviewSizes.sizes(aspectRatio) == null) {
                Log.w("CAMERA_1::", "setAspectRatio received an unsupported value and will be ignored.");
            } else {
                this.mAspectRatio = aspectRatio;
                this.mBgHandler.post(new Runnable() {
                    public void run() {
                        synchronized (Camera1.this) {
                            if (Camera1.this.mCamera != null) {
                                Camera1.this.adjustCameraParameters();
                            }
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }

    AspectRatio getAspectRatio() {
        return this.mAspectRatio;
    }

    void setAutoFocus(boolean z) {
        if (this.mAutoFocus != z) {
            synchronized (this) {
                if (setAutoFocusInternal(z)) {
                    try {
                        if (this.mCamera != null) {
                            this.mCamera.setParameters(this.mCameraParameters);
                        }
                    } catch (Throwable e) {
                        Log.e("CAMERA_1::", "setParameters failed", e);
                    }
                }
            }
        }
    }

    boolean getAutoFocus() {
        if (!isCameraOpened()) {
            return this.mAutoFocus;
        }
        String focusMode = this.mCameraParameters.getFocusMode();
        boolean z = focusMode != null && focusMode.contains("continuous");
        return z;
    }

    void setFlash(int i) {
        if (i != this.mFlash && setFlashInternal(i)) {
            try {
                if (this.mCamera != null) {
                    this.mCamera.setParameters(this.mCameraParameters);
                }
            } catch (Throwable e) {
                Log.e("CAMERA_1::", "setParameters failed", e);
            }
        }
    }

    int getFlash() {
        return this.mFlash;
    }

    float getExposureCompensation() {
        return this.mExposure;
    }

    void setExposureCompensation(float f) {
        if (f != this.mExposure && setExposureInternal(f)) {
            try {
                if (this.mCamera != null) {
                    this.mCamera.setParameters(this.mCameraParameters);
                }
            } catch (Throwable e) {
                Log.e("CAMERA_1::", "setParameters failed", e);
            }
        }
    }

    void setZoom(float f) {
        if (f != this.mZoom && setZoomInternal(f)) {
            try {
                if (this.mCamera != null) {
                    this.mCamera.setParameters(this.mCameraParameters);
                }
            } catch (Throwable e) {
                Log.e("CAMERA_1::", "setParameters failed", e);
            }
        }
    }

    float getZoom() {
        return this.mZoom;
    }

    public void setWhiteBalance(int i) {
        if (i != this.mWhiteBalance && setWhiteBalanceInternal(i)) {
            try {
                if (this.mCamera != null) {
                    this.mCamera.setParameters(this.mCameraParameters);
                }
            } catch (Throwable e) {
                Log.e("CAMERA_1::", "setParameters failed", e);
            }
        }
    }

    public int getWhiteBalance() {
        return this.mWhiteBalance;
    }

    void setScanning(boolean z) {
        if (z != this.mIsScanning) {
            setScanningInternal(z);
        }
    }

    boolean getScanning() {
        return this.mIsScanning;
    }

    void takePicture(ReadableMap readableMap) {
        if (!isCameraOpened()) {
            throw new IllegalStateException("Camera is not ready. Call start() before takePicture().");
        } else if (this.mIsPreviewActive) {
            takePictureInternal(readableMap);
        } else {
            throw new IllegalStateException("Preview is paused - resume it before taking a picture.");
        }
    }

    void takePictureInternal(final ReadableMap readableMap) {
        String str = "quality";
        String str2 = ReactVideoView.EVENT_PROP_ORIENTATION;
        if (this.mIsRecording.get() || !this.isPictureCaptureInProgress.compareAndSet(false, true)) {
            throw new IllegalStateException("Camera capture failed. Camera is already capturing.");
        }
        try {
            String str3 = "CAMERA_1::";
            if (readableMap.hasKey(str2)) {
                if (readableMap.getInt(str2) != 0) {
                    this.mOrientation = readableMap.getInt(str2);
                    this.mCameraParameters.setRotation(calcCameraRotation(orientationEnumToRotation(this.mOrientation)));
                    try {
                        this.mCamera.setParameters(this.mCameraParameters);
                    } catch (Throwable e) {
                        Log.e(str3, "setParameters rotation failed", e);
                    }
                }
            }
            if (readableMap.hasKey(str)) {
                this.mCameraParameters.setJpegQuality((int) (readableMap.getDouble(str) * 100.0d));
                try {
                    this.mCamera.setParameters(this.mCameraParameters);
                } catch (Throwable e2) {
                    Log.e(str3, "setParameters quality failed", e2);
                }
            }
            this.mCamera.takePicture(null, null, null, new PictureCallback() {
                public void onPictureTaken(byte[] bArr, Camera camera) {
                    if (Camera1.this.mPlaySoundOnCapture.booleanValue()) {
                        Camera1.this.sound.play(0);
                    }
                    synchronized (Camera1.this) {
                        if (Camera1.this.mCamera != null) {
                            if (!readableMap.hasKey("pauseAfterCapture") || readableMap.getBoolean("pauseAfterCapture")) {
                                Camera1.this.mCamera.stopPreview();
                                Camera1.this.mIsPreviewActive = false;
                                Camera1.this.mCamera.setPreviewCallback(null);
                            } else {
                                Camera1.this.mCamera.startPreview();
                                Camera1.this.mIsPreviewActive = true;
                                if (Camera1.this.mIsScanning) {
                                    Camera1.this.mCamera.setPreviewCallback(Camera1.this);
                                }
                            }
                        }
                    }
                    Camera1.this.isPictureCaptureInProgress.set(false);
                    Camera1.this.mOrientation = 0;
                    Callback callback = Camera1.this.mCallback;
                    Camera1 camera1 = Camera1.this;
                    callback.onPictureTaken(bArr, camera1.displayOrientationToOrientationEnum(camera1.mDeviceOrientation));
                    if (Camera1.this.mustUpdateSurface) {
                        Camera1.this.updateSurface();
                    }
                }
            });
        } catch (Exception e3) {
            this.isPictureCaptureInProgress.set(false);
            throw e3;
        }
    }

    boolean record(String str, int i, int i2, boolean z, CamcorderProfile camcorderProfile, int i3, int i4) {
        int i5 = i3;
        String str2 = "CAMERA_1::";
        if (!this.isPictureCaptureInProgress.get() && this.mIsRecording.compareAndSet(false, true)) {
            if (i5 != 0) {
                this.mOrientation = i5;
            }
            try {
                int i6;
                setUpMediaRecorder(str, i, i2, z, camcorderProfile, i4);
                this.mMediaRecorder.prepare();
                this.mMediaRecorder.start();
                try {
                    this.mCamera.setParameters(this.mCameraParameters);
                } catch (Throwable e) {
                    Log.e(str2, "Record setParameters failed", e);
                }
                i5 = displayOrientationToOrientationEnum(this.mDeviceOrientation);
                Callback callback = this.mCallback;
                String str3;
                if (this.mOrientation != 0) {
                    i6 = this.mOrientation;
                    str3 = str;
                } else {
                    str3 = str;
                    i6 = i5;
                }
                callback.onRecordingStart(str, i6, i5);
                return true;
            } catch (Throwable e2) {
                this.mIsRecording.set(false);
                Log.e(str2, "Record start failed", e2);
            }
        }
        return false;
    }

    void stopRecording() {
        if (this.mIsRecording.compareAndSet(true, false)) {
            stopMediaRecorder();
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.lock();
            }
            if (this.mustUpdateSurface) {
                updateSurface();
            }
        }
    }

    int getCameraOrientation() {
        return this.mCameraInfo.orientation;
    }

    /* JADX WARNING: Missing block: B:24:0x003e, code:
            return;
     */
    void setDisplayOrientation(int r4) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.mDisplayOrientation;	 Catch:{ all -> 0x003f }
        if (r0 != r4) goto L_0x0007;
    L_0x0005:
        monitor-exit(r3);	 Catch:{ all -> 0x003f }
        return;
    L_0x0007:
        r3.mDisplayOrientation = r4;	 Catch:{ all -> 0x003f }
        r0 = r3.isCameraOpened();	 Catch:{ all -> 0x003f }
        if (r0 == 0) goto L_0x003d;
    L_0x000f:
        r0 = r3.mIsPreviewActive;	 Catch:{ all -> 0x003f }
        r1 = 0;
        if (r0 == 0) goto L_0x001c;
    L_0x0014:
        r0 = android.os.Build.VERSION.SDK_INT;	 Catch:{ all -> 0x003f }
        r2 = 14;
        if (r0 >= r2) goto L_0x001c;
    L_0x001a:
        r0 = 1;
        goto L_0x001d;
    L_0x001c:
        r0 = 0;
    L_0x001d:
        if (r0 == 0) goto L_0x0026;
    L_0x001f:
        r2 = r3.mCamera;	 Catch:{ all -> 0x003f }
        r2.stopPreview();	 Catch:{ all -> 0x003f }
        r3.mIsPreviewActive = r1;	 Catch:{ all -> 0x003f }
    L_0x0026:
        r1 = r3.mCamera;	 Catch:{ RuntimeException -> 0x0030 }
        r4 = r3.calcDisplayOrientation(r4);	 Catch:{ RuntimeException -> 0x0030 }
        r1.setDisplayOrientation(r4);	 Catch:{ RuntimeException -> 0x0030 }
        goto L_0x0038;
    L_0x0030:
        r4 = move-exception;
        r1 = "CAMERA_1::";
        r2 = "setDisplayOrientation failed";
        android.util.Log.e(r1, r2, r4);	 Catch:{ all -> 0x003f }
    L_0x0038:
        if (r0 == 0) goto L_0x003d;
    L_0x003a:
        r3.startCameraPreview();	 Catch:{ all -> 0x003f }
    L_0x003d:
        monitor-exit(r3);	 Catch:{ all -> 0x003f }
        return;
    L_0x003f:
        r4 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x003f }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.cameraview.Camera1.setDisplayOrientation(int):void");
    }

    void setDeviceOrientation(int i) {
        synchronized (this) {
            if (this.mDeviceOrientation == i) {
                return;
            }
            this.mDeviceOrientation = i;
            if (isCameraOpened() && this.mOrientation == 0 && !this.mIsRecording.get() && !this.isPictureCaptureInProgress.get()) {
                this.mCameraParameters.setRotation(calcCameraRotation(i));
                try {
                    this.mCamera.setParameters(this.mCameraParameters);
                } catch (Throwable e) {
                    Log.e("CAMERA_1::", "setParameters failed", e);
                }
            }
        }
    }

    public void setPreviewTexture(final SurfaceTexture surfaceTexture) {
        this.mBgHandler.post(new Runnable() {
            public void run() {
                try {
                    if (Camera1.this.mCamera == null) {
                        Camera1.this.mPreviewTexture = surfaceTexture;
                        return;
                    }
                    Camera1.this.mCamera.stopPreview();
                    Camera1.this.mIsPreviewActive = false;
                    if (surfaceTexture == null) {
                        Camera1.this.mCamera.setPreviewTexture((SurfaceTexture) Camera1.this.mPreview.getSurfaceTexture());
                    } else {
                        Camera1.this.mCamera.setPreviewTexture(surfaceTexture);
                    }
                    Camera1.this.mPreviewTexture = surfaceTexture;
                    Camera1.this.startCameraPreview();
                } catch (Throwable e) {
                    Log.e("CAMERA_1::", "setPreviewTexture failed", e);
                }
            }
        });
    }

    public Size getPreviewSize() {
        Size previewSize = this.mCameraParameters.getPreviewSize();
        return new Size(previewSize.width, previewSize.height);
    }

    private void chooseCamera() {
        String str = "CAMERA_1::";
        String str2 = this._mCameraId;
        if (str2 == null) {
            try {
                int numberOfCameras = Camera.getNumberOfCameras();
                if (numberOfCameras == 0) {
                    this.mCameraId = -1;
                    Log.w(str, "getNumberOfCameras returned 0. No camera available.");
                    return;
                }
                for (int i = 0; i < numberOfCameras; i++) {
                    Camera.getCameraInfo(i, this.mCameraInfo);
                    if (this.mCameraInfo.facing == this.mFacing) {
                        this.mCameraId = i;
                        return;
                    }
                }
                this.mCameraId = 0;
                Camera.getCameraInfo(this.mCameraId, this.mCameraInfo);
            } catch (Throwable e) {
                Log.e(str, "chooseCamera failed.", e);
                this.mCameraId = -1;
            }
        } else {
            try {
                this.mCameraId = Integer.parseInt(str2);
                Camera.getCameraInfo(this.mCameraId, this.mCameraInfo);
            } catch (Exception unused) {
                this.mCameraId = -1;
            }
        }
    }

    private boolean openCamera() {
        if (this.mCamera != null) {
            releaseCamera();
        }
        int i = this.mCameraId;
        if (i == -1) {
            return false;
        }
        try {
            this.mCamera = Camera.open(i);
            this.mCameraParameters = this.mCamera.getParameters();
            this.mPreviewSizes.clear();
            for (Size size : this.mCameraParameters.getSupportedPreviewSizes()) {
                this.mPreviewSizes.add(new Size(size.width, size.height));
            }
            this.mPictureSizes.clear();
            for (Size size2 : this.mCameraParameters.getSupportedPictureSizes()) {
                this.mPictureSizes.add(new Size(size2.width, size2.height));
            }
            for (AspectRatio aspectRatio : this.mPreviewSizes.ratios()) {
                if (this.mPictureSizes.sizes(aspectRatio) == null) {
                    this.mPreviewSizes.remove(aspectRatio);
                }
            }
            if (this.mAspectRatio == null) {
                this.mAspectRatio = Constants.DEFAULT_ASPECT_RATIO;
            }
            adjustCameraParameters();
            this.mCamera.setDisplayOrientation(calcDisplayOrientation(this.mDisplayOrientation));
            this.mCallback.onCameraOpened();
            return true;
        } catch (RuntimeException unused) {
            return false;
        }
    }

    private AspectRatio chooseAspectRatio() {
        AspectRatio aspectRatio = null;
        for (AspectRatio aspectRatio2 : this.mPreviewSizes.ratios()) {
            if (aspectRatio2.equals(Constants.DEFAULT_ASPECT_RATIO)) {
                break;
            }
        }
        return aspectRatio2;
    }

    void adjustCameraParameters() {
        SortedSet sizes = this.mPreviewSizes.sizes(this.mAspectRatio);
        String str = "CAMERA_1::";
        if (sizes == null) {
            Log.w(str, "adjustCameraParameters received an unsupported aspect ratio value and will be ignored.");
            this.mAspectRatio = chooseAspectRatio();
            sizes = this.mPreviewSizes.sizes(this.mAspectRatio);
        }
        Size chooseOptimalSize = chooseOptimalSize(sizes);
        this.mPictureSize = (Size) this.mPictureSizes.sizes(this.mAspectRatio).last();
        boolean z = this.mIsPreviewActive;
        if (z) {
            this.mCamera.stopPreview();
            this.mIsPreviewActive = false;
        }
        this.mCameraParameters.setPreviewSize(chooseOptimalSize.getWidth(), chooseOptimalSize.getHeight());
        this.mCameraParameters.setPictureSize(this.mPictureSize.getWidth(), this.mPictureSize.getHeight());
        int i = this.mOrientation;
        if (i != 0) {
            this.mCameraParameters.setRotation(calcCameraRotation(orientationEnumToRotation(i)));
        } else {
            this.mCameraParameters.setRotation(calcCameraRotation(this.mDeviceOrientation));
        }
        setAutoFocusInternal(this.mAutoFocus);
        setFlashInternal(this.mFlash);
        setExposureInternal(this.mExposure);
        setAspectRatio(this.mAspectRatio);
        setZoomInternal(this.mZoom);
        setWhiteBalanceInternal(this.mWhiteBalance);
        setScanningInternal(this.mIsScanning);
        setPlaySoundInternal(this.mPlaySoundOnCapture.booleanValue());
        try {
            this.mCamera.setParameters(this.mCameraParameters);
        } catch (Throwable e) {
            Log.e(str, "setParameters failed", e);
        }
        if (z) {
            startCameraPreview();
        }
    }

    private Size chooseOptimalSize(SortedSet<Size> sortedSet) {
        if (!this.mPreview.isReady()) {
            return (Size) sortedSet.first();
        }
        int width = this.mPreview.getWidth();
        int height = this.mPreview.getHeight();
        if (isLandscape(this.mDisplayOrientation)) {
            int i = height;
            height = width;
            width = i;
        }
        Size size = null;
        for (Size size2 : sortedSet) {
            if (width <= size2.getWidth() && height <= size2.getHeight()) {
                break;
            }
        }
        return size2;
    }

    private void releaseCamera() {
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.release();
            this.mCamera = null;
            this.mPictureSize = null;
            this.mCallback.onCameraClosed();
            this.isPictureCaptureInProgress.set(false);
            this.mIsRecording.set(false);
        }
    }

    void setFocusArea(final float f, final float f2) {
        this.mBgHandler.post(new Runnable() {
            public void run() {
                synchronized (Camera1.this) {
                    if (Camera1.this.mCamera != null) {
                        Parameters parameters;
                        try {
                            parameters = Camera1.this.mCamera.getParameters();
                        } catch (Throwable e) {
                            Log.e("CAMERA_1::", "setFocusArea.getParameters failed", e);
                            parameters = null;
                        }
                        if (parameters == null) {
                            return;
                        }
                        String focusMode = parameters.getFocusMode();
                        Rect access$1200 = Camera1.this.calculateFocusArea(f, f2);
                        List arrayList = new ArrayList();
                        arrayList.add(new Area(access$1200, 1000));
                        if (parameters.getMaxNumFocusAreas() != 0 && focusMode != null && (focusMode.equals("auto") || focusMode.equals("macro") || focusMode.equals("continuous-picture") || focusMode.equals("continuous-video"))) {
                            parameters.setFocusMode("auto");
                            parameters.setFocusAreas(arrayList);
                            if (parameters.getMaxNumMeteringAreas() > 0) {
                                parameters.setMeteringAreas(arrayList);
                            }
                            if (parameters.getSupportedFocusModes().contains("auto")) {
                                try {
                                    Camera1.this.mCamera.setParameters(parameters);
                                } catch (Throwable e2) {
                                    Log.e("CAMERA_1::", "setParameters failed", e2);
                                }
                                try {
                                    Camera1.this.mCamera.autoFocus(new AutoFocusCallback() {
                                        public void onAutoFocus(boolean z, Camera camera) {
                                        }
                                    });
                                } catch (Throwable e22) {
                                    Log.e("CAMERA_1::", "autoFocus failed", e22);
                                }
                            } else {
                                return;
                            }
                        } else if (parameters.getMaxNumMeteringAreas() <= 0) {
                            try {
                                Camera1.this.mCamera.autoFocus(new AutoFocusCallback() {
                                    public void onAutoFocus(boolean z, Camera camera) {
                                    }
                                });
                            } catch (Throwable e222) {
                                Log.e("CAMERA_1::", "autoFocus failed", e222);
                            }
                        } else if (parameters.getSupportedFocusModes().contains("auto")) {
                            parameters.setFocusMode("auto");
                            parameters.setFocusAreas(arrayList);
                            parameters.setMeteringAreas(arrayList);
                            try {
                                Camera1.this.mCamera.setParameters(parameters);
                            } catch (Throwable e2222) {
                                Log.e("CAMERA_1::", "setParameters failed", e2222);
                            }
                            try {
                                Camera1.this.mCamera.autoFocus(new AutoFocusCallback() {
                                    public void onAutoFocus(boolean z, Camera camera) {
                                    }
                                });
                            } catch (Throwable e22222) {
                                Log.e("CAMERA_1::", "autoFocus failed", e22222);
                            }
                        } else {
                            return;
                        }
                    }
                }
                return;
            }
        });
    }

    private void resetFocus(boolean z, Camera camera) {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                String str = "CAMERA_1::";
                if (Camera1.this.mCamera != null) {
                    Parameters parameters;
                    Camera1.this.mCamera.cancelAutoFocus();
                    try {
                        parameters = Camera1.this.mCamera.getParameters();
                    } catch (Throwable e) {
                        Log.e(str, "resetFocus.getParameters failed", e);
                        parameters = null;
                    }
                    if (parameters != null) {
                        String str2 = "continuous-picture";
                        if (parameters.getFocusMode() != str2) {
                            parameters.setFocusMode(str2);
                            parameters.setFocusAreas(null);
                            parameters.setMeteringAreas(null);
                            try {
                                Camera1.this.mCamera.setParameters(parameters);
                            } catch (Throwable e2) {
                                Log.e(str, "setParameters failed", e2);
                            }
                        }
                        Camera1.this.mCamera.cancelAutoFocus();
                    }
                }
            }
        }, 3000);
    }

    private Rect calculateFocusArea(float f, float f2) {
        int i = (int) (f * 2000.0f);
        int i2 = (int) (f2 * 2000.0f);
        int i3 = i - 150;
        int i4 = i2 - 150;
        i += 150;
        i2 += 150;
        if (i3 < 0) {
            i3 = 0;
        }
        if (i > CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE) {
            i = CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE;
        }
        if (i4 < 0) {
            i4 = 0;
        }
        if (i2 > CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE) {
            i2 = CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE;
        }
        return new Rect(i3 + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, i4 + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, i + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, i2 + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
    }

    private int calcDisplayOrientation(int i) {
        if (this.mCameraInfo.facing == 1) {
            return (360 - ((this.mCameraInfo.orientation + i) % 360)) % 360;
        }
        return ((this.mCameraInfo.orientation - i) + 360) % 360;
    }

    private int calcCameraRotation(int i) {
        if (this.mCameraInfo.facing == 0) {
            return (this.mCameraInfo.orientation + i) % 360;
        }
        return ((this.mCameraInfo.orientation + i) + (isLandscape(i) ? 180 : 0)) % 360;
    }

    private boolean setAutoFocusInternal(boolean z) {
        this.mAutoFocus = z;
        if (!isCameraOpened()) {
            return false;
        }
        String str;
        List supportedFocusModes = this.mCameraParameters.getSupportedFocusModes();
        if (z) {
            str = "continuous-picture";
            if (supportedFocusModes.contains(str)) {
                this.mCameraParameters.setFocusMode(str);
                return true;
            }
        }
        str = "fixed";
        if (supportedFocusModes.contains(str)) {
            this.mCameraParameters.setFocusMode(str);
        } else {
            str = "infinity";
            if (supportedFocusModes.contains(str)) {
                this.mCameraParameters.setFocusMode(str);
            } else {
                this.mCameraParameters.setFocusMode((String) supportedFocusModes.get(0));
            }
        }
        return true;
    }

    private boolean setFlashInternal(int i) {
        if (isCameraOpened()) {
            List supportedFlashModes = this.mCameraParameters.getSupportedFlashModes();
            String str = (String) FLASH_MODES.get(i);
            if (supportedFlashModes == null) {
                return false;
            }
            if (supportedFlashModes.contains(str)) {
                this.mCameraParameters.setFlashMode(str);
                this.mFlash = i;
                return true;
            } else if (supportedFlashModes.contains((String) FLASH_MODES.get(this.mFlash))) {
                return false;
            } else {
                this.mCameraParameters.setFlashMode("off");
                return true;
            }
        }
        this.mFlash = i;
        return false;
    }

    private boolean setExposureInternal(float f) {
        this.mExposure = f;
        int i = 0;
        if (isCameraOpened()) {
            int minExposureCompensation = this.mCameraParameters.getMinExposureCompensation();
            int maxExposureCompensation = this.mCameraParameters.getMaxExposureCompensation();
            if (minExposureCompensation != maxExposureCompensation) {
                float f2 = this.mExposure;
                if (f2 >= 0.0f && f2 <= 1.0f) {
                    i = ((int) (f2 * ((float) (maxExposureCompensation - minExposureCompensation)))) + minExposureCompensation;
                }
                this.mCameraParameters.setExposureCompensation(i);
                return true;
            }
        }
        return false;
    }

    private boolean setZoomInternal(float f) {
        if (isCameraOpened() && this.mCameraParameters.isZoomSupported()) {
            this.mCameraParameters.setZoom((int) (((float) this.mCameraParameters.getMaxZoom()) * f));
            this.mZoom = f;
            return true;
        }
        this.mZoom = f;
        return false;
    }

    private boolean setWhiteBalanceInternal(int i) {
        this.mWhiteBalance = i;
        if (!isCameraOpened()) {
            return false;
        }
        List supportedWhiteBalance = this.mCameraParameters.getSupportedWhiteBalance();
        String str = (String) WB_MODES.get(i);
        if (supportedWhiteBalance == null || !supportedWhiteBalance.contains(str)) {
            str = (String) WB_MODES.get(this.mWhiteBalance);
            if (supportedWhiteBalance != null && supportedWhiteBalance.contains(str)) {
                return false;
            }
            this.mCameraParameters.setWhiteBalance("auto");
            return true;
        }
        this.mCameraParameters.setWhiteBalance(str);
        return true;
    }

    private void setScanningInternal(boolean z) {
        this.mIsScanning = z;
        if (!isCameraOpened()) {
            return;
        }
        if (this.mIsScanning) {
            this.mCamera.setPreviewCallback(this);
        } else {
            this.mCamera.setPreviewCallback(null);
        }
    }

    private void setPlaySoundInternal(boolean z) {
        this.mPlaySoundOnCapture = Boolean.valueOf(z);
        Camera camera = this.mCamera;
        if (camera != null) {
            try {
                if (!camera.enableShutterSound(false)) {
                    this.mPlaySoundOnCapture = Boolean.valueOf(false);
                }
            } catch (Throwable e) {
                Log.e("CAMERA_1::", "setPlaySoundInternal failed", e);
                this.mPlaySoundOnCapture = Boolean.valueOf(false);
            }
        }
    }

    void setPlaySoundOnCapture(boolean z) {
        if (z != this.mPlaySoundOnCapture.booleanValue()) {
            setPlaySoundInternal(z);
        }
    }

    public boolean getPlaySoundOnCapture() {
        return this.mPlaySoundOnCapture.booleanValue();
    }

    public void onPreviewFrame(byte[] bArr, Camera camera) {
        Size previewSize = this.mCameraParameters.getPreviewSize();
        this.mCallback.onFramePreview(bArr, previewSize.width, previewSize.height, this.mDeviceOrientation);
    }

    private void setUpMediaRecorder(String str, int i, int i2, boolean z, CamcorderProfile camcorderProfile, int i3) {
        CamcorderProfile camcorderProfile2;
        this.mMediaRecorder = new MediaRecorder();
        this.mCamera.unlock();
        this.mMediaRecorder.setCamera(this.mCamera);
        this.mMediaRecorder.setVideoSource(1);
        if (z) {
            this.mMediaRecorder.setAudioSource(5);
        }
        this.mMediaRecorder.setOutputFile(str);
        this.mVideoPath = str;
        if (CamcorderProfile.hasProfile(this.mCameraId, camcorderProfile.quality)) {
            camcorderProfile2 = CamcorderProfile.get(this.mCameraId, camcorderProfile.quality);
        } else {
            camcorderProfile2 = CamcorderProfile.get(this.mCameraId, 1);
        }
        camcorderProfile2.videoBitRate = camcorderProfile.videoBitRate;
        setCamcorderProfile(camcorderProfile2, z, i3);
        MediaRecorder mediaRecorder = this.mMediaRecorder;
        int i4 = this.mOrientation;
        mediaRecorder.setOrientationHint(calcCameraRotation(i4 != 0 ? orientationEnumToRotation(i4) : this.mDeviceOrientation));
        if (i != -1) {
            this.mMediaRecorder.setMaxDuration(i);
        }
        if (i2 != -1) {
            this.mMediaRecorder.setMaxFileSize((long) i2);
        }
        this.mMediaRecorder.setOnInfoListener(this);
        this.mMediaRecorder.setOnErrorListener(this);
    }

    private void stopMediaRecorder() {
        synchronized (this) {
            if (this.mMediaRecorder != null) {
                try {
                    this.mMediaRecorder.stop();
                } catch (Throwable e) {
                    Log.e("CAMERA_1::", "stopMediaRecorder stop failed", e);
                }
                try {
                    this.mMediaRecorder.reset();
                    this.mMediaRecorder.release();
                } catch (Throwable e2) {
                    Log.e("CAMERA_1::", "stopMediaRecorder reset failed", e2);
                }
                this.mMediaRecorder = null;
            }
            this.mCallback.onRecordingEnd();
            int displayOrientationToOrientationEnum = displayOrientationToOrientationEnum(this.mDeviceOrientation);
            if (this.mVideoPath == null || !new File(this.mVideoPath).exists()) {
                this.mCallback.onVideoRecorded(null, this.mOrientation != 0 ? this.mOrientation : displayOrientationToOrientationEnum, displayOrientationToOrientationEnum);
                return;
            }
            this.mCallback.onVideoRecorded(this.mVideoPath, this.mOrientation != 0 ? this.mOrientation : displayOrientationToOrientationEnum, displayOrientationToOrientationEnum);
            this.mVideoPath = null;
            return;
        }
    }

    public ArrayList<int[]> getSupportedPreviewFpsRange() {
        return (ArrayList) this.mCameraParameters.getSupportedPreviewFpsRange();
    }

    private boolean isCompatibleWithDevice(int i) {
        i *= 1000;
        Iterator it = getSupportedPreviewFpsRange().iterator();
        boolean z;
        do {
            z = false;
            if (it.hasNext()) {
                int[] iArr = (int[]) it.next();
                Object obj = (i < iArr[0] || i > iArr[1]) ? null : 1;
                Object obj2 = i > 0 ? 1 : null;
                if (!(obj == null || obj2 == null)) {
                    z = true;
                    continue;
                }
            } else {
                Log.w("CAMERA_1::", "fps (framePerSecond) received an unsupported value and will be ignored.");
                return false;
            }
        } while (!z);
        return true;
    }

    private void setCamcorderProfile(CamcorderProfile camcorderProfile, boolean z, int i) {
        if (!isCompatibleWithDevice(i)) {
            i = camcorderProfile.videoFrameRate;
        }
        this.mMediaRecorder.setOutputFormat(camcorderProfile.fileFormat);
        this.mMediaRecorder.setVideoFrameRate(i);
        this.mMediaRecorder.setVideoSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        this.mMediaRecorder.setVideoEncodingBitRate(camcorderProfile.videoBitRate);
        this.mMediaRecorder.setVideoEncoder(camcorderProfile.videoCodec);
        if (z) {
            this.mMediaRecorder.setAudioEncodingBitRate(camcorderProfile.audioBitRate);
            this.mMediaRecorder.setAudioChannels(camcorderProfile.audioChannels);
            this.mMediaRecorder.setAudioSamplingRate(camcorderProfile.audioSampleRate);
            this.mMediaRecorder.setAudioEncoder(camcorderProfile.audioCodec);
        }
    }

    public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
        if (i == 800 || i == LeicaMakernoteDirectory.TAG_COLOR_TEMPERATURE) {
            stopRecording();
        }
    }

    public void onError(MediaRecorder mediaRecorder, int i, int i2) {
        stopRecording();
    }
}
