package com.google.firebase.ml.common.modeldownload;

import android.annotation.TargetApi;
import androidx.annotation.RequiresApi;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.firebase_ml.zzmd.zzo.zzb;
import com.google.android.gms.internal.firebase_ml.zzue;

public class FirebaseModelDownloadConditions {
    private final boolean zzarw;
    private final boolean zzarx;
    private final boolean zzary;

    public static class Builder {
        private boolean zzarw = false;
        private boolean zzarx = false;
        private boolean zzary = false;

        @RequiresApi(24)
        @TargetApi(24)
        public Builder requireCharging() {
            this.zzarw = true;
            return this;
        }

        public Builder requireWifi() {
            this.zzarx = true;
            return this;
        }

        @RequiresApi(24)
        @TargetApi(24)
        public Builder requireDeviceIdle() {
            this.zzary = true;
            return this;
        }

        public FirebaseModelDownloadConditions build() {
            return new FirebaseModelDownloadConditions(this.zzarw, this.zzarx, this.zzary, null);
        }
    }

    private FirebaseModelDownloadConditions(boolean z, boolean z2, boolean z3) {
        this.zzarw = z;
        this.zzarx = z2;
        this.zzary = z3;
    }

    public boolean isChargingRequired() {
        return this.zzarw;
    }

    public boolean isWifiRequired() {
        return this.zzarx;
    }

    public boolean isDeviceIdleRequired() {
        return this.zzary;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FirebaseModelDownloadConditions)) {
            return false;
        }
        FirebaseModelDownloadConditions firebaseModelDownloadConditions = (FirebaseModelDownloadConditions) obj;
        return this.zzarw == firebaseModelDownloadConditions.zzarw && this.zzary == firebaseModelDownloadConditions.zzary && this.zzarx == firebaseModelDownloadConditions.zzarx;
    }

    public int hashCode() {
        return Objects.hashCode(Boolean.valueOf(this.zzarw), Boolean.valueOf(this.zzarx), Boolean.valueOf(this.zzary));
    }

    public final zzb zzmi() {
        return (zzb) ((zzue) zzb.zzjs().zzr(this.zzarw).zzt(this.zzary).zzs(this.zzarx).zzrj());
    }

    /* synthetic */ FirebaseModelDownloadConditions(boolean z, boolean z2, boolean z3, zzc zzc) {
        this(z, z2, z3);
    }
}
