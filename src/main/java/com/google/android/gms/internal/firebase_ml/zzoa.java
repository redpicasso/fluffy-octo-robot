package com.google.android.gms.internal.firebase_ml;

import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.firebase.FirebaseApp;

public final class zzoa {
    private static final GmsLogger zzaoz = new GmsLogger("SharedPrefManager", "");

    public static synchronized boolean zzc(@NonNull FirebaseApp firebaseApp) {
        boolean z;
        synchronized (zzoa.class) {
            z = firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).getBoolean(String.format("logging_%s_%s", new Object[]{"vision", firebaseApp.getPersistenceKey()}), true);
        }
        return z;
    }

    public static synchronized void zza(@NonNull FirebaseApp firebaseApp, boolean z) {
        synchronized (zzoa.class) {
            firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).edit().putBoolean(String.format("logging_%s_%s", new Object[]{"vision", firebaseApp.getPersistenceKey()}), z).apply();
        }
    }

    public static synchronized boolean zzd(@NonNull FirebaseApp firebaseApp) {
        boolean z;
        synchronized (zzoa.class) {
            z = firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).getBoolean(String.format("logging_%s_%s", new Object[]{"model", firebaseApp.getPersistenceKey()}), true);
        }
        return z;
    }

    public static synchronized void zzb(@NonNull FirebaseApp firebaseApp, boolean z) {
        synchronized (zzoa.class) {
            firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).edit().putBoolean(String.format("logging_%s_%s", new Object[]{"model", firebaseApp.getPersistenceKey()}), z).apply();
        }
    }

    @Nullable
    public static synchronized Long zza(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        synchronized (zzoa.class) {
            long j = firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).getLong(String.format("downloading_model_id_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str}), -1);
            if (j < 0) {
                return null;
            }
            Long valueOf = Long.valueOf(j);
            return valueOf;
        }
    }

    @Nullable
    public static synchronized zzok zzb(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        zzok zzok;
        synchronized (zzoa.class) {
            String string = firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).getString(String.format("downloading_model_type_%s", new Object[]{str}), "");
            zzok = null;
            zzok = zzok.zzbx(string);
            try {
            } catch (IllegalArgumentException unused) {
                GmsLogger gmsLogger = zzaoz;
                String str2 = "SharedPrefManager";
                String str3 = "Invalid model type ";
                string = String.valueOf(string);
                gmsLogger.e(str2, string.length() != 0 ? str3.concat(string) : new String(str3));
            }
        }
        return zzok;
        return zzok;
    }

    @Nullable
    public static synchronized String zzc(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        String string;
        synchronized (zzoa.class) {
            string = firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).getString(String.format("downloading_model_hash_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str}), null);
        }
        return string;
    }

    @Nullable
    public static synchronized String zzd(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        String string;
        synchronized (zzoa.class) {
            string = firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).getString(String.format("current_model_hash_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str}), null);
        }
        return string;
    }

    @Nullable
    public static synchronized String zze(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        String string;
        synchronized (zzoa.class) {
            string = firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).getString(String.format("bad_hash_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str}), null);
        }
        return string;
    }

    @Nullable
    public static synchronized String zze(@NonNull FirebaseApp firebaseApp) {
        String string;
        synchronized (zzoa.class) {
            string = firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).getString("app_version", null);
        }
        return string;
    }

    public static synchronized long zzf(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        long j;
        synchronized (zzoa.class) {
            j = firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).getLong(String.format("downloading_begin_time_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str}), 0);
        }
        return j;
    }

    public static synchronized long zzg(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        long j;
        synchronized (zzoa.class) {
            j = firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).getLong(String.format("model_first_use_time_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str}), 0);
        }
        return j;
    }

    public static synchronized void zza(@NonNull FirebaseApp firebaseApp, @NonNull String str, long j) {
        synchronized (zzoa.class) {
            firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).edit().putLong(String.format("model_first_use_time_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str}), j).apply();
        }
    }

    public static synchronized void zzh(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        synchronized (zzoa.class) {
            String zzc = zzc(firebaseApp, str);
            firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).edit().remove(String.format("downloading_model_id_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str})).remove(String.format("downloading_model_hash_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str})).remove(String.format("downloading_model_type_%s", new Object[]{zzc})).remove(String.format("downloading_begin_time_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str})).remove(String.format("model_first_use_time_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str})).apply();
        }
    }

    public static synchronized void zzi(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        synchronized (zzoa.class) {
            firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).edit().remove(String.format("current_model_hash_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str})).apply();
        }
    }

    public static synchronized void zza(@NonNull FirebaseApp firebaseApp, @NonNull String str, @NonNull String str2) {
        synchronized (zzoa.class) {
            firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).edit().putString(String.format("current_model_hash_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str}), str2).apply();
        }
    }

    public static synchronized void zza(@NonNull FirebaseApp firebaseApp, long j, @NonNull zzop zzop) {
        synchronized (zzoa.class) {
            String modelName = zzop.getModelName();
            String zzmd = zzop.zzmd();
            zzok zzme = zzop.zzme();
            firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).edit().putString(String.format("downloading_model_hash_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), modelName}), zzmd).putLong(String.format("downloading_model_id_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), modelName}), j).putString(String.format("downloading_model_type_%s", new Object[]{zzmd}), zzme.name()).putLong(String.format("downloading_begin_time_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), modelName}), SystemClock.elapsedRealtime()).apply();
        }
    }

    public static synchronized void zza(@NonNull FirebaseApp firebaseApp, @NonNull String str, @NonNull String str2, @NonNull String str3) {
        synchronized (zzoa.class) {
            firebaseApp.getApplicationContext().getSharedPreferences("com.google.firebase.ml.internal", 0).edit().putString(String.format("bad_hash_%s_%s", new Object[]{firebaseApp.getPersistenceKey(), str}), str2).putString("app_version", str3).apply();
        }
    }
}
