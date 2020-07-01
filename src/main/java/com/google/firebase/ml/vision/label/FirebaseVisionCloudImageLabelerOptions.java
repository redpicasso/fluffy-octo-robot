package com.google.firebase.ml.vision.label;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import javax.annotation.concurrent.Immutable;

@Immutable
public class FirebaseVisionCloudImageLabelerOptions {
    private final float zzavp;
    private final boolean zzawj;

    public static class Builder {
        private float zzavp = 0.5f;
        private boolean zzawj = false;

        public Builder setConfidenceThreshold(float f) {
            boolean z = Float.compare(f, 0.0f) >= 0 && Float.compare(f, 1.0f) <= 0;
            Preconditions.checkArgument(z, "Confidence Threshold should be in range [0.0f, 1.0f].");
            this.zzavp = f;
            return this;
        }

        public Builder enforceCertFingerprintMatch() {
            this.zzawj = true;
            return this;
        }

        public FirebaseVisionCloudImageLabelerOptions build() {
            return new FirebaseVisionCloudImageLabelerOptions(this.zzavp, this.zzawj, null);
        }
    }

    private FirebaseVisionCloudImageLabelerOptions(float f, boolean z) {
        this.zzavp = f;
        this.zzawj = z;
    }

    public float getConfidenceThreshold() {
        return this.zzavp;
    }

    public boolean isEnforceCertFingerprintMatch() {
        return this.zzawj;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseVisionCloudImageLabelerOptions)) {
            return false;
        }
        FirebaseVisionCloudImageLabelerOptions firebaseVisionCloudImageLabelerOptions = (FirebaseVisionCloudImageLabelerOptions) obj;
        return Float.compare(this.zzavp, firebaseVisionCloudImageLabelerOptions.zzavp) == 0 && this.zzawj == firebaseVisionCloudImageLabelerOptions.zzawj;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.zzavp), Boolean.valueOf(this.zzawj));
    }

    /* synthetic */ FirebaseVisionCloudImageLabelerOptions(float f, boolean z, zza zza) {
        this(f, z);
    }
}
