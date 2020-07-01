package com.drew.metadata.exif.makernotes;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;

public class OlympusRawDevelopmentMakernoteDescriptor extends TagDescriptor<OlympusRawDevelopmentMakernoteDirectory> {
    public OlympusRawDevelopmentMakernoteDescriptor(@NotNull OlympusRawDevelopmentMakernoteDirectory olympusRawDevelopmentMakernoteDirectory) {
        super(olympusRawDevelopmentMakernoteDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        if (i == 0) {
            return getRawDevVersionDescription();
        }
        switch (i) {
            case 264:
                return getRawDevColorSpaceDescription();
            case 265:
                return getRawDevEngineDescription();
            case 266:
                return getRawDevNoiseReductionDescription();
            case 267:
                return getRawDevEditStatusDescription();
            case 268:
                return getRawDevSettingsDescription();
            default:
                return super.getDescription(i);
        }
    }

    @Nullable
    public String getRawDevVersionDescription() {
        return getVersionBytesDescription(0, 4);
    }

    @Nullable
    public String getRawDevColorSpaceDescription() {
        return getIndexedDescription(264, "sRGB", "Adobe RGB", "Pro Photo RGB");
    }

    @Nullable
    public String getRawDevEngineDescription() {
        return getIndexedDescription(265, "High Speed", "High Function", "Advanced High Speed", "Advanced High Function");
    }

    @Nullable
    public String getRawDevNoiseReductionDescription() {
        Integer integer = ((OlympusRawDevelopmentMakernoteDirectory) this._directory).getInteger(266);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() == 0) {
            return "(none)";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int intValue = integer.intValue();
        if ((intValue & 1) != 0) {
            stringBuilder.append("Noise Reduction, ");
        }
        if (((intValue >> 1) & 1) != 0) {
            stringBuilder.append("Noise Filter, ");
        }
        if (((intValue >> 2) & 1) != 0) {
            stringBuilder.append("Noise Filter (ISO Boost), ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    @Nullable
    public String getRawDevEditStatusDescription() {
        Integer integer = ((OlympusRawDevelopmentMakernoteDirectory) this._directory).getInteger(267);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Original";
        }
        if (intValue == 1) {
            return "Edited (Landscape)";
        }
        if (intValue == 6 || intValue == 8) {
            return "Edited (Portrait)";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getRawDevSettingsDescription() {
        Integer integer = ((OlympusRawDevelopmentMakernoteDirectory) this._directory).getInteger(268);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() == 0) {
            return "(none)";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int intValue = integer.intValue();
        if ((intValue & 1) != 0) {
            stringBuilder.append("WB Color Temp, ");
        }
        if (((intValue >> 1) & 1) != 0) {
            stringBuilder.append("WB Gray Point, ");
        }
        if (((intValue >> 2) & 1) != 0) {
            stringBuilder.append("Saturation, ");
        }
        if (((intValue >> 3) & 1) != 0) {
            stringBuilder.append("Contrast, ");
        }
        if (((intValue >> 4) & 1) != 0) {
            stringBuilder.append("Sharpness, ");
        }
        if (((intValue >> 5) & 1) != 0) {
            stringBuilder.append("Color Space, ");
        }
        if (((intValue >> 6) & 1) != 0) {
            stringBuilder.append("High Function, ");
        }
        if (((intValue >> 7) & 1) != 0) {
            stringBuilder.append("Noise Reduction, ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }
}
