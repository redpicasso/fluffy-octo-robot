package com.google.firebase.ml.vision.label;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.firebase_ml.zzjb;
import com.google.android.gms.internal.firebase_ml.zzpm;
import com.google.android.gms.vision.label.ImageLabel;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class FirebaseVisionImageLabel {
    private final String text;
    private final float zzatw;
    private final String zzavo;

    public FirebaseVisionImageLabel(@NonNull ImageLabel imageLabel) {
        this(imageLabel.getLabel(), imageLabel.getConfidence(), imageLabel.getMid());
    }

    @Nullable
    public static FirebaseVisionImageLabel zza(@Nullable zzjb zzjb) {
        if (zzjb == null) {
            return null;
        }
        return new FirebaseVisionImageLabel(zzjb.getDescription(), zzpm.zza(zzjb.zzhv()), zzjb.getMid());
    }

    @VisibleForTesting
    private FirebaseVisionImageLabel(@Nullable String str, float f, @Nullable String str2) {
        if (str == null) {
            str = "";
        }
        this.text = str;
        this.zzavo = str2;
        float f2 = 0.0f;
        if (Float.compare(f, 0.0f) >= 0) {
            f2 = Float.compare(f, 1.0f) > 0 ? 1.0f : f;
        }
        this.zzatw = f2;
    }

    @Nullable
    public String getEntityId() {
        return this.zzavo;
    }

    public String getText() {
        return this.text;
    }

    public float getConfidence() {
        return this.zzatw;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseVisionImageLabel)) {
            return false;
        }
        FirebaseVisionImageLabel firebaseVisionImageLabel = (FirebaseVisionImageLabel) obj;
        return Objects.equal(this.zzavo, firebaseVisionImageLabel.getEntityId()) && Objects.equal(this.text, firebaseVisionImageLabel.getText()) && Float.compare(this.zzatw, firebaseVisionImageLabel.getConfidence()) == 0;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzavo, this.text, Float.valueOf(this.zzatw));
    }
}
