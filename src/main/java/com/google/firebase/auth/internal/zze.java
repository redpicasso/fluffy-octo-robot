package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.auth.AdditionalUserInfo;
import java.util.Map;
import javax.annotation.Nullable;

@Class(creator = "DefaultAdditionalUserInfoCreator")
public final class zze implements AdditionalUserInfo {
    public static final Creator<zze> CREATOR = new zzd();
    @Field(getter = "getProviderId", id = 1)
    private final String zzia;
    @Field(getter = "isNewUser", id = 3)
    private boolean zzrg;
    @Field(getter = "getRawUserInfo", id = 2)
    private final String zzsd;
    private Map<String, Object> zztc;

    @Constructor
    public zze(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) boolean z) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        this.zzia = str;
        this.zzsd = str2;
        this.zztc = zzam.zzde(str2);
        this.zzrg = z;
    }

    public final int describeContents() {
        return 0;
    }

    public zze(boolean z) {
        this.zzrg = z;
        this.zzsd = null;
        this.zzia = null;
        this.zztc = null;
    }

    @Nullable
    public final String getProviderId() {
        return this.zzia;
    }

    @Nullable
    public final Map<String, Object> getProfile() {
        return this.zztc;
    }

    @Nullable
    public final String getUsername() {
        if ("github.com".equals(this.zzia)) {
            return (String) this.zztc.get(Event.LOGIN);
        }
        return "twitter.com".equals(this.zzia) ? (String) this.zztc.get("screen_name") : null;
    }

    public final boolean isNewUser() {
        return this.zzrg;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getProviderId(), false);
        SafeParcelWriter.writeString(parcel, 2, this.zzsd, false);
        SafeParcelWriter.writeBoolean(parcel, 3, isNewUser());
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
