package com.google.android.gms.internal.firebase_ml;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.Key;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

public final class zzob implements zzoh {
    private static final GmsLogger zzaoz = new GmsLogger("AutoMLModelFileManager", "");
    private final FirebaseApp zzapo;
    private final String zzaqg;

    zzob(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        this.zzapo = firebaseApp;
        this.zzaqg = str;
    }

    public final void zzlt() throws FirebaseMLException {
        File zzj = zzj(this.zzapo, this.zzaqg);
        if (!zzoq.zze(zzj.getParentFile())) {
            GmsLogger gmsLogger = zzaoz;
            String str = "Failed to delete the temp labels file directory: ";
            String valueOf = String.valueOf(zzj.getParentFile().getAbsolutePath());
            gmsLogger.e("AutoMLModelFileManager", valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    @Nullable
    public final File zza(File file) throws FirebaseMLException {
        File zzb = zzoq.zzb(this.zzapo, this.zzaqg, zzok.AUTOML);
        zzb = new File(new File(zzb, String.valueOf(zzoq.zzd(zzb) + 1)), "model.tflite");
        File parentFile = zzb.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        File zzj = zzj(this.zzapo, this.zzaqg);
        File file2 = new File(parentFile, "labels.txt");
        String str = "AutoMLModelFileManager";
        String str2;
        if (file.renameTo(zzb) && zzj.renameTo(file2)) {
            zzaoz.d(str, "Rename to serving model successfully");
            zzb.setExecutable(false);
            zzb.setWritable(false);
            file2.setExecutable(false);
            file2.setWritable(false);
            try {
                zza(new File(parentFile, "manifest.json"), new zzoc(String.format("{\n\t\"modelType\": \"%s\",\n\t\"modelFile\": \"%s\",\n\t\"labelsFile\": \"%s\"\n}", new Object[]{"IMAGE_LABELING", str2, r6})));
                return zzb.getParentFile();
            } catch (Throwable e) {
                str2 = "Failed to write manifest json for the AutoML model: ";
                String valueOf = String.valueOf(this.zzaqg);
                throw new FirebaseMLException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), 13, e);
            }
        }
        zzaoz.d(str, "Rename to serving model failed, remove the temp file.");
        if (!file.delete()) {
            GmsLogger gmsLogger = zzaoz;
            str2 = "Failed to delete the temp model file: ";
            String valueOf2 = String.valueOf(file.getAbsolutePath());
            gmsLogger.d(str, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
        }
        if (!zzj.delete()) {
            GmsLogger gmsLogger2 = zzaoz;
            String str3 = "Failed to delete the temp labels file: ";
            str2 = String.valueOf(zzj.getAbsolutePath());
            gmsLogger2.d(str, str2.length() != 0 ? str3.concat(str2) : new String(str3));
        }
        return null;
    }

    static void zza(@NonNull FirebaseApp firebaseApp, @NonNull String str, @NonNull List<String> list) throws FirebaseMLException {
        try {
            zza(zzj(firebaseApp, str), new zzod(list));
        } catch (Throwable e) {
            String str2 = "Failed to write labels file for the AutoML model: ";
            str = String.valueOf(str);
            throw new FirebaseMLException(str.length() != 0 ? str2.concat(str) : new String(str2), 13, e);
        }
    }

    @VisibleForTesting
    private static File zzj(@NonNull FirebaseApp firebaseApp, @NonNull String str) throws FirebaseMLException {
        File zza = zzoq.zza(firebaseApp, str, zzok.AUTOML);
        String str2;
        if (zza.exists() && zza.isFile() && !zza.delete()) {
            str2 = "Failed to delete the temp labels file: ";
            String valueOf = String.valueOf(zza.getAbsolutePath());
            throw new FirebaseMLException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), 13);
        }
        if (!zza.exists()) {
            GmsLogger gmsLogger = zzaoz;
            str2 = "Temp labels folder does not exist, creating one: ";
            String valueOf2 = String.valueOf(zza.getAbsolutePath());
            gmsLogger.d("AutoMLModelFileManager", valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
            if (!zza.mkdirs()) {
                throw new FirebaseMLException("Failed to create a directory to hold the AutoML model's labels file.", 13);
            }
        }
        return new File(zza, "labels.txt");
    }

    private static void zza(File file, zzoe zzoe) throws IOException {
        Throwable th;
        Throwable th2;
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName(Key.STRING_CHARSET_NAME)));
        try {
            zzoe.zza(bufferedWriter);
            bufferedWriter.close();
            return;
        } catch (Throwable th3) {
            th = th3;
        }
        throw th;
        if (th2 != null) {
            try {
                bufferedWriter.close();
            } catch (Throwable th4) {
                zzlx.zza(th2, th4);
            }
        } else {
            bufferedWriter.close();
        }
        throw th;
    }
}
