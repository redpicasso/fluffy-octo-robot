package com.google.android.gms.internal.firebase_ml;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzmd.zzq;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import java.util.HashMap;
import java.util.Map;

public final class zzqh extends zzpo<FirebaseVisionText> {
    @GuardedBy("CloudTextRecognizer.class")
    private static final Map<zznt<FirebaseVisionCloudTextRecognizerOptions>, zzqh> zzax = new HashMap();
    private final FirebaseVisionCloudTextRecognizerOptions zzazs;

    public static synchronized zzqh zza(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionCloudTextRecognizerOptions firebaseVisionCloudTextRecognizerOptions) {
        zzqh zzqh;
        synchronized (zzqh.class) {
            Preconditions.checkNotNull(firebaseApp, "FirebaseApp must not be null");
            Preconditions.checkNotNull(firebaseApp.getPersistenceKey(), "Firebase app name must not be null");
            Preconditions.checkNotNull(firebaseVisionCloudTextRecognizerOptions, "Options must not be null");
            zznt zzj = zznt.zzj(firebaseApp.getPersistenceKey(), firebaseVisionCloudTextRecognizerOptions);
            zzqh = (zzqh) zzax.get(zzj);
            if (zzqh == null) {
                zzqh = new zzqh(firebaseApp, firebaseVisionCloudTextRecognizerOptions);
                zzax.put(zzj, zzqh);
            }
        }
        return zzqh;
    }

    protected final int zznh() {
        return 1024;
    }

    protected final int zzni() {
        return 768;
    }

    private zzqh(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionCloudTextRecognizerOptions firebaseVisionCloudTextRecognizerOptions) {
        super(firebaseApp, firebaseVisionCloudTextRecognizerOptions.getModelType() == 1 ? "TEXT_DETECTION" : "DOCUMENT_TEXT_DETECTION", new zzjf(), firebaseVisionCloudTextRecognizerOptions.isEnforceCertFingerprintMatch());
        this.zzazs = firebaseVisionCloudTextRecognizerOptions;
        zzmn zzmn = zzmn.CLOUD_TEXT_CREATE;
        if (firebaseVisionCloudTextRecognizerOptions.getModelType() == 2) {
            zzmn = zzmn.CLOUD_DOCUMENT_TEXT_CREATE;
        }
        zznu.zza(firebaseApp, 1).zza(zzq.zzjx(), zzmn);
    }

    public final Task<FirebaseVisionText> processImage(@NonNull FirebaseVisionImage firebaseVisionImage) {
        zzmn zzmn = zzmn.CLOUD_TEXT_DETECT;
        if (this.zzazs.getModelType() == 2) {
            zzmn = zzmn.CLOUD_DOCUMENT_TEXT_DETECT;
        }
        zznu.zza(this.zzapo, 1).zza(zzq.zzjx(), zzmn);
        return super.zza(firebaseVisionImage);
    }
}
