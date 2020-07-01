package com.drew.metadata.exif;

import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import java.util.HashMap;

public class PanasonicRawWbInfo2Directory extends Directory {
    public static final int TagNumWbEntries = 0;
    public static final int TagWbRgbLevels1 = 2;
    public static final int TagWbRgbLevels2 = 6;
    public static final int TagWbRgbLevels3 = 10;
    public static final int TagWbRgbLevels4 = 14;
    public static final int TagWbRgbLevels5 = 18;
    public static final int TagWbRgbLevels6 = 22;
    public static final int TagWbRgbLevels7 = 26;
    public static final int TagWbType1 = 1;
    public static final int TagWbType2 = 5;
    public static final int TagWbType3 = 9;
    public static final int TagWbType4 = 13;
    public static final int TagWbType5 = 17;
    public static final int TagWbType6 = 21;
    public static final int TagWbType7 = 25;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "PanasonicRaw WbInfo2";
    }

    static {
        HashMap hashMap = _tagNameMap;
        Integer valueOf = Integer.valueOf(0);
        String str = "Num WB Entries";
        hashMap.put(valueOf, str);
        _tagNameMap.put(valueOf, str);
        _tagNameMap.put(Integer.valueOf(1), "WB Type 1");
        _tagNameMap.put(Integer.valueOf(2), "WB RGB Levels 1");
        _tagNameMap.put(Integer.valueOf(5), "WB Type 2");
        _tagNameMap.put(Integer.valueOf(6), "WB RGB Levels 2");
        _tagNameMap.put(Integer.valueOf(9), "WB Type 3");
        _tagNameMap.put(Integer.valueOf(10), "WB RGB Levels 3");
        _tagNameMap.put(Integer.valueOf(13), "WB Type 4");
        _tagNameMap.put(Integer.valueOf(14), "WB RGB Levels 4");
        _tagNameMap.put(Integer.valueOf(17), "WB Type 5");
        _tagNameMap.put(Integer.valueOf(18), "WB RGB Levels 5");
        _tagNameMap.put(Integer.valueOf(21), "WB Type 6");
        _tagNameMap.put(Integer.valueOf(22), "WB RGB Levels 6");
        _tagNameMap.put(Integer.valueOf(25), "WB Type 7");
        _tagNameMap.put(Integer.valueOf(26), "WB RGB Levels 7");
    }

    public PanasonicRawWbInfo2Directory() {
        setDescriptor(new PanasonicRawWbInfo2Descriptor(this));
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}