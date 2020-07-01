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
import com.google.android.gms.internal.firebase_auth.zzp.zzg;
import com.google.firebase.auth.api.internal.zzdv;
import java.util.List;

@Class(creator = "GetAccountInfoResponseCreator")
@Reserved({1})
public final class zzek extends AbstractSafeParcelable implements zzdv<zzek, zzg> {
    public static final Creator<zzek> CREATOR = new zzen();
    @Field(getter = "getUserList", id = 2)
    private zzeo zzro;

    @Constructor
    zzek(@Param(id = 2) zzeo zzeo) {
        if (zzeo == null) {
            zzeo = new zzeo();
        } else {
            zzeo = zzeo.zza(zzeo);
        }
        this.zzro = zzeo;
    }

    public final List<zzem> zzer() {
        return this.zzro.zzer();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzro, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzjm<zzg> zzee() {
        return zzg.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zzg) {
            zzg zzg = (zzg) zzjc;
            if (zzg.zzy() == 0) {
                this.zzro = new zzeo();
            } else {
                this.zzro = zzeo.zza(zzg);
            }
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of GetAccountInfoResponse.");
    }
}
