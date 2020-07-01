package com.lwansbrough.RCTCamera;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.util.Log;
import com.drew.metadata.exif.makernotes.OlympusCameraSettingsMakernoteDirectory;
import com.drew.metadata.photoshop.PhotoshopDirectory;
import com.facebook.react.uimanager.ViewProps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RCTCamera {
    private static final Resolution RESOLUTION_1080P = new Resolution(1920, PhotoshopDirectory.TAG_COUNT_INFORMATION);
    private static final Resolution RESOLUTION_480P = new Resolution(853, 480);
    private static final Resolution RESOLUTION_720P = new Resolution(OlympusCameraSettingsMakernoteDirectory.TagWhiteBalance2, 720);
    private static RCTCamera ourInstance;
    private int _actualDeviceOrientation = 0;
    private int _adjustedDeviceOrientation = 0;
    private List<String> _barCodeTypes = null;
    private boolean _barcodeScannerEnabled = false;
    private final HashMap<Integer, CameraInfoWrapper> _cameraInfos = new HashMap();
    private final HashMap<Integer, Integer> _cameraTypeToIndex = new HashMap();
    private final Map<Number, Camera> _cameras = new HashMap();
    private int _orientation = -1;

    private class CameraInfoWrapper {
        public final CameraInfo info;
        public int previewHeight = -1;
        public int previewVisibleHeight = -1;
        public int previewVisibleWidth = -1;
        public int previewWidth = -1;
        public int rotation = 0;

        public CameraInfoWrapper(CameraInfo cameraInfo) {
            this.info = cameraInfo;
        }
    }

    private static class Resolution {
        public int height;
        public int width;

        public Resolution(int i, int i2) {
            this.width = i;
            this.height = i2;
        }
    }

    public static RCTCamera getInstance() {
        return ourInstance;
    }

    public static void createInstance(int i) {
        ourInstance = new RCTCamera(i);
    }

    public synchronized Camera acquireCameraInstance(int i) {
        if (this._cameras.get(Integer.valueOf(i)) == null && this._cameraTypeToIndex.get(Integer.valueOf(i)) != null) {
            try {
                this._cameras.put(Integer.valueOf(i), Camera.open(((Integer) this._cameraTypeToIndex.get(Integer.valueOf(i))).intValue()));
                adjustPreviewLayout(i);
            } catch (Throwable e) {
                Log.e("RCTCamera", "acquireCameraInstance failed", e);
            }
        }
        return (Camera) this._cameras.get(Integer.valueOf(i));
    }

    public void releaseCameraInstance(int i) {
        Camera camera = (Camera) this._cameras.get(Integer.valueOf(i));
        if (camera != null) {
            this._cameras.remove(Integer.valueOf(i));
            camera.release();
        }
    }

    public int getPreviewWidth(int i) {
        CameraInfoWrapper cameraInfoWrapper = (CameraInfoWrapper) this._cameraInfos.get(Integer.valueOf(i));
        if (cameraInfoWrapper == null) {
            return 0;
        }
        return cameraInfoWrapper.previewWidth;
    }

    public int getPreviewHeight(int i) {
        CameraInfoWrapper cameraInfoWrapper = (CameraInfoWrapper) this._cameraInfos.get(Integer.valueOf(i));
        if (cameraInfoWrapper == null) {
            return 0;
        }
        return cameraInfoWrapper.previewHeight;
    }

    public int getPreviewVisibleHeight(int i) {
        CameraInfoWrapper cameraInfoWrapper = (CameraInfoWrapper) this._cameraInfos.get(Integer.valueOf(i));
        if (cameraInfoWrapper == null) {
            return 0;
        }
        return cameraInfoWrapper.previewVisibleHeight;
    }

    public int getPreviewVisibleWidth(int i) {
        CameraInfoWrapper cameraInfoWrapper = (CameraInfoWrapper) this._cameraInfos.get(Integer.valueOf(i));
        if (cameraInfoWrapper == null) {
            return 0;
        }
        return cameraInfoWrapper.previewVisibleWidth;
    }

    public Size getBestSize(List<Size> list, int i, int i2) {
        Size size = null;
        for (Size size2 : list) {
            if (size2.width <= i) {
                if (size2.height <= i2) {
                    if (size == null || size2.width * size2.height > size.width * size.height) {
                        size = size2;
                    }
                }
            }
        }
        return size;
    }

    private Size getSmallestSize(List<Size> list) {
        Size size = null;
        for (Size size2 : list) {
            if (size == null || size2.width * size2.height < size.width * size.height) {
                size = size2;
            }
        }
        return size;
    }

    private Size getClosestSize(List<Size> list, int i, int i2) {
        Size size = null;
        for (Size size2 : list) {
            if (size != null) {
                if (Math.sqrt(Math.pow((double) (size2.width - i), 2.0d) + Math.pow((double) (size2.height - i2), 2.0d)) >= Math.sqrt(Math.pow((double) (size.width - i), 2.0d) + Math.pow((double) (size.height - i2), 2.0d))) {
                }
            }
            size = size2;
        }
        return size;
    }

    protected List<Size> getSupportedVideoSizes(Camera camera) {
        Parameters parameters = camera.getParameters();
        List<Size> supportedVideoSizes = parameters.getSupportedVideoSizes();
        if (supportedVideoSizes != null) {
            return supportedVideoSizes;
        }
        return parameters.getSupportedPreviewSizes();
    }

    public int getOrientation() {
        return this._orientation;
    }

    public void setOrientation(int i) {
        if (this._orientation != i) {
            this._orientation = i;
            adjustPreviewLayout(1);
            adjustPreviewLayout(2);
        }
    }

    public boolean isBarcodeScannerEnabled() {
        return this._barcodeScannerEnabled;
    }

    public void setBarcodeScannerEnabled(boolean z) {
        this._barcodeScannerEnabled = z;
    }

    public List<String> getBarCodeTypes() {
        return this._barCodeTypes;
    }

    public void setBarCodeTypes(List<String> list) {
        this._barCodeTypes = list;
    }

    public int getActualDeviceOrientation() {
        return this._actualDeviceOrientation;
    }

    public void setAdjustedDeviceOrientation(int i) {
        this._adjustedDeviceOrientation = i;
    }

    public int getAdjustedDeviceOrientation() {
        return this._adjustedDeviceOrientation;
    }

    public void setActualDeviceOrientation(int i) {
        this._actualDeviceOrientation = i;
        adjustPreviewLayout(1);
        adjustPreviewLayout(2);
    }

    public void setCaptureMode(int i, int i2) {
        Camera camera = (Camera) this._cameras.get(Integer.valueOf(i));
        if (camera != null) {
            Parameters parameters = camera.getParameters();
            boolean z = true;
            if (i2 != 1) {
                z = false;
            }
            parameters.setRecordingHint(z);
            try {
                camera.setParameters(parameters);
            } catch (Throwable e) {
                Log.e("RCTCamera", "setParameters failed", e);
            }
        }
    }

    public void setCaptureQuality(int i, String str) {
        Camera acquireCameraInstance = acquireCameraInstance(i);
        if (acquireCameraInstance != null) {
            Parameters parameters = acquireCameraInstance.getParameters();
            Size size = null;
            List supportedPictureSizes = parameters.getSupportedPictureSizes();
            Object obj = -1;
            switch (str.hashCode()) {
                case -1078030475:
                    if (str.equals("medium")) {
                        obj = 1;
                        break;
                    }
                    break;
                case -318184504:
                    if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_PREVIEW)) {
                        obj = 3;
                        break;
                    }
                    break;
                case 107348:
                    if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_LOW)) {
                        obj = null;
                        break;
                    }
                    break;
                case 1604548:
                    if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_480P)) {
                        obj = 4;
                        break;
                    }
                    break;
                case 1688155:
                    if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_720P)) {
                        obj = 5;
                        break;
                    }
                    break;
                case 3202466:
                    if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_HIGH)) {
                        obj = 2;
                        break;
                    }
                    break;
                case 46737913:
                    if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_1080P)) {
                        obj = 6;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    size = getSmallestSize(supportedPictureSizes);
                    break;
                case 1:
                    size = (Size) supportedPictureSizes.get(supportedPictureSizes.size() / 2);
                    break;
                case 2:
                    size = getBestSize(parameters.getSupportedPictureSizes(), Integer.MAX_VALUE, Integer.MAX_VALUE);
                    break;
                case 3:
                    Size bestSize = getBestSize(parameters.getSupportedPreviewSizes(), Integer.MAX_VALUE, Integer.MAX_VALUE);
                    size = getClosestSize(parameters.getSupportedPictureSizes(), bestSize.width, bestSize.height);
                    break;
                case 4:
                    size = getBestSize(supportedPictureSizes, RESOLUTION_480P.width, RESOLUTION_480P.height);
                    break;
                case 5:
                    size = getBestSize(supportedPictureSizes, RESOLUTION_720P.width, RESOLUTION_720P.height);
                    break;
                case 6:
                    size = getBestSize(supportedPictureSizes, RESOLUTION_1080P.width, RESOLUTION_1080P.height);
                    break;
            }
            if (size != null) {
                parameters.setPictureSize(size.width, size.height);
                try {
                    acquireCameraInstance.setParameters(parameters);
                } catch (Throwable e) {
                    Log.e("RCTCamera", "setParameters failed", e);
                }
            }
        }
    }

    public CamcorderProfile setCaptureVideoQuality(int i, String str) {
        Camera acquireCameraInstance = acquireCameraInstance(i);
        if (acquireCameraInstance == null) {
            return null;
        }
        Size smallestSize;
        CamcorderProfile camcorderProfile;
        List supportedVideoSizes = getSupportedVideoSizes(acquireCameraInstance);
        int i2 = -1;
        switch (str.hashCode()) {
            case -1078030475:
                if (str.equals("medium")) {
                    i2 = 1;
                    break;
                }
                break;
            case 107348:
                if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_LOW)) {
                    i2 = 0;
                    break;
                }
                break;
            case 1604548:
                if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_480P)) {
                    i2 = 3;
                    break;
                }
                break;
            case 1688155:
                if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_720P)) {
                    i2 = 4;
                    break;
                }
                break;
            case 3202466:
                if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_HIGH)) {
                    i2 = 2;
                    break;
                }
                break;
            case 46737913:
                if (str.equals(RCTCameraModule.RCT_CAMERA_CAPTURE_QUALITY_1080P)) {
                    i2 = 5;
                    break;
                }
                break;
        }
        if (i2 == 0) {
            smallestSize = getSmallestSize(supportedVideoSizes);
            camcorderProfile = CamcorderProfile.get(((Integer) this._cameraTypeToIndex.get(Integer.valueOf(i))).intValue(), 4);
        } else if (i2 == 1) {
            smallestSize = (Size) supportedVideoSizes.get(supportedVideoSizes.size() / 2);
            camcorderProfile = CamcorderProfile.get(((Integer) this._cameraTypeToIndex.get(Integer.valueOf(i))).intValue(), 5);
        } else if (i2 == 2) {
            smallestSize = getBestSize(supportedVideoSizes, Integer.MAX_VALUE, Integer.MAX_VALUE);
            camcorderProfile = CamcorderProfile.get(((Integer) this._cameraTypeToIndex.get(Integer.valueOf(i))).intValue(), 1);
        } else if (i2 == 3) {
            smallestSize = getBestSize(supportedVideoSizes, RESOLUTION_480P.width, RESOLUTION_480P.height);
            camcorderProfile = CamcorderProfile.get(((Integer) this._cameraTypeToIndex.get(Integer.valueOf(i))).intValue(), 4);
        } else if (i2 == 4) {
            smallestSize = getBestSize(supportedVideoSizes, RESOLUTION_720P.width, RESOLUTION_720P.height);
            camcorderProfile = CamcorderProfile.get(((Integer) this._cameraTypeToIndex.get(Integer.valueOf(i))).intValue(), 5);
        } else if (i2 != 5) {
            camcorderProfile = null;
            smallestSize = camcorderProfile;
        } else {
            smallestSize = getBestSize(supportedVideoSizes, RESOLUTION_1080P.width, RESOLUTION_1080P.height);
            camcorderProfile = CamcorderProfile.get(((Integer) this._cameraTypeToIndex.get(Integer.valueOf(i))).intValue(), 6);
        }
        if (camcorderProfile == null) {
            return null;
        }
        if (smallestSize != null) {
            camcorderProfile.videoFrameHeight = smallestSize.height;
            camcorderProfile.videoFrameWidth = smallestSize.width;
        }
        return camcorderProfile;
    }

    public void setTorchMode(int i, int i2) {
        Camera acquireCameraInstance = acquireCameraInstance(i);
        if (acquireCameraInstance != null) {
            Parameters parameters = acquireCameraInstance.getParameters();
            String flashMode = parameters.getFlashMode();
            if (i2 == 0) {
                flashMode = "off";
            } else if (i2 == 1) {
                flashMode = "torch";
            }
            List supportedFlashModes = parameters.getSupportedFlashModes();
            if (supportedFlashModes != null && supportedFlashModes.contains(flashMode)) {
                parameters.setFlashMode(flashMode);
                try {
                    acquireCameraInstance.setParameters(parameters);
                } catch (Throwable e) {
                    Log.e("RCTCamera", "setParameters failed", e);
                }
            }
        }
    }

    public void setFlashMode(int i, int i2) {
        Camera acquireCameraInstance = acquireCameraInstance(i);
        if (acquireCameraInstance != null) {
            Parameters parameters = acquireCameraInstance.getParameters();
            String flashMode = parameters.getFlashMode();
            if (i2 == 0) {
                flashMode = "off";
            } else if (i2 == 1) {
                flashMode = ViewProps.ON;
            } else if (i2 == 2) {
                flashMode = "auto";
            }
            List supportedFlashModes = parameters.getSupportedFlashModes();
            if (supportedFlashModes != null && supportedFlashModes.contains(flashMode)) {
                parameters.setFlashMode(flashMode);
                try {
                    acquireCameraInstance.setParameters(parameters);
                } catch (Throwable e) {
                    Log.e("RCTCamera", "setParameters failed", e);
                }
            }
        }
    }

    public void setZoom(int i, int i2) {
        Camera acquireCameraInstance = acquireCameraInstance(i);
        if (acquireCameraInstance != null) {
            Parameters parameters = acquireCameraInstance.getParameters();
            int maxZoom = parameters.getMaxZoom();
            if (parameters.isZoomSupported() && i2 >= 0 && i2 < maxZoom) {
                parameters.setZoom(i2);
                try {
                    acquireCameraInstance.setParameters(parameters);
                } catch (Throwable e) {
                    Log.e("RCTCamera", "setParameters failed", e);
                }
            }
        }
    }

    public void adjustCameraRotationToDeviceOrientation(int i, int i2) {
        Camera camera = (Camera) this._cameras.get(Integer.valueOf(i));
        if (camera != null) {
            CameraInfoWrapper cameraInfoWrapper = (CameraInfoWrapper) this._cameraInfos.get(Integer.valueOf(i));
            int i3 = cameraInfoWrapper.info.orientation;
            if (cameraInfoWrapper.info.facing == 1) {
                i3 = (i3 + (i2 * 90)) % 360;
            } else {
                i3 = ((i3 - (i2 * 90)) + 360) % 360;
            }
            cameraInfoWrapper.rotation = i3;
            Parameters parameters = camera.getParameters();
            parameters.setRotation(cameraInfoWrapper.rotation);
            try {
                camera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void adjustPreviewLayout(int i) {
        Camera camera = (Camera) this._cameras.get(Integer.valueOf(i));
        if (camera != null) {
            int i2;
            int i3;
            CameraInfoWrapper cameraInfoWrapper = (CameraInfoWrapper) this._cameraInfos.get(Integer.valueOf(i));
            int i4 = cameraInfoWrapper.info.orientation;
            if (cameraInfoWrapper.info.facing == 1) {
                i2 = this._actualDeviceOrientation;
                i3 = ((i2 * 90) + i4) % 360;
                i4 = ((720 - i4) - (i2 * 90)) % 360;
            } else {
                i3 = ((i4 - (this._actualDeviceOrientation * 90)) + 360) % 360;
                i4 = i3;
            }
            cameraInfoWrapper.rotation = i3;
            setAdjustedDeviceOrientation(i3);
            camera.setDisplayOrientation(i4);
            Parameters parameters = camera.getParameters();
            parameters.setRotation(cameraInfoWrapper.rotation);
            Size bestSize = getBestSize(parameters.getSupportedPreviewSizes(), Integer.MAX_VALUE, Integer.MAX_VALUE);
            i3 = bestSize.width;
            i2 = bestSize.height;
            parameters.setPreviewSize(i3, i2);
            try {
                camera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (cameraInfoWrapper.rotation == 0 || cameraInfoWrapper.rotation == 180) {
                cameraInfoWrapper.previewWidth = i3;
                cameraInfoWrapper.previewHeight = i2;
            } else {
                cameraInfoWrapper.previewWidth = i2;
                cameraInfoWrapper.previewHeight = i3;
            }
        }
    }

    public void setPreviewVisibleSize(int i, int i2, int i3) {
        CameraInfoWrapper cameraInfoWrapper = (CameraInfoWrapper) this._cameraInfos.get(Integer.valueOf(i));
        if (cameraInfoWrapper != null) {
            cameraInfoWrapper.previewVisibleWidth = i2;
            cameraInfoWrapper.previewVisibleHeight = i3;
        }
    }

    private RCTCamera(int i) {
        int i2 = 0;
        this._actualDeviceOrientation = i;
        while (i2 < Camera.getNumberOfCameras()) {
            CameraInfo cameraInfo = new CameraInfo();
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == 1 && this._cameraInfos.get(Integer.valueOf(1)) == null) {
                this._cameraInfos.put(Integer.valueOf(1), new CameraInfoWrapper(cameraInfo));
                this._cameraTypeToIndex.put(Integer.valueOf(1), Integer.valueOf(i2));
                acquireCameraInstance(1);
                releaseCameraInstance(1);
            } else if (cameraInfo.facing == 0 && this._cameraInfos.get(Integer.valueOf(2)) == null) {
                this._cameraInfos.put(Integer.valueOf(2), new CameraInfoWrapper(cameraInfo));
                this._cameraTypeToIndex.put(Integer.valueOf(2), Integer.valueOf(i2));
                acquireCameraInstance(2);
                releaseCameraInstance(2);
            }
            i2++;
        }
    }
}
