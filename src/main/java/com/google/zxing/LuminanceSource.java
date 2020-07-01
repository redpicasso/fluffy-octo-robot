package com.google.zxing;

import com.facebook.imageutils.JfifUtil;

public abstract class LuminanceSource {
    private final int height;
    private final int width;

    public abstract byte[] getMatrix();

    public abstract byte[] getRow(int i, byte[] bArr);

    public boolean isCropSupported() {
        return false;
    }

    public boolean isRotateSupported() {
        return false;
    }

    protected LuminanceSource(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public LuminanceSource crop(int i, int i2, int i3, int i4) {
        throw new UnsupportedOperationException("This luminance source does not support cropping.");
    }

    public LuminanceSource invert() {
        return new InvertedLuminanceSource(this);
    }

    public LuminanceSource rotateCounterClockwise() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 90 degrees.");
    }

    public LuminanceSource rotateCounterClockwise45() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 45 degrees.");
    }

    public final String toString() {
        int i = this.width;
        byte[] bArr = new byte[i];
        StringBuilder stringBuilder = new StringBuilder(this.height * (i + 1));
        byte[] bArr2 = bArr;
        for (int i2 = 0; i2 < this.height; i2++) {
            bArr2 = getRow(i2, bArr2);
            for (int i3 = 0; i3 < this.width; i3++) {
                int i4 = bArr2[i3] & 255;
                char c = i4 < 64 ? '#' : i4 < 128 ? '+' : i4 < JfifUtil.MARKER_SOFn ? '.' : ' ';
                stringBuilder.append(c);
            }
            stringBuilder.append(10);
        }
        return stringBuilder.toString();
    }
}
