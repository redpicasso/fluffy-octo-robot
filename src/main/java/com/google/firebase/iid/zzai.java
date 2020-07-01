package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.firebase.FirebaseApp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
public final class zzai {
    private final Context zza;
    @GuardedBy("this")
    private String zzb;
    @GuardedBy("this")
    private String zzc;
    @GuardedBy("this")
    private int zzd;
    @GuardedBy("this")
    private int zze = 0;

    public zzai(Context context) {
        this.zza = context;
    }

    public final synchronized int zza() {
        if (this.zze != 0) {
            return this.zze;
        }
        PackageManager packageManager = this.zza.getPackageManager();
        if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", "com.google.android.gms") == -1) {
            Log.e("FirebaseInstanceId", "Google Play services missing or without correct permission.");
            return 0;
        }
        Intent intent;
        if (!PlatformVersion.isAtLeastO()) {
            intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.setPackage("com.google.android.gms");
            List queryIntentServices = packageManager.queryIntentServices(intent, 0);
            if (queryIntentServices != null && queryIntentServices.size() > 0) {
                this.zze = 1;
                return this.zze;
            }
        }
        intent = new Intent("com.google.iid.TOKEN_REQUEST");
        intent.setPackage("com.google.android.gms");
        List queryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers == null || queryBroadcastReceivers.size() <= 0) {
            Log.w("FirebaseInstanceId", "Failed to resolve IID implementation package, falling back");
            if (PlatformVersion.isAtLeastO()) {
                this.zze = 2;
            } else {
                this.zze = 1;
            }
            return this.zze;
        }
        this.zze = 2;
        return this.zze;
    }

    public static String zza(FirebaseApp firebaseApp) {
        String gcmSenderId = firebaseApp.getOptions().getGcmSenderId();
        if (gcmSenderId != null) {
            return gcmSenderId;
        }
        String applicationId = firebaseApp.getOptions().getApplicationId();
        if (!applicationId.startsWith("1:")) {
            return applicationId;
        }
        String[] split = applicationId.split(":");
        if (split.length < 2) {
            return null;
        }
        applicationId = split[1];
        if (applicationId.isEmpty()) {
            return null;
        }
        return applicationId;
    }

    public static String zza(PublicKey publicKey) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(publicKey.getEncoded());
            digest[0] = (byte) ((digest[0] & 15) + 112);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException unused) {
            Log.w("FirebaseInstanceId", "Unexpected error, device missing required algorithms");
            return null;
        }
    }

    public final synchronized String zzb() {
        if (this.zzb == null) {
            zze();
        }
        return this.zzb;
    }

    public final synchronized String zzc() {
        if (this.zzc == null) {
            zze();
        }
        return this.zzc;
    }

    public final synchronized int zzd() {
        if (this.zzd == 0) {
            PackageInfo zza = zza("com.google.android.gms");
            if (zza != null) {
                this.zzd = zza.versionCode;
            }
        }
        return this.zzd;
    }

    private final synchronized void zze() {
        PackageInfo zza = zza(this.zza.getPackageName());
        if (zza != null) {
            this.zzb = Integer.toString(zza.versionCode);
            this.zzc = zza.versionName;
        }
    }

    private final PackageInfo zza(String str) {
        try {
            return this.zza.getPackageManager().getPackageInfo(str, 0);
        } catch (NameNotFoundException e) {
            str = String.valueOf(e);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 23);
            stringBuilder.append("Failed to find package ");
            stringBuilder.append(str);
            Log.w("FirebaseInstanceId", stringBuilder.toString());
            return null;
        }
    }
}
