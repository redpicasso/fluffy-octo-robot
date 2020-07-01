package com.drew.metadata.exif;

import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import java.util.HashMap;

public class PanasonicRawDistortionDirectory extends Directory {
    public static final int TagDistortionCorrection = 7;
    public static final int TagDistortionN = 12;
    public static final int TagDistortionParam02 = 2;
    public static final int TagDistortionParam04 = 4;
    public static final int TagDistortionParam08 = 8;
    public static final int TagDistortionParam09 = 9;
    public static final int TagDistortionParam11 = 11;
    public static final int TagDistortionScale = 5;
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "PanasonicRaw DistortionInfo";
    }

    static {
        _tagNameMap.put(Integer.valueOf(2), "Distortion Param 2");
        _tagNameMap.put(Integer.valueOf(4), "Distortion Param 4");
        _tagNameMap.put(Integer.valueOf(5), "Distortion Scale");
        _tagNameMap.put(Integer.valueOf(7), "Distortion Correction");
        _tagNameMap.put(Integer.valueOf(8), "Distortion Param 8");
        _tagNameMap.put(Integer.valueOf(9), "Distortion Param 9");
        _tagNameMap.put(Integer.valueOf(11), "Distortion Param 11");
        _tagNameMap.put(Integer.valueOf(12), "Distortion N");
    }

    public PanasonicRawDistortionDirectory() {
        setDescriptor(new PanasonicRawDistortionDescriptor(this));
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
