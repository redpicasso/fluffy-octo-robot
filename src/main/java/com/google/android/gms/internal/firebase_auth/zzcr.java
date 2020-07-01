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

@Class(creator = "SendEmailVerificationWithSettingsAidlRequestCreator")
public final class zzcr extends AbstractSafeParcelable {
    public static final Creator<zzcr> CREATOR = new zzcq();
    @Field(getter = "getToken", id = 1)
    private final String zzji;
    @Field(getter = "getActionCodeSettings", id = 2)
    @Nullable
    private final ActionCodeSettings zzkk;

    @Constructor
    public zzcr(@Param(id = 1) String str, @Param(id = 2) @Nullable ActionCodeSettings actionCodeSettings) {
        this.zzji = str;
        this.zzkk = actionCodeSettings;
    }

    public final String getToken() {
        return this.zzji;
    }

    @Nullable
    public final ActionCodeSettings zzdj() {
        return this.zzkk;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzji, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzkk, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
