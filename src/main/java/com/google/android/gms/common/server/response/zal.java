package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;

@ShowFirstParty
@Class(creator = "FieldMapPairCreator")
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zal extends AbstractSafeParcelable {
    public static final Creator<zal> CREATOR = new zak();
    @VersionField(id = 1)
    private final int versionCode;
    @Field(id = 2)
    final String zarm;
    @Field(id = 3)
    final FastJsonResponse.Field<?, ?> zarn;

    @Constructor
    zal(@Param(id = 1) int i, @Param(id = 2) String str, @Param(id = 3) FastJsonResponse.Field<?, ?> field) {
        this.versionCode = i;
        this.zarm = str;
        this.zarn = field;
    }

    zal(String str, FastJsonResponse.Field<?, ?> field) {
        this.versionCode = 1;
        this.zarm = str;
        this.zarn = field;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeString(parcel, 2, this.zarm, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zarn, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
