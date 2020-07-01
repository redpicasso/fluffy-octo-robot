package com.google.android.gms.iid;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.internal.gcm.zzj;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;

final class zzt implements ServiceConnection {
    @GuardedBy("this")
    int state;
    final Messenger zzch;
    zzy zzci;
    @GuardedBy("this")
    final Queue<zzz<?>> zzcj;
    @GuardedBy("this")
    final SparseArray<zzz<?>> zzck;
    final /* synthetic */ zzr zzcl;

    private zzt(zzr zzr) {
        this.zzcl = zzr;
        this.state = 0;
        this.zzch = new Messenger(new zzj(Looper.getMainLooper(), new zzu(this)));
        this.zzcj = new ArrayDeque();
        this.zzck = new SparseArray();
    }

    /* JADX WARNING: Missing block: B:13:0x002f, code:
            return false;
     */
    /* JADX WARNING: Missing block: B:35:0x0096, code:
            return true;
     */
    final synchronized boolean zze(com.google.android.gms.iid.zzz r6) {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = r5.state;	 Catch:{ all -> 0x0097 }
        r1 = 2;
        r2 = 0;
        r3 = 1;
        if (r0 == 0) goto L_0x0041;
    L_0x0008:
        if (r0 == r3) goto L_0x003a;
    L_0x000a:
        if (r0 == r1) goto L_0x0030;
    L_0x000c:
        r6 = 3;
        if (r0 == r6) goto L_0x002e;
    L_0x000f:
        r6 = 4;
        if (r0 != r6) goto L_0x0013;
    L_0x0012:
        goto L_0x002e;
    L_0x0013:
        r6 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0097 }
        r0 = r5.state;	 Catch:{ all -> 0x0097 }
        r1 = 26;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0097 }
        r2.<init>(r1);	 Catch:{ all -> 0x0097 }
        r1 = "Unknown state: ";
        r2.append(r1);	 Catch:{ all -> 0x0097 }
        r2.append(r0);	 Catch:{ all -> 0x0097 }
        r0 = r2.toString();	 Catch:{ all -> 0x0097 }
        r6.<init>(r0);	 Catch:{ all -> 0x0097 }
        throw r6;	 Catch:{ all -> 0x0097 }
    L_0x002e:
        monitor-exit(r5);
        return r2;
    L_0x0030:
        r0 = r5.zzcj;	 Catch:{ all -> 0x0097 }
        r0.add(r6);	 Catch:{ all -> 0x0097 }
        r5.zzt();	 Catch:{ all -> 0x0097 }
        monitor-exit(r5);
        return r3;
    L_0x003a:
        r0 = r5.zzcj;	 Catch:{ all -> 0x0097 }
        r0.add(r6);	 Catch:{ all -> 0x0097 }
        monitor-exit(r5);
        return r3;
    L_0x0041:
        r0 = r5.zzcj;	 Catch:{ all -> 0x0097 }
        r0.add(r6);	 Catch:{ all -> 0x0097 }
        r6 = r5.state;	 Catch:{ all -> 0x0097 }
        if (r6 != 0) goto L_0x004c;
    L_0x004a:
        r6 = 1;
        goto L_0x004d;
    L_0x004c:
        r6 = 0;
    L_0x004d:
        com.google.android.gms.common.internal.Preconditions.checkState(r6);	 Catch:{ all -> 0x0097 }
        r6 = "MessengerIpcClient";
        r6 = android.util.Log.isLoggable(r6, r1);	 Catch:{ all -> 0x0097 }
        if (r6 == 0) goto L_0x005f;
    L_0x0058:
        r6 = "MessengerIpcClient";
        r0 = "Starting bind to GmsCore";
        android.util.Log.v(r6, r0);	 Catch:{ all -> 0x0097 }
    L_0x005f:
        r5.state = r3;	 Catch:{ all -> 0x0097 }
        r6 = new android.content.Intent;	 Catch:{ all -> 0x0097 }
        r0 = "com.google.android.c2dm.intent.REGISTER";
        r6.<init>(r0);	 Catch:{ all -> 0x0097 }
        r0 = "com.google.android.gms";
        r6.setPackage(r0);	 Catch:{ all -> 0x0097 }
        r0 = com.google.android.gms.common.stats.ConnectionTracker.getInstance();	 Catch:{ all -> 0x0097 }
        r1 = r5.zzcl;	 Catch:{ all -> 0x0097 }
        r1 = r1.zzl;	 Catch:{ all -> 0x0097 }
        r6 = r0.bindService(r1, r6, r5, r3);	 Catch:{ all -> 0x0097 }
        if (r6 != 0) goto L_0x0083;
    L_0x007d:
        r6 = "Unable to bind to service";
        r5.zzd(r2, r6);	 Catch:{ all -> 0x0097 }
        goto L_0x0095;
    L_0x0083:
        r6 = r5.zzcl;	 Catch:{ all -> 0x0097 }
        r6 = r6.zzce;	 Catch:{ all -> 0x0097 }
        r0 = new com.google.android.gms.iid.zzv;	 Catch:{ all -> 0x0097 }
        r0.<init>(r5);	 Catch:{ all -> 0x0097 }
        r1 = 30;
        r4 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ all -> 0x0097 }
        r6.schedule(r0, r1, r4);	 Catch:{ all -> 0x0097 }
    L_0x0095:
        monitor-exit(r5);
        return r3;
    L_0x0097:
        r6 = move-exception;
        monitor-exit(r5);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzt.zze(com.google.android.gms.iid.zzz):boolean");
    }

    /* JADX WARNING: Missing block: B:12:0x0052, code:
            r5 = r5.getData();
     */
    /* JADX WARNING: Missing block: B:13:0x005d, code:
            if (r5.getBoolean("unsupported", false) == false) goto L_0x006b;
     */
    /* JADX WARNING: Missing block: B:14:0x005f, code:
            r1.zzd(new com.google.android.gms.iid.zzaa(4, "Not supported by GmsCore"));
     */
    /* JADX WARNING: Missing block: B:15:0x006b, code:
            r1.zzh(r5);
     */
    /* JADX WARNING: Missing block: B:16:0x006e, code:
            return true;
     */
    final boolean zzd(android.os.Message r5) {
        /*
        r4 = this;
        r0 = r5.arg1;
        r1 = "MessengerIpcClient";
        r2 = 3;
        r1 = android.util.Log.isLoggable(r1, r2);
        if (r1 == 0) goto L_0x0023;
    L_0x000b:
        r1 = 41;
        r2 = new java.lang.StringBuilder;
        r2.<init>(r1);
        r1 = "Received response to request: ";
        r2.append(r1);
        r2.append(r0);
        r1 = r2.toString();
        r2 = "MessengerIpcClient";
        android.util.Log.d(r2, r1);
    L_0x0023:
        monitor-enter(r4);
        r1 = r4.zzck;	 Catch:{ all -> 0x006f }
        r1 = r1.get(r0);	 Catch:{ all -> 0x006f }
        r1 = (com.google.android.gms.iid.zzz) r1;	 Catch:{ all -> 0x006f }
        r2 = 1;
        if (r1 != 0) goto L_0x0049;
    L_0x002f:
        r5 = "MessengerIpcClient";
        r1 = 50;
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006f }
        r3.<init>(r1);	 Catch:{ all -> 0x006f }
        r1 = "Received response for unknown request: ";
        r3.append(r1);	 Catch:{ all -> 0x006f }
        r3.append(r0);	 Catch:{ all -> 0x006f }
        r0 = r3.toString();	 Catch:{ all -> 0x006f }
        android.util.Log.w(r5, r0);	 Catch:{ all -> 0x006f }
        monitor-exit(r4);	 Catch:{ all -> 0x006f }
        return r2;
    L_0x0049:
        r3 = r4.zzck;	 Catch:{ all -> 0x006f }
        r3.remove(r0);	 Catch:{ all -> 0x006f }
        r4.zzu();	 Catch:{ all -> 0x006f }
        monitor-exit(r4);	 Catch:{ all -> 0x006f }
        r5 = r5.getData();
        r0 = 0;
        r3 = "unsupported";
        r0 = r5.getBoolean(r3, r0);
        if (r0 == 0) goto L_0x006b;
    L_0x005f:
        r5 = new com.google.android.gms.iid.zzaa;
        r0 = 4;
        r3 = "Not supported by GmsCore";
        r5.<init>(r0, r3);
        r1.zzd(r5);
        goto L_0x006e;
    L_0x006b:
        r1.zzh(r5);
    L_0x006e:
        return r2;
    L_0x006f:
        r5 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x006f }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzt.zzd(android.os.Message):boolean");
    }

    public final synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service connected");
        }
        if (iBinder == null) {
            zzd(0, "Null service connection");
            return;
        }
        try {
            this.zzci = new zzy(iBinder);
            this.state = 2;
            zzt();
        } catch (RemoteException e) {
            zzd(0, e.getMessage());
        }
    }

    private final void zzt() {
        this.zzcl.zzce.execute(new zzw(this));
    }

    public final synchronized void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("MessengerIpcClient", 2)) {
            Log.v("MessengerIpcClient", "Service disconnected");
        }
        zzd(2, "Service disconnected");
    }

    final synchronized void zzd(int i, String str) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String str2 = "MessengerIpcClient";
            String str3 = "Disconnected: ";
            String valueOf = String.valueOf(str);
            Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        }
        int i2 = this.state;
        if (i2 == 0) {
            throw new IllegalStateException();
        } else if (i2 == 1 || i2 == 2) {
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Unbinding service");
            }
            this.state = 4;
            ConnectionTracker.getInstance().unbindService(this.zzcl.zzl, this);
            zzaa zzaa = new zzaa(i, str);
            for (zzz zzd : this.zzcj) {
                zzd.zzd(zzaa);
            }
            this.zzcj.clear();
            for (i = 0; i < this.zzck.size(); i++) {
                ((zzz) this.zzck.valueAt(i)).zzd(zzaa);
            }
            this.zzck.clear();
        } else if (i2 == 3) {
            this.state = 4;
        } else if (i2 != 4) {
            int i3 = this.state;
            StringBuilder stringBuilder = new StringBuilder(26);
            stringBuilder.append("Unknown state: ");
            stringBuilder.append(i3);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    final synchronized void zzu() {
        if (this.state == 2 && this.zzcj.isEmpty() && this.zzck.size() == 0) {
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Finished handling requests, unbinding");
            }
            this.state = 3;
            ConnectionTracker.getInstance().unbindService(this.zzcl.zzl, this);
        }
    }

    final synchronized void zzv() {
        if (this.state == 1) {
            zzd(1, "Timed out while binding");
        }
    }

    final synchronized void zzg(int i) {
        zzz zzz = (zzz) this.zzck.get(i);
        if (zzz != null) {
            StringBuilder stringBuilder = new StringBuilder(31);
            stringBuilder.append("Timing out request: ");
            stringBuilder.append(i);
            Log.w("MessengerIpcClient", stringBuilder.toString());
            this.zzck.remove(i);
            zzz.zzd(new zzaa(3, "Timed out waiting for response"));
            zzu();
        }
    }
}
