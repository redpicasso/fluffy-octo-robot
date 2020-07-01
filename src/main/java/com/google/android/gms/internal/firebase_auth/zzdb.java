package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "SignInWithCredentialAidlRequestCreator")
public final class zzdb extends AbstractSafeParcelable {
    public static final Creator<zzdb> CREATOR = new zzda();
    @Field(getter = "getVerifyAssertionRequest", id = 1)
    private final zzfm zzki;

    @Constructor
    public zzdb(@Param(id = 1) zzfm zzfm) {
        this.zzki = zzfm;
    }

    public final zzfm zzdh() {
        return this.zzki;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzki, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
