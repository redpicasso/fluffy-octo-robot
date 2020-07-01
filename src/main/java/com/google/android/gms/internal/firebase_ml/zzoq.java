package com.google.android.gms.internal.firebase_ml;

import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import java.io.File;

public final class zzoq {
    private static final GmsLogger zzaoz = new GmsLogger("RemoteModelFileManager", "");
    private static String zzarb;
    @VisibleForTesting
    private static final String zzarc;
    @VisibleForTesting
    private static final String zzard = String.format(zzarb, new Object[]{"automl"});
    @VisibleForTesting
    private static final String zzare = String.format(zzarb, new Object[]{"base"});
    @VisibleForTesting
    private static final String zzarf = String.format(zzarb, new Object[]{"translate"});
    private final FirebaseApp zzapo;
    private final String zzaqg;
    private final zzok zzara;
    private final zzox zzarg;
    private final zzoh zzarh;

    public zzoq(@NonNull FirebaseApp firebaseApp, @NonNull String str, @NonNull zzol zzol, @NonNull zzok zzok) {
        this.zzapo = firebaseApp;
        this.zzaqg = str;
        this.zzara = zzok;
        this.zzarg = new zzox(zzol);
        int i = zzor.zzari[zzok.ordinal()];
        if (i == 1) {
            this.zzarh = new zzob(firebaseApp, str);
        } else if (i == 2) {
            this.zzarh = new zzot(firebaseApp, str);
        } else if (i == 3 || i == 4) {
            this.zzarh = new zzos(firebaseApp, str);
        } else {
            throw new IllegalArgumentException("Unexpected model type");
        }
    }

