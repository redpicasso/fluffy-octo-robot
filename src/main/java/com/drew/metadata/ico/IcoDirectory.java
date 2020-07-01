package com.drew.metadata.ico;

import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import java.util.HashMap;

public class IcoDirectory extends Directory {
    public static final int TAG_BITS_PER_PIXEL = 7;
    public static final int TAG_COLOUR_PALETTE_SIZE = 4;
    public static final int TAG_COLOUR_PLANES = 5;
    public static final int TAG_CURSOR_HOTSPOT_X = 6;
    public static final int TAG_CURSOR_HOTSPOT_Y = 8;
    public static final int TAG_IMAGE_HEIGHT = 3;
    public static final int TAG_IMAGE_OFFSET_BYTES = 10;
    public static final int TAG_IMAGE_SIZE_BYTES = 9;
    public static final int TAG_IMAGE_TYPE = 1;
    public static final int TAG_IMAGE_WIDTH = 2;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "ICO";
    }

    static {
        _tagNameMap.put(Integer.valueOf(1), "Image Type");
        _tagNameMap.put(Integer.valueOf(2), "Image Width");
        _tagNameMap.put(Integer.valueOf(3), "Image Height");
        _tagNameMap.put(Integer.valueOf(4), "Colour Palette Size");
        _tagNameMap.put(Integer.valueOf(5), "Colour Planes");
        _tagNameMap.put(Integer.valueOf(6), "Hotspot X");
        _tagNameMap.put(Integer.valueOf(7), "Bits Per Pixel");
        _tagNameMap.put(Integer.valueOf(8), "Hotspot Y");
        _tagNameMap.put(Integer.valueOf(9), "Image Size Bytes");
        _tagNameMap.put(Integer.valueOf(10), "Image Offset Bytes");
    }

    public IcoDirectory() {
        setDescriptor(new IcoDescriptor(this));
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
