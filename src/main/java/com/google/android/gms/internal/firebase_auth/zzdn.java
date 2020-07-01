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
import com.google.firebase.auth.zzac;

@Class(creator = "StartMfaPhoneNumberSignInAidlRequestCreator")
public final class zzdn extends AbstractSafeParcelable {
    public static final Creator<zzdn> CREATOR = new zzdm();
    @Field(getter = "getLocaleHeader", id = 3)
    @Nullable
    private final String zzhq;
    @Field(getter = "getPendingCredential", id = 2)
    private final String zzkg;
    @Field(getter = "getTimeoutInSeconds", id = 4)
    private final long zzko;
    @Field(getter = "getForceNewSmsVerificationSession", id = 5)
    private final boolean zzkp;
    @Field(getter = "getRequireSmsVerification", id = 6)
    private final boolean zzkq;
    @Field(getter = "getPhoneMultiFactorInfo", id = 1)
    private zzac zzkr;

    @Constructor
    public zzdn(@Param(id = 1) zzac zzac, @Param(id = 2) String str, @Param(id = 3) @Nullable String str2, @Param(id = 4) long j, @Param(id = 5) boolean z, @Param(id = 6) boolean z2) {
        this.zzkr = zzac;
        this.zzkg = str;
        this.zzhq = str2;
        this.zzko = j;
        this.zzkp = z;
        this.zzkq = z2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkr, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzkg, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhq, false);
        SafeParcelWriter.writeLong(parcel, 4, this.zzko);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzkp);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzkq);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
