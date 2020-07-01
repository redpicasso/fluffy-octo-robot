package org.reactnative.camera;

import androidx.annotation.Nullable;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.common.MapBuilder.Builder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CameraViewManager extends ViewGroupManager<RNCameraView> {
    private static final String REACT_CLASS = "RNCamera";

    public enum Events {
        EVENT_CAMERA_READY("onCameraReady"),
        EVENT_ON_MOUNT_ERROR("onMountError"),
        EVENT_ON_BAR_CODE_READ("onBarCodeRead"),
        EVENT_ON_FACES_DETECTED("onFacesDetected"),
        EVENT_ON_BARCODES_DETECTED("onGoogleVisionBarcodesDetected"),
        EVENT_ON_FACE_DETECTION_ERROR("onFaceDetectionError"),
        EVENT_ON_BARCODE_DETECTION_ERROR("onGoogleVisionBarcodeDetectionError"),
        EVENT_ON_TEXT_RECOGNIZED("onTextRecognized"),
        EVENT_ON_PICTURE_TAKEN("onPictureTaken"),
        EVENT_ON_PICTURE_SAVED("onPictureSaved"),
        EVENT_ON_RECORDING_START("onRecordingStart"),
        EVENT_ON_RECORDING_END("onRecordingEnd"),
        EVENT_ON_TOUCH("onTouch");
        
        private final String mName;

        private Events(String str) {
            this.mName = str;
        }

        public String toString() {
            return this.mName;
        }
    }

    public String getName() {
        return REACT_CLASS;
    }

    public void onDropViewInstance(RNCameraView rNCameraView) {
        rNCameraView.onHostDestroy();
        super.onDropViewInstance(rNCameraView);
    }

    protected RNCameraView createViewInstance(ThemedReactContext themedReactContext) {
        return new RNCameraView(themedReactContext);
    }

    @Nullable
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        Builder builder = MapBuilder.builder();
        for (Events events : Events.values()) {
            builder.put(events.toString(), MapBuilder.of("registrationName", events.toString()));
        }
        return builder.build();
    }

    @ReactProp(name = "type")
    public void setType(RNCameraView rNCameraView, int i) {
        rNCameraView.setFacing(i);
    }

    @ReactProp(name = "cameraId")
    public void setCameraId(RNCameraView rNCameraView, String str) {
        rNCameraView.setCameraId(str);
    }

    @ReactProp(name = "ratio")
    public void setRatio(RNCameraView rNCameraView, String str) {
        rNCameraView.setAspectRatio(AspectRatio.parse(str));
    }

    @ReactProp(name = "flashMode")
    public void setFlashMode(RNCameraView rNCameraView, int i) {
        rNCameraView.setFlash(i);
    }

    @ReactProp(name = "exposure")
    public void setExposureCompensation(RNCameraView rNCameraView, float f) {
        rNCameraView.setExposureCompensation(f);
    }

    @ReactProp(name = "autoFocus")
    public void setAutoFocus(RNCameraView rNCameraView, boolean z) {
        rNCameraView.setAutoFocus(z);
    }

    @ReactProp(name = "focusDepth")
    public void setFocusDepth(RNCameraView rNCameraView, float f) {
        rNCameraView.setFocusDepth(f);
    }

    @ReactProp(name = "autoFocusPointOfInterest")
    public void setAutoFocusPointOfInterest(RNCameraView rNCameraView, ReadableMap readableMap) {
        if (readableMap != null) {
            rNCameraView.setAutoFocusPointOfInterest((float) readableMap.getDouble("x"), (float) readableMap.getDouble("y"));
        }
    }

    @ReactProp(name = "zoom")
    public void setZoom(RNCameraView rNCameraView, float f) {
        rNCameraView.setZoom(f);
    }

    @ReactProp(name = "useNativeZoom")
    public void setUseNativeZoom(RNCameraView rNCameraView, boolean z) {
        rNCameraView.setUseNativeZoom(z);
    }

    @ReactProp(name = "whiteBalance")
    public void setWhiteBalance(RNCameraView rNCameraView, int i) {
        rNCameraView.setWhiteBalance(i);
    }

    @ReactProp(name = "pictureSize")
    public void setPictureSize(RNCameraView rNCameraView, String str) {
        rNCameraView.setPictureSize(str.equals("None") ? null : Size.parse(str));
    }

    @ReactProp(name = "playSoundOnCapture")
    public void setPlaySoundOnCapture(RNCameraView rNCameraView, boolean z) {
        rNCameraView.setPlaySoundOnCapture(z);
    }

    @ReactProp(name = "barCodeTypes")
    public void setBarCodeTypes(RNCameraView rNCameraView, ReadableArray readableArray) {
        if (readableArray != null) {
            List arrayList = new ArrayList(readableArray.size());
            for (int i = 0; i < readableArray.size(); i++) {
                arrayList.add(readableArray.getString(i));
            }
            rNCameraView.setBarCodeTypes(arrayList);
        }
    }

    @ReactProp(name = "barCodeScannerEnabled")
    public void setBarCodeScanning(RNCameraView rNCameraView, boolean z) {
        rNCameraView.setShouldScanBarCodes(z);
    }

    @ReactProp(name = "useCamera2Api")
    public void setUseCamera2Api(RNCameraView rNCameraView, boolean z) {
        rNCameraView.setUsingCamera2Api(z);
    }

    @ReactProp(name = "touchDetectorEnabled")
    public void setTouchDetectorEnabled(RNCameraView rNCameraView, boolean z) {
        rNCameraView.setShouldDetectTouches(z);
    }

    @ReactProp(name = "faceDetectorEnabled")
    public void setFaceDetecting(RNCameraView rNCameraView, boolean z) {
        rNCameraView.setShouldDetectFaces(z);
    }

    @ReactProp(name = "faceDetectionMode")
    public void setFaceDetectionMode(RNCameraView rNCameraView, int i) {
        rNCameraView.setFaceDetectionMode(i);
    }

    @ReactProp(name = "faceDetectionLandmarks")
    public void setFaceDetectionLandmarks(RNCameraView rNCameraView, int i) {
        rNCameraView.setFaceDetectionLandmarks(i);
    }

    @ReactProp(name = "faceDetectionClassifications")
    public void setFaceDetectionClassifications(RNCameraView rNCameraView, int i) {
        rNCameraView.setFaceDetectionClassifications(i);
    }

    @ReactProp(name = "trackingEnabled")
    public void setTracking(RNCameraView rNCameraView, boolean z) {
        rNCameraView.setTracking(z);
    }

    @ReactProp(name = "googleVisionBarcodeDetectorEnabled")
    public void setGoogleVisionBarcodeDetecting(RNCameraView rNCameraView, boolean z) {
        rNCameraView.setShouldGoogleDetectBarcodes(z);
    }

    @ReactProp(name = "googleVisionBarcodeType")
    public void setGoogleVisionBarcodeType(RNCameraView rNCameraView, int i) {
        rNCameraView.setGoogleVisionBarcodeType(i);
    }

    @ReactProp(name = "googleVisionBarcodeMode")
    public void setGoogleVisionBarcodeMode(RNCameraView rNCameraView, int i) {
        rNCameraView.setGoogleVisionBarcodeMode(i);
    }

    @ReactProp(name = "textRecognizerEnabled")
    public void setTextRecognizing(RNCameraView rNCameraView, boolean z) {
        rNCameraView.setShouldRecognizeText(z);
    }

    @ReactProp(name = "rectOfInterest")
    public void setRectOfInterest(RNCameraView rNCameraView, ReadableMap readableMap) {
        if (readableMap != null) {
            rNCameraView.setRectOfInterest((float) readableMap.getDouble("x"), (float) readableMap.getDouble("y"), (float) readableMap.getDouble("width"), (float) readableMap.getDouble("height"));
        }
    }

    @ReactProp(name = "cameraViewDimensions")
    public void setCameraViewDimensions(RNCameraView rNCameraView, ReadableMap readableMap) {
        if (readableMap != null) {
            rNCameraView.setCameraViewDimensions((int) readableMap.getDouble("width"), (int) readableMap.getDouble("height"));
        }
    }
}
