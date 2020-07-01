package com.adobe.xmp.impl;

import com.adobe.xmp.XMPConst;
import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.facebook.imageutils.JfifUtil;
import com.google.common.base.Ascii;

public class Utils implements XMPConst {
    public static final int UUID_LENGTH = 36;
    public static final int UUID_SEGMENT_COUNT = 4;
    private static boolean[] xmlNameChars;
    private static boolean[] xmlNameStartChars;

    static {
        initCharTables();
    }

    private Utils() {
    }

    static boolean checkUUIDFormat(String str) {
        boolean z = false;
        if (str == null) {
            return false;
        }
        int i = 0;
        Object obj = 1;
        int i2 = 0;
        while (i < str.length()) {
            if (str.charAt(i) == '-') {
                i2++;
                obj = (obj == null || !(i == 8 || i == 13 || i == 18 || i == 23)) ? null : 1;
            }
            i++;
        }
        if (obj != null && 4 == i2 && 36 == i) {
            z = true;
        }
        return z;
    }

    public static String escapeXML(String str, boolean z, boolean z2) {
        char charAt;
        Object obj;
        for (int i = 0; i < str.length(); i++) {
            charAt = str.charAt(i);
            if (charAt == '<' || charAt == '>' || charAt == '&' || ((z2 && (charAt == 9 || charAt == 10 || charAt == 13)) || (z && charAt == '\"'))) {
                obj = 1;
                break;
            }
        }
        obj = null;
        if (obj == null) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer((str.length() * 4) / 3);
        for (int i2 = 0; i2 < str.length(); i2++) {
            charAt = str.charAt(i2);
            if (z2 && (charAt == 9 || charAt == 10 || charAt == 13)) {
                stringBuffer.append("&#x");
                stringBuffer.append(Integer.toHexString(charAt).toUpperCase());
                charAt = ';';
            } else {
                String str2;
                if (charAt == '\"') {
                    str2 = z ? "&quot;" : "\"";
                } else if (charAt == '&') {
                    str2 = "&amp;";
                } else if (charAt == '<') {
                    str2 = "&lt;";
                } else if (charAt == '>') {
                    str2 = "&gt;";
                }
                stringBuffer.append(str2);
            }
            stringBuffer.append(charAt);
        }
        return stringBuffer.toString();
    }

    private static void initCharTables() {
        xmlNameChars = new boolean[256];
        xmlNameStartChars = new boolean[256];
        int i = 0;
        while (i < xmlNameChars.length) {
            boolean[] zArr = xmlNameStartChars;
            boolean z = true;
            boolean z2 = i == 58 || ((65 <= i && i <= 90) || i == 95 || ((97 <= i && i <= 122) || ((JfifUtil.MARKER_SOFn <= i && i <= 214) || ((JfifUtil.MARKER_SOI <= i && i <= 246) || (248 <= i && i <= 255)))));
            zArr[i] = z2;
            zArr = xmlNameChars;
            if (!(xmlNameStartChars[i] || i == 45 || i == 46 || ((48 <= i && i <= 57) || i == NikonType2MakernoteDirectory.TAG_AF_INFO_2))) {
                z = false;
            }
            zArr[i] = z;
            i = (char) (i + 1);
        }
    }

    static boolean isControlChar(char c) {
        return ((c > 31 && c != Ascii.MAX) || c == 9 || c == 10 || c == 13) ? false : true;
    }

