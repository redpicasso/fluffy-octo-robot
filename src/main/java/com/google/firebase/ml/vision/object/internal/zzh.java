package com.google.firebase.ml.vision.object.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "ObjectParcelCreator")
public final class zzh extends AbstractSafeParcelable {
    public static final Creator<zzh> CREATOR = new zzi();
    @Field(id = 5)
    private final int category;
    @Field(id = 3)
    private final Float confidence;
    @Field(id = 4)
    private final String zzavo;
    @Field(id = 1)
    private final int[] zzazj;
    @Field(id = 2)
    private final Integer zzazk;

    @Constructor
    public zzh(@Param(id = 1) int[] iArr, @Param(id = 2) Integer num, @Param(id = 3) Float f, @Param(id = 4) String str, @Param(id = 5) int i) {
        this.zzazj = iArr;
        this.zzazk = num;
        this.confidence = f;
        this.zzavo = str;
        this.category = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIntArray(parcel, 1, this.zzazj, false);
        SafeParcelWriter.writeIntegerObject(parcel, 2, this.zzazk, false);
        SafeParcelWriter.writeFloatObject(parcel, 3, this.confidence, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzavo, false);
        SafeParcelWriter.writeInt(parcel, 5, this.category);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
