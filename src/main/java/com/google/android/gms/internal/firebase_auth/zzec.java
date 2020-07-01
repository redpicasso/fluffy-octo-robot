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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zzb;
import com.google.firebase.auth.api.internal.zzdv;
import java.util.ArrayList;
import java.util.List;

@Class(creator = "CreateAuthUriResponseCreator")
@Reserved({1})
public final class zzec extends AbstractSafeParcelable implements zzdv<zzec, zzb> {
    public static final Creator<zzec> CREATOR = new zzef();
    @Field(getter = "getProviderId", id = 4)
    private String zzia;
    @Field(getter = "getAuthUri", id = 2)
    private String zzqy;
    @Field(getter = "isRegistered", id = 3)
    private boolean zzqz;
    @Field(getter = "isForExistingProvider", id = 5)
    private boolean zzra;
    @Field(getter = "getStringList", id = 6)
    private zzfk zzrb;
    @Field(getter = "getSignInMethods", id = 7)
    private List<String> zzrc;

    public zzec() {
        this.zzrb = zzfk.zzfa();
    }

    @Constructor
    public zzec(@Param(id = 2) String str, @Param(id = 3) boolean z, @Param(id = 4) String str2, @Param(id = 5) boolean z2, @Param(id = 6) zzfk zzfk, @Param(id = 7) List<String> list) {
        this.zzqy = str;
        this.zzqz = z;
        this.zzia = str2;
        this.zzra = z2;
        this.zzrb = zzfk == null ? zzfk.zzfa() : zzfk.zza(zzfk);
        this.zzrc = list;
    }

    @Nullable
    public final List<String> getSignInMethods() {
        return this.zzrc;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzqy, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzqz);
        SafeParcelWriter.writeString(parcel, 4, this.zzia, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzra);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzrb, i, false);
        SafeParcelWriter.writeStringList(parcel, 7, this.zzrc, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final zzjm<zzb> zzee() {
        return zzb.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zzb) {
            zzfk zzfa;
            zzb zzb = (zzb) zzjc;
            this.zzqy = Strings.emptyToNull(zzb.zzf());
            this.zzqz = zzb.zzi();
            this.zzia = Strings.emptyToNull(zzb.getProviderId());
            this.zzra = zzb.zzj();
            if (zzb.zzh() == 0) {
                zzfa = zzfk.zzfa();
            } else {
                zzfa = new zzfk(1, new ArrayList(zzb.zzg()));
            }
            this.zzrb = zzfa;
            this.zzrc = zzb.zzl() == 0 ? new ArrayList(0) : zzb.zzk();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of CreateAuthUriResponse.");
    }
}
