package com.google.firebase.ml.vision.cloud.landmark;

import androidx.annotation.NonNull;
import com.drew.metadata.exif.makernotes.OlympusMakernoteDirectory;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzmd.zzq;
import com.google.android.gms.internal.firebase_ml.zzmn;
import com.google.android.gms.internal.firebase_ml.zznt;
import com.google.android.gms.internal.firebase_ml.zznu;
import com.google.android.gms.internal.firebase_ml.zzpo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseVisionCloudLandmarkDetector extends zzpo<List<FirebaseVisionCloudLandmark>> {
    private static final Map<zznt<FirebaseVisionCloudDetectorOptions>, FirebaseVisionCloudLandmarkDetector> zzax = new HashMap();

    public static synchronized FirebaseVisionCloudLandmarkDetector zza(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionCloudDetectorOptions firebaseVisionCloudDetectorOptions) {
        FirebaseVisionCloudLandmarkDetector firebaseVisionCloudLandmarkDetector;
        synchronized (FirebaseVisionCloudLandmarkDetector.class) {
            Preconditions.checkNotNull(firebaseApp, "FirebaseApp must not be null");
            Preconditions.checkNotNull(firebaseApp.getPersistenceKey(), "Firebase app name must not be null");
            Preconditions.checkNotNull(firebaseVisionCloudDetectorOptions, "Options must not be null");
            zznt zzj = zznt.zzj(firebaseApp.getPersistenceKey(), firebaseVisionCloudDetectorOptions);
            firebaseVisionCloudLandmarkDetector = (FirebaseVisionCloudLandmarkDetector) zzax.get(zzj);
            if (firebaseVisionCloudLandmarkDetector == null) {
                firebaseVisionCloudLandmarkDetector = new FirebaseVisionCloudLandmarkDetector(firebaseApp, firebaseVisionCloudDetectorOptions);
                zzax.put(zzj, firebaseVisionCloudLandmarkDetector);
            }
        }
        return firebaseVisionCloudLandmarkDetector;
    }

    protected final int zznh() {
        return OlympusMakernoteDirectory.TAG_PREVIEW_IMAGE;
    }

    protected final int zzni() {
        return 480;
    }

    private FirebaseVisionCloudLandmarkDetector(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionCloudDetectorOptions firebaseVisionCloudDetectorOptions) {
        super(firebaseApp, "LANDMARK_DETECTION", firebaseVisionCloudDetectorOptions);
        zznu.zza(firebaseApp, 1).zza(zzq.zzjx(), zzmn.CLOUD_LANDMARK_CREATE);
    }

    public Task<List<FirebaseVisionCloudLandmark>> detectInImage(@NonNull FirebaseVisionImage firebaseVisionImage) {
        zznu.zza(this.zzapo, 1).zza(zzq.zzjx(), zzmn.CLOUD_LANDMARK_DETECT);
        return super.zza(firebaseVisionImage);
    }
}
