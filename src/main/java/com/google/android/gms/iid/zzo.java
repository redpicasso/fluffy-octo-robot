package com.google.android.gms.iid;

import android.util.Base64;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.util.VisibleForTesting;
import java.security.KeyPair;

final class zzo {
    private final KeyPair zzcb;
    private final long zzcc;

    @VisibleForTesting
    zzo(KeyPair keyPair, long j) {
        this.zzcb = keyPair;
        this.zzcc = j;
    }

    final KeyPair getKeyPair() {
        return this.zzcb;
    }

    final long getCreationTime() {
        return this.zzcc;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzo)) {
            return false;
        }
        zzo zzo = (zzo) obj;
        if (this.zzcc == zzo.zzcc && this.zzcb.getPublic().equals(zzo.zzcb.getPublic()) && this.zzcb.getPrivate().equals(zzo.zzcb.getPrivate())) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzcb.getPublic(), this.zzcb.getPrivate(), Long.valueOf(this.zzcc));
    }

    private final String zzq() {
        return Base64.encodeToString(this.zzcb.getPublic().getEncoded(), 11);
    }

    private final String zzr() {
        return Base64.encodeToString(this.zzcb.getPrivate().getEncoded(), 11);
    }
}
