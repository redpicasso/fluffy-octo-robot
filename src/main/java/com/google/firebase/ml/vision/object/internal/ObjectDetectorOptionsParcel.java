package com.google.firebase.ml.vision.object.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "ObjectDetectorOptionsParcelCreator")
public class ObjectDetectorOptionsParcel extends AbstractSafeParcelable {
    public static final Creator<ObjectDetectorOptionsParcel> CREATOR = new zzg();
    @Field(id = 1)
    public final int zzaze;
    @Field(id = 2)
    public final boolean zzazf;
    @Field(id = 3)
    public final boolean zzazg;

    @Constructor
    public ObjectDetectorOptionsParcel(@Param(id = 1) int i, @Param(id = 2) boolean z, @Param(id = 3) boolean z2) {
        this.zzaze = i;
        this.zzazf = z;
        this.zzazg = z2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzaze);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzazf);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzazg);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
