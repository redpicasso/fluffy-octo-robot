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
import java.util.ArrayList;
import java.util.Map;

@ShowFirstParty
@Class(creator = "FieldMappingDictionaryEntryCreator")
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zam extends AbstractSafeParcelable {
    public static final Creator<zam> CREATOR = new zan();
    @Field(id = 2)
    final String className;
    @VersionField(id = 1)
    private final int versionCode;
    @Field(id = 3)
    final ArrayList<zal> zaro;

    @Constructor
    zam(@Param(id = 1) int i, @Param(id = 2) String str, @Param(id = 3) ArrayList<zal> arrayList) {
        this.versionCode = i;
        this.className = str;
        this.zaro = arrayList;
    }

    zam(String str, Map<String, FastJsonResponse.Field<?, ?>> map) {
        ArrayList arrayList;
        this.versionCode = 1;
        this.className = str;
        if (map == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList();
            for (String str2 : map.keySet()) {
                arrayList.add(new zal(str2, (FastJsonResponse.Field) map.get(str2)));
            }
        }
        this.zaro = arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeString(parcel, 2, this.className, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zaro, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
