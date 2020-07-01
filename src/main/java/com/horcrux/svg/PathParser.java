package com.horcrux.svg;

import android.graphics.Path;
import android.graphics.RectF;
import java.util.ArrayList;

class PathParser {
    static ArrayList<PathElement> elements;
    private static int i;
    private static int l;
    private static Path mPath;
    private static boolean mPenDown;
    private static float mPenDownX;
    private static float mPenDownY;
    private static float mPenX;
    private static float mPenY;
    private static float mPivotX;
    private static float mPivotY;
    static float mScale;
    private static String s;

    private static boolean is_cmd(char c) {
        switch (c) {
            case 'A':
            case 'C':
            case 'H':
            case 'L':
            case 'M':
            case 'Q':
            case 'S':
            case 'T':
            case 'V':
            case 'Z':
            case 'a':
            case 'c':
            case 'h':
            case 'l':
            case 'm':
            case 'q':
            case 's':
            case 't':
            case 'v':
            case 'z':
                return true;
            default:
                return false;
        }
    }

    private static boolean is_number_start(char c) {
        return (c >= '0' && c <= '9') || c == '.' || c == '-' || c == '+';
    }

    PathParser() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:66:0x0092 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01bc  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x01a0  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0196  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x018a  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x017e  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x016a  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0156  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0149  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x013e  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0139  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00e5  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00c3  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00a1  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x002c A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01dd  */
    static android.graphics.Path parse(java.lang.String r23) {
        /*
        r0 = new java.util.ArrayList;
        r0.<init>();
        elements = r0;
        r0 = new android.graphics.Path;
        r0.<init>();
        mPath = r0;
        r0 = r23.length();
        l = r0;
        s = r23;
        r0 = 0;
        i = r0;
        r1 = 0;
        mPenX = r1;
        mPenY = r1;
        mPivotX = r1;
        mPivotY = r1;
        mPenDownX = r1;
        mPenDownY = r1;
        mPenDown = r0;
        r2 = 32;
        r3 = 32;
    L_0x002c:
        r4 = i;
        r5 = l;
        if (r4 >= r5) goto L_0x01f3;
    L_0x0032:
        skip_spaces();
        r4 = i;
        r5 = l;
        if (r4 < r5) goto L_0x003d;
    L_0x003b:
        goto L_0x01f3;
    L_0x003d:
        r4 = 1;
        if (r3 == r2) goto L_0x0042;
    L_0x0040:
        r5 = 1;
        goto L_0x0043;
    L_0x0042:
        r5 = 0;
    L_0x0043:
        r6 = s;
        r7 = i;
        r6 = r6.charAt(r7);
        r7 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        r8 = 77;
        r9 = "UnexpectedData";
        if (r5 != 0) goto L_0x005e;
    L_0x0053:
        if (r6 == r8) goto L_0x005e;
    L_0x0055:
        if (r6 != r7) goto L_0x0058;
    L_0x0057:
        goto L_0x005e;
    L_0x0058:
        r0 = new java.lang.Error;
        r0.<init>(r9);
        throw r0;
    L_0x005e:
        r10 = is_cmd(r6);
        if (r10 == 0) goto L_0x006c;
    L_0x0064:
        r3 = i;
        r3 = r3 + r4;
        i = r3;
        r3 = r6;
    L_0x006a:
        r4 = 0;
        goto L_0x008b;
    L_0x006c:
        r6 = is_number_start(r6);
        if (r6 == 0) goto L_0x01ed;
    L_0x0072:
        if (r5 == 0) goto L_0x01ed;
    L_0x0074:
        r5 = 90;
        if (r3 == r5) goto L_0x01e7;
    L_0x0078:
        r5 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        if (r3 == r5) goto L_0x01e7;
    L_0x007c:
        if (r3 == r8) goto L_0x0080;
    L_0x007e:
        if (r3 != r7) goto L_0x006a;
    L_0x0080:
        r3 = is_absolute(r3);
        if (r3 == 0) goto L_0x0089;
    L_0x0086:
        r3 = 76;
        goto L_0x008b;
    L_0x0089:
        r3 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
    L_0x008b:
        r5 = is_absolute(r3);
        switch(r3) {
            case 65: goto L_0x01bc;
            case 67: goto L_0x01a0;
            case 72: goto L_0x0196;
            case 76: goto L_0x018a;
            case 77: goto L_0x017e;
            case 81: goto L_0x016a;
            case 83: goto L_0x0156;
            case 84: goto L_0x0149;
            case 86: goto L_0x013e;
            case 90: goto L_0x0139;
            case 97: goto L_0x0118;
            case 99: goto L_0x00fb;
            case 104: goto L_0x00f2;
            case 108: goto L_0x00e5;
            case 109: goto L_0x00d8;
            case 113: goto L_0x00c3;
            case 115: goto L_0x00ae;
            case 116: goto L_0x00a1;
            case 118: goto L_0x0098;
            case 122: goto L_0x0139;
            default: goto L_0x0092;
        };
    L_0x0092:
        r0 = new java.lang.Error;
        r0.<init>(r9);
        throw r0;
    L_0x0098:
        r6 = parse_list_number();
        line(r1, r6);
        goto L_0x01db;
    L_0x00a1:
        r6 = parse_list_number();
        r9 = parse_list_number();
        smoothQuadraticBezierCurve(r6, r9);
        goto L_0x01db;
    L_0x00ae:
        r6 = parse_list_number();
        r9 = parse_list_number();
        r10 = parse_list_number();
        r11 = parse_list_number();
        smoothCurve(r6, r9, r10, r11);
        goto L_0x01db;
    L_0x00c3:
        r6 = parse_list_number();
        r9 = parse_list_number();
        r10 = parse_list_number();
        r11 = parse_list_number();
        quadraticBezierCurve(r6, r9, r10, r11);
        goto L_0x01db;
    L_0x00d8:
        r6 = parse_list_number();
        r9 = parse_list_number();
        move(r6, r9);
        goto L_0x01db;
    L_0x00e5:
        r6 = parse_list_number();
        r9 = parse_list_number();
        line(r6, r9);
        goto L_0x01db;
    L_0x00f2:
        r6 = parse_list_number();
        line(r6, r1);
        goto L_0x01db;
    L_0x00fb:
        r9 = parse_list_number();
        r10 = parse_list_number();
        r11 = parse_list_number();
        r12 = parse_list_number();
        r13 = parse_list_number();
        r14 = parse_list_number();
        curve(r9, r10, r11, r12, r13, r14);
        goto L_0x01db;
    L_0x0118:
        r15 = parse_list_number();
        r16 = parse_list_number();
        r17 = parse_list_number();
        r18 = parse_flag();
        r19 = parse_flag();
        r20 = parse_list_number();
        r21 = parse_list_number();
        arc(r15, r16, r17, r18, r19, r20, r21);
        goto L_0x01db;
    L_0x0139:
        close();
        goto L_0x01db;
    L_0x013e:
        r6 = mPenX;
        r9 = parse_list_number();
        lineTo(r6, r9);
        goto L_0x01db;
    L_0x0149:
        r6 = parse_list_number();
        r9 = parse_list_number();
        smoothQuadraticBezierCurveTo(r6, r9);
        goto L_0x01db;
    L_0x0156:
        r6 = parse_list_number();
        r9 = parse_list_number();
        r10 = parse_list_number();
        r11 = parse_list_number();
        smoothCurveTo(r6, r9, r10, r11);
        goto L_0x01db;
    L_0x016a:
        r6 = parse_list_number();
        r9 = parse_list_number();
        r10 = parse_list_number();
        r11 = parse_list_number();
        quadraticBezierCurveTo(r6, r9, r10, r11);
        goto L_0x01db;
    L_0x017e:
        r6 = parse_list_number();
        r9 = parse_list_number();
        moveTo(r6, r9);
        goto L_0x01db;
    L_0x018a:
        r6 = parse_list_number();
        r9 = parse_list_number();
        lineTo(r6, r9);
        goto L_0x01db;
    L_0x0196:
        r6 = parse_list_number();
        r9 = mPenY;
        lineTo(r6, r9);
        goto L_0x01db;
    L_0x01a0:
        r10 = parse_list_number();
        r11 = parse_list_number();
        r12 = parse_list_number();
        r13 = parse_list_number();
        r14 = parse_list_number();
        r15 = parse_list_number();
        curveTo(r10, r11, r12, r13, r14, r15);
        goto L_0x01db;
    L_0x01bc:
        r16 = parse_list_number();
        r17 = parse_list_number();
        r18 = parse_list_number();
        r19 = parse_flag();
        r20 = parse_flag();
        r21 = parse_list_number();
        r22 = parse_list_number();
        arcTo(r16, r17, r18, r19, r20, r21, r22);
    L_0x01db:
        if (r4 == 0) goto L_0x002c;
    L_0x01dd:
        if (r5 == 0) goto L_0x01e3;
    L_0x01df:
        r3 = 77;
        goto L_0x002c;
    L_0x01e3:
        r3 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        goto L_0x002c;
    L_0x01e7:
        r0 = new java.lang.Error;
        r0.<init>(r9);
        throw r0;
    L_0x01ed:
        r0 = new java.lang.Error;
        r0.<init>(r9);
        throw r0;
    L_0x01f3:
        r0 = mPath;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.PathParser.parse(java.lang.String):android.graphics.Path");
    }

    private static void move(float f, float f2) {
        moveTo(f + mPenX, f2 + mPenY);
    }

    private static void moveTo(float f, float f2) {
        mPenX = f;
        mPivotX = f;
        mPenDownX = f;
        mPenY = f2;
        mPivotY = f2;
        mPenDownY = f2;
        Path path = mPath;
        float f3 = mScale;
        path.moveTo(f * f3, f3 * f2);
        elements.add(new PathElement(ElementType.kCGPathElementMoveToPoint, new Point[]{new Point((double) f, (double) f2)}));
    }

    private static void line(float f, float f2) {
        lineTo(f + mPenX, f2 + mPenY);
    }

    private static void lineTo(float f, float f2) {
        setPenDown();
        mPenX = f;
        mPivotX = f;
        mPenY = f2;
        mPivotY = f2;
        Path path = mPath;
        float f3 = mScale;
        path.lineTo(f * f3, f3 * f2);
        elements.add(new PathElement(ElementType.kCGPathElementAddLineToPoint, new Point[]{new Point((double) f, (double) f2)}));
    }

    private static void curve(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = mPenX;
        f += f7;
        float f8 = mPenY;
        curveTo(f, f2 + f8, f3 + f7, f4 + f8, f5 + f7, f6 + f8);
    }

    private static void curveTo(float f, float f2, float f3, float f4, float f5, float f6) {
        mPivotX = f3;
        mPivotY = f4;
        cubicTo(f, f2, f3, f4, f5, f6);
    }

    private static void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        setPenDown();
        mPenX = f5;
        mPenY = f6;
        Path path = mPath;
        float f7 = mScale;
        path.cubicTo(f * f7, f2 * f7, f3 * f7, f4 * f7, f5 * f7, f6 * f7);
        elements.add(new PathElement(ElementType.kCGPathElementAddCurveToPoint, new Point[]{new Point((double) f, (double) f2), new Point((double) f3, (double) f4), new Point((double) f5, (double) f6)}));
    }

    private static void smoothCurve(float f, float f2, float f3, float f4) {
        float f5 = mPenX;
        f += f5;
        float f6 = mPenY;
        smoothCurveTo(f, f2 + f6, f3 + f5, f4 + f6);
    }

    private static void smoothCurveTo(float f, float f2, float f3, float f4) {
        float f5 = (mPenX * 2.0f) - mPivotX;
        float f6 = (mPenY * 2.0f) - mPivotY;
        mPivotX = f;
        mPivotY = f2;
        cubicTo(f5, f6, f, f2, f3, f4);
    }

    private static void quadraticBezierCurve(float f, float f2, float f3, float f4) {
        float f5 = mPenX;
        f += f5;
        float f6 = mPenY;
        quadraticBezierCurveTo(f, f2 + f6, f3 + f5, f4 + f6);
    }

    private static void quadraticBezierCurveTo(float f, float f2, float f3, float f4) {
        mPivotX = f;
        mPivotY = f2;
        f *= 2.0f;
        f2 *= 2.0f;
        cubicTo((mPenX + f) / 3.0f, (mPenY + f2) / 3.0f, (f3 + f) / 3.0f, (f4 + f2) / 3.0f, f3, f4);
    }

    private static void smoothQuadraticBezierCurve(float f, float f2) {
        smoothQuadraticBezierCurveTo(f + mPenX, f2 + mPenY);
    }

    private static void smoothQuadraticBezierCurveTo(float f, float f2) {
        quadraticBezierCurveTo((mPenX * 2.0f) - mPivotX, (mPenY * 2.0f) - mPivotY, f, f2);
    }

    private static void arc(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
        arcTo(f, f2, f3, z, z2, f4 + mPenX, f5 + mPenY);
    }

    private static void arcTo(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
        boolean z3 = z;
        boolean z4 = z2;
        float f6 = mPenX;
        float f7 = mPenY;
        float f8 = f2 == 0.0f ? f == 0.0f ? f5 - f7 : f : f2;
        f8 = Math.abs(f8);
        float abs = Math.abs(f == 0.0f ? f4 - f6 : f);
        if (abs == 0.0f || f8 == 0.0f || (f4 == f6 && f5 == f7)) {
            lineTo(f4, f5);
            return;
        }
        float f9;
        float toRadians = (float) Math.toRadians((double) f3);
        double d = (double) toRadians;
        float cos = (float) Math.cos(d);
        float sin = (float) Math.sin(d);
        float f10 = f4 - f6;
        float f11 = f5 - f7;
        float f12 = ((cos * f10) / 2.0f) + ((sin * f11) / 2.0f);
        float f13 = -sin;
        float f14 = ((f13 * f10) / 2.0f) + ((cos * f11) / 2.0f);
        float f15 = abs * abs;
        float f16 = (f15 * f8) * f8;
        float f17 = ((f8 * f8) * f12) * f12;
        f15 = (f15 * f14) * f14;
        float f18 = (f16 - f15) - f17;
        if (f18 < 0.0f) {
            f16 = f13;
            f12 = (float) Math.sqrt((double) (1.0f - (f18 / f16)));
            abs *= f12;
            f8 *= f12;
            f9 = f11 / 2.0f;
            f12 = f10 / 2.0f;
        } else {
            f16 = f13;
            f9 = (float) Math.sqrt((double) (f18 / (f15 + f17)));
            if (z3 == z4) {
                f9 = -f9;
            }
            f13 = (((-f9) * f14) * abs) / f8;
            f9 = ((f9 * f12) * f8) / abs;
            f12 = ((cos * f13) - (sin * f9)) + (f10 / 2.0f);
            f9 = (f11 / 2.0f) + ((f13 * sin) + (f9 * cos));
        }
        f13 = cos / abs;
        sin /= abs;
        f14 = f16 / f8;
        cos /= f8;
        float f19 = -f12;
        float f20 = -f9;
        f2 = f8;
        f = abs;
        f16 = toRadians;
        f8 = (float) Math.atan2((double) ((f14 * f19) + (cos * f20)), (double) ((f19 * f13) + (f20 * sin)));
        float f21 = f10 - f12;
        f19 = f11 - f9;
        abs = (float) Math.atan2((double) ((f14 * f21) + (cos * f19)), (double) ((f13 * f21) + (sin * f19)));
        f21 = f12 + f6;
        f19 = f9 + f7;
        f10 += f6;
        f11 += f7;
        setPenDown();
        mPivotX = f10;
        mPenX = f10;
        mPivotY = f11;
        mPenY = f11;
        if (f == f2 && f16 == 0.0f) {
            f6 = (float) Math.toDegrees((double) f8);
            f7 = Math.abs((f6 - ((float) Math.toDegrees((double) abs))) % 360.0f);
            if (z ? f7 >= 180.0f : f7 <= 180.0f) {
                f7 = 360.0f - f7;
            }
            if (!z2) {
                f7 = -f7;
            }
            abs = f21 - f;
            f20 = mScale;
            mPath.arcTo(new RectF(abs * f20, (f19 - f) * f20, (f21 + f) * f20, (f19 + f) * f20), f6, f7);
            elements.add(new PathElement(ElementType.kCGPathElementAddCurveToPoint, new Point[]{new Point((double) f10, (double) f11)}));
        } else {
            arcToBezier(f21, f19, f, f2, f8, abs, z2, f16);
        }
    }

    private static void close() {
        if (mPenDown) {
            mPenX = mPenDownX;
            mPenY = mPenDownY;
            mPenDown = false;
            mPath.close();
            elements.add(new PathElement(ElementType.kCGPathElementCloseSubpath, new Point[]{new Point((double) mPenX, (double) mPenY)}));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0069 A:{LOOP_END, LOOP:0: B:10:0x0067->B:11:0x0069} */
    private static void arcToBezier(float r24, float r25, float r26, float r27, float r28, float r29, boolean r30, float r31) {
        /*
        r0 = r28;
        r1 = r31;
        r1 = (double) r1;
        r3 = java.lang.Math.cos(r1);
        r3 = (float) r3;
        r1 = java.lang.Math.sin(r1);
        r1 = (float) r1;
        r2 = r3 * r26;
        r4 = -r1;
        r4 = r4 * r27;
        r1 = r1 * r26;
        r3 = r3 * r27;
        r5 = r29 - r0;
        r6 = 4618760256179416344; // 0x401921fb54442d18 float:3.37028055E12 double:6.283185307179586;
        r8 = 0;
        r9 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r9 >= 0) goto L_0x002a;
    L_0x0024:
        if (r30 == 0) goto L_0x002a;
    L_0x0026:
        r8 = (double) r5;
        r8 = r8 + r6;
    L_0x0028:
        r5 = (float) r8;
        goto L_0x0033;
    L_0x002a:
        r8 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r8 <= 0) goto L_0x0033;
    L_0x002e:
        if (r30 != 0) goto L_0x0033;
    L_0x0030:
        r8 = (double) r5;
        r8 = r8 - r6;
        goto L_0x0028;
    L_0x0033:
        r6 = (double) r5;
        r8 = 4609753056924675352; // 0x3ff921fb54442d18 float:3.37028055E12 double:1.5707963267948966;
        r6 = r6 / r8;
        r6 = round(r6);
        r6 = java.lang.Math.abs(r6);
        r6 = java.lang.Math.ceil(r6);
        r6 = (int) r6;
        r7 = (float) r6;
        r5 = r5 / r7;
        r7 = 4608683618675807573; // 0x3ff5555555555555 float:1.46601547E13 double:1.3333333333333333;
        r9 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r9 = r5 / r9;
        r9 = (double) r9;
        r9 = java.lang.Math.tan(r9);
        r9 = r9 * r7;
        r7 = (float) r9;
        r8 = (double) r0;
        r10 = java.lang.Math.cos(r8);
        r10 = (float) r10;
        r8 = java.lang.Math.sin(r8);
        r8 = (float) r8;
        r11 = r0;
        r0 = 0;
    L_0x0067:
        if (r0 >= r6) goto L_0x0125;
    L_0x0069:
        r12 = r7 * r8;
        r12 = r10 - r12;
        r10 = r10 * r7;
        r8 = r8 + r10;
        r11 = r11 + r5;
        r13 = (double) r11;
        r9 = java.lang.Math.cos(r13);
        r10 = (float) r9;
        r13 = java.lang.Math.sin(r13);
        r9 = (float) r13;
        r13 = r7 * r9;
        r13 = r13 + r10;
        r14 = r7 * r10;
        r14 = r9 - r14;
        r15 = r2 * r12;
        r15 = r24 + r15;
        r16 = r4 * r8;
        r15 = r15 + r16;
        r12 = r12 * r1;
        r12 = r25 + r12;
        r8 = r8 * r3;
        r12 = r12 + r8;
        r8 = r2 * r13;
        r8 = r24 + r8;
        r16 = r4 * r14;
        r8 = r8 + r16;
        r13 = r13 * r1;
        r13 = r25 + r13;
        r14 = r14 * r3;
        r13 = r13 + r14;
        r14 = r2 * r10;
        r14 = r24 + r14;
        r16 = r4 * r9;
        r14 = r14 + r16;
        r16 = r1 * r10;
        r16 = r25 + r16;
        r17 = r3 * r9;
        r31 = r1;
        r1 = r16 + r17;
        r16 = mPath;
        r17 = mScale;
        r18 = r15 * r17;
        r19 = r12 * r17;
        r20 = r8 * r17;
        r21 = r13 * r17;
        r22 = r14 * r17;
        r23 = r1 * r17;
        r17 = r18;
        r18 = r19;
        r19 = r20;
        r20 = r21;
        r21 = r22;
        r22 = r23;
        r16.cubicTo(r17, r18, r19, r20, r21, r22);
        r16 = r2;
        r2 = elements;
        r27 = r3;
        r3 = new com.horcrux.svg.PathElement;
        r17 = r4;
        r4 = com.horcrux.svg.ElementType.kCGPathElementAddCurveToPoint;
        r29 = r5;
        r5 = 3;
        r5 = new com.horcrux.svg.Point[r5];
        r18 = r6;
        r6 = new com.horcrux.svg.Point;
        r20 = r9;
        r19 = r10;
        r9 = (double) r15;
        r28 = r11;
        r11 = (double) r12;
        r6.<init>(r9, r11);
        r9 = 0;
        r5[r9] = r6;
        r6 = new com.horcrux.svg.Point;
        r10 = (double) r8;
        r12 = (double) r13;
        r6.<init>(r10, r12);
        r8 = 1;
        r5[r8] = r6;
        r6 = 2;
        r8 = new com.horcrux.svg.Point;
        r10 = (double) r14;
        r12 = (double) r1;
        r8.<init>(r10, r12);
        r5[r6] = r8;
        r3.<init>(r4, r5);
        r2.add(r3);
        r0 = r0 + 1;
        r3 = r27;
        r11 = r28;
        r5 = r29;
        r1 = r31;
        r2 = r16;
        r4 = r17;
        r6 = r18;
        r10 = r19;
        r8 = r20;
        goto L_0x0067;
    L_0x0125:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.PathParser.arcToBezier(float, float, float, float, float, float, boolean, float):void");
    }

    private static void setPenDown() {
        if (!mPenDown) {
            mPenDownX = mPenX;
            mPenDownY = mPenY;
            mPenDown = true;
        }
    }

    private static double round(double d) {
        double pow = Math.pow(10.0d, 4.0d);
        return ((double) Math.round(d * pow)) / pow;
    }

    private static void skip_spaces() {
        while (true) {
            int i = i;
            if (i < l && Character.isWhitespace(s.charAt(i))) {
                i++;
            } else {
                return;
            }
        }
    }

    private static boolean is_absolute(char c) {
        return Character.isUpperCase(c);
    }

    private static boolean parse_flag() {
        skip_spaces();
        char charAt = s.charAt(i);
        if (charAt == '0' || charAt == '1') {
            i++;
            int i = i;
            if (i < l && s.charAt(i) == ',') {
                i++;
            }
            skip_spaces();
            if (charAt == '1') {
                return true;
            }
            return false;
        }
        throw new Error("UnexpectedData");
    }

    private static float parse_list_number() {
        if (i != l) {
            float parse_number = parse_number();
            skip_spaces();
            parse_list_separator();
            return parse_number;
        }
        throw new Error("UnexpectedEnd");
    }

    private static float parse_number() {
        skip_spaces();
        int i = i;
        String str = "InvalidNumber";
        if (i != l) {
            char charAt = s.charAt(i);
            if (charAt == '-' || charAt == '+') {
                i++;
                charAt = s.charAt(i);
            }
            if (charAt >= '0' && charAt <= '9') {
                skip_digits();
                int i2 = i;
                if (i2 < l) {
                    charAt = s.charAt(i2);
                }
            } else if (charAt != '.') {
                throw new Error(str);
            }
            if (charAt == '.') {
                i++;
                skip_digits();
                int i3 = i;
                if (i3 < l) {
                    charAt = s.charAt(i3);
                }
            }
            if (charAt == 'e' || charAt == 'E') {
                int i4 = i;
                if (i4 + 1 < l) {
                    charAt = s.charAt(i4 + 1);
                    if (!(charAt == 'm' || charAt == 'x')) {
                        i++;
                        charAt = s.charAt(i);
                        if (charAt == '+' || charAt == '-') {
                            i++;
                            skip_digits();
                        } else if (charAt < '0' || charAt > '9') {
                            throw new Error(str);
                        } else {
                            skip_digits();
                        }
                    }
                }
            }
            float parseFloat = Float.parseFloat(s.substring(i, i));
            if (!Float.isInfinite(parseFloat) && !Float.isNaN(parseFloat)) {
                return parseFloat;
            }
            throw new Error(str);
        }
        throw new Error(str);
    }

    private static void parse_list_separator() {
        int i = i;
        if (i < l && s.charAt(i) == ',') {
            i++;
        }
    }

    private static void skip_digits() {
        while (true) {
            int i = i;
            if (i < l && Character.isDigit(s.charAt(i))) {
                i++;
            } else {
                return;
            }
        }
    }
}
