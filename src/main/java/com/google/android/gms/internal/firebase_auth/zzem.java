package com.google.android.gms.internal.firebase_auth;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.firebase.auth.zzf;
import java.util.List;

@Class(creator = "GetAccountInfoUserCreator")
@Reserved({1})
public final class zzem extends AbstractSafeParcelable {
    public static final Creator<zzem> CREATOR = new zzep();
    @Field(getter = "getEmail", id = 3)
    private String zzif;
    @Field(getter = "getPassword", id = 8)
    private String zzig;
    @Field(getter = "getPhoneNumber", id = 9)
    private String zzjo;
    @Field(getter = "getDisplayName", id = 5)
    private String zzjv;
    @Field(getter = "getPhotoUrl", id = 6)
    private String zzkc;
    @Field(getter = "getDefaultOAuthCredential", id = 13)
    private zzf zzkw;
    @Field(getter = "getMfaInfoList", id = 14)
    private List<zzeu> zzky;
    @Field(getter = "getLocalId", id = 2)
    private String zzrf;
    @Field(getter = "isNewUser", id = 12)
    private boolean zzrg;
    @Field(getter = "isEmailVerified", id = 4)
    private boolean zzrp;
    @Field(getter = "getProviderInfoList", id = 7)
    private zzey zzrq;
    @Field(getter = "getCreationTimestamp", id = 10)
    private long zzrr;
    @Field(getter = "getLastSignInTimestamp", id = 11)
    private long zzrs;

    public zzem() {
        this.zzrq = new zzey();
    }

    @Constructor
    public zzem(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) boolean z, @Param(id = 5) String str3, @Param(id = 6) String str4, @Param(id = 7) zzey zzey, @Param(id = 8) String str5, @Param(id = 9) String str6, @Param(id = 10) long j, @Param(id = 11) long j2, @Param(id = 12) boolean z2, @Param(id = 13) zzf zzf, @Param(id = 14) List<zzeu> list) {
        zzey zzey2;
        List list2;
        this.zzrf = str;
        this.zzif = str2;
        this.zzrp = z;
        this.zzjv = str3;
        this.zzkc = str4;
        if (zzey == null) {
            zzey2 = new zzey();
        } else {
            zzey2 = zzey.zza(zzey);
        }
        this.zzrq = zzey2;
        this.zzig = str5;
        this.zzjo = str6;
        this.zzrr = j;
        this.zzrs = j2;
        this.zzrg = z2;
        this.zzkw = zzf;
        if (list2 == null) {
            list2 = zzay.zzce();
        }
        this.zzky = list2;
    }

    @Nullable
    public final String getEmail() {
        return this.zzif;
    }

    public final boolean isEmailVerified() {
        return this.zzrp;
    }

    @NonNull
    public final String getLocalId() {
        return this.zzrf;
    }

    @Nullable
    public final String getDisplayName() {
        return this.zzjv;
    }

    @Nullable
    public final Uri getPhotoUri() {
        return !TextUtils.isEmpty(this.zzkc) ? Uri.parse(this.zzkc) : null;
    }

    @Nullable
    public final String getPhoneNumber() {
        return this.zzjo;
    }

    public final long getCreationTimestamp() {
        return this.zzrr;
    }

    public final long getLastSignInTimestamp() {
        return this.zzrs;
    }

    public final boolean isNewUser() {
        return this.zzrg;
    }

    @NonNull
    public final zzem zzcf(@Nullable String str) {
        this.zzif = str;
        return this;
    }

    @NonNull
    public final zzem zzcg(@Nullable String str) {
        this.zzjv = str;
        return this;
    }

    @NonNull
    public final zzem zzch(@Nullable String str) {
        this.zzkc = str;
        return this;
    }

    @NonNull
    public final zzem zzci(String str) {
        Preconditions.checkNotEmpty(str);
        this.zzig = str;
        return this;
    }

    @NonNull
    public final zzem zzc(List<zzew> list) {
        Preconditions.checkNotNull(list);
        this.zzrq = new zzey();
        this.zzrq.zzes().addAll(list);
        return this;
    }

    public final zzem zzo(boolean z) {
        this.zzrg = z;
        return this;
    }

    @NonNull
    public final List<zzew> zzes() {
        return this.zzrq.zzes();
    }

    public final zzey zzet() {
        return this.zzrq;
    }

    @Nullable
    public final zzf zzdo() {
        return this.zzkw;
    }

    @NonNull
    public final zzem zza(zzf zzf) {
        this.zzkw = zzf;
        return this;
    }

    @NonNull
    public final List<zzeu> zzbc() {
        return this.zzky;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzrf, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzif, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzrp);
        SafeParcelWriter.writeString(parcel, 5, this.zzjv, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzkc, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzrq, i, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzig, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzjo, false);
        SafeParcelWriter.writeLong(parcel, 10, this.zzrr);
        SafeParcelWriter.writeLong(parcel, 11, this.zzrs);
        SafeParcelWriter.writeBoolean(parcel, 12, this.zzrg);
        SafeParcelWriter.writeParcelable(parcel, 13, this.zzkw, i, false);
        SafeParcelWriter.writeTypedList(parcel, 14, this.zzky, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
