package com.facebook.react.modules.network;

import com.facebook.common.logging.FLog;
import com.facebook.react.common.ReactConstants;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ProgressiveStringDecoder {
    private static final String EMPTY_STRING = "";
    private final CharsetDecoder mDecoder;
    private byte[] remainder = null;

    public ProgressiveStringDecoder(Charset charset) {
        this.mDecoder = charset.newDecoder();
    }

    public String decodeNext(byte[] bArr, int i) {
        Object bArr2;
        Object obj = this.remainder;
        if (obj != null) {
            Object obj2 = new byte[(obj.length + i)];
            System.arraycopy(obj, 0, obj2, 0, obj.length);
            System.arraycopy(bArr2, 0, obj2, this.remainder.length, i);
            i += this.remainder.length;
            bArr2 = obj2;
        }
        Object obj3 = 1;
        ByteBuffer wrap = ByteBuffer.wrap(bArr2, 0, i);
        CharBuffer charBuffer = null;
        obj = null;
        int i2 = 0;
        while (obj == null && i2 < 4) {
            try {
                charBuffer = this.mDecoder.decode(wrap);
                obj = 1;
            } catch (CharacterCodingException unused) {
                i2++;
                wrap = ByteBuffer.wrap(bArr2, 0, i - i2);
            }
        }
        if (obj == null || i2 <= 0) {
            obj3 = null;
        }
        if (obj3 != null) {
            this.remainder = new byte[i2];
            System.arraycopy(bArr2, i - i2, this.remainder, 0, i2);
        } else {
            this.remainder = null;
        }
        if (obj != null) {
            return new String(charBuffer.array(), 0, charBuffer.length());
        }
        FLog.w(ReactConstants.TAG, "failed to decode string from byte array");
        return "";
    }
}
