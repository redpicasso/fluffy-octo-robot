package com.google.firebase.iid;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.MainThread;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.internal.firebase_messaging.zze;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zzw implements ServiceConnection {
    @GuardedBy("this")
    int zza;
    final Messenger zzb;
    zzaf zzc;
    @GuardedBy("this")
    final Queue<zzah<?>> zzd;
    @GuardedBy("this")
    final SparseArray<zzah<?>> zze;
    final /* synthetic */ zzv zzf;

    private zzw(zzv zzv) {
        this.zzf = zzv;
        this.zza = 0;
        this.zzb = new Messenger(new zze(Looper.getMainLooper(), new zzz(this)));
        this.zzd = new ArrayDeque();
        this.zze = new SparseArray();
    }

    /* JADX WARNING: Missing block: B:13:0x002f, code:
            return false;
     */
    /* JADX WARNING: Missing block: B:35:0x0096, code:
            return true;
     */
    final synchronized boolean zza(com.google.firebase.iid.zzah<?> r6) {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = r5.zza;	 Catch:{ all -> 0x0097 }
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
        r0 = r5.zza;	 Catch:{ all -> 0x0097 }
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
        r0 = r5.zzd;	 Catch:{ all -> 0x0097 }
        r0.add(r6);	 Catch:{ all -> 0x0097 }
        r5.zza();	 Catch:{ all -> 0x0097 }
        monitor-exit(r5);
        return r3;
    L_0x003a:
        r0 = r5.zzd;	 Catch:{ all -> 0x0097 }
        r0.add(r6);	 Catch:{ all -> 0x0097 }
        monitor-exit(r5);
        return r3;
    L_0x0041:
        r0 = r5.zzd;	 Catch:{ all -> 0x0097 }
        r0.add(r6);	 Catch:{ all -> 0x0097 }
        r6 = r5.zza;	 Catch:{ all -> 0x0097 }
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
        r5.zza = r3;	 Catch:{ all -> 0x0097 }
        r6 = new android.content.Intent;	 Catch:{ all -> 0x0097 }
        r0 = "com.google.android.c2dm.intent.REGISTER";
        r6.<init>(r0);	 Catch:{ all -> 0x0097 }
        r0 = "com.google.android.gms";
        r6.setPackage(r0);	 Catch:{ all -> 0x0097 }
        r0 = com.google.android.gms.common.stats.ConnectionTracker.getInstance();	 Catch:{ all -> 0x0097 }
        r1 = r5.zzf;	 Catch:{ all -> 0x0097 }
        r1 = r1.zzb;	 Catch:{ all -> 0x0097 }
        r6 = r0.bindService(r1, r6, r5, r3);	 Catch:{ all -> 0x0097 }
        if (r6 != 0) goto L_0x0083;
    L_0x007d:
        r6 = "Unable to bind to service";
        r5.zza(r2, r6);	 Catch:{ all -> 0x0097 }
        goto L_0x0095;
    L_0x0083:
        r6 = r5.zzf;	 Catch:{ all -> 0x0097 }
        r6 = r6.zzc;	 Catch:{ all -> 0x0097 }
        r0 = new com.google.firebase.iid.zzy;	 Catch:{ all -> 0x0097 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzw.zza(com.google.firebase.iid.zzah):boolean");
    }

    /* JADX WARNING: Missing block: B:12:0x0052, code:
            r5 = r5.getData();
     */
    /* JADX WARNING: Missing block: B:13:0x005d, code:
            if (r5.getBoolean("unsupported", false) == false) goto L_0x006b;
     */
    /* JADX WARNING: Missing block: B:14:0x005f, code:
            r1.zza(new com.google.firebase.iid.zzag(4, "Not supported by GmsCore"));
     */
    /* JADX WARNING: Missing block: B:15:0x006b, code:
            r1.zza(r5);
     */
    /* JADX WARNING: Missing block: B:16:0x006e, code:
            return true;
     */
    final boolean zza(android.os.Message r5) {
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
        r1 = r4.zze;	 Catch:{ all -> 0x006f }
        r1 = r1.get(r0);	 Catch:{ all -> 0x006f }
        r1 = (com.google.firebase.iid.zzah) r1;	 Catch:{ all -> 0x006f }
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
        r3 = r4.zze;	 Catch:{ all -> 0x006f }
        r3.remove(r0);	 Catch:{ all -> 0x006f }
        r4.zzb();	 Catch:{ all -> 0x006f }
        monitor-exit(r4);	 Catch:{ all -> 0x006f }
        r5 = r5.getData();
        r0 = 0;
        r3 = "unsupported";
        r0 = r5.getBoolean(r3, r0);
        if (r0 == 0) goto L_0x006b;
    L_0x005f:
        r5 = new com.google.firebase.iid.zzag;
        r0 = 4;
        r3 = "Not supported by GmsCore";
        r5.<init>(r0, r3);
        r1.zza(r5);
        goto L_0x006e;
    L_0x006b:
        r1.zza(r5);
    L_0x006e:
        return r2;
    L_0x006f:
        r5 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x006f }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzw.zza(android.os.Message):boolean");
    }

    @MainThread
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        String str = "MessengerIpcClient";
        if (Log.isLoggable(str, 2)) {
            Log.v(str, "Service connected");
        }
        this.zzf.zzc.execute(new zzab(this, iBinder));
    }

    final void zza() {
        this.zzf.zzc.execute(new zzaa(this));
    }

    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        String str = "MessengerIpcClient";
        if (Log.isLoggable(str, 2)) {
            Log.v(str, "Service disconnected");
        }
        this.zzf.zzc.execute(new zzad(this));
    }

    final synchronized void zza(int i, String str) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String str2 = "MessengerIpcClient";
            String str3 = "Disconnected: ";
            String valueOf = String.valueOf(str);
            Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        }
        int i2 = this.zza;
        if (i2 == 0) {
            throw new IllegalStateException();
        } else if (i2 == 1 || i2 == 2) {
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Unbinding service");
            }
            this.zza = 4;
            ConnectionTracker.getInstance().unbindService(this.zzf.zzb, this);
            zzag zzag = new zzag(i, str);
            for (zzah zza : this.zzd) {
                zza.zza(zzag);
            }
            this.zzd.clear();
            for (i = 0; i < this.zze.size(); i++) {
                ((zzah) this.zze.valueAt(i)).zza(zzag);
            }
            this.zze.clear();
        } else if (i2 == 3) {
            this.zza = 4;
        } else if (i2 != 4) {
            int i3 = this.zza;
            StringBuilder stringBuilder = new StringBuilder(26);
            stringBuilder.append("Unknown state: ");
            stringBuilder.append(i3);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    final synchronized void zzb() {
        if (this.zza == 2 && this.zzd.isEmpty() && this.zze.size() == 0) {
            if (Log.isLoggable("MessengerIpcClient", 2)) {
                Log.v("MessengerIpcClient", "Finished handling requests, unbinding");
            }
            this.zza = 3;
            ConnectionTracker.getInstance().unbindService(this.zzf.zzb, this);
        }
    }

    final synchronized void zzc() {
        if (this.zza == 1) {
            zza(1, "Timed out while binding");
        }
    }

    final synchronized void zza(int i) {
        zzah zzah = (zzah) this.zze.get(i);
        if (zzah != null) {
            StringBuilder stringBuilder = new StringBuilder(31);
            stringBuilder.append("Timing out request: ");
            stringBuilder.append(i);
            Log.w("MessengerIpcClient", stringBuilder.toString());
            this.zze.remove(i);
            zzah.zza(new zzag(3, "Timed out waiting for response"));
            zzb();
        }
    }

    /* synthetic */ zzw(zzv zzv, zzx zzx) {
        this(zzv);
    }
}
