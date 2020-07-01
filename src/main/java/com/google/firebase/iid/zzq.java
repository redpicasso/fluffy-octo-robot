package com.google.firebase.iid;

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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zzq {
    zzq() {
    }

    @WorkerThread
    final zzs zza(Context context, String str) throws zzt {
        zzs zzc = zzc(context, str);
        if (zzc != null) {
            return zzc;
        }
        return zzb(context, str);
    }

    @WorkerThread
    final zzs zzb(Context context, String str) {
        zzs zzs = new zzs(zzai.zza(zzb.zza().getPublic()), System.currentTimeMillis());
        zzs zza = zza(context, str, zzs, true);
        String str2 = "FirebaseInstanceId";
        if (zza == null || zza.equals(zzs)) {
            if (Log.isLoggable(str2, 3)) {
                Log.d(str2, "Generated new key");
            }
            zza(context, str, zzs);
            return zzs;
        }
        if (Log.isLoggable(str2, 3)) {
            Log.d(str2, "Loaded key after generating new one, using loaded one");
        }
        return zza;
    }

    static void zza(Context context) {
        for (File file : zzb(context).listFiles()) {
            if (file.getName().startsWith("com.google.InstanceId")) {
                file.delete();
            }
        }
    }

    @Nullable
    private final zzs zzc(Context context, String str) throws zzt {
        zzt e;
        try {
            zzs zzd = zzd(context, str);
            if (zzd != null) {
                zza(context, str, zzd);
                return zzd;
            }
            e = null;
            try {
                zzs zza = zza(context.getSharedPreferences("com.google.android.gms.appid", 0), str);
                if (zza != null) {
                    zza(context, str, zza, false);
                    return zza;
                }
            } catch (zzt e2) {
                e = e2;
            }
            if (e == null) {
                return null;
            }
            throw e;
        } catch (zzt e3) {
            e = e3;
        }
    }

    private static PublicKey zza(String str) throws zzt {
        Exception e;
        String valueOf;
        StringBuilder stringBuilder;
        try {
            try {
                return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str, 8)));
            } catch (InvalidKeySpecException e2) {
                e = e2;
                valueOf = String.valueOf(e);
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 19);
                stringBuilder.append("Invalid key stored ");
                stringBuilder.append(valueOf);
                Log.w("FirebaseInstanceId", stringBuilder.toString());
                throw new zzt(e);
            } catch (NoSuchAlgorithmException e3) {
                e = e3;
                valueOf = String.valueOf(e);
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 19);
                stringBuilder.append("Invalid key stored ");
                stringBuilder.append(valueOf);
                Log.w("FirebaseInstanceId", stringBuilder.toString());
                throw new zzt(e);
            }
        } catch (Exception e4) {
            throw new zzt(e4);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x001d  */
    @androidx.annotation.Nullable
    private final com.google.firebase.iid.zzs zzd(android.content.Context r4, java.lang.String r5) throws com.google.firebase.iid.zzt {
        /*
        r3 = this;
        r4 = zze(r4, r5);
        r5 = r4.exists();
        if (r5 != 0) goto L_0x000c;
    L_0x000a:
        r4 = 0;
        return r4;
    L_0x000c:
        r4 = r3.zza(r4);	 Catch:{ zzt -> 0x0013, IOException -> 0x0011 }
        return r4;
    L_0x0011:
        r5 = move-exception;
        goto L_0x0014;
    L_0x0013:
        r5 = move-exception;
    L_0x0014:
        r0 = 3;
        r1 = "FirebaseInstanceId";
        r0 = android.util.Log.isLoggable(r1, r0);
        if (r0 == 0) goto L_0x003f;
    L_0x001d:
        r5 = java.lang.String.valueOf(r5);
        r0 = java.lang.String.valueOf(r5);
        r0 = r0.length();
        r0 = r0 + 39;
        r2 = new java.lang.StringBuilder;
        r2.<init>(r0);
        r0 = "Failed to read ID from file, retrying: ";
        r2.append(r0);
        r2.append(r5);
        r5 = r2.toString();
        android.util.Log.d(r1, r5);
    L_0x003f:
        r4 = r3.zza(r4);	 Catch:{ IOException -> 0x0044 }
        return r4;
    L_0x0044:
        r4 = move-exception;
        r5 = java.lang.String.valueOf(r4);
        r0 = java.lang.String.valueOf(r5);
        r0 = r0.length();
        r0 = r0 + 45;
        r2 = new java.lang.StringBuilder;
        r2.<init>(r0);
        r0 = "IID file exists, but failed to read from it: ";
        r2.append(r0);
        r2.append(r5);
        r5 = r2.toString();
        android.util.Log.w(r1, r5);
        r5 = new com.google.firebase.iid.zzt;
        r5.<init>(r4);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzq.zzd(android.content.Context, java.lang.String):com.google.firebase.iid.zzs");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0092 A:{SYNTHETIC, Splitter: B:30:0x0092} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0064 A:{Catch:{ all -> 0x0099, all -> 0x009e }} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0092 A:{SYNTHETIC, Splitter: B:30:0x0092} */
    @androidx.annotation.Nullable
    private final com.google.firebase.iid.zzs zza(android.content.Context r10, java.lang.String r11, com.google.firebase.iid.zzs r12, boolean r13) {
        /*
        r9 = this;
        r0 = 3;
        r1 = "FirebaseInstanceId";
        r2 = android.util.Log.isLoggable(r1, r0);
        if (r2 == 0) goto L_0x000e;
    L_0x0009:
        r2 = "Writing ID to properties file";
        android.util.Log.d(r1, r2);
    L_0x000e:
        r2 = new java.util.Properties;
        r2.<init>();
        r3 = r12.zza();
        r4 = "id";
        r2.setProperty(r4, r3);
        r3 = r12.zzb;
        r3 = java.lang.String.valueOf(r3);
        r4 = "cre";
        r2.setProperty(r4, r3);
        r10 = zze(r10, r11);
        r11 = 0;
        r10.createNewFile();	 Catch:{ IOException -> 0x00b5 }
        r3 = new java.io.RandomAccessFile;	 Catch:{ IOException -> 0x00b5 }
        r4 = "rw";
        r3.<init>(r10, r4);	 Catch:{ IOException -> 0x00b5 }
        r10 = r3.getChannel();	 Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
        r10.lock();	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r4 = 0;
        if (r13 == 0) goto L_0x0086;
    L_0x0043:
        r6 = r10.size();	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r13 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1));
        if (r13 <= 0) goto L_0x0086;
    L_0x004b:
        r10.position(r4);	 Catch:{ IOException -> 0x005d, zzt -> 0x005b }
        r12 = zza(r10);	 Catch:{ IOException -> 0x005d, zzt -> 0x005b }
        if (r10 == 0) goto L_0x0057;
    L_0x0054:
        zza(r11, r10);	 Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
    L_0x0057:
        zza(r11, r3);	 Catch:{ IOException -> 0x00b5 }
        return r12;
    L_0x005b:
        r13 = move-exception;
        goto L_0x005e;
    L_0x005d:
        r13 = move-exception;
    L_0x005e:
        r0 = android.util.Log.isLoggable(r1, r0);	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        if (r0 == 0) goto L_0x0086;
    L_0x0064:
        r13 = java.lang.String.valueOf(r13);	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r0 = java.lang.String.valueOf(r13);	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r0 = r0.length();	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r0 = r0 + 58;
        r6 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r6.<init>(r0);	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r0 = "Tried reading ID before writing new one, but failed with: ";
        r6.append(r0);	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r6.append(r13);	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r13 = r6.toString();	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        android.util.Log.d(r1, r13);	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
    L_0x0086:
        r10.truncate(r4);	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r13 = java.nio.channels.Channels.newOutputStream(r10);	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        r2.store(r13, r11);	 Catch:{ Throwable -> 0x009c, all -> 0x0099 }
        if (r10 == 0) goto L_0x0095;
    L_0x0092:
        zza(r11, r10);	 Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
    L_0x0095:
        zza(r11, r3);	 Catch:{ IOException -> 0x00b5 }
        return r12;
    L_0x0099:
        r12 = move-exception;
        r13 = r11;
        goto L_0x00a2;
    L_0x009c:
        r12 = move-exception;
        throw r12;	 Catch:{ all -> 0x009e }
    L_0x009e:
        r13 = move-exception;
        r8 = r13;
        r13 = r12;
        r12 = r8;
    L_0x00a2:
        if (r10 == 0) goto L_0x00a7;
    L_0x00a4:
        zza(r13, r10);	 Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
    L_0x00a7:
        throw r12;	 Catch:{ Throwable -> 0x00ab, all -> 0x00a8 }
    L_0x00a8:
        r10 = move-exception;
        r12 = r11;
        goto L_0x00b1;
    L_0x00ab:
        r10 = move-exception;
        throw r10;	 Catch:{ all -> 0x00ad }
    L_0x00ad:
        r12 = move-exception;
        r8 = r12;
        r12 = r10;
        r10 = r8;
    L_0x00b1:
        zza(r12, r3);	 Catch:{ IOException -> 0x00b5 }
        throw r10;	 Catch:{ IOException -> 0x00b5 }
    L_0x00b5:
        r10 = move-exception;
        r10 = java.lang.String.valueOf(r10);
        r12 = java.lang.String.valueOf(r10);
        r12 = r12.length();
        r12 = r12 + 21;
        r13 = new java.lang.StringBuilder;
        r13.<init>(r12);
        r12 = "Failed to write key: ";
        r13.append(r12);
        r13.append(r10);
        r10 = r13.toString();
        android.util.Log.w(r1, r10);
        return r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzq.zza(android.content.Context, java.lang.String, com.google.firebase.iid.zzs, boolean):com.google.firebase.iid.zzs");
    }

    private static File zzb(Context context) {
        File noBackupFilesDir = ContextCompat.getNoBackupFilesDir(context);
        if (noBackupFilesDir != null && noBackupFilesDir.isDirectory()) {
            return noBackupFilesDir;
        }
        Log.w("FirebaseInstanceId", "noBackupFilesDir doesn't exist, using regular files directory instead");
        return context.getFilesDir();
    }

    private static File zze(Context context, String str) {
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
        return new File(zzb(context), str);
    }

    /* JADX WARNING: Missing block: B:26:0x0036, code:
            zza(r10, r0);
     */
    private final com.google.firebase.iid.zzs zza(java.io.File r10) throws com.google.firebase.iid.zzt, java.io.IOException {
        /*
        r9 = this;
        r0 = new java.io.FileInputStream;
        r0.<init>(r10);
        r10 = 0;
        r7 = r0.getChannel();	 Catch:{ Throwable -> 0x0034 }
        r2 = 0;
        r4 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        r6 = 1;
        r1 = r7;
        r1.lock(r2, r4, r6);	 Catch:{ Throwable -> 0x0026, all -> 0x0023 }
        r1 = zza(r7);	 Catch:{ Throwable -> 0x0026, all -> 0x0023 }
        if (r7 == 0) goto L_0x001f;
    L_0x001c:
        zza(r10, r7);	 Catch:{ Throwable -> 0x0034 }
    L_0x001f:
        zza(r10, r0);
        return r1;
    L_0x0023:
        r1 = move-exception;
        r2 = r10;
        goto L_0x002c;
    L_0x0026:
        r1 = move-exception;
        throw r1;	 Catch:{ all -> 0x0028 }
    L_0x0028:
        r2 = move-exception;
        r8 = r2;
        r2 = r1;
        r1 = r8;
    L_0x002c:
        if (r7 == 0) goto L_0x0031;
    L_0x002e:
        zza(r2, r7);	 Catch:{ Throwable -> 0x0034 }
    L_0x0031:
        throw r1;	 Catch:{ Throwable -> 0x0034 }
    L_0x0032:
        r1 = move-exception;
        goto L_0x0036;
    L_0x0034:
        r10 = move-exception;
        throw r10;	 Catch:{ all -> 0x0032 }
    L_0x0036:
        zza(r10, r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzq.zza(java.io.File):com.google.firebase.iid.zzs");
    }

    private static zzs zza(FileChannel fileChannel) throws zzt, IOException {
        Properties properties = new Properties();
        properties.load(Channels.newInputStream(fileChannel));
        try {
            long parseLong = Long.parseLong(properties.getProperty("cre"));
            String property = properties.getProperty("id");
            if (property == null) {
                property = properties.getProperty("pub");
                if (property != null) {
                    property = zzai.zza(zza(property));
                } else {
                    throw new zzt("Invalid properties file");
                }
            }
            return new zzs(property, parseLong);
        } catch (Exception e) {
            throw new zzt(e);
        }
    }

    @Nullable
    private static zzs zza(SharedPreferences sharedPreferences, String str) throws zzt {
        long zzb = zzb(sharedPreferences, str);
        String string = sharedPreferences.getString(zzat.zza(str, "id"), null);
        if (string == null) {
            String string2 = sharedPreferences.getString(zzat.zza(str, "|P|"), null);
            if (string2 == null) {
                return null;
            }
            string = zzai.zza(zza(string2));
        }
        return new zzs(string, zzb);
    }

    private final void zza(Context context, String str, zzs zzs) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.google.android.gms.appid", 0);
        try {
            if (zzs.equals(zza(sharedPreferences, str))) {
            }
        } catch (zzt unused) {
            String str2 = "FirebaseInstanceId";
            if (Log.isLoggable(str2, 3)) {
                Log.d(str2, "Writing key to shared preferences");
            }
            Editor edit = sharedPreferences.edit();
            edit.putString(zzat.zza(str, "id"), zzs.zza());
            edit.putString(zzat.zza(str, "cre"), String.valueOf(zzs.zzb));
            edit.commit();
        }
    }

    private static long zzb(SharedPreferences sharedPreferences, String str) {
        String string = sharedPreferences.getString(zzat.zza(str, "cre"), null);
        if (string != null) {
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException unused) {
                return 0;
            }
        }
    }
}
