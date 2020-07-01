package com.facebook.react.uimanager.layoutanimation;

enum InterpolatorType {
    LINEAR,
    EASE_IN,
    EASE_OUT,
    EASE_IN_EASE_OUT,
    SPRING;

    public static com.facebook.react.uimanager.layoutanimation.InterpolatorType fromString(java.lang.String r6) {
        /*
        r0 = java.util.Locale.US;
        r0 = r6.toLowerCase(r0);
        r1 = r0.hashCode();
        r2 = 4;
        r3 = 3;
        r4 = 2;
        r5 = 1;
        switch(r1) {
            case -1965056864: goto L_0x003a;
            case -1310315117: goto L_0x0030;
            case -1102672091: goto L_0x0026;
            case -895679987: goto L_0x001c;
            case 1164546989: goto L_0x0012;
            default: goto L_0x0011;
        };
    L_0x0011:
        goto L_0x0044;
    L_0x0012:
        r1 = "easeineaseout";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0044;
    L_0x001a:
        r0 = 3;
        goto L_0x0045;
    L_0x001c:
        r1 = "spring";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0044;
    L_0x0024:
        r0 = 4;
        goto L_0x0045;
    L_0x0026:
        r1 = "linear";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0044;
    L_0x002e:
        r0 = 0;
        goto L_0x0045;
    L_0x0030:
        r1 = "easein";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0044;
    L_0x0038:
        r0 = 1;
        goto L_0x0045;
    L_0x003a:
        r1 = "easeout";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0044;
    L_0x0042:
        r0 = 2;
        goto L_0x0045;
    L_0x0044:
        r0 = -1;
    L_0x0045:
        if (r0 == 0) goto L_0x0072;
    L_0x0047:
        if (r0 == r5) goto L_0x006f;
    L_0x0049:
        if (r0 == r4) goto L_0x006c;
    L_0x004b:
        if (r0 == r3) goto L_0x0069;
    L_0x004d:
        if (r0 != r2) goto L_0x0052;
    L_0x004f:
        r6 = SPRING;
        return r6;
    L_0x0052:
        r0 = new java.lang.IllegalArgumentException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Unsupported interpolation type : ";
        r1.append(r2);
        r1.append(r6);
        r6 = r1.toString();
        r0.<init>(r6);
        throw r0;
    L_0x0069:
        r6 = EASE_IN_EASE_OUT;
        return r6;
    L_0x006c:
        r6 = EASE_OUT;
        return r6;
    L_0x006f:
        r6 = EASE_IN;
        return r6;
    L_0x0072:
        r6 = LINEAR;
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.layoutanimation.InterpolatorType.fromString(java.lang.String):com.facebook.react.uimanager.layoutanimation.InterpolatorType");
    }
}
