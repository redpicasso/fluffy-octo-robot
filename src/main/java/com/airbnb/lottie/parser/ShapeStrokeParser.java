package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.JsonReader.Options;

class ShapeStrokeParser {
    private static final Options DASH_PATTERN_NAMES = Options.of("n", "v");
    private static Options NAMES = Options.of("nm", "c", "w", "o", "lc", "lj", "ml", "hd", "d");

    private ShapeStrokeParser() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008d  */
    static com.airbnb.lottie.model.content.ShapeStroke parse(com.airbnb.lottie.parser.moshi.JsonReader r17, com.airbnb.lottie.LottieComposition r18) throws java.io.IOException {
        /*
        r0 = r17;
        r3 = new java.util.ArrayList;
        r3.<init>();
        r4 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r12 = 0;
    L_0x0011:
        r13 = r17.hasNext();
        if (r13 == 0) goto L_0x0115;
    L_0x0017:
        r13 = NAMES;
        r13 = r0.selectName(r13);
        r14 = 1;
        switch(r13) {
            case 0: goto L_0x010c;
            case 1: goto L_0x0103;
            case 2: goto L_0x00fa;
            case 3: goto L_0x00f1;
            case 4: goto L_0x00e1;
            case 5: goto L_0x00d0;
            case 6: goto L_0x00c6;
            case 7: goto L_0x00bd;
            case 8: goto L_0x0028;
            default: goto L_0x0021;
        };
    L_0x0021:
        r1 = r18;
        r2 = 0;
        r17.skipValue();
        goto L_0x0011;
    L_0x0028:
        r17.beginArray();
    L_0x002b:
        r13 = r17.hasNext();
        if (r13 == 0) goto L_0x00a4;
    L_0x0031:
        r17.beginObject();
        r13 = 0;
        r15 = 0;
    L_0x0036:
        r16 = r17.hasNext();
        if (r16 == 0) goto L_0x0057;
    L_0x003c:
        r2 = DASH_PATTERN_NAMES;
        r2 = r0.selectName(r2);
        if (r2 == 0) goto L_0x0052;
    L_0x0044:
        if (r2 == r14) goto L_0x004d;
    L_0x0046:
        r17.skipName();
        r17.skipValue();
        goto L_0x0036;
    L_0x004d:
        r15 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r17, r18);
        goto L_0x0036;
    L_0x0052:
        r13 = r17.nextString();
        goto L_0x0036;
    L_0x0057:
        r17.endObject();
        r2 = r13.hashCode();
        r1 = 100;
        r14 = 2;
        if (r2 == r1) goto L_0x0080;
    L_0x0063:
        r1 = 103; // 0x67 float:1.44E-43 double:5.1E-322;
        if (r2 == r1) goto L_0x0076;
    L_0x0067:
        r1 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
        if (r2 == r1) goto L_0x006c;
    L_0x006b:
        goto L_0x008a;
    L_0x006c:
        r1 = "o";
        r1 = r13.equals(r1);
        if (r1 == 0) goto L_0x008a;
    L_0x0074:
        r1 = 0;
        goto L_0x008b;
    L_0x0076:
        r1 = "g";
        r1 = r13.equals(r1);
        if (r1 == 0) goto L_0x008a;
    L_0x007e:
        r1 = 2;
        goto L_0x008b;
    L_0x0080:
        r1 = "d";
        r1 = r13.equals(r1);
        if (r1 == 0) goto L_0x008a;
    L_0x0088:
        r1 = 1;
        goto L_0x008b;
    L_0x008a:
        r1 = -1;
    L_0x008b:
        if (r1 == 0) goto L_0x009e;
    L_0x008d:
        r2 = 1;
        if (r1 == r2) goto L_0x0095;
    L_0x0090:
        if (r1 == r14) goto L_0x0095;
    L_0x0092:
        r1 = r18;
        goto L_0x00a2;
    L_0x0095:
        r1 = r18;
        r1.setHasDashPattern(r2);
        r3.add(r15);
        goto L_0x00a2;
    L_0x009e:
        r1 = r18;
        r2 = 1;
        r5 = r15;
    L_0x00a2:
        r14 = 1;
        goto L_0x002b;
    L_0x00a4:
        r1 = r18;
        r2 = 1;
        r17.endArray();
        r13 = r3.size();
        if (r13 != r2) goto L_0x00ba;
    L_0x00b0:
        r2 = 0;
        r13 = r3.get(r2);
        r3.add(r13);
        goto L_0x0011;
    L_0x00ba:
        r2 = 0;
        goto L_0x0011;
    L_0x00bd:
        r1 = r18;
        r2 = 0;
        r12 = r17.nextBoolean();
        goto L_0x0011;
    L_0x00c6:
        r1 = r18;
        r2 = 0;
        r13 = r17.nextDouble();
        r11 = (float) r13;
        goto L_0x0011;
    L_0x00d0:
        r1 = r18;
        r2 = 0;
        r10 = com.airbnb.lottie.model.content.ShapeStroke.LineJoinType.values();
        r13 = r17.nextInt();
        r14 = 1;
        r13 = r13 - r14;
        r10 = r10[r13];
        goto L_0x0011;
    L_0x00e1:
        r1 = r18;
        r2 = 0;
        r9 = com.airbnb.lottie.model.content.ShapeStroke.LineCapType.values();
        r13 = r17.nextInt();
        r13 = r13 - r14;
        r9 = r9[r13];
        goto L_0x0011;
    L_0x00f1:
        r1 = r18;
        r2 = 0;
        r7 = com.airbnb.lottie.parser.AnimatableValueParser.parseInteger(r17, r18);
        goto L_0x0011;
    L_0x00fa:
        r1 = r18;
        r2 = 0;
        r8 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r17, r18);
        goto L_0x0011;
    L_0x0103:
        r1 = r18;
        r2 = 0;
        r6 = com.airbnb.lottie.parser.AnimatableValueParser.parseColor(r17, r18);
        goto L_0x0011;
    L_0x010c:
        r1 = r18;
        r2 = 0;
        r4 = r17.nextString();
        goto L_0x0011;
    L_0x0115:
        r13 = new com.airbnb.lottie.model.content.ShapeStroke;
        r0 = r13;
        r1 = r4;
        r2 = r5;
        r4 = r6;
        r5 = r7;
        r6 = r8;
        r7 = r9;
        r8 = r10;
        r9 = r11;
        r10 = r12;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
        return r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.ShapeStrokeParser.parse(com.airbnb.lottie.parser.moshi.JsonReader, com.airbnb.lottie.LottieComposition):com.airbnb.lottie.model.content.ShapeStroke");
    }
}
