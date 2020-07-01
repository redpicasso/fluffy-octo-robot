package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ShowFirstParty
@Class(creator = "FieldMappingDictionaryCreator")
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zaj extends AbstractSafeParcelable {
    public static final Creator<zaj> CREATOR = new zao();
    @VersionField(id = 1)
    private final int zali;
    private final HashMap<String, Map<String, Field<?, ?>>> zarj;
    @SafeParcelable.Field(getter = "getSerializedDictionary", id = 2)
    private final ArrayList<zam> zark;
    @SafeParcelable.Field(getter = "getRootClassName", id = 3)
    private final String zarl;

    @Constructor
    zaj(@Param(id = 1) int i, @Param(id = 2) ArrayList<zam> arrayList, @Param(id = 3) String str) {
        this.zali = i;
        this.zark = null;
        HashMap hashMap = new HashMap();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            zam zam = (zam) arrayList.get(i2);
            String str2 = zam.className;
            HashMap hashMap2 = new HashMap();
            int size2 = zam.zaro.size();
            for (int i3 = 0; i3 < size2; i3++) {
                zal zal = (zal) zam.zaro.get(i3);
                hashMap2.put(zal.zarm, zal.zarn);
            }
            hashMap.put(str2, hashMap2);
        }
        this.zarj = hashMap;
        this.zarl = (String) Preconditions.checkNotNull(str);
        zacp();
    }

    public final void zacp() {
        for (String str : this.zarj.keySet()) {
            Map map = (Map) this.zarj.get(str);
            for (String str2 : map.keySet()) {
                ((Field) map.get(str2)).zaa(this);
            }
        }
    }

    public final void zacq() {
        for (String str : this.zarj.keySet()) {
            Map map = (Map) this.zarj.get(str);
            HashMap hashMap = new HashMap();
            for (String str2 : map.keySet()) {
                hashMap.put(str2, ((Field) map.get(str2)).zacj());
            }
            this.zarj.put(str, hashMap);
        }
    }

    public zaj(Class<? extends FastJsonResponse> cls) {
        this.zali = 1;
        this.zark = null;
        this.zarj = new HashMap();
        this.zarl = cls.getCanonicalName();
    }

    public final void zaa(Class<? extends FastJsonResponse> cls, Map<String, Field<?, ?>> map) {
        this.zarj.put(cls.getCanonicalName(), map);
    }

    public final Map<String, Field<?, ?>> zai(String str) {
        return (Map) this.zarj.get(str);
    }

    public final boolean zaa(Class<? extends FastJsonResponse> cls) {
        return this.zarj.containsKey(cls.getCanonicalName());
    }

    public final String zacr() {
        return this.zarl;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : this.zarj.keySet()) {
            stringBuilder.append(str);
            stringBuilder.append(":\n");
            Map map = (Map) this.zarj.get(str);
            for (String str2 : map.keySet()) {
                stringBuilder.append("  ");
                stringBuilder.append(str2);
                stringBuilder.append(": ");
                stringBuilder.append(map.get(str2));
            }
        }
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zali);
        List arrayList = new ArrayList();
        for (String str : this.zarj.keySet()) {
            arrayList.add(new zam(str, (Map) this.zarj.get(str)));
        }
        SafeParcelWriter.writeTypedList(parcel, 2, arrayList, false);
        SafeParcelWriter.writeString(parcel, 3, this.zarl, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
