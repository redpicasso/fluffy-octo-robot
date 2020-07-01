package com.horcrux.svg;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
import java.util.ArrayList;

class SVGLength {
    final UnitType unit;
    final double value;

    /* renamed from: com.horcrux.svg.SVGLength$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$bridge$ReadableType = new int[ReadableType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$facebook$react$bridge$ReadableType[com.facebook.react.bridge.ReadableType.Array.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.facebook.react.bridge.ReadableType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$bridge$ReadableType = r0;
            r0 = $SwitchMap$com$facebook$react$bridge$ReadableType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.bridge.ReadableType.Number;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$bridge$ReadableType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.bridge.ReadableType.String;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$react$bridge$ReadableType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.react.bridge.ReadableType.Array;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.SVGLength.1.<clinit>():void");
        }
    }

    public enum UnitType {
        UNKNOWN,
        NUMBER,
        PERCENTAGE,
        EMS,
        EXS,
        PX,
        CM,
        MM,
        IN,
        PT,
        PC
    }

    private SVGLength() {
        this.value = 0.0d;
        this.unit = UnitType.UNKNOWN;
    }

    SVGLength(double d) {
        this.value = d;
        this.unit = UnitType.NUMBER;
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00bd  */
    private SVGLength(java.lang.String r8) {
        /*
        r7 = this;
        r7.<init>();
        r8 = r8.trim();
        r0 = r8.length();
        r1 = r0 + -1;
        if (r0 == 0) goto L_0x0103;
    L_0x000f:
        r2 = "normal";
        r2 = r8.equals(r2);
        if (r2 == 0) goto L_0x0019;
    L_0x0017:
        goto L_0x0103;
    L_0x0019:
        r2 = r8.codePointAt(r1);
        r3 = 37;
        r4 = 0;
        if (r2 != r3) goto L_0x0036;
    L_0x0022:
        r0 = com.horcrux.svg.SVGLength.UnitType.PERCENTAGE;
        r7.unit = r0;
        r8 = r8.substring(r4, r1);
        r8 = java.lang.Double.valueOf(r8);
        r0 = r8.doubleValue();
        r7.value = r0;
        goto L_0x010b;
    L_0x0036:
        r1 = r0 + -2;
        if (r1 <= 0) goto L_0x00f4;
    L_0x003a:
        r2 = r8.substring(r1);
        r3 = -1;
        r5 = r2.hashCode();
        r6 = 3178; // 0xc6a float:4.453E-42 double:1.57E-320;
        if (r5 == r6) goto L_0x00aa;
    L_0x0047:
        r6 = 3240; // 0xca8 float:4.54E-42 double:1.601E-320;
        if (r5 == r6) goto L_0x00a0;
    L_0x004b:
        r6 = 3251; // 0xcb3 float:4.556E-42 double:1.606E-320;
        if (r5 == r6) goto L_0x0096;
    L_0x004f:
        r6 = 3365; // 0xd25 float:4.715E-42 double:1.6625E-320;
        if (r5 == r6) goto L_0x008c;
    L_0x0053:
        r6 = 3488; // 0xda0 float:4.888E-42 double:1.7233E-320;
        if (r5 == r6) goto L_0x0082;
    L_0x0057:
        r6 = 3571; // 0xdf3 float:5.004E-42 double:1.7643E-320;
        if (r5 == r6) goto L_0x0078;
    L_0x005b:
        r6 = 3588; // 0xe04 float:5.028E-42 double:1.7727E-320;
        if (r5 == r6) goto L_0x006e;
    L_0x005f:
        r6 = 3592; // 0xe08 float:5.033E-42 double:1.7747E-320;
        if (r5 == r6) goto L_0x0064;
    L_0x0063:
        goto L_0x00b4;
    L_0x0064:
        r5 = "px";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x00b4;
    L_0x006c:
        r2 = 0;
        goto L_0x00b5;
    L_0x006e:
        r5 = "pt";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x00b4;
    L_0x0076:
        r2 = 3;
        goto L_0x00b5;
    L_0x0078:
        r5 = "pc";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x00b4;
    L_0x0080:
        r2 = 4;
        goto L_0x00b5;
    L_0x0082:
        r5 = "mm";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x00b4;
    L_0x008a:
        r2 = 5;
        goto L_0x00b5;
    L_0x008c:
        r5 = "in";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x00b4;
    L_0x0094:
        r2 = 7;
        goto L_0x00b5;
    L_0x0096:
        r5 = "ex";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x00b4;
    L_0x009e:
        r2 = 2;
        goto L_0x00b5;
    L_0x00a0:
        r5 = "em";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x00b4;
    L_0x00a8:
        r2 = 1;
        goto L_0x00b5;
    L_0x00aa:
        r5 = "cm";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x00b4;
    L_0x00b2:
        r2 = 6;
        goto L_0x00b5;
    L_0x00b4:
        r2 = -1;
    L_0x00b5:
        switch(r2) {
            case 0: goto L_0x00e0;
            case 1: goto L_0x00db;
            case 2: goto L_0x00d6;
            case 3: goto L_0x00d1;
            case 4: goto L_0x00cc;
            case 5: goto L_0x00c7;
            case 6: goto L_0x00c2;
            case 7: goto L_0x00bd;
            default: goto L_0x00b8;
        };
    L_0x00b8:
        r1 = com.horcrux.svg.SVGLength.UnitType.NUMBER;
        r7.unit = r1;
        goto L_0x00e5;
    L_0x00bd:
        r0 = com.horcrux.svg.SVGLength.UnitType.IN;
        r7.unit = r0;
        goto L_0x00e4;
    L_0x00c2:
        r0 = com.horcrux.svg.SVGLength.UnitType.CM;
        r7.unit = r0;
        goto L_0x00e4;
    L_0x00c7:
        r0 = com.horcrux.svg.SVGLength.UnitType.MM;
        r7.unit = r0;
        goto L_0x00e4;
    L_0x00cc:
        r0 = com.horcrux.svg.SVGLength.UnitType.PC;
        r7.unit = r0;
        goto L_0x00e4;
    L_0x00d1:
        r0 = com.horcrux.svg.SVGLength.UnitType.PT;
        r7.unit = r0;
        goto L_0x00e4;
    L_0x00d6:
        r0 = com.horcrux.svg.SVGLength.UnitType.EXS;
        r7.unit = r0;
        goto L_0x00e4;
    L_0x00db:
        r0 = com.horcrux.svg.SVGLength.UnitType.EMS;
        r7.unit = r0;
        goto L_0x00e4;
    L_0x00e0:
        r0 = com.horcrux.svg.SVGLength.UnitType.NUMBER;
        r7.unit = r0;
    L_0x00e4:
        r0 = r1;
    L_0x00e5:
        r8 = r8.substring(r4, r0);
        r8 = java.lang.Double.valueOf(r8);
        r0 = r8.doubleValue();
        r7.value = r0;
        goto L_0x010b;
    L_0x00f4:
        r0 = com.horcrux.svg.SVGLength.UnitType.NUMBER;
        r7.unit = r0;
        r8 = java.lang.Double.valueOf(r8);
        r0 = r8.doubleValue();
        r7.value = r0;
        goto L_0x010b;
    L_0x0103:
        r8 = com.horcrux.svg.SVGLength.UnitType.UNKNOWN;
        r7.unit = r8;
        r0 = 0;
        r7.value = r0;
    L_0x010b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.SVGLength.<init>(java.lang.String):void");
    }

    static SVGLength from(Dynamic dynamic) {
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$bridge$ReadableType[dynamic.getType().ordinal()];
        if (i == 1) {
            return new SVGLength(dynamic.asDouble());
        }
        if (i != 2) {
            return new SVGLength();
        }
        return new SVGLength(dynamic.asString());
    }

    static String toString(Dynamic dynamic) {
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$bridge$ReadableType[dynamic.getType().ordinal()];
        if (i == 1) {
            return String.valueOf(dynamic.asDouble());
        }
        if (i != 2) {
            return null;
        }
        return dynamic.asString();
    }

    static ArrayList<SVGLength> arrayFrom(Dynamic dynamic) {
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$bridge$ReadableType[dynamic.getType().ordinal()];
        ArrayList<SVGLength> arrayList;
        if (i == 1) {
            arrayList = new ArrayList(1);
            arrayList.add(new SVGLength(dynamic.asDouble()));
            return arrayList;
        } else if (i == 2) {
            arrayList = new ArrayList(1);
            arrayList.add(new SVGLength(dynamic.asString()));
            return arrayList;
        } else if (i != 3) {
            return null;
        } else {
            ReadableArray asArray = dynamic.asArray();
            i = asArray.size();
            ArrayList<SVGLength> arrayList2 = new ArrayList(i);
            for (int i2 = 0; i2 < i; i2++) {
                arrayList2.add(from(asArray.getDynamic(i2)));
            }
            return arrayList2;
        }
    }
}
