package com.google.maps.android;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class PolyUtil {
    private static final double DEFAULT_TOLERANCE = 0.1d;

    private PolyUtil() {
    }

    private static double tanLatGC(double d, double d2, double d3, double d4) {
        return ((Math.tan(d) * Math.sin(d3 - d4)) + (Math.tan(d2) * Math.sin(d4))) / Math.sin(d3);
    }

    private static double mercatorLatRhumb(double d, double d2, double d3, double d4) {
        return ((MathUtil.mercator(d) * (d3 - d4)) + (MathUtil.mercator(d2) * d4)) / d3;
    }

    /* JADX WARNING: Missing block: B:8:0x0013, code:
            return false;
     */
    private static boolean intersects(double r12, double r14, double r16, double r18, double r20, boolean r22) {
        /*
        r0 = 0;
        r8 = 0;
        r2 = (r20 > r0 ? 1 : (r20 == r0 ? 0 : -1));
        if (r2 < 0) goto L_0x000b;
    L_0x0007:
        r2 = (r20 > r16 ? 1 : (r20 == r16 ? 0 : -1));
        if (r2 >= 0) goto L_0x0013;
    L_0x000b:
        r2 = (r20 > r0 ? 1 : (r20 == r0 ? 0 : -1));
        if (r2 >= 0) goto L_0x0014;
    L_0x000f:
        r2 = (r20 > r16 ? 1 : (r20 == r16 ? 0 : -1));
        if (r2 >= 0) goto L_0x0014;
    L_0x0013:
        return r8;
    L_0x0014:
        r2 = -4613618979930100456; // 0xbff921fb54442d18 float:3.37028055E12 double:-1.5707963267948966;
        r4 = (r18 > r2 ? 1 : (r18 == r2 ? 0 : -1));
        if (r4 > 0) goto L_0x001e;
    L_0x001d:
        return r8;
    L_0x001e:
        r4 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1));
        if (r4 <= 0) goto L_0x008f;
    L_0x0022:
        r4 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1));
        if (r4 <= 0) goto L_0x008f;
    L_0x0026:
        r2 = 4609753056924675352; // 0x3ff921fb54442d18 float:3.37028055E12 double:1.5707963267948966;
        r4 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1));
        if (r4 >= 0) goto L_0x008f;
    L_0x002f:
        r4 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1));
        if (r4 < 0) goto L_0x0034;
    L_0x0033:
        goto L_0x008f;
    L_0x0034:
        r4 = -4609115380302729960; // 0xc00921fb54442d18 float:3.37028055E12 double:-3.141592653589793;
        r6 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1));
        if (r6 > 0) goto L_0x003e;
    L_0x003d:
        return r8;
    L_0x003e:
        r4 = r16 - r20;
        r4 = r4 * r12;
        r6 = r14 * r20;
        r4 = r4 + r6;
        r4 = r4 / r16;
        r6 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1));
        if (r6 < 0) goto L_0x0054;
    L_0x004b:
        r6 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1));
        if (r6 < 0) goto L_0x0054;
    L_0x004f:
        r6 = (r18 > r4 ? 1 : (r18 == r4 ? 0 : -1));
        if (r6 >= 0) goto L_0x0054;
    L_0x0053:
        return r8;
    L_0x0054:
        r9 = 1;
        r6 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1));
        if (r6 > 0) goto L_0x0062;
    L_0x0059:
        r6 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1));
        if (r6 > 0) goto L_0x0062;
    L_0x005d:
        r0 = (r18 > r4 ? 1 : (r18 == r4 ? 0 : -1));
        if (r0 < 0) goto L_0x0062;
    L_0x0061:
        return r9;
    L_0x0062:
        r0 = (r18 > r2 ? 1 : (r18 == r2 ? 0 : -1));
        if (r0 < 0) goto L_0x0067;
    L_0x0066:
        return r9;
    L_0x0067:
        if (r22 == 0) goto L_0x007c;
    L_0x0069:
        r10 = java.lang.Math.tan(r18);
        r0 = r12;
        r2 = r14;
        r4 = r16;
        r6 = r20;
        r0 = tanLatGC(r0, r2, r4, r6);
        r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1));
        if (r2 < 0) goto L_0x008f;
    L_0x007b:
        goto L_0x008e;
    L_0x007c:
        r10 = com.google.maps.android.MathUtil.mercator(r18);
        r0 = r12;
        r2 = r14;
        r4 = r16;
        r6 = r20;
        r0 = mercatorLatRhumb(r0, r2, r4, r6);
        r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1));
        if (r2 < 0) goto L_0x008f;
    L_0x008e:
        r8 = 1;
    L_0x008f:
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.maps.android.PolyUtil.intersects(double, double, double, double, double, boolean):boolean");
    }

    public static boolean containsLocation(LatLng latLng, List<LatLng> list, boolean z) {
        return containsLocation(latLng.latitude, latLng.longitude, list, z);
    }

    public static boolean containsLocation(double d, double d2, List<LatLng> list, boolean z) {
        int size = list.size();
        boolean z2 = false;
        if (size == 0) {
            return false;
        }
        double toRadians = Math.toRadians(d);
        double toRadians2 = Math.toRadians(d2);
        LatLng latLng = (LatLng) list.get(size - 1);
        double toRadians3 = Math.toRadians(latLng.latitude);
        double toRadians4 = Math.toRadians(latLng.longitude);
        double d3 = toRadians3;
        int i = 0;
        for (LatLng latLng2 : list) {
            double wrap = MathUtil.wrap(toRadians2 - toRadians4, -3.141592653589793d, 3.141592653589793d);
            if (toRadians == d3 && wrap == 0.0d) {
                return true;
            }
            double toRadians5 = Math.toRadians(latLng2.latitude);
            double toRadians6 = Math.toRadians(latLng2.longitude);
            if (intersects(d3, toRadians5, MathUtil.wrap(toRadians6 - toRadians4, -3.141592653589793d, 3.141592653589793d), toRadians, wrap, z)) {
                i++;
            }
            d3 = toRadians5;
            toRadians4 = toRadians6;
        }
        if ((i & 1) != 0) {
            z2 = true;
        }
        return z2;
    }

    public static boolean isLocationOnEdge(LatLng latLng, List<LatLng> list, boolean z, double d) {
        return isLocationOnEdgeOrPath(latLng, list, true, z, d);
    }

    public static boolean isLocationOnEdge(LatLng latLng, List<LatLng> list, boolean z) {
        return isLocationOnEdge(latLng, list, z, DEFAULT_TOLERANCE);
    }

    public static boolean isLocationOnPath(LatLng latLng, List<LatLng> list, boolean z, double d) {
        return isLocationOnEdgeOrPath(latLng, list, false, z, d);
    }

    public static boolean isLocationOnPath(LatLng latLng, List<LatLng> list, boolean z) {
        return isLocationOnPath(latLng, list, z, DEFAULT_TOLERANCE);
    }

    private static boolean isLocationOnEdgeOrPath(LatLng latLng, List<LatLng> list, boolean z, boolean z2, double d) {
        LatLng latLng2 = latLng;
        int size = list.size();
        if (size == 0) {
            return false;
        }
        int i;
        List list2;
        double d2 = d / 6371009.0d;
        double hav = MathUtil.hav(d2);
        double toRadians = Math.toRadians(latLng2.latitude);
        double toRadians2 = Math.toRadians(latLng2.longitude);
        if (z) {
            i = size - 1;
            list2 = list;
        } else {
            list2 = list;
            i = 0;
        }
        LatLng latLng3 = (LatLng) list2.get(i);
        double toRadians3 = Math.toRadians(latLng3.latitude);
        double toRadians4 = Math.toRadians(latLng3.longitude);
        Iterator it;
        double toRadians5;
        if (z2) {
            double d3 = toRadians3;
            double d4 = toRadians4;
            for (LatLng latLng4 : list) {
                toRadians5 = Math.toRadians(latLng4.latitude);
                d2 = Math.toRadians(latLng4.longitude);
                if (isOnSegmentGC(d3, d4, toRadians5, d2, toRadians, toRadians2, hav)) {
                    return true;
                }
                d4 = d2;
                d3 = toRadians5;
            }
        } else {
            double d5 = toRadians - d2;
            d2 = toRadians + d2;
            double mercator = MathUtil.mercator(toRadians3);
            double mercator2 = MathUtil.mercator(toRadians);
            double[] dArr = new double[3];
            it = list.iterator();
            while (it.hasNext()) {
                latLng2 = (LatLng) it.next();
                toRadians5 = d2;
                double toRadians6 = Math.toRadians(latLng2.latitude);
                double mercator3 = MathUtil.mercator(toRadians6);
                Iterator it2 = it;
                double toRadians7 = Math.toRadians(latLng2.longitude);
                if (Math.max(toRadians3, toRadians6) >= d5 && Math.min(toRadians3, toRadians6) <= toRadians5) {
                    double d6 = -3.141592653589793d;
                    d = 3.141592653589793d;
                    toRadians3 = MathUtil.wrap(toRadians7 - toRadians4, d6, d);
                    toRadians4 = MathUtil.wrap(toRadians2 - toRadians4, d6, d);
                    dArr[0] = toRadians4;
                    dArr[1] = toRadians4 + 6.283185307179586d;
                    dArr[2] = toRadians4 - 6.283185307179586d;
                    for (double d7 : dArr) {
                        double d8 = mercator3 - mercator;
                        double d9 = (toRadians3 * toRadians3) + (d8 * d8);
                        double d10 = 0.0d;
                        if (d9 > 0.0d) {
                            d10 = MathUtil.clamp(((d7 * toRadians3) + ((mercator2 - mercator) * d8)) / d9, 0.0d, 1.0d);
                        }
                        if (MathUtil.havDistance(toRadians, MathUtil.inverseMercator(mercator + (d10 * d8)), d7 - (d10 * toRadians3)) < hav) {
                            return true;
                        }
                    }
                    continue;
                }
                toRadians4 = toRadians7;
                toRadians3 = toRadians6;
                it = it2;
                d2 = toRadians5;
                mercator = mercator3;
            }
        }
        return false;
    }

    private static double sinDeltaBearing(double d, double d2, double d3, double d4, double d5, double d6) {
        double sin = Math.sin(d);
        double cos = Math.cos(d3);
        double cos2 = Math.cos(d5);
        double d7 = d6 - d2;
        double d8 = d4 - d2;
        double sin2 = Math.sin(d7) * cos2;
        double sin3 = Math.sin(d8) * cos;
        sin *= 2.0d;
        double sin4 = Math.sin(d5 - d) + ((cos2 * sin) * MathUtil.hav(d7));
        cos2 = Math.sin(d3 - d) + ((sin * cos) * MathUtil.hav(d8));
        sin = ((sin2 * sin2) + (sin4 * sin4)) * ((sin3 * sin3) + (cos2 * cos2));
        if (sin <= 0.0d) {
            return 1.0d;
        }
        return ((sin2 * cos2) - (sin4 * sin3)) / Math.sqrt(sin);
    }

    private static boolean isOnSegmentGC(double d, double d2, double d3, double d4, double d5, double d6, double d7) {
        double havDistance = MathUtil.havDistance(d, d5, d2 - d6);
        boolean z = true;
        if (havDistance <= d7) {
            return true;
        }
        double havDistance2 = MathUtil.havDistance(d3, d5, d4 - d6);
        if (havDistance2 <= d7) {
            return true;
        }
        double havFromSin = MathUtil.havFromSin(MathUtil.sinFromHav(havDistance) * sinDeltaBearing(d, d2, d3, d4, d5, d6));
        if (havFromSin > d7) {
            return false;
        }
        double havDistance3 = MathUtil.havDistance(d, d3, d2 - d4);
        double d8 = ((1.0d - (havDistance3 * 2.0d)) * havFromSin) + havDistance3;
        if (havDistance > d8 || havDistance2 > d8) {
            return false;
        }
        if (havDistance3 < 0.74d) {
            return true;
        }
        double d9 = 1.0d - (2.0d * havFromSin);
        if (MathUtil.sinSumFromHav((havDistance - havFromSin) / d9, (havDistance2 - havFromSin) / d9) <= 0.0d) {
            z = false;
        }
        return z;
    }

    public static List<LatLng> simplify(List<LatLng> list, double d) {
        List<LatLng> list2 = list;
        int size = list.size();
        if (size >= 1) {
            double d2 = 0.0d;
            if (d > 0.0d) {
                boolean isClosedPolygon = isClosedPolygon(list);
                Object obj = null;
                if (isClosedPolygon) {
                    obj = (LatLng) list2.get(list.size() - 1);
                    list2.remove(list.size() - 1);
                    list2.add(new LatLng(obj.latitude + 1.0E-11d, obj.longitude + 1.0E-11d));
                }
                Stack stack = new Stack();
                double[] dArr = new double[size];
                int i = 0;
                dArr[0] = 1.0d;
                dArr[size - 1] = 1.0d;
                if (size > 2) {
                    int i2;
                    stack.push(new int[]{null, i2});
                    size = 0;
                    while (stack.size() > 0) {
                        int[] iArr = (int[]) stack.pop();
                        double d3 = d2;
                        for (i2 = iArr[0] + 1; i2 < iArr[1]; i2++) {
                            d2 = distanceToLine((LatLng) list2.get(i2), (LatLng) list2.get(iArr[0]), (LatLng) list2.get(iArr[1]));
                            if (d2 > d3) {
                                d3 = d2;
                                size = i2;
                            }
                        }
                        if (d3 > d) {
                            dArr[size] = d3;
                            stack.push(new int[]{iArr[0], size});
                            stack.push(new int[]{size, iArr[1]});
                        }
                        d2 = 0.0d;
                    }
                }
                if (isClosedPolygon) {
                    list2.remove(list.size() - 1);
                    list2.add(obj);
                }
                List arrayList = new ArrayList();
                for (LatLng latLng : list) {
                    if (dArr[i] != 0.0d) {
                        arrayList.add(latLng);
                    }
                    i++;
                }
                return arrayList;
            }
            throw new IllegalArgumentException("Tolerance must be greater than zero");
        }
        throw new IllegalArgumentException("Polyline must have at least 1 point");
    }

    public static boolean isClosedPolygon(List<LatLng> list) {
        if (((LatLng) list.get(0)).equals((LatLng) list.get(list.size() - 1))) {
            return true;
        }
        return false;
    }

    public static double distanceToLine(LatLng latLng, LatLng latLng2, LatLng latLng3) {
        if (latLng2.equals(latLng3)) {
            return SphericalUtil.computeDistanceBetween(latLng3, latLng);
        }
        double toRadians = Math.toRadians(latLng.latitude);
        double toRadians2 = Math.toRadians(latLng.longitude);
        double toRadians3 = Math.toRadians(latLng2.latitude);
        double toRadians4 = Math.toRadians(latLng2.longitude);
        double toRadians5 = Math.toRadians(latLng3.latitude) - toRadians3;
        double toRadians6 = Math.toRadians(latLng3.longitude) - toRadians4;
        toRadians = (((toRadians - toRadians3) * toRadians5) + ((toRadians2 - toRadians4) * toRadians6)) / ((toRadians5 * toRadians5) + (toRadians6 * toRadians6));
        if (toRadians <= 0.0d) {
            return SphericalUtil.computeDistanceBetween(latLng, latLng2);
        }
        if (toRadians >= 1.0d) {
            return SphericalUtil.computeDistanceBetween(latLng, latLng3);
        }
        return SphericalUtil.computeDistanceBetween(new LatLng(latLng.latitude - latLng2.latitude, latLng.longitude - latLng2.longitude), new LatLng((latLng3.latitude - latLng2.latitude) * toRadians, toRadians * (latLng3.longitude - latLng2.longitude)));
    }

    public static List<LatLng> decode(String str) {
        int length = str.length();
        List<LatLng> arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            int i4;
            int i5 = 1;
            int i6 = 0;
            while (true) {
                i4 = i + 1;
                i = (str.charAt(i) - 63) - 1;
                i5 += i << i6;
                i6 += 5;
                if (i < 31) {
                    break;
                }
                i = i4;
            }
            i = ((i5 & 1) != 0 ? ~(i5 >> 1) : i5 >> 1) + i2;
            i2 = 1;
            i5 = 0;
            while (true) {
                i6 = i4 + 1;
                i4 = (str.charAt(i4) - 63) - 1;
                i2 += i4 << i5;
                i5 += 5;
                if (i4 < 31) {
                    break;
                }
                i4 = i6;
            }
            i3 += (i2 & 1) != 0 ? ~(i2 >> 1) : i2 >> 1;
            arrayList.add(new LatLng(((double) i) * 1.0E-5d, ((double) i3) * 1.0E-5d));
            i2 = i;
            i = i6;
        }
        return arrayList;
    }

    public static String encode(List<LatLng> list) {
        StringBuffer stringBuffer = new StringBuffer();
        long j = 0;
        long j2 = 0;
        for (LatLng latLng : list) {
            long round = Math.round(latLng.latitude * 100000.0d);
            long round2 = Math.round(latLng.longitude * 100000.0d);
            j2 = round2 - j2;
            encode(round - j, stringBuffer);
            encode(j2, stringBuffer);
            j = round;
            j2 = round2;
        }
        return stringBuffer.toString();
    }

    private static void encode(long j, StringBuffer stringBuffer) {
        j = j < 0 ? ~(j << 1) : j << 1;
        while (j >= 32) {
            stringBuffer.append(Character.toChars((int) ((32 | (31 & j)) + 63)));
            j >>= 5;
        }
        stringBuffer.append(Character.toChars((int) (j + 63)));
    }
}
