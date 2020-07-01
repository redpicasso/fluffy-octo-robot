package com.horcrux.svg;

class ViewBox {
    private static final int MOS_MEET = 0;
    private static final int MOS_NONE = 2;
    private static final int MOS_SLICE = 1;

    ViewBox() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00b9  */
    static android.graphics.Matrix getTransform(android.graphics.RectF r22, android.graphics.RectF r23, java.lang.String r24, int r25) {
        /*
        r0 = r22;
        r1 = r23;
        r2 = r24;
        r3 = r25;
        r4 = r0.left;
        r4 = (double) r4;
        r6 = r0.top;
        r6 = (double) r6;
        r8 = r22.width();
        r8 = (double) r8;
        r0 = r22.height();
        r10 = (double) r0;
        r0 = r1.left;
        r12 = (double) r0;
        r0 = r1.top;
        r14 = (double) r0;
        r0 = r23.width();
        r0 = (double) r0;
        r2 = r23.height();
        r2 = (double) r2;
        r16 = r14;
        r14 = r0 / r8;
        r18 = r8;
        r8 = r2 / r10;
        r4 = r4 * r14;
        r12 = r12 - r4;
        r6 = r6 * r8;
        r4 = r16 - r6;
        r6 = 2;
        r16 = r2;
        r2 = r25;
        if (r2 != r6) goto L_0x0062;
    L_0x003e:
        r2 = java.lang.Math.min(r14, r8);
        r6 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r8 <= 0) goto L_0x0053;
    L_0x0048:
        r0 = r0 / r2;
        r0 = r0 - r18;
        r6 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r0 = r0 / r6;
        r12 = r12 - r0;
        r0 = r16 / r2;
        r0 = r0 - r10;
        goto L_0x005e;
    L_0x0053:
        r6 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r8 = r18 * r2;
        r0 = r0 - r8;
        r0 = r0 / r6;
        r12 = r12 - r0;
        r10 = r10 * r2;
        r0 = r16 - r10;
    L_0x005e:
        r0 = r0 / r6;
        r4 = r4 - r0;
        r14 = r2;
        goto L_0x00be;
    L_0x0062:
        r3 = "none";
        r6 = r24;
        r7 = r6.equals(r3);
        if (r7 != 0) goto L_0x0074;
    L_0x006c:
        if (r2 != 0) goto L_0x0074;
    L_0x006e:
        r14 = java.lang.Math.min(r14, r8);
    L_0x0072:
        r2 = r14;
        goto L_0x0084;
    L_0x0074:
        r3 = r6.equals(r3);
        if (r3 != 0) goto L_0x0082;
    L_0x007a:
        r3 = 1;
        if (r2 != r3) goto L_0x0082;
    L_0x007d:
        r14 = java.lang.Math.max(r14, r8);
        goto L_0x0072;
    L_0x0082:
        r2 = r14;
        r14 = r8;
    L_0x0084:
        r7 = "xMid";
        r7 = r6.contains(r7);
        if (r7 == 0) goto L_0x0095;
    L_0x008c:
        r8 = r18 * r2;
        r7 = r0 - r8;
        r20 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r7 = r7 / r20;
        r12 = r12 + r7;
    L_0x0095:
        r7 = "xMax";
        r7 = r6.contains(r7);
        if (r7 == 0) goto L_0x00a1;
    L_0x009d:
        r8 = r18 * r2;
        r0 = r0 - r8;
        r12 = r12 + r0;
    L_0x00a1:
        r0 = "YMid";
        r0 = r6.contains(r0);
        if (r0 == 0) goto L_0x00b1;
    L_0x00a9:
        r0 = r10 * r14;
        r0 = r16 - r0;
        r7 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r0 = r0 / r7;
        r4 = r4 + r0;
    L_0x00b1:
        r0 = "YMax";
        r0 = r6.contains(r0);
        if (r0 == 0) goto L_0x00be;
    L_0x00b9:
        r10 = r10 * r14;
        r0 = r16 - r10;
        r4 = r4 + r0;
    L_0x00be:
        r0 = new android.graphics.Matrix;
        r0.<init>();
        r1 = (float) r12;
        r4 = (float) r4;
        r0.postTranslate(r1, r4);
        r1 = (float) r2;
        r2 = (float) r14;
        r0.preScale(r1, r2);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.ViewBox.getTransform(android.graphics.RectF, android.graphics.RectF, java.lang.String, int):android.graphics.Matrix");
    }
}
