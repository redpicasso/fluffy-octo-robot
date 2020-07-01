package com.drew.metadata.png;

import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.TagDescriptor;
import java.util.HashMap;

public class PngChromaticitiesDirectory extends Directory {
    public static final int TAG_BLUE_X = 7;
    public static final int TAG_BLUE_Y = 8;
    public static final int TAG_GREEN_X = 5;
    public static final int TAG_GREEN_Y = 6;
    public static final int TAG_RED_X = 3;
    public static final int TAG_RED_Y = 4;
    public static final int TAG_WHITE_POINT_X = 1;
    public static final int TAG_WHITE_POINT_Y = 2;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "PNG Chromaticities";
    }

    static {
        _tagNameMap.put(Integer.valueOf(1), "White Point X");
        _tagNameMap.put(Integer.valueOf(2), "White Point Y");
        _tagNameMap.put(Integer.valueOf(3), "Red X");
        _tagNameMap.put(Integer.valueOf(4), "Red Y");
        _tagNameMap.put(Integer.valueOf(5), "Green X");
        _tagNameMap.put(Integer.valueOf(6), "Green Y");
        _tagNameMap.put(Integer.valueOf(7), "Blue X");
        _tagNameMap.put(Integer.valueOf(8), "Blue Y");
    }

    public PngChromaticitiesDirectory() {
        setDescriptor(new TagDescriptor(this));
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
