package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;

@KeepForSdk
@Class(creator = "FavaDiagnosticsEntityCreator")
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public class FavaDiagnosticsEntity extends AbstractSafeParcelable implements ReflectedParcelable {
    @KeepForSdk
    public static final Creator<FavaDiagnosticsEntity> CREATOR = new zaa();
    @VersionField(id = 1)
    private final int zali;
    @Field(id = 2)
    private final String zapx;
    @Field(id = 3)
    private final int zapy;

    @Constructor
    public FavaDiagnosticsEntity(@Param(id = 1) int i, @Param(id = 2) String str, @Param(id = 3) int i2) {
        this.zali = i;
        this.zapx = str;
        this.zapy = i2;
    }

    @KeepForSdk
    public FavaDiagnosticsEntity(String str, int i) {
        this.zali = 1;
        this.zapx = str;
        this.zapy = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zali);
        SafeParcelWriter.writeString(parcel, 2, this.zapx, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zapy);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
