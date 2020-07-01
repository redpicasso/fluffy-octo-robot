package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zzs;
import com.google.firebase.auth.api.internal.zzdv;

@Class(creator = "VerifyCustomTokenResponseCreator")
@Reserved({1})
public final class zzfq extends AbstractSafeParcelable implements zzdv<zzfq, zzs> {
    public static final Creator<zzfq> CREATOR = new zzft();
    @Field(getter = "getIdToken", id = 2)
    private String zzib;
    @Field(getter = "getRefreshToken", id = 3)
    private String zzkh;
    @Field(getter = "isNewUser", id = 5)
    private boolean zzrg;
    @Field(getter = "getExpiresIn", id = 4)
    private long zzrh;

    @Constructor
    zzfq(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) long j, @Param(id = 5) boolean z) {
        this.zzib = str;
        this.zzkh = str2;
        this.zzrh = j;
        this.zzrg = z;
    }

    public final String getIdToken() {
        return this.zzib;
    }

    @NonNull
    public final String zzs() {
        return this.zzkh;
    }

    public final long zzt() {
        return this.zzrh;
    }

    public final boolean isNewUser() {
        return this.zzrg;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzib, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzkh, false);
        SafeParcelWriter.writeLong(parcel, 4, this.zzrh);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzrg);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public final zzjm<zzs> zzee() {
        return zzs.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zzs) {
            zzs zzs = (zzs) zzjc;
            this.zzib = Strings.emptyToNull(zzs.getIdToken());
            this.zzkh = Strings.emptyToNull(zzs.zzs());
            this.zzrh = zzs.zzt();
            this.zzrg = zzs.zzu();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of VerifyCustomTokenResponse.");
    }
}
