package com.google.android.gms.internal.firebase_auth;

final class zzkz extends zzku {
    zzkz() {
    }

    /* JADX WARNING: Missing block: B:29:0x0061, code:
            return -1;
     */
    /* JADX WARNING: Missing block: B:58:0x00b9, code:
            return -1;
     */
    final int zzb(int r16, byte[] r17, int r18, int r19) {
        /*
        r15 = this;
        r0 = r17;
        r1 = r18;
        r2 = r19;
        r3 = r1 | r2;
        r4 = r0.length;
        r4 = r4 - r2;
        r3 = r3 | r4;
        r4 = 2;
        r5 = 3;
        r6 = 0;
        if (r3 < 0) goto L_0x00ba;
    L_0x0010:
        r7 = (long) r1;
        r1 = (long) r2;
        r1 = r1 - r7;
        r2 = (int) r1;
        r1 = 16;
        r9 = 1;
        if (r2 >= r1) goto L_0x001c;
    L_0x001a:
        r1 = 0;
        goto L_0x002e;
    L_0x001c:
        r11 = r7;
        r1 = 0;
    L_0x001e:
        if (r1 >= r2) goto L_0x002d;
    L_0x0020:
        r13 = r11 + r9;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zza(r0, r11);
        if (r3 >= 0) goto L_0x0029;
    L_0x0028:
        goto L_0x002e;
    L_0x0029:
        r1 = r1 + 1;
        r11 = r13;
        goto L_0x001e;
    L_0x002d:
        r1 = r2;
    L_0x002e:
        r2 = r2 - r1;
        r11 = (long) r1;
        r7 = r7 + r11;
    L_0x0031:
        r1 = 0;
    L_0x0032:
        if (r2 <= 0) goto L_0x0040;
    L_0x0034:
        r11 = r7 + r9;
        r1 = com.google.android.gms.internal.firebase_auth.zzkq.zza(r0, r7);
        if (r1 < 0) goto L_0x0041;
    L_0x003c:
        r2 = r2 + -1;
        r7 = r11;
        goto L_0x0032;
    L_0x0040:
        r11 = r7;
    L_0x0041:
        if (r2 != 0) goto L_0x0044;
    L_0x0043:
        return r6;
    L_0x0044:
        r2 = r2 + -1;
        r3 = -32;
        r7 = -65;
        r8 = -1;
        if (r1 >= r3) goto L_0x0062;
    L_0x004d:
        if (r2 != 0) goto L_0x0050;
    L_0x004f:
        return r1;
    L_0x0050:
        r2 = r2 + -1;
        r3 = -62;
        if (r1 < r3) goto L_0x0061;
    L_0x0056:
        r13 = r11 + r9;
        r1 = com.google.android.gms.internal.firebase_auth.zzkq.zza(r0, r11);
        if (r1 <= r7) goto L_0x005f;
    L_0x005e:
        goto L_0x0061;
    L_0x005f:
        r7 = r13;
        goto L_0x0031;
    L_0x0061:
        return r8;
    L_0x0062:
        r13 = -16;
        if (r1 >= r13) goto L_0x008f;
    L_0x0066:
        if (r2 >= r4) goto L_0x006d;
    L_0x0068:
        r0 = zza(r0, r1, r11, r2);
        return r0;
    L_0x006d:
        r2 = r2 + -2;
        r13 = r11 + r9;
        r11 = com.google.android.gms.internal.firebase_auth.zzkq.zza(r0, r11);
        if (r11 > r7) goto L_0x008e;
    L_0x0077:
        r12 = -96;
        if (r1 != r3) goto L_0x007d;
    L_0x007b:
        if (r11 < r12) goto L_0x008e;
    L_0x007d:
        r3 = -19;
        if (r1 != r3) goto L_0x0083;
    L_0x0081:
        if (r11 >= r12) goto L_0x008e;
    L_0x0083:
        r11 = r13 + r9;
        r1 = com.google.android.gms.internal.firebase_auth.zzkq.zza(r0, r13);
        if (r1 <= r7) goto L_0x008c;
    L_0x008b:
        goto L_0x008e;
    L_0x008c:
        r7 = r11;
        goto L_0x0031;
    L_0x008e:
        return r8;
    L_0x008f:
        if (r2 >= r5) goto L_0x0096;
    L_0x0091:
        r0 = zza(r0, r1, r11, r2);
        return r0;
    L_0x0096:
        r2 = r2 + -3;
        r13 = r11 + r9;
        r3 = com.google.android.gms.internal.firebase_auth.zzkq.zza(r0, r11);
        if (r3 > r7) goto L_0x00b9;
    L_0x00a0:
        r1 = r1 << 28;
        r3 = r3 + 112;
        r1 = r1 + r3;
        r1 = r1 >> 30;
        if (r1 != 0) goto L_0x00b9;
    L_0x00a9:
        r11 = r13 + r9;
        r1 = com.google.android.gms.internal.firebase_auth.zzkq.zza(r0, r13);
        if (r1 > r7) goto L_0x00b9;
    L_0x00b1:
        r13 = r11 + r9;
        r1 = com.google.android.gms.internal.firebase_auth.zzkq.zza(r0, r11);
        if (r1 <= r7) goto L_0x005f;
    L_0x00b9:
        return r8;
    L_0x00ba:
        r3 = new java.lang.ArrayIndexOutOfBoundsException;
        r5 = new java.lang.Object[r5];
        r0 = r0.length;
        r0 = java.lang.Integer.valueOf(r0);
        r5[r6] = r0;
        r0 = java.lang.Integer.valueOf(r18);
        r1 = 1;
        r5[r1] = r0;
        r0 = java.lang.Integer.valueOf(r19);
        r5[r4] = r0;
        r0 = "Array length=%d, index=%d, limit=%d";
        r0 = java.lang.String.format(r0, r5);
        r3.<init>(r0);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzkz.zzb(int, byte[], int, int):int");
    }

    final String zzg(byte[] bArr, int i, int i2) throws zzic {
        if (((i | i2) | ((bArr.length - i) - i2)) >= 0) {
            int i3;
            int i4 = i + i2;
            char[] cArr = new char[i2];
            int i5 = 0;
            while (i < i4) {
                byte zza = zzkq.zza(bArr, (long) i);
                if (!zzkv.zzd(zza)) {
                    break;
                }
                i++;
                i3 = i5 + 1;
                zzkv.zza(zza, cArr, i5);
                i5 = i3;
            }
            int i6 = i5;
            while (i < i4) {
                i5 = i + 1;
                byte zza2 = zzkq.zza(bArr, (long) i);
                int i7;
                if (zzkv.zzd(zza2)) {
                    i7 = i6 + 1;
                    zzkv.zza(zza2, cArr, i6);
                    while (i5 < i4) {
                        zza2 = zzkq.zza(bArr, (long) i5);
                        if (!zzkv.zzd(zza2)) {
                            break;
                        }
                        i5++;
                        i3 = i7 + 1;
                        zzkv.zza(zza2, cArr, i7);
                        i7 = i3;
                    }
                    i = i5;
                    i6 = i7;
                } else if (zzkv.zze(zza2)) {
                    if (i5 < i4) {
                        i7 = i5 + 1;
                        i3 = i6 + 1;
                        zzkv.zza(zza2, zzkq.zza(bArr, (long) i5), cArr, i6);
                        i = i7;
                        i6 = i3;
                    } else {
                        throw zzic.zziz();
                    }
                } else if (zzkv.zzf(zza2)) {
                    if (i5 < i4 - 1) {
                        i7 = i5 + 1;
                        i3 = i7 + 1;
                        int i8 = i6 + 1;
                        zzkv.zza(zza2, zzkq.zza(bArr, (long) i5), zzkq.zza(bArr, (long) i7), cArr, i6);
                        i = i3;
                        i6 = i8;
                    } else {
                        throw zzic.zziz();
                    }
                } else if (i5 < i4 - 2) {
                    i7 = i5 + 1;
                    byte zza3 = zzkq.zza(bArr, (long) i5);
                    i5 = i7 + 1;
                    int i9 = i5 + 1;
                    int i10 = i6 + 1;
                    zzkv.zza(zza2, zza3, zzkq.zza(bArr, (long) i7), zzkq.zza(bArr, (long) i5), cArr, i6);
                    i = i9;
                    i6 = i10 + 1;
                } else {
                    throw zzic.zziz();
                }
            }
            return new String(cArr, 0, i6);
        }
        throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)}));
    }

    final int zzb(CharSequence charSequence, byte[] bArr, int i, int i2) {
        CharSequence charSequence2 = charSequence;
        byte[] bArr2 = bArr;
        int i3 = i;
        int i4 = i2;
        long j = (long) i3;
        long j2 = ((long) i4) + j;
        int length = charSequence.length();
        String str = " at index ";
        String str2 = "Failed writing ";
        if (length > i4 || bArr2.length - i4 < i3) {
            char charAt = charSequence2.charAt(length - 1);
            i3 += i4;
            StringBuilder stringBuilder = new StringBuilder(37);
            stringBuilder.append(str2);
            stringBuilder.append(charAt);
            stringBuilder.append(str);
            stringBuilder.append(i3);
            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
        }
        char c;
        long j3;
        char charAt2;
        i3 = 0;
        while (true) {
            c = 128;
            j3 = 1;
            if (i3 >= length) {
                break;
            }
            charAt2 = charSequence2.charAt(i3);
            if (charAt2 >= 128) {
                break;
            }
            j3 = 1 + j;
            zzkq.zza(bArr2, j, (byte) charAt2);
            i3++;
            j = j3;
        }
        if (i3 == length) {
            return (int) j;
        }
        while (i3 < length) {
            long j4;
            long j5;
            charAt2 = charSequence2.charAt(i3);
            long j6;
            long j7;
            if (charAt2 < c && j < j2) {
                j6 = j + j3;
                zzkq.zza(bArr2, j, (byte) charAt2);
                j = j3;
                j4 = j6;
            } else if (charAt2 < 2048 && j <= j2 - 2) {
                j6 = j + j3;
                zzkq.zza(bArr2, j, (byte) ((charAt2 >>> 6) | 960));
                j7 = j6 + j3;
                zzkq.zza(bArr2, j6, (byte) ((charAt2 & 63) | 128));
                j4 = j7;
                j = j3;
                i3++;
                c = 128;
                j5 = j;
                j = j4;
                j3 = j5;
            } else if ((charAt2 < 55296 || 57343 < charAt2) && j <= j2 - 3) {
                j6 = j + j3;
                zzkq.zza(bArr2, j, (byte) ((charAt2 >>> 12) | 480));
                j7 = j6 + j3;
                zzkq.zza(bArr2, j6, (byte) (((charAt2 >>> 6) & 63) | 128));
                long j8 = j7 + 1;
                zzkq.zza(bArr2, j7, (byte) ((charAt2 & 63) | 128));
                j4 = j8;
                j = 1;
            } else if (j <= j2 - 4) {
                i4 = i3 + 1;
                if (i4 != length) {
                    char charAt3 = charSequence2.charAt(i4);
                    if (Character.isSurrogatePair(charAt2, charAt3)) {
                        i3 = Character.toCodePoint(charAt2, charAt3);
                        long j9 = j + 1;
                        zzkq.zza(bArr2, j, (byte) ((i3 >>> 18) | 240));
                        j = j9 + 1;
                        zzkq.zza(bArr2, j9, (byte) (((i3 >>> 12) & 63) | 128));
                        j6 = j + 1;
                        zzkq.zza(bArr2, j, (byte) (((i3 >>> 6) & 63) | 128));
                        j = 1;
                        j4 = j6 + 1;
                        zzkq.zza(bArr2, j6, (byte) ((i3 & 63) | 128));
                        i3 = i4;
                        i3++;
                        c = 128;
                        j5 = j;
                        j = j4;
                        j3 = j5;
                    } else {
                        i3 = i4;
                    }
                }
                throw new zzkw(i3 - 1, length);
            } else {
                if (55296 <= charAt2 && charAt2 <= 57343) {
                    int i5 = i3 + 1;
                    if (i5 == length || !Character.isSurrogatePair(charAt2, charSequence2.charAt(i5))) {
                        throw new zzkw(i3, length);
                    }
                }
                StringBuilder stringBuilder2 = new StringBuilder(46);
                stringBuilder2.append(str2);
                stringBuilder2.append(charAt2);
                stringBuilder2.append(str);
                stringBuilder2.append(j);
                throw new ArrayIndexOutOfBoundsException(stringBuilder2.toString());
            }
            i3++;
            c = 128;
            j5 = j;
            j = j4;
            j3 = j5;
        }
        return (int) j;
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        if (i2 == 0) {
            return zzkt.zzbh(i);
        }
        if (i2 == 1) {
            return zzkt.zzs(i, zzkq.zza(bArr, j));
        }
        if (i2 == 2) {
            return zzkt.zzd(i, zzkq.zza(bArr, j), zzkq.zza(bArr, j + 1));
        }
        throw new AssertionError();
    }
}
