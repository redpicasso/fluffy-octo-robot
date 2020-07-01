package com.google.android.cameraview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraManager.AvailabilityCallback;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.media.MediaActionSound;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import androidx.annotation.NonNull;
import com.drew.metadata.exif.makernotes.LeicaMakernoteDirectory;
import com.facebook.react.bridge.ReadableMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import org.reactnative.camera.utils.ObjectUtils;

@TargetApi(21)
class Camera2 extends CameraViewImpl implements OnInfoListener, OnErrorListener {
    private static final int FOCUS_AREA_SIZE_DEFAULT = 300;
    private static final int FOCUS_METERING_AREA_WEIGHT_DEFAULT = 1000;
    private static final SparseIntArray INTERNAL_FACINGS = new SparseIntArray();
    private static final int MAX_PREVIEW_HEIGHT = 1080;
    private static final int MAX_PREVIEW_WIDTH = 1920;
    private static final String TAG = "Camera2";
    private String _mCameraId;
    private AspectRatio mAspectRatio = Constants.DEFAULT_ASPECT_RATIO;
    private boolean mAutoFocus;
    Set<String> mAvailableCameras = new HashSet();
    CameraDevice mCamera;
    private CameraCharacteristics mCameraCharacteristics;
    private final StateCallback mCameraDeviceCallback = new StateCallback() {
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            Camera2 camera2 = Camera2.this;
            camera2.mCamera = cameraDevice;
            camera2.mCallback.onCameraOpened();
            Camera2.this.startCaptureSession();
        }

