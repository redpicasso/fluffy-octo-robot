package com.google.zxing.pdf417.decoder;

class DetectionResultColumn {
    private static final int MAX_NEARBY_DISTANCE = 5;
    private final BoundingBox boundingBox;
    private final Codeword[] codewords;

    DetectionResultColumn(BoundingBox boundingBox) {
        this.boundingBox = new BoundingBox(boundingBox);
        this.codewords = new Codeword[((boundingBox.getMaxY() - boundingBox.getMinY()) + 1)];
    }

    final Codeword getCodewordNearby(int i) {
        Codeword codeword = getCodeword(i);
        if (codeword != null) {
            return codeword;
        }
        for (int i2 = 1; i2 < 5; i2++) {
            Codeword codeword2;
            int imageRowToCodewordIndex = imageRowToCodewordIndex(i) - i2;
            if (imageRowToCodewordIndex >= 0) {
                codeword2 = this.codewords[imageRowToCodewordIndex];
                if (codeword2 != null) {
                    return codeword2;
                }
            }
            imageRowToCodewordIndex = imageRowToCodewordIndex(i) + i2;
            Codeword[] codewordArr = this.codewords;
            if (imageRowToCodewordIndex < codewordArr.length) {
                codeword2 = codewordArr[imageRowToCodewordIndex];
                if (codeword2 != null) {
                    return codeword2;
                }
            }
        }
        return null;
    }

    final int imageRowToCodewordIndex(int i) {
        return i - this.boundingBox.getMinY();
    }

    final void setCodeword(int i, Codeword codeword) {
        this.codewords[imageRowToCodewordIndex(i)] = codeword;
    }

    final Codeword getCodeword(int i) {
        return this.codewords[imageRowToCodewordIndex(i)];
    }

    final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    final Codeword[] getCodewords() {
        return this.codewords;
    }

    /* JADX WARNING: Missing block: B:16:0x0059, code:
            if (r1 != null) goto L_0x005b;
     */
    /* JADX WARNING: Missing block: B:18:?, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:19:0x005f, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:20:0x0060, code:
            r1.addSuppressed(r0);
     */
    /* JADX WARNING: Missing block: B:21:0x0064, code:
            r0.close();
     */
    public java.lang.String toString() {
        /*
        r12 = this;
        r0 = new java.util.Formatter;
        r0.<init>();
        r1 = 0;
        r2 = r12.codewords;	 Catch:{ Throwable -> 0x0057 }
        r3 = r2.length;	 Catch:{ Throwable -> 0x0057 }
        r4 = 0;
        r5 = 0;
        r6 = 0;
    L_0x000c:
        if (r5 >= r3) goto L_0x004d;
    L_0x000e:
        r7 = r2[r5];	 Catch:{ Throwable -> 0x0057 }
        r8 = 1;
        if (r7 != 0) goto L_0x0024;
    L_0x0013:
        r7 = "%3d:    |   %n";
        r8 = new java.lang.Object[r8];	 Catch:{ Throwable -> 0x0057 }
        r9 = r6 + 1;
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ Throwable -> 0x0057 }
        r8[r4] = r6;	 Catch:{ Throwable -> 0x0057 }
        r0.format(r7, r8);	 Catch:{ Throwable -> 0x0057 }
        r6 = r9;
        goto L_0x004a;
    L_0x0024:
        r9 = "%3d: %3d|%3d%n";
        r10 = 3;
        r10 = new java.lang.Object[r10];	 Catch:{ Throwable -> 0x0057 }
        r11 = r6 + 1;
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ Throwable -> 0x0057 }
        r10[r4] = r6;	 Catch:{ Throwable -> 0x0057 }
        r6 = r7.getRowNumber();	 Catch:{ Throwable -> 0x0057 }
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ Throwable -> 0x0057 }
        r10[r8] = r6;	 Catch:{ Throwable -> 0x0057 }
        r6 = 2;
        r7 = r7.getValue();	 Catch:{ Throwable -> 0x0057 }
        r7 = java.lang.Integer.valueOf(r7);	 Catch:{ Throwable -> 0x0057 }
        r10[r6] = r7;	 Catch:{ Throwable -> 0x0057 }
        r0.format(r9, r10);	 Catch:{ Throwable -> 0x0057 }
        r6 = r11;
    L_0x004a:
        r5 = r5 + 1;
        goto L_0x000c;
    L_0x004d:
        r1 = r0.toString();	 Catch:{ Throwable -> 0x0057 }
        r0.close();
        return r1;
    L_0x0055:
        r2 = move-exception;
        goto L_0x0059;
    L_0x0057:
        r1 = move-exception;
        throw r1;	 Catch:{ all -> 0x0055 }
    L_0x0059:
        if (r1 == 0) goto L_0x0064;
    L_0x005b:
        r0.close();	 Catch:{ Throwable -> 0x005f }
        goto L_0x0067;
    L_0x005f:
        r0 = move-exception;
        r1.addSuppressed(r0);
        goto L_0x0067;
    L_0x0064:
        r0.close();
    L_0x0067:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.DetectionResultColumn.toString():java.lang.String");
    }
}
