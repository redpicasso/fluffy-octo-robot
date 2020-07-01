package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "UnenrollMfaAidlRequestCreator")
public final class zzdp extends AbstractSafeParcelable {
    public static final Creator<zzdp> CREATOR = new zzdo();
    @Field(getter = "getUid", id = 2)
    private final String zzju;
    @Field(getter = "getCachedTokenState", id = 1)
    private final String zzkf;

    @Constructor
    public zzdp(@Param(id = 1) String str, @Param(id = 2) String str2) {
        this.zzkf = str;
        this.zzju = str2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzkf, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzju, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
