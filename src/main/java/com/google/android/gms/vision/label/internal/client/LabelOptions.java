package com.google.android.gms.vision.label.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@ShowFirstParty
@KeepForSdk
@Class(creator = "LabelOptionsCreator")
@Reserved({1})
public class LabelOptions extends AbstractSafeParcelable {
    public static final Creator<LabelOptions> CREATOR = new zzh();
    @Field(id = 2)
    @Deprecated
    public final int zzej;

    @Constructor
    public LabelOptions(@Param(id = 2) int i) {
        this.zzej = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzej);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
