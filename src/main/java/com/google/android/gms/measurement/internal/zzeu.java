package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzf;
import java.util.List;

public final class zzeu {
    final zzfj zzj;

    zzeu(zzfj zzfj) {
        this.zzj = zzfj;
    }

    @WorkerThread
    protected final void zzat(String str) {
        if (str == null || str.isEmpty()) {
            this.zzj.zzab().zzgq().zzao("Install Referrer Reporter was called with invalid app package name");
            return;
        }
        this.zzj.zzaa().zzo();
        if (zzhn()) {
            this.zzj.zzab().zzgq().zzao("Install Referrer Reporter is initializing");
            ServiceConnection zzex = new zzex(this, str);
            this.zzj.zzaa().zzo();
            Intent intent = new Intent("com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE");
            String str2 = "com.android.vending";
            intent.setComponent(new ComponentName(str2, "com.google.android.finsky.externalreferrer.GetInstallReferrerService"));
            PackageManager packageManager = this.zzj.getContext().getPackageManager();
            if (packageManager == null) {
                this.zzj.zzab().zzgn().zzao("Failed to obtain Package Manager to verify binding conditions");
                return;
            }
            List queryIntentServices = packageManager.queryIntentServices(intent, 0);
            if (queryIntentServices == null || queryIntentServices.isEmpty()) {
                this.zzj.zzab().zzgq().zzao("Play Service for fetching Install Referrer is unavailable on device");
                return;
            }
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentServices.get(0);
            if (resolveInfo.serviceInfo != null) {
                String str3 = resolveInfo.serviceInfo.packageName;
                if (resolveInfo.serviceInfo.name != null && str2.equals(str3) && zzhn()) {
                    try {
                        this.zzj.zzab().zzgq().zza("Install Referrer Service is", ConnectionTracker.getInstance().bindService(this.zzj.getContext(), new Intent(intent), zzex, 1) ? "available" : "not available");
                        return;
                    } catch (Exception e) {
                        this.zzj.zzab().zzgk().zza("Exception occurred while binding to Install Referrer Service", e.getMessage());
                        return;
                    }
                }
                this.zzj.zzab().zzgq().zzao("Play Store missing or incompatible. Version 8.3.73 or later required");
            }
            return;
        }
        this.zzj.zzab().zzgq().zzao("Install Referrer Reporter is not available");
    }

    @VisibleForTesting
    private final boolean zzhn() {
        boolean z = false;
        try {
            PackageManagerWrapper packageManager = Wrappers.packageManager(this.zzj.getContext());
            if (packageManager == null) {
                this.zzj.zzab().zzgq().zzao("Failed to retrieve Package Manager to check Play Store compatibility");
                return false;
            }
            if (packageManager.getPackageInfo("com.android.vending", 128).versionCode >= 80837300) {
                z = true;
            }
            return z;
        } catch (Exception e) {
            this.zzj.zzab().zzgq().zza("Failed to retrieve Play Store version", e);
            return false;
        }
    }

    @WorkerThread
    @Nullable
    @VisibleForTesting
    final Bundle zza(String str, zzf zzf) {
        this.zzj.zzaa().zzo();
        if (zzf == null) {
            this.zzj.zzab().zzgn().zzao("Attempting to use Install Referrer Service while it is not initialized");
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("package_name", str);
        try {
            Bundle zza = zzf.zza(bundle);
            if (zza != null) {
                return zza;
            }
            this.zzj.zzab().zzgk().zzao("Install Referrer Service returned a null response");
            return null;
        } catch (Exception e) {
            this.zzj.zzab().zzgk().zza("Exception occurred while retrieving the Install Referrer", e.getMessage());
            return null;
        }
    }
}
