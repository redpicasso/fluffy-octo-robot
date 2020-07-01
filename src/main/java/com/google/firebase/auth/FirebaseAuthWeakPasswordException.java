package com.google.firebase.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class FirebaseAuthWeakPasswordException extends FirebaseAuthInvalidCredentialsException {
    private final String zzjf;

    public FirebaseAuthWeakPasswordException(@NonNull String str, @NonNull String str2, @Nullable String str3) {
        super(str, str2);
        this.zzjf = str3;
    }

    @Nullable
    public final String getReason() {
        return this.zzjf;
    }
}
