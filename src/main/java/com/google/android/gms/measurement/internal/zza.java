package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Map;

public final class zza extends zzd {
    private final Map<String, Long> zzby = new ArrayMap();
    private final Map<String, Integer> zzbz = new ArrayMap();
    private long zzca;

    public zza(zzfj zzfj) {
        super(zzfj);
    }

    public final void beginAdUnitExposure(String str, long j) {
        if (str == null || str.length() == 0) {
            zzab().zzgk().zzao("Ad unit id must be a non-empty string");
        } else {
            zzaa().zza(new zzc(this, str, j));
        }
    }

    @WorkerThread
    private final void zza(String str, long j) {
        zzm();
        zzo();
        Preconditions.checkNotEmpty(str);
        if (this.zzbz.isEmpty()) {
            this.zzca = j;
        }
        Integer num = (Integer) this.zzbz.get(str);
        if (num != null) {
            this.zzbz.put(str, Integer.valueOf(num.intValue() + 1));
        } else if (this.zzbz.size() >= 100) {
            zzab().zzgn().zzao("Too many ads visible");
        } else {
            this.zzbz.put(str, Integer.valueOf(1));
            this.zzby.put(str, Long.valueOf(j));
        }
    }

    public final void endAdUnitExposure(String str, long j) {
        if (str == null || str.length() == 0) {
            zzab().zzgk().zzao("Ad unit id must be a non-empty string");
        } else {
            zzaa().zza(new zzb(this, str, j));
        }
    }

    @WorkerThread
    private final void zzb(String str, long j) {
        zzm();
        zzo();
        Preconditions.checkNotEmpty(str);
        Integer num = (Integer) this.zzbz.get(str);
        if (num != null) {
            zzhr zzin = zzt().zzin();
            int intValue = num.intValue() - 1;
            if (intValue == 0) {
                long longValue;
                this.zzbz.remove(str);
                Long l = (Long) this.zzby.get(str);
                if (l == null) {
                    zzab().zzgk().zzao("First ad unit exposure time was never set");
                } else {
                    longValue = j - l.longValue();
                    this.zzby.remove(str);
                    zza(str, longValue, zzin);
                }
                if (this.zzbz.isEmpty()) {
                    longValue = this.zzca;
                    if (longValue == 0) {
                        zzab().zzgk().zzao("First ad exposure time was never set");
                        return;
                    } else {
                        zza(j - longValue, zzin);
                        this.zzca = 0;
                    }
                }
                return;
            }
            this.zzbz.put(str, Integer.valueOf(intValue));
            return;
        }
        zzab().zzgk().zza("Call to endAdUnitExposure for unknown ad unit id", str);
    }

    @WorkerThread
    private final void zza(long j, zzhr zzhr) {
        if (zzhr == null) {
            zzab().zzgs().zzao("Not logging ad exposure. No active activity");
        } else if (j < 1000) {
            zzab().zzgs().zza("Not logging ad exposure. Less than 1000 ms. exposure", Long.valueOf(j));
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong("_xt", j);
            zzhq.zza(zzhr, bundle, true);
            zzq().logEvent("am", "_xa", bundle);
        }
    }

    @WorkerThread
    private final void zza(String str, long j, zzhr zzhr) {
        if (zzhr == null) {
            zzab().zzgs().zzao("Not logging ad unit exposure. No active activity");
        } else if (j < 1000) {
            zzab().zzgs().zza("Not logging ad unit exposure. Less than 1000 ms. exposure", Long.valueOf(j));
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("_ai", str);
            bundle.putLong("_xt", j);
            zzhq.zza(zzhr, bundle, true);
            zzq().logEvent("am", "_xu", bundle);
        }
    }

    @WorkerThread
    public final void zzc(long j) {
        zzhr zzin = zzt().zzin();
        for (String str : this.zzby.keySet()) {
            zza(str, j - ((Long) this.zzby.get(str)).longValue(), zzin);
        }
        if (!this.zzby.isEmpty()) {
            zza(j - this.zzca, zzin);
        }
        zzd(j);
    }

    @WorkerThread
    private final void zzd(long j) {
        for (String put : this.zzby.keySet()) {
            this.zzby.put(put, Long.valueOf(j));
        }
        if (!this.zzby.isEmpty()) {
            this.zzca = j;
        }
    }
}
