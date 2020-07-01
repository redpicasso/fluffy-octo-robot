package org.reactnative.camera;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.CamcorderProfile;
import android.os.Build.VERSION;
import android.view.ViewGroup;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import com.drew.metadata.exif.makernotes.OlympusMakernoteDirectory;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.UIManagerModule;
import com.google.zxing.Result;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.reactnative.barcodedetector.RNBarcodeDetector;
import org.reactnative.camera.events.BarCodeReadEvent;
import org.reactnative.camera.events.BarcodeDetectionErrorEvent;
import org.reactnative.camera.events.BarcodesDetectedEvent;
import org.reactnative.camera.events.CameraMountErrorEvent;
import org.reactnative.camera.events.CameraReadyEvent;
import org.reactnative.camera.events.FaceDetectionErrorEvent;
import org.reactnative.camera.events.FacesDetectedEvent;
import org.reactnative.camera.events.PictureSavedEvent;
import org.reactnative.camera.events.PictureTakenEvent;
import org.reactnative.camera.events.RecordingEndEvent;
import org.reactnative.camera.events.RecordingStartEvent;
import org.reactnative.camera.events.TextRecognizedEvent;
import org.reactnative.camera.events.TouchEvent;
import org.reactnative.facedetector.RNFaceDetector;

public class RNCameraViewHelper {
    public static final String[][] exifTags;

    private static boolean rotationIsLandscape(int i) {
        return i == 90 || i == 270;
    }

