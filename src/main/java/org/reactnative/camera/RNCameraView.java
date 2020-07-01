package org.reactnative.camera;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.media.CamcorderProfile;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.RNFetchBlob.RNFetchBlobConst;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.google.android.cameraview.CameraView;
import com.google.android.cameraview.CameraView.Callback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.reactnative.barcodedetector.RNBarcodeDetector;
import org.reactnative.camera.tasks.BarCodeScannerAsyncTask;
import org.reactnative.camera.tasks.BarCodeScannerAsyncTaskDelegate;
import org.reactnative.camera.tasks.BarcodeDetectorAsyncTask;
import org.reactnative.camera.tasks.BarcodeDetectorAsyncTaskDelegate;
import org.reactnative.camera.tasks.FaceDetectorAsyncTask;
import org.reactnative.camera.tasks.FaceDetectorAsyncTaskDelegate;
import org.reactnative.camera.tasks.PictureSavedDelegate;
import org.reactnative.camera.tasks.ResolveTakenPictureAsyncTask;
import org.reactnative.camera.tasks.TextRecognizerAsyncTask;
import org.reactnative.camera.tasks.TextRecognizerAsyncTaskDelegate;
import org.reactnative.camera.utils.RNFileUtils;
import org.reactnative.facedetector.RNFaceDetector;

public class RNCameraView extends CameraView implements LifecycleEventListener, BarCodeScannerAsyncTaskDelegate, FaceDetectorAsyncTaskDelegate, BarcodeDetectorAsyncTaskDelegate, TextRecognizerAsyncTaskDelegate, PictureSavedDelegate {
    public volatile boolean barCodeScannerTaskLock;
    public volatile boolean faceDetectorTaskLock;
    public volatile boolean googleBarcodeDetectorTaskLock;
    private boolean invertImageData;
    private List<String> mBarCodeTypes = null;
    private int mCameraViewHeight;
    private int mCameraViewWidth;
    private int mFaceDetectionClassifications;
    private int mFaceDetectionLandmarks;
    private RNFaceDetector mFaceDetector;
    private int mFaceDetectorMode;
    private GestureDetector mGestureDetector;
    private RNBarcodeDetector mGoogleBarcodeDetector;
    private int mGoogleVisionBarCodeMode;
    private int mGoogleVisionBarCodeType;
    private boolean mIsNew;
    private boolean mIsPaused;
    private Boolean mIsRecording;
    private Boolean mIsRecordingInterrupted;
    private boolean mLimitScanArea;
    private MultiFormatReader mMultiFormatReader;
    private int mPaddingX;
    private int mPaddingY;
    private Map<Promise, File> mPictureTakenDirectories = new ConcurrentHashMap();
    private Map<Promise, ReadableMap> mPictureTakenOptions = new ConcurrentHashMap();
    private Queue<Promise> mPictureTakenPromises = new ConcurrentLinkedQueue();
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScanAreaHeight;
    private float mScanAreaWidth;
    private float mScanAreaX;
    private float mScanAreaY;
    private boolean mShouldDetectFaces;
    private boolean mShouldDetectTouches;
    private boolean mShouldGoogleDetectBarcodes;
    private boolean mShouldRecognizeText;
    private boolean mShouldScanBarCodes;
    private ThemedReactContext mThemedReactContext;
    private boolean mTrackingEnabled;
    private boolean mUseNativeZoom;
    private Promise mVideoRecordedPromise;
    private SimpleOnGestureListener onGestureListener;
    private OnScaleGestureListener onScaleGestureListener;
    public volatile boolean textRecognizerTaskLock;

    @SuppressLint({"all"})
    public void requestLayout() {
    }

