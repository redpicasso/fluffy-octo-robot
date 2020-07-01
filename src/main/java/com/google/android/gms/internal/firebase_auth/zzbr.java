package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "ChangePasswordAidlRequestCreator")
public final class zzbr extends AbstractSafeParcelable {
    public static final Creator<zzbr> CREATOR = new zzbq();
    @Field(getter = "getPassword", id = 2)
    private final String zzig;
    @Field(getter = "getCachedState", id = 1)
    private final String zzii;

    @Constructor
    public zzbr(@Param(id = 1) String str, @Param(id = 2) String str2) {
        this.zzii = str;
        this.zzig = str2;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final String getPassword() {
        return this.zzig;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzii, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzig, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
