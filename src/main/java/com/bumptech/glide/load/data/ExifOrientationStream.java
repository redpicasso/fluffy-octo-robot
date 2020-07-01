package com.bumptech.glide.load.data;

import androidx.annotation.NonNull;
import com.google.common.base.Ascii;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ExifOrientationStream extends FilterInputStream {
    private static final byte[] EXIF_SEGMENT = new byte[]{(byte) -1, (byte) -31, (byte) 0, Ascii.FS, (byte) 69, (byte) 120, (byte) 105, (byte) 102, (byte) 0, (byte) 0, (byte) 77, (byte) 77, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 8, (byte) 0, (byte) 1, (byte) 1, Ascii.DC2, (byte) 0, (byte) 2, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0};
    private static final int ORIENTATION_POSITION = (SEGMENT_LENGTH + 2);
    private static final int SEGMENT_LENGTH = EXIF_SEGMENT.length;
    private static final int SEGMENT_START_POSITION = 2;
    private final byte orientation;
    private int position;

    public boolean markSupported() {
        return false;
    }

    public ExifOrientationStream(InputStream inputStream, int i) {
        super(inputStream);
        if (i < -1 || i > 8) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot add invalid orientation: ");
            stringBuilder.append(i);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.orientation = (byte) i;
    }

    public void mark(int i) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001e  */
    public int read() throws java.io.IOException {
        /*
        r3 = this;
        r0 = r3.position;
        r1 = 2;
        if (r0 < r1) goto L_0x0017;
    L_0x0005:
        r2 = ORIENTATION_POSITION;
        if (r0 <= r2) goto L_0x000a;
    L_0x0009:
        goto L_0x0017;
    L_0x000a:
        if (r0 != r2) goto L_0x000f;
    L_0x000c:
        r0 = r3.orientation;
        goto L_0x001b;
    L_0x000f:
        r2 = EXIF_SEGMENT;
        r0 = r0 - r1;
        r0 = r2[r0];
        r0 = r0 & 255;
        goto L_0x001b;
    L_0x0017:
        r0 = super.read();
    L_0x001b:
        r1 = -1;
        if (r0 == r1) goto L_0x0024;
    L_0x001e:
        r1 = r3.position;
        r1 = r1 + 1;
        r3.position = r1;
    L_0x0024:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.data.ExifOrientationStream.read():int");
    }

    public int read(@NonNull byte[] bArr, int i, int i2) throws IOException {
        int read;
        int i3 = this.position;
        int i4 = ORIENTATION_POSITION;
        if (i3 > i4) {
            read = super.read(bArr, i, i2);
        } else if (i3 == i4) {
            bArr[i] = this.orientation;
            read = 1;
        } else if (i3 < 2) {
            read = super.read(bArr, i, 2 - i3);
        } else {
            i2 = Math.min(i4 - i3, i2);
            System.arraycopy(EXIF_SEGMENT, this.position - 2, bArr, i, i2);
            read = i2;
        }
        if (read > 0) {
            this.position += read;
        }
        return read;
    }

    public long skip(long j) throws IOException {
        j = super.skip(j);
        if (j > 0) {
            this.position = (int) (((long) this.position) + j);
        }
        return j;
    }

    public void reset() throws IOException {
        throw new UnsupportedOperationException();
    }
}
