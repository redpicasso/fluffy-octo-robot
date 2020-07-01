package com.drew.metadata.exif.makernotes;

import androidx.exifinterface.media.ExifInterface;
import com.drew.lang.ByteArrayReader;
import com.drew.lang.Charsets;
import com.drew.lang.RandomAccessReader;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Age;
import com.drew.metadata.Face;
import com.drew.metadata.TagDescriptor;
import com.facebook.react.modules.clipboard.ClipboardModule;
import com.google.logging.type.LogSeverity;
import java.io.IOException;
import java.text.DecimalFormat;

public class PanasonicMakernoteDescriptor extends TagDescriptor<PanasonicMakernoteDirectory> {
    private static final String[] _sceneModes = new String[]{"Normal", "Portrait", "Scenery", "Sports", "Night Portrait", "Program", "Aperture Priority", "Shutter Priority", "Macro", "Spot", "Manual", "Movie Preview", "Panning", "Simple", "Color Effects", "Self Portrait", "Economy", "Fireworks", "Party", "Snow", "Night Scenery", "Food", "Baby", "Soft Skin", "Candlelight", "Starry Night", "High Sensitivity", "Panorama Assist", "Underwater", "Beach", "Aerial Photo", "Sunset", "Pet", "Intelligent ISO", ClipboardModule.NAME, "High Speed Continuous Shooting", "Intelligent Auto", null, "Multi-aspect", null, "Transform", "Flash Burst", "Pin Hole", "Film Grain", "My Color", "Photo Frame", null, null, null, null, "HDR"};

