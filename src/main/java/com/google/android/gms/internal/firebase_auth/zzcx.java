package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "SetFirebaseUiVersionAidlRequestCreator")
public final class zzcx extends AbstractSafeParcelable {
    public static final Creator<zzcx> CREATOR = new zzcw();
    @Field(getter = "getFirebaseUiVersion", id = 1)
    private final String zzkm;

    @Constructor
    public zzcx(@Param(id = 1) String str) {
        this.zzkm = str;
    }

    public final String zzdl() {
        return this.zzkm;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzkm, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
