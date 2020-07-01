package com.google.firebase.ml.vision.object.internal;

import android.os.IInterface;
import android.os.RemoteException;

public interface zzb extends IInterface {
    IObjectDetector newObjectDetector(ObjectDetectorOptionsParcel objectDetectorOptionsParcel) throws RemoteException;
}
