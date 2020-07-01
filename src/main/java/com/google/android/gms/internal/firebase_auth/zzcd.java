package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.firebase.auth.PhoneAuthCredential;

@Class(creator = "FinalizeMfaSignInAidlRequestCreator")
public final class zzcd extends AbstractSafeParcelable {
    public static final Creator<zzcd> CREATOR = new zzcc();
    @Field(getter = "getPhoneAuthCredential", id = 1)
    private final PhoneAuthCredential zzke;
    @Field(getter = "getPendingCredential", id = 2)
    private final String zzkg;

    @Constructor
    public zzcd(@Param(id = 1) PhoneAuthCredential phoneAuthCredential, @Param(id = 2) String str) {
        this.zzke = phoneAuthCredential;
        this.zzkg = str;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzke, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzkg, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
