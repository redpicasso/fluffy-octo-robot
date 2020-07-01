package com.google.android.gms.internal.firebase_ml;

import android.content.Context;
import android.os.SystemClock;
import android.util.SparseArray;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.vision.label.ImageLabel;
import com.google.android.gms.vision.label.ImageLabeler;
import com.google.android.gms.vision.label.ImageLabeler.Builder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions;
import java.util.ArrayList;
import java.util.List;

public final class zzqe implements zznm<List<FirebaseVisionImageLabel>, zzpz>, zznw {
    @VisibleForTesting
    private static boolean zzata = true;
    private static volatile Boolean zzaza;
    private final Context zzad;
    private final zznu zzaqs;
    private final FirebaseVisionOnDeviceImageLabelerOptions zzazb;
    @GuardedBy("this")
    private ImageLabeler zzazc;

    public zzqe(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionOnDeviceImageLabelerOptions firebaseVisionOnDeviceImageLabelerOptions) {
        Preconditions.checkNotNull(firebaseApp, "Context can not be null");
        Preconditions.checkNotNull(firebaseVisionOnDeviceImageLabelerOptions, "FirebaseVisionOnDeviceImageLabelerOptions can not be null");
        this.zzad = firebaseApp.getApplicationContext();
        this.zzazb = firebaseVisionOnDeviceImageLabelerOptions;
        this.zzaqs = zznu.zza(firebaseApp, 1);
    }

    public final zznw zzlm() {
        return this;
    }

    @WorkerThread
    private final synchronized List<FirebaseVisionImageLabel> zza(zzpz zzpz) throws FirebaseMLException {
        List<FirebaseVisionImageLabel> arrayList;
        if (zzaza == null) {
            Context context = this.zzad;
            boolean z = true;
            Object obj = (DynamiteModule.getLocalVersion(context, "com.google.android.gms.vision.dynamite.ica") > 0 || DynamiteModule.getLocalVersion(context, "com.google.android.gms.vision.dynamite.imagelabel") > 0) ? 1 : null;
            if (obj != null) {
                z = false;
            }
            zzaza = Boolean.valueOf(z);
        }
        if (zzaza.booleanValue()) {
            throw new FirebaseMLException("No model is bundled. Please check your app setup to includefirebase-ml-vision-image-label-model dependency.", 14);
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.zzazc == null) {
            zza(zzmk.UNKNOWN_ERROR, elapsedRealtime, zzpz);
            throw new FirebaseMLException("Model source is unavailable. Please load the model resource first.", 13);
        } else if (this.zzazc.isOperational()) {
            SparseArray detect = this.zzazc.detect(zzpz.zzaxe);
            arrayList = new ArrayList();
            if (detect != null) {
                for (int i = 0; i < detect.size(); i++) {
                    arrayList.add(new FirebaseVisionImageLabel((ImageLabel) detect.get(detect.keyAt(i))));
                }
            }
            zza(zzmk.NO_ERROR, elapsedRealtime, zzpz);
            zzata = false;
        } else {
            zza(zzmk.MODEL_NOT_DOWNLOADED, elapsedRealtime, zzpz);
            throw new FirebaseMLException("Waiting for the label detection model to be downloaded. Please wait.", 14);
        }
        return arrayList;
    }

    @WorkerThread
    public final synchronized void zzlp() {
        if (this.zzazc == null) {
            this.zzazc = new Builder(this.zzad).setScoreThreshold(this.zzazb.getConfidenceThreshold()).build();
        }
    }

    @WorkerThread
    public final synchronized void release() {
        if (this.zzazc != null) {
            this.zzazc.release();
            this.zzazc = null;
        }
        zzata = true;
    }

    private final void zza(zzmk zzmk, long j, zzpz zzpz) {
        this.zzaqs.zza(new zzqf(this, j, zzmk, zzpz), zzmn.ON_DEVICE_IMAGE_LABEL_DETECT);
    }
}
