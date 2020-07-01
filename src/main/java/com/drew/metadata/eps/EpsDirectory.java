package com.drew.metadata.eps;

import androidx.exifinterface.media.ExifInterface;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import java.util.HashMap;

public class EpsDirectory extends Directory {
    public static final int TAG_AUTHOR = 2;
    public static final int TAG_BOUNDING_BOX = 3;
    public static final int TAG_COLOR_TYPE = 30;
    public static final int TAG_CONTINUE_LINE = 36;
    public static final int TAG_COPYRIGHT = 4;
    public static final int TAG_CREATION_DATE = 5;
    public static final int TAG_CREATOR = 6;
    public static final int TAG_DOCUMENT_DATA = 16;
    public static final int TAG_DSC_VERSION = 1;
    public static final int TAG_EMULATION = 17;
    public static final int TAG_EXTENSIONS = 18;
    public static final int TAG_FOR = 7;
    public static final int TAG_IMAGE_DATA = 8;
    public static final int TAG_IMAGE_HEIGHT = 29;
    public static final int TAG_IMAGE_WIDTH = 28;
    public static final int TAG_KEYWORDS = 9;
    public static final int TAG_LANGUAGE_LEVEL = 19;
    public static final int TAG_MODIFY_DATE = 10;
    public static final int TAG_OPERATOR_INTERNVENTION = 22;
    public static final int TAG_OPERATOR_MESSAGE = 23;
    public static final int TAG_ORIENTATION = 20;
    public static final int TAG_PAGES = 11;
    public static final int TAG_PAGE_ORDER = 21;
    public static final int TAG_PROOF_MODE = 24;
    public static final int TAG_RAM_SIZE = 31;
    public static final int TAG_REQUIREMENTS = 25;
    public static final int TAG_ROUTING = 12;
    public static final int TAG_SUBJECT = 13;
    public static final int TAG_TIFF_PREVIEW_OFFSET = 33;
    public static final int TAG_TIFF_PREVIEW_SIZE = 32;
    public static final int TAG_TITLE = 14;
    public static final int TAG_VERSION = 15;
    public static final int TAG_VM_LOCATION = 26;
    public static final int TAG_VM_USAGE = 27;
    public static final int TAG_WMF_PREVIEW_OFFSET = 35;
    public static final int TAG_WMF_PREVIEW_SIZE = 34;
    @NotNull
    protected static final HashMap<String, Integer> _tagIntegerMap = new HashMap();
    @NotNull
    protected static final HashMap<Integer, String> _tagNameMap = new HashMap();

    @NotNull
    public String getName() {
        return "EPS";
    }

