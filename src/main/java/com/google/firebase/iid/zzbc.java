package com.google.firebase.iid;

import android.content.Intent;
import android.util.Log;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final /* synthetic */ class zzbc implements Runnable {
    private final zzbd zza;
    private final Intent zzb;

    zzbc(zzbd zzbd, Intent intent) {
        this.zza = zzbd;
        this.zzb = intent;
    }

    public final void run() {
        zzbd zzbd = this.zza;
        String action = this.zzb.getAction();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(action).length() + 61);
        stringBuilder.append("Service took too long to process intent: ");
        stringBuilder.append(action);
        stringBuilder.append(" App may get closed.");
        Log.w("FirebaseInstanceId", stringBuilder.toString());
        zzbd.zza();
    }
}
