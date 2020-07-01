package com.google.android.gms.internal.firebase_ml;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import java.io.Closeable;
import java.io.IOException;

public class zzpw<TDetectionResult> implements Closeable {
    private final zznq zzasm;
    private final zznm<TDetectionResult, zzpz> zzaxo;

    protected zzpw(@NonNull FirebaseApp firebaseApp, zznm<TDetectionResult, zzpz> zznm) {
        Preconditions.checkNotNull(firebaseApp, "FirebaseApp must not be null");
        Preconditions.checkNotNull(firebaseApp.getPersistenceKey(), "Firebase app name must not be null");
        this.zzaxo = zznm;
        this.zzasm = zznq.zza(firebaseApp);
        this.zzasm.zza((zznm) zznm);
    }

    protected final Task<TDetectionResult> zza(@NonNull FirebaseVisionImage firebaseVisionImage, boolean z, boolean z2) {
        Preconditions.checkNotNull(firebaseVisionImage, "FirebaseVisionImage can not be null");
        return this.zzasm.zza(this.zzaxo, new zzpz(firebaseVisionImage.zza(z, z2)));
    }

    public void close() throws IOException {
        this.zzasm.zzb(this.zzaxo);
    }
}
