package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import javax.annotation.Nullable;

class InterpolationAnimatedNode extends ValueAnimatedNode {
    public static final String EXTRAPOLATE_TYPE_CLAMP = "clamp";
    public static final String EXTRAPOLATE_TYPE_EXTEND = "extend";
    public static final String EXTRAPOLATE_TYPE_IDENTITY = "identity";
    private final String mExtrapolateLeft;
    private final String mExtrapolateRight;
    private final double[] mInputRange;
    private final double[] mOutputRange;
    @Nullable
    private ValueAnimatedNode mParent;

    private static double[] fromDoubleArray(ReadableArray readableArray) {
        double[] dArr = new double[readableArray.size()];
        for (int i = 0; i < dArr.length; i++) {
            dArr[i] = readableArray.getDouble(i);
        }
        return dArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0065 A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0065 A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0065 A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00b7 A:{RETURN} */
    private static double interpolate(double r16, double r18, double r20, double r22, double r24, java.lang.String r26, java.lang.String r27) {
        /*
        r0 = r26;
        r1 = r27;
        r2 = 0;
        r3 = "Invalid extrapolation type ";
        r4 = "clamp";
        r5 = "identity";
        r6 = "extend";
        r7 = 94742715; // 0x5a5a8bb float:1.5578507E-35 double:4.68091207E-316;
        r8 = -135761730; // 0xfffffffff7e870be float:-9.428903E33 double:NaN;
        r9 = -1289044198; // 0xffffffffb32abf1a float:-3.9755015E-8 double:NaN;
        r10 = -1;
        r11 = 2;
        r12 = 1;
        r13 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r13 >= 0) goto L_0x0066;
    L_0x001d:
        r14 = r26.hashCode();
        if (r14 == r9) goto L_0x0038;
    L_0x0023:
        if (r14 == r8) goto L_0x0030;
    L_0x0025:
        if (r14 == r7) goto L_0x0028;
    L_0x0027:
        goto L_0x0040;
    L_0x0028:
        r14 = r0.equals(r4);
        if (r14 == 0) goto L_0x0040;
    L_0x002e:
        r14 = 1;
        goto L_0x0041;
    L_0x0030:
        r14 = r0.equals(r5);
        if (r14 == 0) goto L_0x0040;
    L_0x0036:
        r14 = 0;
        goto L_0x0041;
    L_0x0038:
        r14 = r0.equals(r6);
        if (r14 == 0) goto L_0x0040;
    L_0x003e:
        r14 = 2;
        goto L_0x0041;
    L_0x0040:
        r14 = -1;
    L_0x0041:
        if (r14 == 0) goto L_0x0065;
    L_0x0043:
        if (r14 == r12) goto L_0x0062;
    L_0x0045:
        if (r14 != r11) goto L_0x0048;
    L_0x0047:
        goto L_0x0066;
    L_0x0048:
        r1 = new com.facebook.react.bridge.JSApplicationIllegalArgumentException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r2.append(r3);
        r2.append(r0);
        r0 = "for left extrapolation";
        r2.append(r0);
        r0 = r2.toString();
        r1.<init>(r0);
        throw r1;
    L_0x0062:
        r14 = r18;
        goto L_0x0068;
    L_0x0065:
        return r16;
    L_0x0066:
        r14 = r16;
    L_0x0068:
        r0 = (r14 > r20 ? 1 : (r14 == r20 ? 0 : -1));
        if (r0 <= 0) goto L_0x00b3;
    L_0x006c:
        r0 = r27.hashCode();
        if (r0 == r9) goto L_0x0087;
    L_0x0072:
        if (r0 == r8) goto L_0x007f;
    L_0x0074:
        if (r0 == r7) goto L_0x0077;
    L_0x0076:
        goto L_0x008e;
    L_0x0077:
        r0 = r1.equals(r4);
        if (r0 == 0) goto L_0x008e;
    L_0x007d:
        r10 = 1;
        goto L_0x008e;
    L_0x007f:
        r0 = r1.equals(r5);
        if (r0 == 0) goto L_0x008e;
    L_0x0085:
        r10 = 0;
        goto L_0x008e;
    L_0x0087:
        r0 = r1.equals(r6);
        if (r0 == 0) goto L_0x008e;
    L_0x008d:
        r10 = 2;
    L_0x008e:
        if (r10 == 0) goto L_0x00b2;
    L_0x0090:
        if (r10 == r12) goto L_0x00af;
    L_0x0092:
        if (r10 != r11) goto L_0x0095;
    L_0x0094:
        goto L_0x00b3;
    L_0x0095:
        r0 = new com.facebook.react.bridge.JSApplicationIllegalArgumentException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r2.append(r3);
        r2.append(r1);
        r1 = "for right extrapolation";
        r2.append(r1);
        r1 = r2.toString();
        r0.<init>(r1);
        throw r0;
    L_0x00af:
        r14 = r20;
        goto L_0x00b3;
    L_0x00b2:
        return r14;
    L_0x00b3:
        r0 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1));
        if (r0 != 0) goto L_0x00b8;
    L_0x00b7:
        return r22;
    L_0x00b8:
        r0 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1));
        if (r0 != 0) goto L_0x00c0;
    L_0x00bc:
        if (r13 > 0) goto L_0x00bf;
    L_0x00be:
        return r22;
    L_0x00bf:
        return r24;
    L_0x00c0:
        r0 = r24 - r22;
        r14 = r14 - r18;
        r0 = r0 * r14;
        r2 = r20 - r18;
        r0 = r0 / r2;
        r0 = r22 + r0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.animated.InterpolationAnimatedNode.interpolate(double, double, double, double, double, java.lang.String, java.lang.String):double");
    }

    static double interpolate(double d, double[] dArr, double[] dArr2, String str, String str2) {
        int findRangeIndex = findRangeIndex(d, dArr);
        int i = findRangeIndex + 1;
        return interpolate(d, dArr[findRangeIndex], dArr[i], dArr2[findRangeIndex], dArr2[i], str, str2);
    }

    private static int findRangeIndex(double d, double[] dArr) {
        int i = 1;
        while (i < dArr.length - 1 && dArr[i] < d) {
            i++;
        }
        return i - 1;
    }

    public InterpolationAnimatedNode(ReadableMap readableMap) {
        this.mInputRange = fromDoubleArray(readableMap.getArray("inputRange"));
        this.mOutputRange = fromDoubleArray(readableMap.getArray("outputRange"));
        this.mExtrapolateLeft = readableMap.getString("extrapolateLeft");
        this.mExtrapolateRight = readableMap.getString("extrapolateRight");
    }

    public void onAttachedToNode(AnimatedNode animatedNode) {
        if (this.mParent != null) {
            throw new IllegalStateException("Parent already attached");
        } else if (animatedNode instanceof ValueAnimatedNode) {
            this.mParent = (ValueAnimatedNode) animatedNode;
        } else {
            throw new IllegalArgumentException("Parent is of an invalid type");
        }
    }

    public void onDetachedFromNode(AnimatedNode animatedNode) {
        if (animatedNode == this.mParent) {
            this.mParent = null;
            return;
        }
        throw new IllegalArgumentException("Invalid parent node provided");
    }

    public void update() {
        ValueAnimatedNode valueAnimatedNode = this.mParent;
        if (valueAnimatedNode != null) {
            this.mValue = interpolate(valueAnimatedNode.getValue(), this.mInputRange, this.mOutputRange, this.mExtrapolateLeft, this.mExtrapolateRight);
        }
    }
}
