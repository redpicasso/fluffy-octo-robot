package com.lwansbrough.RCTCamera;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

class RCTCameraViewFinder extends TextureView implements SurfaceTextureListener, PreviewCallback {
    public static volatile boolean barcodeScannerTaskLock = false;
    private Camera _camera;
    private int _cameraType;
    private int _captureMode;
    private boolean _clearWindowBackground = false;
    private boolean _isStarting;
    private boolean _isStopping;
    private final MultiFormatReader _multiFormatReader = new MultiFormatReader();
    private SurfaceTexture _surfaceTexture;
    private int _surfaceTextureHeight;
    private int _surfaceTextureWidth;
    private float mFingerSpacing;

    private class ReaderAsyncTask extends AsyncTask<Void, Void, Void> {
        private final Camera camera;
        private byte[] imageData;

        ReaderAsyncTask(Camera camera, byte[] bArr) {
            this.camera = camera;
            this.imageData = bArr;
        }

        /*  JADX ERROR: NullPointerException in pass: ProcessVariables
            java.lang.NullPointerException
            	at jadx.core.dex.visitors.regions.ProcessVariables.addToUsageMap(ProcessVariables.java:271)
            	at jadx.core.dex.visitors.regions.ProcessVariables.access$000(ProcessVariables.java:31)
            	at jadx.core.dex.visitors.regions.ProcessVariables$CollectUsageRegionVisitor.processInsn(ProcessVariables.java:152)
            	at jadx.core.dex.visitors.regions.ProcessVariables$CollectUsageRegionVisitor.processBlockTraced(ProcessVariables.java:129)
            	at jadx.core.dex.visitors.regions.TracedRegionVisitor.processBlock(TracedRegionVisitor.java:23)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:53)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
            	at java.util.ArrayList.forEach(Unknown Source)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
            	at java.util.ArrayList.forEach(Unknown Source)
            	at java.util.Collections$UnmodifiableCollection.forEach(Unknown Source)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
            	at java.util.ArrayList.forEach(Unknown Source)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
            	at jadx.core.dex.visitors.regions.ProcessVariables.visit(ProcessVariables.java:183)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
            	at java.util.ArrayList.forEach(Unknown Source)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
            	at java.util.ArrayList.forEach(Unknown Source)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
            	at jadx.core.ProcessClass.process(ProcessClass.java:32)
            	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
            	at java.lang.Iterable.forEach(Unknown Source)
            	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
            	at jadx.core.ProcessClass.process(ProcessClass.java:37)
            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
            */
        private com.google.zxing.Result getBarcode(int r11, int r12, boolean r13) {
            /*
            r10 = this;
            r9 = new com.google.zxing.PlanarYUVLuminanceSource;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r1 = r10.imageData;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r4 = 0;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r5 = 0;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r8 = 0;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r0 = r9;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r2 = r11;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r3 = r12;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r6 = r11;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r7 = r12;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            if (r13 == 0) goto L_0x0020;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
        L_0x0011:
            r11 = new com.google.zxing.BinaryBitmap;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r12 = new com.google.zxing.common.HybridBinarizer;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r13 = r9.invert();	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r12.<init>(r13);	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r11.<init>(r12);	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            goto L_0x002a;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
        L_0x0020:
            r11 = new com.google.zxing.BinaryBitmap;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r12 = new com.google.zxing.common.HybridBinarizer;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r12.<init>(r9);	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r11.<init>(r12);	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
        L_0x002a:
            r12 = com.lwansbrough.RCTCamera.RCTCameraViewFinder.this;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r12 = r12._multiFormatReader;	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r11 = r12.decodeWithState(r11);	 Catch:{ Throwable -> 0x0049, all -> 0x003e }
            r12 = com.lwansbrough.RCTCamera.RCTCameraViewFinder.this;
            r12 = r12._multiFormatReader;
            r12.reset();
            return r11;
        L_0x003e:
            r11 = move-exception;
            r12 = com.lwansbrough.RCTCamera.RCTCameraViewFinder.this;
            r12 = r12._multiFormatReader;
            r12.reset();
            throw r11;
        L_0x0049:
            r11 = com.lwansbrough.RCTCamera.RCTCameraViewFinder.this;
            r11 = r11._multiFormatReader;
            r11.reset();
            r11 = 0;
            return r11;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lwansbrough.RCTCamera.RCTCameraViewFinder.ReaderAsyncTask.getBarcode(int, int, boolean):com.google.zxing.Result");
        }

        private Result getBarcodeAnyOrientation() {
            Size previewSize = this.camera.getParameters().getPreviewSize();
            int i = previewSize.width;
            int i2 = previewSize.height;
            Result barcode = getBarcode(i, i2, false);
            if (barcode != null) {
                return barcode;
            }
            Result barcode2 = getBarcode(i, i2, true);
            if (barcode2 != null) {
                return barcode2;
            }
            rotateImage(i, i2);
            i = previewSize.height;
            int i3 = previewSize.width;
            Result barcode3 = getBarcode(i, i3, false);
            if (barcode3 != null) {
                return barcode3;
            }
            return getBarcode(i, i3, true);
        }

        private void rotateImage(int i, int i2) {
            byte[] bArr = new byte[this.imageData.length];
            for (int i3 = 0; i3 < i; i3++) {
                for (int i4 = 0; i4 < i2; i4++) {
                    int i5 = (i3 * i2) + i4;
                    int i6 = (((i4 * i) + i) - i3) - 1;
                    if (i5 >= 0) {
                        byte[] bArr2 = this.imageData;
                        if (i5 < bArr2.length && i6 >= 0 && i6 < bArr2.length) {
                            bArr[i6] = bArr2[i5];
                        }
                    }
                }
            }
            this.imageData = bArr;
        }

        /* JADX WARNING: Removed duplicated region for block: B:13:0x0073 A:{ExcHandler: all (unused java.lang.Throwable), Splitter: B:4:0x0009} */
        /* JADX WARNING: Missing block: B:13:0x0073, code:
            com.lwansbrough.RCTCamera.RCTCameraViewFinder.access$100(r12.this$0).reset();
            com.lwansbrough.RCTCamera.RCTCameraViewFinder.barcodeScannerTaskLock = false;
     */
        /* JADX WARNING: Missing block: B:14:0x007e, code:
            return null;
     */
        protected java.lang.Void doInBackground(java.lang.Void... r13) {
            /*
            r12 = this;
            r13 = r12.isCancelled();
            r0 = 0;
            if (r13 == 0) goto L_0x0008;
        L_0x0007:
            return r0;
        L_0x0008:
            r13 = 0;
            r1 = r12.getBarcodeAnyOrientation();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            if (r1 == 0) goto L_0x007f;
        L_0x000f:
            r2 = com.lwansbrough.RCTCamera.RCTCameraModule.getReactContextSingleton();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r3 = com.facebook.react.bridge.Arguments.createMap();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r4 = com.facebook.react.bridge.Arguments.createArray();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r5 = r1.getResultPoints();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            if (r5 == 0) goto L_0x004b;
        L_0x0021:
            r6 = r5.length;	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r7 = 0;
        L_0x0023:
            if (r7 >= r6) goto L_0x004b;
        L_0x0025:
            r8 = r5[r7];	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r9 = com.facebook.react.bridge.Arguments.createMap();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r10 = "x";
            r11 = r8.getX();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r11 = java.lang.String.valueOf(r11);	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r9.putString(r10, r11);	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r10 = "y";
            r8 = r8.getY();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r8 = java.lang.String.valueOf(r8);	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r9.putString(r10, r8);	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r4.pushMap(r9);	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r7 = r7 + 1;
            goto L_0x0023;
        L_0x004b:
            r5 = "bounds";
            r3.putArray(r5, r4);	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r4 = "data";
            r5 = r1.getText();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r3.putString(r4, r5);	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r4 = "type";
            r1 = r1.getBarcodeFormat();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r1 = r1.toString();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r3.putString(r4, r1);	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r1 = com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter.class;
            r1 = r2.getJSModule(r1);	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r1 = (com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter) r1;	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r2 = "CameraBarCodeReadAndroid";
            r1.emit(r2, r3);	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
        L_0x0073:
            r1 = com.lwansbrough.RCTCamera.RCTCameraViewFinder.this;
            r1 = r1._multiFormatReader;
            r1.reset();
            com.lwansbrough.RCTCamera.RCTCameraViewFinder.barcodeScannerTaskLock = r13;
            return r0;
        L_0x007f:
            r1 = new java.lang.Exception;	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            r1.<init>();	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            throw r1;	 Catch:{ Throwable -> 0x0073, Throwable -> 0x0073 }
            */
            throw new UnsupportedOperationException("Method not decompiled: com.lwansbrough.RCTCamera.RCTCameraViewFinder.ReaderAsyncTask.doInBackground(java.lang.Void[]):java.lang.Void");
        }
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public RCTCameraViewFinder(Context context, int i) {
        super(context);
        setSurfaceTextureListener(this);
        this._cameraType = i;
        initBarcodeReader(RCTCamera.getInstance().getBarCodeTypes());
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this._surfaceTexture = surfaceTexture;
        this._surfaceTextureWidth = i;
        this._surfaceTextureHeight = i2;
        startCamera();
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        this._surfaceTextureWidth = i;
        this._surfaceTextureHeight = i2;
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this._surfaceTexture = null;
        this._surfaceTextureWidth = 0;
        this._surfaceTextureHeight = 0;
        stopCamera();
        return true;
    }

    public int getCameraType() {
        return this._cameraType;
    }

    public double getRatio() {
        return (double) (((float) RCTCamera.getInstance().getPreviewWidth(this._cameraType)) / ((float) RCTCamera.getInstance().getPreviewHeight(this._cameraType)));
    }

    public void setCameraType(final int i) {
        if (this._cameraType != i) {
            new Thread(new Runnable() {
                public void run() {
                    RCTCameraViewFinder.this.stopPreview();
                    RCTCameraViewFinder.this._cameraType = i;
                    RCTCameraViewFinder.this.startPreview();
                }
            }).start();
        }
    }

    public void setCaptureMode(int i) {
        RCTCamera.getInstance().setCaptureMode(this._cameraType, i);
        this._captureMode = i;
    }

    public void setCaptureQuality(String str) {
        RCTCamera.getInstance().setCaptureQuality(this._cameraType, str);
    }

    public void setTorchMode(int i) {
        RCTCamera.getInstance().setTorchMode(this._cameraType, i);
    }

    public void setFlashMode(int i) {
        RCTCamera.getInstance().setFlashMode(this._cameraType, i);
    }

    public void setClearWindowBackground(boolean z) {
        this._clearWindowBackground = z;
    }

    public void setZoom(int i) {
        RCTCamera.getInstance().setZoom(this._cameraType, i);
    }

    public void startPreview() {
        if (this._surfaceTexture != null) {
            startCamera();
        }
    }

    public void stopPreview() {
        if (this._camera != null) {
            stopCamera();
        }
    }

    private synchronized void startCamera() {
        if (!this._isStarting) {
            boolean z = true;
            this._isStarting = true;
            try {
                this._camera = RCTCamera.getInstance().acquireCameraInstance(this._cameraType);
                Parameters parameters = this._camera.getParameters();
                Object obj = this._captureMode == 0 ? 1 : null;
                if (this._captureMode != 1) {
                    z = false;
                }
                StringBuilder stringBuilder;
                if (obj != null || z) {
                    List supportedPictureSizes;
                    List supportedFocusModes = parameters.getSupportedFocusModes();
                    if (obj != null && supportedFocusModes.contains("continuous-picture")) {
                        parameters.setFocusMode("continuous-picture");
                    } else if (z && supportedFocusModes.contains("continuous-video")) {
                        parameters.setFocusMode("continuous-video");
                    } else if (supportedFocusModes.contains("auto")) {
                        parameters.setFocusMode("auto");
                    }
                    if (obj != null) {
                        supportedPictureSizes = parameters.getSupportedPictureSizes();
                    } else if (z) {
                        supportedPictureSizes = RCTCamera.getInstance().getSupportedVideoSizes(this._camera);
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported capture mode:");
                        stringBuilder.append(this._captureMode);
                        throw new RuntimeException(stringBuilder.toString());
                    }
                    Size bestSize = RCTCamera.getInstance().getBestSize(supportedPictureSizes, Integer.MAX_VALUE, Integer.MAX_VALUE);
                    parameters.setPictureSize(bestSize.width, bestSize.height);
                    try {
                        this._camera.setParameters(parameters);
                    } catch (Throwable e) {
                        Log.e("RCTCameraViewFinder", "setParameters failed", e);
                    }
                    this._camera.setPreviewTexture(this._surfaceTexture);
                    this._camera.startPreview();
                    if (this._clearWindowBackground) {
                        Activity activity = getActivity();
                        if (activity != null) {
                            activity.getWindow().setBackgroundDrawable(null);
                        }
                    }
                    this._camera.setPreviewCallback(this);
                    this._isStarting = false;
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported capture mode:");
                    stringBuilder.append(this._captureMode);
                    throw new RuntimeException(stringBuilder.toString());
                }
            } catch (NullPointerException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                try {
                    e3.printStackTrace();
                    stopCamera();
                } catch (Throwable th) {
                    this._isStarting = false;
                }
            }
        }
    }

    private synchronized void stopCamera() {
        if (!this._isStopping) {
            this._isStopping = true;
            try {
                if (this._camera != null) {
                    this._camera.stopPreview();
                    this._camera.setPreviewCallback(null);
                    RCTCamera.getInstance().releaseCameraInstance(this._cameraType);
                    this._camera = null;
                }
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                } catch (Throwable th) {
                    this._isStopping = false;
                }
            }
            this._isStopping = false;
        }
    }

