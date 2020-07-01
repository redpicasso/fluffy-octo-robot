package com.drew.metadata.exif.makernotes;

import com.drew.lang.Rational;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;

public class OlympusFocusInfoMakernoteDescriptor extends TagDescriptor<OlympusFocusInfoMakernoteDirectory> {
    public OlympusFocusInfoMakernoteDescriptor(@NotNull OlympusFocusInfoMakernoteDirectory olympusFocusInfoMakernoteDirectory) {
        super(olympusFocusInfoMakernoteDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        if (i == 0) {
            return getFocusInfoVersionDescription();
        }
        if (i == 521) {
            return getAutoFocusDescription();
        }
        if (i == 773) {
            return getFocusDistanceDescription();
        }
        if (i == OlympusFocusInfoMakernoteDirectory.TagAfPoint) {
            return getAfPointDescription();
        }
        if (i == 4609) {
            return getExternalFlashDescription();
        }
        if (i == OlympusFocusInfoMakernoteDirectory.TagSensorTemperature) {
            return getSensorTemperatureDescription();
        }
        if (i == OlympusFocusInfoMakernoteDirectory.TagImageStabilization) {
            return getImageStabilizationDescription();
        }
        if (i == OlympusFocusInfoMakernoteDirectory.TagExternalFlashBounce) {
            return getExternalFlashBounceDescription();
        }
        if (i == OlympusFocusInfoMakernoteDirectory.TagExternalFlashZoom) {
            return getExternalFlashZoomDescription();
        }
        if (i == OlympusFocusInfoMakernoteDirectory.TagManualFlash) {
            return getManualFlashDescription();
        }
        if (i != OlympusFocusInfoMakernoteDirectory.TagMacroLed) {
            return super.getDescription(i);
        }
        return getMacroLedDescription();
    }

    @Nullable
    public String getFocusInfoVersionDescription() {
        return getVersionBytesDescription(0, 4);
    }

    @Nullable
    public String getAutoFocusDescription() {
        return getIndexedDescription(521, "Off", "On");
    }

    @Nullable
    public String getFocusDistanceDescription() {
        Rational rational = ((OlympusFocusInfoMakernoteDirectory) this._directory).getRational(773);
        String str = "inf";
        if (rational == null || rational.getNumerator() == 4294967295L || rational.getNumerator() == 0) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((double) rational.getNumerator()) / 1000.0d);
        stringBuilder.append(" m");
        return stringBuilder.toString();
    }

    @Nullable
    public String getAfPointDescription() {
        Integer integer = ((OlympusFocusInfoMakernoteDirectory) this._directory).getInteger(OlympusFocusInfoMakernoteDirectory.TagAfPoint);
        if (integer == null) {
            return null;
        }
        return integer.toString();
    }

    @Nullable
    public String getExternalFlashDescription() {
        int[] intArray = ((OlympusFocusInfoMakernoteDirectory) this._directory).getIntArray(4609);
        if (intArray == null || intArray.length < 2) {
            return null;
        }
        String format = String.format("%d %d", new Object[]{Short.valueOf((short) intArray[0]), Short.valueOf((short) intArray[1])});
        if (format.equals("0 0")) {
            return "Off";
        }
        if (format.equals("1 0")) {
            return "On";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(format);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    public String getExternalFlashBounceDescription() {
        return getIndexedDescription(OlympusFocusInfoMakernoteDirectory.TagExternalFlashBounce, "Bounce or Off", "Direct");
    }

    @Nullable
    public String getExternalFlashZoomDescription() {
        int[] intArray = ((OlympusFocusInfoMakernoteDirectory) this._directory).getIntArray(OlympusFocusInfoMakernoteDirectory.TagExternalFlashZoom);
        if (intArray == null) {
            if (((OlympusFocusInfoMakernoteDirectory) this._directory).getInteger(OlympusFocusInfoMakernoteDirectory.TagExternalFlashZoom) == null) {
                return null;
            }
            intArray = new int[]{((OlympusFocusInfoMakernoteDirectory) this._directory).getInteger(OlympusFocusInfoMakernoteDirectory.TagExternalFlashZoom).intValue()};
        }
        if (intArray.length == 0) {
            return null;
        }
        String str = "%d";
        String format = String.format(str, new Object[]{Short.valueOf((short) intArray[0])});
        if (intArray.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(format);
            stringBuilder.append(" ");
            stringBuilder.append(String.format(str, new Object[]{Short.valueOf((short) intArray[1])}));
            format = stringBuilder.toString();
        }
        str = "Off";
        if (format.equals("0")) {
            return str;
        }
        String str2 = "On";
        if (format.equals("1")) {
            return str2;
        }
        if (format.equals("0 0")) {
            return str;
        }
        if (format.equals("1 0")) {
            return str2;
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Unknown (");
        stringBuilder2.append(format);
        stringBuilder2.append(")");
        return stringBuilder2.toString();
    }

    @Nullable
    public String getManualFlashDescription() {
        int[] intArray = ((OlympusFocusInfoMakernoteDirectory) this._directory).getIntArray(OlympusFocusInfoMakernoteDirectory.TagManualFlash);
        if (intArray == null) {
            return null;
        }
        if (((short) intArray[0]) == (short) 0) {
            return "Off";
        }
        if (((short) intArray[1]) == (short) 1) {
            return "Full";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("On (1/");
        stringBuilder.append((short) intArray[1]);
        stringBuilder.append(" strength)");
        return stringBuilder.toString();
    }

    @Nullable
    public String getMacroLedDescription() {
        return getIndexedDescription(OlympusFocusInfoMakernoteDirectory.TagMacroLed, "Off", "On");
    }

    @Nullable
    public String getSensorTemperatureDescription() {
        return ((OlympusFocusInfoMakernoteDirectory) this._directory).getString(OlympusFocusInfoMakernoteDirectory.TagSensorTemperature);
    }

    @Nullable
    public String getImageStabilizationDescription() {
        byte[] byteArray = ((OlympusFocusInfoMakernoteDirectory) this._directory).getByteArray(OlympusFocusInfoMakernoteDirectory.TagImageStabilization);
        if (byteArray == null) {
            return null;
        }
        if ((((byteArray[0] | byteArray[1]) | byteArray[2]) | byteArray[3]) == 0) {
            return "Off";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("On, ");
        stringBuilder.append((byteArray[43] & 1) > 0 ? "Mode 1" : "Mode 2");
        return stringBuilder.toString();
    }
}
