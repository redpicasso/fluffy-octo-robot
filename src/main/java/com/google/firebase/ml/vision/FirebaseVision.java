package com.google.firebase.ml.vision;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzoa;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions.Builder;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector;
import com.google.firebase.ml.vision.document.FirebaseVisionCloudDocumentRecognizerOptions;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions;
import com.google.firebase.ml.vision.object.zza;
import com.google.firebase.ml.vision.object.zzc;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import java.util.HashMap;
import java.util.Map;

public class FirebaseVision {
    private static final FirebaseVisionCloudDetectorOptions zzaut = new Builder().build();
    private static final FirebaseVisionFaceDetectorOptions zzauu = new FirebaseVisionFaceDetectorOptions.Builder().build();
    private static final FirebaseVisionBarcodeDetectorOptions zzauv = new FirebaseVisionBarcodeDetectorOptions.Builder().build();
    private static final FirebaseVisionCloudTextRecognizerOptions zzauw = new FirebaseVisionCloudTextRecognizerOptions.Builder().build();
    private static final FirebaseVisionCloudDocumentRecognizerOptions zzaux = new FirebaseVisionCloudDocumentRecognizerOptions.Builder().build();
    private static final FirebaseVisionOnDeviceImageLabelerOptions zzauy = new FirebaseVisionOnDeviceImageLabelerOptions.Builder().build();
    private static final FirebaseVisionCloudImageLabelerOptions zzauz = new FirebaseVisionCloudImageLabelerOptions.Builder().build();
    private static final zza zzava = new zzc().zznp();
    private static final Map<String, FirebaseVision> zzax = new HashMap();
    private final FirebaseApp zzaso;

    public static FirebaseVision getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    public static FirebaseVision getInstance(@NonNull FirebaseApp firebaseApp) {
        FirebaseVision firebaseVision;
        Preconditions.checkNotNull(firebaseApp, "FirebaseApp can not be null");
        String persistenceKey = firebaseApp.getPersistenceKey();
        synchronized (zzax) {
            firebaseVision = (FirebaseVision) zzax.get(persistenceKey);
            if (firebaseVision == null) {
                firebaseVision = new FirebaseVision(firebaseApp);
                zzax.put(persistenceKey, firebaseVision);
            }
        }
        return firebaseVision;
    }

    public void setStatsCollectionEnabled(boolean z) {
        zzoa.zza(this.zzaso, z);
    }

    public boolean isStatsCollectionEnabled() {
        return zzoa.zzc(this.zzaso);
    }

    public FirebaseVisionFaceDetector getVisionFaceDetector(@NonNull FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions) {
        Preconditions.checkNotNull(firebaseVisionFaceDetectorOptions, "Please provide a valid FirebaseVisionFaceDetectorOptions");
        return FirebaseVisionFaceDetector.zza(this.zzaso, firebaseVisionFaceDetectorOptions);
    }

    public FirebaseVisionFaceDetector getVisionFaceDetector() {
        return FirebaseVisionFaceDetector.zza(this.zzaso, zzauu);
    }

    public FirebaseVisionTextRecognizer getOnDeviceTextRecognizer() {
        return FirebaseVisionTextRecognizer.zza(this.zzaso, null, true);
    }

    public FirebaseVisionTextRecognizer getCloudTextRecognizer() {
        return FirebaseVisionTextRecognizer.zza(this.zzaso, zzauw, false);
    }

    public FirebaseVisionTextRecognizer getCloudTextRecognizer(FirebaseVisionCloudTextRecognizerOptions firebaseVisionCloudTextRecognizerOptions) {
        return FirebaseVisionTextRecognizer.zza(this.zzaso, firebaseVisionCloudTextRecognizerOptions, false);
    }

    public FirebaseVisionBarcodeDetector getVisionBarcodeDetector(@NonNull FirebaseVisionBarcodeDetectorOptions firebaseVisionBarcodeDetectorOptions) {
        return FirebaseVisionBarcodeDetector.zza(this.zzaso, (FirebaseVisionBarcodeDetectorOptions) Preconditions.checkNotNull(firebaseVisionBarcodeDetectorOptions, "Please provide a valid FirebaseVisionBarcodeDetectorOptions"));
    }

    public FirebaseVisionBarcodeDetector getVisionBarcodeDetector() {
        return FirebaseVisionBarcodeDetector.zza(this.zzaso, zzauv);
    }

    public FirebaseVisionImageLabeler getOnDeviceImageLabeler(@NonNull FirebaseVisionOnDeviceImageLabelerOptions firebaseVisionOnDeviceImageLabelerOptions) {
        return FirebaseVisionImageLabeler.zza(this.zzaso, (FirebaseVisionOnDeviceImageLabelerOptions) Preconditions.checkNotNull(firebaseVisionOnDeviceImageLabelerOptions, "Please provide a valid FirebaseVisionOnDeviceImageLabelerOptions"));
    }

    public FirebaseVisionImageLabeler getOnDeviceImageLabeler() {
        return FirebaseVisionImageLabeler.zza(this.zzaso, zzauy);
    }

    public FirebaseVisionImageLabeler getCloudImageLabeler(@NonNull FirebaseVisionCloudImageLabelerOptions firebaseVisionCloudImageLabelerOptions) {
        return FirebaseVisionImageLabeler.zza(this.zzaso, firebaseVisionCloudImageLabelerOptions);
    }

    public FirebaseVisionImageLabeler getCloudImageLabeler() {
        return FirebaseVisionImageLabeler.zza(this.zzaso, zzauz);
    }

    public FirebaseVisionDocumentTextRecognizer getCloudDocumentTextRecognizer(@NonNull FirebaseVisionCloudDocumentRecognizerOptions firebaseVisionCloudDocumentRecognizerOptions) {
        return FirebaseVisionDocumentTextRecognizer.zza(this.zzaso, firebaseVisionCloudDocumentRecognizerOptions);
    }

    public FirebaseVisionDocumentTextRecognizer getCloudDocumentTextRecognizer() {
        return FirebaseVisionDocumentTextRecognizer.zza(this.zzaso, zzaux);
    }

    public FirebaseVisionCloudLandmarkDetector getVisionCloudLandmarkDetector(@NonNull FirebaseVisionCloudDetectorOptions firebaseVisionCloudDetectorOptions) {
        return FirebaseVisionCloudLandmarkDetector.zza(this.zzaso, firebaseVisionCloudDetectorOptions);
    }

    public FirebaseVisionCloudLandmarkDetector getVisionCloudLandmarkDetector() {
        return FirebaseVisionCloudLandmarkDetector.zza(this.zzaso, zzaut);
    }

    private FirebaseVision(FirebaseApp firebaseApp) {
        this.zzaso = firebaseApp;
    }
}
