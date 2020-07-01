package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.firebase.auth.UserProfileChangeRequest;

@Class(creator = "UpdateProfileAidlRequestCreator")
public final class zzdv extends AbstractSafeParcelable {
    public static final Creator<zzdv> CREATOR = new zzdu();
    @Field(getter = "getCachedState", id = 2)
    private final String zzii;
    @Field(getter = "getUserProfileChangeRequest", id = 1)
    private final UserProfileChangeRequest zzkt;

    @Constructor
    public zzdv(@Param(id = 1) UserProfileChangeRequest userProfileChangeRequest, @Param(id = 2) String str) {
        this.zzkt = userProfileChangeRequest;
        this.zzii = str;
    }

    public final UserProfileChangeRequest zzdn() {
        return this.zzkt;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkt, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzii, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
