package com.drew.metadata.mov.media;

import com.drew.lang.annotations.NotNull;
import java.util.HashMap;

public class QuickTimeVideoDirectory extends QuickTimeMediaDirectory {
    public static final int TAG_COLOR_TABLE = 13;
    public static final int TAG_COMPRESSION_TYPE = 10;
    public static final int TAG_COMPRESSOR_NAME = 8;
    public static final int TAG_DEPTH = 9;
    public static final int TAG_FRAME_RATE = 14;
    public static final int TAG_GRAPHICS_MODE = 11;
    public static final int TAG_HEIGHT = 5;
    public static final int TAG_HORIZONTAL_RESOLUTION = 6;
    public static final int TAG_OPCOLOR = 12;
    public static final int TAG_SPATIAL_QUALITY = 3;
    public static final int TAG_TEMPORAL_QUALITY = 2;
    public static final int TAG_VENDOR = 1;
    public static final int TAG_VERTICAL_RESOLUTION = 7;
    public static final int TAG_WIDTH = 4;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "QuickTime Video";
    }

    public QuickTimeVideoDirectory() {
        setDescriptor(new QuickTimeVideoDescriptor(this));
    }

    static {
        QuickTimeMediaDirectory.addQuickTimeMediaTags(_tagNameMap);
        _tagNameMap.put(Integer.valueOf(1), "Vendor");
        _tagNameMap.put(Integer.valueOf(2), "Temporal Quality");
        _tagNameMap.put(Integer.valueOf(3), "Spatial Quality");
        _tagNameMap.put(Integer.valueOf(4), "Width");
        _tagNameMap.put(Integer.valueOf(5), "Height");
        _tagNameMap.put(Integer.valueOf(6), "Horizontal Resolution");
        _tagNameMap.put(Integer.valueOf(7), "Vertical Resolution");
        _tagNameMap.put(Integer.valueOf(8), "Compressor Name");
        _tagNameMap.put(Integer.valueOf(9), "Depth");
        _tagNameMap.put(Integer.valueOf(10), "Compression Type");
        _tagNameMap.put(Integer.valueOf(11), "Graphics Mode");
        _tagNameMap.put(Integer.valueOf(12), "Opcolor");
        _tagNameMap.put(Integer.valueOf(13), "Color Table");
        _tagNameMap.put(Integer.valueOf(14), "Frame Rate");
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
