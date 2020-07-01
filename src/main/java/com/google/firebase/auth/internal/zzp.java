package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.firebase.auth.zzac;
import com.google.firebase.auth.zzf;
import com.google.firebase.auth.zzw;
import com.google.firebase.auth.zzx;
import java.util.ArrayList;
import java.util.List;

@Class(creator = "DefaultMultiFactorResolverCreator")
public final class zzp extends zzw {
    public static final Creator<zzp> CREATOR = new zzs();
    @Field(getter = "getDefaultOAuthCredential", id = 4)
    private final zzf zzkw;
    @Field(getter = "getFirebaseAppName", id = 3)
    private final String zztj;
    @Field(getter = "getPhoneMultiFactorInfoList", id = 1)
    private final List<zzac> zzts = new ArrayList();
    @Field(getter = "getSession", id = 2)
    private final zzr zztt;

    @Constructor
    public zzp(@Param(id = 1) List<zzac> list, @Param(id = 2) zzr zzr, @Param(id = 3) String str, @Param(id = 4) @Nullable zzf zzf) {
        for (zzx zzx : list) {
            if (zzx instanceof zzac) {
                this.zzts.add((zzac) zzx);
            }
        }
        this.zztt = (zzr) Preconditions.checkNotNull(zzr);
        this.zztj = Preconditions.checkNotEmpty(str);
        this.zzkw = zzf;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.zzts, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zztt, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.zztj, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzkw, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
