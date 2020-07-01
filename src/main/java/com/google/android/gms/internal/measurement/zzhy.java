package com.google.android.gms.internal.measurement;

import java.nio.ByteBuffer;

final class zzhy {
    private static final zzhz zzamz;

    private static int zzc(int i, int i2, int i3) {
        return (i > -12 || i2 > -65 || i3 > -65) ? -1 : (i ^ (i2 << 8)) ^ (i3 << 16);
    }

    private static int zzch(int i) {
        return i > -12 ? -1 : i;
    }

    public static boolean zzh(byte[] bArr) {
        return zzamz.zzf(bArr, 0, bArr.length);
    }

    private static int zzr(int i, int i2) {
        return (i > -12 || i2 > -65) ? -1 : i ^ (i2 << 8);
    }

    public static boolean zzf(byte[] bArr, int i, int i2) {
        return zzamz.zzf(bArr, i, i2);
    }

    private static int zzg(byte[] bArr, int i, int i2) {
        byte b = bArr[i - 1];
        i2 -= i;
        if (i2 == 0) {
            return zzch(b);
        }
        if (i2 == 1) {
            return zzr(b, bArr[i]);
        }
        if (i2 == 2) {
            return zzc(b, bArr[i], bArr[i + 1]);
        }
        throw new AssertionError();
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005b A:{RETURN} */
    static int zza(java.lang.CharSequence r8) {
        /*
        r0 = r8.length();
        r1 = 0;
        r2 = 0;
    L_0x0006:
        if (r2 >= r0) goto L_0x0013;
    L_0x0008:
        r3 = r8.charAt(r2);
        r4 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        if (r3 >= r4) goto L_0x0013;
    L_0x0010:
        r2 = r2 + 1;
        goto L_0x0006;
    L_0x0013:
        r3 = r0;
    L_0x0014:
        if (r2 >= r0) goto L_0x0059;
    L_0x0016:
        r4 = r8.charAt(r2);
        r5 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        if (r4 >= r5) goto L_0x0026;
    L_0x001e:
        r4 = 127 - r4;
        r4 = r4 >>> 31;
        r3 = r3 + r4;
        r2 = r2 + 1;
        goto L_0x0014;
    L_0x0026:
        r4 = r8.length();
    L_0x002a:
        if (r2 >= r4) goto L_0x0058;
    L_0x002c:
        r6 = r8.charAt(r2);
        if (r6 >= r5) goto L_0x0038;
    L_0x0032:
        r6 = 127 - r6;
        r6 = r6 >>> 31;
        r1 = r1 + r6;
        goto L_0x0055;
    L_0x0038:
        r1 = r1 + 2;
        r7 = 55296; // 0xd800 float:7.7486E-41 double:2.732E-319;
        if (r7 > r6) goto L_0x0055;
    L_0x003f:
        r7 = 57343; // 0xdfff float:8.0355E-41 double:2.8331E-319;
        if (r6 > r7) goto L_0x0055;
    L_0x0044:
        r6 = java.lang.Character.codePointAt(r8, r2);
        r7 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        if (r6 < r7) goto L_0x004f;
    L_0x004c:
        r2 = r2 + 1;
        goto L_0x0055;
    L_0x004f:
        r8 = new com.google.android.gms.internal.measurement.zzib;
        r8.<init>(r2, r4);
        throw r8;
    L_0x0055:
        r2 = r2 + 1;
        goto L_0x002a;
    L_0x0058:
        r3 = r3 + r1;
    L_0x0059:
        if (r3 < r0) goto L_0x005c;
    L_0x005b:
        return r3;
    L_0x005c:
        r8 = new java.lang.IllegalArgumentException;
        r0 = (long) r3;
        r2 = 4294967296; // 0x100000000 float:0.0 double:2.121995791E-314;
        r0 = r0 + r2;
        r2 = 54;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "UTF-8 length does not fit in int: ";
        r3.append(r2);
        r3.append(r0);
        r0 = r3.toString();
        r8.<init>(r0);
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzhy.zza(java.lang.CharSequence):int");
    }

    static int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        return zzamz.zzb(charSequence, bArr, i, i2);
    }

    static String zzh(byte[] bArr, int i, int i2) throws zzfi {
        return zzamz.zzh(bArr, i, i2);
    }

    static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        zzhz zzhz = zzamz;
        if (byteBuffer.hasArray()) {
            int arrayOffset = byteBuffer.arrayOffset();
            byteBuffer.position(zza(charSequence, byteBuffer.array(), byteBuffer.position() + arrayOffset, byteBuffer.remaining()) - arrayOffset);
        } else if (byteBuffer.isDirect()) {
            zzhz.zzb(charSequence, byteBuffer);
        } else {
            zzhz.zzc(charSequence, byteBuffer);
        }
    }

    static {
        zzhz zzic;
        Object obj = (zzhv.zzwt() && zzhv.zzwu()) ? 1 : null;
        if (obj == null || zzdi.zzrv()) {
            zzic = new zzic();
        } else {
            zzic = new zzie();
        }
        zzamz = zzic;
    }
}