    public RNCameraView(ThemedReactContext themedReactContext) {
        super(themedReactContext, true);
        Boolean valueOf = Boolean.valueOf(false);
        this.mIsPaused = false;
        this.mIsNew = true;
        this.invertImageData = false;
        this.mIsRecording = valueOf;
        this.mIsRecordingInterrupted = valueOf;
        this.mUseNativeZoom = false;
        this.barCodeScannerTaskLock = false;
        this.faceDetectorTaskLock = false;
        this.googleBarcodeDetectorTaskLock = false;
        this.textRecognizerTaskLock = false;
        this.mShouldDetectFaces = false;
        this.mShouldGoogleDetectBarcodes = false;
        this.mShouldScanBarCodes = false;
        this.mShouldRecognizeText = false;
        this.mShouldDetectTouches = false;
        this.mFaceDetectorMode = RNFaceDetector.FAST_MODE;
        this.mFaceDetectionLandmarks = RNFaceDetector.NO_LANDMARKS;
        this.mFaceDetectionClassifications = RNFaceDetector.NO_CLASSIFICATIONS;
        this.mGoogleVisionBarCodeType = RNBarcodeDetector.ALL_FORMATS;
        this.mGoogleVisionBarCodeMode = RNBarcodeDetector.NORMAL_MODE;
        this.mTrackingEnabled = true;
        this.mLimitScanArea = false;
        this.mScanAreaX = 0.0f;
        this.mScanAreaY = 0.0f;
        this.mScanAreaWidth = 0.0f;
        this.mScanAreaHeight = 0.0f;
        this.mCameraViewWidth = 0;
        this.mCameraViewHeight = 0;
        this.onGestureListener = new SimpleOnGestureListener() {
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                ViewGroup viewGroup = RNCameraView.this;
                RNCameraViewHelper.emitTouchEvent(viewGroup, false, viewGroup.scalePosition(motionEvent.getX()), RNCameraView.this.scalePosition(motionEvent.getY()));
                return true;
            }

            public boolean onDoubleTap(MotionEvent motionEvent) {
                ViewGroup viewGroup = RNCameraView.this;
                RNCameraViewHelper.emitTouchEvent(viewGroup, true, viewGroup.scalePosition(motionEvent.getX()), RNCameraView.this.scalePosition(motionEvent.getY()));
                return true;
            }
        };
        this.onScaleGestureListener = new OnScaleGestureListener() {
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            }

            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                RNCameraView.this.onZoom(scaleGestureDetector.getScaleFactor());
                return true;
            }

            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                RNCameraView.this.onZoom(scaleGestureDetector.getScaleFactor());
                return true;
            }
        };
        this.mThemedReactContext = themedReactContext;
        themedReactContext.addLifecycleEventListener(this);
        addCallback(new Callback() {
            public void onCameraOpened(CameraView cameraView) {
                RNCameraViewHelper.emitCameraReadyEvent(cameraView);
            }

            public void onMountError(CameraView cameraView) {
                RNCameraViewHelper.emitMountErrorEvent(cameraView, "Camera view threw an error - component could not be rendered.");
            }

            public void onPictureTaken(CameraView cameraView, byte[] bArr, int i) {
                Promise promise = (Promise) RNCameraView.this.mPictureTakenPromises.poll();
                ReadableMap readableMap = (ReadableMap) RNCameraView.this.mPictureTakenOptions.remove(promise);
                String str = "fastMode";
                if (readableMap.hasKey(str) && readableMap.getBoolean(str)) {
                    promise.resolve(null);
                }
                File file = (File) RNCameraView.this.mPictureTakenDirectories.remove(promise);
                if (VERSION.SDK_INT >= 11) {
                    new ResolveTakenPictureAsyncTask(bArr, promise, readableMap, file, i, RNCameraView.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                } else {
                    new ResolveTakenPictureAsyncTask(bArr, promise, readableMap, file, i, RNCameraView.this).execute(new Void[0]);
                }
                RNCameraViewHelper.emitPictureTakenEvent(cameraView);
            }

            public void onRecordingStart(CameraView cameraView, String str, int i, int i2) {
                WritableMap createMap = Arguments.createMap();
                createMap.putInt("videoOrientation", i);
                createMap.putInt("deviceOrientation", i2);
                createMap.putString("uri", RNFileUtils.uriFromFile(new File(str)).toString());
                RNCameraViewHelper.emitRecordingStartEvent(cameraView, createMap);
            }

            public void onRecordingEnd(CameraView cameraView) {
                RNCameraViewHelper.emitRecordingEndEvent(cameraView);
            }

            public void onVideoRecorded(CameraView cameraView, String str, int i, int i2) {
                if (RNCameraView.this.mVideoRecordedPromise != null) {
                    if (str != null) {
                        WritableMap createMap = Arguments.createMap();
                        createMap.putBoolean("isRecordingInterrupted", RNCameraView.this.mIsRecordingInterrupted.booleanValue());
                        createMap.putInt("videoOrientation", i);
                        createMap.putInt("deviceOrientation", i2);
                        createMap.putString("uri", RNFileUtils.uriFromFile(new File(str)).toString());
                        RNCameraView.this.mVideoRecordedPromise.resolve(createMap);
                    } else {
                        RNCameraView.this.mVideoRecordedPromise.reject("E_RECORDING", "Couldn't stop recording - there is none in progress");
                    }
                    RNCameraView.this.mIsRecording = Boolean.valueOf(false);
                    RNCameraView.this.mIsRecordingInterrupted = Boolean.valueOf(false);
                    RNCameraView.this.mVideoRecordedPromise = null;
                }
            }

            public void onFramePreview(CameraView cameraView, byte[] bArr, int i, int i2, int i3) {
                AnonymousClass1 thisR;
                CameraView cameraView2 = cameraView;
                byte[] bArr2 = bArr;
                int correctCameraRotation = RNCameraViewHelper.getCorrectCameraRotation(i3, RNCameraView.this.getFacing(), RNCameraView.this.getCameraOrientation());
                Object obj = (RNCameraView.this.mShouldScanBarCodes && !RNCameraView.this.barCodeScannerTaskLock && (cameraView2 instanceof BarCodeScannerAsyncTaskDelegate)) ? 1 : null;
                Object obj2 = (RNCameraView.this.mShouldDetectFaces && !RNCameraView.this.faceDetectorTaskLock && (cameraView2 instanceof FaceDetectorAsyncTaskDelegate)) ? 1 : null;
                Object obj3 = (RNCameraView.this.mShouldGoogleDetectBarcodes && !RNCameraView.this.googleBarcodeDetectorTaskLock && (cameraView2 instanceof BarcodeDetectorAsyncTaskDelegate)) ? 1 : null;
                Object obj4 = (RNCameraView.this.mShouldRecognizeText && !RNCameraView.this.textRecognizerTaskLock && (cameraView2 instanceof TextRecognizerAsyncTaskDelegate)) ? 1 : null;
                if (!(obj == null && obj2 == null && obj3 == null && obj4 == null) && ((double) bArr2.length) >= (((double) i) * 1.5d) * ((double) i2)) {
                    RNCameraView rNCameraView;
                    boolean z;
                    RNCameraView rNCameraView2;
                    boolean z2;
                    if (obj != null) {
                        rNCameraView = RNCameraView.this;
                        rNCameraView.barCodeScannerTaskLock = true;
                        BarCodeScannerAsyncTask barCodeScannerAsyncTask = r2;
                        BarCodeScannerAsyncTask barCodeScannerAsyncTask2 = new BarCodeScannerAsyncTask((BarCodeScannerAsyncTaskDelegate) cameraView2, rNCameraView.mMultiFormatReader, bArr, i, i2, RNCameraView.this.mLimitScanArea, RNCameraView.this.mScanAreaX, RNCameraView.this.mScanAreaY, RNCameraView.this.mScanAreaWidth, RNCameraView.this.mScanAreaHeight, RNCameraView.this.mCameraViewWidth, RNCameraView.this.mCameraViewHeight, RNCameraView.this.getAspectRatio().toFloat());
                        barCodeScannerAsyncTask.execute(new Void[0]);
                    }
                    if (obj2 != null) {
                        z = false;
                        thisR = this;
                        rNCameraView2 = RNCameraView.this;
                        rNCameraView2.faceDetectorTaskLock = true;
                        FaceDetectorAsyncTask faceDetectorAsyncTask = r2;
                        z2 = true;
                        FaceDetectorAsyncTask faceDetectorAsyncTask2 = new FaceDetectorAsyncTask((FaceDetectorAsyncTaskDelegate) cameraView, rNCameraView2.mFaceDetector, bArr, i, i2, correctCameraRotation, RNCameraView.this.getResources().getDisplayMetrics().density, RNCameraView.this.getFacing(), RNCameraView.this.getWidth(), RNCameraView.this.getHeight(), RNCameraView.this.mPaddingX, RNCameraView.this.mPaddingY);
                        faceDetectorAsyncTask.execute(new Void[0]);
                    } else {
                        z2 = true;
                        z = false;
                        thisR = this;
                    }
                    if (obj3 != null) {
                        rNCameraView = RNCameraView.this;
                        rNCameraView.googleBarcodeDetectorTaskLock = z2;
                        if (rNCameraView.mGoogleVisionBarCodeMode == RNBarcodeDetector.NORMAL_MODE) {
                            RNCameraView.this.invertImageData = z;
                        } else if (RNCameraView.this.mGoogleVisionBarCodeMode == RNBarcodeDetector.ALTERNATE_MODE) {
                            rNCameraView = RNCameraView.this;
                            rNCameraView.invertImageData = rNCameraView.invertImageData ^ z2;
                        } else if (RNCameraView.this.mGoogleVisionBarCodeMode == RNBarcodeDetector.INVERTED_MODE) {
                            RNCameraView.this.invertImageData = z2;
                        }
                        byte[] bArr3;
                        if (RNCameraView.this.invertImageData) {
                            bArr3 = bArr;
                            for (int i4 = 0; i4 < bArr3.length; i4++) {
                                bArr3[i4] = (byte) (~bArr3[i4]);
                            }
                        } else {
                            bArr3 = bArr;
                        }
                        BarcodeDetectorAsyncTask barcodeDetectorAsyncTask = r2;
                        BarcodeDetectorAsyncTask barcodeDetectorAsyncTask2 = new BarcodeDetectorAsyncTask((BarcodeDetectorAsyncTaskDelegate) cameraView, RNCameraView.this.mGoogleBarcodeDetector, bArr, i, i2, correctCameraRotation, RNCameraView.this.getResources().getDisplayMetrics().density, RNCameraView.this.getFacing(), RNCameraView.this.getWidth(), RNCameraView.this.getHeight(), RNCameraView.this.mPaddingX, RNCameraView.this.mPaddingY);
                        barcodeDetectorAsyncTask.execute(new Void[z]);
                    }
                    if (obj4 != null) {
                        rNCameraView2 = RNCameraView.this;
                        rNCameraView2.textRecognizerTaskLock = true;
                        new TextRecognizerAsyncTask((TextRecognizerAsyncTaskDelegate) cameraView, rNCameraView2.mThemedReactContext, bArr, i, i2, correctCameraRotation, RNCameraView.this.getResources().getDisplayMetrics().density, RNCameraView.this.getFacing(), RNCameraView.this.getWidth(), RNCameraView.this.getHeight(), RNCameraView.this.mPaddingX, RNCameraView.this.mPaddingY).execute(new Void[z]);
                    }
                }
            }
        });
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View view = getView();
        if (view != null) {
            float f = (float) (i3 - i);
            float f2 = (float) (i4 - i2);
            float toFloat = getAspectRatio().toFloat();
            i4 = getResources().getConfiguration().orientation;
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
            float f3;
            if (i4 == 2) {
                f3 = toFloat * f2;
                if (f3 < f) {
                    i3 = (int) (f / toFloat);
                } else {
                    i4 = (int) f3;
                    i3 = (int) f2;
                    i = (int) ((f - ((float) i4)) / 2.0f);
                    i2 = (int) ((f2 - ((float) i3)) / 2.0f);
                    this.mPaddingX = i;
                    this.mPaddingY = i2;
                    view.layout(i, i2, i4 + i, i3 + i2);
                }
            }
            f3 = toFloat * f;
            if (f3 > f2) {
                i3 = (int) f3;
            } else {
                i4 = (int) (f2 / toFloat);
                i3 = (int) f2;
                i = (int) ((f - ((float) i4)) / 2.0f);
                i2 = (int) ((f2 - ((float) i3)) / 2.0f);
                this.mPaddingX = i;
                this.mPaddingY = i2;
                view.layout(i, i2, i4 + i, i3 + i2);
            }
            i4 = (int) f;
            i = (int) ((f - ((float) i4)) / 2.0f);
            i2 = (int) ((f2 - ((float) i3)) / 2.0f);
            this.mPaddingX = i;
            this.mPaddingY = i2;
            view.layout(i, i2, i4 + i, i3 + i2);
        }
    }

    public void setBarCodeTypes(List<String> list) {
        this.mBarCodeTypes = list;
        initBarcodeReader();
    }

    public void takePicture(final ReadableMap readableMap, final Promise promise, final File file) {
        this.mBgHandler.post(new Runnable() {
            public void run() {
                RNCameraView.this.mPictureTakenPromises.add(promise);
                RNCameraView.this.mPictureTakenOptions.put(promise, readableMap);
                RNCameraView.this.mPictureTakenDirectories.put(promise, file);
                try {
                    super.access$2501(readableMap);
                } catch (Exception e) {
                    RNCameraView.this.mPictureTakenPromises.remove(promise);
                    RNCameraView.this.mPictureTakenOptions.remove(promise);
                    RNCameraView.this.mPictureTakenDirectories.remove(promise);
                    promise.reject("E_TAKE_PICTURE_FAILED", e.getMessage());
                }
            }
        });
    }

    public void onPictureSaved(WritableMap writableMap) {
        RNCameraViewHelper.emitPictureSavedEvent(this, writableMap);
    }

    public void record(final ReadableMap readableMap, final Promise promise, final File file) {
        this.mBgHandler.post(new Runnable() {
            public void run() {
                String str = ReactVideoView.EVENT_PROP_ORIENTATION;
                String str2 = "mute";
                String str3 = "videoBitrate";
                String str4 = "quality";
                String str5 = "fps";
                String str6 = "maxFileSize";
                String str7 = "maxDuration";
                String str8 = "E_RECORDING_FAILED";
                String str9 = RNFetchBlobConst.RNFB_RESPONSE_PATH;
                try {
                    String string = readableMap.hasKey(str9) ? readableMap.getString(str9) : RNFileUtils.getOutputFilePath(file, ".mp4");
                    int i = readableMap.hasKey(str7) ? readableMap.getInt(str7) : -1;
                    int i2 = readableMap.hasKey(str6) ? readableMap.getInt(str6) : -1;
                    int i3 = readableMap.hasKey(str5) ? readableMap.getInt(str5) : -1;
                    CamcorderProfile camcorderProfile = readableMap.hasKey(str4) ? RNCameraViewHelper.getCamcorderProfile(readableMap.getInt(str4)) : CamcorderProfile.get(1);
                    if (readableMap.hasKey(str3)) {
                        camcorderProfile.videoBitRate = readableMap.getInt(str3);
                    }
                    if (super.access$2601(string, i * 1000, i2, readableMap.hasKey(str2) ? readableMap.getBoolean(str2) ^ 1 : true, camcorderProfile, readableMap.hasKey(str) ? readableMap.getInt(str) : 0, i3)) {
                        RNCameraView.this.mIsRecording = Boolean.valueOf(true);
                        RNCameraView.this.mVideoRecordedPromise = promise;
                        return;
                    }
                    promise.reject(str8, "Starting video recording failed. Another recording might be in progress.");
                } catch (IOException unused) {
                    promise.reject(str8, "Starting video recording failed - could not create video file.");
                }
            }
        });
    }

    private void initBarcodeReader() {
        this.mMultiFormatReader = new MultiFormatReader();
        Map enumMap = new EnumMap(DecodeHintType.class);
        EnumSet noneOf = EnumSet.noneOf(BarcodeFormat.class);
        List<String> list = this.mBarCodeTypes;
        if (list != null) {
            for (String str : list) {
                String str2 = (String) CameraModule.VALID_BARCODE_TYPES.get(str2);
                if (str2 != null) {
                    noneOf.add(BarcodeFormat.valueOf(str2));
                }
            }
        }
        enumMap.put(DecodeHintType.POSSIBLE_FORMATS, noneOf);
        this.mMultiFormatReader.setHints(enumMap);
    }

    public void setShouldScanBarCodes(boolean z) {
        if (z && this.mMultiFormatReader == null) {
            initBarcodeReader();
        }
        this.mShouldScanBarCodes = z;
        z = this.mShouldDetectFaces || this.mShouldGoogleDetectBarcodes || this.mShouldScanBarCodes || this.mShouldRecognizeText;
        setScanning(z);
    }

    public void onBarCodeRead(Result result, int i, int i2) {
        String barcodeFormat = result.getBarcodeFormat().toString();
        if (this.mShouldScanBarCodes && this.mBarCodeTypes.contains(barcodeFormat)) {
            RNCameraViewHelper.emitBarCodeReadEvent(this, result, i, i2);
        }
    }

    public void onBarCodeScanningTaskCompleted() {
        this.barCodeScannerTaskLock = false;
        MultiFormatReader multiFormatReader = this.mMultiFormatReader;
        if (multiFormatReader != null) {
            multiFormatReader.reset();
        }
    }

    public void setRectOfInterest(float f, float f2, float f3, float f4) {
        this.mLimitScanArea = true;
        this.mScanAreaX = f;
        this.mScanAreaY = f2;
        this.mScanAreaWidth = f3;
        this.mScanAreaHeight = f4;
    }

    public void setCameraViewDimensions(int i, int i2) {
        this.mCameraViewWidth = i;
        this.mCameraViewHeight = i2;
    }

    public void setShouldDetectTouches(boolean z) {
        if (this.mShouldDetectTouches || !z) {
            this.mGestureDetector = null;
        } else {
            this.mGestureDetector = new GestureDetector(this.mThemedReactContext, this.onGestureListener);
        }
        this.mShouldDetectTouches = z;
    }

    public void setUseNativeZoom(boolean z) {
        if (this.mUseNativeZoom || !z) {
            this.mScaleGestureDetector = null;
        } else {
            this.mScaleGestureDetector = new ScaleGestureDetector(this.mThemedReactContext, this.onScaleGestureListener);
        }
        this.mUseNativeZoom = z;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mUseNativeZoom) {
            this.mScaleGestureDetector.onTouchEvent(motionEvent);
        }
        if (this.mShouldDetectTouches) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        return true;
    }

    private void setupFaceDetector() {
        this.mFaceDetector = new RNFaceDetector(this.mThemedReactContext);
        this.mFaceDetector.setMode(this.mFaceDetectorMode);
        this.mFaceDetector.setLandmarkType(this.mFaceDetectionLandmarks);
        this.mFaceDetector.setClassificationType(this.mFaceDetectionClassifications);
        this.mFaceDetector.setTracking(this.mTrackingEnabled);
    }

    public void setFaceDetectionLandmarks(int i) {
        this.mFaceDetectionLandmarks = i;
        RNFaceDetector rNFaceDetector = this.mFaceDetector;
        if (rNFaceDetector != null) {
            rNFaceDetector.setLandmarkType(i);
        }
    }

    public void setFaceDetectionClassifications(int i) {
        this.mFaceDetectionClassifications = i;
        RNFaceDetector rNFaceDetector = this.mFaceDetector;
        if (rNFaceDetector != null) {
            rNFaceDetector.setClassificationType(i);
        }
    }

    public void setFaceDetectionMode(int i) {
        this.mFaceDetectorMode = i;
        RNFaceDetector rNFaceDetector = this.mFaceDetector;
        if (rNFaceDetector != null) {
            rNFaceDetector.setMode(i);
        }
    }

    public void setTracking(boolean z) {
        this.mTrackingEnabled = z;
        RNFaceDetector rNFaceDetector = this.mFaceDetector;
        if (rNFaceDetector != null) {
            rNFaceDetector.setTracking(z);
        }
    }

    public void setShouldDetectFaces(boolean z) {
        if (z && this.mFaceDetector == null) {
            setupFaceDetector();
        }
        this.mShouldDetectFaces = z;
        z = this.mShouldDetectFaces || this.mShouldGoogleDetectBarcodes || this.mShouldScanBarCodes || this.mShouldRecognizeText;
        setScanning(z);
    }

    public void onFacesDetected(WritableArray writableArray) {
        if (this.mShouldDetectFaces) {
            RNCameraViewHelper.emitFacesDetectedEvent(this, writableArray);
        }
    }

    public void onFaceDetectionError(RNFaceDetector rNFaceDetector) {
        if (this.mShouldDetectFaces) {
            RNCameraViewHelper.emitFaceDetectionErrorEvent(this, rNFaceDetector);
        }
    }

    public void onFaceDetectingTaskCompleted() {
        this.faceDetectorTaskLock = false;
    }

    private void setupBarcodeDetector() {
        this.mGoogleBarcodeDetector = new RNBarcodeDetector(this.mThemedReactContext);
        this.mGoogleBarcodeDetector.setBarcodeType(this.mGoogleVisionBarCodeType);
    }

    public void setShouldGoogleDetectBarcodes(boolean z) {
        if (z && this.mGoogleBarcodeDetector == null) {
            setupBarcodeDetector();
        }
        this.mShouldGoogleDetectBarcodes = z;
        z = this.mShouldDetectFaces || this.mShouldGoogleDetectBarcodes || this.mShouldScanBarCodes || this.mShouldRecognizeText;
        setScanning(z);
    }

    public void setGoogleVisionBarcodeType(int i) {
        this.mGoogleVisionBarCodeType = i;
        RNBarcodeDetector rNBarcodeDetector = this.mGoogleBarcodeDetector;
        if (rNBarcodeDetector != null) {
            rNBarcodeDetector.setBarcodeType(i);
        }
    }

    public void setGoogleVisionBarcodeMode(int i) {
        this.mGoogleVisionBarCodeMode = i;
    }

    public void onBarcodesDetected(WritableArray writableArray) {
        if (this.mShouldGoogleDetectBarcodes) {
            RNCameraViewHelper.emitBarcodesDetectedEvent(this, writableArray);
        }
    }

    public void onBarcodeDetectionError(RNBarcodeDetector rNBarcodeDetector) {
        if (this.mShouldGoogleDetectBarcodes) {
            RNCameraViewHelper.emitBarcodeDetectionErrorEvent(this, rNBarcodeDetector);
        }
    }

    public void onBarcodeDetectingTaskCompleted() {
        this.googleBarcodeDetectorTaskLock = false;
    }

    public void setShouldRecognizeText(boolean z) {
        this.mShouldRecognizeText = z;
        z = this.mShouldDetectFaces || this.mShouldGoogleDetectBarcodes || this.mShouldScanBarCodes || this.mShouldRecognizeText;
        setScanning(z);
    }

    public void onTextRecognized(WritableArray writableArray) {
        if (this.mShouldRecognizeText) {
            RNCameraViewHelper.emitTextRecognizedEvent(this, writableArray);
        }
    }

    public void onTextRecognizerTaskCompleted() {
        this.textRecognizerTaskLock = false;
    }

    public void onHostResume() {
        if (hasCameraPermissions()) {
            this.mBgHandler.post(new Runnable() {
                public void run() {
                    if ((RNCameraView.this.mIsPaused && !RNCameraView.this.isCameraOpened()) || RNCameraView.this.mIsNew) {
                        RNCameraView.this.mIsPaused = false;
                        RNCameraView.this.mIsNew = false;
                        RNCameraView.this.start();
                    }
                }
            });
        } else {
            RNCameraViewHelper.emitMountErrorEvent(this, "Camera permissions not granted - component could not be rendered.");
        }
    }

    public void onHostPause() {
        if (this.mIsRecording.booleanValue()) {
            this.mIsRecordingInterrupted = Boolean.valueOf(true);
        }
        if (!this.mIsPaused && isCameraOpened()) {
            this.mIsPaused = true;
            stop();
        }
    }

    public void onHostDestroy() {
        RNFaceDetector rNFaceDetector = this.mFaceDetector;
        if (rNFaceDetector != null) {
            rNFaceDetector.release();
        }
        RNBarcodeDetector rNBarcodeDetector = this.mGoogleBarcodeDetector;
        if (rNBarcodeDetector != null) {
            rNBarcodeDetector.release();
        }
        this.mMultiFormatReader = null;
        this.mThemedReactContext.removeLifecycleEventListener(this);
        this.mBgHandler.post(new Runnable() {
            public void run() {
                RNCameraView.this.stop();
                RNCameraView.this.cleanup();
            }
        });
    }

    private void onZoom(float f) {
        float zoom = getZoom();
        f = (f - 1.0f) + zoom;
        if (f > zoom) {
            setZoom(Math.min(f, 1.0f));
        } else {
            setZoom(Math.max(f, 0.0f));
        }
    }

    private boolean hasCameraPermissions() {
        if (VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(getContext(), "android.permission.CAMERA") == 0) {
            return true;
        }
        return false;
    }

    private int scalePosition(float f) {
        Resources resources = getResources();
        resources.getConfiguration();
        return (int) (f / resources.getDisplayMetrics().density);
    }
}
