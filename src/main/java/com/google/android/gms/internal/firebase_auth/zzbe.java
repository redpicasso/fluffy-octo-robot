package com.google.android.gms.internal.firebase_auth;

import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class zzbe<K, V> extends zzaz<K, V> {
    private static final zzaz<Object, Object> zzhh = new zzbe(null, new Object[0], 0);
    private final transient int size;
    private final transient Object[] zzhf;
    private final transient Object zzhi;

    /* JADX WARNING: Missing block: B:16:0x005f, code:
            r0[r6] = (byte) r2;
            r3 = r3 + 1;
     */
    /* JADX WARNING: Missing block: B:29:0x009d, code:
            r0[r6] = (short) r2;
            r3 = r3 + 1;
     */
    /* JADX WARNING: Missing block: B:40:0x00d2, code:
            r0[r7] = r2;
            r3 = r3 + 1;
     */
    static <K, V> com.google.android.gms.internal.firebase_auth.zzbe<K, V> zza(int r10, java.lang.Object[] r11) {
        /*
        r10 = r11.length;
        r0 = 1;
        r10 = r10 >> r0;
        r1 = 4;
        com.google.android.gms.internal.firebase_auth.zzaj.zzb(r1, r10);
        r10 = 2;
        r10 = java.lang.Math.max(r1, r10);
        r2 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r3 = 0;
        r4 = 751619276; // 0x2ccccccc float:5.8207657E-12 double:3.71349263E-315;
        if (r10 >= r4) goto L_0x002d;
    L_0x0014:
        r2 = r10 + -1;
        r2 = java.lang.Integer.highestOneBit(r2);
        r0 = r2 << 1;
        r2 = r0;
    L_0x001d:
        r4 = (double) r2;
        r6 = 4604480259023595110; // 0x3fe6666666666666 float:2.720083E23 double:0.7;
        r4 = r4 * r6;
        r6 = (double) r10;
        r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r0 >= 0) goto L_0x0036;
    L_0x002a:
        r2 = r2 << 1;
        goto L_0x001d;
    L_0x002d:
        if (r10 >= r2) goto L_0x0030;
    L_0x002f:
        goto L_0x0031;
    L_0x0030:
        r0 = 0;
    L_0x0031:
        r10 = "collection too large";
        com.google.android.gms.internal.firebase_auth.zzaj.checkArgument(r0, r10);
    L_0x0036:
        r10 = r2 + -1;
        r0 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r4 = -1;
        if (r2 > r0) goto L_0x0075;
    L_0x003d:
        r0 = new byte[r2];
        java.util.Arrays.fill(r0, r4);
    L_0x0042:
        if (r3 >= r1) goto L_0x00e7;
    L_0x0044:
        r2 = r3 * 2;
        r4 = r11[r2];
        r5 = r2 ^ 1;
        r5 = r11[r5];
        com.google.android.gms.internal.firebase_auth.zzat.zza(r4, r5);
        r6 = r4.hashCode();
        r6 = com.google.android.gms.internal.firebase_auth.zzaw.zzg(r6);
    L_0x0057:
        r6 = r6 & r10;
        r7 = r0[r6];
        r8 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r7 = r7 & r8;
        if (r7 != r8) goto L_0x0065;
    L_0x005f:
        r2 = (byte) r2;
        r0[r6] = r2;
        r3 = r3 + 1;
        goto L_0x0042;
    L_0x0065:
        r8 = r11[r7];
        r8 = r8.equals(r4);
        if (r8 != 0) goto L_0x0070;
    L_0x006d:
        r6 = r6 + 1;
        goto L_0x0057;
    L_0x0070:
        r10 = zza(r4, r5, r11, r7);
        throw r10;
    L_0x0075:
        r0 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        if (r2 > r0) goto L_0x00b3;
    L_0x007a:
        r0 = new short[r2];
        java.util.Arrays.fill(r0, r4);
    L_0x007f:
        if (r3 >= r1) goto L_0x00e7;
    L_0x0081:
        r2 = r3 * 2;
        r4 = r11[r2];
        r5 = r2 ^ 1;
        r5 = r11[r5];
        com.google.android.gms.internal.firebase_auth.zzat.zza(r4, r5);
        r6 = r4.hashCode();
        r6 = com.google.android.gms.internal.firebase_auth.zzaw.zzg(r6);
    L_0x0094:
        r6 = r6 & r10;
        r7 = r0[r6];
        r8 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r7 = r7 & r8;
        if (r7 != r8) goto L_0x00a3;
    L_0x009d:
        r2 = (short) r2;
        r0[r6] = r2;
        r3 = r3 + 1;
        goto L_0x007f;
    L_0x00a3:
        r8 = r11[r7];
        r8 = r8.equals(r4);
        if (r8 != 0) goto L_0x00ae;
    L_0x00ab:
        r6 = r6 + 1;
        goto L_0x0094;
    L_0x00ae:
        r10 = zza(r4, r5, r11, r7);
        throw r10;
    L_0x00b3:
        r0 = new int[r2];
        java.util.Arrays.fill(r0, r4);
    L_0x00b8:
        if (r3 >= r1) goto L_0x00e7;
    L_0x00ba:
        r2 = r3 * 2;
        r5 = r11[r2];
        r6 = r2 ^ 1;
        r6 = r11[r6];
        com.google.android.gms.internal.firebase_auth.zzat.zza(r5, r6);
        r7 = r5.hashCode();
        r7 = com.google.android.gms.internal.firebase_auth.zzaw.zzg(r7);
    L_0x00cd:
        r7 = r7 & r10;
        r8 = r0[r7];
        if (r8 != r4) goto L_0x00d7;
    L_0x00d2:
        r0[r7] = r2;
        r3 = r3 + 1;
        goto L_0x00b8;
    L_0x00d7:
        r9 = r11[r8];
        r9 = r9.equals(r5);
        if (r9 != 0) goto L_0x00e2;
    L_0x00df:
        r7 = r7 + 1;
        goto L_0x00cd;
    L_0x00e2:
        r10 = zza(r5, r6, r11, r8);
        throw r10;
    L_0x00e7:
        r10 = new com.google.android.gms.internal.firebase_auth.zzbe;
        r10.<init>(r0, r11, r1);
        return r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzbe.zza(int, java.lang.Object[]):com.google.android.gms.internal.firebase_auth.zzbe<K, V>");
    }

    private static IllegalArgumentException zza(Object obj, Object obj2, Object[] objArr, int i) {
        String valueOf = String.valueOf(obj);
        String valueOf2 = String.valueOf(obj2);
        String valueOf3 = String.valueOf(objArr[i]);
        String valueOf4 = String.valueOf(objArr[i ^ 1]);
        StringBuilder stringBuilder = new StringBuilder((((String.valueOf(valueOf).length() + 39) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()) + String.valueOf(valueOf4).length());
        stringBuilder.append("Multiple entries with same key: ");
        stringBuilder.append(valueOf);
        valueOf = "=";
        stringBuilder.append(valueOf);
        stringBuilder.append(valueOf2);
        stringBuilder.append(" and ");
        stringBuilder.append(valueOf3);
        stringBuilder.append(valueOf);
        stringBuilder.append(valueOf4);
        return new IllegalArgumentException(stringBuilder.toString());
    }

    private zzbe(Object obj, Object[] objArr, int i) {
        this.zzhi = obj;
        this.zzhf = objArr;
        this.size = i;
    }

    public final int size() {
        return this.size;
    }

    @NullableDecl
    public final V get(@NullableDecl Object obj) {
        Object obj2 = this.zzhi;
        Object[] objArr = this.zzhf;
        int i = this.size;
        if (obj == null) {
            return null;
        }
        if (i == 1) {
            if (objArr[0].equals(obj)) {
                return objArr[1];
            }
            return null;
        } else if (obj2 == null) {
            return null;
        } else {
            int length;
            int zzg;
            int i2;
            if (obj2 instanceof byte[]) {
                byte[] bArr = (byte[]) obj2;
                length = bArr.length - 1;
                zzg = zzaw.zzg(obj.hashCode());
                while (true) {
                    zzg &= length;
                    i2 = bArr[zzg] & 255;
                    if (i2 == 255) {
                        return null;
                    }
                    if (objArr[i2].equals(obj)) {
                        return objArr[i2 ^ 1];
                    }
                    zzg++;
                }
            } else if (obj2 instanceof short[]) {
                short[] sArr = (short[]) obj2;
                length = sArr.length - 1;
                zzg = zzaw.zzg(obj.hashCode());
                while (true) {
                    zzg &= length;
                    i2 = sArr[zzg] & 65535;
                    if (i2 == 65535) {
                        return null;
                    }
                    if (objArr[i2].equals(obj)) {
                        return objArr[i2 ^ 1];
                    }
                    zzg++;
                }
            } else {
                int[] iArr = (int[]) obj2;
                i = iArr.length - 1;
                length = zzaw.zzg(obj.hashCode());
                while (true) {
                    length &= i;
                    i2 = iArr[length];
                    if (i2 == -1) {
                        return null;
                    }
                    if (objArr[i2].equals(obj)) {
                        return objArr[i2 ^ 1];
                    }
                    length++;
                }
            }
        }
    }

    final zzbc<Entry<K, V>> zzcf() {
        return new zzbd(this, this.zzhf, 0, this.size);
    }

    final zzbc<K> zzcg() {
        return new zzbf(this, new zzbi(this.zzhf, 0, this.size));
    }

    final zzav<V> zzch() {
        return new zzbi(this.zzhf, 1, this.size);
    }
}
