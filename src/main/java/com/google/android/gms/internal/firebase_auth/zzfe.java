package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.internal.firebase_auth.zzhs.zza;
import com.google.android.gms.internal.firebase_auth.zzp.zzk;
import com.google.firebase.auth.api.internal.zzfd;

@Class(creator = "SendVerificationCodeRequestCreator")
public final class zzfe extends AbstractSafeParcelable implements zzfd<zzk> {
    public static final Creator<zzfe> CREATOR = new zzfh();
    @Field(getter = "getTenantId", id = 5)
    @Nullable
    private final String zzhy;
    @Field(getter = "getPhoneNumber", id = 1)
    private final String zzjo;
    @Field(getter = "getTimeoutInSeconds", id = 2)
    private final long zzko;
    @Field(getter = "getForceNewSmsVerificationSession", id = 3)
    private final boolean zzsf;
    @Field(getter = "getLanguageHeader", id = 4)
    private final String zzsg;

    @Constructor
    public zzfe(@Param(id = 1) String str, @Param(id = 2) long j, @Param(id = 3) boolean z, @Param(id = 4) String str2, @Param(id = 5) @Nullable String str3) {
        this.zzjo = Preconditions.checkNotEmpty(str);
        this.zzko = j;
        this.zzsf = z;
        this.zzsg = str2;
        this.zzhy = str3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzjo, false);
        SafeParcelWriter.writeLong(parcel, 2, this.zzko);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzsf);
        SafeParcelWriter.writeString(parcel, 4, this.zzsg, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzak = zzk.zzah().zzak(this.zzjo);
        String str = this.zzhy;
        if (str != null) {
            zzak.zzal(str);
        }
        return (zzk) ((zzhs) zzak.zzih());
    }
}
