package com.drew.metadata.exif.makernotes;

import androidx.exifinterface.media.ExifInterface;
import com.adobe.xmp.XMPError;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory.AFInfo;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory.CameraSettings;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory.FocalLength;
import com.facebook.imageutils.JfifUtil;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.logging.type.LogSeverity;
import java.text.DecimalFormat;
import java.util.HashMap;

public class CanonMakernoteDescriptor extends TagDescriptor<CanonMakernoteDirectory> {
    private static final HashMap<Integer, String> _lensTypeById = new HashMap();

    private double decodeCanonEv(int i) {
        int i2;
        if (i < 0) {
            i = -i;
            i2 = -1;
        } else {
            i2 = 1;
        }
        int i3 = i & 31;
        i -= i3;
        if (i3 == 12) {
            i3 = 10;
        } else if (i3 == 20) {
            i3 = 21;
        }
        return ((double) (i2 * (i + i3))) / 32.0d;
    }

    public CanonMakernoteDescriptor(@NotNull CanonMakernoteDirectory canonMakernoteDirectory) {
        super(canonMakernoteDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        switch (i) {
            case 12:
                return getSerialNumberDescription();
            case CameraSettings.TAG_FOCUS_MODE_1 /*49415*/:
                return getFocusMode1Description();
            case CameraSettings.TAG_COLOR_TONE /*49449*/:
                return getColorToneDescription();
            case CameraSettings.TAG_SRAW_QUALITY /*49453*/:
                return getSRawQualityDescription();
            case FocalLength.TAG_WHITE_BALANCE /*49671*/:
                return getWhiteBalanceDescription();
            case AFInfo.TAG_AF_POINTS_IN_FOCUS /*53770*/:
                return getTagAfPointsInFocus();
            default:
                switch (i) {
                    case CameraSettings.TAG_MACRO_MODE /*49409*/:
                        return getMacroModeDescription();
                    case CameraSettings.TAG_SELF_TIMER_DELAY /*49410*/:
                        return getSelfTimerDelayDescription();
                    case CameraSettings.TAG_QUALITY /*49411*/:
                        return getQualityDescription();
                    case CameraSettings.TAG_FLASH_MODE /*49412*/:
                        return getFlashModeDescription();
                    case CameraSettings.TAG_CONTINUOUS_DRIVE_MODE /*49413*/:
                        return getContinuousDriveModeDescription();
                    default:
                        switch (i) {
                            case CameraSettings.TAG_RECORD_MODE /*49417*/:
                                return getRecordModeDescription();
                            case CameraSettings.TAG_IMAGE_SIZE /*49418*/:
                                return getImageSizeDescription();
                            case CameraSettings.TAG_EASY_SHOOTING_MODE /*49419*/:
                                return getEasyShootingModeDescription();
                            case CameraSettings.TAG_DIGITAL_ZOOM /*49420*/:
                                return getDigitalZoomDescription();
                            case CameraSettings.TAG_CONTRAST /*49421*/:
                                return getContrastDescription();
                            case CameraSettings.TAG_SATURATION /*49422*/:
                                return getSaturationDescription();
                            case CameraSettings.TAG_SHARPNESS /*49423*/:
                                return getSharpnessDescription();
                            case CameraSettings.TAG_ISO /*49424*/:
                                return getIsoDescription();
                            case CameraSettings.TAG_METERING_MODE /*49425*/:
                                return getMeteringModeDescription();
                            case CameraSettings.TAG_FOCUS_TYPE /*49426*/:
                                return getFocusTypeDescription();
                            case CameraSettings.TAG_AF_POINT_SELECTED /*49427*/:
                                return getAfPointSelectedDescription();
                            case CameraSettings.TAG_EXPOSURE_MODE /*49428*/:
                                return getExposureModeDescription();
                            default:
                                switch (i) {
                                    case CameraSettings.TAG_LENS_TYPE /*49430*/:
                                        return getLensTypeDescription();
                                    case CameraSettings.TAG_LONG_FOCAL_LENGTH /*49431*/:
                                        return getLongFocalLengthDescription();
                                    case CameraSettings.TAG_SHORT_FOCAL_LENGTH /*49432*/:
                                        return getShortFocalLengthDescription();
                                    case CameraSettings.TAG_FOCAL_UNITS_PER_MM /*49433*/:
                                        return getFocalUnitsPerMillimetreDescription();
                                    case CameraSettings.TAG_MAX_APERTURE /*49434*/:
                                        return getMaxApertureDescription();
                                    case CameraSettings.TAG_MIN_APERTURE /*49435*/:
                                        return getMinApertureDescription();
                                    case CameraSettings.TAG_FLASH_ACTIVITY /*49436*/:
                                        return getFlashActivityDescription();
                                    case CameraSettings.TAG_FLASH_DETAILS /*49437*/:
                                        return getFlashDetailsDescription();
                                    case CameraSettings.TAG_FOCUS_CONTINUOUS /*49438*/:
                                        return getFocusContinuousDescription();
                                    case CameraSettings.TAG_AE_SETTING /*49439*/:
                                        return getAESettingDescription();
                                    case CameraSettings.TAG_FOCUS_MODE_2 /*49440*/:
                                        return getFocusMode2Description();
                                    case CameraSettings.TAG_DISPLAY_APERTURE /*49441*/:
                                        return getDisplayApertureDescription();
                                    default:
                                        switch (i) {
                                            case CameraSettings.TAG_SPOT_METERING_MODE /*49445*/:
                                                return getSpotMeteringModeDescription();
                                            case CameraSettings.TAG_PHOTO_EFFECT /*49446*/:
                                                return getPhotoEffectDescription();
                                            case CameraSettings.TAG_MANUAL_FLASH_OUTPUT /*49447*/:
                                                return getManualFlashOutputDescription();
                                            default:
                                                switch (i) {
                                                    case FocalLength.TAG_AF_POINT_USED /*49678*/:
                                                        return getAfPointUsedDescription();
                                                    case FocalLength.TAG_FLASH_BIAS /*49679*/:
                                                        return getFlashBiasDescription();
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
    public String getSerialNumberDescription() {
        if (((CanonMakernoteDirectory) this._directory).getInteger(12) == null) {
            return null;
        }
        return String.format("%04X%05d", new Object[]{Integer.valueOf((((CanonMakernoteDirectory) this._directory).getInteger(12).intValue() >> 8) & 255), Integer.valueOf(((CanonMakernoteDirectory) this._directory).getInteger(12).intValue() & 255)});
    }

    @Nullable
    public String getFlashBiasDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(FocalLength.TAG_FLASH_BIAS);
        if (integer == null) {
            return null;
        }
        Object obj = null;
        if (integer.intValue() > 61440) {
            integer = Integer.valueOf(Integer.valueOf(65535 - integer.intValue()).intValue() + 1);
            obj = 1;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(obj != null ? "-" : "");
        stringBuilder.append(Float.toString(((float) integer.intValue()) / 32.0f));
        stringBuilder.append(" EV");
        return stringBuilder.toString();
    }

    @Nullable
    public String getAfPointUsedDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(FocalLength.TAG_AF_POINT_USED);
        if (integer == null) {
            return null;
        }
        if ((integer.intValue() & 7) == 0) {
            return "Right";
        }
        if ((integer.intValue() & 7) == 1) {
            return "Centre";
        }
        if ((integer.intValue() & 7) == 2) {
            return "Left";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getTagAfPointsInFocus() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(AFInfo.TAG_AF_POINTS_IN_FOCUS);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if ((integer.intValue() & (1 << i)) != 0) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(i);
            }
        }
        return stringBuilder.length() == 0 ? "None" : stringBuilder.toString();
    }

    @Nullable
    public String getWhiteBalanceDescription() {
        return getIndexedDescription(FocalLength.TAG_WHITE_BALANCE, "Auto", "Sunny", "Cloudy", "Tungsten", "Florescent", ExifInterface.TAG_FLASH, "Custom");
    }

    @Nullable
    public String getFocusMode2Description() {
        return getIndexedDescription(CameraSettings.TAG_FOCUS_MODE_2, "Single", "Continuous");
    }

    @Nullable
    public String getFlashDetailsDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_FLASH_DETAILS);
        if (integer == null) {
            return null;
        }
        if (((integer.intValue() >> 14) & 1) != 0) {
            return "External E-TTL";
        }
        if (((integer.intValue() >> 13) & 1) != 0) {
            return "Internal flash";
        }
        if (((integer.intValue() >> 11) & 1) != 0) {
            return "FP sync used";
        }
        if (((integer.intValue() >> 4) & 1) != 0) {
            return "FP sync enabled";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getFocalUnitsPerMillimetreDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_FOCAL_UNITS_PER_MM);
        if (integer == null) {
            return null;
        }
        return integer.intValue() != 0 ? Integer.toString(integer.intValue()) : "";
    }

    @Nullable
    public String getShortFocalLengthDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_SHORT_FOCAL_LENGTH);
        if (integer == null) {
            return null;
        }
        String focalUnitsPerMillimetreDescription = getFocalUnitsPerMillimetreDescription();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(integer.intValue()));
        stringBuilder.append(" ");
        stringBuilder.append(focalUnitsPerMillimetreDescription);
        return stringBuilder.toString();
    }

    @Nullable
    public String getLongFocalLengthDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_LONG_FOCAL_LENGTH);
        if (integer == null) {
            return null;
        }
        String focalUnitsPerMillimetreDescription = getFocalUnitsPerMillimetreDescription();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(integer.intValue()));
        stringBuilder.append(" ");
        stringBuilder.append(focalUnitsPerMillimetreDescription);
        return stringBuilder.toString();
    }

    @Nullable
    public String getExposureModeDescription() {
        return getIndexedDescription(CameraSettings.TAG_EXPOSURE_MODE, "Easy shooting", "Program", "Tv-priority", "Av-priority", "Manual", "A-DEP");
    }

    @Nullable
    public String getLensTypeDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_LENS_TYPE);
        if (integer == null) {
            return null;
        }
        String str;
        if (_lensTypeById.containsKey(integer)) {
            str = (String) _lensTypeById.get(integer);
        } else {
            str = String.format("Unknown (%d)", new Object[]{integer});
        }
        return str;
    }

    @Nullable
    public String getMaxApertureDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_MAX_APERTURE);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() <= 512) {
            return TagDescriptor.getFStopDescription(Math.exp((decodeCanonEv(integer.intValue()) * Math.log(2.0d)) / 2.0d));
        }
        return String.format("Unknown (%d)", new Object[]{integer});
    }

    @Nullable
    public String getMinApertureDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_MIN_APERTURE);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() <= 512) {
            return TagDescriptor.getFStopDescription(Math.exp((decodeCanonEv(integer.intValue()) * Math.log(2.0d)) / 2.0d));
        }
        return String.format("Unknown (%d)", new Object[]{integer});
    }

    @Nullable
    public String getAfPointSelectedDescription() {
        return getIndexedDescription(CameraSettings.TAG_AF_POINT_SELECTED, 12288, "None (MF)", "Auto selected", "Right", "Centre", "Left");
    }

    @Nullable
    public String getMeteringModeDescription() {
        return getIndexedDescription(CameraSettings.TAG_METERING_MODE, 3, "Evaluative", "Partial", "Centre weighted");
    }

    @Nullable
    public String getIsoDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_ISO);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder;
        if ((integer.intValue() & 16384) != 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(integer.intValue() & -16385);
            return stringBuilder.toString();
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Not specified (see ISOSpeedRatings tag)";
        }
        switch (intValue) {
            case 15:
                return "Auto";
            case 16:
                return "50";
            case 17:
                return "100";
            case 18:
                return "200";
            case 19:
                return "400";
            default:
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown (");
                stringBuilder.append(integer);
                stringBuilder.append(")");
                return stringBuilder.toString();
        }
    }

    @Nullable
    public String getSharpnessDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_SHARPNESS);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Normal";
        }
        if (intValue == 1) {
            return "High";
        }
        if (intValue == 65535) {
            return "Low";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getSaturationDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_SATURATION);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Normal";
        }
        if (intValue == 1) {
            return "High";
        }
        if (intValue == 65535) {
            return "Low";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getContrastDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_CONTRAST);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Normal";
        }
        if (intValue == 1) {
            return "High";
        }
        if (intValue == 65535) {
            return "Low";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getEasyShootingModeDescription() {
        return getIndexedDescription(CameraSettings.TAG_EASY_SHOOTING_MODE, "Full auto", "Manual", "Landscape", "Fast shutter", "Slow shutter", "Night", "B&W", "Sepia", "Portrait", "Sports", "Macro / Closeup", "Pan focus");
    }

    @Nullable
    public String getImageSizeDescription() {
        return getIndexedDescription(CameraSettings.TAG_IMAGE_SIZE, "Large", "Medium", "Small");
    }

    @Nullable
    public String getFocusMode1Description() {
        return getIndexedDescription(CameraSettings.TAG_FOCUS_MODE_1, "One-shot", "AI Servo", "AI Focus", "Manual Focus", "Single", "Continuous", "Manual Focus");
    }

    @Nullable
    public String getContinuousDriveModeDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_CONTINUOUS_DRIVE_MODE);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_SELF_TIMER_DELAY);
            if (integer != null) {
                return integer.intValue() == 0 ? "Single shot" : "Single shot with self-timer";
            }
        } else if (intValue != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown (");
            stringBuilder.append(integer);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        return "Continuous";
    }

    @Nullable
    public String getFlashModeDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_FLASH_MODE);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 16) {
            return "External flash";
        }
        switch (intValue) {
            case 0:
                return "No flash fired";
            case 1:
                return "Auto";
            case 2:
                return "On";
            case 3:
                return "Red-eye reduction";
            case 4:
                return "Slow-synchro";
            case 5:
                return "Auto and red-eye reduction";
            case 6:
                return "On and red-eye reduction";
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown (");
                stringBuilder.append(integer);
                stringBuilder.append(")");
                return stringBuilder.toString();
        }
    }

    @Nullable
    public String getSelfTimerDelayDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_SELF_TIMER_DELAY);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() == 0) {
            return "Self timer not used";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(decimalFormat.format(((double) integer.intValue()) * 0.1d));
        stringBuilder.append(" sec");
        return stringBuilder.toString();
    }

    @Nullable
    public String getMacroModeDescription() {
        return getIndexedDescription(CameraSettings.TAG_MACRO_MODE, 1, "Macro", "Normal");
    }

    @Nullable
    public String getQualityDescription() {
        return getIndexedDescription(CameraSettings.TAG_QUALITY, 2, "Normal", "Fine", null, "Superfine");
    }

    @Nullable
    public String getDigitalZoomDescription() {
        return getIndexedDescription(CameraSettings.TAG_DIGITAL_ZOOM, "No digital zoom", "2x", "4x");
    }

    @Nullable
    public String getRecordModeDescription() {
        return getIndexedDescription(CameraSettings.TAG_RECORD_MODE, 1, "JPEG", "CRW+THM", "AVI+THM", "TIF", "TIF+JPEG", "CR2", "CR2+JPEG", null, "MOV", "MP4");
    }

    @Nullable
    public String getFocusTypeDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_FOCUS_TYPE);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Manual";
        }
        if (intValue == 1) {
            return "Auto";
        }
        if (intValue == 3) {
            return "Close-up (Macro)";
        }
        if (intValue == 8) {
            return "Locked (Pan Mode)";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getFlashActivityDescription() {
        return getIndexedDescription(CameraSettings.TAG_FLASH_ACTIVITY, "Flash did not fire", "Flash fired");
    }

    @Nullable
    public String getFocusContinuousDescription() {
        return getIndexedDescription(CameraSettings.TAG_FOCUS_CONTINUOUS, 0, "Single", "Continuous", null, null, null, null, null, null, "Manual");
    }

    @Nullable
    public String getAESettingDescription() {
        return getIndexedDescription(CameraSettings.TAG_AE_SETTING, 0, "Normal AE", "Exposure Compensation", "AE Lock", "AE Lock + Exposure Comp.", "No AE");
    }

    @Nullable
    public String getDisplayApertureDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_DISPLAY_APERTURE);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() == 65535) {
            return integer.toString();
        }
        return TagDescriptor.getFStopDescription((double) (((float) integer.intValue()) / 10.0f));
    }

    @Nullable
    public String getSpotMeteringModeDescription() {
        return getIndexedDescription(CameraSettings.TAG_SPOT_METERING_MODE, 0, "Center", "AF Point");
    }

    @Nullable
    public String getPhotoEffectDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_PHOTO_EFFECT);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 100) {
            return "My Color Data";
        }
        switch (intValue) {
            case 0:
                return "Off";
            case 1:
                return "Vivid";
            case 2:
                return "Neutral";
            case 3:
                return "Smooth";
            case 4:
                return "Sepia";
            case 5:
                return "B&W";
            case 6:
                return "Custom";
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown (");
                stringBuilder.append(integer);
                stringBuilder.append(")");
                return stringBuilder.toString();
        }
    }

    @Nullable
    public String getManualFlashOutputDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_MANUAL_FLASH_OUTPUT);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        String str = "n/a";
        if (intValue == 0) {
            return str;
        }
        if (intValue == OlympusCameraSettingsMakernoteDirectory.TagWhiteBalance2) {
            return "Full";
        }
        if (intValue == OlympusCameraSettingsMakernoteDirectory.TagWhiteBalanceBracket) {
            return "Medium";
        }
        if (intValue == OlympusCameraSettingsMakernoteDirectory.TagModifiedSaturation) {
            return "Low";
        }
        if (intValue == 32767) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getColorToneDescription() {
        Integer integer = ((CanonMakernoteDirectory) this._directory).getInteger(CameraSettings.TAG_COLOR_TONE);
        if (integer == null) {
            return null;
        }
        return integer.intValue() == 32767 ? "n/a" : integer.toString();
    }

    @Nullable
    public String getSRawQualityDescription() {
        return getIndexedDescription(CameraSettings.TAG_SRAW_QUALITY, 0, "n/a", "sRAW1 (mRAW)", "sRAW2 (sRAW)");
    }

    static {
        _lensTypeById.put(Integer.valueOf(1), "Canon EF 50mm f/1.8");
        _lensTypeById.put(Integer.valueOf(2), "Canon EF 28mm f/2.8");
        _lensTypeById.put(Integer.valueOf(3), "Canon EF 135mm f/2.8 Soft");
        _lensTypeById.put(Integer.valueOf(4), "Canon EF 35-105mm f/3.5-4.5 or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(5), "Canon EF 35-70mm f/3.5-4.5");
        _lensTypeById.put(Integer.valueOf(6), "Canon EF 28-70mm f/3.5-4.5 or Sigma or Tokina Lens");
        _lensTypeById.put(Integer.valueOf(7), "Canon EF 100-300mm f/5.6L");
        _lensTypeById.put(Integer.valueOf(8), "Canon EF 100-300mm f/5.6 or Sigma or Tokina Lens");
        _lensTypeById.put(Integer.valueOf(9), "Canon EF 70-210mm f/4");
        _lensTypeById.put(Integer.valueOf(10), "Canon EF 50mm f/2.5 Macro or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(11), "Canon EF 35mm f/2");
        _lensTypeById.put(Integer.valueOf(13), "Canon EF 15mm f/2.8 Fisheye");
        _lensTypeById.put(Integer.valueOf(14), "Canon EF 50-200mm f/3.5-4.5L");
        _lensTypeById.put(Integer.valueOf(15), "Canon EF 50-200mm f/3.5-4.5");
        _lensTypeById.put(Integer.valueOf(16), "Canon EF 35-135mm f/3.5-4.5");
        _lensTypeById.put(Integer.valueOf(17), "Canon EF 35-70mm f/3.5-4.5A");
        _lensTypeById.put(Integer.valueOf(18), "Canon EF 28-70mm f/3.5-4.5");
        _lensTypeById.put(Integer.valueOf(20), "Canon EF 100-200mm f/4.5A");
        _lensTypeById.put(Integer.valueOf(21), "Canon EF 80-200mm f/2.8L");
        _lensTypeById.put(Integer.valueOf(22), "Canon EF 20-35mm f/2.8L or Tokina Lens");
        _lensTypeById.put(Integer.valueOf(23), "Canon EF 35-105mm f/3.5-4.5");
        String str = "Canon EF 35-80mm f/4-5.6 Power Zoom";
        _lensTypeById.put(Integer.valueOf(24), str);
        _lensTypeById.put(Integer.valueOf(25), str);
        _lensTypeById.put(Integer.valueOf(26), "Canon EF 100mm f/2.8 Macro or Other Lens");
        str = "Canon EF 35-80mm f/4-5.6";
        _lensTypeById.put(Integer.valueOf(27), str);
        _lensTypeById.put(Integer.valueOf(28), "Canon EF 80-200mm f/4.5-5.6 or Tamron Lens");
        _lensTypeById.put(Integer.valueOf(29), "Canon EF 50mm f/1.8 II");
        _lensTypeById.put(Integer.valueOf(30), "Canon EF 35-105mm f/4.5-5.6");
        _lensTypeById.put(Integer.valueOf(31), "Canon EF 75-300mm f/4-5.6 or Tamron Lens");
        _lensTypeById.put(Integer.valueOf(32), "Canon EF 24mm f/2.8 or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(33), "Voigtlander or Carl Zeiss Lens");
        _lensTypeById.put(Integer.valueOf(35), str);
        _lensTypeById.put(Integer.valueOf(36), "Canon EF 38-76mm f/4.5-5.6");
        _lensTypeById.put(Integer.valueOf(37), "Canon EF 35-80mm f/4-5.6 or Tamron Lens");
        _lensTypeById.put(Integer.valueOf(38), "Canon EF 80-200mm f/4.5-5.6");
        _lensTypeById.put(Integer.valueOf(39), "Canon EF 75-300mm f/4-5.6");
        _lensTypeById.put(Integer.valueOf(40), "Canon EF 28-80mm f/3.5-5.6");
        str = "Canon EF 28-90mm f/4-5.6";
        _lensTypeById.put(Integer.valueOf(41), str);
        _lensTypeById.put(Integer.valueOf(42), "Canon EF 28-200mm f/3.5-5.6 or Tamron Lens");
        _lensTypeById.put(Integer.valueOf(43), "Canon EF 28-105mm f/4-5.6");
        _lensTypeById.put(Integer.valueOf(44), "Canon EF 90-300mm f/4.5-5.6");
        _lensTypeById.put(Integer.valueOf(45), "Canon EF-S 18-55mm f/3.5-5.6 [II]");
        _lensTypeById.put(Integer.valueOf(46), str);
        _lensTypeById.put(Integer.valueOf(47), "Zeiss Milvus 35mm f/2 or 50mm f/2");
        _lensTypeById.put(Integer.valueOf(48), "Canon EF-S 18-55mm f/3.5-5.6 IS");
        _lensTypeById.put(Integer.valueOf(49), "Canon EF-S 55-250mm f/4-5.6 IS");
        _lensTypeById.put(Integer.valueOf(50), "Canon EF-S 18-200mm f/3.5-5.6 IS");
        _lensTypeById.put(Integer.valueOf(51), "Canon EF-S 18-135mm f/3.5-5.6 IS");
        _lensTypeById.put(Integer.valueOf(52), "Canon EF-S 18-55mm f/3.5-5.6 IS II");
        _lensTypeById.put(Integer.valueOf(53), "Canon EF-S 18-55mm f/3.5-5.6 III");
        _lensTypeById.put(Integer.valueOf(54), "Canon EF-S 55-250mm f/4-5.6 IS II");
        _lensTypeById.put(Integer.valueOf(94), "Canon TS-E 17mm f/4L");
        _lensTypeById.put(Integer.valueOf(95), "Canon TS-E 24.0mm f/3.5 L II");
        _lensTypeById.put(Integer.valueOf(124), "Canon MP-E 65mm f/2.8 1-5x Macro Photo");
        _lensTypeById.put(Integer.valueOf(125), "Canon TS-E 24mm f/3.5L");
        _lensTypeById.put(Integer.valueOf(126), "Canon TS-E 45mm f/2.8");
        _lensTypeById.put(Integer.valueOf(127), "Canon TS-E 90mm f/2.8");
        str = "Canon EF 300mm f/2.8L";
        _lensTypeById.put(Integer.valueOf(129), str);
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_ADAPTER), "Canon EF 50mm f/1.0L");
        _lensTypeById.put(Integer.valueOf(131), "Canon EF 28-80mm f/2.8-4L or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_LENS), "Canon EF 1200mm f/5.6L");
        String str2 = "Canon EF 600mm f/4L IS";
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM), str2);
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_FLASH_USED), "Canon EF 200mm f/1.8L");
        _lensTypeById.put(Integer.valueOf(136), str);
        _lensTypeById.put(Integer.valueOf(137), "Canon EF 85mm f/1.2L or Sigma or Tamron Lens");
        _lensTypeById.put(Integer.valueOf(138), "Canon EF 28-80mm f/2.8-4L");
        str = "Canon EF 400mm f/2.8L";
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_LENS_STOPS), str);
        String str3 = "Canon EF 500mm f/4.5L";
        _lensTypeById.put(Integer.valueOf(140), str3);
        _lensTypeById.put(Integer.valueOf(141), str3);
        _lensTypeById.put(Integer.valueOf(142), "Canon EF 300mm f/2.8L IS");
        _lensTypeById.put(Integer.valueOf(143), "Canon EF 500mm f/4L IS or Sigma Lens");
        str3 = "Canon EF 35-135mm f/4-5.6 USM";
        _lensTypeById.put(Integer.valueOf(144), str3);
        _lensTypeById.put(Integer.valueOf(145), "Canon EF 100-300mm f/4.5-5.6 USM");
        _lensTypeById.put(Integer.valueOf(146), "Canon EF 70-210mm f/3.5-4.5 USM");
        _lensTypeById.put(Integer.valueOf(147), str3);
        str3 = "Canon EF 28-80mm f/3.5-5.6 USM";
        _lensTypeById.put(Integer.valueOf(148), str3);
        _lensTypeById.put(Integer.valueOf(149), "Canon EF 100mm f/2 USM");
        _lensTypeById.put(Integer.valueOf(150), "Canon EF 14mm f/2.8L or Sigma Lens");
        String str4 = "Canon EF 200mm f/2.8L";
        _lensTypeById.put(Integer.valueOf(151), str4);
        _lensTypeById.put(Integer.valueOf(152), "Canon EF 300mm f/4L IS or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(153), "Canon EF 35-350mm f/3.5-5.6L or Sigma or Tamron Lens");
        _lensTypeById.put(Integer.valueOf(154), "Canon EF 20mm f/2.8 USM or Zeiss Lens");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_UNKNOWN_10), "Canon EF 85mm f/1.8 USM");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_SCENE_ASSIST), "Canon EF 28-105mm f/3.5-4.5 USM or Tamron Lens");
        _lensTypeById.put(Integer.valueOf(160), "Canon EF 20-35mm f/3.5-4.5 USM or Tamron or Tokina Lens");
        _lensTypeById.put(Integer.valueOf(CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE), "Canon EF 28-70mm f/2.8L or Sigma or Tamron Lens");
        _lensTypeById.put(Integer.valueOf(162), str4);
        str4 = "Canon EF 300mm f/4L";
        _lensTypeById.put(Integer.valueOf(163), str4);
        _lensTypeById.put(Integer.valueOf(164), "Canon EF 400mm f/5.6L");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_IMAGE_COUNT), "Canon EF 70-200mm f/2.8 L");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_DELETED_IMAGE_COUNT), "Canon EF 70-200mm f/2.8 L + 1.4x");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER), "Canon EF 70-200mm f/2.8 L + 2x");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_FLASH_INFO), "Canon EF 28mm f/1.8 USM or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(169), "Canon EF 17-35mm f/2.8L or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(170), "Canon EF 200mm f/2.8L II");
        _lensTypeById.put(Integer.valueOf(171), str4);
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION), "Canon EF 400mm f/5.6L or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_AF_RESPONSE), "Canon EF 180mm Macro f/3.5L or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(174), "Canon EF 135mm f/2L or Other Lens");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_UNKNOWN_30), str);
        _lensTypeById.put(Integer.valueOf(176), "Canon EF 24-85mm f/3.5-4.5 USM");
        _lensTypeById.put(Integer.valueOf(177), "Canon EF 300mm f/4L IS");
        _lensTypeById.put(Integer.valueOf(178), "Canon EF 28-135mm f/3.5-5.6 IS");
        _lensTypeById.put(Integer.valueOf(179), "Canon EF 24mm f/1.4L");
        _lensTypeById.put(Integer.valueOf(180), "Canon EF 35mm f/1.4L or Other Lens");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_UNKNOWN_48), "Canon EF 100-400mm f/4.5-5.6L IS + 1.4x or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(182), "Canon EF 100-400mm f/4.5-5.6L IS + 2x or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_AF_INFO_2), "Canon EF 100-400mm f/4.5-5.6L IS or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_FILE_INFO), "Canon EF 400mm f/2.8L + 2x");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_AF_TUNE), str2);
        _lensTypeById.put(Integer.valueOf(186), "Canon EF 70-200mm f/4L");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_UNKNOWN_49), "Canon EF 70-200mm f/4L + 1.4x");
        _lensTypeById.put(Integer.valueOf(188), "Canon EF 70-200mm f/4L + 2x");
        _lensTypeById.put(Integer.valueOf(NikonType2MakernoteDirectory.TAG_UNKNOWN_50), "Canon EF 70-200mm f/4L + 2.8x");
        _lensTypeById.put(Integer.valueOf(190), "Canon EF 100mm f/2.8 Macro USM");
        _lensTypeById.put(Integer.valueOf(191), "Canon EF 400mm f/4 DO IS");
        _lensTypeById.put(Integer.valueOf(193), "Canon EF 35-80mm f/4-5.6 USM");
        _lensTypeById.put(Integer.valueOf(194), "Canon EF 80-200mm f/4.5-5.6 USM");
        _lensTypeById.put(Integer.valueOf(195), "Canon EF 35-105mm f/4.5-5.6 USM");
        str = "Canon EF 75-300mm f/4-5.6 USM";
        _lensTypeById.put(Integer.valueOf(196), str);
        _lensTypeById.put(Integer.valueOf(197), "Canon EF 75-300mm f/4-5.6 IS USM");
        _lensTypeById.put(Integer.valueOf(198), "Canon EF 50mm f/1.4 USM or Zeiss Lens");
        _lensTypeById.put(Integer.valueOf(199), str3);
        _lensTypeById.put(Integer.valueOf(LogSeverity.INFO_VALUE), str);
        _lensTypeById.put(Integer.valueOf(XMPError.BADXML), str3);
        _lensTypeById.put(Integer.valueOf(XMPError.BADRDF), "Canon EF 28-80mm f/3.5-5.6 USM IV");
        _lensTypeById.put(Integer.valueOf(208), "Canon EF 22-55mm f/4-5.6 USM");
        _lensTypeById.put(Integer.valueOf(209), "Canon EF 55-200mm f/4.5-5.6");
        _lensTypeById.put(Integer.valueOf(210), "Canon EF 28-90mm f/4-5.6 USM");
        _lensTypeById.put(Integer.valueOf(211), "Canon EF 28-200mm f/3.5-5.6 USM");
        _lensTypeById.put(Integer.valueOf(212), "Canon EF 28-105mm f/4-5.6 USM");
        _lensTypeById.put(Integer.valueOf(213), "Canon EF 90-300mm f/4.5-5.6 USM or Tamron Lens");
        _lensTypeById.put(Integer.valueOf(214), "Canon EF-S 18-55mm f/3.5-5.6 USM");
        _lensTypeById.put(Integer.valueOf(JfifUtil.MARKER_RST7), "Canon EF 55-200mm f/4.5-5.6 II USM");
        _lensTypeById.put(Integer.valueOf(JfifUtil.MARKER_EOI), "Tamron AF 18-270mm f/3.5-6.3 Di II VC PZD");
        _lensTypeById.put(Integer.valueOf(CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY), "Canon EF 70-200mm f/2.8L IS");
        _lensTypeById.put(Integer.valueOf(JfifUtil.MARKER_APP1), "Canon EF 70-200mm f/2.8L IS + 1.4x");
        _lensTypeById.put(Integer.valueOf(226), "Canon EF 70-200mm f/2.8L IS + 2x");
        _lensTypeById.put(Integer.valueOf(227), "Canon EF 70-200mm f/2.8L IS + 2.8x");
        _lensTypeById.put(Integer.valueOf(228), "Canon EF 28-105mm f/3.5-4.5 USM");
        _lensTypeById.put(Integer.valueOf(229), "Canon EF 16-35mm f/2.8L");
        _lensTypeById.put(Integer.valueOf(230), "Canon EF 24-70mm f/2.8L");
        _lensTypeById.put(Integer.valueOf(231), "Canon EF 17-40mm f/4L");
        _lensTypeById.put(Integer.valueOf(232), "Canon EF 70-300mm f/4.5-5.6 DO IS USM");
        _lensTypeById.put(Integer.valueOf(233), "Canon EF 28-300mm f/3.5-5.6L IS");
        _lensTypeById.put(Integer.valueOf(234), "Canon EF-S 17-85mm f/4-5.6 IS USM or Tokina Lens");
        _lensTypeById.put(Integer.valueOf(235), "Canon EF-S 10-22mm f/3.5-4.5 USM");
        _lensTypeById.put(Integer.valueOf(236), "Canon EF-S 60mm f/2.8 Macro USM");
        _lensTypeById.put(Integer.valueOf(237), "Canon EF 24-105mm f/4L IS");
        _lensTypeById.put(Integer.valueOf(238), "Canon EF 70-300mm f/4-5.6 IS USM");
        _lensTypeById.put(Integer.valueOf(239), "Canon EF 85mm f/1.2L II");
        _lensTypeById.put(Integer.valueOf(240), "Canon EF-S 17-55mm f/2.8 IS USM");
        _lensTypeById.put(Integer.valueOf(241), "Canon EF 50mm f/1.2L");
        _lensTypeById.put(Integer.valueOf(242), "Canon EF 70-200mm f/4L IS");
        _lensTypeById.put(Integer.valueOf(243), "Canon EF 70-200mm f/4L IS + 1.4x");
        _lensTypeById.put(Integer.valueOf(244), "Canon EF 70-200mm f/4L IS + 2x");
        _lensTypeById.put(Integer.valueOf(245), "Canon EF 70-200mm f/4L IS + 2.8x");
        _lensTypeById.put(Integer.valueOf(246), "Canon EF 16-35mm f/2.8L II");
        _lensTypeById.put(Integer.valueOf(247), "Canon EF 14mm f/2.8L II USM");
        _lensTypeById.put(Integer.valueOf(248), "Canon EF 200mm f/2L IS or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(249), "Canon EF 800mm f/5.6L IS");
        _lensTypeById.put(Integer.valueOf(ExponentialBackoffSender.RND_MAX), "Canon EF 24mm f/1.4L II or Sigma Lens");
        _lensTypeById.put(Integer.valueOf(251), "Canon EF 70-200mm f/2.8L IS II USM");
        _lensTypeById.put(Integer.valueOf(252), "Canon EF 70-200mm f/2.8L IS II USM + 1.4x");
        _lensTypeById.put(Integer.valueOf(253), "Canon EF 70-200mm f/2.8L IS II USM + 2x");
        _lensTypeById.put(Integer.valueOf(ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE), "Canon EF 100mm f/2.8L Macro IS USM");
        _lensTypeById.put(Integer.valueOf(255), "Sigma 24-105mm f/4 DG OS HSM | A or Other Sigma Lens");
        _lensTypeById.put(Integer.valueOf(488), "Canon EF-S 15-85mm f/3.5-5.6 IS USM");
        _lensTypeById.put(Integer.valueOf(489), "Canon EF 70-300mm f/4-5.6L IS USM");
        _lensTypeById.put(Integer.valueOf(490), "Canon EF 8-15mm f/4L Fisheye USM");
        _lensTypeById.put(Integer.valueOf(491), "Canon EF 300mm f/2.8L IS II USM");
        _lensTypeById.put(Integer.valueOf(492), "Canon EF 400mm f/2.8L IS II USM");
        _lensTypeById.put(Integer.valueOf(493), "Canon EF 500mm f/4L IS II USM or EF 24-105mm f4L IS USM");
        _lensTypeById.put(Integer.valueOf(494), "Canon EF 600mm f/4.0L IS II USM");
        _lensTypeById.put(Integer.valueOf(495), "Canon EF 24-70mm f/2.8L II USM");
        _lensTypeById.put(Integer.valueOf(496), "Canon EF 200-400mm f/4L IS USM");
        _lensTypeById.put(Integer.valueOf(499), "Canon EF 200-400mm f/4L IS USM + 1.4x");
        _lensTypeById.put(Integer.valueOf(502), "Canon EF 28mm f/2.8 IS USM");
        _lensTypeById.put(Integer.valueOf(503), "Canon EF 24mm f/2.8 IS USM");
        _lensTypeById.put(Integer.valueOf(504), "Canon EF 24-70mm f/4L IS USM");
        _lensTypeById.put(Integer.valueOf(505), "Canon EF 35mm f/2 IS USM");
        _lensTypeById.put(Integer.valueOf(506), "Canon EF 400mm f/4 DO IS II USM");
        _lensTypeById.put(Integer.valueOf(507), "Canon EF 16-35mm f/4L IS USM");
        _lensTypeById.put(Integer.valueOf(508), "Canon EF 11-24mm f/4L USM");
        _lensTypeById.put(Integer.valueOf(747), "Canon EF 100-400mm f/4.5-5.6L IS II USM");
        _lensTypeById.put(Integer.valueOf(750), "Canon EF 35mm f/1.4L II USM");
        _lensTypeById.put(Integer.valueOf(OlympusMakernoteDirectory.TAG_OLYMPUS_IMAGE_WIDTH), "Canon EF-S 18-135mm f/3.5-5.6 IS STM");
        _lensTypeById.put(Integer.valueOf(OlympusMakernoteDirectory.TAG_OLYMPUS_IMAGE_HEIGHT), "Canon EF-M 18-55mm f/3.5-5.6 IS STM or Tamron Lens");
        _lensTypeById.put(Integer.valueOf(4144), "Canon EF 40mm f/2.8 STM");
        _lensTypeById.put(Integer.valueOf(4145), "Canon EF-M 22mm f/2 STM");
        _lensTypeById.put(Integer.valueOf(4146), "Canon EF-S 18-55mm f/3.5-5.6 IS STM");
        _lensTypeById.put(Integer.valueOf(4147), "Canon EF-M 11-22mm f/4-5.6 IS STM");
        _lensTypeById.put(Integer.valueOf(4148), "Canon EF-S 55-250mm f/4-5.6 IS STM");
        _lensTypeById.put(Integer.valueOf(OlympusMakernoteDirectory.TAG_PREVIEW_IMAGE_VALID), "Canon EF-M 55-200mm f/4.5-6.3 IS STM");
        _lensTypeById.put(Integer.valueOf(OlympusMakernoteDirectory.TAG_PREVIEW_IMAGE_START), "Canon EF-S 10-18mm f/4.5-5.6 IS STM");
        _lensTypeById.put(Integer.valueOf(OlympusMakernoteDirectory.TAG_AF_RESULT), "Canon EF 24-105mm f/3.5-5.6 IS STM");
        _lensTypeById.put(Integer.valueOf(OlympusMakernoteDirectory.TAG_CCD_SCAN_MODE), "Canon EF-M 15-45mm f/3.5-6.3 IS STM");
        _lensTypeById.put(Integer.valueOf(OlympusMakernoteDirectory.TAG_NOISE_REDUCTION), "Canon EF-S 24mm f/2.8 STM");
        _lensTypeById.put(Integer.valueOf(OlympusMakernoteDirectory.TAG_NEAR_LENS_STEP), "Canon EF 50mm f/1.8 STM");
        _lensTypeById.put(Integer.valueOf(36912), "Canon EF-S 18-135mm f/3.5-5.6 IS USM");
        _lensTypeById.put(Integer.valueOf(65535), "N/A");
    }
}
