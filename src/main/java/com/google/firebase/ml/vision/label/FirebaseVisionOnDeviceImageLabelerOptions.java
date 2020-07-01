package com.google.firebase.ml.vision.label;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzmd.zzy;
import com.google.android.gms.internal.firebase_ml.zzue;
import javax.annotation.concurrent.Immutable;

@Immutable
public class FirebaseVisionOnDeviceImageLabelerOptions {
    private final float zzavp;

    public static class Builder {
        private float zzavp = 0.5f;

        public Builder setConfidenceThreshold(float f) {
            boolean z = Float.compare(f, 0.0f) >= 0 && Float.compare(f, 1.0f) <= 0;
            Preconditions.checkArgument(z, "Confidence Threshold should be in range [0.0f, 1.0f].");
            this.zzavp = f;
            return this;
        }

        public FirebaseVisionOnDeviceImageLabelerOptions build() {
            return new FirebaseVisionOnDeviceImageLabelerOptions(this.zzavp, null);
        }
    }

    public float getConfidenceThreshold() {
        return this.zzavp;
    }

    private FirebaseVisionOnDeviceImageLabelerOptions(float f) {
        this.zzavp = f;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseVisionOnDeviceImageLabelerOptions)) {
            return false;
        }
        return this.zzavp == ((FirebaseVisionOnDeviceImageLabelerOptions) obj).zzavp;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.zzavp));
    }

    public final zzy zzno() {
        return (zzy) ((zzue) zzy.zzko().zzq(this.zzavp).zzrj());
    }

    /* synthetic */ FirebaseVisionOnDeviceImageLabelerOptions(float f, zzc zzc) {
        this(f);
    }
}
