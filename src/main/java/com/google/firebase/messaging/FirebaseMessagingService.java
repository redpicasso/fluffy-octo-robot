package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import com.google.firebase.iid.zzaq;
import java.util.ArrayDeque;
import java.util.Queue;

/* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
public class FirebaseMessagingService extends zzc {
    private static final Queue<String> zza = new ArrayDeque(10);

    @WorkerThread
    public void onDeletedMessages() {
    }

    @WorkerThread
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
    }

    @WorkerThread
    public void onMessageSent(@NonNull String str) {
    }

    @WorkerThread
    public void onNewToken(@NonNull String str) {
    }

    @WorkerThread
    public void onSendError(@NonNull String str, @NonNull Exception exception) {
    }

    protected final Intent zza(Intent intent) {
        return zzaq.zza().zzb();
    }

    public final boolean zzb(Intent intent) {
        if (!"com.google.firebase.messaging.NOTIFICATION_OPEN".equals(intent.getAction())) {
            return false;
        }
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("pending_intent");
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (CanceledException unused) {
                Log.e("FirebaseMessaging", "Notification pending intent canceled");
            }
        }
        if (MessagingAnalytics.shouldUploadMetrics(intent)) {
            MessagingAnalytics.logNotificationOpen(intent);
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c7  */
    /* JADX WARNING: Missing block: B:54:0x00f3, code:
            if (r1.equals(r5) != false) goto L_0x0101;
     */
    public final void zzc(android.content.Intent r12) {
        /*
        r11 = this;
        r0 = r12.getAction();
        r1 = "com.google.android.c2dm.intent.RECEIVE";
        r1 = r1.equals(r0);
        r2 = "FirebaseMessaging";
        if (r1 != 0) goto L_0x0059;
    L_0x000e:
        r1 = "com.google.firebase.messaging.RECEIVE_DIRECT_BOOT";
        r1 = r1.equals(r0);
        if (r1 == 0) goto L_0x0017;
    L_0x0016:
        goto L_0x0059;
    L_0x0017:
        r1 = "com.google.firebase.messaging.NOTIFICATION_DISMISS";
        r1 = r1.equals(r0);
        if (r1 == 0) goto L_0x0029;
    L_0x001f:
        r0 = com.google.firebase.messaging.MessagingAnalytics.shouldUploadMetrics(r12);
        if (r0 == 0) goto L_0x0058;
    L_0x0025:
        com.google.firebase.messaging.MessagingAnalytics.logNotificationDismiss(r12);
        return;
    L_0x0029:
        r1 = "com.google.firebase.messaging.NEW_TOKEN";
        r0 = r1.equals(r0);
        if (r0 == 0) goto L_0x003b;
    L_0x0031:
        r0 = "token";
        r12 = r12.getStringExtra(r0);
        r11.onNewToken(r12);
        return;
    L_0x003b:
        r0 = "Unknown intent action: ";
        r12 = r12.getAction();
        r12 = java.lang.String.valueOf(r12);
        r1 = r12.length();
        if (r1 == 0) goto L_0x0050;
    L_0x004b:
        r12 = r0.concat(r12);
        goto L_0x0055;
    L_0x0050:
        r12 = new java.lang.String;
        r12.<init>(r0);
    L_0x0055:
        android.util.Log.d(r2, r12);
    L_0x0058:
        return;
    L_0x0059:
        r0 = "google.message_id";
        r1 = r12.getStringExtra(r0);
        r3 = android.text.TextUtils.isEmpty(r1);
        r4 = 2;
        if (r3 == 0) goto L_0x006c;
    L_0x0066:
        r3 = 0;
        r3 = com.google.android.gms.tasks.Tasks.forResult(r3);
        goto L_0x007c;
    L_0x006c:
        r3 = new android.os.Bundle;
        r3.<init>();
        r3.putString(r0, r1);
        r5 = com.google.firebase.iid.zzv.zza(r11);
        r3 = r5.zza(r4, r3);
    L_0x007c:
        r5 = android.text.TextUtils.isEmpty(r1);
        r6 = 1;
        r7 = 3;
        r8 = 0;
        if (r5 == 0) goto L_0x0087;
    L_0x0085:
        r1 = 0;
        goto L_0x00c5;
    L_0x0087:
        r5 = zza;
        r5 = r5.contains(r1);
        if (r5 == 0) goto L_0x00b0;
    L_0x008f:
        r5 = android.util.Log.isLoggable(r2, r7);
        if (r5 == 0) goto L_0x00ae;
    L_0x0095:
        r5 = "Received duplicate message: ";
        r1 = java.lang.String.valueOf(r1);
        r9 = r1.length();
        if (r9 == 0) goto L_0x00a6;
    L_0x00a1:
        r1 = r5.concat(r1);
        goto L_0x00ab;
    L_0x00a6:
        r1 = new java.lang.String;
        r1.<init>(r5);
    L_0x00ab:
        android.util.Log.d(r2, r1);
    L_0x00ae:
        r1 = 1;
        goto L_0x00c5;
    L_0x00b0:
        r5 = zza;
        r5 = r5.size();
        r9 = 10;
        if (r5 < r9) goto L_0x00bf;
    L_0x00ba:
        r5 = zza;
        r5.remove();
    L_0x00bf:
        r5 = zza;
        r5.add(r1);
        goto L_0x0085;
    L_0x00c5:
        if (r1 != 0) goto L_0x019d;
    L_0x00c7:
        r1 = "message_type";
        r1 = r12.getStringExtra(r1);
        r5 = "gcm";
        if (r1 != 0) goto L_0x00d2;
    L_0x00d1:
        r1 = r5;
    L_0x00d2:
        r9 = -1;
        r10 = r1.hashCode();
        switch(r10) {
            case -2062414158: goto L_0x00f6;
            case 102161: goto L_0x00ef;
            case 814694033: goto L_0x00e5;
            case 814800675: goto L_0x00db;
            default: goto L_0x00da;
        };
    L_0x00da:
        goto L_0x0100;
    L_0x00db:
        r5 = "send_event";
        r5 = r1.equals(r5);
        if (r5 == 0) goto L_0x0100;
    L_0x00e3:
        r8 = 2;
        goto L_0x0101;
    L_0x00e5:
        r5 = "send_error";
        r5 = r1.equals(r5);
        if (r5 == 0) goto L_0x0100;
    L_0x00ed:
        r8 = 3;
        goto L_0x0101;
    L_0x00ef:
        r5 = r1.equals(r5);
        if (r5 == 0) goto L_0x0100;
    L_0x00f5:
        goto L_0x0101;
    L_0x00f6:
        r5 = "deleted_messages";
        r5 = r1.equals(r5);
        if (r5 == 0) goto L_0x0100;
    L_0x00fe:
        r8 = 1;
        goto L_0x0101;
    L_0x0100:
        r8 = -1;
    L_0x0101:
        if (r8 == 0) goto L_0x014c;
    L_0x0103:
        if (r8 == r6) goto L_0x0148;
    L_0x0105:
        if (r8 == r4) goto L_0x0140;
    L_0x0107:
        if (r8 == r7) goto L_0x0125;
    L_0x0109:
        r12 = "Received message with unknown type: ";
        r0 = java.lang.String.valueOf(r1);
        r1 = r0.length();
        if (r1 == 0) goto L_0x011a;
    L_0x0115:
        r12 = r12.concat(r0);
        goto L_0x0120;
    L_0x011a:
        r0 = new java.lang.String;
        r0.<init>(r12);
        r12 = r0;
    L_0x0120:
        android.util.Log.w(r2, r12);
        goto L_0x019d;
    L_0x0125:
        r0 = r12.getStringExtra(r0);
        if (r0 != 0) goto L_0x0131;
    L_0x012b:
        r0 = "message_id";
        r0 = r12.getStringExtra(r0);
    L_0x0131:
        r1 = new com.google.firebase.messaging.SendException;
        r4 = "error";
        r12 = r12.getStringExtra(r4);
        r1.<init>(r12);
        r11.onSendError(r0, r1);
        goto L_0x019d;
    L_0x0140:
        r12 = r12.getStringExtra(r0);
        r11.onMessageSent(r12);
        goto L_0x019d;
    L_0x0148:
        r11.onDeletedMessages();
        goto L_0x019d;
    L_0x014c:
        r0 = com.google.firebase.messaging.MessagingAnalytics.shouldUploadMetrics(r12);
        if (r0 == 0) goto L_0x0155;
    L_0x0152:
        com.google.firebase.messaging.MessagingAnalytics.logNotificationReceived(r12);
    L_0x0155:
        r0 = r12.getExtras();
        if (r0 != 0) goto L_0x0160;
    L_0x015b:
        r0 = new android.os.Bundle;
        r0.<init>();
    L_0x0160:
        r1 = "androidx.contentpager.content.wakelockid";
        r0.remove(r1);
        r1 = com.google.firebase.messaging.zzk.zza(r0);
        if (r1 == 0) goto L_0x0195;
    L_0x016b:
        r1 = new com.google.firebase.messaging.zzk;
        r1.<init>(r0);
        r4 = java.util.concurrent.Executors.newSingleThreadExecutor();
        r5 = new com.google.firebase.messaging.zzd;
        r5.<init>(r11, r1, r4);
        r1 = r5.zza();	 Catch:{ all -> 0x0190 }
        if (r1 == 0) goto L_0x0183;
    L_0x017f:
        r4.shutdown();
        goto L_0x019d;
    L_0x0183:
        r4.shutdown();
        r1 = com.google.firebase.messaging.MessagingAnalytics.shouldUploadMetrics(r12);
        if (r1 == 0) goto L_0x0195;
    L_0x018c:
        com.google.firebase.messaging.MessagingAnalytics.logNotificationForeground(r12);
        goto L_0x0195;
    L_0x0190:
        r12 = move-exception;
        r4.shutdown();
        throw r12;
    L_0x0195:
        r12 = new com.google.firebase.messaging.RemoteMessage;
        r12.<init>(r0);
        r11.onMessageReceived(r12);
    L_0x019d:
        r0 = 1;
        r12 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ ExecutionException -> 0x01a9, InterruptedException -> 0x01a7, TimeoutException -> 0x01a5 }
        com.google.android.gms.tasks.Tasks.await(r3, r0, r12);	 Catch:{ ExecutionException -> 0x01a9, InterruptedException -> 0x01a7, TimeoutException -> 0x01a5 }
        return;
    L_0x01a5:
        r12 = move-exception;
        goto L_0x01aa;
    L_0x01a7:
        r12 = move-exception;
        goto L_0x01aa;
    L_0x01a9:
        r12 = move-exception;
    L_0x01aa:
        r12 = java.lang.String.valueOf(r12);
        r0 = java.lang.String.valueOf(r12);
        r0 = r0.length();
        r0 = r0 + 20;
        r1 = new java.lang.StringBuilder;
        r1.<init>(r0);
        r0 = "Message ack failed: ";
        r1.append(r0);
        r1.append(r12);
        r12 = r1.toString();
        android.util.Log.w(r2, r12);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.FirebaseMessagingService.zzc(android.content.Intent):void");
    }
}
