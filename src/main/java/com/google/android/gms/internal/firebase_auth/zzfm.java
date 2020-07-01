package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.internal.firebase_auth.zzp.zzp;
import com.google.android.gms.internal.firebase_auth.zzp.zzp.zza;
import com.google.firebase.auth.api.internal.zzfd;

@Class(creator = "VerifyAssertionRequestCreator")
@Reserved({1})
public final class zzfm extends AbstractSafeParcelable implements zzfd<zzp> {
    public static final Creator<zzfm> CREATOR = new zzfp();
    @Field(getter = "getTenantId", id = 15)
    private String zzhy;
    @Field(getter = "getProviderId", id = 6)
    private String zzia;
    @Field(getter = "getIdToken", id = 4)
    private String zzib;
    @Field(getter = "getAccessToken", id = 5)
    private String zzic;
    @Field(getter = "getPendingToken", id = 17)
    @Nullable
    private String zzie;
    @Field(getter = "getEmail", id = 7)
    @Nullable
    private String zzif;
    @Field(getter = "getAutoCreate", id = 11)
    private boolean zzjp;
    @Field(getter = "getReturnSecureToken", id = 10)
    private boolean zzsj;
    @Field(getter = "getRequestUri", id = 2)
    private String zzsn;
    @Field(getter = "getCurrentIdToken", id = 3)
    private String zzso;
    @Field(getter = "getPostBody", id = 8)
    private String zzsp;
    @Field(getter = "getOauthTokenSecret", id = 9)
    private String zzsq;
    @Field(getter = "getAuthCode", id = 12)
    private String zzsr;
    @Field(getter = "getSessionId", id = 13)
    private String zzss;
    @Field(getter = "getIdpResponseUrl", id = 14)
    private String zzst;
    @Field(getter = "getReturnIdpCredential", id = 16)
    private boolean zzsu;

    public zzfm() {
        this.zzsj = true;
        this.zzjp = true;
    }

    @Constructor
    zzfm(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3, @Param(id = 5) String str4, @Param(id = 6) String str5, @Param(id = 7) String str6, @Param(id = 8) String str7, @Param(id = 9) String str8, @Param(id = 10) boolean z, @Param(id = 11) boolean z2, @Param(id = 12) String str9, @Param(id = 13) String str10, @Param(id = 14) String str11, @Param(id = 15) String str12, @Param(id = 16) boolean z3, @Param(id = 17) String str13) {
        this.zzsn = str;
        this.zzso = str2;
        this.zzib = str3;
        this.zzic = str4;
        this.zzia = str5;
        this.zzif = str6;
        this.zzsp = str7;
        this.zzsq = str8;
        this.zzsj = z;
        this.zzjp = z2;
        this.zzsr = str9;
        this.zzss = str10;
        this.zzst = str11;
        this.zzhy = str12;
        this.zzsu = z3;
        this.zzie = str13;
    }

    public zzfm(@Nullable String str, @Nullable String str2, String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8) {
        this.zzsn = "http://localhost";
        this.zzib = str;
        this.zzic = str2;
        this.zzsq = str5;
        this.zzsr = str6;
        this.zzhy = str7;
        this.zzie = str8;
        this.zzsj = true;
        if (TextUtils.isEmpty(this.zzib) && TextUtils.isEmpty(this.zzic) && TextUtils.isEmpty(this.zzsr)) {
            throw new IllegalArgumentException("idToken, accessToken and authCode cannot all be null");
        }
        this.zzia = Preconditions.checkNotEmpty(str3);
        this.zzif = null;
        StringBuilder stringBuilder = new StringBuilder();
        str4 = "&";
        if (!TextUtils.isEmpty(this.zzib)) {
            stringBuilder.append("id_token=");
            stringBuilder.append(this.zzib);
            stringBuilder.append(str4);
        }
        if (!TextUtils.isEmpty(this.zzic)) {
            stringBuilder.append("access_token=");
            stringBuilder.append(this.zzic);
            stringBuilder.append(str4);
        }
        if (!TextUtils.isEmpty(this.zzif)) {
            stringBuilder.append("identifier=");
            stringBuilder.append(this.zzif);
            stringBuilder.append(str4);
        }
        if (!TextUtils.isEmpty(this.zzsq)) {
            stringBuilder.append("oauth_token_secret=");
            stringBuilder.append(this.zzsq);
            stringBuilder.append(str4);
        }
        if (!TextUtils.isEmpty(this.zzsr)) {
            stringBuilder.append("code=");
            stringBuilder.append(this.zzsr);
            stringBuilder.append(str4);
        }
        stringBuilder.append("providerId=");
        stringBuilder.append(this.zzia);
        this.zzsp = stringBuilder.toString();
        this.zzjp = true;
    }

    public final zzfm zzcy(String str) {
        this.zzso = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zzfm zzp(boolean z) {
        this.zzjp = false;
        return this;
    }

    public final zzfm zzcz(@Nullable String str) {
        this.zzhy = str;
        return this;
    }

    public final zzfm zzq(boolean z) {
        this.zzsj = true;
        return this;
    }

    public final zzfm zzr(boolean z) {
        this.zzsu = z;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzsn, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzso, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzib, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzic, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzia, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzsp, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzsq, false);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzsj);
        SafeParcelWriter.writeBoolean(parcel, 11, this.zzjp);
        SafeParcelWriter.writeString(parcel, 12, this.zzsr, false);
        SafeParcelWriter.writeString(parcel, 13, this.zzss, false);
        SafeParcelWriter.writeString(parcel, 14, this.zzst, false);
        SafeParcelWriter.writeString(parcel, 15, this.zzhy, false);
        SafeParcelWriter.writeBoolean(parcel, 16, this.zzsu);
        SafeParcelWriter.writeString(parcel, 17, this.zzie, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzk = zzp.zzat().zzi(this.zzsj).zzk(this.zzjp);
        String str = this.zzso;
        if (str != null) {
            zzk.zzbg(str);
        }
        str = this.zzsn;
        if (str != null) {
            zzk.zzbd(str);
        }
        str = this.zzsp;
        if (str != null) {
            zzk.zzbe(str);
        }
        str = this.zzhy;
        if (str != null) {
            zzk.zzbh(str);
        }
        str = this.zzie;
        if (str != null) {
            zzk.zzbi(str);
        }
        if (!TextUtils.isEmpty(this.zzss)) {
            zzk.zzbf(this.zzss);
        }
        if (!TextUtils.isEmpty(this.zzst)) {
            zzk.zzbd(this.zzst);
        }
        return (zzp) ((zzhs) zzk.zzj(this.zzsu).zzih());
    }
}
