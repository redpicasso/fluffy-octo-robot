package com.google.firebase.auth;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.internal.zza;
import com.google.firebase.auth.internal.zzz;

final class zzn implements zza, zzz {
    private final /* synthetic */ FirebaseAuth zziy;

    zzn(FirebaseAuth firebaseAuth) {
        this.zziy = firebaseAuth;
    }

    public final void zza(@NonNull zzes zzes, @NonNull FirebaseUser firebaseUser) {
        this.zziy.zza(firebaseUser, zzes, true);
    }

    public final void zza(Status status) {
        int statusCode = status.getStatusCode();
        if (statusCode == FirebaseError.ERROR_USER_NOT_FOUND || statusCode == FirebaseError.ERROR_USER_TOKEN_EXPIRED || statusCode == FirebaseError.ERROR_USER_DISABLED) {
            this.zziy.signOut();
        }
    }
}
