package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.ArrayList;
import java.util.List;

@Class(creator = "PatternItemCreator")
@Reserved({1})
public class PatternItem extends AbstractSafeParcelable {
    public static final Creator<PatternItem> CREATOR = new zzi();
    private static final String TAG = "PatternItem";
    @Field(getter = "getType", id = 2)
    private final int type;
    @Field(getter = "getLength", id = 3)
    @Nullable
    private final Float zzdv;

    @Constructor
    public PatternItem(@Param(id = 2) int i, @Param(id = 3) @Nullable Float f) {
        boolean z = true;
        if (i != 1 && (f == null || f.floatValue() < 0.0f)) {
            z = false;
        }
        String valueOf = String.valueOf(f);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 45);
        stringBuilder.append("Invalid PatternItem: type=");
        stringBuilder.append(i);
        stringBuilder.append(" length=");
        stringBuilder.append(valueOf);
        Preconditions.checkArgument(z, stringBuilder.toString());
        this.type = i;
        this.zzdv = f;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.type);
        SafeParcelWriter.writeFloatObject(parcel, 3, this.zzdv, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.type), this.zzdv);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PatternItem)) {
            return false;
        }
        PatternItem patternItem = (PatternItem) obj;
        return this.type == patternItem.type && Objects.equal(this.zzdv, patternItem.zzdv);
    }

    public String toString() {
        int i = this.type;
        String valueOf = String.valueOf(this.zzdv);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 39);
        stringBuilder.append("[PatternItem: type=");
        stringBuilder.append(i);
        stringBuilder.append(" length=");
        stringBuilder.append(valueOf);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Nullable
    static List<PatternItem> zza(@Nullable List<PatternItem> list) {
        if (list == null) {
            return null;
        }
        List<PatternItem> arrayList = new ArrayList(list.size());
        for (Object obj : list) {
            Object obj2;
            if (obj2 == null) {
                obj2 = null;
            } else {
                Gap dash;
                int i = obj2.type;
                if (i == 0) {
                    dash = new Dash(obj2.zzdv.floatValue());
                } else if (i == 1) {
                    obj2 = new Dot();
                } else if (i != 2) {
                    String str = TAG;
                    StringBuilder stringBuilder = new StringBuilder(37);
                    stringBuilder.append("Unknown PatternItem type: ");
                    stringBuilder.append(i);
                    Log.w(str, stringBuilder.toString());
                } else {
                    dash = new Gap(obj2.zzdv.floatValue());
                }
                obj2 = dash;
            }
            arrayList.add(obj2);
        }
        return arrayList;
    }
}
