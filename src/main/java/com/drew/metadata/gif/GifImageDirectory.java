package com.drew.metadata.gif;

import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import java.util.HashMap;

public class GifImageDirectory extends Directory {
    public static final int TAG_HAS_LOCAL_COLOUR_TABLE = 5;
    public static final int TAG_HEIGHT = 4;
    public static final int TAG_IS_COLOR_TABLE_SORTED = 7;
    public static final int TAG_IS_INTERLACED = 6;
    public static final int TAG_LEFT = 1;
    public static final int TAG_LOCAL_COLOUR_TABLE_BITS_PER_PIXEL = 8;
    public static final int TAG_TOP = 2;
    public static final int TAG_WIDTH = 3;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "GIF Image";
    }

    static {
        _tagNameMap.put(Integer.valueOf(1), "Left");
        _tagNameMap.put(Integer.valueOf(2), "Top");
        _tagNameMap.put(Integer.valueOf(3), "Width");
        _tagNameMap.put(Integer.valueOf(4), "Height");
        _tagNameMap.put(Integer.valueOf(5), "Has Local Colour Table");
        _tagNameMap.put(Integer.valueOf(6), "Is Interlaced");
        _tagNameMap.put(Integer.valueOf(7), "Is Local Colour Table Sorted");
        _tagNameMap.put(Integer.valueOf(8), "Local Colour Table Bits Per Pixel");
    }

    public GifImageDirectory() {
        setDescriptor(new GifImageDescriptor(this));
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
