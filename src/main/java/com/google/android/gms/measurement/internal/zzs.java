package com.google.android.gms.measurement.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import java.lang.reflect.InvocationTargetException;

public final class zzs extends zzgf {
    private Boolean zzeb;
    @NonNull
    private zzu zzec = zzv.zzee;
    private Boolean zzed;

    zzs(zzfj zzfj) {
        super(zzfj);
        zzak.zza(zzfj);
    }

    final void zza(@NonNull zzu zzu) {
        this.zzec = zzu;
    }

    static String zzbm() {
        return (String) zzak.zzgf.get(null);
    }

    @WorkerThread
    public final int zzi(@Size(min = 1) String str) {
        return zzb(str, zzak.zzgt);
    }

    public final long zzao() {
        zzae();
        return 16250;
    }

    public final boolean zzbn() {
        if (this.zzed == null) {
            synchronized (this) {
                if (this.zzed == null) {
                    ApplicationInfo applicationInfo = getContext().getApplicationInfo();
                    String myProcessName = ProcessUtils.getMyProcessName();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        boolean z = str != null && str.equals(myProcessName);
                        this.zzed = Boolean.valueOf(z);
                    }
                    if (this.zzed == null) {
                        this.zzed = Boolean.TRUE;
                        zzab().zzgk().zzao("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzed.booleanValue();
    }

    @WorkerThread
    public final long zza(String str, @NonNull zzdu<Long> zzdu) {
        if (str == null) {
            return ((Long) zzdu.get(null)).longValue();
        }
        Object zzb = this.zzec.zzb(str, zzdu.getKey());
        if (TextUtils.isEmpty(zzb)) {
            return ((Long) zzdu.get(null)).longValue();
        }
        try {
            return ((Long) zzdu.get(Long.valueOf(Long.parseLong(zzb)))).longValue();
        } catch (NumberFormatException unused) {
            return ((Long) zzdu.get(null)).longValue();
        }
    }

    @WorkerThread
    public final int zzb(String str, @NonNull zzdu<Integer> zzdu) {
        if (str == null) {
            return ((Integer) zzdu.get(null)).intValue();
        }
        Object zzb = this.zzec.zzb(str, zzdu.getKey());
        if (TextUtils.isEmpty(zzb)) {
            return ((Integer) zzdu.get(null)).intValue();
        }
        try {
            return ((Integer) zzdu.get(Integer.valueOf(Integer.parseInt(zzb)))).intValue();
        } catch (NumberFormatException unused) {
            return ((Integer) zzdu.get(null)).intValue();
        }
    }

    @WorkerThread
    public final double zzc(String str, @NonNull zzdu<Double> zzdu) {
        if (str == null) {
            return ((Double) zzdu.get(null)).doubleValue();
        }
        Object zzb = this.zzec.zzb(str, zzdu.getKey());
        if (TextUtils.isEmpty(zzb)) {
            return ((Double) zzdu.get(null)).doubleValue();
        }
        try {
            return ((Double) zzdu.get(Double.valueOf(Double.parseDouble(zzb)))).doubleValue();
        } catch (NumberFormatException unused) {
            return ((Double) zzdu.get(null)).doubleValue();
        }
    }

    @WorkerThread
    public final boolean zzd(String str, @NonNull zzdu<Boolean> zzdu) {
        if (str == null) {
            return ((Boolean) zzdu.get(null)).booleanValue();
        }
        Object zzb = this.zzec.zzb(str, zzdu.getKey());
        if (TextUtils.isEmpty(zzb)) {
            return ((Boolean) zzdu.get(null)).booleanValue();
        }
        return ((Boolean) zzdu.get(Boolean.valueOf(Boolean.parseBoolean(zzb)))).booleanValue();
    }

    public final boolean zze(String str, zzdu<Boolean> zzdu) {
        return zzd(str, zzdu);
    }

    public final boolean zza(zzdu<Boolean> zzdu) {
        return zzd(null, zzdu);
    }

    @Nullable
    @VisibleForTesting
    private final Bundle zzbo() {
        try {
            if (getContext().getPackageManager() == null) {
                zzab().zzgk().zzao("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo applicationInfo = Wrappers.packageManager(getContext()).getApplicationInfo(getContext().getPackageName(), 128);
            if (applicationInfo != null) {
                return applicationInfo.metaData;
            }
            zzab().zzgk().zzao("Failed to load metadata: ApplicationInfo is null");
            return null;
        } catch (NameNotFoundException e) {
            zzab().zzgk().zza("Failed to load metadata: Package name not found", e);
            return null;
        }
    }

    @Nullable
    @VisibleForTesting
    final Boolean zzj(@Size(min = 1) String str) {
        Preconditions.checkNotEmpty(str);
        Bundle zzbo = zzbo();
        if (zzbo == null) {
            zzab().zzgk().zzao("Failed to load metadata: Metadata bundle is null");
            return null;
        } else if (zzbo.containsKey(str)) {
            return Boolean.valueOf(zzbo.getBoolean(str));
        } else {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x002b A:{SYNTHETIC, Splitter: B:9:0x002b} */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a A:{RETURN} */
    @androidx.annotation.Nullable
    @com.google.android.gms.common.util.VisibleForTesting
    final java.util.List<java.lang.String> zzk(@androidx.annotation.Size(min = 1) java.lang.String r4) {
        /*
        r3 = this;
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r4);
        r0 = r3.zzbo();
        r1 = 0;
        if (r0 != 0) goto L_0x0019;
    L_0x000a:
        r4 = r3.zzab();
        r4 = r4.zzgk();
        r0 = "Failed to load metadata: Metadata bundle is null";
        r4.zzao(r0);
    L_0x0017:
        r4 = r1;
        goto L_0x0028;
    L_0x0019:
        r2 = r0.containsKey(r4);
        if (r2 != 0) goto L_0x0020;
    L_0x001f:
        goto L_0x0017;
    L_0x0020:
        r4 = r0.getInt(r4);
        r4 = java.lang.Integer.valueOf(r4);
    L_0x0028:
        if (r4 != 0) goto L_0x002b;
    L_0x002a:
        return r1;
    L_0x002b:
        r0 = r3.getContext();	 Catch:{ NotFoundException -> 0x0043 }
        r0 = r0.getResources();	 Catch:{ NotFoundException -> 0x0043 }
        r4 = r4.intValue();	 Catch:{ NotFoundException -> 0x0043 }
        r4 = r0.getStringArray(r4);	 Catch:{ NotFoundException -> 0x0043 }
        if (r4 != 0) goto L_0x003e;
    L_0x003d:
        return r1;
    L_0x003e:
        r4 = java.util.Arrays.asList(r4);	 Catch:{ NotFoundException -> 0x0043 }
        return r4;
    L_0x0043:
        r4 = move-exception;
        r0 = r3.zzab();
        r0 = r0.zzgk();
        r2 = "Failed to load string array from metadata: resource not found";
        r0.zza(r2, r4);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzs.zzk(java.lang.String):java.util.List<java.lang.String>");
    }

    public final boolean zzbp() {
        zzae();
        Boolean zzj = zzj("firebase_analytics_collection_deactivated");
        return zzj != null && zzj.booleanValue();
    }

    public final Boolean zzbq() {
        zzae();
        return zzj("firebase_analytics_collection_enabled");
    }

    public final Boolean zzbr() {
        zzm();
        Boolean zzj = zzj("google_analytics_adid_collection_enabled");
        boolean z = zzj == null || zzj.booleanValue();
        return Boolean.valueOf(z);
    }

    public static long zzbs() {
        return ((Long) zzak.zzhi.get(null)).longValue();
    }

    public static long zzbt() {
        return ((Long) zzak.zzgi.get(null)).longValue();
    }

    public final String zzbu() {
        String str = "";
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{"debug.firebase.analytics.app", str});
        } catch (ClassNotFoundException e) {
            zzab().zzgk().zza("Could not find SystemProperties class", e);
            return str;
        } catch (NoSuchMethodException e2) {
            zzab().zzgk().zza("Could not find SystemProperties.get() method", e2);
            return str;
        } catch (IllegalAccessException e3) {
            zzab().zzgk().zza("Could not access SystemProperties.get()", e3);
            return str;
        } catch (InvocationTargetException e4) {
            zzab().zzgk().zza("SystemProperties.get() threw an exception", e4);
            return str;
        }
    }

    public static boolean zzbv() {
        return ((Boolean) zzak.zzge.get(null)).booleanValue();
    }

    public final boolean zzl(String str) {
        return "1".equals(this.zzec.zzb(str, "gaia_collection_enabled"));
    }

    public final boolean zzm(String str) {
        return "1".equals(this.zzec.zzb(str, "measurement.event_sampling_enabled"));
    }

    @WorkerThread
    final boolean zzn(String str) {
        return zzd(str, zzak.zzhs);
    }

    @WorkerThread
    final boolean zzo(String str) {
        return zzd(str, zzak.zzhm);
    }

    @WorkerThread
    final String zzp(String str) {
        zzdu zzdu = zzak.zzhn;
        if (str == null) {
            return (String) zzdu.get(null);
        }
        return (String) zzdu.get(this.zzec.zzb(str, zzdu.getKey()));
    }

    final boolean zzq(String str) {
        return zzd(str, zzak.zzht);
    }

    @WorkerThread
    final boolean zzr(String str) {
        return zzd(str, zzak.zzhu);
    }

    @WorkerThread
    final boolean zzs(String str) {
        return zzd(str, zzak.zzhv);
    }

    @WorkerThread
    final boolean zzt(String str) {
        return zzd(str, zzak.zzhx);
    }

    @WorkerThread
    final boolean zzbw() {
        if (this.zzeb == null) {
            this.zzeb = zzj("app_measurement_lite");
            if (this.zzeb == null) {
                this.zzeb = Boolean.valueOf(false);
            }
        }
        if (this.zzeb.booleanValue() || !this.zzj.zzia()) {
            return true;
        }
        return false;
    }

    @WorkerThread
    final boolean zzu(String str) {
        return zzd(str, zzak.zzhw);
    }

    @WorkerThread
    static boolean zzbx() {
        return ((Boolean) zzak.zzhy.get(null)).booleanValue();
    }

    @WorkerThread
    final boolean zzv(String str) {
        return zzd(str, zzak.zzhz);
    }

    @WorkerThread
    final boolean zzw(String str) {
        return zzd(str, zzak.zzia);
    }

    @WorkerThread
    final boolean zzx(String str) {
        return zzd(str, zzak.zzib);
    }

    @WorkerThread
    final boolean zzy(String str) {
        return zzd(str, zzak.zzic);
    }

    @WorkerThread
    final boolean zzz(String str) {
        return zzd(str, zzak.zzih);
    }
}
