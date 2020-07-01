package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.firebase.auth.zzf;

@Class(creator = "OnFailedIdpSignInAidlResponseCreator")
public final class zzdz extends AbstractSafeParcelable {
    public static final Creator<zzdz> CREATOR = new zzdy();
    @Field(getter = "getTenantId", id = 4)
    private final String zzhy;
    @Field(getter = "getEmail", id = 3)
    private final String zzif;
    @Field(getter = "getStatus", id = 1)
    private final Status zzkv;
    @Field(getter = "getDefaultOAuthCredential", id = 2)
    private final zzf zzkw;

    @Constructor
    public zzdz(@Param(id = 1) Status status, @Param(id = 2) zzf zzf, @Param(id = 3) String str, @Param(id = 4) @Nullable String str2) {
        this.zzkv = status;
        this.zzkw = zzf;
        this.zzif = str;
        this.zzhy = str2;
    }

    public final Status getStatus() {
        return this.zzkv;
    }

    public final zzf zzdo() {
        return this.zzkw;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkv, i, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzkw, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
