package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.internal.firebase_auth.zzay;
import com.google.firebase.auth.zzac;
import com.google.firebase.auth.zzx;
import java.util.ArrayList;
import java.util.List;

@Class(creator = "MultiFactorInfoListCreator")
public final class zzao extends AbstractSafeParcelable {
    public static final Creator<zzao> CREATOR = new zzar();
    @Field(getter = "getPhoneMultiFactorInfoList", id = 1)
    private final List<zzac> zzts;

    @Constructor
    zzao(@Param(id = 1) List<zzac> list) {
        List list2;
        if (list2 == null) {
            list2 = zzay.zzce();
        }
        this.zzts = list2;
    }

    public static zzao zzf(List<zzx> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List arrayList = new ArrayList();
        for (zzx zzx : list) {
            if (zzx instanceof zzac) {
                arrayList.add((zzac) zzx);
            }
        }
        return new zzao(arrayList);
    }

    public final List<zzx> zzdp() {
        List<zzx> arrayList = new ArrayList();
        for (zzac add : this.zzts) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.zzts, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