    static {
        HashMap hashMap = _tagIntegerMap;
        Integer valueOf = Integer.valueOf(1);
        hashMap.put("%!PS-Adobe-", valueOf);
        hashMap = _tagIntegerMap;
        Integer valueOf2 = Integer.valueOf(2);
        hashMap.put("%%Author", valueOf2);
        hashMap = _tagIntegerMap;
        Integer valueOf3 = Integer.valueOf(3);
        hashMap.put("%%BoundingBox", valueOf3);
        hashMap = _tagIntegerMap;
        Integer valueOf4 = Integer.valueOf(4);
        hashMap.put("%%Copyright", valueOf4);
        hashMap = _tagIntegerMap;
        Integer valueOf5 = Integer.valueOf(5);
        hashMap.put("%%CreationDate", valueOf5);
        hashMap = _tagIntegerMap;
        Integer valueOf6 = Integer.valueOf(6);
        hashMap.put("%%Creator", valueOf6);
        hashMap = _tagIntegerMap;
        Integer valueOf7 = Integer.valueOf(7);
        hashMap.put("%%For", valueOf7);
        _tagIntegerMap.put("%ImageData", Integer.valueOf(8));
        _tagIntegerMap.put("%%Keywords", Integer.valueOf(9));
        _tagIntegerMap.put("%%ModDate", Integer.valueOf(10));
        _tagIntegerMap.put("%%Pages", Integer.valueOf(11));
        _tagIntegerMap.put("%%Routing", Integer.valueOf(12));
        _tagIntegerMap.put("%%Subject", Integer.valueOf(13));
        _tagIntegerMap.put("%%Title", Integer.valueOf(14));
        _tagIntegerMap.put("%%Version", Integer.valueOf(15));
        _tagIntegerMap.put("%%DocumentData", Integer.valueOf(16));
        _tagIntegerMap.put("%%Emulation", Integer.valueOf(17));
        _tagIntegerMap.put("%%Extensions", Integer.valueOf(18));
        _tagIntegerMap.put("%%LanguageLevel", Integer.valueOf(19));
        _tagIntegerMap.put("%%Orientation", Integer.valueOf(20));
        _tagIntegerMap.put("%%PageOrder", Integer.valueOf(21));
        _tagIntegerMap.put("%%OperatorIntervention", Integer.valueOf(22));
        _tagIntegerMap.put("%%OperatorMessage", Integer.valueOf(23));
        _tagIntegerMap.put("%%ProofMode", Integer.valueOf(24));
        _tagIntegerMap.put("%%Requirements", Integer.valueOf(25));
        _tagIntegerMap.put("%%VMlocation", Integer.valueOf(26));
        _tagIntegerMap.put("%%VMusage", Integer.valueOf(27));
        _tagIntegerMap.put("Image Width", Integer.valueOf(28));
        _tagIntegerMap.put("Image Height", Integer.valueOf(29));
        _tagIntegerMap.put("Color Type", Integer.valueOf(30));
        _tagIntegerMap.put("Ram Size", Integer.valueOf(31));
        _tagIntegerMap.put("TIFFPreview", Integer.valueOf(32));
        _tagIntegerMap.put("TIFFPreviewOffset", Integer.valueOf(33));
        _tagIntegerMap.put("WMFPreview", Integer.valueOf(34));
        _tagIntegerMap.put("WMFPreviewOffset", Integer.valueOf(35));
        _tagIntegerMap.put("%%+", Integer.valueOf(36));
        _tagNameMap.put(Integer.valueOf(36), "Line Continuation");
        _tagNameMap.put(valueOf3, "Bounding Box");
        _tagNameMap.put(valueOf4, ExifInterface.TAG_COPYRIGHT);
        _tagNameMap.put(Integer.valueOf(16), "Document Data");
        _tagNameMap.put(Integer.valueOf(17), "Emulation");
        _tagNameMap.put(Integer.valueOf(18), "Extensions");
        _tagNameMap.put(Integer.valueOf(19), "Language Level");
        _tagNameMap.put(Integer.valueOf(20), ExifInterface.TAG_ORIENTATION);
        _tagNameMap.put(Integer.valueOf(21), "Page Order");
        _tagNameMap.put(Integer.valueOf(15), "Version");
        _tagNameMap.put(Integer.valueOf(8), "Image Data");
        _tagNameMap.put(Integer.valueOf(28), "Image Width");
        _tagNameMap.put(Integer.valueOf(29), "Image Height");
        _tagNameMap.put(Integer.valueOf(30), "Color Type");
        _tagNameMap.put(Integer.valueOf(31), "Ram Size");
        _tagNameMap.put(valueOf6, "Creator");
        _tagNameMap.put(valueOf5, "Creation Date");
        _tagNameMap.put(valueOf7, "For");
        _tagNameMap.put(Integer.valueOf(25), "Requirements");
        _tagNameMap.put(Integer.valueOf(12), "Routing");
        _tagNameMap.put(Integer.valueOf(14), "Title");
        _tagNameMap.put(valueOf, "DSC Version");
        _tagNameMap.put(Integer.valueOf(11), "Pages");
        _tagNameMap.put(Integer.valueOf(22), "Operator Intervention");
        _tagNameMap.put(Integer.valueOf(23), "Operator Message");
        _tagNameMap.put(Integer.valueOf(24), "Proof Mode");
        _tagNameMap.put(Integer.valueOf(26), "VM Location");
        _tagNameMap.put(Integer.valueOf(27), "VM Usage");
        _tagNameMap.put(valueOf2, "Author");
        _tagNameMap.put(Integer.valueOf(9), "Keywords");
        _tagNameMap.put(Integer.valueOf(10), "Modify Date");
        _tagNameMap.put(Integer.valueOf(13), "Subject");
        _tagNameMap.put(Integer.valueOf(32), "TIFF Preview Size");
        _tagNameMap.put(Integer.valueOf(33), "TIFF Preview Offset");
        _tagNameMap.put(Integer.valueOf(34), "WMF Preview Size");
        _tagNameMap.put(Integer.valueOf(35), "WMF Preview Offset");
    }

    public EpsDirectory() {
        setDescriptor(new EpsDescriptor(this));
    }

    @NotNull
    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
