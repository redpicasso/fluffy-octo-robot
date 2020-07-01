package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "EmailAuthCredentialCreator")
public class EmailAuthCredential extends AuthCredential {
    public static final Creator<EmailAuthCredential> CREATOR = new zzg();
    @Field(getter = "getEmail", id = 1)
    private String zzif;
    @Field(getter = "getPassword", id = 2)
    private String zzig;
    @Field(getter = "getSignInLink", id = 3)
    private final String zzih;
    @Field(getter = "getCachedState", id = 4)
    private String zzii;
    @Field(getter = "isForLinking", id = 5)
    private boolean zzij;

    @Constructor
    EmailAuthCredential(@Param(id = 1) @NonNull String str, @Param(id = 2) @NonNull String str2, @Param(id = 3) @NonNull String str3, @Param(id = 4) @NonNull String str4, @Param(id = 5) @NonNull boolean z) {
        this.zzif = Preconditions.checkNotEmpty(str);
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("Cannot create an EmailAuthCredential without a password or emailLink.");
        }
        this.zzig = str2;
        this.zzih = str3;
        this.zzii = str4;
        this.zzij = z;
    }

    @NonNull
    public String getProvider() {
        return "password";
    }

    EmailAuthCredential(String str, String str2) {
        this(str, str2, null, null, false);
    }

    @NonNull
    public final String getEmail() {
        return this.zzif;
    }

    @NonNull
    public final String getPassword() {
        return this.zzig;
    }

    @NonNull
    public final String zzco() {
        return this.zzih;
    }

    @Nullable
    public final String zzcp() {
        return this.zzii;
    }

    public final boolean zzcq() {
        return this.zzij;
    }

    public final EmailAuthCredential zza(@Nullable FirebaseUser firebaseUser) {
        this.zzii = firebaseUser.zzcz();
        this.zzij = true;
        return this;
    }

    public String getSignInMethod() {
        return !TextUtils.isEmpty(this.zzig) ? "password" : EmailAuthProvider.EMAIL_LINK_SIGN_IN_METHOD;
    }

    public final boolean zzcr() {
        return !TextUtils.isEmpty(this.zzih);
    }

    public static boolean isSignInWithEmailLink(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        zzb zzbr = zzb.zzbr(str);
        if (zzbr == null || zzbr.getOperation() != 4) {
            return false;
        }
        return true;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzig, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzih, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzii, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzij);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
