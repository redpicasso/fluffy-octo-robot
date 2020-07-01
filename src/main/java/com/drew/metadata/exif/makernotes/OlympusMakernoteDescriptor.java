package com.drew.metadata.exif.makernotes;

import androidx.exifinterface.media.ExifInterface;
import com.drew.imaging.PhotographicConversions;
import com.drew.lang.DateUtil;
import com.drew.lang.Rational;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;
import com.drew.metadata.exif.makernotes.OlympusMakernoteDirectory.CameraSettings;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class OlympusMakernoteDescriptor extends TagDescriptor<OlympusMakernoteDirectory> {
    public OlympusMakernoteDescriptor(@NotNull OlympusMakernoteDirectory olympusMakernoteDirectory) {
        super(olympusMakernoteDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        if (i == 0) {
            return getMakernoteVersionDescription();
        }
        if (i == 519) {
            return getCameraTypeDescription();
        }
        if (i == 521) {
            return getCameraIdDescription();
        }
        if (i == 770) {
            return getOneTouchWbDescription();
        }
        if (i == 4100) {
            return getFlashModeDescription();
        }
        if (i == OlympusMakernoteDirectory.TAG_SHARPNESS) {
            return getSharpnessDescription();
        }
        if (i == 4113) {
            return getColorMatrixDescription();
        }
        if (i == OlympusMakernoteDirectory.TAG_WB_MODE) {
            return getWbModeDescription();
        }
        if (i == OlympusMakernoteDirectory.TAG_CONTRAST) {
            return getContrastDescription();
        }
        if (i == OlympusMakernoteDirectory.TAG_PREVIEW_IMAGE_VALID) {
            return getPreviewImageValidDescription();
        }
        if (i == 4106) {
            return getFocusRangeDescription();
        }
        if (i == 4107) {
            return getFocusModeDescription();
        }
        if (i == OlympusMakernoteDirectory.TAG_RED_BALANCE) {
            return getRedBalanceDescription();
        }
        if (i == OlympusMakernoteDirectory.TAG_BLUE_BALANCE) {
            return getBlueBalanceDescription();
        }
        switch (i) {
            case 257:
                return getColorModeDescription();
            case 258:
                return getImageQuality1Description();
            case 259:
                return getImageQuality2Description();
            default:
                switch (i) {
                    case 512:
                        return getSpecialModeDescription();
                    case 513:
                        return getJpegQualityDescription();
                    case 514:
                        return getMacroModeDescription();
                    case 515:
                        return getBWModeDescription();
                    case 516:
                        return getDigitalZoomDescription();
                    case 517:
                        return getFocalPlaneDiagonalDescription();
                    default:
                        switch (i) {
                            case 4096:
                                return getShutterSpeedDescription();
                            case 4097:
                                return getIsoValueDescription();
                            case 4098:
                                return getApertureValueDescription();
                            default:
                                switch (i) {
                                    case CameraSettings.TAG_EXPOSURE_MODE /*61442*/:
                                        return getExposureModeDescription();
                                    case CameraSettings.TAG_FLASH_MODE /*61443*/:
                                        return getFlashModeCameraSettingDescription();
                                    case CameraSettings.TAG_WHITE_BALANCE /*61444*/:
                                        return getWhiteBalanceDescription();
                                    case CameraSettings.TAG_IMAGE_SIZE /*61445*/:
                                        return getImageSizeDescription();
                                    case CameraSettings.TAG_IMAGE_QUALITY /*61446*/:
                                        return getImageQualityDescription();
                                    case CameraSettings.TAG_SHOOTING_MODE /*61447*/:
                                        return getShootingModeDescription();
                                    case CameraSettings.TAG_METERING_MODE /*61448*/:
                                        return getMeteringModeDescription();
                                    case CameraSettings.TAG_APEX_FILM_SPEED_VALUE /*61449*/:
                                        return getApexFilmSpeedDescription();
                                    case CameraSettings.TAG_APEX_SHUTTER_SPEED_TIME_VALUE /*61450*/:
                                        return getApexShutterSpeedTimeDescription();
                                    case CameraSettings.TAG_APEX_APERTURE_VALUE /*61451*/:
                                        return getApexApertureDescription();
                                    case CameraSettings.TAG_MACRO_MODE /*61452*/:
                                        return getMacroModeCameraSettingDescription();
                                    case CameraSettings.TAG_DIGITAL_ZOOM /*61453*/:
                                        return getDigitalZoomCameraSettingDescription();
                                    case CameraSettings.TAG_EXPOSURE_COMPENSATION /*61454*/:
                                        return getExposureCompensationDescription();
                                    case CameraSettings.TAG_BRACKET_STEP /*61455*/:
                                        return getBracketStepDescription();
                                    default:
                                        switch (i) {
                                            case CameraSettings.TAG_INTERVAL_LENGTH /*61457*/:
                                                return getIntervalLengthDescription();
                                            case CameraSettings.TAG_INTERVAL_NUMBER /*61458*/:
                                                return getIntervalNumberDescription();
                                            case CameraSettings.TAG_FOCAL_LENGTH /*61459*/:
                                                return getFocalLengthDescription();
                                            case CameraSettings.TAG_FOCUS_DISTANCE /*61460*/:
                                                return getFocusDistanceDescription();
                                            case CameraSettings.TAG_FLASH_FIRED /*61461*/:
                                                return getFlashFiredDescription();
                                            case CameraSettings.TAG_DATE /*61462*/:
                                                return getDateDescription();
                                            case CameraSettings.TAG_TIME /*61463*/:
                                                return getTimeDescription();
                                            case CameraSettings.TAG_MAX_APERTURE_AT_FOCAL_LENGTH /*61464*/:
                                                return getMaxApertureAtFocalLengthDescription();
                                            default:
                                                switch (i) {
                                                    case CameraSettings.TAG_FILE_NUMBER_MEMORY /*61467*/:
                                                        return getFileNumberMemoryDescription();
                                                    case CameraSettings.TAG_LAST_FILE_NUMBER /*61468*/:
                                                        return getLastFileNumberDescription();
                                                    case CameraSettings.TAG_WHITE_BALANCE_RED /*61469*/:
                                                        return getWhiteBalanceRedDescription();
                                                    case CameraSettings.TAG_WHITE_BALANCE_GREEN /*61470*/:
                                                        return getWhiteBalanceGreenDescription();
                                                    case CameraSettings.TAG_WHITE_BALANCE_BLUE /*61471*/:
                                                        return getWhiteBalanceBlueDescription();
                                                    case CameraSettings.TAG_SATURATION /*61472*/:
                                                        return getSaturationDescription();
                                                    case CameraSettings.TAG_CONTRAST /*61473*/:
                                                        return getContrastCameraSettingDescription();
                                                    case CameraSettings.TAG_SHARPNESS /*61474*/:
                                                        return getSharpnessCameraSettingDescription();
                                                    case CameraSettings.TAG_SUBJECT_PROGRAM /*61475*/:
                                                        return getSubjectProgramDescription();
                                                    case CameraSettings.TAG_FLASH_COMPENSATION /*61476*/:
                                                        return getFlashCompensationDescription();
                                                    case CameraSettings.TAG_ISO_SETTING /*61477*/:
                                                        return getIsoSettingDescription();
                                                    case CameraSettings.TAG_CAMERA_MODEL /*61478*/:
                                                        return getCameraModelDescription();
                                                    case CameraSettings.TAG_INTERVAL_MODE /*61479*/:
                                                        return getIntervalModeDescription();
                                                    case CameraSettings.TAG_FOLDER_NAME /*61480*/:
                                                        return getFolderNameDescription();
                                                    case CameraSettings.TAG_COLOR_MODE /*61481*/:
                                                        return getColorModeCameraSettingDescription();
                                                    case CameraSettings.TAG_COLOR_FILTER /*61482*/:
                                                        return getColorFilterDescription();
                                                    case CameraSettings.TAG_BLACK_AND_WHITE_FILTER /*61483*/:
                                                        return getBlackAndWhiteFilterDescription();
                                                    case CameraSettings.TAG_INTERNAL_FLASH /*61484*/:
                                                        return getInternalFlashDescription();
                                                    case CameraSettings.TAG_APEX_BRIGHTNESS_VALUE /*61485*/:
                                                        return getApexBrightnessDescription();
                                                    case CameraSettings.TAG_SPOT_FOCUS_POINT_X_COORDINATE /*61486*/:
                                                        return getSpotFocusPointXCoordinateDescription();
                                                    case CameraSettings.TAG_SPOT_FOCUS_POINT_Y_COORDINATE /*61487*/:
                                                        return getSpotFocusPointYCoordinateDescription();
                                                    case CameraSettings.TAG_WIDE_FOCUS_ZONE /*61488*/:
                                                        return getWideFocusZoneDescription();
                                                    case CameraSettings.TAG_FOCUS_MODE /*61489*/:
                                                        return getFocusModeCameraSettingDescription();
                                                    case CameraSettings.TAG_FOCUS_AREA /*61490*/:
                                                        return getFocusAreaDescription();
                                                    case CameraSettings.TAG_DEC_SWITCH_POSITION /*61491*/:
                                                        return getDecSwitchPositionDescription();
                                                    default:
                                                        return super.getDescription(i);
                                                }
                                        }
                                }
                        }
                }
        }
    }

    @Nullable
    public String getExposureModeDescription() {
        return getIndexedDescription(CameraSettings.TAG_EXPOSURE_MODE, "P", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, ExifInterface.LATITUDE_SOUTH, "M");
    }

    @Nullable
    public String getFlashModeCameraSettingDescription() {
        return getIndexedDescription(CameraSettings.TAG_FLASH_MODE, "Normal", "Red-eye reduction", "Rear flash sync", "Wireless");
    }

    @Nullable
    public String getWhiteBalanceDescription() {
        return getIndexedDescription(CameraSettings.TAG_WHITE_BALANCE, "Auto", "Daylight", "Cloudy", "Tungsten", null, "Custom", null, "Fluorescent", "Fluorescent 2", null, null, "Custom 2", "Custom 3");
    }

    @Nullable
    public String getImageSizeDescription() {
        return getIndexedDescription(CameraSettings.TAG_IMAGE_SIZE, "2560 x 1920", "1600 x 1200", "1280 x 960", "640 x 480");
    }

    @Nullable
    public String getImageQualityDescription() {
        return getIndexedDescription(CameraSettings.TAG_IMAGE_QUALITY, "Raw", "Super Fine", "Fine", "Standard", "Economy", "Extra Fine");
    }

    @Nullable
    public String getShootingModeDescription() {
        return getIndexedDescription(CameraSettings.TAG_SHOOTING_MODE, "Single", "Continuous", "Self Timer", null, "Bracketing", "Interval", "UHS Continuous", "HS Continuous");
    }

    @Nullable
    public String getMeteringModeDescription() {
        return getIndexedDescription(CameraSettings.TAG_METERING_MODE, "Multi-Segment", "Centre Weighted", "Spot");
    }

    @Nullable
    public String getApexFilmSpeedDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_APEX_FILM_SPEED_VALUE);
        if (longObject == null) {
            return null;
        }
        double pow = Math.pow((((double) longObject.longValue()) / 8.0d) - 1.0d, 2.0d) * 3.125d;
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(pow);
    }

    @Nullable
    public String getApexShutterSpeedTimeDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_APEX_SHUTTER_SPEED_TIME_VALUE);
        if (longObject == null) {
            return null;
        }
        double pow = Math.pow(((double) (49 - longObject.longValue())) / 8.0d, 2.0d);
        DecimalFormat decimalFormat = new DecimalFormat("0.###");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(decimalFormat.format(pow));
        stringBuilder.append(" sec");
        return stringBuilder.toString();
    }

    @Nullable
    public String getApexApertureDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_APEX_APERTURE_VALUE);
        if (longObject == null) {
            return null;
        }
        return TagDescriptor.getFStopDescription(Math.pow((((double) longObject.longValue()) / 16.0d) - 0.5d, 2.0d));
    }

    @Nullable
    public String getMacroModeCameraSettingDescription() {
        return getIndexedDescription(CameraSettings.TAG_MACRO_MODE, "Off", "On");
    }

    @Nullable
    public String getDigitalZoomCameraSettingDescription() {
        return getIndexedDescription(CameraSettings.TAG_DIGITAL_ZOOM, "Off", "Electronic magnification", "Digital zoom 2x");
    }

    @Nullable
    public String getExposureCompensationDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_EXPOSURE_COMPENSATION);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        if (longObject == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(decimalFormat.format((((double) longObject.longValue()) / 3.0d) - 2.0d));
        stringBuilder.append(" EV");
        return stringBuilder.toString();
    }

    @Nullable
    public String getBracketStepDescription() {
        return getIndexedDescription(CameraSettings.TAG_BRACKET_STEP, "1/3 EV", "2/3 EV", "1 EV");
    }

    @Nullable
    public String getIntervalLengthDescription() {
        if (!((OlympusMakernoteDirectory) this._directory).isIntervalMode()) {
            return "N/A";
        }
        String str;
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_INTERVAL_LENGTH);
        if (longObject == null) {
            str = null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(longObject);
            stringBuilder.append(" min");
            str = stringBuilder.toString();
        }
        return str;
    }

    @Nullable
    public String getIntervalNumberDescription() {
        if (!((OlympusMakernoteDirectory) this._directory).isIntervalMode()) {
            return "N/A";
        }
        String str;
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_INTERVAL_NUMBER);
        if (longObject == null) {
            str = null;
        } else {
            str = Long.toString(longObject.longValue());
        }
        return str;
    }

    @Nullable
    public String getFocalLengthDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_FOCAL_LENGTH);
        if (longObject == null) {
            return null;
        }
        return TagDescriptor.getFocalLengthDescription(((double) longObject.longValue()) / 256.0d);
    }

    @Nullable
    public String getFocusDistanceDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_FOCUS_DISTANCE);
        if (longObject == null) {
            return null;
        }
        if (longObject.longValue() == 0) {
            return "Infinity";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(longObject);
        stringBuilder.append(" mm");
        return stringBuilder.toString();
    }

    @Nullable
    public String getFlashFiredDescription() {
        return getIndexedDescription(CameraSettings.TAG_FLASH_FIRED, "No", "Yes");
    }

    @Nullable
    public String getDateDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_DATE);
        if (longObject == null) {
            return null;
        }
        int longValue = (int) (longObject.longValue() & 255);
        int longValue2 = (int) ((longObject.longValue() >> 16) & 255);
        if (!DateUtil.isValidDate(((int) (255 & (longObject.longValue() >> 8))) + 1970, longValue2, longValue)) {
            return "Invalid date";
        }
        return String.format("%04d-%02d-%02d", new Object[]{Integer.valueOf(((int) (255 & (longObject.longValue() >> 8))) + 1970), Integer.valueOf(longValue2 + 1), Integer.valueOf(longValue)});
    }

    @Nullable
    public String getTimeDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_TIME);
        if (longObject == null) {
            return null;
        }
        if (!DateUtil.isValidTime((int) ((longObject.longValue() >> 8) & 255), (int) ((longObject.longValue() >> 16) & 255), (int) (255 & longObject.longValue()))) {
            return "Invalid time";
        }
        return String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf((int) ((longObject.longValue() >> 8) & 255)), Integer.valueOf((int) ((longObject.longValue() >> 16) & 255)), Integer.valueOf((int) (255 & longObject.longValue()))});
    }

    @Nullable
    public String getMaxApertureAtFocalLengthDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_TIME);
        if (longObject == null) {
            return null;
        }
        return TagDescriptor.getFStopDescription(Math.pow((((double) longObject.longValue()) / 16.0d) - 0.5d, 2.0d));
    }

    @Nullable
    public String getFileNumberMemoryDescription() {
        return getIndexedDescription(CameraSettings.TAG_FILE_NUMBER_MEMORY, "Off", "On");
    }

    @Nullable
    public String getLastFileNumberDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_LAST_FILE_NUMBER);
        if (longObject == null) {
            return null;
        }
        return longObject.longValue() == 0 ? "File Number Memory Off" : Long.toString(longObject.longValue());
    }

    @Nullable
    public String getWhiteBalanceRedDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_WHITE_BALANCE_RED);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        if (longObject == null) {
            return null;
        }
        return decimalFormat.format(((double) longObject.longValue()) / 256.0d);
    }

    @Nullable
    public String getWhiteBalanceGreenDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_WHITE_BALANCE_GREEN);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        if (longObject == null) {
            return null;
        }
        return decimalFormat.format(((double) longObject.longValue()) / 256.0d);
    }

    @Nullable
    public String getWhiteBalanceBlueDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_WHITE_BALANCE_BLUE);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        if (longObject == null) {
            return null;
        }
        return decimalFormat.format(((double) longObject.longValue()) / 256.0d);
    }

    @Nullable
    public String getSaturationDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_SATURATION);
        if (longObject == null) {
            return null;
        }
        return Long.toString(longObject.longValue() - 3);
    }

    @Nullable
    public String getContrastCameraSettingDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_CONTRAST);
        if (longObject == null) {
            return null;
        }
        return Long.toString(longObject.longValue() - 3);
    }

    @Nullable
    public String getSharpnessCameraSettingDescription() {
        return getIndexedDescription(CameraSettings.TAG_SHARPNESS, "Hard", "Normal", "Soft");
    }

    @Nullable
    public String getSubjectProgramDescription() {
        return getIndexedDescription(CameraSettings.TAG_SUBJECT_PROGRAM, "None", "Portrait", "Text", "Night Portrait", "Sunset", "Sports Action");
    }

    @Nullable
    public String getFlashCompensationDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_FLASH_COMPENSATION);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        if (longObject == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(decimalFormat.format(((double) (longObject.longValue() - 6)) / 3.0d));
        stringBuilder.append(" EV");
        return stringBuilder.toString();
    }

    @Nullable
    public String getIsoSettingDescription() {
        return getIndexedDescription(CameraSettings.TAG_ISO_SETTING, "100", "200", "400", "800", "Auto", "64");
    }

    @Nullable
    public String getCameraModelDescription() {
        return getIndexedDescription(CameraSettings.TAG_CAMERA_MODEL, "DiMAGE 7", "DiMAGE 5", "DiMAGE S304", "DiMAGE S404", "DiMAGE 7i", "DiMAGE 7Hi", "DiMAGE A1", "DiMAGE S414");
    }

    @Nullable
    public String getIntervalModeDescription() {
        return getIndexedDescription(CameraSettings.TAG_INTERVAL_MODE, "Still Image", "Time Lapse Movie");
    }

    @Nullable
    public String getFolderNameDescription() {
        return getIndexedDescription(CameraSettings.TAG_FOLDER_NAME, "Standard Form", "Data Form");
    }

    @Nullable
    public String getColorModeCameraSettingDescription() {
        return getIndexedDescription(CameraSettings.TAG_COLOR_MODE, "Natural Color", "Black & White", "Vivid Color", "Solarization", "AdobeRGB");
    }

    @Nullable
    public String getColorFilterDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_COLOR_FILTER);
        if (longObject == null) {
            return null;
        }
        return Long.toString(longObject.longValue() - 3);
    }

    @Nullable
    public String getBlackAndWhiteFilterDescription() {
        return super.getDescription(CameraSettings.TAG_BLACK_AND_WHITE_FILTER);
    }

    @Nullable
    public String getInternalFlashDescription() {
        return getIndexedDescription(CameraSettings.TAG_INTERNAL_FLASH, "Did Not Fire", "Fired");
    }

    @Nullable
    public String getApexBrightnessDescription() {
        Long longObject = ((OlympusMakernoteDirectory) this._directory).getLongObject(CameraSettings.TAG_APEX_BRIGHTNESS_VALUE);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        if (longObject == null) {
            return null;
        }
        return decimalFormat.format((((double) longObject.longValue()) / 8.0d) - 6.0d);
    }

    @Nullable
    public String getSpotFocusPointXCoordinateDescription() {
        return super.getDescription(CameraSettings.TAG_SPOT_FOCUS_POINT_X_COORDINATE);
    }

    @Nullable
    public String getSpotFocusPointYCoordinateDescription() {
        return super.getDescription(CameraSettings.TAG_SPOT_FOCUS_POINT_Y_COORDINATE);
    }

    @Nullable
    public String getWideFocusZoneDescription() {
        return getIndexedDescription(CameraSettings.TAG_WIDE_FOCUS_ZONE, "No Zone or AF Failed", "Center Zone (Horizontal Orientation)", "Center Zone (Vertical Orientation)", "Left Zone", "Right Zone");
    }

    @Nullable
    public String getFocusModeCameraSettingDescription() {
        return getIndexedDescription(CameraSettings.TAG_FOCUS_MODE, "Auto Focus", "Manual Focus");
    }

    @Nullable
    public String getFocusAreaDescription() {
        return getIndexedDescription(CameraSettings.TAG_FOCUS_AREA, "Wide Focus (Normal)", "Spot Focus");
    }

    @Nullable
    public String getDecSwitchPositionDescription() {
        return getIndexedDescription(CameraSettings.TAG_DEC_SWITCH_POSITION, "Exposure", ExifInterface.TAG_CONTRAST, ExifInterface.TAG_SATURATION, "Filter");
    }

    @Nullable
    public String getMakernoteVersionDescription() {
        return getVersionBytesDescription(0, 2);
    }

    @Nullable
    public String getImageQuality2Description() {
        return getIndexedDescription(259, "Raw", "Super Fine", "Fine", "Standard", "Extra Fine");
    }

    @Nullable
    public String getImageQuality1Description() {
        return getIndexedDescription(258, "Raw", "Super Fine", "Fine", "Standard", "Extra Fine");
    }

    @Nullable
    public String getColorModeDescription() {
        return getIndexedDescription(257, "Natural Colour", "Black & White", "Vivid Colour", "Solarization", "AdobeRGB");
    }

    @Nullable
    public String getSharpnessDescription() {
        return getIndexedDescription(OlympusMakernoteDirectory.TAG_SHARPNESS, "Normal", "Hard", "Soft");
    }

    @Nullable
    public String getColorMatrixDescription() {
        int[] intArray = ((OlympusMakernoteDirectory) this._directory).getIntArray(4113);
        String str = null;
        if (intArray == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < intArray.length; i++) {
            stringBuilder.append((short) intArray[i]);
            if (i < intArray.length - 1) {
                stringBuilder.append(" ");
            }
        }
        if (stringBuilder.length() != 0) {
            str = stringBuilder.toString();
        }
        return str;
    }

    @Nullable
    public String getWbModeDescription() {
        if (((OlympusMakernoteDirectory) this._directory).getIntArray(OlympusMakernoteDirectory.TAG_WB_MODE) == null) {
            return null;
        }
        String format = String.format("%d %d", new Object[]{Integer.valueOf(((OlympusMakernoteDirectory) this._directory).getIntArray(OlympusMakernoteDirectory.TAG_WB_MODE)[0]), Integer.valueOf(((OlympusMakernoteDirectory) this._directory).getIntArray(OlympusMakernoteDirectory.TAG_WB_MODE)[1])});
        if (format.equals("1 0")) {
            return "Auto";
        }
        if (format.equals("1 2")) {
            return "Auto (2)";
        }
        if (format.equals("1 4")) {
            return "Auto (4)";
        }
        if (format.equals("2 2")) {
            return "3000 Kelvin";
        }
        if (format.equals("2 3")) {
            return "3700 Kelvin";
        }
        if (format.equals("2 4")) {
            return "4000 Kelvin";
        }
        if (format.equals("2 5")) {
            return "4500 Kelvin";
        }
        if (format.equals("2 6")) {
            return "5500 Kelvin";
        }
        if (format.equals("2 7")) {
            return "6500 Kelvin";
        }
        if (format.equals("2 8")) {
            return "7500 Kelvin";
        }
        if (format.equals("3 0")) {
            return "One-touch";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown ");
        stringBuilder.append(format);
        return stringBuilder.toString();
    }

    @Nullable
    public String getRedBalanceDescription() {
        int[] intArray = ((OlympusMakernoteDirectory) this._directory).getIntArray(OlympusMakernoteDirectory.TAG_RED_BALANCE);
        if (intArray == null) {
            return null;
        }
        return String.valueOf(((double) ((short) intArray[0])) / 256.0d);
    }

    @Nullable
    public String getBlueBalanceDescription() {
        int[] intArray = ((OlympusMakernoteDirectory) this._directory).getIntArray(OlympusMakernoteDirectory.TAG_BLUE_BALANCE);
        if (intArray == null) {
            return null;
        }
        return String.valueOf(((double) ((short) intArray[0])) / 256.0d);
    }

    @Nullable
    public String getContrastDescription() {
        return getIndexedDescription(OlympusMakernoteDirectory.TAG_CONTRAST, "High", "Normal", "Low");
    }

    @Nullable
    public String getPreviewImageValidDescription() {
        return getIndexedDescription(OlympusMakernoteDirectory.TAG_PREVIEW_IMAGE_VALID, "No", "Yes");
    }

    @Nullable
    public String getFocusModeDescription() {
        return getIndexedDescription(4107, "Auto", "Manual");
    }

    @Nullable
    public String getFocusRangeDescription() {
        return getIndexedDescription(4106, "Normal", "Macro");
    }

    @Nullable
    public String getFlashModeDescription() {
        return getIndexedDescription(4100, null, null, "On", "Off");
    }

    @Nullable
    public String getDigitalZoomDescription() {
        Rational rational = ((OlympusMakernoteDirectory) this._directory).getRational(516);
        if (rational == null) {
            return null;
        }
        return rational.toSimpleString(false);
    }

    @Nullable
    public String getFocalPlaneDiagonalDescription() {
        Rational rational = ((OlympusMakernoteDirectory) this._directory).getRational(517);
        if (rational == null) {
            return null;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.###");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(decimalFormat.format(rational.doubleValue()));
        stringBuilder.append(" mm");
        return stringBuilder.toString();
    }

    @Nullable
    public String getCameraTypeDescription() {
        String string = ((OlympusMakernoteDirectory) this._directory).getString(519);
        if (string == null) {
            return null;
        }
        if (OlympusMakernoteDirectory.OlympusCameraTypes.containsKey(string)) {
            string = (String) OlympusMakernoteDirectory.OlympusCameraTypes.get(string);
        }
        return string;
    }

    @Nullable
    public String getCameraIdDescription() {
        byte[] byteArray = ((OlympusMakernoteDirectory) this._directory).getByteArray(521);
        if (byteArray == null) {
            return null;
        }
        return new String(byteArray);
    }

    @Nullable
    public String getOneTouchWbDescription() {
        return getIndexedDescription(770, "Off", "On", "On (Preset)");
    }

    @Nullable
    public String getShutterSpeedDescription() {
        return super.getShutterSpeedDescription(4096);
    }

    @Nullable
    public String getIsoValueDescription() {
        Rational rational = ((OlympusMakernoteDirectory) this._directory).getRational(4097);
        if (rational == null) {
            return null;
        }
        return String.valueOf(Math.round(Math.pow(2.0d, rational.doubleValue() - 5.0d) * 100.0d));
    }

    @Nullable
    public String getApertureValueDescription() {
        Double doubleObject = ((OlympusMakernoteDirectory) this._directory).getDoubleObject(4098);
        if (doubleObject == null) {
            return null;
        }
        return TagDescriptor.getFStopDescription(PhotographicConversions.apertureToFStop(doubleObject.doubleValue()));
    }

    @Nullable
    public String getMacroModeDescription() {
        return getIndexedDescription(514, "Normal (no macro)", "Macro");
    }

    @Nullable
    public String getBWModeDescription() {
        return getIndexedDescription(515, "Off", "On");
    }

    @Nullable
    public String getJpegQualityDescription() {
        String string = ((OlympusMakernoteDirectory) this._directory).getString(519);
        if (string == null) {
            return getIndexedDescription(513, 1, "Standard Quality", "High Quality", "Super High Quality");
        }
        Integer integer = ((OlympusMakernoteDirectory) this._directory).getInteger(513);
        if (integer == null) {
            return null;
        }
        String str = "RAW";
        String str2 = "Super High Quality (Fine)";
        String str3 = "High Quality (Normal)";
        String str4 = "Standard Quality (Low)";
        String str5 = ")";
        String str6 = "Unknown (";
        int intValue;
        StringBuilder stringBuilder;
        if ((!string.startsWith("SX") || string.startsWith("SX151")) && !string.startsWith("D4322")) {
            intValue = integer.intValue();
            if (intValue == 0) {
                return str4;
            }
            if (intValue == 1) {
                return str3;
            }
            if (intValue == 2) {
                return str2;
            }
            if (intValue == 4) {
                return str;
            }
            if (intValue == 5) {
                return "Medium-Fine";
            }
            if (intValue == 6) {
                return "Small-Fine";
            }
            if (intValue == 33) {
                return "Uncompressed";
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str6);
            stringBuilder.append(integer.toString());
            stringBuilder.append(str5);
            return stringBuilder.toString();
        }
        intValue = integer.intValue();
        if (intValue == 0) {
            return str4;
        }
        if (intValue == 1) {
            return str3;
        }
        if (intValue == 2) {
            return str2;
        }
        if (intValue == 6) {
            return str;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(str6);
        stringBuilder.append(integer.toString());
        stringBuilder.append(str5);
        return stringBuilder.toString();
    }

    @Nullable
    public String getSpecialModeDescription() {
        long[] jArr = (long[]) ((OlympusMakernoteDirectory) this._directory).getObject(512);
        if (jArr == null) {
            return null;
        }
        if (jArr.length < 1) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int i = (int) jArr[0];
        if (i != 0) {
            String str = "Unknown picture taking mode";
            if (i == 1) {
                stringBuilder.append(str);
            } else if (i == 2) {
                stringBuilder.append("Fast picture taking mode");
            } else if (i != 3) {
                stringBuilder.append(str);
            } else {
                stringBuilder.append("Panorama picture taking mode");
            }
        } else {
            stringBuilder.append("Normal picture taking mode");
        }
        if (jArr.length >= 2) {
            i = (int) jArr[1];
            if (i != 0) {
                if (i == 1) {
                    stringBuilder.append(" / 1st in a sequence");
                } else if (i == 2) {
                    stringBuilder.append(" / 2nd in a sequence");
                } else if (i != 3) {
                    stringBuilder.append(" / ");
                    stringBuilder.append(jArr[1]);
                    stringBuilder.append("th in a sequence");
                } else {
                    stringBuilder.append(" / 3rd in a sequence");
                }
            }
        }
        if (jArr.length >= 3) {
            int i2 = (int) jArr[2];
            if (i2 == 1) {
                stringBuilder.append(" / Left to right panorama direction");
            } else if (i2 == 2) {
                stringBuilder.append(" / Right to left panorama direction");
            } else if (i2 == 3) {
                stringBuilder.append(" / Bottom to top panorama direction");
            } else if (i2 == 4) {
                stringBuilder.append(" / Top to bottom panorama direction");
            }
        }
        return stringBuilder.toString();
    }
}