    public PanasonicMakernoteDescriptor(@NotNull PanasonicMakernoteDirectory panasonicMakernoteDirectory) {
        super(panasonicMakernoteDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        switch (i) {
            case 1:
                return getQualityModeDescription();
            case 2:
                return getVersionDescription();
            case 3:
                return getWhiteBalanceDescription();
            case 7:
                return getFocusModeDescription();
            case 15:
                return getAfAreaModeDescription();
            case 26:
                return getImageStabilizationDescription();
            case 28:
                return getMacroModeDescription();
            case 31:
                return getRecordModeDescription();
            case 32:
                return getAudioDescription();
            case 33:
                return getUnknownDataDumpDescription();
            case 37:
                return getInternalSerialNumberDescription();
            case 38:
                return getExifVersionDescription();
            case 40:
                return getColorEffectDescription();
            case 41:
                return getUptimeDescription();
            case 42:
                return getBurstModeDescription();
            case 44:
                return getContrastModeDescription();
            case 45:
                return getNoiseReductionDescription();
            case 46:
                return getSelfTimerDescription();
            case 48:
                return getRotationDescription();
            case 49:
                return getAfAssistLampDescription();
            case 50:
                return getColorModeDescription();
            case 51:
                return getBabyAgeDescription();
            case 52:
                return getOpticalZoomModeDescription();
            case 53:
                return getConversionLensDescription();
            case 57:
                return getContrastDescription();
            case 58:
                return getWorldTimeLocationDescription();
            case 59:
                return getTextStampDescription();
            case 61:
                return getAdvancedSceneModeDescription();
            case 62:
                return getTextStamp1Description();
            case 69:
                return getBracketSettingsDescription();
            case 72:
                return getFlashCurtainDescription();
            case 73:
                return getLongExposureNoiseReductionDescription();
            case 78:
                return getDetectedFacesDescription();
            case 89:
                return getTransformDescription();
            case 93:
                return getIntelligentExposureDescription();
            case 96:
                return getLensFirmwareVersionDescription();
            case 97:
                return getRecognizedFacesDescription();
            case 98:
                return getFlashWarningDescription();
            case 101:
                return getTitleDescription();
            case 102:
                return getBabyNameDescription();
            case 103:
                return getLocationDescription();
            case 105:
                return getCountryDescription();
            case 107:
                return getStateDescription();
            case 109:
                return getCityDescription();
            case 111:
                return getLandmarkDescription();
            case 112:
                return getIntelligentResolutionDescription();
            case 121:
                return getIntelligentDRangeDescription();
            case 124:
                return getClearRetouchDescription();
            case 137:
                return getPhotoStyleDescription();
            case 138:
                return getShadingCompensationDescription();
            case 140:
                return getAccelerometerZDescription();
            case 141:
                return getAccelerometerXDescription();
            case 142:
                return getAccelerometerYDescription();
            case 143:
                return getCameraOrientationDescription();
            case 144:
                return getRollAngleDescription();
            case 145:
                return getPitchAngleDescription();
            case 147:
                return getSweepPanoramaDirectionDescription();
            case 150:
                return getTimerRecordingDescription();
            case 158:
                return getHDRDescription();
            case 159:
                return getShutterTypeDescription();
            case 171:
                return getTouchAeDescription();
            case 32768:
                return getMakernoteVersionDescription();
            case PanasonicMakernoteDirectory.TAG_SCENE_MODE /*32769*/:
                return getSceneModeDescription();
            case PanasonicMakernoteDirectory.TAG_FLASH_FIRED /*32775*/:
                return getFlashFiredDescription();
            case PanasonicMakernoteDirectory.TAG_TEXT_STAMP_2 /*32776*/:
                return getTextStamp2Description();
            case PanasonicMakernoteDirectory.TAG_TEXT_STAMP_3 /*32777*/:
                return getTextStamp3Description();
            case PanasonicMakernoteDirectory.TAG_BABY_AGE_1 /*32784*/:
                return getBabyAge1Description();
            case PanasonicMakernoteDirectory.TAG_TRANSFORM_1 /*32786*/:
                return getTransform1Description();
            default:
                return super.getDescription(i);
        }
    }

    @Nullable
    public String getTextStampDescription() {
        return getIndexedDescription(59, 1, "Off", "On");
    }

    @Nullable
    public String getTextStamp1Description() {
        return getIndexedDescription(62, 1, "Off", "On");
    }

    @Nullable
    public String getTextStamp2Description() {
        return getIndexedDescription(PanasonicMakernoteDirectory.TAG_TEXT_STAMP_2, 1, "Off", "On");
    }

    @Nullable
    public String getTextStamp3Description() {
        return getIndexedDescription(PanasonicMakernoteDirectory.TAG_TEXT_STAMP_3, 1, "Off", "On");
    }

    @Nullable
    public String getMacroModeDescription() {
        return getIndexedDescription(28, 1, "Off", "On");
    }

    @Nullable
    public String getFlashFiredDescription() {
        return getIndexedDescription(PanasonicMakernoteDirectory.TAG_FLASH_FIRED, 1, "Off", "On");
    }

    @Nullable
    public String getImageStabilizationDescription() {
        return getIndexedDescription(26, 2, "On, Mode 1", "Off", "On, Mode 2");
    }

    @Nullable
    public String getAudioDescription() {
        return getIndexedDescription(32, 1, "Off", "On");
    }

    @Nullable
    public String getTransformDescription() {
        return getTransformDescription(89);
    }

    @Nullable
    public String getTransform1Description() {
        return getTransformDescription(PanasonicMakernoteDirectory.TAG_TRANSFORM_1);
    }

    @Nullable
    private String getTransformDescription(int i) {
        byte[] byteArray = ((PanasonicMakernoteDirectory) this._directory).getByteArray(i);
        if (byteArray == null) {
            return null;
        }
        RandomAccessReader byteArrayReader = new ByteArrayReader(byteArray);
        try {
            i = byteArrayReader.getUInt16(0);
            int uInt16 = byteArrayReader.getUInt16(2);
            if (i == -1 && uInt16 == 1) {
                return "Slim Low";
            }
            if (i == -3 && uInt16 == 2) {
                return "Slim High";
            }
            if (i == 0 && uInt16 == 0) {
                return "Off";
            }
            if (i == 1 && uInt16 == 1) {
                return "Stretch Low";
            }
            if (i == 3 && uInt16 == 2) {
                return "Stretch High";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown (");
            stringBuilder.append(i);
            stringBuilder.append(" ");
            stringBuilder.append(uInt16);
            stringBuilder.append(")");
            return stringBuilder.toString();
        } catch (IOException unused) {
            return null;
        }
    }

    @Nullable
    public String getIntelligentExposureDescription() {
        return getIndexedDescription(93, "Off", "Low", "Standard", "High");
    }

    @Nullable
    public String getFlashWarningDescription() {
        return getIndexedDescription(98, "No", "Yes (Flash required but disabled)");
    }

    @Nullable
    private static String trim(@Nullable String str) {
        return str == null ? null : str.trim();
    }

    @Nullable
    public String getCountryDescription() {
        return trim(getStringFromBytes(105, Charsets.UTF_8));
    }

    @Nullable
    public String getStateDescription() {
        return trim(getStringFromBytes(107, Charsets.UTF_8));
    }

    @Nullable
    public String getCityDescription() {
        return trim(getStringFromBytes(109, Charsets.UTF_8));
    }

    @Nullable
    public String getLandmarkDescription() {
        return trim(getStringFromBytes(111, Charsets.UTF_8));
    }

    @Nullable
    public String getTitleDescription() {
        return trim(getStringFromBytes(101, Charsets.UTF_8));
    }

    @Nullable
    public String getBracketSettingsDescription() {
        return getIndexedDescription(69, "No Bracket", "3 Images, Sequence 0/-/+", "3 Images, Sequence -/0/+", "5 Images, Sequence 0/-/+", "5 Images, Sequence -/0/+", "7 Images, Sequence 0/-/+", "7 Images, Sequence -/0/+");
    }

    @Nullable
    public String getFlashCurtainDescription() {
        return getIndexedDescription(72, "n/a", "1st", "2nd");
    }

    @Nullable
    public String getLongExposureNoiseReductionDescription() {
        return getIndexedDescription(73, 1, "Off", "On");
    }

    @Nullable
    public String getLensFirmwareVersionDescription() {
        byte[] byteArray = ((PanasonicMakernoteDirectory) this._directory).getByteArray(96);
        if (byteArray == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            stringBuilder.append(byteArray[i]);
            if (i < byteArray.length - 1) {
                stringBuilder.append(".");
            }
        }
        return stringBuilder.toString();
    }

    @Nullable
    public String getIntelligentDRangeDescription() {
        return getIndexedDescription(121, "Off", "Low", "Standard", "High");
    }

    @Nullable
    public String getClearRetouchDescription() {
        return getIndexedDescription(124, "Off", "On");
    }

    @Nullable
    public String getPhotoStyleDescription() {
        return getIndexedDescription(137, "Auto", "Standard or Custom", "Vivid", "Natural", "Monochrome", "Scenery", "Portrait");
    }

    @Nullable
    public String getShadingCompensationDescription() {
        return getIndexedDescription(138, "Off", "On");
    }

    @Nullable
    public String getAccelerometerZDescription() {
        Integer integer = ((PanasonicMakernoteDirectory) this._directory).getInteger(140);
        if (integer == null) {
            return null;
        }
        return String.valueOf(integer.shortValue());
    }

    @Nullable
    public String getAccelerometerXDescription() {
        Integer integer = ((PanasonicMakernoteDirectory) this._directory).getInteger(141);
        if (integer == null) {
            return null;
        }
        return String.valueOf(integer.shortValue());
    }

    @Nullable
    public String getAccelerometerYDescription() {
        Integer integer = ((PanasonicMakernoteDirectory) this._directory).getInteger(142);
        if (integer == null) {
            return null;
        }
        return String.valueOf(integer.shortValue());
    }

    @Nullable
    public String getCameraOrientationDescription() {
        return getIndexedDescription(143, "Normal", "Rotate CW", "Rotate 180", "Rotate CCW", "Tilt Upwards", "Tile Downwards");
    }

    @Nullable
    public String getRollAngleDescription() {
        Integer integer = ((PanasonicMakernoteDirectory) this._directory).getInteger(144);
        if (integer == null) {
            return null;
        }
        return new DecimalFormat("0.#").format(((double) integer.shortValue()) / 10.0d);
    }

    @Nullable
    public String getPitchAngleDescription() {
        Integer integer = ((PanasonicMakernoteDirectory) this._directory).getInteger(145);
        if (integer == null) {
            return null;
        }
        return new DecimalFormat("0.#").format(((double) (-integer.shortValue())) / 10.0d);
    }

    @Nullable
    public String getSweepPanoramaDirectionDescription() {
        return getIndexedDescription(147, "Off", "Left to Right", "Right to Left", "Top to Bottom", "Bottom to Top");
    }

    @Nullable
    public String getTimerRecordingDescription() {
        return getIndexedDescription(150, "Off", "Time Lapse", "Stop-motion Animation");
    }

    @Nullable
    public String getHDRDescription() {
        Integer integer = ((PanasonicMakernoteDirectory) this._directory).getInteger(158);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Off";
        }
        if (intValue == 100) {
            return "1 EV";
        }
        if (intValue == LogSeverity.INFO_VALUE) {
            return "2 EV";
        }
        if (intValue == 300) {
            return "3 EV";
        }
        if (intValue == 32868) {
            return "1 EV (Auto)";
        }
        if (intValue == 32968) {
            return "2 EV (Auto)";
        }
        if (intValue == 33068) {
            return "3 EV (Auto)";
        }
        return String.format("Unknown (%d)", new Object[]{integer});
    }

    @Nullable
    public String getShutterTypeDescription() {
        return getIndexedDescription(159, "Mechanical", "Electronic", "Hybrid");
    }

    @Nullable
    public String getTouchAeDescription() {
        return getIndexedDescription(171, "Off", "On");
    }

    @Nullable
    public String getBabyNameDescription() {
        return trim(getStringFromBytes(102, Charsets.UTF_8));
    }

    @Nullable
    public String getLocationDescription() {
        return trim(getStringFromBytes(103, Charsets.UTF_8));
    }

    @Nullable
    public String getIntelligentResolutionDescription() {
        return getIndexedDescription(112, "Off", null, "Auto", "On");
    }

    @Nullable
    public String getContrastDescription() {
        return getIndexedDescription(57, "Normal");
    }

    @Nullable
    public String getWorldTimeLocationDescription() {
        return getIndexedDescription(58, 1, "Home", "Destination");
    }

    @Nullable
    public String getAdvancedSceneModeDescription() {
        return getIndexedDescription(61, 1, "Normal", "Outdoor/Illuminations/Flower/HDR Art", "Indoor/Architecture/Objects/HDR B&W", "Creative", "Auto", null, "Expressive", "Retro", "Pure", "Elegant", null, "Monochrome", "Dynamic Art", "Silhouette");
    }

    @Nullable
    public String getUnknownDataDumpDescription() {
        return getByteLengthDescription(33);
    }

    @Nullable
    public String getColorEffectDescription() {
        return getIndexedDescription(40, 1, "Off", "Warm", "Cool", "Black & White", "Sepia");
    }

    @Nullable
    public String getUptimeDescription() {
        Integer integer = ((PanasonicMakernoteDirectory) this._directory).getInteger(41);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((float) integer.intValue()) / 100.0f);
        stringBuilder.append(" s");
        return stringBuilder.toString();
    }

    @Nullable
    public String getBurstModeDescription() {
        return getIndexedDescription(42, "Off", null, "On", "Indefinite", "Unlimited");
    }

    @Nullable
    public String getContrastModeDescription() {
        Integer integer = ((PanasonicMakernoteDirectory) this._directory).getInteger(44);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        String str = "Normal";
        if (intValue == 0) {
            return str;
        }
        String str2 = "Low";
        if (intValue == 1) {
            return str2;
        }
        String str3 = "High";
        if (intValue == 2) {
            return str3;
        }
        if (intValue == 6) {
            return "Medium Low";
        }
        if (intValue == 7) {
            return "Medium High";
        }
        if (intValue == 256) {
            return str2;
        }
        if (intValue == 272) {
            return str;
        }
        if (intValue == 288) {
            return str3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getNoiseReductionDescription() {
        return getIndexedDescription(45, "Standard (0)", "Low (-1)", "High (+1)", "Lowest (-2)", "Highest (+2)");
    }

    @Nullable
    public String getSelfTimerDescription() {
        return getIndexedDescription(46, 1, "Off", "10 s", "2 s");
    }

    @Nullable
    public String getRotationDescription() {
        Integer integer = ((PanasonicMakernoteDirectory) this._directory).getInteger(48);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 1) {
            return "Horizontal";
        }
        if (intValue == 3) {
            return "Rotate 180";
        }
        if (intValue == 6) {
            return "Rotate 90 CW";
        }
        if (intValue == 8) {
            return "Rotate 270 CW";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getAfAssistLampDescription() {
        return getIndexedDescription(49, 1, "Fired", "Enabled but not used", "Disabled but required", "Disabled and not required");
    }

    @Nullable
    public String getColorModeDescription() {
        return getIndexedDescription(50, "Normal", "Natural", "Vivid");
    }

    @Nullable
    public String getOpticalZoomModeDescription() {
        return getIndexedDescription(52, 1, "Standard", "Extended");
    }

    @Nullable
    public String getConversionLensDescription() {
        return getIndexedDescription(53, 1, "Off", "Wide", "Telephoto", "Macro");
    }

    @Nullable
    public String getDetectedFacesDescription() {
        return buildFacesDescription(((PanasonicMakernoteDirectory) this._directory).getDetectedFaces());
    }

    @Nullable
    public String getRecognizedFacesDescription() {
        return buildFacesDescription(((PanasonicMakernoteDirectory) this._directory).getRecognizedFaces());
    }

    @Nullable
    private String buildFacesDescription(@Nullable Face[] faceArr) {
        String str = null;
        if (faceArr == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (i < faceArr.length) {
            stringBuilder.append("Face ");
            int i2 = i + 1;
            stringBuilder.append(i2);
            stringBuilder.append(": ");
            stringBuilder.append(faceArr[i].toString());
            stringBuilder.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
            i = i2;
        }
        if (stringBuilder.length() > 0) {
            str = stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        return str;
    }

    @Nullable
    public String getRecordModeDescription() {
        return getIndexedDescription(31, 1, _sceneModes);
    }

    @Nullable
    public String getSceneModeDescription() {
        return getIndexedDescription(PanasonicMakernoteDirectory.TAG_SCENE_MODE, 1, _sceneModes);
    }

    @Nullable
    public String getFocusModeDescription() {
        return getIndexedDescription(7, 1, "Auto", "Manual", null, "Auto, Focus Button", "Auto, Continuous");
    }

    @Nullable
    public String getAfAreaModeDescription() {
        int[] intArray = ((PanasonicMakernoteDirectory) this._directory).getIntArray(15);
        if (intArray == null || intArray.length < 2) {
            return null;
        }
        int i = intArray[0];
        String str = ")";
        String str2 = " ";
        String str3 = "Unknown (";
        int i2;
        StringBuilder stringBuilder;
        if (i == 0) {
            i2 = intArray[1];
            if (i2 == 1) {
                return "Spot Mode On";
            }
            if (i2 == 16) {
                return "Spot Mode Off";
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str3);
            stringBuilder.append(intArray[0]);
            stringBuilder.append(str2);
            stringBuilder.append(intArray[1]);
            stringBuilder.append(str);
            return stringBuilder.toString();
        } else if (i == 1) {
            i2 = intArray[1];
            if (i2 == 0) {
                return "Spot Focusing";
            }
            if (i2 == 1) {
                return "5-area";
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str3);
            stringBuilder.append(intArray[0]);
            stringBuilder.append(str2);
            stringBuilder.append(intArray[1]);
            stringBuilder.append(str);
            return stringBuilder.toString();
        } else if (i == 16) {
            i2 = intArray[1];
            if (i2 == 0) {
                return "1-area";
            }
            if (i2 == 16) {
                return "1-area (high speed)";
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str3);
            stringBuilder.append(intArray[0]);
            stringBuilder.append(str2);
            stringBuilder.append(intArray[1]);
            stringBuilder.append(str);
            return stringBuilder.toString();
        } else if (i == 32) {
            i = intArray[1];
            if (i == 0) {
                return "Auto or Face Detect";
            }
            if (i == 1) {
                return "3-area (left)";
            }
            if (i == 2) {
                return "3-area (center)";
            }
            if (i == 3) {
                return "3-area (right)";
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str3);
            stringBuilder.append(intArray[0]);
            stringBuilder.append(str2);
            stringBuilder.append(intArray[1]);
            stringBuilder.append(str);
            return stringBuilder.toString();
        } else if (i == 64) {
            return "Face Detect";
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str3);
            stringBuilder.append(intArray[0]);
            stringBuilder.append(str2);
            stringBuilder.append(intArray[1]);
            stringBuilder.append(str);
            return stringBuilder.toString();
        }
    }

    @Nullable
    public String getQualityModeDescription() {
        return getIndexedDescription(1, 2, "High", "Normal", null, null, "Very High", "Raw", null, "Motion Picture");
    }

    @Nullable
    public String getVersionDescription() {
        return getVersionBytesDescription(2, 2);
    }

    @Nullable
    public String getMakernoteVersionDescription() {
        return getVersionBytesDescription(32768, 2);
    }

    @Nullable
    public String getExifVersionDescription() {
        return getVersionBytesDescription(38, 2);
    }

    @Nullable
    public String getInternalSerialNumberDescription() {
        return get7BitStringFromBytes(37);
    }

    @Nullable
    public String getWhiteBalanceDescription() {
        return getIndexedDescription(3, 1, "Auto", "Daylight", "Cloudy", "Incandescent", "Manual", null, null, ExifInterface.TAG_FLASH, null, "Black & White", "Manual", "Shade");
    }

    @Nullable
    public String getBabyAgeDescription() {
        Age age = ((PanasonicMakernoteDirectory) this._directory).getAge(51);
        if (age == null) {
            return null;
        }
        return age.toFriendlyString();
    }

    @Nullable
    public String getBabyAge1Description() {
        Age age = ((PanasonicMakernoteDirectory) this._directory).getAge(PanasonicMakernoteDirectory.TAG_BABY_AGE_1);
        if (age == null) {
            return null;
        }
        return age.toFriendlyString();
    }
}
