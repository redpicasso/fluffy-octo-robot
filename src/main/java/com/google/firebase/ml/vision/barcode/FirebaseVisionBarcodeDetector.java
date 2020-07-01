package com.google.firebase.ml.vision.barcode;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzmd.zzq;
import com.google.android.gms.internal.firebase_ml.zzmd.zzv;
import com.google.android.gms.internal.firebase_ml.zzmn;
import com.google.android.gms.internal.firebase_ml.zznt;
import com.google.android.gms.internal.firebase_ml.zznu;
import com.google.android.gms.internal.firebase_ml.zzpk;
import com.google.android.gms.internal.firebase_ml.zzpw;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseVisionBarcodeDetector extends zzpw<List<FirebaseVisionBarcode>> implements Closeable {
    private static final Map<zznt<FirebaseVisionBarcodeDetectorOptions>, FirebaseVisionBarcodeDetector> zzax = new HashMap();

    public static synchronized FirebaseVisionBarcodeDetector zza(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionBarcodeDetectorOptions firebaseVisionBarcodeDetectorOptions) {
        FirebaseVisionBarcodeDetector firebaseVisionBarcodeDetector;
        synchronized (FirebaseVisionBarcodeDetector.class) {
            Preconditions.checkNotNull(firebaseApp, "You must provide a valid FirebaseApp.");
            Preconditions.checkNotNull(firebaseApp.getPersistenceKey(), "Firebase app name must not be null");
            Preconditions.checkNotNull(firebaseApp.getApplicationContext(), "You must provide a valid Context.");
            Preconditions.checkNotNull(firebaseVisionBarcodeDetectorOptions, "You must provide a valid FirebaseVisionBarcodeDetectorOptions.");
            zznt zzj = zznt.zzj(firebaseApp.getPersistenceKey(), firebaseVisionBarcodeDetectorOptions);
            firebaseVisionBarcodeDetector = (FirebaseVisionBarcodeDetector) zzax.get(zzj);
            if (firebaseVisionBarcodeDetector == null) {
                firebaseVisionBarcodeDetector = new FirebaseVisionBarcodeDetector(firebaseApp, firebaseVisionBarcodeDetectorOptions);
                zzax.put(zzj, firebaseVisionBarcodeDetector);
            }
        }
        return firebaseVisionBarcodeDetector;
    }

    private FirebaseVisionBarcodeDetector(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionBarcodeDetectorOptions firebaseVisionBarcodeDetectorOptions) {
        super(firebaseApp, new zzpk(firebaseApp, firebaseVisionBarcodeDetectorOptions));
        zznu.zza(firebaseApp, 1).zza(zzq.zzjx().zzb(zzv.zzki().zzb(firebaseVisionBarcodeDetectorOptions.zzng())), zzmn.ON_DEVICE_BARCODE_CREATE);
    }

    public Task<List<FirebaseVisionBarcode>> detectInImage(@NonNull FirebaseVisionImage firebaseVisionImage) {
        Preconditions.checkNotNull(firebaseVisionImage, "FirebaseVisionImage can not be null");
        return zza(firebaseVisionImage, false, false);
    }

    public void close() throws IOException {
        super.close();
    }
}
