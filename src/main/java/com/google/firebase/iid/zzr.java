package com.google.firebase.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.annotation.NonNull;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
public final class zzr implements zzo {
    private final IBinder zza;

    zzr(@NonNull IBinder iBinder) {
        this.zza = iBinder;
    }

    public final void zza(@NonNull Message message) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken("com.google.android.gms.iid.IMessengerCompat");
        obtain.writeInt(1);
        message.writeToParcel(obtain, 0);
        try {
            this.zza.transact(1, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    @NonNull
    public final IBinder asBinder() {
        return this.zza;
    }
}
