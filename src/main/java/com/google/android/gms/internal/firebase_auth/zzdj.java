package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.firebase.auth.PhoneAuthCredential;

@Class(creator = "SignInWithPhoneNumberAidlRequestCreator")
public final class zzdj extends AbstractSafeParcelable {
    public static final Creator<zzdj> CREATOR = new zzdi();
    @Field(getter = "getTenantId", id = 2)
    @Nullable
    private final String zzhy;
    @Field(getter = "getCredential", id = 1)
    private final PhoneAuthCredential zzkj;

    @Constructor
    public zzdj(@Param(id = 1) PhoneAuthCredential phoneAuthCredential, @Param(id = 2) @Nullable String str) {
        this.zzkj = phoneAuthCredential;
        this.zzhy = str;
    }

    public final PhoneAuthCredential zzdi() {
        return this.zzkj;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkj, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
