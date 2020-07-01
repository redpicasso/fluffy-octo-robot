package com.google.firebase.ml.vision.label;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zznt;
import com.google.android.gms.internal.firebase_ml.zzpo;
import com.google.android.gms.internal.firebase_ml.zzpw;
import com.google.android.gms.internal.firebase_ml.zzqc;
import com.google.android.gms.internal.firebase_ml.zzqd;
import com.google.android.gms.internal.firebase_ml.zzqg;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions.Builder;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

public class FirebaseVisionImageLabeler implements Closeable {
    public static final int CLOUD = 2;
    public static final int ON_DEVICE = 1;
    @GuardedBy("FirebaseVisionImageLabeler.class")
    private static final Map<zznt<FirebaseVisionOnDeviceImageLabelerOptions>, FirebaseVisionImageLabeler> zzayw = new HashMap();
    @GuardedBy("FirebaseVisionImageLabeler.class")
    private static final Map<zznt<FirebaseVisionCloudImageLabelerOptions>, FirebaseVisionImageLabeler> zzayx = new HashMap();
    @GuardedBy("FirebaseVisionImageLabeler.class")
    private static final Map<zznt<Object>, FirebaseVisionImageLabeler> zzayy = new HashMap();
    private final zzqc zzayr;
    private final zzqd zzays;
    private final zzqg zzayt;
    private final FirebaseVisionCloudImageLabelerOptions zzayu;
    @ImageLabelerType
    private final int zzayv;

    public @interface ImageLabelerType {
    }

    public static synchronized FirebaseVisionImageLabeler zza(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionOnDeviceImageLabelerOptions firebaseVisionOnDeviceImageLabelerOptions) {
        FirebaseVisionImageLabeler firebaseVisionImageLabeler;
        synchronized (FirebaseVisionImageLabeler.class) {
            Preconditions.checkNotNull(firebaseApp, "FirebaseApp must not be null");
            Preconditions.checkNotNull(firebaseApp.getPersistenceKey(), "Firebase app name must not be null");
            zznt zzj = zznt.zzj(firebaseApp.getPersistenceKey(), firebaseVisionOnDeviceImageLabelerOptions);
            FirebaseVisionImageLabeler firebaseVisionImageLabeler2 = (FirebaseVisionImageLabeler) zzayw.get(zzj);
            if (firebaseVisionImageLabeler2 == null) {
                firebaseVisionImageLabeler = new FirebaseVisionImageLabeler(new zzqd(firebaseApp, firebaseVisionOnDeviceImageLabelerOptions));
                zzayw.put(zzj, firebaseVisionImageLabeler);
            } else {
                firebaseVisionImageLabeler = firebaseVisionImageLabeler2;
            }
        }
        return firebaseVisionImageLabeler;
    }

    public static synchronized FirebaseVisionImageLabeler zza(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionCloudImageLabelerOptions firebaseVisionCloudImageLabelerOptions) {
        FirebaseVisionImageLabeler firebaseVisionImageLabeler;
        synchronized (FirebaseVisionImageLabeler.class) {
            Preconditions.checkNotNull(firebaseApp, "FirebaseApp must not be null");
            Preconditions.checkNotNull(firebaseApp.getPersistenceKey(), "Firebase app name must not be null");
            zznt zzj = zznt.zzj(firebaseApp.getPersistenceKey(), firebaseVisionCloudImageLabelerOptions);
            firebaseVisionImageLabeler = (FirebaseVisionImageLabeler) zzayx.get(zzj);
            if (firebaseVisionImageLabeler == null) {
                Builder maxResults = new Builder().setMaxResults(20);
                if (firebaseVisionCloudImageLabelerOptions.isEnforceCertFingerprintMatch()) {
                    maxResults.enforceCertFingerprintMatch();
                }
                firebaseVisionImageLabeler = new FirebaseVisionImageLabeler(new zzqc(firebaseApp, maxResults.build()), firebaseVisionCloudImageLabelerOptions);
                zzayx.put(zzj, firebaseVisionImageLabeler);
            }
        }
        return firebaseVisionImageLabeler;
    }

    public Task<List<FirebaseVisionImageLabel>> processImage(@NonNull FirebaseVisionImage firebaseVisionImage) {
        boolean z = (this.zzays == null && this.zzayr == null) ? false : true;
        Preconditions.checkState(z, "One of on-device, cloud, or on-device AutoML image labeler should be set.");
        zzqd zzqd = this.zzays;
        if (zzqd != null) {
            return zzqd.detectInImage(firebaseVisionImage);
        }
        return this.zzayr.detectInImage(firebaseVisionImage).continueWith(new zzb(this));
    }

    @ImageLabelerType
    public int getImageLabelerType() {
        return this.zzayv;
    }

    public void close() throws IOException {
        zzpw zzpw = this.zzays;
        if (zzpw != null) {
            zzpw.close();
        }
        zzpo zzpo = this.zzayr;
        if (zzpo != null) {
            zzpo.close();
        }
    }

    private FirebaseVisionImageLabeler(@Nullable zzqd zzqd) {
        this(zzqd, null, null, null);
    }

    private FirebaseVisionImageLabeler(@Nullable zzqc zzqc, @Nullable FirebaseVisionCloudImageLabelerOptions firebaseVisionCloudImageLabelerOptions) {
        this(null, zzqc, null, firebaseVisionCloudImageLabelerOptions);
    }

    private FirebaseVisionImageLabeler(@Nullable zzqd zzqd, @Nullable zzqc zzqc, @Nullable zzqg zzqg, @Nullable FirebaseVisionCloudImageLabelerOptions firebaseVisionCloudImageLabelerOptions) {
        boolean z = (zzqd == null && zzqc == null) ? false : true;
        Preconditions.checkArgument(z, "One of on-device, cloud or on-device AutoML image labeler should be set.");
        this.zzays = zzqd;
        this.zzayr = zzqc;
        this.zzayu = firebaseVisionCloudImageLabelerOptions;
        this.zzayt = null;
        if (zzqc != null) {
            this.zzayv = 2;
        } else if (zzqd != null) {
            this.zzayv = 1;
        } else {
            this.zzayv = 3;
        }
    }
}
