package com.google.firebase.ml.vision.automl.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "ImageLabelParcelCreator")
public final class zze extends AbstractSafeParcelable {
    public static final Creator<zze> CREATOR = new zzf();
    @Field(id = 2)
    private final String text;
    @Field(id = 3)
    private final float zzatw;
    @Field(id = 1)
    private final String zzavo;

    @Constructor
    public zze(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) float f) {
        this.zzavo = str;
        this.text = str2;
        this.zzatw = f;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzavo, false);
        SafeParcelWriter.writeString(parcel, 2, this.text, false);
        SafeParcelWriter.writeFloat(parcel, 3, this.zzatw);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zze)) {
            return false;
        }
        zze zze = (zze) obj;
        return Objects.equal(this.zzavo, zze.zzavo) && Objects.equal(this.text, zze.text) && Float.compare(this.zzatw, zze.zzatw) == 0;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzavo, this.text, Float.valueOf(this.zzatw));
    }
}
