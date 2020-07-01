package com.google.android.gms.internal.firebase_ml;

import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class zzln<K, V> extends zzlj<K, V> {
    private static final zzlj<Object, Object> zzadi = new zzln(null, new Object[0], 0);
    private final transient int size;
    private final transient Object[] zzadd;
    private final transient int[] zzadj;

    /* JADX WARNING: Missing block: B:24:0x0079, code:
            r3[r8] = r5;
            r1 = r1 + 1;
     */
    static <K, V> com.google.android.gms.internal.firebase_ml.zzln<K, V> zza(int r11, java.lang.Object[] r12) {
        /*
        if (r11 != 0) goto L_0x0007;
    L_0x0002:
        r11 = zzadi;
        r11 = (com.google.android.gms.internal.firebase_ml.zzln) r11;
        return r11;
    L_0x0007:
        r0 = 0;
        r1 = 0;
        r2 = 1;
        if (r11 != r2) goto L_0x0019;
    L_0x000c:
        r11 = r12[r1];
        r1 = r12[r2];
        com.google.android.gms.internal.firebase_ml.zzld.zzc(r11, r1);
        r11 = new com.google.android.gms.internal.firebase_ml.zzln;
        r11.<init>(r0, r12, r2);
        return r11;
    L_0x0019:
        r3 = r12.length;
        r3 = r3 >> r2;
        com.google.android.gms.internal.firebase_ml.zzks.zzc(r11, r3);
        r3 = 2;
        r3 = java.lang.Math.max(r11, r3);
        r4 = 751619276; // 0x2ccccccc float:5.8207657E-12 double:3.71349263E-315;
        r5 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        if (r3 >= r4) goto L_0x0042;
    L_0x002a:
        r4 = r3 + -1;
        r4 = java.lang.Integer.highestOneBit(r4);
        r4 = r4 << r2;
        r5 = r4;
    L_0x0032:
        r6 = (double) r5;
        r8 = 4604480259023595110; // 0x3fe6666666666666 float:2.720083E23 double:0.7;
        r6 = r6 * r8;
        r8 = (double) r3;
        r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r4 >= 0) goto L_0x004c;
    L_0x003f:
        r5 = r5 << 1;
        goto L_0x0032;
    L_0x0042:
        if (r3 >= r5) goto L_0x0046;
    L_0x0044:
        r3 = 1;
        goto L_0x0047;
    L_0x0046:
        r3 = 0;
    L_0x0047:
        r4 = "collection too large";
        com.google.android.gms.internal.firebase_ml.zzks.checkArgument(r3, r4);
    L_0x004c:
        if (r11 != r2) goto L_0x0057;
    L_0x004e:
        r1 = r12[r1];
        r2 = r12[r2];
        com.google.android.gms.internal.firebase_ml.zzld.zzc(r1, r2);
        goto L_0x00f1;
    L_0x0057:
        r0 = r5 + -1;
        r3 = new int[r5];
        r4 = -1;
        java.util.Arrays.fill(r3, r4);
    L_0x005f:
        if (r1 >= r11) goto L_0x00f0;
    L_0x0061:
        r5 = r1 * 2;
        r6 = r12[r5];
        r7 = r5 + 1;
        r7 = r12[r7];
        com.google.android.gms.internal.firebase_ml.zzld.zzc(r6, r7);
        r8 = r6.hashCode();
        r8 = com.google.android.gms.internal.firebase_ml.zzle.zzal(r8);
    L_0x0074:
        r8 = r8 & r0;
        r9 = r3[r8];
        if (r9 != r4) goto L_0x007e;
    L_0x0079:
        r3[r8] = r5;
        r1 = r1 + 1;
        goto L_0x005f;
    L_0x007e:
        r10 = r12[r9];
        r10 = r10.equals(r6);
        if (r10 != 0) goto L_0x0089;
    L_0x0086:
        r8 = r8 + 1;
        goto L_0x0074;
    L_0x0089:
        r11 = new java.lang.IllegalArgumentException;
        r0 = java.lang.String.valueOf(r6);
        r1 = java.lang.String.valueOf(r7);
        r3 = r12[r9];
        r3 = java.lang.String.valueOf(r3);
        r2 = r2 ^ r9;
        r12 = r12[r2];
        r12 = java.lang.String.valueOf(r12);
        r2 = java.lang.String.valueOf(r0);
        r2 = r2.length();
        r2 = r2 + 39;
        r4 = java.lang.String.valueOf(r1);
        r4 = r4.length();
        r2 = r2 + r4;
        r4 = java.lang.String.valueOf(r3);
        r4 = r4.length();
        r2 = r2 + r4;
        r4 = java.lang.String.valueOf(r12);
        r4 = r4.length();
        r2 = r2 + r4;
        r4 = new java.lang.StringBuilder;
        r4.<init>(r2);
        r2 = "Multiple entries with same key: ";
        r4.append(r2);
        r4.append(r0);
        r0 = "=";
        r4.append(r0);
        r4.append(r1);
        r1 = " and ";
        r4.append(r1);
        r4.append(r3);
        r4.append(r0);
        r4.append(r12);
        r12 = r4.toString();
        r11.<init>(r12);
        throw r11;
    L_0x00f0:
        r0 = r3;
    L_0x00f1:
        r1 = new com.google.android.gms.internal.firebase_ml.zzln;
        r1.<init>(r0, r12, r11);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzln.zza(int, java.lang.Object[]):com.google.android.gms.internal.firebase_ml.zzln<K, V>");
    }

    private zzln(int[] iArr, Object[] objArr, int i) {
        this.zzadj = iArr;
        this.zzadd = objArr;
        this.size = i;
    }

    public final int size() {
        return this.size;
    }

    @NullableDecl
    public final V get(@NullableDecl Object obj) {
        int[] iArr = this.zzadj;
        Object[] objArr = this.zzadd;
        int i = this.size;
        if (obj == null) {
            return null;
        }
        if (i == 1) {
            if (objArr[0].equals(obj)) {
                return objArr[1];
            }
            return null;
        } else if (iArr == null) {
            return null;
        } else {
            i = iArr.length - 1;
            int zzal = zzle.zzal(obj.hashCode());
            while (true) {
                zzal &= i;
                int i2 = iArr[zzal];
                if (i2 == -1) {
                    return null;
                }
                if (objArr[i2].equals(obj)) {
                    return objArr[i2 ^ 1];
                }
                zzal++;
            }
        }
    }

    final zzll<Entry<K, V>> zziq() {
        return new zzlo(this, this.zzadd, 0, this.size);
    }

    final zzll<K> zzir() {
        return new zzlq(this, new zzlr(this.zzadd, 0, this.size));
    }

    final zzlf<V> zzis() {
        return new zzlr(this.zzadd, 1, this.size);
    }
}
