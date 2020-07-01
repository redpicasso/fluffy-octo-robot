package com.horcrux.svg;

import com.facebook.react.bridge.ReadableArray;
import com.horcrux.svg.SVGLength.UnitType;

class PropHelper {
    private static final int inputMatrixDataSize = 6;

    PropHelper() {
    }

    static int toMatrixData(ReadableArray readableArray, float[] fArr, float f) {
        int size = readableArray.size();
        if (size != 6) {
            return size;
        }
        fArr[0] = (float) readableArray.getDouble(0);
        fArr[1] = (float) readableArray.getDouble(2);
        fArr[2] = ((float) readableArray.getDouble(4)) * f;
        fArr[3] = (float) readableArray.getDouble(1);
        fArr[4] = (float) readableArray.getDouble(3);
        fArr[5] = ((float) readableArray.getDouble(5)) * f;
        return 6;
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00aa  */
    /* JADX WARNING: Missing block: B:48:0x00a8, code:
            r12 = 1.0d;
     */
    /* JADX WARNING: Missing block: B:54:0x00c1, code:
            r7 = java.lang.Double.valueOf(r7.substring(0, r8)).doubleValue() * r12;
     */
    static double fromRelative(java.lang.String r7, double r8, double r10, double r12) {
        /*
        r7 = r7.trim();
        r0 = r7.length();
        r1 = r0 + -1;
        if (r0 == 0) goto L_0x00db;
    L_0x000c:
        r2 = "normal";
        r2 = r7.equals(r2);
        if (r2 == 0) goto L_0x0016;
    L_0x0014:
        goto L_0x00db;
    L_0x0016:
        r2 = r7.codePointAt(r1);
        r3 = 37;
        r4 = 0;
        if (r2 != r3) goto L_0x0031;
    L_0x001f:
        r7 = r7.substring(r4, r1);
        r7 = java.lang.Double.valueOf(r7);
        r10 = r7.doubleValue();
        r12 = 4636737291354636288; // 0x4059000000000000 float:0.0 double:100.0;
        r10 = r10 / r12;
        r10 = r10 * r8;
        return r10;
    L_0x0031:
        r8 = r0 + -2;
        if (r8 <= 0) goto L_0x00d2;
    L_0x0035:
        r9 = r7.substring(r8);
        r1 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r3 = -1;
        r5 = r9.hashCode();
        r6 = 3178; // 0xc6a float:4.453E-42 double:1.57E-320;
        if (r5 == r6) goto L_0x0099;
    L_0x0044:
        r6 = 3240; // 0xca8 float:4.54E-42 double:1.601E-320;
        if (r5 == r6) goto L_0x008f;
    L_0x0048:
        r6 = 3365; // 0xd25 float:4.715E-42 double:1.6625E-320;
        if (r5 == r6) goto L_0x0085;
    L_0x004c:
        r6 = 3488; // 0xda0 float:4.888E-42 double:1.7233E-320;
        if (r5 == r6) goto L_0x007b;
    L_0x0050:
        r6 = 3571; // 0xdf3 float:5.004E-42 double:1.7643E-320;
        if (r5 == r6) goto L_0x0071;
    L_0x0054:
        r6 = 3588; // 0xe04 float:5.028E-42 double:1.7727E-320;
        if (r5 == r6) goto L_0x0067;
    L_0x0058:
        r6 = 3592; // 0xe08 float:5.033E-42 double:1.7747E-320;
        if (r5 == r6) goto L_0x005d;
    L_0x005c:
        goto L_0x00a3;
    L_0x005d:
        r5 = "px";
        r9 = r9.equals(r5);
        if (r9 == 0) goto L_0x00a3;
    L_0x0065:
        r9 = 0;
        goto L_0x00a4;
    L_0x0067:
        r5 = "pt";
        r9 = r9.equals(r5);
        if (r9 == 0) goto L_0x00a3;
    L_0x006f:
        r9 = 2;
        goto L_0x00a4;
    L_0x0071:
        r5 = "pc";
        r9 = r9.equals(r5);
        if (r9 == 0) goto L_0x00a3;
    L_0x0079:
        r9 = 3;
        goto L_0x00a4;
    L_0x007b:
        r5 = "mm";
        r9 = r9.equals(r5);
        if (r9 == 0) goto L_0x00a3;
    L_0x0083:
        r9 = 4;
        goto L_0x00a4;
    L_0x0085:
        r5 = "in";
        r9 = r9.equals(r5);
        if (r9 == 0) goto L_0x00a3;
    L_0x008d:
        r9 = 6;
        goto L_0x00a4;
    L_0x008f:
        r5 = "em";
        r9 = r9.equals(r5);
        if (r9 == 0) goto L_0x00a3;
    L_0x0097:
        r9 = 1;
        goto L_0x00a4;
    L_0x0099:
        r5 = "cm";
        r9 = r9.equals(r5);
        if (r9 == 0) goto L_0x00a3;
    L_0x00a1:
        r9 = 5;
        goto L_0x00a4;
    L_0x00a3:
        r9 = -1;
    L_0x00a4:
        switch(r9) {
            case 0: goto L_0x00a8;
            case 1: goto L_0x00c1;
            case 2: goto L_0x00bf;
            case 3: goto L_0x00bc;
            case 4: goto L_0x00b6;
            case 5: goto L_0x00b0;
            case 6: goto L_0x00aa;
            default: goto L_0x00a7;
        };
    L_0x00a7:
        r8 = r0;
    L_0x00a8:
        r12 = r1;
        goto L_0x00c1;
    L_0x00aa:
        r12 = 4636033603912859648; // 0x4056800000000000 float:0.0 double:90.0;
        goto L_0x00c1;
    L_0x00b0:
        r12 = 4630183578586017914; // 0x4041b76ed677707a float:-6.8015614E13 double:35.43307;
        goto L_0x00c1;
    L_0x00b6:
        r12 = 4615161236842447043; // 0x400c58b1572580c3 float:1.81972446E14 double:3.543307;
        goto L_0x00c1;
    L_0x00bc:
        r12 = 4624633867356078080; // 0x402e000000000000 float:0.0 double:15.0;
        goto L_0x00c1;
    L_0x00bf:
        r12 = 4608308318706860032; // 0x3ff4000000000000 float:0.0 double:1.25;
    L_0x00c1:
        r7 = r7.substring(r4, r8);
        r7 = java.lang.Double.valueOf(r7);
        r7 = r7.doubleValue();
        r7 = r7 * r12;
    L_0x00cf:
        r7 = r7 * r10;
        return r7;
    L_0x00d2:
        r7 = java.lang.Double.valueOf(r7);
        r7 = r7.doubleValue();
        goto L_0x00cf;
    L_0x00db:
        r7 = 0;
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.PropHelper.fromRelative(java.lang.String, double, double, double):double");
    }

    static double fromRelative(SVGLength sVGLength, double d, double d2, double d3, double d4) {
        if (sVGLength == null) {
            return d2;
        }
        UnitType unitType = sVGLength.unit;
        double d5 = sVGLength.value;
        switch (unitType) {
            case NUMBER:
            case PX:
                d4 = 1.0d;
                break;
            case PERCENTAGE:
                d5 = (d5 / 100.0d) * d;
                break;
            case EMS:
                break;
            case EXS:
                d4 /= 2.0d;
                break;
            case CM:
                d4 = 35.43307d;
                break;
            case MM:
                d4 = 3.543307d;
                break;
            case IN:
                d4 = 90.0d;
                break;
            case PT:
                d4 = 1.25d;
                break;
            case PC:
                d4 = 15.0d;
                break;
        }
        d5 *= d4;
        d5 *= d3;
        return d5 + d2;
    }
}
