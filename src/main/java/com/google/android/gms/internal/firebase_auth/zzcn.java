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

@Class(creator = "LinkPhoneAuthCredentialAidlRequestCreator")
public final class zzcn extends AbstractSafeParcelable {
    public static final Creator<zzcn> CREATOR = new zzcm();
    @Field(getter = "getCachedState", id = 1)
    private final String zzii;
    @Field(getter = "getCredential", id = 2)
    private final PhoneAuthCredential zzkj;

    @Constructor
    public zzcn(@Param(id = 1) String str, @Param(id = 2) PhoneAuthCredential phoneAuthCredential) {
        this.zzii = str;
        this.zzkj = phoneAuthCredential;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final PhoneAuthCredential zzdi() {
        return this.zzkj;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzii, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzkj, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
