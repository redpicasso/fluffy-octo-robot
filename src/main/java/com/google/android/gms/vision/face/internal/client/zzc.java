package com.google.android.gms.vision.face.internal.client;

import android.content.Context;
import android.graphics.PointF;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.DynamiteModule.LoadingException;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.vision.dynamite.face.ModuleDescriptor;
import com.google.android.gms.internal.vision.zzm;
import com.google.android.gms.internal.vision.zzn;
import com.google.android.gms.internal.vision.zzp;
import com.google.android.gms.vision.face.Contour;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import java.nio.ByteBuffer;

public final class zzc extends zzm<zzg> {
    private final zze zzda;

    public zzc(Context context, zze zze) {
        super(context, "FaceNativeHandle", "face");
        this.zzda = zze;
        zzq();
    }

    public final Face[] zzb(ByteBuffer byteBuffer, zzn zzn) {
        int i = 0;
        if (!isOperational()) {
            return new Face[0];
        }
        try {
            FaceParcel[] zzc = ((zzg) zzq()).zzc(ObjectWrapper.wrap(byteBuffer), zzn);
            Face[] faceArr = new Face[zzc.length];
            while (0 < zzc.length) {
                FaceParcel[] faceParcelArr;
                Face[] faceArr2;
                Landmark[] landmarkArr;
                Contour[] contourArr;
                FaceParcel faceParcel = zzc[0];
                int i2 = faceParcel.id;
                PointF pointF = new PointF(faceParcel.centerX, faceParcel.centerY);
                float f = faceParcel.width;
                float f2 = faceParcel.height;
                float f3 = faceParcel.zzdb;
                float f4 = faceParcel.zzdc;
                float f5 = faceParcel.zzdd;
                LandmarkParcel[] landmarkParcelArr = faceParcel.zzde;
                if (landmarkParcelArr == null) {
                    faceParcelArr = zzc;
                    faceArr2 = faceArr;
                    landmarkArr = new Landmark[i];
                } else {
                    landmarkArr = new Landmark[landmarkParcelArr.length];
                    while (0 < landmarkParcelArr.length) {
                        LandmarkParcel landmarkParcel = landmarkParcelArr[0];
                        faceParcelArr = zzc;
                        LandmarkParcel[] landmarkParcelArr2 = landmarkParcelArr;
                        faceArr2 = faceArr;
                        landmarkArr[0] = new Landmark(new PointF(landmarkParcel.x, landmarkParcel.y), landmarkParcel.type);
                        int i3 = 0 + 1;
                        zzc = faceParcelArr;
                        faceArr = faceArr2;
                        landmarkParcelArr = landmarkParcelArr2;
                    }
                    faceParcelArr = zzc;
                    faceArr2 = faceArr;
                }
                zza[] zzaArr = faceParcel.zzdf;
                if (zzaArr == null) {
                    contourArr = new Contour[0];
                } else {
                    Contour[] contourArr2 = new Contour[zzaArr.length];
                    for (int i4 = 0; i4 < zzaArr.length; i4++) {
                        zza zza = zzaArr[i4];
                        contourArr2[i4] = new Contour(zza.zzcz, zza.type);
                    }
                    contourArr = contourArr2;
                }
                float f6 = faceParcel.zzcg;
                float f7 = faceParcel.zzch;
                i = faceParcel.zzci;
                faceArr2[0] = new Face(i2, pointF, f, f2, f3, f4, f5, landmarkArr, contourArr, f6, f7, i);
                int i5 = 0 + 1;
                zzc = faceParcelArr;
                faceArr = faceArr2;
            }
            return faceArr;
        } catch (Throwable e) {
            Log.e("FaceNativeHandle", "Could not call native face detector", e);
            return new Face[0];
        }
    }

    public final boolean zzd(int i) {
        if (!isOperational()) {
            return false;
        }
        try {
            return ((zzg) zzq()).zzd(i);
        } catch (Throwable e) {
            Log.e("FaceNativeHandle", "Could not call native face detector", e);
            return false;
        }
    }

    protected final void zzm() throws RemoteException {
        ((zzg) zzq()).zzn();
    }

    protected final /* synthetic */ Object zza(DynamiteModule dynamiteModule, Context context) throws RemoteException, LoadingException {
        zzj asInterface;
        if (zzp.zza(context, ModuleDescriptor.MODULE_ID)) {
            asInterface = zzk.asInterface(dynamiteModule.instantiate("com.google.android.gms.vision.face.NativeFaceDetectorV2Creator"));
        } else {
            asInterface = zzk.asInterface(dynamiteModule.instantiate("com.google.android.gms.vision.face.ChimeraNativeFaceDetectorCreator"));
        }
        if (asInterface == null) {
            return null;
        }
        return asInterface.newFaceDetector(ObjectWrapper.wrap(context), this.zzda);
    }
}