    static boolean isInternalProperty(String str, String str2) {
        if ("http://purl.org/dc/elements/1.1/".equals(str)) {
            if ("dc:format".equals(str2) || "dc:language".equals(str2)) {
                return true;
            }
        } else if ("http://ns.adobe.com/xap/1.0/".equals(str)) {
            if ("xmp:BaseURL".equals(str2) || "xmp:CreatorTool".equals(str2) || "xmp:Format".equals(str2) || "xmp:Locale".equals(str2) || "xmp:MetadataDate".equals(str2) || "xmp:ModifyDate".equals(str2)) {
                return true;
            }
        } else if (XMPConst.NS_PDF.equals(str)) {
            if ("pdf:BaseURL".equals(str2) || "pdf:Creator".equals(str2) || "pdf:ModDate".equals(str2) || "pdf:PDFVersion".equals(str2) || "pdf:Producer".equals(str2)) {
                return true;
            }
        } else if ("http://ns.adobe.com/tiff/1.0/".equals(str)) {
            if (!("tiff:ImageDescription".equals(str2) || "tiff:Artist".equals(str2) || "tiff:Copyright".equals(str2))) {
                return true;
            }
        } else if ("http://ns.adobe.com/exif/1.0/".equals(str)) {
            if (!"exif:UserComment".equals(str2)) {
                return true;
            }
        } else if ("http://ns.adobe.com/exif/1.0/aux/".equals(str)) {
            return true;
        } else {
            if (XMPConst.NS_PHOTOSHOP.equals(str)) {
                if ("photoshop:ICCProfile".equals(str2)) {
                    return true;
                }
            } else if (XMPConst.NS_CAMERARAW.equals(str)) {
                if ("crs:Version".equals(str2) || "crs:RawFileName".equals(str2) || "crs:ToneCurveName".equals(str2)) {
                    return true;
                }
            } else if (XMPConst.NS_ADOBESTOCKPHOTO.equals(str) || XMPConst.NS_XMP_MM.equals(str) || XMPConst.TYPE_TEXT.equals(str) || XMPConst.TYPE_PAGEDFILE.equals(str) || XMPConst.TYPE_GRAPHICS.equals(str) || XMPConst.TYPE_IMAGE.equals(str) || XMPConst.TYPE_FONT.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNameChar(char c) {
        return (c <= 255 && xmlNameChars[c]) || isNameStartChar(c) || ((c >= 768 && c <= 879) || (c >= 8255 && c <= 8256));
    }

    private static boolean isNameStartChar(char c) {
        return (c <= 255 && xmlNameStartChars[c]) || ((c >= 256 && c <= 767) || ((c >= 880 && c <= 893) || ((c >= 895 && c <= 8191) || ((c >= 8204 && c <= 8205) || ((c >= 8304 && c <= 8591) || ((c >= 11264 && c <= 12271) || ((c >= 12289 && c <= 55295) || ((c >= 63744 && c <= 64975) || ((c >= 65008 && c <= 65533) || (c >= Ascii.MIN && c <= 65535))))))))));
    }

    public static boolean isXMLName(String str) {
        if (str.length() > 0 && !isNameStartChar(str.charAt(0))) {
            return false;
        }
        for (int i = 1; i < str.length(); i++) {
            if (!isNameChar(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isXMLNameNS(String str) {
        if (str.length() > 0 && (!isNameStartChar(str.charAt(0)) || str.charAt(0) == ':')) {
            return false;
        }
        int i = 1;
        while (i < str.length()) {
            if (!isNameChar(str.charAt(i)) || str.charAt(i) == ':') {
                return false;
            }
            i++;
        }
        return true;
    }

    public static String normalizeLangValue(String str) {
        if (XMPConst.X_DEFAULT.equals(str)) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 1;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if (charAt != ' ') {
                if (charAt == '-' || charAt == '_') {
                    stringBuffer.append('-');
                    i++;
                } else {
                    stringBuffer.append(i != 2 ? Character.toLowerCase(str.charAt(i2)) : Character.toUpperCase(str.charAt(i2)));
                }
            }
        }
        return stringBuffer.toString();
    }

    static String removeControlChars(String str) {
        StringBuffer stringBuffer = new StringBuffer(str);
        for (int i = 0; i < stringBuffer.length(); i++) {
            if (isControlChar(stringBuffer.charAt(i))) {
                stringBuffer.setCharAt(i, ' ');
            }
        }
        return stringBuffer.toString();
    }

    static String[] splitNameAndValue(String str) {
        int indexOf = str.indexOf(61);
        String substring = str.substring(str.charAt(1) == '?' ? 2 : 1, indexOf);
        int i = indexOf + 1;
        char charAt = str.charAt(i);
        i++;
        int length = str.length() - 2;
        StringBuffer stringBuffer = new StringBuffer(length - indexOf);
        while (i < length) {
            stringBuffer.append(str.charAt(i));
            i++;
            if (str.charAt(i) == charAt) {
                i++;
            }
        }
        return new String[]{substring, stringBuffer.toString()};
    }
}
