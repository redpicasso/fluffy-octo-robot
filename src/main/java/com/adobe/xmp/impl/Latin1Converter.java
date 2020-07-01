package com.adobe.xmp.impl;

import com.bumptech.glide.load.Key;
import com.facebook.imageutils.JfifUtil;
import java.io.UnsupportedEncodingException;

public class Latin1Converter {
    private static final int STATE_START = 0;
    private static final int STATE_UTF8CHAR = 11;

    private Latin1Converter() {
    }

    public static ByteBuffer convert(ByteBuffer byteBuffer) {
        if (!Key.STRING_CHARSET_NAME.equals(byteBuffer.getEncoding())) {
            return byteBuffer;
        }
        byte[] bArr = new byte[8];
        ByteBuffer byteBuffer2 = new ByteBuffer((byteBuffer.length() * 4) / 3);
        int i = 0;
        int i2 = 0;
        Object obj = null;
        int i3 = 0;
        int i4 = 0;
        while (i2 < byteBuffer.length()) {
            int charAt = byteBuffer.charAt(i2);
            if (obj == 11) {
                if (i3 <= 0 || (charAt & JfifUtil.MARKER_SOFn) != 128) {
                    byteBuffer2.append(convertToUTF8(bArr[0]));
                    i2 -= i4;
                } else {
                    int i5 = i4 + 1;
                    bArr[i4] = (byte) charAt;
                    i3--;
                    if (i3 == 0) {
                        byteBuffer2.append(bArr, 0, i5);
                    } else {
                        i4 = i5;
                    }
                }
                obj = null;
                i4 = 0;
            } else if (charAt < 127) {
                byteBuffer2.append((byte) charAt);
            } else if (charAt >= JfifUtil.MARKER_SOFn) {
                int i6 = -1;
                i3 = charAt;
                while (i6 < 8 && (i3 & 128) == 128) {
                    i6++;
                    i3 <<= 1;
                }
                i3 = i4 + 1;
                bArr[i4] = (byte) charAt;
                i4 = i3;
                i3 = i6;
                obj = 11;
            } else {
                byteBuffer2.append(convertToUTF8((byte) charAt));
            }
            i2++;
        }
        if (obj == 11) {
            while (i < i4) {
                byteBuffer2.append(convertToUTF8(bArr[i]));
                i++;
            }
        }
        return byteBuffer2;
    }

    private static byte[] convertToUTF8(byte b) {
        int i = b & 255;
        if (i >= 128) {
            if (i == 129 || i == 141 || i == 143 || i == 144 || i == 157) {
                return new byte[]{(byte) 32};
            }
            try {
                return new String(new byte[]{b}, "cp1252").getBytes(Key.STRING_CHARSET_NAME);
            } catch (UnsupportedEncodingException unused) {
                return new byte[]{b};
            }
        }
    }
}
