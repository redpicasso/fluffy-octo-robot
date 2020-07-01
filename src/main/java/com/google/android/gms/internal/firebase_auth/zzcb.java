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

@Class(creator = "FinalizeMfaEnrollmentAidlRequestCreator")
public final class zzcb extends AbstractSafeParcelable {
    public static final Creator<zzcb> CREATOR = new zzca();
    @Field(getter = "getDisplayName", id = 3)
    private final String zzjv;
    @Field(getter = "getPhoneAuthCredential", id = 1)
    private final PhoneAuthCredential zzke;
    @Field(getter = "getCachedTokenState", id = 2)
    private final String zzkf;

    @Constructor
    public zzcb(@Param(id = 1) PhoneAuthCredential phoneAuthCredential, @Param(id = 2) String str, @Param(id = 3) String str2) {
        this.zzke = phoneAuthCredential;
        this.zzkf = str;
        this.zzjv = str2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzke, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzkf, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzjv, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
