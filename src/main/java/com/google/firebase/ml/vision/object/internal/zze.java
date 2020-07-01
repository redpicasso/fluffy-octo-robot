package com.google.firebase.ml.vision.object.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "ImageMetadataParcelCreator")
public final class zze extends AbstractSafeParcelable {
    public static final Creator<zze> CREATOR = new zzf();
    @Field(id = 2)
    public final int height;
    @Field(id = 3)
    private final int id;
    @Field(id = 5)
    public final int rotation;
    @Field(id = 1)
    public final int width;
    @Field(id = 4)
    public final long zzauo;

    @Constructor
    public zze(@Param(id = 1) int i, @Param(id = 2) int i2, @Param(id = 3) int i3, @Param(id = 4) long j, @Param(id = 5) int i4) {
        this.width = i;
        this.height = i2;
        this.id = i3;
        this.zzauo = j;
        this.rotation = i4;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.width);
        SafeParcelWriter.writeInt(parcel, 2, this.height);
        SafeParcelWriter.writeInt(parcel, 3, this.id);
        SafeParcelWriter.writeLong(parcel, 4, this.zzauo);
        SafeParcelWriter.writeInt(parcel, 5, this.rotation);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
