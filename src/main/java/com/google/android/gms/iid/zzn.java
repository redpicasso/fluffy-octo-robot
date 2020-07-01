package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.load.Key;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

final class zzn {
    zzn() {
    }

    @WorkerThread
    final zzo zze(Context context, String str) throws zzp {
        zzo zzh = zzh(context, str);
        if (zzh != null) {
            return zzh;
        }
        return zzf(context, str);
    }

    @WorkerThread
    final zzo zzf(Context context, String str) {
        String str2 = "InstanceID";
        zzo zzo = new zzo(zzd.zzl(), System.currentTimeMillis());
        try {
            zzo zzh = zzh(context, str);
            if (zzh != null) {
                if (Log.isLoggable(str2, 3)) {
                    Log.d(str2, "Loaded key after generating new one, using loaded one");
                }
                return zzh;
            }
        } catch (zzp unused) {
            if (Log.isLoggable(str2, 3)) {
                Log.d(str2, "Generated new key");
            }
            zzd(context, str, zzo);
            zze(context, str, zzo);
            return zzo;
        }
    }

    static void zzg(Context context, String str) {
        File zzj = zzj(context, str);
        if (zzj.exists()) {
            zzj.delete();
        }
    }

    static void zzi(Context context) {
        for (File file : zzj(context).listFiles()) {
            if (file.getName().startsWith("com.google.InstanceId")) {
                file.delete();
            }
        }
    }

    @Nullable
    private final zzo zzh(Context context, String str) throws zzp {
        zzp e;
        try {
            zzo zzi = zzi(context, str);
            if (zzi != null) {
                zze(context, str, zzi);
                return zzi;
            }
            e = null;
            try {
                zzo zzd = zzd(context.getSharedPreferences("com.google.android.gms.appid", 0), str);
                if (zzd != null) {
                    zzd(context, str, zzd);
                    return zzd;
                }
            } catch (zzp e2) {
                e = e2;
            }
            if (e == null) {
                return null;
            }
            throw e;
        } catch (zzp e3) {
            e = e3;
        }
    }

    private static KeyPair zzg(String str, String str2) throws zzp {
        Exception e;
        StringBuilder stringBuilder;
        try {
            byte[] decode = Base64.decode(str, 8);
            byte[] decode2 = Base64.decode(str2, 8);
            try {
                KeyFactory instance = KeyFactory.getInstance("RSA");
                return new KeyPair(instance.generatePublic(new X509EncodedKeySpec(decode)), instance.generatePrivate(new PKCS8EncodedKeySpec(decode2)));
            } catch (InvalidKeySpecException e2) {
                e = e2;
                str2 = String.valueOf(e);
                stringBuilder = new StringBuilder(String.valueOf(str2).length() + 19);
                stringBuilder.append("Invalid key stored ");
                stringBuilder.append(str2);
                Log.w("InstanceID", stringBuilder.toString());
                throw new zzp(e);
            } catch (NoSuchAlgorithmException e3) {
                e = e3;
                str2 = String.valueOf(e);
                stringBuilder = new StringBuilder(String.valueOf(str2).length() + 19);
                stringBuilder.append("Invalid key stored ");
                stringBuilder.append(str2);
                Log.w("InstanceID", stringBuilder.toString());
                throw new zzp(e);
            }
        } catch (Exception e4) {
            throw new zzp(e4);
        }
    }

    @Nullable
    private final zzo zzi(Context context, String str) throws zzp {
        StringBuilder stringBuilder;
        File zzj = zzj(context, str);
        if (!zzj.exists()) {
            return null;
        }
        try {
            zzj = zzd(zzj);
            return zzj;
        } catch (IOException e) {
            String str2 = "InstanceID";
            if (Log.isLoggable(str2, 3)) {
                str = String.valueOf(e);
                stringBuilder = new StringBuilder(String.valueOf(str).length() + 40);
                stringBuilder.append("Failed to read key from file, retrying: ");
                stringBuilder.append(str);
                Log.d(str2, stringBuilder.toString());
            }
            try {
                return zzd(zzj);
            } catch (Exception e2) {
                str = String.valueOf(e2);
                stringBuilder = new StringBuilder(String.valueOf(str).length() + 45);
                stringBuilder.append("IID file exists, but failed to read from it: ");
                stringBuilder.append(str);
                Log.w(str2, stringBuilder.toString());
                throw new zzp(e2);
            }
        }
    }

