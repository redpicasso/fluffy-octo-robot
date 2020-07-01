package com.facebook.imageutils;

import java.io.IOException;
import java.io.InputStream;

class StreamProcessor {
    StreamProcessor() {
    }

    public static int readPackedInt(InputStream inputStream, int i, boolean z) throws IOException {
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int read = inputStream.read();
            if (read != -1) {
                if (z) {
                    read = (read & 255) << (i2 * 8);
                } else {
                    i3 <<= 8;
                    read &= 255;
                }
                i3 |= read;
                i2++;
            } else {
                throw new IOException("no more bytes");
            }
        }
        return i3;
    }
}
