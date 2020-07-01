package com.drew.metadata.exif.makernotes;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;
import java.util.HashMap;

public class OlympusRawDevelopment2MakernoteDescriptor extends TagDescriptor<OlympusRawDevelopment2MakernoteDirectory> {
    private static final HashMap<Integer, String> _filters = new HashMap();

    public OlympusRawDevelopment2MakernoteDescriptor(@NotNull OlympusRawDevelopment2MakernoteDirectory olympusRawDevelopment2MakernoteDirectory) {
        super(olympusRawDevelopment2MakernoteDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        if (i == 0) {
            return getRawDevVersionDescription();
        }
        if (i == 256) {
            return getRawDevExposureBiasValueDescription();
        }
        if (i == 289) {
            return getRawDevArtFilterDescription();
        }
        if (i == 272) {
            return getRawDevPmBwFilterDescription();
        }
        if (i == 273) {
            return getRawDevPmPictureToneDescription();
        }
        switch (i) {
            case 265:
                return getRawDevColorSpaceDescription();
            case 266:
                return getRawDevNoiseReductionDescription();
            case 267:
                return getRawDevEngineDescription();
            case 268:
                return getRawDevPictureModeDescription();
            default:
                return super.getDescription(i);
        }
    }

    @Nullable
    public String getRawDevVersionDescription() {
        return getVersionBytesDescription(0, 4);
    }

    @Nullable
    public String getRawDevExposureBiasValueDescription() {
        return getIndexedDescription(256, 1, "Color Temperature", "Gray Point");
    }

    @Nullable
    public String getRawDevColorSpaceDescription() {
        return getIndexedDescription(265, "sRGB", "Adobe RGB", "Pro Photo RGB");
    }

    @Nullable
    public String getRawDevNoiseReductionDescription() {
        Integer integer = ((OlympusRawDevelopment2MakernoteDirectory) this._directory).getInteger(266);
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
    public String getRawDevEngineDescription() {
        return getIndexedDescription(267, "High Speed", "High Function", "Advanced High Speed", "Advanced High Function");
    }

    @Nullable
    public String getRawDevPictureModeDescription() {
        Integer integer = ((OlympusRawDevelopment2MakernoteDirectory) this._directory).getInteger(268);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 1) {
            return "Vivid";
        }
        if (intValue == 2) {
            return "Natural";
        }
        if (intValue == 3) {
            return "Muted";
        }
        if (intValue == 256) {
            return "Monotone";
        }
        if (intValue == 512) {
            return "Sepia";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getRawDevPmBwFilterDescription() {
        return getIndexedDescription(272, "Neutral", "Yellow", "Orange", "Red", "Green");
    }

    @Nullable
    public String getRawDevPmPictureToneDescription() {
        return getIndexedDescription(273, "Neutral", "Sepia", "Blue", "Purple", "Green");
    }

    @Nullable
    public String getRawDevArtFilterDescription() {
        return getFilterDescription(289);
    }

    @Nullable
    public String getFilterDescription(int i) {
        int[] intArray = ((OlympusRawDevelopment2MakernoteDirectory) this._directory).getIntArray(i);
        if (intArray == null || intArray.length == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < intArray.length; i2++) {
            String str = "; ";
            if (i2 == 0) {
                stringBuilder.append(_filters.containsKey(Integer.valueOf(intArray[i2])) ? (String) _filters.get(Integer.valueOf(intArray[i2])) : "[unknown]");
            } else {
                stringBuilder.append(intArray[i2]);
                stringBuilder.append(str);
            }
            stringBuilder.append(str);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    static {
        _filters.put(Integer.valueOf(0), "Off");
        _filters.put(Integer.valueOf(1), "Soft Focus");
        _filters.put(Integer.valueOf(2), "Pop Art");
        _filters.put(Integer.valueOf(3), "Pale & Light Color");
        _filters.put(Integer.valueOf(4), "Light Tone");
        _filters.put(Integer.valueOf(5), "Pin Hole");
        _filters.put(Integer.valueOf(6), "Grainy Film");
        _filters.put(Integer.valueOf(9), "Diorama");
        _filters.put(Integer.valueOf(10), "Cross Process");
        _filters.put(Integer.valueOf(12), "Fish Eye");
        _filters.put(Integer.valueOf(13), "Drawing");
        _filters.put(Integer.valueOf(14), "Gentle Sepia");
        _filters.put(Integer.valueOf(15), "Pale & Light Color II");
        _filters.put(Integer.valueOf(16), "Pop Art II");
        _filters.put(Integer.valueOf(17), "Pin Hole II");
        _filters.put(Integer.valueOf(18), "Pin Hole III");
        _filters.put(Integer.valueOf(19), "Grainy Film II");
        _filters.put(Integer.valueOf(20), "Dramatic Tone");
        _filters.put(Integer.valueOf(21), "Punk");
        _filters.put(Integer.valueOf(22), "Soft Focus 2");
        _filters.put(Integer.valueOf(23), "Sparkle");
        _filters.put(Integer.valueOf(24), "Watercolor");
        _filters.put(Integer.valueOf(25), "Key Line");
        _filters.put(Integer.valueOf(26), "Key Line II");
        _filters.put(Integer.valueOf(27), "Miniature");
        _filters.put(Integer.valueOf(28), "Reflection");
        _filters.put(Integer.valueOf(29), "Fragmented");
        _filters.put(Integer.valueOf(31), "Cross Process II");
        _filters.put(Integer.valueOf(32), "Dramatic Tone II");
        _filters.put(Integer.valueOf(33), "Watercolor I");
        _filters.put(Integer.valueOf(34), "Watercolor II");
        _filters.put(Integer.valueOf(35), "Diorama II");
        _filters.put(Integer.valueOf(36), "Vintage");
        _filters.put(Integer.valueOf(37), "Vintage II");
        _filters.put(Integer.valueOf(38), "Vintage III");
        _filters.put(Integer.valueOf(39), "Partial Color");
        _filters.put(Integer.valueOf(40), "Partial Color II");
        _filters.put(Integer.valueOf(41), "Partial Color III");
    }
}
