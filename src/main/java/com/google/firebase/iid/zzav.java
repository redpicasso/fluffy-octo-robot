package com.google.firebase.iid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.iid.InstanceID;
import com.google.firebase.FirebaseApp;
import java.io.IOException;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zzav implements Runnable {
    private final long zza;
    private final WakeLock zzb = ((PowerManager) zza().getSystemService("power")).newWakeLock(1, "fiid-sync");
    private final FirebaseInstanceId zzc;
    private final zzax zzd;

    @VisibleForTesting
    zzav(FirebaseInstanceId firebaseInstanceId, zzai zzai, zzax zzax, long j) {
        this.zzc = firebaseInstanceId;
        this.zzd = zzax;
        this.zza = j;
        this.zzb.setReferenceCounted(false);
    }

    @SuppressLint({"Wakelock"})
    public final void run() {
        if (zzaq.zza().zza(zza())) {
            this.zzb.acquire();
        }
        try {
            this.zzc.zza(true);
            if (!this.zzc.zzf()) {
                this.zzc.zza(false);
                if (zzaq.zza().zza(zza())) {
                    this.zzb.release();
                }
            } else if (!zzaq.zza().zzb(zza()) || zzb()) {
                if (zzc() && this.zzd.zza(this.zzc)) {
                    this.zzc.zza(false);
                } else {
                    this.zzc.zza(this.zza);
                }
                if (zzaq.zza().zza(zza())) {
                    this.zzb.release();
                }
            } else {
                new zzau(this).zza();
                if (zzaq.zza().zza(zza())) {
                    this.zzb.release();
                }
            }
        } catch (IOException e) {
            String message = e.getMessage();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(message).length() + 93);
            stringBuilder.append("Topic sync or token retrieval failed on hard failure exceptions: ");
            stringBuilder.append(message);
            stringBuilder.append(". Won't retry the operation.");
            Log.e("FirebaseInstanceId", stringBuilder.toString());
            this.zzc.zza(false);
            if (zzaq.zza().zza(zza())) {
                this.zzb.release();
            }
        } catch (Throwable th) {
            if (zzaq.zza().zza(zza())) {
                this.zzb.release();
            }
            throw th;
        }
    }

    @VisibleForTesting
    private final boolean zzc() throws IOException {
        String str = "FirebaseInstanceId";
        zzas zzb = this.zzc.zzb();
        if (!this.zzc.zza(zzb)) {
            return true;
        }
        String str2;
        try {
            String zzc = this.zzc.zzc();
            if (zzc == null) {
                Log.e(str, "Token retrieval failed: null");
                return false;
            }
            if (Log.isLoggable(str, 3)) {
                Log.d(str, "Token successfully retrieved");
            }
            if ((zzb == null || !(zzb == null || zzc.equals(zzb.zza))) && FirebaseApp.DEFAULT_APP_NAME.equals(this.zzc.zza().getName())) {
                if (Log.isLoggable(str, 3)) {
                    str2 = "Invoking onNewToken for app: ";
                    String valueOf = String.valueOf(this.zzc.zza().getName());
                    Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
                Parcelable intent = new Intent("com.google.firebase.messaging.NEW_TOKEN");
                intent.putExtra("token", zzc);
                Context zza = zza();
                Intent intent2 = new Intent(zza, FirebaseInstanceIdReceiver.class);
                intent2.setAction("com.google.firebase.MESSAGING_EVENT");
                intent2.putExtra("wrapped_intent", intent);
                zza.sendBroadcast(intent2);
            }
            return true;
        } catch (IOException e) {
            if (!InstanceID.ERROR_SERVICE_NOT_AVAILABLE.equals(e.getMessage())) {
                if (!"INTERNAL_SERVER_ERROR".equals(e.getMessage())) {
                    if (e.getMessage() == null) {
                        str2 = e.getMessage();
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 52);
                        stringBuilder.append("Token retrieval failed: ");
                        stringBuilder.append(str2);
                        stringBuilder.append(". Will retry token retrieval");
                        Log.e(str, stringBuilder.toString());
                        return false;
                    }
                    throw e;
                }
            }
            Log.e(str, "Token retrieval failed without exception message. Will retry token retrieval");
            return false;
        } catch (SecurityException unused) {
            Log.e(str, "Token retrieval failed with SecurityException. Will retry token retrieval");
            return false;
        }
    }

    final Context zza() {
        return this.zzc.zza().getApplicationContext();
    }

    final boolean zzb() {
        ConnectivityManager connectivityManager = (ConnectivityManager) zza().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
