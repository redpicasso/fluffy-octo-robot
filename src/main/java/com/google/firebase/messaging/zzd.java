package com.google.firebase.messaging;

import android.content.Context;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
final class zzd {
    private final Executor zza;
    private final Context zzb;
    private final zzk zzc;

    public zzd(Context context, zzk zzk, Executor executor) {
        this.zza = executor;
        this.zzb = context;
        this.zzc = zzk;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x005b A:{RETURN} */
    final boolean zza() {
        /*
        r10 = this;
        r0 = r10.zzc;
        r1 = "gcm.n.noui";
        r0 = r0.zzb(r1);
        r1 = 1;
        if (r0 == 0) goto L_0x000c;
    L_0x000b:
        return r1;
    L_0x000c:
        r0 = r10.zzb;
        r2 = "keyguard";
        r0 = r0.getSystemService(r2);
        r0 = (android.app.KeyguardManager) r0;
        r0 = r0.inKeyguardRestrictedInputMode();
        r2 = 0;
        if (r0 != 0) goto L_0x0058;
    L_0x001d:
        r0 = com.google.android.gms.common.util.PlatformVersion.isAtLeastLollipop();
        if (r0 != 0) goto L_0x0028;
    L_0x0023:
        r3 = 10;
        android.os.SystemClock.sleep(r3);
    L_0x0028:
        r0 = android.os.Process.myPid();
        r3 = r10.zzb;
        r4 = "activity";
        r3 = r3.getSystemService(r4);
        r3 = (android.app.ActivityManager) r3;
        r3 = r3.getRunningAppProcesses();
        if (r3 == 0) goto L_0x0058;
    L_0x003c:
        r3 = r3.iterator();
    L_0x0040:
        r4 = r3.hasNext();
        if (r4 == 0) goto L_0x0058;
    L_0x0046:
        r4 = r3.next();
        r4 = (android.app.ActivityManager.RunningAppProcessInfo) r4;
        r5 = r4.pid;
        if (r5 != r0) goto L_0x0040;
    L_0x0050:
        r0 = r4.importance;
        r3 = 100;
        if (r0 != r3) goto L_0x0058;
    L_0x0056:
        r0 = 1;
        goto L_0x0059;
    L_0x0058:
        r0 = 0;
    L_0x0059:
        if (r0 == 0) goto L_0x005c;
    L_0x005b:
        return r2;
    L_0x005c:
        r0 = r10.zzc;
        r3 = "gcm.n.image";
        r0 = r0.zza(r3);
        r0 = com.google.firebase.messaging.zzj.zza(r0);
        if (r0 == 0) goto L_0x006f;
    L_0x006a:
        r3 = r10.zza;
        r0.zza(r3);
    L_0x006f:
        r3 = r10.zzb;
        r4 = r10.zzc;
        r3 = com.google.firebase.messaging.zzb.zza(r3, r4);
        r4 = r3.zza;
        r5 = "FirebaseMessaging";
        if (r0 == 0) goto L_0x00e0;
    L_0x007d:
        r6 = r0.zza();	 Catch:{ ExecutionException -> 0x00b9, InterruptedException -> 0x00a9, TimeoutException -> 0x00a0 }
        r7 = 5;
        r9 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ ExecutionException -> 0x00b9, InterruptedException -> 0x00a9, TimeoutException -> 0x00a0 }
        r6 = com.google.android.gms.tasks.Tasks.await(r6, r7, r9);	 Catch:{ ExecutionException -> 0x00b9, InterruptedException -> 0x00a9, TimeoutException -> 0x00a0 }
        r6 = (android.graphics.Bitmap) r6;	 Catch:{ ExecutionException -> 0x00b9, InterruptedException -> 0x00a9, TimeoutException -> 0x00a0 }
        r4.setLargeIcon(r6);	 Catch:{ ExecutionException -> 0x00b9, InterruptedException -> 0x00a9, TimeoutException -> 0x00a0 }
        r7 = new androidx.core.app.NotificationCompat$BigPictureStyle;	 Catch:{ ExecutionException -> 0x00b9, InterruptedException -> 0x00a9, TimeoutException -> 0x00a0 }
        r7.<init>();	 Catch:{ ExecutionException -> 0x00b9, InterruptedException -> 0x00a9, TimeoutException -> 0x00a0 }
        r6 = r7.bigPicture(r6);	 Catch:{ ExecutionException -> 0x00b9, InterruptedException -> 0x00a9, TimeoutException -> 0x00a0 }
        r7 = 0;
        r6 = r6.bigLargeIcon(r7);	 Catch:{ ExecutionException -> 0x00b9, InterruptedException -> 0x00a9, TimeoutException -> 0x00a0 }
        r4.setStyle(r6);	 Catch:{ ExecutionException -> 0x00b9, InterruptedException -> 0x00a9, TimeoutException -> 0x00a0 }
        goto L_0x00e0;
    L_0x00a0:
        r4 = "Failed to download image in time, showing notification without it";
        android.util.Log.w(r5, r4);
        r0.close();
        goto L_0x00e0;
    L_0x00a9:
        r4 = "Interrupted while downloading image, showing notification without it";
        android.util.Log.w(r5, r4);
        r0.close();
        r0 = java.lang.Thread.currentThread();
        r0.interrupt();
        goto L_0x00e0;
    L_0x00b9:
        r0 = move-exception;
        r0 = r0.getCause();
        r0 = java.lang.String.valueOf(r0);
        r4 = java.lang.String.valueOf(r0);
        r4 = r4.length();
        r4 = r4 + 26;
        r6 = new java.lang.StringBuilder;
        r6.<init>(r4);
        r4 = "Failed to download image: ";
        r6.append(r4);
        r6.append(r0);
        r0 = r6.toString();
        android.util.Log.w(r5, r0);
    L_0x00e0:
        r0 = 3;
        r0 = android.util.Log.isLoggable(r5, r0);
        if (r0 == 0) goto L_0x00ec;
    L_0x00e7:
        r0 = "Showing notification";
        android.util.Log.d(r5, r0);
    L_0x00ec:
        r0 = r10.zzb;
        r4 = "notification";
        r0 = r0.getSystemService(r4);
        r0 = (android.app.NotificationManager) r0;
        r4 = r3.zzb;
        r3 = r3.zza;
        r3 = r3.build();
        r0.notify(r4, r2, r3);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zzd.zza():boolean");
    }
}
