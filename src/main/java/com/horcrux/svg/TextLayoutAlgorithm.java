package com.horcrux.svg;

import android.view.View;
import java.util.ArrayList;

class TextLayoutAlgorithm {

    /* renamed from: com.horcrux.svg.TextLayoutAlgorithm$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor = new int[TextAnchor.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[com.horcrux.svg.TextProperties.TextAnchor.end.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.horcrux.svg.TextProperties.TextAnchor.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor = r0;
            r0 = $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.horcrux.svg.TextProperties.TextAnchor.start;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.horcrux.svg.TextProperties.TextAnchor.middle;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.horcrux.svg.TextProperties.TextAnchor.end;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TextLayoutAlgorithm.1.<clinit>():void");
        }
    }

    /* renamed from: com.horcrux.svg.TextLayoutAlgorithm$1CharacterPositioningResolver */
    class AnonymousClass1CharacterPositioningResolver {
        private int global = 0;
        private boolean horizontal = true;
        private boolean in_text_path = false;
        private String[] resolve_dx;
        private String[] resolve_dy;
        private String[] resolve_x;
        private String[] resolve_y;
        private CharacterInformation[] result;

        AnonymousClass1CharacterPositioningResolver(CharacterInformation[] characterInformationArr, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4) {
            this.result = characterInformationArr;
            this.resolve_x = strArr;
            this.resolve_y = strArr2;
            this.resolve_dx = strArr3;
            this.resolve_dy = strArr4;
        }

        private void resolveCharacterPositioning(TextView textView) {
            TextView textView2 = textView;
            int i;
            if (textView.getClass() == TextView.class || textView.getClass() == TSpanView.class) {
                int max;
                int i2;
                i = this.global;
                String[] strArr = new String[0];
                String[] strArr2 = new String[0];
                String[] strArr3 = new String[0];
                String[] strArr4 = new String[0];
                double[] dArr = new double[0];
                if (!this.in_text_path) {
                    max = Math.max(strArr.length, strArr2.length);
                } else if (this.horizontal) {
                    max = strArr.length;
                } else {
                    max = strArr2.length;
                }
                String str = ((TSpanView) textView2).mContent;
                if (str == null) {
                    i2 = 0;
                } else {
                    i2 = str.length();
                }
                int i3 = 0;
                for (int i4 = 0; i4 < i2; i4++) {
                    int i5 = i + i4;
                    if (this.result[i5].addressable) {
                        this.result[i5].anchoredChunk = i3 < max;
                        if (i3 < strArr.length) {
                            this.resolve_x[i5] = strArr[i3];
                        }
                        String str2 = "";
                        if (this.in_text_path && !this.horizontal) {
                            this.resolve_x[i] = str2;
                        }
                        if (i3 < strArr2.length) {
                            this.resolve_y[i5] = strArr2[i3];
                        }
                        if (this.in_text_path && this.horizontal) {
                            this.resolve_y[i] = str2;
                        }
                        if (i3 < strArr3.length) {
                            this.resolve_dx[i5] = strArr3[i3];
                        }
                        if (i3 < strArr4.length) {
                            this.resolve_dy[i5] = strArr4[i3];
                        }
                        if (i3 < dArr.length) {
                            this.result[i5].rotate = dArr[i3];
                        } else if (dArr.length != 0) {
                            CharacterInformation[] characterInformationArr = this.result;
                            characterInformationArr[i5].rotate = characterInformationArr[i5 - 1].rotate;
                        }
                    }
                    i3++;
                }
            } else if (textView.getClass() == TextPathView.class) {
                this.result[this.global].anchoredChunk = true;
                this.in_text_path = true;
                for (i = 0; i < textView.getChildCount(); i++) {
                    resolveCharacterPositioning((TextView) textView2.getChildAt(i));
                }
                if (textView2 instanceof TextPathView) {
                    this.in_text_path = false;
                }
            }
        }
    }

    class CharacterInformation {
        boolean addressable = true;
        double advance;
        boolean anchoredChunk = false;
        char character;
        TextView element;
        boolean firstCharacterInResolvedDescendant = false;
        boolean hidden = false;
        int index;
        boolean middle = false;
        boolean resolved = false;
        double rotate = 0.0d;
        boolean rotateSpecified = false;
        double x = 0.0d;
        boolean xSpecified = false;
        double y = 0.0d;
        boolean ySpecified = false;

