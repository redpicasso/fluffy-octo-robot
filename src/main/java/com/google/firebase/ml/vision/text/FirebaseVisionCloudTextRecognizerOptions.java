package com.google.firebase.ml.vision.text;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseVisionCloudTextRecognizerOptions {
    public static final int DENSE_MODEL = 2;
    public static final int SPARSE_MODEL = 1;
    private final boolean zzawj;
    private final List<String> zzaxr;
    private final int zzazl;

    public static class Builder {
        private boolean zzawj = false;
        private List<String> zzaxr = new ArrayList();
        private int zzazl = 1;

        public Builder setLanguageHints(@NonNull List<String> list) {
            Preconditions.checkNotNull(list, "Provided hinted languages can not be null");
            this.zzaxr = list;
            Collections.sort(this.zzaxr);
            return this;
        }

        public Builder setModelType(int i) {
            boolean z = true;
            if (!(i == 1 || i == 2)) {
                z = false;
            }
            Preconditions.checkArgument(z, "modelType should be either SPARSE_MODEL or DENSE_MODEL");
            this.zzazl = i;
            return this;
        }

        public Builder enforceCertFingerprintMatch() {
            this.zzawj = true;
            return this;
        }

        public FirebaseVisionCloudTextRecognizerOptions build() {
            return new FirebaseVisionCloudTextRecognizerOptions(this.zzaxr, this.zzazl, this.zzawj, null);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CloudTextModelType {
    }

    public List<String> getHintedLanguages() {
        return this.zzaxr;
    }

    public final boolean isEnforceCertFingerprintMatch() {
        return this.zzawj;
    }

    public int getModelType() {
        return this.zzazl;
    }

    private FirebaseVisionCloudTextRecognizerOptions(@NonNull List<String> list, int i, boolean z) {
        Preconditions.checkNotNull(list, "Provided hinted languages can not be null");
        this.zzaxr = list;
        this.zzazl = i;
        this.zzawj = z;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseVisionCloudTextRecognizerOptions)) {
            return false;
        }
        FirebaseVisionCloudTextRecognizerOptions firebaseVisionCloudTextRecognizerOptions = (FirebaseVisionCloudTextRecognizerOptions) obj;
        return this.zzaxr.equals(firebaseVisionCloudTextRecognizerOptions.getHintedLanguages()) && this.zzazl == firebaseVisionCloudTextRecognizerOptions.zzazl && this.zzawj == firebaseVisionCloudTextRecognizerOptions.zzawj;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzaxr, Integer.valueOf(this.zzazl), Boolean.valueOf(this.zzawj));
    }

    /* synthetic */ FirebaseVisionCloudTextRecognizerOptions(List list, int i, boolean z, zza zza) {
        this(list, i, z);
    }
}
