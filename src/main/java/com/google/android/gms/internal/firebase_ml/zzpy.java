package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.common.internal.GmsLogger;

public final class zzpy {
    private static final GmsLogger zzaoz = new GmsLogger("MLKitImageUtils", "");
    private static zzpy zzaxq = new zzpy();

    private zzpy() {
    }

    public static zzpy zznl() {
        return zzaxq;
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b5 A:{Catch:{ all -> 0x00be }} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b0 A:{Catch:{ all -> 0x00be }} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b0 A:{Catch:{ all -> 0x00be }} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b5 A:{Catch:{ all -> 0x00be }} */
    /* JADX WARNING: Missing block: B:8:0x0047, code:
            r8 = r12;
     */
    /* JADX WARNING: Missing block: B:15:0x0068, code:
            r8 = r3;
     */
    /* JADX WARNING: Missing block: B:17:0x0073, code:
            if (r8 == null) goto L_0x0083;
     */
    /* JADX WARNING: Missing block: B:18:0x0075, code:
            r10 = android.graphics.Bitmap.createBitmap(r1, 0, 0, r6, r7, r8, true);
     */
    /* JADX WARNING: Missing block: B:19:0x007d, code:
            if (r1 == r10) goto L_0x0083;
     */
    /* JADX WARNING: Missing block: B:20:0x007f, code:
            r1.recycle();
     */
    /* JADX WARNING: Missing block: B:21:0x0083, code:
            r10 = r1;
     */
    /* JADX WARNING: Missing block: B:22:0x0084, code:
            com.google.android.gms.common.util.IOUtils.closeQuietly(r0);
            com.google.android.gms.common.util.IOUtils.closeQuietly(r2);
     */
    /* JADX WARNING: Missing block: B:23:0x008a, code:
            return r10;
     */
    public static android.graphics.Bitmap zza(android.content.ContentResolver r10, android.net.Uri r11, int r12) throws java.io.IOException {
        /*
        r12 = 0;
        r0 = r10.openInputStream(r11);	 Catch:{ FileNotFoundException -> 0x009a, all -> 0x0096 }
        r1 = new android.graphics.BitmapFactory$Options;	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        r1.<init>();	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        r2 = 1;
        r1.inJustDecodeBounds = r2;	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        android.graphics.BitmapFactory.decodeStream(r0, r12, r1);	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        r1 = new android.graphics.BitmapFactory$Options;	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        r1.<init>();	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        r2 = r1.outWidth;	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        r2 = r2 / 1024;
        r3 = r1.outHeight;	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        r3 = r3 / 1024;
        r2 = java.lang.Math.max(r2, r3);	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        r1.inSampleSize = r2;	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        r2 = r10.openInputStream(r11);	 Catch:{ FileNotFoundException -> 0x0092, all -> 0x008f }
        r1 = android.graphics.BitmapFactory.decodeStream(r2, r12, r1);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        r10 = zza(r10, r11);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        r3 = new android.graphics.Matrix;	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        r3.<init>();	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        r6 = r1.getWidth();	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        r7 = r1.getHeight();	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        r4 = -1028390912; // 0xffffffffc2b40000 float:-90.0 double:NaN;
        r5 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r8 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        switch(r10) {
            case 0: goto L_0x0047;
            case 1: goto L_0x0047;
            case 2: goto L_0x006a;
            case 3: goto L_0x0063;
            case 4: goto L_0x005f;
            case 5: goto L_0x0058;
            case 6: goto L_0x0054;
            case 7: goto L_0x004d;
            case 8: goto L_0x0049;
            default: goto L_0x0047;
        };	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
    L_0x0047:
        r8 = r12;
        goto L_0x0073;
    L_0x0049:
        r3.postRotate(r4);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        goto L_0x0068;
    L_0x004d:
        r3.postRotate(r4);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        r3.postScale(r8, r9);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        goto L_0x0068;
    L_0x0054:
        r3.postRotate(r5);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        goto L_0x0068;
    L_0x0058:
        r3.postRotate(r5);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        r3.postScale(r8, r9);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        goto L_0x0068;
    L_0x005f:
        r3.postScale(r9, r8);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        goto L_0x0068;
    L_0x0063:
        r10 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r3.postRotate(r10);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
    L_0x0068:
        r8 = r3;
        goto L_0x0073;
    L_0x006a:
        r12 = new android.graphics.Matrix;	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        r12.<init>();	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        r12.postScale(r8, r9);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        goto L_0x0047;
    L_0x0073:
        if (r8 == 0) goto L_0x0083;
    L_0x0075:
        r4 = 0;
        r5 = 0;
        r9 = 1;
        r3 = r1;
        r10 = android.graphics.Bitmap.createBitmap(r3, r4, r5, r6, r7, r8, r9);	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        if (r1 == r10) goto L_0x0083;
    L_0x007f:
        r1.recycle();	 Catch:{ FileNotFoundException -> 0x008d, all -> 0x008b }
        goto L_0x0084;
    L_0x0083:
        r10 = r1;
    L_0x0084:
        com.google.android.gms.common.util.IOUtils.closeQuietly(r0);
        com.google.android.gms.common.util.IOUtils.closeQuietly(r2);
        return r10;
    L_0x008b:
        r10 = move-exception;
        goto L_0x00c0;
    L_0x008d:
        r10 = move-exception;
        goto L_0x0094;
    L_0x008f:
        r10 = move-exception;
        r2 = r12;
        goto L_0x00c0;
    L_0x0092:
        r10 = move-exception;
        r2 = r12;
    L_0x0094:
        r12 = r0;
        goto L_0x009c;
    L_0x0096:
        r10 = move-exception;
        r0 = r12;
        r2 = r0;
        goto L_0x00c0;
    L_0x009a:
        r10 = move-exception;
        r2 = r12;
    L_0x009c:
        r0 = zzaoz;	 Catch:{ all -> 0x00be }
        r1 = "MLKitImageUtils";
        r3 = "Could not open file: ";
        r11 = r11.toString();	 Catch:{ all -> 0x00be }
        r11 = java.lang.String.valueOf(r11);	 Catch:{ all -> 0x00be }
        r4 = r11.length();	 Catch:{ all -> 0x00be }
        if (r4 == 0) goto L_0x00b5;
    L_0x00b0:
        r11 = r3.concat(r11);	 Catch:{ all -> 0x00be }
        goto L_0x00ba;
    L_0x00b5:
        r11 = new java.lang.String;	 Catch:{ all -> 0x00be }
        r11.<init>(r3);	 Catch:{ all -> 0x00be }
    L_0x00ba:
        r0.e(r1, r11, r10);	 Catch:{ all -> 0x00be }
        throw r10;	 Catch:{ all -> 0x00be }
    L_0x00be:
        r10 = move-exception;
        r0 = r12;
    L_0x00c0:
        com.google.android.gms.common.util.IOUtils.closeQuietly(r0);
        com.google.android.gms.common.util.IOUtils.closeQuietly(r2);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzpy.zza(android.content.ContentResolver, android.net.Uri, int):android.graphics.Bitmap");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005d A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005d A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005e  */
    private static int zza(android.content.ContentResolver r7, android.net.Uri r8) {
        /*
        r0 = r8.getScheme();
        r1 = "content";
        r0 = r1.equals(r0);
        r1 = 0;
        if (r0 != 0) goto L_0x001a;
    L_0x000d:
        r0 = r8.getScheme();
        r2 = "file";
        r0 = r2.equals(r0);
        if (r0 != 0) goto L_0x001a;
    L_0x0019:
        return r1;
    L_0x001a:
        r0 = 0;
        r7 = r7.openInputStream(r8);	 Catch:{ IOException -> 0x0030, all -> 0x002d }
        r2 = new androidx.exifinterface.media.ExifInterface;	 Catch:{ IOException -> 0x002b }
        r2.<init>(r7);	 Catch:{ IOException -> 0x002b }
        com.google.android.gms.common.util.IOUtils.closeQuietly(r7);
        r0 = r2;
        goto L_0x005b;
    L_0x0029:
        r8 = move-exception;
        goto L_0x0066;
    L_0x002b:
        r2 = move-exception;
        goto L_0x0032;
    L_0x002d:
        r8 = move-exception;
        r7 = r0;
        goto L_0x0066;
    L_0x0030:
        r2 = move-exception;
        r7 = r0;
    L_0x0032:
        r3 = zzaoz;	 Catch:{ all -> 0x0029 }
        r4 = "MLKitImageUtils";
        r8 = java.lang.String.valueOf(r8);	 Catch:{ all -> 0x0029 }
        r5 = java.lang.String.valueOf(r8);	 Catch:{ all -> 0x0029 }
        r5 = r5.length();	 Catch:{ all -> 0x0029 }
        r5 = r5 + 48;
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0029 }
        r6.<init>(r5);	 Catch:{ all -> 0x0029 }
        r5 = "failed to open file to read rotation meta data: ";
        r6.append(r5);	 Catch:{ all -> 0x0029 }
        r6.append(r8);	 Catch:{ all -> 0x0029 }
        r8 = r6.toString();	 Catch:{ all -> 0x0029 }
        r3.e(r4, r8, r2);	 Catch:{ all -> 0x0029 }
        com.google.android.gms.common.util.IOUtils.closeQuietly(r7);
    L_0x005b:
        if (r0 != 0) goto L_0x005e;
    L_0x005d:
        return r1;
    L_0x005e:
        r7 = 1;
        r8 = "Orientation";
        r7 = r0.getAttributeInt(r8, r7);
        return r7;
    L_0x0066:
        com.google.android.gms.common.util.IOUtils.closeQuietly(r7);
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzpy.zza(android.content.ContentResolver, android.net.Uri):int");
    }
}
