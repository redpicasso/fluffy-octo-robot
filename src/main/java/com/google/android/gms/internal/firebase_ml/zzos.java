package com.google.android.gms.internal.firebase_ml;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import java.io.File;

final class zzos implements zzoh {
    private final FirebaseApp zzapo;
    private final String zzaqg;

    zzos(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        this.zzapo = firebaseApp;
        this.zzaqg = str;
    }

    public final void zzlt() {
    }

    @Nullable
    public final File zza(File file) throws FirebaseMLException {
        File zzb = zzoq.zzb(this.zzapo, this.zzaqg, zzok.CUSTOM);
        File file2 = new File(zzb, String.valueOf(zzoq.zzd(zzb) + 1));
        String str = "RemoteModelFileManager";
        if (file.renameTo(file2)) {
            zzoq.zzaoz.d(str, "Rename to serving model successfully");
            file2.setExecutable(false);
            file2.setWritable(false);
            return file2;
        }
        zzoq.zzaoz.d(str, "Rename to serving model failed, remove the temp file.");
        if (!file.delete()) {
            GmsLogger zzlr = zzoq.zzaoz;
            String str2 = "Failed to delete the temp file: ";
            String valueOf = String.valueOf(file.getAbsolutePath());
            zzlr.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
        return null;
    }
}
