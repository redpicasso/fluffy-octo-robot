package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import com.google.android.gms.common.server.response.FastJsonResponse.FieldConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@KeepForSdk
@Class(creator = "StringToIntConverterCreator")
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class StringToIntConverter extends AbstractSafeParcelable implements FieldConverter<String, Integer> {
    public static final Creator<StringToIntConverter> CREATOR = new zad();
    @VersionField(id = 1)
    private final int zali;
    private final HashMap<String, Integer> zaqc;
    private final SparseArray<String> zaqd;
    @Field(getter = "getSerializedMap", id = 2)
    private final ArrayList<zaa> zaqe;

    @Class(creator = "StringToIntConverterEntryCreator")
    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public static final class zaa extends AbstractSafeParcelable {
        public static final Creator<zaa> CREATOR = new zac();
        @VersionField(id = 1)
        private final int versionCode;
        @Field(id = 2)
        final String zaqa;
        @Field(id = 3)
        final int zaqb;

        @Constructor
        zaa(@Param(id = 1) int i, @Param(id = 2) String str, @Param(id = 3) int i2) {
            this.versionCode = i;
            this.zaqa = str;
            this.zaqb = i2;
        }

        zaa(String str, int i) {
            this.versionCode = 1;
            this.zaqa = str;
            this.zaqb = i;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            i = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
            SafeParcelWriter.writeString(parcel, 2, this.zaqa, false);
            SafeParcelWriter.writeInt(parcel, 3, this.zaqb);
            SafeParcelWriter.finishObjectHeader(parcel, i);
        }
    }

    @Constructor
    StringToIntConverter(@Param(id = 1) int i, @Param(id = 2) ArrayList<zaa> arrayList) {
        this.zali = i;
        this.zaqc = new HashMap();
        this.zaqd = new SparseArray();
        this.zaqe = null;
        ArrayList arrayList2 = arrayList;
        i = arrayList2.size();
        int i2 = 0;
        while (i2 < i) {
            Object obj = arrayList2.get(i2);
            i2++;
            zaa zaa = (zaa) obj;
            add(zaa.zaqa, zaa.zaqb);
        }
    }

    public final int zach() {
        return 7;
    }

    public final int zaci() {
        return 0;
    }

    @KeepForSdk
    public StringToIntConverter() {
        this.zali = 1;
        this.zaqc = new HashMap();
        this.zaqd = new SparseArray();
        this.zaqe = null;
    }

    @KeepForSdk
    public final StringToIntConverter add(String str, int i) {
        this.zaqc.put(str, Integer.valueOf(i));
        this.zaqd.put(i, str);
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zali);
        List arrayList = new ArrayList();
        for (String str : this.zaqc.keySet()) {
            arrayList.add(new zaa(str, ((Integer) this.zaqc.get(str)).intValue()));
        }
        SafeParcelWriter.writeTypedList(parcel, 2, arrayList, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public final /* synthetic */ Object convertBack(Object obj) {
        String str = (String) this.zaqd.get(((Integer) obj).intValue());
        if (str == null) {
            String str2 = "gms_unknown";
            if (this.zaqc.containsKey(str2)) {
                return str2;
            }
        }
        return str;
    }

    public final /* synthetic */ Object convert(Object obj) {
        Integer num = (Integer) this.zaqc.get((String) obj);
        return num == null ? (Integer) this.zaqc.get("gms_unknown") : num;
    }
}
