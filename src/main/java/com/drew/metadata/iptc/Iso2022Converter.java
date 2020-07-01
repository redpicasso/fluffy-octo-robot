package com.drew.metadata.iptc;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

public final class Iso2022Converter {
    private static final int DOT = 14844066;
    private static final byte ESC = (byte) 27;
    private static final String ISO_8859_1 = "ISO-8859-1";
    private static final byte LATIN_CAPITAL_A = (byte) 65;
    private static final byte LATIN_CAPITAL_G = (byte) 71;
    private static final byte PERCENT_SIGN = (byte) 37;
    private static final String UTF_8 = "UTF-8";

    @Nullable
    public static String convertISO2022CharsetToJavaCharset(@NotNull byte[] bArr) {
        if (bArr.length > 2 && bArr[0] == (byte) 27 && bArr[1] == PERCENT_SIGN && bArr[2] == LATIN_CAPITAL_G) {
            return "UTF-8";
        }
        return (bArr.length > 3 && bArr[0] == (byte) 27 && (((bArr[3] & 255) | ((bArr[2] & 255) << 8)) | ((bArr[1] & 255) << 16)) == DOT && bArr[4] == LATIN_CAPITAL_A) ? ISO_8859_1 : null;
    }

    @Nullable
    static Charset guessCharSet(@NotNull byte[] bArr) {
        r0 = new String[3];
        int i = 0;
        r0[0] = "UTF-8";
        r0[1] = System.getProperty("file.encoding");
        r0[2] = ISO_8859_1;
        int length = r0.length;
        while (i < length) {
            Charset forName = Charset.forName(r0[i]);
            try {
                forName.newDecoder().decode(ByteBuffer.wrap(bArr));
                return forName;
            } catch (CharacterCodingException unused) {
                i++;
            }
        }
        return null;
    }

    private Iso2022Converter() {
    }
}
