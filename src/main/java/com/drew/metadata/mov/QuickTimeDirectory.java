package com.drew.metadata.mov;

import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import java.util.HashMap;

public class QuickTimeDirectory extends Directory {
    public static final int TAG_COMPATIBLE_BRANDS = 4098;
    public static final int TAG_CREATION_TIME = 256;
    public static final int TAG_CURRENT_TIME = 269;
    public static final int TAG_DURATION = 259;
    public static final int TAG_MAJOR_BRAND = 4096;
    public static final int TAG_MEDIA_TIME_SCALE = 774;
    public static final int TAG_MINOR_VERSION = 4097;
    public static final int TAG_MODIFICATION_TIME = 257;
    public static final int TAG_NEXT_TRACK_ID = 270;
    public static final int TAG_POSTER_TIME = 266;
    public static final int TAG_PREFERRED_RATE = 260;
    public static final int TAG_PREFERRED_VOLUME = 261;
    public static final int TAG_PREVIEW_DURATION = 265;
    public static final int TAG_PREVIEW_TIME = 264;
    public static final int TAG_SELECTION_DURATION = 268;
    public static final int TAG_SELECTION_TIME = 267;
    public static final int TAG_TIME_SCALE = 258;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "QuickTime";
    }

    static {
        _tagNameMap.put(Integer.valueOf(4096), "Major Brand");
        _tagNameMap.put(Integer.valueOf(4097), "Minor Version");
        _tagNameMap.put(Integer.valueOf(4098), "Compatible Brands");
        _tagNameMap.put(Integer.valueOf(256), "Creation Time");
        _tagNameMap.put(Integer.valueOf(257), "Modification Time");
        String str = "Media Time Scale";
        _tagNameMap.put(Integer.valueOf(258), str);
        _tagNameMap.put(Integer.valueOf(259), "Duration");
        _tagNameMap.put(Integer.valueOf(260), "Preferred Rate");
        _tagNameMap.put(Integer.valueOf(261), "Preferred Volume");
        _tagNameMap.put(Integer.valueOf(264), "Preview Time");
        _tagNameMap.put(Integer.valueOf(265), "Preview Duration");
        _tagNameMap.put(Integer.valueOf(266), "Poster Time");
        _tagNameMap.put(Integer.valueOf(267), "Selection Time");
        _tagNameMap.put(Integer.valueOf(268), "Selection Duration");
        _tagNameMap.put(Integer.valueOf(269), "Current Time");
        _tagNameMap.put(Integer.valueOf(270), "Next Track ID");
        _tagNameMap.put(Integer.valueOf(774), str);
    }

    public QuickTimeDirectory() {
        setDescriptor(new QuickTimeDescriptor(this));
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
