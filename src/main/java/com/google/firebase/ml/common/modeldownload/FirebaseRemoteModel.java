package com.google.firebase.ml.common.modeldownload;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzmd.zzo;
import com.google.android.gms.internal.firebase_ml.zzmd.zzo.zza;
import com.google.android.gms.internal.firebase_ml.zzmd.zzu;
import com.google.android.gms.internal.firebase_ml.zzmd.zzu.zzb;
import com.google.android.gms.internal.firebase_ml.zzue;
import java.util.HashMap;
import java.util.Map;

public class FirebaseRemoteModel {
    private static final Map<zza, String> zzasd = new HashMap();
    @VisibleForTesting
    private static final Map<zza, String> zzase;
    @Nullable
    private final String zzaqg;
    private String zzaqz;
    @Nullable
    private final zza zzasc;
    private final boolean zzasf;
    private final FirebaseModelDownloadConditions zzasg;
    private final FirebaseModelDownloadConditions zzash;

    public static class Builder {
        @Nullable
        private final String zzaqg;
        @Nullable
        private final zza zzasc;
        private boolean zzasf = true;
        private FirebaseModelDownloadConditions zzasg = new com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions.Builder().build();
        private FirebaseModelDownloadConditions zzash = new com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions.Builder().build();

        public Builder(@NonNull String str) {
            this.zzaqg = str;
            this.zzasc = null;
        }

        public Builder enableModelUpdates(boolean z) {
            this.zzasf = z;
            return this;
        }

        public Builder setInitialDownloadConditions(@NonNull FirebaseModelDownloadConditions firebaseModelDownloadConditions) {
            this.zzasg = firebaseModelDownloadConditions;
            return this;
        }

        public Builder setUpdatesDownloadConditions(@NonNull FirebaseModelDownloadConditions firebaseModelDownloadConditions) {
            this.zzash = firebaseModelDownloadConditions;
            return this;
        }

        public FirebaseRemoteModel build() {
            Preconditions.checkArgument(TextUtils.isEmpty(this.zzaqg) ^ 1, "One of cloud model name and base model cannot be empty");
            Preconditions.checkNotNull(this.zzasg, "Initial download condition cannot be null");
            Preconditions.checkNotNull(this.zzash, "Update download condition cannot be null");
            return new FirebaseRemoteModel(this.zzaqg, null, this.zzasf, this.zzasg, this.zzash, null);
        }
    }

    private FirebaseRemoteModel(@Nullable String str, @Nullable zza zza, boolean z, @NonNull FirebaseModelDownloadConditions firebaseModelDownloadConditions, @NonNull FirebaseModelDownloadConditions firebaseModelDownloadConditions2) {
        this.zzaqg = str;
        this.zzasc = zza;
        this.zzasf = z;
        this.zzasg = firebaseModelDownloadConditions;
        this.zzash = firebaseModelDownloadConditions2;
    }

    @NonNull
    public final String zzmj() {
        String str = this.zzaqg;
        if (str != null) {
            return str;
        }
        return (String) zzase.get(this.zzasc);
    }

    public final boolean zzmk() {
        return this.zzasc != null;
    }

    @Nullable
    public String getModelName() {
        return this.zzaqg;
    }

    public boolean isModelUpdatesEnabled() {
        return this.zzasf;
    }

    public FirebaseModelDownloadConditions getInitialDownloadConditions() {
        return this.zzasg;
    }

    public FirebaseModelDownloadConditions getUpdatesDownloadConditions() {
        return this.zzash;
    }

    public final boolean zzcd(@NonNull String str) {
        zza zza = this.zzasc;
        if (zza == null) {
            return false;
        }
        return str.equals(zzasd.get(zza));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseRemoteModel)) {
            return false;
        }
        FirebaseRemoteModel firebaseRemoteModel = (FirebaseRemoteModel) obj;
        return Objects.equal(this.zzaqg, firebaseRemoteModel.zzaqg) && Objects.equal(this.zzasc, firebaseRemoteModel.zzasc) && this.zzasf == firebaseRemoteModel.zzasf && this.zzasg.equals(firebaseRemoteModel.zzasg) && this.zzash.equals(firebaseRemoteModel.zzash);
    }

    public int hashCode() {
        r0 = new Object[5];
        r0[3] = Integer.valueOf(Objects.hashCode(this.zzasg));
        r0[4] = Integer.valueOf(Objects.hashCode(this.zzash));
        return Objects.hashCode(r0);
    }

    public final void zzce(@NonNull String str) {
        this.zzaqz = str;
    }

    public final zzo zzmh() {
        zza zzd = zzo.zzjq().zzc(this.zzasg.zzmi()).zzd(this.zzash.zzmi());
        zzu.zza zzb = zzu.zzkg().zzbe(zzmj()).zzb(zzb.CLOUD);
        String str = this.zzaqz;
        if (str == null) {
            str = "";
        }
        return (zzo) ((zzue) zzd.zzb(zzb.zzbg(str)).zzn(this.zzasf).zzrj());
    }

    /* synthetic */ FirebaseRemoteModel(String str, zza zza, boolean z, FirebaseModelDownloadConditions firebaseModelDownloadConditions, FirebaseModelDownloadConditions firebaseModelDownloadConditions2, zzd zzd) {
        this(str, null, z, firebaseModelDownloadConditions, firebaseModelDownloadConditions2);
    }

    static {
        Map hashMap = new HashMap();
        zzase = hashMap;
        hashMap.put(zza.FACE_DETECTION, "face_detector_model_m41");
        zzase.put(zza.SMART_REPLY, "smart_reply_model_m41");
        zzase.put(zza.TRANSLATE, "translate_model_m41");
        String str = "modelHash";
        zzasd.put(zza.FACE_DETECTION, str);
        zzasd.put(zza.SMART_REPLY, "smart_reply_model_hash");
        zzasd.put(zza.TRANSLATE, str);
    }
}
