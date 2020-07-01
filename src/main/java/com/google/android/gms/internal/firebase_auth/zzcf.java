package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "GetAccessTokenAidlRequestCreator")
public final class zzcf extends AbstractSafeParcelable {
    public static final Creator<zzcf> CREATOR = new zzce();
    @Field(getter = "getRefreshToken", id = 1)
    private final String zzkh;

    @Constructor
    public zzcf(@Param(id = 1) String str) {
        this.zzkh = str;
    }

    public final String zzs() {
        return this.zzkh;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzkh, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
