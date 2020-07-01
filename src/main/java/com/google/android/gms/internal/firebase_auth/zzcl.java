package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "LinkFederatedCredentialAidlRequestCreator")
public final class zzcl extends AbstractSafeParcelable {
    public static final Creator<zzcl> CREATOR = new zzck();
    @Field(getter = "getCachedState", id = 1)
    private final String zzii;
    @Field(getter = "getVerifyAssertionRequest", id = 2)
    private final zzfm zzki;

    @Constructor
    public zzcl(@Param(id = 1) String str, @Param(id = 2) zzfm zzfm) {
        this.zzii = str;
        this.zzki = zzfm;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final zzfm zzdh() {
        return this.zzki;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzii, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzki, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
