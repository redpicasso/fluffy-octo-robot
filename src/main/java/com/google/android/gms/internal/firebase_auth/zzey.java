package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.Strings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Class(creator = "ProviderUserInfoListCreator")
@Reserved({1})
public final class zzey extends AbstractSafeParcelable {
    public static final Creator<zzey> CREATOR = new zzfb();
    @Field(getter = "getProviderUserInfos", id = 2)
    private List<zzew> zzse;

    public zzey() {
        this.zzse = new ArrayList();
    }

    @Constructor
    zzey(@Param(id = 2) List<zzew> list) {
        if (list == null || list.isEmpty()) {
            this.zzse = Collections.emptyList();
        } else {
            this.zzse = Collections.unmodifiableList(list);
        }
    }

    public final List<zzew> zzes() {
        return this.zzse;
    }

    public static zzey zza(zzey zzey) {
        Collection collection = zzey.zzse;
        zzey zzey2 = new zzey();
        if (collection != null) {
            zzey2.zzse.addAll(collection);
        }
        return zzey2;
    }

    public static zzey zze(List<zzu> list) {
        List arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            zzu zzu = (zzu) list.get(i);
            arrayList.add(new zzew(Strings.emptyToNull(zzu.zzbo()), Strings.emptyToNull(zzu.getDisplayName()), Strings.emptyToNull(zzu.zzam()), Strings.emptyToNull(zzu.getProviderId()), null, Strings.emptyToNull(zzu.getPhoneNumber()), Strings.emptyToNull(zzu.getEmail())));
        }
        return new zzey(arrayList);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzse, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
