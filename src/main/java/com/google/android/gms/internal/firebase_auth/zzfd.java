package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zzj;
import com.google.firebase.auth.api.internal.zzdv;

@Class(creator = "ResetPasswordResponseCreator")
@Reserved({1})
public final class zzfd extends AbstractSafeParcelable implements zzdv<zzfd, zzj> {
    public static final Creator<zzfd> CREATOR = new zzff();
    @Field(getter = "getEmail", id = 2)
    private String zzif;
    @Field(getter = "getNewEmail", id = 3)
    private String zzku;
    @Field(getter = "getRequestType", id = 4)
    private String zzru;

    @Constructor
    zzfd(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3) {
        this.zzif = str;
        this.zzku = str2;
        this.zzru = str3;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String zzae() {
        return this.zzku;
    }

    public final String zzey() {
        return this.zzru;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzku, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzru, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public final zzjm<zzj> zzee() {
        return zzj.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zzj) {
            zzj zzj = (zzj) zzjc;
            this.zzif = Strings.emptyToNull(zzj.getEmail());
            this.zzku = Strings.emptyToNull(zzj.zzae());
            int i = zzfc.zzry[zzj.zzaf().ordinal()];
            String str = i != 1 ? i != 2 ? null : "PASSWORD_RESET" : "VERIFY_EMAIL";
            this.zzru = str;
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of ResetPasswordResponse.");
    }
}
