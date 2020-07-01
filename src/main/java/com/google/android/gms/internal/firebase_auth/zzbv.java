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

@Class(creator = "ConfirmPasswordResetAidlRequestCreator")
public final class zzbv extends AbstractSafeParcelable {
    public static final Creator<zzbv> CREATOR = new zzbu();
    @Field(getter = "getCode", id = 1)
    private final String zzhu;
    @Field(getter = "getTenantId", id = 3)
    @Nullable
    private final String zzhy;
    @Field(getter = "getNewPassword", id = 2)
    private final String zzkd;

    @Constructor
    public zzbv(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) @Nullable String str3) {
        this.zzhu = str;
        this.zzkd = str2;
        this.zzhy = str3;
    }

    public final String zzcn() {
        return this.zzhu;
    }

    public final String zzdg() {
        return this.zzkd;
    }

    @Nullable
    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzhu, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzkd, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
