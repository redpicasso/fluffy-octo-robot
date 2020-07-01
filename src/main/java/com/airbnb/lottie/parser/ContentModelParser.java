package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader.Options;

class ContentModelParser {
    private static Options NAMES = Options.of("ty", "d");

    private ContentModelParser() {
    }

    /* JADX WARNING: Missing block: B:39:0x0094, code:
            if (r2.equals("gs") != false) goto L_0x00c0;
     */
    @androidx.annotation.Nullable
    static com.airbnb.lottie.model.content.ContentModel parse(com.airbnb.lottie.parser.moshi.JsonReader r7, com.airbnb.lottie.LottieComposition r8) throws java.io.IOException {
        /*
        r7.beginObject();
        r0 = 2;
        r1 = 2;
    L_0x0005:
        r2 = r7.hasNext();
        r3 = 1;
        r4 = 0;
        if (r2 == 0) goto L_0x0028;
    L_0x000d:
        r2 = NAMES;
        r2 = r7.selectName(r2);
        if (r2 == 0) goto L_0x0023;
    L_0x0015:
        if (r2 == r3) goto L_0x001e;
    L_0x0017:
        r7.skipName();
        r7.skipValue();
        goto L_0x0005;
    L_0x001e:
        r1 = r7.nextInt();
        goto L_0x0005;
    L_0x0023:
        r2 = r7.nextString();
        goto L_0x0029;
    L_0x0028:
        r2 = r4;
    L_0x0029:
        if (r2 != 0) goto L_0x002c;
    L_0x002b:
        return r4;
    L_0x002c:
        r5 = -1;
        r6 = r2.hashCode();
        switch(r6) {
            case 3239: goto L_0x00b5;
            case 3270: goto L_0x00ab;
            case 3295: goto L_0x00a1;
            case 3307: goto L_0x0097;
            case 3308: goto L_0x008e;
            case 3488: goto L_0x0083;
            case 3633: goto L_0x0078;
            case 3646: goto L_0x006d;
            case 3669: goto L_0x0063;
            case 3679: goto L_0x0058;
            case 3681: goto L_0x004d;
            case 3705: goto L_0x0041;
            case 3710: goto L_0x0036;
            default: goto L_0x0034;
        };
    L_0x0034:
        goto L_0x00bf;
    L_0x0036:
        r0 = "tr";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x003e:
        r0 = 5;
        goto L_0x00c0;
    L_0x0041:
        r0 = "tm";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x0049:
        r0 = 9;
        goto L_0x00c0;
    L_0x004d:
        r0 = "st";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x0055:
        r0 = 1;
        goto L_0x00c0;
    L_0x0058:
        r0 = "sr";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x0060:
        r0 = 10;
        goto L_0x00c0;
    L_0x0063:
        r0 = "sh";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x006b:
        r0 = 6;
        goto L_0x00c0;
    L_0x006d:
        r0 = "rp";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x0075:
        r0 = 12;
        goto L_0x00c0;
    L_0x0078:
        r0 = "rc";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x0080:
        r0 = 8;
        goto L_0x00c0;
    L_0x0083:
        r0 = "mm";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x008b:
        r0 = 11;
        goto L_0x00c0;
    L_0x008e:
        r3 = "gs";
        r3 = r2.equals(r3);
        if (r3 == 0) goto L_0x00bf;
    L_0x0096:
        goto L_0x00c0;
    L_0x0097:
        r0 = "gr";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x009f:
        r0 = 0;
        goto L_0x00c0;
    L_0x00a1:
        r0 = "gf";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x00a9:
        r0 = 4;
        goto L_0x00c0;
    L_0x00ab:
        r0 = "fl";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x00b3:
        r0 = 3;
        goto L_0x00c0;
    L_0x00b5:
        r0 = "el";
        r0 = r2.equals(r0);
        if (r0 == 0) goto L_0x00bf;
    L_0x00bd:
        r0 = 7;
        goto L_0x00c0;
    L_0x00bf:
        r0 = -1;
    L_0x00c0:
        switch(r0) {
            case 0: goto L_0x0119;
            case 1: goto L_0x0114;
            case 2: goto L_0x010f;
            case 3: goto L_0x010a;
            case 4: goto L_0x0105;
            case 5: goto L_0x0100;
            case 6: goto L_0x00fb;
            case 7: goto L_0x00f6;
            case 8: goto L_0x00f1;
            case 9: goto L_0x00ec;
            case 10: goto L_0x00e7;
            case 11: goto L_0x00dd;
            case 12: goto L_0x00d8;
            default: goto L_0x00c3;
        };
    L_0x00c3:
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r0 = "Unknown shape type ";
        r8.append(r0);
        r8.append(r2);
        r8 = r8.toString();
        com.airbnb.lottie.utils.Logger.warning(r8);
        goto L_0x011d;
    L_0x00d8:
        r4 = com.airbnb.lottie.parser.RepeaterParser.parse(r7, r8);
        goto L_0x011d;
    L_0x00dd:
        r4 = com.airbnb.lottie.parser.MergePathsParser.parse(r7);
        r0 = "Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove().";
        r8.addWarning(r0);
        goto L_0x011d;
    L_0x00e7:
        r4 = com.airbnb.lottie.parser.PolystarShapeParser.parse(r7, r8);
        goto L_0x011d;
    L_0x00ec:
        r4 = com.airbnb.lottie.parser.ShapeTrimPathParser.parse(r7, r8);
        goto L_0x011d;
    L_0x00f1:
        r4 = com.airbnb.lottie.parser.RectangleShapeParser.parse(r7, r8);
        goto L_0x011d;
    L_0x00f6:
        r4 = com.airbnb.lottie.parser.CircleShapeParser.parse(r7, r8, r1);
        goto L_0x011d;
    L_0x00fb:
        r4 = com.airbnb.lottie.parser.ShapePathParser.parse(r7, r8);
        goto L_0x011d;
    L_0x0100:
        r4 = com.airbnb.lottie.parser.AnimatableTransformParser.parse(r7, r8);
        goto L_0x011d;
    L_0x0105:
        r4 = com.airbnb.lottie.parser.GradientFillParser.parse(r7, r8);
        goto L_0x011d;
    L_0x010a:
        r4 = com.airbnb.lottie.parser.ShapeFillParser.parse(r7, r8);
        goto L_0x011d;
    L_0x010f:
        r4 = com.airbnb.lottie.parser.GradientStrokeParser.parse(r7, r8);
        goto L_0x011d;
    L_0x0114:
        r4 = com.airbnb.lottie.parser.ShapeStrokeParser.parse(r7, r8);
        goto L_0x011d;
    L_0x0119:
        r4 = com.airbnb.lottie.parser.ShapeGroupParser.parse(r7, r8);
    L_0x011d:
        r8 = r7.hasNext();
        if (r8 == 0) goto L_0x0127;
    L_0x0123:
        r7.skipValue();
        goto L_0x011d;
    L_0x0127:
        r7.endObject();
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.ContentModelParser.parse(com.airbnb.lottie.parser.moshi.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.content.ContentModel");
    }
}
