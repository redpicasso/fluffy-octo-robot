package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "PhoneAuthCredentialCreator")
public class PhoneAuthCredential extends AuthCredential implements Cloneable {
    public static final Creator<PhoneAuthCredential> CREATOR = new zzaa();
    @Field(getter = "getSessionInfo", id = 1)
    private String zzjl;
    @Field(getter = "getSmsCode", id = 2)
    private String zzjm;
    @Field(getter = "getHasVerificationProof", id = 3)
    private boolean zzjn;
    @Field(getter = "getPhoneNumber", id = 4)
    private String zzjo;
    @Field(getter = "getAutoCreate", id = 5)
    private boolean zzjp;
    @Field(getter = "getTemporaryProof", id = 6)
    private String zzjq;

    @Constructor
    PhoneAuthCredential(@Param(id = 1) @Nullable String str, @Param(id = 2) @Nullable String str2, @Param(id = 3) boolean z, @Param(id = 4) @Nullable String str3, @Param(id = 5) boolean z2, @Param(id = 6) @Nullable String str4) {
        boolean z3 = (z && !TextUtils.isEmpty(str3)) || !((TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) && (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)));
        Preconditions.checkArgument(z3, "Cannot create PhoneAuthCredential without either verificationProof, sessionInfo, ortemprary proof.");
        this.zzjl = str;
        this.zzjm = str2;
        this.zzjn = z;
        this.zzjo = str3;
        this.zzjp = z2;
        this.zzjq = str4;
    }

    @NonNull
    public String getProvider() {
        return "phone";
    }

    public String getSignInMethod() {
        return "phone";
    }

    @Nullable
    public String getSmsCode() {
        return this.zzjm;
    }

    public final PhoneAuthCredential zzn(boolean z) {
        this.zzjp = false;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzjl, false);
        SafeParcelWriter.writeString(parcel, 2, getSmsCode(), false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzjn);
        SafeParcelWriter.writeString(parcel, 4, this.zzjo, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzjp);
        SafeParcelWriter.writeString(parcel, 6, this.zzjq, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return new PhoneAuthCredential(this.zzjl, getSmsCode(), this.zzjn, this.zzjo, this.zzjp, this.zzjq);
    }
}
