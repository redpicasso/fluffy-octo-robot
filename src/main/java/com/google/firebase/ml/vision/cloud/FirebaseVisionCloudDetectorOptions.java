package com.google.firebase.ml.vision.cloud;

import com.google.android.gms.common.internal.Objects;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.concurrent.Immutable;

@Immutable
public class FirebaseVisionCloudDetectorOptions {
    public static final FirebaseVisionCloudDetectorOptions DEFAULT = new Builder().build();
    public static final int LATEST_MODEL = 2;
    public static final int STABLE_MODEL = 1;
    private final int zzawh;
    private final int zzawi;
    private final boolean zzawj;

    public static class Builder {
        private int zzawh = 10;
        private int zzawi = 1;
        private boolean zzawj = false;

        public Builder setMaxResults(int i) {
            this.zzawh = i;
            return this;
        }

        public Builder setModelType(int i) {
            this.zzawi = i;
            return this;
        }

        public Builder enforceCertFingerprintMatch() {
            this.zzawj = true;
            return this;
        }

        public FirebaseVisionCloudDetectorOptions build() {
            return new FirebaseVisionCloudDetectorOptions(this.zzawh, this.zzawi, this.zzawj, null);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModelType {
    }

    private FirebaseVisionCloudDetectorOptions(int i, int i2, boolean z) {
        this.zzawh = i;
        this.zzawi = i2;
        this.zzawj = z;
    }

    public int getMaxResults() {
        return this.zzawh;
    }

    public int getModelType() {
        return this.zzawi;
    }

    public final boolean isEnforceCertFingerprintMatch() {
        return this.zzawj;
    }

    public Builder builder() {
        return new Builder();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseVisionCloudDetectorOptions)) {
            return false;
        }
        FirebaseVisionCloudDetectorOptions firebaseVisionCloudDetectorOptions = (FirebaseVisionCloudDetectorOptions) obj;
        return this.zzawh == firebaseVisionCloudDetectorOptions.zzawh && this.zzawi == firebaseVisionCloudDetectorOptions.zzawi && this.zzawj == firebaseVisionCloudDetectorOptions.zzawj;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzawh), Integer.valueOf(this.zzawi), Boolean.valueOf(this.zzawj));
    }

    /* synthetic */ FirebaseVisionCloudDetectorOptions(int i, int i2, boolean z, zza zza) {
        this(i, i2, z);
    }
}
