package com.google.android.gms.internal.firebase_ml;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;

public final class zzoo {
    private static final GmsLogger zzaoz = new GmsLogger("ModelDownloadManager", "");
    private final Context zzad;
    private final FirebaseApp zzapo;
    private final DownloadManager zzaqu = ((DownloadManager) this.zzad.getSystemService("download"));
    private final FirebaseRemoteModel zzaqv;
    private final zzon zzaqw;
    private final zzoq zzaqx;

    zzoo(@NonNull FirebaseApp firebaseApp, @NonNull zzoq zzoq, @NonNull FirebaseRemoteModel firebaseRemoteModel, @NonNull zzon zzon) {
        this.zzapo = firebaseApp;
        this.zzaqx = zzoq;
        this.zzad = firebaseApp.getApplicationContext();
        this.zzaqv = firebaseRemoteModel;
        if (this.zzaqu == null) {
            zzaoz.d("ModelDownloadManager", "Download manager service is not available in the service.");
        }
        this.zzaqw = zzon;
    }

    @WorkerThread
    @Nullable
    final synchronized Long zzlv() throws FirebaseMLException {
        zzop zzmc = zzmc();
        if (zzmc == null) {
            GmsLogger gmsLogger = zzaoz;
            String str = "ModelDownloadManager";
            String str2 = "No model updates for model: ";
            String valueOf = String.valueOf(this.zzaqv.zzmj());
            gmsLogger.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return null;
        }
        return zza(zzmc);
    }

    @Nullable
    final synchronized Long zzlw() {
        return zzoa.zza(this.zzapo, this.zzaqv.zzmj());
    }

    @Nullable
    final synchronized String zzlx() {
        return zzoa.zzc(this.zzapo, this.zzaqv.zzmj());
    }