    static {
        r0 = new String[129][];
        String str = "string";
        r0[0] = new String[]{str, ExifInterface.TAG_ARTIST};
        String str2 = "int";
        r0[1] = new String[]{str2, ExifInterface.TAG_BITS_PER_SAMPLE};
        r0[2] = new String[]{str2, ExifInterface.TAG_COMPRESSION};
        r0[3] = new String[]{str, ExifInterface.TAG_COPYRIGHT};
        r0[4] = new String[]{str, ExifInterface.TAG_DATETIME};
        r0[5] = new String[]{str, ExifInterface.TAG_IMAGE_DESCRIPTION};
        r0[6] = new String[]{str2, ExifInterface.TAG_IMAGE_LENGTH};
        r0[7] = new String[]{str2, ExifInterface.TAG_IMAGE_WIDTH};
        r0[8] = new String[]{str2, ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT};
        r0[9] = new String[]{str2, ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH};
        r0[10] = new String[]{str, ExifInterface.TAG_MAKE};
        r0[11] = new String[]{str, ExifInterface.TAG_MODEL};
        r0[12] = new String[]{str2, ExifInterface.TAG_ORIENTATION};
        r0[13] = new String[]{str2, ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION};
        r0[14] = new String[]{str2, ExifInterface.TAG_PLANAR_CONFIGURATION};
        String str3 = "double";
        r0[15] = new String[]{str3, ExifInterface.TAG_PRIMARY_CHROMATICITIES};
        r0[16] = new String[]{str3, ExifInterface.TAG_REFERENCE_BLACK_WHITE};
        r0[17] = new String[]{str2, ExifInterface.TAG_RESOLUTION_UNIT};
        r0[18] = new String[]{str2, ExifInterface.TAG_ROWS_PER_STRIP};
        r0[19] = new String[]{str2, ExifInterface.TAG_SAMPLES_PER_PIXEL};
        r0[20] = new String[]{str, ExifInterface.TAG_SOFTWARE};
        r0[21] = new String[]{str2, ExifInterface.TAG_STRIP_BYTE_COUNTS};
        r0[22] = new String[]{str2, ExifInterface.TAG_STRIP_OFFSETS};
        r0[23] = new String[]{str2, ExifInterface.TAG_TRANSFER_FUNCTION};
        r0[24] = new String[]{str3, ExifInterface.TAG_WHITE_POINT};
        r0[25] = new String[]{str3, ExifInterface.TAG_X_RESOLUTION};
        r0[26] = new String[]{str3, ExifInterface.TAG_Y_CB_CR_COEFFICIENTS};
        r0[27] = new String[]{str2, ExifInterface.TAG_Y_CB_CR_POSITIONING};
        r0[28] = new String[]{str2, ExifInterface.TAG_Y_CB_CR_SUB_SAMPLING};
        r0[29] = new String[]{str3, ExifInterface.TAG_Y_RESOLUTION};
        r0[30] = new String[]{str3, ExifInterface.TAG_APERTURE_VALUE};
        r0[31] = new String[]{str3, ExifInterface.TAG_BRIGHTNESS_VALUE};
        r0[32] = new String[]{str, ExifInterface.TAG_CFA_PATTERN};
        r0[33] = new String[]{str2, ExifInterface.TAG_COLOR_SPACE};
        r0[34] = new String[]{str, ExifInterface.TAG_COMPONENTS_CONFIGURATION};
        r0[35] = new String[]{str3, ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL};
        r0[36] = new String[]{str2, ExifInterface.TAG_CONTRAST};
        r0[37] = new String[]{str2, ExifInterface.TAG_CUSTOM_RENDERED};
        r0[38] = new String[]{str, ExifInterface.TAG_DATETIME_DIGITIZED};
        r0[39] = new String[]{str, ExifInterface.TAG_DATETIME_ORIGINAL};
        r0[40] = new String[]{str, ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION};
        r0[41] = new String[]{str3, ExifInterface.TAG_DIGITAL_ZOOM_RATIO};
        r0[42] = new String[]{str, ExifInterface.TAG_EXIF_VERSION};
        r0[43] = new String[]{str3, ExifInterface.TAG_EXPOSURE_BIAS_VALUE};
        r0[44] = new String[]{str3, ExifInterface.TAG_EXPOSURE_INDEX};
        r0[45] = new String[]{str2, ExifInterface.TAG_EXPOSURE_MODE};
        r0[46] = new String[]{str2, ExifInterface.TAG_EXPOSURE_PROGRAM};
        r0[47] = new String[]{str3, ExifInterface.TAG_EXPOSURE_TIME};
        r0[48] = new String[]{str3, ExifInterface.TAG_F_NUMBER};
        r0[49] = new String[]{str, ExifInterface.TAG_FILE_SOURCE};
        r0[50] = new String[]{str2, ExifInterface.TAG_FLASH};
        r0[51] = new String[]{str3, ExifInterface.TAG_FLASH_ENERGY};
        r0[52] = new String[]{str, ExifInterface.TAG_FLASHPIX_VERSION};
        r0[53] = new String[]{str3, ExifInterface.TAG_FOCAL_LENGTH};
        r0[54] = new String[]{str2, ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM};
        r0[55] = new String[]{str2, ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT};
        r0[56] = new String[]{str3, ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION};
        r0[57] = new String[]{str3, ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION};
        r0[58] = new String[]{str2, ExifInterface.TAG_GAIN_CONTROL};
        r0[59] = new String[]{str2, ExifInterface.TAG_ISO_SPEED_RATINGS};
        r0[60] = new String[]{str, ExifInterface.TAG_IMAGE_UNIQUE_ID};
        r0[61] = new String[]{str2, ExifInterface.TAG_LIGHT_SOURCE};
        r0[62] = new String[]{str, ExifInterface.TAG_MAKER_NOTE};
        r0[63] = new String[]{str3, ExifInterface.TAG_MAX_APERTURE_VALUE};
        r0[64] = new String[]{str2, ExifInterface.TAG_METERING_MODE};
        r0[65] = new String[]{str2, ExifInterface.TAG_NEW_SUBFILE_TYPE};
        r0[66] = new String[]{str, ExifInterface.TAG_OECF};
        r0[67] = new String[]{str2, ExifInterface.TAG_PIXEL_X_DIMENSION};
        r0[68] = new String[]{str2, ExifInterface.TAG_PIXEL_Y_DIMENSION};
        r0[69] = new String[]{str, ExifInterface.TAG_RELATED_SOUND_FILE};
        r0[70] = new String[]{str2, ExifInterface.TAG_SATURATION};
        r0[71] = new String[]{str2, ExifInterface.TAG_SCENE_CAPTURE_TYPE};
        r0[72] = new String[]{str, ExifInterface.TAG_SCENE_TYPE};
        r0[73] = new String[]{str2, ExifInterface.TAG_SENSING_METHOD};
        r0[74] = new String[]{str2, ExifInterface.TAG_SHARPNESS};
        r0[75] = new String[]{str3, ExifInterface.TAG_SHUTTER_SPEED_VALUE};
        r0[76] = new String[]{str, ExifInterface.TAG_SPATIAL_FREQUENCY_RESPONSE};
        r0[77] = new String[]{str, ExifInterface.TAG_SPECTRAL_SENSITIVITY};
        r0[78] = new String[]{str2, ExifInterface.TAG_SUBFILE_TYPE};
        r0[79] = new String[]{str, ExifInterface.TAG_SUBSEC_TIME};
        r0[80] = new String[]{str, ExifInterface.TAG_SUBSEC_TIME_DIGITIZED};
        r0[81] = new String[]{str, ExifInterface.TAG_SUBSEC_TIME_ORIGINAL};
        r0[82] = new String[]{str2, ExifInterface.TAG_SUBJECT_AREA};
        r0[83] = new String[]{str3, ExifInterface.TAG_SUBJECT_DISTANCE};
        r0[84] = new String[]{str2, ExifInterface.TAG_SUBJECT_DISTANCE_RANGE};
        r0[85] = new String[]{str2, ExifInterface.TAG_SUBJECT_LOCATION};
        r0[86] = new String[]{str, ExifInterface.TAG_USER_COMMENT};
        r0[87] = new String[]{str2, ExifInterface.TAG_WHITE_BALANCE};
        r0[88] = new String[]{str2, ExifInterface.TAG_GPS_ALTITUDE_REF};
        r0[89] = new String[]{str, ExifInterface.TAG_GPS_AREA_INFORMATION};
        r0[90] = new String[]{str3, ExifInterface.TAG_GPS_DOP};
        r0[91] = new String[]{str, ExifInterface.TAG_GPS_DATESTAMP};
        r0[92] = new String[]{str3, ExifInterface.TAG_GPS_DEST_BEARING};
        r0[93] = new String[]{str, ExifInterface.TAG_GPS_DEST_BEARING_REF};
        r0[94] = new String[]{str3, ExifInterface.TAG_GPS_DEST_DISTANCE};
        r0[95] = new String[]{str, ExifInterface.TAG_GPS_DEST_DISTANCE_REF};
        r0[96] = new String[]{str3, ExifInterface.TAG_GPS_DEST_LATITUDE};
        r0[97] = new String[]{str, ExifInterface.TAG_GPS_DEST_LATITUDE_REF};
        r0[98] = new String[]{str3, ExifInterface.TAG_GPS_DEST_LONGITUDE};
        r0[99] = new String[]{str, ExifInterface.TAG_GPS_DEST_LONGITUDE_REF};
        r0[100] = new String[]{str2, ExifInterface.TAG_GPS_DIFFERENTIAL};
        r0[101] = new String[]{str3, ExifInterface.TAG_GPS_IMG_DIRECTION};
        r0[102] = new String[]{str, ExifInterface.TAG_GPS_IMG_DIRECTION_REF};
        r0[103] = new String[]{str, ExifInterface.TAG_GPS_LATITUDE_REF};
        r0[104] = new String[]{str, ExifInterface.TAG_GPS_LONGITUDE_REF};
        r0[105] = new String[]{str, ExifInterface.TAG_GPS_MAP_DATUM};
        r0[106] = new String[]{str, ExifInterface.TAG_GPS_MEASURE_MODE};
        r0[107] = new String[]{str, ExifInterface.TAG_GPS_PROCESSING_METHOD};
        r0[108] = new String[]{str, ExifInterface.TAG_GPS_SATELLITES};
        r0[109] = new String[]{str3, ExifInterface.TAG_GPS_SPEED};
        r0[110] = new String[]{str, ExifInterface.TAG_GPS_SPEED_REF};
        r0[111] = new String[]{str, ExifInterface.TAG_GPS_STATUS};
        r0[112] = new String[]{str, ExifInterface.TAG_GPS_TIMESTAMP};
        r0[113] = new String[]{str3, ExifInterface.TAG_GPS_TRACK};
        r0[114] = new String[]{str, ExifInterface.TAG_GPS_TRACK_REF};
        r0[115] = new String[]{str, ExifInterface.TAG_GPS_VERSION_ID};
        r0[116] = new String[]{str, ExifInterface.TAG_INTEROPERABILITY_INDEX};
        r0[117] = new String[]{str2, ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH};
        r0[118] = new String[]{str2, ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH};
        r0[119] = new String[]{str2, ExifInterface.TAG_DNG_VERSION};
        r0[120] = new String[]{str2, ExifInterface.TAG_DEFAULT_CROP_SIZE};
        r0[121] = new String[]{str2, ExifInterface.TAG_ORF_PREVIEW_IMAGE_START};
        r0[122] = new String[]{str2, ExifInterface.TAG_ORF_PREVIEW_IMAGE_LENGTH};
        r0[123] = new String[]{str2, ExifInterface.TAG_ORF_ASPECT_FRAME};
        r0[124] = new String[]{str2, ExifInterface.TAG_RW2_SENSOR_BOTTOM_BORDER};
        r0[125] = new String[]{str2, ExifInterface.TAG_RW2_SENSOR_LEFT_BORDER};
        r0[126] = new String[]{str2, ExifInterface.TAG_RW2_SENSOR_RIGHT_BORDER};
        r0[127] = new String[]{str2, ExifInterface.TAG_RW2_SENSOR_TOP_BORDER};
        r0[128] = new String[]{str2, ExifInterface.TAG_RW2_ISO};
        exifTags = r0;
    }

