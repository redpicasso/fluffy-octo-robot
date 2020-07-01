package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

final class zzgv implements Runnable {
    private final /* synthetic */ zzgp zzpt;
    private final /* synthetic */ long zzqf;

    zzgv(zzgp zzgp, long j) {
        this.zzpt = zzgp;
        this.zzqf = j;
    }

    public final void run() {
        zzgf zzgf = this.zzpt;
        long j = this.zzqf;
        zzgf.zzo();
        zzgf.zzm();
        zzgf.zzbi();
        zzgf.zzab().zzgr().zzao("Resetting analytics data (FE)");
        zzgf.zzv().zziz();
        if (zzgf.zzad().zzr(zzgf.zzr().zzag())) {
            zzgf.zzac().zzlo.set(j);
        }
        boolean isEnabled = zzgf.zzj.isEnabled();
        if (!zzgf.zzad().zzbp()) {
            zzgf.zzac().zzf(isEnabled ^ 1);
        }
        zzgf.zzs().resetAnalyticsData();
        zzgf.zzpz = isEnabled ^ 1;
        this.zzpt.zzs().zza(new AtomicReference());
    }
}
