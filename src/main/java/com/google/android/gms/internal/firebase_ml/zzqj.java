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
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.gms.vision.text.TextRecognizer.Builder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

public final class zzqj implements zznm<FirebaseVisionText, zzpz>, zznw {
    @VisibleForTesting
    static boolean zzata = true;
    private final Context zzad;
    private final zznu zzaqs;
    private zzpu zzawc = new zzpu();
    @GuardedBy("this")
    private TextRecognizer zzazt;

    public zzqj(@NonNull FirebaseApp firebaseApp) {
        Preconditions.checkNotNull(firebaseApp, "Firebase App can not be null");
        this.zzad = firebaseApp.getApplicationContext();
        this.zzaqs = zznu.zza(firebaseApp, 1);
    }

    public final zznw zzlm() {
        return this;
    }

    @WorkerThread
    public final synchronized void zzlp() {
        if (this.zzazt == null) {
            this.zzazt = new Builder(this.zzad).build();
        }
    }

    @WorkerThread
    public final synchronized void release() {
        if (this.zzazt != null) {
            this.zzazt.release();
            this.zzazt = null;
        }
        zzata = true;
    }

    @WorkerThread
    @Nullable
    private final synchronized FirebaseVisionText zzd(@NonNull zzpz zzpz) throws FirebaseMLException {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.zzazt == null) {
            zza(zzmk.UNKNOWN_ERROR, elapsedRealtime, zzpz);
            throw new FirebaseMLException("Model source is unavailable. Please load the model resource first.", 13);
        } else if (this.zzazt.isOperational()) {
            this.zzawc.zzb(zzpz);
            SparseArray detect = this.zzazt.detect(zzpz.zzaxe);
            zza(zzmk.NO_ERROR, elapsedRealtime, zzpz);
            zzata = false;
            if (detect == null) {
                return null;
            }
            return new FirebaseVisionText(detect);
        } else {
            zza(zzmk.MODEL_NOT_DOWNLOADED, elapsedRealtime, zzpz);
            throw new FirebaseMLException("Waiting for the text recognition model to be downloaded. Please wait.", 14);
        }
    }

    private final void zza(zzmk zzmk, long j, zzpz zzpz) {
        this.zzaqs.zza(new zzqk(j, zzmk, zzpz), zzmn.ON_DEVICE_TEXT_DETECT);
    }
}
