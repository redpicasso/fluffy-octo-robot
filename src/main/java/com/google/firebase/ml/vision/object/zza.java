package com.google.firebase.ml.vision.object;

import com.google.android.gms.common.internal.Objects;

public final class zza {
    private final int zzaze;
    private final boolean zzazf;
    private final boolean zzazg;

    private zza(int i, boolean z, boolean z2) {
        this.zzaze = i;
        this.zzazf = z;
        this.zzazg = z2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zza)) {
            return false;
        }
        zza zza = (zza) obj;
        return zza.zzaze == this.zzaze && zza.zzazg == this.zzazg && zza.zzazf == this.zzazf;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzaze), Boolean.valueOf(this.zzazg), Boolean.valueOf(this.zzazf));
    }

    /* synthetic */ zza(int i, boolean z, boolean z2, zzb zzb) {
        this(i, false, false);
    }
}
