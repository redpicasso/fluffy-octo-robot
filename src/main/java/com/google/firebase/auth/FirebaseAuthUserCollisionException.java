package com.google.firebase.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class FirebaseAuthUserCollisionException extends FirebaseAuthException {
    @Nullable
    private String zzhy;
    @Nullable
    private String zzif;
    @Nullable
    private AuthCredential zzje;

    public FirebaseAuthUserCollisionException(@NonNull String str, @NonNull String str2) {
        super(str, str2);
    }

    public final FirebaseAuthUserCollisionException zzbt(String str) {
        this.zzif = str;
        return this;
    }

    public final FirebaseAuthUserCollisionException zza(AuthCredential authCredential) {
        this.zzje = authCredential;
        return this;
    }

    public final FirebaseAuthUserCollisionException zzbu(String str) {
        this.zzhy = str;
        return this;
    }

    @Nullable
    public final AuthCredential getUpdatedCredential() {
        return this.zzje;
    }
}
