package com.lwansbrough.RCTCamera;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.CamcorderProfile;
import android.media.MediaActionSound;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.RNFetchBlob.RNFetchBlobConst;
import com.brentvatne.react.ReactVideoView;
import com.drew.metadata.exif.makernotes.LeicaMakernoteDirectory;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.ViewProps;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class RCTCameraModule extends ReactContextBaseJavaModule implements OnInfoListener, OnErrorListener, LifecycleEventListener {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int RCT_CAMERA_ASPECT_FILL = 0;
    public static final int RCT_CAMERA_ASPECT_FIT = 1;
    public static final int RCT_CAMERA_ASPECT_STRETCH = 2;
    public static final int RCT_CAMERA_CAPTURE_MODE_STILL = 0;
    public static final int RCT_CAMERA_CAPTURE_MODE_VIDEO = 1;
    public static final String RCT_CAMERA_CAPTURE_QUALITY_1080P = "1080p";
    public static final String RCT_CAMERA_CAPTURE_QUALITY_480P = "480p";
    public static final String RCT_CAMERA_CAPTURE_QUALITY_720P = "720p";
    public static final String RCT_CAMERA_CAPTURE_QUALITY_HIGH = "high";
    public static final String RCT_CAMERA_CAPTURE_QUALITY_LOW = "low";
    public static final String RCT_CAMERA_CAPTURE_QUALITY_MEDIUM = "medium";
    public static final String RCT_CAMERA_CAPTURE_QUALITY_PREVIEW = "preview";
    public static final int RCT_CAMERA_CAPTURE_TARGET_CAMERA_ROLL = 2;
    public static final int RCT_CAMERA_CAPTURE_TARGET_DISK = 1;
    public static final int RCT_CAMERA_CAPTURE_TARGET_MEMORY = 0;
    public static final int RCT_CAMERA_CAPTURE_TARGET_TEMP = 3;
    public static final int RCT_CAMERA_FLASH_MODE_AUTO = 2;
    public static final int RCT_CAMERA_FLASH_MODE_OFF = 0;
    public static final int RCT_CAMERA_FLASH_MODE_ON = 1;
    public static final int RCT_CAMERA_ORIENTATION_AUTO = Integer.MAX_VALUE;
    public static final int RCT_CAMERA_ORIENTATION_LANDSCAPE_LEFT = 1;
    public static final int RCT_CAMERA_ORIENTATION_LANDSCAPE_RIGHT = 3;
    public static final int RCT_CAMERA_ORIENTATION_PORTRAIT = 0;
    public static final int RCT_CAMERA_ORIENTATION_PORTRAIT_UPSIDE_DOWN = 2;
    public static final int RCT_CAMERA_TORCH_MODE_AUTO = 2;
    public static final int RCT_CAMERA_TORCH_MODE_OFF = 0;
    public static final int RCT_CAMERA_TORCH_MODE_ON = 1;
    public static final int RCT_CAMERA_TYPE_BACK = 2;
    public static final int RCT_CAMERA_TYPE_FRONT = 1;
    private static final String TAG = "RCTCameraModule";
    private static ReactApplicationContext _reactContext;
    private long MRStartTime;
    private RCTSensorOrientationChecker _sensorOrientationChecker;
    private Camera mCamera = null;
    private MediaRecorder mMediaRecorder;
    private ReadableMap mRecordingOptions;
    private Promise mRecordingPromise = null;
    private Boolean mSafeToCapture = Boolean.valueOf(true);
    private File mVideoFile;

    public String getName() {
        return TAG;
    }

    public void onHostDestroy() {
    }

    public RCTCameraModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        _reactContext = reactApplicationContext;
        this._sensorOrientationChecker = new RCTSensorOrientationChecker(_reactContext);
        _reactContext.addLifecycleEventListener(this);
    }

    public static ReactApplicationContext getReactContextSingleton() {
        return _reactContext;
    }

    public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
        if ((i == 800 || i == LeicaMakernoteDirectory.TAG_COLOR_TEMPERATURE) && this.mRecordingPromise != null) {
            releaseMediaRecorder();
        }
    }

    public void onError(MediaRecorder mediaRecorder, int i, int i2) {
        if (this.mRecordingPromise != null) {
            releaseMediaRecorder();
        }
    }

    @Nullable
    public Map<String, Object> getConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("Aspect", getAspectConstants());
                put("BarCodeType", getBarCodeConstants());
                put("Type", getTypeConstants());
                put("CaptureQuality", getCaptureQualityConstants());
                put("CaptureMode", getCaptureModeConstants());
                put("CaptureTarget", getCaptureTargetConstants());
                put(ExifInterface.TAG_ORIENTATION, getOrientationConstants());
                put("FlashMode", getFlashModeConstants());
                put("TorchMode", getTorchModeConstants());
            }

            private Map<String, Object> getAspectConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("stretch", Integer.valueOf(2));
                        put("fit", Integer.valueOf(1));
                        put("fill", Integer.valueOf(0));
                    }
                });
            }

            private Map<String, Object> getBarCodeConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                });
            }

            private Map<String, Object> getTypeConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("front", Integer.valueOf(1));
                        put("back", Integer.valueOf(2));
                    }
                });
            }

            private Map<String, Object> getCaptureQualityConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        String str = RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_LOW;
                        put(str, str);
                        str = "medium";
                        put(str, str);
                        str = RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_HIGH;
                        put(str, str);
                        put("photo", str);
                        str = RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_PREVIEW;
                        put(str, str);
                        str = RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_480P;
                        put(str, str);
                        str = RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_720P;
                        put(str, str);
                        str = RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_1080P;
                        put(str, str);
                    }
                });
            }

            private Map<String, Object> getCaptureModeConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("still", Integer.valueOf(0));
                        put("video", Integer.valueOf(1));
                    }
                });
            }

            private Map<String, Object> getCaptureTargetConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("memory", Integer.valueOf(0));
                        put("disk", Integer.valueOf(1));
                        put("cameraRoll", Integer.valueOf(2));
                        put("temp", Integer.valueOf(3));
                    }
                });
            }

            private Map<String, Object> getOrientationConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("auto", Integer.valueOf(Integer.MAX_VALUE));
                        put("landscapeLeft", Integer.valueOf(1));
                        put("landscapeRight", Integer.valueOf(3));
                        put("portrait", Integer.valueOf(0));
                        put("portraitUpsideDown", Integer.valueOf(2));
                    }
                });
            }

            private Map<String, Object> getFlashModeConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("off", Integer.valueOf(0));
                        put(ViewProps.ON, Integer.valueOf(1));
                        put("auto", Integer.valueOf(2));
                    }
                });
            }

            private Map<String, Object> getTorchModeConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("off", Integer.valueOf(0));
                        put(ViewProps.ON, Integer.valueOf(1));
                        put("auto", Integer.valueOf(2));
                    }
                });
            }
        });
    }

    private Throwable prepareMediaRecorder(ReadableMap readableMap, int i) {
        CamcorderProfile captureVideoQuality = RCTCamera.getInstance().setCaptureVideoQuality(readableMap.getInt("type"), readableMap.getString("quality"));
        if (captureVideoQuality == null) {
            return new RuntimeException("CamcorderProfile not found in prepareMediaRecorder.");
        }
        this.mCamera.unlock();
        this.mMediaRecorder = new MediaRecorder();
        this.mMediaRecorder.setOnInfoListener(this);
        this.mMediaRecorder.setOnErrorListener(this);
        this.mMediaRecorder.setCamera(this.mCamera);
        this.mMediaRecorder.setAudioSource(5);
        this.mMediaRecorder.setVideoSource(1);
        if (i == 0) {
            this.mMediaRecorder.setOrientationHint(90);
        } else if (i == 1) {
            this.mMediaRecorder.setOrientationHint(0);
        } else if (i == 2) {
            this.mMediaRecorder.setOrientationHint(270);
        } else if (i == 3) {
            this.mMediaRecorder.setOrientationHint(180);
        }
        captureVideoQuality.fileFormat = 2;
        this.mMediaRecorder.setProfile(captureVideoQuality);
        this.mVideoFile = null;
        int i2 = readableMap.getInt("target");
        if (i2 == 0) {
            this.mVideoFile = getTempMediaFile(2);
        } else if (i2 == 2) {
            this.mVideoFile = getOutputCameraRollFile(2);
        } else if (i2 != 3) {
            this.mVideoFile = getOutputMediaFile(2);
        } else {
            this.mVideoFile = getTempMediaFile(2);
        }
        File file = this.mVideoFile;
        if (file == null) {
            return new RuntimeException("Error while preparing output file in prepareMediaRecorder.");
        }
        this.mMediaRecorder.setOutputFile(file.getPath());
        String str = "totalSeconds";
        if (readableMap.hasKey(str)) {
            this.mMediaRecorder.setMaxDuration(readableMap.getInt(str) * 1000);
        }
        str = "maxFileSize";
        if (readableMap.hasKey(str)) {
            this.mMediaRecorder.setMaxFileSize((long) readableMap.getInt(str));
        }
        try {
            this.mMediaRecorder.prepare();
            return null;
        } catch (Throwable e) {
            Log.e(TAG, "Media recorder prepare error.", e);
            releaseMediaRecorder();
            return e;
        }
    }

    private void record(ReadableMap readableMap, Promise promise, int i) {
        if (this.mRecordingPromise == null) {
            this.mCamera = RCTCamera.getInstance().acquireCameraInstance(readableMap.getInt("type"));
            if (this.mCamera == null) {
                promise.reject(new RuntimeException("No camera found."));
                return;
            }
            Throwable prepareMediaRecorder = prepareMediaRecorder(readableMap, i);
            if (prepareMediaRecorder != null) {
                promise.reject(prepareMediaRecorder);
                return;
            }
            try {
                this.mMediaRecorder.start();
                this.MRStartTime = System.currentTimeMillis();
                this.mRecordingOptions = readableMap;
                this.mRecordingPromise = promise;
            } catch (Throwable e) {
                Log.e(TAG, "Media recorder start error.", e);
                promise.reject(e);
            }
        }
    }

    /* JADX WARNING: Missing block: B:30:0x0080, code:
            if (r4 != 3) goto L_0x0140;
     */
    private void releaseMediaRecorder() {
        /*
        r6 = this;
        r0 = java.lang.System.currentTimeMillis();
        r2 = r6.MRStartTime;
        r0 = r0 - r2;
        r2 = "RCTCameraModule";
        r3 = 1500; // 0x5dc float:2.102E-42 double:7.41E-321;
        r5 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1));
        if (r5 >= 0) goto L_0x001a;
    L_0x000f:
        r3 = r3 - r0;
        java.lang.Thread.sleep(r3);	 Catch:{ InterruptedException -> 0x0014 }
        goto L_0x001a;
    L_0x0014:
        r0 = move-exception;
        r1 = "releaseMediaRecorder thread sleep error.";
        android.util.Log.e(r2, r1, r0);
    L_0x001a:
        r0 = r6.mMediaRecorder;
        r1 = 0;
        if (r0 == 0) goto L_0x0035;
    L_0x001f:
        r0.stop();	 Catch:{ RuntimeException -> 0x0023 }
        goto L_0x0029;
    L_0x0023:
        r0 = move-exception;
        r3 = "Media recorder stop error.";
        android.util.Log.e(r2, r3, r0);
    L_0x0029:
        r0 = r6.mMediaRecorder;
        r0.reset();
        r0 = r6.mMediaRecorder;
        r0.release();
        r6.mMediaRecorder = r1;
    L_0x0035:
        r0 = r6.mCamera;
        if (r0 == 0) goto L_0x003c;
    L_0x0039:
        r0.lock();
    L_0x003c:
        r0 = r6.mRecordingPromise;
        if (r0 != 0) goto L_0x0041;
    L_0x0040:
        return;
    L_0x0041:
        r0 = new java.io.File;
        r2 = r6.mVideoFile;
        r2 = r2.getPath();
        r0.<init>(r2);
        r2 = r0.exists();
        if (r2 != 0) goto L_0x0061;
    L_0x0052:
        r0 = r6.mRecordingPromise;
        r2 = new java.lang.RuntimeException;
        r3 = "There is nothing recorded.";
        r2.<init>(r3);
        r0.reject(r2);
        r6.mRecordingPromise = r1;
        return;
    L_0x0061:
        r2 = 0;
        r3 = 1;
        r0.setReadable(r3, r2);
        r0.setWritable(r3, r2);
        r2 = new com.facebook.react.bridge.WritableNativeMap;
        r2.<init>();
        r4 = r6.mRecordingOptions;
        r5 = "target";
        r4 = r4.getInt(r5);
        r5 = 2;
        if (r4 == 0) goto L_0x0128;
    L_0x0079:
        r0 = "path";
        if (r4 == r3) goto L_0x0115;
    L_0x007d:
        if (r4 == r5) goto L_0x0084;
    L_0x007f:
        r3 = 3;
        if (r4 == r3) goto L_0x0115;
    L_0x0082:
        goto L_0x0140;
    L_0x0084:
        r3 = new android.content.ContentValues;
        r3.<init>();
        r4 = r6.mVideoFile;
        r4 = r4.getPath();
        r5 = "_data";
        r3.put(r5, r4);
        r4 = r6.mRecordingOptions;
        r5 = "title";
        r4 = r4.hasKey(r5);
        if (r4 == 0) goto L_0x00a5;
    L_0x009e:
        r4 = r6.mRecordingOptions;
        r4 = r4.getString(r5);
        goto L_0x00a7;
    L_0x00a5:
        r4 = "video";
    L_0x00a7:
        r3.put(r5, r4);
        r4 = r6.mRecordingOptions;
        r5 = "description";
        r4 = r4.hasKey(r5);
        if (r4 == 0) goto L_0x00c1;
    L_0x00b4:
        r4 = r6.mRecordingOptions;
        r4 = r4.hasKey(r5);
        r4 = java.lang.Boolean.valueOf(r4);
        r3.put(r5, r4);
    L_0x00c1:
        r4 = r6.mRecordingOptions;
        r5 = "latitude";
        r4 = r4.hasKey(r5);
        if (r4 == 0) goto L_0x00d4;
    L_0x00cb:
        r4 = r6.mRecordingOptions;
        r4 = r4.getString(r5);
        r3.put(r5, r4);
    L_0x00d4:
        r4 = r6.mRecordingOptions;
        r5 = "longitude";
        r4 = r4.hasKey(r5);
        if (r4 == 0) goto L_0x00e7;
    L_0x00de:
        r4 = r6.mRecordingOptions;
        r4 = r4.getString(r5);
        r3.put(r5, r4);
    L_0x00e7:
        r4 = "mime_type";
        r5 = "video/mp4";
        r3.put(r4, r5);
        r4 = _reactContext;
        r4 = r4.getContentResolver();
        r5 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        r4.insert(r5, r3);
        r3 = r6.mVideoFile;
        r3 = r3.getAbsolutePath();
        r6.addToMediaStore(r3);
        r3 = r6.mVideoFile;
        r3 = android.net.Uri.fromFile(r3);
        r3 = r3.toString();
        r2.putString(r0, r3);
        r0 = r6.mRecordingPromise;
        r0.resolve(r2);
        goto L_0x0140;
    L_0x0115:
        r3 = r6.mVideoFile;
        r3 = android.net.Uri.fromFile(r3);
        r3 = r3.toString();
        r2.putString(r0, r3);
        r0 = r6.mRecordingPromise;
        r0.resolve(r2);
        goto L_0x0140;
    L_0x0128:
        r3 = r6.mVideoFile;
        r3 = convertFileToByteArray(r3);
        r4 = new java.lang.String;
        r4.<init>(r3, r5);
        r3 = "data";
        r2.putString(r3, r4);
        r3 = r6.mRecordingPromise;
        r3.resolve(r2);
        r0.delete();
    L_0x0140:
        r6.mRecordingPromise = r1;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lwansbrough.RCTCamera.RCTCameraModule.releaseMediaRecorder():void");
    }

    public static byte[] convertFileToByteArray(File file) {
        try {
            InputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[8192];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @ReactMethod
    public void capture(final ReadableMap readableMap, final Promise promise) {
        if (RCTCamera.getInstance() == null) {
            promise.reject("Camera is not ready yet.");
            return;
        }
        String str = ReactVideoView.EVENT_PROP_ORIENTATION;
        int i = readableMap.hasKey(str) ? readableMap.getInt(str) : RCTCamera.getInstance().getOrientation();
        if (i == Integer.MAX_VALUE) {
            this._sensorOrientationChecker.onResume();
            this._sensorOrientationChecker.registerOrientationListener(new RCTSensorOrientationListener() {
                public void orientationEvent() {
                    int orientation = RCTCameraModule.this._sensorOrientationChecker.getOrientation();
                    RCTCameraModule.this._sensorOrientationChecker.unregisterOrientationListener();
                    RCTCameraModule.this._sensorOrientationChecker.onPause();
                    RCTCameraModule.this.captureWithOrientation(readableMap, promise, orientation);
                }
            });
        } else {
            captureWithOrientation(readableMap, promise, i);
        }
    }

    private void captureWithOrientation(final ReadableMap readableMap, final Promise promise, int i) {
        String str = "type";
        final Camera acquireCameraInstance = RCTCamera.getInstance().acquireCameraInstance(readableMap.getInt(str));
        if (acquireCameraInstance == null) {
            promise.reject("No camera found.");
        } else if (readableMap.getInt("mode") == 1) {
            record(readableMap, promise, i);
        } else {
            String str2 = "quality";
            RCTCamera.getInstance().setCaptureQuality(readableMap.getInt(str), readableMap.getString(str2));
            String str3 = "playSoundOnCapture";
            if (readableMap.hasKey(str3) && readableMap.getBoolean(str3)) {
                new MediaActionSound().play(0);
            }
            if (readableMap.hasKey(str2)) {
                RCTCamera.getInstance().setCaptureQuality(readableMap.getInt(str), readableMap.getString(str2));
            }
            RCTCamera.getInstance().adjustCameraRotationToDeviceOrientation(readableMap.getInt(str), i);
            acquireCameraInstance.setPreviewCallback(null);
            PictureCallback anonymousClass3 = new PictureCallback() {
                public void onPictureTaken(final byte[] bArr, Camera camera) {
                    camera.stopPreview();
                    camera.startPreview();
                    AsyncTask.execute(new Runnable() {
                        public void run() {
                            RCTCameraModule.this.processImage(new MutableImage(bArr), readableMap, promise);
                        }
                    });
                    RCTCameraModule.this.mSafeToCapture = Boolean.valueOf(true);
                }
            };
            ShutterCallback anonymousClass4 = new ShutterCallback() {
                public void onShutter() {
                    try {
                        acquireCameraInstance.setPreviewCallback(null);
                        acquireCameraInstance.setPreviewTexture(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            if (this.mSafeToCapture.booleanValue()) {
                try {
                    acquireCameraInstance.takePicture(anonymousClass4, null, anonymousClass3);
                    this.mSafeToCapture = Boolean.valueOf(false);
                } catch (Throwable e) {
                    Log.e(TAG, "Couldn't capture photo.", e);
                }
            }
        }
    }

    /* JADX WARNING: Missing block: B:121:0x015a, code:
            return;
     */
    private synchronized void processImage(com.lwansbrough.RCTCamera.MutableImage r12, com.facebook.react.bridge.ReadableMap r13, com.facebook.react.bridge.Promise r14) {
        /*
        r11 = this;
        monitor-enter(r11);
        r0 = "fixOrientation";
        r0 = r13.hasKey(r0);	 Catch:{ all -> 0x015b }
        r1 = 0;
        r2 = 1;
        if (r0 == 0) goto L_0x0015;
    L_0x000b:
        r0 = "fixOrientation";
        r0 = r13.getBoolean(r0);	 Catch:{ all -> 0x015b }
        if (r0 == 0) goto L_0x0015;
    L_0x0013:
        r0 = 1;
        goto L_0x0016;
    L_0x0015:
        r0 = 0;
    L_0x0016:
        if (r0 == 0) goto L_0x0022;
    L_0x0018:
        r12.fixOrientation();	 Catch:{ ImageMutationFailedException -> 0x001c }
        goto L_0x0022;
    L_0x001c:
        r0 = move-exception;
        r3 = "Error fixing orientation image";
        r14.reject(r3, r0);	 Catch:{ all -> 0x015b }
    L_0x0022:
        r0 = r12.getWidth();	 Catch:{ all -> 0x015b }
        r3 = (double) r0;	 Catch:{ all -> 0x015b }
        r0 = r12.getHeight();	 Catch:{ all -> 0x015b }
        r5 = (double) r0;
        r3 = r3 / r5;
        r5 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r0 = "type";
        r0 = r13.getInt(r0);	 Catch:{ IllegalArgumentException -> 0x005d }
        r7 = com.lwansbrough.RCTCamera.RCTCamera.getInstance();	 Catch:{ IllegalArgumentException -> 0x005d }
        r7 = r7.getPreviewVisibleWidth(r0);	 Catch:{ IllegalArgumentException -> 0x005d }
        r7 = (double) r7;	 Catch:{ IllegalArgumentException -> 0x005d }
        r9 = com.lwansbrough.RCTCamera.RCTCamera.getInstance();	 Catch:{ IllegalArgumentException -> 0x005d }
        r0 = r9.getPreviewVisibleHeight(r0);	 Catch:{ IllegalArgumentException -> 0x005d }
        r9 = (double) r0;
        r7 = r7 / r9;
        r0 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1));
        if (r0 <= 0) goto L_0x004e;
    L_0x004c:
        r0 = 1;
        goto L_0x004f;
    L_0x004e:
        r0 = 0;
    L_0x004f:
        r9 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r9 <= 0) goto L_0x0055;
    L_0x0053:
        r3 = 1;
        goto L_0x0056;
    L_0x0055:
        r3 = 0;
    L_0x0056:
        if (r0 == r3) goto L_0x005a;
    L_0x0058:
        r0 = 1;
        goto L_0x005b;
    L_0x005a:
        r0 = 0;
    L_0x005b:
        r3 = r7;
        goto L_0x005e;
    L_0x005d:
        r0 = 0;
    L_0x005e:
        r7 = "cropToPreview";
        r7 = r13.hasKey(r7);	 Catch:{ all -> 0x015b }
        if (r7 == 0) goto L_0x0070;
    L_0x0066:
        r7 = "cropToPreview";
        r7 = r13.getBoolean(r7);	 Catch:{ all -> 0x015b }
        if (r7 == 0) goto L_0x0070;
    L_0x006e:
        r7 = 1;
        goto L_0x0071;
    L_0x0070:
        r7 = 0;
    L_0x0071:
        if (r7 == 0) goto L_0x0081;
    L_0x0073:
        if (r0 == 0) goto L_0x0077;
    L_0x0075:
        r3 = r5 / r3;
    L_0x0077:
        r12.cropToPreview(r3);	 Catch:{ IllegalArgumentException -> 0x007b }
        goto L_0x0081;
    L_0x007b:
        r3 = move-exception;
        r4 = "Error cropping image to preview";
        r14.reject(r4, r3);	 Catch:{ all -> 0x015b }
    L_0x0081:
        r3 = "mirrorImage";
        r3 = r13.hasKey(r3);	 Catch:{ all -> 0x015b }
        if (r3 == 0) goto L_0x0092;
    L_0x0089:
        r3 = "mirrorImage";
        r3 = r13.getBoolean(r3);	 Catch:{ all -> 0x015b }
        if (r3 == 0) goto L_0x0092;
    L_0x0091:
        r1 = 1;
    L_0x0092:
        if (r1 == 0) goto L_0x009e;
    L_0x0094:
        r12.mirrorImage();	 Catch:{ ImageMutationFailedException -> 0x0098 }
        goto L_0x009e;
    L_0x0098:
        r1 = move-exception;
        r3 = "Error mirroring image";
        r14.reject(r3, r1);	 Catch:{ all -> 0x015b }
    L_0x009e:
        r1 = 80;
        r3 = "jpegQuality";
        r3 = r13.hasKey(r3);	 Catch:{ all -> 0x015b }
        if (r3 == 0) goto L_0x00ae;
    L_0x00a8:
        r1 = "jpegQuality";
        r1 = r13.getInt(r1);	 Catch:{ all -> 0x015b }
    L_0x00ae:
        if (r0 == 0) goto L_0x00b5;
    L_0x00b0:
        r3 = r12.getHeight();	 Catch:{ all -> 0x015b }
        goto L_0x00b9;
    L_0x00b5:
        r3 = r12.getWidth();	 Catch:{ all -> 0x015b }
    L_0x00b9:
        r6 = r3;
        if (r0 == 0) goto L_0x00c1;
    L_0x00bc:
        r0 = r12.getWidth();	 Catch:{ all -> 0x015b }
        goto L_0x00c5;
    L_0x00c1:
        r0 = r12.getHeight();	 Catch:{ all -> 0x015b }
    L_0x00c5:
        r7 = r0;
        r0 = "target";
        r0 = r13.getInt(r0);	 Catch:{ all -> 0x015b }
        if (r0 == 0) goto L_0x013e;
    L_0x00ce:
        if (r0 == r2) goto L_0x011f;
    L_0x00d0:
        r3 = 2;
        if (r0 == r3) goto L_0x00f7;
    L_0x00d3:
        r3 = 3;
        if (r0 == r3) goto L_0x00d8;
    L_0x00d6:
        goto L_0x0159;
    L_0x00d8:
        r5 = r11.getTempMediaFile(r2);	 Catch:{ all -> 0x015b }
        if (r5 != 0) goto L_0x00e5;
    L_0x00de:
        r12 = "Error creating media file.";
        r14.reject(r12);	 Catch:{ all -> 0x015b }
        monitor-exit(r11);
        return;
    L_0x00e5:
        r12.writeDataToFile(r5, r13, r1);	 Catch:{ IOException -> 0x00ef }
        r9 = 0;
        r4 = r11;
        r8 = r14;
        r4.resolveImage(r5, r6, r7, r8, r9);	 Catch:{ all -> 0x015b }
        goto L_0x0159;
    L_0x00ef:
        r12 = move-exception;
        r13 = "failed to save image file";
        r14.reject(r13, r12);	 Catch:{ all -> 0x015b }
        monitor-exit(r11);
        return;
    L_0x00f7:
        r5 = r11.getOutputCameraRollFile(r2);	 Catch:{ all -> 0x015b }
        if (r5 != 0) goto L_0x0104;
    L_0x00fd:
        r12 = "Error creating media file.";
        r14.reject(r12);	 Catch:{ all -> 0x015b }
        monitor-exit(r11);
        return;
    L_0x0104:
        r12.writeDataToFile(r5, r13, r1);	 Catch:{ IOException -> 0x0117, NullPointerException -> 0x0115 }
        r12 = r5.getAbsolutePath();	 Catch:{ all -> 0x015b }
        r11.addToMediaStore(r12);	 Catch:{ all -> 0x015b }
        r9 = 1;
        r4 = r11;
        r8 = r14;
        r4.resolveImage(r5, r6, r7, r8, r9);	 Catch:{ all -> 0x015b }
        goto L_0x0159;
    L_0x0115:
        r12 = move-exception;
        goto L_0x0118;
    L_0x0117:
        r12 = move-exception;
    L_0x0118:
        r13 = "failed to save image file";
        r14.reject(r13, r12);	 Catch:{ all -> 0x015b }
        monitor-exit(r11);
        return;
    L_0x011f:
        r5 = r11.getOutputMediaFile(r2);	 Catch:{ all -> 0x015b }
        if (r5 != 0) goto L_0x012c;
    L_0x0125:
        r12 = "Error creating media file.";
        r14.reject(r12);	 Catch:{ all -> 0x015b }
        monitor-exit(r11);
        return;
    L_0x012c:
        r12.writeDataToFile(r5, r13, r1);	 Catch:{ IOException -> 0x0136 }
        r9 = 0;
        r4 = r11;
        r8 = r14;
        r4.resolveImage(r5, r6, r7, r8, r9);	 Catch:{ all -> 0x015b }
        goto L_0x0159;
    L_0x0136:
        r12 = move-exception;
        r13 = "failed to save image file";
        r14.reject(r13, r12);	 Catch:{ all -> 0x015b }
        monitor-exit(r11);
        return;
    L_0x013e:
        r12 = r12.toBase64(r1);	 Catch:{ all -> 0x015b }
        r13 = new com.facebook.react.bridge.WritableNativeMap;	 Catch:{ all -> 0x015b }
        r13.<init>();	 Catch:{ all -> 0x015b }
        r0 = "data";
        r13.putString(r0, r12);	 Catch:{ all -> 0x015b }
        r12 = "width";
        r13.putInt(r12, r6);	 Catch:{ all -> 0x015b }
        r12 = "height";
        r13.putInt(r12, r7);	 Catch:{ all -> 0x015b }
        r14.resolve(r13);	 Catch:{ all -> 0x015b }
    L_0x0159:
        monitor-exit(r11);
        return;
    L_0x015b:
        r12 = move-exception;
        monitor-exit(r11);
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lwansbrough.RCTCamera.RCTCameraModule.processImage(com.lwansbrough.RCTCamera.MutableImage, com.facebook.react.bridge.ReadableMap, com.facebook.react.bridge.Promise):void");
    }

    @ReactMethod
    public void stopCapture(Promise promise) {
        if (this.mRecordingPromise != null) {
            releaseMediaRecorder();
            promise.resolve("Finished recording.");
            return;
        }
        promise.resolve("Not recording.");
    }

    @ReactMethod
    public void hasFlash(ReadableMap readableMap, Promise promise) {
        Camera acquireCameraInstance = RCTCamera.getInstance().acquireCameraInstance(readableMap.getInt("type"));
        if (acquireCameraInstance == null) {
            promise.reject("No camera found.");
            return;
        }
        List supportedFlashModes = acquireCameraInstance.getParameters().getSupportedFlashModes();
        boolean z = (supportedFlashModes == null || supportedFlashModes.isEmpty()) ? false : true;
        promise.resolve(Boolean.valueOf(z));
    }

    @ReactMethod
    public void setZoom(ReadableMap readableMap, int i) {
        RCTCamera instance = RCTCamera.getInstance();
        if (instance != null) {
            Camera acquireCameraInstance = instance.acquireCameraInstance(readableMap.getInt("type"));
            if (acquireCameraInstance != null) {
                Parameters parameters = acquireCameraInstance.getParameters();
                int maxZoom = parameters.getMaxZoom();
                if (parameters.isZoomSupported() && i >= 0 && i < maxZoom) {
                    parameters.setZoom(i);
                    try {
                        acquireCameraInstance.setParameters(parameters);
                    } catch (Throwable e) {
                        Log.e(TAG, "setParameters failed", e);
                    }
                }
            }
        }
    }

    private File getOutputMediaFile(int i) {
        String str;
        if (i == 1) {
            str = Environment.DIRECTORY_PICTURES;
        } else if (i == 2) {
            str = Environment.DIRECTORY_MOVIES;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported media type:");
            stringBuilder.append(i);
            Log.e(TAG, stringBuilder.toString());
            return null;
        }
        return getOutputFile(i, Environment.getExternalStoragePublicDirectory(str));
    }

    private File getOutputCameraRollFile(int i) {
        return getOutputFile(i, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

    private File getOutputFile(int i, File file) {
        boolean exists = file.exists();
        String str = TAG;
        if (exists || file.mkdirs()) {
            String format;
            String format2 = String.format("%s", new Object[]{new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())});
            if (i == 1) {
                format = String.format("IMG_%s.jpg", new Object[]{format2});
            } else if (i == 2) {
                format = String.format("VID_%s.mp4", new Object[]{format2});
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported media type:");
                stringBuilder.append(i);
                Log.e(str, stringBuilder.toString());
                return null;
            }
            return new File(String.format("%s%s%s", new Object[]{file.getPath(), File.separator, format}));
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("failed to create directory:");
        stringBuilder2.append(file.getAbsolutePath());
        Log.e(str, stringBuilder2.toString());
        return null;
    }

    private File getTempMediaFile(int i) {
        String str = TAG;
        try {
            File createTempFile;
            String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File cacheDir = _reactContext.getCacheDir();
            StringBuilder stringBuilder;
            if (i == 1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("IMG_");
                stringBuilder.append(format);
                createTempFile = File.createTempFile(stringBuilder.toString(), ".jpg", cacheDir);
            } else if (i == 2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("VID_");
                stringBuilder.append(format);
                createTempFile = File.createTempFile(stringBuilder.toString(), ".mp4", cacheDir);
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Unsupported media type:");
                stringBuilder2.append(i);
                Log.e(str, stringBuilder2.toString());
                return null;
            }
            return createTempFile;
        } catch (Exception e) {
            Log.e(str, e.getMessage());
            return null;
        }
    }

    private void addToMediaStore(String str) {
        MediaScannerConnection.scanFile(_reactContext, new String[]{str}, null, null);
    }

    public void onHostResume() {
        this.mSafeToCapture = Boolean.valueOf(true);
    }

    public void onHostPause() {
        if (this.mRecordingPromise != null) {
            releaseMediaRecorder();
        }
    }

    private void resolveImage(File file, int i, int i2, final Promise promise, boolean z) {
        final WritableMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putString(RNFetchBlobConst.RNFB_RESPONSE_PATH, Uri.fromFile(file).toString());
        writableNativeMap.putInt("width", i);
        writableNativeMap.putInt("height", i2);
        if (z) {
            MediaScannerConnection.scanFile(_reactContext, new String[]{file.getAbsolutePath()}, null, new OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                    if (uri != null) {
                        writableNativeMap.putString("mediaUri", uri.toString());
                    }
                    promise.resolve(writableNativeMap);
                }
            });
            return;
        }
        promise.resolve(writableNativeMap);
    }
}
