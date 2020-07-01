package com.drew.metadata.gif;

import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import java.util.HashMap;

public class GifHeaderDirectory extends Directory {
    public static final int TAG_BACKGROUND_COLOR_INDEX = 8;
    public static final int TAG_BITS_PER_PIXEL = 6;
    public static final int TAG_COLOR_TABLE_SIZE = 4;
    public static final int TAG_GIF_FORMAT_VERSION = 1;
    public static final int TAG_HAS_GLOBAL_COLOR_TABLE = 7;
    public static final int TAG_IMAGE_HEIGHT = 3;
    public static final int TAG_IMAGE_WIDTH = 2;
    public static final int TAG_IS_COLOR_TABLE_SORTED = 5;
    public static final int TAG_PIXEL_ASPECT_RATIO = 9;
    @Deprecated
    public static final int TAG_TRANSPARENT_COLOR_INDEX = 8;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "GIF Header";
    }

    static {
        _tagNameMap.put(Integer.valueOf(1), "GIF Format Version");
        _tagNameMap.put(Integer.valueOf(3), "Image Height");
        _tagNameMap.put(Integer.valueOf(2), "Image Width");
        _tagNameMap.put(Integer.valueOf(4), "Color Table Size");
        _tagNameMap.put(Integer.valueOf(5), "Is Color Table Sorted");
        _tagNameMap.put(Integer.valueOf(6), "Bits per Pixel");
        _tagNameMap.put(Integer.valueOf(7), "Has Global Color Table");
        _tagNameMap.put(Integer.valueOf(8), "Background Color Index");
        _tagNameMap.put(Integer.valueOf(9), "Pixel Aspect Ratio");
    }

    public GifHeaderDirectory() {
        setDescriptor(new GifHeaderDescriptor(this));
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
