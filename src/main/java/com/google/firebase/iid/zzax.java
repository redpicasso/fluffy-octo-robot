package com.google.firebase.iid;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.io.IOException;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zzax {
    @GuardedBy("this")
    private int zza = 0;
    @GuardedBy("this")
    private final Map<Integer, TaskCompletionSource<Void>> zzb = new ArrayMap();
    @GuardedBy("itself")
    private final zzat zzc;

    zzax(zzat zzat) {
        this.zzc = zzat;
    }

    final synchronized Task<Void> zza(String str) {
        TaskCompletionSource taskCompletionSource;
        Object zza;
        int i;
        synchronized (this.zzc) {
            zza = this.zzc.zza();
            zzat zzat = this.zzc;
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zza).length() + 1) + String.valueOf(str).length());
            stringBuilder.append(zza);
            stringBuilder.append(",");
            stringBuilder.append(str);
            zzat.zza(stringBuilder.toString());
        }
        taskCompletionSource = new TaskCompletionSource();
        Map map = this.zzb;
        if (TextUtils.isEmpty(zza)) {
            i = 0;
        } else {
            i = zza.split(",").length - 1;
        }
        map.put(Integer.valueOf(this.zza + i), taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    final synchronized boolean zza() {
        return zzb() != null;
    }

    /* JADX WARNING: Missing block: B:8:0x0016, code:
            return true;
     */
    /* JADX WARNING: Missing block: B:11:0x001c, code:
            if (zza(r5, r0) != false) goto L_0x0020;
     */
    /* JADX WARNING: Missing block: B:13:0x001f, code:
            return false;
     */
    /* JADX WARNING: Missing block: B:14:0x0020, code:
            monitor-enter(r4);
     */
    /* JADX WARNING: Missing block: B:16:?, code:
            r2 = (com.google.android.gms.tasks.TaskCompletionSource) r4.zzb.remove(java.lang.Integer.valueOf(r4.zza));
            zzb(r0);
            r4.zza++;
     */
    /* JADX WARNING: Missing block: B:17:0x0037, code:
            monitor-exit(r4);
     */
    /* JADX WARNING: Missing block: B:18:0x0038, code:
            if (r2 == null) goto L_0x0000;
     */
    /* JADX WARNING: Missing block: B:19:0x003a, code:
            r2.setResult(null);
     */
    @androidx.annotation.WorkerThread
    final boolean zza(com.google.firebase.iid.FirebaseInstanceId r5) throws java.io.IOException {
        /*
        r4 = this;
    L_0x0000:
        monitor-enter(r4);
        r0 = r4.zzb();	 Catch:{ all -> 0x0042 }
        r1 = 1;
        if (r0 != 0) goto L_0x0017;
    L_0x0008:
        r5 = com.google.firebase.iid.FirebaseInstanceId.zzd();	 Catch:{ all -> 0x0042 }
        if (r5 == 0) goto L_0x0015;
    L_0x000e:
        r5 = "FirebaseInstanceId";
        r0 = "topic sync succeeded";
        android.util.Log.d(r5, r0);	 Catch:{ all -> 0x0042 }
    L_0x0015:
        monitor-exit(r4);	 Catch:{ all -> 0x0042 }
        return r1;
    L_0x0017:
        monitor-exit(r4);	 Catch:{ all -> 0x0042 }
        r2 = zza(r5, r0);
        if (r2 != 0) goto L_0x0020;
    L_0x001e:
        r5 = 0;
        return r5;
    L_0x0020:
        monitor-enter(r4);
        r2 = r4.zzb;	 Catch:{ all -> 0x003f }
        r3 = r4.zza;	 Catch:{ all -> 0x003f }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x003f }
        r2 = r2.remove(r3);	 Catch:{ all -> 0x003f }
        r2 = (com.google.android.gms.tasks.TaskCompletionSource) r2;	 Catch:{ all -> 0x003f }
        r4.zzb(r0);	 Catch:{ all -> 0x003f }
        r0 = r4.zza;	 Catch:{ all -> 0x003f }
        r0 = r0 + r1;
        r4.zza = r0;	 Catch:{ all -> 0x003f }
        monitor-exit(r4);	 Catch:{ all -> 0x003f }
        if (r2 == 0) goto L_0x0000;
    L_0x003a:
        r0 = 0;
        r2.setResult(r0);
        goto L_0x0000;
    L_0x003f:
        r5 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x003f }
        throw r5;
    L_0x0042:
        r5 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0042 }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzax.zza(com.google.firebase.iid.FirebaseInstanceId):boolean");
    }

    @GuardedBy("this")
    @Nullable
    private final String zzb() {
        Object zza;
        synchronized (this.zzc) {
            zza = this.zzc.zza();
        }
        if (!TextUtils.isEmpty(zza)) {
            String[] split = zza.split(",");
            if (split.length > 1 && !TextUtils.isEmpty(split[1])) {
                return split[1];
            }
        }
        return null;
    }

    private final synchronized boolean zzb(String str) {
        synchronized (this.zzc) {
            String zza = this.zzc.zza();
            String str2 = ",";
            String valueOf = String.valueOf(str);
            if (zza.startsWith(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2))) {
                str2 = ",";
                str = String.valueOf(str);
                this.zzc.zza(zza.substring((str.length() != 0 ? str2.concat(str) : new String(str2)).length()));
                return true;
            }
            return false;
        }
    }

    @WorkerThread
    private static boolean zza(FirebaseInstanceId firebaseInstanceId, String str) throws IOException {
        String str2 = "FirebaseInstanceId";
        String[] split = str.split("!");
        if (split.length == 2) {
            String str3 = split[0];
            str = split[1];
            Object obj = -1;
            try {
                int hashCode = str3.hashCode();
                if (hashCode != 83) {
                    if (hashCode == 85 && str3.equals("U")) {
                        obj = 1;
                    }
                } else if (str3.equals(ExifInterface.LATITUDE_SOUTH)) {
                    obj = null;
                }
                if (obj == null) {
                    firebaseInstanceId.zzb(str);
                    if (FirebaseInstanceId.zzd()) {
                        Log.d(str2, "subscribe operation succeeded");
                    }
                } else if (obj == 1) {
                    firebaseInstanceId.zzc(str);
                    if (FirebaseInstanceId.zzd()) {
                        Log.d(str2, "unsubscribe operation succeeded");
                    }
                }
            } catch (IOException e) {
                if (!InstanceID.ERROR_SERVICE_NOT_AVAILABLE.equals(e.getMessage())) {
                    if (!"INTERNAL_SERVER_ERROR".equals(e.getMessage())) {
                        if (e.getMessage() == null) {
                            Log.e(str2, "Topic operation failed without exception message. Will retry Topic operation.");
                            return false;
                        }
                        throw e;
                    }
                }
                String message = e.getMessage();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(message).length() + 53);
                stringBuilder.append("Topic operation failed: ");
                stringBuilder.append(message);
                stringBuilder.append(". Will retry Topic operation.");
                Log.e(str2, stringBuilder.toString());
                return false;
            }
        }
        return true;
    }
}
