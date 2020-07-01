package com.facebook.react.uimanager.layoutanimation;

enum AnimatedPropertyType {
    OPACITY,
    SCALE_X,
    SCALE_Y,
    SCALE_XY;

    public static com.facebook.react.uimanager.layoutanimation.AnimatedPropertyType fromString(java.lang.String r4) {
        /*
        r0 = r4.hashCode();
        r1 = 3;
        r2 = 2;
        r3 = 1;
        switch(r0) {
            case -1267206133: goto L_0x0029;
            case -908189618: goto L_0x001f;
            case -908189617: goto L_0x0015;
            case 1910893003: goto L_0x000b;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0033;
    L_0x000b:
        r0 = "scaleXY";
        r0 = r4.equals(r0);
        if (r0 == 0) goto L_0x0033;
    L_0x0013:
        r0 = 3;
        goto L_0x0034;
    L_0x0015:
        r0 = "scaleY";
        r0 = r4.equals(r0);
        if (r0 == 0) goto L_0x0033;
    L_0x001d:
        r0 = 2;
        goto L_0x0034;
    L_0x001f:
        r0 = "scaleX";
        r0 = r4.equals(r0);
        if (r0 == 0) goto L_0x0033;
    L_0x0027:
        r0 = 1;
        goto L_0x0034;
    L_0x0029:
        r0 = "opacity";
        r0 = r4.equals(r0);
        if (r0 == 0) goto L_0x0033;
    L_0x0031:
        r0 = 0;
        goto L_0x0034;
    L_0x0033:
        r0 = -1;
    L_0x0034:
        if (r0 == 0) goto L_0x005c;
    L_0x0036:
        if (r0 == r3) goto L_0x0059;
    L_0x0038:
        if (r0 == r2) goto L_0x0056;
    L_0x003a:
        if (r0 != r1) goto L_0x003f;
    L_0x003c:
        r4 = SCALE_XY;
        return r4;
    L_0x003f:
        r0 = new java.lang.IllegalArgumentException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Unsupported animated property: ";
        r1.append(r2);
        r1.append(r4);
        r4 = r1.toString();
        r0.<init>(r4);
        throw r0;
    L_0x0056:
        r4 = SCALE_Y;
        return r4;
    L_0x0059:
        r4 = SCALE_X;
        return r4;
    L_0x005c:
        r4 = OPACITY;
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.layoutanimation.AnimatedPropertyType.fromString(java.lang.String):com.facebook.react.uimanager.layoutanimation.AnimatedPropertyType");
    }
}
