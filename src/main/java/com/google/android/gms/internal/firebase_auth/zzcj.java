package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "LinkEmailAuthCredentialAidlRequestCreator")
public final class zzcj extends AbstractSafeParcelable {
    public static final Creator<zzcj> CREATOR = new zzci();
    @Field(getter = "getEmail", id = 1)
    private final String zzif;
    @Field(getter = "getPassword", id = 2)
    private final String zzig;
    @Field(getter = "getCachedState", id = 3)
    private final String zzii;

    @Constructor
    public zzcj(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) String str3) {
        this.zzif = str;
        this.zzig = str2;
        this.zzii = str3;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String getPassword() {
        return this.zzig;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzig, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzii, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
