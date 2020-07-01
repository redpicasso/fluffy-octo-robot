package com.google.firebase.ml.vision.face;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.firebase_ml.zzkj;
import com.google.android.gms.internal.firebase_ml.zzmd.zzp;
import com.google.android.gms.internal.firebase_ml.zzmd.zzp.zza;
import com.google.android.gms.internal.firebase_ml.zzmd.zzp.zzb;
import com.google.android.gms.internal.firebase_ml.zzmd.zzp.zzc;
import com.google.android.gms.internal.firebase_ml.zzmd.zzp.zzd;
import com.google.android.gms.internal.firebase_ml.zzmd.zzp.zze;
import com.google.android.gms.internal.firebase_ml.zzue;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class FirebaseVisionFaceDetectorOptions {
    public static final int ACCURATE = 2;
    public static final int ALL_CLASSIFICATIONS = 2;
    public static final int ALL_CONTOURS = 2;
    public static final int ALL_LANDMARKS = 2;
    public static final int FAST = 1;
    public static final int NO_CLASSIFICATIONS = 1;
    public static final int NO_CONTOURS = 1;
    public static final int NO_LANDMARKS = 1;
    private final boolean trackingEnabled;
    private final int zzayf;
    private final int zzayg;
    private final int zzayh;
    private final int zzayi;
    private final float zzayj;

    public static class Builder {
        private boolean trackingEnabled = false;
        private int zzayf = 1;
        private int zzayg = 1;
        private int zzayh = 1;
        private int zzayi = 1;
        private float zzayj = 0.1f;

        public Builder setLandmarkMode(int i) {
            this.zzayf = i;
            return this;
        }

        public Builder setContourMode(int i) {
            this.zzayg = i;
            return this;
        }

        public Builder setClassificationMode(int i) {
            this.zzayh = i;
            return this;
        }

        public Builder enableTracking() {
            this.trackingEnabled = true;
            return this;
        }

        public Builder setPerformanceMode(int i) {
            this.zzayi = i;
            return this;
        }

        public Builder setMinFaceSize(float f) {
            this.zzayj = f;
            return this;
        }

        public FirebaseVisionFaceDetectorOptions build() {
            return new FirebaseVisionFaceDetectorOptions(this.zzayf, this.zzayg, this.zzayh, this.zzayi, this.trackingEnabled, this.zzayj, null);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClassificationMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContourMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LandmarkMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PerformanceMode {
    }

    public int getLandmarkMode() {
        return this.zzayf;
    }

    public int getContourMode() {
        return this.zzayg;
    }

    public int getClassificationMode() {
        return this.zzayh;
    }

    public int getPerformanceMode() {
        return this.zzayi;
    }

    public boolean isTrackingEnabled() {
        return this.trackingEnabled;
    }

    public float getMinFaceSize() {
        return this.zzayj;
    }

    private FirebaseVisionFaceDetectorOptions(int i, int i2, int i3, int i4, boolean z, float f) {
        this.zzayf = i;
        this.zzayg = i2;
        this.zzayh = i3;
        this.zzayi = i4;
        this.trackingEnabled = z;
        this.zzayj = f;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseVisionFaceDetectorOptions)) {
            return false;
        }
        FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions = (FirebaseVisionFaceDetectorOptions) obj;
        return Float.floatToIntBits(this.zzayj) == Float.floatToIntBits(firebaseVisionFaceDetectorOptions.zzayj) && this.zzayf == firebaseVisionFaceDetectorOptions.zzayf && this.zzayg == firebaseVisionFaceDetectorOptions.zzayg && this.zzayi == firebaseVisionFaceDetectorOptions.zzayi && this.trackingEnabled == firebaseVisionFaceDetectorOptions.trackingEnabled && this.zzayh == firebaseVisionFaceDetectorOptions.zzayh;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(Float.floatToIntBits(this.zzayj)), Integer.valueOf(this.zzayf), Integer.valueOf(this.zzayg), Integer.valueOf(this.zzayi), Boolean.valueOf(this.trackingEnabled), Integer.valueOf(this.zzayh));
    }

    public String toString() {
        return zzkj.zzaz("FaceDetectorOptions").zzb("landmarkMode", this.zzayf).zzb("contourMode", this.zzayg).zzb("classificationMode", this.zzayh).zzb("performanceMode", this.zzayi).zza("trackingEnabled", this.trackingEnabled).zza("minFaceSize", this.zzayj).toString();
    }

    public final zzp zznn() {
        zzd zzd;
        zzb zzb;
        zze zze;
        zzc zzc;
        zza zzju = zzp.zzju();
        int i = this.zzayf;
        if (i == 1) {
            zzd = zzd.NO_LANDMARKS;
        } else if (i != 2) {
            zzd = zzd.UNKNOWN_LANDMARKS;
        } else {
            zzd = zzd.ALL_LANDMARKS;
        }
        zzju = zzju.zzb(zzd);
        i = this.zzayh;
        if (i == 1) {
            zzb = zzb.NO_CLASSIFICATIONS;
        } else if (i != 2) {
            zzb = zzb.UNKNOWN_CLASSIFICATIONS;
        } else {
            zzb = zzb.ALL_CLASSIFICATIONS;
        }
        zzju = zzju.zzb(zzb);
        i = this.zzayi;
        if (i == 1) {
            zze = zze.FAST;
        } else if (i != 2) {
            zze = zze.UNKNOWN_PERFORMANCE;
        } else {
            zze = zze.ACCURATE;
        }
        zzju = zzju.zzb(zze);
        i = this.zzayg;
        if (i == 1) {
            zzc = zzc.NO_CONTOURS;
        } else if (i != 2) {
            zzc = zzc.UNKNOWN_CONTOURS;
        } else {
            zzc = zzc.ALL_CONTOURS;
        }
        return (zzp) ((zzue) zzju.zzb(zzc).zzv(isTrackingEnabled()).zzn(this.zzayj).zzrj());
    }

    /* synthetic */ FirebaseVisionFaceDetectorOptions(int i, int i2, int i3, int i4, boolean z, float f, zza zza) {
        this(i, i2, i3, i4, z, f);
    }
}
