package com.google.firebase.ml.vision.object.internal;

import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;
import com.google.android.gms.internal.firebase_ml.zzb;
import com.google.android.gms.internal.firebase_ml.zzc;

public interface IObjectDetector extends IInterface {

    public static abstract class zza extends zzb implements IObjectDetector {
        public zza() {
            super("com.google.firebase.ml.vision.object.internal.IObjectDetector");
        }

        protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                Parcelable[] zza = zza(Stub.asInterface(parcel.readStrongBinder()), (zze) zzc.zza(parcel, zze.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedArray(zza, 1);
            } else if (i == 2) {
                start();
                parcel2.writeNoException();
            } else if (i != 3) {
                return false;
            } else {
                stop();
                parcel2.writeNoException();
            }
            return true;
        }
    }

    void start() throws RemoteException;

    void stop() throws RemoteException;

    zzh[] zza(IObjectWrapper iObjectWrapper, zze zze) throws RemoteException;
}
