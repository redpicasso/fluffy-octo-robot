package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "UnlinkFederatedCredentialAidlRequestCreator")
public final class zzdt extends AbstractSafeParcelable {
    public static final Creator<zzdt> CREATOR = new zzds();
    @Field(getter = "getCachedState", id = 2)
    private final String zzii;
    @Field(getter = "getProvider", id = 1)
    private final String zzks;

    @Constructor
    public zzdt(@Param(id = 1) String str, @Param(id = 2) String str2) {
        this.zzks = str;
        this.zzii = str2;
    }

    public final String getProvider() {
        return this.zzks;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzks, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzii, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