    private static void zzd(Context context, String str, zzo zzo) {
        String str2 = "InstanceID";
        FileOutputStream fileOutputStream;
        try {
            if (Log.isLoggable(str2, 3)) {
                Log.d(str2, "Writing key to properties file");
            }
            File zzj = zzj(context, str);
            zzj.createNewFile();
            Properties properties = new Properties();
            properties.setProperty("pub", zzo.zzq());
            properties.setProperty("pri", zzo.zzr());
            properties.setProperty("cre", String.valueOf(zzo.zzcc));
            fileOutputStream = new FileOutputStream(zzj);
            properties.store(fileOutputStream, null);
            zzd(null, fileOutputStream);
        } catch (IOException e) {
            String valueOf = String.valueOf(e);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 21);
            stringBuilder.append("Failed to write key: ");
            stringBuilder.append(valueOf);
            Log.w(str2, stringBuilder.toString());
        } catch (Throwable th) {
            zzd(r4, fileOutputStream);
        }
    }

    private static File zzj(Context context) {
        File noBackupFilesDir = ContextCompat.getNoBackupFilesDir(context);
        if (noBackupFilesDir != null && noBackupFilesDir.isDirectory()) {
            return noBackupFilesDir;
        }
        Log.w("InstanceID", "noBackupFilesDir doesn't exist, using regular files directory instead");
        return context.getFilesDir();
    }

    private static File zzj(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            str = "com.google.InstanceId.properties";
        } else {
            try {
                str = Base64.encodeToString(str.getBytes(Key.STRING_CHARSET_NAME), 11);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 33);
                stringBuilder.append("com.google.InstanceId_");
                stringBuilder.append(str);
                stringBuilder.append(".properties");
                str = stringBuilder.toString();
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }
        return new File(zzj(context), str);
    }

    /* JADX WARNING: Missing block: B:22:0x0045, code:
            zzd(r5, r0);
     */
    @androidx.annotation.Nullable
    private static com.google.android.gms.iid.zzo zzd(java.io.File r5) throws com.google.android.gms.iid.zzp, java.io.IOException {
        /*
        r0 = new java.io.FileInputStream;
        r0.<init>(r5);
        r5 = 0;
        r1 = new java.util.Properties;	 Catch:{ Throwable -> 0x0043 }
        r1.<init>();	 Catch:{ Throwable -> 0x0043 }
        r1.load(r0);	 Catch:{ Throwable -> 0x0043 }
        r2 = "pub";
        r2 = r1.getProperty(r2);	 Catch:{ Throwable -> 0x0043 }
        r3 = "pri";
        r3 = r1.getProperty(r3);	 Catch:{ Throwable -> 0x0043 }
        if (r2 == 0) goto L_0x003d;
    L_0x001c:
        if (r3 != 0) goto L_0x001f;
    L_0x001e:
        goto L_0x003d;
    L_0x001f:
        r2 = zzg(r2, r3);	 Catch:{ Throwable -> 0x0043 }
        r3 = "cre";
        r1 = r1.getProperty(r3);	 Catch:{ NumberFormatException -> 0x0036 }
        r3 = java.lang.Long.parseLong(r1);	 Catch:{ NumberFormatException -> 0x0036 }
        r1 = new com.google.android.gms.iid.zzo;	 Catch:{ Throwable -> 0x0043 }
        r1.<init>(r2, r3);	 Catch:{ Throwable -> 0x0043 }
        zzd(r5, r0);
        return r1;
    L_0x0036:
        r1 = move-exception;
        r2 = new com.google.android.gms.iid.zzp;	 Catch:{ Throwable -> 0x0043 }
        r2.<init>(r1);	 Catch:{ Throwable -> 0x0043 }
        throw r2;	 Catch:{ Throwable -> 0x0043 }
    L_0x003d:
        zzd(r5, r0);
        return r5;
    L_0x0041:
        r1 = move-exception;
        goto L_0x0045;
    L_0x0043:
        r5 = move-exception;
        throw r5;	 Catch:{ all -> 0x0041 }
    L_0x0045:
        zzd(r5, r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzn.zzd(java.io.File):com.google.android.gms.iid.zzo");
    }

    @Nullable
    private static zzo zzd(SharedPreferences sharedPreferences, String str) throws zzp {
        String string = sharedPreferences.getString(zzak.zzh(str, "|P|"), null);
        String string2 = sharedPreferences.getString(zzak.zzh(str, "|K|"), null);
        if (string == null || string2 == null) {
            return null;
        }
        return new zzo(zzg(string, string2), zze(sharedPreferences, str));
    }

    private final void zze(Context context, String str, zzo zzo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.google.android.gms.appid", 0);
        try {
            if (zzo.equals(zzd(sharedPreferences, str))) {
            }
        } catch (zzp unused) {
            String str2 = "InstanceID";
            if (Log.isLoggable(str2, 3)) {
                Log.d(str2, "Writing key to shared preferences");
            }
            Editor edit = sharedPreferences.edit();
            edit.putString(zzak.zzh(str, "|P|"), zzo.zzq());
            edit.putString(zzak.zzh(str, "|K|"), zzo.zzr());
            edit.putString(zzak.zzh(str, "cre"), String.valueOf(zzo.zzcc));
            edit.commit();
        }
    }

    private static long zze(SharedPreferences sharedPreferences, String str) {
        String string = sharedPreferences.getString(zzak.zzh(str, "cre"), null);
        if (string != null) {
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException unused) {
                return 0;
            }
        }
    }
}
