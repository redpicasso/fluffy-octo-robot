package com.drew.metadata.exif.makernotes;

import com.drew.lang.Rational;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;

public class OlympusRawInfoMakernoteDescriptor extends TagDescriptor<OlympusRawInfoMakernoteDirectory> {
    public OlympusRawInfoMakernoteDescriptor(@NotNull OlympusRawInfoMakernoteDirectory olympusRawInfoMakernoteDirectory) {
        super(olympusRawInfoMakernoteDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        if (i == 0) {
            return getVersionBytesDescription(0, 4);
        }
        if (i == 512) {
            return getColorMatrix2Description();
        }
        if (i == 1537) {
            return getYCbCrCoefficientsDescription();
        }
        if (i != 4096) {
            return super.getDescription(i);
        }
        return getOlympusLightSourceDescription();
    }

    @Nullable
    public String getColorMatrix2Description() {
        int[] intArray = ((OlympusRawInfoMakernoteDirectory) this._directory).getIntArray(512);
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
    public String getYCbCrCoefficientsDescription() {
        int[] intArray = ((OlympusRawInfoMakernoteDirectory) this._directory).getIntArray(1537);
        String str = null;
        if (intArray == null) {
            return null;
        }
        Rational[] rationalArr = new Rational[(intArray.length / 2)];
        for (int i = 0; i < intArray.length / 2; i++) {
            int i2 = i * 2;
            rationalArr[i] = new Rational((long) ((short) intArray[i2]), (long) ((short) intArray[i2 + 1]));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i3 = 0; i3 < rationalArr.length; i3++) {
            stringBuilder.append(rationalArr[i3].doubleValue());
            if (i3 < rationalArr.length - 1) {
                stringBuilder.append(" ");
            }
        }
        if (stringBuilder.length() != 0) {
            str = stringBuilder.toString();
        }
        return str;
    }

    @Nullable
    public String getOlympusLightSourceDescription() {
        Integer integer = ((OlympusRawInfoMakernoteDirectory) this._directory).getInteger(4096);
        if (integer == null) {
            return null;
        }
        short shortValue = integer.shortValue();
        if (shortValue == (short) 0) {
            return "Unknown";
        }
        if (shortValue == (short) 20) {
            return "Tungsten (Incandescent)";
        }
        if (shortValue == (short) 22) {
            return "Evening Sunlight";
        }
        if (shortValue == (short) 256) {
            return "One Touch White Balance";
        }
        if (shortValue == (short) 512) {
            return "Custom 1-4";
        }
        switch (shortValue) {
            case (short) 16:
                return "Shade";
            case (short) 17:
                return "Cloudy";
            case (short) 18:
                return "Fine Weather";
            default:
                switch (shortValue) {
                    case (short) 33:
                        return "Daylight Fluorescent";
                    case (short) 34:
                        return "Day White Fluorescent";
                    case (short) 35:
                        return "Cool White Fluorescent";
                    case (short) 36:
                        return "White Fluorescent";
                    default:
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown (");
                        stringBuilder.append(integer);
                        stringBuilder.append(")");
                        return stringBuilder.toString();
                }
        }
    }
}
