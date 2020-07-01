package com.google.firebase.iid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.util.Log;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
public final class zzaq {
    private static zzaq zza;
    @GuardedBy("this")
    @Nullable
    private String zzb = null;
    private Boolean zzc = null;
    private Boolean zzd = null;
    private final Queue<Intent> zze = new ArrayDeque();

    public static synchronized zzaq zza() {
        zzaq zzaq;
        synchronized (zzaq.class) {
            if (zza == null) {
                zza = new zzaq();
            }
            zzaq = zza;
        }
        return zzaq;
    }

    private zzaq() {
    }

    @MainThread
    public final Intent zzb() {
        return (Intent) this.zze.poll();
    }

    @MainThread
    public final int zza(Context context, Intent intent) {
        String str = "FirebaseInstanceId";
        if (Log.isLoggable(str, 3)) {
            Log.d(str, "Starting service");
        }
        this.zze.offer(intent);
        intent = new Intent("com.google.firebase.MESSAGING_EVENT");
        intent.setPackage(context.getPackageName());
        return zzb(context, intent);
    }

    private final int zzb(Context context, Intent intent) {
        String zzc = zzc(context, intent);
        String str = "FirebaseInstanceId";
        if (zzc != null) {
            if (Log.isLoggable(str, 3)) {
                String str2 = "Restricting intent to a specific service: ";
                String valueOf = String.valueOf(zzc);
                Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
            intent.setClassName(context.getPackageName(), zzc);
        }
        try {
            ComponentName zza;
            if (zza(context)) {
                zza = zzaw.zza(context, intent);
            } else {
                zza = context.startService(intent);
                Log.d(str, "Missing wake lock permission, service start may be delayed");
            }
            if (zza != null) {
                return -1;
            }
            Log.e(str, "Error while delivering the message: ServiceIntent not found.");
            return 404;
        } catch (Throwable e) {
            Log.e(str, "Error while delivering the message to the serviceIntent", e);
            return 401;
        } catch (IllegalStateException e2) {
            String valueOf2 = String.valueOf(e2);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf2).length() + 45);
            stringBuilder.append("Failed to start service while in background: ");
            stringBuilder.append(valueOf2);
            Log.e(str, stringBuilder.toString());
            return 402;
        }
    }

    @Nullable
    private final synchronized String zzc(Context context, Intent intent) {
        if (this.zzb != null) {
            return this.zzb;
        }
        ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
        if (resolveService == null || resolveService.serviceInfo == null) {
            Log.e("FirebaseInstanceId", "Failed to resolve target intent service, skipping classname enforcement");
            return null;
        }
        ServiceInfo serviceInfo = resolveService.serviceInfo;
        String str;
        if (!context.getPackageName().equals(serviceInfo.packageName) || serviceInfo.name == null) {
            String str2 = serviceInfo.packageName;
            str = serviceInfo.name;
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str2).length() + 94) + String.valueOf(str).length());
            stringBuilder.append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ");
            stringBuilder.append(str2);
            stringBuilder.append("/");
            stringBuilder.append(str);
            Log.e("FirebaseInstanceId", stringBuilder.toString());
            return null;
        }
        if (serviceInfo.name.startsWith(".")) {
            String valueOf = String.valueOf(context.getPackageName());
            str = String.valueOf(serviceInfo.name);
            this.zzb = str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
        } else {
            this.zzb = serviceInfo.name;
        }
        return this.zzb;
    }

    final boolean zza(Context context) {
        if (this.zzc == null) {
            this.zzc = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0);
        }
        if (!this.zzc.booleanValue()) {
            String str = "FirebaseInstanceId";
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "Missing Permission: android.permission.WAKE_LOCK this should normally be included by the manifest merger, but may needed to be manually added to your manifest");
            }
        }
        return this.zzc.booleanValue();
    }

    final boolean zzb(Context context) {
        if (this.zzd == null) {
            this.zzd = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0);
        }
        if (!this.zzc.booleanValue()) {
            String str = "FirebaseInstanceId";
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "Missing Permission: android.permission.ACCESS_NETWORK_STATE this should normally be included by the manifest merger, but may needed to be manually added to your manifest");
            }
        }
        return this.zzd.booleanValue();
    }
}
