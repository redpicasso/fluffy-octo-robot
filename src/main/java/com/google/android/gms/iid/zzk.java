package com.google.android.gms.iid;

import android.content.BroadcastReceiver.PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public final class zzk implements ServiceConnection {
    private final Intent zzbp;
    private final ScheduledExecutorService zzbq;
    private final Queue<zzg> zzbr;
    private zzi zzbs;
    @GuardedBy("this")
    private boolean zzbt;
    private final Context zzl;

    public zzk(Context context, String str) {
        this(context, str, new ScheduledThreadPoolExecutor(0, new NamedThreadFactory("EnhancedIntentService")));
    }

    @VisibleForTesting
    private zzk(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
        this.zzbr = new ArrayDeque();
        this.zzbt = false;
        this.zzl = context.getApplicationContext();
        this.zzbp = new Intent(str).setPackage(this.zzl.getPackageName());
        this.zzbq = scheduledExecutorService;
    }

    public final synchronized void zzd(Intent intent, PendingResult pendingResult) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "new intent queued in the bind-strategy delivery");
        }
        this.zzbr.add(new zzg(intent, pendingResult, this.zzbq));
        zzm();
    }

    /* JADX WARNING: Missing block: B:40:0x00a8, code:
            return;
     */
    private final synchronized void zzm() {
        /*
        r6 = this;
        monitor-enter(r6);
        r0 = "EnhancedIntentService";
        r1 = 3;
        r0 = android.util.Log.isLoggable(r0, r1);	 Catch:{ all -> 0x00ab }
        if (r0 == 0) goto L_0x0011;
    L_0x000a:
        r0 = "EnhancedIntentService";
        r2 = "flush queue called";
        android.util.Log.d(r0, r2);	 Catch:{ all -> 0x00ab }
    L_0x0011:
        r0 = r6.zzbr;	 Catch:{ all -> 0x00ab }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x00ab }
        if (r0 != 0) goto L_0x00a9;
    L_0x0019:
        r0 = "EnhancedIntentService";
        r0 = android.util.Log.isLoggable(r0, r1);	 Catch:{ all -> 0x00ab }
        if (r0 == 0) goto L_0x0028;
    L_0x0021:
        r0 = "EnhancedIntentService";
        r2 = "found intent to be delivered";
        android.util.Log.d(r0, r2);	 Catch:{ all -> 0x00ab }
    L_0x0028:
        r0 = r6.zzbs;	 Catch:{ all -> 0x00ab }
        if (r0 == 0) goto L_0x0051;
    L_0x002c:
        r0 = r6.zzbs;	 Catch:{ all -> 0x00ab }
        r0 = r0.isBinderAlive();	 Catch:{ all -> 0x00ab }
        if (r0 == 0) goto L_0x0051;
    L_0x0034:
        r0 = "EnhancedIntentService";
        r0 = android.util.Log.isLoggable(r0, r1);	 Catch:{ all -> 0x00ab }
        if (r0 == 0) goto L_0x0043;
    L_0x003c:
        r0 = "EnhancedIntentService";
        r2 = "binder is alive, sending the intent.";
        android.util.Log.d(r0, r2);	 Catch:{ all -> 0x00ab }
    L_0x0043:
        r0 = r6.zzbr;	 Catch:{ all -> 0x00ab }
        r0 = r0.poll();	 Catch:{ all -> 0x00ab }
        r0 = (com.google.android.gms.iid.zzg) r0;	 Catch:{ all -> 0x00ab }
        r2 = r6.zzbs;	 Catch:{ all -> 0x00ab }
        r2.zzd(r0);	 Catch:{ all -> 0x00ab }
        goto L_0x0011;
    L_0x0051:
        r0 = "EnhancedIntentService";
        r0 = android.util.Log.isLoggable(r0, r1);	 Catch:{ all -> 0x00ab }
        r1 = 0;
        r2 = 1;
        if (r0 == 0) goto L_0x007a;
    L_0x005b:
        r0 = "EnhancedIntentService";
        r3 = r6.zzbt;	 Catch:{ all -> 0x00ab }
        if (r3 != 0) goto L_0x0063;
    L_0x0061:
        r3 = 1;
        goto L_0x0064;
    L_0x0063:
        r3 = 0;
    L_0x0064:
        r4 = 39;
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ab }
        r5.<init>(r4);	 Catch:{ all -> 0x00ab }
        r4 = "binder is dead. start connection? ";
        r5.append(r4);	 Catch:{ all -> 0x00ab }
        r5.append(r3);	 Catch:{ all -> 0x00ab }
        r3 = r5.toString();	 Catch:{ all -> 0x00ab }
        android.util.Log.d(r0, r3);	 Catch:{ all -> 0x00ab }
    L_0x007a:
        r0 = r6.zzbt;	 Catch:{ all -> 0x00ab }
        if (r0 != 0) goto L_0x00a7;
    L_0x007e:
        r6.zzbt = r2;	 Catch:{ all -> 0x00ab }
        r0 = com.google.android.gms.common.stats.ConnectionTracker.getInstance();	 Catch:{ SecurityException -> 0x009a }
        r2 = r6.zzl;	 Catch:{ SecurityException -> 0x009a }
        r3 = r6.zzbp;	 Catch:{ SecurityException -> 0x009a }
        r4 = 65;
        r0 = r0.bindService(r2, r3, r6, r4);	 Catch:{ SecurityException -> 0x009a }
        if (r0 == 0) goto L_0x0092;
    L_0x0090:
        monitor-exit(r6);
        return;
    L_0x0092:
        r0 = "EnhancedIntentService";
        r2 = "binding to the service failed";
        android.util.Log.e(r0, r2);	 Catch:{ SecurityException -> 0x009a }
        goto L_0x00a2;
    L_0x009a:
        r0 = move-exception;
        r2 = "EnhancedIntentService";
        r3 = "Exception while binding the service";
        android.util.Log.e(r2, r3, r0);	 Catch:{ all -> 0x00ab }
    L_0x00a2:
        r6.zzbt = r1;	 Catch:{ all -> 0x00ab }
        r6.zzn();	 Catch:{ all -> 0x00ab }
    L_0x00a7:
        monitor-exit(r6);
        return;
    L_0x00a9:
        monitor-exit(r6);
        return;
    L_0x00ab:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzk.zzm():void");
    }

    @GuardedBy("this")
    private final void zzn() {
        while (!this.zzbr.isEmpty()) {
            ((zzg) this.zzbr.poll()).finish();
        }
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this) {
            this.zzbt = false;
            this.zzbs = (zzi) iBinder;
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                String valueOf = String.valueOf(componentName);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 20);
                stringBuilder.append("onServiceConnected: ");
                stringBuilder.append(valueOf);
                Log.d("EnhancedIntentService", stringBuilder.toString());
            }
            if (iBinder == null) {
                Log.e("EnhancedIntentService", "Null service connection");
                zzn();
            } else {
                zzm();
            }
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        String str = "EnhancedIntentService";
        if (Log.isLoggable(str, 3)) {
            String valueOf = String.valueOf(componentName);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 23);
            stringBuilder.append("onServiceDisconnected: ");
            stringBuilder.append(valueOf);
            Log.d(str, stringBuilder.toString());
        }
        zzm();
    }
}
