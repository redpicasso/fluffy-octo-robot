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

@Class(creator = "SignInWithCustomTokenAidlRequestCreator")
public final class zzdd extends AbstractSafeParcelable {
    public static final Creator<zzdd> CREATOR = new zzdc();
    @Field(getter = "getTenantId", id = 2)
    @Nullable
    private final String zzhy;
    @Field(getter = "getToken", id = 1)
    private final String zzji;

    @Constructor
    public zzdd(@Param(id = 1) String str, @Param(id = 2) @Nullable String str2) {
        this.zzji = str;
        this.zzhy = str2;
    }

    public final String getToken() {
        return this.zzji;
    }

    @Nullable
    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzji, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
