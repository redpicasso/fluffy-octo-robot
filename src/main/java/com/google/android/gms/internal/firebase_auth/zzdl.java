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

@Class(creator = "StartMfaPhoneNumberEnrollmentAidlRequestCreator")
public final class zzdl extends AbstractSafeParcelable {
    public static final Creator<zzdl> CREATOR = new zzdk();
    @Field(getter = "getLocaleHeader", id = 3)
    @Nullable
    private final String zzhq;
    @Field(getter = "getIdToken", id = 1)
    private final String zzib;
    @Field(getter = "getPhoneNumber", id = 2)
    private final String zzjo;
    @Field(getter = "getTimeoutInSeconds", id = 4)
    private final long zzko;
    @Field(getter = "getForceNewSmsVerificationSession", id = 5)
    private final boolean zzkp;
    @Field(getter = "getRequireSmsVerification", id = 6)
    private final boolean zzkq;

    @Constructor
    public zzdl(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) @Nullable String str3, @Param(id = 4) long j, @Param(id = 5) boolean z, @Param(id = 6) boolean z2) {
        this.zzib = str;
        this.zzjo = str2;
        this.zzhq = str3;
        this.zzko = j;
        this.zzkp = z;
        this.zzkq = z2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzib, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzjo, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhq, false);
        SafeParcelWriter.writeLong(parcel, 4, this.zzko);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzkp);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzkq);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