    public static void emitMountErrorEvent(final ViewGroup viewGroup, final String str) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(CameraMountErrorEvent.obtain(viewGroup.getId(), str));
            }
        });
    }

    public static void emitCameraReadyEvent(final ViewGroup viewGroup) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(CameraReadyEvent.obtain(viewGroup.getId()));
            }
        });
    }

    public static void emitPictureSavedEvent(final ViewGroup viewGroup, final WritableMap writableMap) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(PictureSavedEvent.obtain(viewGroup.getId(), writableMap));
            }
        });
    }

    public static void emitPictureTakenEvent(final ViewGroup viewGroup) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(PictureTakenEvent.obtain(viewGroup.getId()));
            }
        });
    }

    public static void emitRecordingStartEvent(final ViewGroup viewGroup, final WritableMap writableMap) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(RecordingStartEvent.obtain(viewGroup.getId(), writableMap));
            }
        });
    }

    public static void emitRecordingEndEvent(final ViewGroup viewGroup) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(RecordingEndEvent.obtain(viewGroup.getId()));
            }
        });
    }

    public static void emitTouchEvent(ViewGroup viewGroup, boolean z, int i, int i2) {
        ReactContext reactContext = (ReactContext) viewGroup.getContext();
        final ViewGroup viewGroup2 = viewGroup;
        final boolean z2 = z;
        final int i3 = i;
        final int i4 = i2;
        final ReactContext reactContext2 = reactContext;
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext2.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(TouchEvent.obtain(viewGroup2.getId(), z2, i3, i4));
            }
        });
    }

    public static void emitFacesDetectedEvent(final ViewGroup viewGroup, final WritableArray writableArray) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(FacesDetectedEvent.obtain(viewGroup.getId(), writableArray));
            }
        });
    }

    public static void emitFaceDetectionErrorEvent(final ViewGroup viewGroup, final RNFaceDetector rNFaceDetector) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(FaceDetectionErrorEvent.obtain(viewGroup.getId(), rNFaceDetector));
            }
        });
    }

    public static void emitBarcodesDetectedEvent(final ViewGroup viewGroup, final WritableArray writableArray) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(BarcodesDetectedEvent.obtain(viewGroup.getId(), writableArray));
            }
        });
    }

    public static void emitBarcodeDetectionErrorEvent(final ViewGroup viewGroup, final RNBarcodeDetector rNBarcodeDetector) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(BarcodeDetectionErrorEvent.obtain(viewGroup.getId(), rNBarcodeDetector));
            }
        });
    }

    public static void emitBarCodeReadEvent(ViewGroup viewGroup, Result result, int i, int i2) {
        ReactContext reactContext = (ReactContext) viewGroup.getContext();
        final ViewGroup viewGroup2 = viewGroup;
        final Result result2 = result;
        final int i3 = i;
        final int i4 = i2;
        final ReactContext reactContext2 = reactContext;
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext2.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(BarCodeReadEvent.obtain(viewGroup2.getId(), result2, i3, i4));
            }
        });
    }

    public static void emitTextRecognizedEvent(final ViewGroup viewGroup, final WritableArray writableArray) {
        final ReactContext reactContext = (ReactContext) viewGroup.getContext();
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
            public void run() {
                ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(TextRecognizedEvent.obtain(viewGroup.getId(), writableArray));
            }
        });
    }

    public static int getCorrectCameraRotation(int i, int i2, int i3) {
        if (i2 == 1) {
            return (i3 + i) % 360;
        }
        return ((i3 - i) + (rotationIsLandscape(i) ? 180 : 0)) % 360;
    }

    private static int getCamcorderProfileQualityFromCameraModuleConstant(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    return (i == 3 || i == 4) ? 4 : 1;
                } else {
                    return 5;
                }
            }
        } else if (VERSION.SDK_INT >= 21) {
            return 8;
        }
        return 6;
    }

    public static CamcorderProfile getCamcorderProfile(int i) {
        CamcorderProfile camcorderProfile = CamcorderProfile.get(1);
        int camcorderProfileQualityFromCameraModuleConstant = getCamcorderProfileQualityFromCameraModuleConstant(i);
        if (CamcorderProfile.hasProfile(camcorderProfileQualityFromCameraModuleConstant)) {
            camcorderProfile = CamcorderProfile.get(camcorderProfileQualityFromCameraModuleConstant);
            if (i == 4) {
                camcorderProfile.videoFrameWidth = OlympusMakernoteDirectory.TAG_PREVIEW_IMAGE;
            }
        }
        return camcorderProfile;
    }

    public static WritableMap getExifData(ExifInterface exifInterface) {
        WritableMap createMap = Arguments.createMap();
        for (String[] strArr : exifTags) {
            String str = strArr[1];
            if (exifInterface.getAttribute(str) != null) {
                String str2 = strArr[0];
                int i = -1;
                int hashCode = str2.hashCode();
                if (hashCode != -1325958191) {
                    if (hashCode != -891985903) {
                        if (hashCode == 104431 && str2.equals("int")) {
                            i = 1;
                        }
                    } else if (str2.equals("string")) {
                        i = 0;
                    }
                } else if (str2.equals("double")) {
                    i = 2;
                }
                if (i == 0) {
                    createMap.putString(str, exifInterface.getAttribute(str));
                } else if (i == 1) {
                    createMap.putInt(str, exifInterface.getAttributeInt(str, 0));
                } else if (i == 2) {
                    createMap.putDouble(str, exifInterface.getAttributeDouble(str, 0.0d));
                }
            }
        }
        double[] latLong = exifInterface.getLatLong();
        if (latLong != null) {
            createMap.putDouble(ExifInterface.TAG_GPS_LATITUDE, latLong[0]);
            createMap.putDouble(ExifInterface.TAG_GPS_LONGITUDE, latLong[1]);
            createMap.putDouble(ExifInterface.TAG_GPS_ALTITUDE, exifInterface.getAltitude(0.0d));
        }
        return createMap;
    }

    public static void setExifData(ExifInterface exifInterface, ReadableMap readableMap) {
        for (String[] strArr : exifTags) {
            String str = strArr[1];
            if (readableMap.hasKey(str)) {
                String str2 = strArr[0];
                int i = -1;
                int hashCode = str2.hashCode();
                if (hashCode != -1325958191) {
                    if (hashCode != -891985903) {
                        if (hashCode == 104431 && str2.equals("int")) {
                            i = 1;
                        }
                    } else if (str2.equals("string")) {
                        i = 0;
                    }
                } else if (str2.equals("double")) {
                    i = 2;
                }
                if (i == 0) {
                    exifInterface.setAttribute(str, readableMap.getString(str));
                } else if (i == 1) {
                    exifInterface.setAttribute(str, Integer.toString(readableMap.getInt(str)));
                    readableMap.getInt(str);
                } else if (i == 2) {
                    exifInterface.setAttribute(str, Double.toString(readableMap.getDouble(str)));
                    readableMap.getDouble(str);
                }
            }
        }
        String str3 = ExifInterface.TAG_GPS_LATITUDE;
        if (readableMap.hasKey(str3)) {
            String str4 = ExifInterface.TAG_GPS_LONGITUDE;
            if (readableMap.hasKey(str4)) {
                exifInterface.setLatLong(readableMap.getDouble(str3), readableMap.getDouble(str4));
            }
        }
        str3 = ExifInterface.TAG_GPS_ALTITUDE;
        if (readableMap.hasKey(str3)) {
            exifInterface.setAltitude(readableMap.getDouble(str3));
        }
    }

    public static void clearExifData(ExifInterface exifInterface) {
        for (String[] strArr : exifTags) {
            exifInterface.setAttribute(strArr[1], null);
        }
        exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, null);
        exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, null);
        exifInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, null);
    }

    public static Bitmap generateSimulatorPhoto(int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        float f = (float) i;
        float f2 = (float) i2;
        canvas.drawRect(0.0f, 0.0f, f, f2, paint);
        Paint paint2 = new Paint();
        paint2.setColor(InputDeviceCompat.SOURCE_ANY);
        paint2.setTextSize(35.0f);
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G '->' HH:mm:ss z");
        canvas.drawText(simpleDateFormat.format(instance.getTime()), 0.1f * f, f2 * 0.2f, paint2);
        canvas.drawText(simpleDateFormat.format(instance.getTime()), 0.2f * f, f2 * 0.4f, paint2);
        canvas.drawText(simpleDateFormat.format(instance.getTime()), 0.3f * f, 0.6f * f2, paint2);
        canvas.drawText(simpleDateFormat.format(instance.getTime()), f * 0.4f, f2 * 0.8f, paint2);
        return createBitmap;
    }
}
