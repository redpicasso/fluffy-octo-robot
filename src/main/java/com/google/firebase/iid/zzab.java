package com.google.firebase.iid;

import android.os.IBinder;
import android.os.RemoteException;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final /* synthetic */ class zzab implements Runnable {
    private final zzw zza;
    private final IBinder zzb;

    zzab(zzw zzw, IBinder iBinder) {
        this.zza = zzw;
        this.zzb = iBinder;
    }

    public final void run() {
        zzw zzw = this.zza;
        IBinder iBinder = this.zzb;
        synchronized (zzw) {
            if (iBinder == null) {
                zzw.zza(0, "Null service connection");
                return;
            }
            try {
                zzw.zzc = new zzaf(iBinder);
                zzw.zza = 2;
                zzw.zza();
            } catch (RemoteException e) {
                zzw.zza(0, e.getMessage());
            }
        }
    }
}
