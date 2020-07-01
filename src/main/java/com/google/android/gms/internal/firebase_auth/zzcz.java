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

@Class(creator = "SignInAnonymouslyAidlRequestCreator")
public final class zzcz extends AbstractSafeParcelable {
    public static final Creator<zzcz> CREATOR = new zzcy();
    @Field(getter = "getTenantId", id = 1)
    @Nullable
    private final String zzhy;

    @Constructor
    public zzcz(@Param(id = 1) @Nullable String str) {
        this.zzhy = str;
    }

    @Nullable
    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
