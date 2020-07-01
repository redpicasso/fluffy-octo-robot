package com.google.zxing.pdf417.decoder;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

final class BoundingBox {
    private final ResultPoint bottomLeft;
    private final ResultPoint bottomRight;
    private final BitMatrix image;
    private final int maxX;
    private final int maxY;
    private final int minX;
    private final int minY;
    private final ResultPoint topLeft;
    private final ResultPoint topRight;

    BoundingBox(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        Object obj = null;
        Object obj2 = (resultPoint == null || resultPoint2 == null) ? 1 : null;
        if (resultPoint3 == null || resultPoint4 == null) {
            obj = 1;
        }
        if (obj2 == null || obj == null) {
            if (obj2 != null) {
                resultPoint = new ResultPoint(0.0f, resultPoint3.getY());
                resultPoint2 = new ResultPoint(0.0f, resultPoint4.getY());
            } else if (obj != null) {
                resultPoint3 = new ResultPoint((float) (bitMatrix.getWidth() - 1), resultPoint.getY());
                resultPoint4 = new ResultPoint((float) (bitMatrix.getWidth() - 1), resultPoint2.getY());
            }
            this.image = bitMatrix;
            this.topLeft = resultPoint;
            this.bottomLeft = resultPoint2;
            this.topRight = resultPoint3;
            this.bottomRight = resultPoint4;
            this.minX = (int) Math.min(resultPoint.getX(), resultPoint2.getX());
            this.maxX = (int) Math.max(resultPoint3.getX(), resultPoint4.getX());
            this.minY = (int) Math.min(resultPoint.getY(), resultPoint3.getY());
            this.maxY = (int) Math.max(resultPoint2.getY(), resultPoint4.getY());
            return;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    BoundingBox(BoundingBox boundingBox) {
        this.image = boundingBox.image;
        this.topLeft = boundingBox.getTopLeft();
        this.bottomLeft = boundingBox.getBottomLeft();
        this.topRight = boundingBox.getTopRight();
        this.bottomRight = boundingBox.getBottomRight();
        this.minX = boundingBox.getMinX();
        this.maxX = boundingBox.getMaxX();
        this.minY = boundingBox.getMinY();
        this.maxY = boundingBox.getMaxY();
    }

    static BoundingBox merge(BoundingBox boundingBox, BoundingBox boundingBox2) throws NotFoundException {
        if (boundingBox == null) {
            return boundingBox2;
        }
        return boundingBox2 == null ? boundingBox : new BoundingBox(boundingBox.image, boundingBox.topLeft, boundingBox.bottomLeft, boundingBox2.topRight, boundingBox2.bottomRight);
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002d  */
    com.google.zxing.pdf417.decoder.BoundingBox addMissingRows(int r13, int r14, boolean r15) throws com.google.zxing.NotFoundException {
        /*
        r12 = this;
        r0 = r12.topLeft;
        r1 = r12.bottomLeft;
        r2 = r12.topRight;
        r3 = r12.bottomRight;
        if (r13 <= 0) goto L_0x0029;
    L_0x000a:
        if (r15 == 0) goto L_0x000e;
    L_0x000c:
        r4 = r0;
        goto L_0x000f;
    L_0x000e:
        r4 = r2;
    L_0x000f:
        r5 = r4.getY();
        r5 = (int) r5;
        r5 = r5 - r13;
        if (r5 >= 0) goto L_0x0018;
    L_0x0017:
        r5 = 0;
    L_0x0018:
        r13 = new com.google.zxing.ResultPoint;
        r4 = r4.getX();
        r5 = (float) r5;
        r13.<init>(r4, r5);
        if (r15 == 0) goto L_0x0026;
    L_0x0024:
        r8 = r13;
        goto L_0x002a;
    L_0x0026:
        r10 = r13;
        r8 = r0;
        goto L_0x002b;
    L_0x0029:
        r8 = r0;
    L_0x002a:
        r10 = r2;
    L_0x002b:
        if (r14 <= 0) goto L_0x005b;
    L_0x002d:
        if (r15 == 0) goto L_0x0032;
    L_0x002f:
        r13 = r12.bottomLeft;
        goto L_0x0034;
    L_0x0032:
        r13 = r12.bottomRight;
    L_0x0034:
        r0 = r13.getY();
        r0 = (int) r0;
        r0 = r0 + r14;
        r14 = r12.image;
        r14 = r14.getHeight();
        if (r0 < r14) goto L_0x004a;
    L_0x0042:
        r14 = r12.image;
        r14 = r14.getHeight();
        r0 = r14 + -1;
    L_0x004a:
        r14 = new com.google.zxing.ResultPoint;
        r13 = r13.getX();
        r0 = (float) r0;
        r14.<init>(r13, r0);
        if (r15 == 0) goto L_0x0058;
    L_0x0056:
        r9 = r14;
        goto L_0x005c;
    L_0x0058:
        r11 = r14;
        r9 = r1;
        goto L_0x005d;
    L_0x005b:
        r9 = r1;
    L_0x005c:
        r11 = r3;
    L_0x005d:
        r13 = new com.google.zxing.pdf417.decoder.BoundingBox;
        r7 = r12.image;
        r6 = r13;
        r6.<init>(r7, r8, r9, r10, r11);
        return r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.BoundingBox.addMissingRows(int, int, boolean):com.google.zxing.pdf417.decoder.BoundingBox");
    }

    int getMinX() {
        return this.minX;
    }

    int getMaxX() {
        return this.maxX;
    }

    int getMinY() {
        return this.minY;
    }

    int getMaxY() {
        return this.maxY;
    }

    ResultPoint getTopLeft() {
        return this.topLeft;
    }

    ResultPoint getTopRight() {
        return this.topRight;
    }

    ResultPoint getBottomLeft() {
        return this.bottomLeft;
    }

    ResultPoint getBottomRight() {
        return this.bottomRight;
    }
}
