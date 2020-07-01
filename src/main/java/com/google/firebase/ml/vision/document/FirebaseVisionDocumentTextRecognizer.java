package com.google.firebase.ml.vision.document;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzjf;
import com.google.android.gms.internal.firebase_ml.zzmd.zzq;
import com.google.android.gms.internal.firebase_ml.zzmn;
import com.google.android.gms.internal.firebase_ml.zznt;
import com.google.android.gms.internal.firebase_ml.zznu;
import com.google.android.gms.internal.firebase_ml.zzpo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import java.util.HashMap;
import java.util.Map;

public class FirebaseVisionDocumentTextRecognizer extends zzpo<FirebaseVisionDocumentText> {
    private static final Map<zznt<FirebaseVisionCloudDocumentRecognizerOptions>, FirebaseVisionDocumentTextRecognizer> zzax = new HashMap();

    public static synchronized FirebaseVisionDocumentTextRecognizer zza(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionCloudDocumentRecognizerOptions firebaseVisionCloudDocumentRecognizerOptions) {
        FirebaseVisionDocumentTextRecognizer firebaseVisionDocumentTextRecognizer;
        synchronized (FirebaseVisionDocumentTextRecognizer.class) {
            Preconditions.checkNotNull(firebaseApp, "FirebaseApp must not be null");
            Preconditions.checkNotNull(firebaseApp.getPersistenceKey(), "Firebase app name must not be null");
            Preconditions.checkNotNull(firebaseVisionCloudDocumentRecognizerOptions, "Options must not be null");
            zznt zzj = zznt.zzj(firebaseApp.getPersistenceKey(), firebaseVisionCloudDocumentRecognizerOptions);
            firebaseVisionDocumentTextRecognizer = (FirebaseVisionDocumentTextRecognizer) zzax.get(zzj);
            if (firebaseVisionDocumentTextRecognizer == null) {
                zzjf zzjf = new zzjf();
                zzjf.zzd(firebaseVisionCloudDocumentRecognizerOptions.getHintedLanguages());
                FirebaseVisionDocumentTextRecognizer firebaseVisionDocumentTextRecognizer2 = new FirebaseVisionDocumentTextRecognizer(firebaseApp, zzjf, firebaseVisionCloudDocumentRecognizerOptions.isEnforceCertFingerprintMatch());
                zzax.put(zzj, firebaseVisionDocumentTextRecognizer2);
                firebaseVisionDocumentTextRecognizer = firebaseVisionDocumentTextRecognizer2;
            }
        }
        return firebaseVisionDocumentTextRecognizer;
    }

    protected final int zznh() {
        return 1024;
    }

    protected final int zzni() {
        return 768;
    }

    private FirebaseVisionDocumentTextRecognizer(@NonNull FirebaseApp firebaseApp, @NonNull zzjf zzjf, boolean z) {
        super(firebaseApp, "DOCUMENT_TEXT_DETECTION", zzjf, z);
        zznu.zza(firebaseApp, 1).zza(zzq.zzjx(), zzmn.CLOUD_DOCUMENT_TEXT_CREATE);
    }

    public Task<FirebaseVisionDocumentText> processImage(@NonNull FirebaseVisionImage firebaseVisionImage) {
        zznu.zza(this.zzapo, 1).zza(zzq.zzjx(), zzmn.CLOUD_DOCUMENT_TEXT_DETECT);
        return super.zza(firebaseVisionImage);
    }
}