    private Activity getActivity() {
        for (Context context = getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
        }
        return null;
    }

    private BarcodeFormat parseBarCodeString(String str) {
        if ("aztec".equals(str)) {
            return BarcodeFormat.AZTEC;
        }
        if ("ean13".equals(str)) {
            return BarcodeFormat.EAN_13;
        }
        if ("ean8".equals(str)) {
            return BarcodeFormat.EAN_8;
        }
        if ("qr".equals(str)) {
            return BarcodeFormat.QR_CODE;
        }
        if ("pdf417".equals(str)) {
            return BarcodeFormat.PDF_417;
        }
        if ("upce".equals(str)) {
            return BarcodeFormat.UPC_E;
        }
        if ("datamatrix".equals(str)) {
            return BarcodeFormat.DATA_MATRIX;
        }
        if ("code39".equals(str)) {
            return BarcodeFormat.CODE_39;
        }
        if ("code93".equals(str)) {
            return BarcodeFormat.CODE_93;
        }
        if ("interleaved2of5".equals(str)) {
            return BarcodeFormat.ITF;
        }
        if ("codabar".equals(str)) {
            return BarcodeFormat.CODABAR;
        }
        if ("code128".equals(str)) {
            return BarcodeFormat.CODE_128;
        }
        if ("maxicode".equals(str)) {
            return BarcodeFormat.MAXICODE;
        }
        if ("rss14".equals(str)) {
            return BarcodeFormat.RSS_14;
        }
        if ("rssexpanded".equals(str)) {
            return BarcodeFormat.RSS_EXPANDED;
        }
        if ("upca".equals(str)) {
            return BarcodeFormat.UPC_A;
        }
        if ("upceanextension".equals(str)) {
            return BarcodeFormat.UPC_EAN_EXTENSION;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported code.. [");
        stringBuilder.append(str);
        stringBuilder.append("]");
        Log.v("RCTCamera", stringBuilder.toString());
        return null;
    }

    private void initBarcodeReader(List<String> list) {
        Map enumMap = new EnumMap(DecodeHintType.class);
        EnumSet noneOf = EnumSet.noneOf(BarcodeFormat.class);
        if (list != null) {
            for (String parseBarCodeString : list) {
                BarcodeFormat parseBarCodeString2 = parseBarCodeString(parseBarCodeString);
                if (parseBarCodeString2 != null) {
                    noneOf.add(parseBarCodeString2);
                }
            }
        }
        enumMap.put(DecodeHintType.POSSIBLE_FORMATS, noneOf);
        this._multiFormatReader.setHints(enumMap);
    }

    public void onPreviewFrame(byte[] bArr, Camera camera) {
        if (RCTCamera.getInstance().isBarcodeScannerEnabled() && !barcodeScannerTaskLock) {
            barcodeScannerTaskLock = true;
            new ReaderAsyncTask(camera, bArr).execute(new Void[0]);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Camera camera = this._camera;
        if (camera == null) {
            return false;
        }
        Parameters parameters = camera.getParameters();
        int action = motionEvent.getAction();
        if (motionEvent.getPointerCount() > 1) {
            if (action == 5) {
                this.mFingerSpacing = getFingerSpacing(motionEvent);
            } else if (action == 2 && parameters.isZoomSupported()) {
                this._camera.cancelAutoFocus();
                handleZoom(motionEvent, parameters);
            }
        } else if (action == 1) {
            handleFocus(motionEvent, parameters);
        }
        return true;
    }

    private void handleZoom(MotionEvent motionEvent, Parameters parameters) {
        int maxZoom = parameters.getMaxZoom();
        int zoom = parameters.getZoom();
        float fingerSpacing = getFingerSpacing(motionEvent);
        float f = this.mFingerSpacing;
        if (fingerSpacing > f) {
            if (zoom < maxZoom) {
                zoom++;
            }
        } else if (fingerSpacing < f && zoom > 0) {
            zoom--;
        }
        this.mFingerSpacing = fingerSpacing;
        parameters.setZoom(zoom);
        try {
            this._camera.setParameters(parameters);
        } catch (Throwable e) {
            Log.e("RCTCameraViewFinder", "setParameters failed", e);
        }
    }

    public void handleFocus(MotionEvent motionEvent, Parameters parameters) {
        List supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes != null) {
            String str = "auto";
            if (supportedFocusModes.contains(str) && parameters.getMaxNumFocusAreas() != 0) {
                this._camera.cancelAutoFocus();
                try {
                    Area computeFocusAreaFromMotionEvent = RCTCameraUtils.computeFocusAreaFromMotionEvent(motionEvent, this._surfaceTextureWidth, this._surfaceTextureHeight);
                    parameters.setFocusMode(str);
                    supportedFocusModes = new ArrayList();
                    supportedFocusModes.add(computeFocusAreaFromMotionEvent);
                    parameters.setFocusAreas(supportedFocusModes);
                    if (parameters.getMaxNumMeteringAreas() > 0) {
                        parameters.setMeteringAreas(supportedFocusModes);
                    }
                    try {
                        this._camera.setParameters(parameters);
                    } catch (Throwable e) {
                        Log.e("RCTCameraViewFinder", "setParameters failed", e);
                    }
                    try {
                        this._camera.autoFocus(new AutoFocusCallback() {
                            public void onAutoFocus(boolean z, Camera camera) {
                                if (z) {
                                    camera.cancelAutoFocus();
                                }
                            }
                        });
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } catch (RuntimeException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    private float getFingerSpacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }
}
