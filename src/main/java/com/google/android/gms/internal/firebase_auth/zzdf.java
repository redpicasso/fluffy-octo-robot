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

@Class(creator = "SignInWithEmailAndPasswordAidlRequestCreator")
public final class zzdf extends AbstractSafeParcelable {
    public static final Creator<zzdf> CREATOR = new zzde();
    @Field(getter = "getTenantId", id = 3)
    @Nullable
    private final String zzhy;
    @Field(getter = "getEmail", id = 1)
    private final String zzif;
    @Field(getter = "getPassword", id = 2)
    private final String zzig;

    @Constructor
    public zzdf(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) @Nullable String str3) {
        this.zzif = str;
        this.zzig = str2;
        this.zzhy = str3;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String getPassword() {
        return this.zzig;
    }

    @Nullable
    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzig, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
