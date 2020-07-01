package com.google.android.gms.internal.firebase_auth;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "ProviderUserInfoCreator")
@Reserved({1})
public final class zzew extends AbstractSafeParcelable {
    public static final Creator<zzew> CREATOR = new zzez();
    @Field(getter = "getProviderId", id = 5)
    private String zzia;
    @Field(getter = "getEmail", id = 8)
    private String zzif;
    @Field(getter = "getPhoneNumber", id = 7)
    private String zzjo;
    @Field(getter = "getDisplayName", id = 3)
    private String zzjv;
    @Field(getter = "getPhotoUrl", id = 4)
    private String zzkc;
    @Field(getter = "getFederatedId", id = 2)
    private String zzsc;
    @Field(getter = "getRawUserInfo", id = 6)
    private String zzsd;

    @Constructor
    zzew(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3, @Param(id = 5) String str4, @Param(id = 6) String str5, @Param(id = 7) String str6, @Param(id = 8) String str7) {
        this.zzsc = str;
        this.zzjv = str2;
        this.zzkc = str3;
        this.zzia = str4;
        this.zzsd = str5;
        this.zzjo = str6;
        this.zzif = str7;
    }

    public final String zzbo() {
        return this.zzsc;
    }

    @Nullable
    public final String getDisplayName() {
        return this.zzjv;
    }

    @Nullable
    public final Uri getPhotoUri() {
        return !TextUtils.isEmpty(this.zzkc) ? Uri.parse(this.zzkc) : null;
    }

    public final String getProviderId() {
        return this.zzia;
    }

    public final String getPhoneNumber() {
        return this.zzjo;
    }

    public final void zzco(String str) {
        this.zzsd = str;
    }

    @Nullable
    public final String getRawUserInfo() {
        return this.zzsd;
    }

    @Nullable
    public final String getEmail() {
        return this.zzif;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzsc, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzjv, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzkc, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzia, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzsd, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzjo, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzif, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