        public void onClosed(@NonNull CameraDevice cameraDevice) {
            Camera2.this.mCallback.onCameraClosed();
        }

        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            Camera2.this.mCamera = null;
        }

        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError: ");
            stringBuilder.append(cameraDevice.getId());
            stringBuilder.append(" (");
            stringBuilder.append(i);
            stringBuilder.append(")");
            Log.e(Camera2.TAG, stringBuilder.toString());
            Camera2.this.mCamera = null;
        }
    };
    private String mCameraId;
    private final CameraManager mCameraManager;
    private int mCameraOrientation;
    PictureCaptureCallback mCaptureCallback = new PictureCaptureCallback() {
        public void onPrecaptureRequired() {
            Camera2.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, Integer.valueOf(1));
            setState(3);
            try {
                Camera2.this.mCaptureSession.capture(Camera2.this.mPreviewRequestBuilder.build(), this, null);
                Camera2.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, Integer.valueOf(0));
            } catch (Throwable e) {
                Log.e(Camera2.TAG, "Failed to run precapture sequence.", e);
            }
        }

        public void onReady() {
            Camera2.this.captureStillPicture();
        }
    };
    CameraCaptureSession mCaptureSession;
    private int mDeviceOrientation;
    private int mDisplayOrientation;
    private float mExposure;
    private int mFacing;
    private int mFlash;
    private float mFocusDepth;
    private int mImageFormat;
    private Rect mInitialCropRegion;
    private AspectRatio mInitialRatio;
    private boolean mIsRecording;
    private boolean mIsScanning;
    private MediaRecorder mMediaRecorder;
    private final OnImageAvailableListener mOnImageAvailableListener = new OnImageAvailableListener() {
        /* JADX WARNING: Missing block: B:15:0x004e, code:
            if (r7 != null) goto L_0x0050;
     */
        /* JADX WARNING: Missing block: B:16:0x0050, code:
            if (r0 != null) goto L_0x0052;
     */
        /* JADX WARNING: Missing block: B:18:?, code:
            r7.close();
     */
        /* JADX WARNING: Missing block: B:19:0x0056, code:
            r7 = move-exception;
     */
        /* JADX WARNING: Missing block: B:20:0x0057, code:
            r0.addSuppressed(r7);
     */
        /* JADX WARNING: Missing block: B:21:0x005b, code:
            r7.close();
     */
        public void onImageAvailable(android.media.ImageReader r7) {
            /*
            r6 = this;
            r7 = r7.acquireNextImage();
            r0 = 0;
            r1 = r7.getPlanes();	 Catch:{ Throwable -> 0x004c }
            r2 = r1.length;	 Catch:{ Throwable -> 0x004c }
            if (r2 <= 0) goto L_0x0044;
        L_0x000c:
            r2 = 0;
            r1 = r1[r2];	 Catch:{ Throwable -> 0x004c }
            r1 = r1.getBuffer();	 Catch:{ Throwable -> 0x004c }
            r3 = r1.remaining();	 Catch:{ Throwable -> 0x004c }
            r3 = new byte[r3];	 Catch:{ Throwable -> 0x004c }
            r1.get(r3);	 Catch:{ Throwable -> 0x004c }
            r1 = r7.getFormat();	 Catch:{ Throwable -> 0x004c }
            r4 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
            if (r1 != r4) goto L_0x002c;
        L_0x0024:
            r1 = com.google.android.cameraview.Camera2.this;	 Catch:{ Throwable -> 0x004c }
            r1 = r1.mCallback;	 Catch:{ Throwable -> 0x004c }
            r1.onPictureTaken(r3, r2);	 Catch:{ Throwable -> 0x004c }
            goto L_0x0041;
        L_0x002c:
            r1 = com.google.android.cameraview.Camera2.this;	 Catch:{ Throwable -> 0x004c }
            r1 = r1.mCallback;	 Catch:{ Throwable -> 0x004c }
            r2 = r7.getWidth();	 Catch:{ Throwable -> 0x004c }
            r4 = r7.getHeight();	 Catch:{ Throwable -> 0x004c }
            r5 = com.google.android.cameraview.Camera2.this;	 Catch:{ Throwable -> 0x004c }
            r5 = r5.mDisplayOrientation;	 Catch:{ Throwable -> 0x004c }
            r1.onFramePreview(r3, r2, r4, r5);	 Catch:{ Throwable -> 0x004c }
        L_0x0041:
            r7.close();	 Catch:{ Throwable -> 0x004c }
        L_0x0044:
            if (r7 == 0) goto L_0x0049;
        L_0x0046:
            r7.close();
        L_0x0049:
            return;
        L_0x004a:
            r1 = move-exception;
            goto L_0x004e;
        L_0x004c:
            r0 = move-exception;
            throw r0;	 Catch:{ all -> 0x004a }
        L_0x004e:
            if (r7 == 0) goto L_0x005e;
        L_0x0050:
            if (r0 == 0) goto L_0x005b;
        L_0x0052:
            r7.close();	 Catch:{ Throwable -> 0x0056 }
            goto L_0x005e;
        L_0x0056:
            r7 = move-exception;
            r0.addSuppressed(r7);
            goto L_0x005e;
        L_0x005b:
            r7.close();
        L_0x005e:
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.cameraview.Camera2.4.onImageAvailable(android.media.ImageReader):void");
        }
    };
    private Size mPictureSize;
    private final SizeMap mPictureSizes = new SizeMap();
    private Boolean mPlaySoundOnCapture = Boolean.valueOf(false);
    Builder mPreviewRequestBuilder;
    private final SizeMap mPreviewSizes = new SizeMap();
    private Surface mPreviewSurface;
    private ImageReader mScanImageReader;
    private final CameraCaptureSession.StateCallback mSessionCallback = new CameraCaptureSession.StateCallback() {
        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
            String str = Camera2.TAG;
            if (Camera2.this.mCamera != null) {
                Camera2 camera2 = Camera2.this;
                camera2.mCaptureSession = cameraCaptureSession;
                camera2.mInitialCropRegion = (Rect) camera2.mPreviewRequestBuilder.get(CaptureRequest.SCALER_CROP_REGION);
                Camera2.this.updateAutoFocus();
                Camera2.this.updateFlash();
                Camera2.this.updateFocusDepth();
                Camera2.this.updateWhiteBalance();
                Camera2.this.updateZoom();
                try {
                    Camera2.this.mCaptureSession.setRepeatingRequest(Camera2.this.mPreviewRequestBuilder.build(), Camera2.this.mCaptureCallback, null);
                } catch (Throwable e) {
                    Log.e(str, "Failed to start camera preview because it couldn't access camera", e);
                } catch (Throwable e2) {
                    Log.e(str, "Failed to start camera preview.", e2);
                }
            }
        }

        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
            Log.e(Camera2.TAG, "Failed to configure capture session.");
        }

        public void onClosed(@NonNull CameraCaptureSession cameraCaptureSession) {
            if (Camera2.this.mCaptureSession != null && Camera2.this.mCaptureSession.equals(cameraCaptureSession)) {
                Camera2.this.mCaptureSession = null;
            }
        }
    };
    private ImageReader mStillImageReader;
    private String mVideoPath;
    private int mWhiteBalance;
    private float mZoom;
    MediaActionSound sound = new MediaActionSound();

    private static abstract class PictureCaptureCallback extends CaptureCallback {
        static final int STATE_CAPTURING = 5;
        static final int STATE_LOCKED = 2;
        static final int STATE_LOCKING = 1;
        static final int STATE_PRECAPTURE = 3;
        static final int STATE_PREVIEW = 0;
        static final int STATE_WAITING = 4;
        private ReadableMap mOptions = null;
        private int mState;

        public abstract void onPrecaptureRequired();

        public abstract void onReady();

        PictureCaptureCallback() {
        }

        void setState(int i) {
            this.mState = i;
        }

        void setOptions(ReadableMap readableMap) {
            this.mOptions = readableMap;
        }

        ReadableMap getOptions() {
            return this.mOptions;
        }

        public void onCaptureProgressed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureResult captureResult) {
            process(captureResult);
        }

        public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
            process(totalCaptureResult);
        }

        private void process(@NonNull CaptureResult captureResult) {
            int i = this.mState;
            Integer num;
            if (i == 1) {
                Integer num2 = (Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE);
                if (num2 != null) {
                    if (num2.intValue() == 4 || num2.intValue() == 5) {
                        num = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                        if (num == null || num.intValue() == 2) {
                            setState(5);
                            onReady();
                            return;
                        }
                        setState(2);
                        onPrecaptureRequired();
                    }
                }
            } else if (i == 3) {
                num = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                if (num == null || num.intValue() == 5 || num.intValue() == 4 || num.intValue() == 2) {
                    setState(4);
                }
            } else if (i == 4) {
                num = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                if (num == null || num.intValue() != 5) {
                    setState(5);
                    onReady();
                }
            }
        }
    }

    private boolean isLandscape(int i) {
        return i == 90 || i == 270;
    }

    static {
        INTERNAL_FACINGS.put(0, 1);
        INTERNAL_FACINGS.put(1, 0);
    }

    Camera2(Callback callback, PreviewImpl previewImpl, Context context, Handler handler) {
        super(callback, previewImpl, handler);
        this.mCameraManager = (CameraManager) context.getSystemService("camera");
        this.mCameraManager.registerAvailabilityCallback(new AvailabilityCallback() {
            public void onCameraAvailable(@NonNull String str) {
                super.onCameraAvailable(str);
                Camera2.this.mAvailableCameras.add(str);
            }

            public void onCameraUnavailable(@NonNull String str) {
                super.onCameraUnavailable(str);
                Camera2.this.mAvailableCameras.remove(str);
            }
        }, null);
        this.mImageFormat = this.mIsScanning ? 35 : 256;
        this.mPreview.setCallback(new Callback() {
            public void onSurfaceChanged() {
                Camera2.this.startCaptureSession();
            }

            public void onSurfaceDestroyed() {
                Camera2.this.stop();
            }
        });
    }

    boolean start() {
        if (chooseCameraIdByFacing()) {
            collectCameraInfo();
            setAspectRatio(this.mInitialRatio);
            this.mInitialRatio = null;
            prepareStillImageReader();
            prepareScanImageReader();
            startOpeningCamera();
            return true;
        }
        this.mAspectRatio = this.mInitialRatio;
        this.mCallback.onMountError();
        return false;
    }

    void stop() {
        CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            this.mCaptureSession = null;
        }
        CameraDevice cameraDevice = this.mCamera;
        if (cameraDevice != null) {
            cameraDevice.close();
            this.mCamera = null;
        }
        ImageReader imageReader = this.mStillImageReader;
        if (imageReader != null) {
            imageReader.close();
            this.mStillImageReader = null;
        }
        imageReader = this.mScanImageReader;
        if (imageReader != null) {
            imageReader.close();
            this.mScanImageReader = null;
        }
        MediaRecorder mediaRecorder = this.mMediaRecorder;
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            this.mMediaRecorder.reset();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
            if (this.mIsRecording) {
                this.mCallback.onRecordingEnd();
                this.mCallback.onVideoRecorded(this.mVideoPath, 0, 0);
                this.mIsRecording = false;
            }
        }
    }

    boolean isCameraOpened() {
        return this.mCamera != null;
    }

    void setFacing(int i) {
        if (this.mFacing != i) {
            this.mFacing = i;
            if (isCameraOpened()) {
                stop();
                start();
            }
        }
    }

    int getFacing() {
        return this.mFacing;
    }

    public ArrayList<int[]> getSupportedPreviewFpsRange() {
        Log.e("CAMERA_2:: ", "getSupportedPreviewFpsRange is not currently supported for Camera2");
        return new ArrayList();
    }

    void setCameraId(String str) {
        if (!ObjectUtils.equals(this._mCameraId, str)) {
            this._mCameraId = str;
            if (!ObjectUtils.equals(this._mCameraId, this.mCameraId) && isCameraOpened()) {
                stop();
                start();
            }
        }
    }

    String getCameraId() {
        return this._mCameraId;
    }

    Set<AspectRatio> getSupportedAspectRatios() {
        return this.mPreviewSizes.ratios();
    }

    List<Properties> getCameraIds() {
        try {
            List<Properties> arrayList = new ArrayList();
            for (String str : this.mCameraManager.getCameraIdList()) {
                Properties properties = new Properties();
                Integer num = (Integer) this.mCameraManager.getCameraCharacteristics(str).get(CameraCharacteristics.LENS_FACING);
                properties.put("id", str);
                properties.put("type", String.valueOf(num.intValue() == 0 ? 1 : 0));
                arrayList.add(properties);
            }
            return arrayList;
        } catch (Throwable e) {
            throw new RuntimeException("Failed to get a list of camera ids", e);
        }
    }

    SortedSet<Size> getAvailablePictureSizes(AspectRatio aspectRatio) {
        return this.mPictureSizes.sizes(aspectRatio);
    }

    void setPictureSize(Size size) {
        CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
        if (cameraCaptureSession != null) {
            try {
                cameraCaptureSession.stopRepeating();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            this.mCaptureSession.close();
            this.mCaptureSession = null;
        }
        ImageReader imageReader = this.mStillImageReader;
        if (imageReader != null) {
            imageReader.close();
        }
        if (size == null) {
            AspectRatio aspectRatio = this.mAspectRatio;
            if (aspectRatio != null && this.mPictureSize != null) {
                this.mPictureSizes.sizes(aspectRatio).last();
            } else {
                return;
            }
        }
        this.mPictureSize = size;
        prepareStillImageReader();
        startCaptureSession();
    }

    Size getPictureSize() {
        return this.mPictureSize;
    }

    boolean setAspectRatio(AspectRatio aspectRatio) {
        if (aspectRatio != null && this.mPreviewSizes.isEmpty()) {
            this.mInitialRatio = aspectRatio;
            return false;
        } else if (aspectRatio == null || aspectRatio.equals(this.mAspectRatio) || !this.mPreviewSizes.ratios().contains(aspectRatio)) {
            return false;
        } else {
            this.mAspectRatio = aspectRatio;
            prepareStillImageReader();
            prepareScanImageReader();
            CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
            if (cameraCaptureSession != null) {
                cameraCaptureSession.close();
                this.mCaptureSession = null;
                startCaptureSession();
            }
            return true;
        }
    }

    AspectRatio getAspectRatio() {
        return this.mAspectRatio;
    }

    void setAutoFocus(boolean z) {
        if (this.mAutoFocus != z) {
            this.mAutoFocus = z;
            if (this.mPreviewRequestBuilder != null) {
                updateAutoFocus();
                CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
                if (cameraCaptureSession != null) {
                    try {
                        cameraCaptureSession.setRepeatingRequest(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
                    } catch (CameraAccessException unused) {
                        this.mAutoFocus ^= 1;
                    }
                }
            }
        }
    }

    boolean getAutoFocus() {
        return this.mAutoFocus;
    }

    void setFlash(int i) {
        int i2 = this.mFlash;
        if (i2 != i) {
            this.mFlash = i;
            if (this.mPreviewRequestBuilder != null) {
                updateFlash();
                CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
                if (cameraCaptureSession != null) {
                    try {
                        cameraCaptureSession.setRepeatingRequest(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
                    } catch (CameraAccessException unused) {
                        this.mFlash = i2;
                    }
                }
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
        Log.e("CAMERA_2:: ", "Adjusting exposure is not currently supported for Camera2");
    }

    void takePicture(ReadableMap readableMap) {
        this.mCaptureCallback.setOptions(readableMap);
        if (this.mAutoFocus) {
            lockFocus();
        } else {
            captureStillPicture();
        }
    }

    boolean record(String str, int i, int i2, boolean z, CamcorderProfile camcorderProfile, int i3, int i4) {
        Exception e;
        if (!this.mIsRecording) {
            setUpMediaRecorder(str, i, i2, z, camcorderProfile);
            try {
                this.mMediaRecorder.prepare();
                if (this.mCaptureSession != null) {
                    this.mCaptureSession.close();
                    this.mCaptureSession = null;
                }
                Size chooseOptimalSize = chooseOptimalSize();
                this.mPreview.setBufferSize(chooseOptimalSize.getWidth(), chooseOptimalSize.getHeight());
                Surface previewSurface = getPreviewSurface();
                Surface surface = this.mMediaRecorder.getSurface();
                this.mPreviewRequestBuilder = this.mCamera.createCaptureRequest(3);
                this.mPreviewRequestBuilder.addTarget(previewSurface);
                this.mPreviewRequestBuilder.addTarget(surface);
                this.mCamera.createCaptureSession(Arrays.asList(new Surface[]{previewSurface, surface}), this.mSessionCallback, null);
                this.mMediaRecorder.start();
                this.mIsRecording = true;
                this.mCallback.onRecordingStart(this.mVideoPath, 0, 0);
                return true;
            } catch (CameraAccessException e2) {
                e = e2;
                e.printStackTrace();
                return false;
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    void stopRecording() {
        if (this.mIsRecording) {
            stopMediaRecorder();
            CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
            if (cameraCaptureSession != null) {
                cameraCaptureSession.close();
                this.mCaptureSession = null;
            }
            startCaptureSession();
        }
    }

    public void setFocusDepth(float f) {
        float f2 = this.mFocusDepth;
        if (f2 != f) {
            this.mFocusDepth = f;
            if (this.mCaptureSession != null) {
                updateFocusDepth();
                try {
                    this.mCaptureSession.setRepeatingRequest(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
                } catch (CameraAccessException unused) {
                    this.mFocusDepth = f2;
                }
            }
        }
    }

    float getFocusDepth() {
        return this.mFocusDepth;
    }

    public void setZoom(float f) {
        float f2 = this.mZoom;
        if (f2 != f) {
            this.mZoom = f;
            if (this.mCaptureSession != null) {
                updateZoom();
                try {
                    this.mCaptureSession.setRepeatingRequest(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
                } catch (CameraAccessException unused) {
                    this.mZoom = f2;
                }
            }
        }
    }

    float getZoom() {
        return this.mZoom;
    }

    public void setWhiteBalance(int i) {
        int i2 = this.mWhiteBalance;
        if (i2 != i) {
            this.mWhiteBalance = i;
            if (this.mCaptureSession != null) {
                updateWhiteBalance();
                try {
                    this.mCaptureSession.setRepeatingRequest(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
                } catch (CameraAccessException unused) {
                    this.mWhiteBalance = i2;
                }
            }
        }
    }

    public int getWhiteBalance() {
        return this.mWhiteBalance;
    }

    void setPlaySoundOnCapture(boolean z) {
        this.mPlaySoundOnCapture = Boolean.valueOf(z);
    }

    public boolean getPlaySoundOnCapture() {
        return this.mPlaySoundOnCapture.booleanValue();
    }

    void setScanning(boolean z) {
        if (this.mIsScanning != z) {
            this.mIsScanning = z;
            if (this.mIsScanning) {
                this.mImageFormat = 35;
            } else {
                this.mImageFormat = 256;
            }
            CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
            if (cameraCaptureSession != null) {
                cameraCaptureSession.close();
                this.mCaptureSession = null;
            }
            startCaptureSession();
        }
    }

    boolean getScanning() {
        return this.mIsScanning;
    }

    int getCameraOrientation() {
        return this.mCameraOrientation;
    }

    void setDisplayOrientation(int i) {
        this.mDisplayOrientation = i;
        this.mPreview.setDisplayOrientation(this.mDisplayOrientation);
    }

    void setDeviceOrientation(int i) {
        this.mDeviceOrientation = i;
    }

    public static boolean isLegacy(Context context) {
        String str = TAG;
        try {
            CameraManager cameraManager = (CameraManager) context.getSystemService("camera");
            for (String cameraCharacteristics : cameraManager.getCameraIdList()) {
                Integer num = (Integer) cameraManager.getCameraCharacteristics(cameraCharacteristics).get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                if (num == null || num.intValue() == 2) {
                    Log.w(str, "Camera2 can only run in legacy mode and should not be used.");
                    return true;
                }
            }
            return false;
        } catch (Throwable e) {
            Log.e(str, "Failed to check camera legacy status, returning true.", e);
            return true;
        }
    }

    private boolean chooseCameraIdByFacing() {
        String str = this._mCameraId;
        String str2 = "Unexpected state: LENS_FACING null";
        String str3 = TAG;
        Integer num;
        int size;
        int i;
        if (str == null) {
            try {
                int i2 = INTERNAL_FACINGS.get(this.mFacing);
                String[] cameraIdList = this.mCameraManager.getCameraIdList();
                if (cameraIdList.length == 0) {
                    Log.e(str3, "No cameras available.");
                    return false;
                }
                for (String str4 : cameraIdList) {
                    CameraCharacteristics cameraCharacteristics = this.mCameraManager.getCameraCharacteristics(str4);
                    Integer num2 = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                    if (num2 == null) {
                        Log.e(str3, str2);
                    } else if (num2.intValue() == i2) {
                        this.mCameraId = str4;
                        this.mCameraCharacteristics = cameraCharacteristics;
                        return true;
                    }
                }
                this.mCameraId = cameraIdList[0];
                this.mCameraCharacteristics = this.mCameraManager.getCameraCharacteristics(this.mCameraId);
                num = (Integer) this.mCameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if (num == null) {
                    Log.e(str3, str2);
                    return false;
                }
                size = INTERNAL_FACINGS.size();
                for (i = 0; i < size; i++) {
                    if (INTERNAL_FACINGS.valueAt(i) == num.intValue()) {
                        this.mFacing = INTERNAL_FACINGS.keyAt(i);
                        return true;
                    }
                }
                this.mFacing = 0;
                return true;
            } catch (Throwable e) {
                Log.e(str3, "Failed to get a list of camera devices", e);
                return false;
            }
        }
        try {
            this.mCameraCharacteristics = this.mCameraManager.getCameraCharacteristics(str);
            num = (Integer) this.mCameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            if (num == null) {
                Log.e(str3, str2);
                return false;
            }
            size = INTERNAL_FACINGS.size();
            for (i = 0; i < size; i++) {
                if (INTERNAL_FACINGS.valueAt(i) == num.intValue()) {
                    this.mFacing = INTERNAL_FACINGS.keyAt(i);
                    break;
                }
            }
            this.mCameraId = this._mCameraId;
            return true;
        } catch (Throwable e2) {
            Log.e(str3, "Failed to get camera characteristics", e2);
            return false;
        }
    }

    private void collectCameraInfo() {
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) this.mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap != null) {
            this.mPreviewSizes.clear();
            for (Size size : streamConfigurationMap.getOutputSizes(this.mPreview.getOutputClass())) {
                int width = size.getWidth();
                int height = size.getHeight();
                if (width <= MAX_PREVIEW_WIDTH && height <= 1080) {
                    this.mPreviewSizes.add(new Size(width, height));
                }
            }
            this.mPictureSizes.clear();
            collectPictureSizes(this.mPictureSizes, streamConfigurationMap);
            if (this.mPictureSize == null) {
                this.mPictureSize = (Size) this.mPictureSizes.sizes(this.mAspectRatio).last();
            }
            for (AspectRatio aspectRatio : this.mPreviewSizes.ratios()) {
                if (!this.mPictureSizes.ratios().contains(aspectRatio)) {
                    this.mPreviewSizes.remove(aspectRatio);
                }
            }
            if (!this.mPreviewSizes.ratios().contains(this.mAspectRatio)) {
                this.mAspectRatio = (AspectRatio) this.mPreviewSizes.ratios().iterator().next();
            }
            this.mCameraOrientation = ((Integer) this.mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to get configuration map: ");
        stringBuilder.append(this.mCameraId);
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected void collectPictureSizes(SizeMap sizeMap, StreamConfigurationMap streamConfigurationMap) {
        for (Size size : streamConfigurationMap.getOutputSizes(this.mImageFormat)) {
            this.mPictureSizes.add(new Size(size.getWidth(), size.getHeight()));
        }
    }

    private void prepareStillImageReader() {
        ImageReader imageReader = this.mStillImageReader;
        if (imageReader != null) {
            imageReader.close();
        }
        this.mStillImageReader = ImageReader.newInstance(this.mPictureSize.getWidth(), this.mPictureSize.getHeight(), 256, 1);
        this.mStillImageReader.setOnImageAvailableListener(this.mOnImageAvailableListener, null);
    }

    private void prepareScanImageReader() {
        ImageReader imageReader = this.mScanImageReader;
        if (imageReader != null) {
            imageReader.close();
        }
        Size size = (Size) this.mPreviewSizes.sizes(this.mAspectRatio).last();
        this.mScanImageReader = ImageReader.newInstance(size.getWidth(), size.getHeight(), 35, 1);
        this.mScanImageReader.setOnImageAvailableListener(this.mOnImageAvailableListener, null);
    }

    private void startOpeningCamera() {
        try {
            this.mCameraManager.openCamera(this.mCameraId, this.mCameraDeviceCallback, null);
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to open camera: ");
            stringBuilder.append(this.mCameraId);
            throw new RuntimeException(stringBuilder.toString(), e);
        }
    }

    void startCaptureSession() {
        if (isCameraOpened() && this.mPreview.isReady() && this.mStillImageReader != null && this.mScanImageReader != null) {
            Size chooseOptimalSize = chooseOptimalSize();
            this.mPreview.setBufferSize(chooseOptimalSize.getWidth(), chooseOptimalSize.getHeight());
            Surface previewSurface = getPreviewSurface();
            try {
                this.mPreviewRequestBuilder = this.mCamera.createCaptureRequest(1);
                this.mPreviewRequestBuilder.addTarget(previewSurface);
                if (this.mIsScanning) {
                    this.mPreviewRequestBuilder.addTarget(this.mScanImageReader.getSurface());
                }
                this.mCamera.createCaptureSession(Arrays.asList(new Surface[]{previewSurface, this.mStillImageReader.getSurface(), this.mScanImageReader.getSurface()}), this.mSessionCallback, null);
            } catch (Throwable e) {
                Log.e(TAG, "Failed to start capture session", e);
                this.mCallback.onMountError();
            }
        }
    }

    public void resumePreview() {
        unlockFocus();
    }

    public void pausePreview() {
        try {
            this.mCaptureSession.stopRepeating();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public Surface getPreviewSurface() {
        Surface surface = this.mPreviewSurface;
        if (surface != null) {
            return surface;
        }
        return this.mPreview.getSurface();
    }

    public void setPreviewTexture(SurfaceTexture surfaceTexture) {
        if (surfaceTexture != null) {
            this.mPreviewSurface = new Surface(surfaceTexture);
        } else {
            this.mPreviewSurface = null;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (Camera2.this.mCaptureSession != null) {
                    Camera2.this.mCaptureSession.close();
                    Camera2.this.mCaptureSession = null;
                }
                Camera2.this.startCaptureSession();
            }
        });
    }

    public Size getPreviewSize() {
        return new Size(this.mPreview.getWidth(), this.mPreview.getHeight());
    }

    private Size chooseOptimalSize() {
        int width = this.mPreview.getWidth();
        int height = this.mPreview.getHeight();
        if (width < height) {
            int i = height;
            height = width;
            width = i;
        }
        SortedSet<Size> sizes = this.mPreviewSizes.sizes(this.mAspectRatio);
        for (Size size : sizes) {
            if (size.getWidth() >= width && size.getHeight() >= height) {
                return size;
            }
        }
        return (Size) sizes.last();
    }

    void updateAutoFocus() {
        boolean z = this.mAutoFocus;
        Integer valueOf = Integer.valueOf(0);
        if (z) {
            int[] iArr = (int[]) this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
            if (iArr == null || iArr.length == 0 || (iArr.length == 1 && iArr[0] == 0)) {
                this.mAutoFocus = false;
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, valueOf);
                return;
            }
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(4));
            return;
        }
        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, valueOf);
    }

    void updateFlash() {
        int i = this.mFlash;
        if (i == 0) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
            this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
        } else if (i == 1) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(3));
            this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
        } else if (i == 2) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
            this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(2));
        } else if (i == 3) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(2));
            this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
        } else if (i == 4) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(4));
            this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
        }
    }

    void updateFocusDepth() {
        if (!this.mAutoFocus) {
            Float f = (Float) this.mCameraCharacteristics.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
            if (f != null) {
                this.mPreviewRequestBuilder.set(CaptureRequest.LENS_FOCUS_DISTANCE, Float.valueOf(this.mFocusDepth * f.floatValue()));
                return;
            }
            throw new NullPointerException("Unexpected state: LENS_INFO_MINIMUM_FOCUS_DISTANCE null");
        }
    }

    void updateZoom() {
        float floatValue = (this.mZoom * (((Float) this.mCameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue() - 1.0f)) + 1.0f;
        Rect rect = (Rect) this.mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        if (rect != null) {
            int width = rect.width();
            int height = rect.height();
            width = (width - ((int) (((float) width) / floatValue))) / 2;
            height = (height - ((int) (((float) height) / floatValue))) / 2;
            Rect rect2 = new Rect(rect.left + width, rect.top + height, rect.right - width, rect.bottom - height);
            if (floatValue != 1.0f) {
                this.mPreviewRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, rect2);
            } else {
                this.mPreviewRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, this.mInitialCropRegion);
            }
        }
    }

    void updateWhiteBalance() {
        int i = this.mWhiteBalance;
        if (i == 0) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(1));
        } else if (i == 1) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(6));
        } else if (i == 2) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(5));
        } else if (i == 3) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(8));
        } else if (i == 4) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(3));
        } else if (i == 5) {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(2));
        }
    }

    private void lockFocus() {
        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(1));
        try {
            this.mCaptureCallback.setState(1);
            this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
        } catch (Throwable e) {
            Log.e(TAG, "Failed to lock focus.", e);
        }
    }

    void setFocusArea(float f, float f2) {
        String str = "Failed to manual focus.";
        String str2 = TAG;
        if (this.mCaptureSession != null) {
            CaptureCallback anonymousClass8 = new CaptureCallback() {
                public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
                    super.onCaptureCompleted(cameraCaptureSession, captureRequest, totalCaptureResult);
                    if (captureRequest.getTag() == "FOCUS_TAG") {
                        Camera2.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, null);
                        try {
                            Camera2.this.mCaptureSession.setRepeatingRequest(Camera2.this.mPreviewRequestBuilder.build(), null, null);
                        } catch (Throwable e) {
                            Log.e(Camera2.TAG, "Failed to manual focus.", e);
                        }
                    }
                }

                public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
                    super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Manual AF failure: ");
                    stringBuilder.append(captureFailure);
                    Log.e(Camera2.TAG, stringBuilder.toString());
                }
            };
            try {
                this.mCaptureSession.stopRepeating();
            } catch (Throwable e) {
                Log.e(str2, str, e);
            }
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(2));
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(0));
            try {
                this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), anonymousClass8, null);
            } catch (Throwable e2) {
                Log.e(str2, str, e2);
            }
            if (isMeteringAreaAFSupported()) {
                MeteringRectangle calculateFocusArea = calculateFocusArea(f, f2);
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_REGIONS, new MeteringRectangle[]{calculateFocusArea});
            }
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_MODE, Integer.valueOf(1));
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(1));
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(1));
            this.mPreviewRequestBuilder.setTag("FOCUS_TAG");
            try {
                this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), anonymousClass8, null);
            } catch (Throwable e3) {
                Log.e(str2, str, e3);
            }
        }
    }

    private boolean isMeteringAreaAFSupported() {
        return ((Integer) this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue() >= 1;
    }

    private MeteringRectangle calculateFocusArea(float f, float f2) {
        Rect rect = (Rect) this.mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        return new MeteringRectangle(Math.max(((int) (f * ((float) rect.width()))) - 150, 0), Math.max(((int) (f2 * ((float) rect.height()))) - 150, 0), 300, 300, 999);
    }

    void captureStillPicture() {
        String str = "quality";
        try {
            Builder createCaptureRequest = this.mCamera.createCaptureRequest(2);
            if (this.mIsScanning) {
                this.mImageFormat = 256;
                createCaptureRequest.removeTarget(this.mScanImageReader.getSurface());
            }
            createCaptureRequest.addTarget(this.mStillImageReader.getSurface());
            createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, this.mPreviewRequestBuilder.get(CaptureRequest.CONTROL_AF_MODE));
            int i = this.mFlash;
            if (i == 0) {
                createCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
                createCaptureRequest.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
            } else if (i == 1) {
                createCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(3));
            } else if (i == 2) {
                createCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
                createCaptureRequest.set(CaptureRequest.FLASH_MODE, Integer.valueOf(2));
            } else if (i == 3) {
                createCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(2));
            } else if (i == 4) {
                createCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(2));
            }
            createCaptureRequest.set(CaptureRequest.JPEG_ORIENTATION, Integer.valueOf(getOutputRotation()));
            if (this.mCaptureCallback.getOptions().hasKey(str)) {
                createCaptureRequest.set(CaptureRequest.JPEG_QUALITY, Byte.valueOf((byte) ((int) (this.mCaptureCallback.getOptions().getDouble(str) * 100.0d))));
            }
            createCaptureRequest.set(CaptureRequest.SCALER_CROP_REGION, this.mPreviewRequestBuilder.get(CaptureRequest.SCALER_CROP_REGION));
            this.mCaptureSession.stopRepeating();
            this.mCaptureSession.capture(createCaptureRequest.build(), new CaptureCallback() {
                public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                    String str = "pauseAfterCapture";
                    if (Camera2.this.mCaptureCallback.getOptions().hasKey(str) && !Camera2.this.mCaptureCallback.getOptions().getBoolean(str)) {
                        Camera2.this.unlockFocus();
                    }
                    if (Camera2.this.mPlaySoundOnCapture.booleanValue()) {
                        Camera2.this.sound.play(0);
                    }
                }
            }, null);
        } catch (Throwable e) {
            Log.e(TAG, "Cannot capture a still picture.", e);
        }
    }

    private int getOutputRotation() {
        int intValue = ((Integer) this.mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        if (this.mFacing == 0) {
            return (intValue + this.mDeviceOrientation) % 360;
        }
        return ((intValue + this.mDeviceOrientation) + (isLandscape(this.mDeviceOrientation) ? 180 : 0)) % 360;
    }

    private void setUpMediaRecorder(String str, int i, int i2, boolean z, CamcorderProfile camcorderProfile) {
        this.mMediaRecorder = new MediaRecorder();
        this.mMediaRecorder.setVideoSource(2);
        if (z) {
            this.mMediaRecorder.setAudioSource(1);
        }
        this.mMediaRecorder.setOutputFile(str);
        this.mVideoPath = str;
        CamcorderProfile camcorderProfile2 = !CamcorderProfile.hasProfile(Integer.parseInt(this.mCameraId), camcorderProfile.quality) ? CamcorderProfile.get(1) : camcorderProfile;
        camcorderProfile2.videoBitRate = camcorderProfile.videoBitRate;
        setCamcorderProfile(camcorderProfile2, z);
        this.mMediaRecorder.setOrientationHint(getOutputRotation());
        if (i != -1) {
            this.mMediaRecorder.setMaxDuration(i);
        }
        if (i2 != -1) {
            this.mMediaRecorder.setMaxFileSize((long) i2);
        }
        this.mMediaRecorder.setOnInfoListener(this);
        this.mMediaRecorder.setOnErrorListener(this);
    }

    private void setCamcorderProfile(CamcorderProfile camcorderProfile, boolean z) {
        this.mMediaRecorder.setOutputFormat(camcorderProfile.fileFormat);
        this.mMediaRecorder.setVideoFrameRate(camcorderProfile.videoFrameRate);
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

    private void stopMediaRecorder() {
        this.mIsRecording = false;
        try {
            this.mCaptureSession.stopRepeating();
            this.mCaptureSession.abortCaptures();
            this.mMediaRecorder.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mMediaRecorder.reset();
        this.mMediaRecorder.release();
        this.mMediaRecorder = null;
        this.mCallback.onRecordingEnd();
        String str = this.mVideoPath;
        if (str == null || !new File(str).exists()) {
            this.mCallback.onVideoRecorded(null, 0, 0);
            return;
        }
        this.mCallback.onVideoRecorded(this.mVideoPath, 0, 0);
        this.mVideoPath = null;
    }

    void unlockFocus() {
        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(2));
        try {
            this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
            updateAutoFocus();
            updateFlash();
            if (this.mIsScanning) {
                this.mImageFormat = 35;
                startCaptureSession();
                return;
            }
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(0));
            this.mCaptureSession.setRepeatingRequest(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
            this.mCaptureCallback.setState(0);
        } catch (Throwable e) {
            Log.e(TAG, "Failed to restart camera preview.", e);
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
