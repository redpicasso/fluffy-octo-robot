package com.google.android.gms.internal.firebase_ml;

import android.os.SystemClock;
import com.google.android.gms.internal.firebase_ml.zzmd.zzab;
import com.google.android.gms.internal.firebase_ml.zzmd.zzq;
import com.google.android.gms.internal.firebase_ml.zzmd.zzq.zza;
import com.google.android.gms.internal.firebase_ml.zzmd.zzs;

final /* synthetic */ class zzqk implements zznv {
    private final long zzaue;
    private final zzmk zzazu;
    private final zzpz zzazv;

    zzqk(long j, zzmk zzmk, zzpz zzpz) {
        this.zzaue = j;
        this.zzazu = zzmk;
        this.zzazv = zzpz;
    }

    public final zza zzm() {
        long j = this.zzaue;
        long elapsedRealtime = SystemClock.elapsedRealtime() - j;
        return zzq.zzjx().zzb(zzab.zzle().zzh(zzs.zzkb().zzn(elapsedRealtime).zzc(this.zzazu).zzz(zzqj.zzata).zzaa(true).zzab(true)).zze(zzpv.zzc(this.zzazv)));
    }
}
