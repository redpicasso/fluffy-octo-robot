package com.google.android.gms.internal.firebase_ml;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import java.io.File;
import java.io.IOException;

public final class zzox {
    private static final GmsLogger zzaoz = new GmsLogger("RemoteModelUtils", "");
    private final zzol zzaro;

    zzox(@NonNull zzol zzol) {
        Preconditions.checkNotNull(zzol);
        this.zzaro = zzol;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.android.gms.internal.firebase_ml.zzox.zzf(java.io.File):java.lang.String, dom blocks: [B:22:0x0050, B:27:0x005e, B:32:0x006c]
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.lang.Iterable.forEach(Unknown Source)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007c  */
    @androidx.annotation.Nullable
    private static java.lang.String zzf(java.io.File r8) throws java.io.IOException {
        /*
        r0 = "RemoteModelUtils";
        r1 = 0;
        r2 = "SHA-256";	 Catch:{ NoSuchAlgorithmException -> 0x006b, FileNotFoundException -> 0x005d, IOException -> 0x004f, all -> 0x004c }
        r2 = java.security.MessageDigest.getInstance(r2);	 Catch:{ NoSuchAlgorithmException -> 0x006b, FileNotFoundException -> 0x005d, IOException -> 0x004f, all -> 0x004c }
        r3 = new java.io.FileInputStream;	 Catch:{ NoSuchAlgorithmException -> 0x006b, FileNotFoundException -> 0x005d, IOException -> 0x004f, all -> 0x004c }
        r3.<init>(r8);	 Catch:{ NoSuchAlgorithmException -> 0x006b, FileNotFoundException -> 0x005d, IOException -> 0x004f, all -> 0x004c }
        r8 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r8 = new byte[r8];	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
    L_0x0012:
        r4 = r3.read(r8);	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r5 = -1;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r6 = 0;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        if (r4 == r5) goto L_0x001e;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
    L_0x001a:
        r2.update(r8, r6, r4);	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        goto L_0x0012;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
    L_0x001e:
        r8 = r2.digest();	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r2 = new java.lang.StringBuilder;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r2.<init>();	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
    L_0x0027:
        r4 = r8.length;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        if (r6 >= r4) goto L_0x0044;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
    L_0x002a:
        r4 = r8[r6];	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r4 = r4 & 255;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r4 = java.lang.Integer.toHexString(r4);	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r5 = r4.length();	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r7 = 1;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        if (r5 != r7) goto L_0x003e;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
    L_0x0039:
        r5 = 48;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r2.append(r5);	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
    L_0x003e:
        r2.append(r4);	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r6 = r6 + 1;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        goto L_0x0027;	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
    L_0x0044:
        r8 = r2.toString();	 Catch:{ NoSuchAlgorithmException -> 0x006c, FileNotFoundException -> 0x005e, IOException -> 0x0050 }
        r3.close();
        return r8;
    L_0x004c:
        r8 = move-exception;
        r3 = r1;
        goto L_0x007a;
    L_0x004f:
        r3 = r1;
    L_0x0050:
        r8 = zzaoz;	 Catch:{ all -> 0x0079 }
        r2 = "Cannot read the temp file for SHA-256 check";	 Catch:{ all -> 0x0079 }
        r8.e(r0, r2);	 Catch:{ all -> 0x0079 }
        if (r3 == 0) goto L_0x0078;
    L_0x0059:
        r3.close();
        goto L_0x0078;
    L_0x005d:
        r3 = r1;
    L_0x005e:
        r8 = zzaoz;	 Catch:{ all -> 0x0079 }
        r2 = "Temp file is not found";	 Catch:{ all -> 0x0079 }
        r8.e(r0, r2);	 Catch:{ all -> 0x0079 }
        if (r3 == 0) goto L_0x0078;
    L_0x0067:
        r3.close();
        goto L_0x0078;
    L_0x006b:
        r3 = r1;
    L_0x006c:
        r8 = zzaoz;	 Catch:{ all -> 0x0079 }
        r2 = "Do not have SHA-256 algorithm";	 Catch:{ all -> 0x0079 }
        r8.e(r0, r2);	 Catch:{ all -> 0x0079 }
        if (r3 == 0) goto L_0x0078;
    L_0x0075:
        r3.close();
    L_0x0078:
        return r1;
    L_0x0079:
        r8 = move-exception;
    L_0x007a:
        if (r3 == 0) goto L_0x007f;
    L_0x007c:
        r3.close();
    L_0x007f:
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzox.zzf(java.io.File):java.lang.String");
    }

    static boolean zza(@NonNull File file, @NonNull String str) {
        Object zzf;
        GmsLogger gmsLogger;
        String str2;
        String str3 = "RemoteModelUtils";
        try {
            zzf = zzf(file);
        } catch (IOException unused) {
            gmsLogger = zzaoz;
            str2 = "Failed to close the tmp FileInputStream: ";
            String valueOf = String.valueOf(file.getAbsolutePath());
            gmsLogger.d(str3, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            zzf = "";
        }
        gmsLogger = zzaoz;
        str2 = "Calculated hash value is: ";
        String valueOf2 = String.valueOf(zzf);
        gmsLogger.d(str3, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
        return str.equals(zzf);
    }

    final boolean zzb(File file, zzon zzon) {
        return this.zzaro.zza(file, zzon);
    }
}
