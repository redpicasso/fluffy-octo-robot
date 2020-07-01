package com.drew.metadata.exif.makernotes;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;

public class OlympusImageProcessingMakernoteDescriptor extends TagDescriptor<OlympusImageProcessingMakernoteDirectory> {
    public OlympusImageProcessingMakernoteDescriptor(@NotNull OlympusImageProcessingMakernoteDirectory olympusImageProcessingMakernoteDirectory) {
        super(olympusImageProcessingMakernoteDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        if (i == 0) {
            return getImageProcessingVersionDescription();
        }
        if (i == 512) {
            return getColorMatrixDescription();
        }
        if (i == 4124) {
            return getMultipleExposureModeDescription();
        }
        if (i == OlympusImageProcessingMakernoteDirectory.TagAspectRatio) {
            return getAspectRatioDescription();
        }
        if (i == OlympusImageProcessingMakernoteDirectory.TagKeystoneCompensation) {
            return getKeystoneCompensationDescription();
        }
        if (i == OlympusImageProcessingMakernoteDirectory.TagKeystoneDirection) {
            return getKeystoneDirectionDescription();
        }
        switch (i) {
            case 4112:
                return getNoiseReduction2Description();
            case 4113:
                return getDistortionCorrection2Description();
            case 4114:
                return getShadingCompensation2Description();
            default:
                return super.getDescription(i);
        }
    }

    @Nullable
    public String getImageProcessingVersionDescription() {
        return getVersionBytesDescription(0, 4);
    }

    @Nullable
    public String getColorMatrixDescription() {
        int[] intArray = ((OlympusImageProcessingMakernoteDirectory) this._directory).getIntArray(512);
        if (intArray == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < intArray.length; i++) {
            if (i != 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append((short) intArray[i]);
        }
        return stringBuilder.toString();
    }

    @Nullable
    public String getNoiseReduction2Description() {
        Integer integer = ((OlympusImageProcessingMakernoteDirectory) this._directory).getInteger(4112);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() == 0) {
            return "(none)";
        }
        StringBuilder stringBuilder = new StringBuilder();
        short shortValue = integer.shortValue();
        if ((shortValue & 1) != 0) {
            stringBuilder.append("Noise Reduction, ");
        }
        if (((shortValue >> 1) & 1) != 0) {
            stringBuilder.append("Noise Filter, ");
        }
        if (((shortValue >> 2) & 1) != 0) {
            stringBuilder.append("Noise Filter (ISO Boost), ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    @Nullable
    public String getDistortionCorrection2Description() {
        return getIndexedDescription(4113, "Off", "On");
    }

    @Nullable
    public String getShadingCompensation2Description() {
        return getIndexedDescription(4114, "Off", "On");
    }

    @Nullable
    public String getMultipleExposureModeDescription() {
        int[] intArray = ((OlympusImageProcessingMakernoteDirectory) this._directory).getIntArray(4124);
        if (intArray == null) {
            if (((OlympusImageProcessingMakernoteDirectory) this._directory).getInteger(4124) == null) {
                return null;
            }
            intArray = new int[]{((OlympusImageProcessingMakernoteDirectory) this._directory).getInteger(4124).intValue()};
        }
        if (intArray.length == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        short s = (short) intArray[0];
        if (s == (short) 0) {
            stringBuilder.append("Off");
        } else if (s == (short) 2) {
            stringBuilder.append("On (2 frames)");
        } else if (s != (short) 3) {
            stringBuilder.append("Unknown (");
            stringBuilder.append((short) intArray[0]);
            stringBuilder.append(")");
        } else {
            stringBuilder.append("On (3 frames)");
        }
        if (intArray.length > 1) {
            stringBuilder.append("; ");
            stringBuilder.append((short) intArray[1]);
        }
        return stringBuilder.toString();
    }

    @Nullable
    public String getAspectRatioDescription() {
        byte[] byteArray = ((OlympusImageProcessingMakernoteDirectory) this._directory).getByteArray(OlympusImageProcessingMakernoteDirectory.TagAspectRatio);
        if (byteArray == null || byteArray.length < 2) {
            return null;
        }
        String format = String.format("%d %d", new Object[]{Byte.valueOf(byteArray[0]), Byte.valueOf(byteArray[1])});
        if (format.equals("1 1")) {
            format = "4:3";
        } else if (format.equals("1 4")) {
            format = "1:1";
        } else if (format.equals("2 1")) {
            format = "3:2 (RAW)";
        } else if (format.equals("2 2")) {
            format = "3:2";
        } else if (format.equals("3 1")) {
            format = "16:9 (RAW)";
        } else if (format.equals("3 3")) {
            format = "16:9";
        } else if (format.equals("4 1")) {
            format = "1:1 (RAW)";
        } else if (format.equals("4 4")) {
            format = "6:6";
        } else if (format.equals("5 5")) {
            format = "5:4";
        } else if (format.equals("6 6")) {
            format = "7:6";
        } else if (format.equals("7 7")) {
            format = "6:5";
        } else if (format.equals("8 8")) {
            format = "7:5";
        } else if (format.equals("9 1")) {
            format = "3:4 (RAW)";
        } else if (format.equals("9 9")) {
            format = "3:4";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown (");
            stringBuilder.append(format);
            stringBuilder.append(")");
            format = stringBuilder.toString();
        }
        return format;
    }

    @Nullable
    public String getKeystoneCompensationDescription() {
        byte[] byteArray = ((OlympusImageProcessingMakernoteDirectory) this._directory).getByteArray(OlympusImageProcessingMakernoteDirectory.TagKeystoneCompensation);
        if (byteArray == null || byteArray.length < 2) {
            return null;
        }
        String format = String.format("%d %d", new Object[]{Byte.valueOf(byteArray[0]), Byte.valueOf(byteArray[1])});
        if (format.equals("0 0")) {
            format = "Off";
        } else if (format.equals("0 1")) {
            format = "On";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown (");
            stringBuilder.append(format);
            stringBuilder.append(")");
            format = stringBuilder.toString();
        }
        return format;
    }

    @Nullable
    public String getKeystoneDirectionDescription() {
        return getIndexedDescription(OlympusImageProcessingMakernoteDirectory.TagKeystoneDirection, "Vertical", "Horizontal");
    }
}
