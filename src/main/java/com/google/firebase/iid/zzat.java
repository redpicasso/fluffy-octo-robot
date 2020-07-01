package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zzat {
    private final SharedPreferences zza;
    private final Context zzb;
    private final zzq zzc;
    @GuardedBy("this")
    private final Map<String, zzs> zzd;

    public zzat(Context context) {
        this(context, new zzq());
    }

    private zzat(Context context, zzq zzq) {
        String str = "FirebaseInstanceId";
        this.zzd = new ArrayMap();
        this.zzb = context;
        this.zza = context.getSharedPreferences("com.google.android.gms.appid", 0);
        this.zzc = zzq;
        File file = new File(ContextCompat.getNoBackupFilesDir(this.zzb), "com.google.android.gms.appid-no-backup");
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !zzc()) {
                    Log.i(str, "App restored, clearing state");
                    zzb();
                    FirebaseInstanceId.getInstance().zze();
                }
            } catch (IOException e) {
                if (Log.isLoggable(str, 3)) {
                    String str2 = "Error creating file in no backup dir: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
            }
        }
    }

    public final synchronized String zza() {
        return this.zza.getString("topic_operation_queue", "");
    }

    public final synchronized void zza(String str) {
        this.zza.edit().putString("topic_operation_queue", str).apply();
    }

    private final synchronized boolean zzc() {
        return this.zza.getAll().isEmpty();
    }

    private static String zzc(String str, String str2, String str3) {
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 4) + String.valueOf(str2).length()) + String.valueOf(str3).length());
        stringBuilder.append(str);
        stringBuilder.append("|T|");
        stringBuilder.append(str2);
        stringBuilder.append("|");
        stringBuilder.append(str3);
        return stringBuilder.toString();
    }

    static String zza(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 3) + String.valueOf(str2).length());
        stringBuilder.append(str);
        stringBuilder.append("|S|");
        stringBuilder.append(str2);
        return stringBuilder.toString();
    }

    public final synchronized void zzb() {
        this.zzd.clear();
        zzq.zza(this.zzb);
        this.zza.edit().clear().commit();
    }

    public final synchronized zzas zza(String str, String str2, String str3) {
        return zzas.zza(this.zza.getString(zzc(str, str2, str3), null));
    }

    public final synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        str4 = zzas.zza(str4, str5, System.currentTimeMillis());
        if (str4 != null) {
            Editor edit = this.zza.edit();
            edit.putString(zzc(str, str2, str3), str4);
            edit.commit();
        }
    }

    public final synchronized void zzb(String str, String str2, String str3) {
        str = zzc(str, str2, str3);
        Editor edit = this.zza.edit();
        edit.remove(str);
        edit.commit();
    }

    public final synchronized zzs zzb(String str) {
        zzs zzs;
        zzs = (zzs) this.zzd.get(str);
        if (zzs != null) {
            return zzs;
        }
        zzs = this.zzc.zza(this.zzb, str);
        try {
        } catch (zzt unused) {
            Log.w("FirebaseInstanceId", "Stored data is corrupt, generating new identity");
            FirebaseInstanceId.getInstance().zze();
            zzs = this.zzc.zzb(this.zzb, str);
        }
        this.zzd.put(str, zzs);
        return zzs;
    }

    public final synchronized void zzc(String str) {
        str = String.valueOf(str).concat("|T|");
        Editor edit = this.zza.edit();
        for (String str2 : this.zza.getAll().keySet()) {
            if (str2.startsWith(str)) {
                edit.remove(str2);
            }
        }
        edit.commit();
    }
}
