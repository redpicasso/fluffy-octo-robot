package com.google.firebase.ml.vision.automl.internal;

import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;
import com.google.android.gms.internal.firebase_ml.zzb;
import com.google.android.gms.internal.firebase_ml.zzc;

public interface IOnDeviceAutoMLImageLabeler extends IInterface {

    public static abstract class zza extends zzb implements IOnDeviceAutoMLImageLabeler {
        public zza() {
            super("com.google.firebase.ml.vision.automl.internal.IOnDeviceAutoMLImageLabeler");
        }

        protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                Parcelable[] zza = zza(Stub.asInterface(parcel.readStrongBinder()), (zzg) zzc.zza(parcel, zzg.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedArray(zza, 1);
            } else if (i == 2) {
                zzlp();
                parcel2.writeNoException();
            } else if (i != 3) {
                return false;
            } else {
                close();
                parcel2.writeNoException();
            }
            return true;
        }
    }

    void close() throws RemoteException;

    zze[] zza(IObjectWrapper iObjectWrapper, zzg zzg) throws RemoteException;

    void zzlp() throws RemoteException;
}
