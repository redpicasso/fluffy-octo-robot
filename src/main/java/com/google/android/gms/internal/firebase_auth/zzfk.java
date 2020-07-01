package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import com.google.android.gms.common.util.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Class(creator = "StringListCreator")
public final class zzfk extends AbstractSafeParcelable {
    public static final Creator<zzfk> CREATOR = new zzfn();
    @VersionField(id = 1)
    private final int versionCode;
    @Field(getter = "getValues", id = 2)
    private List<String> zzsm;

    public final List<String> zzez() {
        return this.zzsm;
    }

    public zzfk() {
        this(null);
    }

    private zzfk(@Nullable List<String> list) {
        this.versionCode = 1;
        this.zzsm = new ArrayList();
        if (list != null && !list.isEmpty()) {
            this.zzsm.addAll(list);
        }
    }

    @Constructor
    zzfk(@Param(id = 1) int i, @Param(id = 2) List<String> list) {
        this.versionCode = i;
        if (list == null || list.isEmpty()) {
            this.zzsm = Collections.emptyList();
            return;
        }
        for (i = 0; i < list.size(); i++) {
            list.set(i, Strings.emptyToNull((String) list.get(i)));
        }
        this.zzsm = Collections.unmodifiableList(list);
    }

    public static zzfk zzfa() {
        return new zzfk(null);
    }

    public static zzfk zza(zzfk zzfk) {
        return new zzfk(zzfk != null ? zzfk.zzsm : null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeStringList(parcel, 2, this.zzsm, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
