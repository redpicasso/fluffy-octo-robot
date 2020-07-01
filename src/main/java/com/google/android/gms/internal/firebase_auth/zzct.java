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
import com.google.firebase.auth.ActionCodeSettings;

@Class(creator = "SendGetOobConfirmationCodeEmailAidlRequestCreator")
public final class zzct extends AbstractSafeParcelable {
    public static final Creator<zzct> CREATOR = new zzcs();
    @Field(getter = "getTenantId", id = 3)
    @Nullable
    private final String zzhy;
    @Field(getter = "getEmail", id = 1)
    private final String zzif;
    @Field(getter = "getActionCodeSettings", id = 2)
    private final ActionCodeSettings zzkk;

    @Constructor
    public zzct(@Param(id = 1) String str, @Param(id = 2) ActionCodeSettings actionCodeSettings, @Param(id = 3) @Nullable String str2) {
        this.zzif = str;
        this.zzkk = actionCodeSettings;
        this.zzhy = str2;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final ActionCodeSettings zzdj() {
        return this.zzkk;
    }

    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzif, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzkk, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
