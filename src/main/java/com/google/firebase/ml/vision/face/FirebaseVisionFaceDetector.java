package com.google.firebase.ml.vision.face;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzmd.zzq;
import com.google.android.gms.internal.firebase_ml.zzmd.zzw;
import com.google.android.gms.internal.firebase_ml.zzmn;
import com.google.android.gms.internal.firebase_ml.zznt;
import com.google.android.gms.internal.firebase_ml.zznu;
import com.google.android.gms.internal.firebase_ml.zzpw;
import com.google.android.gms.internal.firebase_ml.zzqa;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FirebaseVisionFaceDetector extends zzpw<List<FirebaseVisionFace>> implements Closeable {
    private static final Map<zznt<FirebaseVisionFaceDetectorOptions>, FirebaseVisionFaceDetector> zzax = new HashMap();
    private final FirebaseVisionFaceDetectorOptions zzaye;

    public static synchronized FirebaseVisionFaceDetector zza(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions) {
        FirebaseVisionFaceDetector firebaseVisionFaceDetector;
        synchronized (FirebaseVisionFaceDetector.class) {
            Preconditions.checkNotNull(firebaseApp, "You must provide a valid FirebaseApp.");
            Preconditions.checkNotNull(firebaseApp.getPersistenceKey(), "Firebase app name must not be null");
            Preconditions.checkNotNull(firebaseApp.getApplicationContext(), "You must provide a valid Context.");
            Preconditions.checkNotNull(firebaseVisionFaceDetectorOptions, "You must provide a valid FirebaseVisionFaceDetectorOptions.");
            zznt zzj = zznt.zzj(firebaseApp.getPersistenceKey(), firebaseVisionFaceDetectorOptions);
            firebaseVisionFaceDetector = (FirebaseVisionFaceDetector) zzax.get(zzj);
            if (firebaseVisionFaceDetector == null) {
                firebaseVisionFaceDetector = new FirebaseVisionFaceDetector(firebaseApp, firebaseVisionFaceDetectorOptions);
                zzax.put(zzj, firebaseVisionFaceDetector);
            }
        }
        return firebaseVisionFaceDetector;
    }

    private FirebaseVisionFaceDetector(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions) {
        super(firebaseApp, new zzqa(firebaseApp, firebaseVisionFaceDetectorOptions));
        this.zzaye = firebaseVisionFaceDetectorOptions;
        zznu.zza(firebaseApp, 1).zza(zzq.zzjx().zzb(zzw.zzkk().zzb(firebaseVisionFaceDetectorOptions.zznn())), zzmn.ON_DEVICE_FACE_CREATE);
    }

    public Task<List<FirebaseVisionFace>> detectInImage(@NonNull FirebaseVisionImage firebaseVisionImage) {
        boolean z = this.zzaye.getContourMode() != 2 || firebaseVisionImage.zza(false, false).getMetadata().getWidth() >= 32;
        Preconditions.checkArgument(z, String.format(Locale.getDefault(), "The width of input image cannot be less than %s when using contour mode ALL_CONTOURS!", new Object[]{Integer.valueOf(32)}));
        return super.zza(firebaseVisionImage, false, true);
    }

    public void close() throws IOException {
        super.close();
    }
}
