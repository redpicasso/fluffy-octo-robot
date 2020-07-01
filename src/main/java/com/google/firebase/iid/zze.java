package com.google.firebase.iid;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zze implements Creator<zzf> {
    zze() {
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzf[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        IBinder readStrongBinder = parcel.readStrongBinder();
        return readStrongBinder != null ? new zzf(readStrongBinder) : null;
    }
}
