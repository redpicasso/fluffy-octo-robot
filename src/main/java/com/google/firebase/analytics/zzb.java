package com.google.firebase.analytics;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

final class zzb implements Callable<String> {
    private final /* synthetic */ FirebaseAnalytics zzaca;

    zzb(FirebaseAnalytics firebaseAnalytics) {
        this.zzaca = firebaseAnalytics;
    }

    public final /* synthetic */ Object call() throws Exception {
        String zza = this.zzaca.zzi();
        if (zza != null) {
            return zza;
        }
        if (this.zzaca.zzl) {
            zza = this.zzaca.zzabu.getAppInstanceId();
        } else {
            zza = this.zzaca.zzj.zzq().zzy(120000);
        }
        if (zza != null) {
            this.zzaca.zzbg(zza);
            return zza;
        }
        throw new TimeoutException();
    }
}
