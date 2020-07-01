package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;

@Class(creator = "SignInResponseCreator")
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zak extends AbstractSafeParcelable {
    public static final Creator<zak> CREATOR = new zaj();
    @VersionField(id = 1)
    private final int versionCode;
    @Field(getter = "getConnectionResult", id = 2)
    private final ConnectionResult zapo;
    @Field(getter = "getResolveAccountResponse", id = 3)
    @Nullable
    private final ResolveAccountResponse zata;

    @Constructor
    zak(@Param(id = 1) int i, @Param(id = 2) ConnectionResult connectionResult, @Param(id = 3) @Nullable ResolveAccountResponse resolveAccountResponse) {
        this.versionCode = i;
        this.zapo = connectionResult;
        this.zata = resolveAccountResponse;
    }

    public zak(int i) {
        this(new ConnectionResult(8, null), null);
    }

    private zak(ConnectionResult connectionResult, @Nullable ResolveAccountResponse resolveAccountResponse) {
        this(1, connectionResult, null);
    }

    public final ConnectionResult getConnectionResult() {
        return this.zapo;
    }

    @Nullable
    public final ResolveAccountResponse zacv() {
        return this.zata;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zapo, i, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zata, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
