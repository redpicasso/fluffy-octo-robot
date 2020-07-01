package com.drew.metadata.mp4.media;

import com.drew.lang.annotations.NotNull;
import java.util.HashMap;

public class Mp4VideoDirectory extends Mp4MediaDirectory {
    public static final int TAG_COLOR_TABLE = 113;
    public static final int TAG_COMPRESSION_TYPE = 110;
    public static final int TAG_COMPRESSOR_NAME = 108;
    public static final int TAG_DEPTH = 109;
    public static final int TAG_FRAME_RATE = 114;
    public static final int TAG_GRAPHICS_MODE = 111;
    public static final int TAG_HEIGHT = 105;
    public static final int TAG_HORIZONTAL_RESOLUTION = 106;
    public static final int TAG_OPCOLOR = 112;
    public static final int TAG_SPATIAL_QUALITY = 103;
    public static final int TAG_TEMPORAL_QUALITY = 102;
    public static final int TAG_VENDOR = 101;
    public static final int TAG_VERTICAL_RESOLUTION = 107;
    public static final int TAG_WIDTH = 104;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "MP4 Video";
    }

    public Mp4VideoDirectory() {
        setDescriptor(new Mp4VideoDescriptor(this));
    }

    static {
        Mp4MediaDirectory.addMp4MediaTags(_tagNameMap);
        _tagNameMap.put(Integer.valueOf(101), "Vendor");
        _tagNameMap.put(Integer.valueOf(102), "Temporal Quality");
        _tagNameMap.put(Integer.valueOf(103), "Spatial Quality");
        _tagNameMap.put(Integer.valueOf(104), "Width");
        _tagNameMap.put(Integer.valueOf(105), "Height");
        _tagNameMap.put(Integer.valueOf(106), "Horizontal Resolution");
        _tagNameMap.put(Integer.valueOf(107), "Vertical Resolution");
        _tagNameMap.put(Integer.valueOf(108), "Compressor Name");
        _tagNameMap.put(Integer.valueOf(109), "Depth");
        _tagNameMap.put(Integer.valueOf(110), "Compression Type");
        _tagNameMap.put(Integer.valueOf(111), "Graphics Mode");
        _tagNameMap.put(Integer.valueOf(112), "Opcolor");
        _tagNameMap.put(Integer.valueOf(113), "Color Table");
        _tagNameMap.put(Integer.valueOf(114), "Frame Rate");
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
