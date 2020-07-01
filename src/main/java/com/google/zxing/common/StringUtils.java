package com.google.zxing.common;

import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.facebook.imageutils.JfifUtil;
import com.google.zxing.DecodeHintType;
import java.nio.charset.Charset;
import java.util.Map;

public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS;
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset().name();
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    static {
        boolean z;
        if (!SHIFT_JIS.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING)) {
            if (!EUC_JP.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING)) {
                z = false;
                ASSUME_SHIFT_JIS = z;
            }
        }
        z = true;
        ASSUME_SHIFT_JIS = z;
    }

    private StringUtils() {
    }

    public static String guessEncoding(byte[] bArr, Map<DecodeHintType, ?> map) {
        byte[] bArr2 = bArr;
        Map<DecodeHintType, ?> map2 = map;
        if (map2 != null && map2.containsKey(DecodeHintType.CHARACTER_SET)) {
            return map2.get(DecodeHintType.CHARACTER_SET).toString();
        }
        int length = bArr2.length;
        int i = 0;
        Object obj = (bArr2.length > 3 && bArr2[0] == (byte) -17 && bArr2[1] == (byte) -69 && bArr2[2] == (byte) -65) ? 1 : null;
        int i2 = 0;
        int i3 = 0;
        Object obj2 = 1;
        Object obj3 = 1;
        Object obj4 = 1;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i3 < length && (obj2 != null || obj3 != null || obj4 != null)) {
            int i13 = bArr2[i3] & 255;
            if (obj4 != null) {
                if (i4 > 0) {
                    if ((i13 & 128) != 0) {
                        i4--;
                    }
                } else if ((i13 & 128) != 0) {
                    if ((i13 & 64) != 0) {
                        i4++;
                        if ((i13 & 32) == 0) {
                            i6++;
                        } else {
                            i4++;
                            if ((i13 & 16) == 0) {
                                i7++;
                            } else {
                                i4++;
                                if ((i13 & 8) == 0) {
                                    i8++;
                                }
                            }
                        }
                    }
                }
                obj4 = null;
            }
            if (obj2 != null) {
                if (i13 > 127 && i13 < 160) {
                    obj2 = null;
                } else if (i13 > 159 && (i13 < JfifUtil.MARKER_SOFn || i13 == JfifUtil.MARKER_RST7 || i13 == 247)) {
                    i10++;
                }
            }
            if (obj3 != null) {
                if (i5 > 0) {
                    if (i13 >= 64 && i13 != 127 && i13 <= 252) {
                        i5--;
                    }
                } else if (!(i13 == 128 || i13 == 160 || i13 > 239)) {
                    int i14;
                    if (i13 <= 160 || i13 >= CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY) {
                        if (i13 > 127) {
                            i5++;
                            i14 = i11 + 1;
                            if (i14 > i) {
                                i = i14;
                                i11 = i;
                            } else {
                                i11 = i14;
                            }
                        } else {
                            i11 = 0;
                        }
                        i12 = 0;
                    } else {
                        i2++;
                        i14 = i12 + 1;
                        if (i14 > i9) {
                            i9 = i14;
                            i12 = i9;
                        } else {
                            i12 = i14;
                        }
                        i11 = 0;
                    }
                }
                obj3 = null;
            }
            i3++;
            bArr2 = bArr;
        }
        if (obj4 != null && i4 > 0) {
            obj4 = null;
        }
        if (obj3 != null && i5 > 0) {
            obj3 = null;
        }
        String str = UTF8;
        if (obj4 != null && (obj != null || (i6 + i7) + i8 > 0)) {
            return str;
        }
        String str2 = SHIFT_JIS;
        if (obj3 != null && (ASSUME_SHIFT_JIS || i9 >= 3 || i >= 3)) {
            return str2;
        }
        String str3 = ISO88591;
        if (obj2 != null && obj3 != null) {
            return (!(i9 == 2 && i2 == 2) && i10 * 10 < length) ? str3 : str2;
        } else {
            if (obj2 != null) {
                return str3;
            }
            if (obj3 != null) {
                return str2;
            }
            return obj4 != null ? str : PLATFORM_DEFAULT_ENCODING;
        }
    }
}
