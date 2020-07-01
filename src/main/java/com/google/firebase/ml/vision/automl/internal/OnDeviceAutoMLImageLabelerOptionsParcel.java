package com.google.firebase.ml.vision.automl.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "OnDeviceAutoMLImageLabelerOptionsParcelCreator")
public class OnDeviceAutoMLImageLabelerOptionsParcel extends AbstractSafeParcelable {
    public static final Creator<OnDeviceAutoMLImageLabelerOptionsParcel> CREATOR = new zzi();
    @Field(id = 2)
    public final String zzasr;
    @Field(id = 3)
    public final String zzass;
    @Field(id = 1)
    public final float zzavp;

    @Constructor
    public OnDeviceAutoMLImageLabelerOptionsParcel(@Param(id = 1) float f, @Param(id = 2) String str, @Param(id = 3) String str2) {
        this.zzavp = f;
        this.zzasr = str;
        this.zzass = str2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeFloat(parcel, 1, this.zzavp);
        SafeParcelWriter.writeString(parcel, 2, this.zzasr, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzass, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
