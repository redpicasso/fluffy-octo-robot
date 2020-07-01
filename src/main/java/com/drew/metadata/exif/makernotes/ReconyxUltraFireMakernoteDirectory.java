package com.drew.metadata.exif.makernotes;

import androidx.exifinterface.media.ExifInterface;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import java.util.HashMap;

public class ReconyxUltraFireMakernoteDirectory extends Directory {
    public static final int MAKERNOTE_ID = 65536;
    public static final int MAKERNOTE_PUBLIC_ID = 133234689;
    public static final int TAG_AMBIENT_TEMPERATURE = 70;
    public static final int TAG_AMBIENT_TEMPERATURE_FAHRENHEIT = 68;
    public static final int TAG_BATTERY_VOLTAGE = 73;
    public static final int TAG_BTL_VERSION = 38;
    public static final int TAG_CAMERA_VERSION = 24;
    public static final int TAG_DATE_TIME_ORIGINAL = 59;
    public static final int TAG_DAY_OF_WEEK = 66;
    public static final int TAG_EVENT_NUMBER = 55;
    public static final int TAG_EVENT_TYPE = 52;
    public static final int TAG_FLASH = 72;
    public static final int TAG_LABEL = 0;
    public static final int TAG_MAKERNOTE_ID = 10;
    public static final int TAG_MAKERNOTE_PUBLIC_ID = 18;
    public static final int TAG_MAKERNOTE_PUBLIC_SIZE = 22;
    public static final int TAG_MAKERNOTE_SIZE = 14;
    public static final int TAG_MOON_PHASE = 67;
    public static final int TAG_PEX_VERSION = 45;
    public static final int TAG_SEQUENCE = 53;
    public static final int TAG_SERIAL_NUMBER = 75;
    public static final int TAG_UIB_VERSION = 31;
    public static final int TAG_USER_LABEL = 80;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "Reconyx UltraFire Makernote";
    }

    static {
        _tagNameMap.put(Integer.valueOf(0), "Makernote Label");
        _tagNameMap.put(Integer.valueOf(10), "Makernote ID");
        _tagNameMap.put(Integer.valueOf(14), "Makernote Size");
        _tagNameMap.put(Integer.valueOf(18), "Makernote Public ID");
        _tagNameMap.put(Integer.valueOf(22), "Makernote Public Size");
        _tagNameMap.put(Integer.valueOf(24), "Camera Version");
        _tagNameMap.put(Integer.valueOf(31), "Uib Version");
        _tagNameMap.put(Integer.valueOf(38), "Btl Version");
        _tagNameMap.put(Integer.valueOf(45), "Pex Version");
        _tagNameMap.put(Integer.valueOf(52), "Event Type");
        _tagNameMap.put(Integer.valueOf(53), "Sequence");
        _tagNameMap.put(Integer.valueOf(55), "Event Number");
        _tagNameMap.put(Integer.valueOf(59), "Date/Time Original");
        _tagNameMap.put(Integer.valueOf(66), "Day of Week");
        _tagNameMap.put(Integer.valueOf(67), "Moon Phase");
        _tagNameMap.put(Integer.valueOf(68), "Ambient Temperature Fahrenheit");
        _tagNameMap.put(Integer.valueOf(70), "Ambient Temperature");
        _tagNameMap.put(Integer.valueOf(72), ExifInterface.TAG_FLASH);
        _tagNameMap.put(Integer.valueOf(73), "Battery Voltage");
        _tagNameMap.put(Integer.valueOf(75), "Serial Number");
        _tagNameMap.put(Integer.valueOf(80), "User Label");
    }

    public ReconyxUltraFireMakernoteDirectory() {
        setDescriptor(new ReconyxUltraFireMakernoteDescriptor(this));
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
