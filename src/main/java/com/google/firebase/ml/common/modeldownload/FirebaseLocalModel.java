package com.google.firebase.ml.common.modeldownload;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzmd.zzo;
import com.google.android.gms.internal.firebase_ml.zzmd.zzo.zza;
import com.google.android.gms.internal.firebase_ml.zzmd.zzu;
import com.google.android.gms.internal.firebase_ml.zzmd.zzu.zzb;
import com.google.android.gms.internal.firebase_ml.zzue;

public class FirebaseLocalModel {
    private final String zzaqg;
    private final String zzart;
    private final String zzaru;

    public static class Builder {
        private final String zzaqg;
        private String zzart = null;
        private String zzarv = null;

        public Builder(@NonNull String str) {
            Preconditions.checkNotEmpty(str, "Model name can not be empty");
            this.zzaqg = str;
        }

        public Builder setFilePath(@NonNull String str) {
            Preconditions.checkNotEmpty(str, "Model Source file path can not be empty");
            Preconditions.checkArgument(this.zzarv == null, "A local model source is either from local file or for asset, you can not set both.");
            this.zzart = str;
            return this;
        }

        public Builder setAssetFilePath(@NonNull String str) {
            Preconditions.checkNotEmpty(str, "Model Source file path can not be empty");
            Preconditions.checkArgument(this.zzart == null, "A local model source is either from local file or for asset, you can not set both.");
            this.zzarv = str;
            return this;
        }

        public FirebaseLocalModel build() {
            boolean z = (this.zzart != null && this.zzarv == null) || (this.zzart == null && this.zzarv != null);
            Preconditions.checkArgument(z, "Please set either filePath or assetFilePath.");
            return new FirebaseLocalModel(this.zzaqg, this.zzart, this.zzarv, null);
        }
    }

    public String getModelName() {
        return this.zzaqg;
    }

    @Nullable
    public String getFilePath() {
        return this.zzart;
    }

    @Nullable
    public String getAssetFilePath() {
        return this.zzaru;
    }

    private FirebaseLocalModel(@NonNull String str, @Nullable String str2, @Nullable String str3) {
        this.zzaqg = str;
        this.zzart = str2;
        this.zzaru = str3;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseLocalModel)) {
            return false;
        }
        FirebaseLocalModel firebaseLocalModel = (FirebaseLocalModel) obj;
        return Objects.equal(this.zzaqg, firebaseLocalModel.zzaqg) && Objects.equal(this.zzart, firebaseLocalModel.zzart) && Objects.equal(this.zzaru, firebaseLocalModel.zzaru);
    }

    public int hashCode() {
        return Objects.hashCode(this.zzaqg, this.zzart, this.zzaru);
    }

    public final zzo zzmh() {
        zzb zzb;
        zza zzjq = zzo.zzjq();
        zzu.zza zzkg = zzu.zzkg();
        String str = this.zzart;
        if (str == null) {
            str = this.zzaru;
        }
        zzkg = zzkg.zzbf(str);
        if (this.zzart != null) {
            zzb = zzb.LOCAL;
        } else if (this.zzaru != null) {
            zzb = zzb.APP_ASSET;
        } else {
            zzb = zzb.SOURCE_UNKNOWN;
        }
        return (zzo) ((zzue) zzjq.zzb(zzkg.zzb(zzb)).zzrj());
    }

    /* synthetic */ FirebaseLocalModel(String str, String str2, String str3, zzb zzb) {
        this(str, str2, str3);
    }
}
