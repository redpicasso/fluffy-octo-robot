package com.google.firebase.iid;

import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final /* synthetic */ class zzk implements EventHandler {
    private final zza zza;

    zzk(zza zza) {
        this.zza = zza;
    }

    public final void handle(Event event) {
        zza zza = this.zza;
        synchronized (zza) {
            if (zza.zza()) {
                FirebaseInstanceId.this.zzj();
            }
        }
    }
}