    /* JADX WARNING: Missing block: B:11:0x005b, code:
            return;
     */
    /* JADX WARNING: Missing block: B:13:0x005d, code:
            return;
     */
    final synchronized void zzly() throws com.google.firebase.ml.common.FirebaseMLException {
        /*
        r6 = this;
        monitor-enter(r6);
        r0 = r6.zzlw();	 Catch:{ all -> 0x005e }
        r1 = r6.zzaqu;	 Catch:{ all -> 0x005e }
        if (r1 == 0) goto L_0x005c;
    L_0x0009:
        if (r0 != 0) goto L_0x000c;
    L_0x000b:
        goto L_0x005c;
    L_0x000c:
        r1 = zzaoz;	 Catch:{ all -> 0x005e }
        r2 = "ModelDownloadManager";
        r3 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x005e }
        r4 = java.lang.String.valueOf(r3);	 Catch:{ all -> 0x005e }
        r4 = r4.length();	 Catch:{ all -> 0x005e }
        r4 = r4 + 44;
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x005e }
        r5.<init>(r4);	 Catch:{ all -> 0x005e }
        r4 = "Cancel or remove existing downloading task: ";
        r5.append(r4);	 Catch:{ all -> 0x005e }
        r5.append(r3);	 Catch:{ all -> 0x005e }
        r3 = r5.toString();	 Catch:{ all -> 0x005e }
        r1.d(r2, r3);	 Catch:{ all -> 0x005e }
        r1 = r6.zzaqu;	 Catch:{ all -> 0x005e }
        r2 = 1;
        r2 = new long[r2];	 Catch:{ all -> 0x005e }
        r3 = 0;
        r4 = r0.longValue();	 Catch:{ all -> 0x005e }
        r2[r3] = r4;	 Catch:{ all -> 0x005e }
        r0 = r1.remove(r2);	 Catch:{ all -> 0x005e }
        if (r0 > 0) goto L_0x004a;
    L_0x0044:
        r0 = r6.zzlz();	 Catch:{ all -> 0x005e }
        if (r0 != 0) goto L_0x005a;
    L_0x004a:
        r0 = r6.zzaqx;	 Catch:{ all -> 0x005e }
        r0.zzmg();	 Catch:{ all -> 0x005e }
        r0 = r6.zzapo;	 Catch:{ all -> 0x005e }
        r1 = r6.zzaqv;	 Catch:{ all -> 0x005e }
        r1 = r1.zzmj();	 Catch:{ all -> 0x005e }
        com.google.android.gms.internal.firebase_ml.zzoa.zzh(r0, r1);	 Catch:{ all -> 0x005e }
    L_0x005a:
        monitor-exit(r6);
        return;
    L_0x005c:
        monitor-exit(r6);
        return;
    L_0x005e:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzoo.zzly():void");
    }

    private final synchronized Long zza(@NonNull Request request, @NonNull zzop zzop) {
        if (this.zzaqu == null) {
            return null;
        }
        long enqueue = this.zzaqu.enqueue(request);
        StringBuilder stringBuilder = new StringBuilder(53);
        stringBuilder.append("Schedule a new downloading task: ");
        stringBuilder.append(enqueue);
        zzaoz.d("ModelDownloadManager", stringBuilder.toString());
        zzoa.zza(this.zzapo, enqueue, zzop);
        return Long.valueOf(enqueue);
    }

    /* JADX WARNING: Missing block: B:28:0x0068, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:30:0x006a, code:
            return null;
     */
    @androidx.annotation.Nullable
    final synchronized java.lang.Integer zzlz() {
        /*
        r9 = this;
        monitor-enter(r9);
        r0 = r9.zzlw();	 Catch:{ all -> 0x006b }
        r1 = r9.zzaqu;	 Catch:{ all -> 0x006b }
        r2 = 0;
        if (r1 == 0) goto L_0x0069;
    L_0x000a:
        if (r0 != 0) goto L_0x000d;
    L_0x000c:
        goto L_0x0069;
    L_0x000d:
        r1 = r9.zzaqu;	 Catch:{ all -> 0x006b }
        r3 = new android.app.DownloadManager$Query;	 Catch:{ all -> 0x006b }
        r3.<init>();	 Catch:{ all -> 0x006b }
        r4 = 1;
        r5 = new long[r4];	 Catch:{ all -> 0x006b }
        r6 = 0;
        r7 = r0.longValue();	 Catch:{ all -> 0x006b }
        r5[r6] = r7;	 Catch:{ all -> 0x006b }
        r0 = r3.setFilterById(r5);	 Catch:{ all -> 0x006b }
        r0 = r1.query(r0);	 Catch:{ all -> 0x006b }
        if (r0 == 0) goto L_0x003d;
    L_0x0028:
        r1 = r0.moveToFirst();	 Catch:{ all -> 0x006b }
        if (r1 == 0) goto L_0x003d;
    L_0x002e:
        r1 = "status";
        r1 = r0.getColumnIndex(r1);	 Catch:{ all -> 0x006b }
        r0 = r0.getInt(r1);	 Catch:{ all -> 0x006b }
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ all -> 0x006b }
        goto L_0x003e;
    L_0x003d:
        r0 = r2;
    L_0x003e:
        if (r0 != 0) goto L_0x0042;
    L_0x0040:
        monitor-exit(r9);
        return r2;
    L_0x0042:
        r1 = r0.intValue();	 Catch:{ all -> 0x006b }
        r3 = 2;
        if (r1 == r3) goto L_0x0067;
    L_0x0049:
        r1 = r0.intValue();	 Catch:{ all -> 0x006b }
        r3 = 4;
        if (r1 == r3) goto L_0x0067;
    L_0x0050:
        r1 = r0.intValue();	 Catch:{ all -> 0x006b }
        if (r1 == r4) goto L_0x0067;
    L_0x0056:
        r1 = r0.intValue();	 Catch:{ all -> 0x006b }
        r3 = 8;
        if (r1 == r3) goto L_0x0067;
    L_0x005e:
        r1 = r0.intValue();	 Catch:{ all -> 0x006b }
        r3 = 16;
        if (r1 == r3) goto L_0x0067;
    L_0x0066:
        r0 = r2;
    L_0x0067:
        monitor-exit(r9);
        return r0;
    L_0x0069:
        monitor-exit(r9);
        return r2;
    L_0x006b:
        r0 = move-exception;
        monitor-exit(r9);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzoo.zzlz():java.lang.Integer");
    }

    /* JADX WARNING: Missing block: B:13:0x0024, code:
            return null;
     */
    @androidx.annotation.Nullable
    final synchronized android.os.ParcelFileDescriptor zzma() {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = r5.zzlw();	 Catch:{ all -> 0x0025 }
        r1 = r5.zzaqu;	 Catch:{ all -> 0x0025 }
        r2 = 0;
        if (r1 == 0) goto L_0x0023;
    L_0x000a:
        if (r0 != 0) goto L_0x000d;
    L_0x000c:
        goto L_0x0023;
    L_0x000d:
        r1 = r5.zzaqu;	 Catch:{ FileNotFoundException -> 0x0018 }
        r3 = r0.longValue();	 Catch:{ FileNotFoundException -> 0x0018 }
        r2 = r1.openDownloadedFile(r3);	 Catch:{ FileNotFoundException -> 0x0018 }
        goto L_0x0021;
    L_0x0018:
        r0 = zzaoz;	 Catch:{ all -> 0x0025 }
        r1 = "ModelDownloadManager";
        r3 = "Downloaded file is not found";
        r0.e(r1, r3);	 Catch:{ all -> 0x0025 }
    L_0x0021:
        monitor-exit(r5);
        return r2;
    L_0x0023:
        monitor-exit(r5);
        return r2;
    L_0x0025:
        r0 = move-exception;
        monitor-exit(r5);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzoo.zzma():android.os.ParcelFileDescriptor");
    }

    final synchronized void zzby(@NonNull String str) throws FirebaseMLException {
        zzoa.zza(this.zzapo, this.zzaqv.zzmj(), str);
        zzly();
    }

    private final synchronized boolean zzmb() throws FirebaseMLException {
        return this.zzaqx.zzmf() != null;
    }

    /* JADX WARNING: Missing block: B:26:0x006c, code:
            return r1;
     */
    /* JADX WARNING: Missing block: B:28:0x006e, code:
            return null;
     */
    @androidx.annotation.WorkerThread
    @androidx.annotation.Nullable
    private final synchronized com.google.android.gms.internal.firebase_ml.zzop zzmc() throws com.google.firebase.ml.common.FirebaseMLException {
        /*
        r8 = this;
        monitor-enter(r8);
        r0 = r8.zzaqv;	 Catch:{ all -> 0x006f }
        r0 = r0.zzmj();	 Catch:{ all -> 0x006f }
        r1 = r8.zzapo;	 Catch:{ all -> 0x006f }
        r2 = r8.zzaqv;	 Catch:{ all -> 0x006f }
        r3 = r8.zzaqw;	 Catch:{ all -> 0x006f }
        r1 = com.google.android.gms.internal.firebase_ml.zzou.zzb(r1, r2, r3);	 Catch:{ all -> 0x006f }
        r2 = 0;
        if (r1 != 0) goto L_0x0016;
    L_0x0014:
        monitor-exit(r8);
        return r2;
    L_0x0016:
        r3 = r8.zzapo;	 Catch:{ all -> 0x006f }
        r4 = r1.zzaqz;	 Catch:{ all -> 0x006f }
        r5 = com.google.android.gms.internal.firebase_ml.zzoa.zze(r3, r0);	 Catch:{ all -> 0x006f }
        r4 = r4.equals(r5);	 Catch:{ all -> 0x006f }
        r5 = 0;
        r6 = 1;
        if (r4 == 0) goto L_0x003c;
    L_0x0028:
        r4 = r3.getApplicationContext();	 Catch:{ all -> 0x006f }
        r4 = com.google.android.gms.internal.firebase_ml.zznk.zza(r4);	 Catch:{ all -> 0x006f }
        r3 = com.google.android.gms.internal.firebase_ml.zzoa.zze(r3);	 Catch:{ all -> 0x006f }
        r3 = r4.equals(r3);	 Catch:{ all -> 0x006f }
        if (r3 == 0) goto L_0x003c;
    L_0x003a:
        r3 = 1;
        goto L_0x003d;
    L_0x003c:
        r3 = 0;
    L_0x003d:
        if (r3 == 0) goto L_0x0049;
    L_0x003f:
        r3 = zzaoz;	 Catch:{ all -> 0x006f }
        r4 = "ModelDownloadManager";
        r7 = "The new model is incompatible and the app is not upgraded, do not download";
        r3.d(r4, r7);	 Catch:{ all -> 0x006f }
        goto L_0x004a;
    L_0x0049:
        r5 = 1;
    L_0x004a:
        r3 = r8.zzmb();	 Catch:{ all -> 0x006f }
        r3 = r3 ^ r6;
        if (r3 == 0) goto L_0x0056;
    L_0x0051:
        r4 = r8.zzapo;	 Catch:{ all -> 0x006f }
        com.google.android.gms.internal.firebase_ml.zzoa.zzi(r4, r0);	 Catch:{ all -> 0x006f }
    L_0x0056:
        r4 = r8.zzapo;	 Catch:{ all -> 0x006f }
        r7 = r1.zzaqz;	 Catch:{ all -> 0x006f }
        r0 = com.google.android.gms.internal.firebase_ml.zzoa.zzd(r4, r0);	 Catch:{ all -> 0x006f }
        r0 = r7.equals(r0);	 Catch:{ all -> 0x006f }
        r0 = r0 ^ r6;
        if (r5 == 0) goto L_0x006d;
    L_0x0067:
        if (r3 != 0) goto L_0x006b;
    L_0x0069:
        if (r0 == 0) goto L_0x006d;
    L_0x006b:
        monitor-exit(r8);
        return r1;
    L_0x006d:
        monitor-exit(r8);
        return r2;
    L_0x006f:
        r0 = move-exception;
        monitor-exit(r8);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzoo.zzmc():com.google.android.gms.internal.firebase_ml.zzop");
    }

    @WorkerThread
    @Nullable
    private final synchronized Long zza(@NonNull zzop zzop) throws FirebaseMLException {
        String zzc = zzoa.zzc(this.zzapo, zzop.zzaqg);
        if (zzc == null || !zzc.equals(zzop.zzaqz)) {
            zzaoz.d("ModelDownloadManager", "Need to download a new model.");
            zzly();
            Request request = new Request(zzop.zzaqy);
            request.setDestinationUri(null);
            FirebaseModelDownloadConditions initialDownloadConditions = this.zzaqv.getInitialDownloadConditions();
            if (this.zzaqv.isModelUpdatesEnabled() || this.zzaqx.zzmf() == null) {
                if (this.zzaqv.isModelUpdatesEnabled() && this.zzaqx.zzmf() != null) {
                    zzaoz.d("ModelDownloadManager", "Model update is enabled and have a previous downloaded model, use download condition");
                    initialDownloadConditions = this.zzaqv.getUpdatesDownloadConditions();
                }
                zzaoz.d("ModelDownloadManager", "Use initial download conditions.");
                if (VERSION.SDK_INT >= 24) {
                    request.setRequiresCharging(initialDownloadConditions.isChargingRequired());
                    request.setRequiresDeviceIdle(initialDownloadConditions.isDeviceIdleRequired());
                }
                if (initialDownloadConditions.isWifiRequired()) {
                    request.setAllowedNetworkTypes(2);
                }
                return zza(request, zzop);
            }
            zzaoz.d("ModelDownloadManager", "Model update is disabled and have a previous downloaded model, skip downloading");
            return null;
        }
        zzaoz.d("ModelDownloadManager", "New model is already in downloading, do nothing.");
        return null;
    }
}
