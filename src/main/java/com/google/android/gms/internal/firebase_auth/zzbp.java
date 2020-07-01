package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "ChangeEmailAidlRequestCreator")
public final class zzbp extends AbstractSafeParcelable {
    public static final Creator<zzbp> CREATOR = new zzbo();
    @Field(getter = "getEmail", id = 2)
    private final String zzif;
    @Field(getter = "getCachedState", id = 1)
    private final String zzii;

    @Constructor
    public zzbp(@Param(id = 1) String str, @Param(id = 2) String str2) {
        this.zzii = str;
        this.zzif = str2;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzii, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzif, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
