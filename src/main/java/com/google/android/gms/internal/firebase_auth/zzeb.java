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
import com.google.firebase.auth.internal.zzap;
import com.google.firebase.auth.zzf;
import com.google.firebase.auth.zzx;
import java.util.List;

@Class(creator = "OnFailedMfaSignInAidlResponseCreator")
public final class zzeb extends AbstractSafeParcelable {
    public static final Creator<zzeb> CREATOR = new zzea();
    @Field(getter = "getDefaultOAuthCredential", id = 3)
    private zzf zzkw;
    @Field(getter = "getMfaPendingCredential", id = 1)
    private String zzkx;
    @Field(getter = "getMfaInfoList", id = 2)
    private List<zzeu> zzky;

    @Constructor
    public zzeb(@Param(id = 1) String str, @Param(id = 2) List<zzeu> list, @Param(id = 3) @Nullable zzf zzf) {
        this.zzkx = str;
        this.zzky = list;
        this.zzkw = zzf;
    }

    public final String zzbb() {
        return this.zzkx;
    }

    public final zzf zzdo() {
        return this.zzkw;
    }

    public final List<zzx> zzdp() {
        return zzap.zzg(this.zzky);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzkx, false);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzky, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzkw, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