        CharacterInformation(int i, char c) {
            this.index = i;
            this.character = c;
        }
    }

    class LayoutInput {
        boolean horizontal;
        TextView text;

        LayoutInput() {
        }
    }

    TextLayoutAlgorithm() {
    }

    private void getSubTreeTypographicCharacterPositions(ArrayList<TextPathView> arrayList, ArrayList<TextView> arrayList2, StringBuilder stringBuilder, View view, TextPathView textPathView) {
        int i = 0;
        if (view instanceof TSpanView) {
            TSpanView tSpanView = (TSpanView) view;
            String str = tSpanView.mContent;
            if (str == null) {
                while (i < tSpanView.getChildCount()) {
                    getSubTreeTypographicCharacterPositions(arrayList, arrayList2, stringBuilder, tSpanView.getChildAt(i), textPathView);
                    i++;
                }
                return;
            }
            while (i < str.length()) {
                arrayList2.add(tSpanView);
                arrayList.add(textPathView);
                i++;
            }
            stringBuilder.append(str);
            return;
        }
        if (view instanceof TextPathView) {
            textPathView = (TextPathView) view;
        }
        while (i < textPathView.getChildCount()) {
            getSubTreeTypographicCharacterPositions(arrayList, arrayList2, stringBuilder, textPathView.getChildAt(i), textPathView);
            i++;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:146:0x0324  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x02e9  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x02e9  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0324  */
    /* JADX WARNING: Missing block: B:62:0x019a, code:
            if (r18 == Double.POSITIVE_INFINITY) goto L_0x019f;
     */
    /* JADX WARNING: Missing block: B:65:0x01a1, code:
            if (r4 == (r10 - 1)) goto L_0x01a3;
     */
    /* JADX WARNING: Missing block: B:66:0x01a3, code:
            r12 = com.horcrux.svg.TextProperties.TextAnchor.start;
            r13 = com.horcrux.svg.TextProperties.Direction.ltr;
            r14 = r10 - 1;
     */
    /* JADX WARNING: Missing block: B:67:0x01a9, code:
            if (r4 != r14) goto L_0x01af;
     */
    /* JADX WARNING: Missing block: B:68:0x01ab, code:
            r16 = r0;
            r18 = r5;
     */
    /* JADX WARNING: Missing block: B:69:0x01af, code:
            r2 = r11[r8].x;
            r12 = com.horcrux.svg.TextLayoutAlgorithm.AnonymousClass1.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[r12.ordinal()];
     */
    /* JADX WARNING: Missing block: B:70:0x01bc, code:
            if (r12 == 1) goto L_0x01d2;
     */
    /* JADX WARNING: Missing block: B:72:0x01bf, code:
            if (r12 == 2) goto L_0x01ca;
     */
    /* JADX WARNING: Missing block: B:74:0x01c2, code:
            if (r12 == 3) goto L_0x01c5;
     */
    /* JADX WARNING: Missing block: B:76:0x01c7, code:
            if (r13 != com.horcrux.svg.TextProperties.Direction.ltr) goto L_0x01d6;
     */
    /* JADX WARNING: Missing block: B:77:0x01ca, code:
            r12 = com.horcrux.svg.TextProperties.Direction.ltr;
            r2 = r2 - ((r18 + r16) / 2.0d);
     */
    /* JADX WARNING: Missing block: B:79:0x01d4, code:
            if (r13 != com.horcrux.svg.TextProperties.Direction.ltr) goto L_0x01d9;
     */
    /* JADX WARNING: Missing block: B:80:0x01d6, code:
            r2 = r2 - r18;
     */
    /* JADX WARNING: Missing block: B:81:0x01d9, code:
            r2 = r2 - r16;
     */
    /* JADX WARNING: Missing block: B:82:0x01db, code:
            if (r4 != r14) goto L_0x01df;
     */
    /* JADX WARNING: Missing block: B:83:0x01dd, code:
            r12 = r4;
     */
    /* JADX WARNING: Missing block: B:84:0x01df, code:
            r12 = r4 - 1;
     */
    /* JADX WARNING: Missing block: B:85:0x01e1, code:
            if (r8 > r12) goto L_0x01ed;
     */
    /* JADX WARNING: Missing block: B:86:0x01e3, code:
            r13 = r11[r8];
            r13.x += r2;
            r8 = r8 + 1;
     */
    /* JADX WARNING: Missing block: B:87:0x01ed, code:
            r8 = r4;
     */
    /* JADX WARNING: Missing block: B:88:0x01ee, code:
            r20 = r16;
            r16 = r0;
     */
    com.horcrux.svg.TextLayoutAlgorithm.CharacterInformation[] layoutText(com.horcrux.svg.TextLayoutAlgorithm.LayoutInput r27) {
        /*
        r26 = this;
        r7 = r26;
        r0 = r27;
        r8 = r0.text;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r2 = new java.util.ArrayList;
        r2.<init>();
        r9 = new java.util.ArrayList;
        r9.<init>();
        r5 = 0;
        r0 = r26;
        r1 = r9;
        r3 = r6;
        r4 = r8;
        r0.getSubTreeTypographicCharacterPositions(r1, r2, r3, r4, r5);
        r0 = r6.toString();
        r0 = r0.toCharArray();
        r10 = r0.length;
        r11 = new com.horcrux.svg.TextLayoutAlgorithm.CharacterInformation[r10];
        r12 = 0;
        r1 = 0;
    L_0x002b:
        if (r1 >= r10) goto L_0x0039;
    L_0x002d:
        r2 = new com.horcrux.svg.TextLayoutAlgorithm$CharacterInformation;
        r3 = r0[r1];
        r2.<init>(r1, r3);
        r11[r1] = r2;
        r1 = r1 + 1;
        goto L_0x002b;
    L_0x0039:
        if (r10 != 0) goto L_0x003c;
    L_0x003b:
        return r11;
    L_0x003c:
        r13 = new android.graphics.PointF[r10];
        r0 = 0;
    L_0x003f:
        r14 = 0;
        if (r0 >= r10) goto L_0x004c;
    L_0x0042:
        r1 = new android.graphics.PointF;
        r1.<init>(r14, r14);
        r13[r0] = r1;
        r0 = r0 + 1;
        goto L_0x003f;
    L_0x004c:
        r0 = 0;
    L_0x004d:
        r15 = 1;
        if (r0 >= r10) goto L_0x0080;
    L_0x0050:
        r1 = r11[r0];
        r1.addressable = r15;
        r1 = r11[r0];
        r1.middle = r12;
        r1 = r11[r0];
        if (r0 != 0) goto L_0x005d;
    L_0x005c:
        goto L_0x005e;
    L_0x005d:
        r15 = 0;
    L_0x005e:
        r1.anchoredChunk = r15;
        r1 = r11[r0];
        r1 = r1.addressable;
        if (r1 == 0) goto L_0x0072;
    L_0x0066:
        r1 = r11[r0];
        r1 = r1.middle;
        if (r1 != 0) goto L_0x0072;
    L_0x006c:
        r1 = r13[r0];
        r1.set(r14, r14);
        goto L_0x007d;
    L_0x0072:
        if (r0 <= 0) goto L_0x007d;
    L_0x0074:
        r1 = r13[r0];
        r2 = r0 + -1;
        r2 = r13[r2];
        r1.set(r2);
    L_0x007d:
        r0 = r0 + 1;
        goto L_0x004d;
    L_0x0080:
        r6 = new java.lang.String[r10];
        r5 = new java.lang.String[r10];
        r4 = new java.lang.String[r10];
        r3 = new java.lang.String[r10];
        r0 = new com.horcrux.svg.TextLayoutAlgorithm$1CharacterPositioningResolver;
        r1 = r26;
        r2 = r11;
        r16 = r3;
        r3 = r6;
        r17 = r4;
        r4 = r5;
        r18 = r5;
        r5 = r17;
        r17 = r6;
        r6 = r16;
        r0.<init>(r2, r3, r4, r5, r6);
        r0 = new android.graphics.PointF;
        r0.<init>(r14, r14);
        r1 = 0;
    L_0x00a4:
        if (r1 >= r10) goto L_0x00ef;
    L_0x00a6:
        r2 = r17[r1];
        r3 = "";
        r2 = r2.equals(r3);
        r4 = "0";
        if (r2 == 0) goto L_0x00b4;
    L_0x00b2:
        r17[r1] = r4;
    L_0x00b4:
        r2 = r18[r1];
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x00be;
    L_0x00bc:
        r18[r1] = r4;
    L_0x00be:
        r2 = r0.x;
        r3 = r17[r1];
        r3 = java.lang.Float.parseFloat(r3);
        r2 = r2 + r3;
        r0.x = r2;
        r2 = r0.y;
        r3 = r18[r1];
        r3 = java.lang.Float.parseFloat(r3);
        r2 = r2 + r3;
        r0.y = r2;
        r2 = r11[r1];
        r3 = r13[r1];
        r3 = r3.x;
        r4 = r0.x;
        r3 = r3 + r4;
        r3 = (double) r3;
        r2.x = r3;
        r2 = r11[r1];
        r3 = r13[r1];
        r3 = r3.y;
        r4 = r0.y;
        r3 = r3 + r4;
        r3 = (double) r3;
        r2.y = r3;
        r1 = r1 + 1;
        goto L_0x00a4;
    L_0x00ef:
        r1 = new com.horcrux.svg.TextLayoutAlgorithm$1TextLengthResolver;
        r1.<init>(r11);
        r1.resolveTextLength(r8);
        r0.set(r14, r14);
        r1 = 1;
    L_0x00fb:
        if (r1 >= r10) goto L_0x014e;
    L_0x00fd:
        r2 = r17[r1];
        if (r2 == 0) goto L_0x010f;
    L_0x0101:
        r2 = r17[r1];
        r2 = java.lang.Double.parseDouble(r2);
        r4 = r11[r1];
        r4 = r4.x;
        r2 = r2 - r4;
        r2 = (float) r2;
        r0.x = r2;
    L_0x010f:
        r2 = r18[r1];
        if (r2 == 0) goto L_0x0121;
    L_0x0113:
        r2 = r18[r1];
        r2 = java.lang.Double.parseDouble(r2);
        r4 = r11[r1];
        r4 = r4.y;
        r2 = r2 - r4;
        r2 = (float) r2;
        r0.y = r2;
    L_0x0121:
        r2 = r11[r1];
        r3 = r2.x;
        r5 = r0.x;
        r5 = (double) r5;
        r3 = r3 + r5;
        r2.x = r3;
        r2 = r11[r1];
        r3 = r2.y;
        r5 = r0.y;
        r5 = (double) r5;
        r3 = r3 + r5;
        r2.y = r3;
        r2 = r11[r1];
        r2 = r2.middle;
        if (r2 == 0) goto L_0x0145;
    L_0x013b:
        r2 = r11[r1];
        r2 = r2.anchoredChunk;
        if (r2 == 0) goto L_0x0145;
    L_0x0141:
        r2 = r11[r1];
        r2.anchoredChunk = r12;
    L_0x0145:
        r1 = r1 + 1;
        if (r1 >= r10) goto L_0x00fb;
    L_0x0149:
        r2 = r11[r1];
        r2.anchoredChunk = r15;
        goto L_0x00fb;
    L_0x014e:
        r4 = 0;
        r5 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
        r8 = 0;
        r16 = -4503599627370496; // 0xfff0000000000000 float:0.0 double:-Infinity;
        r18 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
        r20 = -4503599627370496; // 0xfff0000000000000 float:0.0 double:-Infinity;
    L_0x0158:
        r22 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        if (r4 >= r10) goto L_0x01f9;
    L_0x015c:
        r1 = r11[r4];
        r1 = r1.addressable;
        if (r1 != 0) goto L_0x0164;
    L_0x0162:
        goto L_0x01f2;
    L_0x0164:
        r1 = r11[r4];
        r1 = r1.anchoredChunk;
        if (r1 == 0) goto L_0x0171;
    L_0x016a:
        r18 = r5;
        r5 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
        r12 = -4503599627370496; // 0xfff0000000000000 float:0.0 double:-Infinity;
        goto L_0x0175;
    L_0x0171:
        r12 = r16;
        r16 = r20;
    L_0x0175:
        r1 = r11[r4];
        r0 = r1.x;
        r14 = r11[r4];
        r2 = r14.advance;
        r2 = r2 + r0;
        r14 = java.lang.Math.min(r0, r2);
        r5 = java.lang.Math.min(r5, r14);
        r0 = java.lang.Math.max(r0, r2);
        r0 = java.lang.Math.max(r12, r0);
        if (r4 <= 0) goto L_0x019d;
    L_0x0190:
        r2 = r11[r4];
        r2 = r2.anchoredChunk;
        if (r2 == 0) goto L_0x019d;
    L_0x0196:
        r2 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
        r12 = (r18 > r2 ? 1 : (r18 == r2 ? 0 : -1));
        if (r12 != 0) goto L_0x01a3;
    L_0x019c:
        goto L_0x019f;
    L_0x019d:
        r2 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
    L_0x019f:
        r12 = r10 + -1;
        if (r4 != r12) goto L_0x01ee;
    L_0x01a3:
        r12 = com.horcrux.svg.TextProperties.TextAnchor.start;
        r13 = com.horcrux.svg.TextProperties.Direction.ltr;
        r14 = r10 + -1;
        if (r4 != r14) goto L_0x01af;
    L_0x01ab:
        r16 = r0;
        r18 = r5;
    L_0x01af:
        r15 = r11[r8];
        r2 = r15.x;
        r15 = com.horcrux.svg.TextLayoutAlgorithm.AnonymousClass1.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;
        r12 = r12.ordinal();
        r12 = r15[r12];
        r15 = 1;
        if (r12 == r15) goto L_0x01d2;
    L_0x01be:
        r15 = 2;
        if (r12 == r15) goto L_0x01ca;
    L_0x01c1:
        r15 = 3;
        if (r12 == r15) goto L_0x01c5;
    L_0x01c4:
        goto L_0x01db;
    L_0x01c5:
        r12 = com.horcrux.svg.TextProperties.Direction.ltr;
        if (r13 != r12) goto L_0x01d6;
    L_0x01c9:
        goto L_0x01d9;
    L_0x01ca:
        r12 = com.horcrux.svg.TextProperties.Direction.ltr;
        r12 = r18 + r16;
        r12 = r12 / r22;
        r2 = r2 - r12;
        goto L_0x01db;
    L_0x01d2:
        r12 = com.horcrux.svg.TextProperties.Direction.ltr;
        if (r13 != r12) goto L_0x01d9;
    L_0x01d6:
        r2 = r2 - r18;
        goto L_0x01db;
    L_0x01d9:
        r2 = r2 - r16;
    L_0x01db:
        if (r4 != r14) goto L_0x01df;
    L_0x01dd:
        r12 = r4;
        goto L_0x01e1;
    L_0x01df:
        r12 = r4 + -1;
    L_0x01e1:
        if (r8 > r12) goto L_0x01ed;
    L_0x01e3:
        r13 = r11[r8];
        r14 = r13.x;
        r14 = r14 + r2;
        r13.x = r14;
        r8 = r8 + 1;
        goto L_0x01e1;
    L_0x01ed:
        r8 = r4;
    L_0x01ee:
        r20 = r16;
        r16 = r0;
    L_0x01f2:
        r4 = r4 + 1;
        r12 = 0;
        r14 = 0;
        r15 = 1;
        goto L_0x0158;
    L_0x01f9:
        r0 = new android.graphics.PointF;
        r2 = 0;
        r0.<init>(r2, r2);
        r2 = new android.graphics.PathMeasure;
        r2.<init>();
        r3 = 0;
        r5 = r3;
        r4 = 0;
        r6 = 0;
        r15 = 0;
    L_0x0209:
        if (r4 >= r10) goto L_0x03ac;
    L_0x020b:
        r12 = r9.get(r4);
        r12 = (com.horcrux.svg.TextPathView) r12;
        if (r12 == 0) goto L_0x034e;
    L_0x0213:
        r13 = r11[r4];
        r13 = r13.addressable;
        if (r13 == 0) goto L_0x034e;
    L_0x0219:
        r5 = r12.getTextPath(r3, r3);
        r6 = r11[r4];
        r6 = r6.middle;
        if (r6 != 0) goto L_0x0327;
    L_0x0223:
        r12.getSide();
        r6 = com.horcrux.svg.TextProperties.TextPathSide.right;
        r6 = 0;
        r2.setPath(r5, r6);
        r6 = r2.getLength();
        r13 = (double) r6;
        r6 = r12.getStartOffset();
        r16 = r2;
        r1 = r6.value;
        r6 = r11[r4];
        r17 = r5;
        r5 = r6.advance;
        r3 = r11[r4];
        r19 = r9;
        r24 = r10;
        r9 = r3.x;
        r3 = r11[r4];
        r25 = r8;
        r7 = r3.y;
        r3 = r11[r4];
        r7 = r3.rotate;
        r5 = r5 / r22;
        r9 = r9 + r5;
        r9 = r9 + r1;
        r1 = r16.isClosed();
        r2 = 0;
        if (r1 != 0) goto L_0x026a;
    L_0x025d:
        r1 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1));
        if (r1 < 0) goto L_0x0265;
    L_0x0261:
        r1 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1));
        if (r1 <= 0) goto L_0x026a;
    L_0x0265:
        r1 = r11[r4];
        r5 = 1;
        r1.hidden = r5;
    L_0x026a:
        r1 = r16.isClosed();
        if (r1 == 0) goto L_0x02e1;
    L_0x0270:
        r1 = com.horcrux.svg.TextProperties.TextAnchor.start;
        r5 = com.horcrux.svg.TextProperties.Direction.ltr;
        r6 = r11[r25];
        r6 = r6.x;
        r6 = com.horcrux.svg.TextLayoutAlgorithm.AnonymousClass1.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;
        r1 = r1.ordinal();
        r1 = r6[r1];
        r6 = 1;
        if (r1 == r6) goto L_0x02bf;
    L_0x0283:
        r6 = 2;
        if (r1 == r6) goto L_0x02ab;
    L_0x0286:
        r7 = 3;
        if (r1 == r7) goto L_0x028a;
    L_0x0289:
        goto L_0x02e1;
    L_0x028a:
        r1 = com.horcrux.svg.TextProperties.Direction.ltr;
        if (r5 != r1) goto L_0x029d;
    L_0x028e:
        r5 = -r13;
        r1 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1));
        if (r1 < 0) goto L_0x0297;
    L_0x0293:
        r1 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1));
        if (r1 <= 0) goto L_0x02e1;
    L_0x0297:
        r1 = r11[r4];
        r5 = 1;
        r1.hidden = r5;
        goto L_0x02e2;
    L_0x029d:
        r5 = 1;
        r1 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1));
        if (r1 < 0) goto L_0x02a6;
    L_0x02a2:
        r1 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1));
        if (r1 <= 0) goto L_0x02e2;
    L_0x02a6:
        r1 = r11[r4];
        r1.hidden = r5;
        goto L_0x02e2;
    L_0x02ab:
        r7 = 3;
        r1 = -r13;
        r1 = r1 / r22;
        r3 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1));
        if (r3 < 0) goto L_0x02b9;
    L_0x02b3:
        r1 = r13 / r22;
        r3 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1));
        if (r3 <= 0) goto L_0x02e1;
    L_0x02b9:
        r1 = r11[r4];
        r2 = 1;
        r1.hidden = r2;
        goto L_0x02e1;
    L_0x02bf:
        r7 = 3;
        r1 = com.horcrux.svg.TextProperties.Direction.ltr;
        if (r5 != r1) goto L_0x02d2;
    L_0x02c4:
        r1 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1));
        if (r1 < 0) goto L_0x02cc;
    L_0x02c8:
        r1 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1));
        if (r1 <= 0) goto L_0x02e1;
    L_0x02cc:
        r1 = r11[r4];
        r5 = 1;
        r1.hidden = r5;
        goto L_0x02e2;
    L_0x02d2:
        r5 = 1;
        r7 = -r13;
        r1 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1));
        if (r1 < 0) goto L_0x02dc;
    L_0x02d8:
        r1 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1));
        if (r1 <= 0) goto L_0x02e2;
    L_0x02dc:
        r1 = r11[r4];
        r1.hidden = r5;
        goto L_0x02e2;
    L_0x02e1:
        r5 = 1;
    L_0x02e2:
        r9 = r9 % r13;
        r1 = r11[r4];
        r1 = r1.hidden;
        if (r1 != 0) goto L_0x0324;
    L_0x02e9:
        r1 = 2;
        r2 = new float[r1];
        r3 = new float[r1];
        r1 = (float) r9;
        r7 = r16;
        r7.getPosTan(r1, r2, r3);
        r1 = r3[r5];
        r1 = (double) r1;
        r5 = 0;
        r3 = r3[r5];
        r8 = (double) r3;
        r1 = java.lang.Math.atan2(r1, r8);
        r8 = 4633260481411531256; // 0x404ca5dc1a63c1f8 float:4.7099186E-23 double:57.29577951308232;
        r1 = r1 * r8;
        r8 = 4636033603912859648; // 0x4056800000000000 float:0.0 double:90.0;
        r8 = r8 + r1;
        r3 = 2;
        r6 = new double[r3];
        r13 = java.lang.Math.cos(r8);
        r6[r5] = r13;
        r8 = java.lang.Math.sin(r8);
        r3 = 1;
        r6[r3] = r8;
        r3 = r11[r4];
        r5 = r3.rotate;
        r5 = r5 + r1;
        r3.rotate = r5;
        goto L_0x034a;
    L_0x0324:
        r7 = r16;
        goto L_0x034a;
    L_0x0327:
        r7 = r2;
        r17 = r5;
        r25 = r8;
        r19 = r9;
        r24 = r10;
        r1 = r11[r4];
        r2 = r4 + -1;
        r3 = r11[r2];
        r5 = r3.x;
        r1.x = r5;
        r1 = r11[r4];
        r3 = r11[r2];
        r5 = r3.y;
        r1.y = r5;
        r1 = r11[r4];
        r2 = r11[r2];
        r2 = r2.rotate;
        r1.rotate = r2;
    L_0x034a:
        r5 = r17;
        r6 = 1;
        goto L_0x0355;
    L_0x034e:
        r7 = r2;
        r25 = r8;
        r19 = r9;
        r24 = r10;
    L_0x0355:
        if (r12 != 0) goto L_0x039c;
    L_0x0357:
        r1 = r11[r4];
        r1 = r1.addressable;
        if (r1 == 0) goto L_0x039c;
    L_0x035d:
        if (r6 == 0) goto L_0x0379;
    L_0x035f:
        r1 = 0;
        r7.setPath(r5, r1);
        r2 = 2;
        r3 = new float[r2];
        r6 = r7.getLength();
        r8 = 0;
        r7.getPosTan(r6, r3, r8);
        r6 = r3[r1];
        r9 = 1;
        r3 = r3[r9];
        r0.set(r6, r3);
        r6 = 0;
        r15 = 1;
        goto L_0x037d;
    L_0x0379:
        r1 = 0;
        r2 = 2;
        r8 = 0;
        r9 = 1;
    L_0x037d:
        if (r15 == 0) goto L_0x039e;
    L_0x037f:
        r3 = r11[r4];
        r3 = r3.anchoredChunk;
        if (r3 == 0) goto L_0x0387;
    L_0x0385:
        r15 = 0;
        goto L_0x039e;
    L_0x0387:
        r3 = r11[r4];
        r12 = r3.x;
        r10 = r0.x;
        r1 = (double) r10;
        r12 = r12 + r1;
        r3.x = r12;
        r1 = r11[r4];
        r2 = r1.y;
        r10 = r0.y;
        r12 = (double) r10;
        r2 = r2 + r12;
        r1.y = r2;
        goto L_0x039e;
    L_0x039c:
        r8 = 0;
        r9 = 1;
    L_0x039e:
        r4 = r4 + 1;
        r2 = r7;
        r3 = r8;
        r9 = r19;
        r10 = r24;
        r8 = r25;
        r7 = r26;
        goto L_0x0209;
    L_0x03ac:
        return r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TextLayoutAlgorithm.layoutText(com.horcrux.svg.TextLayoutAlgorithm$LayoutInput):com.horcrux.svg.TextLayoutAlgorithm$CharacterInformation[]");
    }
}
