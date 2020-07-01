package com.drew.metadata.exif.makernotes;

import com.drew.lang.Rational;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;
import java.text.DecimalFormat;

public class NikonType2MakernoteDescriptor extends TagDescriptor<NikonType2MakernoteDirectory> {
    public NikonType2MakernoteDescriptor(@NotNull NikonType2MakernoteDirectory nikonType2MakernoteDirectory) {
        super(nikonType2MakernoteDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        if (i == 1) {
            return getFirmwareVersionDescription();
        }
        if (i == 2) {
            return getIsoSettingDescription();
        }
        if (i == 13) {
            return getProgramShiftDescription();
        }
        if (i == 14) {
            return getExposureDifferenceDescription();
        }
        if (i == 18) {
            return getAutoFlashCompensationDescription();
        }
        if (i == 28) {
            return getExposureTuningDescription();
        }
        if (i == 30) {
            return getColorSpaceDescription();
        }
        if (i == 34) {
            return getActiveDLightingDescription();
        }
        if (i == 42) {
            return getVignetteControlDescription();
        }
        if (i == NikonType2MakernoteDirectory.TAG_LENS_STOPS) {
            return getLensStopsDescription();
        }
        if (i == 141) {
            return getColorModeDescription();
        }
        if (i == 177) {
            return getHighISONoiseReductionDescription();
        }
        if (i == 182) {
            return getPowerUpTimeDescription();
        }
        if (i == 23) {
            return getFlashExposureCompensationDescription();
        }
        if (i == 24) {
            return getFlashBracketCompensationDescription();
        }
        if (i == 131) {
            return getLensTypeDescription();
        }
        if (i == NikonType2MakernoteDirectory.TAG_LENS) {
            return getLensDescription();
        }
        if (i == 146) {
            return getHueAdjustmentDescription();
        }
        if (i == 147) {
            return getNEFCompressionDescription();
        }
        switch (i) {
            case NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM /*134*/:
                return getDigitalZoomDescription();
            case NikonType2MakernoteDirectory.TAG_FLASH_USED /*135*/:
                return getFlashUsedDescription();
            case 136:
                return getAutoFocusPositionDescription();
            case 137:
                return getShootingModeDescription();
            default:
                return super.getDescription(i);
        }
    }

    @Nullable
    public String getPowerUpTimeDescription() {
        return getEpochTimeDescription(182);
    }

    @Nullable
    public String getHighISONoiseReductionDescription() {
        return getIndexedDescription(177, "Off", "Minimal", "Low", null, "Normal", null, "High");
    }

    @Nullable
    public String getFlashUsedDescription() {
        return getIndexedDescription(NikonType2MakernoteDirectory.TAG_FLASH_USED, "Flash Not Used", "Manual Flash", null, "Flash Not Ready", null, null, null, "External Flash", "Fired, Commander Mode", "Fired, TTL Mode");
    }

    @Nullable
    public String getNEFCompressionDescription() {
        return getIndexedDescription(147, 1, "Lossy (Type 1)", null, "Uncompressed", null, null, null, "Lossless", "Lossy (Type 2)");
    }

    @Nullable
    public String getShootingModeDescription() {
        return getBitFlagDescription(137, new String[]{"Single Frame", "Continuous"}, "Delay", null, "PC Control", "Exposure Bracketing", "Auto ISO", "White-Balance Bracketing", "IR Control");
    }

    @Nullable
    public String getLensTypeDescription() {
        return getBitFlagDescription(131, new String[]{"AF", "MF"}, "D", "G", "VR");
    }

    @Nullable
    public String getColorSpaceDescription() {
        return getIndexedDescription(30, 1, "sRGB", "Adobe RGB");
    }

    @Nullable
    public String getActiveDLightingDescription() {
        Integer integer = ((NikonType2MakernoteDirectory) this._directory).getInteger(34);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Off";
        }
        if (intValue == 1) {
            return "Light";
        }
        if (intValue == 3) {
            return "Normal";
        }
        if (intValue == 5) {
            return "High";
        }
        if (intValue == 7) {
            return "Extra High";
        }
        if (intValue == 65535) {
            return "Auto";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getVignetteControlDescription() {
        Integer integer = ((NikonType2MakernoteDirectory) this._directory).getInteger(42);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Off";
        }
        if (intValue == 1) {
            return "Low";
        }
        if (intValue == 3) {
            return "Normal";
        }
        if (intValue == 5) {
            return "High";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getAutoFocusPositionDescription() {
        int[] intArray = ((NikonType2MakernoteDirectory) this._directory).getIntArray(136);
        if (intArray == null) {
            return null;
        }
        String str = ")";
        String str2 = "Unknown (";
        if (intArray.length == 4 && intArray[0] == 0 && intArray[2] == 0 && intArray[3] == 0) {
            int i = intArray[1];
            if (i == 0) {
                return "Centre";
            }
            if (i == 1) {
                return "Top";
            }
            if (i == 2) {
                return "Bottom";
            }
            if (i == 3) {
                return "Left";
            }
            if (i == 4) {
                return "Right";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(intArray[1]);
            stringBuilder.append(str);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str2);
        stringBuilder2.append(((NikonType2MakernoteDirectory) this._directory).getString(136));
        stringBuilder2.append(str);
        return stringBuilder2.toString();
    }

    @Nullable
    public String getDigitalZoomDescription() {
        Rational rational = ((NikonType2MakernoteDirectory) this._directory).getRational(NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM);
        if (rational == null) {
            return null;
        }
        String str;
        if (rational.intValue() == 1) {
            str = "No digital zoom";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(rational.toSimpleString(true));
            stringBuilder.append("x digital zoom");
            str = stringBuilder.toString();
        }
        return str;
    }

    @Nullable
    public String getProgramShiftDescription() {
        return getEVDescription(13);
    }

    @Nullable
    public String getExposureDifferenceDescription() {
        return getEVDescription(14);
    }

    @Nullable
    public String getAutoFlashCompensationDescription() {
        return getEVDescription(18);
    }

    @Nullable
    public String getFlashExposureCompensationDescription() {
        return getEVDescription(23);
    }

    @Nullable
    public String getFlashBracketCompensationDescription() {
        return getEVDescription(24);
    }

    @Nullable
    public String getExposureTuningDescription() {
        return getEVDescription(28);
    }

    @Nullable
    public String getLensStopsDescription() {
        return getEVDescription(NikonType2MakernoteDirectory.TAG_LENS_STOPS);
    }

    @Nullable
    private String getEVDescription(int i) {
        int[] intArray = ((NikonType2MakernoteDirectory) this._directory).getIntArray(i);
        if (intArray == null || intArray.length < 2 || intArray.length < 3 || intArray[2] == 0) {
            return null;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        double d = ((double) (intArray[0] * intArray[1])) / ((double) intArray[2]);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(decimalFormat.format(d));
        stringBuilder.append(" EV");
        return stringBuilder.toString();
    }

    @Nullable
    public String getIsoSettingDescription() {
        int[] intArray = ((NikonType2MakernoteDirectory) this._directory).getIntArray(2);
        if (intArray == null) {
            return null;
        }
        if (intArray[0] != 0 || intArray[1] == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown (");
            stringBuilder.append(((NikonType2MakernoteDirectory) this._directory).getString(2));
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("ISO ");
        stringBuilder2.append(intArray[1]);
        return stringBuilder2.toString();
    }

    @Nullable
    public String getLensDescription() {
        return getLensSpecificationDescription(NikonType2MakernoteDirectory.TAG_LENS);
    }

    @Nullable
    public String getLensFocusDistance() {
        int[] decryptedIntArray = ((NikonType2MakernoteDirectory) this._directory).getDecryptedIntArray(152);
        if (decryptedIntArray == null || decryptedIntArray.length < 11) {
            return null;
        }
        return String.format("%.2fm", new Object[]{Double.valueOf(getDistanceInMeters(decryptedIntArray[10]))});
    }

    @Nullable
    public String getHueAdjustmentDescription() {
        return getFormattedString(146, "%s degrees");
    }

    @Nullable
    public String getColorModeDescription() {
        String string = ((NikonType2MakernoteDirectory) this._directory).getString(141);
        if (string == null) {
            return null;
        }
        return string.startsWith("MODE1") ? "Mode I (sRGB)" : string;
    }

    @Nullable
    public String getFirmwareVersionDescription() {
        return getVersionBytesDescription(1, 2);
    }

    private double getDistanceInMeters(int i) {
        if (i < 0) {
            i += 256;
        }
        return Math.pow(10.0d, (double) (((float) i) / 40.0f)) * 0.01d;
    }
}
