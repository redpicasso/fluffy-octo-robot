package com.drew.metadata;

import androidx.exifinterface.media.ExifInterface;
import com.drew.lang.Rational;
import com.drew.lang.StringUtil;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TagDescriptor<T extends Directory> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    @NotNull
    protected final T _directory;

    public TagDescriptor(@NotNull T t) {
        this._directory = t;
    }

    @Nullable
    public String getDescription(int i) {
        Object object = this._directory.getObject(i);
        if (object == null) {
            return null;
        }
        if (object.getClass().isArray() && Array.getLength(object) > 16) {
            return String.format("[%d values]", new Object[]{Integer.valueOf(Array.getLength(object))});
        } else if (object instanceof Date) {
            return new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy").format((Date) object).replaceAll("([0-9]{2} [^ ]+)$", ":$1");
        } else {
            return this._directory.getString(i);
        }
    }

    @Nullable
    public static String convertBytesToVersionString(@Nullable int[] iArr, int i) {
        if (iArr == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int i2 = 0;
        while (i2 < 4 && i2 < iArr.length) {
            if (i2 == i) {
                stringBuilder.append('.');
            }
            char c = (char) iArr[i2];
            if (c < '0') {
                c = (char) (c + 48);
            }
            if (i2 != 0 || c != '0') {
                stringBuilder.append(c);
            }
            i2++;
        }
        return stringBuilder.toString();
    }

    @Nullable
    protected String getVersionBytesDescription(int i, int i2) {
        int[] intArray = this._directory.getIntArray(i);
        if (intArray == null) {
            return null;
        }
        return convertBytesToVersionString(intArray, i2);
    }

    @Nullable
    protected String getIndexedDescription(int i, @NotNull String... strArr) {
        return getIndexedDescription(i, 0, strArr);
    }

    @Nullable
    protected String getIndexedDescription(int i, int i2, @NotNull String... strArr) {
        Long longObject = this._directory.getLongObject(i);
        if (longObject == null) {
            return null;
        }
        long longValue = longObject.longValue() - ((long) i2);
        if (longValue >= 0 && longValue < ((long) strArr.length)) {
            String str = strArr[(int) longValue];
            if (str != null) {
                return str;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(longObject);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    protected String getByteLengthDescription(int i) {
        byte[] byteArray = this._directory.getByteArray(i);
        if (byteArray == null) {
            return null;
        }
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(byteArray.length);
        objArr[1] = byteArray.length == 1 ? "" : "s";
        return String.format("(%d byte%s)", objArr);
    }

    @Nullable
    protected String getSimpleRational(int i) {
        Rational rational = this._directory.getRational(i);
        if (rational == null) {
            return null;
        }
        return rational.toSimpleString(true);
    }

    @Nullable
    protected String getDecimalRational(int i, int i2) {
        if (this._directory.getRational(i) == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%.");
        stringBuilder.append(i2);
        stringBuilder.append("f");
        return String.format(stringBuilder.toString(), new Object[]{Double.valueOf(r5.doubleValue())});
    }

    @Nullable
    protected String getFormattedInt(int i, @NotNull String str) {
        if (this._directory.getInteger(i) == null) {
            return null;
        }
        return String.format(str, new Object[]{this._directory.getInteger(i)});
    }

    @Nullable
    protected String getFormattedFloat(int i, @NotNull String str) {
        if (this._directory.getFloatObject(i) == null) {
            return null;
        }
        return String.format(str, new Object[]{this._directory.getFloatObject(i)});
    }

    @Nullable
    protected String getFormattedString(int i, @NotNull String str) {
        if (this._directory.getString(i) == null) {
            return null;
        }
        return String.format(str, new Object[]{this._directory.getString(i)});
    }

    @Nullable
    protected String getEpochTimeDescription(int i) {
        Long longObject = this._directory.getLongObject(i);
        if (longObject == null) {
            return null;
        }
        return new Date(longObject.longValue()).toString();
    }

    @Nullable
    protected String getBitFlagDescription(int i, @NotNull Object... objArr) {
        Integer integer = this._directory.getInteger(i);
        if (integer == null) {
            return null;
        }
        Iterable arrayList = new ArrayList();
        Integer num = integer;
        for (i = 0; objArr.length > i; i++) {
            Object obj = objArr[i];
            if (obj != null) {
                int i2 = (num.intValue() & 1) == 1 ? 1 : 0;
                if (obj instanceof String[]) {
                    arrayList.add(((String[]) obj)[i2]);
                } else if (i2 != 0 && (obj instanceof String)) {
                    arrayList.add((String) obj);
                }
            }
            num = Integer.valueOf(num.intValue() >> 1);
        }
        return StringUtil.join(arrayList, ", ");
    }

    @Nullable
    protected String get7BitStringFromBytes(int i) {
        byte[] byteArray = this._directory.getByteArray(i);
        if (byteArray == null) {
            return null;
        }
        int length = byteArray.length;
        for (int i2 = 0; i2 < byteArray.length; i2++) {
            int i3 = byteArray[i2] & 255;
            if (i3 == 0 || i3 > 127) {
                length = i2;
                break;
            }
        }
        return new String(byteArray, 0, length);
    }

    @Nullable
    protected String getStringFromBytes(int i, Charset charset) {
        byte[] byteArray = this._directory.getByteArray(i);
        if (byteArray == null) {
            return null;
        }
        try {
            return new String(byteArray, charset.name()).trim();
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    @Nullable
    protected String getRationalOrDoubleString(int i) {
        Rational rational = this._directory.getRational(i);
        if (rational != null) {
            return rational.toSimpleString(true);
        }
        Double doubleObject = this._directory.getDoubleObject(i);
        return doubleObject != null ? new DecimalFormat("0.###").format(doubleObject) : null;
    }

    @Nullable
    protected static String getFStopDescription(double d) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("f/");
        stringBuilder.append(decimalFormat.format(d));
        return stringBuilder.toString();
    }

    @Nullable
    protected static String getFocalLengthDescription(double d) {
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(decimalFormat.format(d));
        stringBuilder.append(" mm");
        return stringBuilder.toString();
    }

    @Nullable
    protected String getLensSpecificationDescription(int i) {
        Rational[] rationalArray = this._directory.getRationalArray(i);
        if (rationalArray == null || rationalArray.length != 4 || (rationalArray[0].isZero() && rationalArray[2].isZero())) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String str = "mm";
        if (rationalArray[0].equals(rationalArray[1])) {
            stringBuilder.append(rationalArray[0].toSimpleString(true));
            stringBuilder.append(str);
        } else {
            stringBuilder.append(rationalArray[0].toSimpleString(true));
            stringBuilder.append('-');
            stringBuilder.append(rationalArray[1].toSimpleString(true));
            stringBuilder.append(str);
        }
        if (!rationalArray[2].isZero()) {
            stringBuilder.append(' ');
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            if (rationalArray[2].equals(rationalArray[3])) {
                stringBuilder.append(getFStopDescription(rationalArray[2].doubleValue()));
            } else {
                stringBuilder.append("f/");
                stringBuilder.append(decimalFormat.format(rationalArray[2].doubleValue()));
                stringBuilder.append('-');
                stringBuilder.append(decimalFormat.format(rationalArray[3].doubleValue()));
            }
        }
        return stringBuilder.toString();
    }

    @Nullable
    protected String getOrientationDescription(int i) {
        return getIndexedDescription(i, 1, "Top, left side (Horizontal / normal)", "Top, right side (Mirror horizontal)", "Bottom, right side (Rotate 180)", "Bottom, left side (Mirror vertical)", "Left side, top (Mirror horizontal and rotate 270 CW)", "Right side, top (Rotate 90 CW)", "Right side, bottom (Mirror horizontal and rotate 90 CW)", "Left side, bottom (Rotate 270 CW)");
    }

    @Nullable
    protected String getShutterSpeedDescription(int i) {
        Float floatObject = this._directory.getFloatObject(i);
        if (floatObject == null) {
            return null;
        }
        String str = " sec";
        if (floatObject.floatValue() <= 1.0f) {
            float round = ((float) Math.round(((double) ((float) (1.0d / Math.exp(((double) floatObject.floatValue()) * Math.log(2.0d))))) * 10.0d)) / 10.0f;
            DecimalFormat decimalFormat = new DecimalFormat("0.##");
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(decimalFormat.format((double) round));
            stringBuilder.append(str);
            return stringBuilder.toString();
        }
        i = (int) Math.exp(((double) floatObject.floatValue()) * Math.log(2.0d));
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("1/");
        stringBuilder2.append(i);
        stringBuilder2.append(str);
        return stringBuilder2.toString();
    }

    @Nullable
    protected String getLightSourceDescription(short s) {
        if (s == (short) 0) {
            return "Unknown";
        }
        if (s == (short) 1) {
            return "Daylight";
        }
        if (s == (short) 2) {
            return "Fluorescent";
        }
        if (s == (short) 3) {
            return "Tungsten (Incandescent)";
        }
        if (s == (short) 4) {
            return ExifInterface.TAG_FLASH;
        }
        if (s == (short) 255) {
            return "Other";
        }
        switch (s) {
            case (short) 9:
                return "Fine Weather";
            case (short) 10:
                return "Cloudy";
            case (short) 11:
                return "Shade";
            case (short) 12:
                return "Daylight Fluorescent";
            case (short) 13:
                return "Day White Fluorescent";
            case (short) 14:
                return "Cool White Fluorescent";
            case (short) 15:
                return "White Fluorescent";
            case (short) 16:
                return "Warm White Fluorescent";
            case (short) 17:
                return "Standard Light A";
            case (short) 18:
                return "Standard Light B";
            case (short) 19:
                return "Standard Light C";
            case (short) 20:
                return "D55";
            case (short) 21:
                return "D65";
            case (short) 22:
                return "D75";
            case (short) 23:
                return "D50";
            case (short) 24:
                return "ISO Studio Tungsten";
            default:
                return getDescription(s);
        }
    }
}
