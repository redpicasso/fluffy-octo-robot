package com.google.android.gms.vision.label.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.Locale;

@Class(creator = "ImageLabelerOptionsCreator")
@Reserved({1})
public class ImageLabelerOptions extends AbstractSafeParcelable {
    public static final Creator<ImageLabelerOptions> CREATOR = new zzg();
    @Field(id = 2)
    private int zzef;
    @Field(id = 3)
    public int zzeg;
    @Field(id = 4)
    public float zzeh;
    @Field(id = 5)
    public int zzei;

    public static int zza(String str) {
        if (str.equals(Locale.ENGLISH.getLanguage())) {
        }
        return 1;
    }

    @Constructor
    public ImageLabelerOptions(@Param(id = 2) int i, @Param(id = 3) int i2, @Param(id = 4) float f, @Param(id = 5) int i3) {
        if (i == 1) {
            this.zzef = i;
            this.zzeg = i2;
            this.zzeh = f;
            this.zzei = i3;
            return;
        }
        throw new IllegalArgumentException("Unknown language.");
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzef);
        SafeParcelWriter.writeInt(parcel, 3, this.zzeg);
        SafeParcelWriter.writeFloat(parcel, 4, this.zzeh);
        SafeParcelWriter.writeInt(parcel, 5, this.zzei);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
