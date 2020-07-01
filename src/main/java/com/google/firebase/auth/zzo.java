package com.google.firebase.auth;

import com.google.android.gms.common.api.Status;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.internal.zzaa;

final class zzo implements zzaa {
    private final /* synthetic */ FirebaseAuth zziy;
    private final /* synthetic */ FirebaseUser zzjc;

    zzo(FirebaseAuth firebaseAuth, FirebaseUser firebaseUser) {
        this.zziy = firebaseAuth;
        this.zzjc = firebaseUser;
    }

    public final void zzcv() {
        if (this.zziy.zzip.getUid().equalsIgnoreCase(this.zzjc.getUid())) {
            this.zziy.zzcs();
        }
    }

    public final void zza(Status status) {
        if (status.getStatusCode() == FirebaseError.ERROR_USER_NOT_FOUND || status.getStatusCode() == FirebaseError.ERROR_USER_TOKEN_EXPIRED || status.getStatusCode() == FirebaseError.ERROR_USER_DISABLED) {
            this.zziy.signOut();
        }
    }
}
