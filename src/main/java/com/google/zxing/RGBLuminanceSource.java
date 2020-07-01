package com.google.zxing;

public final class RGBLuminanceSource extends LuminanceSource {
    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final byte[] luminances;
    private final int top;

    public boolean isCropSupported() {
        return true;
    }

    public RGBLuminanceSource(int i, int i2, int[] iArr) {
        super(i, i2);
        this.dataWidth = i;
        this.dataHeight = i2;
        int i3 = 0;
        this.left = 0;
        this.top = 0;
        i *= i2;
        this.luminances = new byte[i];
        while (i3 < i) {
            i2 = iArr[i3];
            int i4 = (i2 >> 16) & 255;
            int i5 = (i2 >> 7) & 510;
            this.luminances[i3] = (byte) (((i4 + i5) + (i2 & 255)) / 4);
            i3++;
        }
    }

    private RGBLuminanceSource(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6) {
        super(i5, i6);
        if (i5 + i3 > i || i6 + i4 > i2) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        this.luminances = bArr;
        this.dataWidth = i;
        this.dataHeight = i2;
        this.left = i3;
        this.top = i4;
    }

    public byte[] getRow(int i, byte[] bArr) {
        if (i < 0 || i >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: ".concat(String.valueOf(i)));
        }
        Object bArr2;
        int width = getWidth();
        if (bArr2 == null || bArr2.length < width) {
            bArr2 = new byte[width];
        }
        System.arraycopy(this.luminances, ((i + this.top) * this.dataWidth) + this.left, bArr2, 0, width);
        return bArr2;
    }

    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        if (width == this.dataWidth && height == this.dataHeight) {
            return this.luminances;
        }
        int i = width * height;
        Object obj = new byte[i];
        int i2 = this.top;
        int i3 = this.dataWidth;
        i2 = (i2 * i3) + this.left;
        int i4 = 0;
        if (width == i3) {
            System.arraycopy(this.luminances, i2, obj, 0, i);
            return obj;
        }
        while (i4 < height) {
            System.arraycopy(this.luminances, i2, obj, i4 * width, width);
            i2 += this.dataWidth;
            i4++;
        }
        return obj;
    }

    public LuminanceSource crop(int i, int i2, int i3, int i4) {
        return new RGBLuminanceSource(this.luminances, this.dataWidth, this.dataHeight, this.left + i, this.top + i2, i3, i4);
    }
}
