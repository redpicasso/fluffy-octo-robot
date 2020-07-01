package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import java.util.ArrayList;
import java.util.List;

@Class(creator = "MfaInfoCreator")
public final class zzeu extends AbstractSafeParcelable {
    public static final Creator<zzeu> CREATOR = new zzex();
    @Field(getter = "getDisplayName", id = 3)
    private final String zzjv;
    @Field(getter = "getPhoneInfo", id = 1)
    @Nullable
    private final String zzrz;
    @Field(getter = "getMfaEnrollmentId", id = 2)
    @NonNull
    private final String zzsa;
    @Field(getter = "getEnrolledAtTimestampInSeconds", id = 4)
    private final long zzsb;

    @Constructor
    public zzeu(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) String str3, @Param(id = 4) long j) {
        this.zzrz = str;
        this.zzsa = Preconditions.checkNotEmpty(str2);
        this.zzjv = str3;
        this.zzsb = j;
    }

    @Nullable
    public final String zzbk() {
        return this.zzrz;
    }

    public final String zzbl() {
        return this.zzsa;
    }

    public final String getDisplayName() {
        return this.zzjv;
    }

    public final long zzex() {
        return this.zzsb;
    }

    public static zzeu zza(zzr zzr) {
        return new zzeu(zzr.zzbk(), zzr.zzbl(), zzr.getDisplayName(), zzr.zzbm().getSeconds());
    }

    public static List<zzeu> zzd(List<zzr> list) {
        if (list == null) {
            return zzay.zzce();
        }
        List<zzeu> arrayList = new ArrayList();
        for (zzr zza : list) {
            arrayList.add(zza(zza));
        }
        return arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzrz, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzsa, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzjv, false);
        SafeParcelWriter.writeLong(parcel, 4, this.zzsb);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
