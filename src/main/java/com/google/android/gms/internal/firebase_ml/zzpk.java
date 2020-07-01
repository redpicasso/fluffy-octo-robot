package com.google.android.gms.internal.firebase_ml;

import android.content.Context;
import android.os.SystemClock;
import android.util.SparseArray;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.barcode.BarcodeDetector.Builder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import java.util.ArrayList;
import java.util.List;

public final class zzpk implements zznm<List<FirebaseVisionBarcode>, zzpz>, zznw {
    @VisibleForTesting
    private static boolean zzata = true;
    private final Context zzad;
    private final zznu zzaqs;
    private final FirebaseVisionBarcodeDetectorOptions zzawa;
    @VisibleForTesting
    @GuardedBy("this")
    private BarcodeDetector zzawb;
    private zzpu zzawc = new zzpu();

    public zzpk(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionBarcodeDetectorOptions firebaseVisionBarcodeDetectorOptions) {
        Preconditions.checkNotNull(firebaseApp, "FirebaseApp can not be null");
        Preconditions.checkNotNull(firebaseVisionBarcodeDetectorOptions, "FirebaseVisionBarcodeDetectorOptions can not be null");
        this.zzad = firebaseApp.getApplicationContext();
        this.zzawa = firebaseVisionBarcodeDetectorOptions;
        this.zzaqs = zznu.zza(firebaseApp, 1);
    }

    public final zznw zzlm() {
        return this;
    }

    @WorkerThread
    public final synchronized void zzlp() {
        if (this.zzawb == null) {
            this.zzawb = new Builder(this.zzad).setBarcodeFormats(this.zzawa.zznf()).build();
        }
    }

    @WorkerThread
    public final synchronized void release() {
        if (this.zzawb != null) {
            this.zzawb.release();
            this.zzawb = null;
        }
        zzata = true;
    }

    @WorkerThread
    private final synchronized List<FirebaseVisionBarcode> zza(@NonNull zzpz zzpz) throws FirebaseMLException {
        List<FirebaseVisionBarcode> arrayList;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.zzawb == null) {
            zza(zzmk.UNKNOWN_ERROR, elapsedRealtime, zzpz, null);
            throw new FirebaseMLException("Model source is unavailable. Please load the model resource first.", 13);
        } else if (this.zzawb.isOperational()) {
            this.zzawc.zzb(zzpz);
            SparseArray detect = this.zzawb.detect(zzpz.zzaxe);
            arrayList = new ArrayList();
            for (int i = 0; i < detect.size(); i++) {
                Barcode barcode = (Barcode) detect.get(detect.keyAt(i));
                if (barcode != null) {
                    arrayList.add(new FirebaseVisionBarcode(barcode));
                }
            }
            zza(zzmk.NO_ERROR, elapsedRealtime, zzpz, (List) arrayList);
            zzata = false;
        } else {
            zza(zzmk.MODEL_NOT_DOWNLOADED, elapsedRealtime, zzpz, null);
            throw new FirebaseMLException("Waiting for the barcode detection model to be downloaded. Please wait.", 14);
        }
        return arrayList;
    }

    private final void zza(zzmk zzmk, long j, @NonNull zzpz zzpz, @Nullable List<FirebaseVisionBarcode> list) {
        this.zzaqs.zza(new zzpl(this, j, zzmk, zzpz, list), zzmn.ON_DEVICE_BARCODE_DETECT);
    }
}
