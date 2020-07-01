package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.firebase.auth.EmailAuthCredential;

@Class(creator = "SignInWithEmailLinkAidlRequestCreator")
public final class zzdh extends AbstractSafeParcelable {
    public static final Creator<zzdh> CREATOR = new zzdg();
    @Field(getter = "getCredential", id = 1)
    private final EmailAuthCredential zzkn;

    @Constructor
    public zzdh(@Param(id = 1) EmailAuthCredential emailAuthCredential) {
        this.zzkn = emailAuthCredential;
    }

    public final EmailAuthCredential zzdm() {
        return this.zzkn;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkn, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
