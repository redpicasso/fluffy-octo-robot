package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import java.util.Map;

public final class zzhq extends zzg {
    @VisibleForTesting
    protected zzhr zzqo;
    private volatile zzhr zzqp;
    private zzhr zzqq;
    private final Map<Activity, zzhr> zzqr = new ArrayMap();
    private zzhr zzqs;
    private String zzqt;

    public zzhq(zzfj zzfj) {
        super(zzfj);
    }

    protected final boolean zzbk() {
        return false;
    }

    @WorkerThread
    public final zzhr zzin() {
        zzbi();
        zzo();
        return this.zzqo;
    }

    public final void setCurrentScreen(@NonNull Activity activity, @Size(max = 36, min = 1) @Nullable String str, @Size(max = 36, min = 1) @Nullable String str2) {
        if (this.zzqp == null) {
            zzab().zzgp().zzao("setCurrentScreen cannot be called while no activity active");
        } else if (this.zzqr.get(activity) == null) {
            zzab().zzgp().zzao("setCurrentScreen must be called with an activity in the activity lifecycle");
        } else {
            if (str2 == null) {
                str2 = zzbh(activity.getClass().getCanonicalName());
            }
            boolean equals = this.zzqp.zzqv.equals(str2);
            boolean zzs = zzjs.zzs(this.zzqp.zzqu, str);
            if (equals && zzs) {
                zzab().zzgp().zzao("setCurrentScreen cannot be called with the same class and name");
            } else if (str != null && (str.length() <= 0 || str.length() > 100)) {
                zzab().zzgp().zza("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            } else if (str2 == null || (str2.length() > 0 && str2.length() <= 100)) {
                zzab().zzgs().zza("Setting current screen to name, class", str == null ? "null" : str, str2);
                zzhr zzhr = new zzhr(str, str2, zzz().zzjv());
                this.zzqr.put(activity, zzhr);
                zza(activity, zzhr, true);
            } else {
                zzab().zzgp().zza("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            }
        }
    }

    public final zzhr zzio() {
        zzm();
        return this.zzqp;
    }

    @MainThread
    private final void zza(Activity activity, zzhr zzhr, boolean z) {
        zzhr zzhr2 = this.zzqp == null ? this.zzqq : this.zzqp;
        if (zzhr.zzqv == null) {
            zzhr = new zzhr(zzhr.zzqu, zzbh(activity.getClass().getCanonicalName()), zzhr.zzqw);
        }
        this.zzqq = this.zzqp;
        this.zzqp = zzhr;
        zzaa().zza(new zzht(this, z, zzhr2, zzhr));
    }

    @WorkerThread
    private final void zza(@NonNull zzhr zzhr, boolean z) {
        zzp().zzc(zzx().elapsedRealtime());
        if (zzv().zza(zzhr.zzqx, z)) {
            zzhr.zzqx = false;
        }
    }

    public static void zza(zzhr zzhr, Bundle bundle, boolean z) {
        String str = "_si";
        String str2 = "_sn";
        String str3 = "_sc";
        if (bundle == null || zzhr == null || (bundle.containsKey(str3) && !z)) {
            if (bundle != null && zzhr == null && z) {
                bundle.remove(str2);
                bundle.remove(str3);
                bundle.remove(str);
            }
            return;
        }
        if (zzhr.zzqu != null) {
            bundle.putString(str2, zzhr.zzqu);
        } else {
            bundle.remove(str2);
        }
        bundle.putString(str3, zzhr.zzqv);
        bundle.putLong(str, zzhr.zzqw);
    }

    @WorkerThread
    public final void zza(String str, zzhr zzhr) {
        zzo();
        synchronized (this) {
            if (this.zzqt == null || this.zzqt.equals(str) || zzhr != null) {
                this.zzqt = str;
                this.zzqs = zzhr;
            }
        }
    }

    @VisibleForTesting
    private static String zzbh(String str) {
        String[] split = str.split("\\.");
        str = split.length > 0 ? split[split.length - 1] : "";
        return str.length() > 100 ? str.substring(0, 100) : str;
    }

    @MainThread
    private final zzhr zza(@NonNull Activity activity) {
        Preconditions.checkNotNull(activity);
        zzhr zzhr = (zzhr) this.zzqr.get(activity);
        if (zzhr != null) {
            return zzhr;
        }
        zzhr zzhr2 = new zzhr(null, zzbh(activity.getClass().getCanonicalName()), zzz().zzjv());
        this.zzqr.put(activity, zzhr2);
        return zzhr2;
    }

    @MainThread
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        if (bundle != null) {
            bundle = bundle.getBundle("com.google.app_measurement.screen_service");
            if (bundle != null) {
                this.zzqr.put(activity, new zzhr(bundle.getString(ConditionalUserProperty.NAME), bundle.getString("referrer_name"), bundle.getLong("id")));
            }
        }
    }

    @MainThread
    public final void onActivityResumed(Activity activity) {
        zza(activity, zza(activity), false);
        zzgf zzp = zzp();
        zzp.zzaa().zza(new zze(zzp, zzp.zzx().elapsedRealtime()));
    }

    @MainThread
    public final void onActivityPaused(Activity activity) {
        zzhr zza = zza(activity);
        this.zzqq = this.zzqp;
        this.zzqp = null;
        zzaa().zza(new zzhs(this, zza));
    }

    @MainThread
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        if (bundle != null) {
            zzhr zzhr = (zzhr) this.zzqr.get(activity);
            if (zzhr != null) {
                Bundle bundle2 = new Bundle();
                bundle2.putLong("id", zzhr.zzqw);
                bundle2.putString(ConditionalUserProperty.NAME, zzhr.zzqu);
                bundle2.putString("referrer_name", zzhr.zzqv);
                bundle.putBundle("com.google.app_measurement.screen_service", bundle2);
            }
        }
    }

    @MainThread
    public final void onActivityDestroyed(Activity activity) {
        this.zzqr.remove(activity);
    }
}