    /* JADX WARNING: Missing block: B:61:0x013f, code:
            return null;
     */
    @androidx.annotation.WorkerThread
    @androidx.annotation.Nullable
    public final synchronized java.io.File zza(@androidx.annotation.NonNull android.os.ParcelFileDescriptor r10, @androidx.annotation.NonNull java.lang.String r11, @androidx.annotation.NonNull com.google.android.gms.internal.firebase_ml.zzon r12) throws com.google.firebase.ml.common.FirebaseMLException {
        /*
        r9 = this;
        monitor-enter(r9);
        r0 = r9.zzapo;	 Catch:{ all -> 0x0183 }
        r0 = com.google.android.gms.internal.firebase_ml.zzoa.zzb(r0, r11);	 Catch:{ all -> 0x0183 }
        r1 = r9.zzara;	 Catch:{ all -> 0x0183 }
        r1 = r1.equals(r0);	 Catch:{ all -> 0x0183 }
        if (r1 != 0) goto L_0x0056;
    L_0x000f:
        r10 = new com.google.firebase.ml.common.FirebaseMLException;	 Catch:{ all -> 0x0183 }
        if (r0 != 0) goto L_0x0016;
    L_0x0013:
        r11 = "invalid";
        goto L_0x001a;
    L_0x0016:
        r11 = r0.name();	 Catch:{ all -> 0x0183 }
    L_0x001a:
        r12 = r9.zzara;	 Catch:{ all -> 0x0183 }
        r12 = r12.name();	 Catch:{ all -> 0x0183 }
        r0 = java.lang.String.valueOf(r11);	 Catch:{ all -> 0x0183 }
        r0 = r0.length();	 Catch:{ all -> 0x0183 }
        r0 = r0 + 93;
        r1 = java.lang.String.valueOf(r12);	 Catch:{ all -> 0x0183 }
        r1 = r1.length();	 Catch:{ all -> 0x0183 }
        r0 = r0 + r1;
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0183 }
        r1.<init>(r0);	 Catch:{ all -> 0x0183 }
        r0 = "You are trying to use a ";
        r1.append(r0);	 Catch:{ all -> 0x0183 }
        r1.append(r11);	 Catch:{ all -> 0x0183 }
        r11 = " model as a ";
        r1.append(r11);	 Catch:{ all -> 0x0183 }
        r1.append(r12);	 Catch:{ all -> 0x0183 }
        r11 = " model. Please make sure you specified the correct model.";
        r1.append(r11);	 Catch:{ all -> 0x0183 }
        r11 = r1.toString();	 Catch:{ all -> 0x0183 }
        r12 = 3;
        r10.<init>(r11, r12);	 Catch:{ all -> 0x0183 }
        throw r10;	 Catch:{ all -> 0x0183 }
    L_0x0056:
        r0 = r9.zzapo;	 Catch:{ all -> 0x0183 }
        r1 = r9.zzaqg;	 Catch:{ all -> 0x0183 }
        r2 = r9.zzara;	 Catch:{ all -> 0x0183 }
        r3 = 1;
        r0 = zza(r0, r1, r2, r3);	 Catch:{ all -> 0x0183 }
        r1 = new java.io.File;	 Catch:{ all -> 0x0183 }
        r2 = "to_be_validated_model.tmp";
        r1.<init>(r0, r2);	 Catch:{ all -> 0x0183 }
        r0 = 0;
        r2 = new android.os.ParcelFileDescriptor$AutoCloseInputStream;	 Catch:{ IOException -> 0x015a }
        r2.<init>(r10);	 Catch:{ IOException -> 0x015a }
        r10 = new java.io.FileOutputStream;	 Catch:{ Throwable -> 0x0150, all -> 0x014d }
        r10.<init>(r1);	 Catch:{ Throwable -> 0x0150, all -> 0x014d }
        r3 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r3 = new byte[r3];	 Catch:{ Throwable -> 0x0143, all -> 0x0140 }
    L_0x0077:
        r4 = r2.read(r3);	 Catch:{ Throwable -> 0x0143, all -> 0x0140 }
        r5 = -1;
        r6 = 0;
        if (r4 == r5) goto L_0x0083;
    L_0x007f:
        r10.write(r3, r6, r4);	 Catch:{ Throwable -> 0x0143, all -> 0x0140 }
        goto L_0x0077;
    L_0x0083:
        r3 = r10.getFD();	 Catch:{ Throwable -> 0x0143, all -> 0x0140 }
        r3.sync();	 Catch:{ Throwable -> 0x0143, all -> 0x0140 }
        zza(r0, r10);	 Catch:{ Throwable -> 0x0150, all -> 0x014d }
        zza(r0, r2);	 Catch:{ IOException -> 0x015a }
        r10 = com.google.android.gms.internal.firebase_ml.zzox.zza(r1, r11);	 Catch:{ all -> 0x0183 }
        if (r10 == 0) goto L_0x00ea;
    L_0x0096:
        r2 = r9.zzarg;	 Catch:{ all -> 0x0183 }
        r6 = r2.zzb(r1, r12);	 Catch:{ all -> 0x0183 }
        if (r6 != 0) goto L_0x00ea;
    L_0x009e:
        r12 = r9.zzapo;	 Catch:{ all -> 0x0183 }
        r12 = r12.getApplicationContext();	 Catch:{ all -> 0x0183 }
        r12 = com.google.android.gms.internal.firebase_ml.zznk.zza(r12);	 Catch:{ all -> 0x0183 }
        r2 = r9.zzapo;	 Catch:{ all -> 0x0183 }
        r3 = r9.zzaqg;	 Catch:{ all -> 0x0183 }
        com.google.android.gms.internal.firebase_ml.zzoa.zza(r2, r3, r11, r12);	 Catch:{ all -> 0x0183 }
        r2 = zzaoz;	 Catch:{ all -> 0x0183 }
        r3 = "RemoteModelFileManager";
        r4 = "Model is not compatible. Model hash: ";
        r5 = java.lang.String.valueOf(r11);	 Catch:{ all -> 0x0183 }
        r7 = r5.length();	 Catch:{ all -> 0x0183 }
        if (r7 == 0) goto L_0x00c4;
    L_0x00bf:
        r4 = r4.concat(r5);	 Catch:{ all -> 0x0183 }
        goto L_0x00ca;
    L_0x00c4:
        r5 = new java.lang.String;	 Catch:{ all -> 0x0183 }
        r5.<init>(r4);	 Catch:{ all -> 0x0183 }
        r4 = r5;
    L_0x00ca:
        r2.d(r3, r4);	 Catch:{ all -> 0x0183 }
        r2 = zzaoz;	 Catch:{ all -> 0x0183 }
        r3 = "RemoteModelFileManager";
        r4 = "The current app version is: ";
        r12 = java.lang.String.valueOf(r12);	 Catch:{ all -> 0x0183 }
        r5 = r12.length();	 Catch:{ all -> 0x0183 }
        if (r5 == 0) goto L_0x00e2;
    L_0x00dd:
        r12 = r4.concat(r12);	 Catch:{ all -> 0x0183 }
        goto L_0x00e7;
    L_0x00e2:
        r12 = new java.lang.String;	 Catch:{ all -> 0x0183 }
        r12.<init>(r4);	 Catch:{ all -> 0x0183 }
    L_0x00e7:
        r2.d(r3, r12);	 Catch:{ all -> 0x0183 }
    L_0x00ea:
        if (r10 == 0) goto L_0x00f7;
    L_0x00ec:
        if (r6 != 0) goto L_0x00ef;
    L_0x00ee:
        goto L_0x00f7;
    L_0x00ef:
        r10 = r9.zzarh;	 Catch:{ all -> 0x0183 }
        r10 = r10.zza(r1);	 Catch:{ all -> 0x0183 }
        monitor-exit(r9);
        return r10;
    L_0x00f7:
        if (r10 != 0) goto L_0x0116;
    L_0x00f9:
        r10 = zzaoz;	 Catch:{ all -> 0x0183 }
        r12 = "RemoteModelFileManager";
        r2 = "Hash does not match with expected: ";
        r11 = java.lang.String.valueOf(r11);	 Catch:{ all -> 0x0183 }
        r3 = r11.length();	 Catch:{ all -> 0x0183 }
        if (r3 == 0) goto L_0x010e;
    L_0x0109:
        r11 = r2.concat(r11);	 Catch:{ all -> 0x0183 }
        goto L_0x0113;
    L_0x010e:
        r11 = new java.lang.String;	 Catch:{ all -> 0x0183 }
        r11.<init>(r2);	 Catch:{ all -> 0x0183 }
    L_0x0113:
        r10.d(r12, r11);	 Catch:{ all -> 0x0183 }
    L_0x0116:
        r10 = r1.delete();	 Catch:{ all -> 0x0183 }
        if (r10 != 0) goto L_0x013e;
    L_0x011c:
        r10 = zzaoz;	 Catch:{ all -> 0x0183 }
        r11 = "RemoteModelFileManager";
        r12 = "Failed to delete the temp file: ";
        r1 = r1.getAbsolutePath();	 Catch:{ all -> 0x0183 }
        r1 = java.lang.String.valueOf(r1);	 Catch:{ all -> 0x0183 }
        r2 = r1.length();	 Catch:{ all -> 0x0183 }
        if (r2 == 0) goto L_0x0135;
    L_0x0130:
        r12 = r12.concat(r1);	 Catch:{ all -> 0x0183 }
        goto L_0x013b;
    L_0x0135:
        r1 = new java.lang.String;	 Catch:{ all -> 0x0183 }
        r1.<init>(r12);	 Catch:{ all -> 0x0183 }
        r12 = r1;
    L_0x013b:
        r10.d(r11, r12);	 Catch:{ all -> 0x0183 }
    L_0x013e:
        monitor-exit(r9);
        return r0;
    L_0x0140:
        r11 = move-exception;
        r12 = r0;
        goto L_0x0149;
    L_0x0143:
        r11 = move-exception;
        throw r11;	 Catch:{ all -> 0x0145 }
    L_0x0145:
        r12 = move-exception;
        r8 = r12;
        r12 = r11;
        r11 = r8;
    L_0x0149:
        zza(r12, r10);	 Catch:{ Throwable -> 0x0150, all -> 0x014d }
        throw r11;	 Catch:{ Throwable -> 0x0150, all -> 0x014d }
    L_0x014d:
        r10 = move-exception;
        r11 = r0;
        goto L_0x0156;
    L_0x0150:
        r10 = move-exception;
        throw r10;	 Catch:{ all -> 0x0152 }
    L_0x0152:
        r11 = move-exception;
        r8 = r11;
        r11 = r10;
        r10 = r8;
    L_0x0156:
        zza(r11, r2);	 Catch:{ IOException -> 0x015a }
        throw r10;	 Catch:{ IOException -> 0x015a }
    L_0x015a:
        r10 = move-exception;
        r11 = zzaoz;	 Catch:{ all -> 0x0183 }
        r12 = "RemoteModelFileManager";
        r10 = java.lang.String.valueOf(r10);	 Catch:{ all -> 0x0183 }
        r1 = java.lang.String.valueOf(r10);	 Catch:{ all -> 0x0183 }
        r1 = r1.length();	 Catch:{ all -> 0x0183 }
        r1 = r1 + 56;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0183 }
        r2.<init>(r1);	 Catch:{ all -> 0x0183 }
        r1 = "Failed to copy downloaded model file to private folder: ";
        r2.append(r1);	 Catch:{ all -> 0x0183 }
        r2.append(r10);	 Catch:{ all -> 0x0183 }
        r10 = r2.toString();	 Catch:{ all -> 0x0183 }
        r11.e(r12, r10);	 Catch:{ all -> 0x0183 }
        monitor-exit(r9);
        return r0;
    L_0x0183:
        r10 = move-exception;
        monitor-exit(r9);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzoq.zza(android.os.ParcelFileDescriptor, java.lang.String, com.google.android.gms.internal.firebase_ml.zzon):java.io.File");
    }

    @WorkerThread
    public final synchronized boolean zzb(@NonNull File file) throws FirebaseMLException {
        File zza = zza(this.zzapo, this.zzaqg, this.zzara, false);
        if (!zza.exists()) {
            return false;
        }
        boolean z = true;
        for (File file2 : zza.listFiles()) {
            if (!(file2.equals(file) || zze(file2))) {
                z = false;
            }
        }
        return z;
    }

    @WorkerThread
    public final synchronized File zzc(@NonNull File file) throws FirebaseMLException {
        File file2 = new File(String.valueOf(zza(this.zzapo, this.zzaqg, this.zzara, false).getAbsolutePath()).concat("/0"));
        if (file2.exists()) {
            return file;
        }
        if (file.renameTo(file2)) {
            return file2;
        }
        return file;
    }

    @WorkerThread
    @Nullable
    public final synchronized String zzmf() throws FirebaseMLException {
        File zza = zza(this.zzapo, this.zzaqg, this.zzara, false);
        int zzd = zzd(zza);
        if (zzd < 0) {
            return null;
        }
        String absolutePath = zza.getAbsolutePath();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(absolutePath).length() + 12);
        stringBuilder.append(absolutePath);
        stringBuilder.append("/");
        stringBuilder.append(zzd);
        return stringBuilder.toString();
    }

    @WorkerThread
    public final synchronized void zzmg() throws FirebaseMLException {
        this.zzarh.zzlt();
    }

    @WorkerThread
    static File zza(@NonNull FirebaseApp firebaseApp, @NonNull String str, @NonNull zzok zzok) throws FirebaseMLException {
        return zza(firebaseApp, str, zzok, true);
    }

    @WorkerThread
    static File zzb(@NonNull FirebaseApp firebaseApp, @NonNull String str, zzok zzok) throws FirebaseMLException {
        return zza(firebaseApp, str, zzok, false);
    }

    @WorkerThread
    private static File zza(@NonNull FirebaseApp firebaseApp, @NonNull String str, @NonNull zzok zzok, boolean z) throws FirebaseMLException {
        String str2;
        StringBuilder stringBuilder;
        File file;
        int i = zzor.zzari[zzok.ordinal()];
        if (i == 1) {
            str2 = zzare;
        } else if (i == 2) {
            str2 = zzarf;
        } else if (i == 3) {
            str2 = zzard;
        } else if (i == 4) {
            str2 = zzarc;
        } else {
            str = zzok.name();
            stringBuilder = new StringBuilder(String.valueOf(str).length() + 69);
            stringBuilder.append("Unknown model type ");
            stringBuilder.append(str);
            stringBuilder.append(". Cannot find a dir to store the downloaded model.");
            throw new FirebaseMLException(stringBuilder.toString(), 13);
        }
        if (VERSION.SDK_INT >= 21) {
            file = new File(firebaseApp.getApplicationContext().getNoBackupFilesDir(), str2);
        } else {
            file = firebaseApp.getApplicationContext().getDir(str2, 0);
        }
        File file2 = new File(new File(z ? new File(file, "temp") : file, firebaseApp.getPersistenceKey()), str);
        if (!file2.exists()) {
            GmsLogger gmsLogger = zzaoz;
            str2 = "model folder does not exist, creating one: ";
            String valueOf = String.valueOf(file2.getAbsolutePath());
            gmsLogger.d("RemoteModelFileManager", valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            file2.mkdirs();
        } else if (!file2.isDirectory()) {
            String valueOf2 = String.valueOf(file2);
            stringBuilder = new StringBuilder(String.valueOf(valueOf2).length() + 71);
            stringBuilder.append("Can not create model folder, since an existing file has the same name: ");
            stringBuilder.append(valueOf2);
            throw new FirebaseMLException(stringBuilder.toString(), 6);
        }
        return file2;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.android.gms.internal.firebase_ml.zzoq.zzd(java.io.File):int, dom blocks: []
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
    @androidx.annotation.WorkerThread
    static int zzd(@androidx.annotation.NonNull java.io.File r7) {
        /*
        r7 = r7.listFiles();
        r0 = r7.length;
        if (r0 != 0) goto L_0x0009;
    L_0x0007:
        r7 = -1;
        return r7;
    L_0x0009:
        r0 = r7.length;
        r1 = 0;
        r2 = 0;
    L_0x000c:
        if (r1 >= r0) goto L_0x0042;
    L_0x000e:
        r3 = r7[r1];
        r4 = r3.getName();	 Catch:{ NumberFormatException -> 0x001d }
        r4 = java.lang.Integer.parseInt(r4);	 Catch:{ NumberFormatException -> 0x001d }
        r2 = java.lang.Math.max(r2, r4);	 Catch:{ NumberFormatException -> 0x001d }
        goto L_0x003f;
        r4 = zzaoz;
        r5 = "Contains non-integer file name ";
        r3 = r3.getName();
        r3 = java.lang.String.valueOf(r3);
        r6 = r3.length();
        if (r6 == 0) goto L_0x0035;
    L_0x0030:
        r3 = r5.concat(r3);
        goto L_0x003a;
    L_0x0035:
        r3 = new java.lang.String;
        r3.<init>(r5);
    L_0x003a:
        r5 = "RemoteModelFileManager";
        r4.d(r5, r3);
    L_0x003f:
        r1 = r1 + 1;
        goto L_0x000c;
    L_0x0042:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzoq.zzd(java.io.File):int");
    }

    static boolean zze(File file) {
        Object obj;
        if (file.isDirectory()) {
            obj = 1;
            for (File zze : file.listFiles()) {
                obj = (obj == null || !zze(zze)) ? null : 1;
            }
        } else {
            obj = 1;
        }
        return obj != null && file.delete();
    }

    static {
        String str = "com.google.firebase.ml.%s.models";
        zzarb = str;
        zzarc = String.format(str, new Object[]{"custom"});
    }
}
