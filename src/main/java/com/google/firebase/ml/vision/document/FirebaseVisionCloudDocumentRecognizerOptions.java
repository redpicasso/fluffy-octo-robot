package com.google.firebase.ml.vision.document;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseVisionCloudDocumentRecognizerOptions {
    private final boolean zzawj;
    private final List<String> zzaxr;

    public static class Builder {
        private boolean zzawj = false;
        private List<String> zzaxr = new ArrayList();

        public Builder setLanguageHints(@NonNull List<String> list) {
            Preconditions.checkNotNull(list, "Provided hinted languages can not be null");
            this.zzaxr = list;
            Collections.sort(this.zzaxr);
            return this;
        }

        public Builder enforceCertFingerprintMatch() {
            this.zzawj = true;
            return this;
        }

        public FirebaseVisionCloudDocumentRecognizerOptions build() {
            return new FirebaseVisionCloudDocumentRecognizerOptions(this.zzaxr, this.zzawj, null);
        }
    }

    public List<String> getHintedLanguages() {
        return this.zzaxr;
    }

    public final boolean isEnforceCertFingerprintMatch() {
        return this.zzawj;
    }

    private FirebaseVisionCloudDocumentRecognizerOptions(@NonNull List<String> list, boolean z) {
        Preconditions.checkNotNull(list, "Provided hinted languages can not be null");
        this.zzaxr = list;
        this.zzawj = z;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseVisionCloudDocumentRecognizerOptions)) {
            return false;
        }
        FirebaseVisionCloudDocumentRecognizerOptions firebaseVisionCloudDocumentRecognizerOptions = (FirebaseVisionCloudDocumentRecognizerOptions) obj;
        return this.zzaxr.equals(firebaseVisionCloudDocumentRecognizerOptions.getHintedLanguages()) && this.zzawj == firebaseVisionCloudDocumentRecognizerOptions.zzawj;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzaxr, Boolean.valueOf(this.zzawj));
    }

    /* synthetic */ FirebaseVisionCloudDocumentRecognizerOptions(List list, boolean z, zza zza) {
        this(list, z);
    }
}
